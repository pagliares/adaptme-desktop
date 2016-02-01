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
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.xml.bind.JAXBElement;

import org.eclipse.epf.uma.Element;
import org.eclipse.epf.uma.MethodElement;
import org.eclipse.epf.uma.NamedElement;
import org.eclipse.epf.uma.ObjectFactory;
import org.eclipse.epf.uma.Role;
import org.eclipse.epf.uma.Task;

import adaptme.base.ElementWrapper;
import adaptme.base.MethodLibraryHash;
import adaptme.ui.components.renderer.ListCellRendererCustom;
import adaptme.ui.window.AdaptMeUI;
import adaptme.util.EPFConstants;

public abstract class PanelRoles extends JPanel {

    private static final long serialVersionUID = 6194158465136760008L;
    private Task task;
    private MethodLibraryHash hash;
    private AdaptMeUI owner;
    private TabbedPanel tabbedPanel;
    private JTextArea textAreaBriefDescription;
    private JList<ElementWrapper> listPrimaryPerformers;
    private JList<ElementWrapper> listAdditionalPerformers;
    private JButton btnAddAdditinalPerformers;
    private JButton btnRemovePrimaryPerformers;
    private JButton btnRemoveAdditionalPerformers;
    private JButton btnAddPrimaryPerformers;
    private DocumentListener documentListener;
    private ListDataListener listDataListener;

    public abstract void save();

    public abstract void load();

    public abstract void openDialogPrimaryPerformes(Element element, AdaptMeUI owner, TabbedPanel tabbedPanel);

    public abstract void removePrimaryPerformers(int index, JList<ElementWrapper> listPrimaryPerformes);

    public abstract void openDialogAdditinalPerformers(Element element, AdaptMeUI owner, TabbedPanel tabbedPanel);

    public abstract void removeAdditionalPerformes(int index, JList<ElementWrapper> listAdditionalPerformers);

    public PanelRoles(Task task, TabbedPanel tabbedPanel, MethodLibraryHash hash, AdaptMeUI owner) {

	this.task = task;
	this.hash = hash;
	this.owner = owner;
	this.tabbedPanel = tabbedPanel;
	initCompoments();
    }

    private void initCompoments() {
	addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent e) {
		listAdditionalPerformers.clearSelection();
		listPrimaryPerformers.clearSelection();
	    }
	});
	setBackground(Color.WHITE);

	JLabel lblRoles = new JLabel("Roles");
	lblRoles.setFont(new Font("Tahoma", Font.PLAIN, 13));
	lblRoles.setForeground(Color.BLUE);

	JLabel lblAssignTheRoles = new JLabel("Assign the roles to perform this task.");

	JLabel lblPrimaryPerformers = new JLabel("Primary performers:");

	JScrollPane scrollPane = new JScrollPane();

	JLabel lblAdditionalPerformers = new JLabel("Additional performers:");

	JScrollPane scrollPane_1 = new JScrollPane();

	JLabel lblBriefDescription = new JLabel("Brief description of selected element:");

	JScrollPane scrollPane_2 = new JScrollPane();
	scrollPane_2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

	btnAddPrimaryPerformers = new JButton("Add...");
	btnAddPrimaryPerformers.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent event) {
		openDialogPrimaryPerformes(task, owner, tabbedPanel);
	    }
	});

	btnRemovePrimaryPerformers = new JButton("Remove");
	btnRemovePrimaryPerformers.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		int index = listPrimaryPerformers.getSelectedIndex();
		removePrimaryPerformers(index, listPrimaryPerformers);
	    }
	});
	btnRemovePrimaryPerformers.setEnabled(false);

	btnAddAdditinalPerformers = new JButton("Add...");
	btnAddAdditinalPerformers.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent event) {
		openDialogAdditinalPerformers(task, owner, tabbedPanel);
	    }
	});

	btnRemoveAdditionalPerformers = new JButton("Remove");
	btnRemoveAdditionalPerformers.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		int index = listAdditionalPerformers.getSelectedIndex();
		removeAdditionalPerformes(index, listAdditionalPerformers);
	    }
	});
	btnRemoveAdditionalPerformers.setEnabled(false);
	GroupLayout groupLayout = new GroupLayout(this);
	groupLayout
		.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup().addContainerGap()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
					.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 383,
						Short.MAX_VALUE)
					.addGroup(
						groupLayout.createSequentialGroup()
							.addGroup(
								groupLayout.createParallelGroup(Alignment.TRAILING)
									.addComponent(lblRoles, Alignment.LEADING)
									.addComponent(lblAssignTheRoles,
										Alignment.LEADING)
							.addComponent(lblPrimaryPerformers, Alignment.LEADING)
							.addComponent(lblAdditionalPerformers, Alignment.LEADING)
							.addComponent(lblBriefDescription, Alignment.LEADING)
							.addComponent(scrollPane_1, Alignment.LEADING,
								GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
					.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 306,
						Short.MAX_VALUE))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
					.addComponent(btnRemoveAdditionalPerformers, GroupLayout.DEFAULT_SIZE,
						GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(btnAddPrimaryPerformers, GroupLayout.DEFAULT_SIZE,
						GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(btnRemovePrimaryPerformers, GroupLayout.DEFAULT_SIZE,
						GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(btnAddAdditinalPerformers, GroupLayout.DEFAULT_SIZE,
						GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
				.addContainerGap()));
	groupLayout
		.setVerticalGroup(
			groupLayout
				.createParallelGroup(
					Alignment.LEADING)
				.addGroup(
					groupLayout.createSequentialGroup()
						.addGroup(
							groupLayout
								.createParallelGroup(
									Alignment.LEADING)
								.addGroup(
									groupLayout.createSequentialGroup()
										.addContainerGap()
										.addComponent(lblRoles)
										.addPreferredGap(
											ComponentPlacement.RELATED)
									.addComponent(lblAssignTheRoles)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblPrimaryPerformers)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(scrollPane,
										GroupLayout.PREFERRED_SIZE, 140,
										GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblAdditionalPerformers)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(lblBriefDescription))
			.addGroup(groupLayout.createSequentialGroup().addGap(88).addComponent(btnAddPrimaryPerformers)
				.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnRemovePrimaryPerformers)
				.addGap(112).addComponent(btnAddAdditinalPerformers)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(btnRemoveAdditionalPerformers)))
		.addPreferredGap(ComponentPlacement.RELATED)
		.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
		.addContainerGap(25, Short.MAX_VALUE)));

	textAreaBriefDescription = new JTextArea();
	textAreaBriefDescription.setLineWrap(true);
	textAreaBriefDescription.setWrapStyleWord(true);
	scrollPane_2.setViewportView(textAreaBriefDescription);

	listAdditionalPerformers = new JList<>();
	listAdditionalPerformers.addListSelectionListener(new ListSelectionListener() {
	    public void valueChanged(ListSelectionEvent e) {
		listPrimaryPerformers.clearSelection();
		if (listAdditionalPerformers.getSelectedValue() == null) {
		    btnRemoveAdditionalPerformers.setEnabled(false);
		    setBriefDescription("");
		    return;
		} else {
		    btnRemoveAdditionalPerformers.setEnabled(true);
		}
		Element element = listAdditionalPerformers.getSelectedValue().getElement();
		if (element == null) {
		    return;
		}
		Role role = (Role) element;
		setBriefDescription(role.getBriefDescription());
	    }
	});
	listAdditionalPerformers.setCellRenderer(new ListCellRendererCustom());
	ListModel<ElementWrapper> model = new DefaultListModel<>();
	listAdditionalPerformers.setModel(model);
	scrollPane_1.setViewportView(listAdditionalPerformers);

	listPrimaryPerformers = new JList<>();
	ListModel<ElementWrapper> listPrimaryPerformersModel = new DefaultListModel<>();
	listPrimaryPerformers.setModel(listPrimaryPerformersModel);
	listPrimaryPerformers.addListSelectionListener(new ListSelectionListener() {
	    public void valueChanged(ListSelectionEvent e) {
		listAdditionalPerformers.clearSelection();
		if (listPrimaryPerformers.getSelectedValue() == null) {
		    btnRemovePrimaryPerformers.setEnabled(false);
		    setBriefDescription("");
		    return;
		} else {
		    btnRemovePrimaryPerformers.setEnabled(true);
		}
		Element element = listPrimaryPerformers.getSelectedValue().getElement();
		if (element == null) {
		    return;
		}
		Role role = (Role) element;
		setBriefDescription(role.getBriefDescription());
	    }
	});
	listPrimaryPerformers.setCellRenderer(new ListCellRendererCustom());
	scrollPane.setViewportView(listPrimaryPerformers);
	setLayout(groupLayout);
    }

    public List<String> getPrimaryPerformers() {
	ListModel<ElementWrapper> model = listPrimaryPerformers.getModel();
	List<String> list = new ArrayList<>();
	for (int i = 0; i < model.getSize(); i++) {
	    list.add(((MethodElement) model.getElementAt(i).getElement()).getId());
	}
	return list;
    }

    public List<JAXBElement<String>> getAdditionalPerformers() {
	ListModel<ElementWrapper> model = listAdditionalPerformers.getModel();
	List<JAXBElement<String>> list = new ArrayList<>();
	ObjectFactory objectFactory = new ObjectFactory();
	for (int i = 0; i < model.getSize(); i++) {
	    JAXBElement<String> jaxbElement = objectFactory
		    .createTaskAdditionallyPerformedBy(((MethodElement) model.getElementAt(i).getElement()).getId());
	    list.add(jaxbElement);
	}
	return list;
    }

    public void setPrimaryPerformers(List<String> list) {
	List<String> oldList = getPrimaryPerformers();
	for (String id : list) {
	    oldList.add(id);
	}
	ListModel<ElementWrapper> model = listPrimaryPerformers.getModel();
	((DefaultListModel<ElementWrapper>) model).clear();
	for (String id : oldList) {
	    NamedElement element = (NamedElement) hash.getHashMap().get(id);
	    ((DefaultListModel<ElementWrapper>) model)
		    .addElement(new ElementWrapper(element, element.getName(), EPFConstants.roleIcon));
	}
	listPrimaryPerformers.setModel(model);
    }

    public String getBriefDescription() {
	return textAreaBriefDescription.getText();
    }

    public void setBriefDescription(String text) {
	textAreaBriefDescription.setText(text);
    }

    public void setAdditionalPerformers(List<JAXBElement<String>> list) {
	ListModel<ElementWrapper> model = listAdditionalPerformers.getModel();
	for (JAXBElement<String> jaxbElement : list) {
	    if (jaxbElement.getName().getLocalPart().equals("AdditionallyPerformedBy")) {
		Element element = hash.getHashMap().get(jaxbElement.getValue());
		if (element instanceof Role) {
		    Role role = (Role) element;
		    ((DefaultListModel<ElementWrapper>) model)
			    .addElement(new ElementWrapper(role, role.getName(), EPFConstants.roleIcon));
		}
	    }
	}
	listAdditionalPerformers.setModel(model);
    }

    public void setChangeListeners(DocumentListener documentListener, ListDataListener listDataListener) {

	this.documentListener = documentListener;
	this.listDataListener = listDataListener;

	listPrimaryPerformers.getModel().addListDataListener(this.listDataListener);
	listAdditionalPerformers.getModel().addListDataListener(this.listDataListener);

    }

    public DocumentListener getDocumentListener() {
	return documentListener;
    }

    public ListDataListener getListDataListener() {
	return listDataListener;
    }
}
