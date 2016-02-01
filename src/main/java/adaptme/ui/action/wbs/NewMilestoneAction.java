package adaptme.ui.action.wbs;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.eclipse.epf.uma.Activity;
import org.eclipse.epf.uma.BreakdownElement;
import org.eclipse.epf.uma.Milestone;
import org.jdesktop.swingx.JXTreeTable;

import adaptme.base.ObjectFactory;
import adaptme.ui.components.model.SPEMWorkBreakdownStructureTreeTableModel;

public class NewMilestoneAction extends AbstractAction {

    private static final long serialVersionUID = 7147230724465964521L;
    BreakdownElement breakdownElement;
    private JXTreeTable treeTable;

    public NewMilestoneAction(String title, BreakdownElement process, JXTreeTable treeTable) {
	super(title);
	this.breakdownElement = process;
	this.treeTable = treeTable;

    }

    @Override
    public void actionPerformed(ActionEvent e) {

	String name = ObjectFactory.showInputDialogForName();
	if (name != null) {
	    Milestone milestone = ObjectFactory.newMilestone(name, breakdownElement);
	    ((Activity) breakdownElement).getBreakdownElementOrRoadmap().add(milestone);
	    treeTable.updateUI();
	    ((SPEMWorkBreakdownStructureTreeTableModel) treeTable.getTreeTableModel()).update();
	}
    }

}
