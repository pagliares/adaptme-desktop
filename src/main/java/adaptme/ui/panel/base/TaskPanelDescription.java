package adaptme.ui.panel.base;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListDataListener;
import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.epf.uma.MethodElement;
import org.eclipse.epf.uma.NamedElement;
import org.eclipse.epf.uma.Task;
import org.eclipse.epf.uma.VariabilityType;

import adaptme.base.ElementWrapper;
import adaptme.base.MethodLibraryHash;
import adaptme.base.enums.MethodContentVariability;
import adaptme.ui.components.renderer.ListCellRendererCustom;
import adaptme.ui.window.AdaptMeUI;
import adaptme.util.EPFConstants;

public abstract class TaskPanelDescription extends JPanel {

    private static final long serialVersionUID = 8579542977730551647L;
    private JButton btnDeselectCopyright;
    private JButton btnSelectBase;
    private JButton btnSelectCopyright;
    private JComboBox<MethodContentVariability> comboBoxVariabilityType;

    private JList<ElementWrapper> listBase;
    private JList<ElementWrapper> listCopyright;
    private MethodLibraryHash hash;
    private AdaptMeUI owner;
    private TabbedPanel tabbedPanel;
    private Task task;
    private JTextArea textAreaAuthors;
    private JTextArea textAreaBriefDescription;
    private JTextArea textAreaChangeDescription;
    private JTextPane textAreaKeyConsiderations;
    private JTextPane textAreaMainDescription;
    private JTextField textFieldDate;
    private JTextField textFieldName;
    private JTextField textFieldPresentationName;
    private JTextField textFieldVersion;
    private JTextPane textPaneAlternatives;
    private JTextPane textPanePurpose;
    private DocumentListener documentListener;
    private ListDataListener listDataListener;

    public abstract void load();

    public abstract void save();

    public abstract void removeCopyright(String id, JList<ElementWrapper> listCopyright);

    public abstract void openDialogSelectCopyright(Task task, AdaptMeUI owner, TabbedPanel tabbedPanel);

    public abstract void openDialogSelectBase(Task task, AdaptMeUI owner, TabbedPanel tabbedPanel);

    public TaskPanelDescription(Task task, TabbedPanel tabbedPanel, MethodLibraryHash hash, AdaptMeUI owner) {
	this.task = task;
	this.hash = hash;
	this.owner = owner;
	this.tabbedPanel = tabbedPanel;
	initComponets();
    }

    public String getAlternatives() {
	return textPaneAlternatives.getText();
    }

    public String getAuthors() {
	return textAreaAuthors.getText();
    }

    public String getBase() {
	if (listBase.getModel().getSize() > 0) {
	    String id = ((Task) listBase.getModel().getElementAt(0).getElement()).getId();
	    return id;
	} else {
	    return null;
	}
    }

    public String getBriefDescription() {
	return textAreaBriefDescription.getText();
    }

    public String getChangeDate() {
	return textFieldDate.getText();
    }

    public String getChangeDescription() {
	return textAreaChangeDescription.getText();
    }

    public String getCopyright() {
	if (listCopyright.getModel().getSize() > 0) {
	    return ((MethodElement) ((ElementWrapper) listCopyright.getModel().getElementAt(0)).getElement()).getId();
	} else {
	    return null;
	}
    }

    public String getKeyConsiderations() {
	return textAreaKeyConsiderations.getText();
    }

    public String getMainDescription() {
	return textAreaMainDescription.getText();
    }

    public String getPresentationName() {
	return textFieldPresentationName.getText();
    }

    public String getPurpose() {
	return textPanePurpose.getText();
    }

    public String gettextFieldName() {
	return textFieldName.getText();
    }

    public String getVersion() {
	return textFieldVersion.getText();
    }

    private void initComponets() {
	setBackground(Color.WHITE);
	JLabel lblTask = new JLabel("Task");

	JLabel lblGeneralInformation = new JLabel("General Information");
	lblGeneralInformation.setFont(new Font("Tahoma", Font.PLAIN, 13));
	lblGeneralInformation.setForeground(Color.BLUE);

	JLabel lblName = new JLabel("Name:");

	JLabel lblPresentationName = new JLabel("Presentation name:");

	JLabel lblBriefDescription = new JLabel("Brief description:");

	JLabel lblDetailInformation = new JLabel("Detail Information");
	lblDetailInformation.setFont(new Font("Tahoma", Font.PLAIN, 13));
	lblDetailInformation.setForeground(Color.BLUE);

	JLabel lblMainDescription = new JLabel("Main description:");

	JLabel lblkeyConsiderations = new JLabel("<html>Key<br> considerations:<html>");

	JLabel lblVersionInformation = new JLabel("Version Information");
	lblVersionInformation.setFont(new Font("Tahoma", Font.PLAIN, 13));
	lblVersionInformation.setForeground(Color.BLUE);

	JLabel lblProvideVersionInformation = new JLabel("Provide version information about this task.");

	JLabel lblVersion = new JLabel("Version:");

	JLabel lblChangeDate = new JLabel("Change date:");

	JLabel lblChangeDescription = new JLabel("Change description:");

	JLabel lblAuthors = new JLabel("Authors:");

	JLabel lblCopyright = new JLabel("Copyright:");

	JLabel lblContentVariability = new JLabel("Content Variability");
	lblContentVariability.setFont(new Font("Tahoma", Font.PLAIN, 13));
	lblContentVariability.setForeground(Color.BLUE);

	JLabel lblSpecifyHowThis = new JLabel("Specify how this task relates to another task.");

	JLabel lblVariabilityType = new JLabel("Variability type:");

	JLabel lblBase = new JLabel("Base:");

	textFieldName = new JTextField();
	textFieldName.setColumns(10);

	JScrollPane scrollPane_1 = new JScrollPane();
	scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

	JScrollPane scrollPane_2 = new JScrollPane();
	scrollPane_2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

	JScrollPane scrollPane_3 = new JScrollPane();
	scrollPane_3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

	JScrollPane scrollPane_7 = new JScrollPane();
	scrollPane_7.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

	JScrollPane scrollPane_8 = new JScrollPane();
	scrollPane_8.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

	JScrollPane scrollPane_9 = new JScrollPane();

	textFieldVersion = new JTextField();

	textFieldDate = new JTextField();
	textFieldDate.setEditable(false);
	Color bgColor = UIManager.getColor("TextField.background");
	textFieldDate.setBackground(bgColor);
	Color fgColor = UIManager.getColor("TextField.foreground");
	textFieldDate.setDisabledTextColor(fgColor);
	textFieldDate.setBorder(BorderFactory.createEtchedBorder());

	btnSelectCopyright = new JButton("Select...");
	btnSelectCopyright.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent event) {
		openDialogSelectCopyright(task, owner, tabbedPanel);
	    }
	});

	btnDeselectCopyright = new JButton("Deselect...");
	btnDeselectCopyright.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		setCopyright(null);
	    }
	});

	setComboBoxVariabilityType(new JComboBox<MethodContentVariability>());
	getComboBoxVariabilityType().addItemListener(new ItemListener() {
	    public void itemStateChanged(ItemEvent e) {
		MethodContentVariability methodContentVariability = (MethodContentVariability) e.getItem();
		if (methodContentVariability.getVariabilityType() == VariabilityType.CONTRIBUTES) {
		    setPresentationName("");
		}
		if (methodContentVariability == MethodContentVariability.NOT_APPLICAPLE) {
		    btnSelectBase.setEnabled(false);
		    setBase(null);
		} else {
		    btnSelectBase.setEnabled(true);
		}
	    }
	});
	DefaultComboBoxModel<MethodContentVariability> model = new DefaultComboBoxModel<>();
	model.addElement(MethodContentVariability.make(VariabilityType.NA));
	model.addElement(MethodContentVariability.make(VariabilityType.CONTRIBUTES));
	model.addElement(MethodContentVariability.make(VariabilityType.EXTENDS));
	model.addElement(MethodContentVariability.make(VariabilityType.REPLACES));
	model.addElement(MethodContentVariability.make(VariabilityType.EXTENDS_REPLACES));
	getComboBoxVariabilityType().setModel(model);

	btnSelectBase = new JButton("Select...");
	if (task.getVariabilityType() == VariabilityType.NA) {
	    btnSelectBase.setEnabled(false);
	    ;
	}
	btnSelectBase.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent event) {
		openDialogSelectBase(task, owner, tabbedPanel);
	    }
	});
	ListModel<ElementWrapper> listBaseModel = new DefaultListModel<>();

	textAreaChangeDescription = new JTextArea();
	textAreaChangeDescription.setLineWrap(true);
	textAreaChangeDescription.setWrapStyleWord(true);
	scrollPane_7.setViewportView(textAreaChangeDescription);

	textAreaAuthors = new JTextArea();
	textAreaAuthors.setLineWrap(true);
	textAreaAuthors.setWrapStyleWord(true);
	scrollPane_8.setViewportView(textAreaAuthors);

	listCopyright = new JList<>();
	ListModel<ElementWrapper> listCopyrightModel = new DefaultListModel<>();
	listCopyright.setModel(listCopyrightModel);
	listCopyright.setCellRenderer(new ListCellRendererCustom());
	scrollPane_9.setViewportView(listCopyright);

	textAreaKeyConsiderations = new JTextPane();
	textAreaKeyConsiderations.setContentType("text/html");
	scrollPane_3.setViewportView(textAreaKeyConsiderations);

	textAreaMainDescription = new JTextPane();
	textAreaMainDescription.setContentType("text/html");
	scrollPane_2.setViewportView(textAreaMainDescription);

	textAreaBriefDescription = new JTextArea();
	textAreaBriefDescription.setLineWrap(true);
	textAreaBriefDescription.setWrapStyleWord(true);
	scrollPane_1.setViewportView(textAreaBriefDescription);

	textFieldPresentationName = new JTextField();
	textFieldPresentationName.setColumns(10);

	JLabel lblPurpose = new JLabel("Purpose:");

	JScrollPane scrollPane = new JScrollPane();
	scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

	JLabel lblAlternatives = new JLabel("Alternatives:");

	JScrollPane scrollPane_4 = new JScrollPane();
	scrollPane_4.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

	textPaneAlternatives = new JTextPane();
	textPaneAlternatives.setContentType("text/html");
	scrollPane_4.setViewportView(textPaneAlternatives);

	textPanePurpose = new JTextPane();
	textPanePurpose.setContentType("text/html");
	scrollPane.setViewportView(textPanePurpose);

	JScrollPane scrollPane_10 = new JScrollPane();

	listBase = new JList<>();
	listBase.setCellRenderer(new ListCellRendererCustom());
	listBase.setModel(listBaseModel);
	scrollPane_10.setViewportView(listBase);
	GroupLayout groupLayout = new GroupLayout(this);
	groupLayout
		.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup().addGap(10).addComponent(lblTask))
			.addGroup(groupLayout.createSequentialGroup().addGap(10).addComponent(lblGeneralInformation))
			.addGroup(
				groupLayout.createSequentialGroup().addGap(6)
					.addComponent(lblName, GroupLayout.PREFERRED_SIZE, 66,
						GroupLayout.PREFERRED_SIZE)
					.addGap(78)
					.addComponent(textFieldName, GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
					.addGap(15))
		.addGroup(
			groupLayout.createSequentialGroup().addGap(6)
				.addComponent(lblPresentationName, GroupLayout.PREFERRED_SIZE, 130,
					GroupLayout.PREFERRED_SIZE)
				.addGap(14)
				.addComponent(textFieldPresentationName, GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
				.addGap(15))
		.addGroup(
			groupLayout.createSequentialGroup().addGap(6)
				.addComponent(lblBriefDescription, GroupLayout.PREFERRED_SIZE, 121,
					GroupLayout.PREFERRED_SIZE)
				.addGap(23).addComponent(scrollPane_1).addGap(17))
		.addGroup(groupLayout.createSequentialGroup().addGap(6).addComponent(lblDetailInformation))
		.addGroup(groupLayout.createSequentialGroup().addGap(6)
			.addComponent(lblPurpose, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE).addGap(78)
			.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE).addGap(19))
		.addGroup(
			groupLayout.createSequentialGroup().addGap(6)
				.addComponent(lblMainDescription, GroupLayout.PREFERRED_SIZE, 128,
					GroupLayout.PREFERRED_SIZE)
				.addGap(16).addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
				.addGap(19))
		.addGroup(groupLayout.createSequentialGroup().addGap(6).addComponent(lblVersionInformation))
		.addGroup(groupLayout.createSequentialGroup().addGap(6).addComponent(lblProvideVersionInformation,
			GroupLayout.PREFERRED_SIZE, 278, GroupLayout.PREFERRED_SIZE))
		.addGroup(groupLayout.createSequentialGroup().addGap(6)
			.addComponent(lblVersion, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE).addGap(85)
			.addComponent(textFieldVersion, GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE).addGap(19))
		.addGroup(groupLayout.createSequentialGroup().addGap(4).addComponent(lblCopyright).addGap(91)
			.addComponent(scrollPane_9, GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE).addGap(12)
			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(btnSelectCopyright, GroupLayout.PREFERRED_SIZE, 100,
					GroupLayout.PREFERRED_SIZE)
				.addComponent(btnDeselectCopyright, GroupLayout.PREFERRED_SIZE, 100,
					GroupLayout.PREFERRED_SIZE))
			.addGap(19))
		.addGroup(groupLayout.createSequentialGroup().addGap(4).addComponent(lblContentVariability))
		.addGroup(groupLayout.createSequentialGroup().addGap(4).addComponent(lblSpecifyHowThis))
		.addGroup(groupLayout.createSequentialGroup().addGap(4).addComponent(lblVariabilityType).addGap(66)
			.addComponent(comboBoxVariabilityType, 0, 335, Short.MAX_VALUE).addGap(19))
		.addGroup(groupLayout.createSequentialGroup().addGap(4).addComponent(lblBase).addGap(109)
			.addComponent(scrollPane_10, GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE).addGap(13)
			.addComponent(btnSelectBase, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
			.addGap(19))
		.addGroup(groupLayout.createSequentialGroup().addGap(6)
			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(lblkeyConsiderations, GroupLayout.PREFERRED_SIZE, 81,
					GroupLayout.PREFERRED_SIZE)
				.addComponent(lblAlternatives))
			.addGap(63)
			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane_4, GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
				.addComponent(scrollPane_3, GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE))
			.addGap(19))
		.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup().addGap(4)
			.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup().addComponent(lblAuthors).addGap(101)
					.addComponent(scrollPane_8, GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE))
			.addGroup(groupLayout.createSequentialGroup()
				.addComponent(lblChangeDescription, GroupLayout.PREFERRED_SIZE, 136,
					GroupLayout.PREFERRED_SIZE)
				.addGap(10).addComponent(scrollPane_7, GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE))
			.addGroup(
				groupLayout.createSequentialGroup()
					.addComponent(lblChangeDate, GroupLayout.PREFERRED_SIZE, 98,
						GroupLayout.PREFERRED_SIZE)
					.addGap(48)
					.addComponent(textFieldDate, GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)))
			.addGap(19)));
	groupLayout
		.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addGap(6).addComponent(lblTask).addGap(11)
					.addComponent(lblGeneralInformation).addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup().addGap(6)
							.addComponent(lblName))
						.addComponent(textFieldName, GroupLayout.PREFERRED_SIZE,
							GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		.addGap(6)
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup().addGap(6).addComponent(lblPresentationName))
			.addComponent(textFieldPresentationName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
				GroupLayout.PREFERRED_SIZE))
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup().addGap(14).addComponent(lblBriefDescription)
				.addGap(49).addComponent(lblDetailInformation))
			.addGroup(groupLayout.createSequentialGroup().addGap(12).addComponent(scrollPane_1,
				GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)))
		.addGap(12)
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(lblPurpose)
			.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE))
		.addGap(6)
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(lblMainDescription)
			.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE))
		.addGap(12)
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(lblkeyConsiderations)
			.addComponent(scrollPane_3, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE))
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup().addGap(9).addComponent(lblAlternatives).addGap(51)
				.addComponent(lblVersionInformation))
			.addGroup(groupLayout.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(scrollPane_4, GroupLayout.PREFERRED_SIZE, 59,
					GroupLayout.PREFERRED_SIZE))).addGap(18)
		.addComponent(lblProvideVersionInformation).addGap(21)
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(lblVersion)
			.addComponent(textFieldVersion, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
		.addGap(35)
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup().addGap(4).addComponent(lblChangeDate))
			.addComponent(textFieldDate, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup().addGap(19).addComponent(lblChangeDescription))
			.addGroup(groupLayout.createSequentialGroup().addGap(4).addComponent(scrollPane_7,
				GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)))
		.addPreferredGap(ComponentPlacement.RELATED)
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(lblAuthors)
			.addComponent(scrollPane_8, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE))
		.addPreferredGap(ComponentPlacement.RELATED)
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup().addGap(2).addComponent(lblCopyright))
			.addComponent(scrollPane_9, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
			.addGroup(groupLayout.createSequentialGroup().addGap(6).addComponent(btnSelectCopyright)
				.addGap(6).addComponent(btnDeselectCopyright))).addGap(12)
		.addComponent(lblContentVariability).addGap(6).addComponent(lblSpecifyHowThis).addGap(5)
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup().addGap(4).addComponent(lblVariabilityType))
			.addComponent(comboBoxVariabilityType, GroupLayout.PREFERRED_SIZE, 24,
				GroupLayout.PREFERRED_SIZE))
		.addGap(12)
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(lblBase)
			.addComponent(scrollPane_10, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
			.addGroup(groupLayout.createSequentialGroup().addGap(8).addComponent(btnSelectBase)))));
	setLayout(groupLayout);
    }

    public void setAuthors(String text) {
	textAreaAuthors.setText(text);
    }

    public void setBase(String id) {
	if (id == null) {
	    ListModel<ElementWrapper> model = listBase.getModel();
	    ((DefaultListModel<ElementWrapper>) model).clear();
	    listBase.setModel(model);
	    return;
	}
	ListModel<ElementWrapper> model = listBase.getModel();
	((DefaultListModel<ElementWrapper>) model).clear();
	MethodElement methodElement = (MethodElement) hash.getHashMap().get(id);
	((DefaultListModel<ElementWrapper>) model)
		.addElement(new ElementWrapper(methodElement, methodElement.getName(), EPFConstants.taskIcon));
	listBase.setModel(model);
    }

    public void setBriefDescription(String text) {
	textAreaBriefDescription.setText(text);
    }

    public void setChangeDescription(String text) {
	textAreaChangeDescription.setText(text);
    }

    public void setChangeListeners(DocumentListener documentListener, ListDataListener listDataListener) {

	this.documentListener = documentListener;
	this.listDataListener = listDataListener;

	textAreaAuthors.getDocument().addDocumentListener(this.documentListener);
	textAreaBriefDescription.getDocument().addDocumentListener(this.documentListener);
	textAreaChangeDescription.getDocument().addDocumentListener(this.documentListener);
	textAreaKeyConsiderations.getDocument().addDocumentListener(this.documentListener);
	textAreaMainDescription.getDocument().addDocumentListener(this.documentListener);
	textFieldDate.getDocument().addDocumentListener(this.documentListener);
	textFieldName.getDocument().addDocumentListener(this.documentListener);
	textFieldPresentationName.getDocument().addDocumentListener(this.documentListener);
	textFieldVersion.getDocument().addDocumentListener(this.documentListener);
	textPaneAlternatives.getDocument().addDocumentListener(this.documentListener);
	textPanePurpose.getDocument().addDocumentListener(this.documentListener);
	listBase.getModel().addListDataListener(this.listDataListener);
	listCopyright.getModel().addListDataListener(this.listDataListener);
    }

    public DocumentListener getDocumentListener() {
	return documentListener;
    }

    public ListDataListener getListDataListener() {
	return listDataListener;
    }

    public void setCopyright(String id) {
	if (id == null) {
	    ListModel<ElementWrapper> model = listCopyright.getModel();
	    ((DefaultListModel<ElementWrapper>) model).clear();
	    listCopyright.setModel(model);
	    return;
	}
	ListModel<ElementWrapper> model = listCopyright.getModel();
	((DefaultListModel<ElementWrapper>) model).clear();
	NamedElement namedElement = (NamedElement) hash.getHashMap().get(id);
	((DefaultListModel<ElementWrapper>) model)
		.addElement(new ElementWrapper(namedElement, namedElement.getName(), EPFConstants.artifactIcon));
	listCopyright.setModel(model);
    }

    public void setChangeDate(XMLGregorianCalendar xmlGregorianCalendar) {
	SimpleDateFormat dateFormat = new SimpleDateFormat("EEEEE, d MMMMM yyyy HH:mm:ss");
	textFieldDate.setText(dateFormat.format(xmlGregorianCalendar.toGregorianCalendar().getTime()));
    }

    public void setKeyConsiderations(String text) {
	textAreaKeyConsiderations.setText(text);
    }

    public void setMainDescription(String text) {
	textAreaMainDescription.setText(text);
    }

    public void setPresentationName(String text) {
	textFieldPresentationName.setText(text);
    }

    public void setPurpose(String text) {
	textPanePurpose.setText(text);
    }

    public void setTextFieldName(String text) {
	textFieldName.setText(text);
    }

    public void setVariabiliteType(MethodContentVariability methodContentVariability) {
	getComboBoxVariabilityType().setSelectedItem(methodContentVariability);
    }

    public void setVersion(String text) {
	textFieldVersion.setText(text);
    }

    public JComboBox<MethodContentVariability> getComboBoxVariabilityType() {
	return comboBoxVariabilityType;
    }

    public void setComboBoxVariabilityType(JComboBox<MethodContentVariability> comboBoxVariabilityType) {
	this.comboBoxVariabilityType = comboBoxVariabilityType;
    }
}
