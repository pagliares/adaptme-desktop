package adaptme.ui.window.perspective;


import java.util.List;

import javax.swing.JPanel;
import javax.swing.table.AbstractTableModel;

import simulator.base.WorkProductXACDML;

 

public class WorkProductTableModelExperimentation extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private List<WorkProductXACDML> workProducts;
 	
	private String[] headers = new String[] { "Work product", "Input/output","Task name", "Variable type"};

	public WorkProductTableModelExperimentation(List<WorkProductXACDML> workProducts) {
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
		WorkProductXACDML workProduct = workProducts.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return workProduct.getName();
		case 1:
			return workProduct.getInputOrOutput();
		case 2:
			return workProduct.getTaskName();
		case 3:
			return workProduct.getVariableType();
		
		default:
			return null;
		}
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		 
		WorkProductXACDML workProduct = getWorkProductAt(rowIndex);
		switch (columnIndex) {
		case 0:
			workProduct.setName((String) aValue);
			break;
		case 1:
			workProduct.setInputOrOutput((String) aValue);  
			break;
		case 2:
			workProduct.setTaskName((String) aValue);  
			break;
		case 3:
			workProduct.setVariableType((VariableType) aValue);  
			break;
		
		} 
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return String.class;
		case 1:
			return String.class;
		case 2:
			return String.class;
		case 3:
			return VariableType.class;
		
		default:
			return null;
		}
	}

	@Override
	public String getColumnName(int col) {
		return headers[col];
	}

	public WorkProductXACDML getWorkProductAt(int row) {
		return workProducts.get(row);
	}

	public void removeDeveloperAt(int row) {
		workProducts.remove(row);
	}

	public void addDeveloper(WorkProductXACDML developer) {
		workProducts.add(developer);
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		if ((column == 0) || (column == 1) || (column == 2)){
			return false;
		}
		return true;
	}

	public List<WorkProductXACDML> getWorkProducts() {
		return workProducts;
	}

}
