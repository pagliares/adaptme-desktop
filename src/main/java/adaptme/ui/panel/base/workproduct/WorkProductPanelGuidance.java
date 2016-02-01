package adaptme.ui.panel.base.workproduct;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.xml.bind.JAXBElement;

import org.eclipse.epf.uma.Example;
import org.eclipse.epf.uma.NamedElement;
import org.eclipse.epf.uma.WorkProduct;

import adaptme.base.ElementWrapper;
import adaptme.base.MethodLibraryHash;
import adaptme.ui.components.renderer.ListCellRendererCustom;
import adaptme.ui.panel.base.TabbedPanel;
import adaptme.ui.window.AdaptMeUI;
import adaptme.util.EPFConstants;

public abstract class WorkProductPanelGuidance extends JPanel {

    private static final long serialVersionUID = 8663908352507686606L;
    private JList<ElementWrapper> listGuidance;
    private WorkProduct workProduct;
    private MethodLibraryHash hash;
    private AdaptMeUI owner;
    private TabbedPanel tabbedPanel;

    JButton btnRemoveGuidance;
    private DocumentListener documentListener;
    private ListDataListener listDataListener;

    public abstract void load();

    public abstract void save();

    public abstract void openDialogSelectGuidance(WorkProduct workProduct, AdaptMeUI owner, TabbedPanel tabbedPanel);

    public abstract void removeGuidance(int id, JList<ElementWrapper> listGuidance);

    public WorkProductPanelGuidance(WorkProduct workProduct, TabbedPanel tabbedPanel, MethodLibraryHash mlHash,
	    AdaptMeUI owner) {
	this.workProduct = workProduct;
	this.hash = mlHash;
	this.owner = owner;
	this.tabbedPanel = tabbedPanel;
	initComponents();
    }

    private void initComponents() {
	setBackground(Color.WHITE);

	JLabel lblGuidance = new JLabel("Guidance");
	lblGuidance.setForeground(Color.BLUE);
	lblGuidance.setFont(new Font("Tahoma", Font.PLAIN, 13));

	JLabel lblGuidance_1 = new JLabel("Guidance:");

	JScrollPane scrollPane_0 = new JScrollPane();

	JButton btnAddGuidance = new JButton("Add...");
	btnAddGuidance.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent event) {
		openDialogSelectGuidance(workProduct, owner, tabbedPanel);
	    }
	});

	btnRemoveGuidance = new JButton("Remove");
	btnRemoveGuidance.setEnabled(false);
	btnRemoveGuidance.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		int index = getListGuidance().getSelectedIndex();
		removeGuidance(index, getListGuidance());
	    }
	});

	JLabel lblBriefDescriptionOf = new JLabel("Brief description of selected element:");

	JScrollPane scrollPane = new JScrollPane();
	scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

	JLabel lblWorkProduct = new JLabel("Work Product");

	JLabel lblProvideLinksTo = new JLabel("Provide links to additional information in the form of guidance.");
	GroupLayout groupLayout = new GroupLayout(this);
	groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
		.addGroup(groupLayout.createSequentialGroup().addContainerGap()
			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addComponent(lblBriefDescriptionOf)
					.addContainerGap(192, Short.MAX_VALUE))
			.addGroup(groupLayout.createSequentialGroup().addComponent(lblWorkProduct).addContainerGap(305,
				Short.MAX_VALUE))
			.addGroup(groupLayout.createSequentialGroup()
				.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
					.addComponent(lblGuidance, Alignment.LEADING)
					.addComponent(lblGuidance_1, Alignment.LEADING))
				.addContainerGap(318, Short.MAX_VALUE))
			.addGroup(groupLayout.createSequentialGroup().addComponent(lblProvideLinksTo)
				.addContainerGap(74, Short.MAX_VALUE))
			.addGroup(Alignment.TRAILING,
				groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
							282, Short.MAX_VALUE)
					.addComponent(scrollPane_0, GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
					.addComponent(btnAddGuidance, GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
					.addComponent(btnRemoveGuidance, GroupLayout.DEFAULT_SIZE,
						GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(11)))));
	groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
		.addGroup(groupLayout.createSequentialGroup().addGap(18).addComponent(lblWorkProduct).addGap(3)
			.addComponent(lblGuidance).addPreferredGap(ComponentPlacement.RELATED)
			.addComponent(lblProvideLinksTo).addPreferredGap(ComponentPlacement.RELATED)
			.addComponent(lblGuidance_1).addPreferredGap(ComponentPlacement.UNRELATED)
			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addGap(32).addComponent(btnAddGuidance)
					.addGap(18).addComponent(btnRemoveGuidance))
			.addGroup(groupLayout.createSequentialGroup().addGap(8).addComponent(scrollPane_0,
				GroupLayout.PREFERRED_SIZE, 202, GroupLayout.PREFERRED_SIZE)))
			.addGap(18).addComponent(lblBriefDescriptionOf).addGap(18)
			.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
			.addContainerGap(57, Short.MAX_VALUE)));

	JTextPane textPane = new JTextPane();
	scrollPane.setViewportView(textPane);

	setListGuidance(new JList<ElementWrapper>());
	getListGuidance().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	DefaultListModel<ElementWrapper> model = new DefaultListModel<>();
	getListGuidance().setModel(model);
	getListGuidance().addListSelectionListener(new ListSelectionListener() {
	    public void valueChanged(ListSelectionEvent e) {
		btnRemoveGuidance.setEnabled(true);
	    }
	});
	getListGuidance().setCellRenderer(new ListCellRendererCustom());
	scrollPane_0.setViewportView(getListGuidance());
	setLayout(groupLayout);
    }

    public void setGuidanceModel(List<JAXBElement<String>> jaxbElements) {
	DefaultListModel<ElementWrapper> model = getGuidanceModel();
	for (JAXBElement<String> jaxbElement : jaxbElements) {
	    NamedElement namedElement = (NamedElement) hash.getHashMap().get(jaxbElement.getValue());
	    if (namedElement instanceof Example) {
		model.addElement(new ElementWrapper(namedElement, namedElement.getName(), EPFConstants.exampleIcon));
	    } else {
		model.addElement(new ElementWrapper(namedElement, namedElement.getName(), null));
	    }
	}
	getListGuidance().setModel(model);
    }

    public DefaultListModel<ElementWrapper> getGuidanceModel() {

	DefaultListModel<ElementWrapper> model = (DefaultListModel<ElementWrapper>) getListGuidance().getModel();
	return model;
    }

    public void setChangeListeners(DocumentListener documentListener, ListDataListener listDataListener) {

	this.documentListener = documentListener;
	this.listDataListener = listDataListener;

	getListGuidance().getModel().addListDataListener(this.listDataListener);
    }

    public DocumentListener getDocumentListener() {
	return documentListener;
    }

    public ListDataListener getListDataListener() {
	return listDataListener;
    }

    public JList<ElementWrapper> getListGuidance() {
	return listGuidance;
    }

    public void setListGuidance(JList<ElementWrapper> listGuidance) {
	this.listGuidance = listGuidance;
    }
}
