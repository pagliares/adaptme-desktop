// Arquivo NegExp.java
// Implementa��o das Classes do Grupo Utilit�rio da Biblioteca de Simula��o JAVA
// 30.Abr.1999	Wladimir

package simula;

/**
 * Distribuicao exponencial
 */
public class NegExp extends Distribution
{
	private double mean;

	/**
	 * associa a stream � distribui��o e recebe par�metros.
	 */
	public NegExp(Sample s, double Mean)
	{super(s); mean = Mean;}

	/**
	 * obt�m uma amostra segundo a dada distribui��o.
	 */
	public double Draw(){return mean * stream.NegExp();}
	
	public static void main (String [] args) {
		Sample s = new Sample();
		NegExp ne = new NegExp(s, 800);
		for (int i = 0; i < 28; i++)
		System.out.print(ne.Draw()/8 + ",");
	}
	
}