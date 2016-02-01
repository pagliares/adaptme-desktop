package adaptme.ui.action;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.eclipse.epf.uma.DeliveryProcess;
import org.eclipse.epf.uma.MethodPackage;
import org.eclipse.epf.uma.ProcessComponent;

import adaptme.base.ElementWrapper;
import adaptme.base.MethodLibraryWrapper;
import adaptme.base.ObjectFactory;
import adaptme.ui.window.perspective.pane.AlternativeOfProcessPanel;
import adaptme.util.EPFConstants;

public class NewDeliveryProcessAction extends AbstractAction {

    private static final long serialVersionUID = 795488483787579110L;
    private AlternativeOfProcessPanel spemManagementPerspectivePanel;
    private JTree mainTree;
    private List<MethodPackage> methodPluginList;
    private List<Object> processPackageList;

    public NewDeliveryProcessAction(String title, MethodLibraryWrapper methodLibraryWrapper,
	    AlternativeOfProcessPanel spemManagementPerspectivePanel, JTree mainTree) {
	super(title);
	this.spemManagementPerspectivePanel = spemManagementPerspectivePanel;
	this.mainTree = mainTree;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	String name = JOptionPane.showInputDialog("Enter the Name: ");

	DeliveryProcess deliveryProcess = ObjectFactory.newDeliveryProcess(name);
	ProcessComponent processComponent = ObjectFactory.newProcessComponent(name, deliveryProcess);

	if (methodPluginList != null) {
	    methodPluginList.add(processComponent);
	}
	if (processPackageList != null) {
	    processPackageList.add(processComponent);
	}
	DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(
		new ElementWrapper(processComponent, processComponent.getName(), EPFConstants.deliveryProcessIcon));
	TreePath path = mainTree.getSelectionPath();
	DefaultMutableTreeNode treeNode = getTreeNode(path);
	treeNode.add(newNode);

	spemManagementPerspectivePanel.processProcessComponentPanel(processComponent, newNode);

	mainTree.updateUI();
	methodPluginList = null;
	processPackageList = null;
    }

    public void setMethodPluginList(List<MethodPackage> list) {
	methodPluginList = list;
    }

    public void setProcessPackageList(List<Object> processPackageList) {
	this.processPackageList = processPackageList;
    }

    public DefaultMutableTreeNode getTreeNode(TreePath path) {
	return (DefaultMutableTreeNode) (path.getLastPathComponent());
    }
}
