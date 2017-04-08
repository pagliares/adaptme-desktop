package adaptme.ui.components.renderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import org.eclipse.epf.uma.WorkOrderType;

public class DependencyCellEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {

	private WorkOrderType country;
	private List<WorkOrderType> listCountry;

	public DependencyCellEditor(List<WorkOrderType> listCountry) {
		this.listCountry = listCountry;
	}

	@Override
	public Object getCellEditorValue() {
		return this.country;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		if (value instanceof WorkOrderType) {
			this.country = (WorkOrderType) value;
		}

		JComboBox<WorkOrderType> comboCountry = new JComboBox<WorkOrderType>();

		for (WorkOrderType aCountry : listCountry) {
			comboCountry.addItem(aCountry);
		}

		comboCountry.setSelectedItem(country);
		comboCountry.addActionListener(this);

		if (isSelected) {
			comboCountry.setBackground(table.getSelectionBackground());
		} else {
		}
			comboCountry.setBackground(Color.WHITE);

		return comboCountry;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		JComboBox<WorkOrderType> comboCountry = (JComboBox<WorkOrderType>) event.getSource();
		this.country = (WorkOrderType) comboCountry.getSelectedItem();
	}

}
