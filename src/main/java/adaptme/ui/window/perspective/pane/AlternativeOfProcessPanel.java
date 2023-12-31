package adaptme.ui.window.perspective.pane;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
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
import org.eclipse.epf.uma.Process;
import org.eclipse.epf.uma.ProcessComponent;
import org.eclipse.epf.uma.ProcessPackage;
import org.eclipse.epf.uma.Role;
import org.eclipse.epf.uma.Task;

import adaptme.base.ElementWrapper;
import adaptme.base.MethodLibraryHash;
import adaptme.base.MethodLibraryWrapper;
import adaptme.base.TreeNodeFactory;
import adaptme.base.persist.PersistProcess;
import adaptme.ui.action.NewArtifactAction;
import adaptme.ui.action.NewCapabilityPatternAction;
import adaptme.ui.action.NewContentPackageAction;
import adaptme.ui.action.NewDeliverableAction;
import adaptme.ui.action.NewDeliveryProcessAction;
import adaptme.ui.action.NewOutcomeAction;
import adaptme.ui.action.NewRoleAction;
import adaptme.ui.action.NewTaskAction;
import adaptme.ui.action.OpenToSimulatorAction;
import adaptme.ui.action.RenameAction;
import adaptme.ui.components.CustomFileChooser;
import adaptme.ui.components.ElementDocumentListener;
import adaptme.ui.components.ElementListDataListener;
import adaptme.ui.components.TabbedPanelClossable;
import adaptme.ui.components.renderer.TreeCellRendererCustom;
import adaptme.ui.dynamic.NumberCompontent;
import adaptme.ui.dynamic.NumberTreeNode;
import adaptme.ui.dynamic.RunSimulationPanelXPThesis;
import adaptme.ui.dynamic.TreePanel;
import adaptme.ui.dynamic.UpdatePanel;
import adaptme.ui.dynamic.simulation.alternative.process.IntegratedLocalAndRepositoryViewPanel;
import adaptme.ui.dynamic.simulation.alternative.process.MainPanelSimulationOfAlternativeOfProcess;
import adaptme.ui.panel.base.TabbedPanel;
import adaptme.ui.panel.base.process.dnd.WBSTransferHandler;
import adaptme.ui.panel.base.process.processcomponent.TabbedPanelProcessComponent;
import adaptme.ui.panel.base.role.TabbedPanelRole;
import adaptme.ui.panel.base.task.TabbedPanelTask;
import adaptme.ui.panel.base.workproduct.artifact.TabbedPanelArtifact;
import adaptme.ui.panel.base.workproduct.deliverable.TabbedPanelDeliverable;
import adaptme.ui.panel.base.workproduct.outcome.TabbedPanelOutcome;
import adaptme.ui.window.AdaptMeUI;
import adaptme.ui.window.perspective.XACDMLTextAreaPanel;
import adaptme.ui.window.perspective.ExperimentationPanel;
import adaptme.ui.window.perspective.JavaProgramTextAreaPanel;
import adaptme.ui.window.perspective.RoleResourcesPanel;
import adaptme.ui.window.perspective.RunSimulationPanel;
import adaptme.ui.window.perspective.SPEMDrivenPerspectivePanel;
import adaptme.ui.window.perspective.ShowResultsPanel;
import adaptme.ui.window.perspective.WorkProductResourcesPanel;
import adaptme.util.EPFConstants;
import adaptme.util.RestoreMe;
import model.spem.MethodContentRepository;
import model.spem.ProcessContentRepository;
import model.spem.ProcessRepository;
import model.spem.SimulationFacade;
import model.spem.util.ProcessContentType;
 
public class AlternativeOfProcessPanel {

	private JPanel alternativeOfProcessPanel;
	private AdaptMeUI adaptMeUI;
	private TreeModel leftTreeModel;
	
	private JTree leftTree;
	
	private JTabbedPane tabbedPanePrincipal;
	private JTabbedPane tabbedPaneActivity3;
	private JTabbedPane tabbedPaneActivity4;
	private JTabbedPane leftTreeTabbedPane;


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
	private JMenuItem menuOpenToSimulate;
	private JPopupMenu popupMenu;

	private MethodLibraryWrapper methodLibraryWrapper;
	private AbstractAction actionNewRole;
	private NewTaskAction actionNewTask;
	private NewArtifactAction actionNewArtifact;
	private NewOutcomeAction actionNewOutcome;
	private NewDeliverableAction actionNewDeliverable;
	private NewCapabilityPatternAction actionNewCapabilityPattern;
	private NewDeliveryProcessAction actionDeliveryProcess;
	private NewContentPackageAction actionNewContentPackage;
	private OpenToSimulatorAction openToSimulatorAction;
	private RenameAction renameAction;

	private JMenu newMenu;
	private JMenu fileMenu;

	private SPEMDrivenPerspectivePanel spemDrivenPerspectivePanel;
 	
	private ProcessRepository processRepository;
	private SimulationFacade simulationFacade = new SimulationFacade();
	private String simulationObjective;
	
	private List<ProcessContentRepository> completeListOfProcessContentRepository;
	private Set<ProcessContentRepository> listOfProcessContentRepositoryTasks;
 	

	public AlternativeOfProcessPanel(AdaptMeUI adaptMeUI, SPEMDrivenPerspectivePanel spemDrivenPerspectivePanel,
			MethodLibraryWrapper methodLibraryWrapper) {

		this.adaptMeUI = adaptMeUI;
		this.spemDrivenPerspectivePanel = spemDrivenPerspectivePanel;
		this.methodLibraryWrapper = methodLibraryWrapper;

		fileMenu = new JMenu("File");
		JMenuItem saveMenuItem = new JMenuItem("Save");
		saveMenuItem.addActionListener(e -> saveMenuAction());
		JMenuItem importMenuItem = new JMenuItem("Import");
		importMenuItem.addActionListener(e -> importMenuAction());

		JMenuItem exportMenuItem = new JMenuItem("Export");
		exportMenuItem.addActionListener(e -> exportMenuAction());
		fileMenu.add(saveMenuItem);
		fileMenu.add(importMenuItem);
		fileMenu.add(exportMenuItem);

		alternativeOfProcessPanel = new JPanel();
		alternativeOfProcessPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		alternativeOfProcessPanel.setLayout(new BorderLayout(0, 0));

		JSplitPane splitPane = new JSplitPane();
		splitPane.setDividerLocation(400);
		alternativeOfProcessPanel.add(splitPane, BorderLayout.CENTER);

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
		newMenu = new JMenu("New");
		popupMenu.add(newMenu);
		menuItemNewRole = new JMenuItem();
		newMenu.add(menuItemNewRole);
		listMenuItems.add(menuItemNewRole);
		menuItemNewTask = new JMenuItem();
		newMenu.add(menuItemNewTask);
		listMenuItems.add(menuItemNewTask);
		menuItemNewArtifact = new JMenuItem();
		newMenu.add(menuItemNewArtifact);
		listMenuItems.add(menuItemNewArtifact);
		menuItemNewDeliverable = new JMenuItem();
		newMenu.add(menuItemNewDeliverable);
		listMenuItems.add(menuItemNewDeliverable);
		menuItemNewOutcome = new JMenuItem();
		newMenu.add(menuItemNewOutcome);
		listMenuItems.add(menuItemNewOutcome);
		menuItemNewCapabilityPattern = new JMenuItem();
		newMenu.add(menuItemNewCapabilityPattern);
		listMenuItems.add(menuItemNewCapabilityPattern);
		menuItemNewDeliveryProcess = new JMenuItem();
		newMenu.add(menuItemNewDeliveryProcess);
		listMenuItems.add(menuItemNewDeliveryProcess);
		menuItemOpenInSimulator = new JMenuItem();
		popupMenu.add(menuItemOpenInSimulator);
		listMenuItems.add(menuItemOpenInSimulator);
		menuItemNewContentPackage = new JMenuItem();
		newMenu.add(menuItemNewContentPackage);
		listMenuItems.add(menuItemNewContentPackage);
		menuItemRename = new JMenuItem();
		popupMenu.add(menuItemRename);
		listMenuItems.add(menuItemRename);
		menuOpenToSimulate = new JMenuItem();
		popupMenu.add(menuOpenToSimulate);
		listMenuItems.add(menuOpenToSimulate);
		leftTree.add(popupMenu);
		leftTree.addMouseListener(new PopupTrigger());

		// methodLibraryWrapper = new MethodLibraryWrapper();
		// methodLibraryWrapper.load(new
		// File(getClass().getResource("/resources/process/xp_base/xp_base.xml").getPath()));
		TreeNodeFactory factory = new TreeNodeFactory(methodLibraryWrapper);
		MutableTreeNode leftRoot = factory.buildTree();
		leftTreeModel = new DefaultTreeModel(leftRoot);
		leftTree.setModel(leftTreeModel);
		leftTree.setTransferHandler(new WBSTransferHandler(methodLibraryWrapper.getMethodLibraryHash()));

		// iniciando menu de contexto. Encontrar uma forma de refatorar
		actionNewRole = new NewRoleAction("Role", methodLibraryWrapper, this, leftTree);
		menuItemNewRole.setAction(actionNewRole);
		actionNewTask = new NewTaskAction("Task", methodLibraryWrapper, this, leftTree);
		menuItemNewTask.setAction(actionNewTask);
		actionNewDeliverable = new NewDeliverableAction("Deliverable", methodLibraryWrapper, this, leftTree);
		menuItemNewDeliverable.setAction(actionNewDeliverable);
		actionNewOutcome = new NewOutcomeAction("Outcome", methodLibraryWrapper, this, leftTree);
		menuItemNewOutcome.setAction(actionNewOutcome);
		actionNewArtifact = new NewArtifactAction("Artifact", methodLibraryWrapper, this, leftTree);
		menuItemNewArtifact.setAction(actionNewArtifact);
		actionNewCapabilityPattern = new NewCapabilityPatternAction("Capability Pattern", methodLibraryWrapper, this,
				leftTree);
		menuItemNewCapabilityPattern.setAction(actionNewCapabilityPattern);
		actionDeliveryProcess = new NewDeliveryProcessAction("Delivery Process", methodLibraryWrapper, this, leftTree);
		menuItemNewDeliveryProcess.setAction(actionDeliveryProcess);
		actionNewContentPackage = new NewContentPackageAction("Content Package", methodLibraryWrapper, leftTree);
		menuItemNewContentPackage.setAction(actionNewContentPackage);
		renameAction = new RenameAction("Rename", leftTree);
		openToSimulatorAction = new OpenToSimulatorAction("Map SPEM to extended XACDML", this);
		menuOpenToSimulate.setAction(openToSimulatorAction);
		menuItemRename.setAction(renameAction);
	}

	private void importMenuAction() {
		String lastOpenPath = RestoreMe.restoreLastOpenPath();

		try {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Open Resource File");
			fileChooser.setCurrentDirectory(new File(lastOpenPath));
			int option = fileChooser.showOpenDialog(adaptMeUI.getFrame());
			if (option == JFileChooser.CANCEL_OPTION) {
				return;
			}
			File selectedFile = fileChooser.getSelectedFile();
			if (selectedFile != null) {
				RestoreMe.storeLastOpenPath(selectedFile.getCanonicalPath().substring(0,
						selectedFile.getCanonicalPath().length() - selectedFile.getName().length()));
				// methodLibraryWrapper = new MethodLibraryWrapper();
				methodLibraryWrapper.load(selectedFile);
				TreeNodeFactory factory = new TreeNodeFactory(methodLibraryWrapper);
				MutableTreeNode leftRoot = factory.buildTree();
				leftTreeModel = new DefaultTreeModel(leftRoot);
				leftTree.setModel(leftTreeModel);
				leftTree.setTransferHandler(new WBSTransferHandler(methodLibraryWrapper.getMethodLibraryHash()));
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private void exportMenuAction() {
		String lastSavePath = RestoreMe.restoreLastOpenPath();
		JFileChooser fileChooser = new CustomFileChooser();
		fileChooser.setDialogTitle("Export");
		fileChooser.setCurrentDirectory(new File(lastSavePath));
		fileChooser.showSaveDialog(adaptMeUI.getFrame());
		File file = fileChooser.getSelectedFile();
		if (file == null) {
			return;
		}
		methodLibraryWrapper.saveAs(file);
	}

	public void saveMenuAction() {
		Component[] components = tabbedPanePrincipal.getComponents();
		for (Component component : components) {
			if (component instanceof TabbedPanelRole) {
				((TabbedPanelRole) component).save();
			}
		}
	}

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

	private class PopupTrigger extends MouseAdapter {

		@Override
		public void mouseReleased(MouseEvent e) {
			if (e.isPopupTrigger() || e.getButton() == MouseEvent.BUTTON3) {
				int x = e.getX();
				int y = e.getY();
				TreePath path = leftTree.getPathForLocation(x, y);

				if (path == null) {
					return;
				}

				DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
				Object object = node.getUserObject();
				if (object instanceof ElementWrapper) {
					ElementWrapper ew = (ElementWrapper) object;  // este elemento wrapper que é adicionado na tree
					newMenu.setVisible(true);
					if (ew.getName().equals(EPFConstants.rolesText)) {
						leftTree.setSelectionPath(path);
						leftTree.scrollPathToVisible(path);
						for (JMenuItem menu : listMenuItems) {
							menu.setVisible(false);
						}
						menuItemNewRole.setVisible(true);
						popupMenu.show(leftTree, x, y);
					} else if (ew.getName().equals(EPFConstants.contentPackagesText)) {
						leftTree.setSelectionPath(path);
						leftTree.scrollPathToVisible(path);
						for (JMenuItem menu : listMenuItems) {
							menu.setVisible(false);
						}
						menuItemNewContentPackage.setVisible(true);
						popupMenu.show(leftTree, x, y);
					} else if (ew.getName().equals(EPFConstants.tasksText)) {
						leftTree.setSelectionPath(path);
						leftTree.scrollPathToVisible(path);
						for (JMenuItem menu : listMenuItems) {
							menu.setVisible(false);
						}
						menuItemNewTask.setVisible(true);
						popupMenu.show(leftTree, x, y);
					} else if (ew.getName().equals(EPFConstants.workProductText)) {
						leftTree.setSelectionPath(path);
						leftTree.scrollPathToVisible(path);
						for (JMenuItem menu : listMenuItems) {
							menu.setVisible(false);
						}
						menuItemNewArtifact.setVisible(true);
						menuItemNewOutcome.setVisible(true);
						menuItemNewDeliverable.setVisible(true);
						popupMenu.show(leftTree, x, y);
					} else if (ew.getName().equals(EPFConstants.capabilityPatternsText)) {
						leftTree.setSelectionPath(path);
						leftTree.scrollPathToVisible(path);
						for (JMenuItem menu : listMenuItems) {
							menu.setVisible(false);
						}
						menuItemNewCapabilityPattern.setVisible(true);
						popupMenu.show(leftTree, x, y);
						MethodPlugin plugin = (MethodPlugin) ((ElementWrapper) ((DefaultMutableTreeNode) node
								.getParent().getParent()).getUserObject()).getElement();
						actionNewCapabilityPattern.setMethodPluginList(plugin.getMethodPackage());
					} else if (ew.getName().equals(EPFConstants.deliveryProcessesText)) {
						leftTree.setSelectionPath(path);
						leftTree.scrollPathToVisible(path);
						for (JMenuItem menu : listMenuItems) {
							menu.setVisible(false);
						}
						menuItemNewDeliveryProcess.setVisible(true);
						popupMenu.show(leftTree, x, y);
						MethodPlugin plugin = (MethodPlugin) ((ElementWrapper) ((DefaultMutableTreeNode) node
								.getParent().getParent()).getUserObject()).getElement();
						actionDeliveryProcess.setMethodPluginList(plugin.getMethodPackage());
					} else if (ew.getElement() instanceof ProcessComponent) {
						Process process = ((ProcessComponent) ew.getElement()).getProcess(); // 
						leftTree.setSelectionPath(path);
						leftTree.scrollPathToVisible(path);
						for (JMenuItem menu : listMenuItems) {
							menu.setVisible(false);
						}
						newMenu.setVisible(false);
						menuOpenToSimulate.setVisible(true);
						popupMenu.show(leftTree, x, y);
						openToSimulatorAction.setProcess(process);
						openToSimulatorAction.setMethodLibraryHash(methodLibraryWrapper.getMethodLibraryHash());
						return;
					} else if (ew.getElement() instanceof ProcessPackage) {
						return;
					}
				}
			}
		}
	}

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

	public void openProcessToSimulate(Process process, MethodLibraryHash methodLibraryHash) {
		
//		tabbedPaneActivity3 = new JTabbedPane();
		tabbedPaneActivity3 = new TabbedPanelClossable();
		tabbedPaneActivity4 = new JTabbedPane();

		PersistProcess persistProcess = new PersistProcess();
		processRepository = persistProcess.buildProcess(process, methodLibraryHash);
		// @TODO  - the method below is not working yet. Side effect when rendering the tree.
		//	processRepository = persistProcess.buildProcessWithDeliveryProcessAsRoot(process, methodLibraryHash); 
		
		simulationFacade.addProcessAlternative(processRepository);
		
		DefaultMutableTreeNode treeNode = buildTreeNode(processRepository);
		TreePanel treePanel = new TreePanel(new DefaultTreeModel(treeNode));
		
		MainPanelSimulationOfAlternativeOfProcess mainPanel = new MainPanelSimulationOfAlternativeOfProcess(treePanel);
		treePanel.getTree().addTreeSelectionListener(
				evt -> mainPanel.changePanel((NumberCompontent) evt.getNewLeadSelectionPath().getLastPathComponent()));
		
		Set<String> taskList = persistProcess.getTaskList();
		
		RoleResourcesPanel roleResourcePanel = createContentsOfTabMappingRolesToXACDML(persistProcess);
		
 		WorkProductResourcesPanel workProductResourcesPanel = createContentsOfTabMappingWorkProductsToXACDML();
		
		createContentsOfTabMappingWBE(persistProcess, mainPanel);

		XACDMLTextAreaPanel defineXACDMLTextAreaPanel = new XACDMLTextAreaPanel(processRepository, mainPanel, this, taskList, workProductResourcesPanel, roleResourcePanel);
		tabbedPaneActivity3.addTab("3.4. Generate XACDML", defineXACDMLTextAreaPanel.getPanel());
		
		spemDrivenPerspectivePanel.addTab("3. Generation of a simulation model for the alternative of process", tabbedPaneActivity3);
		spemDrivenPerspectivePanel.getTabbedPane().setSelectedIndex(2); // exibe as subtabs da tab 3, deixando a 3.1 selecionada por default
																		  // sem esta linha aparece a aba 3 e somente ao clicar nela que
																		  // as subtabs aparecem. O indice 2 indica a tab 3 e nao a subtab 3.1
		
		
		JavaProgramTextAreaPanel javaProgramTextAreaPanel = new JavaProgramTextAreaPanel(defineXACDMLTextAreaPanel);
		tabbedPaneActivity4.addTab("4.1. Generating experimentation program", javaProgramTextAreaPanel.getPanel());
		
		ExperimentationPanel experimentationPanel = new ExperimentationPanel(workProductResourcesPanel, tabbedPaneActivity4, simulationFacade);
		tabbedPaneActivity4.addTab("4.2. Configuring experimentation program", experimentationPanel);
		
//		RunSimulationPanel runSimulationPanel = new RunSimulationPanel(defineXACDMLTextAreaPanel);
//		tabbedPaneActivity4.addTab("4.3. Run experiment", runSimulationPanel.getPanel());
		
		ShowResultsPanel showResultsPanel = new ShowResultsPanel(experimentationPanel, simulationFacade, this);  // talvez tenha que passar a lista de process
																											// repository dentro de simulation facade
		tabbedPaneActivity4.addTab("4.3. Showing results of alternatives of process", showResultsPanel);
		
		spemDrivenPerspectivePanel.addTab("4. Experimentation of the simulation model", tabbedPaneActivity4);
	}

	private WorkProductResourcesPanel createContentsOfTabMappingWorkProductsToXACDML() {
		WorkProductResourcesPanel workProductResourcesPanel = new WorkProductResourcesPanel();
		
		// teste - em vez de pasar uma lista de String com o nome do work product, passar a lista de tarefas para se pegar os de entrada e saida
		completeListOfProcessContentRepository = processRepository.getProcessContents();
		listOfProcessContentRepositoryTasks = processRepository.getListProcessContentRepository(completeListOfProcessContentRepository);
			
		insertWorkProductIntoMilestone();
			
		workProductResourcesPanel.setWorkProductXACDMLTableModel(listOfProcessContentRepositoryTasks);	// configura JTable dentro da aba 3.2
		
		//	setModelProcessContentRepository pcr = completeListOfProcessContentRepository.get(0); 
		//  workProductResourcesPanel.setModelComboBoxWorkProductForExtendedXACDML(pcr);
		
		workProductResourcesPanel.configuraTableListener();
		
		tabbedPaneActivity3.addTab("3.2. Mapping SPEM work products to XACDML", workProductResourcesPanel.getPanel());
		return workProductResourcesPanel;
	}

	/** 
	 * @author Pagliares
	 * Method to insert work products into milestones. 
	 * We use the workproducts of a task as reference, but it would work for other dummy active states
	 * Repair that if a process has milestone but not task, it would fail miserably
	 * TODO - In the future, resolve this problems and the case with more than one input/output workproducts
	 * 1 - Create new input/output work products based on the first task work products found.
	 * 2 - Add the new work products into the milestone
	 */
	private void insertWorkProductIntoMilestone() {
		
		MethodContentRepository inputMethodContentRepository = null;
		MethodContentRepository outputMethodContentRepository = null;
		
		// 1 - Create new input/output work products based on the first task work products found.
		for (ProcessContentRepository pcr : listOfProcessContentRepositoryTasks) {
			if (pcr.getType().equals(ProcessContentType.TASK)) { 
				Set<MethodContentRepository>setOfInputMethodContentRepository = pcr.getInputMethodContentsRepository();
				for (MethodContentRepository mcrInput :setOfInputMethodContentRepository ) {
					inputMethodContentRepository = new MethodContentRepository();
					inputMethodContentRepository.setId(mcrInput.getId());
					inputMethodContentRepository.setName(mcrInput.getName());
 					inputMethodContentRepository.setType(mcrInput.getType());	
 				}
				
				Set<MethodContentRepository>setOfOutputMethodContentRepository = pcr.getOutputMethodContentsRepository();
				for (MethodContentRepository mcrOutput :setOfOutputMethodContentRepository ) {
					outputMethodContentRepository = new MethodContentRepository();
					outputMethodContentRepository.setId(mcrOutput.getId());
					outputMethodContentRepository.setName(mcrOutput.getName());
					outputMethodContentRepository.setProcessContentRepository(pcr);
					outputMethodContentRepository.setType(mcrOutput.getType());	
 				}
				break;
			}
		}
		
		// 2 - Add the new work products into the milestone
		for (ProcessContentRepository pcr: listOfProcessContentRepositoryTasks) {
			if ((pcr.getType().equals(ProcessContentType.MILESTONE))) {
				inputMethodContentRepository.setProcessContentRepository(pcr);
				outputMethodContentRepository.setProcessContentRepository(pcr);
				pcr.getInputMethodContentsRepository().add(inputMethodContentRepository);			
				pcr.getOutputMethodContentsRepository().add(outputMethodContentRepository);
			}
			
			// Printing to console for debugging purposes. Remove after more detailed tests
			System.out.println("PCR name, \tinput work product name, \toutput work product name, \tinput work product type, \toutput work product type, \ttask associated with the input and output work product");
			System.out.print(pcr);
			System.out.print(" " + pcr.getInputMethodContentsRepository().iterator().next().getName());
			System.out.print("                         " + pcr.getOutputMethodContentsRepository().iterator().next().getName());

			
			System.out.print(" " +pcr.getInputMethodContentsRepository().iterator().next().getType());
			System.out.print(" " +pcr.getOutputMethodContentsRepository().iterator().next().getType());

			System.out.print(" " +pcr.getInputMethodContentsRepository().iterator().next().getProcessElementRepository().getName());
			System.out.print(" " +pcr.getOutputMethodContentsRepository().iterator().next().getProcessElementRepository().getName());
		}	
 	}

	private RoleResourcesPanel createContentsOfTabMappingRolesToXACDML(PersistProcess persistProcess) {
		RoleResourcesPanel roleResourcePanel = new RoleResourcesPanel();
		roleResourcePanel.setComboBoxRole(persistProcess.getRolesList());
		roleResourcePanel.configuraTableListener();
		tabbedPaneActivity3.addTab("3.1. Mapping SPEM Roles to XACDML", roleResourcePanel.getTopPanel());
		return roleResourcePanel;
	}

	private void createContentsOfTabMappingWBE(PersistProcess persistProcess,
			MainPanelSimulationOfAlternativeOfProcess mainPanel) {
		List<String> keySet = new ArrayList<>();

 		
 		HashMap<String, IntegratedLocalAndRepositoryViewPanel> hashMapLocalView = persistProcess.buildGUI(processRepository, keySet);
		 
		
		// main panel é o painel do centro (que esta a local view aninhado)
		for (String key : keySet) {
			System.out.println(key);
			mainPanel.getPanelMainContent().add(hashMapLocalView.get(key));  
//			mainPanel.getListIntegratedLocalAndRepositoryViewPanel().add(hashMapLocalView.get(key));  // teste
			mainPanel.addLayoutComponent(hashMapLocalView.get(key), key);
			

 		}
		
		tabbedPaneActivity3.addTab("3.3. Mapping SPEM work breakdown elements to XACDML", mainPanel.getPanel());
	}

	private int count = 0;

	private NumberTreeNode buildTreeNode(ProcessRepository processRepository) {
		NumberTreeNode root = new NumberTreeNode(processRepository.getName(), "root");
 		for (ProcessContentRepository content : processRepository.getProcessContents()) {
			root.add(buildChidren(content));
		}
 
		return root;
	}

	private NumberTreeNode buildChidren(ProcessContentRepository children) {
		NumberTreeNode node = new NumberTreeNode(children, children.getName());
		node.setNumber(count);
		count++;
		for (ProcessContentRepository content : children.getChildren()) {
			node.add(buildChidren(content));
		}
		return node;
	}
	
	

	public JMenu getFileMenu() {
		return fileMenu;
	}
	
	public JPanel getPanel() {
		return alternativeOfProcessPanel;
	}

	public String getSimulationObjective() {
		return simulationObjective;
	}

	public void setSimulationObjective(String simulationObjective) {
		this.simulationObjective = simulationObjective;
	}
	
	public void closeTabbedPane() {
		spemDrivenPerspectivePanel.removeTab(tabbedPaneActivity4);
		spemDrivenPerspectivePanel.removeTab(tabbedPaneActivity3);
		spemDrivenPerspectivePanel.resetXPProcess(); // para limpar a method library. Nao funcionou
		 
	}
}
