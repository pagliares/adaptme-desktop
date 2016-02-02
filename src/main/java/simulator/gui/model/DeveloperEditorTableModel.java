package simulator.gui.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.base.Developer;
import simulator.base.Role;
import simulator.base.WorkProduct;

public class DeveloperEditorTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -4739793782253876825L;

	private List<Developer> developers;

	String[] headers = new String[] { "Name", "Skill", "Max Skill", "Experience", "Initial Velocity" };

	public DeveloperEditorTableModel(List<Developer> developers) {
		this.developers = developers;
	}

	@Override
	public int getRowCount() {
		return developers.size();
	}

	@Override
	public int getColumnCount() {
		return 5;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Developer developer = developers.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return developer.getName();
		case 1:
			return developer.getSkill();
		case 2:
			return developer.getMaxSkill();
		case 3:
			return developer.getExperience();
		case 4:
			return developer.getCurrentVelocity();
		default:
			return null;
		}
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Developer developer = getDeveloperAt(rowIndex);
		switch (columnIndex) {
		case 0:
			developer.setName((String) aValue);
			break;
		case 1:
			developer.setSkill((double) aValue);
			break;
		case 2:
			developer.setMaxSkill((double) aValue);
			break;
		case 3:
			developer.setExperience((double) aValue);
			break;
		case 4:
			developer.setInitialVelocity((double) aValue);
			break;
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return String.class;
		case 1:
			return Double.class;
		case 2:
			return Double.class;
		case 3:
			return Double.class;
		case 4:
			return Double.class;
		default:
			return null;
		}
	}

	@Override
	public String getColumnName(int col) {
		return headers[col];
	}

	public Developer getDeveloperAt(int row) {
		return developers.get(row);
	}

	public void removeDeveloperAt(int row) {
		developers.remove(row);
	}

	public void addDeveloper(Developer developer) {
		developers.add(developer);
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return true;
	}

	public int getNumberOfDevelopers() {
		return developers.size();
	}

}
