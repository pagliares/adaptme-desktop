package adaptme.ui.components.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.eclipse.epf.uma.WorkBreakdownElement;
import org.eclipse.epf.uma.WorkOrder;
import org.eclipse.epf.uma.WorkOrderType;

import adaptme.base.MethodLibraryHash;

public class DependencyTableModel extends AbstractTableModel  {

	private String[] columnNames = {"Index",
            "Presentation name",
            "Dependency"};
	
    private List<WorkOrder> workOrders = new ArrayList<>();

	private MethodLibraryHash hash;
     
    public DependencyTableModel(List<WorkOrder> workOrders, MethodLibraryHash hash) {
        this.hash = hash;
		this.workOrders.addAll(workOrders);
    }
     
    public DependencyTableModel(List<WorkOrder> listPerson) {
    	this.workOrders.addAll(listPerson);
	}

	@Override
    public int getColumnCount() {
        return columnNames.length;
    }
     
    public String getColumnName(int column) {
        return columnNames[column];
    }
     
    public Class getColumnClass(int column) {
        return getValueAt(0, column).getClass();
    }
 
    @Override
    public int getRowCount() {
        return workOrders.size();
    }
 
    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        WorkOrder workOrder = workOrders.get(rowIndex);
         
        if(columnIndex == 2){
        	workOrder.setLinkType((WorkOrderType)value);
        }
    }
     
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        WorkOrder workOrder = workOrders.get(rowIndex);
         
        switch (columnIndex) {
        case 0:
                return rowIndex;
        case 1:
                return ((WorkBreakdownElement)hash.getHashMap().get(workOrder.getValue())).getPresentationName();
        case 2:
                return workOrder.getLinkType();
        }
         
        return null;
    }
 
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 2;
    } 

}
