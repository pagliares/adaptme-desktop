package adaptme.ui.panel.base.role;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JDialog;
import javax.swing.JList;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.epf.uma.Element;
import org.eclipse.epf.uma.Role;
import org.eclipse.epf.uma.RoleDescription;
import org.eclipse.epf.uma.VariabilityType;

import adaptme.base.ElementWrapper;
import adaptme.base.MethodLibraryHash;
import adaptme.base.enums.MethodContentVariability;
import adaptme.ui.components.dialog.SelectDialog;
import adaptme.ui.components.dialog.SelectDialogFilter;
import adaptme.ui.panel.base.RolePanelDescription;
import adaptme.ui.panel.base.TabbedPanel;
import adaptme.ui.window.AdaptMeUI;
import adaptme.util.DateUtil;

public class PanelRoleDescription extends RolePanelDescription {

    private static final long serialVersionUID = -2153410689837079495L;
    private Role role;
    // private TabbedPanel tabbedPanel;
    // private MethodLibraryHash hash;
    // private AdaptMeUI owner;

    public PanelRoleDescription(Element element, TabbedPanel tabbedPanel, MethodLibraryHash hash, AdaptMeUI owner) {
	super(element, tabbedPanel, hash, owner);
	this.role = (Role) element;

    }

    @Override
    public void load() {
	this.setBriefDescription(role.getBriefDescription());
	this.setPresentationName(role.getPresentationName());
	this.setTextFieldName(role.getName());
	RoleDescription roleDescription = (RoleDescription) role.getPresentation();
	if (roleDescription != null) {
	    this.setMainDescription(roleDescription.getMainDescription());
	    this.setKeyConsiderations(roleDescription.getKeyConsiderations());
	    this.setSkills(roleDescription.getSkills());
	    this.setAssignmentApproaches(roleDescription.getAssignmentApproaches());
	    this.setSynonyms(roleDescription.getSynonyms());
	    this.setVersion(roleDescription.getVersion());
	    try {
		GregorianCalendar c = new GregorianCalendar();
		Date d = new Date();
		c.setTime(d);
		XMLGregorianCalendar xmlGregorianCalendar;
		xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		this.setChangeDate(roleDescription.getChangeDate() == null ? xmlGregorianCalendar
			: roleDescription.getChangeDate());
	    } catch (DatatypeConfigurationException e) {
		e.printStackTrace();
	    }
	    this.setChangeDescription(roleDescription.getChangeDescription());
	    this.setAuthors(roleDescription.getAuthors());
	    this.setCopyright(roleDescription.getCopyright());
	}
	VariabilityType variabilityType = role.getVariabilityType();

	MethodContentVariability methodContentVariability = MethodContentVariability.make(variabilityType);

	this.setVariabiliteType(methodContentVariability);
	this.setBase(role.getVariabilityBasedOnElement());
    }

    @Override
    public void save() {
	role.setName(this.gettextFieldName());
	role.setPresentationName(this.getPresentationName());
	role.setBriefDescription(this.getBriefDescription());

	MethodContentVariability methodContentVariability = (MethodContentVariability) getComboBoxVariabilityType()
		.getSelectedItem();
	role.setVariabilityType(methodContentVariability.getVariabilityType());
	role.setVariabilityBasedOnElement(this.getBase());

	RoleDescription roleDescription = new RoleDescription();
	roleDescription.setMainDescription(this.getMainDescription());
	roleDescription.setKeyConsiderations(this.getKeyConsiderations());
	roleDescription.setSkills(this.getSkills());
	roleDescription.setAssignmentApproaches(this.getAssignmentApproaches());
	roleDescription.setSynonyms(this.getSynonyms());
	roleDescription.setVersion(this.getVersion());
	try {
	    Date date = new Date();
	    roleDescription.setChangeDate(DatatypeFactory.newInstance()
		    .newXMLGregorianCalendar(DateUtil.asXMLGregorianCalendar(date).toString()));
	} catch (DatatypeConfigurationException e1) {
	    e1.printStackTrace();
	}
	roleDescription.setChangeDescription(this.getChangeDescription());
	roleDescription.setAuthors(this.getAuthors());
	roleDescription.setCopyright(this.getCopyright());

	role.setPresentation(roleDescription);
    }

    @Override
    public void removeCopyright(String id, JList<ElementWrapper> listCopyright) {
	setCopyright(null);
    }

    @Override
    public void openDialogSelectCopyright(Element element, AdaptMeUI owner, TabbedPanel tabbedPanel) {
	SelectDialog dialog = new SelectDialog(role, owner, tabbedPanel, "Select Dialog: ",
		SelectDialogFilter.SUPPORTING_MATERIALS, true);
	dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	dialog.setVisible(true);
    }

    @Override
    public void openDialogSelectBase(Element element, AdaptMeUI owner, TabbedPanel tabbedPanel) {
	SelectDialog dialog = new SelectDialog(role, owner, tabbedPanel, "Select Dialog: ", SelectDialogFilter.ROLE,
		true);
	dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	dialog.setVisible(true);
    }
}
