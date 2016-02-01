package adaptme.ui.panel.base.task;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListDataListener;
import javax.swing.tree.MutableTreeNode;

import org.eclipse.epf.uma.ContentCategoryPackage;
import org.eclipse.epf.uma.Task;

import adaptme.base.ElementWrapper;
import adaptme.base.MethodLibraryHash;
import adaptme.ui.panel.base.PanelCategories;
import adaptme.ui.panel.base.TabbedPanel;
import adaptme.ui.window.AdaptMeUI;
import adaptme.util.EPFConstants;

public class TabbedPanelTask extends JTabbedPane implements TabbedPanel {

    private static final long serialVersionUID = -5824744557066311220L;
    private JPanel panelTaskDescription;
    private JPanel panelTaskWorkProducts;
    private JPanel panelTaskGuidance;
    private JPanel panelTaskCategories;
    private JPanel panelTaskRoles;
    private JPanel panelTaskSteps;

    private Task task;
    private MethodLibraryHash hash;
    private ContentCategoryPackage contentCategoryPackage;
    private AdaptMeUI owner;
    private JTree jTree;
    private MutableTreeNode node;
    private DocumentListener documentListener;
    private ListDataListener listDataListener;

    public TabbedPanelTask(Task task, MethodLibraryHash hash, ContentCategoryPackage contentCategoryPackage,
	    AdaptMeUI owner, MutableTreeNode node, JTree jTree) {
	this.task = task;
	this.hash = hash;
	this.contentCategoryPackage = contentCategoryPackage;
	this.owner = owner;
	this.node = node;
	this.jTree = jTree;
	initComponents();
    }

    private void initComponents() {
	setTabPlacement(JTabbedPane.BOTTOM);
	panelTaskDescription = new PanelTaskDescription(task, this, hash, owner);
	panelTaskWorkProducts = new PanelTaskWorkProducts(task, this, hash, owner);
	panelTaskGuidance = new PanelTaskGuidance(task, this, hash, owner);
	panelTaskCategories = new PanelTaskCategories(task, this, contentCategoryPackage, hash, owner);
	panelTaskSteps = new PanelTaskSteps(task, this, hash, owner);
	panelTaskRoles = new PanelTaskRoles(task, this, hash, owner);

	JScrollPane scrollPaneTaskDescription = new JScrollPane();
	JScrollPane scrollPaneTaskWorkProducts = new JScrollPane();
	JScrollPane scrollPaneTaskGuidance = new JScrollPane();
	JScrollPane scrollPaneTaskCategories = new JScrollPane();
	JScrollPane scrollPaneTaskRoles = new JScrollPane();
	JScrollPane scrollPaneTaskSteps = new JScrollPane();

	scrollPaneTaskDescription.setViewportView(panelTaskDescription);
	scrollPaneTaskWorkProducts.setViewportView(panelTaskWorkProducts);
	scrollPaneTaskGuidance.setViewportView(panelTaskGuidance);
	scrollPaneTaskCategories.setViewportView(panelTaskCategories);
	scrollPaneTaskRoles.setViewportView(panelTaskRoles);
	scrollPaneTaskSteps.setViewportView(panelTaskSteps);

	this.addTab("Description", null, scrollPaneTaskDescription, null);
	this.addTab("Steps", null, scrollPaneTaskSteps, null);
	this.addTab("Roles", null, scrollPaneTaskRoles, null);
	this.addTab("Work Procucts", null, scrollPaneTaskWorkProducts, null);
	this.addTab("Guidance", null, scrollPaneTaskGuidance, null);
	this.addTab("Categories", null, scrollPaneTaskCategories, null);

	this.load();
    }

    public JPanel getPanelTaskDescription() {
	return panelTaskDescription;
    }

    public JPanel getPanelTaskWorkProducts() {
	return panelTaskWorkProducts;
    }

    public JPanel getPanelTaskGuidance() {
	return panelTaskGuidance;
    }

    public JPanel getPanelTaskCategories() {
	return panelTaskCategories;
    }

    public JPanel getPanelTaskRoles() {
	return panelTaskRoles;
    }

    public void load() {
	((PanelTaskDescription) panelTaskDescription).load();
	((PanelTaskWorkProducts) panelTaskWorkProducts).load();
	((PanelTaskGuidance) panelTaskGuidance).load();
	((PanelTaskRoles) panelTaskRoles).load();
	((PanelTaskSteps) panelTaskSteps).load();
	((PanelTaskCategories) panelTaskCategories).load();
    }

    public void save() {
	((PanelTaskDescription) panelTaskDescription).save();
	((PanelTaskWorkProducts) panelTaskWorkProducts).save();
	((PanelTaskGuidance) panelTaskGuidance).save();
	((PanelTaskRoles) panelTaskRoles).save();
	((PanelTaskSteps) panelTaskSteps).save();
	((PanelTaskCategories) panelTaskCategories).save();
	node.setUserObject(new ElementWrapper(task, task.getName(), EPFConstants.taskIcon));
	jTree.updateUI();
    }

    public JFrame getOwner() {
	return owner.getFrame();
    }

    public void setChangeListeners(DocumentListener documentListener, ListDataListener listDataListener) {
	this.documentListener = documentListener;
	this.listDataListener = listDataListener;
	((PanelTaskDescription) panelTaskDescription).setChangeListeners(this.documentListener, this.listDataListener);
	((PanelTaskWorkProducts) panelTaskWorkProducts).setChangeListeners(this.documentListener,
		this.listDataListener);
	((PanelTaskGuidance) panelTaskGuidance).setChangeListeners(this.documentListener, this.listDataListener);
	((PanelTaskRoles) panelTaskRoles).setChangeListeners(this.documentListener, this.listDataListener);
	((PanelTaskSteps) panelTaskSteps).setChangeListeners(this.documentListener, this.listDataListener);
	((PanelCategories) panelTaskCategories).setChangeListeners(this.documentListener, this.listDataListener);
    }

    @Override
    public DocumentListener getDocumentListener() {
	return documentListener;
    }

    @Override
    public ListDataListener getListDataListener() {
	return listDataListener;
    }

    @Override
    public MutableTreeNode getNode() {
	return node;
    }

}
