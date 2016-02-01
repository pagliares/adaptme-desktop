package adaptme.dynamic.gui.renderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import adaptme.util.EPFConstants;
import model.spem.ProcessContentRepository;
import model.spem.util.ProcessContentType;

public class TreeTableCellRenderSimpleUMA extends DefaultTreeCellRenderer {

    private static final long serialVersionUID = 1L;
    protected Color m_textSelectionColor;
    protected Color m_textNonSelectionColor;
    protected Color m_bkSelectionColor;
    protected Color m_bkNonSelectionColor;
    protected Color m_borderSelectionColor;

    protected boolean m_selected;

    public TreeTableCellRenderSimpleUMA() {
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
	Object userObject = node.getUserObject();
	if (userObject instanceof String) {
	    setText((String) userObject);
	    setIcon(EPFConstants.deliveryProcessIcon);
	} else {
	    ProcessContentRepository content = (ProcessContentRepository) userObject;
	    setText(content.getName());
	    if (content.getType() == ProcessContentType.ACTIVITY) {
		setIcon(EPFConstants.activityIcon);
	    } else if (content.getType() == ProcessContentType.ITERATION) {
		setIcon(EPFConstants.iterationIcon);
	    } else if (content.getType() == ProcessContentType.MILESTONE) {
		setIcon(EPFConstants.milestoneIcon);
	    } else if (content.getType() == ProcessContentType.PHASE) {
		setIcon(EPFConstants.phaseIcon);
	    } else if (content.getType() == ProcessContentType.TASK) {
		setIcon(EPFConstants.taskDescriptorIcon);
	    }
	}

	setFont(tree.getFont());
	setForeground(sel ? m_textSelectionColor : m_textNonSelectionColor);
	setBackground(sel ? m_bkSelectionColor : m_bkNonSelectionColor);
	m_selected = sel;
	return this;
    }

    @Override
    public void paintComponent(Graphics g) {
	Color bColor = getBackground();
	Icon icon = getIcon();

	g.setColor(bColor);
	int offset = 0;
	if (icon != null && getText() != null) {
	    offset = (icon.getIconWidth() + getIconTextGap());
	}
	g.fillRect(offset, 0, getWidth() - 1 - offset, getHeight() - 1);

	if (m_selected) {
	    g.setColor(m_borderSelectionColor);
	    g.drawRect(offset, 0, getWidth() - 1 - offset, getHeight() - 1);
	}
	super.paintComponent(g);
    }
}
