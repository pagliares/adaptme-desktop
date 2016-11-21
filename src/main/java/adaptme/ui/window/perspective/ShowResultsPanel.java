package adaptme.ui.window.perspective;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;

import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

import adaptme.IDynamicExperimentationProgramProxy;
import adaptme.facade.SimulationManagerFacade;
import adaptme.ui.window.perspective.pane.AlternativeOfProcessPanel;
import model.spem.ProcessRepository;
import model.spem.SimulationFacade;
import simula.manager.QueueEntry;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;

public class ShowResultsPanel extends JPanel {
	
	private int indexSelectedRow;
	private JTextArea textArea;
	private JTable table;
	private ShowResultsTableModel showResultsTableModel;
	private ExperimentationPanel experimentationPanel;
	private AlternativeOfProcessPanel alternativeOfProcessPanel;
	private SimulationFacade simulationFacade;
	private SimulationManagerFacade simulationManagerFacade;
	private SortedMap resultadoGlobal = new TreeMap(); // hoje, 16 nov, 11:41 veja comentarios abaixo
	 
	public ShowResultsPanel(ExperimentationPanel experimentationPanel, SimulationFacade simulationFacade, AlternativeOfProcessPanel alternativeOfProcessPanel) {
		this.experimentationPanel =  experimentationPanel;
		this.simulationFacade = simulationFacade;
		this.alternativeOfProcessPanel = alternativeOfProcessPanel;
		
		this.simulationManagerFacade = SimulationManagerFacade.getSimulationManagerFacade(); // singleton
		this.simulationManagerFacade.setShowResultsPanel(this);
		this.simulationManagerFacade.setSimulationFacade(simulationFacade);
		
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
		showResultsTableModel = new ShowResultsTableModel(simulationFacade);
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
 		
 		table.changeSelection(0, 0, false, false); // seleciona a primeira linha da tabela por default
 		
 		configuraTableListener();
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(16, 185, 1096, 504);
		add(scrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		JButton btnShowResults = new JButton("Show results");
		btnShowResults.setBounds(1212, 368, 117, 29);
		add(btnShowResults);
		
		JButton btnSimulateAnotherAlternative = new JButton("Simulate another alternative of process");
		btnSimulateAnotherAlternative.setBounds(1114, 468, 290, 29);
		add(btnSimulateAnotherAlternative);
		
		JButton btnClear = new JButton("Clear ");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
			}
		});
		btnClear.setBounds(1212, 415, 117, 29);
		add(btnClear);
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
		
		btnShowResults.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				
				String resultadoGlobalCabecalho = simulationManagerFacade.getResultadosCabecalho();
				textArea.append(resultadoGlobalCabecalho);
 				String resultadoGlobalString = simulationManagerFacade.getResultadosGlobalString(experimentationPanel.getMapQueueVariableType());
				textArea.append(resultadoGlobalString); 
			
			}
		});
	}
	
	public void configuraTableListener() {

		// Listener disparado ao selecionar uma linha da tabela
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent event) {
				indexSelectedRow = table.getSelectedRow();

				if ((indexSelectedRow > -1)) {
					String processAlternativeName = (String) table.getValueAt(indexSelectedRow, 0);
					Map<String, String> resultsSimulationMapAdaptMe = simulationManagerFacade.getResultsSimulationMapAdaptMe();
					System.out.println(resultsSimulationMapAdaptMe);
					System.out.println(resultsSimulationMapAdaptMe);

					textArea.setText("");
					textArea.append(processAlternativeName);
					textArea.append(resultsSimulationMapAdaptMe.get(processAlternativeName+"0"));

					// Map<String, IDynamicExperimentationProgramProxy> resultsSimulationMap = simulationManagerFacade.getResultsSimulationMap();
					//	IDynamicExperimentationProgramProxy experimentationProgramProxy = resultsSimulationMap.get(processAlternativeName+0);
					//	textArea.append(processAlternativeName);
					//  textArea.append(simulationManagerFacade.getSimulationResults());
				}
			}
		});
	}
	
	public void updateShowResultsPanelTable(int numberReplications) {
		int indexProcessAlternative = simulationFacade.getProcessAlternatives().size() - 1;
		showResultsTableModel.setValueAt(numberReplications, indexProcessAlternative, 2); // storing the number of replications
 	}
	
	public String getSelectedProcessAlternativeName() {
//		ProcessRepository pr = showResultsTableModel.getProcessAlternativeAt(indexSelectedRow);
//		String selectedProcessAlternativeName = pr.getName();
//		return selectedProcessAlternativeName;
		return "teste";
	}
}
