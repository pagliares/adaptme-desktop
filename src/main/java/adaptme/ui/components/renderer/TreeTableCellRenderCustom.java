package adaptme.ui.components.renderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultTreeCellRenderer;

import org.eclipse.epf.uma.Activity;
import org.eclipse.epf.uma.CapabilityPattern;
import org.eclipse.epf.uma.DeliveryProcess;
import org.eclipse.epf.uma.Iteration;
import org.eclipse.epf.uma.MethodElement;
import org.eclipse.epf.uma.Milestone;
import org.eclipse.epf.uma.Phase;
import org.eclipse.epf.uma.RoleDescriptor;
import org.eclipse.epf.uma.TaskDescriptor;
import org.eclipse.epf.uma.VariabilityType;
import org.eclipse.epf.uma.WorkProductDescriptor;

import adaptme.base.MethodLibraryHash;
import adaptme.util.EPFConstants;
import simulator.uma.dynamic.DynamicProcess;

public class TreeTableCellRenderCustom extends DefaultTreeCellRenderer {

	private static final long serialVersionUID = 1L;
	protected Color m_textSelectionColor;
	protected Color m_textNonSelectionColor;
	protected Color m_bkSelectionColor;
	protected Color m_bkNonSelectionColor;
	protected Color m_borderSelectionColor;

	protected boolean m_selected;
	private MethodLibraryHash hash;

	public TreeTableCellRenderCustom(MethodLibraryHash hash) {
		super();
		m_textSelectionColor = UIManager.getColor("Tree.selectionForeground");
		m_textNonSelectionColor = UIManager.getColor("Tree.textForeground");
		m_bkSelectionColor = UIManager.getColor("Tree.selectionBackground");
		m_bkNonSelectionColor = UIManager.getColor("Tree.textBackground");
		m_borderSelectionColor = UIManager.getColor("Tree.selectionBorderColor");
		setOpaque(false);

		this.hash = hash;
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf,
			int row, boolean hasFocus) {
		String name;
		if (value instanceof Activity) {
			Activity activity = (Activity) value;
			if (activity.getVariabilityType() != VariabilityType.NA && hash != null) {
				name = ((Activity) hash.getHashMap().get(activity.getVariabilityBasedOnElement()))
						.getPresentationName();
				m_textNonSelectionColor = new Color(0x007F3A);
				m_textSelectionColor = Color.BLACK;
			} else {
				name = activity.getPresentationName();
				m_textSelectionColor = UIManager.getColor("Tree.selectionForeground");
				m_textNonSelectionColor = UIManager.getColor("Tree.textForeground");
			}
		} else {
			name = ((MethodElement) value).getPresentationName();
		}
		setText(name);
		if (value instanceof DynamicProcess) {
			setIcon(EPFConstants.deliveryProcessIcon);
		} else if (value instanceof DeliveryProcess) {
			setIcon(EPFConstants.deliveryProcessIcon);
		} else if (value instanceof CapabilityPattern) {
			setIcon(EPFConstants.capabilityPatternIcon);
		} else if (value instanceof WorkProductDescriptor) {
			setIcon(EPFConstants.workProductDescriptor);
		} else if (value instanceof Milestone) {
			setIcon(EPFConstants.milestoneIcon);
		} else if (value instanceof Phase) {
			setIcon(EPFConstants.phaseIcon);
		} else if (value instanceof TaskDescriptor) {
			setIcon(EPFConstants.taskDescriptorIcon);
		} else if (value instanceof Iteration) {
			setIcon(EPFConstants.iterationIcon);
		} else if (value instanceof RoleDescriptor) {
			setIcon(EPFConstants.roleDescriptor);
		} else if (value instanceof Activity) {
			setIcon(EPFConstants.activityIcon);
		}
		setFont(tree.getFont());
		setForeground(sel ? m_textSelectionColor : m_textNonSelectionColor);
		setBackground(sel ? m_bkSelectionColor : row % 2 != 0 ? new Color(242, 242, 242) : m_bkNonSelectionColor);
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
