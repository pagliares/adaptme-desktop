package adaptme.ui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.eclipse.epf.uma.ContentPackage;
import org.eclipse.epf.uma.MethodPlugin;

import adaptme.base.ElementWrapper;
import adaptme.base.MethodLibraryWrapper;
import adaptme.base.ObjectFactory;
import adaptme.base.TreeNodeFactory;

public class NewContentPackageAction extends AbstractAction {

    private static final long serialVersionUID = -8111172782715599490L;
    private MethodLibraryWrapper methodLibraryWrapper;
    private JTree mainTree;

    public NewContentPackageAction(String title, MethodLibraryWrapper methodLibraryWrapper, JTree mainTree) {
	super(title);
	this.methodLibraryWrapper = methodLibraryWrapper;
	this.mainTree = mainTree;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
	TreePath path = mainTree.getSelectionPath();
	if (path == null || path.getPathCount() < 2)
	    return;
	MethodPlugin methodPlugin = (MethodPlugin) ((ElementWrapper) ((DefaultMutableTreeNode) path.getParentPath()
		.getParentPath().getLastPathComponent()).getUserObject()).getElement();
	DefaultMutableTreeNode treeNode = getTreeNode(path);
	ContentPackage newContentPackage = ObjectFactory.newContentPackage(methodPlugin);
	// DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(new
	// ElementWrapper(newContentPackage, newContentPackage.getName(),
	// EPFConstants.methodContentIcon));
	DefaultMutableTreeNode newNode = (DefaultMutableTreeNode) TreeNodeFactory.buildTree(newContentPackage);
	treeNode.add(newNode);

	mainTree.updateUI();
	methodLibraryWrapper.add(methodPlugin, newContentPackage);
    }

    public DefaultMutableTreeNode getTreeNode(TreePath path) {
	return (DefaultMutableTreeNode) (path.getLastPathComponent());
    }
}
