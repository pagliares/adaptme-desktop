package adaptme.ui.dynamic.meeting;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.TitledBorder;

import adaptme.ui.dynamic.UpdatePanel;
import adaptme.ui.listener.ProbabilityDistributionPanelListener;
import adaptme.ui.window.perspective.SPEMDrivenPerspectivePanel;
import model.spem.ProcessContentRepository;
import model.spem.ProcessRepository;
import model.spem.Sample;
import model.spem.derived.BestFitDistribution;
import model.spem.derived.ConstantParameters;
import model.spem.derived.NegativeExponential;
import model.spem.derived.NormalParameters;
import model.spem.derived.Parameters;
import model.spem.derived.PoissonParameters;
import model.spem.derived.UniformParameters;
import model.spem.derived.gui.ParametersPanel;
import javax.swing.UIManager;
import java.awt.GridBagLayout;

public class LocalViewPanel implements UpdatePanel {
    private JLabel lblSession;
    private JLabel lblBestFitProbbility;
    private JComboBox<BestFitDistribution> distributionJComboBox;
    private ProcessContentRepository processContentRepository;
    private ParametersPanel parametersPanel;
    private ProbabilityDistributionPanelListener focusListener;
    private Parameters parameters;


    public LocalViewPanel(ProcessContentRepository processContentRepository) {

	this.processContentRepository = processContentRepository;
	lblSession = new JLabel("Development session");
	lblSession.setFont(new Font("SansSerif", Font.BOLD, 14));

	lblBestFitProbbility = new JLabel("Best fit probability distribution");

	distributionJComboBox = new JComboBox<>();
	distributionJComboBox.addItem(BestFitDistribution.NONE);
	distributionJComboBox.addItem(BestFitDistribution.CONSTANT);
	distributionJComboBox.addItem(BestFitDistribution.NEGATIVE_EXPONENTIAL);
	distributionJComboBox.addItem(BestFitDistribution.NORMAL);
	distributionJComboBox.addItem(BestFitDistribution.POISSON);
	distributionJComboBox.addItem(BestFitDistribution.UNIFORM);
	
	panel.setBorder(
		new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Local View", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(59, 59, 59)));
 	focusListener = new ProbabilityDistributionPanelListener();

	scrollPaneParameters = new JScrollPane();
	scrollPaneParameters.setBorder(BorderFactory.createEmptyBorder());
	scrollPaneParameters.setViewportBorder(null);
	parameters = Parameters.createParameter(BestFitDistribution.CONSTANT);
	Sample sample = new Sample();
	processContentRepository.setSample(sample);
	processContentRepository.getSample().setParameters(parameters);
	parametersPanel =  new ParametersPanel(parameters, focusListener);
	focusListener.setParameters(parameters);

	scrollPaneParameters.setViewportView(parametersPanel.getPanel());
	
	distributionJComboBox.addItemListener(e -> {
//	    String s = (String) distributionJComboBox.getSelectedItem();
	    // scrollPaneParameters.removeAll();
//	     parameters = Parameters.createParameter(BestFitDistribution.getDistributionByName(s));
	     parameters = Parameters.createParameter((BestFitDistribution)distributionJComboBox.getSelectedItem());
	    parametersPanel =  new ParametersPanel(parameters, focusListener);
		focusListener.setParameters(parameters);
	    scrollPaneParameters.setViewportView(parametersPanel.getPanel());
	    scrollPaneParameters.revalidate();
	    scrollPaneParameters.repaint();
		processContentRepository.getSample().setParameters(parameters);
	});
	
	LocalViewBottomPanel localViewBottomPanel = new LocalViewBottomPanel();
	GridBagLayout gridBagLayout = (GridBagLayout) localViewBottomPanel.getLayout();
	gridBagLayout.rowWeights = new double[]{0.0, 0.0};
	gridBagLayout.rowHeights = new int[]{57, 150};
	gridBagLayout.columnWeights = new double[]{0.0};
	gridBagLayout.columnWidths = new int[]{549};
	GroupLayout gl_panel = new GroupLayout(panel);
	gl_panel.setHorizontalGroup(
		gl_panel.createParallelGroup(Alignment.LEADING)
			.addGroup(gl_panel.createSequentialGroup()
				.addGap(6)
				.addComponent(lblSession))
			.addGroup(gl_panel.createSequentialGroup()
				.addGap(6)
				.addComponent(lblBestFitProbbility, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
				.addGap(69)
				.addComponent(distributionJComboBox, GroupLayout.PREFERRED_SIZE, 186, GroupLayout.PREFERRED_SIZE))
			.addGroup(gl_panel.createSequentialGroup()
				.addGap(6)
				.addComponent(scrollPaneParameters, GroupLayout.PREFERRED_SIZE, 430, GroupLayout.PREFERRED_SIZE))
			.addComponent(localViewBottomPanel, GroupLayout.PREFERRED_SIZE, 522, GroupLayout.PREFERRED_SIZE)
	);
	gl_panel.setVerticalGroup(
		gl_panel.createParallelGroup(Alignment.LEADING)
			.addGroup(gl_panel.createSequentialGroup()
				.addGap(18)
				.addComponent(lblSession)
				.addGap(18)
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel.createSequentialGroup()
						.addGap(5)
						.addComponent(lblBestFitProbbility))
					.addComponent(distributionJComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(22)
				.addComponent(scrollPaneParameters, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
				.addGap(6)
				.addComponent(localViewBottomPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
	);
	panel.setLayout(gl_panel);
    }

    public String getDistribution() {
	return (String) distributionJComboBox.getSelectedItem();
    }

    public void setSessionTitle(String title) {
    	this.title = title;
    	lblSession.setText(title);
    }

    private JPanel panel = new JPanel();
    private String title;
    private JScrollPane scrollPaneParameters;

    public void setTitle(String title) {
	this.title = title;
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
}
