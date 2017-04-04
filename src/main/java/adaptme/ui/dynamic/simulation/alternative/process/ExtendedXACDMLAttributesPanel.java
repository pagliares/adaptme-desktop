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
import model.spem.util.BehaviourAtEndOfIterationType;
import model.spem.util.ConditionToProcessType;
import model.spem.util.DependencyType;
import model.spem.util.ProcessContentType;
import model.spem.util.ProcessingQuantityType;
import javax.swing.LayoutStyle.ComponentPlacement;

public class ExtendedXACDMLAttributesPanel extends JPanel {

	private JLabel behaviourAtTheEndOfIterationLabel;
	private JLabel extendedXacdmlAttributesLabel;
	private JComboBox<BehaviourAtEndOfIterationType> behaviourAtTheEndOfIterationComboBox;
	
	private JLabel spemTypeLabel;
	
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
	
	private JLabel quantityOfResourcesNeededLabels;
	private JTextField quantityOfResourcesNeededTextField;
	private JTextField quantityOfResourcesTextField;
	private JTextField spemTypeTextField;
	
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
			timeboxTextField.setEditable(false);
		}
		
		processingQuantityLabel = new JLabel("Processing quantity");
		processingQuantityComboBox = new JComboBox<>();
		processingQuantityComboBox.addItem(ProcessingQuantityType.BATCH);
		processingQuantityComboBox.addItem(ProcessingQuantityType.UNIT);

		parentLabel = new JLabel("Parent");
		parentTextField = new JTextField();
		parentTextField.setColumns(10);
		if (workbreakdownElement.getFather() != null) {
			parentTextField.setEditable(false);
		}
		
		dependencyTypeLabel = new JLabel("Dependency type");
		dependencyTypeComboBox = new JComboBox<>();
		dependencyTypeComboBox.addItem(DependencyType.FINISH_TO_FINISH);
		dependencyTypeComboBox.addItem(DependencyType.FINISH_TO_START);
		dependencyTypeComboBox.addItem(DependencyType.START_TO_FINISH);
		dependencyTypeComboBox.addItem(DependencyType.START_TO_START);

		conditionToProcessLabel = new JLabel("Condition to process");
		conditionToProcessComboBox = new JComboBox<>();
		conditionToProcessComboBox.addItem(ConditionToProcessType.ALL_ENTITIES_AVAILABLE);
		conditionToProcessComboBox.addItem(ConditionToProcessType.SINGLE_ENTITY_AVAILABLE);
		
		JLabel quantityOfResourcesLabel = new JLabel("Quantity of resources needed to perform the task");
		
		quantityOfResourcesTextField = new JTextField();
		quantityOfResourcesTextField.setColumns(10);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(10)
							.addComponent(spemTypeLabel)
							.addGap(12)
							.addComponent(spemTypeTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(105)
							.addComponent(timeboxLabel)
							.addGap(26)
							.addComponent(timeboxTextField, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(10)
							.addComponent(processingQuantityLabel)
							.addGap(12)
							.addComponent(processingQuantityComboBox, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(parentLabel)
							.addGap(10)
							.addComponent(parentTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(10)
							.addComponent(dependencyTypeLabel)
							.addGap(21)
							.addComponent(dependencyTypeComboBox, GroupLayout.PREFERRED_SIZE, 253, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(10)
							.addComponent(conditionToProcessLabel)
							.addGap(7)
							.addComponent(conditionToProcessComboBox, GroupLayout.PREFERRED_SIZE, 253, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(10)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(behaviourAtTheEndOfIterationLabel, GroupLayout.PREFERRED_SIZE, 236, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(233)
									.addComponent(behaviourAtTheEndOfIterationComboBox, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE))))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addComponent(quantityOfResourcesLabel)
							.addGap(7)
							.addComponent(quantityOfResourcesTextField, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(extendedXacdmlAttributesLabel, GroupLayout.PREFERRED_SIZE, 228, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(18, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(13)
					.addComponent(extendedXacdmlAttributesLabel, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addComponent(spemTypeLabel))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(2)
							.addComponent(spemTypeTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addComponent(timeboxLabel))
						.addComponent(timeboxTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(14)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(4)
							.addComponent(processingQuantityLabel))
						.addComponent(processingQuantityComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(7)
							.addComponent(parentLabel))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(4)
							.addComponent(parentTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(21)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addComponent(dependencyTypeLabel))
						.addComponent(dependencyTypeComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(7)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(4)
							.addComponent(conditionToProcessLabel))
						.addComponent(conditionToProcessComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(16)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(4)
							.addComponent(behaviourAtTheEndOfIterationLabel))
						.addComponent(behaviourAtTheEndOfIterationComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(9)
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



	public JTextField getQuantityOfResourcesNeededTextField() {
		return quantityOfResourcesNeededTextField;
	}



	public void setQuantityOfResourcesNeededTextField(JTextField quantityOfResourcesNeededTextField) {
		this.quantityOfResourcesNeededTextField = quantityOfResourcesNeededTextField;
	}



	public JTextField getSpemTypeTextField() {
		return spemTypeTextField;
	}



	public void setSpemTypeTextField(JTextField spemTypeTextField) {
		this.spemTypeTextField = spemTypeTextField;
	}
}
