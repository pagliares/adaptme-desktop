package adaptme.ui.window.perspective;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;

import adaptme.facade.SimulationManagerFacade;
import adaptme.ui.window.perspective.pane.AlternativeOfProcessPanel;
import model.spem.ProcessRepository;
import model.spem.SimulationFacade;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;

public class ShowResultsPanel extends JPanel {
	
	private JTable table;
	private ShowResultsTableModel showResultsTableModel;
	private ExperimentationPanel experimentationPanel;
	private AlternativeOfProcessPanel alternativeOfProcessPanel;
	private SimulationFacade simulationFacade;
	 
	public ShowResultsPanel(ExperimentationPanel experimentationPanel, SimulationFacade simulationFacade, AlternativeOfProcessPanel alternativeOfProcessPanel) {
		this.experimentationPanel =  experimentationPanel;
		this.simulationFacade = simulationFacade;
		this.alternativeOfProcessPanel = alternativeOfProcessPanel;
		
		experimentationPanel.setListener(this);
		
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Process alternatives results", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(6, 6, 1430, 167);
		add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPaneTableResults = new JScrollPane();
		scrollPaneTableResults.setBounds(6, 32, 1404, 129);
		panel.add(scrollPaneTableResults);
		
		table = new JTable();
		showResultsTableModel = new ShowResultsTableModel();
//		showResultsTableModel.addProcessAlternative(processRepository);
		showResultsTableModel.setListOfProcessAlternatives(simulationFacade.getProcessAlternatives());
		
		JTable variableTypeTable = experimentationPanel.getTable();
		int numberOfLines = variableTypeTable.getRowCount();
		
		for (int i=0; i< numberOfLines; i++) {
			VariableType variableType = (VariableType)variableTypeTable.getValueAt(i, 3);
			if (variableType.equals(VariableType.DEPENDENT)) {
				showResultsTableModel.addColumn((String)variableTypeTable.getValueAt(i, 0));
			}
		}
		
		table.setModel(showResultsTableModel);
 		scrollPaneTableResults.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(6, 427, 1049, 91);
		add(panel_1);
		panel_1.setLayout(null);
		
		JButton btnSimulateAnotherAlternative = new JButton("Simulate another alternative of process");
		btnSimulateAnotherAlternative.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Process p = Runtime.getRuntime().exec("cp skeleton/DynamicExperimentationProgramProxy.java src/main/java/adaptme");
					alternativeOfProcessPanel.closeTabbedPane();
				} 

				catch (IOException e1) {
//					JOptionPane.showMessageDialog(this, "Place a stub empty ExperimentationProgramProxy.java in the folder xacdml_models"); 
					e1.printStackTrace();
				}
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(16, 185, 1039, 230);
		add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		btnSimulateAnotherAlternative.setBounds(690, 31, 290, 29);
		panel_1.add(btnSimulateAnotherAlternative);
		
		JButton btnShowResults = new JButton("Show results");
		btnShowResults.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimulationManagerFacade simulationManagerFacade = experimentationPanel.getFacade();
				textArea.setText(simulationManagerFacade.getSimulationResults());
			}
		});
		
		btnShowResults.setBounds(382, 31, 117, 29);
		panel_1.add(btnShowResults);
		
		
		

	}
	
	public void updateShowResultsPanelTable() {
		 
		JTable variableTypeTable = experimentationPanel.getTable();
		int numberOfLines = variableTypeTable.getRowCount();
		System.out.println(showResultsTableModel.removeAllColumns());
		
		for (int i=0; i< numberOfLines; i++) {
			VariableType variableType = (VariableType)variableTypeTable.getValueAt(i, 3);
			if (variableType.equals(VariableType.DEPENDENT)) {
				showResultsTableModel.addColumn((String)variableTypeTable.getValueAt(i, 0));
			}
		}
 	}
}
