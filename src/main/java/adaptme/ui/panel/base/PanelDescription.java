package adaptme.ui.panel.base;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;

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

import org.eclipse.epf.uma.Element;
import org.eclipse.epf.uma.MethodElement;
import org.eclipse.epf.uma.NamedElement;
import org.eclipse.epf.uma.Role;
import org.eclipse.epf.uma.VariabilityType;

import adaptme.base.ElementWrapper;
import adaptme.base.MethodLibraryHash;
import adaptme.base.enums.MethodContentVariability;
import adaptme.ui.components.renderer.ListCellRendererCustom;
import adaptme.ui.window.AdaptMeUI;
import adaptme.util.EPFConstants;

public abstract class PanelDescription extends JPanel {

    private static final long serialVersionUID = 6573807084888669992L;
    private JButton btnDeselectCopyright;
    private JButton btnSelectBase;
    private JButton btnSelectCopyright;

    private JComboBox<MethodContentVariability> comboBoxVariabilityType;

    private MethodLibraryHash hash;
    private AdaptMeUI owner;
    private Element element;
    private TabbedPanel tabbedPanel;

    private DocumentListener documentListener;
    private ListDataListener listDataListener;

    private JList<ElementWrapper> listBase;
    private JList<ElementWrapper> listCopyright;

    private JTextPane textAreaAssignmentApproaches;
    private JTextArea textAreaAuthors;
    private JTextArea textAreaBriefDescription;
    private JTextArea textAreaChangeDescription;
    private JTextPane textAreaKeyConsiderations;
    private JTextPane textAreaMainDescription;
    private JTextArea textAreaSkills;
    private JTextArea textAreaSynonyms;
    private JTextField textFieldDate;
    private JTextField textFieldName;
    private JTextField textFieldPresentationName;
    private JTextField textFieldVersion;

    public abstract void load();

    public abstract void save();

    public abstract void removeCopyright(String id, JList<ElementWrapper> listCopyright);

    public abstract void openDialogSelectCopyright(Element element, AdaptMeUI owner, TabbedPanel tabbedPanel);

    public abstract void openDialogSelectBase(Element element, AdaptMeUI owner2, TabbedPanel tabbedPanel);

    public PanelDescription(Element element, TabbedPanel tabbedPanel, MethodLibraryHash hash, AdaptMeUI owner) {
	this.element = element;
	this.hash = hash;
	this.owner = owner;
	this.tabbedPanel = tabbedPanel;
	initComponets();
    }

    private void initComponets() {
	setBackground(Color.WHITE);

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

	JLabel lblStaffingInformation = new JLabel("Staffing Information");
	lblStaffingInformation.setFont(new Font("Tahoma", Font.PLAIN, 13));
	lblStaffingInformation.setForeground(Color.BLUE);

	JLabel lblSkills = new JLabel("Skills:");

	JLabel lblassignmentApproaches = new JLabel("<html>Assignment<br> approaches:<html>");

	JLabel lblSynonyms = new JLabel("Synonyms:");

	JLabel lblVersionInformation = new JLabel("Version Information");
	lblVersionInformation.setFont(new Font("Tahoma", Font.PLAIN, 13));
	lblVersionInformation.setForeground(Color.BLUE);

	JLabel lblProvideVersionInformation = new JLabel("Provide version information about this role.");

	JLabel lblVersion = new JLabel("Version:");

	JLabel lblChangeDate = new JLabel("Change date:");

	JLabel lblChangeDescription = new JLabel("Change description:");

	JLabel lblAuthors = new JLabel("Authors:");

	JLabel lblCopyright = new JLabel("Copyright:");

	JLabel lblContentVariability = new JLabel("Content Variability");
	lblContentVariability.setFont(new Font("Tahoma", Font.PLAIN, 13));
	lblContentVariability.setForeground(Color.BLUE);

	JLabel lblSpecifyHowThis = new JLabel("Specify how this role relates to another role.");

	JLabel lblVariabilityType = new JLabel("Variability type:");

	JLabel lblBase = new JLabel("Base:");

	textFieldName = new JTextField();
	textFieldName.setColumns(10);

	JScrollPane scrollPane_1 = new JScrollPane();
	scrollPane_1.getVerticalScrollBar().setUnitIncrement(16);
	scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

	JScrollPane scrollPane_2 = new JScrollPane();
	scrollPane_2.getVerticalScrollBar().setUnitIncrement(16);
	scrollPane_2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

	JScrollPane scrollPane_3 = new JScrollPane();
	scrollPane_3.getVerticalScrollBar().setUnitIncrement(16);
	scrollPane_3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

	JScrollPane scrollPane_4 = new JScrollPane();
	scrollPane_4.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

	JScrollPane scrollPane_5 = new JScrollPane();
	scrollPane_5.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

	JScrollPane scrollPane_6 = new JScrollPane();
	scrollPane_6.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

	JScrollPane scrollPane_7 = new JScrollPane();
	scrollPane_7.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

	JScrollPane scrollPane_8 = new JScrollPane();
	scrollPane_8.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

	JScrollPane scrollPane_9 = new JScrollPane();

	JScrollPane scrollPane_10 = new JScrollPane();

	textFieldVersion = new JTextField();

	textFieldDate = new JTextField();
	textFieldDate.setEditable(false);
	Color bgColor = UIManager.getColor("TextField.background");
	textFieldDate.setBackground(bgColor);
	Color fgColor = UIManager.getColor("TextField.foreground");
	textFieldDate.setDisabledTextColor(fgColor);

	btnSelectCopyright = new JButton("Select...");
	btnSelectCopyright.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		openDialogSelectCopyright(element, owner, tabbedPanel);
	    }
	});

	btnDeselectCopyright = new JButton("Deselect...");
	btnDeselectCopyright.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		removeCopyright(null, listCopyright);
	    }
	});

	comboBoxVariabilityType = new JComboBox<MethodContentVariability>();
	getComboBoxVariabilityType().addItemListener(new ItemListener() {
	    public void itemStateChanged(ItemEvent e) {
		MethodContentVariability methodContentVariability = (MethodContentVariability) e.getItem();
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
	btnSelectBase.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		openDialogSelectBase(element, owner, tabbedPanel);
	    }
	});

	listBase = new JList<>();
	listBase.setCellRenderer(new ListCellRendererCustom());
	ListModel<ElementWrapper> listBaseModel = new DefaultListModel<>();
	listBase.setModel(listBaseModel);
	scrollPane_10.setViewportView(listBase);

	textAreaChangeDescription = new JTextArea();
	textAreaChangeDescription.setLineWrap(true);
	textAreaChangeDescription.setWrapStyleWord(true);
	scrollPane_7.setViewportView(textAreaChangeDescription);

	textAreaAuthors = new JTextArea();
	textAreaAuthors.setLineWrap(true);
	textAreaAuthors.setWrapStyleWord(true);
	scrollPane_8.setViewportView(textAreaAuthors);

	listCopyright = new JList<>();
	listCopyright.setCellRenderer(new ListCellRendererCustom());
	ListModel<ElementWrapper> listCopyrightModel = new DefaultListModel<>();
	listCopyright.setModel(listCopyrightModel);
	scrollPane_9.setViewportView(listCopyright);

	textAreaSynonyms = new JTextArea();
	textAreaSynonyms.setLineWrap(true);
	textAreaSynonyms.setWrapStyleWord(true);
	scrollPane_6.setViewportView(textAreaSynonyms);

	textAreaAssignmentApproaches = new JTextPane();
	textAreaAssignmentApproaches.setContentType("text/html");
	scrollPane_5.setViewportView(textAreaAssignmentApproaches);

	textAreaSkills = new JTextArea();
	textAreaSkills.setLineWrap(true);
	textAreaSkills.setWrapStyleWord(true);
	scrollPane_4.setViewportView(textAreaSkills);

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
	GroupLayout groupLayout = new GroupLayout(this);
	groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
		.addGroup(groupLayout.createSequentialGroup().addGap(10).addComponent(lblGeneralInformation,
			GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE))
		.addGroup(groupLayout.createSequentialGroup().addGap(10)
			.addComponent(lblName, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE).addGap(114)
			.addComponent(textFieldName, GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE).addGap(27))
		.addGroup(groupLayout.createSequentialGroup().addGap(10).addComponent(lblDetailInformation,
			GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE))
		.addGroup(groupLayout.createSequentialGroup().addGap(10)
			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(lblMainDescription, GroupLayout.PREFERRED_SIZE, 107,
					GroupLayout.PREFERRED_SIZE)
			.addComponent(lblkeyConsiderations, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE))
			.addGap(46)
			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane_3, GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
				.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE))
			.addGap(28))
		.addGroup(groupLayout.createSequentialGroup().addGap(10).addComponent(lblStaffingInformation,
			GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE))
		.addGroup(groupLayout.createSequentialGroup().addGap(10).addComponent(lblVersionInformation,
			GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE))
		.addGroup(groupLayout.createSequentialGroup().addGap(10).addComponent(lblProvideVersionInformation,
			GroupLayout.PREFERRED_SIZE, 270, GroupLayout.PREFERRED_SIZE))
		.addGroup(groupLayout.createSequentialGroup().addGap(10)
			.addComponent(lblVersion, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
			.addGap(102).addComponent(textFieldVersion, GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
			.addGap(28))
		.addGroup(groupLayout.createSequentialGroup().addGap(10).addComponent(lblContentVariability,
			GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE))
		.addGroup(groupLayout.createSequentialGroup().addGap(10).addComponent(lblSpecifyHowThis,
			GroupLayout.PREFERRED_SIZE, 279, GroupLayout.PREFERRED_SIZE))
		.addGroup(
			groupLayout.createSequentialGroup().addGap(10)
				.addComponent(lblVariabilityType, GroupLayout.PREFERRED_SIZE, 95,
					GroupLayout.PREFERRED_SIZE)
				.addGap(59).addComponent(comboBoxVariabilityType, 0, 320, Short.MAX_VALUE)
				.addGap(27))
		.addGroup(groupLayout.createSequentialGroup().addGap(10)
			.addComponent(lblBase, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE).addGap(118)
			.addComponent(scrollPane_10, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE).addGap(18)
			.addComponent(btnSelectBase, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
			.addGap(31))
		.addGroup(groupLayout.createSequentialGroup().addGap(10)
			.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(lblBriefDescription, GroupLayout.PREFERRED_SIZE, 106,
						GroupLayout.PREFERRED_SIZE)
					.addGap(47).addComponent(scrollPane_1))
			.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
				.addComponent(lblPresentationName, GroupLayout.PREFERRED_SIZE, 124,
					GroupLayout.PREFERRED_SIZE)
				.addGap(29).addComponent(textFieldPresentationName, GroupLayout.DEFAULT_SIZE, 320,
					Short.MAX_VALUE)))
			.addGap(28))
		.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup().addGap(10)
			.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(lblSynonyms, GroupLayout.PREFERRED_SIZE, 66,
						GroupLayout.PREFERRED_SIZE)
					.addGap(87)
					.addComponent(scrollPane_6, GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE))
			.addGroup(
				groupLayout.createSequentialGroup()
					.addComponent(lblSkills, GroupLayout.PREFERRED_SIZE, 35,
						GroupLayout.PREFERRED_SIZE)
					.addGap(118).addComponent(scrollPane_4))
			.addGroup(Alignment.LEADING,
				groupLayout.createSequentialGroup()
					.addComponent(lblassignmentApproaches, GroupLayout.PREFERRED_SIZE, 61,
						GroupLayout.PREFERRED_SIZE)
					.addGap(92)
					.addComponent(scrollPane_5, GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)))
			.addGap(28))
		.addGroup(Alignment.TRAILING,
			groupLayout.createSequentialGroup()
				.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
					.addGroup(groupLayout.createSequentialGroup().addGap(10)
						.addComponent(lblChangeDescription, GroupLayout.PREFERRED_SIZE, 126,
							GroupLayout.PREFERRED_SIZE)
						.addGap(26).addComponent(scrollPane_7, GroupLayout.DEFAULT_SIZE, 321,
							Short.MAX_VALUE))
			.addGroup(
				groupLayout.createSequentialGroup()
					.addComponent(lblChangeDate, GroupLayout.PREFERRED_SIZE, 85,
						GroupLayout.PREFERRED_SIZE)
					.addGap(78)
					.addComponent(textFieldDate, GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)))
			.addGap(28))
		.addGroup(groupLayout.createSequentialGroup().addGap(10)
			.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(lblAuthors, GroupLayout.PREFERRED_SIZE, 53,
						GroupLayout.PREFERRED_SIZE)
					.addGap(100).addComponent(scrollPane_8))
			.addGroup(Alignment.LEADING,
				groupLayout.createSequentialGroup()
					.addComponent(lblCopyright, GroupLayout.PREFERRED_SIZE, 63,
						GroupLayout.PREFERRED_SIZE)
					.addGap(90)
					.addComponent(scrollPane_9, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
					.addGap(17)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup().addGap(1).addComponent(
							btnSelectCopyright, GroupLayout.PREFERRED_SIZE, 99,
							GroupLayout.PREFERRED_SIZE))
						.addComponent(btnDeselectCopyright, GroupLayout.PREFERRED_SIZE, 100,
							GroupLayout.PREFERRED_SIZE))))
			.addGap(31)));
	groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
		.createSequentialGroup().addGap(10)
		.addComponent(lblGeneralInformation, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
		.addGap(2)
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup().addGap(6).addComponent(lblName,
				GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
			.addComponent(textFieldName, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
		.addGap(6)
		.addGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(lblPresentationName, GroupLayout.PREFERRED_SIZE, 15,
					GroupLayout.PREFERRED_SIZE)
				.addComponent(textFieldPresentationName, GroupLayout.PREFERRED_SIZE, 27,
					GroupLayout.PREFERRED_SIZE))
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup().addGap(8)
				.addComponent(lblBriefDescription, GroupLayout.PREFERRED_SIZE, 15,
					GroupLayout.PREFERRED_SIZE)
				.addGap(58).addComponent(lblDetailInformation, GroupLayout.PREFERRED_SIZE, 17,
					GroupLayout.PREFERRED_SIZE))
			.addGroup(groupLayout.createSequentialGroup().addGap(6).addComponent(scrollPane_1,
				GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)))
		.addGap(18)
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup()
				.addComponent(lblMainDescription, GroupLayout.PREFERRED_SIZE, 15,
					GroupLayout.PREFERRED_SIZE)
				.addGap(113).addComponent(lblkeyConsiderations, GroupLayout.PREFERRED_SIZE, 30,
					GroupLayout.PREFERRED_SIZE))
			.addGroup(groupLayout.createSequentialGroup().addGap(115).addComponent(scrollPane_3,
				GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE))
			.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE))
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup().addGap(38)
				.addComponent(lblStaffingInformation, GroupLayout.PREFERRED_SIZE, 17,
					GroupLayout.PREFERRED_SIZE)
				.addGap(28)
				.addComponent(lblSkills, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
			.addGroup(groupLayout.createSequentialGroup().addGap(73).addComponent(scrollPane_4,
				GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)))
		.addPreferredGap(ComponentPlacement.RELATED)
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
			.addComponent(lblassignmentApproaches, GroupLayout.PREFERRED_SIZE, 30,
				GroupLayout.PREFERRED_SIZE)
			.addComponent(scrollPane_5, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE))
		.addGap(6)
		.addGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(lblSynonyms, GroupLayout.PREFERRED_SIZE, 15,
						GroupLayout.PREFERRED_SIZE)
					.addGap(49).addComponent(lblVersionInformation, GroupLayout.PREFERRED_SIZE, 17,
						GroupLayout.PREFERRED_SIZE))
			.addComponent(scrollPane_6, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE))
		.addGap(18)
		.addComponent(lblProvideVersionInformation, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
		.addGap(21)
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup().addGap(6).addComponent(lblVersion,
				GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
			.addComponent(textFieldVersion, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
		.addGap(9)
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup().addGap(5).addComponent(lblChangeDate,
				GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
			.addComponent(textFieldDate, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup().addGap(37).addComponent(lblChangeDescription,
				GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
			.addGroup(groupLayout.createSequentialGroup().addGap(22).addComponent(scrollPane_7,
				GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)))
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup().addGap(15).addComponent(lblAuthors,
				GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
			.addGroup(groupLayout.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(scrollPane_8, GroupLayout.PREFERRED_SIZE, 59,
					GroupLayout.PREFERRED_SIZE)))
		.addGap(8)
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup().addGap(5).addComponent(lblCopyright,
				GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
			.addGroup(groupLayout.createSequentialGroup().addGap(3).addComponent(scrollPane_9,
				GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE))
			.addGroup(groupLayout.createSequentialGroup()
				.addComponent(btnSelectCopyright, GroupLayout.PREFERRED_SIZE, 27,
					GroupLayout.PREFERRED_SIZE)
				.addGap(21).addComponent(btnDeselectCopyright, GroupLayout.PREFERRED_SIZE, 27,
					GroupLayout.PREFERRED_SIZE)))
		.addGap(6)
		.addComponent(lblContentVariability, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
		.addGap(6).addComponent(lblSpecifyHowThis, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
		.addGap(6)
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup().addGap(5).addComponent(lblVariabilityType,
				GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
			.addComponent(comboBoxVariabilityType, GroupLayout.PREFERRED_SIZE, 25,
				GroupLayout.PREFERRED_SIZE))
		.addGap(6)
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
			.addComponent(lblBase, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
			.addComponent(scrollPane_10, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
			.addGroup(groupLayout.createSequentialGroup().addGap(5).addComponent(btnSelectBase,
				GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)))));
	setLayout(groupLayout);
    }

    public String getAssignmentApproaches() {
	return textAreaAssignmentApproaches.getText();
    }

    public String getAuthors() {
	return textAreaAuthors.getText();
    }

    public String getBase() {
	if (listBase.getModel().getSize() > 0) {
	    String id = ((Role) listBase.getModel().getElementAt(0).getElement()).getId();
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

    public DocumentListener getDocumentListener() {
	return documentListener;
    }

    public String getKeyConsiderations() {
	return textAreaKeyConsiderations.getText();
    }

    public ListDataListener getListDataListener() {
	return listDataListener;
    }

    public String getMainDescription() {
	return textAreaMainDescription.getText();
    }

    public String getPresentationName() {
	return textFieldPresentationName.getText();
    }

    public String getSkills() {
	return textAreaSkills.getText();
    }

    public String getSynonyms() {
	return textAreaSynonyms.getText();
    }

    public String gettextFieldName() {
	return textFieldName.getText();
    }

    public String getVersion() {
	return textFieldVersion.getText();
    }

    public void setAssignmentApproaches(String text) {
	textAreaAssignmentApproaches.setText(text);
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
		.addElement(new ElementWrapper(methodElement, methodElement.getName(), EPFConstants.roleIcon));
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

	textAreaAssignmentApproaches.getDocument().addDocumentListener(this.documentListener);
	textAreaAuthors.getDocument().addDocumentListener(this.documentListener);
	textAreaBriefDescription.getDocument().addDocumentListener(this.documentListener);
	textAreaChangeDescription.getDocument().addDocumentListener(this.documentListener);
	textAreaKeyConsiderations.getDocument().addDocumentListener(this.documentListener);
	textAreaMainDescription.getDocument().addDocumentListener(this.documentListener);
	textAreaSkills.getDocument().addDocumentListener(this.documentListener);
	textAreaSynonyms.getDocument().addDocumentListener(this.documentListener);
	textFieldDate.getDocument().addDocumentListener(this.documentListener);
	textFieldName.getDocument().addDocumentListener(this.documentListener);
	textFieldPresentationName.getDocument().addDocumentListener(this.documentListener);
	textFieldVersion.getDocument().addDocumentListener(this.documentListener);

	listCopyright.getModel().addListDataListener(this.listDataListener);
	listBase.getModel().addListDataListener(this.listDataListener);
    }

    public void setDate(XMLGregorianCalendar xmlGregorianCalendar) {
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

    public void setSkills(String text) {
	textAreaSkills.setText(text);
    }

    public void setSynonyms(String text) {
	textAreaSynonyms.setText(text);
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

    public JList<ElementWrapper> getListCopyright() {
	return listCopyright;
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

    public JComboBox<MethodContentVariability> getComboBoxVariabilityType() {
	return comboBoxVariabilityType;
    }
}
