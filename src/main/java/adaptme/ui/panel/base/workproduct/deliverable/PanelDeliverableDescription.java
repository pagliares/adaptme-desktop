package adaptme.ui.panel.base.workproduct.deliverable;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.epf.uma.Deliverable;
import org.eclipse.epf.uma.DeliverableDescription;
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

public class PanelDeliverableDescription extends WorkProductPanelDescription {

    private static final long serialVersionUID = -4665707762390565330L;
    private Deliverable deliverable;
    // private TabbedPanel tabbedPanel;
    // private MethodLibraryHash hash;
    // private AdaptMeUI owner;

    public PanelDeliverableDescription(Deliverable deliverable, TabbedPanel tabbedPanel, MethodLibraryHash hash,
	    AdaptMeUI owner) {
	super(deliverable, tabbedPanel, hash, owner);
	this.deliverable = deliverable;
	// this.tabbedPanel = tabbedPanel;
	// this.hash = hash;
	// this.owner = owner;
    }

    @Override
    public void load() {
	this.setBriefDescription(deliverable.getBriefDescription());
	this.setPresentationName(deliverable.getPresentationName());
	this.setTextFieldName(deliverable.getName());
	this.setIsWorkProduct(deliverable.isIsAbstract());
	if (deliverable.isIsAbstract()) {
	    getBtnAddWorkProductSlot().setEnabled(false);
	    getBtnRemoveWorkProductSlot().setEnabled(false);
	}
	this.setWorkProductSlot(deliverable.getFulfill());
	WorkProductDescription workProductDescription = (WorkProductDescription) deliverable.getPresentation();
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
	VariabilityType variabilityType = deliverable.getVariabilityType();

	MethodContentVariability methodContentVariability = MethodContentVariability.make(variabilityType);

	this.setVariabiliteType(methodContentVariability);
	this.setBase(deliverable.getVariabilityBasedOnElement());
    }

    @Override
    public void save() {
	deliverable.setName(this.gettextFieldName());
	deliverable.setPresentationName(this.getPresentationName());
	deliverable.setBriefDescription(this.getBriefDescription());

	MethodContentVariability methodContentVariability = (MethodContentVariability) getComboBoxVariabilityType()
		.getSelectedItem();
	deliverable.setVariabilityType(methodContentVariability.getVariabilityType());
	deliverable.setVariabilityBasedOnElement(this.getBase());
	deliverable.setIsAbstract(this.getIsWorkProduct());
	deliverable.getFulfill().clear();
	deliverable.getFulfill().addAll(this.getWorkProductSlot());

	DeliverableDescription deliverableDescription = new DeliverableDescription();
	deliverableDescription.setPurpose(getPurpose());
	deliverableDescription.setMainDescription(this.getMainDescription());
	deliverableDescription.setKeyConsiderations(this.getKeyConsiderations());

	deliverableDescription.setImpactOfNotHaving(this.getImpactNotHaving());
	deliverableDescription.setReasonsForNotNeeding(this.getReasonNotNeeding());

	deliverableDescription.setVersion(this.getVersion());
	try {
	    Date date = new Date();
	    deliverableDescription.setChangeDate(DatatypeFactory.newInstance()
		    .newXMLGregorianCalendar(DateUtil.asXMLGregorianCalendar(date).toString()));
	} catch (DatatypeConfigurationException e1) {
	    e1.printStackTrace();
	}
	deliverableDescription.setChangeDescription(this.getChangeDescription());
	deliverableDescription.setAuthors(this.getAuthors());
	deliverableDescription.setCopyright(this.getCopyright());

	deliverable.setPresentation(deliverableDescription);
    }

    @Override
    public void openDialogSelectCopyright(WorkProduct workProduct, AdaptMeUI owner, TabbedPanel tabbedPanel) {
	SelectDialog dialog = new SelectDialog(deliverable, owner, tabbedPanel, "Select Dialog: ",
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
	SelectDialog dialog = new SelectDialog(deliverable, owner, tabbedPanel, "Select Dialog: ",
		SelectDialogFilter.DELIVERABLE, true);
	dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	dialog.setVisible(true);
    }

    @Override
    public void openDialogWorkProductSlot(WorkProduct workProduct, AdaptMeUI owner, TabbedPanel tabbedPanel) {
	SelectDialog dialog = new SelectDialog(deliverable, owner, tabbedPanel, "Select Dialog: ",
		SelectDialogFilter.WORK_PRODUCTS, true);
	dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	dialog.setVisible(true);
    }

    @Override
    public void removeWorkProductSlot(int index, JList<ElementWrapper> listWorkProductSlot) {
	((DefaultListModel<ElementWrapper>) listWorkProductSlot.getModel()).remove(index);
    }

}
