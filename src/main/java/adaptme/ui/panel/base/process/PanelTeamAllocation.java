package adaptme.ui.panel.base.process;

import java.awt.Color;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.eclipse.epf.uma.Process;
import org.eclipse.epf.uma.VariabilityType;
import org.jdesktop.swingx.JXTreeTable;

import adaptme.base.MethodLibraryHash;
import adaptme.ui.components.model.TeamAllocationTreeTableModel;
import adaptme.ui.components.renderer.TreeTableCellRenderCustom;

public class PanelTeamAllocation extends JPanel {

	private static final long serialVersionUID = -5722126812622255488L;
	private Process process;
	private MethodLibraryHash hash;

	private JXTreeTable treeTable;
	private TeamAllocationTreeTableModel treeTableModel;

	public PanelTeamAllocation(Process process, MethodLibraryHash hash) {

		this.process = process;
		this.hash = hash;

		initComponents();
	}

	private void initComponents() {
		setForeground(Color.WHITE);

		JScrollPane scrollPane = new JScrollPane();
		treeTable = new JXTreeTable();
		this.treeTable.setColumnSelectionAllowed(true);
		this.treeTable.setCellSelectionEnabled(true);
		Process root = new Process();
		root.setVariabilityType(VariabilityType.NA);
		root.getBreakdownElementOrRoadmap().add(process);
		treeTableModel = new TeamAllocationTreeTableModel(root, hash);
		this.treeTable.setTreeTableModel(treeTableModel);
		treeTable.setTreeCellRenderer(new TreeTableCellRenderCustom(hash));
		treeTable.setShowGrid(true, true);
		treeTable.setColumnControlVisible(true);
		treeTable.packAll();
		treeTable.setDragEnabled(true);
		scrollPane.setViewportView(treeTable);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(scrollPane,
				Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(scrollPane,
				GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE));
		setLayout(groupLayout);
	}
}
