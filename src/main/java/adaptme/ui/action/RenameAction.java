package adaptme.ui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.eclipse.epf.uma.MethodElement;

import adaptme.base.ElementWrapper;
import adaptme.base.ObjectFactory;

public class RenameAction extends AbstractAction {

    private static final long serialVersionUID = -4049562280318189291L;
    private JTree mainTree;

    public RenameAction(String name, JTree mainTree) {
	super(name);
	this.mainTree = mainTree;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	TreePath path = mainTree.getSelectionPath();
	if (path == null || path.getPathCount() < 2) {
	    return;
	}
	ElementWrapper element = (ElementWrapper) ((DefaultMutableTreeNode) path.getLastPathComponent())
		.getUserObject();
	String name = ObjectFactory.showInputDialogForName();
	if (name == null) {
	    return;
	}
	element.setName(name);
	MethodElement methodElement = (MethodElement) element.getElement();
	methodElement.setName(name);
	methodElement.setPresentationName(name);
	mainTree.updateUI();
    }

}
