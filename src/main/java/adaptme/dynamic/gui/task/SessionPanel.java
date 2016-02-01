package adaptme.dynamic.gui.task;

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
import model.spem.ProcessContentRepository;
import model.spem.Sample;
import model.spem.derived.BestFitDistribution;
import model.spem.derived.Parameters;
import model.spem.derived.gui.ParametersPanel;

public class SessionPanel implements UpdatePanel {
    private JLabel lblSession;
    private JLabel lblDurationmean;
    private JTextField textFieldDurationMean;
    private JLabel lblDurationstdDeviation;
    private JTextField textFieldDurationStdDeviation;
    private JLabel lblBestFitProbbility;
    private JComboBox<String> comboBoxDistribution;
    private String title;
    private ProcessContentRepository processContentRepository;
    private JPanel panel = new JPanel();
    private JLabel lblUnity;
    private JComboBox<String> comboBoxMeasurementUnity;
    private JLabel lblMinimumNumberOf;
    private JTextField textFieldNumberOfDevelopersNeeded;
    private JScrollPane scrollPaneParameters;

    public SessionPanel(RepositoryViewPanel repositoryViewPanel, ProcessContentRepository processContentRepository) {

	this.processContentRepository = processContentRepository;
	lblSession = new JLabel("Development session");
	lblSession.setFont(new Font("SansSerif", Font.BOLD, 14));

	lblDurationmean = new JLabel("Duration (mean)");

	textFieldDurationMean = new JTextField();
	textFieldDurationMean.setText("10");
	textFieldDurationMean.setColumns(10);

	lblDurationstdDeviation = new JLabel("Duration (Std. deviation)");

	textFieldDurationStdDeviation = new JTextField();
	textFieldDurationStdDeviation.setText("0");
	textFieldDurationStdDeviation.setColumns(10);

	lblBestFitProbbility = new JLabel("Best fit probability distribution");

	scrollPaneParameters = new JScrollPane();
	scrollPaneParameters.setViewportBorder(null);
	scrollPaneParameters.setBorder(BorderFactory.createEmptyBorder());
	comboBoxDistribution = new JComboBox<>();
	Parameters parameters = Parameters.createParameter(BestFitDistribution.NORMAL);
	scrollPaneParameters.setViewportView(new ParametersPanel(parameters).getPanel());
	comboBoxDistribution.addItemListener(e -> {
	    String s = (String) comboBoxDistribution.getSelectedItem();
	    Parameters p = Parameters.createParameter(BestFitDistribution.getDistributionByName(s));
	    scrollPaneParameters.setViewportView(new ParametersPanel(p).getPanel());
	    scrollPaneParameters.revalidate();
	    scrollPaneParameters.repaint();
	});
	panel.setBorder(
		new TitledBorder(null, "Session", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(59, 59, 59)));

	lblUnity = new JLabel("Measurement unity");

	comboBoxMeasurementUnity = new JComboBox<>();

	lblMinimumNumberOf = new JLabel("Minimum number of developers needed");

	textFieldNumberOfDevelopersNeeded = new JTextField();
	textFieldNumberOfDevelopersNeeded.setText("1");
	textFieldNumberOfDevelopersNeeded.setColumns(10);
	GroupLayout gl_panel = new GroupLayout(panel);
	gl_panel.setHorizontalGroup(
		gl_panel.createParallelGroup(Alignment.LEADING)
			.addGroup(gl_panel.createSequentialGroup()
				.addGap(6)
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
					.addComponent(lblSession)
					.addGroup(gl_panel.createSequentialGroup()
						.addComponent(lblBestFitProbbility, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
						.addGap(65)
						.addComponent(comboBoxDistribution, GroupLayout.PREFERRED_SIZE, 186, GroupLayout.PREFERRED_SIZE))
					.addComponent(scrollPaneParameters, GroupLayout.PREFERRED_SIZE, 426, GroupLayout.PREFERRED_SIZE)
					.addGroup(gl_panel.createSequentialGroup()
						.addComponent(lblUnity)
						.addGap(136)
						.addComponent(comboBoxMeasurementUnity, GroupLayout.PREFERRED_SIZE, 186, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_panel.createSequentialGroup()
						.addComponent(lblDurationmean)
						.addGap(10)
						.addComponent(textFieldDurationMean, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
						.addGap(40)
						.addComponent(lblDurationstdDeviation)
						.addGap(10)
						.addComponent(textFieldDurationStdDeviation, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_panel.createSequentialGroup()
						.addComponent(lblMinimumNumberOf)
						.addGap(21)
						.addComponent(textFieldNumberOfDevelopersNeeded, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))))
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
					.addComponent(comboBoxDistribution, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(6)
				.addComponent(scrollPaneParameters, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
				.addGap(18)
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel.createSequentialGroup()
						.addGap(5)
						.addComponent(lblUnity))
					.addComponent(comboBoxMeasurementUnity, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(7)
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel.createSequentialGroup()
						.addGap(3)
						.addComponent(lblDurationmean))
					.addComponent(textFieldDurationMean, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGroup(gl_panel.createSequentialGroup()
						.addGap(3)
						.addComponent(lblDurationstdDeviation))
					.addComponent(textFieldDurationStdDeviation, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(12)
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel.createSequentialGroup()
						.addGap(6)
						.addComponent(lblMinimumNumberOf))
					.addComponent(textFieldNumberOfDevelopersNeeded, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
	);
	panel.setLayout(gl_panel);

	FocusListener focusListener = new FocusListener() {

	    @Override
	    public void focusLost(FocusEvent e) {
		repositoryViewPanel.setMessagem("");
	    }

	    @Override
	    public void focusGained(FocusEvent e) {
		repositoryViewPanel.setMessagem("Não existe dados no servidor para " + title);
	    }
	};
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

    public String getMeasurementUnity() {
	return (String) comboBoxMeasurementUnity.getSelectedItem();
    }

    public void setMeasurementUnity(List<String> list) {
	DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(list.toArray(new String[list.size()]));
	comboBoxMeasurementUnity.setModel(model);
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
