import org.apache.commons.math3.distribution.PoissonDistribution;

public class Poisson {

	public static void main(String[] args) {
		PoissonDistribution p = new PoissonDistribution(5.0);
		
		for (int i = 1; i <= 801; i++)
			System.out.println(p.sample());
		
	}

}
