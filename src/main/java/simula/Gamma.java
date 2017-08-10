package simula;

import org.apache.commons.math3.distribution.*;

public class Gamma extends Distribution {

	private double scale, shape;
	private  AbstractRealDistribution gamma;
	
	public Gamma(Sample s, double scale, double shape) {
		super(s);
		this.scale = scale;
		this.shape = shape;
		
		this.gamma = new GammaDistribution(shape, scale);
	}

	@Override
	public double Draw() {
		 return gamma.sample();
	}
	
	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}

	public double getShape() {
		return shape;
	}

	public void setShape(double shape) {
		this.shape = shape;
	}
	
	public static void main(String[] args) {
		Gamma wb = new Gamma(null, 430, 0.30);
		for (int i = 1; i <=100; i++)
			System.out.println(wb.Draw());
	}

}
