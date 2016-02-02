package simulator.gui.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.base.WorkProduct;

public class WorkProductTableModel extends AbstractTableModel {

	private List<WorkProduct> workProducts;
	String[] headers = new String[] { "Work product", "Demand Work Product", "Quantity", "Queue name", "Capacity", "Policy", 
			                          "Observer queue length name", "Observer queue lenght time name"};

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
			return workProduct.getSize();
		case 3:
			return "Queue name";
		case 4:
			return 0;
		case 5:
			return "FIFO";
		case 6:
			return "Queue name";
		case 7:
			return 0;
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
			workProduct.setSize((int) aValue);
			break;
		case 3:
			 
			break;
		case 4:
			 
			break;
		case 5:
			 
			break;
		case 6:
			 
			break;
		case 7:
			 
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
			return Integer.class;
		case 3:
			return String.class;
		case 4:
			return String.class;
		case 5:
			return String.class;  // nao se e isso para combobox
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

	public int getNumberOfDevelopers() {
		return workProducts.size();
	}

}
