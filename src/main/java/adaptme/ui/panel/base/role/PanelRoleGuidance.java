package adaptme.ui.panel.base.role;

import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.xml.bind.JAXBElement;

import org.eclipse.epf.uma.Element;
import org.eclipse.epf.uma.MethodElement;
import org.eclipse.epf.uma.ObjectFactory;
import org.eclipse.epf.uma.Role;

import adaptme.base.ElementWrapper;
import adaptme.base.MethodLibraryHash;
import adaptme.ui.components.dialog.SelectDialog;
import adaptme.ui.components.dialog.SelectDialogFilter;
import adaptme.ui.panel.base.PanelGuidance;
import adaptme.ui.panel.base.TabbedPanel;
import adaptme.ui.window.AdaptMeUI;

public class PanelRoleGuidance extends PanelGuidance {

    private static final long serialVersionUID = 466328775417202027L;
    private Role role;
    // private TabbedPanel tabbedPanel;
    // private MethodLibraryHash hash;
    // private AdaptMeUI owner;

    public PanelRoleGuidance(Element element, TabbedPanel tabbedPanel, MethodLibraryHash hash, AdaptMeUI owner) {
	super(element, tabbedPanel, hash, owner);
	this.role = (Role) element;
	// this.tabbedPanel = tabbedPanel;
	// this.hash = hash;
	// this.owner = owner;

    }

    @Override
    public void load() {
	setGuidanceModel(role.getChecklistOrConceptOrExample());
    }

    @Override
    public void save() {
	List<JAXBElement<String>> listJaxbElements = role.getChecklistOrConceptOrExample();
	ListModel<ElementWrapper> list = getListGuidance().getModel();
	listJaxbElements.clear();
	for (int i = 0; i < list.getSize(); i++) {
	    MethodElement methodElement = (MethodElement) list.getElementAt(i).getElement();
	    ObjectFactory objectFactory = new ObjectFactory();
	    listJaxbElements.add(objectFactory.createContentElementExample(methodElement.getId()));
	}
    }

    @Override
    public void openDialogSelectGuidance(Element element, AdaptMeUI owner, TabbedPanel tabbedPanel) {
	SelectDialog dialog = new SelectDialog(role, owner, tabbedPanel, "Select Dialog: ", SelectDialogFilter.GUIDANCE,
		true);
	dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	dialog.setVisible(true);
    }

    @Override
    public void removeGuidance(int id, JList<ElementWrapper> listGuidance) {
	((DefaultListModel<ElementWrapper>) listGuidance.getModel()).remove(id);
    }
}
