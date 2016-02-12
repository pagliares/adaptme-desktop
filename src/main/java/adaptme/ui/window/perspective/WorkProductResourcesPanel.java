package adaptme.ui.window.perspective;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
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

import model.spem.derived.BestFitDistribution;
import model.spem.derived.Parameters;
import model.spem.derived.gui.ParametersPanel;
import simulator.base.WorkProduct;
import simulator.gui.model.WorkProductTableModel;
import javax.swing.LayoutStyle.ComponentPlacement;

public class WorkProductResourcesPanel {
	private JPanel panel;
	private JScrollPane scrollPane;
	private JTable tableWorkProduct;
	private JComboBox policyJComboBox;
 	private TableColumnModel modeloColuna;
	private List<WorkProduct> workProducts = new ArrayList<>();
	private JComboBox<String> comboBox;
	private JPanel probabilityDistributionsPanel;
	private JLabel lblNewLabel;
	private JLabel selectedDemandWorkProductLabel;
 
	public WorkProductResourcesPanel() {
		
		policyJComboBox = new JComboBox();
		policyJComboBox.addItem("FIFO");
		policyJComboBox.addItem("STACK");
		
		 
		panel = new JPanel();
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Work product resources", TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(59, 59, 59)));

		// panel_2 = new JPanel();
		// panel_2.setBorder(new TitledBorder(null, "Role resources",
		// TitledBorder.LEADING, TitledBorder.TOP, null, null));

		panel_1.setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(null);
		panel_1.add(scrollPane, BorderLayout.CENTER);
		tableWorkProduct = new JTable();
		tableWorkProduct.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		
		

		scrollPane.setViewportView(tableWorkProduct);

		// panel_2.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setViewportBorder(null);
		// panel_2.add(scrollPane_1, BorderLayout.CENTER);
		GroupLayout gl_panel = new GroupLayout(panel);

		// gl_panel.setHorizontalGroup(
		// gl_panel.createParallelGroup(Alignment.LEADING)
		// .addGroup(gl_panel.createSequentialGroup()
		// .addGap(6)
		// .addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 248,
		// Short.MAX_VALUE)
		// .addGap(27)
		// .addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 248,
		// Short.MAX_VALUE)
		// .addGap(6))
		// );

		gl_panel.setHorizontalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addGap(6)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE).addGap(27)

						.addGap(6)));
						// trecho abaixo igual ao superior sem
						// .addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 413,
						// Short.MAX_VALUE))

		// gl_panel.setVerticalGroup(
		// gl_panel.createParallelGroup(Alignment.LEADING)
		// .addGroup(gl_panel.createSequentialGroup()
		// .addGap(6)
		// .addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
		// .addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 413,
		// Short.MAX_VALUE)
		// .addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 413,
		// Short.MAX_VALUE))
		// .addGap(6))
		// );
		// trecho abaixo igual ao superior sem .addComponent(panel_2,
		// GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE))
		gl_panel.setVerticalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup().addGap(6)
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE)
										.addGap(6))));
		
		probabilityDistributionsPanel = new JPanel();
		probabilityDistributionsPanel.setBorder(new TitledBorder(null, "Probability distribution parameters", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.add(probabilityDistributionsPanel, BorderLayout.SOUTH);
		
		JLabel label = new JLabel("Best fit probability distribution");
		
 		comboBox  = new JComboBox<String>();
  		setDistribution(BestFitDistribution.getList());
		
		
		
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setViewportBorder(null);
		scrollPane_2.setBorder(BorderFactory.createEmptyBorder());
		
		lblNewLabel = new JLabel("Generate activity for demand work product :");
		
		selectedDemandWorkProductLabel = new JLabel("");
		GroupLayout gl_probabilityDistributionsPanel = new GroupLayout(probabilityDistributionsPanel);
		gl_probabilityDistributionsPanel.setHorizontalGroup(
			gl_probabilityDistributionsPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_probabilityDistributionsPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_probabilityDistributionsPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 419, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_probabilityDistributionsPanel.createSequentialGroup()
							.addComponent(label, GroupLayout.PREFERRED_SIZE, 205, GroupLayout.PREFERRED_SIZE)
							.addGap(28)
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 186, GroupLayout.PREFERRED_SIZE)
							.addGap(0, 0, Short.MAX_VALUE))
						.addGroup(gl_probabilityDistributionsPanel.createSequentialGroup()
							.addComponent(lblNewLabel)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(selectedDemandWorkProductLabel)))
					.addContainerGap())
		);
		gl_probabilityDistributionsPanel.setVerticalGroup(
			gl_probabilityDistributionsPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_probabilityDistributionsPanel.createSequentialGroup()
					.addContainerGap(16, Short.MAX_VALUE)
					.addGroup(gl_probabilityDistributionsPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(selectedDemandWorkProductLabel))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_probabilityDistributionsPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
					.addGap(16))
		);
		probabilityDistributionsPanel.setLayout(gl_probabilityDistributionsPanel);
		probabilityDistributionsPanel.setVisible(false);
		
		comboBox.addItemListener(e -> {
		    String s = (String) comboBox.getSelectedItem();
		    // scrollPaneParameters.removeAll();
		    Parameters p = Parameters.createParameter(BestFitDistribution.getDistributionByName(s));
		    scrollPane_2.setViewportView(new ParametersPanel(p).getPanel());
		    scrollPane_2.revalidate();
		    scrollPane_2.repaint();
		});
		panel.setLayout(gl_panel);

	}

	public JPanel getPanel() {
		return panel;
	}

	public void setModelComboBoxWorkProduct(Set<String> list) {
		
		String[] names = list.toArray(new String[list.size()]);
		
		for (int i = 0; i < names.length; i++) {
			WorkProduct workProduct = new WorkProduct();
			workProduct.setName(names[i]);
			workProducts.add(workProduct);
		}
		
		WorkProductTableModel model = new WorkProductTableModel(workProducts);
		tableWorkProduct.setModel(model);
		
		tableWorkProduct.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
		    @Override
		    public void valueChanged(ListSelectionEvent event) {
		    	
		    	int indexSelectRow = tableWorkProduct.getSelectedRow();
		    	boolean isRowandCheckBoxSelected = (Boolean)model.getValueAt(tableWorkProduct.getSelectedRow(), 1) == true;
//		        if ((indexSelectRow > -1) && (isRowandCheckBoxSelected)){
		    	 if ((indexSelectRow > -1) && (isRowandCheckBoxSelected)){
		            // print first column value from selected row
//		            System.out.println(tableWorkProduct.getValueAt(tableWorkProduct.getSelectedRow(), 0).toString());
		    		probabilityDistributionsPanel.setVisible(true);
		    		selectedDemandWorkProductLabel.setText(tableWorkProduct.getValueAt(tableWorkProduct.getSelectedRow(), 0).toString());

		        }
		    }
		});
		
		modeloColuna = tableWorkProduct.getColumnModel();
		TableColumn colunaPolicy = modeloColuna.getColumn(5);
 		colunaPolicy.setCellEditor(new DefaultCellEditor(policyJComboBox));
 		
 		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		tableWorkProduct.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
		tableWorkProduct.getColumnModel().getColumn(3).setCellRenderer( centerRenderer );
		tableWorkProduct.getColumnModel().getColumn(4).setCellRenderer( centerRenderer );
		tableWorkProduct.getColumnModel().getColumn(5).setCellRenderer( centerRenderer );
		tableWorkProduct.getColumnModel().getColumn(6).setCellRenderer( centerRenderer );
		tableWorkProduct.getColumnModel().getColumn(7).setCellRenderer( centerRenderer );
//		tableWorkProduct.setDefaultRenderer(String.class, centerRenderer); // centraliza todas colunas com String.class
		

		((DefaultTableCellRenderer)tableWorkProduct.getTableHeader().getDefaultRenderer())
	    .setHorizontalAlignment(JLabel.CENTER);
		
		tableWorkProduct.getColumnModel().getColumn(1).setPreferredWidth(23);
		tableWorkProduct.getColumnModel().getColumn(2).setPreferredWidth(9);
		tableWorkProduct.getColumnModel().getColumn(3).setPreferredWidth(14);
		tableWorkProduct.getColumnModel().getColumn(4).setPreferredWidth(10);
		tableWorkProduct.getColumnModel().getColumn(5).setPreferredWidth(8);
		tableWorkProduct.getColumnModel().getColumn(6).setPreferredWidth(14);
		
 	}

	public List<WorkProduct> getWorkProducts() {
		return workProducts;
	}

	public JTable getTableWorkProduct() {
		return tableWorkProduct;
	}
	
	 public String getDistribution() {
		return (String) comboBox.getSelectedItem();
	 }

	 public void setDistribution(List<String> list) {
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(list.toArray(new String[list.size()]));
			comboBox.setModel(model);
	 }
}
