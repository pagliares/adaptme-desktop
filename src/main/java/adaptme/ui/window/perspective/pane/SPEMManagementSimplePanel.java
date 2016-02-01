package adaptme.ui.window.perspective.pane;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import org.eclipse.epf.uma.Artifact;
import org.eclipse.epf.uma.ContentCategoryPackage;
import org.eclipse.epf.uma.Deliverable;
import org.eclipse.epf.uma.Element;
import org.eclipse.epf.uma.MethodPackage;
import org.eclipse.epf.uma.MethodPlugin;
import org.eclipse.epf.uma.NamedElement;
import org.eclipse.epf.uma.Outcome;
import org.eclipse.epf.uma.ProcessComponent;
import org.eclipse.epf.uma.Role;
import org.eclipse.epf.uma.Task;

import adaptme.base.ElementWrapper;
import adaptme.base.MethodLibraryHash;
import adaptme.base.MethodLibraryWrapper;
import adaptme.ui.components.ElementDocumentListener;
import adaptme.ui.components.ElementListDataListener;
import adaptme.ui.components.TabbedPanelClossable;
import adaptme.ui.components.renderer.TreeCellRendererCustom;
import adaptme.ui.panel.base.TabbedPanel;
import adaptme.ui.panel.base.process.processcomponent.TabbedPanelProcessComponent;
import adaptme.ui.panel.base.role.TabbedPanelRole;
import adaptme.ui.panel.base.task.TabbedPanelTask;
import adaptme.ui.panel.base.workproduct.artifact.TabbedPanelArtifact;
import adaptme.ui.panel.base.workproduct.deliverable.TabbedPanelDeliverable;
import adaptme.ui.panel.base.workproduct.outcome.TabbedPanelOutcome;
import adaptme.ui.window.AdaptMeUI;

public class SPEMManagementSimplePanel {

    private JPanel panel;
    private AdaptMeUI adaptMeUI;
    private JTree leftTree;
    private TreeModel leftTreeModel;
    private JTabbedPane tabbedPanePrincipal;
    private MethodLibraryWrapper methodLibraryWrapper;

    private List<JMenuItem> listMenuItems;
    private JMenuItem menuItemNewArtifact;
    private JMenuItem menuItemNewContentPackage;
    private JMenuItem menuItemNewDeliverable;
    private JMenuItem menuItemNewOutcome;
    private JMenuItem menuItemNewRole;
    private JMenuItem menuItemNewTask;
    private JMenuItem menuItemNewCapabilityPattern;
    private JMenuItem menuItemNewDeliveryProcess;
    private JMenuItem menuItemOpenInSimulator;
    private JMenuItem menuItemRename;
    private JPopupMenu popupMenu;

    // private AbstractAction actionNewRole;
    // private NewTaskAction actionNewTask;
    // private NewArtifactAction actionNewArtifact;
    // private NewOutcomeAction actionNewOutcome;
    // private NewDeliverableAction actionNewDeliverable;
    // private NewCapabilityPatternAction actionNewCapabilityPattern;
    // private NewDeliveryProcessAction actionDeliveryProcess;
    // private NewContentPackageAction actionNewContentPackage;
    // private RenameAction renameAction;

    private JTabbedPane leftTreeTabbedPane;

    public SPEMManagementSimplePanel(AdaptMeUI adaptMeUI) {

	this.adaptMeUI = adaptMeUI;

	panel = new JPanel();
	panel.setBorder(new EmptyBorder(5, 5, 5, 5));
	panel.setLayout(new BorderLayout(0, 0));

	JSplitPane splitPane = new JSplitPane();
	splitPane.setDividerLocation(300);
	panel.add(splitPane, BorderLayout.CENTER);

	tabbedPanePrincipal = new TabbedPanelClossable();
	splitPane.setRightComponent(tabbedPanePrincipal);

	leftTree = new JTree();
	leftTree.setCellRenderer(new TreeCellRendererCustom());
	leftTree.addMouseListener(new MouseListenerAdapterPluginDescription(leftTree, adaptMeUI));
	leftTreeModel = new DefaultTreeModel(null);
	leftTree.setModel(leftTreeModel);
	leftTree.setRootVisible(false);
	leftTree.setShowsRootHandles(true);
	leftTree.setDragEnabled(true);

	leftTreeTabbedPane = new JTabbedPane(SwingConstants.TOP);
	splitPane.setLeftComponent(leftTreeTabbedPane);

	JScrollPane scrollPane = new JScrollPane();
	leftTreeTabbedPane.addTab("Library", null, scrollPane, null);
	scrollPane.getVerticalScrollBar().setUnitIncrement(16);
	scrollPane.setViewportView(leftTree);

	// configurando o popupMenu
	listMenuItems = new ArrayList<>();
	popupMenu = new JPopupMenu();
	JMenu mnNewMenu = new JMenu("New");
	popupMenu.add(mnNewMenu);
	menuItemNewRole = new JMenuItem();
	mnNewMenu.add(menuItemNewRole);
	listMenuItems.add(menuItemNewRole);
	menuItemNewTask = new JMenuItem();
	mnNewMenu.add(menuItemNewTask);
	listMenuItems.add(menuItemNewTask);
	menuItemNewArtifact = new JMenuItem();
	mnNewMenu.add(menuItemNewArtifact);
	listMenuItems.add(menuItemNewArtifact);
	menuItemNewDeliverable = new JMenuItem();
	mnNewMenu.add(menuItemNewDeliverable);
	listMenuItems.add(menuItemNewDeliverable);
	menuItemNewOutcome = new JMenuItem();
	mnNewMenu.add(menuItemNewOutcome);
	listMenuItems.add(menuItemNewOutcome);
	menuItemNewCapabilityPattern = new JMenuItem();
	mnNewMenu.add(menuItemNewCapabilityPattern);
	listMenuItems.add(menuItemNewCapabilityPattern);
	menuItemNewDeliveryProcess = new JMenuItem();
	mnNewMenu.add(menuItemNewDeliveryProcess);
	listMenuItems.add(menuItemNewDeliveryProcess);
	menuItemOpenInSimulator = new JMenuItem();
	popupMenu.add(menuItemOpenInSimulator);
	listMenuItems.add(menuItemOpenInSimulator);
	menuItemNewContentPackage = new JMenuItem();
	mnNewMenu.add(menuItemNewContentPackage);
	listMenuItems.add(menuItemNewContentPackage);
	menuItemRename = new JMenuItem();
	popupMenu.add(menuItemRename);
	listMenuItems.add(menuItemRename);
	leftTree.add(popupMenu);
	// leftTree.addMouseListener(new PopupTrigger());

	// methodLibraryWrapper = new MethodLibraryWrapper();
	// methodLibraryWrapper.load(new
	// File(getClass().getResource("/resources/process/xp_base/xp_base.xml").getPath()));
	// TreeNodeFactory factory = new TreeNodeFactory(methodLibraryWrapper);
	// MutableTreeNode leftRoot = factory.buildTree();
	// leftTreeModel = new DefaultTreeModel(leftRoot);
	// leftTree.setModel(leftTreeModel);
	// leftTree.setTransferHandler(new
	// WBSTransferHandler(methodLibraryWrapper.getMethodLibraryHash()));
	// initContextMenu();
    }

    // private void initContextMenu() {
    // // iniciando menu de contexto. Encontrar uma forma de refatorar
    // actionNewRole = new NewRoleAction("Role", methodLibraryWrapper, this,
    // leftTree);
    // menuItemNewRole.setAction(actionNewRole);
    // actionNewTask = new NewTaskAction("Task", methodLibraryWrapper, this,
    // leftTree);
    // menuItemNewTask.setAction(actionNewTask);
    // actionNewDeliverable = new NewDeliverableAction("Deliverable",
    // methodLibraryWrapper, this, leftTree);
    // menuItemNewDeliverable.setAction(actionNewDeliverable);
    // actionNewOutcome = new NewOutcomeAction("Outcome", methodLibraryWrapper,
    // this, leftTree);
    // menuItemNewOutcome.setAction(actionNewOutcome);
    // actionNewArtifact = new NewArtifactAction("Artifact",
    // methodLibraryWrapper, this, leftTree);
    // menuItemNewArtifact.setAction(actionNewArtifact);
    // actionNewCapabilityPattern = new NewCapabilityPatternAction("Capability
    // Pattern", methodLibraryWrapper, this,
    // leftTree);
    // menuItemNewCapabilityPattern.setAction(actionNewCapabilityPattern);
    // actionDeliveryProcess = new NewDeliveryProcessAction("Delivery Process",
    // methodLibraryWrapper, this, leftTree);
    // menuItemNewDeliveryProcess.setAction(actionDeliveryProcess);
    // actionNewContentPackage = new NewContentPackageAction("Content Package",
    // methodLibraryWrapper, leftTree);
    // menuItemNewContentPackage.setAction(actionNewContentPackage);
    // renameAction = new RenameAction("Rename", leftTree);
    // menuItemRename.setAction(renameAction);
    // }

    public void processRolePanel(Role role, ContentCategoryPackage contentCategoryPackage,
	    DefaultMutableTreeNode node) {
	if (checkOpen(role)) {
	    return;
	}
	MethodLibraryHash hash = methodLibraryWrapper.getMethodLibraryHash();
	JTabbedPane tabbedPanelRole = new TabbedPanelRole(role, hash, contentCategoryPackage, adaptMeUI, node,
		leftTree);
	((TabbedPanelRole) tabbedPanelRole).setChangeListeners(new ElementDocumentListener(),
		new ElementListDataListener());
	((TabbedPanelClossable) tabbedPanePrincipal).addTab(tabbedPanelRole, role.getName());
    }

    public void processTaskPanel(Task task, ContentCategoryPackage contentCategoryPackage, MutableTreeNode node) {
	if (checkOpen(task)) {
	    return;
	}
	MethodLibraryHash hash = methodLibraryWrapper.getMethodLibraryHash();
	JTabbedPane tabbedPaneTask = new TabbedPanelTask(task, hash, contentCategoryPackage, adaptMeUI, node, leftTree);
	((TabbedPanelTask) tabbedPaneTask).setChangeListeners(new ElementDocumentListener(),
		new ElementListDataListener());
	((TabbedPanelClossable) tabbedPanePrincipal).addTab(tabbedPaneTask, task.getName());
    }

    public void processArtifactPanel(Artifact artifact, ContentCategoryPackage contentCategoryPackage,
	    DefaultMutableTreeNode node) {
	if (checkOpen(artifact)) {
	    return;
	}
	MethodLibraryHash hash = methodLibraryWrapper.getMethodLibraryHash();
	JTabbedPane tabbedPaneArtifact = new TabbedPanelArtifact(artifact, hash, contentCategoryPackage, adaptMeUI,
		node, leftTree);
	((TabbedPanelArtifact) tabbedPaneArtifact).setChangeListeners(new ElementDocumentListener(),
		new ElementListDataListener());
	((TabbedPanelClossable) tabbedPanePrincipal).addTab(tabbedPaneArtifact, artifact.getName());
    }

    public void processOutcomePanel(Outcome outcome, ContentCategoryPackage contentCategoryPackage,
	    DefaultMutableTreeNode node) {
	if (checkOpen(outcome)) {
	    return;
	}
	MethodLibraryHash hash = methodLibraryWrapper.getMethodLibraryHash();
	JTabbedPane tabbedPaneOutcome = new TabbedPanelOutcome(outcome, hash, contentCategoryPackage, adaptMeUI, node,
		leftTree);
	((TabbedPanelOutcome) tabbedPaneOutcome).setChangeListeners(new ElementDocumentListener(),
		new ElementListDataListener());
	((TabbedPanelClossable) tabbedPanePrincipal).addTab(tabbedPaneOutcome, outcome.getName());
    }

    public void processDeliverablePanel(Deliverable deliverable, ContentCategoryPackage contentCategoryPackage,
	    DefaultMutableTreeNode node) {
	if (checkOpen(deliverable)) {
	    return;
	}
	MethodLibraryHash hash = methodLibraryWrapper.getMethodLibraryHash();
	JTabbedPane tabbedPaneDeliverable = new TabbedPanelDeliverable(deliverable, hash, contentCategoryPackage,
		adaptMeUI, node, leftTree);
	((TabbedPanelDeliverable) tabbedPaneDeliverable).setChangeListeners(new ElementDocumentListener(),
		new ElementListDataListener());
	((TabbedPanelClossable) tabbedPanePrincipal).addTab(tabbedPaneDeliverable, deliverable.getName());
    }

    public void processProcessComponentPanel(ProcessComponent processComponent, DefaultMutableTreeNode node) {
	if (checkOpen(processComponent)) {
	    return;
	}

	JTabbedPane tabbedPane = new TabbedPanelProcessComponent(processComponent.getProcess(), null,
		methodLibraryWrapper.getMethodLibraryHash(), node);
	((TabbedPanelClossable) tabbedPanePrincipal).addTab(tabbedPane, processComponent.getName());
    }

    private boolean checkOpen(NamedElement namedElement) {
	Component[] components = tabbedPanePrincipal.getComponents();
	for (Component component : components) {
	    if (component instanceof TabbedPanel) {
		if (((NamedElement) ((ElementWrapper) ((DefaultMutableTreeNode) ((TabbedPanel) component).getNode())
			.getUserObject()).getElement()).getName().equals(namedElement.getName())) {
		    tabbedPanePrincipal.setSelectedComponent(component);
		    return true;
		}
	    }
	}
	return false;
    }

    public DefaultTreeModel getLeftTreeModel() {
	return (DefaultTreeModel) leftTree.getModel();
    }

    // private class PopupTrigger extends MouseAdapter {
    //
    // @Override
    // public void mouseReleased(MouseEvent e) {
    // if (e.isPopupTrigger() || e.getButton() == MouseEvent.BUTTON3) {
    // int x = e.getX();
    // int y = e.getY();
    // TreePath path = leftTree.getPathForLocation(x, y);
    //
    // if (path == null) {
    // return;
    // }
    //
    // DefaultMutableTreeNode node = (DefaultMutableTreeNode)
    // path.getLastPathComponent();
    // Object object = node.getUserObject();
    // if (object instanceof ElementWrapper) {
    // ElementWrapper ew = (ElementWrapper) object;
    // if (ew.getName().equals(EPFConstants.rolesText)) {
    // leftTree.setSelectionPath(path);
    // leftTree.scrollPathToVisible(path);
    // for (JMenuItem menu : listMenuItems) {
    // menu.setVisible(false);
    // }
    // menuItemNewRole.setVisible(true);
    // popupMenu.show(leftTree, x, y);
    // } else if (ew.getName().equals(EPFConstants.contentPackagesText)) {
    // leftTree.setSelectionPath(path);
    // leftTree.scrollPathToVisible(path);
    // for (JMenuItem menu : listMenuItems) {
    // menu.setVisible(false);
    // }
    // menuItemNewContentPackage.setVisible(true);
    // popupMenu.show(leftTree, x, y);
    // } else if (ew.getName().equals(EPFConstants.tasksText)) {
    // leftTree.setSelectionPath(path);
    // leftTree.scrollPathToVisible(path);
    // for (JMenuItem menu : listMenuItems) {
    // menu.setVisible(false);
    // }
    // menuItemNewTask.setVisible(true);
    // popupMenu.show(leftTree, x, y);
    // } else if (ew.getName().equals(EPFConstants.workProductText)) {
    // leftTree.setSelectionPath(path);
    // leftTree.scrollPathToVisible(path);
    // for (JMenuItem menu : listMenuItems) {
    // menu.setVisible(false);
    // }
    // menuItemNewArtifact.setVisible(true);
    // menuItemNewOutcome.setVisible(true);
    // menuItemNewDeliverable.setVisible(true);
    // popupMenu.show(leftTree, x, y);
    // } else if (ew.getName().equals(EPFConstants.capabilityPatternsText)) {
    // leftTree.setSelectionPath(path);
    // leftTree.scrollPathToVisible(path);
    // for (JMenuItem menu : listMenuItems) {
    // menu.setVisible(false);
    // }
    // menuItemNewCapabilityPattern.setVisible(true);
    // popupMenu.show(leftTree, x, y);
    // MethodPlugin plugin = (MethodPlugin) ((ElementWrapper)
    // ((DefaultMutableTreeNode) node
    // .getParent().getParent()).getUserObject()).getElement();
    // actionNewCapabilityPattern.setMethodPluginList(plugin.getMethodPackage());
    // } else if (ew.getName().equals(EPFConstants.deliveryProcessesText)) {
    // leftTree.setSelectionPath(path);
    // leftTree.scrollPathToVisible(path);
    // for (JMenuItem menu : listMenuItems) {
    // menu.setVisible(false);
    // }
    // menuItemNewDeliveryProcess.setVisible(true);
    // popupMenu.show(leftTree, x, y);
    // MethodPlugin plugin = (MethodPlugin) ((ElementWrapper)
    // ((DefaultMutableTreeNode) node
    // .getParent().getParent()).getUserObject()).getElement();
    // actionDeliveryProcess.setMethodPluginList(plugin.getMethodPackage());
    // } else if (ew.getElement() instanceof ProcessComponent) {
    // return;
    // } else if (ew.getElement() instanceof ProcessPackage) {
    // return;
    // }
    // }
    // }
    // }
    // }

    public class MouseListenerAdapterPluginDescription extends MouseAdapter {

	JTree mainTree = null;
	AdaptMeUI adaptMeUI;

	public MouseListenerAdapterPluginDescription(JTree mainTree, AdaptMeUI adaptMeUI) {
	    super();
	    this.mainTree = mainTree;
	    this.adaptMeUI = adaptMeUI;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	    int selRow = mainTree.getRowForLocation(e.getX(), e.getY());
	    TreePath selPath = mainTree.getPathForLocation(e.getX(), e.getY());

	    if (selRow != -1) {
		if (e.getClickCount() == 2) {
		    DefaultMutableTreeNode node = (DefaultMutableTreeNode) selPath.getLastPathComponent();
		    Object obj = node.getUserObject();
		    if (obj instanceof ElementWrapper) {
			Element element = ((ElementWrapper) obj).getElement();
			if (element instanceof Role) {
			    Role role = (Role) element;
			    ContentCategoryPackage contentCategoryPackage = getContentCategoryPackage(selPath);
			    processRolePanel(role, contentCategoryPackage, node);
			} else if (element instanceof Task) {
			    Task task = (Task) element;
			    ContentCategoryPackage contentCategoryPackage = getContentCategoryPackage(selPath);
			    processTaskPanel(task, contentCategoryPackage, node);
			} else if (element instanceof Artifact) {
			    Artifact artifact = (Artifact) element;
			    ContentCategoryPackage contentCategoryPackage = getContentCategoryPackage(selPath);
			    processArtifactPanel(artifact, contentCategoryPackage, node);
			} else if (element instanceof Outcome) {
			    Outcome outcome = (Outcome) element;
			    ContentCategoryPackage contentCategoryPackage = getContentCategoryPackage(selPath);
			    processOutcomePanel(outcome, contentCategoryPackage, node);
			} else if (element instanceof Deliverable) {
			    Deliverable deliverable = (Deliverable) element;
			    ContentCategoryPackage contentCategoryPackage = getContentCategoryPackage(selPath);
			    processDeliverablePanel(deliverable, contentCategoryPackage, node);
			} else if (element instanceof ProcessComponent) {
			    ProcessComponent processComponent = (ProcessComponent) element;
			    processProcessComponentPanel(processComponent, node);
			}

		    }
		}
	    }
	}

	private ContentCategoryPackage getContentCategoryPackage(TreePath selPath) {

	    ElementWrapper elementWrapper;
	    while (true) {
		elementWrapper = (ElementWrapper) ((DefaultMutableTreeNode) selPath.getParentPath()
			.getLastPathComponent()).getUserObject();
		selPath = selPath.getParentPath();
		if (elementWrapper.getElement() instanceof MethodPlugin) {
		    break;
		}
	    }
	    MethodPlugin methodPlugin = (MethodPlugin) elementWrapper.getElement();
	    ContentCategoryPackage contentCategoryPackage = null;
	    for (MethodPackage methodPackage : methodPlugin.getMethodPackage()) {
		if (methodPackage instanceof ContentCategoryPackage) {
		    contentCategoryPackage = (ContentCategoryPackage) methodPackage;
		    break;
		}
	    }
	    return contentCategoryPackage;
	}
    }

    public JPanel getPanel() {
	return panel;
    }
}
