package adaptme.ui.panel.base.workproduct.artifact;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListDataListener;
import javax.swing.tree.MutableTreeNode;

import org.eclipse.epf.uma.Artifact;
import org.eclipse.epf.uma.ContentCategoryPackage;

import adaptme.base.ElementWrapper;
import adaptme.base.MethodLibraryHash;
import adaptme.ui.panel.base.TabbedPanel;
import adaptme.ui.window.AdaptMeUI;
import adaptme.util.EPFConstants;

public class TabbedPanelArtifact extends JTabbedPane implements TabbedPanel {

    private static final long serialVersionUID = -3565079777093817180L;
    private JPanel panelArtifactDescription;
    private JPanel panelArtifactWorkProducts;
    private JPanel panelArtifactGuidance;
    private JPanel panelArtifactCategories;

    Artifact artifact;
    MethodLibraryHash hash;
    ContentCategoryPackage contentCategoryPackage;
    AdaptMeUI owner;
    JTree jTree;
    MutableTreeNode node;
    private DocumentListener documentListener;
    private ListDataListener listDataListener;

    public TabbedPanelArtifact(Artifact artifact, MethodLibraryHash hash, ContentCategoryPackage contentCategoryPackage,
	    AdaptMeUI owner, MutableTreeNode node, JTree jTree) {
	this.artifact = artifact;
	this.hash = hash;
	this.contentCategoryPackage = contentCategoryPackage;
	this.owner = owner;
	this.node = node;
	this.jTree = jTree;
	initComponents();
    }

    private void initComponents() {
	setTabPlacement(JTabbedPane.BOTTOM);
	panelArtifactDescription = new PanelArtifactDescription(artifact, this, hash, owner);
	panelArtifactWorkProducts = new PanelArtifactStates(artifact, this, hash, owner);
	panelArtifactGuidance = new PanelArtifactGuidance(artifact, this, hash, owner);
	panelArtifactCategories = new PanelArtifactCategories(artifact, hash, contentCategoryPackage, this, owner);

	JScrollPane scrollPaneArtifactDescription = new JScrollPane();
	JScrollPane scrollPaneArtifactWorkProducts = new JScrollPane();
	JScrollPane scrollPaneArtifactGuidance = new JScrollPane();
	JScrollPane scrollPaneArtifactCategories = new JScrollPane();

	scrollPaneArtifactDescription.setViewportView(panelArtifactDescription);
	scrollPaneArtifactWorkProducts.setViewportView(panelArtifactWorkProducts);
	scrollPaneArtifactGuidance.setViewportView(panelArtifactGuidance);
	scrollPaneArtifactCategories.setViewportView(panelArtifactCategories);

	this.addTab("Description", null, scrollPaneArtifactDescription, null);
	this.addTab("Guidance", null, scrollPaneArtifactGuidance, null);
	this.addTab("Categories", null, scrollPaneArtifactCategories, null);
	this.addTab("State", null, scrollPaneArtifactWorkProducts, null);

	this.load();
    }

    public JPanel getPanelArtifactDescription() {
	return panelArtifactDescription;
    }

    public JPanel getPanelArtifactWorkProducts() {
	return panelArtifactWorkProducts;
    }

    public JPanel getPanelArtifactGuidance() {
	return panelArtifactGuidance;
    }

    public JPanel getPanelArtifactCategories() {
	return panelArtifactCategories;
    }

    public void load() {
	((PanelArtifactDescription) panelArtifactDescription).load();
	((PanelArtifactStates) panelArtifactWorkProducts).load();
	((PanelArtifactGuidance) panelArtifactGuidance).load();
	((PanelArtifactCategories) panelArtifactCategories).load();
    }

    public void save() {
	((PanelArtifactDescription) panelArtifactDescription).save();
	((PanelArtifactStates) panelArtifactWorkProducts).save();
	((PanelArtifactGuidance) panelArtifactGuidance).save();
	((PanelArtifactCategories) panelArtifactCategories).save();

	node.setUserObject(new ElementWrapper(artifact, artifact.getName(), EPFConstants.artifactIcon));
	jTree.updateUI();
    }

    public AdaptMeUI getOwner() {
	return owner;
    }

    public void setChangeListeners(DocumentListener documentListener, ListDataListener listDataListener) {
	this.documentListener = documentListener;
	this.listDataListener = listDataListener;
	((PanelArtifactDescription) panelArtifactDescription).setChangeListeners(this.documentListener,
		this.listDataListener);
	((PanelArtifactStates) panelArtifactWorkProducts).setChangeListeners(this.documentListener,
		this.listDataListener);
	((PanelArtifactGuidance) panelArtifactGuidance).setChangeListeners(this.documentListener,
		this.listDataListener);
	((PanelArtifactCategories) panelArtifactCategories).setChangeListeners(this.documentListener,
		this.listDataListener);
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
