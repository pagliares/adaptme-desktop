package adaptme.ui.dynamic;

import java.awt.Font;
import java.util.List;
import java.util.Random;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;

import model.spem.derived.BestFitDistribution;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RepositoryViewPanel {

	private JPanel panel;

	private JLabel lblSampleSize;
	private JTextField textFieldSampleSize;
	private JLabel lblDurationmean;
	private JTextField textFieldDurationMean;
	private JLabel lblDurationstdDeviation;
	private JTextField textFieldDurationStdDeviation;
	private JLabel lblBestFitProbability;
	private JComboBox<String> comboBoxBestDistribution;
	private JLabel lblBusinesDays;
	private JLabel label;
	private JLabel lblProcessElement;
	private JButton btnUseRepositoryDistribution;
	private JPanel panelGraphic;
	private JLabel lblMessagem;
	private JTextField textFieldNumberOfBins;

	public RepositoryViewPanel() {

		panel = new JPanel();

		panel.setBorder(new TitledBorder(null, "Repository View", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		lblSampleSize = new JLabel("Sample size");

		textFieldSampleSize = new JTextField();
		textFieldSampleSize.setText("0");
		textFieldSampleSize.setColumns(10);

		lblDurationmean = new JLabel("Duration (mean)");

		textFieldDurationMean = new JTextField();
		textFieldDurationMean.setText("0");
		textFieldDurationMean.setColumns(10);

		lblDurationstdDeviation = new JLabel("Duration (Std. Deviation)");

		textFieldDurationStdDeviation = new JTextField();
		textFieldDurationStdDeviation.setText("0");
		textFieldDurationStdDeviation.setColumns(10);

		lblBestFitProbability = new JLabel("Best fit probability distribution");

		comboBoxBestDistribution = new JComboBox<>();
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(
				BestFitDistribution.getList().toArray(new String[BestFitDistribution.getList().size()]));
		comboBoxBestDistribution.setModel(model);

		lblBusinesDays = new JLabel("Business days");

		label = new JLabel("Business days");

		lblProcessElement = new JLabel("Process element: ");
		lblProcessElement.setFont(new Font("Tahoma", Font.BOLD, 13));

		btnUseRepositoryDistribution = new JButton("Use repository distribution");

		panelGraphic = new JPanel();

		lblMessagem = new JLabel("");
		lblMessagem.setFont(new Font("SansSerif", Font.BOLD, 14));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Histogram Config", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelGraphic.setLayout(new BorderLayout(0, 0));
		panelGraphic.add(getHistogram(5), BorderLayout.CENTER);
		
		JLabel lblNumberOfBins = new JLabel("Number of Bins");
		
		textFieldNumberOfBins = new JTextField();
		textFieldNumberOfBins.setText("5");
		textFieldNumberOfBins.setColumns(10);
		
		JButton btnUpdateHistogram = new JButton("Update Histogram");
		btnUpdateHistogram.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelGraphic.removeAll();
				panelGraphic.add(getHistogram(Integer.parseInt(textFieldNumberOfBins.getText())), BorderLayout.CENTER);
				panelGraphic.updateUI();
			}
		});
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNumberOfBins)
					.addPreferredGap(ComponentPlacement.RELATED, 112, Short.MAX_VALUE)
					.addComponent(textFieldNumberOfBins, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
					.addGap(37))
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(116)
					.addComponent(btnUpdateHistogram)
					.addContainerGap(121, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNumberOfBins)
						.addComponent(textFieldNumberOfBins, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
					.addComponent(btnUpdateHistogram)
					.addContainerGap())
		);
		panel_1.setLayout(gl_panel_1);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(4)
					.addComponent(lblProcessElement, GroupLayout.PREFERRED_SIZE, 271, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(4)
					.addComponent(lblSampleSize, GroupLayout.PREFERRED_SIZE, 219, GroupLayout.PREFERRED_SIZE)
					.addGap(49)
					.addComponent(textFieldSampleSize, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(4)
					.addComponent(lblDurationmean, GroupLayout.PREFERRED_SIZE, 219, GroupLayout.PREFERRED_SIZE)
					.addGap(49)
					.addComponent(textFieldDurationMean, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
					.addGap(12)
					.addComponent(label))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(4)
					.addComponent(lblDurationstdDeviation, GroupLayout.PREFERRED_SIZE, 219, GroupLayout.PREFERRED_SIZE)
					.addGap(50)
					.addComponent(textFieldDurationStdDeviation, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(lblBusinesDays))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(4)
					.addComponent(lblBestFitProbability, GroupLayout.PREFERRED_SIZE, 219, GroupLayout.PREFERRED_SIZE)
					.addGap(49)
					.addComponent(comboBoxBestDistribution, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(4)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 422, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(4)
					.addComponent(panelGraphic, GroupLayout.PREFERRED_SIZE, 420, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(4)
					.addComponent(lblMessagem, GroupLayout.PREFERRED_SIZE, 396, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(120)
					.addComponent(btnUseRepositoryDistribution))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(8)
					.addComponent(lblProcessElement)
					.addGap(2)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(11)
							.addComponent(lblSampleSize))
						.addComponent(textFieldSampleSize, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(25)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(4)
							.addComponent(lblDurationmean))
						.addComponent(textFieldDurationMean, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(6)
							.addComponent(label)))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(4)
							.addComponent(lblDurationstdDeviation))
						.addComponent(textFieldDurationStdDeviation, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(6)
							.addComponent(lblBusinesDays)))
					.addGap(12)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(10)
							.addComponent(lblBestFitProbability))
						.addComponent(comboBoxBestDistribution, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(17)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE)
					.addGap(8)
					.addComponent(panelGraphic, GroupLayout.PREFERRED_SIZE, 233, GroupLayout.PREFERRED_SIZE)
					.addGap(12)
					.addComponent(lblMessagem, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
					.addGap(16)
					.addComponent(btnUseRepositoryDistribution))
		);
		panel.setLayout(gl_panel);
	}

	public ChartPanel getHistogram(int bins){
		int arraySize = 1000;
		double[] value = new double[arraySize];
		Random generator = new Random();
		for (int i = 1; i < arraySize; i++) {
			value[i] = generator.nextDouble() * 10;
		}
		HistogramDataset dataset = new HistogramDataset();
		dataset.setType(HistogramType.FREQUENCY);
		dataset.addSeries("Histogram", value, bins);
		String plotTitle = "Histogram";
		String xaxis = "number";
		String yaxis = "value";
		PlotOrientation orientation = PlotOrientation.VERTICAL;
		boolean show = false;
		boolean toolTips = false;
		boolean urls = false;
		JFreeChart chart = ChartFactory.createHistogram(plotTitle, xaxis, yaxis, dataset, orientation, show, toolTips,
				urls);
		ChartPanel chartPanel = new ChartPanel(chart);
		return chartPanel;
	}
	
	public double getSampleSize() {
		return Double.parseDouble(textFieldSampleSize.getText());
	}

	public double getDurationMean() {
		return Double.parseDouble(textFieldDurationMean.getText());
	}

	public double getDurationStdDeviation() {
		return Double.parseDouble(textFieldDurationStdDeviation.getText());
	}

	public void setSampleSize(double sampleSize) {
		textFieldSampleSize.setText("" + sampleSize);
	}

	public void setDurationMean(double durationMean) {
		textFieldDurationMean.setText("" + durationMean);
	}

	public void setDurationStdDeviation(double durationStdDeviation) {
		textFieldDurationStdDeviation.setText("" + durationStdDeviation);
	}

	public String getCBestDistribution() {
		return (String) comboBoxBestDistribution.getSelectedItem();
	}

	public void setComboBoxBestDistribution(List<String> list) {
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(list.toArray(new String[list.size()]));
		comboBoxBestDistribution.setModel(model);
	}

	public void setTitle(String title) {
		lblProcessElement.setText("Process element: " + title);
	}

	public JComponent getPanel() {
		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		return scrollPane;
	}

	public void setMessagem(String string) {
		lblMessagem.setText(string);
	}
}
