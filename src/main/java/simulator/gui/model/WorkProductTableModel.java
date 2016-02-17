package simulator.gui.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;
import simulator.base.Policy;
import simulator.base.QueueType;
import simulator.base.WorkProduct;

public class WorkProductTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private List<WorkProduct> workProducts;
	
	private String[] headers = new String[] { "Work product", "Demand Work Product?", "Queue type", "Queue name", 
									  "Capacity", "Policy", "Observer queue length name", "Observer queue lenght time name"};

	public WorkProductTableModel(List<WorkProduct> workProducts) {
		this.workProducts = workProducts;
	}

	@Override
	public int getRowCount() {
		return workProducts.size();
	}

	@Override
	public int getColumnCount() {
		return headers.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		WorkProduct workProduct = workProducts.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return workProduct.getName();
		case 1:
			return workProduct.isDemandWorkProduct();
		case 2:
			return workProduct.getQueueType();
		case 3:
			return workProduct.getQueueName();
		case 4:
			return workProduct.getCapacity();
		case 5:
			return workProduct.getPolicy();
		case 6:
			return workProduct.getObserverQueueLenghtName();
		case 7:
			return workProduct.getObserverQueueLenghtTimeName();
		default:
			return null;
		}
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		 
		WorkProduct workProduct = getWorkProductAt(rowIndex);
		switch (columnIndex) {
		case 0:
			workProduct.setName((String) aValue);
			break;
		case 1:
			workProduct.setDemandWorkProduct((boolean) aValue);
			break;
		case 2:
			workProduct.setQueueType((QueueType) aValue); 
			break;
		case 3:
			workProduct.setQueueName((String) aValue); 
			break;
		case 4:
			workProduct.setCapacity((Integer) aValue);
			break;
		case 5:
			workProduct.setPolicy((Policy)aValue); 
			break;
		case 6:
			workProduct.setObserverQueueLenghtName((String) aValue);
			break;
		case 7:
			workProduct.setObserverQueueLenghtTimeName((String) aValue);
			break;
		} 
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return String.class;
		case 1:
			return Boolean.class;
		case 2:
			return QueueType.class;
		case 3:
			return String.class;
		case 4:
			return Integer.class;
		case 5:
			return Policy.class;   
		case 6:
			return String.class;
		case 7:
			return String.class;
		default:
			return null;
		}
	}

	@Override
	public String getColumnName(int col) {
		return headers[col];
	}

	public WorkProduct getWorkProductAt(int row) {
		return workProducts.get(row);
	}

	public void removeDeveloperAt(int row) {
		workProducts.remove(row);
	}

	public void addDeveloper(WorkProduct developer) {
		workProducts.add(developer);
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		if (column == 0) {
			return false;
		}
		return true;
	}

	public List<WorkProduct> getWorkProducts() {
		return workProducts;
	}

}
