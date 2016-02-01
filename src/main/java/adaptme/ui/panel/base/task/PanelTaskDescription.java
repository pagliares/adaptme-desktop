package adaptme.ui.panel.base.task;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JDialog;
import javax.swing.JList;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.epf.uma.Task;
import org.eclipse.epf.uma.TaskDescription;
import org.eclipse.epf.uma.VariabilityType;

import adaptme.base.ElementWrapper;
import adaptme.base.MethodLibraryHash;
import adaptme.base.enums.MethodContentVariability;
import adaptme.ui.components.dialog.SelectDialog;
import adaptme.ui.components.dialog.SelectDialogFilter;
import adaptme.ui.panel.base.TabbedPanel;
import adaptme.ui.panel.base.TaskPanelDescription;
import adaptme.ui.window.AdaptMeUI;
import adaptme.util.DateUtil;

public class PanelTaskDescription extends TaskPanelDescription {

    private static final long serialVersionUID = 7372363476206087435L;
    private Task task;
    // private MethodLibraryHash hash;
    // private TabbedPanel tabbedPanel;
    // private AdaptMeUI owner;

    public PanelTaskDescription(Task task, TabbedPanel tabbedPanel, MethodLibraryHash hash, AdaptMeUI owner) {
	super(task, tabbedPanel, hash, owner);
	this.task = task;
	// this.tabbedPanel = tabbedPanel;
	// this.hash = hash;
	// this.owner = owner;
    }

    @Override
    public void load() {
	this.setBriefDescription(task.getBriefDescription());
	this.setPresentationName(task.getPresentationName());
	this.setTextFieldName(task.getName());
	TaskDescription taskDescription = (TaskDescription) task.getPresentation();
	if (taskDescription != null) {
	    this.setPurpose(taskDescription.getPurpose());
	    this.setMainDescription(taskDescription.getMainDescription());
	    this.setKeyConsiderations(taskDescription.getKeyConsiderations());
	    this.setVersion(taskDescription.getVersion());
	    try {
		GregorianCalendar c = new GregorianCalendar();
		Date d = new Date();
		c.setTime(d);
		XMLGregorianCalendar xmlGregorianCalendar;
		xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		this.setChangeDate(taskDescription.getChangeDate() == null ? xmlGregorianCalendar
			: taskDescription.getChangeDate());
	    } catch (DatatypeConfigurationException e) {
		e.printStackTrace();
	    }
	    this.setChangeDescription(taskDescription.getChangeDescription());
	    this.setAuthors(taskDescription.getAuthors());
	    this.setCopyright(taskDescription.getCopyright());
	}
	VariabilityType variabilityType = task.getVariabilityType();

	MethodContentVariability methodContentVariability = MethodContentVariability.make(variabilityType);

	this.setVariabiliteType(methodContentVariability);
	this.setBase(task.getVariabilityBasedOnElement());
    }

    @Override
    public void save() {
	task.setName(this.gettextFieldName());
	task.setPresentationName(this.getPresentationName());
	task.setBriefDescription(this.getBriefDescription());

	MethodContentVariability methodContentVariability = (MethodContentVariability) this.getComboBoxVariabilityType()
		.getSelectedItem();
	task.setVariabilityType(methodContentVariability.getVariabilityType());
	task.setVariabilityBasedOnElement(this.getBase());

	TaskDescription taskDescription = new TaskDescription();
	taskDescription.setPurpose(getPurpose());
	taskDescription.setMainDescription(this.getMainDescription());
	taskDescription.setKeyConsiderations(this.getKeyConsiderations());
	taskDescription.setAlternatives(getAlternatives());
	taskDescription.setVersion(this.getVersion());
	try {
	    Date date = new Date();
	    taskDescription.setChangeDate(DatatypeFactory.newInstance()
		    .newXMLGregorianCalendar(DateUtil.asXMLGregorianCalendar(date).toString()));
	} catch (DatatypeConfigurationException e1) {
	    e1.printStackTrace();
	}
	taskDescription.setChangeDescription(this.getChangeDescription());
	taskDescription.setAuthors(this.getAuthors());
	taskDescription.setCopyright(this.getCopyright());

	task.setPresentation(taskDescription);
    }

    @Override
    public void removeCopyright(String id, JList<ElementWrapper> listCopyright) {
	setCopyright(null);
    }

    @Override
    public void openDialogSelectCopyright(Task task, AdaptMeUI owner, TabbedPanel tabbedPanel) {
	SelectDialog dialog = new SelectDialog(task, owner, tabbedPanel, "Select Dialog: ",
		SelectDialogFilter.SUPPORTING_MATERIALS, true);
	dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	dialog.setVisible(true);
    }

    @Override
    public void openDialogSelectBase(Task task, AdaptMeUI owner, TabbedPanel tabbedPanel) {
	SelectDialog dialog = new SelectDialog(task, owner, tabbedPanel, "Select Dialog: ", SelectDialogFilter.TASK,
		true);
	dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	dialog.setVisible(true);
    }

}
