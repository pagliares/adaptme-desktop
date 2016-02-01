package adaptme.ui.panel.base.workproduct.deliverable;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListDataListener;
import javax.swing.tree.MutableTreeNode;

import org.eclipse.epf.uma.ContentCategoryPackage;
import org.eclipse.epf.uma.Deliverable;

import adaptme.base.ElementWrapper;
import adaptme.base.MethodLibraryHash;
import adaptme.ui.panel.base.TabbedPanel;
import adaptme.ui.window.AdaptMeUI;
import adaptme.util.EPFConstants;

public class TabbedPanelDeliverable extends JTabbedPane implements TabbedPanel {

    private static final long serialVersionUID = 6155861063188385449L;
    private JPanel panelDeliverableDescription;
    private JPanel panelDeliverableWorkProducts;
    private JPanel panelDeliverableGuidance;
    private JPanel panelDeliverableCategories;

    Deliverable deliverable;
    MethodLibraryHash hash;
    ContentCategoryPackage contentCategoryPackage;
    AdaptMeUI owner;
    JTree jTree;
    MutableTreeNode node;
    private DocumentListener documentListener;
    private ListDataListener listDataListener;

    public TabbedPanelDeliverable(Deliverable deliverable, MethodLibraryHash hash,
	    ContentCategoryPackage contentCategoryPackage, AdaptMeUI owner, MutableTreeNode node, JTree jTree) {
	this.deliverable = deliverable;
	this.hash = hash;
	this.contentCategoryPackage = contentCategoryPackage;
	this.owner = owner;
	this.node = node;
	this.jTree = jTree;
	initComponents();
    }

    private void initComponents() {
	setTabPlacement(JTabbedPane.BOTTOM);
	panelDeliverableDescription = new PanelDeliverableDescription(deliverable, this, hash, owner);
	panelDeliverableWorkProducts = new PanelDeliverableStates(deliverable, this, hash, owner);
	panelDeliverableGuidance = new PanelDeliverableGuidance(deliverable, this, hash, owner);
	panelDeliverableCategories = new PanelDeliverableCategories(deliverable, hash, contentCategoryPackage, this,
		owner);

	JScrollPane scrollPaneDeliverableDescription = new JScrollPane();
	JScrollPane scrollPaneDeliverableWorkProducts = new JScrollPane();
	JScrollPane scrollPaneDeliverableGuidance = new JScrollPane();
	JScrollPane scrollPaneDeliverableCategories = new JScrollPane();

	scrollPaneDeliverableDescription.setViewportView(panelDeliverableDescription);
	scrollPaneDeliverableWorkProducts.setViewportView(panelDeliverableWorkProducts);
	scrollPaneDeliverableGuidance.setViewportView(panelDeliverableGuidance);
	scrollPaneDeliverableCategories.setViewportView(panelDeliverableCategories);

	this.addTab("Description", null, scrollPaneDeliverableDescription, null);
	this.addTab("Guidance", null, scrollPaneDeliverableGuidance, null);
	this.addTab("Categories", null, scrollPaneDeliverableCategories, null);
	this.addTab("State", null, scrollPaneDeliverableWorkProducts, null);

	this.load();
    }

    public JPanel getPanelDeliverableDescription() {
	return panelDeliverableDescription;
    }

    public JPanel getPanelDeliverableWorkProducts() {
	return panelDeliverableWorkProducts;
    }

    public JPanel getPanelDeliverableGuidance() {
	return panelDeliverableGuidance;
    }

    public JPanel getPanelDeliverableCategories() {
	return panelDeliverableCategories;
    }

    public void load() {
	((PanelDeliverableDescription) panelDeliverableDescription).load();
	((PanelDeliverableStates) panelDeliverableWorkProducts).load();
	((PanelDeliverableGuidance) panelDeliverableGuidance).load();
	((PanelDeliverableCategories) panelDeliverableCategories).load();
    }

    public void save() {
	((PanelDeliverableDescription) panelDeliverableDescription).save();
	((PanelDeliverableStates) panelDeliverableWorkProducts).save();
	((PanelDeliverableGuidance) panelDeliverableGuidance).save();
	((PanelDeliverableCategories) panelDeliverableCategories).save();

	node.setUserObject(new ElementWrapper(deliverable, deliverable.getName(), EPFConstants.deliverableIcon));
	jTree.updateUI();
    }

    public void setChangeListeners(DocumentListener documentListener, ListDataListener listDataListener) {
	this.documentListener = documentListener;
	this.listDataListener = listDataListener;
	((PanelDeliverableDescription) panelDeliverableDescription).setChangeListeners(this.documentListener,
		this.listDataListener);
	((PanelDeliverableStates) panelDeliverableWorkProducts).setChangeListeners(this.documentListener,
		this.listDataListener);
	((PanelDeliverableGuidance) panelDeliverableGuidance).setChangeListeners(this.documentListener,
		this.listDataListener);
	((PanelDeliverableCategories) panelDeliverableCategories).setChangeListeners(this.documentListener,
		this.listDataListener);

    }

    public AdaptMeUI getOwner() {
	return owner;
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
