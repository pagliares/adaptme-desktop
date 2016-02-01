package adaptme.ui.panel.base.workproduct.deliverable;

import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.xml.bind.JAXBElement;

import org.eclipse.epf.uma.Deliverable;
import org.eclipse.epf.uma.MethodElement;
import org.eclipse.epf.uma.ObjectFactory;
import org.eclipse.epf.uma.WorkProduct;

import adaptme.base.ElementWrapper;
import adaptme.base.MethodLibraryHash;
import adaptme.ui.components.dialog.SelectDialog;
import adaptme.ui.components.dialog.SelectDialogFilter;
import adaptme.ui.panel.base.TabbedPanel;
import adaptme.ui.panel.base.workproduct.WorkProductPanelGuidance;
import adaptme.ui.window.AdaptMeUI;

public class PanelDeliverableGuidance extends WorkProductPanelGuidance {

    private static final long serialVersionUID = 7387605763894468487L;
    private Deliverable deliverable;
    // private TabbedPanel tabbedPanel;
    // private MethodLibraryHash hash;
    // private AdaptMeUI owner;

    public PanelDeliverableGuidance(Deliverable deliverable, TabbedPanel tabbedPanel, MethodLibraryHash hash,
	    AdaptMeUI owner) {
	super(deliverable, tabbedPanel, hash, owner);
	this.deliverable = deliverable;
	// this.tabbedPanel = tabbedPanel;
	// this.hash = hash;
	// this.owner = owner;
    }

    @Override
    public void load() {
	setGuidanceModel(deliverable.getChecklistOrConceptOrExample());
    }

    @Override
    public void save() {
	List<JAXBElement<String>> listJaxbElements = deliverable.getChecklistOrConceptOrExample();
	ListModel<ElementWrapper> list = getListGuidance().getModel();
	listJaxbElements.clear();
	int max = listJaxbElements.size();
	for (int i = 0; i < list.getSize(); i++) {
	    boolean hasEqual = false;
	    MethodElement methodElement = (MethodElement) list.getElementAt(i).getElement();
	    for (int j = 0; j < max; j++) {
		if (methodElement.getId().equals(listJaxbElements.get(j).getValue())) {
		    hasEqual = true;
		}
	    }
	    if (!hasEqual) {
		ObjectFactory objectFactory = new ObjectFactory();
		listJaxbElements.add(objectFactory.createContentElementExample(methodElement.getId()));
	    }
	}
    }

    @Override
    public void openDialogSelectGuidance(WorkProduct workProduct, AdaptMeUI owner, TabbedPanel tabbedPanel) {
	SelectDialog dialog = new SelectDialog(deliverable, owner, tabbedPanel, "Select Dialog: ",
		SelectDialogFilter.GUIDANCE, true);
	dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	dialog.setVisible(true);
    }

    @Override
    public void removeGuidance(int index, JList<ElementWrapper> listGuidance) {
	((DefaultListModel<ElementWrapper>) listGuidance.getModel()).remove(index);
    }

}
