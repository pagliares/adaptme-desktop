package adaptme.ui.window.perspective;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultCellEditor;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import adaptme.ui.dynamic.simulation.alternative.process.IntegratedLocalAndRepositoryViewPanel;
import model.spem.ProcessContentRepository;
import simulator.base.Policy;
import simulator.base.QueueType;
import simulator.base.Role;
import simulator.gui.model.RoleTableModel;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.GridBagLayout;

public class RoleResourcesPanel {
	
	private JPanel topPanel;
	private JScrollPane scrollPaneTableRole;
	private JPanel outerRoleResourcesBottomPanel;
	private JTable tableRole;
 	private TableColumnModel modeloColuna;
 	private RoleTableModel roleTableModel;
 	private JPanel titlePanel;
	
 	private List<Role> roles = new ArrayList<>();
 	private JComboBox<QueueType> queueTypeJComboBox;
 	
	private RoleResourcesBottomPanel roleResourcesBottomPannel;

	private List<JPanel> listOfRoleResourcesBottomPanels = new ArrayList<>();
	
  	
 	private int indexSelectedRow;

	public RoleResourcesPanel() {
 		
		queueTypeJComboBox = new JComboBox<>();
		queueTypeJComboBox.addItem(QueueType.QUEUE);
		queueTypeJComboBox.addItem(QueueType.SET);
		queueTypeJComboBox.addItem(QueueType.STACK);

		topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		
		titlePanel = new JPanel();
		titlePanel.setLayout(new BorderLayout());
		titlePanel.setBorder(new TitledBorder(null, "Role resources", TitledBorder.LEADING, TitledBorder.TOP, null,new Color(59, 59, 59)));

		scrollPaneTableRole = new JScrollPane();
 		tableRole = new JTable();
		tableRole.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));

		scrollPaneTableRole.setPreferredSize(new Dimension(700, 200));
		scrollPaneTableRole.setViewportView(tableRole);
		
 		
		titlePanel.add(scrollPaneTableRole, BorderLayout.NORTH);
		
	

		
 		GroupLayout gl_outerPanel = new GroupLayout(topPanel);
		gl_outerPanel.setHorizontalGroup(
			gl_outerPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_outerPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(titlePanel, GroupLayout.PREFERRED_SIZE, 568, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_outerPanel.setVerticalGroup(
			gl_outerPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_outerPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(titlePanel, GroupLayout.PREFERRED_SIZE, 459, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(14, Short.MAX_VALUE))
		);
		
		
		outerRoleResourcesBottomPanel = new JPanel();
 		outerRoleResourcesBottomPanel.setLayout(new BorderLayout());
 		outerRoleResourcesBottomPanel.setBorder(new TitledBorder(null, "Configure observers", TitledBorder.LEADING, TitledBorder.TOP, null,new Color(59, 59, 59)));
 		titlePanel.add(outerRoleResourcesBottomPanel, BorderLayout.CENTER);
	}

	

	public void setComboBoxRole(Set<String> list) {
		 
		String[] names = list.toArray(new String[list.size()]);

		for (int i = 0; i < names.length; i++) {
			Role role = new Role();
			role.setName(names[i]);
			roles.add(role);
			roleResourcesBottomPannel = new RoleResourcesBottomPanel(i, names[i]);
			roleResourcesBottomPannel.setQueueNameTextField(names[i]);
			listOfRoleResourcesBottomPanels.add(roleResourcesBottomPannel);
		}

		roleTableModel = new RoleTableModel(roles);
 		tableRole.setModel(roleTableModel);
 		configuraColunas();
 		tableRole.changeSelection(0, 0, false, false);  // seleciona a primeira linha da tabela por default
 		
 		outerRoleResourcesBottomPanel.add((RoleResourcesBottomPanel) listOfRoleResourcesBottomPanels.get(0), BorderLayout.CENTER);

 		for (int i = 0; i < names.length; i++) {
 			tableRole.setValueAt(QueueType.QUEUE, i, 1);
		}
		
		
 		
 	}
	
	public void configuraTableListener() { 

		// Listener disparado ao selecionar uma linha da tabela
		tableRole.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent event) {

				indexSelectedRow = tableRole.getSelectedRow();
				//	boolean isRowandCheckBoxSelected = (Boolean) model.getValueAt(tableWorkProduct.getSelectedRow(),1) == true;

				if ((indexSelectedRow > -1)) { 					 
					roleResourcesBottomPannel = (RoleResourcesBottomPanel) listOfRoleResourcesBottomPanels.get(indexSelectedRow);
					String roleName = (String)tableRole.getValueAt(indexSelectedRow, 0);
					roleResourcesBottomPannel.setQueueNameTextField(roleName);
					outerRoleResourcesBottomPanel.removeAll();
					outerRoleResourcesBottomPanel.add(roleResourcesBottomPannel, BorderLayout.CENTER);
					outerRoleResourcesBottomPanel.updateUI();
				}
			}
		});

		 
 	}
	
	public void configuraColunas() { 
		
		modeloColuna = tableRole.getColumnModel(); 
		
		TableColumn colunaQueueType = modeloColuna.getColumn(1);
		colunaQueueType.setCellEditor(new DefaultCellEditor(queueTypeJComboBox));
		 
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		
		tableRole.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		tableRole.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		tableRole.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		tableRole.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
 

		((DefaultTableCellRenderer) tableRole.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

		tableRole.getColumnModel().getColumn(0).setPreferredWidth(15);
		tableRole.getColumnModel().getColumn(1).setPreferredWidth(23);
		tableRole.getColumnModel().getColumn(2).setPreferredWidth(9);
		tableRole.getColumnModel().getColumn(3).setPreferredWidth(9);
		tableRole.getColumnModel().getColumn(4).setPreferredWidth(9);
		
	}

	public List<Role> getRoles() {
		return roles;
	}

	public JTable getTableRole() {
		return tableRole;
	}
	
	public JPanel getTopPanel() {
		return topPanel;
	}
}
