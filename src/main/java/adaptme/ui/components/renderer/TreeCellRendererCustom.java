package adaptme.ui.components.renderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;

import adaptme.base.ElementWrapper;

public class TreeCellRendererCustom extends JLabel implements TreeCellRenderer {

    private static final long serialVersionUID = 1L;
    protected Color m_textSelectionColor;
    protected Color m_textNonSelectionColor;
    protected Color m_bkSelectionColor;
    protected Color m_bkNonSelectionColor;
    protected Color m_borderSelectionColor;

    protected boolean m_selected;

    public TreeCellRendererCustom() {
	super();
	m_textSelectionColor = UIManager.getColor("Tree.selectionForeground");
	m_textNonSelectionColor = UIManager.getColor("Tree.textForeground");
	m_bkSelectionColor = UIManager.getColor("Tree.selectionBackground");
	m_bkNonSelectionColor = UIManager.getColor("Tree.textBackground");
	m_borderSelectionColor = UIManager.getColor("Tree.selectionBorderColor");
	setOpaque(false);
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf,
	    int row, boolean hasFocus) {
	DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
	Object obj = node.getUserObject();
	if (obj != null) {

	    if (obj instanceof Boolean)
		setText("Retrieving data...");

	    if (obj instanceof ElementWrapper) {
		ElementWrapper ew = (ElementWrapper) obj;
		setText(ew.toString());
		setIcon(ew.getIcon());
	    }
	    setFont(tree.getFont());
	    setForeground(sel ? m_textSelectionColor : m_textNonSelectionColor);
	    setBackground(sel ? m_bkSelectionColor : m_bkNonSelectionColor);
	    m_selected = sel;
	}
	return this;
    }

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
