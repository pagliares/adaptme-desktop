package adaptme.ui.panel.base.role;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.xml.bind.JAXBElement;

import org.eclipse.epf.uma.ContentCategory;
import org.eclipse.epf.uma.ContentCategoryPackage;
import org.eclipse.epf.uma.CustomCategory;
import org.eclipse.epf.uma.Element;
import org.eclipse.epf.uma.ObjectFactory;
import org.eclipse.epf.uma.Role;
import org.eclipse.epf.uma.RoleSet;

import adaptme.base.ElementWrapper;
import adaptme.base.MethodLibraryHash;
import adaptme.ui.components.dialog.SelectDialog;
import adaptme.ui.components.dialog.SelectDialogFilter;
import adaptme.ui.panel.base.PanelCategories;
import adaptme.ui.panel.base.TabbedPanel;
import adaptme.ui.window.AdaptMeUI;
import adaptme.util.EPFConstants;

public class PanelRoleCategories extends PanelCategories {

    private static final long serialVersionUID = 1357530370400544778L;
    private Role role;
    // private MethodLibraryHash hash;
    private ContentCategoryPackage contentCategoryPackage;
    // private TabbedPanel tabbedPanel;
    // private AdaptMeUI owner;

    private List<RoleSet> removedListRoleSet = new ArrayList<>();
    private List<CustomCategory> removedCustomCategory = new ArrayList<>();

    public PanelRoleCategories(Element element, MethodLibraryHash hash, ContentCategoryPackage contentCategoryPackage,
	    TabbedPanel tabbedPanel, AdaptMeUI owner) {
	super(element, hash, contentCategoryPackage, tabbedPanel, owner);
	this.role = (Role) element;
	// this.hash = hash;
	this.contentCategoryPackage = contentCategoryPackage;
	// this.tabbedPanel = tabbedPanel;
	// this.owner = owner;

	setTitleLabel("Role sets:");
	setCategoryLabel("Manage the categories to which this role belongs.");
    }

    @Override
    public void load() {
	{
	    RoleSet roleSet = null;
	    List<ContentCategory> list = new ArrayList<>();
	    for (ContentCategory contentCategory : contentCategoryPackage.getContentCategory()) {
		if (contentCategory instanceof RoleSet) {
		    roleSet = (RoleSet) contentCategory;
		    for (String id : roleSet.getRole()) {
			if (id.equals(role.getId())) {
			    list.add(roleSet);
			    break;
			}
		    }
		}
	    }
	    setStandardCategories(list);
	}
	{
	    CustomCategory customCategory = null;
	    List<CustomCategory> list = new ArrayList<>();
	    for (ContentCategory contentCategory : contentCategoryPackage.getContentCategory()) {
		if (contentCategory instanceof CustomCategory) {
		    customCategory = (CustomCategory) contentCategory;
		    List<JAXBElement<String>> jaxbList = customCategory.getCategorizedElementOrSubCategory();
		    for (JAXBElement<String> jaxbElement : jaxbList) {
			if (jaxbElement.getValue().equals(role.getId())) {
			    list.add(customCategory);
			}
		    }
		}
	    }
	    setCustomCategories(list);
	}
    }

    @Override
    public void save() {
	{
	    List<ContentCategory> list = getStandardCategorys();
	    for (ContentCategory contentCategory : list) {
		removedListRoleSet.remove(contentCategory);
	    }
	    for (ContentCategory contentCategory : list) {
		RoleSet roleSet = (RoleSet) contentCategory;
		boolean hasEquals = false;
		for (String id : roleSet.getRole()) {
		    if (id.equals(role.getId())) {
			hasEquals = true;
			break;
		    }
		}
		if (!hasEquals) {
		    roleSet.getRole().add(role.getId());
		}
	    }
	    for (RoleSet roleSet : removedListRoleSet) {
		roleSet.getRole().remove(role.getId());
	    }
	}
	{
	    List<CustomCategory> list = getCustomCategories();
	    for (CustomCategory customCategory : list) {
		removedCustomCategory.remove(customCategory);
	    }
	    for (CustomCategory customCategory : list) {
		boolean hasEquals = false;
		for (JAXBElement<String> jaxbElement : customCategory.getCategorizedElementOrSubCategory()) {
		    if (jaxbElement.getValue().equals(role.getId())) {
			hasEquals = true;
			break;
		    }
		}
		if (!hasEquals) {
		    ObjectFactory objectFactory = new ObjectFactory();
		    customCategory.getCategorizedElementOrSubCategory()
			    .add(objectFactory.createCustomCategoryCategorizedElement(role.getId()));
		}
	    }
	    List<JAXBElement<String>> toRemove = new ArrayList<>();
	    for (CustomCategory customCategory : removedCustomCategory) {
		for (JAXBElement<String> jaxbElement : customCategory.getCategorizedElementOrSubCategory()) {
		    if (jaxbElement.getValue().equals(role.getId())) {
			toRemove.add(jaxbElement);
		    }
		}
		for (JAXBElement<String> jaxbElement : toRemove) {
		    customCategory.getCategorizedElementOrSubCategory().remove(jaxbElement);
		}
	    }
	}
    }

    @Override
    public void removeCategory(int id, JList<ElementWrapper> listCategory) {
	ElementWrapper elementWrapper = ((DefaultListModel<ElementWrapper>) listCategory.getModel()).remove(id);
	removedListRoleSet.add((RoleSet) elementWrapper.getElement());
    }

    @Override
    public void removeCustomCategory(int id, JList<ElementWrapper> listCustomCategories) {
	ElementWrapper elementWrapper = ((DefaultListModel<ElementWrapper>) listCustomCategories.getModel()).remove(id);
	removedCustomCategory.add((CustomCategory) elementWrapper.getElement());
    }

    @Override
    public void openDialogCategory(Element element, AdaptMeUI owner, TabbedPanel tabbedPanel) {
	SelectDialog dialog = new SelectDialog(role, owner, tabbedPanel, "Select a Rose Set",
		SelectDialogFilter.ROLESET, true);
	dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	dialog.setVisible(true);
    }

    @Override
    public void openDialogCustomCategory(Element element, AdaptMeUI owner, TabbedPanel tabbedPanel) {
	SelectDialog dialog = new SelectDialog(role, owner, tabbedPanel, "Select a Custom Categoreis",
		SelectDialogFilter.CUSTOM_CATEGORIES, true);
	dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	dialog.setVisible(true);
    }

    @Override
    public void setStandardCategories(List<ContentCategory> list) {
	DefaultListModel<ElementWrapper> model = (DefaultListModel<ElementWrapper>) getListCategory().getModel();
	for (ContentCategory contentCategory : list) {
	    model.addElement(new ElementWrapper(contentCategory, contentCategory.getName(), EPFConstants.roleSetIcon));
	    ;
	}
	getListCategory().setModel(model);
    }

}
