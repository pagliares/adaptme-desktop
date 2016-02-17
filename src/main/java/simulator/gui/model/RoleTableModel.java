package simulator.gui.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;
import simulator.base.QueueType;
import simulator.base.Role;

public class RoleTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -4739793782253876825L;

	private List<Role> roles;
	private String[] headersRole = new String[] { "Name", "Queue type", "Queue size", "Queue initial quantity", "Observe stationary time" };

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
			return role.getQueueType();
		case 2:
			return role.getQueueSize();
 		case 3:
 			return role.getIntialQuantity();
		case 4:
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
			role.setQueueType((QueueType) aValue);
			break;
		case 2:
			role.setQueueSize((int) aValue);
			break;
		case 3:
			role.setIntialQuantity((int) aValue);
			break;
		case 4:
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
			return QueueType.class;
		case 2:
			return Integer.class;
		case 3:
			return Integer.class;
		case 4:
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
		if (column == 0) {
			return false;
		}
		return true;
	}

	public int getNumberOfRoles() {
		return roles.size();
	}

}
