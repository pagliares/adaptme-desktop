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
import simula.manager.QueueEntry;
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
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class ExperimentationPanel extends JPanel {
	
	
	private JTextField simulationDurationTextField;
	private JTable table;
	private JTextField numberOfReplicationsTextField;
	private TableColumnModel modeloColuna;
	private JComboBox<VariableType> comboBoxVariableType = new JComboBox<>();
	
	private ShowResultsPanel showResultsPanel;
	private SimulationManagerFacade simulationManagerFacade;
	
	private WorkProductResourcesPanel workProductResourcesPanel;
	
	private JTabbedPane tabbedPaneActivity4;
	
	private Map<String, VariableType> mapQueueVariableType = new HashMap<>();

	public ExperimentationPanel(WorkProductResourcesPanel workProductResourcesPanel, JTabbedPane tabbedPaneActivity4, SimulationFacade simulationFacade) {
		this.tabbedPaneActivity4 = tabbedPaneActivity4;
		this.workProductResourcesPanel = workProductResourcesPanel;
		this.simulationManagerFacade = SimulationManagerFacade.getSimulationManagerFacade(); // singleton
		this.simulationManagerFacade.setShowResultsPanel(showResultsPanel);
		this.simulationManagerFacade.setExperimentationPanel(this);;
		
		JPanel welchChartPanel = new WelchChartPanel().createChartPanel();
		welchChartPanel.setBounds(6, 23, 483, 266);
		 
		 
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(null, "Run control", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JLabel lblNumberOfReplications = new JLabel("Number of replications");
		
		numberOfReplicationsTextField = new JTextField();
		numberOfReplicationsTextField.setColumns(10);
		
		JButton btnSimulate = new JButton("Simulate");
		btnSimulate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(numberOfReplicationsTextField.getText().trim().isEmpty() || (Integer.parseInt(numberOfReplicationsTextField.getText()) < 1)){
					JOptionPane.showMessageDialog(getPanel(), "The number of replications is required");
					return; 
				}
				
				if((simulationDurationTextField.getText().trim().isEmpty()) || (Integer.parseInt(simulationDurationTextField.getText()) < 1)){
					JOptionPane.showMessageDialog(getPanel(), "The duration of the simulation is is required when simulation is not finished by lack of temporary entities.");
					return; 
				}
				
				
				
				int numberReplications = Integer.parseInt(numberOfReplicationsTextField.getText());
				int durationTime = Integer.parseInt(simulationDurationTextField.getText());
				
				showResultsPanel.updateShowResultsPanelTable(numberReplications);
				simulationFacade.addNumberOfSimulationRuns(numberReplications);
				float sDuration = Float.parseFloat(simulationDurationTextField.getText());
				simulationManagerFacade.execute(sDuration, numberReplications);  
				
				HashMap queues = simulationManagerFacade.getSimulationManager().getQueues();
				Set keys = queues.keySet();

 				int numberOfLines = table.getRowCount();
				System.out.println(queues.toString());
				 
				
				for (Object o: keys) { 
					QueueEntry qe = (QueueEntry)queues.get(o);
 					
					for (int i=0; i< numberOfLines; i++) {
						VariableType variableType = (VariableType)table.getValueAt(i, 3);
						if (variableType.equals(VariableType.DEPENDENT) && table.getValueAt(i, 4).equals(o)) {
							mapQueueVariableType.put(qe.GetId(), variableType);
						}
					}
					 
				}
				
				tabbedPaneActivity4.setSelectedIndex(2); // show resultsPanel
			 
				
			}
		});
		GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		gl_panel_4.setHorizontalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addGap(155)
					.addComponent(lblNumberOfReplications)
					.addGap(5)
					.addComponent(numberOfReplicationsTextField, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
					.addGap(194)
					.addComponent(btnSimulate)
					.addGap(398))
		);
		gl_panel_4.setVerticalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addGap(11)
					.addComponent(lblNumberOfReplications))
				.addGroup(gl_panel_4.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
						.addComponent(numberOfReplicationsTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSimulate)))
		);
		panel_4.setLayout(gl_panel_4);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Project ending condition", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		JLabel lblDurationTime = new JLabel("Duration time");
		
		simulationDurationTextField = new JTextField();
		simulationDurationTextField.setColumns(10);
		
		JLabel lblWorkProductFinishes = new JLabel("No more temporary entity");
		
		JComboBox comboBox = new JComboBox();
		GroupLayout gl_panel_7 = new GroupLayout(panel_7);
		gl_panel_7.setHorizontalGroup(
			gl_panel_7.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_7.createSequentialGroup()
					.addGap(155)
					.addComponent(lblDurationTime)
					.addGap(12)
					.addComponent(simulationDurationTextField, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
					.addGap(253)
					.addComponent(lblWorkProductFinishes, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE)
					.addGap(77))
		);
		gl_panel_7.setVerticalGroup(
			gl_panel_7.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_7.createSequentialGroup()
					.addGroup(gl_panel_7.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_7.createSequentialGroup()
							.addGap(19)
							.addComponent(lblDurationTime))
						.addGroup(gl_panel_7.createSequentialGroup()
							.addGap(13)
							.addComponent(simulationDurationTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_7.createSequentialGroup()
							.addGap(19)
							.addGroup(gl_panel_7.createParallelGroup(Alignment.BASELINE)
								.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblWorkProductFinishes))))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_7.setLayout(gl_panel_7);
		
		comboBoxVariableType.addItem(VariableType.INDEPENDENT);
		comboBoxVariableType.addItem(VariableType.DEPENDENT);
		comboBoxVariableType.addItem(VariableType.INTERMEDIATE);
		
		JScrollPane scrollPane = new JScrollPane();
		
		table = new JTable();
		
		List<WorkProductXACDML> list = workProductResourcesPanel.getWorkProducts();
		table.setModel(new WorkProductTableModelExperimentation(list));
		
		for (int i=0; i< list.size(); i++) {
 			table.setValueAt(VariableType.INDEPENDENT, i, 3);
 			comboBox.addItem(list.get(i).getName());
		}
		configuraColunas();
 
		scrollPane.setViewportView(table);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(32)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_4, GroupLayout.DEFAULT_SIZE, 1037, Short.MAX_VALUE)
						.addComponent(panel_7, GroupLayout.DEFAULT_SIZE, 1077, Short.MAX_VALUE)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 1077, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(22)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 238, GroupLayout.PREFERRED_SIZE)
					.addGap(12)
					.addComponent(panel_7, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
					.addGap(12)
					.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
					.addGap(15))
		);
		setLayout(groupLayout);

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

	public JPanel getPanel() {
		return this;
	}

	public Map<String, VariableType> getMapQueueVariableType() {
		return mapQueueVariableType;
	}
	

	

}
