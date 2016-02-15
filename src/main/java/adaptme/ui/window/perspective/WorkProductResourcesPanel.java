package adaptme.ui.window.perspective;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

import simulator.base.Policy;
import simulator.base.WorkProduct;
import simulator.gui.model.WorkProductTableModel;

public class WorkProductResourcesPanel {
	
	private JPanel topPanel;
	private JPanel titledPanel;
	private ProbabilityDistributionInnerPanel probabilityDistributionInnerPannel;
 	private List<JPanel> listOfProbabilityDistributionsInnerPanels = new ArrayList<>();
	
	private JScrollPane scrollPane;
	private JComboBox<String> comboBox;
	
	private JTable tableWorkProduct;
	private WorkProductTableModel model;
 	private TableColumnModel modeloColuna;
	private JComboBox policyJComboBox;

	private List<WorkProduct> workProducts = new ArrayList<>();
	
	private GroupLayout gl_topPanel;
	
	private JLabel lblNewLabel;
	private JLabel selectedDemandWorkProductLabel;
	
	private boolean isFirstTime = true;
	private int previousSelectedRow;
	private int indexSelectedRow;
	
	
	public WorkProductResourcesPanel() {
		
		policyJComboBox = new JComboBox();
		policyJComboBox.addItem(Policy.FIFO);
		policyJComboBox.addItem(Policy.STACK);
		policyJComboBox.addItem(Policy.PRIORITY_QUEUE);
		
		topPanel = new JPanel();
	    titledPanel = new JPanel();
		titledPanel.setBorder(new TitledBorder(null, "Work product resources", TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(59, 59, 59)));
		
		titledPanel.setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(null);
		
		titledPanel.add(scrollPane, BorderLayout.CENTER);
		tableWorkProduct = new JTable();
		tableWorkProduct.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		
		scrollPane.setViewportView(tableWorkProduct);
		 
		 
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setViewportBorder(null);
 		gl_topPanel = new GroupLayout(topPanel);

		 
		gl_topPanel.setHorizontalGroup(
				gl_topPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_topPanel.createSequentialGroup().addGap(6)
						.addComponent(titledPanel, GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE).addGap(27)

						.addGap(6)));
						 
		gl_topPanel.setVerticalGroup(
				gl_topPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_topPanel.createSequentialGroup().addGap(6)
								.addGroup(gl_topPanel.createParallelGroup(Alignment.LEADING)
										.addComponent(titledPanel, GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE)
										.addGap(6))));
	}

	public void setModelComboBoxWorkProduct(Set<String> list) {
		
		String[] names = list.toArray(new String[list.size()]);
		
		for (int i = 0; i < names.length; i++) {
			WorkProduct workProduct = new WorkProduct();
			workProduct.setName(names[i]);
			workProducts.add(workProduct);
			probabilityDistributionInnerPannel = new ProbabilityDistributionInnerPanel(i);
			listOfProbabilityDistributionsInnerPanels.add(probabilityDistributionInnerPannel);
		}
		model = new WorkProductTableModel(workProducts);
		tableWorkProduct.setModel(model);
		topPanel.setLayout(gl_topPanel);
		
		tableWorkProduct.changeSelection(0, 0, false, false);  // seleciona a primeira linha da tabela por default
	}
	
	public void configuraTableListener() { 
		
		// Listener disparado ao selecionar uma linha da tabela
		tableWorkProduct.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			
			@Override
			public void valueChanged(ListSelectionEvent event) {
				
				indexSelectedRow = tableWorkProduct.getSelectedRow();
 //				boolean isRowandCheckBoxSelected = (Boolean) model.getValueAt(tableWorkProduct.getSelectedRow(),1) == true;
				 
				if ((indexSelectedRow > -1)) {
 					 
					probabilityDistributionInnerPannel = (ProbabilityDistributionInnerPanel) listOfProbabilityDistributionsInnerPanels.get(indexSelectedRow);
					System.out.println(probabilityDistributionInnerPannel.getName());
					titledPanel.add(probabilityDistributionInnerPannel, BorderLayout.SOUTH);

 					 
//					scrollPane_2.setViewportView(probabilityDistributionPanel);
//					scrollPane_2.revalidate();
//					scrollPane_2.repaint();
					String probabilityDistributionInnerPannelName =  probabilityDistributionInnerPannel.getName();
 					String selectedWorkProduct = tableWorkProduct.getValueAt(indexSelectedRow, 0).toString();
					probabilityDistributionInnerPannel.getSelectedDemandWorkProductLabel().setText(selectedWorkProduct +"  " + probabilityDistributionInnerPannelName);
				}
			}
		});
		
 	}
	
	public void configuraColunas() { 
		modeloColuna = tableWorkProduct.getColumnModel();

		TableColumn colunaPolicy = modeloColuna.getColumn(4);
		colunaPolicy.setCellEditor(new DefaultCellEditor(policyJComboBox));

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		tableWorkProduct.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		tableWorkProduct.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		tableWorkProduct.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
		tableWorkProduct.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
		tableWorkProduct.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);


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
	 
	 public List<JPanel> getListOfProbabilityDistributionPanels() {
		 return listOfProbabilityDistributionsInnerPanels;
	 }
	 
	 public JPanel getPanel() {
			return topPanel;
		}
}
