package adaptme.ui.components.dialog;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.xml.bind.JAXBElement;

import org.eclipse.epf.uma.Artifact;
import org.eclipse.epf.uma.CustomCategory;
import org.eclipse.epf.uma.Domain;
import org.eclipse.epf.uma.Element;
import org.eclipse.epf.uma.Example;
import org.eclipse.epf.uma.ObjectFactory;
import org.eclipse.epf.uma.SupportingMaterial;
import org.eclipse.epf.uma.WorkProduct;
import org.eclipse.epf.uma.WorkProductType;

import adaptme.ui.panel.base.TabbedPanel;
import adaptme.ui.panel.base.workproduct.artifact.PanelArtifactCategories;
import adaptme.ui.panel.base.workproduct.artifact.PanelArtifactDescription;
import adaptme.ui.panel.base.workproduct.artifact.PanelArtifactGuidance;
import adaptme.ui.panel.base.workproduct.artifact.TabbedPanelArtifact;

public class ArtifactPersonalization {
    private JDialog dialog;
    private TabbedPanel tabbedPanel;
    private SelectDialogFilter filter;
    private Element element;

    public ArtifactPersonalization(Element element, JDialog dialog, TabbedPanel tabbedPanel,
	    SelectDialogFilter filter) {
	this.element = element;
	this.dialog = dialog;
	this.tabbedPanel = tabbedPanel;
	this.filter = filter;
    }

    public void run() {
	if (filter == SelectDialogFilter.GUIDANCE) {
	    if (!actionGuidanceRole()) {
		return;
	    }
	} else if (filter == SelectDialogFilter.SUPPORTING_MATERIALS) {
	    if (!actionSupportingMaterial()) {
		return;
	    }
	} else if (filter == SelectDialogFilter.ARTIFACT) {
	    if (!actionAtirfact()) {
		return;
	    }
	} else if (filter == SelectDialogFilter.CUSTOM_CATEGORIES) {
	    if (!actionCustomCategories()) {
		return;
	    }
	} else if (filter == SelectDialogFilter.DOMAIN) {
	    if (!actionDomain()) {
		return;
	    }
	} else if (filter == SelectDialogFilter.WORK_PRODUCT_KINDS) {
	    if (!actionWorkProductKinds()) {
		return;
	    }
	} else if (filter == SelectDialogFilter.WORK_PRODUCTS) {
	    if (!actionWorkProducts()) {
		return;
	    }
	}
    }

    private boolean actionWorkProducts() {
	if ((element instanceof WorkProduct && ((WorkProduct) element).isIsAbstract())) {
	    WorkProduct workProduct = (WorkProduct) element;
	    List<String> list = new ArrayList<>();
	    list.add(workProduct.getId());
	    ((PanelArtifactDescription) ((TabbedPanelArtifact) tabbedPanel).getPanelArtifactDescription())
		    .setWorkProductSlot(list);
	    return true;
	}
	JOptionPane.showMessageDialog(dialog, "Invalid element.");
	dialog.dispose();
	return false;
    }

    private boolean actionDomain() {
	if (element instanceof Domain) {
	    Domain domain = (Domain) element;
	    List<Domain> list = new ArrayList<>();
	    list.add(domain);
	    ((PanelArtifactCategories) ((TabbedPanelArtifact) tabbedPanel).getPanelArtifactCategories())
		    .setDomain(list);
	    dialog.dispose();
	    return true;
	}
	JOptionPane.showMessageDialog(dialog, "Invalid element.");
	dialog.dispose();
	return false;
    }

    private boolean actionWorkProductKinds() {
	if (element instanceof WorkProductType) {
	    WorkProductType workProductType = (WorkProductType) element;
	    List<WorkProductType> list = new ArrayList<>();
	    list.add(workProductType);
	    ((PanelArtifactCategories) ((TabbedPanelArtifact) tabbedPanel).getPanelArtifactCategories())
		    .setWorkProductKinks(list);
	    dialog.dispose();
	    return true;
	}
	JOptionPane.showMessageDialog(dialog, "Invalid element.");
	dialog.dispose();
	return false;
    }

    private boolean actionCustomCategories() {
	if (element instanceof CustomCategory) {
	    CustomCategory customCategory = (CustomCategory) element;
	    List<CustomCategory> list = new ArrayList<>();
	    list.add(customCategory);
	    ((PanelArtifactCategories) ((TabbedPanelArtifact) tabbedPanel).getPanelArtifactCategories())
		    .setCustomCategories(list);
	    dialog.dispose();
	    return true;
	}
	JOptionPane.showMessageDialog(dialog, "Invalid element.");
	dialog.dispose();
	return false;
    }

    private boolean actionAtirfact() {
	if ((element instanceof Artifact)) {
	    Artifact artifact = (Artifact) element;
	    ((PanelArtifactDescription) ((TabbedPanelArtifact) tabbedPanel).getPanelArtifactDescription())
		    .setBase(artifact.getId());
	    return true;
	}
	JOptionPane.showMessageDialog(dialog, "Invalid element.");
	dialog.dispose();
	return false;
    }

    private boolean actionSupportingMaterial() {
	if ((element instanceof SupportingMaterial)) {
	    SupportingMaterial supportingMaterial = (SupportingMaterial) element;
	    ((PanelArtifactDescription) ((TabbedPanelArtifact) tabbedPanel).getPanelArtifactDescription())
		    .setCopyright(supportingMaterial.getId());
	    return true;
	}
	JOptionPane.showMessageDialog(dialog, "Invalid element.");
	dialog.dispose();
	return false;
    }

    private boolean actionGuidanceRole() {
	if ((element instanceof Example)) {
	    Example example = (Example) element;
	    List<JAXBElement<String>> jaxbList = new ArrayList<>();
	    ObjectFactory objectFactory = new ObjectFactory();
	    jaxbList.add(objectFactory.createContentElementExample(example.getId()));
	    ((PanelArtifactGuidance) ((TabbedPanelArtifact) tabbedPanel).getPanelArtifactGuidance())
		    .setGuidanceModel(jaxbList);
	    dialog.dispose();
	    return true;
	}
	JOptionPane.showMessageDialog(dialog, "Invalid element.");
	dialog.dispose();
	return false;
    }

}
