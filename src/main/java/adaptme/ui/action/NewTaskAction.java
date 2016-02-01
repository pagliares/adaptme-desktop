package adaptme.ui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.eclipse.epf.uma.ContentCategoryPackage;
import org.eclipse.epf.uma.ContentPackage;
import org.eclipse.epf.uma.MethodPackage;
import org.eclipse.epf.uma.MethodPlugin;
import org.eclipse.epf.uma.Task;

import adaptme.base.ElementWrapper;
import adaptme.base.MethodLibraryWrapper;
import adaptme.base.ObjectFactory;
import adaptme.ui.window.perspective.pane.AlternativeOfProcessPanel;
import adaptme.util.EPFConstants;

public class NewTaskAction extends AbstractAction {

    private static final long serialVersionUID = 6443041675472484101L;
    private MethodLibraryWrapper methodLibraryWrapper;
    private AlternativeOfProcessPanel spemManagementPerspective;
    private JTree mainTree;

    public NewTaskAction(String title, MethodLibraryWrapper methodLibraryWrapper,
	    AlternativeOfProcessPanel spemManagementPerspectivePanel, JTree mainTree) {
	super(title);
	this.methodLibraryWrapper = methodLibraryWrapper;
	spemManagementPerspective = spemManagementPerspectivePanel;
	this.mainTree = mainTree;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	TreePath path = mainTree.getSelectionPath();
	ContentCategoryPackage contentCategoryPackage = getContentCategoryPackage(path);
	if (path == null || path.getPathCount() < 2) {
	    return;
	}
	DefaultMutableTreeNode treeNode = getTreeNode(path);
	ElementWrapper ew = (ElementWrapper) ((DefaultMutableTreeNode) path.getParentPath().getLastPathComponent())
		.getUserObject();
	ContentPackage contentPackage = (ContentPackage) ew.getElement();
	Task newTask = ObjectFactory.newTask(contentPackage);
	DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(
		new ElementWrapper(newTask, newTask.getName(), EPFConstants.taskIcon));
	treeNode.add(newNode);
	spemManagementPerspective.processTaskPanel(newTask, contentCategoryPackage, newNode);

	mainTree.updateUI();
	methodLibraryWrapper.add(contentPackage, newTask);
    }

    public DefaultMutableTreeNode getTreeNode(TreePath path) {
	return (DefaultMutableTreeNode) (path.getLastPathComponent());
    }

    private ContentCategoryPackage getContentCategoryPackage(TreePath selPath) {

	ElementWrapper elementWrapper;
	while (true) {
	    elementWrapper = (ElementWrapper) ((DefaultMutableTreeNode) selPath.getParentPath().getLastPathComponent())
		    .getUserObject();
	    selPath = selPath.getParentPath();
	    if (elementWrapper.getElement() instanceof MethodPlugin) {
		break;
	    }
	}
	MethodPlugin methodPlugin = (MethodPlugin) elementWrapper.getElement();
	ContentCategoryPackage contentCategoryPackage = null;
	for (MethodPackage methodPackage : methodPlugin.getMethodPackage()) {
	    if (methodPackage instanceof ContentCategoryPackage) {
		contentCategoryPackage = (ContentCategoryPackage) methodPackage;
		break;
	    }
	}
	return contentCategoryPackage;
    }
}
