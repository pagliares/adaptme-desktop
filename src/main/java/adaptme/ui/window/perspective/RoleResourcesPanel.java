package adaptme.ui.window.perspective;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import adaptme.ui.dynamic.simulation.alternative.process.IntegratedLocalAndRepositoryViewPanel;
import model.spem.ProcessContentRepository;
import simulator.base.QueueType;
import simulator.base.Role;
import simulator.gui.model.RoleTableModel;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.GridBagLayout;

public class RoleResourcesPanel {
	
	private JPanel outerPanel;
	private JScrollPane scrollPane;
	
	private JTable tableRole;
 	private TableColumnModel modeloColuna;
 	private RoleTableModel roleTableModel;
	
 	private List<Role> roles = new ArrayList<>();
 	private JComboBox<QueueType> queueTypeJComboBox;
 	private HashMap<String, IntegratedLocalAndRepositoryViewPanel> hashMapLocalView;

	public RoleResourcesPanel(HashMap<String, IntegratedLocalAndRepositoryViewPanel> hashMapLocalView) {
		this.hashMapLocalView = hashMapLocalView;
		
		queueTypeJComboBox = new JComboBox<>();
		queueTypeJComboBox.addItem(QueueType.QUEUE);
		queueTypeJComboBox.addItem(QueueType.SET);
		queueTypeJComboBox.addItem(QueueType.STACK);

		outerPanel = new JPanel();
		JPanel roleResourcesPanel = new JPanel();
		roleResourcesPanel.setBorder(new TitledBorder(null, "Role resources", TitledBorder.LEADING, TitledBorder.TOP, null,new Color(59, 59, 59)));

		scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(null);
		tableRole = new JTable();
		tableRole.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));

		scrollPane.setViewportView(tableRole);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setViewportBorder(null);
		GroupLayout gl_outerPanel = new GroupLayout(outerPanel);
		gl_outerPanel.setHorizontalGroup(
			gl_outerPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_outerPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(roleResourcesPanel, GroupLayout.PREFERRED_SIZE, 568, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_outerPanel.setVerticalGroup(
			gl_outerPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_outerPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(roleResourcesPanel, GroupLayout.PREFERRED_SIZE, 459, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(14, Short.MAX_VALUE))
		);
		
		RoleResourcesBottomPanel roleResourcesBottomPanel = new RoleResourcesBottomPanel((HashMap) null);
		GroupLayout gl_roleResourcesPanel = new GroupLayout(roleResourcesPanel);
		gl_roleResourcesPanel.setHorizontalGroup(
			gl_roleResourcesPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_roleResourcesPanel.createSequentialGroup()
					.addGroup(gl_roleResourcesPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_roleResourcesPanel.createSequentialGroup()
							.addGap(0, 0, Short.MAX_VALUE)
							.addComponent(roleResourcesBottomPanel, GroupLayout.PREFERRED_SIZE, 655, GroupLayout.PREFERRED_SIZE))
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 567, Short.MAX_VALUE))
					.addGap(15))
		);
		gl_roleResourcesPanel.setVerticalGroup(
			gl_roleResourcesPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_roleResourcesPanel.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 181, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(roleResourcesBottomPanel, GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
					.addContainerGap())
		);
		GroupLayout gl_roleResourcesBottomPanel = new GroupLayout(roleResourcesBottomPanel);
		gl_roleResourcesBottomPanel.setHorizontalGroup(
			gl_roleResourcesBottomPanel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 535, Short.MAX_VALUE)
		);
		gl_roleResourcesBottomPanel.setVerticalGroup(
			gl_roleResourcesBottomPanel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 191, Short.MAX_VALUE)
		);
		roleResourcesBottomPanel.setLayout(gl_roleResourcesBottomPanel);
		roleResourcesPanel.setLayout(gl_roleResourcesPanel);
		outerPanel.setLayout(gl_outerPanel);
	}

	public JPanel getPanel() {
		return outerPanel;
	}

	public void setComboBoxRole(Set<String> list) {
		 
		String[] names = list.toArray(new String[list.size()]);

		for (int i = 0; i < names.length; i++) {
			Role role = new Role();
			role.setName(names[i]);
			roles.add(role);
		}

		roleTableModel = new RoleTableModel(roles);
 		tableRole.setModel(roleTableModel);
 		configuraColunas();
 		tableRole.changeSelection(0, 0, false, false);  // seleciona a primeira linha da tabela por default
 		tableRole.setValueAt(QueueType.QUEUE, 0, 1);
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
}
