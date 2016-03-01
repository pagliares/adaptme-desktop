package simulator.gui;

import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import adaptme.ui.window.perspective.DeveloperEditorTableModel;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import simulator.base.Developer;

public class PanelDeveloperEditor extends JPanel {

    private static final long serialVersionUID = -2353540586656170132L;
    private JTable table;

    public PanelDeveloperEditor(List<Developer> developers) {

	JButton btnAddDeveloper = new JButton("Add Developer");
	btnAddDeveloper.addActionListener(e -> {
	    DeveloperEditorTableModel model = (DeveloperEditorTableModel) table.getModel();
	    model.addDeveloper(new Developer("New Developer", 0, 0, 0));
	    table.updateUI();
	});

	JButton btnRemoveDeveloper = new JButton("Remove Developer");
	btnRemoveDeveloper.addActionListener(e -> {
	    int row = table.getSelectedRow();
	    if (row == -1) {
		return;
	    }
	    DeveloperEditorTableModel model = (DeveloperEditorTableModel) table.getModel();
	    model.removeDeveloperAt(row);
	    table.updateUI();
	});
	DeveloperEditorTableModel model = new DeveloperEditorTableModel(developers);
	table = new JTable(model);
	JScrollPane scrollPane = new JScrollPane();
	scrollPane.setViewportView(table);
	table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	GroupLayout groupLayout = new GroupLayout(this);
	groupLayout
		.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(btnAddDeveloper, GroupLayout.PREFERRED_SIZE, 130,
						GroupLayout.PREFERRED_SIZE)
					.addGap(18).addComponent(btnRemoveDeveloper)));
	groupLayout
		.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(
					groupLayout.createSequentialGroup()
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 247,
							Short.MAX_VALUE)
						.addGap(18)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(btnAddDeveloper).addComponent(btnRemoveDeveloper))
			.addGap(14)));
	table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	setLayout(groupLayout);

    }

    public int numberOfDevelopers() {
	return ((DeveloperEditorTableModel) table.getModel()).getNumberOfDevelopers();
    }
}
