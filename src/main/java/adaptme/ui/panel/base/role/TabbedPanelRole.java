package adaptme.ui.panel.base.role;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListDataListener;
import javax.swing.tree.MutableTreeNode;

import org.eclipse.epf.uma.ContentCategoryPackage;
import org.eclipse.epf.uma.Role;

import adaptme.base.ElementWrapper;
import adaptme.base.MethodLibraryHash;
import adaptme.ui.panel.base.TabbedPanel;
import adaptme.ui.window.AdaptMeUI;
import adaptme.util.EPFConstants;

public class TabbedPanelRole extends JTabbedPane implements TabbedPanel {

    private static final long serialVersionUID = -4587672641387777284L;
    private JPanel panelRoleDescription;
    private JPanel panelRoleWorkProducts;
    private JPanel panelRoleGuidance;
    private JPanel panelRoleCategories;

    private Role role;
    private MethodLibraryHash hash;
    private ContentCategoryPackage contentCategoryPackage;
    private AdaptMeUI owner;
    private JTree jTree;
    private MutableTreeNode node;
    private DocumentListener documentListener;
    private ListDataListener listDataListener;

    public TabbedPanelRole(Role role, MethodLibraryHash hash, ContentCategoryPackage contentCategoryPackage,
	    AdaptMeUI owner, MutableTreeNode node, JTree jTree) {
	this.role = role;
	this.hash = hash;
	this.contentCategoryPackage = contentCategoryPackage;
	this.owner = owner;
	this.node = node;
	this.jTree = jTree;
	initComponents();
    }

    private void initComponents() {
	setTabPlacement(JTabbedPane.BOTTOM);
	panelRoleDescription = new PanelRoleDescription(role, this, hash, owner);
	panelRoleWorkProducts = new PanelRoleWorkProducts(role, this, hash, owner);
	panelRoleGuidance = new PanelRoleGuidance(role, this, hash, owner);
	panelRoleCategories = new PanelRoleCategories(role, hash, contentCategoryPackage, this, owner);

	JScrollPane scrollPaneRoleDescription = new JScrollPane();
	JScrollPane scrollPaneRoleWorkProducts = new JScrollPane();
	JScrollPane scrollPaneRoleGuidance = new JScrollPane();
	JScrollPane scrollPaneRoleCategories = new JScrollPane();

	scrollPaneRoleDescription.setViewportView(panelRoleDescription);
	scrollPaneRoleWorkProducts.setViewportView(panelRoleWorkProducts);
	scrollPaneRoleGuidance.setViewportView(panelRoleGuidance);
	scrollPaneRoleCategories.setViewportView(panelRoleCategories);

	this.addTab("Description", null, scrollPaneRoleDescription, null);
	this.addTab("Work Procucts", null, scrollPaneRoleWorkProducts, null);
	this.addTab("Guidance", null, scrollPaneRoleGuidance, null);
	this.addTab("Categories", null, scrollPaneRoleCategories, null);

	this.load();
    }

    public JPanel getPanelRoleDescription() {
	return panelRoleDescription;
    }

    public JPanel getPanelRoleWorkProducts() {
	return panelRoleWorkProducts;
    }

    public JPanel getPanelRoleGuidance() {
	return panelRoleGuidance;
    }

    public JPanel getPanelRoleCategories() {
	return panelRoleCategories;
    }

    public void load() {
	((PanelRoleDescription) panelRoleDescription).load();
	((PanelRoleWorkProducts) panelRoleWorkProducts).load();
	((PanelRoleGuidance) panelRoleGuidance).load();
	((PanelRoleCategories) panelRoleCategories).load();
    }

    public void save() {
	((PanelRoleDescription) panelRoleDescription).save();
	((PanelRoleWorkProducts) panelRoleWorkProducts).save();
	((PanelRoleGuidance) panelRoleGuidance).save();
	((PanelRoleCategories) panelRoleCategories).save();

	node.setUserObject(new ElementWrapper(role, role.getName(), EPFConstants.roleIcon));
	jTree.updateUI();
    }

    public AdaptMeUI getOwner() {
	return owner;
    }

    public void setChangeListeners(DocumentListener documentListener, ListDataListener listDataListener) {
	this.documentListener = documentListener;
	this.listDataListener = listDataListener;
	((PanelRoleDescription) panelRoleDescription).setChangeListeners(this.documentListener, this.listDataListener);
	((PanelRoleWorkProducts) panelRoleWorkProducts).setChangeListeners(this.documentListener,
		this.listDataListener);
	((PanelRoleGuidance) panelRoleGuidance).setChangeListeners(this.documentListener, this.listDataListener);
	((PanelRoleCategories) panelRoleCategories).setChangeListeners(this.documentListener, this.listDataListener);

    }

    public DocumentListener getDocumentListener() {
	return documentListener;
    }

    public ListDataListener getListDataListener() {
	return listDataListener;
    }

    public MutableTreeNode getNode() {
	return node;
    }

}
