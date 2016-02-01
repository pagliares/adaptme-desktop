package adaptme.ui.panel.base;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.eclipse.epf.uma.Artifact;
import org.eclipse.epf.uma.Deliverable;
import org.eclipse.epf.uma.MethodElement;
import org.eclipse.epf.uma.Outcome;
import org.eclipse.epf.uma.Task;
import org.eclipse.epf.uma.WorkProduct;

import adaptme.base.ElementWrapper;
import adaptme.base.MethodLibraryHash;
import adaptme.ui.components.renderer.ListCellRendererCustom;
import adaptme.ui.window.AdaptMeUI;
import adaptme.util.EPFConstants;

public abstract class TaskPanelWorkProducts extends JPanel {

    private static final long serialVersionUID = -7964677800651199862L;
    private Task task;
    // private MethodLibraryHash hash;
    private AdaptMeUI owner;
    private TabbedPanel tabbedPanel;

    private JTextPane textPaneBriefDescription;
    private JList<ElementWrapper> listOptionalInputs;
    private JList<ElementWrapper> listOutputs;
    private JList<ElementWrapper> listMandatoryInputs;
    private JButton btnAddOptionalInputs;
    private JButton btnAddOutputs;
    private JButton btnRemoveOutputs;
    private JButton btnRemoveOptionalInputs;
    private JButton btnRemoveMandatoryInputs;
    private DocumentListener documentListener;
    private ListDataListener listDataListener;

    public abstract void save();

    public abstract void load();

    public abstract void openDialogSelectMandatoryInputs(Task task, AdaptMeUI owner, TabbedPanel tabbedPanel);

    public abstract void openDialogSelectOutputs(Task task, AdaptMeUI owner, TabbedPanel tabbedPanel);

    public abstract void openDialogSelectOptionalInputs(Task task, AdaptMeUI owner, TabbedPanel tabbedPanel);

    public abstract void removeMandatoryInputs(int index, JList<ElementWrapper> listMandatoryInputs);

    public abstract void removeOutputs(int index, JList<ElementWrapper> listOutputs);

    public abstract void removeOptionalInputs(int index, JList<ElementWrapper> listOptionalInputs);

    public TaskPanelWorkProducts(Task task, TabbedPanel tabbedPanelTask, MethodLibraryHash hash, AdaptMeUI owner) {
	addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent e) {
		listMandatoryInputs.clearSelection();
		listOutputs.clearSelection();
		listMandatoryInputs.clearSelection();

		btnRemoveOutputs.setEnabled(false);
		btnRemoveOptionalInputs.setEnabled(false);
		btnRemoveMandatoryInputs.setEnabled(false);
		setBriefDescription("");
	    }
	});
	this.task = task;
	// this.hash = hash;
	this.owner = owner;
	this.tabbedPanel = tabbedPanelTask;
	initComponents();
    }

    private void initComponents() {
	setBackground(Color.WHITE);

	JLabel lblWorkProducts = new JLabel("Work Products:");
	lblWorkProducts.setForeground(Color.BLUE);
	lblWorkProducts.setFont(new Font("Tahoma", Font.PLAIN, 13));

	JLabel lblSpecifyWorkProducts = new JLabel("Specify the input and output work products for this task.");

	JScrollPane scrollPane_0 = new JScrollPane();

	JButton btnAddMandatoryInputs = new JButton("Add...");
	btnAddMandatoryInputs.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent event) {
		openDialogSelectMandatoryInputs(task, owner, tabbedPanel);
	    }
	});

	JScrollPane scrollPane_1 = new JScrollPane();

	btnRemoveMandatoryInputs = new JButton("Remove");
	btnRemoveMandatoryInputs.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		int index = listMandatoryInputs.getSelectedIndex();
		removeMandatoryInputs(index, listMandatoryInputs);
	    }

	});
	btnRemoveMandatoryInputs.setEnabled(false);

	JLabel lblBriefDescriptionOf = new JLabel("Brief description of selected element:");

	JScrollPane scrollPane = new JScrollPane();
	scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

	JLabel lblTask = new JLabel("Task:");

	JLabel lblMandatoryInputs = new JLabel("Mandatory inputs:");

	JLabel lblOptionalInputs = new JLabel("Optional inputs:");

	JScrollPane scrollPane_2 = new JScrollPane();

	JLabel lblOutputs = new JLabel("Outputs:");

	btnAddOptionalInputs = new JButton("Add...");
	btnAddOptionalInputs.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent event) {
		openDialogSelectOptionalInputs(task, owner, tabbedPanel);
	    }
	});

	btnRemoveOptionalInputs = new JButton("Remove");
	btnRemoveOptionalInputs.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		int index = listOptionalInputs.getSelectedIndex();
		removeOptionalInputs(index, listOptionalInputs);
	    }
	});
	btnRemoveOptionalInputs.setEnabled(false);

	btnAddOutputs = new JButton("Add..");
	btnAddOutputs.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent event) {
		openDialogSelectOutputs(task, owner, tabbedPanel);
	    }
	});

	btnRemoveOutputs = new JButton("Remove");
	btnRemoveOutputs.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		int index = listOutputs.getSelectedIndex();
		removeOutputs(index, listOutputs);
	    }
	});
	btnRemoveOutputs.setEnabled(false);

	GroupLayout groupLayout = new GroupLayout(this);
	groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
		.createSequentialGroup().addContainerGap()
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(Alignment.TRAILING,
				groupLayout.createSequentialGroup().addGroup(groupLayout
					.createParallelGroup(Alignment.LEADING).addComponent(lblWorkProducts)
					.addGroup(groupLayout.createSequentialGroup()
						.addComponent(scrollPane_0, GroupLayout.DEFAULT_SIZE, 325,
							Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
							.addComponent(btnAddMandatoryInputs, GroupLayout.DEFAULT_SIZE,
								GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnRemoveMandatoryInputs)))
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnAddOptionalInputs, GroupLayout.DEFAULT_SIZE,
							GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnRemoveOptionalInputs, GroupLayout.DEFAULT_SIZE,
							GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
					.addContainerGap())
			.addGroup(Alignment.TRAILING,
				groupLayout.createSequentialGroup().addComponent(lblTask).addContainerGap(400,
					Short.MAX_VALUE))
			.addGroup(Alignment.TRAILING,
				groupLayout.createSequentialGroup().addComponent(lblSpecifyWorkProducts)
					.addContainerGap(34, Short.MAX_VALUE))
			.addGroup(Alignment.TRAILING,
				groupLayout.createSequentialGroup().addComponent(lblMandatoryInputs)
					.addContainerGap(307, Short.MAX_VALUE))
			.addGroup(Alignment.TRAILING,
				groupLayout.createSequentialGroup().addComponent(lblOptionalInputs).addContainerGap(323,
					Short.MAX_VALUE))
			.addGroup(Alignment.TRAILING,
				groupLayout.createSequentialGroup().addComponent(lblBriefDescriptionOf).addGap(171))
			.addGroup(Alignment.TRAILING,
				groupLayout.createSequentialGroup()
					.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnAddOutputs, GroupLayout.DEFAULT_SIZE, 89,
							Short.MAX_VALUE)
						.addComponent(btnRemoveOutputs, GroupLayout.DEFAULT_SIZE,
							GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap())
			.addGroup(Alignment.TRAILING,
				groupLayout.createSequentialGroup().addComponent(lblOutputs).addContainerGap(375,
					Short.MAX_VALUE))
			.addGroup(groupLayout.createSequentialGroup()
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
				.addGap(119)))));
	groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
		.createSequentialGroup().addGap(
			12)
		.addComponent(lblTask).addGap(18)
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup().addComponent(lblWorkProducts)
				.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblSpecifyWorkProducts)
				.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblMandatoryInputs).addGap(8)
				.addComponent(scrollPane_0, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE))
			.addGroup(groupLayout.createSequentialGroup().addGap(73).addComponent(btnAddMandatoryInputs)
				.addGap(5).addComponent(btnRemoveMandatoryInputs)))
		.addGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(lblOptionalInputs)
					.addGap(13)
					.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 95,
						GroupLayout.PREFERRED_SIZE)
			.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(lblOutputs).addGap(7)
			.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
			.addPreferredGap(ComponentPlacement.RELATED, 72, Short.MAX_VALUE)
			.addComponent(lblBriefDescriptionOf).addPreferredGap(ComponentPlacement.UNRELATED))
			.addGroup(groupLayout.createSequentialGroup().addGap(47).addComponent(btnAddOptionalInputs)
				.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnRemoveOptionalInputs)
				.addGap(75).addComponent(btnAddOutputs).addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(btnRemoveOutputs)))
		.addPreferredGap(ComponentPlacement.UNRELATED)
		.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE).addGap(95)));

	listOutputs = new JList<ElementWrapper>();
	listOutputs.addListSelectionListener(new ListSelectionListener() {
	    public void valueChanged(ListSelectionEvent e) {
		listMandatoryInputs.clearSelection();
		listOptionalInputs.clearSelection();
		btnRemoveOptionalInputs.setEnabled(false);
		btnRemoveMandatoryInputs.setEnabled(false);
		if (listOutputs.getSelectedIndex() == -1) {
		    return;
		}
		btnRemoveOutputs.setEnabled(true);
		MethodElement methodElement = (MethodElement) listOutputs.getSelectedValue().getElement();
		setBriefDescription(methodElement.getBriefDescription());
	    }
	});
	DefaultListModel<ElementWrapper> modelOutputs = new DefaultListModel<>();
	listOutputs.setModel(modelOutputs);
	listOutputs.setCellRenderer(new ListCellRendererCustom());
	scrollPane_2.setViewportView(listOutputs);

	textPaneBriefDescription = new JTextPane();
	scrollPane.setViewportView(textPaneBriefDescription);

	listOptionalInputs = new JList<>();
	listOptionalInputs.addListSelectionListener(new ListSelectionListener() {
	    public void valueChanged(ListSelectionEvent e) {
		listMandatoryInputs.clearSelection();
		listOutputs.clearSelection();
		btnRemoveOutputs.setEnabled(false);
		btnRemoveMandatoryInputs.setEnabled(false);
		if (listOptionalInputs.getSelectedIndex() == -1) {
		    return;
		}
		btnRemoveOptionalInputs.setEnabled(true);
		MethodElement methodElement = (MethodElement) listOptionalInputs.getSelectedValue().getElement();
		setBriefDescription(methodElement.getBriefDescription());
	    }
	});
	DefaultListModel<ElementWrapper> modelOptionalInputs = new DefaultListModel<>();
	listOptionalInputs.setModel(modelOptionalInputs);
	listOptionalInputs.setCellRenderer(new ListCellRendererCustom());
	scrollPane_1.setViewportView(listOptionalInputs);

	listMandatoryInputs = new JList<ElementWrapper>();
	listMandatoryInputs.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	DefaultListModel<ElementWrapper> modelMandatoryInputs = new DefaultListModel<>();
	listMandatoryInputs.setModel(modelMandatoryInputs);
	listMandatoryInputs.addListSelectionListener(new ListSelectionListener() {
	    public void valueChanged(ListSelectionEvent e) {
		listOptionalInputs.clearSelection();
		listOutputs.clearSelection();
		btnRemoveOutputs.setEnabled(false);
		btnRemoveOptionalInputs.setEnabled(false);
		if (listMandatoryInputs.getSelectedIndex() == -1) {
		    return;
		}
		btnRemoveMandatoryInputs.setEnabled(true);
		MethodElement methodElement = (MethodElement) listMandatoryInputs.getSelectedValue().getElement();
		setBriefDescription(methodElement.getBriefDescription());
	    }
	});
	listMandatoryInputs.setCellRenderer(new ListCellRendererCustom());
	scrollPane_0.setViewportView(listMandatoryInputs);
	setLayout(groupLayout);
    }

    public void setBriefDescription(String text) {
	textPaneBriefDescription.setText(text);
    }

    public String getBriefDescription() {
	return textPaneBriefDescription.getText();
    }

    public void setOptionalInputs(List<WorkProduct> list) {
	DefaultListModel<ElementWrapper> model = (DefaultListModel<ElementWrapper>) listOptionalInputs.getModel();
	for (WorkProduct workProduct : list) {
	    if (workProduct instanceof Artifact) {
		model.addElement(new ElementWrapper(workProduct, workProduct.getName(), EPFConstants.artifactIcon));
	    } else if (workProduct instanceof Deliverable) {
		model.addElement(new ElementWrapper(workProduct, workProduct.getName(), EPFConstants.deliverableIcon));
	    } else if (workProduct instanceof Outcome) {
		model.addElement(new ElementWrapper(workProduct, workProduct.getName(), EPFConstants.outcomeIcon));
	    }
	}
	listOptionalInputs.setModel(model);
    }

    public List<WorkProduct> getOptionalInputs() {
	ListModel<ElementWrapper> model = listOptionalInputs.getModel();
	List<WorkProduct> list = new ArrayList<>();
	for (int i = 0; i < model.getSize(); i++) {
	    ElementWrapper ew = model.getElementAt(i);
	    list.add(((WorkProduct) ew.getElement()));
	}
	return list;
    }

    public List<WorkProduct> getMandatoryInputs() {
	DefaultListModel<ElementWrapper> model = (DefaultListModel<ElementWrapper>) listMandatoryInputs.getModel();
	List<WorkProduct> list = new ArrayList<>();
	for (int i = 0; i < model.getSize(); i++) {
	    list.add((WorkProduct) model.getElementAt(i).getElement());
	}
	return list;
    }

    public void setMandatoryInputs(List<WorkProduct> list) {
	DefaultListModel<ElementWrapper> model = (DefaultListModel<ElementWrapper>) listMandatoryInputs.getModel();
	for (WorkProduct workProduct : list) {
	    if (workProduct instanceof Artifact) {
		model.addElement(new ElementWrapper(workProduct, workProduct.getName(), EPFConstants.artifactIcon));
	    } else if (workProduct instanceof Deliverable) {
		model.addElement(new ElementWrapper(workProduct, workProduct.getName(), EPFConstants.deliverableIcon));
	    } else if (workProduct instanceof Outcome) {
		model.addElement(new ElementWrapper(workProduct, workProduct.getName(), EPFConstants.outcomeIcon));
	    }
	}
	listMandatoryInputs.setModel(model);
    }

    public List<WorkProduct> getOutputs() {
	DefaultListModel<ElementWrapper> model = (DefaultListModel<ElementWrapper>) listOutputs.getModel();
	List<WorkProduct> list = new ArrayList<>();
	for (int i = 0; i < model.getSize(); i++) {
	    list.add((WorkProduct) model.getElementAt(i).getElement());
	}
	return list;
    }

    public void setOutputs(List<WorkProduct> list) {
	DefaultListModel<ElementWrapper> model = (DefaultListModel<ElementWrapper>) listOutputs.getModel();
	for (WorkProduct workProduct : list) {
	    if (workProduct instanceof Artifact) {
		model.addElement(new ElementWrapper(workProduct, workProduct.getName(), EPFConstants.artifactIcon));
	    } else if (workProduct instanceof Deliverable) {
		model.addElement(new ElementWrapper(workProduct, workProduct.getName(), EPFConstants.deliverableIcon));
	    } else if (workProduct instanceof Outcome) {
		model.addElement(new ElementWrapper(workProduct, workProduct.getName(), EPFConstants.outcomeIcon));
	    }
	}
	listOutputs.setModel(model);
    }

    public void setChangeListeners(DocumentListener documentListener, ListDataListener listDataListener) {

	this.documentListener = documentListener;
	this.listDataListener = listDataListener;

	listOptionalInputs.getModel().addListDataListener(this.listDataListener);
	listOutputs.getModel().addListDataListener(this.listDataListener);
	listMandatoryInputs.getModel().addListDataListener(this.listDataListener);

    }

    public DocumentListener getDocumentListener() {
	return documentListener;
    }

    public ListDataListener getListDataListener() {
	return listDataListener;
    }
}
