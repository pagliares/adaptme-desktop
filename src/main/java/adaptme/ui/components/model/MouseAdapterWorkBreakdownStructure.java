package adaptme.ui.components.model;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.tree.TreePath;

import org.eclipse.epf.uma.Activity;
import org.eclipse.epf.uma.BreakdownElement;
import org.eclipse.epf.uma.DeliveryProcess;
import org.eclipse.epf.uma.VariabilityType;
import org.eclipse.epf.uma.WorkBreakdownElement;
import org.jdesktop.swingx.JXTreeTable;

import adaptme.base.MethodLibraryHash;
import adaptme.ui.panel.base.process.WBSNewMenu;

public class MouseAdapterWorkBreakdownStructure extends MouseAdapter {

    JXTreeTable treeTable;
    private MethodLibraryHash hash;
    private SPEMWorkBreakdownStructureTreeTableModel treeTableModel;

    public MouseAdapterWorkBreakdownStructure(JXTreeTable treeTable,
	    SPEMWorkBreakdownStructureTreeTableModel treeTableModel, MethodLibraryHash hash) {
	super();
	this.treeTable = treeTable;
	this.treeTableModel = treeTableModel;
	this.hash = hash;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
	if (e.isPopupTrigger() || e.getButton() == MouseEvent.BUTTON3) {
	    int x = e.getX();
	    int y = e.getY();
	    TreePath path = treeTable.getPathForLocation(x, y);

	    if (path == null) {
		return;
	    }

	    Object object = path.getLastPathComponent();
	    WorkBreakdownElement workBreakdownElement = (WorkBreakdownElement) object;
	    WorkBreakdownElement superActivity = (WorkBreakdownElement) hash.getHashMap()
		    .get(workBreakdownElement.getSuperActivity());
	    JPopupMenu popupMenu = new JPopupMenu();
	    new WBSNewMenu(superActivity, treeTable, popupMenu, "New Sibling");
	    popupMenu.add(new JMenuItem(new AbstractAction("Remove") {

		@Override
		public void actionPerformed(ActionEvent e) {
		    treeTableModel.remove(object);
		    treeTable.revalidate();
		    treeTable.repaint();
		    treeTable.updateUI();
		    ((SPEMWorkBreakdownStructureTreeTableModel) treeTable.getTreeTableModel()).update();
		}
	    }));
	    if (object instanceof Activity) {
		new WBSNewMenu((BreakdownElement) object, treeTable, popupMenu, "New Child");
		if (!(object instanceof DeliveryProcess)) {
		    new WBSNewMenu(superActivity, treeTable, popupMenu, "New Sibling");
		}
		popupMenu.add(new JMenuItem(new AbstractAction("Remove") {

		    @Override
		    public void actionPerformed(ActionEvent e) {
			treeTableModel.remove(object);
			treeTable.revalidate();
			treeTable.repaint();
			treeTable.updateUI();
			((SPEMWorkBreakdownStructureTreeTableModel) treeTable.getTreeTableModel()).update();
		    }
		}));
	    }
	    popupMenu.setVisible(true);
	    popupMenu.show(treeTable, x, y);
	} else if (e.getClickCount() >= 2) {
	    int x = e.getX();
	    int y = e.getY();
	    TreePath path = treeTable.getPathForLocation(x, y);

	    if (path == null) {
		return;
	    }

	    Object object = path.getLastPathComponent();

	    if (object instanceof WorkBreakdownElement) {
		WorkBreakdownElement workBreakdownElement = (WorkBreakdownElement) object;
		if (workBreakdownElement instanceof Activity) {
		    if (((Activity) workBreakdownElement).getVariabilityType() != VariabilityType.NA) {
			return;
		    }
		}
		String presentationName = JOptionPane.showInputDialog("Insert the new Presentation name:",
			workBreakdownElement.getPresentationName());
		if (presentationName == null || presentationName.equals("")) {
		    return;
		}
		workBreakdownElement.setPresentationName(presentationName);
		workBreakdownElement.setName(presentationName);
		treeTable.updateUI();
	    }
	}
    }

}
