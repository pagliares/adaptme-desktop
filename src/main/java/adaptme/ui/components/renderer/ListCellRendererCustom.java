package adaptme.ui.components.renderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;

import adaptme.base.ElementWrapper;

public class ListCellRendererCustom extends JLabel implements ListCellRenderer<ElementWrapper> {

    private static final long serialVersionUID = 1L;
    protected Color m_textSelectionColor;
    protected Color m_textNonSelectionColor;
    protected Color m_bkSelectionColor;
    protected Color m_bkNonSelectionColor;
    protected Color m_borderSelectionColor;

    protected boolean m_selected;

    public ListCellRendererCustom() {
	super();
	m_textSelectionColor = UIManager.getColor("Tree.selectionForeground");
	m_textNonSelectionColor = UIManager.getColor("Tree.textForeground");
	m_bkSelectionColor = UIManager.getColor("Tree.selectionBackground");
	m_bkNonSelectionColor = UIManager.getColor("Tree.textBackground");
	m_borderSelectionColor = UIManager.getColor("Tree.selectionBorderColor");
	setOpaque(false);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends ElementWrapper> list, ElementWrapper value, int index,
	    boolean isSelected, boolean cellHasFocus) {

	setText(value.toString());
	setIcon(value.getIcon());

	setFont(list.getFont());
	setForeground(isSelected ? m_textSelectionColor : m_textNonSelectionColor);
	setBackground(isSelected ? m_bkSelectionColor : m_bkNonSelectionColor);
	m_selected = isSelected;

	return this;

    }

    @Override
    public void paintComponent(Graphics g) {
	Color bColor = getBackground();
	Icon icon = getIcon();

	g.setColor(bColor);
	int offset = 0;
	if (icon != null && getText() != null)
	    offset = (icon.getIconWidth() + getIconTextGap());
	g.fillRect(offset, 0, getWidth() - 1 - offset, getHeight() - 1);

	if (m_selected) {
	    g.setColor(m_borderSelectionColor);
	    g.drawRect(offset, 0, getWidth() - 1 - offset, getHeight() - 1);
	}
	super.paintComponent(g);
    }
}
