package adaptme.ui.window.perspective;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
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

import model.spem.MethodContentRepository;
import model.spem.ProcessContentRepository;
import simulator.base.Policy;
import simulator.base.QueueType;
import simulator.base.WorkProduct;

public class WorkProductResourcesPanel {
	
	private JPanel topPanel;
	private JPanel titledPanel;
	private JPanel outerProbabilityPanel;
	private ProbabilityDistributionInnerPanel probabilityDistributionInnerPannel;
 	private List<JPanel> listOfProbabilityDistributionsInnerPanels = new ArrayList<>();
 	
 	private WorkProductResourcesBottomRightPanel workProductResourcesBottomRightPanel;
 	private List<JPanel> listOfWorkProductResourcesBottomRightPanels = new ArrayList<>();

	private GroupLayout gl_topPanel;
	private JScrollPane scrollPaneTableWorkProduct;
 	
	private JTable tableWorkProduct;
	private WorkProductTableModel model;
 	private TableColumnModel modeloColuna;
 	
	private JComboBox<Policy> policyJComboBox;
	private JComboBox<QueueType> queueTypeJComboBox;

	private List<WorkProduct> workProducts = new ArrayList<>();
//	private Set<WorkProduct> workProducts = new HashSet<>();
	private int indexSelectedRow;
	
	public WorkProductResourcesPanel() {
		
		queueTypeJComboBox = new JComboBox<>();
		queueTypeJComboBox.addItem(QueueType.QUEUE);
		queueTypeJComboBox.addItem(QueueType.SET);
		queueTypeJComboBox.addItem(QueueType.STACK);
		
		policyJComboBox = new JComboBox<>();
		policyJComboBox.addItem(Policy.FIFO);
		 
		
		topPanel = new JPanel();
	    titledPanel = new JPanel();
		titledPanel.setBorder(new TitledBorder(null, "Work product resources", TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(59, 59, 59)));
		
		titledPanel.setLayout(new BorderLayout(0, 0));

		scrollPaneTableWorkProduct = new JScrollPane();
		scrollPaneTableWorkProduct.setViewportBorder(null);
		
		titledPanel.add(scrollPaneTableWorkProduct, BorderLayout.NORTH);
		tableWorkProduct = new JTable();
		tableWorkProduct.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		
		scrollPaneTableWorkProduct.setViewportView(tableWorkProduct);
		 
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
		
		outerProbabilityPanel = new JPanel();
		titledPanel.add(outerProbabilityPanel, BorderLayout.CENTER);
		outerProbabilityPanel.setLayout(new BorderLayout(0, 0));
	}

	public void setModelComboBoxWorkProduct(List<String> list) {
		
		String[] names = list.toArray(new String[list.size()]);
		
		for (int i = 0; i < names.length; i++) {
			
			WorkProduct workProduct = new WorkProduct();
			workProduct.setName(names[i]);
			workProducts.add(workProduct);
			
			probabilityDistributionInnerPannel = new ProbabilityDistributionInnerPanel(i, "Generate activity for demand work product : " + names[i]);
			probabilityDistributionInnerPannel.setSelectedDemandWorkProductLabel(new JLabel(names[i] + " " + probabilityDistributionInnerPannel.getName()));
			listOfProbabilityDistributionsInnerPanels.add(probabilityDistributionInnerPannel);
			
			workProductResourcesBottomRightPanel = new WorkProductResourcesBottomRightPanel(i, names[i] + " queue");
			workProductResourcesBottomRightPanel.setQueueNameTextField(names[i] + " queue");
			listOfWorkProductResourcesBottomRightPanels.add(workProductResourcesBottomRightPanel);
			
			
		}
		
		model = new WorkProductTableModel(workProducts, listOfWorkProductResourcesBottomRightPanels);
		tableWorkProduct.setModel(model);
		configuraColunas();
		topPanel.setLayout(gl_topPanel);
		
		tableWorkProduct.changeSelection(0, 0, false, false);  // seleciona a primeira linha da tabela por default

 		outerProbabilityPanel.add((ProbabilityDistributionInnerPanel) listOfProbabilityDistributionsInnerPanels.get(0), BorderLayout.WEST);
 		outerProbabilityPanel.add((WorkProductResourcesBottomRightPanel) listOfWorkProductResourcesBottomRightPanels.get(0), BorderLayout.CENTER);
 		
 		for (int i = 0; i < names.length; i++) {
 			tableWorkProduct.setValueAt(names[i] + " queue", i, 2);
 			tableWorkProduct.setValueAt(QueueType.QUEUE, i, 3);
 			tableWorkProduct.setValueAt(Policy.FIFO, i, 6);
		}

	}
	
	
public void setModelComboBoxWorkProduct2(List<ProcessContentRepository> listOfProcessContentRepositoryTasks) {
	int i = 0;
	Set<MethodContentRepository> setOfInputMethodContentRepository;
	Set<MethodContentRepository> setOfOutputMethodContentRepository;
	
	for (ProcessContentRepository pcr: listOfProcessContentRepositoryTasks) {
		setOfInputMethodContentRepository = pcr.getInputMethodContentsRepository();
	    setOfOutputMethodContentRepository = pcr.getOutputMethodContentsRepository();
	 
	
	    for (MethodContentRepository mcr: setOfInputMethodContentRepository) {
	    	 
	       
			WorkProduct workProduct = new WorkProduct();
			workProduct.setName(mcr.getName());
			workProduct.setInputOrOutput("INPUT");
			if (!workProducts.contains(workProduct)) {
				workProducts.add(workProduct);
				i++;
			}
			probabilityDistributionInnerPannel = new ProbabilityDistributionInnerPanel(i, "Generate activity for demand work product : " + mcr.getName());
			probabilityDistributionInnerPannel.setSelectedDemandWorkProductLabel(new JLabel(mcr.getName() + " " + probabilityDistributionInnerPannel.getName()));
			listOfProbabilityDistributionsInnerPanels.add(probabilityDistributionInnerPannel);
			
			workProductResourcesBottomRightPanel = new WorkProductResourcesBottomRightPanel(i, mcr.getName() + " input queue");
			workProductResourcesBottomRightPanel.setQueueNameTextField(mcr.getName() + " queue");
			listOfWorkProductResourcesBottomRightPanels.add(workProductResourcesBottomRightPanel);
			
		 
			
			
	    }	
	    System.out.println("value of i after all input" + i);
	    
	    for (MethodContentRepository mcr: setOfOutputMethodContentRepository) {
	    	 
		    
			WorkProduct workProduct = new WorkProduct();
			workProduct.setName(mcr.getName());
			workProduct.setInputOrOutput("OUTPUT");
			if (!workProducts.contains(workProduct)) {
				workProducts.add(workProduct);
				i++;
			}
		 
			
			probabilityDistributionInnerPannel = new ProbabilityDistributionInnerPanel(i, "Generate activity for demand work product : " + mcr.getName());
			probabilityDistributionInnerPannel.setSelectedDemandWorkProductLabel(new JLabel(mcr.getName() + " " + probabilityDistributionInnerPannel.getName()));
			listOfProbabilityDistributionsInnerPanels.add(probabilityDistributionInnerPannel);
			
			workProductResourcesBottomRightPanel = new WorkProductResourcesBottomRightPanel(i, mcr.getName() + " output queue");
			workProductResourcesBottomRightPanel.setQueueNameTextField(mcr.getName() + " queue");
			listOfWorkProductResourcesBottomRightPanels.add(workProductResourcesBottomRightPanel);
			
	    }	
	    System.out.println("value of i after all output" + i);
		 
		Collections.sort(workProducts);  // ordenando para facilitar a visualizacao nas tabelas da atividade 3.1 e 4.1
		
		model = new WorkProductTableModel(workProducts, listOfWorkProductResourcesBottomRightPanels);
		tableWorkProduct.setModel(model);
		configuraColunas();
		topPanel.setLayout(gl_topPanel);
		
		tableWorkProduct.changeSelection(0, 0, false, false);  // seleciona a primeira linha da tabela por default

 		outerProbabilityPanel.add((ProbabilityDistributionInnerPanel) listOfProbabilityDistributionsInnerPanels.get(0), BorderLayout.WEST);
 		outerProbabilityPanel.add((WorkProductResourcesBottomRightPanel) listOfWorkProductResourcesBottomRightPanels.get(0), BorderLayout.CENTER);
	}
	
	
 	
     for (int j = 0; j < workProducts.size(); j++) {
    	 if (workProducts.get(j).getInputOrOutput().equals("INPUT")) {
    	    	tableWorkProduct.setValueAt(workProducts.get(j).getName() + " input queue", j, 2);
    	    	tableWorkProduct.setValueAt(QueueType.QUEUE, j, 3);
    	    	tableWorkProduct.setValueAt(Policy.FIFO, j, 6);
    	 }

     }
     
     for (int w = 0; w < workProducts.size(); w++) {
    	 if (workProducts.get(w).getInputOrOutput().equals("OUTPUT")) {
    	    	tableWorkProduct.setValueAt(workProducts.get(w).getName() + " output queue", w, 2);
    	    	tableWorkProduct.setValueAt(QueueType.QUEUE, w, 3);
    	    	tableWorkProduct.setValueAt(Policy.FIFO, w, 6);
    	 }

     }
//}
//    i = 0;
//	for (ProcessContentRepository pcr: listOfProcessContentRepositoryTasks) {
//		setOfInputMethodContentRepository = pcr.getInputMethodContentsRepository();
//	    setOfOutputMethodContentRepository = pcr.getOutputMethodContentsRepository();
//	 
//	
//	    for (MethodContentRepository mcr: setOfInputMethodContentRepository) {
//	    	tableWorkProduct.setValueAt(mcr.getName() + " input queue", i, 2);
//	    	tableWorkProduct.setValueAt(QueueType.QUEUE, i, 3);
//	    	tableWorkProduct.setValueAt(Policy.FIFO, i, 6);
//	    	i++;
//	    }
//	}
//	  System.out.println("value of i after all setvalue input" + i);
//	
//	 
//		for (ProcessContentRepository pcr: listOfProcessContentRepositoryTasks) {
//			setOfInputMethodContentRepository = pcr.getInputMethodContentsRepository();
//		    setOfOutputMethodContentRepository = pcr.getOutputMethodContentsRepository();
//		 
//		
//		    for (MethodContentRepository mcr: setOfOutputMethodContentRepository) {
//		    	tableWorkProduct.setValueAt(mcr.getName() + " output queue", i, 2);
//		    	tableWorkProduct.setValueAt(QueueType.QUEUE, i, 3);
//		    	tableWorkProduct.setValueAt(Policy.FIFO, i, 6);
//		    	i++;
//		    }
//		}
//		System.out.println("value of i after all setvalue output" + i);
	
	
}
	public void configuraTableListener() { 
		
		// Listener disparado ao selecionar uma linha da tabela
		tableWorkProduct.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			
			@Override
			public void valueChanged(ListSelectionEvent event) {
				
				indexSelectedRow = tableWorkProduct.getSelectedRow();
 				 
				if ((indexSelectedRow > -1)) { 					 
					probabilityDistributionInnerPannel = (ProbabilityDistributionInnerPanel) listOfProbabilityDistributionsInnerPanels.get(indexSelectedRow);
					workProductResourcesBottomRightPanel = (WorkProductResourcesBottomRightPanel) listOfWorkProductResourcesBottomRightPanels.get(indexSelectedRow);

 					outerProbabilityPanel.removeAll();
					outerProbabilityPanel.add(probabilityDistributionInnerPannel, BorderLayout.WEST);
					outerProbabilityPanel.add(workProductResourcesBottomRightPanel, BorderLayout.CENTER);
					outerProbabilityPanel.updateUI();
					String queueName = (String)tableWorkProduct.getValueAt(indexSelectedRow, 2);
					String queueNameEmpty = (String)tableWorkProduct.getValueAt(indexSelectedRow, 0);
					
					if ((queueName == null) || (queueName.trim().isEmpty())) {
						workProductResourcesBottomRightPanel.setQueueNameTextField(queueNameEmpty + " queue");

					} else {
						workProductResourcesBottomRightPanel.setQueueNameTextField(queueName);
					}
					
				}
			}
		});
		
 	}
	
	public void configuraColunas() { 
		
		modeloColuna = tableWorkProduct.getColumnModel();

		TableColumn colunaQueueType = modeloColuna.getColumn(3);
		colunaQueueType.setCellEditor(new DefaultCellEditor(queueTypeJComboBox));
		
		TableColumn colunaPolicy = modeloColuna.getColumn(6);
		colunaPolicy.setCellEditor(new DefaultCellEditor(policyJComboBox));

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		
		tableWorkProduct.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
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
		 
	}

	public List<WorkProduct> getWorkProducts() {
		return workProducts;
	}
	
//	public Set<WorkProduct> getWorkProducts() {
//		return workProducts;
//	}

	public JTable getTableWorkProduct() {
		return tableWorkProduct;
	}
	
	 public List<JPanel> getListOfProbabilityDistributionPanels() {
		 return listOfProbabilityDistributionsInnerPanels;
	 }
	 
	 public JPanel getPanel() {
		 return topPanel;
	 }

	public WorkProductResourcesBottomRightPanel getWorkProductResourcesBottomRightPanel() {
		return workProductResourcesBottomRightPanel;
	}
	
	public List<JPanel> getListOfWorkProductResourcesBottomRightPanels() {
		return listOfWorkProductResourcesBottomRightPanels;
	}
}
