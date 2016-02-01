package adaptme.ui.action.wbs;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.eclipse.epf.uma.Activity;
import org.eclipse.epf.uma.BreakdownElement;
import org.eclipse.epf.uma.Phase;
import org.jdesktop.swingx.JXTreeTable;

import adaptme.base.ObjectFactory;
import adaptme.ui.components.model.SPEMWorkBreakdownStructureTreeTableModel;

public class NewPhaseAction extends AbstractAction {

    private static final long serialVersionUID = -4256377970495005054L;
    BreakdownElement breakdownElement;
    private JXTreeTable treeTable;

    public NewPhaseAction(String title, BreakdownElement process, JXTreeTable treeTable) {
	super(title);
	this.breakdownElement = process;
	this.treeTable = treeTable;

    }

    @Override
    public void actionPerformed(ActionEvent e) {

	String name = ObjectFactory.showInputDialogForName();
	if (name != null) {
	    Phase phase = ObjectFactory.newPhase(name, breakdownElement);
	    ((Activity) breakdownElement).getBreakdownElementOrRoadmap().add(phase);
	    treeTable.updateUI();
	    ((SPEMWorkBreakdownStructureTreeTableModel) treeTable.getTreeTableModel()).update();
	}

    }

}
