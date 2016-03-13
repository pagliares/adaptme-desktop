package adaptme.ui.window.perspective;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTable;

public class ShowResultsPanel extends JPanel {
	private JTable table;
	private ShowResultsTableModel showResultsTableModel;

	/**
	 * Create the panel.
	 */
	public ShowResultsPanel() {
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Process alternatives results", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(6, 6, 945, 167);
		add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPaneTableResults = new JScrollPane();
		scrollPaneTableResults.setBounds(0, 32, 939, 129);
		panel.add(scrollPaneTableResults);
		
		table = new JTable();
		showResultsTableModel = new ShowResultsTableModel();
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
}
