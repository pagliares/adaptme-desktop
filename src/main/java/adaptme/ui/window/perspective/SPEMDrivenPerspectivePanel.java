package adaptme.ui.window.perspective;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.File;

import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultTreeModel;

import org.jdesktop.swingx.JXTreeTable;

import adaptme.base.MethodLibraryWrapper;
import adaptme.base.ObjectFactory;
import adaptme.dynamic.gui.SimulationObjectivePanel;
import adaptme.ui.components.model.DynamicWorkBreakdownStructureTreeTableModel;
import adaptme.ui.components.renderer.TreeTableCellRenderCustom;
import adaptme.ui.window.AdaptMeUI;
import adaptme.ui.window.perspective.pane.AlternativeOfProcessPanel;
import adaptme.ui.window.perspective.pane.PerspectivePanel;
import simulator.uma.dynamic.DynamicProcess;

public class SPEMDrivenPerspectivePanel implements PerspectivePanel {

	private JPanel panel;
	// private AdaptMeUI adaptMeUI;

	private MethodLibraryWrapper methodLibraryWrapper;
	private AlternativeOfProcessPanel alternativeOfProcessPanel;

	private JXTreeTable dynamicTreeTable;
	private DynamicWorkBreakdownStructureTreeTableModel dynamicTreeTableModel;

	private DynamicProcess dynamicProcessXP;

	private JTabbedPane tabbedPane;

	public SPEMDrivenPerspectivePanel(AdaptMeUI adaptMeUI) {

		panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setLayout(new BorderLayout(0, 0));

		tabbedPane = new JTabbedPane(SwingConstants.TOP);
		methodLibraryWrapper = new MethodLibraryWrapper();
		methodLibraryWrapper.load(new File(getClass().getResource("/resources/process/empty/empty.xml").getPath()));
		ObjectFactory.setHash(methodLibraryWrapper.getMethodLibraryHash());
		alternativeOfProcessPanel = new AlternativeOfProcessPanel(adaptMeUI, this, methodLibraryWrapper);

		SimulationObjectivePanel simulationObjectivePanel = new SimulationObjectivePanel(tabbedPane, alternativeOfProcessPanel);
		tabbedPane.addTab("1. Definition of the simulation objective", null, simulationObjectivePanel.getPanel(), null);
		JSplitPane splitPane = new JSplitPane();
		splitPane.setLeftComponent(tabbedPane);
		splitPane.setDividerLocation(900);
		panel.add(tabbedPane, BorderLayout.CENTER);
		initDynamicProcessXP();
		dynamicTreeTableModel = new DynamicWorkBreakdownStructureTreeTableModel(dynamicProcessXP);
		dynamicTreeTable = new JXTreeTable();
		dynamicTreeTable.setTreeTableModel(dynamicTreeTableModel);
		dynamicTreeTable
				.setTreeCellRenderer(new TreeTableCellRenderCustom(methodLibraryWrapper.getMethodLibraryHash()));
		JTabbedPane tabbedPane1 = new JTabbedPane();
		splitPane.setRightComponent(tabbedPane1);
	}

	private void initDynamicProcessXP() {
		dynamicProcessXP = ObjectFactory.newDynamicProcess("xp_base");
	}

	public JPanel getPanel() {
		return panel;
	}

	public void addTab(String title, Component component) {
		tabbedPane.addTab(title, component);
	}
	public JTabbedPane getTabbedPane(){
		return tabbedPane;
	}
	
	@Override
	public void resetXPProcess() {
		initDynamicProcessXP();
		// dynamicTreeTable = new JXTreeTable();
		dynamicTreeTableModel = new DynamicWorkBreakdownStructureTreeTableModel(dynamicProcessXP);
		dynamicTreeTable.setTreeTableModel(dynamicTreeTableModel);
		dynamicTreeTable
				.setTreeCellRenderer(new TreeTableCellRenderCustom(methodLibraryWrapper.getMethodLibraryHash()));
		dynamicTreeTable.expandAll();
	}

	public DefaultTreeModel getTreeModel() {
		return alternativeOfProcessPanel.getLeftTreeModel();
	}

	public JMenu getFileMenu() {
		return alternativeOfProcessPanel.getFileMenu();
	}

}
