package adaptme.ui.components.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.eclipse.epf.uma.Artifact;
import org.eclipse.epf.uma.Deliverable;
import org.eclipse.epf.uma.Element;
import org.eclipse.epf.uma.Outcome;
import org.eclipse.epf.uma.Role;
import org.eclipse.epf.uma.Task;

import adaptme.base.ElementWrapper;
import adaptme.ui.components.JTreeExpandAll;
import adaptme.ui.components.renderer.TreeCellRendererCustom;
import adaptme.ui.panel.base.TabbedPanel;
import adaptme.ui.window.AdaptMeUI;

public class SelectDialog extends JDialog {

    private static final long serialVersionUID = 2336141117637479094L;
    private JTextField textFieldSearch;
    private JTextArea textAreaBrifDescription;

    private JComboBox<SelectDialogFilter> comboBox;
    private JTree tree;
    private JDialog thisDialog;

    private TabbedPanel tabbedPanel;
    private Element element;
    private SelectDialogFilter filter;
    private DefaultTreeModel model;

    public SelectDialog(Element element, AdaptMeUI owner, TabbedPanel tabbedPanel, String title,
	    SelectDialogFilter filter, boolean modal) {
	super(owner.getFrame(), title, modal);
	setResizable(false);
	this.element = element;
	thisDialog = this;
	this.tabbedPanel = tabbedPanel;
	this.filter = filter;
	initComponents(owner);
    }

    private void initComponents(AdaptMeUI owner) {

	setBounds(0, 0, 500, 692);
	JLabel lblMethodElementType = new JLabel("Method Element Type:");

	DefaultComboBoxModel<SelectDialogFilter> comboBoxModel = new DefaultComboBoxModel<>();
	comboBoxModel.addElement(filter);
	comboBox = new JComboBox<>();
	comboBox.setModel(comboBoxModel);

	JLabel lblNamePatternsmatching = new JLabel("Name patterns (matching names will be shown):");

	textFieldSearch = new JTextField();
	textFieldSearch.setColumns(10);

	JLabel lblPatternsAreSeparated = new JLabel(
		"<html>Patterns are separated by comma, where \": any string, ?: any character</html>");
	model = owner.getTreeModel();

	JLabel lblBriefDescription = new JLabel("Brief Description:");

	JScrollPane scrollPane = new JScrollPane();
	scrollPane.getVerticalScrollBar().setUnitIncrement(16);
	scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	textAreaBrifDescription = new JTextArea();
	textAreaBrifDescription.setLineWrap(true);
	textAreaBrifDescription.setEditable(false);
	textAreaBrifDescription.setWrapStyleWord(true);
	scrollPane.setViewportView(textAreaBrifDescription);
	textAreaBrifDescription.setColumns(10);

	JButton btnOk = new JButton("OK");
	btnOk.addActionListener(event -> {
	    TreePath path = tree.getSelectionPath();
	    if (path == null) {
		return;
	    }
	    DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
	    ElementWrapper elementWrapper = (ElementWrapper) node.getUserObject();
	    Element e = elementWrapper.getElement();
	    if (element instanceof Role) {
		RolePersonalization rolePersonalization = new RolePersonalization(e, (Role) element, thisDialog,
			tabbedPanel, filter);
		rolePersonalization.run();
	    } else if (element instanceof Task) {
		TaskPersonalization taskPersonalization = new TaskPersonalization(e, thisDialog, tabbedPanel, filter);
		taskPersonalization.run();
	    } else if (element instanceof Artifact) {
		ArtifactPersonalization artifactPersonalization = new ArtifactPersonalization(e, thisDialog,
			tabbedPanel, filter);
		artifactPersonalization.run();
	    } else if (element instanceof Outcome) {
		OutcomePersonalization outcomePersonalization = new OutcomePersonalization(e, thisDialog, tabbedPanel,
			filter);
		outcomePersonalization.run();
	    } else if (element instanceof Deliverable) {
		DeliverablePersonalization deliverablePersonalization = new DeliverablePersonalization(e, thisDialog,
			tabbedPanel, filter);
		deliverablePersonalization.run();
	    }

	    thisDialog.dispose();
	});

	JButton btnCancel = new JButton("Cancel");
	btnCancel.addActionListener(e -> thisDialog.dispose());

	JScrollPane scrollPane_1 = new JScrollPane();

	tree = new JTree();
	tree.setCellRenderer(new TreeCellRendererCustom());
	tree.setRootVisible(false);
	tree.setShowsRootHandles(true);
	scrollPane_1.setViewportView(tree);
	tree.setModel(model);

	final JButton btnExpandAll = new JButton("Expand all");
	btnExpandAll.addActionListener(new ActionListener() {
	    boolean isExpanded = false;

	    @Override
	    public void actionPerformed(ActionEvent e) {
		if (!isExpanded) {
		    JTreeExpandAll.expandAll(tree, true);
		    isExpanded = true;
		}
	    }
	});

	GroupLayout groupLayout = new GroupLayout(getContentPane());
	groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
		.addGroup(groupLayout.createSequentialGroup().addContainerGap()
			.addGroup(groupLayout.createParallelGroup(
				Alignment.LEADING)
			.addComponent(lblMethodElementType)
			.addGroup(groupLayout.createSequentialGroup().addGroup(groupLayout
				.createParallelGroup(Alignment.LEADING).addComponent(lblBriefDescription)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(Alignment.TRAILING,
						groupLayout.createSequentialGroup()
							.addComponent(btnOk, GroupLayout.PREFERRED_SIZE, 84,
								GroupLayout.PREFERRED_SIZE)
							.addGap(31).addComponent(btnCancel, GroupLayout.PREFERRED_SIZE,
								86, GroupLayout.PREFERRED_SIZE))
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
				.addComponent(btnExpandAll, Alignment.TRAILING).addComponent(scrollPane_1,
					GroupLayout.PREFERRED_SIZE, 473, GroupLayout.PREFERRED_SIZE))
				.addGap(197))
			.addGroup(
				groupLayout.createSequentialGroup()
					.addComponent(lblNamePatternsmatching, GroupLayout.DEFAULT_SIZE, 526,
						Short.MAX_VALUE)
					.addGap(328))
			.addGroup(Alignment.TRAILING,
				groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(comboBox, Alignment.LEADING, 0, 386, Short.MAX_VALUE)
						.addComponent(textFieldSearch, Alignment.LEADING,
							GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
				.addComponent(lblPatternsAreSeparated, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 473,
					Short.MAX_VALUE)).addGap(197)))));
	groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
		.addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(lblMethodElementType)
			.addPreferredGap(ComponentPlacement.RELATED)
			.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
				GroupLayout.PREFERRED_SIZE)
			.addGap(13).addComponent(lblNamePatternsmatching).addPreferredGap(ComponentPlacement.UNRELATED)
			.addComponent(textFieldSearch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
				GroupLayout.PREFERRED_SIZE)
			.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblPatternsAreSeparated)
			.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnExpandAll)
			.addPreferredGap(ComponentPlacement.UNRELATED)
			.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 309, GroupLayout.PREFERRED_SIZE)
			.addGap(18).addComponent(lblBriefDescription).addPreferredGap(ComponentPlacement.RELATED)
			.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
			.addPreferredGap(ComponentPlacement.RELATED).addGroup(groupLayout
				.createParallelGroup(Alignment.BASELINE).addComponent(btnOk).addComponent(btnCancel))
		.addGap(97)));

	getContentPane().setLayout(groupLayout);
    }
}
