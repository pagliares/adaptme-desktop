package adaptme.ui.window.perspective;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import simulator.base.Role;
import simulator.base.WorkProduct;
import simulator.gui.model.RoleTableModel;
import xacdml.model.XACDMLBuilderFacade;

public class RoleResourcesPanel {
	private JPanel panel;
	private JScrollPane scrollPane;
	private JTable tableRole;
 	private JComboBox policyJComboBox;
	private TableColumnModel modeloColuna;
	
 	private List<Role> roles = new ArrayList<>();

	public RoleResourcesPanel() {

		panel = new JPanel();
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Role resources", TitledBorder.LEADING, TitledBorder.TOP, null,new Color(59, 59, 59)));

		panel_1.setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(null);
		panel_1.add(scrollPane, BorderLayout.CENTER);
		tableRole = new JTable();
		tableRole.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));

		scrollPane.setViewportView(tableRole);

 
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setViewportBorder(null);
 		GroupLayout gl_panel = new GroupLayout(panel);


		gl_panel.setHorizontalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addGap(6)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE).addGap(27)

						.addGap(6)));
						 
		gl_panel.setVerticalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup().addGap(6)
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE)
										.addGap(6))));
		panel.setLayout(gl_panel);
	}

	public JPanel getPanel() {
		return panel;
	}

	
	public void setComboBoxRole(Set<String> list) {
		 
		String[] names = list.toArray(new String[list.size()]);

		for (int i = 0; i < names.length; i++) {
			Role role = new Role();
			role.setName(names[i]);
			roles.add(role);
		}

		RoleTableModel roleTableModel = new RoleTableModel(roles);
		
 		tableRole.setModel(roleTableModel);
		modeloColuna = tableRole.getColumnModel(); 
 
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		tableRole.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		tableRole.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
 

		((DefaultTableCellRenderer) tableRole.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

		tableRole.getColumnModel().getColumn(1).setPreferredWidth(15);
		tableRole.getColumnModel().getColumn(1).setPreferredWidth(23);
		tableRole.getColumnModel().getColumn(2).setPreferredWidth(9);

 	}

	public List<Role> getRoles() {
		return roles;
	}

}
