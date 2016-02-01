package simulator.gui.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.base.WorkProduct;

public class WorkProductTableModel extends AbstractTableModel {

	private List<WorkProduct> workProducts;
	String[] headers = new String[] { "Work product", "Demand Work Product", "Quantity" };

	public WorkProductTableModel(List<WorkProduct> workProducts) {
		this.workProducts = workProducts;
	}

	@Override
	public int getRowCount() {
		return workProducts.size();
	}

	@Override
	public int getColumnCount() {
		return 3;
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
