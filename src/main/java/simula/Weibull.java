package simula;

import org.apache.commons.math3.distribution.*;

public class Weibull extends Distribution {

	private double scale, shape;
	private  AbstractRealDistribution weibull;
	
	public Weibull(Sample s, double alpha, double beta) {
		super(s);
		this.setScale(alpha);
		this.setShape(beta);
		this.weibull = new WeibullDistribution(alpha, beta);
	}

	@Override
	public double Draw() {
		 return weibull.sample();
	}
	
	public double getScale() {
		return scale;
	}

	private void setScale(double scale) {
		this.scale = scale;
	}

	public double getShape() {
		return shape;
	}

	public void setShape(double shape) {
		this.shape = shape;
	}
	
	public static void main(String[] args) {
		Weibull wb = new Weibull(null, 0.22, 0.36);
		for (int i = 1; i <=100; i++)
			System.out.println(wb.Draw());
	}

}
