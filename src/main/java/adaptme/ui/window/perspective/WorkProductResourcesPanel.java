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
	private GenerateActivityProbabilityDistributionPanel generateActivityProbabilityDistributionPanel;
 	private List<JPanel> listOfGenerateActivityProbabilityDistributionPanels = new ArrayList<>();
 	
 	private WorkProductResourcesQueueObserversPanel queueWorkProductResourcesObserversPanel;
 	private List<JPanel> listOfQueueWorkProductResourcesObserversPanel = new ArrayList<>();
 	
 	private WorkProductResourcesGenerateActivityObserversPanel generateActivityWorkProductResourcesObserversPanel;
 	private List<JPanel> listOfGenerateActivityWorkProductResourcesObserversPanel = new ArrayList<>();

	private GroupLayout gl_topPanel;
	private JScrollPane scrollPaneTableWorkProduct;
 	
	private JTable tableWorkProduct;
	private WorkProductTableModel model;
 	private TableColumnModel modeloColuna;
 	
	private JComboBox<Policy> policyJComboBox;
	private JComboBox<QueueType> queueTypeJComboBox;

	private List<WorkProduct> workProducts = new ArrayList<>();
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
		titledPanel.add(outerProbabilityPanel, BorderLayout.WEST);  //AQUI
		outerProbabilityPanel.setLayout(new BorderLayout(0, 0));
	}

	
	
	public void setModelComboBoxWorkProduct(List<ProcessContentRepository> listOfProcessContentRepositoryTasks) {
		int i = 0;
		Set<MethodContentRepository> setOfInputMethodContentRepository;
		Set<MethodContentRepository> setOfOutputMethodContentRepository;

		for (ProcessContentRepository pcr : listOfProcessContentRepositoryTasks) {
			setOfInputMethodContentRepository = pcr.getInputMethodContentsRepository();
			setOfOutputMethodContentRepository = pcr.getOutputMethodContentsRepository();

			for (MethodContentRepository mcr : setOfOutputMethodContentRepository) {

				WorkProduct workProduct = new WorkProduct();
				workProduct.setName(mcr.getName());
				workProduct.setInputOrOutput("OUTPUT");
				workProduct.setTaskName(pcr.getName());

				workProducts.add(workProduct); // colocamos todos produtos de
												// trabalho de saida
				i++;

				generateActivityProbabilityDistributionPanel = new GenerateActivityProbabilityDistributionPanel(i,
						"Generate activity for demand work product : " + mcr.getName());
				generateActivityProbabilityDistributionPanel.setSelectedDemandWorkProductLabel(
						new JLabel(mcr.getName() + " " + generateActivityProbabilityDistributionPanel.getName()));
				listOfGenerateActivityProbabilityDistributionPanels.add(generateActivityProbabilityDistributionPanel);

				queueWorkProductResourcesObserversPanel = new WorkProductResourcesQueueObserversPanel(i,
						mcr.getName() + " output queue", "queue observers");
				queueWorkProductResourcesObserversPanel.setQueueNameTextField(mcr.getName() + " queue");
				listOfQueueWorkProductResourcesObserversPanel.add(queueWorkProductResourcesObserversPanel);

				generateActivityWorkProductResourcesObserversPanel = new WorkProductResourcesGenerateActivityObserversPanel(i,
						mcr.getName() + " output queue", "generate activity observers");
				generateActivityWorkProductResourcesObserversPanel.setQueueNameTextField(mcr.getName() + " queue");
				listOfGenerateActivityWorkProductResourcesObserversPanel
						.add(generateActivityWorkProductResourcesObserversPanel);

			}
			System.out.println("value of i after all output" + i);

			for (MethodContentRepository mcr : setOfInputMethodContentRepository) {

				WorkProduct workProduct = new WorkProduct();
				workProduct.setName(mcr.getName());
				workProduct.setInputOrOutput("INPUT");
				workProduct.setTaskName(pcr.getName());

				if (!workProducts.contains(workProduct)) {
					workProducts.add(workProduct);
					i++;

					generateActivityProbabilityDistributionPanel = new GenerateActivityProbabilityDistributionPanel(i,
							"Generate activity for demand work product : " + mcr.getName());
					generateActivityProbabilityDistributionPanel.setSelectedDemandWorkProductLabel(
							new JLabel(mcr.getName() + " " + generateActivityProbabilityDistributionPanel.getName()));

					listOfGenerateActivityProbabilityDistributionPanels.add(generateActivityProbabilityDistributionPanel);

					queueWorkProductResourcesObserversPanel = new WorkProductResourcesQueueObserversPanel(i,
							mcr.getName() + " input queue", "queue observers");
					queueWorkProductResourcesObserversPanel.setQueueNameTextField(mcr.getName() + " queue");
					listOfQueueWorkProductResourcesObserversPanel.add(queueWorkProductResourcesObserversPanel);

					generateActivityWorkProductResourcesObserversPanel = new WorkProductResourcesGenerateActivityObserversPanel(i,
							mcr.getName() + " input queue", "generate activity observers");
					generateActivityWorkProductResourcesObserversPanel.setQueueNameTextField(mcr.getName() + " queue");
					listOfGenerateActivityWorkProductResourcesObserversPanel.add(generateActivityWorkProductResourcesObserversPanel);
				}
			}
			System.out.println("value of i after all input" + i);

//			Collections.sort(workProducts); // ordenando para facilitar a
											// visualizacao nas tabelas da
											// atividade 3.1 e 4.1

			model = new WorkProductTableModel(workProducts, listOfQueueWorkProductResourcesObserversPanel,
					listOfGenerateActivityWorkProductResourcesObserversPanel);
			tableWorkProduct.setModel(model);
			configuraColunas();
			topPanel.setLayout(gl_topPanel);

			tableWorkProduct.changeSelection(0, 0, false, false); // seleciona a primeira linha da tabela por default
																	 
			// adiciono inicialmente apenas o panel para configuracao de observers de queue. Nao adiciono observers e distributions de generate activities
			outerProbabilityPanel.add(
					(WorkProductResourcesQueueObserversPanel) listOfQueueWorkProductResourcesObserversPanel.get(0),
					BorderLayout.WEST);
		}

		for (int j = 0; j < workProducts.size(); j++) {
			if (workProducts.get(j).getInputOrOutput().equalsIgnoreCase("Input")) {
				tableWorkProduct.setValueAt(workProducts.get(j).getName() + " input queue", j, 3);
				tableWorkProduct.setValueAt(QueueType.QUEUE, j, 4);
				tableWorkProduct.setValueAt(Policy.FIFO, j, 7);
			}

		}

		for (int w = 0; w < workProducts.size(); w++) {
			if (workProducts.get(w).getInputOrOutput().equalsIgnoreCase("Output")) {
				tableWorkProduct.setValueAt(workProducts.get(w).getName() + " output queue" + w, w, 3);
				tableWorkProduct.setValueAt(QueueType.QUEUE, w, 4);
				tableWorkProduct.setValueAt(Policy.FIFO, w, 7);
			}
		}
	}
	public void configuraTableListener() { 
		
		// Listener disparado ao selecionar uma linha da tabela
		tableWorkProduct.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			
			@Override
			public void valueChanged(ListSelectionEvent event) {
				
				indexSelectedRow = tableWorkProduct.getSelectedRow();
 				 
				if ((indexSelectedRow > -1)) { 					 
					generateActivityProbabilityDistributionPanel = (GenerateActivityProbabilityDistributionPanel) listOfGenerateActivityProbabilityDistributionPanels.get(indexSelectedRow);
					queueWorkProductResourcesObserversPanel = (WorkProductResourcesQueueObserversPanel) listOfQueueWorkProductResourcesObserversPanel.get(indexSelectedRow);
					generateActivityWorkProductResourcesObserversPanel = (WorkProductResourcesGenerateActivityObserversPanel) listOfGenerateActivityWorkProductResourcesObserversPanel.get(indexSelectedRow);

					
 					outerProbabilityPanel.removeAll();
 					
 					outerProbabilityPanel.add(queueWorkProductResourcesObserversPanel, BorderLayout.WEST);
 					// teste com este if para ver se exibo os paineis apenas se o checkbox estiver selecionado
 				    if ((Boolean)model.getValueAt(indexSelectedRow, 8)) {
 				    	outerProbabilityPanel.add(generateActivityProbabilityDistributionPanel, BorderLayout.CENTER);
 				    	outerProbabilityPanel.add(generateActivityWorkProductResourcesObserversPanel, BorderLayout.EAST);
 				    }
					outerProbabilityPanel.updateUI();
					
					String queueName = (String)tableWorkProduct.getValueAt(indexSelectedRow, 3);
					String queueNameEmpty = (String)tableWorkProduct.getValueAt(indexSelectedRow, 0);
					
					if ((queueName == null) || (queueName.trim().isEmpty())) {
						queueWorkProductResourcesObserversPanel.setQueueNameTextField(queueNameEmpty + " queue");
						generateActivityWorkProductResourcesObserversPanel.setQueueNameTextField(queueNameEmpty + " queue");

					} else {
						queueWorkProductResourcesObserversPanel.setQueueNameTextField(queueName);
						generateActivityWorkProductResourcesObserversPanel.setQueueNameTextField(queueName);
					}
					
				}
			}
		});
		
 	}
	
	public void configuraColunas() { 
		
		modeloColuna = tableWorkProduct.getColumnModel();

		TableColumn colunaQueueType = modeloColuna.getColumn(4);
		colunaQueueType.setCellEditor(new DefaultCellEditor(queueTypeJComboBox));
		
		TableColumn colunaPolicy = modeloColuna.getColumn(7);
		colunaPolicy.setCellEditor(new DefaultCellEditor(policyJComboBox));

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		
 		tableWorkProduct.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
		tableWorkProduct.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
		tableWorkProduct.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
		tableWorkProduct.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);
//		tableWorkProduct.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);
		 


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

	public JTable getTableWorkProduct() {
		return tableWorkProduct;
	}
	
	 public List<JPanel> getListOfProbabilityDistributionPanels() {
		 return listOfGenerateActivityProbabilityDistributionPanels;
	 }
	 
	 public JPanel getPanel() {
		 return topPanel;
	 }

	public WorkProductResourcesQueueObserversPanel getWorkProductResourcesBottomRightPanel() {
		return queueWorkProductResourcesObserversPanel;
	}
	
	public List<JPanel> getListOfWorkProductResourcesBottomRightPanels() {
		return listOfQueueWorkProductResourcesObserversPanel;
	}
}
