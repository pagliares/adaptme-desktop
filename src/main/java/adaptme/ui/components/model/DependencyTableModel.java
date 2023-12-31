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

	private SPEMWorkBreakdownStructureTreeTableModel spemWorkBreakdownStructureTreeTableModel;
     
    public DependencyTableModel(List<WorkOrder> workOrders, MethodLibraryHash hash, SPEMWorkBreakdownStructureTreeTableModel spemWorkBreakdownStructureTreeTableModel) {
        this.hash = hash;
		this.spemWorkBreakdownStructureTreeTableModel = spemWorkBreakdownStructureTreeTableModel;
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
        WorkBreakdownElement element = (WorkBreakdownElement) hash.getHashMap().get(workOrder.getValue());
        switch (columnIndex) {
        case 0:
                return spemWorkBreakdownStructureTreeTableModel.getIndexList().indexOf(element);
        case 1:
                return element.getPresentationName();
        case 2:
                return workOrder.getLinkType();
        }
         
        return null;
    }
 
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 2;
    } 

}
