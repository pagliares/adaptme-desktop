package adaptme.base.persist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.commons.math3.distribution.BinomialDistribution;
import org.apache.commons.math3.distribution.IntegerDistribution;
import org.apache.commons.math3.distribution.LogNormalDistribution;
import org.apache.commons.math3.distribution.MultivariateRealDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.PoissonDistribution;
import org.apache.commons.math3.distribution.RealDistribution;
import org.apache.commons.math3.distribution.TriangularDistribution;
import org.apache.commons.math3.distribution.UniformIntegerDistribution;

import model.spem.measurement.DurationMeasurement;
import model.spem.measurement.Measurement;
import model.spem.measurement.RoleMeasurement;
import model.spem.measurement.WorkProductMeasurement;
import model.spem.util.IntegerDistributionEnum;
import model.spem.util.TimeEnum;

public class MeasurementFactory {
	
	public static List<Measurement> createNormalDistribution(double mean, double sd, int size) {
		return null;
	}
	
	
	public static List<DurationMeasurement> createIntegerDistributionForDuration(IntegerDistribution integerDistribution, int size, TimeEnum scale) {
		
		List<DurationMeasurement> list = new ArrayList<>();
		DurationMeasurement measurement;
		for (int i=0; i < size; i++) {
			measurement = new DurationMeasurement();
			measurement.setName("Duration measurement");
			measurement.setValue(integerDistribution.sample());
			measurement.setScale(scale);
			list.add(measurement);
		}
		return list;
	}
	
    public static List<DurationMeasurement> createRealDistributionForDuration(RealDistribution realDistribution, int size, TimeEnum scale) {
    	
		List<DurationMeasurement> list = new ArrayList<>();
		DurationMeasurement measurement;
		for (int i=0; i < size; i++) {
			double sample = realDistribution.sample();
			sample = Math.round(sample*100)/100.0d;
			measurement = new DurationMeasurement();
			measurement.setName("Duration measurement");
			measurement.setValue(sample);
			measurement.setScale(scale);
			list.add(measurement);
		}
		return list;
	}
    
    public static List<RoleMeasurement> createIntegerDistributionForRoleMeasurement(IntegerDistribution integerDistribution, int size, TimeEnum experienceScale) {
		Random random = new Random();
		List<RoleMeasurement> list = new ArrayList<>();
		RoleMeasurement measurement;
		for (int i=0; i < size; i++) {
			measurement = new RoleMeasurement();
			measurement.setExperience(integerDistribution.sample());
			measurement.setExperienceScale(experienceScale);
			measurement.setSkill((int)(Math.random() * 10 + 1));
			list.add(measurement);
		}
		return list;
	}
    
    public static List<RoleMeasurement> createRealDistributionForRoleMeasurement(RealDistribution realDistribution, int size, TimeEnum experienceScale) {
		Random random = new Random();
		List<RoleMeasurement> list = new ArrayList<>();
		RoleMeasurement measurement;
		for (int i=0; i < size; i++) {
			measurement = new RoleMeasurement();
			measurement.setExperience((int)realDistribution.sample());
			measurement.setExperienceScale(experienceScale);
			measurement.setSkill((int)(Math.random() * 10 + 1));
			list.add(measurement);
		}
		return list;
	}
	
    public static List<WorkProductMeasurement> createIntegerDistributionForWorkProductMeasurement(IntegerDistribution integerDistribution, int size) {
		List<WorkProductMeasurement> list = new ArrayList<>();
		WorkProductMeasurement measurement;
		for (int i=0; i < size; i++) {
			measurement = new WorkProductMeasurement();
			measurement.setName("work product measurement");
			measurement.setQuantity(integerDistribution.sample());
			measurement.setQuantityScale("quantity scale"); // TODO - receber como parametro?
			measurement.setSize(integerDistribution.sample()); // and when quantity has one distribution and size another? the same for the other measurements
			measurement.setSizeScale("size scale");	
			list.add(measurement);
		}
		return list;
	}
    
    public static List<WorkProductMeasurement> createRealDistributionForWorkProductMeasurement(RealDistribution realDistribution, int size) {
		List<WorkProductMeasurement> list = new ArrayList<>();
		WorkProductMeasurement measurement;
		for (int i=0; i < size; i++) {
			measurement = new WorkProductMeasurement();
			measurement.setName("work product measurement");
			measurement.setQuantity((int)realDistribution.sample());//  TODO verify casting
			measurement.setQuantityScale("quantity scale"); // TODO - receber como parametro?
			measurement.setSize(realDistribution.sample()); // and when quantity has one distribution and size another? the same for the other measurements
			measurement.setSizeScale("size scale");	
			list.add(measurement);
		}
		return list;
	}
	
	 

	public static void main(String[] args) {
		
		System.out.println("Integer Distribution: Poisson");
		IntegerDistribution poissonDistribution = new PoissonDistribution(10.0);
		
		List<DurationMeasurement> result = createIntegerDistributionForDuration(poissonDistribution, 50, TimeEnum.MINUTES);
		
		for (int i = 1; i <= result.size(); i++) {
			System.out.println(i + " " +  result.get(i-1));
		}
		System.out.println();
		
		System.out.println("Real Distribution: Lognormal");
		RealDistribution logNormal = new LogNormalDistribution(15, 12);
        result = createRealDistributionForDuration(logNormal, 50, TimeEnum.MINUTES);
		
		for (int i = 1; i <= result.size(); i++) {
			System.out.println(i + " " +  result.get(i-1));
		}
		
		System.out.println();

		System.out.println("Integer Distribution: Uniform Integer ");
		IntegerDistribution uniformIntegerDistribution = new UniformIntegerDistribution(0, 200);
        List<RoleMeasurement> listRoleMeasurement = createIntegerDistributionForRoleMeasurement(uniformIntegerDistribution, 50, TimeEnum.DAYS);
		
		for (int i = 1; i <= result.size(); i++) {
			System.out.println(i + " " +  listRoleMeasurement.get(i-1));
		}
		System.out.println();
		
		System.out.println("Real Distribution: Normal");
		RealDistribution normalDistribution = new NormalDistribution(30, 6);
        result = createRealDistributionForDuration(normalDistribution, 50, TimeEnum.MINUTES);
		
		for (int i = 1; i <= result.size(); i++) {
			System.out.println(i + " " +  result.get(i-1));
		}
		
		System.out.println("Integer Distribution: Binomial ");
		IntegerDistribution binomialDistribution = new BinomialDistribution(50, 0.50);
        List<WorkProductMeasurement> listWorkProductMeasurement = createIntegerDistributionForWorkProductMeasurement(uniformIntegerDistribution, 50);
		
		for (int i = 1; i <= result.size(); i++) {
			System.out.println(i + " " +  listWorkProductMeasurement.get(i-1));
		}
		System.out.println();
		

		System.out.println("Real Distribution: Triangular ");
		RealDistribution triangularDistribution = new TriangularDistribution(50, 90, 120);
        listWorkProductMeasurement = createRealDistributionForWorkProductMeasurement(triangularDistribution, 50);
		
		for (int i = 1; i <= result.size(); i++) {
			System.out.println(i + " " +  listWorkProductMeasurement.get(i-1));
		}
		System.out.println();

	}

}
