package adaptme.ui.panel.base.process;

import java.awt.Color;

import javax.swing.DropMode;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.eclipse.epf.uma.Process;
import org.eclipse.epf.uma.VariabilityType;
import org.jdesktop.swingx.JXTreeTable;

import adaptme.base.MethodLibraryHash;
import adaptme.ui.components.model.DynamicWorkBreakdownStructureTreeTableModel;
import adaptme.ui.components.model.MouseAdapterWorkBreakdownStructure;
import adaptme.ui.components.renderer.TreeTableCellRenderCustom;
import adaptme.ui.panel.base.process.dnd.WBSTransferHandler;

public class PanelDynamicWorkBreakdownStructure extends JPanel {

    private static final long serialVersionUID = -8240417585850245711L;

    private Process process;
    private MethodLibraryHash hash;

    private JXTreeTable treeTable;
    private DynamicWorkBreakdownStructureTreeTableModel treeTableModel;

    public PanelDynamicWorkBreakdownStructure(Process process, MethodLibraryHash hash) {

	this.process = process;
	this.hash = hash;

	initComponenents();
    }

    private void initComponenents() {
	setBackground(Color.WHITE);
	JScrollPane scrollPane = new JScrollPane();

	treeTable = new JXTreeTable();
	Process root = new Process();
	root.setVariabilityType(VariabilityType.NA);
	root.getBreakdownElementOrRoadmap().add(process);
	treeTableModel = new DynamicWorkBreakdownStructureTreeTableModel(root);
	treeTable.setTreeTableModel(treeTableModel);
	treeTable.setTreeCellRenderer(new TreeTableCellRenderCustom(hash));
	treeTable.setShowGrid(true, true);
	treeTable.setColumnControlVisible(true);
	treeTable.packAll();
	treeTable.setTransferHandler(new WBSTransferHandler(hash));
	treeTable.setDragEnabled(true);
	treeTable.setDropMode(DropMode.ON_OR_INSERT);
	treeTable.expandAll();
	treeTable.addMouseListener(new MouseAdapterWorkBreakdownStructure(treeTable, null, hash));
	scrollPane.setViewportView(treeTable);

	GroupLayout groupLayout = new GroupLayout(this);
	groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(scrollPane,
		GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE));
	groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(scrollPane,
		Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE));
	setLayout(groupLayout);
    }

    public void save() {

    }
}
