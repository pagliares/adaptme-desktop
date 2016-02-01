package adaptme.ui.panel.base.workproduct.outcome;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.epf.uma.Outcome;
import org.eclipse.epf.uma.VariabilityType;
import org.eclipse.epf.uma.WorkProduct;
import org.eclipse.epf.uma.WorkProductDescription;

import adaptme.base.ElementWrapper;
import adaptme.base.MethodLibraryHash;
import adaptme.base.enums.MethodContentVariability;
import adaptme.ui.components.dialog.SelectDialog;
import adaptme.ui.components.dialog.SelectDialogFilter;
import adaptme.ui.panel.base.TabbedPanel;
import adaptme.ui.panel.base.workproduct.WorkProductPanelDescription;
import adaptme.ui.window.AdaptMeUI;
import adaptme.util.DateUtil;

public class PanelOutcomeDescription extends WorkProductPanelDescription {

    private static final long serialVersionUID = 1133623990611560299L;
    private Outcome outcome;

    public PanelOutcomeDescription(Outcome outcome, TabbedPanel tabbedPanel, MethodLibraryHash hash, AdaptMeUI owner) {
	super(outcome, tabbedPanel, hash, owner);
	this.outcome = outcome;
    }

    @Override
    public void load() {
	this.setBriefDescription(outcome.getBriefDescription());
	this.setPresentationName(outcome.getPresentationName());
	this.setTextFieldName(outcome.getName());
	this.setIsWorkProduct(outcome.isIsAbstract());
	if (outcome.isIsAbstract()) {
	    getBtnAddWorkProductSlot().setEnabled(false);
	    getBtnRemoveWorkProductSlot().setEnabled(false);
	}
	this.setWorkProductSlot(outcome.getFulfill());
	WorkProductDescription workProductDescription = (WorkProductDescription) outcome.getPresentation();
	if (workProductDescription != null) {
	    this.setPurpose(workProductDescription.getPurpose());
	    this.setMainDescription(workProductDescription.getMainDescription());
	    this.setKeyConsiderations(workProductDescription.getKeyConsiderations());
	    this.setImpactNotHaving(workProductDescription.getImpactOfNotHaving());
	    this.setReasonNotNeeding(workProductDescription.getReasonsForNotNeeding());

	    this.setVersion(workProductDescription.getVersion());
	    try {
		GregorianCalendar c = new GregorianCalendar();
		Date d = new Date();
		c.setTime(d);
		XMLGregorianCalendar xmlGregorianCalendar;
		xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		this.setDate(workProductDescription.getChangeDate() == null ? xmlGregorianCalendar
			: workProductDescription.getChangeDate());
	    } catch (DatatypeConfigurationException e) {
		e.printStackTrace();
	    }
	    this.setChangeDescription(workProductDescription.getChangeDescription());
	    this.setAuthors(workProductDescription.getAuthors());
	    this.setCopyright(workProductDescription.getCopyright());
	}
	VariabilityType variabilityType = outcome.getVariabilityType();

	MethodContentVariability methodContentVariability = MethodContentVariability.make(variabilityType);

	this.setVariabiliteType(methodContentVariability);
	this.setBase(outcome.getVariabilityBasedOnElement());
    }

    @Override
    public void save() {
	outcome.setName(this.gettextFieldName());
	outcome.setPresentationName(this.getPresentationName());
	outcome.setBriefDescription(this.getBriefDescription());

	MethodContentVariability methodContentVariability = (MethodContentVariability) getComboBoxVariabilityType()
		.getSelectedItem();
	outcome.setVariabilityType(methodContentVariability.getVariabilityType());
	outcome.setVariabilityBasedOnElement(this.getBase());
	outcome.setIsAbstract(this.getIsWorkProduct());
	outcome.getFulfill().clear();
	outcome.getFulfill().addAll(this.getWorkProductSlot());

	WorkProductDescription workProductDescription = new WorkProductDescription();
	workProductDescription.setPurpose(getPurpose());
	workProductDescription.setMainDescription(this.getMainDescription());
	workProductDescription.setKeyConsiderations(this.getKeyConsiderations());

	workProductDescription.setImpactOfNotHaving(this.getImpactNotHaving());
	workProductDescription.setReasonsForNotNeeding(this.getReasonNotNeeding());

	workProductDescription.setVersion(this.getVersion());
	try {
	    Date date = new Date();
	    workProductDescription.setChangeDate(DatatypeFactory.newInstance()
		    .newXMLGregorianCalendar(DateUtil.asXMLGregorianCalendar(date).toString()));
	} catch (DatatypeConfigurationException e1) {
	    e1.printStackTrace();
	}
	workProductDescription.setChangeDescription(this.getChangeDescription());
	workProductDescription.setAuthors(this.getAuthors());
	workProductDescription.setCopyright(this.getCopyright());

	outcome.setPresentation(workProductDescription);
    }

    @Override
    public void openDialogSelectCopyright(WorkProduct workProduct, AdaptMeUI owner, TabbedPanel tabbedPanel) {
	SelectDialog dialog = new SelectDialog(outcome, owner, tabbedPanel, "Select Dialog: ",
		SelectDialogFilter.SUPPORTING_MATERIALS, true);
	dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	dialog.setVisible(true);
    }

    @Override
    public void removeCopyright(int index, JList<ElementWrapper> listCopyright) {
	setCopyright(null);
    }

    @Override
    public void openDialogSelectBase(WorkProduct workProduct, AdaptMeUI owner, TabbedPanel tabbedPanel) {
	SelectDialog dialog = new SelectDialog(outcome, owner, tabbedPanel, "Select Dialog: ",
		SelectDialogFilter.OUTCOME, true);
	dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	dialog.setVisible(true);
    }

    @Override
    public void openDialogWorkProductSlot(WorkProduct workProduct, AdaptMeUI owner, TabbedPanel tabbedPanel) {
	SelectDialog dialog = new SelectDialog(outcome, owner, tabbedPanel, "Select Dialog: ",
		SelectDialogFilter.WORK_PRODUCTS, true);
	dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	dialog.setVisible(true);
    }

    @Override
    public void removeWorkProductSlot(int index, JList<ElementWrapper> listWorkProductSlot) {
	((DefaultListModel<ElementWrapper>) listWorkProductSlot.getModel()).remove(index);
    }

}
