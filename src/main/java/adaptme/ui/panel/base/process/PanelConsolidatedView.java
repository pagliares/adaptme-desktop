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
import adaptme.ui.components.model.ConsolidatedViewTreeTableModel;
import adaptme.ui.components.renderer.TreeTableCellRenderCustom;

public class PanelConsolidatedView extends JPanel {

	private static final long serialVersionUID = 5134455561993050691L;
	private JScrollPane scrollPane;
	private JXTreeTable treeTable;
	private ConsolidatedViewTreeTableModel treeTableModel;

	private Process process;
	private MethodLibraryHash hash;

	public PanelConsolidatedView(Process process, MethodLibraryHash hash) {
		this.process = process;
		this.hash = hash;

		initComponents();
	}

	private void initComponents() {
		setBackground(Color.WHITE);

		this.scrollPane = new JScrollPane();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(this.scrollPane,
				Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(this.scrollPane,
				GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE));

		treeTable = new JXTreeTable();
		Process root = new Process();
		root.setVariabilityType(VariabilityType.NA);
		root.getBreakdownElementOrRoadmap().add(process);
		treeTableModel = new ConsolidatedViewTreeTableModel(root, hash);
		treeTable.setTreeTableModel(treeTableModel);
		treeTable.setTreeCellRenderer(new TreeTableCellRenderCustom(hash));
		treeTable.setShowGrid(true, true);
		treeTable.setColumnControlVisible(true);
		treeTable.packAll();
		treeTable.setDragEnabled(true);
		this.scrollPane.setViewportView(this.treeTable);
		setLayout(groupLayout);
	}
}
