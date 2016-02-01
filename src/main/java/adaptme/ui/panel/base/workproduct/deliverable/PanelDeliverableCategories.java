package adaptme.ui.panel.base.workproduct.deliverable;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.xml.bind.JAXBElement;

import org.eclipse.epf.uma.ContentCategory;
import org.eclipse.epf.uma.ContentCategoryPackage;
import org.eclipse.epf.uma.CustomCategory;
import org.eclipse.epf.uma.Deliverable;
import org.eclipse.epf.uma.Domain;
import org.eclipse.epf.uma.ObjectFactory;
import org.eclipse.epf.uma.WorkProduct;
import org.eclipse.epf.uma.WorkProductType;

import adaptme.base.ElementWrapper;
import adaptme.base.MethodLibraryHash;
import adaptme.ui.components.dialog.SelectDialog;
import adaptme.ui.components.dialog.SelectDialogFilter;
import adaptme.ui.panel.base.TabbedPanel;
import adaptme.ui.panel.base.workproduct.WorkProductPanelCategories;
import adaptme.ui.window.AdaptMeUI;

public class PanelDeliverableCategories extends WorkProductPanelCategories {

    private static final long serialVersionUID = 7969605536698709767L;

    private Deliverable deliverable;
    // private TabbedPanel tabbedPanel;
    // private MethodLibraryHash hash;
    // private AdaptMeUI owner;

    List<WorkProductType> removedWorkProductType = new ArrayList<>();
    List<Domain> removedDomain = new ArrayList<>();
    List<CustomCategory> removedCustomCategory = new ArrayList<>();

    public PanelDeliverableCategories(Deliverable deliverable, MethodLibraryHash hash,
	    ContentCategoryPackage contentCategoryPackage, TabbedPanel tabbedPanel, AdaptMeUI owner) {
	super(deliverable, hash, contentCategoryPackage, tabbedPanel, owner);
	this.deliverable = deliverable;
	// this.tabbedPanel = tabbedPanel;
	// this.hash = hash;
	// this.owner = owner;
    }

    @Override
    public void load() {
	{
	    WorkProductType workProductType = null;
	    List<WorkProductType> list = new ArrayList<>();
	    for (ContentCategory contentCategory : getContentCategoryPackage().getContentCategory()) {
		if (contentCategory instanceof WorkProductType) {
		    workProductType = (WorkProductType) contentCategory;
		    for (String id : workProductType.getWorkProduct()) {
			if (id.equals(deliverable.getId())) {
			    list.add(workProductType);
			    break;
			}
		    }
		}
	    }
	    setWorkProductKinks(list);
	}
	{
	    Domain domain = null;
	    List<Domain> list = new ArrayList<>();
	    for (ContentCategory contentCategory : getContentCategoryPackage().getContentCategory()) {
		if (contentCategory instanceof Domain) {
		    domain = (Domain) contentCategory;
		    for (Object o : domain.getWorkProductOrSubdomain()) {
			if (o instanceof String) {
			    String id = (String) o;
			    if (id.equals(deliverable.getId())) {
				list.add(domain);
				break;
			    }
			}
		    }
		}
	    }
	    setDomain(list);
	}
	{
	    CustomCategory customCategory = null;
	    List<CustomCategory> list = new ArrayList<>();
	    for (ContentCategory contentCategory : getContentCategoryPackage().getContentCategory()) {
		if (contentCategory instanceof CustomCategory) {
		    customCategory = (CustomCategory) contentCategory;
		    List<JAXBElement<String>> jaxbList = customCategory.getCategorizedElementOrSubCategory();
		    for (JAXBElement<String> jaxbElement : jaxbList) {
			if (jaxbElement.getValue().equals(deliverable.getId())) {
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
	for (WorkProductType workProductType : removedWorkProductType) {
	    workProductType.getWorkProduct().remove(deliverable.getId());
	}
	for (Domain domain : removedDomain) {
	    domain.getWorkProductOrSubdomain().remove(deliverable.getId());
	}
	for (CustomCategory customCategory : removedCustomCategory) {
	    List<JAXBElement<String>> list = customCategory.getCategorizedElementOrSubCategory();
	    for (int i = 0; i < list.size(); i++) {
		if (list.get(i).getValue().equals(deliverable.getId())) {
		    list.remove(i);
		}
	    }
	}
	{
	    List<WorkProductType> list = getWorkProductType();
	    for (WorkProductType workProductType : list) {
		boolean hasEquals = false;
		for (String id : workProductType.getWorkProduct()) {
		    if (id.equals(deliverable.getId())) {
			hasEquals = true;
			break;
		    }
		}
		if (!hasEquals) {
		    workProductType.getWorkProduct().add(deliverable.getId());
		}
	    }
	}
	{
	    List<Domain> list = getDomain();
	    for (Domain domain : list) {
		boolean hasEquals = false;
		for (Object o : domain.getWorkProductOrSubdomain()) {
		    if (o instanceof String) {
			String id = (String) o;
			if (id.equals(deliverable.getId())) {
			    hasEquals = true;
			    break;
			}
		    }
		}
		if (!hasEquals) {
		    domain.getWorkProductOrSubdomain().add(deliverable.getId());
		}
	    }
	}
	{
	    List<CustomCategory> list = getCustomCategories();
	    for (CustomCategory customCategory : list) {
		boolean hasEquals = false;
		for (JAXBElement<String> jaxbElement : customCategory.getCategorizedElementOrSubCategory()) {
		    if (jaxbElement.getValue().equals(deliverable.getId())) {
			hasEquals = true;
			break;
		    }
		}
		if (!hasEquals) {
		    ObjectFactory objectFactory = new ObjectFactory();
		    customCategory.getCategorizedElementOrSubCategory()
			    .add(objectFactory.createCustomCategoryCategorizedElement(deliverable.getId()));
		}
	    }
	}
    }

    @Override
    public void openDialogWorkProductType(WorkProduct workProduct, AdaptMeUI owner, TabbedPanel tabbedPanel) {
	SelectDialog dialog = new SelectDialog(deliverable, owner, tabbedPanel, "Select a Work product kinds",
		SelectDialogFilter.WORK_PRODUCT_KINDS, true);
	dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	dialog.setVisible(true);
    }

    @Override
    public void removeWorkProductType(int index, JList<ElementWrapper> listWorkProductType) {
	ElementWrapper elementWrapper = ((DefaultListModel<ElementWrapper>) listWorkProductType.getModel())
		.remove(index);
	removedWorkProductType.add((WorkProductType) elementWrapper.getElement());
    }

    @Override
    public void openDialogCustomCategories(WorkProduct workProduct, AdaptMeUI owner, TabbedPanel tabbedPanel) {
	SelectDialog dialog = new SelectDialog(deliverable, owner, tabbedPanel, "Select a Custom Categoreis",
		SelectDialogFilter.CUSTOM_CATEGORIES, true);
	dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	dialog.setVisible(true);
    }

    @Override
    public void removeCustomCategories(int index, JList<ElementWrapper> listCustomCategories) {
	ElementWrapper elementWrapper = ((DefaultListModel<ElementWrapper>) listCustomCategories.getModel())
		.remove(index);
	removedCustomCategory.add((CustomCategory) elementWrapper.getElement());
    }

    @Override
    public void openDialogDomain(WorkProduct workProduct, AdaptMeUI owner, TabbedPanel tabbedPanel) {
	SelectDialog dialog = new SelectDialog(deliverable, owner, tabbedPanel, "Select a Work product kinds",
		SelectDialogFilter.DOMAIN, true);
	dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	dialog.setVisible(true);
    }

    @Override
    public void removeDomain(int index, JList<ElementWrapper> listDomain) {
	ElementWrapper elementWrapper = ((DefaultListModel<ElementWrapper>) listDomain.getModel()).remove(index);
	removedDomain.add((Domain) elementWrapper.getElement());
    }

}
