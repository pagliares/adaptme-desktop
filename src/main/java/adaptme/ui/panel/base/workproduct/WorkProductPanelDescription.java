package adaptme.ui.panel.base.workproduct;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.epf.uma.Artifact;
import org.eclipse.epf.uma.Deliverable;
import org.eclipse.epf.uma.MethodElement;
import org.eclipse.epf.uma.NamedElement;
import org.eclipse.epf.uma.Outcome;
import org.eclipse.epf.uma.VariabilityType;
import org.eclipse.epf.uma.WorkProduct;

import adaptme.base.ElementWrapper;
import adaptme.base.MethodLibraryHash;
import adaptme.base.enums.MethodContentVariability;
import adaptme.ui.components.renderer.ListCellRendererCustom;
import adaptme.ui.panel.base.TabbedPanel;
import adaptme.ui.window.AdaptMeUI;
import adaptme.util.EPFConstants;

public abstract class WorkProductPanelDescription extends JPanel {

    private static final long serialVersionUID = 198296960620557395L;

    private final String MENSAGEM_IS_WORK_PRODUCT_SLOT = "<html>There are slots present which this Work product fulfills.<br/> Those references will be removed. Do you want to continue?</html>";

    private WorkProduct workProduct;
    private MethodLibraryHash mlHash;
    private AdaptMeUI owner;
    private TabbedPanel tabbedPanel;

    private JComboBox<MethodContentVariability> comboBoxVariabilityType;
    private JTextField textFieldName;
    private JTextField textFieldDate;
    private JTextField textFieldVersion;
    private JTextPane textAreaMainDescription;
    private JList<ElementWrapper> listBase;
    private JList<ElementWrapper> listCopyright;
    private JList<ElementWrapper> listWorkProductSlot;
    private JTextArea textAreaChangeDescription;
    private JTextArea textAreaBriefDescription;
    private JTextArea textAreaAuthors;
    private JTextPane textAreaKeyConsiderations;
    private JTextField textFieldPresentationName;
    private JButton btnSelectCopyright;
    private JButton btnRemoveCopyright;
    private JButton btnSelectBase;
    private JTextPane textPanePurpose;
    private JTextArea textAreaReasonNotNeeding;
    private JTextArea textAreaNotation;
    private JCheckBox chckbxIsWorkProduct;
    private JTextArea textAreaImpactNotHaving;
    private JTextArea textAreaSelectedRepresentation;
    private JTextArea textAreaRepresentationOptions;
    private JTextArea textAreaBriefOutline;
    private JButton btnRemoveWorkProductSlot;
    private JButton btnAddWorkProductSlot;

    private DocumentListener documentListener;
    private ListDataListener listDataListener;

    public abstract void load();

    public abstract void save();

    public abstract void openDialogSelectCopyright(WorkProduct workProduct, AdaptMeUI owner, TabbedPanel tabbedPanel);

    public abstract void removeCopyright(int index, JList<ElementWrapper> listCopyright);

    public abstract void openDialogSelectBase(WorkProduct workProduct, AdaptMeUI owner, TabbedPanel tabbedPanel);

    public abstract void openDialogWorkProductSlot(WorkProduct workProduct, AdaptMeUI owner, TabbedPanel tabbedPanel);

    public abstract void removeWorkProductSlot(int index, JList<ElementWrapper> listWorkProductSlot);

    public WorkProductPanelDescription(WorkProduct workProduct, TabbedPanel tabbedPanel, MethodLibraryHash hash,
	    AdaptMeUI owner) {
	addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent e) {
		listWorkProductSlot.clearSelection();
		getBtnRemoveWorkProductSlot().setEnabled(false);
	    }
	});
	this.workProduct = workProduct;
	this.mlHash = hash;
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

	JLabel lblVersionInformation = new JLabel("Version Information");
	lblVersionInformation.setFont(new Font("Tahoma", Font.PLAIN, 13));
	lblVersionInformation.setForeground(Color.BLUE);

	JLabel lblProvideVersionInformation = new JLabel("Provide version information about this artifact.");

	JLabel lblVersion = new JLabel("Version:");

	JLabel lblChangeDate = new JLabel("Change date:");

	JLabel lblChangeDescription = new JLabel("Change description:");

	JLabel lblAuthors = new JLabel("Authors:");

	JLabel lblCopyright = new JLabel("Copyright:");

	JLabel lblContentVariability = new JLabel("Content Variability");
	lblContentVariability.setFont(new Font("Tahoma", Font.PLAIN, 13));
	lblContentVariability.setForeground(Color.BLUE);

	JLabel lblSpecifyHowThis = new JLabel("Specify how this element relates to another element.");

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

	JScrollPane scrollPane_10 = new JScrollPane();

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
		openDialogSelectCopyright(workProduct, owner, tabbedPanel);
	    }
	});

	btnRemoveCopyright = new JButton("Deselect...");
	btnRemoveCopyright.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		int index = listCopyright.getSelectedIndex();
		removeCopyright(index, listCopyright);
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
	if (workProduct.getVariabilityType() == VariabilityType.NA) {
	    btnSelectBase.setEnabled(false);
	    ;
	}
	btnSelectBase.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent event) {
		openDialogSelectBase(workProduct, owner, tabbedPanel);
	    }
	});

	listBase = new JList<>();
	listBase.setCellRenderer(new ListCellRendererCustom());
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

	JLabel lblWorkProduct = new JLabel("Work Product");

	JLabel lblProvideGeneralInformation = new JLabel("Provide general information about this artifact.");

	JLabel lblSlotsInformation = new JLabel("Slots Information");
	lblSlotsInformation.setForeground(Color.BLUE);
	lblSlotsInformation.setFont(new Font("Tahoma", Font.PLAIN, 13));

	chckbxIsWorkProduct = new JCheckBox("Is Work Product Slot");
	chckbxIsWorkProduct.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		if (chckbxIsWorkProduct.isSelected() && listWorkProductSlot.getModel().getSize() > 0) {
		    int option = JOptionPane.showConfirmDialog(owner.getFrame(), MENSAGEM_IS_WORK_PRODUCT_SLOT,
			    "Work Product Slots", JOptionPane.OK_CANCEL_OPTION);
		    if (option == JOptionPane.CANCEL_OPTION) {
			chckbxIsWorkProduct.setSelected(false);
			return;
		    } else {
			((DefaultListModel<ElementWrapper>) listWorkProductSlot.getModel()).clear();
			getBtnAddWorkProductSlot().setEnabled(true);
			return;
		    }
		}
	    }
	});
	chckbxIsWorkProduct.setBackground(Color.WHITE);

	JLabel lblSelectTheSlots = new JLabel("Select the slots that this artifact fulfills.");

	JLabel lblSlots = new JLabel("Slots:");

	JScrollPane scrollPane_5 = new JScrollPane();
	scrollPane_5.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

	setBtnAddWorkProductSlot(new JButton("Add..."));
	getBtnAddWorkProductSlot().addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		openDialogWorkProductSlot(workProduct, owner, tabbedPanel);
	    }
	});

	setBtnRemoveWorkProductSlot(new JButton("Remove"));
	getBtnRemoveWorkProductSlot().setEnabled(false);
	getBtnRemoveWorkProductSlot().addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		int index = listWorkProductSlot.getSelectedIndex();
		removeWorkProductSlot(index, listWorkProductSlot);
	    }
	});

	JLabel lblDeliveryInformation = new JLabel("Delivery Information");
	lblDeliveryInformation.setForeground(Color.BLUE);
	lblDeliveryInformation.setFont(new Font("Tahoma", Font.PLAIN, 13));

	JLabel lblProvideDoliveryInformation = new JLabel("Provide dolivery information about this artifact.");

	JLabel lblBriefOutline = new JLabel("Brief outline:");

	JScrollPane scrollPane_4 = new JScrollPane();
	scrollPane_4.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

	JLabel lblselectedRepresentation = new JLabel("<html>Selected representation:</html>");

	JScrollPane scrollPane_6 = new JScrollPane();
	scrollPane_6.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

	JLabel lblNotation = new JLabel("Notation:");

	JScrollPane scrollPane_11 = new JScrollPane();
	scrollPane_11.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

	JLabel lblTailoring = new JLabel("Tailoring");
	lblTailoring.setForeground(Color.BLUE);
	lblTailoring.setFont(new Font("Tahoma", Font.PLAIN, 13));

	JLabel lblreasonForNot = new JLabel("<html>Reason for not needing:</html>");

	JScrollPane scrollPane_12 = new JScrollPane();
	scrollPane_12.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

	JScrollPane scrollPane_13 = new JScrollPane();
	scrollPane_13.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

	JLabel lblrepresentationOptions = new JLabel("<html>Representation options:</html>");

	JScrollPane scrollPane_14 = new JScrollPane();
	scrollPane_14.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

	JLabel lblimpactOfNot = new JLabel("<html>Impact of not having:</html>");

	GroupLayout groupLayout = new GroupLayout(this);
	groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
		.createSequentialGroup().addGap(10)
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(lblGeneralInformation)
			.addGroup(groupLayout.createSequentialGroup().addComponent(lblName).addGap(95)
				.addComponent(this.textFieldName, GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)))
		.addGap(10))
		.addGroup(
			groupLayout.createSequentialGroup().addContainerGap()
				.addComponent(lblPresentationName, GroupLayout.PREFERRED_SIZE, 116,
					GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addComponent(this.textFieldPresentationName, GroupLayout.DEFAULT_SIZE, 304,
					Short.MAX_VALUE)
				.addContainerGap())
		.addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(lblBriefDescription)
			.addGap(45).addComponent(scrollPane_1).addContainerGap())
		.addGroup(groupLayout.createSequentialGroup().addContainerGap()
			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(lblkeyConsiderations, GroupLayout.PREFERRED_SIZE, 81,
						GroupLayout.PREFERRED_SIZE)
					.addGap(45)
					.addComponent(scrollPane_3, GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE))
			.addGroup(groupLayout.createSequentialGroup()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
					.addComponent(lblMainDescription).addComponent(lblPurpose))
				.addGap(45)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
					.addComponent(scrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 304,
						Short.MAX_VALUE)
					.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE))))
			.addContainerGap())
		.addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(lblWorkProduct)
			.addContainerGap(375, Short.MAX_VALUE))
		.addGroup(groupLayout.createSequentialGroup().addContainerGap()
			.addComponent(lblProvideGeneralInformation).addContainerGap(215, Short.MAX_VALUE))
		.addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(lblDetailInformation)
			.addContainerGap(338, Short.MAX_VALUE))
		.addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(lblSlotsInformation)
			.addContainerGap(342, Short.MAX_VALUE))
		.addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(lblSlots).addGap(99)
			.addComponent(scrollPane_5, GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
			.addPreferredGap(ComponentPlacement.RELATED)
			.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
				.addComponent(this.btnAddWorkProductSlot, GroupLayout.DEFAULT_SIZE,
					GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(this.btnRemoveWorkProductSlot, GroupLayout.DEFAULT_SIZE,
					GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
			.addContainerGap())
		.addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(lblSelectTheSlots)
			.addContainerGap(252, Short.MAX_VALUE))
		.addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(this.chckbxIsWorkProduct)
			.addContainerGap(321, Short.MAX_VALUE))
		.addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(lblDeliveryInformation)
			.addContainerGap(325, Short.MAX_VALUE))
		.addGroup(groupLayout.createSequentialGroup().addContainerGap()
			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(lblProvideDoliveryInformation)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(lblBriefOutline).addComponent(lblNotation)
						.addComponent(lblselectedRepresentation, GroupLayout.PREFERRED_SIZE, 80,
							GroupLayout.PREFERRED_SIZE))
					.addGap(44)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane_6, GroupLayout.DEFAULT_SIZE, 306,
							Short.MAX_VALUE)
						.addComponent(scrollPane_11, GroupLayout.DEFAULT_SIZE, 306,
							Short.MAX_VALUE)
						.addComponent(scrollPane_4, GroupLayout.DEFAULT_SIZE, 306,
							Short.MAX_VALUE))))
			.addContainerGap())
		.addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(lblTailoring)
			.addContainerGap(390, Short.MAX_VALUE))
		.addGroup(groupLayout.createSequentialGroup().addContainerGap()
			.addGroup(groupLayout
				.createParallelGroup(
					Alignment.LEADING)
				.addGroup(
					groupLayout
						.createParallelGroup(
							Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblChangeDate)
								.addComponent(lblChangeDescription)
								.addComponent(lblVersion).addComponent(lblAuthors))
						.addGap(25)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(this.textFieldDate, GroupLayout.DEFAULT_SIZE, 309,
								Short.MAX_VALUE)
							.addComponent(this.textFieldVersion, Alignment.TRAILING,
								GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
							.addComponent(scrollPane_8, GroupLayout.DEFAULT_SIZE, 309,
								Short.MAX_VALUE)
							.addComponent(scrollPane_7, GroupLayout.DEFAULT_SIZE, 309,
								Short.MAX_VALUE))
							.addGap(10))
						.addGroup(groupLayout.createSequentialGroup().addGroup(groupLayout
							.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(lblCopyright).addGap(70)
								.addComponent(scrollPane_9, GroupLayout.DEFAULT_SIZE,
									224, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblVariabilityType).addGap(40).addComponent(
								this.comboBoxVariabilityType, 0, 230, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup().addComponent(lblBase)
							.addGap(90).addComponent(scrollPane_10,
								GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
							.addComponent(this.btnRemoveCopyright,
								GroupLayout.PREFERRED_SIZE, 79, Short.MAX_VALUE)
							.addComponent(this.btnSelectCopyright, GroupLayout.DEFAULT_SIZE,
								79, Short.MAX_VALUE)
							.addComponent(this.btnSelectBase, GroupLayout.DEFAULT_SIZE,
								GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGap(10)))
				.addGroup(groupLayout.createSequentialGroup().addComponent(lblVersionInformation)
					.addPreferredGap(ComponentPlacement.RELATED, 327, GroupLayout.PREFERRED_SIZE)))
			.addGap(0))
		.addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(lblSpecifyHowThis)
			.addContainerGap(192, Short.MAX_VALUE))
		.addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(lblContentVariability)
			.addContainerGap(336, Short.MAX_VALUE))
		.addGroup(groupLayout.createSequentialGroup().addContainerGap()
			.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
					.addGroup(groupLayout.createSequentialGroup().addGap(10).addComponent(
						lblimpactOfNot, GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE))
					.addComponent(lblreasonForNot, 0, 0, Short.MAX_VALUE))
				.addComponent(lblrepresentationOptions, GroupLayout.PREFERRED_SIZE, 78,
					GroupLayout.PREFERRED_SIZE))
			.addGap(46)
			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane_13, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 306,
					Short.MAX_VALUE)
				.addComponent(scrollPane_14, GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
				.addComponent(scrollPane_12, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 306,
					Short.MAX_VALUE))
			.addContainerGap())
		.addGroup(groupLayout.createSequentialGroup().addContainerGap()
			.addComponent(lblProvideVersionInformation).addContainerGap(216, Short.MAX_VALUE)));
	groupLayout.setVerticalGroup(groupLayout
		.createParallelGroup(
			Alignment.LEADING)
		.addGroup(groupLayout.createSequentialGroup().addGap(6).addComponent(lblWorkProduct)
			.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(lblGeneralInformation)
			.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblProvideGeneralInformation)
			.addGap(20)
			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addGap(3).addComponent(lblName))
				.addComponent(this.textFieldName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
					GroupLayout.PREFERRED_SIZE))
		.addPreferredGap(ComponentPlacement.RELATED)
		.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
			.addComponent(this.textFieldPresentationName, GroupLayout.PREFERRED_SIZE,
				GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
			.addComponent(lblPresentationName))
		.addPreferredGap(ComponentPlacement.RELATED)
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup().addComponent(lblBriefDescription).addGap(63)
				.addComponent(lblSlotsInformation))
			.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE))
		.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblSelectTheSlots)
		.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(this.chckbxIsWorkProduct)
		.addPreferredGap(ComponentPlacement.RELATED)
		.addGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
					.addComponent(scrollPane_5, GroupLayout.PREFERRED_SIZE, 59,
						GroupLayout.PREFERRED_SIZE)
					.addComponent(lblSlots))
				.addGroup(groupLayout.createSequentialGroup().addComponent(this.btnAddWorkProductSlot)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(this.btnRemoveWorkProductSlot)))
			.addGap(65).addComponent(lblDetailInformation).addPreferredGap(ComponentPlacement.UNRELATED)
			.addGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 59,
						GroupLayout.PREFERRED_SIZE)
					.addComponent(lblPurpose))
		.addPreferredGap(ComponentPlacement.RELATED)
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(lblMainDescription)
			.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE))
		.addPreferredGap(ComponentPlacement.RELATED)
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup().addGap(6).addComponent(lblkeyConsiderations))
			.addComponent(scrollPane_3, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE))
		.addGap(16).addComponent(lblDeliveryInformation).addPreferredGap(ComponentPlacement.RELATED)
		.addComponent(lblProvideDoliveryInformation).addPreferredGap(ComponentPlacement.RELATED)
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(lblBriefOutline)
			.addComponent(scrollPane_4, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE))
		.addGap(24)
		.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
			.addComponent(scrollPane_6, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
			.addComponent(lblselectedRepresentation))
		.addPreferredGap(ComponentPlacement.UNRELATED)
		.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
			.addComponent(scrollPane_11, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
			.addComponent(lblNotation)).addGap(12).addComponent(lblTailoring)
		.addGap(18)
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(lblimpactOfNot)
			.addComponent(scrollPane_12, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE))
		.addGap(13)
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(lblreasonForNot)
			.addComponent(scrollPane_13, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE))
		.addPreferredGap(ComponentPlacement.UNRELATED)
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup().addGap(84).addComponent(lblVersionInformation))
			.addGroup(
				groupLayout.createParallelGroup(Alignment.BASELINE)
					.addComponent(scrollPane_14, GroupLayout.PREFERRED_SIZE, 59,
						GroupLayout.PREFERRED_SIZE)
					.addComponent(lblrepresentationOptions)))
			.addGap(18).addComponent(lblProvideVersionInformation)
			.addPreferredGap(
				ComponentPlacement.RELATED)
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup().addGap(21).addComponent(lblVersion))
			.addGroup(groupLayout.createSequentialGroup().addGap(18).addComponent(this.textFieldVersion,
				GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup().addGap(9).addComponent(lblChangeDate))
			.addGroup(groupLayout.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(this.textFieldDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
					GroupLayout.PREFERRED_SIZE)))
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup().addGap(26).addComponent(lblChangeDescription))
			.addGroup(groupLayout.createSequentialGroup().addGap(11).addComponent(scrollPane_7,
				GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)))
		.addPreferredGap(ComponentPlacement.RELATED)
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(lblAuthors)
			.addComponent(scrollPane_8, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE))
		.addPreferredGap(ComponentPlacement.RELATED)
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup().addGap(2).addComponent(lblCopyright))
			.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
				.addComponent(scrollPane_9, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
				.addGroup(groupLayout.createSequentialGroup().addComponent(this.btnSelectCopyright)
					.addPreferredGap(ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
					.addComponent(this.btnRemoveCopyright))))
		.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblContentVariability)
		.addPreferredGap(ComponentPlacement.RELATED)
		.addGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup().addComponent(lblSpecifyHowThis)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup().addGap(3)
							.addComponent(lblVariabilityType))
						.addComponent(this.comboBoxVariabilityType, GroupLayout.PREFERRED_SIZE,
							GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(scrollPane_10, GroupLayout.PREFERRED_SIZE, 59,
							GroupLayout.PREFERRED_SIZE)
						.addComponent(lblBase)))
				.addGroup(groupLayout.createSequentialGroup().addComponent(this.btnSelectBase)
					.addGap(19)))
			.addGap(165)));

	listWorkProductSlot = new JList<>();
	listWorkProductSlot.addListSelectionListener(new ListSelectionListener() {
	    public void valueChanged(ListSelectionEvent e) {
		getBtnRemoveWorkProductSlot().setEnabled(true);
	    }
	});
	ListModel<ElementWrapper> modelWorkProductSlot = new DefaultListModel<>();
	listWorkProductSlot.setModel(modelWorkProductSlot);
	listWorkProductSlot.setCellRenderer(new ListCellRendererCustom());
	scrollPane_5.setViewportView(listWorkProductSlot);

	textAreaBriefOutline = new JTextArea();
	scrollPane_4.setViewportView(textAreaBriefOutline);

	textAreaSelectedRepresentation = new JTextArea();
	scrollPane_6.setViewportView(textAreaSelectedRepresentation);

	textAreaNotation = new JTextArea();
	scrollPane_11.setViewportView(textAreaNotation);

	textAreaImpactNotHaving = new JTextArea();
	scrollPane_12.setViewportView(textAreaImpactNotHaving);

	textAreaReasonNotNeeding = new JTextArea();
	scrollPane_13.setViewportView(textAreaReasonNotNeeding);

	textAreaRepresentationOptions = new JTextArea();
	scrollPane_14.setViewportView(textAreaRepresentationOptions);

	textPanePurpose = new JTextPane();
	textPanePurpose.setContentType("text/html");
	scrollPane.setViewportView(textPanePurpose);
	setLayout(groupLayout);
    }

    public void setPresentationName(String text) {
	textFieldPresentationName.setText(text);
    }

    public void setTextFieldName(String text) {
	textFieldName.setText(text);
    }

    public void setDate(XMLGregorianCalendar xmlGregorianCalendar) {
	SimpleDateFormat dateFormat = new SimpleDateFormat("EEEEE, d MMMMM yyyy HH:mm:ss");
	textFieldDate.setText(dateFormat.format(xmlGregorianCalendar.toGregorianCalendar().getTime()));
    }

    public void setVersion(String text) {
	textFieldVersion.setText(text);
    }

    public void setMainDescription(String text) {
	textAreaMainDescription.setText(text);
    }

    public void setBase(String id) {
	if (id == null) {
	    DefaultListModel<ElementWrapper> model = new DefaultListModel<>();
	    listBase.setModel(model);
	    return;
	}
	DefaultListModel<ElementWrapper> model = new DefaultListModel<>();
	MethodElement methodElement = (MethodElement) mlHash.getHashMap().get(id);
	model.addElement(new ElementWrapper(methodElement, methodElement.getName(), EPFConstants.artifactIcon));
	listBase.setModel(model);
    }

    public void setCopyright(String id) {
	if (id == null) {
	    ListModel<ElementWrapper> model = new DefaultListModel<>();
	    listCopyright.setModel(model);
	    return;
	}
	ListModel<ElementWrapper> model = new DefaultListModel<>();
	NamedElement namedElement = (NamedElement) mlHash.getHashMap().get(id);
	((DefaultListModel<ElementWrapper>) model)
		.addElement(new ElementWrapper(namedElement, namedElement.getName(), EPFConstants.artifactIcon));
	listCopyright.setModel(model);
    }

    public void setChangeDescription(String text) {
	textAreaChangeDescription.setText(text);
    }

    public void setBriefDescription(String text) {
	textAreaBriefDescription.setText(text);
    }

    public void setAuthors(String text) {
	textAreaAuthors.setText(text);
    }

    public void setKeyConsiderations(String text) {
	textAreaKeyConsiderations.setText(text);
    }

    public void setVariabiliteType(MethodContentVariability methodContentVariability) {
	getComboBoxVariabilityType().setSelectedItem(methodContentVariability);
    }

    public String getChangeDescription() {
	return textAreaChangeDescription.getText();
    }

    public String getBriefDescription() {
	return textAreaBriefDescription.getText();
    }

    public String getKeyConsiderations() {
	return textAreaKeyConsiderations.getText();
    }

    public String getCopyright() {
	if (listCopyright.getModel().getSize() > 0) {
	    return ((MethodElement) ((ElementWrapper) listCopyright.getModel().getElementAt(0)).getElement()).getId();
	} else {
	    return null;
	}
    }

    public String getPresentationName() {
	return textFieldPresentationName.getText();
    }

    public String getChangeDate() {
	return textFieldDate.getText();
    }

    public String getMainDescription() {
	return textAreaMainDescription.getText();
    }

    public String gettextFieldName() {
	return textFieldName.getText();
    }

    public String getVersion() {
	return textFieldVersion.getText();
    }

    public String getAuthors() {
	return textAreaAuthors.getText();
    }

    public String getBase() {
	if (listBase.getModel().getSize() > 0) {
	    String id = ((Artifact) listBase.getModel().getElementAt(0).getElement()).getId();
	    return id;
	} else {
	    return null;
	}
    }

    public String getPurpose() {
	return textPanePurpose.getText();
    }

    public void setPurpose(String text) {
	textPanePurpose.setText(text);
    }

    public String getReasonNotNeeding() {
	return textAreaReasonNotNeeding.getText();
    }

    public String getNotation() {
	return textAreaNotation.getText();
    }

    public JList<ElementWrapper> getSlots() {
	return listWorkProductSlot;
    }

    public boolean getIsWorkProduct() {
	return chckbxIsWorkProduct.isSelected();
    }

    public String getImpactNotHaving() {
	return textAreaImpactNotHaving.getText();
    }

    public String getSelectedRepresentation() {
	return textAreaSelectedRepresentation.getText();
    }

    public String getRepresentationOptions() {
	return textAreaRepresentationOptions.getText();
    }

    public String getBriefOutline() {
	return textAreaBriefOutline.getText();
    }

    public JComboBox<MethodContentVariability> getVariabilityType() {
	return getComboBoxVariabilityType();
    }

    public String getPurpose(String text) {
	return textPanePurpose.getText();
    }

    public void setReasonNotNeeding(String text) {
	textAreaReasonNotNeeding.setText(text);
    }

    public void setNotation(String text) {
	textAreaNotation.setText(text);
    }

    public void setSlots() {
	// listSlots;
    }

    public void setIsWorkProduct(Boolean b) {
	chckbxIsWorkProduct.setSelected(b);
	;
    }

    public void setImpactNotHaving(String text) {
	textAreaImpactNotHaving.setText(text);
    }

    public void setSelectedRepresentation(String text) {
	textAreaSelectedRepresentation.setText(text);
    }

    public void setRepresentationOptions(String text) {
	textAreaRepresentationOptions.setText(text);
    }

    public void setBriefOutline(String text) {
	textAreaBriefOutline.setText(text);
    }

    public void setWorkProductSlot(List<String> list) {
	ListModel<ElementWrapper> model = listWorkProductSlot.getModel();
	for (String id : list) {
	    WorkProduct workProduct = (WorkProduct) mlHash.getHashMap().get(id);
	    if (workProduct instanceof Artifact) {
		((DefaultListModel<ElementWrapper>) model)
			.addElement(new ElementWrapper(workProduct, workProduct.getName(), EPFConstants.artifactIcon));
	    } else if (workProduct instanceof Outcome) {
		((DefaultListModel<ElementWrapper>) model).addElement(
			new ElementWrapper(workProduct, workProduct.getName(), EPFConstants.workProductIcon));
	    } else if (workProduct instanceof Deliverable) {
		((DefaultListModel<ElementWrapper>) model).addElement(
			new ElementWrapper(workProduct, workProduct.getName(), EPFConstants.deliverableIcon));
	    }
	}
	listWorkProductSlot.setModel(model);
    }

    public List<String> getWorkProductSlot() {
	ListModel<ElementWrapper> model = listWorkProductSlot.getModel();
	List<String> list = new ArrayList<>();
	for (int i = 0; i < model.getSize(); i++) {
	    list.add(((MethodElement) model.getElementAt(i).getElement()).getId());
	}
	return list;
    }

    public void setChangeListeners(DocumentListener documentListener, ListDataListener listDataListener) {

	this.documentListener = documentListener;
	this.listDataListener = listDataListener;

	textAreaAuthors.getDocument().addDocumentListener(this.documentListener);
	textAreaBriefDescription.getDocument().addDocumentListener(this.documentListener);
	textAreaBriefOutline.getDocument().addDocumentListener(this.documentListener);
	textAreaChangeDescription.getDocument().addDocumentListener(this.documentListener);
	textAreaImpactNotHaving.getDocument().addDocumentListener(this.documentListener);
	textAreaKeyConsiderations.getDocument().addDocumentListener(this.documentListener);
	textAreaMainDescription.getDocument().addDocumentListener(this.documentListener);
	textAreaNotation.getDocument().addDocumentListener(this.documentListener);
	textAreaReasonNotNeeding.getDocument().addDocumentListener(this.documentListener);
	textAreaRepresentationOptions.getDocument().addDocumentListener(this.documentListener);
	textAreaSelectedRepresentation.getDocument().addDocumentListener(this.documentListener);
	textFieldDate.getDocument().addDocumentListener(this.documentListener);
	textFieldName.getDocument().addDocumentListener(this.documentListener);
	textFieldPresentationName.getDocument().addDocumentListener(this.documentListener);
	textFieldVersion.getDocument().addDocumentListener(this.documentListener);
	textPanePurpose.getDocument().addDocumentListener(this.documentListener);
	listBase.getModel().addListDataListener(this.listDataListener);
	listCopyright.getModel().addListDataListener(this.listDataListener);
	listWorkProductSlot.getModel().addListDataListener(this.listDataListener);
    }

    public DocumentListener getDocumentListener() {
	return documentListener;
    }

    public ListDataListener getListDataListener() {
	return listDataListener;
    }

    public JButton getBtnRemoveWorkProductSlot() {
	return btnRemoveWorkProductSlot;
    }

    public void setBtnRemoveWorkProductSlot(JButton btnRemoveWorkProductSlot) {
	this.btnRemoveWorkProductSlot = btnRemoveWorkProductSlot;
    }

    public JButton getBtnAddWorkProductSlot() {
	return btnAddWorkProductSlot;
    }

    public void setBtnAddWorkProductSlot(JButton btnAddWorkProductSlot) {
	this.btnAddWorkProductSlot = btnAddWorkProductSlot;
    }

    public JComboBox<MethodContentVariability> getComboBoxVariabilityType() {
	return comboBoxVariabilityType;
    }

    public void setComboBoxVariabilityType(JComboBox<MethodContentVariability> comboBoxVariabilityType) {
	this.comboBoxVariabilityType = comboBoxVariabilityType;
    }
}
