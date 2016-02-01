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
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.eclipse.epf.uma.ContentCategory;
import org.eclipse.epf.uma.ContentCategoryPackage;
import org.eclipse.epf.uma.CustomCategory;
import org.eclipse.epf.uma.DescribableElement;
import org.eclipse.epf.uma.Element;

import adaptme.base.ElementWrapper;
import adaptme.base.MethodLibraryHash;
import adaptme.ui.components.renderer.ListCellRendererCustom;
import adaptme.ui.window.AdaptMeUI;
import adaptme.util.EPFConstants;

public abstract class PanelCategories extends JPanel {

    private static final long serialVersionUID = 665308479993242923L;
    private JLabel categoryLabel;
    // private ContentCategoryPackage contentCategoryPackage;
    private DocumentListener documentListener;

    private Element element;
    // private MethodLibraryHash hash;
    private JList<ElementWrapper> listCategory;
    private JList<ElementWrapper> listCustomCategories;
    private ListDataListener listDataListener;
    private AdaptMeUI owner;
    private TabbedPanel tabbedPanel;
    private JTextPane textPaneBriefDescription;
    private JLabel titleLabel;
    private JButton btnRemoveCategory;
    private JButton btnRemoveCustomCategories;

    public abstract void load();

    public abstract void save();

    public abstract void removeCategory(int id, JList<ElementWrapper> listCategory);

    public abstract void removeCustomCategory(int id, JList<ElementWrapper> listCustomCategories);

    public abstract void openDialogCategory(Element element, AdaptMeUI owner, TabbedPanel tabbedPanel);

    public abstract void openDialogCustomCategory(Element element, AdaptMeUI owner, TabbedPanel tabbedPanel);

    public abstract void setStandardCategories(List<ContentCategory> list);

    public PanelCategories(Element element, MethodLibraryHash hash, ContentCategoryPackage contentCategoryPackage,
	    TabbedPanel tabbedPanel, AdaptMeUI owner) {
	this.element = element;
	// this.hash = hash;
	// this.contentCategoryPackage = contentCategoryPackage;
	this.owner = owner;
	this.tabbedPanel = tabbedPanel;
	initComponets();
    }

    public String getBriefDescription() {
	return textPaneBriefDescription.getText();
    }

    public List<CustomCategory> getCustomCategories() {
	DefaultListModel<ElementWrapper> model = (DefaultListModel<ElementWrapper>) listCustomCategories.getModel();
	List<CustomCategory> list = new ArrayList<>();
	for (int i = 0; i < model.getSize(); i++) {
	    list.add((CustomCategory) model.getElementAt(i).getElement());
	}
	return list;
    }

    public DocumentListener getDocumentListener() {
	return documentListener;
    }

    public ListDataListener getListDataListener() {
	return listDataListener;
    }

    public List<ContentCategory> getStandardCategorys() {
	DefaultListModel<ElementWrapper> model = (DefaultListModel<ElementWrapper>) getListCategory().getModel();
	List<ContentCategory> list = new ArrayList<>();
	for (int i = 0; i < model.getSize(); i++) {
	    list.add((ContentCategory) model.getElementAt(i).getElement());
	}
	return list;
    }

    private void initComponets() {
	addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent e) {
		listCustomCategories.clearSelection();
		getListCategory().clearSelection();
		textPaneBriefDescription.setText("");
		btnRemoveCategory.setEnabled(false);
		btnRemoveCustomCategories.setEnabled(false);
	    }
	});
	setBackground(Color.WHITE);

	JLabel lblCategories = new JLabel("Categories:");
	lblCategories.setForeground(Color.BLUE);
	lblCategories.setFont(new Font("Tahoma", Font.PLAIN, 13));

	titleLabel = new JLabel("Title.");

	categoryLabel = new JLabel("Category:");

	JScrollPane scrollPane = new JScrollPane();
	scrollPane.getVerticalScrollBar().setUnitIncrement(16);

	JButton btnAddCategory = new JButton("Add...");
	btnAddCategory.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		openDialogCategory(element, owner, tabbedPanel);
	    }
	});

	btnRemoveCategory = new JButton("Remove");
	btnRemoveCategory.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		int id = getListCategory().getSelectedIndex();
		removeCategory(id, getListCategory());
	    }
	});
	btnRemoveCategory.setEnabled(false);

	JLabel lblCustomCategories = new JLabel("Custom categories:");

	JScrollPane scrollPane_1 = new JScrollPane();
	scrollPane_1.getVerticalScrollBar().setUnitIncrement(16);

	JLabel lblBriefDescriptionOf = new JLabel("Brief description of selected element:");

	JScrollPane scrollPane_2 = new JScrollPane();
	scrollPane_2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	scrollPane_2.getVerticalScrollBar().setUnitIncrement(16);

	JButton btnAddCustomCategories = new JButton("Add...");
	btnAddCustomCategories.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		openDialogCustomCategory(element, owner, tabbedPanel);
	    }
	});

	btnRemoveCustomCategories = new JButton("Remove");
	btnRemoveCustomCategories.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		int id = listCustomCategories.getSelectedIndex();
		removeCustomCategory(id, listCustomCategories);
		// listCustomCategories.updateUI();
	    }
	});

	btnRemoveCustomCategories.setEnabled(false);
	GroupLayout groupLayout = new GroupLayout(this);
	groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
		.addGroup(groupLayout.createSequentialGroup().addContainerGap()
			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(lblCategories)
				.addComponent(titleLabel)
				.addGroup(groupLayout.createSequentialGroup().addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(categoryLabel).addComponent(lblCustomCategories)
						.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 331,
							Short.MAX_VALUE)
						.addComponent(lblBriefDescriptionOf)
						.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 331,
							Short.MAX_VALUE)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE))))
		.addGap(18)
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
				.addComponent(btnAddCustomCategories, GroupLayout.DEFAULT_SIZE,
					GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
			.addComponent(btnRemoveCategory, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
				Short.MAX_VALUE).addComponent(btnAddCategory, 0, 0, Short.MAX_VALUE))
			.addComponent(btnRemoveCustomCategories)).addContainerGap()));
	groupLayout
		.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(
					groupLayout.createSequentialGroup()
						.addGroup(groupLayout
							.createParallelGroup(
								Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup().addGap(31)
								.addComponent(lblCategories)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(titleLabel)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(categoryLabel)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE,
									166, GroupLayout.PREFERRED_SIZE))
			.addGroup(groupLayout.createSequentialGroup().addGap(103).addComponent(btnAddCategory)
				.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(btnRemoveCategory)))
		.addPreferredGap(ComponentPlacement.RELATED)
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup().addGap(18).addComponent(lblCustomCategories)
				.addPreferredGap(ComponentPlacement.RELATED).addComponent(scrollPane_1,
					GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE))
			.addGroup(groupLayout.createSequentialGroup().addGap(49).addComponent(btnAddCustomCategories)
				.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnRemoveCustomCategories)))
		.addGap(18).addComponent(lblBriefDescriptionOf).addPreferredGap(ComponentPlacement.RELATED)
		.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE)
		.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

	textPaneBriefDescription = new JTextPane();
	scrollPane_2.setViewportView(textPaneBriefDescription);

	listCustomCategories = new JList<>();
	listCustomCategories.setCellRenderer(new ListCellRendererCustom());
	listCustomCategories.addListSelectionListener(new ListSelectionListener() {
	    public void valueChanged(ListSelectionEvent e) {
		getListCategory().clearSelection();
		ElementWrapper elementWrapper = listCustomCategories.getSelectedValue();
		if (elementWrapper != null) {
		    DescribableElement element = (DescribableElement) elementWrapper.getElement();
		    textPaneBriefDescription.setText(element.getBriefDescription());
		}
		btnRemoveCustomCategories.setEnabled(true);
	    }
	});
	ListModel<ElementWrapper> model = new DefaultListModel<>();
	listCustomCategories.setModel(model);
	listCustomCategories.setCellRenderer(new ListCellRendererCustom());
	scrollPane_1.setViewportView(listCustomCategories);

	listCategory = new JList<>();
	getListCategory().setCellRenderer(new ListCellRendererCustom());
	getListCategory().addListSelectionListener(new ListSelectionListener() {
	    public void valueChanged(ListSelectionEvent e) {
		listCustomCategories.clearSelection();
		ElementWrapper elementWrapper = getListCategory().getSelectedValue();
		if (elementWrapper != null) {
		    DescribableElement element = (DescribableElement) elementWrapper.getElement();
		    textPaneBriefDescription.setText(element.getBriefDescription());
		}
		btnRemoveCategory.setEnabled(true);
		btnRemoveCustomCategories.setEnabled(false);
	    }
	});
	model = new DefaultListModel<>();
	getListCategory().setModel(model);
	scrollPane.setViewportView(getListCategory());
	setLayout(groupLayout);
    }

    public void setChangeListeners(DocumentListener documentListener, ListDataListener listDataListener) {
	this.documentListener = documentListener;
	this.listDataListener = listDataListener;
	listCustomCategories.getModel().addListDataListener(listDataListener);
	listCategory.getModel().addListDataListener(listDataListener);
    }

    public void setCustomCategories(List<CustomCategory> list) {
	DefaultListModel<ElementWrapper> model = (DefaultListModel<ElementWrapper>) listCustomCategories.getModel();
	for (CustomCategory customCategory : list) {
	    model.addElement(
		    new ElementWrapper(customCategory, customCategory.getName(), EPFConstants.customCategoryIcon));
	}
	listCustomCategories.setModel(model);
    }

    public void setTitleLabel(String text) {
	titleLabel.setText(text);
    }

    public void setCategoryLabel(String text) {
	categoryLabel.setText(text);
    }

    public JList<ElementWrapper> getListCategory() {
	return listCategory;
    }
}
