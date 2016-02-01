package adaptme.ui.action.wbs;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.eclipse.epf.uma.Activity;
import org.eclipse.epf.uma.BreakdownElement;
import org.jdesktop.swingx.JXTreeTable;

import adaptme.base.ObjectFactory;
import adaptme.ui.components.model.SPEMWorkBreakdownStructureTreeTableModel;

public class NewActivityAction extends AbstractAction {

    private static final long serialVersionUID = 7575334531143065465L;
    BreakdownElement breakdownElement;
    private JXTreeTable treeTable;

    public NewActivityAction(String title, BreakdownElement breakdownElement, JXTreeTable treeTable) {
	super(title);
	this.breakdownElement = breakdownElement;
	this.treeTable = treeTable;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

	String name = ObjectFactory.showInputDialogForName();
	if (name != null) {
	    if (breakdownElement instanceof Activity) {
		Activity activity = ObjectFactory.newActivity(name, breakdownElement);
		((Activity) breakdownElement).getBreakdownElementOrRoadmap().add(activity);
		treeTable.updateUI();
		((SPEMWorkBreakdownStructureTreeTableModel) treeTable.getTreeTableModel()).update();
	    }
	}
    }

}
