package adaptme.ui.window.perspective;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import adaptme.DynamicExperimentationProgramProxy;
import adaptme.facade.SimulationManagerFacade;
import model.spem.SimulationFacade;
import simula.manager.SimulationManager;
import simulator.base.Policy;
import simulator.base.QueueType;
import simulator.base.WorkProductXACDML;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.border.EtchedBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ExperimentationPanel extends JPanel {
	
	
	private JTextField textField;
	private JTable table;
	private JTextField numberOfReplicationsTextField;
	private JTextField textField_2;
	private TableColumnModel modeloColuna;
	private JComboBox<VariableType> comboBoxVariableType = new JComboBox<>();
	private JTextField textField_3;
	
	private ShowResultsPanel showResultsPanel;
	private SimulationManagerFacade simulationManagerFacade;
	
	private WorkProductResourcesPanel workProductResourcesPanel;
	private JTextField textField_4;
	
	private JTabbedPane tabbedPaneActivity4;

	public ExperimentationPanel(WorkProductResourcesPanel workProductResourcesPanel, JTabbedPane tabbedPaneActivity4, SimulationFacade simulationFacade) {
		this.tabbedPaneActivity4 = tabbedPaneActivity4;
		this.workProductResourcesPanel = workProductResourcesPanel;
		this.simulationManagerFacade = SimulationManagerFacade.getSimulationManagerFacade(); // singleton
		this.simulationManagerFacade.setShowResultsPanel(showResultsPanel);
		setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Run-in period. Welch's method (non-terminating systems)", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(584, 22, 495, 69);
		add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblWindowFrame = new JLabel("Time window");
		lblWindowFrame.setBounds(20, 33, 89, 16);
		panel_1.add(lblWindowFrame);
		
		textField_2 = new JTextField();
		textField_2.setBounds(122, 27, 75, 28);
		panel_1.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblRuninTime = new JLabel("Run-in time");
		lblRuninTime.setBounds(300, 33, 89, 16);
		panel_1.add(lblRuninTime);
		
		textField_3 = new JTextField();
		textField_3.setBounds(389, 27, 69, 28);
		panel_1.add(textField_3);
		textField_3.setColumns(10);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Line chart for run-in period (Welch's method)", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_3.setBounds(584, 191, 495, 295);
		 
		add(panel_3);
		panel_3.setLayout(null);
		
		JPanel welchChartPanel = new WelchChartPanel().createChartPanel();
		welchChartPanel.setBounds(6, 23, 483, 266);
		panel_3.add(welchChartPanel);
		 
		 
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(null, "Run control", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_4.setBounds(32, 417, 522, 69);
		add(panel_4);
		panel_4.setLayout(null);
		
		JLabel lblNumberOfReplications = new JLabel("Number of replications");
		lblNumberOfReplications.setBounds(6, 29, 145, 16);
		panel_4.add(lblNumberOfReplications);
		
		numberOfReplicationsTextField = new JTextField();
		numberOfReplicationsTextField.setBounds(163, 23, 66, 28);
		panel_4.add(numberOfReplicationsTextField);
		numberOfReplicationsTextField.setColumns(10);
		
		JButton btnSimulate = new JButton("Simulate");
		btnSimulate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int numberReplications = Integer.parseInt(numberOfReplicationsTextField.getText());
				showResultsPanel.updateShowResultsPanelTable(numberReplications);
				simulationFacade.addNumberOfSimulationRuns(numberReplications);
				simulationManagerFacade.execute(numberReplications);
				tabbedPaneActivity4.setSelectedIndex(2); // show resultsPanel
			 
				
			}
		});
		btnSimulate.setBounds(256, 24, 98, 29);
		panel_4.add(btnSimulate);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "System type", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_6.setBounds(32, 344, 522, 61);
		add(panel_6);
		panel_6.setLayout(null);
		
		JCheckBox chckbxStopOnB = new JCheckBox("Non-terminating");
		chckbxStopOnB.setBounds(18, 23, 165, 23);
		panel_6.add(chckbxStopOnB);
		
		JCheckBox chckbxStopOnEnd = new JCheckBox("Terminating");
		chckbxStopOnEnd.setBounds(195, 23, 189, 23);
		panel_6.add(chckbxStopOnEnd);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Project ending condition", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_7.setBounds(32, 272, 522, 69);
		add(panel_7);
		panel_7.setLayout(null);
		
		JLabel lblDurationTime = new JLabel("Duration time");
		lblDurationTime.setBounds(21, 33, 87, 16);
		panel_7.add(lblDurationTime);
		
		textField = new JTextField();
		textField.setBounds(118, 27, 74, 28);
		panel_7.add(textField);
		textField.setColumns(10);
		
		JLabel lblWorkProductFinishes = new JLabel("No more work product");
		lblWorkProductFinishes.setBounds(204, 32, 165, 16);
		panel_7.add(lblWorkProductFinishes);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(362, 28, 156, 27);
		panel_7.add(comboBox);
		
		comboBoxVariableType.addItem(VariableType.INDEPENDENT);
		comboBoxVariableType.addItem(VariableType.DEPENDENT);
		comboBoxVariableType.addItem(VariableType.INTERMEDIATE);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(32, 22, 522, 238);
		add(scrollPane);
		
		table = new JTable();
		
		List<WorkProductXACDML> list = workProductResourcesPanel.getWorkProducts();
		table.setModel(new WorkProductTableModelExperimentation(list));
		
		for (int i=0; i< list.size(); i++) {
 			table.setValueAt(VariableType.INDEPENDENT, i, 3);
 			comboBox.addItem(list.get(i).getName());
		}
		configuraColunas();
 
		scrollPane.setViewportView(table);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Pilot run control", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(584, 103, 496, 69);
		add(panel);
		
		JLabel label = new JLabel("Number of replications");
		label.setBounds(6, 29, 145, 16);
		panel.add(label);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(163, 23, 66, 28);
		panel.add(textField_4);
		
		JButton btnSimulatePilot = new JButton("Simulate pilot");
		btnSimulatePilot.setBounds(256, 24, 137, 29);
		panel.add(btnSimulatePilot);

	}
	
	public void configuraColunas() {

		modeloColuna = table.getColumnModel();

		TableColumn colunaQueueType = modeloColuna.getColumn(3);
		colunaQueueType.setCellEditor(new DefaultCellEditor(comboBoxVariableType));

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);

		table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

		((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		
	}
	

	public JTable getTable() {
		return table;
	}

	public void setListener(ShowResultsPanel listener) {
		this.showResultsPanel = listener;
	}

	public SimulationManagerFacade getSimulationManagerFacade() {
		return simulationManagerFacade;
	}

	

	

}
