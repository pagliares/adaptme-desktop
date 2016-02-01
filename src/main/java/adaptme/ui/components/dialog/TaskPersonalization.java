package adaptme.ui.components.dialog;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.xml.bind.JAXBElement;

import org.eclipse.epf.uma.ContentCategory;
import org.eclipse.epf.uma.CustomCategory;
import org.eclipse.epf.uma.Discipline;
import org.eclipse.epf.uma.Element;
import org.eclipse.epf.uma.Example;
import org.eclipse.epf.uma.Guidance;
import org.eclipse.epf.uma.ObjectFactory;
import org.eclipse.epf.uma.Role;
import org.eclipse.epf.uma.SupportingMaterial;
import org.eclipse.epf.uma.Task;
import org.eclipse.epf.uma.WorkProduct;

import adaptme.ui.panel.base.PanelCategories;
import adaptme.ui.panel.base.TabbedPanel;
import adaptme.ui.panel.base.task.PanelTaskDescription;
import adaptme.ui.panel.base.task.PanelTaskGuidance;
import adaptme.ui.panel.base.task.PanelTaskRoles;
import adaptme.ui.panel.base.task.PanelTaskWorkProducts;
import adaptme.ui.panel.base.task.TabbedPanelTask;

public class TaskPersonalization {

    private JDialog dialog;
    private TabbedPanel tabbedPanel;
    private SelectDialogFilter filter;
    private Element element;

    /**
     * @wbp.parser.entryPoint
     */
    public TaskPersonalization(Element element, JDialog dialog, TabbedPanel tabbedPanel, SelectDialogFilter filter) {
	super();
	this.dialog = dialog;
	this.tabbedPanel = tabbedPanel;
	this.filter = filter;
	this.element = element;
    }

    /**
     * @wbp.parser.entryPoint
     */
    public void run() {
	if (filter == SelectDialogFilter.ROLE_PRIMARY_PERFORMERS) {
	    if (!actionPrimaryPerformers()) {
		return;
	    }
	} else if (filter == SelectDialogFilter.ROLE_ADDITIONAL_PERFORMERS) {
	    if (!actionAdditionalPerformers()) {
		return;
	    }
	} else if (filter == SelectDialogFilter.WORK_PRODUCTS_MANDATORY_INPUTS) {
	    if (!actionMandatoryInputs()) {
		return;
	    }
	} else if (filter == SelectDialogFilter.WORK_PRODUCTS_OPTIONAL_INPUTS) {
	    if (!actionOptionalInputs()) {
		return;
	    }
	} else if (filter == SelectDialogFilter.WORK_PRODUCTS_OUTPUTS) {
	    if (!actionOutputs()) {
		return;
	    }
	} else if (filter == SelectDialogFilter.GUIDANCE) {
	    if (!actionGuidance()) {
		return;
	    }
	} else if (filter == SelectDialogFilter.SUPPORTING_MATERIALS) {
	    if (!actionSupportingMaterial()) {
		return;
	    }
	} else if (filter == SelectDialogFilter.TASK) {
	    if (!actionTask()) {
		return;
	    }
	} else if (filter == SelectDialogFilter.DISCIPLANE) {
	    if (!actionDisciplane()) {
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
	    ((PanelCategories) ((TabbedPanelTask) tabbedPanel).getPanelTaskCategories()).setCustomCategories(list);
	    return true;
	}
	JOptionPane.showMessageDialog(dialog, "Invalid element");
	dialog.dispose();
	return false;
    }

    private boolean actionDisciplane() {
	if (element instanceof Discipline) {
	    Discipline discipline = (Discipline) element;
	    List<ContentCategory> list = new ArrayList<>();
	    list.add(discipline);
	    ((PanelCategories) ((TabbedPanelTask) tabbedPanel).getPanelTaskCategories()).setStandardCategories(list);
	    return true;
	}
	JOptionPane.showMessageDialog(dialog, "Invalid element");
	dialog.dispose();
	return false;
    }

    private boolean actionTask() {
	if (element instanceof Task) {
	    Task task = (Task) element;
	    ((PanelTaskDescription) ((TabbedPanelTask) tabbedPanel).getPanelTaskDescription()).setBase(task.getId());
	    return true;
	}
	JOptionPane.showMessageDialog(dialog, "Invalid element");
	dialog.dispose();
	return false;
    }

    private boolean actionGuidance() {
	if (element instanceof Guidance) {
	    Guidance guidance = (Guidance) element;
	    List<JAXBElement<String>> listJaxbElements = new ArrayList<>();
	    ObjectFactory objectFactory = new ObjectFactory();
	    if (guidance instanceof Example) {
		listJaxbElements.add(objectFactory.createContentElementExample(guidance.getId()));
	    }
	    ((PanelTaskGuidance) ((TabbedPanelTask) tabbedPanel).getPanelTaskGuidance())
		    .setGuidanceModel(listJaxbElements);
	    return true;
	}
	JOptionPane.showMessageDialog(dialog, "Invalid element");
	dialog.dispose();
	return false;
    }

    private boolean actionOutputs() {
	if (element instanceof WorkProduct) {
	    WorkProduct workProduct = (WorkProduct) element;
	    List<WorkProduct> list = new ArrayList<>();
	    list.add(workProduct);
	    ((PanelTaskWorkProducts) ((TabbedPanelTask) tabbedPanel).getPanelTaskWorkProducts()).setOutputs(list);
	    return true;
	}
	JOptionPane.showMessageDialog(dialog, "Invalid element");
	dialog.dispose();
	return false;
    }

    private boolean actionOptionalInputs() {
	if (element instanceof WorkProduct) {
	    WorkProduct workProduct = (WorkProduct) element;
	    List<WorkProduct> list = new ArrayList<>();
	    list.add(workProduct);
	    ((PanelTaskWorkProducts) ((TabbedPanelTask) tabbedPanel).getPanelTaskWorkProducts())
		    .setOptionalInputs(list);
	    return true;
	}
	JOptionPane.showMessageDialog(dialog, "Invalid element");
	dialog.dispose();
	return false;
    }

    private boolean actionMandatoryInputs() {
	if (element instanceof WorkProduct) {
	    WorkProduct workProduct = (WorkProduct) element;
	    List<WorkProduct> list = new ArrayList<>();
	    list.add(workProduct);
	    ((PanelTaskWorkProducts) ((TabbedPanelTask) tabbedPanel).getPanelTaskWorkProducts())
		    .setMandatoryInputs(list);
	    return true;
	}
	JOptionPane.showMessageDialog(dialog, "Invalid element");
	dialog.dispose();
	return false;
    }

    private boolean actionPrimaryPerformers() {
	if ((element instanceof Role)) {
	    Role role = (Role) element;
	    List<String> list = new ArrayList<>();
	    list.add(role.getId());
	    ((PanelTaskRoles) ((TabbedPanelTask) tabbedPanel).getPanelTaskRoles()).setPrimaryPerformers(list);
	    return true;
	}
	JOptionPane.showMessageDialog(dialog, "Invalid element.");
	dialog.dispose();
	return false;
    }

    private boolean actionAdditionalPerformers() {
	if (element instanceof Role) {
	    Role role = (Role) element;
	    List<JAXBElement<String>> list = new ArrayList<>();
	    ObjectFactory objectFactory = new ObjectFactory();
	    JAXBElement<String> jaxbElement = objectFactory.createTaskAdditionallyPerformedBy(role.getId());
	    list.add(jaxbElement);
	    ((PanelTaskRoles) ((TabbedPanelTask) tabbedPanel).getPanelTaskRoles()).setAdditionalPerformers(list);
	    return true;
	}
	JOptionPane.showMessageDialog(dialog, "Invalid element.");
	dialog.dispose();
	return false;
    }

    private boolean actionSupportingMaterial() {
	if ((element instanceof SupportingMaterial)) {
	    SupportingMaterial supportingMaterial = (SupportingMaterial) element;
	    ((PanelTaskDescription) ((TabbedPanelTask) tabbedPanel).getPanelTaskDescription())
		    .setCopyright(supportingMaterial.getId());
	    return true;
	}
	JOptionPane.showMessageDialog(dialog, "Invalid element.");
	dialog.dispose();
	return false;
    }
}
