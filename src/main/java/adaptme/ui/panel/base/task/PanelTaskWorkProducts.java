package adaptme.ui.panel.base.task;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.xml.bind.JAXBElement;

import org.eclipse.epf.uma.ObjectFactory;
import org.eclipse.epf.uma.Task;
import org.eclipse.epf.uma.WorkProduct;

import adaptme.base.ElementWrapper;
import adaptme.base.MethodLibraryHash;
import adaptme.ui.components.dialog.SelectDialog;
import adaptme.ui.components.dialog.SelectDialogFilter;
import adaptme.ui.panel.base.TabbedPanel;
import adaptme.ui.panel.base.TaskPanelWorkProducts;
import adaptme.ui.window.AdaptMeUI;

public class PanelTaskWorkProducts extends TaskPanelWorkProducts {

    private static final long serialVersionUID = 1803538012369720825L;
    private Task task;
    private MethodLibraryHash hash;
    // private TabbedPanel tabbedPanel;
    // private AdaptMeUI owner;

    private List<WorkProduct> removedWorkProduct = new ArrayList<>();

    public PanelTaskWorkProducts(Task task, TabbedPanel tabbedPanel, MethodLibraryHash hash, AdaptMeUI owner) {
	super(task, tabbedPanel, hash, owner);
	this.task = task;
	this.hash = hash;
	// this.tabbedPanel = tabbedPanel;
	// this.owner = owner;
    }

    @Override
    public void save() {
	List<JAXBElement<String>> jaxbList = task.getMandatoryInputOrOutputOrAdditionallyPerformedBy();

	for (WorkProduct workProduct : removedWorkProduct) {
	    for (int i = 0; i < jaxbList.size(); i++) {
		if (jaxbList.get(i).getValue().equals(workProduct.getId())) {
		    jaxbList.remove(i);
		}
	    }
	}

	List<WorkProduct> listMandatoryInputs = new ArrayList<>();
	List<WorkProduct> listOptinalInputs = new ArrayList<>();
	List<WorkProduct> listOutputs = new ArrayList<>();

	ObjectFactory objectFactory = new ObjectFactory();

	for (JAXBElement<String> jaxbElement : jaxbList) {
	    String localPart = jaxbElement.getName().getLocalPart();
	    if (localPart.equals("MandatoryInput")) {
		listMandatoryInputs.add((WorkProduct) hash.getHashMap().get(jaxbElement.getValue()));
	    } else if (localPart.equals("Output")) {
		listOutputs.add((WorkProduct) hash.getHashMap().get(jaxbElement.getValue()));
	    } else if (localPart.equals("OptionalInput")) {
		listOptinalInputs.add((WorkProduct) hash.getHashMap().get(jaxbElement.getValue()));
	    }
	}
	{
	    List<WorkProduct> newList = getMandatoryInputs();
	    for (WorkProduct newWorkProduct : newList) {
		boolean hasEqual = false;
		for (WorkProduct oldWorkProduct : listMandatoryInputs) {
		    if (newWorkProduct.getId().equals(oldWorkProduct.getId())) {
			hasEqual = true;
			break;
		    }
		}
		if (!hasEqual) {
		    JAXBElement<String> jaxbElement = objectFactory.createTaskMandatoryInput(newWorkProduct.getId());
		    jaxbList.add(jaxbElement);
		}
	    }
	}
	{
	    List<WorkProduct> newList = getOutputs();
	    for (WorkProduct newWorkProduct : newList) {
		boolean hasEqual = false;
		for (WorkProduct oldWorkProduct : listOutputs) {
		    if (newWorkProduct.getId().equals(oldWorkProduct.getId())) {
			hasEqual = true;
			break;
		    }
		}
		if (!hasEqual) {
		    JAXBElement<String> jaxbElement = objectFactory.createTaskOutput(newWorkProduct.getId());
		    jaxbList.add(jaxbElement);
		}
	    }
	}
	{
	    List<WorkProduct> newList = getOptionalInputs();
	    for (WorkProduct newWorkProduct : newList) {
		boolean hasEqual = false;
		for (WorkProduct oldWorkProduct : listOptinalInputs) {
		    if (newWorkProduct.getId().equals(oldWorkProduct.getId())) {
			hasEqual = true;
			break;
		    }
		}
		if (!hasEqual) {
		    JAXBElement<String> jaxbElement = objectFactory.createTaskOptionalInput(newWorkProduct.getId());
		    jaxbList.add(jaxbElement);
		}
	    }
	}
    }

    @Override
    public void load() {
	List<JAXBElement<String>> jaxbList = task.getMandatoryInputOrOutputOrAdditionallyPerformedBy();
	List<WorkProduct> listMandatoryInputs = new ArrayList<>();
	List<WorkProduct> listOptinalInputs = new ArrayList<>();
	List<WorkProduct> listOutputs = new ArrayList<>();
	for (JAXBElement<String> jaxbElement : jaxbList) {
	    String localPart = jaxbElement.getName().getLocalPart();
	    if (localPart.equals("MandatoryInput")) {
		listMandatoryInputs.add((WorkProduct) hash.getHashMap().get(jaxbElement.getValue()));
	    } else if (localPart.equals("Output")) {
		listOutputs.add((WorkProduct) hash.getHashMap().get(jaxbElement.getValue()));
	    } else if (localPart.equals("OptionalInput")) {
		listOptinalInputs.add((WorkProduct) hash.getHashMap().get(jaxbElement.getValue()));
	    }
	}
	setMandatoryInputs(listMandatoryInputs);
	setOptionalInputs(listOptinalInputs);
	setOutputs(listOutputs);
    }

    @Override
    public void openDialogSelectMandatoryInputs(Task task, AdaptMeUI owner, TabbedPanel tabbedPanel) {
	SelectDialog dialog = new SelectDialog(task, owner, tabbedPanel, "Select Dialog: ",
		SelectDialogFilter.WORK_PRODUCTS_MANDATORY_INPUTS, true);
	dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	dialog.setVisible(true);
    }

    @Override
    public void openDialogSelectOutputs(Task task, AdaptMeUI owner, TabbedPanel tabbedPanel) {
	SelectDialog dialog = new SelectDialog(task, owner, tabbedPanel, "Select Dialog: ",
		SelectDialogFilter.WORK_PRODUCTS_OUTPUTS, true);
	dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	dialog.setVisible(true);
    }

    @Override
    public void openDialogSelectOptionalInputs(Task task, AdaptMeUI owner, TabbedPanel tabbedPanel) {
	SelectDialog dialog = new SelectDialog(task, owner, tabbedPanel, "Select Dialog: ",
		SelectDialogFilter.WORK_PRODUCTS_OPTIONAL_INPUTS, true);
	dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	dialog.setVisible(true);
    }

    @Override
    public void removeMandatoryInputs(int index, JList<ElementWrapper> listMandatoryInputs) {
	ElementWrapper elementWrapper = ((DefaultListModel<ElementWrapper>) listMandatoryInputs.getModel())
		.remove(index);
	removedWorkProduct.add((WorkProduct) elementWrapper.getElement());
    }

    @Override
    public void removeOutputs(int index, JList<ElementWrapper> listOutputs) {
	ElementWrapper elementWrapper = ((DefaultListModel<ElementWrapper>) listOutputs.getModel()).remove(index);
	removedWorkProduct.add((WorkProduct) elementWrapper.getElement());
    }

    @Override
    public void removeOptionalInputs(int index, JList<ElementWrapper> listOptionalInputs) {
	ElementWrapper elementWrapper = ((DefaultListModel<ElementWrapper>) listOptionalInputs.getModel())
		.remove(index);
	removedWorkProduct.add((WorkProduct) elementWrapper.getElement());
    }

}
