package adaptme.ui.action.wbs;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.eclipse.epf.uma.Activity;
import org.eclipse.epf.uma.BreakdownElement;
import org.eclipse.epf.uma.TaskDescriptor;
import org.jdesktop.swingx.JXTreeTable;

import adaptme.base.ObjectFactory;
import adaptme.ui.components.model.SPEMWorkBreakdownStructureTreeTableModel;

public class NewTaskDescriptorAction extends AbstractAction {

    private static final long serialVersionUID = 4367485981289323526L;
    BreakdownElement breakdownElement;
    private JXTreeTable treeTable;

    public NewTaskDescriptorAction(String title, BreakdownElement breakdownElement, JXTreeTable treeTable) {
	super(title);
	this.breakdownElement = breakdownElement;
	this.treeTable = treeTable;

    }

    @Override
    public void actionPerformed(ActionEvent e) {

	String name = ObjectFactory.showInputDialogForName();
	if (name != null) {
	    TaskDescriptor taskDescriptor = ObjectFactory.newTaskDescriptor(name, breakdownElement);
	    ((Activity) breakdownElement).getBreakdownElementOrRoadmap().add(taskDescriptor);
	    treeTable.updateUI();
	    ((SPEMWorkBreakdownStructureTreeTableModel) treeTable.getTreeTableModel()).update();
	}
    }

}
