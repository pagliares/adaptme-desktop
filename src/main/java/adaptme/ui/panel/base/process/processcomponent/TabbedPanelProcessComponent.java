package adaptme.ui.panel.base.process.processcomponent;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListDataListener;
import javax.swing.tree.MutableTreeNode;

import org.eclipse.epf.uma.Process;

import adaptme.base.MethodLibraryHash;
import adaptme.ui.panel.base.TabbedPanel;
import simulator.uma.dynamic.DynamicProcess;

public class TabbedPanelProcessComponent extends JTabbedPane implements TabbedPanel {

    private static final long serialVersionUID = -5803657795822384401L;
    private Process process;
    private DynamicProcess dynamicProcess;
    private MethodLibraryHash hash;
    private MutableTreeNode node;

    private JPanel panelSPEMProcessComponentsWorkBreakdownStructure;
    // private JPanel panelDynamicProcessComponentsWorkBreakdownStructure;
    private JPanel panelProcessComponentsTeamAllocation;
    private JPanel panelProcessComponentsWorkProductUsage;
    private JPanel panelProcessComponentsConsolidatedView;
    // private JPanel panelProcessComponentsDescription;

    public TabbedPanelProcessComponent(Process process, DynamicProcess dynamicProcess, MethodLibraryHash hash,
	    MutableTreeNode node) {
	this.process = process;
	this.dynamicProcess = dynamicProcess;
	this.hash = hash;
	this.node = node;
	initComponents();
    }

    private void initComponents() {
	setTabPlacement(SwingConstants.BOTTOM);
	// panelProcessComponentsDescription = new
	// PanelProcessComponentsDescription(process);

	panelSPEMProcessComponentsWorkBreakdownStructure = new PanelProcessComponentsWorkBreakdownStructure(process,
		hash);
	// panelDynamicProcessComponentsWorkBreakdownStructure = new
	// PanelDynamicWorkBreakdownStructure(dynamicProcess, hash);
	panelProcessComponentsTeamAllocation = new PanelProcessComponentsTeamAllocation(process, hash);
	panelProcessComponentsWorkProductUsage = new PanelProcessComponentsWorkProductUsage(process, hash);
	panelProcessComponentsConsolidatedView = new PanelProcessComponentsConsolidatedView(process, hash);

	// JScrollPane scrollProcessComponentsDescription = new JScrollPane();
	JScrollPane scrollSPEMProcessComponentsWorkBreakdownStructure = new JScrollPane();
	// JScrollPane scrollProjectProcessComponentsWorkBreakdownStructure =
	// new JScrollPane();
	JScrollPane scrollProcessComponentsTeamAllocation = new JScrollPane();
	JScrollPane scrollProcessComponentsWorkProductUsage = new JScrollPane();
	JScrollPane scrollProcessComponentsConsolidatedView = new JScrollPane();

	// scrollProcessComponentsDescription.setViewportView(panelProcessComponentsDescription);
	scrollSPEMProcessComponentsWorkBreakdownStructure
		.setViewportView(panelSPEMProcessComponentsWorkBreakdownStructure);
	// scrollProjectProcessComponentsWorkBreakdownStructure.setViewportView(panelDynamicProcessComponentsWorkBreakdownStructure);
	scrollProcessComponentsTeamAllocation.setViewportView(panelProcessComponentsTeamAllocation);
	scrollProcessComponentsWorkProductUsage.setViewportView(panelProcessComponentsWorkProductUsage);
	scrollProcessComponentsConsolidatedView.setViewportView(panelProcessComponentsConsolidatedView);

	// this.addTab("Description", null, scrollProcessComponentsDescription,
	// null);
	this.addTab("SPEM Work Breakdown Structure", null, scrollSPEMProcessComponentsWorkBreakdownStructure, null);
	// this.addTab("Dynamic Work Breakdown Structure", null,
	// scrollProjectProcessComponentsWorkBreakdownStructure,null);
	this.addTab("Team Allocation", scrollProcessComponentsTeamAllocation);
	this.addTab("Work Product Usage", scrollProcessComponentsWorkProductUsage);
	this.addTab("Consolidated View", scrollProcessComponentsConsolidatedView);

	// load();
    }

    @Override
    public void save() {
	((PanelProcessComponentsWorkBreakdownStructure) panelSPEMProcessComponentsWorkBreakdownStructure).save();
    }

    // public void load() {
    // ((PanelProcessComponentsDescription)
    // panelProcessComponentsDescription).load();
    // }

    @Override
    public ListDataListener getListDataListener() {
	return null;
    }

    @Override
    public DocumentListener getDocumentListener() {
	return null;
    }

    @Override
    public MutableTreeNode getNode() {
	return node;
    }

}
