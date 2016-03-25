package adaptme.ui.window.perspective;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.table.AbstractTableModel;

import simulator.base.Policy;
import simulator.base.QueueType;
import simulator.base.WorkProduct;

public class WorkProductTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private List<WorkProduct> workProducts;
	private Set<WorkProduct> workProductsSet;
	private List<JPanel> listOfQueueWorkProductResourcesObserversPanel;
	private List<JPanel> listOfGenerateActivityWorkProductResourcesObserversPanel;
	
	private String[] headers = new String[] { "Work product", "Input/output","Queue name", "Queue type", 
									  "Queue size", "Queue initial quantity", "Policy", "Generate activity?"};

	public WorkProductTableModel(List<WorkProduct> workProducts, List<JPanel> listOfWorkProductResourcesBottomRightPanels, 
			List<JPanel> listOfGenerateActivityWorkProductResourcesObserversPanel) {
		this.workProducts = workProducts;
		this.listOfQueueWorkProductResourcesObserversPanel = listOfWorkProductResourcesBottomRightPanels;
		this.listOfGenerateActivityWorkProductResourcesObserversPanel = listOfGenerateActivityWorkProductResourcesObserversPanel;
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
			return workProduct.getInputOrOutput();
		case 2:
			return workProduct.getQueueName();
		case 3:
			return workProduct.getQueueType();
		case 4:
			return workProduct.getCapacity();
		case 5:
			return workProduct.getIntialQuantity();
		case 6:
			return workProduct.getPolicy();
		case 7:
			return workProduct.isGenerateActivity();
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
			workProduct.setInputOrOutput((String) aValue);
			break;
		case 2:
			workProduct.setQueueName((String) aValue); 
			WorkProductResourcesObserversPanel workProductResourcesBottomRightPanel;
			workProductResourcesBottomRightPanel = (WorkProductResourcesObserversPanel)listOfQueueWorkProductResourcesObserversPanel.get(rowIndex);
			workProductResourcesBottomRightPanel.setQueueNameTextField((String) aValue);
			
			workProductResourcesBottomRightPanel = (WorkProductResourcesObserversPanel)listOfGenerateActivityWorkProductResourcesObserversPanel.get(rowIndex);
			workProductResourcesBottomRightPanel.setQueueNameTextField((String) aValue);
			break;
		case 3:
			workProduct.setQueueType((QueueType) aValue); 
			break;
		case 4:
			workProduct.setCapacity((Integer) aValue);
			break;
		case 5:
			workProduct.setIntialQuantity((Integer) aValue); 
			break;
		case 6:
			workProduct.setPolicy((Policy)aValue); 
			break;
		case 7:
			workProduct.setGenerateActivity((Boolean)aValue); 
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
			return QueueType.class;
		case 4:
			return Integer.class;
		case 5:
			return Integer.class;   
		case 6:
			return Policy.class; 
		case 7:
			return Boolean.class;
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
		if ((column == 0) || (column == 1) || (column == 2)){
			return false;
		}
		return true;
	}

	public List<WorkProduct> getWorkProducts() {
		return workProducts;
	}

}
