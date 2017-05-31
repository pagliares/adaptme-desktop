package adaptme.ui.dynamic.simulation.alternative.process;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Font;
import javax.swing.JComboBox;

import model.spem.ProcessContentRepository;
import model.spem.ProcessRepository;
import model.spem.util.BehaviourAtEndOfIterationType;
import model.spem.util.ConditionToProcessType;
import model.spem.util.DependencyType;
import model.spem.util.ProcessContentType;
import model.spem.util.ProcessingQuantityType;
import javax.swing.LayoutStyle.ComponentPlacement;

public class ExtendedXACDMLAttributesPanel extends JPanel {

	private JLabel extendedXacdmlAttributesLabel;

	
	private JLabel behaviourAtTheEndOfIterationLabel;
	private JComboBox<BehaviourAtEndOfIterationType> behaviourAtTheEndOfIterationComboBox;
	
	private JLabel spemTypeLabel;
	private JTextField spemTypeTextField;

	private JLabel timeboxLabel;
	private JTextField timeboxTextField;
	
	private JLabel processingQuantityLabel;
	private JComboBox<ProcessingQuantityType> processingQuantityComboBox;
	
	private JLabel parentLabel;
	private JTextField parentTextField;
	
	private JLabel dependencyTypeLabel;
	private JComboBox<DependencyType> dependencyTypeComboBox;
	
	private JLabel conditionToProcessLabel;
	private JComboBox<ConditionToProcessType> conditionToProcessComboBox;
	
	private JLabel quantityOfResourcesLabel;
  	private JTextField quantityOfResourcesTextField;
  	
	
	/**
	 * Create the panel.
	 */
	public ExtendedXACDMLAttributesPanel(ProcessContentRepository workbreakdownElement) {
		if(workbreakdownElement == null){
			workbreakdownElement = new ProcessContentRepository();
		}
		setBorder(null);
		
		extendedXacdmlAttributesLabel = new JLabel("Extended XACDML attributes:");
		extendedXacdmlAttributesLabel.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		
		behaviourAtTheEndOfIterationLabel = new JLabel("Behaviour at the end of an iteration");
		behaviourAtTheEndOfIterationComboBox = new JComboBox<>();
		behaviourAtTheEndOfIterationComboBox.addItem(BehaviourAtEndOfIterationType.MOVE_BACK);
		behaviourAtTheEndOfIterationComboBox.addItem(BehaviourAtEndOfIterationType.DO_NOT_MOVE_BACK);
		
		if (workbreakdownElement.getType().equals(ProcessContentType.ITERATION)) {
			behaviourAtTheEndOfIterationComboBox.setEditable(false);
		}
		
		spemTypeLabel = new JLabel("SPEM type");
		spemTypeTextField = new JTextField();
		spemTypeTextField.setEditable(false);
		spemTypeTextField.setText(workbreakdownElement.getType().toString());
		spemTypeTextField.setColumns(10);
		
		timeboxLabel = new JLabel("Timebox");
		timeboxTextField = new JTextField();
		timeboxTextField.setColumns(10);
		if (workbreakdownElement.getType().equals(ProcessContentType.ITERATION) ||(workbreakdownElement.getType().equals(ProcessContentType.ACTIVITY))) {
			timeboxTextField.setEditable(true);
		}
		
		processingQuantityLabel = new JLabel("Processing quantity");
		processingQuantityComboBox = new JComboBox<>();
		processingQuantityComboBox.addItem(ProcessingQuantityType.UNIT);
		processingQuantityComboBox.addItem(ProcessingQuantityType.BATCH);
		

		parentLabel = new JLabel("Parent");
		parentTextField = new JTextField();
		parentTextField.setColumns(10);
		ProcessContentRepository father = workbreakdownElement.getFather() ;
		if (father != null) {
			parentTextField.setText(workbreakdownElement.getFather().getName());
		}
		if (workbreakdownElement.getFather() != null) {
			parentTextField.setEditable(false);
		}
		
		//
		if (workbreakdownElement.getFather() == null) {
			parentTextField.setText(workbreakdownElement.getProcessRepository().getName());
			parentTextField.setEditable(false);
		}
		//
		
		dependencyTypeLabel = new JLabel("Dependency type");
		dependencyTypeComboBox = new JComboBox<>();
		dependencyTypeComboBox.addItem(DependencyType.FINISH_TO_START);
		dependencyTypeComboBox.addItem(DependencyType.FINISH_TO_FINISH);
		dependencyTypeComboBox.addItem(DependencyType.START_TO_FINISH);
		dependencyTypeComboBox.addItem(DependencyType.START_TO_START);

		conditionToProcessLabel = new JLabel("Condition to process");
		conditionToProcessComboBox = new JComboBox<>();
		conditionToProcessComboBox.addItem(ConditionToProcessType.SINGLE_ENTITY_AVAILABLE);
		conditionToProcessComboBox.addItem(ConditionToProcessType.ALL_ENTITIES_AVAILABLE);
		
		
		quantityOfResourcesLabel = new JLabel("Quantity of resources needed to perform the task");
		
		quantityOfResourcesTextField = new JTextField();
		quantityOfResourcesTextField.setColumns(10);
		if (workbreakdownElement.getType().equals(ProcessContentType.TASK)) {
			quantityOfResourcesTextField.setEditable(true);
		}
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(6)
					.addComponent(extendedXacdmlAttributesLabel, GroupLayout.PREFERRED_SIZE, 228, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(spemTypeLabel)
					.addGap(12)
					.addComponent(spemTypeTextField, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
					.addGap(15)
					.addComponent(parentLabel)
					.addGap(12)
					.addComponent(parentTextField, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(dependencyTypeLabel)
					.addGap(12)
					.addComponent(dependencyTypeComboBox, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE)
					.addGap(56)
					.addComponent(timeboxLabel)
					.addGap(12)
					.addComponent(timeboxTextField, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(130)
							.addComponent(conditionToProcessComboBox, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE))
						.addComponent(conditionToProcessLabel)))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(processingQuantityLabel)
					.addGap(12)
					.addComponent(processingQuantityComboBox, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(behaviourAtTheEndOfIterationLabel, GroupLayout.PREFERRED_SIZE, 236, GroupLayout.PREFERRED_SIZE)
					.addGap(29)
					.addComponent(behaviourAtTheEndOfIterationComboBox, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(quantityOfResourcesLabel)
					.addGap(84)
					.addComponent(quantityOfResourcesTextField, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(13)
					.addComponent(extendedXacdmlAttributesLabel, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
					.addGap(12)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addComponent(spemTypeLabel))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(2)
							.addComponent(spemTypeTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addComponent(parentLabel))
						.addComponent(parentTextField, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
					.addGap(12)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addComponent(dependencyTypeLabel))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(2)
							.addComponent(dependencyTypeComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addComponent(timeboxLabel))
						.addComponent(timeboxTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(12)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(conditionToProcessComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addComponent(conditionToProcessLabel)))
					.addGap(8)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(4)
							.addComponent(processingQuantityLabel))
						.addComponent(processingQuantityComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(8)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(4)
							.addComponent(behaviourAtTheEndOfIterationLabel))
						.addComponent(behaviourAtTheEndOfIterationComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(6)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addComponent(quantityOfResourcesLabel))
						.addComponent(quantityOfResourcesTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
		);
		setLayout(groupLayout);
		
	

	}



	public JComboBox<BehaviourAtEndOfIterationType> getBehaviourAtTheEndOfIterationComboBox() {
		return behaviourAtTheEndOfIterationComboBox;
	}



	public void setBehaviourAtTheEndOfIterationComboBox(
			JComboBox<BehaviourAtEndOfIterationType> behaviourAtTheEndOfIterationComboBox) {
		this.behaviourAtTheEndOfIterationComboBox = behaviourAtTheEndOfIterationComboBox;
	}


	public JTextField getTimeboxTextField() {
		return timeboxTextField;
	}



	public void setTimeboxTextField(JTextField timeboxTextField) {
		this.timeboxTextField = timeboxTextField;
	}



	public JComboBox<ProcessingQuantityType> getProcessingQuantityComboBox() {
		return processingQuantityComboBox;
	}



	public void setProcessingQuantityComboBox(JComboBox<ProcessingQuantityType> processingQuantityComboBox) {
		this.processingQuantityComboBox = processingQuantityComboBox;
	}



	public JTextField getParentTextField() {
		return parentTextField;
	}



	public void setParentTextField(JTextField parentTextField) {
		this.parentTextField = parentTextField;
	}



	public JComboBox<DependencyType> getDependencyTypeComboBox() {
		return dependencyTypeComboBox;
	}



	public void setDependencyTypeComboBox(JComboBox<DependencyType> dependencyTypeComboBox) {
		this.dependencyTypeComboBox = dependencyTypeComboBox;
	}



	public JComboBox<ConditionToProcessType> getConditionToProcessComboBox() {
		return conditionToProcessComboBox;
	}



	public void setConditionToProcessComboBox(JComboBox<ConditionToProcessType> conditionToProcessComboBox) {
		this.conditionToProcessComboBox = conditionToProcessComboBox;
	}
	
	public JTextField getSpemTypeTextField() {
		return spemTypeTextField;
	}



	public void setSpemTypeTextField(JTextField spemTypeTextField) {
		this.spemTypeTextField = spemTypeTextField;
	}



	public JTextField getQuantityOfResourcesTextField() {
		return quantityOfResourcesTextField;
	}



	public void setQuantityOfResourcesTextField(JTextField quantityOfResourcesTextField) {
		this.quantityOfResourcesTextField = quantityOfResourcesTextField;
	}



	public JLabel getExtendedXacdmlAttributesLabel() {
		return extendedXacdmlAttributesLabel;
	}



	public JLabel getBehaviourAtTheEndOfIterationLabel() {
		return behaviourAtTheEndOfIterationLabel;
	}



	public JLabel getSpemTypeLabel() {
		return spemTypeLabel;
	}



	public JLabel getTimeboxLabel() {
		return timeboxLabel;
	}



	public JLabel getProcessingQuantityLabel() {
		return processingQuantityLabel;
	}



	public JLabel getParentLabel() {
		return parentLabel;
	}



	public JLabel getDependencyTypeLabel() {
		return dependencyTypeLabel;
	}



	public JLabel getConditionToProcessLabel() {
		return conditionToProcessLabel;
	}



	public JLabel getQuantityOfResourcesLabel() {
		return quantityOfResourcesLabel;
	}
}
