package adaptme.ui.components.renderer;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;

import org.eclipse.epf.uma.WorkOrderType;

public class DependencyCellRenderer extends DefaultTableCellRenderer {
    
	protected Color m_textSelectionColor;
	protected Color m_textNonSelectionColor;
	protected Color m_bkSelectionColor;
	protected Color m_bkNonSelectionColor;
	protected Color m_borderSelectionColor;
	
	public DependencyCellRenderer() {
		m_textSelectionColor = UIManager.getColor("Tree.selectionForeground");
		m_textNonSelectionColor = UIManager.getColor("Tree.textForeground");
		m_bkSelectionColor = UIManager.getColor("Tree.selectionBackground");
		m_bkNonSelectionColor = UIManager.getColor("Tree.textBackground");
		m_borderSelectionColor = UIManager.getColor("Tree.selectionBorderColor");
		setOpaque(false);
	}
	
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof WorkOrderType) {
        	WorkOrderType country = (WorkOrderType) value;
            setText(country.name());
        }
         
		setFont(table.getFont());
		setForeground(isSelected ? m_textSelectionColor : m_textNonSelectionColor);
		setBackground(isSelected ? m_bkSelectionColor : row % 2 != 0 ? new Color(242, 242, 242) : m_bkNonSelectionColor);

        return this;
    }
     
}