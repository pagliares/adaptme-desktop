package adaptme.ui.components;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.DocumentListener;

import adaptme.ui.panel.base.TabbedPanel;
import adaptme.util.EPFConstants;

public class TabbedPanelClossable extends JTabbedPane {

    private static final long serialVersionUID = -6061794717656920169L;

    private static final Icon CLOSE_TAB_ICON = new ImageIcon(
	    TabbedPanelClossable.class.getResource(EPFConstants.ICONS_PKG + "closeTabButton.png"));
    private static final Icon PAGE_ICON = new ImageIcon(
	    TabbedPanelClossable.class.getResource(EPFConstants.ICONS_PKG + "page_edit.png"));

    /**
     * Adds a component to a JTabbedPane with a little "close tab" button on the
     * right side of the tab.
     *
     * @param c
     *            any JComponent
     * @param title
     *            the title for the tab
     */
    public void addTab(final JComponent c, final String title) {
	final JTabbedPane tabbedPane = this;
	// Add the tab to the pane without any label
	tabbedPane.addTab(null, c);
	int pos = tabbedPane.indexOfComponent(c);

	// Create a FlowLayout that will space things 5px apart
	FlowLayout f = new FlowLayout(FlowLayout.CENTER, 5, 0);

	// Make a small JPanel with the layout and make it non-opaque
	JPanel pnlTab = new JPanel(f);
	pnlTab.setOpaque(false);

	// Add a JLabel with title and the left-side tab icon
	JLabel lblTitle = new JLabel(title);
	lblTitle.setIcon(PAGE_ICON);

	// Create a JButton for the close tab button
	JButton btnClose = new JButton();
	btnClose.setOpaque(false);

	btnClose.setIcon(CLOSE_TAB_ICON);

	// Set border null so the button doesn't make the tab too big
	btnClose.setBorder(null);

	// Make sure the button can't get focus, otherwise it looks funny
	btnClose.setFocusable(false);

	// Put the panel together
	pnlTab.add(lblTitle);
	pnlTab.add(btnClose);

	// Add a thin border to keep the image below the top edge of the tab
	// when the tab is selected
	pnlTab.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));

	// Now assign the component for the tab
	tabbedPane.setTabComponentAt(pos, pnlTab);

	AbstractAction closeTabAction = new AbstractAction() {

	    private static final long serialVersionUID = -5400747500570444070L;

	    @Override
	    public void actionPerformed(ActionEvent e) {
		int option;
		if (c instanceof TabbedPanel) {
		    DocumentListener documentListener = (((TabbedPanel) c).getDocumentListener());
		    if (documentListener != null) {
			boolean changed = ((ElementDocumentListener) documentListener).hasChanged();
			changed = changed
				|| ((ElementListDataListener) ((TabbedPanel) c).getListDataListener()).hasChanged();
			if (changed) {
			    option = JOptionPane.showConfirmDialog(null, "Save changes?", "Save",
				    JOptionPane.YES_NO_CANCEL_OPTION);
			} else {
			    option = JOptionPane.NO_OPTION;
			}
		    } else {
			option = JOptionPane.NO_OPTION;
		    }
		} else {
		    return;
		}
		if (option == JOptionPane.OK_OPTION) {
		    ((TabbedPanel) c).save();
		    tabbedPane.remove(c);
		} else if (option == JOptionPane.NO_OPTION) {
		    ((TabbedPanel) c).save();
		    tabbedPane.remove(c);
		}
	    }
	};
	// Add the listener that removes the tab
	btnClose.addActionListener(closeTabAction);

	// Optionally bring the new tab to the front
	tabbedPane.setSelectedComponent(c);
    }
}
