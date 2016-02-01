package adaptme.ui.window.perspective;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import model.spem.util.StartConfiguration;
import simulator.base.WorkProduct;
import simulator.gui.model.WorkProductTableModel;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultTreeModel;

import java.awt.BorderLayout;
import java.awt.Color;

public class DefineProjectResourcesPanel {
	private JPanel panel;
	private JScrollPane scrollPane;
	private JTable tableWorkProduct;
//	private JPanel panel_2;

	public DefineProjectResourcesPanel() {
		panel = new JPanel();		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Work product resources", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(59, 59, 59)));
		
//		panel_2 = new JPanel();
//		panel_2.setBorder(new TitledBorder(null, "Role resources", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		panel_1.setLayout(new BorderLayout(0, 0));
		
				scrollPane = new JScrollPane();
				scrollPane.setViewportBorder(null);
				panel_1.add(scrollPane, BorderLayout.CENTER);
				tableWorkProduct = new JTable();
				scrollPane.setViewportView(tableWorkProduct);
		
//				panel_2.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setViewportBorder(null);
//		panel_2.add(scrollPane_1, BorderLayout.CENTER);
		GroupLayout gl_panel = new GroupLayout(panel);
		
//		gl_panel.setHorizontalGroup(
//			gl_panel.createParallelGroup(Alignment.LEADING)
//				.addGroup(gl_panel.createSequentialGroup()
//					.addGap(6)
//					.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
//					.addGap(27)
//					.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
//					.addGap(6))
//		);
		
		gl_panel.setHorizontalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel.createSequentialGroup()
						.addGap(6)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
						.addGap(27)
						
						.addGap(6))
			);
		// trecho abaixo igual ao superior sem .addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE))

//		gl_panel.setVerticalGroup(
//			gl_panel.createParallelGroup(Alignment.LEADING)
//				.addGroup(gl_panel.createSequentialGroup()
//					.addGap(6)
//					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
//						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE)
//						.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE))
//					.addGap(6))
//		);
		// trecho abaixo igual ao superior sem .addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE))
		gl_panel.setVerticalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel.createSequentialGroup()
						.addGap(6)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
							.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE).addGap(6))));
		panel.setLayout(gl_panel);
	}

	public JPanel getPanel() {
		return panel;
	}

	public void setModelComboBoxWorkProduct(Set<String> list) {
		String[] names = list.toArray(new String[list.size()]);
		List<WorkProduct> workProducts = new ArrayList<>();
		for (int i = 0; i < names.length; i++) {
			WorkProduct workProduct = new WorkProduct();
			workProduct.setName(names[i]);
			workProducts.add(workProduct);
		}
		WorkProductTableModel model = new WorkProductTableModel(workProducts);
		tableWorkProduct.setModel(model);
	}

	public void setComboBoxRole(Set<String> list) {
//		DefaultTreeModel model = new DefaultTreeModel(list.toArray(new String[list.size()]));
//		tableWorkProduct.setModel(model);
	}

}
