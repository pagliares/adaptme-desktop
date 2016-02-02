package simulator.gui.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.base.Developer;
import simulator.base.Role;
import simulator.base.WorkProduct;

public class RoleTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -4739793782253876825L;

	private List<Role> roles;

	String[] headersRole = new String[] { "Name", "InitialQuantity", "Observe Stationary Time" };

	public RoleTableModel(List<Role> roles) {
		this.roles = roles;
	}

	@Override
	public int getRowCount() {
		return roles.size();
	}

	@Override
	public int getColumnCount() {
		return headersRole.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Role role = roles.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return role.getName();
		case 1:
			return role.getIntialQuantity();
		case 2:
			return role.isObserveStationaryTime();

		default:
			return null;
		}
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Role role = getRoleAt(rowIndex);
		switch (columnIndex) {
		case 0:
			role.setName((String) aValue);
			break;
		case 1:
			role.setIntialQuantity((String) aValue);
			break;
		case 2:
			role.setObserveStationaryTime((boolean) aValue);
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
			return Boolean.class;

		default:
			return null;
		}
	}
	

	@Override
	public String getColumnName(int col) {
		return headersRole[col];
	}
	 

	public Role getRoleAt(int row) {
		return roles.get(row);
	}

	 

	public void removeRoleAt(int row) {
		roles.remove(row);
	}

	public void addRole(Role role) {
		roles.add(role);
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return true;
	}

	public int getNumberOfRoles() {
		return roles.size();
	}

	 
}
