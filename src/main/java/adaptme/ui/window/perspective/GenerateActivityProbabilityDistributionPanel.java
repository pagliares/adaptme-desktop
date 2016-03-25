package adaptme.ui.window.perspective;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.TitledBorder;

import adaptme.ui.listener.ProbabilityDistributionPanelListener;
import model.spem.derived.BestFitDistribution;
import model.spem.derived.ConstantParameters;
import model.spem.derived.NegativeExponential;
import model.spem.derived.NormalParameters;
import model.spem.derived.Parameters;
import model.spem.derived.PoissonParameters;
import model.spem.derived.UniformParameters;
import model.spem.derived.gui.ParametersPanel;
import simulator.base.ActiveObserverType;

public class GenerateActivityProbabilityDistributionPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JComboBox<BestFitDistribution> distributionJComboBox;
	private JScrollPane scrollPane;
	private JLabel panelTitleLabel;
	private JLabel selectedDemandWorkProductLabel;
	private Parameters parameters;
	private ProbabilityDistributionPanelListener focusListener;
	private ParametersPanel parametersPanel;
	
	public GenerateActivityProbabilityDistributionPanel(int i, String title) { 
		 
		this.setName("panel.:" + i);
		this.setBorder(new TitledBorder(null, "Distribution parameters for generate activity", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
 		JLabel label = new JLabel("Best fit probability distribution");
		
 		distributionJComboBox  = new JComboBox<>();
 		distributionJComboBox.addItem(BestFitDistribution.CONSTANT);
 		distributionJComboBox.addItem(BestFitDistribution.NEGATIVE_EXPONENTIAL);
 		distributionJComboBox.addItem(BestFitDistribution.NORMAL);
 		distributionJComboBox.addItem(BestFitDistribution.POISSON);
 		distributionJComboBox.addItem(BestFitDistribution.UNIFORM);
 		
		scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(null);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		
		distributionJComboBox.setSelectedIndex(0);
		focusListener = new ProbabilityDistributionPanelListener();
		parameters = Parameters.createParameter((BestFitDistribution) distributionJComboBox.getSelectedItem());
		parametersPanel = new ParametersPanel(parameters, focusListener);
		focusListener.setParameters(parameters);
		scrollPane.setViewportView(new ParametersPanel(parameters, focusListener).getPanel());
		
		panelTitleLabel = new JLabel(title);
		
		selectedDemandWorkProductLabel = new JLabel("");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(6)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panelTitleLabel)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(label, GroupLayout.PREFERRED_SIZE, 205, GroupLayout.PREFERRED_SIZE)
							.addGap(28)
							.addComponent(distributionJComboBox, GroupLayout.PREFERRED_SIZE, 186, GroupLayout.PREFERRED_SIZE))
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 419, GroupLayout.PREFERRED_SIZE)))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(20)
					.addComponent(panelTitleLabel)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(4)
							.addComponent(label))
						.addComponent(distributionJComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(12)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE))
		);
		setLayout(groupLayout);
 		
		distributionJComboBox.addItemListener(e -> {
			
		    parameters = Parameters.createParameter((BestFitDistribution)distributionJComboBox.getSelectedItem());
		    parametersPanel =  new ParametersPanel(parameters, focusListener);
			focusListener.setParameters(parameters);
			scrollPane.setViewportView(new ParametersPanel(parameters, focusListener).getPanel());
		    scrollPane.revalidate();
		    scrollPane.repaint();
		    
		});
	}
	
	public JLabel getSelectedDemandWorkProductLabel() {
		return selectedDemandWorkProductLabel;
	}

	public void setSelectedDemandWorkProductLabel(JLabel selectedDemandWorkProductLabel) {
		this.selectedDemandWorkProductLabel = selectedDemandWorkProductLabel;
	}

	public JLabel getPanelTitleLabel() {
		return panelTitleLabel;
	}

	public void setPanelTitleLabel(JLabel panelTitleLabel) {
		this.panelTitleLabel = panelTitleLabel;
	}

	public JComboBox<BestFitDistribution> getDistributionJComboBox() {
		return distributionJComboBox;
	}

	public void setDistributionJComboBox(JComboBox<BestFitDistribution> comboBox) {
		this.distributionJComboBox = comboBox;
	}

	public Parameters getParameters() {
		return parameters;
	}

	public void setParameters(Parameters parameters) {
		this.parameters = parameters;
	}
}
