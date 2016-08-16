package adaptme.ui.window.perspective;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

 
public class WelchChartPanel {
 
	public JPanel createChartPanel() {
		String chartTitle = "Warm um period";
		String xAxisLabel = "Time";
		String yAxisLabel = "# User stories";

		XYDataset dataset = createDataset();

		JFreeChart chart = ChartFactory.createXYLineChart(chartTitle, xAxisLabel, yAxisLabel, dataset);

		return new ChartPanel(chart);
	}
 
	private XYDataset createDataset() {
		XYSeriesCollection dataset = new XYSeriesCollection();
//		XYSeries series1 = new XYSeries("Object 1");
 		XYSeries series2 = new XYSeries("User stories production");
//		XYSeries series3 = new XYSeries("Object 3");

//		series1.add(1.0, 2.0);
//		series1.add(2.0, 3.0);
//		series1.add(3.0, 2.5);
//		series1.add(3.5, 2.8);
//		series1.add(4.2, 6.0);

		series2.add(50, 4);
		series2.add(55, 6);
		series2.add(60, 11);
		series2.add(65, 12);
		series2.add(70, 12);

//		series3.add(1.2, 4.0);
//		series3.add(2.5, 4.4);
//		series3.add(3.8, 4.2);
//		series3.add(4.3, 3.8);
//		series3.add(4.5, 4.0);

//		dataset.addSeries(series1);
		dataset.addSeries(series2);
//		dataset.addSeries(series3);

		return dataset;
	}

    
}