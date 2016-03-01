package adaptme.ui.window.perspective;

import java.util.List;

import javax.swing.JPanel;
import javax.swing.table.AbstractTableModel;

import simulator.base.Policy;
import simulator.base.QueueType;
import simulator.base.WorkProduct;

public class WorkProductTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private List<WorkProduct> workProducts;
	private List<JPanel> listOfWorkProductResourcesBottomRightPanels;
	
	private String[] headers = new String[] { "Work product", "Queue type", "Queue name", 
									  "Queue size", "Queue initial quantity", "Policy"};

	public WorkProductTableModel(List<WorkProduct> workProducts, List<JPanel> listOfWorkProductResourcesBottomRightPanels) {
		this.workProducts = workProducts;
		this.listOfWorkProductResourcesBottomRightPanels = listOfWorkProductResourcesBottomRightPanels;
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
			return workProduct.getQueueType();
		case 2:
			return workProduct.getQueueName();
		case 3:
			return workProduct.getCapacity();
		case 4:
			return workProduct.getIntialQuantity();
		case 5:
			return workProduct.getPolicy();
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
			workProduct.setQueueType((QueueType) aValue); 
			break;
		case 2:
			workProduct.setQueueName((String) aValue); 
			WorkProductResourcesBottomRightPanel workProductResourcesBottomRightPanel;
			workProductResourcesBottomRightPanel = (WorkProductResourcesBottomRightPanel)listOfWorkProductResourcesBottomRightPanels.get(rowIndex);
			workProductResourcesBottomRightPanel.setQueueNameTextField((String) aValue);
			break;
		case 3:
			workProduct.setCapacity((Integer) aValue);
			break;
		case 4:
			workProduct.setIntialQuantity((Integer) aValue); 
			break;
		case 5:
			workProduct.setPolicy((Policy)aValue); 
			break;
		} 
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return String.class;
		case 1:
			return QueueType.class;
		case 2:
			return String.class;
		case 3:
			return Integer.class;
		case 4:
			return Integer.class;   
		case 5:
			return Policy.class; 
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
