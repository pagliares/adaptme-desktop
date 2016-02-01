package adaptme.ui.panel.base.workproduct.outcome;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.xml.bind.JAXBElement;

import org.eclipse.epf.uma.ContentCategory;
import org.eclipse.epf.uma.ContentCategoryPackage;
import org.eclipse.epf.uma.CustomCategory;
import org.eclipse.epf.uma.Domain;
import org.eclipse.epf.uma.ObjectFactory;
import org.eclipse.epf.uma.Outcome;
import org.eclipse.epf.uma.WorkProduct;
import org.eclipse.epf.uma.WorkProductType;

import adaptme.base.ElementWrapper;
import adaptme.base.MethodLibraryHash;
import adaptme.ui.components.dialog.SelectDialog;
import adaptme.ui.components.dialog.SelectDialogFilter;
import adaptme.ui.panel.base.TabbedPanel;
import adaptme.ui.panel.base.workproduct.WorkProductPanelCategories;
import adaptme.ui.window.AdaptMeUI;

public class PanelOutcomeCategories extends WorkProductPanelCategories {

    private static final long serialVersionUID = -2136914668705321462L;
    private Outcome outcome;
    private ContentCategoryPackage contentCategoryPackage;

    List<WorkProductType> removedWorkProductType = new ArrayList<>();
    List<Domain> removedDomain = new ArrayList<>();
    List<CustomCategory> removedCustomCategory = new ArrayList<>();

    public PanelOutcomeCategories(Outcome outcome, MethodLibraryHash hash,
	    ContentCategoryPackage contentCategoryPackage, TabbedPanel tabbedPanel, AdaptMeUI owner) {
	super(outcome, hash, contentCategoryPackage, tabbedPanel, owner);
	this.outcome = outcome;
	this.contentCategoryPackage = contentCategoryPackage;
    }

    @Override
    public void load() {
	{
	    List<WorkProductType> list = new ArrayList<>();
	    for (ContentCategory contentCategory : contentCategoryPackage.getContentCategory()) {
		if (contentCategory instanceof WorkProductType) {
		    WorkProductType workProductType = (WorkProductType) contentCategory;
		    for (String id : workProductType.getWorkProduct()) {
			if (id.equals(outcome.getId())) {
			    list.add(workProductType);
			    break;
			}
		    }
		}
	    }
	    setWorkProductKinks(list);
	}
	{
	    List<Domain> list = new ArrayList<>();
	    for (ContentCategory contentCategory : contentCategoryPackage.getContentCategory()) {
		if (contentCategory instanceof Domain) {
		    Domain domain = (Domain) contentCategory;
		    for (Object o : domain.getWorkProductOrSubdomain()) {
			if (o instanceof String) {
			    String id = (String) o;
			    if (id.equals(outcome.getId())) {
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
	    List<CustomCategory> list = new ArrayList<>();
	    for (ContentCategory contentCategory : contentCategoryPackage.getContentCategory()) {
		if (contentCategory instanceof CustomCategory) {
		    CustomCategory customCategory = (CustomCategory) contentCategory;
		    List<JAXBElement<String>> jaxbList = customCategory.getCategorizedElementOrSubCategory();
		    for (JAXBElement<String> jaxbElement : jaxbList) {
			if (jaxbElement.getValue().equals(outcome.getId())) {
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
	    workProductType.getWorkProduct().remove(outcome.getId());
	}
	for (Domain domain : removedDomain) {
	    domain.getWorkProductOrSubdomain().remove(outcome.getId());
	}
	for (CustomCategory customCategory : removedCustomCategory) {
	    List<JAXBElement<String>> list = customCategory.getCategorizedElementOrSubCategory();
	    for (int i = 0; i < list.size(); i++) {
		if (list.get(i).getValue().equals(outcome.getId())) {
		    list.remove(i);
		}
	    }
	}
	{
	    List<WorkProductType> list = getWorkProductType();
	    for (WorkProductType workProductType : list) {
		boolean hasEquals = false;
		for (String id : workProductType.getWorkProduct()) {
		    if (id.equals(outcome.getId())) {
			hasEquals = true;
			break;
		    }
		}
		if (!hasEquals) {
		    workProductType.getWorkProduct().add(outcome.getId());
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
			if (id.equals(outcome.getId())) {
			    hasEquals = true;
			    break;
			}
		    }
		}
		if (!hasEquals) {
		    domain.getWorkProductOrSubdomain().add(outcome.getId());
		}
	    }
	}
	{
	    List<CustomCategory> list = getCustomCategories();
	    for (CustomCategory customCategory : list) {
		boolean hasEquals = false;
		for (JAXBElement<String> jaxbElement : customCategory.getCategorizedElementOrSubCategory()) {
		    if (jaxbElement.getValue().equals(outcome.getId())) {
			hasEquals = true;
			break;
		    }
		}
		if (!hasEquals) {
		    ObjectFactory objectFactory = new ObjectFactory();
		    customCategory.getCategorizedElementOrSubCategory()
			    .add(objectFactory.createCustomCategoryCategorizedElement(outcome.getId()));
		}
	    }
	}
    }

    @Override
    public void openDialogWorkProductType(WorkProduct workProduct, AdaptMeUI owner, TabbedPanel tabbedPanel) {
	SelectDialog dialog = new SelectDialog(outcome, owner, tabbedPanel, "Select a Work product kinds",
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
	SelectDialog dialog = new SelectDialog(outcome, owner, tabbedPanel, "Select a Custom Categoreis",
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
	SelectDialog dialog = new SelectDialog(outcome, owner, tabbedPanel, "Select a Work product kinds",
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
