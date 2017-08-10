package adaptme.ui.dynamic.simulation.alternative.process;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import adaptme.ui.dynamic.UpdatePanel;
import adaptme.ui.listener.ProbabilityDistributionPanelListener;
import model.spem.ProcessContentRepository;
import model.spem.Sample;
import model.spem.derived.BestFitDistribution;
import model.spem.derived.Parameters;
import model.spem.derived.gui.ParametersPanel;
import model.spem.util.ProcessContentType;

import javax.swing.border.LineBorder;
import javax.swing.LayoutStyle.ComponentPlacement;

public class LocalViewPanel implements UpdatePanel {
	
    private JLabel lblSession;
    private JLabel lblBestFitProbbility;
    private JComboBox<BestFitDistribution> distributionJComboBox;
    private ProcessContentRepository processContentRepository;
    private ParametersPanel parametersPanel;
    private ProbabilityDistributionPanelListener focusListener;
    private Parameters bestFitDistributionParameters;
    private LocalViewBottomPanel localViewBottomPanel;

    private JPanel panel = new JPanel();
    private String title;
    private JScrollPane scrollPaneParameters;

	public LocalViewPanel(ProcessContentRepository processContentRepository) {

		this.processContentRepository = processContentRepository;
		lblSession = new JLabel("Development session");
		lblSession.setFont(new Font("SansSerif", Font.BOLD, 14));

		lblBestFitProbbility = new JLabel("Best fit probability distribution");

		distributionJComboBox = new JComboBox<>();
		distributionJComboBox.addItem(BestFitDistribution.CONSTANT);
		distributionJComboBox.addItem(BestFitDistribution.NEGATIVE_EXPONENTIAL);
		distributionJComboBox.addItem(BestFitDistribution.NORMAL);
		distributionJComboBox.addItem(BestFitDistribution.POISSON);
		distributionJComboBox.addItem(BestFitDistribution.UNIFORM);
		distributionJComboBox.addItem(BestFitDistribution.LOGNORMAL);
		distributionJComboBox.addItem(BestFitDistribution.WEIBULL);
		distributionJComboBox.addItem(BestFitDistribution.GAMMA);
		distributionJComboBox.addItem(BestFitDistribution.EXPONENTIAL);

		Sample sample = new Sample();
		processContentRepository.setSample(sample);
		scrollPaneParameters = new JScrollPane();
		scrollPaneParameters.setBorder(BorderFactory.createEmptyBorder());
		scrollPaneParameters.setViewportBorder(null);
		focusListener = new ProbabilityDistributionPanelListener();

		bestFitDistributionParameters = Parameters.createParameter(BestFitDistribution.CONSTANT);
		parametersPanel = new ParametersPanel(bestFitDistributionParameters, focusListener);
		focusListener.setParameters(bestFitDistributionParameters);
		scrollPaneParameters.setViewportView(parametersPanel.getPanel());
		scrollPaneParameters.revalidate();
		scrollPaneParameters.repaint();
		processContentRepository.getSample().setParameters(bestFitDistributionParameters);

		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Local View", TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(59, 59, 59)));

		parametersPanel = new ParametersPanel(bestFitDistributionParameters, focusListener);
		focusListener.setParameters(bestFitDistributionParameters);

		scrollPaneParameters.setViewportView(parametersPanel.getPanel());

		distributionJComboBox.addItemListener(e -> {
			bestFitDistributionParameters = Parameters.createParameter((BestFitDistribution) distributionJComboBox.getSelectedItem());
			parametersPanel = new ParametersPanel(bestFitDistributionParameters, focusListener);
			focusListener.setParameters(bestFitDistributionParameters);
			scrollPaneParameters.setViewportView(parametersPanel.getPanel());
			scrollPaneParameters.revalidate();
			scrollPaneParameters.repaint();
			processContentRepository.getSample().setParameters(bestFitDistributionParameters);
		});
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.getVerticalScrollBar().setUnitIncrement(16);
		
				localViewBottomPanel = new LocalViewBottomPanel(processContentRepository);
				scrollPane_1.setViewportView(localViewBottomPanel);
				GroupLayout gl_panel = new GroupLayout(panel);
				gl_panel.setHorizontalGroup(
					gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(6)
							.addComponent(lblSession))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(6)
							.addComponent(lblBestFitProbbility, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(6)
							.addComponent(scrollPaneParameters, GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
							.addGap(84))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(4)
							.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(229)
							.addComponent(distributionJComboBox, GroupLayout.PREFERRED_SIZE, 251, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
				gl_panel.setVerticalGroup(
					gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(18)
									.addComponent(lblSession)
									.addGap(23)
									.addComponent(lblBestFitProbbility))
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(57)
									.addComponent(distributionJComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addGap(20)
							.addComponent(scrollPaneParameters, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
							.addGap(11)
							.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
							.addGap(4))
				);
				panel.setLayout(gl_panel);
				
				disableLocalViewFormElementsBasedOnSPEMType(processContentRepository);
	}

	private void disableLocalViewFormElementsBasedOnSPEMType(ProcessContentRepository processContentRepository) {
		
		ExtendedXACDMLAttributesPanel extendedXACDMLAttributesPanel = (ExtendedXACDMLAttributesPanel)localViewBottomPanel.getExtendeXACDMLAttributesPanel();
		
		switch (processContentRepository.getType()) { 
		case PHASE:
			disableGUIElementsOfNoInterestToPhaseOrMilestone(extendedXACDMLAttributesPanel);
			break;
		case MILESTONE:
			disableGUIElementsOfNoInterestToPhaseOrMilestone(extendedXACDMLAttributesPanel);
            break;
 		case ACTIVITY:
			disableGUIElementsOfNoInterestToActivity(extendedXACDMLAttributesPanel);
            break;
 		case ITERATION:
			disableGUIElementsOfNoInterestToIteration(extendedXACDMLAttributesPanel);
			break;
 		case TASK:
			disableGUIElementsOfNoInterestToTask(extendedXACDMLAttributesPanel);
			break;		
		}
		
		if (processContentRepository.getPredecessors().size() == 0) {
			extendedXACDMLAttributesPanel.getDependencyTypeLabel().setEnabled(false);
			extendedXACDMLAttributesPanel.getDependencyTypeComboBox().setEnabled(false);

		}
		
		
	}

	private void disableGUIElementsOfNoInterestToPhaseOrMilestone(ExtendedXACDMLAttributesPanel extendedXACDMLAttributesPanel) {
		distributionJComboBox.setEnabled(false);
		parametersPanel.getTitleText().setEnabled(false);
		parametersPanel.getLabel().setEnabled(false);
		localViewBottomPanel.getActivityTextField().setEnabled(false);
		localViewBottomPanel.getActivityLabel().setEnabled(false);
		localViewBottomPanel.getAddObserverButton().setEnabled(false);
		localViewBottomPanel.getRemoveObserverButton().setEnabled(false);
		lblBestFitProbbility.setEnabled(false);
		lblSession.setEnabled(false);
		localViewBottomPanel.getTableObservers().setVisible(false);
		
		extendedXACDMLAttributesPanel.getParentLabel().setEnabled(false);
		extendedXACDMLAttributesPanel.getParentTextField().setEnabled(false);
		
		extendedXACDMLAttributesPanel.getSpemTypeLabel().setEnabled(false);
		extendedXACDMLAttributesPanel.getSpemTypeTextField().setEnabled(false);
		
		extendedXACDMLAttributesPanel.getQuantityOfResourcesLabel().setEnabled(false);
		extendedXACDMLAttributesPanel.getQuantityOfResourcesTextField().setEnabled(false);
		
		extendedXACDMLAttributesPanel.getBehaviourAtTheEndOfIterationLabel().setEnabled(false);
		extendedXACDMLAttributesPanel.getBehaviourAtTheEndOfIterationComboBox().setEnabled(false);
		
		extendedXACDMLAttributesPanel.getTimeboxLabel().setEnabled(false);
		extendedXACDMLAttributesPanel.getTimeboxTextField().setEnabled(false);
	}
	
	private void disableGUIElementsOfNoInterestToIteration(ExtendedXACDMLAttributesPanel extendedXACDMLAttributesPanel) {
		
		extendedXACDMLAttributesPanel.getParentLabel().setEnabled(false);
		extendedXACDMLAttributesPanel.getParentTextField().setEnabled(false);
		
		extendedXACDMLAttributesPanel.getSpemTypeLabel().setEnabled(false);
		extendedXACDMLAttributesPanel.getSpemTypeTextField().setEnabled(false);
		
		extendedXACDMLAttributesPanel.getQuantityOfResourcesLabel().setEnabled(false);
		extendedXACDMLAttributesPanel.getQuantityOfResourcesTextField().setEnabled(false);
	}
	
	private void disableGUIElementsOfNoInterestToActivity(ExtendedXACDMLAttributesPanel extendedXACDMLAttributesPanel) {
		
		extendedXACDMLAttributesPanel.getParentLabel().setEnabled(false);
		extendedXACDMLAttributesPanel.getParentTextField().setEnabled(false);
		
		extendedXACDMLAttributesPanel.getSpemTypeLabel().setEnabled(false);
		extendedXACDMLAttributesPanel.getSpemTypeTextField().setEnabled(false);
		
		extendedXACDMLAttributesPanel.getQuantityOfResourcesLabel().setEnabled(false);
		extendedXACDMLAttributesPanel.getQuantityOfResourcesTextField().setEnabled(false);
		
		extendedXACDMLAttributesPanel.getBehaviourAtTheEndOfIterationLabel().setEnabled(false);
		extendedXACDMLAttributesPanel.getBehaviourAtTheEndOfIterationComboBox().setEnabled(false);		
	}

	private void disableGUIElementsOfNoInterestToTask(ExtendedXACDMLAttributesPanel extendedXACDMLAttributesPanel) {
		extendedXACDMLAttributesPanel.getParentLabel().setEnabled(false);
		extendedXACDMLAttributesPanel.getParentTextField().setEnabled(false);
		
		extendedXACDMLAttributesPanel.getSpemTypeLabel().setEnabled(false);
		extendedXACDMLAttributesPanel.getSpemTypeTextField().setEnabled(false);
		
		extendedXACDMLAttributesPanel.getBehaviourAtTheEndOfIterationLabel().setEnabled(false);
		extendedXACDMLAttributesPanel.getBehaviourAtTheEndOfIterationComboBox().setEnabled(false);
		
		extendedXACDMLAttributesPanel.getTimeboxLabel().setEnabled(false);
		extendedXACDMLAttributesPanel.getTimeboxTextField().setEnabled(false);
	}

    public String getDistribution() {
    	return (String) distributionJComboBox.getSelectedItem();
    }

    public void setSessionTitle(String title) {
    	this.title = title;
    	lblSession.setText("Duration time for " + title);
    }
    
    @Override
    public JPanel getPanel() {
    	return panel;
    }

    @Override
    public void updateContent() {
    	// Parameters parameters = new Parameters();
    	// parameters.setMean(getDurationMean());
    	// parameters.setStandardDeviation(getDurationStdDeviation());
    	Sample sample = new Sample();
    	sample.setDistribution(BestFitDistribution.getDistributionByName(getDistribution()));
    	// sample.setParameters(parameters);
    	processContentRepository.setSample(sample);
    }

	public LocalViewBottomPanel getLocalViewBottomPanel() {
		return localViewBottomPanel;
	}

	public String getTitle() {
		return title;
	}
}
