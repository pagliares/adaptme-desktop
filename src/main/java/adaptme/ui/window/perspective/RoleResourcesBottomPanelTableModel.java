package adaptme.ui.window.perspective;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.base.ActiveObserverType;
import simulator.base.QueueObserverType;
import xacdml.model.generated.ActObserver;
import xacdml.model.generated.QueueObserver;

public class RoleResourcesBottomPanelTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -4739793782253876825L;

	private List<QueueObserver> observers;
	private String[] headerObservers = new String[] {"Name","type"};

	public RoleResourcesBottomPanelTableModel() {
		this.observers = new ArrayList<>();
	}

	@Override
	public int getRowCount() {
		return observers.size();
	}

	@Override
	public int getColumnCount() {
		return headerObservers.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		QueueObserver actObserver = observers.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return actObserver.getName();
		case 1:
			return actObserver.getType();
		default:
			return null;
		}
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		QueueObserver actObserver = observers.get(rowIndex);
		switch (columnIndex) {
		case 0:
			actObserver.setName((String) aValue);
			break;
		case 1:
			QueueObserverType type = (QueueObserverType)aValue;
			actObserver.setType(type.toString());
			break;
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return String.class;
		case 1:
			return QueueObserver.class;
		default:
			return null;
		}
	}
	
	@Override
	public String getColumnName(int col) {
		return headerObservers[col];
	}
	 
	public QueueObserver getObserverAt(int row) {
		return observers.get(row);
	}

	public void removeObserverAt(int row) {
		observers.remove(row);
		fireTableDataChanged();
	}

	public void addQueueObserver(QueueObserver actObserver) {
		observers.add(actObserver);
		fireTableDataChanged();
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return true;
	}

	public int getNumberOfQueueObservers() {
		return observers.size();
	}

	public List<QueueObserver> getObservers() {
		return observers;
	}

}
