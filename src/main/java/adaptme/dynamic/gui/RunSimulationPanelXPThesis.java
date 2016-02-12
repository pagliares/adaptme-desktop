package adaptme.dynamic.gui;

import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import adaptme.util.SimulationSample;
import model.spem.ProcessRepository;
import model.spem.config.ContainerConfig;
import model.spem.config.TaskConfig;
import model.spem.config.WorkProductConfig;
import model.spem.util.FinishType;
import model.spem.util.PredecessorType;
import simulator.base.WorkProduct;
import simulator.simple.Simple;
import simulator.simple.entity.Developer;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class RunSimulationPanelXPThesis {
    private JPanel panel;
    private JLabel lblTeamAtributes;
    private JLabel lblTeamSize;
    private JTextField textFieldTeamSize;
    private JLabel lblPause;
    private JRadioButton rdbtnAtTheEndOfSession;
    private JRadioButton rdbtnAtTheEndOfWorkingDay;
    private JRadioButton rdbtnAtTheEnd;
    private JButton btnRunSimulation;
    private JLabel lblSimulationAtributes;
    private JLabel lblNumberOfRuns;
    private JTextField textField;
    private ProcessRepository processRepository;
    private MainPanel mainPanel;

    public RunSimulationPanelXPThesis(ProcessRepository processRepository, MainPanel mainPanel) {
	this.processRepository = processRepository;
	this.mainPanel = mainPanel;
	panel = new JPanel();

	lblTeamAtributes = new JLabel("Team atributes");
	lblTeamAtributes.setFont(new Font("Tahoma", Font.BOLD, 13));

	lblTeamSize = new JLabel("Team size");

	textFieldTeamSize = new JTextField();
	textFieldTeamSize.setColumns(10);

	lblPause = new JLabel("Pause");
	lblPause.setFont(new Font("Tahoma", Font.BOLD, 13));

	rdbtnAtTheEndOfSession = new JRadioButton("At the end of each session");

	rdbtnAtTheEndOfWorkingDay = new JRadioButton("At the end of each working day");

	rdbtnAtTheEnd = new JRadioButton("At the end of the project");
	rdbtnAtTheEnd.setSelected(true);

	btnRunSimulation = new JButton("Run simulation");
	btnRunSimulation.addActionListener(e -> goForIt());

	lblSimulationAtributes = new JLabel("Simulation attributes");
	lblSimulationAtributes.setFont(new Font("SansSerif", Font.BOLD, 13));

	lblNumberOfRuns = new JLabel("Number of runs");

	textField = new JTextField();
	textField.setColumns(10);
	GroupLayout gl_panel = new GroupLayout(panel);
	gl_panel.setHorizontalGroup(
		gl_panel.createParallelGroup(Alignment.LEADING)
			.addGroup(gl_panel.createSequentialGroup()
				.addGap(20)
				.addComponent(lblTeamAtributes))
			.addGroup(gl_panel.createSequentialGroup()
				.addGap(18)
				.addComponent(lblTeamSize)
				.addGap(12)
				.addComponent(textFieldTeamSize, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
			.addGroup(gl_panel.createSequentialGroup()
				.addGap(20)
				.addComponent(lblSimulationAtributes, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE))
			.addGroup(gl_panel.createSequentialGroup()
				.addGap(20)
				.addComponent(lblPause))
			.addGroup(gl_panel.createSequentialGroup()
				.addGap(20)
				.addComponent(rdbtnAtTheEndOfSession))
			.addGroup(gl_panel.createSequentialGroup()
				.addGap(20)
				.addComponent(rdbtnAtTheEndOfWorkingDay))
			.addGroup(gl_panel.createSequentialGroup()
				.addGap(20)
				.addComponent(rdbtnAtTheEnd))
			.addGroup(gl_panel.createSequentialGroup()
				.addGap(20)
				.addComponent(lblNumberOfRuns)
				.addGap(28)
				.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
			.addGroup(gl_panel.createSequentialGroup()
				.addGap(20)
				.addComponent(btnRunSimulation))
	);
	gl_panel.setVerticalGroup(
		gl_panel.createParallelGroup(Alignment.LEADING)
			.addGroup(gl_panel.createSequentialGroup()
				.addGap(40)
				.addComponent(lblTeamAtributes)
				.addGap(12)
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel.createSequentialGroup()
						.addGap(6)
						.addComponent(lblTeamSize))
					.addComponent(textFieldTeamSize, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(27)
				.addComponent(lblSimulationAtributes, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
				.addGap(23)
				.addComponent(lblPause)
				.addGap(20)
				.addComponent(rdbtnAtTheEndOfSession)
				.addGap(14)
				.addComponent(rdbtnAtTheEndOfWorkingDay)
				.addGap(12)
				.addComponent(rdbtnAtTheEnd)
				.addGap(29)
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel.createSequentialGroup()
						.addGap(6)
						.addComponent(lblNumberOfRuns))
					.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(26)
				.addComponent(btnRunSimulation))
	);
	panel.setLayout(gl_panel);
    }

    private void goForIt() {
	mainPanel.updateContent();
	// HashMap<String, RoleConfig> roleMeasurementConfigHash = new
	// HashMap<>();
	HashMap<String, WorkProductConfig> workProductMeasurementConfigHash = new HashMap<>();
	HashMap<String, ContainerConfig> containerMeasurementConfigHash = new HashMap<>();
	HashMap<String, TaskConfig> taskMeasurementConfigHash = new HashMap<>();
	HashMap<String, List<WorkProduct>> workProductHash = new HashMap<>();
	HashMap<String, List<Developer>> developersHash = new HashMap<>();

	List<Developer> developersList = new ArrayList<>();
	for (int i = 0; i < 4; i++) {
	    Developer developer = new Developer("Developer " + i);
	    developersList.add(developer);
	}
	developersHash.put("Developer", developersList);

	developersList = new ArrayList<>();
	for (int i = 0; i < 1; i++) {
	    Developer developer = new Developer("Customer " + i);
	    developersList.add(developer);
	}
	developersHash.put("Customer", developersList);

	ContainerConfig sprintPlanning = new ContainerConfig();
	sprintPlanning.setFinishStatus("Split Macro User Stories");
	sprintPlanning.setName("Sprint Planning");
	containerMeasurementConfigHash.put(sprintPlanning.getName(), sprintPlanning);

	ContainerConfig development = new ContainerConfig();
	development.setName("Development");
	containerMeasurementConfigHash.put(development.getName(), development);

	TaskConfig prioritizeUserStories = new TaskConfig();
	List<String> list = new ArrayList<>();
	list.add("");
	prioritizeUserStories.setName("Prioritize User Stories");
	prioritizeUserStories.setWorkProductDependencies(list);
	prioritizeUserStories.setFinishType(FinishType.STATUS);
	prioritizeUserStories.setPredecessorType(PredecessorType.STATUS);
	taskMeasurementConfigHash.put(prioritizeUserStories.getName(), prioritizeUserStories);

	TaskConfig estimateUserStories = new TaskConfig();
	list = new ArrayList<>();
	list.add("Prioritize User Stories");
	estimateUserStories.setName("Estimate User Stories");
	estimateUserStories.setWorkProductDependencies(list);
	estimateUserStories.setFinishType(FinishType.STATUS);
	estimateUserStories.setPredecessorType(PredecessorType.STATUS);
	taskMeasurementConfigHash.put(estimateUserStories.getName(), estimateUserStories);

	TaskConfig splitMacroUserStories = new TaskConfig();
	list = new ArrayList<>();
	list.add("Estimate User Stories");
	splitMacroUserStories.setName("Split Macro User Stories");
	splitMacroUserStories.setWorkProductDependencies(list);
	splitMacroUserStories.setFinishType(FinishType.STATUS);
	splitMacroUserStories.setPredecessorType(PredecessorType.STATUS);
	taskMeasurementConfigHash.put(splitMacroUserStories.getName(), splitMacroUserStories);

	TaskConfig soloTestAfter = new TaskConfig();
	list = new ArrayList<>();
	list.add("Split Macro User Stories");
	soloTestAfter.setName("Solo Test After");
	soloTestAfter.setWorkProductDependencies(list);
	soloTestAfter.setFinishType(FinishType.SIZE);
	soloTestAfter.setPredecessorType(PredecessorType.SIZE);
	taskMeasurementConfigHash.put(soloTestAfter.getName(), soloTestAfter);

	List<WorkProduct> listWorkProduct = new ArrayList<WorkProduct>();
	for (int i = 0; i < 10; i++) {
	    WorkProduct workProduct = new WorkProduct();
	    workProduct.setName("User Story " + i);
	    workProduct.setCapacity((int) SimulationSample.getSampleFromLogNormalDistribution(60, 5));
	    workProduct.setStatus("");
	    listWorkProduct.add(workProduct);
	}
	workProductHash.put(prioritizeUserStories.getName(), listWorkProduct);
	workProductHash.put(estimateUserStories.getName(), listWorkProduct);
	workProductHash.put(splitMacroUserStories.getName(), listWorkProduct);
	workProductHash.put(soloTestAfter.getName(), listWorkProduct);

	Simple simple = new Simple(processRepository);
	simple.setContainerMeasurementConfigHash(containerMeasurementConfigHash);
	simple.setWorkProductMeasurementConfigHash(workProductMeasurementConfigHash);
	simple.setTaskMeasurementConfigHash(taskMeasurementConfigHash);
	simple.setWorkProductHash(workProductHash);
	simple.setDevelopersHash(developersHash);

	simple.goForIt();
    }

    public Component getPanel() {
	return panel;
    }
}
