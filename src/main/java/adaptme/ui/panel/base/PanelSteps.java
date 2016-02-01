package adaptme.ui.panel.base;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.eclipse.epf.uma.MethodElement;
import org.eclipse.epf.uma.Section;
import org.eclipse.epf.uma.Task;
import org.eclipse.epf.uma.TaskDescription;

import adaptme.base.ElementWrapper;
import adaptme.base.MethodLibraryHash;
import adaptme.ui.components.renderer.ListCellRendererCustom;
import adaptme.ui.window.AdaptMeUI;
import adaptme.util.EPFConstants;

public abstract class PanelSteps extends JPanel {

    private static final long serialVersionUID = 3095333858107691424L;
    private JTextField textFieldName;
    private Task task;
    // private MethodLibraryHash hash;
    // private AdaptMeUI adaptMeUI;
    // private TabbedPanel tabbedPanel;
    private JList<ElementWrapper> listSteps;
    private JTextArea textAreaDescription;

    private DocumentListener documentListener;

    private ListDataListener listDataListener;

    public PanelSteps(Task task, TabbedPanel tabbedPanel, MethodLibraryHash hash, AdaptMeUI owner) {
	this.task = task;
	// this.hash = hash;
	// this.adaptMeUI = owner;
	// this.tabbedPanel = tabbedPanel;
	initCompoments();
    }

    private void initCompoments() {
	setBackground(Color.WHITE);

	JLabel lblSteps = new JLabel("Steps");
	lblSteps.setFont(new Font("Tahoma", Font.PLAIN, 13));
	lblSteps.setForeground(Color.BLUE);

	JLabel lblSpecifyTheSteps = new JLabel("Specify the steps to perform this task.");

	JLabel lblSteps_1 = new JLabel("Steps:");

	JScrollPane scrollPane = new JScrollPane();
	scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

	JLabel lblName = new JLabel("Name:");

	textFieldName = new JTextField();
	textFieldName.setColumns(10);

	JButton btnAdd = new JButton("Add");
	btnAdd.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		createSection();
	    }
	});

	JButton btnDelete = new JButton("Delete");
	btnDelete.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		int index = listSteps.getSelectedIndex();
		if (index == -1) {
		    return;
		}
		((DefaultListModel<ElementWrapper>) listSteps.getModel()).remove(index);
	    }
	});

	JButton btnUp = new JButton("Up");
	btnUp.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		int index = listSteps.getSelectedIndex();
		if (index == -1 || index == 0) {
		    return;
		}
		DefaultListModel<ElementWrapper> list = (DefaultListModel<ElementWrapper>) listSteps.getModel();
		list.set(index, list.set(index - 1, list.get(index)));
		listSteps.setModel(list);
		listSteps.setSelectedIndex(index - 1);
	    }
	});

	JButton btnDown = new JButton("Down");
	btnDown.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		int index = listSteps.getSelectedIndex();
		if (index == -1 || index == listSteps.getModel().getSize() - 1) {
		    return;
		}
		DefaultListModel<ElementWrapper> list = (DefaultListModel<ElementWrapper>) listSteps.getModel();
		list.set(index, list.set(index + 1, list.get(index)));
		listSteps.setModel(list);
		listSteps.setSelectedIndex(index + 1);
	    }
	});

	JLabel lblDescription = new JLabel("Description:");

	JScrollPane scrollPane_1 = new JScrollPane();
	GroupLayout groupLayout = new GroupLayout(this);
	groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
		.addGroup(groupLayout.createSequentialGroup().addContainerGap()
			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addComponent(lblName).addContainerGap(422,
					Short.MAX_VALUE))
			.addGroup(groupLayout.createSequentialGroup().addComponent(lblSpecifyTheSteps)
				.addContainerGap(194, Short.MAX_VALUE))
			.addGroup(groupLayout.createSequentialGroup().addComponent(lblSteps_1).addContainerGap(421,
				Short.MAX_VALUE))
			.addGroup(groupLayout.createSequentialGroup().addComponent(lblSteps).addContainerGap(430,
				Short.MAX_VALUE))
			.addGroup(groupLayout.createSequentialGroup().addComponent(lblDescription).addContainerGap(381,
				Short.MAX_VALUE))
			.addGroup(Alignment.TRAILING,
				groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
							390, Short.MAX_VALUE)
					.addComponent(textFieldName, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 384,
						Short.MAX_VALUE)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
					.addComponent(btnUp, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
					.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 71,
						GroupLayout.PREFERRED_SIZE)
					.addComponent(btnAdd, GroupLayout.PREFERRED_SIZE, 71,
						GroupLayout.PREFERRED_SIZE)
					.addComponent(btnDown, GroupLayout.PREFERRED_SIZE, 71,
						GroupLayout.PREFERRED_SIZE))
					.addGap(6)))));
	groupLayout
		.setVerticalGroup(
			groupLayout
				.createParallelGroup(
					Alignment.LEADING)
				.addGroup(
					groupLayout.createSequentialGroup().addGap(6).addComponent(lblSteps)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(lblSpecifyTheSteps)
						.addPreferredGap(
							ComponentPlacement.RELATED)
						.addComponent(lblSteps_1)
						.addPreferredGap(
							ComponentPlacement.UNRELATED)
		.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
			.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 201, GroupLayout.PREFERRED_SIZE)
			.addGroup(groupLayout.createSequentialGroup().addComponent(btnAdd).addGap(7)
				.addComponent(btnDelete).addGap(24).addComponent(btnUp).addGap(5)
				.addComponent(btnDown))).addPreferredGap(ComponentPlacement.RELATED)
		.addComponent(lblName).addPreferredGap(ComponentPlacement.RELATED)
		.addComponent(textFieldName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
			GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.UNRELATED)
		.addComponent(lblDescription).addPreferredGap(ComponentPlacement.RELATED)
		.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 201, GroupLayout.PREFERRED_SIZE)
		.addContainerGap(9, Short.MAX_VALUE)));

	textAreaDescription = new JTextArea();
	textAreaDescription.setLineWrap(true);
	textAreaDescription.setWrapStyleWord(true);
	scrollPane_1.setViewportView(textAreaDescription);

	listSteps = new JList<>();
	listSteps.addListSelectionListener(new ListSelectionListener() {
	    public void valueChanged(ListSelectionEvent e) {
		if (listSteps.getSelectedIndex() == -1) {
		    return;
		}
		MethodElement methodElement = (MethodElement) listSteps.getSelectedValue().getElement();
		setTextName(methodElement.getName());
		setDescripiton(methodElement.getBriefDescription());
	    }
	});
	ListModel<ElementWrapper> model = new DefaultListModel<>();
	listSteps.setModel(model);
	listSteps.setCellRenderer(new ListCellRendererCustom());
	scrollPane.setViewportView(listSteps);
	setLayout(groupLayout);
    }

    public void setSteps(List<Section> list) {
	ListModel<ElementWrapper> model = listSteps.getModel();
	for (Section section : list) {
	    boolean hasEquals = false;
	    for (int i = 0; i < model.getSize(); i++) {
		if ((section.getId().equals(((MethodElement) model.getElementAt(i).getElement()).getId()))) {
		    hasEquals = true;
		    break;
		}
	    }
	    if (!hasEquals) {
		((DefaultListModel<ElementWrapper>) model)
			.addElement(new ElementWrapper(section, section.getName(), EPFConstants.sectionIcon));
	    }
	}
	listSteps.setModel(model);
    }

    public void setSteps(Section section) {
	ListModel<ElementWrapper> model = listSteps.getModel();
	int index = model.getSize();
	((DefaultListModel<ElementWrapper>) model)
		.addElement(new ElementWrapper(section, section.getName(), EPFConstants.sectionIcon));
	listSteps.setModel(model);
	listSteps.setSelectedIndex(index);
	setDescripiton(section.getBriefDescription());
    }

    public List<Section> getSteps() {
	List<Section> list = new ArrayList<>();
	DefaultListModel<ElementWrapper> model = (DefaultListModel<ElementWrapper>) listSteps.getModel();
	for (int i = 0; i < model.getSize(); i++) {
	    list.add((Section) model.getElementAt(i).getElement());
	}
	return list;
    }

    public void load() {
	TaskDescription taskDescription = (TaskDescription) task.getPresentation();
	if (taskDescription == null) {
	    return;
	}
	setSteps(taskDescription.getSection());
    }

    public void save() {
	int index = listSteps.getSelectedIndex();
	List<Section> steps = getSteps();
	if (index != -1) {
	    Section section = steps.get(index);
	    section.setName(getNameSteps());
	    steps.set(index, section);
	}
	TaskDescription taskDescription = (TaskDescription) task.getPresentation();
	List<Section> list = taskDescription.getSection();
	list.clear();
	list.addAll(steps);
    }

    public String getNameSteps() {
	return textFieldName.getText();
    }

    public void setTextName(String text) {
	textFieldName.setText(text);
    }

    public String getDescription() {
	return textAreaDescription.getText();
    }

    public void setDescripiton(String text) {
	textAreaDescription.setText(text);
    }

    private void createSection() {
	Section section = new Section();
	section.setName("New Step");
	section.setId("" + UUID.randomUUID());
	section.setBriefDescription("");
	setSteps(section);
    }

    public void setChangeListeners(DocumentListener documentListener, ListDataListener listDataListener) {

	this.documentListener = documentListener;
	this.listDataListener = listDataListener;

	listSteps.getModel().addListDataListener(this.listDataListener);

    }

    public DocumentListener getDocumentListener() {
	return documentListener;
    }

    public ListDataListener getListDataListener() {
	return listDataListener;
    }
}
