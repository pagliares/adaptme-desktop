package adaptme.ui.panel.base.workproduct.outcome;

import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.xml.bind.JAXBElement;

import org.eclipse.epf.uma.MethodElement;
import org.eclipse.epf.uma.ObjectFactory;
import org.eclipse.epf.uma.Outcome;
import org.eclipse.epf.uma.WorkProduct;

import adaptme.base.ElementWrapper;
import adaptme.base.MethodLibraryHash;
import adaptme.ui.components.dialog.SelectDialog;
import adaptme.ui.components.dialog.SelectDialogFilter;
import adaptme.ui.panel.base.TabbedPanel;
import adaptme.ui.panel.base.workproduct.WorkProductPanelGuidance;
import adaptme.ui.window.AdaptMeUI;

public class PanelOutcomeGuidance extends WorkProductPanelGuidance {

    private static final long serialVersionUID = -4550380633001490331L;
    private Outcome outcome;

    public PanelOutcomeGuidance(Outcome outcome, TabbedPanel tabbedPanel, MethodLibraryHash hash, AdaptMeUI owner) {
	super(outcome, tabbedPanel, hash, owner);
	this.outcome = outcome;
    }

    @Override
    public void load() {
	setGuidanceModel(outcome.getChecklistOrConceptOrExample());
    }

    @Override
    public void save() {
	List<JAXBElement<String>> listJaxbElements = outcome.getChecklistOrConceptOrExample();
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
	SelectDialog dialog = new SelectDialog(outcome, owner, tabbedPanel, "Select Dialog: ",
		SelectDialogFilter.GUIDANCE, true);
	dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	dialog.setVisible(true);
    }

    @Override
    public void removeGuidance(int index, JList<ElementWrapper> listGuidance) {
	((DefaultListModel<ElementWrapper>) listGuidance.getModel()).remove(index);
    }

}
