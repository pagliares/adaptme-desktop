package adaptme.ui.panel.base.process;

import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.eclipse.epf.uma.WorkOrder;
import org.eclipse.epf.uma.WorkOrderType;

import adaptme.ui.components.model.DependencyTableModel;
import adaptme.ui.components.renderer.DependencyCellEditor;
import adaptme.ui.components.renderer.DependencyCellRenderer;

public class PanelDependencyTable extends JPanel {
	
    private JTable table = new JTable();
    private DependencyTableModel tableModel;

	public PanelDependencyTable (List<WorkOrder> workOrders) {
         
        List<WorkOrderType> listCountry = new ArrayList<>();
        listCountry.add(WorkOrderType.FINISH_TO_FINISH);
        listCountry.add(WorkOrderType.FINISH_TO_START);
        listCountry.add(WorkOrderType.START_TO_FINISH);
        listCountry.add(WorkOrderType.START_TO_START);
 
        tableModel = new DependencyTableModel(workOrders);
        table.setModel(tableModel);
        table.setDefaultRenderer(WorkOrderType.class, new DependencyCellRenderer());
        table.setDefaultEditor(WorkOrderType.class, new DependencyCellEditor(listCountry));
        table.setRowHeight(25);
         
        JScrollPane scrollpane = new JScrollPane(table);
        GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(
        	groupLayout.createParallelGroup(Alignment.LEADING)
        		.addComponent(scrollpane, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
        );
        groupLayout.setVerticalGroup(
        	groupLayout.createParallelGroup(Alignment.LEADING)
        		.addComponent(scrollpane, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
        setLayout(groupLayout);
	}

	public void setTableModel(DependencyTableModel tableModel) {
		table.setModel(tableModel);
		table.repaint();
		this.repaint();
	}
}
