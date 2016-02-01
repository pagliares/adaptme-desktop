package adaptme.ui.panel.base.role;

import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.ListModel;

import org.eclipse.epf.uma.Element;
import org.eclipse.epf.uma.MethodElement;
import org.eclipse.epf.uma.Role;

import adaptme.base.ElementWrapper;
import adaptme.base.MethodLibraryHash;
import adaptme.ui.components.dialog.SelectDialog;
import adaptme.ui.components.dialog.SelectDialogFilter;
import adaptme.ui.panel.base.RolePanelWorkProducts;
import adaptme.ui.panel.base.TabbedPanel;
import adaptme.ui.window.AdaptMeUI;

public class PanelRoleWorkProducts extends RolePanelWorkProducts {

    private static final long serialVersionUID = 5840481088258425196L;
    private Role role;
    // private TabbedPanel tabbedPanel;
    // private MethodLibraryHash hash;
    // private AdaptMeUI owner;

    public PanelRoleWorkProducts(Element element, TabbedPanel tabbedPanel, MethodLibraryHash hash, AdaptMeUI owner) {
	super(element, tabbedPanel, hash, owner);
	this.role = (Role) element;
	// this.tabbedPanel = tabbedPanel;
	// this.hash = hash;
	// this.owner = owner;
    }

    @Override
    public void load() {
	setResponsibleFor(role.getResponsibleFor());
    }

    @Override
    public void save() {
	List<String> listResponsibleFor = role.getResponsibleFor();
	listResponsibleFor.clear();
	ListModel<ElementWrapper> model = getResponsibleFor();
	for (int i = 0; i < model.getSize(); i++) {
	    listResponsibleFor.add(((MethodElement) model.getElementAt(i).getElement()).getId());
	}
    }

    @Override
    public void openDialogSelectWorkProducts(Element element, AdaptMeUI owner, TabbedPanel tabbedPanel) {
	SelectDialog dialog = new SelectDialog(role, owner, tabbedPanel, "Select Dialog: ",
		SelectDialogFilter.WORK_PRODUCTS, true);
	dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	dialog.setVisible(true);
    }

    @Override
    public void removeWorkProducts(int id, JList<ElementWrapper> listCopyright) {
	((DefaultListModel<ElementWrapper>) listCopyright.getModel()).remove(id);
    }

}
