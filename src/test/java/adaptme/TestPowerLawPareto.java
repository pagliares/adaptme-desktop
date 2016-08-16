package adaptme;

import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.apache.commons.math3.distribution.LogNormalDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.ParetoDistribution;
import org.apache.commons.math3.ml.neuralnet.sofm.util.ExponentialDecayFunction;

public class TestPowerLawPareto {

	public static void main(String[] args) {
		// User stories with pareto
//		for (int i=0; i< 20; i++) {
//		ParetoDistribution pd = new ParetoDistribution(Math.exp(54.82), 15.31);
//		System.out.println("Sample power law with  pareto..:  " + pd.sample());
//		int a = (int)pd.sample();
//		System.out.println(a);
//		System.out.println("# Days..: "  +  (a * 27 ) );
//		}
//		 
//		
//		// User stories com Lognormal
//		LogNormalDistribution lg1 = new LogNormalDistribution(Math.exp(54.82), -15.31);
//		System.out.println("Sample power law with  log normal ..:  " + lg1.sample());
//		
		// User stories com exponential distribution
//		double totaldias = 0;
//		for (int i=0; i< 20; i++) {
//			ExponentialDistribution lg1 = new ExponentialDistribution(Math.exp(54.82), -15.31);
//			double sample = (int)lg1.sample();
//			
//			totaldias = totaldias+sample;
//			System.out.println("Sample power law with  exponential distribution representing user stories ..:  " + sample);
//	
//		}
//		System.out.println("number of days " + totaldias);
//		// days
//		LogNormalDistribution lg = new LogNormalDistribution(4.10, 0.21);
//		System.out.println("Sample form log normal representing days..:  " + lg.sample());
//		
//		NormalDistribution nd = new NormalDistribution(31.37, 1.75);
//		System.out.println("Sample form log normal representing Defects/Kloc..:  " + nd.sample());
//		
//		NormalDistribution nd1 = new NormalDistribution(9.85, 1.53);
//		System.out.println("Sample form log normal representing KLocs..:  " + nd1.sample());
//		for (int i=1; i<31; i++)
//		System.out.println(i + " " + Math.exp(54.82) * Math.pow(i, -15.31)); 
		
//		// Us estimation
		LogNormalDistribution lg = new LogNormalDistribution(15, 12);
		double est = lg.sample();
		System.out.println("Sample form log normal representing days..:  " + est);

	}
	
	public static double calculatePowerLaw(double k, double alpha) {
		return  Math.exp(54.82) * Math.pow(29, -15.31);
	}

}
