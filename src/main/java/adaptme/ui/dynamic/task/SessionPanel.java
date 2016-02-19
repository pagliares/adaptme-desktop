package adaptme.ui.dynamic.task;

import java.awt.Color;
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

import adaptme.ui.dynamic.RepositoryViewPanel;
import adaptme.ui.dynamic.UpdatePanel;
import adaptme.ui.listener.ProbabilityDistributionPanelListener;
import model.spem.ProcessContentRepository;
import model.spem.Sample;
import model.spem.derived.BestFitDistribution;
import model.spem.derived.Parameters;
import model.spem.derived.gui.ParametersPanel;
import adaptme.ui.dynamic.meeting.LocalViewBottomPanel;
import java.awt.GridBagLayout;

public class SessionPanel implements UpdatePanel {
	private JLabel lblSession;
	private JLabel lblBestFitProbbility;
	private JComboBox<BestFitDistribution> distributionJComboBox;
	private String title;
	private ProcessContentRepository processContentRepository;
	private JPanel panel = new JPanel();
	private JLabel lblMinimumNumberOf;
	private JTextField textFieldNumberOfDevelopersNeeded;
	private JScrollPane scrollPaneParameters;
	private Parameters parameters;
	private ProbabilityDistributionPanelListener probabilityDistributionPanelListener = new ProbabilityDistributionPanelListener();

	public SessionPanel(RepositoryViewPanel repositoryViewPanel, ProcessContentRepository processContentRepository) {

		this.processContentRepository = processContentRepository;
		lblSession = new JLabel("Development session");
		lblSession.setFont(new Font("SansSerif", Font.BOLD, 14));

		lblBestFitProbbility = new JLabel("Best fit probability distribution");

		scrollPaneParameters = new JScrollPane();
		scrollPaneParameters.setViewportBorder(null);
		scrollPaneParameters.setBorder(BorderFactory.createEmptyBorder());
		distributionJComboBox = new JComboBox<>();
		distributionJComboBox.addItem(BestFitDistribution.NONE);
 		distributionJComboBox.addItem(BestFitDistribution.CONSTANT);
 		distributionJComboBox.addItem(BestFitDistribution.NEGATIVE_EXPONENTIAL);
 		distributionJComboBox.addItem(BestFitDistribution.NORMAL);
 		distributionJComboBox.addItem(BestFitDistribution.POISSON);
 		distributionJComboBox.addItem(BestFitDistribution.UNIFORM);
 		
		parameters = Parameters.createParameter(BestFitDistribution.NORMAL);
		Sample sample = new Sample();
		processContentRepository.setSample(sample);
		processContentRepository.getSample().setParameters(parameters);
		scrollPaneParameters.setViewportView(new ParametersPanel(parameters, probabilityDistributionPanelListener).getPanel());
		probabilityDistributionPanelListener.setParameters(parameters);
		distributionJComboBox.addItemListener(e -> {
			 
			parameters = Parameters.createParameter((BestFitDistribution)distributionJComboBox.getSelectedItem());
			probabilityDistributionPanelListener.setParameters(parameters);
			scrollPaneParameters.setViewportView(new ParametersPanel(parameters, probabilityDistributionPanelListener).getPanel());
			scrollPaneParameters.revalidate();
			scrollPaneParameters.repaint();
			processContentRepository.getSample().setParameters(parameters);

		});
		panel.setBorder(
				new TitledBorder(null, "Local view", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(59, 59, 59)));

		lblMinimumNumberOf = new JLabel("Minimum number of developers needed");

		textFieldNumberOfDevelopersNeeded = new JTextField();
		textFieldNumberOfDevelopersNeeded.setText("1");
		textFieldNumberOfDevelopersNeeded.setColumns(2);
		
		LocalViewBottomPanel localViewBottomPanel = new LocalViewBottomPanel();
		GridBagLayout gridBagLayout = (GridBagLayout) localViewBottomPanel.getLayout();
		gridBagLayout.rowWeights = new double[]{0.0, 0.0};
		gridBagLayout.rowHeights = new int[]{57, 150};
		gridBagLayout.columnWeights = new double[]{0.0};
		gridBagLayout.columnWidths = new int[]{549};
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(6)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblSession)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(lblBestFitProbbility, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
									.addGap(65)
									.addComponent(distributionJComboBox, GroupLayout.PREFERRED_SIZE, 186, GroupLayout.PREFERRED_SIZE))
								.addComponent(scrollPaneParameters, GroupLayout.PREFERRED_SIZE, 426, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblMinimumNumberOf)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(textFieldNumberOfDevelopersNeeded, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(125, Short.MAX_VALUE))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(10)
					.addComponent(localViewBottomPanel, GroupLayout.PREFERRED_SIZE, 521, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(26, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(18)
					.addComponent(lblSession)
					.addGap(16)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(5)
							.addComponent(lblBestFitProbbility))
						.addComponent(distributionJComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(6)
					.addComponent(scrollPaneParameters, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblMinimumNumberOf)
						.addComponent(textFieldNumberOfDevelopersNeeded, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(localViewBottomPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(21))
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
