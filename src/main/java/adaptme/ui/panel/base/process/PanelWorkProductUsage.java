package adaptme.ui.panel.base.process;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.eclipse.epf.uma.Process;
import org.eclipse.epf.uma.VariabilityType;
import org.jdesktop.swingx.JXTreeTable;

import adaptme.base.MethodLibraryHash;
import adaptme.ui.components.model.WorkProductUsageTreeTableModel;
import adaptme.ui.components.renderer.TreeTableCellRenderCustom;

public class PanelWorkProductUsage extends JPanel {

    private static final long serialVersionUID = -3425090740329789731L;
    private JScrollPane scrollPane;
    private JXTreeTable treeTable;
    private WorkProductUsageTreeTableModel treeTableModel;

    private Process process;
    private MethodLibraryHash hash;

    public PanelWorkProductUsage(Process process, MethodLibraryHash hash) {

	this.process = process;
	this.hash = hash;

	initComponents();
    }

    private void initComponents() {

	this.scrollPane = new JScrollPane();
	GroupLayout groupLayout = new GroupLayout(this);
	groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(this.scrollPane,
		GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE));
	groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(this.scrollPane,
		GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE));

	treeTable = new JXTreeTable();
	this.treeTable.setColumnSelectionAllowed(true);
	this.treeTable.setCellSelectionEnabled(true);
	Process root = new Process();
	root.setVariabilityType(VariabilityType.NA);
	root.getBreakdownElementOrRoadmap().add(process);
	treeTableModel = new WorkProductUsageTreeTableModel(root, hash);
	this.treeTable.setTreeTableModel(treeTableModel);
	treeTable.setTreeCellRenderer(new TreeTableCellRenderCustom(hash));
	treeTable.setShowGrid(true, true);
	treeTable.setColumnControlVisible(true);
	treeTable.packAll();
	treeTable.setDragEnabled(true);
	scrollPane.setViewportView(treeTable);
	setLayout(groupLayout);
    }

}
