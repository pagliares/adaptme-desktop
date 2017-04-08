package adaptme.ui.panel.base.process;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.DropMode;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.eclipse.epf.uma.Process;
import org.eclipse.epf.uma.VariabilityType;
import org.eclipse.epf.uma.WorkBreakdownElement;
import org.jdesktop.swingx.JXTreeTable;

import adaptme.base.MethodLibraryHash;
import adaptme.ui.components.model.DependencyTableModel;
import adaptme.ui.components.model.MouseAdapterWorkBreakdownStructure;
import adaptme.ui.components.model.SPEMWorkBreakdownStructureTreeTableModel;
import adaptme.ui.components.renderer.TreeTableCellRenderCustom;
import adaptme.ui.panel.base.process.dnd.WBSTransferHandler;

public class PanelSPEMWorkBreakdownStructure extends JPanel {

	private static final long serialVersionUID = -8240417585850245711L;

	private Process process;
	private MethodLibraryHash hash;

	private JXTreeTable treeTable;
	private SPEMWorkBreakdownStructureTreeTableModel treeTableModel;
	private PanelDependencyTable panelDependencyTable;

	public PanelSPEMWorkBreakdownStructure(Process process, MethodLibraryHash hash) {

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
		
		panelDependencyTable = new PanelDependencyTable(new ArrayList<>());
		
		treeTableModel = new SPEMWorkBreakdownStructureTreeTableModel(this, root, hash);
		treeTable.setTreeTableModel(treeTableModel);
		treeTable.setTreeCellRenderer(new TreeTableCellRenderCustom(hash));
		treeTable.setShowGrid(true, true);
		treeTable.setColumnControlVisible(true);
		treeTable.packAll();
		treeTable.setTransferHandler(new WBSTransferHandler(hash));
		treeTable.setDragEnabled(true);
		treeTable.setDropMode(DropMode.ON_OR_INSERT);
		treeTable.expandAll();
		treeTable.addMouseListener(new MouseAdapterWorkBreakdownStructure(this, treeTable, treeTableModel, hash));
		scrollPane.setViewportView(treeTable);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
						.addComponent(panelDependencyTable, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE))
					.addGap(2))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
					.addGap(1)
					.addComponent(panelDependencyTable, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE)
					.addGap(3))
		);
		setLayout(groupLayout);
	}

	public void save() {

	}


	public void updateDependencyModel(WorkBreakdownElement element) {
		if(element == null){
			panelDependencyTable.setTableModel(new DependencyTableModel(new ArrayList<>()));
			return;
		}
		panelDependencyTable.setTableModel(new DependencyTableModel(((WorkBreakdownElement) element).getPredecessor(), hash));		
	}
}
