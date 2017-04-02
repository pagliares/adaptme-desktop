package adaptme.ui.dynamic.simulation.alternative.process;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Font;
import javax.swing.JComboBox;

import model.spem.util.BehaviourAtEndOfIterationType;
import model.spem.util.ConditionToProcessType;
import model.spem.util.DependencyType;
import model.spem.util.ProcessContentType;
import model.spem.util.ProcessingQuantityType;

public class ExtendedXACDMLAttributesPanel extends JPanel {

	private JLabel behaviourAtTheEndOfIterationLabel;
	private JLabel extendedXacdmlAttributesLabel;
	private JComboBox<BehaviourAtEndOfIterationType> behaviourAtTheEndOfIterationComboBox;
	
	private JLabel spemTypeLabel;
	private JComboBox<ProcessContentType> spemTypeComboBox;
	
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
	


	/**
	 * Create the panel.
	 */
	public ExtendedXACDMLAttributesPanel() {
		setBorder(null);
		
		extendedXacdmlAttributesLabel = new JLabel("Extended XACDML attributes:");
		extendedXacdmlAttributesLabel.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		
		behaviourAtTheEndOfIterationLabel = new JLabel("Behaviour at the end of an iteration");
		behaviourAtTheEndOfIterationComboBox = new JComboBox<>();
		behaviourAtTheEndOfIterationComboBox.addItem(BehaviourAtEndOfIterationType.MOVE_BACK);
		behaviourAtTheEndOfIterationComboBox.addItem(BehaviourAtEndOfIterationType.DO_NOT_MOVE_BACK);

		
		spemTypeLabel = new JLabel("SPEM type");
	    spemTypeComboBox = new JComboBox<ProcessContentType>();
	    spemTypeComboBox.addItem(ProcessContentType.ACTIVITY);
	    spemTypeComboBox.addItem(ProcessContentType.DELIVERY_PROCESS);
	    spemTypeComboBox.addItem(ProcessContentType.ITERATION);
	    spemTypeComboBox.addItem(ProcessContentType.MILESTONE);
	    spemTypeComboBox.addItem(ProcessContentType.PHASE);
	    spemTypeComboBox.addItem(ProcessContentType.TASK);
	    
		
		timeboxLabel = new JLabel("Timebox");
		timeboxTextField = new JTextField();
		timeboxTextField.setColumns(10);
		
		processingQuantityLabel = new JLabel("Processing quantity");
		processingQuantityComboBox = new JComboBox<>();
		processingQuantityComboBox.addItem(ProcessingQuantityType.BATCH);
		processingQuantityComboBox.addItem(ProcessingQuantityType.UNIT);

		parentLabel = new JLabel("Parent");
		parentTextField = new JTextField();
		parentTextField.setColumns(10);
		
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

		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(extendedXacdmlAttributesLabel, GroupLayout.PREFERRED_SIZE, 228, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(10)
							.addComponent(spemTypeLabel)
							.addGap(12)
							.addComponent(spemTypeComboBox, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
							.addGap(123)
							.addComponent(timeboxLabel)
							.addGap(26)
							.addComponent(timeboxTextField, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(10)
							.addComponent(processingQuantityLabel)
							.addGap(12)
							.addComponent(processingQuantityComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(61)
							.addComponent(parentLabel)
							.addGap(12)
							.addComponent(parentTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(119)
							.addComponent(dependencyTypeComboBox, GroupLayout.PREFERRED_SIZE, 325, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(149)
							.addComponent(conditionToProcessComboBox, GroupLayout.PREFERRED_SIZE, 295, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(10)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(behaviourAtTheEndOfIterationLabel, GroupLayout.PREFERRED_SIZE, 236, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(233)
									.addComponent(behaviourAtTheEndOfIterationComboBox, GroupLayout.PREFERRED_SIZE, 201, GroupLayout.PREFERRED_SIZE))))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(10)
							.addComponent(dependencyTypeLabel))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(10)
							.addComponent(conditionToProcessLabel)))
					.addContainerGap(6, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(12)
					.addComponent(extendedXacdmlAttributesLabel, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addComponent(spemTypeLabel))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(2)
							.addComponent(spemTypeComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addComponent(timeboxLabel))
						.addComponent(timeboxTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(13)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addComponent(processingQuantityLabel))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(2)
							.addComponent(processingQuantityComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addComponent(parentLabel))
						.addComponent(parentTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(17)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(dependencyTypeComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(4)
							.addComponent(dependencyTypeLabel)))
					.addGap(5)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(conditionToProcessComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(4)
							.addComponent(conditionToProcessLabel)))
					.addGap(16)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(4)
							.addComponent(behaviourAtTheEndOfIterationLabel))
						.addComponent(behaviourAtTheEndOfIterationComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
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



	public JComboBox<ProcessContentType> getSpemTypeComboBox() {
		return spemTypeComboBox;
	}



	public void setSpemTypeComboBox(JComboBox<ProcessContentType> spemTypeComboBox) {
		this.spemTypeComboBox = spemTypeComboBox;
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
}
