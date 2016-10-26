// Arquivo Normal.java
// Implementa��o das Classes do Grupo Utilit�rio da Biblioteca de Simula��o JAVA
// 26.Mar.1999	Wladimir

package simula;

import java.util.Random;

import org.apache.commons.math3.distribution.LogNormalDistribution;

/**
 * Distribuicao Gaussiana
 */
public class LogNormal extends Distribution
{
	private double scale, shape;

	/**
	 * associa a stream � distribui��o e recebe par�metros.
	 */
	public LogNormal(Sample s, double scale, double shape){
		super(s); 
		this.scale = scale; 
		this.shape = shape;
	}

	/**
	 * obt�m uma amostra segundo a dada distribui��o.
	 * ESTOU DELEGANDO PARA APACHE COMMONS MATH  PAGLIARES
	 */
	public double Draw(){
//		System.out.println("Scale...:" + scale);
//		System.out.println("Shape...:"  + shape);
		double meanlog = Math.log(scale) - 0.5 * Math.log(1 + Math.pow(shape/scale,2)); 
//		System.out.println(meanlog);
		double sdlog = Math.sqrt(Math.log(1 + Math.pow(shape/scale, 2)));
		LogNormalDistribution log = new LogNormalDistribution(meanlog, sdlog);
		return log.sample();	 
	}
	
	public double getSampleFromLogNormalDistribution(double mean, double stddev) {
		Random random = new Random(System.currentTimeMillis());
		double varx = Math.pow(stddev, 2);
		double ess = Math.log(1.0 + (varx / Math.pow(mean, 2)));
		double mu = Math.log(mean) - (0.5 * Math.pow(ess, 2));
		return Math.pow(2.71828, (mu + (ess * random.nextGaussian())));
	 }
	
	public static void main(String[] args) {
		double acc1 = 0.0;
		double acc2 = 0.0;
		LogNormal ln = new LogNormal(null, 15, 12);
		for(int i = 0; i< 29; i++) {
			acc1+=ln.Draw();
			acc2+=ln.getSampleFromLogNormalDistribution(15, 12);
		}
		System.out.println("Math commons..: " + acc1);
		System.out.println("Na mao..: " + acc2);

	}
}
