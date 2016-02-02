package adaptme.ui.window.perspective;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import adaptme.dynamic.gui.MainPanel;
import adaptme.dynamic.gui.RunSimulationPanel;
import model.spem.ProcessRepository;

public class SimulationPanel {
    private JPanel panel;
    private JTabbedPane tabbedPane;

    public SimulationPanel(ProcessRepository processRepository, MainPanel mainPanel) {
	panel = new JPanel();

	tabbedPane = new JTabbedPane(SwingConstants.TOP);
	AdjustProjectParametersPanel adjustProjectParametersPanel = new AdjustProjectParametersPanel();
	tabbedPane.addTab("3.1. Adjust process parameters for the simulation model",
		adjustProjectParametersPanel.getPanel());
	
	WorkProductResourcesPanel defineProjectResourcesPanel = new WorkProductResourcesPanel();
	tabbedPane.addTab("3.2. Define project resources parameters for the simulation model",
		defineProjectResourcesPanel.getPanel());
	
    // novo, mas aparentemente esta classe nao esta sendo usada. A que esta sendo usada e a AlternativeOfProcessPanel
	DefineProjectResourcesPanel2 defineProjectResourcesPanel2 = new DefineProjectResourcesPanel2();
	tabbedPane.addTab("3.3. Mapping SPEM Roles to XACDML Resources",
		defineProjectResourcesPanel.getPanel());
	
	
	RunSimulationPanel runSimulationPanel = new RunSimulationPanel(processRepository, mainPanel);
	tabbedPane.addTab("3.4. Run simulation", runSimulationPanel.getPanel());
	
	GroupLayout gl_panel = new GroupLayout(panel);
	gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(tabbedPane,
		GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE));
	gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(tabbedPane,
		Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE));
	panel.setLayout(gl_panel);
    }

    public JPanel getPanel() {
	return panel;
    }
}
