package adaptme.ui.panel.base.workproduct.outcome;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListDataListener;
import javax.swing.tree.MutableTreeNode;

import org.eclipse.epf.uma.ContentCategoryPackage;
import org.eclipse.epf.uma.Outcome;

import adaptme.base.ElementWrapper;
import adaptme.base.MethodLibraryHash;
import adaptme.ui.panel.base.TabbedPanel;
import adaptme.ui.window.AdaptMeUI;
import adaptme.util.EPFConstants;

public class TabbedPanelOutcome extends JTabbedPane implements TabbedPanel {

    private static final long serialVersionUID = -2334035871388823765L;
    private JPanel panelOutcomeDescription;
    private JPanel panelOutcomeWorkProducts;
    private JPanel panelOutcomeGuidance;
    private JPanel panelOutcomeCategories;

    Outcome outcome;
    MethodLibraryHash hash;
    ContentCategoryPackage contentCategoryPackage;
    AdaptMeUI owner;
    JTree jTree;
    MutableTreeNode node;
    private DocumentListener documentListener;
    private ListDataListener listDataListener;

    public TabbedPanelOutcome(Outcome outcome, MethodLibraryHash hash, ContentCategoryPackage contentCategoryPackage,
	    AdaptMeUI owner, MutableTreeNode node, JTree jTree) {
	this.outcome = outcome;
	this.hash = hash;
	this.contentCategoryPackage = contentCategoryPackage;
	this.owner = owner;
	this.node = node;
	this.jTree = jTree;
	initComponents();
    }

    private void initComponents() {
	setTabPlacement(JTabbedPane.BOTTOM);
	panelOutcomeDescription = new PanelOutcomeDescription(outcome, this, hash, owner);
	panelOutcomeWorkProducts = new PanelOutcomeStates(outcome, this, hash, owner);
	panelOutcomeGuidance = new PanelOutcomeGuidance(outcome, this, hash, owner);
	panelOutcomeCategories = new PanelOutcomeCategories(outcome, hash, contentCategoryPackage, this, owner);

	JScrollPane scrollPaneOutcomeDescription = new JScrollPane();
	JScrollPane scrollPaneOutcomeWorkProducts = new JScrollPane();
	JScrollPane scrollPaneOutcomeGuidance = new JScrollPane();
	JScrollPane scrollPaneOutcomeCategories = new JScrollPane();

	scrollPaneOutcomeDescription.setViewportView(panelOutcomeDescription);
	scrollPaneOutcomeWorkProducts.setViewportView(panelOutcomeWorkProducts);
	scrollPaneOutcomeGuidance.setViewportView(panelOutcomeGuidance);
	scrollPaneOutcomeCategories.setViewportView(panelOutcomeCategories);

	this.addTab("Description", null, scrollPaneOutcomeDescription, null);
	this.addTab("Guidance", null, scrollPaneOutcomeGuidance, null);
	this.addTab("Categories", null, scrollPaneOutcomeCategories, null);
	this.addTab("State", null, scrollPaneOutcomeWorkProducts, null);

	this.load();
    }

    public JPanel getPanelOutcomeDescription() {
	return panelOutcomeDescription;
    }

    public JPanel getPanelOutcomeWorkProducts() {
	return panelOutcomeWorkProducts;
    }

    public JPanel getPanelOutcomeGuidance() {
	return panelOutcomeGuidance;
    }

    public JPanel getPanelOutcomeCategories() {
	return panelOutcomeCategories;
    }

    public void load() {
	((PanelOutcomeDescription) panelOutcomeDescription).load();
	((PanelOutcomeStates) panelOutcomeWorkProducts).load();
	((PanelOutcomeGuidance) panelOutcomeGuidance).load();
	((PanelOutcomeCategories) panelOutcomeCategories).load();
    }

    public void save() {
	((PanelOutcomeDescription) panelOutcomeDescription).save();
	((PanelOutcomeStates) panelOutcomeWorkProducts).save();
	((PanelOutcomeGuidance) panelOutcomeGuidance).save();
	((PanelOutcomeCategories) panelOutcomeCategories).save();

	node.setUserObject(new ElementWrapper(outcome, outcome.getName(), EPFConstants.outcomeIcon));
	jTree.updateUI();
    }

    public AdaptMeUI getOwner() {
	return owner;
    }

    public DocumentListener getDocumentListener() {
	return documentListener;
    }

    public ListDataListener getListDataListener() {
	return listDataListener;
    }

    public void setChangeListeners(DocumentListener documentListener, ListDataListener listDataListener) {
	this.documentListener = documentListener;
	this.listDataListener = listDataListener;
	((PanelOutcomeDescription) panelOutcomeDescription).setChangeListeners(this.documentListener,
		this.listDataListener);
	((PanelOutcomeStates) panelOutcomeWorkProducts).setChangeListeners(this.documentListener,
		this.listDataListener);
	((PanelOutcomeGuidance) panelOutcomeGuidance).setChangeListeners(this.documentListener, this.listDataListener);
	((PanelOutcomeCategories) panelOutcomeCategories).setChangeListeners(this.documentListener,
		this.listDataListener);

    }

    @Override
    public MutableTreeNode getNode() {
	return node;
    }

}
