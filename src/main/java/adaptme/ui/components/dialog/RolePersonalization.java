package adaptme.ui.components.dialog;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.xml.bind.JAXBElement;

import org.eclipse.epf.uma.Artifact;
import org.eclipse.epf.uma.Checklist;
import org.eclipse.epf.uma.Concept;
import org.eclipse.epf.uma.ContentCategory;
import org.eclipse.epf.uma.CustomCategory;
import org.eclipse.epf.uma.Element;
import org.eclipse.epf.uma.Example;
import org.eclipse.epf.uma.Guideline;
import org.eclipse.epf.uma.MethodElement;
import org.eclipse.epf.uma.ObjectFactory;
import org.eclipse.epf.uma.ReusableAsset;
import org.eclipse.epf.uma.Role;
import org.eclipse.epf.uma.RoleSet;
import org.eclipse.epf.uma.SupportingMaterial;

import adaptme.ui.panel.base.TabbedPanel;
import adaptme.ui.panel.base.role.PanelRoleCategories;
import adaptme.ui.panel.base.role.PanelRoleDescription;
import adaptme.ui.panel.base.role.PanelRoleGuidance;
import adaptme.ui.panel.base.role.PanelRoleWorkProducts;
import adaptme.ui.panel.base.role.TabbedPanelRole;

public class RolePersonalization {
    private JDialog dialog;
    private TabbedPanel tabbedPanel;
    private SelectDialogFilter filter;
    private Element element;

    public RolePersonalization(Element element, Role role, JDialog dialog, TabbedPanel tabbedPanel,
	    SelectDialogFilter filter) {
	super();
	this.element = element;
	this.dialog = dialog;
	this.tabbedPanel = tabbedPanel;
	this.filter = filter;

    }

    public void run() {
	if (filter == SelectDialogFilter.SUPPORTING_MATERIALS) {
	    if (!actionSupportingMaterial()) {
		return;
	    }
	} else if (filter == SelectDialogFilter.ROLE) {
	    if (!actionBaseRole()) {
		return;
	    }
	} else if (filter == SelectDialogFilter.WORK_PRODUCTS) {
	    if (!actionWorkProductsRole()) {
		return;
	    }
	} else if (filter == SelectDialogFilter.GUIDANCE) {
	    if (!actionGuidanceRole()) {
		return;
	    }
	} else if (filter == SelectDialogFilter.ROLESET) {
	    if (!actionRoseSet()) {
		return;
	    }
	} else if (filter == SelectDialogFilter.CUSTOM_CATEGORIES) {
	    if (!actionCustomCategories()) {
		return;
	    }
	}
    }

    private boolean actionCustomCategories() {
	if (element instanceof CustomCategory) {
	    CustomCategory customCategory = (CustomCategory) element;
	    List<CustomCategory> list = new ArrayList<>();
	    list.add(customCategory);
	    ((PanelRoleCategories) ((TabbedPanelRole) tabbedPanel).getPanelRoleCategories()).setCustomCategories(list);
	    dialog.dispose();
	    return true;
	}
	JOptionPane.showMessageDialog(dialog, "Invalid element.");
	dialog.dispose();
	return false;
    }

    private boolean actionRoseSet() {
	if (element instanceof RoleSet) {
	    RoleSet roleSet = (RoleSet) element;
	    List<ContentCategory> list = new ArrayList<>();
	    list.add(roleSet);
	    ((PanelRoleCategories) ((TabbedPanelRole) tabbedPanel).getPanelRoleCategories())
		    .setStandardCategories(list);
	    dialog.dispose();
	    return true;
	}
	JOptionPane.showMessageDialog(dialog, "Invalid element.");
	dialog.dispose();
	return false;
    }

    private boolean actionGuidanceRole() {
	if (isGuidance(element)) {
	    List<JAXBElement<String>> jaxbList = new ArrayList<>();
	    ObjectFactory objectFactory = new ObjectFactory();
	    jaxbList.add(objectFactory.createContentElementExample(((MethodElement) element).getId()));
	    ((PanelRoleGuidance) ((TabbedPanelRole) tabbedPanel).getPanelRoleGuidance()).setGuidanceModel(jaxbList);
	    dialog.dispose();
	    return true;
	}
	JOptionPane.showMessageDialog(dialog, "Invalid element.");
	dialog.dispose();
	return false;
    }

    private boolean actionWorkProductsRole() {
	if ((element instanceof Artifact)) {
	    Artifact artifact = (Artifact) element;
	    List<String> list = new ArrayList<>();
	    list.add(artifact.getId());
	    ((PanelRoleWorkProducts) ((TabbedPanelRole) tabbedPanel).getPanelRoleWorkProducts())
		    .setResponsibleFor(list);
	    dialog.dispose();
	    return true;
	}
	JOptionPane.showMessageDialog(dialog, "Invalid element.");
	dialog.dispose();
	return false;
    }

    private boolean actionBaseRole() {
	if ((element instanceof Role)) {
	    Role role = (Role) element;
	    ((PanelRoleDescription) ((TabbedPanelRole) tabbedPanel).getPanelRoleDescription()).setBase(role.getId());
	    return true;
	}
	JOptionPane.showMessageDialog(dialog, "Invalid element.");
	dialog.dispose();
	return false;
    }

    private boolean actionSupportingMaterial() {
	if ((element instanceof SupportingMaterial)) {
	    SupportingMaterial supportingMaterial = (SupportingMaterial) element;
	    ((PanelRoleDescription) ((TabbedPanelRole) tabbedPanel).getPanelRoleDescription())
		    .setCopyright(supportingMaterial.getId());
	    return true;
	}
	JOptionPane.showMessageDialog(dialog, "Invalid element.");
	dialog.dispose();
	return false;
    }

    private boolean isGuidance(Element element) {
	if (element instanceof Example) {
	    return true;
	} else if (element instanceof Checklist) {
	    return true;
	} else if (element instanceof Concept) {
	    return true;
	} else if (element instanceof Guideline) {
	    return true;
	} else if (element instanceof Example) {
	    return true;
	} else if (element instanceof ReusableAsset) {
	    return true;
	} else if (element instanceof SupportingMaterial) {
	    return true;
	} else {
	    return false;
	}
    }
}
