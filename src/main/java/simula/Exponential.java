package simula;

import org.apache.commons.math3.distribution.*;

public class Exponential extends Distribution {

	private double mean;
	private  AbstractRealDistribution exponential;
	
	public Exponential(Sample s, double mean) {
		super(s);
		this.mean = mean;
		 
		this.exponential = new ExponentialDistribution(mean);
	}

	@Override
	public double Draw() {
		 return exponential.sample();
	}
	
	public double getMean() {
		return mean;
	}

	private void setMean(double mean) {
		this.mean = mean;
	}

	public static void main(String[] args) {
		Exponential exp = new Exponential(null, 2.61);
		for (int i = 1; i <=100; i++)
			System.out.println(exp.Draw());
	}
}
