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

import org.eclipse.epf.uma.DescribableElement;
import org.eclipse.epf.uma.Element;
import org.eclipse.epf.uma.MethodElement;
import org.eclipse.epf.uma.NamedElement;

import adaptme.base.ElementWrapper;
import adaptme.base.MethodLibraryHash;
import adaptme.ui.components.renderer.ListCellRendererCustom;
import adaptme.ui.window.AdaptMeUI;
import adaptme.util.EPFConstants;

public abstract class RolePanelWorkProducts extends JPanel {

    private static final long serialVersionUID = -3199948513756776726L;
    private JButton btnRemove;
    private DocumentListener documentListener;
    private ListDataListener listDataListener;
    private JList<ElementWrapper> listOutputTasks;

    private JList<ElementWrapper> listResponsibleFor;
    private MethodLibraryHash hash;
    private AdaptMeUI owner;
    private Element element;
    private TabbedPanel tabbedPanel;
    private JTextPane textPaneBriefDescription;

    public abstract void load();

    public abstract void save();

    public abstract void openDialogSelectWorkProducts(Element element, AdaptMeUI owner, TabbedPanel tabbedPanel);

    public abstract void removeWorkProducts(int id, JList<ElementWrapper> listCopyright);

    public RolePanelWorkProducts(Element element, TabbedPanel tabbedPanel, MethodLibraryHash hash, AdaptMeUI owner) {
	addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent e) {
		listResponsibleFor.clearSelection();
		listOutputTasks.clearSelection();
		textPaneBriefDescription.setText("");
	    }
	});
	this.element = element;
	this.hash = hash;
	this.owner = owner;
	this.tabbedPanel = tabbedPanel;
	initComponents();
    }

    public String getBriefDescription() {
	return textPaneBriefDescription.getText();
    }

    public DocumentListener getDocumentListener() {
	return documentListener;
    }

    public ListDataListener getListDataListener() {
	return listDataListener;
    }

    public List<String> getListOutputTasks() {
	ListModel<ElementWrapper> model = listOutputTasks.getModel();
	List<String> list = new ArrayList<>();
	for (int i = 0; i < model.getSize(); i++) {
	    ElementWrapper ew = model.getElementAt(i);
	    list.add(((MethodElement) ew.getElement()).getId());
	}
	return list;
    }

    public ListModel<ElementWrapper> getResponsibleFor() {
	ListModel<ElementWrapper> model = listResponsibleFor.getModel();

	return model;
    }

    private void initComponents() {
	setBackground(Color.WHITE);

	JLabel lblWorkProducts = new JLabel("Work Products:");
	lblWorkProducts.setForeground(Color.BLUE);
	lblWorkProducts.setFont(new Font("Tahoma", Font.PLAIN, 13));

	JLabel lblSpecifyWorkProducts = new JLabel("Specify work products that this element is responsible for.");

	JLabel lblResponsibleFor = new JLabel("Responsible for:");

	JScrollPane scrollPane_0 = new JScrollPane();

	JButton btnAdd = new JButton("Add...");
	btnAdd.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent event) {
		openDialogSelectWorkProducts(element, owner, tabbedPanel);
	    }
	});

	JLabel lblWorkProductsThat = new JLabel(
		"<html>Work products that are output of tasks that this element performs:</html>");

	JScrollPane scrollPane_1 = new JScrollPane();

	btnRemove = new JButton("Remove");
	btnRemove.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		int id = listResponsibleFor.getSelectedIndex();
		removeWorkProducts(id, listResponsibleFor);
	    }

	});
	btnRemove.setEnabled(false);

	JLabel lblBriefDescriptionOf = new JLabel("Brief description of selected element:");

	JScrollPane scrollPane = new JScrollPane();
	scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	GroupLayout groupLayout = new GroupLayout(this);
	groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
		.createSequentialGroup().addContainerGap()
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(lblWorkProducts)
			.addComponent(lblSpecifyWorkProducts).addComponent(lblResponsibleFor)
			.addGroup(groupLayout.createSequentialGroup()
				.addComponent(scrollPane_0, GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
					.addComponent(btnAdd, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)
					.addComponent(btnRemove)))
			.addGroup(groupLayout.createSequentialGroup().addComponent(lblBriefDescriptionOf)
				.addPreferredGap(ComponentPlacement.RELATED, 213, GroupLayout.PREFERRED_SIZE))
			.addGroup(groupLayout.createSequentialGroup()
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE).addGap(81))
			.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
				.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
					.addComponent(lblWorkProductsThat, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
						399, Short.MAX_VALUE)
					.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE))
				.addGap(81)))
		.addGap(6)));
	groupLayout
		.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout
				.createSequentialGroup().addGap(
					44)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup().addComponent(lblWorkProducts)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(lblSpecifyWorkProducts)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(lblResponsibleFor).addGap(8).addComponent(scrollPane_0,
							GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE))
			.addGroup(groupLayout.createSequentialGroup().addGap(73).addComponent(btnAdd).addGap(5)
				.addComponent(btnRemove))).addGap(18).addComponent(lblWorkProductsThat)
		.addPreferredGap(ComponentPlacement.RELATED)
		.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE).addGap(18)
		.addComponent(lblBriefDescriptionOf).addPreferredGap(ComponentPlacement.RELATED)
		.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
		.addContainerGap(18, Short.MAX_VALUE)));

	textPaneBriefDescription = new JTextPane();
	scrollPane.setViewportView(textPaneBriefDescription);

	listOutputTasks = new JList<>();
	listOutputTasks.setCellRenderer(new ListCellRendererCustom());
	listOutputTasks.addListSelectionListener(new ListSelectionListener() {
	    public void valueChanged(ListSelectionEvent e) {
		ElementWrapper elementWrapper = listOutputTasks.getSelectedValue();
		if (elementWrapper != null) {
		    DescribableElement element = (DescribableElement) elementWrapper.getElement();
		    textPaneBriefDescription.setText(element.getBriefDescription());
		}
	    }
	});
	scrollPane_1.setViewportView(listOutputTasks);

	listResponsibleFor = new JList<ElementWrapper>();
	listResponsibleFor.setCellRenderer(new ListCellRendererCustom());
	listResponsibleFor.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	DefaultListModel<ElementWrapper> model = new DefaultListModel<>();
	listResponsibleFor.setModel(model);
	listResponsibleFor.addListSelectionListener(new ListSelectionListener() {
	    public void valueChanged(ListSelectionEvent e) {
		btnRemove.setEnabled(true);
		ElementWrapper elementWrapper = listResponsibleFor.getSelectedValue();
		if (elementWrapper != null) {
		    DescribableElement element = (DescribableElement) elementWrapper.getElement();
		    textPaneBriefDescription.setText(element.getBriefDescription());
		}
	    }
	});

	scrollPane_0.setViewportView(listResponsibleFor);
	setLayout(groupLayout);
    }

    public void setBriefDescription(String text) {
	textPaneBriefDescription.setText(text);
    }

    public void setChangeListeners(DocumentListener documentListener, ListDataListener listDataListener) {
	this.documentListener = documentListener;
	this.listDataListener = listDataListener;
	listResponsibleFor.getModel().addListDataListener(listDataListener);
    }

    public void setOutputTasks(List<String> list, MethodLibraryHash mlHash) {
	ListModel<ElementWrapper> model = new DefaultListModel<>();
	for (String id : list) {
	    NamedElement namedElement = (NamedElement) mlHash.getHashMap().get(id);
	    ((DefaultListModel<ElementWrapper>) model)
		    .addElement(new ElementWrapper(namedElement, namedElement.getName(), EPFConstants.artifactIcon));
	}
	listOutputTasks.setModel(model);
    }

    public void setResponsibleFor(DefaultListModel<ElementWrapper> model) {
	listResponsibleFor.setModel(model);
    }

    public void setResponsibleFor(List<String> list) {
	ListModel<ElementWrapper> model = getResponsibleFor();
	for (String id : list) {
	    NamedElement namedElement = (NamedElement) hash.getHashMap().get(id);
	    ((DefaultListModel<ElementWrapper>) model)
		    .addElement(new ElementWrapper(namedElement, namedElement.getName(), EPFConstants.artifactIcon));
	}
	listResponsibleFor.setModel(model);
    }
}
