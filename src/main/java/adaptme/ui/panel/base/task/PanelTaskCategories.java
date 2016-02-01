package adaptme.ui.panel.base.task;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.xml.bind.JAXBElement;

import org.eclipse.epf.uma.ContentCategory;
import org.eclipse.epf.uma.ContentCategoryPackage;
import org.eclipse.epf.uma.CustomCategory;
import org.eclipse.epf.uma.Discipline;
import org.eclipse.epf.uma.Element;
import org.eclipse.epf.uma.ObjectFactory;
import org.eclipse.epf.uma.Task;

import adaptme.base.ElementWrapper;
import adaptme.base.MethodLibraryHash;
import adaptme.ui.components.dialog.SelectDialog;
import adaptme.ui.components.dialog.SelectDialogFilter;
import adaptme.ui.panel.base.PanelCategories;
import adaptme.ui.panel.base.TabbedPanel;
import adaptme.ui.window.AdaptMeUI;
import adaptme.util.EPFConstants;

public class PanelTaskCategories extends PanelCategories {

    private static final long serialVersionUID = -5641537514852247505L;
    private Task task;
    private ContentCategoryPackage contentCategoryPackage;
    // private MethodLibraryHash hash;
    // private TabbedPanel tabbedPanel;
    // private AdaptMeUI owner;

    private Set<Discipline> removedListDiscipline = new HashSet<>();
    private Set<CustomCategory> removedCustomCategory = new HashSet<>();

    public PanelTaskCategories(Task task, TabbedPanel tabbedPanel, ContentCategoryPackage contentCategoryPackage,
	    MethodLibraryHash hash, AdaptMeUI owner) {
	super(task, hash, contentCategoryPackage, tabbedPanel, owner);
	this.task = task;
	this.contentCategoryPackage = contentCategoryPackage;
	// this.hash = hash;
	// this.tabbedPanel = tabbedPanel;
	// this.owner = owner;
    }

    @Override
    public void load() {
	{
	    Discipline discipline = null;
	    List<ContentCategory> list = new ArrayList<>();
	    for (ContentCategory contentCategory : contentCategoryPackage.getContentCategory()) {
		if (contentCategory instanceof Discipline) {
		    discipline = (Discipline) contentCategory;
		    for (JAXBElement<?> jaxbElement : discipline.getTaskOrSubDisciplineOrReferenceWorkflow()) {
			if (jaxbElement.getValue().equals(task.getId())) {
			    list.add(discipline);
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
			if (jaxbElement.getValue().equals(task.getId())) {
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
		removedListDiscipline.remove(contentCategory);
	    }
	    for (ContentCategory contentCategory : list) {
		boolean hasEquals = false;
		Discipline discipline = (Discipline) contentCategory;
		for (JAXBElement<?> jaxbElement : discipline.getTaskOrSubDisciplineOrReferenceWorkflow()) {
		    if (jaxbElement.getValue().equals(task.getId())) {
			hasEquals = true;
			break;
		    }
		    if (!hasEquals) {
			ObjectFactory objectFactory = new ObjectFactory();
			discipline.getTaskOrSubDisciplineOrReferenceWorkflow()
				.add(objectFactory.createDisciplineTask(task.getId()));
		    }
		}
	    }
	    // TODO Verificar melhor forma de remover o alerta.
	    List<JAXBElement<?>> toRomove = new ArrayList<>();
	    for (ContentCategory contentCategory : removedListDiscipline) {
		Discipline discipline = (Discipline) contentCategory;
		for (JAXBElement<?> jaxbElement : discipline.getTaskOrSubDisciplineOrReferenceWorkflow()) {
		    if (jaxbElement.getValue().equals(task.getId())) {
			toRomove.add((JAXBElement<?>) jaxbElement);
		    }
		}
		for (JAXBElement<?> jaxbElement : toRomove) {
		    discipline.getTaskOrSubDisciplineOrReferenceWorkflow().remove(jaxbElement);
		}
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
		    if (jaxbElement.getValue().equals(task.getId())) {
			hasEquals = true;
			break;
		    }
		}
		if (!hasEquals) {
		    ObjectFactory objectFactory = new ObjectFactory();
		    customCategory.getCategorizedElementOrSubCategory()
			    .add(objectFactory.createCustomCategoryCategorizedElement(task.getId()));
		}
	    }
	    List<JAXBElement<String>> toRemove = new ArrayList<>();
	    for (CustomCategory customCategory : removedCustomCategory) {
		for (JAXBElement<String> jaxbElement : customCategory.getCategorizedElementOrSubCategory()) {
		    if (jaxbElement.getValue().equals(task.getId())) {
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
    public void removeCategory(int index, JList<ElementWrapper> listCategory) {
	ElementWrapper elementWrapper = ((DefaultListModel<ElementWrapper>) listCategory.getModel()).remove(index);
	removedListDiscipline.add((Discipline) elementWrapper.getElement());
    }

    @Override
    public void removeCustomCategory(int index, JList<ElementWrapper> listCustomCategories) {

	ElementWrapper elementWrapper = ((DefaultListModel<ElementWrapper>) listCustomCategories.getModel())
		.remove(index);
	removedCustomCategory.add((CustomCategory) elementWrapper.getElement());
    }

    @Override
    public void openDialogCategory(Element element, AdaptMeUI owner, TabbedPanel tabbedPanel) {
	SelectDialog dialog = new SelectDialog(task, owner, tabbedPanel, "Select a Discipline",
		SelectDialogFilter.DISCIPLANE, true);
	dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	dialog.setVisible(true);
    }

    @Override
    public void openDialogCustomCategory(Element element, AdaptMeUI owner, TabbedPanel tabbedPanel) {
	SelectDialog dialog = new SelectDialog(task, owner, tabbedPanel, "Select a Custom Category",
		SelectDialogFilter.CUSTOM_CATEGORIES, true);
	dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	dialog.setVisible(true);
    }

    @Override
    public void setStandardCategories(List<ContentCategory> list) {
	DefaultListModel<ElementWrapper> model = (DefaultListModel<ElementWrapper>) getListCategory().getModel();
	for (ContentCategory contentCategory : list) {
	    model.addElement(
		    new ElementWrapper(contentCategory, contentCategory.getName(), EPFConstants.disciplineIcon));
	    ;
	}
	getListCategory().setModel(model);
    }

}
