package adaptme.ui.panel.base.workproduct;

import java.awt.Color;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListDataListener;

import org.eclipse.epf.uma.WorkProduct;

import adaptme.base.ElementWrapper;
import adaptme.base.MethodLibraryHash;
import adaptme.ui.panel.base.TabbedPanel;
import adaptme.ui.window.AdaptMeUI;

public class WorkProductPanelStates extends JPanel {

    private static final long serialVersionUID = 2633782804271615909L;
    // private WorkProduct workProduct;
    // private MethodLibraryHash hash;
    // private AdaptMeUI owner;
    // private TabbedPanel tabbedPanel;
    private DocumentListener documentListener;
    private ListDataListener listDataListener;

    public WorkProductPanelStates(WorkProduct workProduct, TabbedPanel tabbedPanel, MethodLibraryHash mlHash,
	    AdaptMeUI owner) {

	// this.workProduct = workProduct;
	// this.hash = mlHash;
	// this.owner = owner;
	// this.tabbedPanel = tabbedPanel;
	initComponents();
    }

    private void initComponents() {
	setBackground(Color.WHITE);

	JLabel lblWorkProduct = new JLabel("Work Product");

	JLabel lblSpecifyTheStates = new JLabel("Specify the states associate with this work product.");

	JLabel lblStatesTheWork = new JLabel("States the work product has:");

	JScrollPane scrollPane = new JScrollPane();

	JButton button = new JButton("<-- Assign");

	JButton btnUnassign = new JButton("Unassign");

	JScrollPane scrollPane_1 = new JScrollPane();

	JButton btnManageState = new JButton("Manage State...");

	JLabel lblnoteStatesIn = new JLabel(
		"<html>Note: States in boldface are defined in the current plug-in and can be deleted or updated, other states are from all other plug-ins and have to be deleted or updated from their defining plug-ins.</html>");

	JLabel lblDescription = new JLabel("Description:");

	JScrollPane scrollPane_2 = new JScrollPane();

	GroupLayout groupLayout = new GroupLayout(this);
	groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
		.createSequentialGroup().addContainerGap()
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
			.createSequentialGroup()
			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(lblWorkProduct)
				.addComponent(lblSpecifyTheStates).addComponent(lblStatesTheWork)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane_2, Alignment.LEADING)
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 207,
								Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout
								.createParallelGroup(Alignment.LEADING, false)
								.addComponent(btnUnassign, GroupLayout.DEFAULT_SIZE,
									GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(button, GroupLayout.DEFAULT_SIZE,
									GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 212,
								Short.MAX_VALUE)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(lblnoteStatesIn, GroupLayout.PREFERRED_SIZE, 116,
							GroupLayout.PREFERRED_SIZE)
						.addComponent(btnManageState))))
			.addGap(19))
			.addGroup(groupLayout.createSequentialGroup().addComponent(lblDescription).addContainerGap(648,
				Short.MAX_VALUE)))));
	groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
		.createSequentialGroup()
		.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(lblWorkProduct)
				.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblSpecifyTheStates)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup().addGap(52).addComponent(button)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnUnassign))
					.addGroup(groupLayout.createSequentialGroup().addGap(18)
						.addComponent(lblStatesTheWork).addGap(8)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 152,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 152,
								GroupLayout.PREFERRED_SIZE)))))
			.addGroup(groupLayout.createSequentialGroup().addGap(98).addComponent(btnManageState)
				.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblnoteStatesIn)))
		.addGap(19).addComponent(lblDescription).addPreferredGap(ComponentPlacement.RELATED)
		.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
		.addContainerGap(31, Short.MAX_VALUE)));

	JList<ElementWrapper> list_1 = new JList<ElementWrapper>();
	scrollPane_1.setViewportView(list_1);

	JList<ElementWrapper> list = new JList<ElementWrapper>();
	scrollPane.setViewportView(list);

	JTextArea textArea = new JTextArea();
	scrollPane_2.setViewportView(textArea);
	setLayout(groupLayout);
    }

    public void save() {

    }

    public void load() {

    }

    public void setChangeListeners(DocumentListener documentListener, ListDataListener listDataListener) {

	this.documentListener = documentListener;
	this.listDataListener = listDataListener;

    }

    public DocumentListener getDocumentListener() {
	return documentListener;
    }

    public ListDataListener getListDataListener() {
	return listDataListener;
    }
}
