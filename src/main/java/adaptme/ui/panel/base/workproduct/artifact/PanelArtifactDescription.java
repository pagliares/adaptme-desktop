package adaptme.ui.panel.base.workproduct.artifact;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.epf.uma.Artifact;
import org.eclipse.epf.uma.ArtifactDescription;
import org.eclipse.epf.uma.VariabilityType;
import org.eclipse.epf.uma.WorkProduct;

import adaptme.base.ElementWrapper;
import adaptme.base.MethodLibraryHash;
import adaptme.base.enums.MethodContentVariability;
import adaptme.ui.components.dialog.SelectDialog;
import adaptme.ui.components.dialog.SelectDialogFilter;
import adaptme.ui.panel.base.TabbedPanel;
import adaptme.ui.panel.base.workproduct.WorkProductPanelDescription;
import adaptme.ui.window.AdaptMeUI;
import adaptme.util.DateUtil;

public class PanelArtifactDescription extends WorkProductPanelDescription {

    private static final long serialVersionUID = 1433112055835510696L;
    private Artifact artifact;
    // private TabbedPanel tabbedPanel;
    // private MethodLibraryHash hash;
    // private AdaptMeUI owner;

    public PanelArtifactDescription(Artifact artifact, TabbedPanel tabbedPanel, MethodLibraryHash hash,
	    AdaptMeUI owner) {
	super(artifact, tabbedPanel, hash, owner);
	this.artifact = artifact;
	// this.tabbedPanel = tabbedPanel;
	// this.hash = hash;
	// this.owner = owner;
    }

    @Override
    public void load() {
	this.setBriefDescription(artifact.getBriefDescription());
	this.setPresentationName(artifact.getPresentationName());
	this.setTextFieldName(artifact.getName());
	this.setIsWorkProduct(artifact.isIsAbstract());
	if (artifact.isIsAbstract()) {
	    getBtnAddWorkProductSlot().setEnabled(false);
	    getBtnRemoveWorkProductSlot().setEnabled(false);
	}
	this.setWorkProductSlot(artifact.getFulfill());
	ArtifactDescription artifactDescription = (ArtifactDescription) artifact.getPresentation();
	if (artifactDescription != null) {
	    this.setPurpose(artifactDescription.getPurpose());
	    this.setMainDescription(artifactDescription.getMainDescription());
	    this.setKeyConsiderations(artifactDescription.getKeyConsiderations());
	    this.setBriefOutline(artifactDescription.getBriefOutline());
	    this.setSelectedRepresentation(artifactDescription.getRepresentation());
	    this.setNotation(artifactDescription.getNotation());
	    this.setImpactNotHaving(artifactDescription.getImpactOfNotHaving());
	    this.setReasonNotNeeding(artifactDescription.getReasonsForNotNeeding());
	    this.setRepresentationOptions(artifactDescription.getRepresentationOptions());

	    this.setVersion(artifactDescription.getVersion());
	    try {
		GregorianCalendar c = new GregorianCalendar();
		Date d = new Date();
		c.setTime(d);
		XMLGregorianCalendar xmlGregorianCalendar;
		xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		this.setDate(artifactDescription.getChangeDate() == null ? xmlGregorianCalendar
			: artifactDescription.getChangeDate());
	    } catch (DatatypeConfigurationException e) {
		e.printStackTrace();
	    }
	    this.setChangeDescription(artifactDescription.getChangeDescription());
	    this.setAuthors(artifactDescription.getAuthors());
	    this.setCopyright(artifactDescription.getCopyright());
	}
	VariabilityType variabilityType = artifact.getVariabilityType();

	MethodContentVariability methodContentVariability = MethodContentVariability.make(variabilityType);

	this.setVariabiliteType(methodContentVariability);
	this.setBase(artifact.getVariabilityBasedOnElement());
    }

    @Override
    public void save() {
	artifact.setName(this.gettextFieldName());
	artifact.setPresentationName(this.getPresentationName());
	artifact.setBriefDescription(this.getBriefDescription());

	MethodContentVariability methodContentVariability = (MethodContentVariability) getComboBoxVariabilityType()
		.getSelectedItem();
	artifact.setVariabilityType(methodContentVariability.getVariabilityType());
	artifact.setVariabilityBasedOnElement(this.getBase());
	artifact.setIsAbstract(this.getIsWorkProduct());
	artifact.getFulfill().clear();
	artifact.getFulfill().addAll(this.getWorkProductSlot());

	ArtifactDescription artifactDescription = new ArtifactDescription();
	artifactDescription.setPurpose(getPurpose());
	artifactDescription.setMainDescription(this.getMainDescription());
	artifactDescription.setKeyConsiderations(this.getKeyConsiderations());

	artifactDescription.setBriefOutline(this.getBriefOutline());
	artifactDescription.setRepresentation(this.getSelectedRepresentation());
	artifactDescription.setNotation(this.getNotation());
	artifactDescription.setImpactOfNotHaving(this.getImpactNotHaving());
	artifactDescription.setReasonsForNotNeeding(this.getReasonNotNeeding());
	artifactDescription.setRepresentationOptions(this.getRepresentationOptions());

	artifactDescription.setVersion(this.getVersion());
	try {
	    Date date = new Date();
	    artifactDescription.setChangeDate(DatatypeFactory.newInstance()
		    .newXMLGregorianCalendar(DateUtil.asXMLGregorianCalendar(date).toString()));
	} catch (DatatypeConfigurationException e1) {
	    e1.printStackTrace();
	}
	artifactDescription.setChangeDescription(this.getChangeDescription());
	artifactDescription.setAuthors(this.getAuthors());
	artifactDescription.setCopyright(this.getCopyright());

	artifact.setPresentation(artifactDescription);
    }

    @Override
    public void openDialogSelectCopyright(WorkProduct workProduct, AdaptMeUI owner, TabbedPanel tabbedPanel) {
	SelectDialog dialog = new SelectDialog(artifact, owner, tabbedPanel, "Select Dialog: ",
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
	SelectDialog dialog = new SelectDialog(artifact, owner, tabbedPanel, "Select Dialog: ",
		SelectDialogFilter.ARTIFACT, true);
	dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	dialog.setVisible(true);
    }

    @Override
    public void openDialogWorkProductSlot(WorkProduct workProduct, AdaptMeUI owner, TabbedPanel tabbedPanel) {
	SelectDialog dialog = new SelectDialog(artifact, owner, tabbedPanel, "Select Dialog: ",
		SelectDialogFilter.WORK_PRODUCTS, true);
	dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	dialog.setVisible(true);
    }

    @Override
    public void removeWorkProductSlot(int index, JList<ElementWrapper> listWorkProductSlot) {
	((DefaultListModel<ElementWrapper>) listWorkProductSlot.getModel()).remove(index);
    }

}
