package adaptme.ui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.eclipse.epf.uma.ContentCategoryPackage;
import org.eclipse.epf.uma.ContentPackage;
import org.eclipse.epf.uma.Deliverable;
import org.eclipse.epf.uma.MethodPackage;
import org.eclipse.epf.uma.MethodPlugin;

import adaptme.base.ElementWrapper;
import adaptme.base.MethodLibraryWrapper;
import adaptme.base.ObjectFactory;
import adaptme.ui.window.perspective.pane.AlternativeOfProcessPanel;
import adaptme.util.EPFConstants;

public class NewDeliverableAction extends AbstractAction {
    private static final long serialVersionUID = 3753756847171002689L;
    private MethodLibraryWrapper methodLibraryWrapper;
    private AlternativeOfProcessPanel spemManagementPerspectivePanel;
    private JTree mainTree;

    public NewDeliverableAction(String title, MethodLibraryWrapper methodLibraryWrapper,
	    AlternativeOfProcessPanel spemManagementPerspectivePanel, JTree mainTree) {
	super(title);
	this.methodLibraryWrapper = methodLibraryWrapper;
	this.spemManagementPerspectivePanel = spemManagementPerspectivePanel;
	this.mainTree = mainTree;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	TreePath path = mainTree.getSelectionPath();
	MethodPlugin methodPlugin = (MethodPlugin) ((ElementWrapper) ((DefaultMutableTreeNode) path.getParentPath()
		.getParentPath().getParentPath().getParentPath().getLastPathComponent()).getUserObject()).getElement();
	ContentCategoryPackage contentCategoryPackage = null;
	for (MethodPackage methodPackage : methodPlugin.getMethodPackage()) {
	    if (methodPackage instanceof ContentCategoryPackage) {
		contentCategoryPackage = (ContentCategoryPackage) methodPackage;
		break;
	    }
	}
	if (path == null || path.getPathCount() < 2) {
	    return;
	}
	DefaultMutableTreeNode treeNode = getTreeNode(path);
	ElementWrapper ew = (ElementWrapper) ((DefaultMutableTreeNode) path.getParentPath().getLastPathComponent())
		.getUserObject();
	ContentPackage contentPackage = (ContentPackage) ew.getElement();
	Deliverable newDeliverable = ObjectFactory.newDeliverable(contentPackage);
	DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(
		new ElementWrapper(newDeliverable, newDeliverable.getName(), EPFConstants.deliverableIcon));
	treeNode.add(newNode);
	spemManagementPerspectivePanel.processDeliverablePanel(newDeliverable, contentCategoryPackage, newNode);

	mainTree.updateUI();
	methodLibraryWrapper.add(contentPackage, newDeliverable);
    }

    public DefaultMutableTreeNode getTreeNode(TreePath path) {
	return (DefaultMutableTreeNode) (path.getLastPathComponent());
    }
}
