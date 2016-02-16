package adaptme.dynamic.gui.meeting;

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

import adaptme.dynamic.gui.RepositoryViewPanel;
import adaptme.dynamic.gui.UpdatePanel;
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

public class MeetingPanel implements UpdatePanel {
    private JLabel lblSession;
    private JLabel lblDurationmean;
    private JTextField textFieldDurationMean;
    private JLabel lblDurationstdDeviation;
    private JTextField textFieldDurationStdDeviation;
    private JLabel lblBestFitProbbility;
    private JComboBox<String> comboBoxDistribution;
    private ProcessContentRepository processContentRepository;
    private ParametersPanel parametersPanel;
    private FocusListener focusListener;
    private Parameters parameters;


    public MeetingPanel(RepositoryViewPanel repositoryViewPanel, ProcessContentRepository processContentRepository) {

	this.processContentRepository = processContentRepository;
	lblSession = new JLabel("Development session");
	lblSession.setFont(new Font("SansSerif", Font.BOLD, 14));

	lblDurationmean = new JLabel("Duration (mean)");

	textFieldDurationMean = new JTextField();
	textFieldDurationMean.setText("480");
	textFieldDurationMean.setColumns(10);

	lblDurationstdDeviation = new JLabel("Duration (Std. deviation)");

	textFieldDurationStdDeviation = new JTextField();
	textFieldDurationStdDeviation.setText("0");
	textFieldDurationStdDeviation.setColumns(10);

	lblBestFitProbbility = new JLabel("Best fit probability distribution");

	comboBoxDistribution = new JComboBox<>();
	panel.setBorder(
		new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Local View", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(59, 59, 59)));

	lblBusinessDays = new JLabel("business days");

	label = new JLabel("business days");

	focusListener = new FocusListener() {

	    @Override
	    public void focusLost(FocusEvent e) {
	    	    
//	    		String s = (String) comboBoxDistribution.getSelectedItem();
	    	    ProcessRepository p = SPEMDrivenPerspectivePanel.processRepository;
	    	    JTextField textField = (JTextField) e.getSource();
	    		if (parameters instanceof ConstantParameters) {
					
					ConstantParameters constantParameters = (ConstantParameters)parameters;
					constantParameters.setValue(Double.parseDouble(textField.getText()));
		 			 
				} else if (parameters instanceof UniformParameters) {
					
					UniformParameters UniformParameters = (UniformParameters)parameters;
					if (textField.getName().equals("high")) {
						UniformParameters.setHigh(Double.parseDouble(textField.getText()));
					} else {
						UniformParameters.setLow(Double.parseDouble(textField.getText()));
					}
					
				

				} else if (parameters instanceof NegativeExponential) {
					
					NegativeExponential negativeExponential = (NegativeExponential)parameters;
					negativeExponential.setAverage(Double.parseDouble(textField.getText()));
 					 
	 	 			
				} else if (parameters instanceof NormalParameters) {
					
					NormalParameters normalParameters = (NormalParameters)parameters;
					
					if (textField.getName().equals("average")) {
						normalParameters.setMean(Double.parseDouble(textField.getText()));
					} else {
						normalParameters.setStandardDeviation(Double.parseDouble(textField.getText()));
					}
 

				} else if (parameters instanceof PoissonParameters) {
					
					PoissonParameters poissonParameters = (PoissonParameters)parameters;
					poissonParameters.setMean(Double.parseDouble(textField.getText()));
	 			}
	    	
//		repositoryViewPanel.setMessagem("");
	    }

	    @Override
	    public void focusGained(FocusEvent e) {
		repositoryViewPanel.setMessagem("Não existe dados no servidor para " + title);
	    }
	};
	
	
	scrollPaneParameters = new JScrollPane();
	scrollPaneParameters.setBorder(BorderFactory.createEmptyBorder());
	scrollPaneParameters.setViewportBorder(null);
	parameters = Parameters.createParameter(BestFitDistribution.NORMAL);
	Sample sample = new Sample();
	processContentRepository.setSample(sample);
	processContentRepository.getSample().setParameters(parameters);
	parametersPanel =  new ParametersPanel(parameters, focusListener);
	scrollPaneParameters.setViewportView(parametersPanel.getPanel());
	comboBoxDistribution.addItemListener(e -> {
	    String s = (String) comboBoxDistribution.getSelectedItem();
	    // scrollPaneParameters.removeAll();
	     parameters = Parameters.createParameter(BestFitDistribution.getDistributionByName(s));
	    parametersPanel =  new ParametersPanel(parameters, focusListener);
	    scrollPaneParameters.setViewportView(parametersPanel.getPanel());
	    scrollPaneParameters.revalidate();
	    scrollPaneParameters.repaint();
		processContentRepository.getSample().setParameters(parameters);
	});
	GroupLayout gl_panel = new GroupLayout(panel);
	gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
		.addGroup(gl_panel.createSequentialGroup().addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
			.addGroup(gl_panel.createSequentialGroup().addGap(6)
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(lblSession)
					.addGroup(gl_panel.createSequentialGroup()
						.addComponent(lblBestFitProbbility, GroupLayout.PREFERRED_SIZE, 175,
							GroupLayout.PREFERRED_SIZE)
						.addGap(69).addComponent(comboBoxDistribution,
							GroupLayout.PREFERRED_SIZE, 186, GroupLayout.PREFERRED_SIZE))))
			.addGroup(gl_panel.createSequentialGroup().addContainerGap().addComponent(scrollPaneParameters,
				GroupLayout.PREFERRED_SIZE, 430, GroupLayout.PREFERRED_SIZE))
			.addGroup(gl_panel.createSequentialGroup().addContainerGap().addGroup(gl_panel
				.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup().addComponent(lblDurationmean).addGap(155)
					.addComponent(textFieldDurationMean, GroupLayout.PREFERRED_SIZE, 56,
						GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(lblBusinessDays,
						GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel.createSequentialGroup().addComponent(lblDurationstdDeviation)
					.addGap(113)
					.addComponent(textFieldDurationStdDeviation, GroupLayout.PREFERRED_SIZE, 56,
						GroupLayout.PREFERRED_SIZE)
					.addGap(12).addComponent(label, GroupLayout.PREFERRED_SIZE, 83,
						GroupLayout.PREFERRED_SIZE)))))
		.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
	gl_panel.setVerticalGroup(
		gl_panel.createParallelGroup(Alignment.LEADING)
			.addGroup(gl_panel.createSequentialGroup().addGap(18).addComponent(lblSession).addGap(18)
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel.createSequentialGroup().addGap(5)
						.addComponent(lblBestFitProbbility))
					.addComponent(comboBoxDistribution, GroupLayout.PREFERRED_SIZE,
						GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(22)
				.addComponent(scrollPaneParameters, GroupLayout.PREFERRED_SIZE, 105,
					GroupLayout.PREFERRED_SIZE)
		.addPreferredGap(ComponentPlacement.RELATED)
		.addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(lblDurationmean)
			.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE).addComponent(lblBusinessDays)
				.addComponent(textFieldDurationMean, GroupLayout.PREFERRED_SIZE,
					GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
		.addGap(12)
		.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
			.addGroup(gl_panel.createSequentialGroup().addGap(6).addComponent(lblDurationstdDeviation))
			.addComponent(textFieldDurationStdDeviation, GroupLayout.PREFERRED_SIZE,
				GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
			.addGroup(gl_panel.createSequentialGroup().addGap(6).addComponent(label))).addGap(285)));
	panel.setLayout(gl_panel);

	
	textFieldDurationMean.addFocusListener(focusListener);
	textFieldDurationStdDeviation.addFocusListener(focusListener);
    }

    public String getDistribution() {
	return (String) comboBoxDistribution.getSelectedItem();
    }

    public void setDistribution(List<String> list) {
	DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(list.toArray(new String[list.size()]));
	comboBoxDistribution.setModel(model);
    }

    public double getDurationMean() {
	return Double.parseDouble(textFieldDurationMean.getText());
    }

    public double getDurationStdDeviation() {
	return Double.parseDouble(textFieldDurationStdDeviation.getText());
    }

    public void setDurationMean(double durationMean) {
	textFieldDurationMean.setText("" + durationMean);
    }

    public void setDurationStdDeviation(double durationStdDeviation) {
	textFieldDurationStdDeviation.setText("" + durationStdDeviation);
    }

    public void setSessionTitle(String title) {
	this.title = title;
	lblSession.setText(title);
    }

    private JPanel panel = new JPanel();
    private JLabel lblBusinessDays;
    private JLabel label;
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
