// Arquivo Normal.java
// Implementa��o das Classes do Grupo Utilit�rio da Biblioteca de Simula��o JAVA
// 26.Mar.1999	Wladimir

package simula;

import org.apache.commons.math3.distribution.ParetoDistribution;
import org.apache.commons.math3.distribution.ZipfDistribution;

/**
 * Distribuicao Gaussiana
 */
public class PowerLaw extends Distribution
{
	private double scale, shape;
	 

	/**
	 * associa a stream � distribui��o e recebe par�metros.
	 */
	public PowerLaw(Sample s, double scale, double shape){
		super(s); 
		this.scale = scale; 
		this.shape = shape;
	}

	/**
	 * obt�m uma amostra segundo a dada distribui��o.
	 */
	public double Draw()
	{
		ParetoDistribution pareto = new ParetoDistribution((int) scale, shape);
		return (pareto.sample());
	}
	
	public static void main(String[] args){
		PowerLaw pl = new PowerLaw(null, Math.pow(Math.E, 54.82), 15.31);
		System.out.println(pl.Draw());
	}
}
