package adaptme;

import java.util.Random;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;

public class HistogramExample extends JFrame {

	public HistogramExample() {
		int arraySize = 1000;
		double[] value = new double[arraySize];
		Random generator = new Random();
		for (int i = 1; i < arraySize; i++) {
			value[i] = generator.nextDouble() * 10;
		}
		int number = 10;
		HistogramDataset dataset = new HistogramDataset();
		dataset.setType(HistogramType.FREQUENCY);
		dataset.addSeries("Histogram", value, number);
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
		// default size
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		// add it to our application
		setContentPane(chartPanel);
		pack();
		setVisible(true);
	}

	public static void main(String[] args) {
		new HistogramExample();
		// try {
		// ChartUtilities.saveChartAsPNG(new File("histogram.PNG"), chart,
		// width, height);
		// } catch (IOException e) {
		// }
	}
}