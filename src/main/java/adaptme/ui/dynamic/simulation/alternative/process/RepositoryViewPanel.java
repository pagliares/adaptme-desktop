package adaptme.ui.dynamic.simulation.alternative.process;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.TitledBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;

import adaptme.ui.dynamic.UpdatePanel;
import model.spem.ProcessContentRepository;
import model.spem.derived.BestFitDistribution;

public class RepositoryViewPanel implements UpdatePanel {

	private JPanel panel;

	private JLabel lblSampleSize;
	private JLabel lblProcessElement;
	private JPanel histogramChartPanel;
	private JLabel lblMessagem;
	private JTextField textFieldNumberOfBins;
	private JLabel sampleSizeValueLabel;
	private String title;
	
    private ProcessContentRepository processContentRepository;


	public RepositoryViewPanel(ProcessContentRepository processContentRepository) {

		panel = new JPanel();

		panel.setBorder(null);

		lblSampleSize = new JLabel("Sample size :");

		lblProcessElement = new JLabel(title);
		lblProcessElement.setFont(new Font("Tahoma", Font.BOLD, 13));

		histogramChartPanel = new JPanel();

		lblMessagem = new JLabel("");
		lblMessagem.setFont(new Font("SansSerif", Font.BOLD, 14));
		
		JPanel histogramConfigPanel = new JPanel();
		histogramConfigPanel.setBorder(new TitledBorder(null, "Histogram Config", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		histogramChartPanel.setLayout(new BorderLayout(0, 0));
		histogramChartPanel.add(getHistogram(5), BorderLayout.CENTER);
		
		JLabel lblNumberOfBins = new JLabel("Number of Bins");
		
		textFieldNumberOfBins = new JTextField();
		textFieldNumberOfBins.setText("5");
		textFieldNumberOfBins.setColumns(10);
		
		JButton btnUpdateHistogram = new JButton("Update Histogram");
		btnUpdateHistogram.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				histogramChartPanel.removeAll();
				histogramChartPanel.add(getHistogram(Integer.parseInt(textFieldNumberOfBins.getText())), BorderLayout.CENTER);
				histogramChartPanel.updateUI();
			}
		});
		GroupLayout gl_histogramConfigPanel = new GroupLayout(histogramConfigPanel);
		gl_histogramConfigPanel.setHorizontalGroup(
			gl_histogramConfigPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_histogramConfigPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNumberOfBins)
					.addGap(18)
					.addComponent(textFieldNumberOfBins, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 114, Short.MAX_VALUE)
					.addComponent(btnUpdateHistogram)
					.addGap(96))
		);
		gl_histogramConfigPanel.setVerticalGroup(
			gl_histogramConfigPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_histogramConfigPanel.createSequentialGroup()
					.addContainerGap(19, Short.MAX_VALUE)
					.addGroup(gl_histogramConfigPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNumberOfBins)
						.addComponent(textFieldNumberOfBins, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnUpdateHistogram))
					.addGap(53))
		);
		histogramConfigPanel.setLayout(gl_histogramConfigPanel);
		
		sampleSizeValueLabel = new JLabel("10");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(4)
							.addComponent(lblSampleSize, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(sampleSizeValueLabel))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(4)
							.addComponent(lblMessagem, GroupLayout.PREFERRED_SIZE, 396, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(4)
							.addComponent(lblProcessElement, GroupLayout.PREFERRED_SIZE, 214, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(4)
							.addComponent(histogramChartPanel, GroupLayout.PREFERRED_SIZE, 420, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(histogramConfigPanel, GroupLayout.PREFERRED_SIZE, 397, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(43, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(8)
					.addComponent(lblProcessElement)
					.addGap(13)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSampleSize)
						.addComponent(sampleSizeValueLabel))
					.addGap(18)
					.addComponent(histogramConfigPanel, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(histogramChartPanel, GroupLayout.PREFERRED_SIZE, 304, GroupLayout.PREFERRED_SIZE)
					.addGap(90)
					.addComponent(lblMessagem, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
					.addGap(45))
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

	@Override
	public void updateContent() {
		// TODO Auto-generated method stub
		
	}

	public String getTitle() {
		return title;
	}
}
