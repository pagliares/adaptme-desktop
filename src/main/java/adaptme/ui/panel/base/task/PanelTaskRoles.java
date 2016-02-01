package adaptme.ui.panel.base.task;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.xml.bind.JAXBElement;

import org.eclipse.epf.uma.Element;
import org.eclipse.epf.uma.Role;
import org.eclipse.epf.uma.Task;

import adaptme.base.ElementWrapper;
import adaptme.base.MethodLibraryHash;
import adaptme.ui.components.dialog.SelectDialog;
import adaptme.ui.components.dialog.SelectDialogFilter;
import adaptme.ui.panel.base.PanelRoles;
import adaptme.ui.panel.base.TabbedPanel;
import adaptme.ui.window.AdaptMeUI;

public class PanelTaskRoles extends PanelRoles {

    private static final long serialVersionUID = -126742579708998498L;
    private Task task;
    // private MethodLibraryHash hash;
    // private TabbedPanel tabbedPanel;
    // private AdaptMeUI owner;

    private List<Role> removedAdditionallyPerformers = new ArrayList<>();

    public PanelTaskRoles(Task task, TabbedPanel tabbedPanel, MethodLibraryHash hash, AdaptMeUI owner) {
	super(task, tabbedPanel, hash, owner);
	this.task = task;
	// this.tabbedPanel = tabbedPanel;
	// this.hash = hash;
	// this.owner = owner;
    }

    @Override
    public void save() {
	{
	    List<String> list = task.getPerformedBy();
	    List<String> newList = getPrimaryPerformers();
	    list.clear();
	    for (String string : newList) {
		list.add(string);
	    }
	}
	{
	    List<JAXBElement<String>> list = task.getMandatoryInputOrOutputOrAdditionallyPerformedBy();
	    List<JAXBElement<String>> newList = getAdditionalPerformers();
	    for (Role role : removedAdditionallyPerformers) {
		for (JAXBElement<String> jaxbElement : list) {
		    if (jaxbElement.getValue().equals(role.getId())) {
			list.remove(jaxbElement);
		    }
		}
	    }
	    for (JAXBElement<String> newJAXBlement : newList) {
		boolean hasEqual = false;
		for (JAXBElement<String> jaxbElement : list) {
		    if ((newJAXBlement.getValue().equals(jaxbElement.getValue()))
			    && (newJAXBlement.getName().getPrefix().equals(jaxbElement.getName().getPrefix()))) {
			hasEqual = true;
			break;
		    }
		}
		if (!hasEqual) {
		    list.add(newJAXBlement);
		}
	    }
	}
    }

    @Override
    public void load() {
	setPrimaryPerformers(task.getPerformedBy());
	setAdditionalPerformers(task.getMandatoryInputOrOutputOrAdditionallyPerformedBy());
    }

    @Override
    public void openDialogPrimaryPerformes(Element element, AdaptMeUI owner, TabbedPanel tabbedPanel) {
	SelectDialog dialog = new SelectDialog(task, owner, tabbedPanel, "Select Dialog: ",
		SelectDialogFilter.ROLE_PRIMARY_PERFORMERS, true);
	dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	dialog.setVisible(true);
    }

    @Override
    public void removePrimaryPerformers(int index, JList<ElementWrapper> listPrimaryPerformes) {
	((DefaultListModel<ElementWrapper>) listPrimaryPerformes.getModel()).remove(index);
    }

    @Override
    public void openDialogAdditinalPerformers(Element element, AdaptMeUI owner, TabbedPanel tabbedPanel) {
	SelectDialog dialog = new SelectDialog(task, owner, tabbedPanel, "Select Dialog: ",
		SelectDialogFilter.ROLE_ADDITIONAL_PERFORMERS, true);
	dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	dialog.setVisible(true);
    }

    @Override
    public void removeAdditionalPerformes(int index, JList<ElementWrapper> listAdditionalPerformers) {
	ElementWrapper elementWrapper = ((DefaultListModel<ElementWrapper>) listAdditionalPerformers.getModel())
		.remove(index);
	removedAdditionallyPerformers.add((Role) elementWrapper.getElement());
    }

}
