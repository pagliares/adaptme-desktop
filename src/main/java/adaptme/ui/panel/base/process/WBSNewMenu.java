package adaptme.ui.panel.base.process;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.eclipse.epf.uma.BreakdownElement;
import org.jdesktop.swingx.JXTreeTable;

import adaptme.ui.action.wbs.NewActivityAction;
import adaptme.ui.action.wbs.NewIterationAction;
import adaptme.ui.action.wbs.NewMilestoneAction;
import adaptme.ui.action.wbs.NewPhaseAction;
import adaptme.ui.action.wbs.NewTaskDescriptorAction;

public class WBSNewMenu {

    private BreakdownElement breakdownElement;
    private JXTreeTable treeTable;
    private JPopupMenu popupMenu;
    private String title;

    public WBSNewMenu(BreakdownElement breakdownElement, JXTreeTable treeTable, JPopupMenu popupMenu, String title) {
	super();
	this.breakdownElement = breakdownElement;
	this.treeTable = treeTable;
	this.popupMenu = popupMenu;
	this.title = title;
	init();
    }

    private void init() {
	JMenu newChildMenu = new JMenu(title);
	JMenuItem newPhaseMenuItem = new JMenuItem(new NewPhaseAction("Phase", breakdownElement, treeTable));
	newChildMenu.add(newPhaseMenuItem);
	JMenuItem newIterationMenuItem = new JMenuItem(
		new NewIterationAction("Iterarion", breakdownElement, treeTable));
	newChildMenu.add(newIterationMenuItem);
	JMenuItem newActivityMenuItem = new JMenuItem(new NewActivityAction("Activity", breakdownElement, treeTable));
	newChildMenu.add(newActivityMenuItem);
	JMenuItem newTaskDescriptorMenuItem = new JMenuItem(
		new NewTaskDescriptorAction("Task Descriptor", breakdownElement, treeTable));
	newChildMenu.add(newTaskDescriptorMenuItem);
	JMenuItem newMilestoneMenuItem = new JMenuItem(
		new NewMilestoneAction("Milestone", breakdownElement, treeTable));
	newChildMenu.add(newMilestoneMenuItem);
	popupMenu.add(newChildMenu);
    }
}
