package adaptme.ui.window.perspective;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.table.AbstractTableModel;

import model.spem.ProcessRepository;
import simulator.base.Policy;
import simulator.base.QueueType;
import simulator.base.WorkProductXACDML;

public class ShowResultsTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private List<ProcessRepository> listOfProcessAlternatives = new ArrayList<>();
	
	private List<String> headers;
	
	public ShowResultsTableModel() {
		headers = new ArrayList<>();
		headers.add("Process alternative name");
		headers.add("Project duration");
	}
	 

	@Override
	public int getRowCount() {
		return listOfProcessAlternatives.size();
	}

	@Override
	public int getColumnCount() {
		return headers.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ProcessRepository processRepository = listOfProcessAlternatives.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return listOfProcessAlternatives.get(rowIndex).getName();
		case 1:
			return " ";
		case 2:
			return " ";
		case 3:
			return " ";
		case 4:
			return " ";
		case 5:
			return " ";
		default:
			return null;
		}
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		 
		ProcessRepository processRepository = getProcessAlternativeAt(rowIndex);
		switch (columnIndex) {
		case 0:
			processRepository.setName((String) aValue);
			break;
		case 1:
			 
			break;
		case 2:
			 
			break;
		case 3:
			 
			break;
		case 4:
			 
			break;
		case 5:
			 
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
			return String.class;
		case 4:
			return String.class;   
		case 5:
			return String.class; 
		default:
			return null;
		}
	}

	@Override
	public String getColumnName(int col) {
		return headers.get(col);
	}
	
	public void addColumn(String column) {
		headers.add(column);
		fireTableStructureChanged();
	}

	public ProcessRepository getProcessAlternativeAt(int row) {
		return listOfProcessAlternatives.get(row);
	}

	 
	@Override
	public boolean isCellEditable(int row, int column) {
		if (column == 0) {
			return false;
		}
		return true;
	}
	
	public void addProcessAlternative(ProcessRepository processRepository) {
		this.listOfProcessAlternatives.add(processRepository);
	}
	
	public boolean removeAllColumns() {
		headers.clear();
		headers.add("Process alternative name");
		headers.add("Project duration");
		fireTableStructureChanged();
		return true;
	}


	public List<ProcessRepository> getListOfProcessAlternatives() {
		return listOfProcessAlternatives;
	}


	public void setListOfProcessAlternatives(List<ProcessRepository> listOfProcessAlternatives) {
		this.listOfProcessAlternatives = listOfProcessAlternatives;
	}

	 

}
