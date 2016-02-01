package adaptme.ui.panel.base.workproduct;

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
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.eclipse.epf.uma.ContentCategoryPackage;
import org.eclipse.epf.uma.CustomCategory;
import org.eclipse.epf.uma.Domain;
import org.eclipse.epf.uma.WorkProduct;
import org.eclipse.epf.uma.WorkProductType;

import adaptme.base.ElementWrapper;
import adaptme.base.MethodLibraryHash;
import adaptme.ui.components.renderer.ListCellRendererCustom;
import adaptme.ui.panel.base.TabbedPanel;
import adaptme.ui.window.AdaptMeUI;
import adaptme.util.EPFConstants;

public abstract class WorkProductPanelCategories extends JPanel {

    private static final long serialVersionUID = 5535322748740659498L;
    private JTextPane textPaneBriefDescription;
    private JList<ElementWrapper> listWorkProductKinks;
    private JList<ElementWrapper> listCustomCategories;
    private JList<ElementWrapper> listDomain;

    private WorkProduct workProduct;
    // private MethodLibraryHash hash;
    private ContentCategoryPackage contentCategoryPackage;
    private AdaptMeUI owner;
    private TabbedPanel tabbedPanel;
    private JButton btnAddWorkProductKinks;
    private JButton btnRemoveWorkProductKinks;
    private JButton btnAddCustomCategories;
    private JButton btnRemoveCustomCategories;
    private JButton btnRemoveDomain;
    private JButton btnAddDomain;
    private DocumentListener documentListener;
    private ListDataListener listDataListener;

    public abstract void load();

    public abstract void save();

    public abstract void openDialogWorkProductType(WorkProduct workProduct, AdaptMeUI owner, TabbedPanel tabbedPanel);

    public abstract void removeWorkProductType(int index, JList<ElementWrapper> listCustomCategories);

    public abstract void openDialogCustomCategories(WorkProduct workProduct, AdaptMeUI owner, TabbedPanel tabbedPanel);

    public abstract void removeCustomCategories(int index, JList<ElementWrapper> listCustomCategories);

    public abstract void openDialogDomain(WorkProduct workProduct, AdaptMeUI owner, TabbedPanel tabbedPanel);

    public abstract void removeDomain(int index, JList<ElementWrapper> listCustomCategories);

    public WorkProductPanelCategories(WorkProduct workProduct, MethodLibraryHash hash,
	    ContentCategoryPackage contentCategoryPackage, TabbedPanel tabbedPanel, AdaptMeUI owner) {
	addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent e) {
		listCustomCategories.clearSelection();
		listDomain.clearSelection();
		getListWorkProductKinks().clearSelection();
		btnRemoveCustomCategories.setEnabled(false);
		btnRemoveDomain.setEnabled(false);
		btnRemoveWorkProductKinks.setEnabled(false);
	    }
	});
	this.workProduct = workProduct;
	// this.hash = hash;
	this.owner = owner;
	this.setContentCategoryPackage(contentCategoryPackage);
	this.tabbedPanel = tabbedPanel;
	initComponets();
    }

    private void initComponets() {
	setBackground(Color.WHITE);

	JLabel lblCategories = new JLabel("Categories:");
	lblCategories.setForeground(Color.BLUE);
	lblCategories.setFont(new Font("Tahoma", Font.PLAIN, 13));

	JScrollPane scrollPane = new JScrollPane();

	btnAddDomain = new JButton("Add..");
	btnAddDomain.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		openDialogDomain(workProduct, owner, tabbedPanel);
	    }
	});

	btnRemoveDomain = new JButton("Remove");
	btnRemoveDomain.setEnabled(false);
	btnRemoveDomain.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		int index = listDomain.getSelectedIndex();
		removeDomain(index, listDomain);
	    }
	});

	JScrollPane scrollPane_1 = new JScrollPane();

	btnAddWorkProductKinks = new JButton("Add...");
	btnAddWorkProductKinks.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		openDialogWorkProductType(workProduct, owner, tabbedPanel);
	    }
	});

	btnRemoveWorkProductKinks = new JButton("Remove");
	btnRemoveWorkProductKinks.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		int index = getListWorkProductKinks().getSelectedIndex();
		removeWorkProductType(index, getListWorkProductKinks());
	    }
	});
	btnRemoveWorkProductKinks.setEnabled(false);

	JLabel lblBriefDescriptionOf = new JLabel("Brief description of selected element:");

	JScrollPane scrollPane_2 = new JScrollPane();
	scrollPane_2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

	JLabel lblWorkProduct = new JLabel("Work Product");

	JLabel lblManageTheCategories = new JLabel("Manage the categories to which this work product belongs.");

	JLabel lblDomain = new JLabel("Domain:");

	JLabel lblWorkProductKinds = new JLabel("Work product kinds:");

	JScrollPane scrollPane_3 = new JScrollPane();

	JLabel lblCustomCategories = new JLabel("Custom categories:");

	btnAddCustomCategories = new JButton("Add...");
	btnAddCustomCategories.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		openDialogCustomCategories(workProduct, owner, tabbedPanel);
	    }
	});

	btnRemoveCustomCategories = new JButton("Remove");
	btnRemoveCustomCategories.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		int index = listCustomCategories.getSelectedIndex();
		removeCustomCategories(index, listCustomCategories);
	    }
	});
	btnRemoveCustomCategories.setEnabled(false);
	GroupLayout groupLayout = new GroupLayout(this);
	groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
		.createSequentialGroup().addContainerGap()
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup().addGap(10).addComponent(lblDomain))
			.addGroup(groupLayout.createSequentialGroup()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(lblCategories)
					.addGroup(groupLayout.createSequentialGroup().addGap(10)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(scrollPane_3, GroupLayout.DEFAULT_SIZE, 343,
								Short.MAX_VALUE)
							.addComponent(lblBriefDescriptionOf).addComponent(scrollPane_2,
								GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE))))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
					.addComponent(btnAddCustomCategories, GroupLayout.PREFERRED_SIZE, 71,
						GroupLayout.PREFERRED_SIZE)
					.addComponent(btnRemoveCustomCategories)))
			.addComponent(lblWorkProduct).addComponent(lblManageTheCategories)
			.addGroup(groupLayout.createSequentialGroup().addGap(10)
				.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
					.addGroup(groupLayout.createSequentialGroup()
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 339,
							Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
							.addComponent(btnRemoveDomain, GroupLayout.DEFAULT_SIZE,
								GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnAddDomain, 0, 0, Short.MAX_VALUE)))
					.addGroup(groupLayout.createSequentialGroup()
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
							.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 343,
								Short.MAX_VALUE)
						.addComponent(lblWorkProductKinds, Alignment.LEADING)
						.addComponent(lblCustomCategories, Alignment.LEADING))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
							.addComponent(btnRemoveWorkProductKinks).addComponent(
								btnAddWorkProductKinks, GroupLayout.PREFERRED_SIZE, 71,
								GroupLayout.PREFERRED_SIZE))))))
		.addContainerGap()));
	groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
		.addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(lblWorkProduct)
			.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblCategories)
			.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblManageTheCategories)
			.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblDomain).addGap(8)
			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addComponent(btnAddDomain)
					.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(btnRemoveDomain))
			.addGroup(groupLayout.createSequentialGroup().addGap(2).addComponent(scrollPane, 0, 0,
				Short.MAX_VALUE)))
			.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(lblWorkProductKinds)
			.addPreferredGap(
				ComponentPlacement.RELATED)
		.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
			.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
			.addGroup(groupLayout.createSequentialGroup().addComponent(btnAddWorkProductKinks)
				.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnRemoveWorkProductKinks)))
		.addGap(12).addComponent(lblCustomCategories).addGap(18)
		.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
			.addComponent(scrollPane_3, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
			.addGroup(groupLayout.createSequentialGroup().addComponent(btnAddCustomCategories)
				.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnRemoveCustomCategories)))
		.addGap(18).addComponent(lblBriefDescriptionOf).addPreferredGap(ComponentPlacement.RELATED)
		.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE)
		.addContainerGap(42, Short.MAX_VALUE)));

	ListModel<ElementWrapper> modelCustomCategories = new DefaultListModel<>();
	listCustomCategories = new JList<ElementWrapper>();
	listCustomCategories.addListSelectionListener(new ListSelectionListener() {
	    public void valueChanged(ListSelectionEvent e) {
		listDomain.clearSelection();
		getListWorkProductKinks().clearSelection();

		btnRemoveCustomCategories.setEnabled(true);
		btnRemoveDomain.setEnabled(false);
		btnRemoveWorkProductKinks.setEnabled(false);

	    }
	});
	listCustomCategories.setModel(modelCustomCategories);
	listCustomCategories.setCellRenderer(new ListCellRendererCustom());
	scrollPane_3.setViewportView(listCustomCategories);

	textPaneBriefDescription = new JTextPane();
	scrollPane_2.setViewportView(textPaneBriefDescription);

	ListModel<ElementWrapper> modelWorkProductKinks = new DefaultListModel<>();
	setListWorkProductKinks(new JList<ElementWrapper>());
	getListWorkProductKinks().addListSelectionListener(new ListSelectionListener() {
	    public void valueChanged(ListSelectionEvent e) {
		listCustomCategories.clearSelection();
		listDomain.clearSelection();
		btnRemoveCustomCategories.setEnabled(false);
		btnRemoveDomain.setEnabled(false);
		btnRemoveWorkProductKinks.setEnabled(true);
	    }
	});
	getListWorkProductKinks().setModel(modelWorkProductKinks);
	getListWorkProductKinks().setCellRenderer(new ListCellRendererCustom());
	scrollPane_1.setViewportView(getListWorkProductKinks());

	ListModel<ElementWrapper> modelDomain = new DefaultListModel<>();
	listDomain = new JList<ElementWrapper>();
	listDomain.addListSelectionListener(new ListSelectionListener() {
	    public void valueChanged(ListSelectionEvent e) {
		listCustomCategories.clearSelection();
		getListWorkProductKinks().clearSelection();

		btnRemoveCustomCategories.setEnabled(false);
		btnRemoveDomain.setEnabled(true);
		btnRemoveWorkProductKinks.setEnabled(false);
	    }
	});
	listDomain.setCellRenderer(new ListCellRendererCustom());
	listDomain.setModel(modelDomain);
	scrollPane.setViewportView(listDomain);
	setLayout(groupLayout);
    }

    public JTextPane getBriefDescription() {
	return textPaneBriefDescription;
    }

    public List<WorkProductType> getWorkProductType() {
	DefaultListModel<ElementWrapper> model = (DefaultListModel<ElementWrapper>) getListWorkProductKinks()
		.getModel();
	List<WorkProductType> list = new ArrayList<>();
	for (int i = 0; i < model.getSize(); i++) {
	    list.add((WorkProductType) model.getElementAt(i).getElement());
	}
	return list;
    }

    public List<CustomCategory> getCustomCategories() {
	DefaultListModel<ElementWrapper> model = (DefaultListModel<ElementWrapper>) listCustomCategories.getModel();
	List<CustomCategory> list = new ArrayList<>();
	for (int i = 0; i < model.getSize(); i++) {
	    list.add((CustomCategory) model.getElementAt(i).getElement());
	}
	return list;
    }

    public List<Domain> getDomain() {
	DefaultListModel<ElementWrapper> model = (DefaultListModel<ElementWrapper>) listDomain.getModel();
	List<Domain> list = new ArrayList<>();
	for (int i = 0; i < model.getSize(); i++) {
	    list.add((Domain) model.getElementAt(i).getElement());
	}
	return list;
    }

    public void setWorkProductKinks(List<WorkProductType> list) {
	DefaultListModel<ElementWrapper> model = (DefaultListModel<ElementWrapper>) getListWorkProductKinks()
		.getModel();
	for (WorkProductType workProductType : list) {
	    model.addElement(
		    new ElementWrapper(workProductType, workProductType.getName(), EPFConstants.workProductKindIcon));
	}
	getListWorkProductKinks().setModel(model);
    }

    public void setDomain(List<Domain> list) {
	DefaultListModel<ElementWrapper> model = (DefaultListModel<ElementWrapper>) listDomain.getModel();
	for (Domain domain : list) {
	    model.addElement(new ElementWrapper(domain, domain.getName(), EPFConstants.domainsIcon));
	}
	listDomain.setModel(model);
    }

    public void setCustomCategories(List<CustomCategory> list) {
	DefaultListModel<ElementWrapper> model = (DefaultListModel<ElementWrapper>) listCustomCategories.getModel();
	for (CustomCategory customCategory : list) {
	    model.addElement(
		    new ElementWrapper(customCategory, customCategory.getName(), EPFConstants.customCategoryIcon));
	}
	listCustomCategories.setModel(model);
    }

    public void setChangeListeners(DocumentListener documentListener, ListDataListener listDataListener) {

	this.documentListener = documentListener;
	this.listDataListener = listDataListener;

	listCustomCategories.getModel().addListDataListener(this.listDataListener);
	listDomain.getModel().addListDataListener(this.listDataListener);
	getListWorkProductKinks().getModel().addListDataListener(this.listDataListener);

    }

    public DocumentListener getDocumentListener() {
	return documentListener;
    }

    public ListDataListener getListDataListener() {
	return listDataListener;
    }

    public JList<ElementWrapper> getListWorkProductKinks() {
	return listWorkProductKinks;
    }

    public void setListWorkProductKinks(JList<ElementWrapper> listWorkProductKinks) {
	this.listWorkProductKinks = listWorkProductKinks;
    }

    public ContentCategoryPackage getContentCategoryPackage() {
	return contentCategoryPackage;
    }

    public void setContentCategoryPackage(ContentCategoryPackage contentCategoryPackage) {
	this.contentCategoryPackage = contentCategoryPackage;
    }
}
