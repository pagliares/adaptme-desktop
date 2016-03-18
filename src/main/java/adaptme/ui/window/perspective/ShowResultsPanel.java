package adaptme.ui.window.perspective;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTable;

public class ShowResultsPanel extends JPanel {
	
	private JTable table;
	private ShowResultsTableModel showResultsTableModel;
	private ExperimentationPanel experimentationPanel;
	 
	public ShowResultsPanel(ExperimentationPanel experimentationPanel) {
		this.experimentationPanel =  experimentationPanel;
		
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
		
		JTable variableTypeTable = experimentationPanel.getTable();
		int numberOfLines = variableTypeTable.getRowCount();
		
		for (int i=0; i< numberOfLines; i++) {
			VariableType variableType = (VariableType)variableTypeTable.getValueAt(i, 1);
			if (variableType.equals(VariableType.DEPENDENT)) {
				showResultsTableModel.addColumn((String)variableTypeTable.getValueAt(i, 0));
			}
		}
		
		table.setModel(showResultsTableModel);
 		scrollPaneTableResults.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(6, 186, 945, 91);
		add(panel_1);
		panel_1.setLayout(null);
		
		JButton btnSimulateAnotherAlternative = new JButton("Simulate another alternative of process");
		btnSimulateAnotherAlternative.setBounds(195, 31, 290, 29);
		panel_1.add(btnSimulateAnotherAlternative);

	}
	
	public void updateShowResultsPanelTable() {
		 
		JTable variableTypeTable = experimentationPanel.getTable();
		int numberOfLines = variableTypeTable.getRowCount();
		System.out.println(showResultsTableModel.removeAllColumns());
		
		for (int i=0; i< numberOfLines; i++) {
			VariableType variableType = (VariableType)variableTypeTable.getValueAt(i, 1);
			if (variableType.equals(VariableType.DEPENDENT)) {
				showResultsTableModel.addColumn((String)variableTypeTable.getValueAt(i, 0));
			}
		}
 	}
}
