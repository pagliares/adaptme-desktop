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
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import adaptme.ui.dynamic.UpdatePanel;
import model.spem.MethodContentRepository;
import model.spem.Sample;
import model.spem.derived.BestFitDistribution;
import model.spem.derived.Parameters;
import model.spem.derived.gui.ParametersPanel;

public class InputWorkProductPanel implements UpdatePanel {

    private JLabel lblSizemean;
    private JLabel lblInputWorkProduct;
    private JTextField textFieldSizeMean;
    private JLabel lblSizestdDeviation;
    private JLabel lblBestFitProbability;
    private JComboBox<BestFitDistribution> distributionJComboBox;
    private JTextField textFieldSizeStdDeviation;
    // private RepositoryViewPanel repositoryViewPanel;
    private String title;
    private MethodContentRepository methodContentRepository;
    private JPanel panel = new JPanel();
    private JScrollPane scrollPaneParameters;
    private Parameters parameters;

    public InputWorkProductPanel(
	    MethodContentRepository methodContentRepository) {

	this.methodContentRepository = methodContentRepository;
	// this.repositoryViewPanel = repositoryViewPanel;
	lblSizemean = new JLabel("Size (mean)");

	lblInputWorkProduct = new JLabel("Input work product - ");
	lblInputWorkProduct.setFont(new Font("SansSerif", Font.BOLD, 14));

	textFieldSizeMean = new JTextField();
	textFieldSizeMean.setText("10");
	textFieldSizeMean.setColumns(10);

	lblSizestdDeviation = new JLabel("Size (Std. deviation)");

	textFieldSizeStdDeviation = new JTextField();
	textFieldSizeStdDeviation.setText("0");
	textFieldSizeStdDeviation.setColumns(10);

	lblBestFitProbability = new JLabel("Best fit probability distribution");

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
	scrollPaneParameters.setViewportView(new ParametersPanel(parameters, null).getPanel());
	distributionJComboBox.addItemListener(e -> {
	     
	    parameters = Parameters.createParameter((BestFitDistribution)distributionJComboBox.getSelectedItem());

	    scrollPaneParameters.setViewportView(new ParametersPanel(parameters, null).getPanel());
	    scrollPaneParameters.revalidate();
	    scrollPaneParameters.repaint();
	});
	
	 
	panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Input", TitledBorder.LEADING,
		TitledBorder.TOP, null, new Color(59, 59, 59)));

	GroupLayout gl_panel = new GroupLayout(panel);
	gl_panel.setHorizontalGroup(
		gl_panel.createParallelGroup(Alignment.LEADING)
			.addGroup(gl_panel.createSequentialGroup()
				.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
					.addGroup(Alignment.LEADING,
						gl_panel.createSequentialGroup().addContainerGap().addComponent(
							scrollPaneParameters))
			.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup().addGap(6).addGroup(gl_panel
				.createParallelGroup(Alignment.LEADING)
				.addComponent(lblInputWorkProduct, GroupLayout.PREFERRED_SIZE, 305,
					GroupLayout.PREFERRED_SIZE)
				.addGroup(gl_panel.createSequentialGroup().addComponent(lblBestFitProbability)
					.addGap(68).addComponent(distributionJComboBox, GroupLayout.PREFERRED_SIZE,
						196, GroupLayout.PREFERRED_SIZE))))
			.addGroup(Alignment.LEADING,
				gl_panel.createSequentialGroup().addContainerGap().addComponent(lblSizemean).addGap(28)
					.addComponent(textFieldSizeMean, GroupLayout.PREFERRED_SIZE, 76,
						GroupLayout.PREFERRED_SIZE)
					.addGap(37).addComponent(lblSizestdDeviation).addGap(44)
					.addComponent(textFieldSizeStdDeviation, GroupLayout.PREFERRED_SIZE, 66,
						GroupLayout.PREFERRED_SIZE)))
				.addContainerGap(22, Short.MAX_VALUE)));
	gl_panel.setVerticalGroup(
		gl_panel.createParallelGroup(Alignment.LEADING)
			.addGroup(
				gl_panel.createSequentialGroup().addGap(23)
					.addComponent(lblInputWorkProduct, GroupLayout.PREFERRED_SIZE, 16,
						GroupLayout.PREFERRED_SIZE)
				.addGap(22)
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel.createSequentialGroup().addGap(5)
						.addComponent(lblBestFitProbability))
				.addComponent(distributionJComboBox, GroupLayout.PREFERRED_SIZE,
					GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(18)
		.addComponent(scrollPaneParameters, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
		.addGap(18)
		.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
			.addGroup(gl_panel.createSequentialGroup().addGap(6).addComponent(lblSizemean))
			.addComponent(textFieldSizeMean, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
				GroupLayout.PREFERRED_SIZE)
			.addGroup(gl_panel.createSequentialGroup().addGap(6).addComponent(lblSizestdDeviation))
			.addComponent(textFieldSizeStdDeviation, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
				GroupLayout.PREFERRED_SIZE)).addGap(92)));
	panel.setLayout(gl_panel);
    }

    public double getSizeMean() {
	return Double.parseDouble(textFieldSizeMean.getText());
    }

    public double getSizeStdDeviation() {
	return Double.parseDouble(textFieldSizeStdDeviation.getText());
    }

    public void setSizeMean(double sizeMean) {
	textFieldSizeMean.setText("" + sizeMean);
    }

    public void setSizeStdDeviation(double sizeStdDeviation) {
	textFieldSizeStdDeviation.setText("" + sizeStdDeviation);
    }

    public String getSizeDistribuiton() {
	return (String) distributionJComboBox.getSelectedItem();
    }

   
    public void setInputWorkProductLabel(String label) {
	title = label;
	lblInputWorkProduct.setText("Input work product - " + label);
    }

    @Override
    public JPanel getPanel() {
	return panel;
    }

    @Override
    public void updateContent() {
	// Parameters parameters = new Parameters();
	// parameters.setMean(getSizeMean());
	// parameters.setStandardDeviation(getSizeStdDeviation());
	Sample sample = new Sample();
	sample.setDistribution(BestFitDistribution.getDistributionByName(getSizeDistribuiton()));
	// sample.setParameters(parameters);
	methodContentRepository.setSample(sample);
    }
}
