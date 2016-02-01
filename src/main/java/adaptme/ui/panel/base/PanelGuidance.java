package adaptme.ui.panel.base;

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

import org.eclipse.epf.uma.DescribableElement;
import org.eclipse.epf.uma.Element;
import org.eclipse.epf.uma.NamedElement;

import adaptme.base.ElementWrapper;
import adaptme.base.MethodLibraryHash;
import adaptme.ui.components.renderer.ListCellRendererCustom;
import adaptme.ui.window.AdaptMeUI;

public abstract class PanelGuidance extends JPanel {

    private static final long serialVersionUID = 5812386128740907883L;
    private JButton btnRemove;
    private DocumentListener documentListener;
    private Element element;
    private MethodLibraryHash hash;
    private ListDataListener listDataListener;
    private JList<ElementWrapper> listGuidance;
    private AdaptMeUI owner;
    private TabbedPanel tabbedPanel;
    private JTextPane textPaneBriefDescription;

    public abstract void load();

    public abstract void save();

    public abstract void openDialogSelectGuidance(Element element, AdaptMeUI owner, TabbedPanel tabbedPanel);

    public abstract void removeGuidance(int id, JList<ElementWrapper> listGuidance);

    public PanelGuidance(Element element, TabbedPanel tabbedPanel, MethodLibraryHash hash, AdaptMeUI owner) {
	this.element = element;
	this.tabbedPanel = tabbedPanel;
	this.hash = hash;
	this.owner = owner;
	initComponents();
    }

    public DocumentListener getDocumentListener() {
	return documentListener;
    }

    public DefaultListModel<ElementWrapper> getGuidanceModel() {

	DefaultListModel<ElementWrapper> model = (DefaultListModel<ElementWrapper>) getListGuidance().getModel();
	return model;
    }

    public ListDataListener getListDataListener() {
	return listDataListener;
    }

    private void initComponents() {
	setBackground(Color.WHITE);

	JLabel lblGuidance = new JLabel("Guidance");
	lblGuidance.setForeground(Color.BLUE);
	lblGuidance.setFont(new Font("Tahoma", Font.PLAIN, 13));

	JLabel lblProvideLinksTo = new JLabel("Provide links to additional information in the form of guidance.");

	JLabel lblGuidance_1 = new JLabel("Guidance:");

	JScrollPane scrollPane_0 = new JScrollPane();

	JButton btnAdd = new JButton("Add...");
	btnAdd.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent event) {
		openDialogSelectGuidance(element, owner, tabbedPanel);
	    }
	});

	btnRemove = new JButton("Remove");
	btnRemove.setEnabled(false);
	btnRemove.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		int id = listGuidance.getSelectedIndex();
		removeGuidance(id, getListGuidance());
	    }
	});

	JLabel lblBriefDescriptionOf = new JLabel("Brief description of selected element:");

	JScrollPane scrollPane = new JScrollPane();
	scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	GroupLayout groupLayout = new GroupLayout(this);
	groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
		.addGroup(groupLayout.createSequentialGroup().addContainerGap()
			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
							352, Short.MAX_VALUE)
					.addComponent(scrollPane_0, GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
					.addComponent(btnAdd, GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
					.addComponent(btnRemove, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE))
					.addGap(11))
				.addGroup(groupLayout.createSequentialGroup().addComponent(lblBriefDescriptionOf)
					.addContainerGap(262, Short.MAX_VALUE))
			.addGroup(groupLayout.createSequentialGroup()
				.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
					.addComponent(lblGuidance, Alignment.LEADING)
					.addComponent(lblProvideLinksTo, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
						GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(lblGuidance_1, Alignment.LEADING))
				.addContainerGap(144, Short.MAX_VALUE)))));
	groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
		.addGroup(groupLayout.createSequentialGroup().addGap(35).addComponent(lblGuidance)
			.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblProvideLinksTo)
			.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblGuidance_1)
			.addPreferredGap(ComponentPlacement.UNRELATED)
			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addGap(32).addComponent(btnAdd).addGap(18)
					.addComponent(btnRemove))
			.addGroup(groupLayout.createSequentialGroup().addGap(8).addComponent(scrollPane_0,
				GroupLayout.PREFERRED_SIZE, 202, GroupLayout.PREFERRED_SIZE)))
			.addGap(18).addComponent(lblBriefDescriptionOf).addGap(18)
			.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)
			.addContainerGap(15, Short.MAX_VALUE)));

	textPaneBriefDescription = new JTextPane();
	scrollPane.setViewportView(textPaneBriefDescription);

	listGuidance = new JList<ElementWrapper>();
	getListGuidance().setCellRenderer(new ListCellRendererCustom());
	getListGuidance().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	DefaultListModel<ElementWrapper> model = new DefaultListModel<>();
	getListGuidance().setModel(model);
	getListGuidance().addListSelectionListener(new ListSelectionListener() {
	    public void valueChanged(ListSelectionEvent e) {
		ElementWrapper elementWrapper = getListGuidance().getSelectedValue();
		if (elementWrapper != null) {
		    DescribableElement element = (DescribableElement) elementWrapper.getElement();
		    textPaneBriefDescription.setText(element.getBriefDescription());
		}
		btnRemove.setEnabled(true);
	    }
	});
	scrollPane_0.setViewportView(getListGuidance());
	setLayout(groupLayout);
    }

    public void setChangeListeners(DocumentListener documentListener, ListDataListener listDataListener) {
	this.documentListener = documentListener;
	this.listDataListener = listDataListener;
	getListGuidance().getModel().addListDataListener(this.listDataListener);
    }

    public void setGuidanceModel(List<JAXBElement<String>> jaxbElements) {
	DefaultListModel<ElementWrapper> model = getGuidanceModel();
	for (JAXBElement<String> jaxbElement : jaxbElements) {
	    NamedElement namedElement = (NamedElement) hash.getHashMap().get(jaxbElement.getValue());
	    model.addElement(new ElementWrapper(namedElement, namedElement.getName(), null));
	}
	getListGuidance().setModel(model);
    }

    public JList<ElementWrapper> getListGuidance() {
	return listGuidance;
    }
}
