package adaptme.ui.action.wbs;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.eclipse.epf.uma.Activity;
import org.eclipse.epf.uma.BreakdownElement;
import org.eclipse.epf.uma.Iteration;
import org.jdesktop.swingx.JXTreeTable;

import adaptme.base.ObjectFactory;
import adaptme.ui.components.model.SPEMWorkBreakdownStructureTreeTableModel;

public class NewIterationAction extends AbstractAction {

    private static final long serialVersionUID = 7192310998166031020L;
    BreakdownElement breakdownElement;
    private JXTreeTable treeTable;

    public NewIterationAction(String title, BreakdownElement process, JXTreeTable treeTable) {
	super(title);
	this.breakdownElement = process;
	this.treeTable = treeTable;

    }

    @Override
    public void actionPerformed(ActionEvent e) {

	String name = ObjectFactory.showInputDialogForName();
	if (name != null) {
	    Iteration iterarion = ObjectFactory.newIteration(name, breakdownElement);
	    ((Activity) breakdownElement).getBreakdownElementOrRoadmap().add(iterarion);
	    treeTable.updateUI();
	    ((SPEMWorkBreakdownStructureTreeTableModel) treeTable.getTreeTableModel()).update();
	}
    }

}
