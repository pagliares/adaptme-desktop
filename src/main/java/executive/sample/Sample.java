//Sample.java
//Simple random sampling for discrete event simulation.
//M.Pidd

package executive.sample;

import java.util.Random;

/**
 * Used to create streamable random numbers and associated routines to sample
 * from probability distributions.
 */
public class Sample {

    Random random;

    public Sample(long seed) {
	random = new Random(seed);
    }

    public void setSeed(long seed) {
	random.setSeed(seed);
    }

    public double getSampleFromNegativeExponentialDistribution(int mean) {
	return getSampleFromNegativeExponentialDistribution((double) mean);
    }

    public double getSampleFromNegativeExponentialDistribution(float mean) {
	return getSampleFromNegativeExponentialDistribution((double) mean);
    }

    public double getSampleFromNegativeExponentialDistribution(double mean) {
	double nE = -mean * Math.log(random.nextDouble());
	return nE;
    }

    /**
     * No checks to see if min <= max.
     */
    public double getSampleFromUniformDistribution(int min, int max) {
	return getSampleFromUniformDistribution((double) min, (double) max);
    }

    /**
     * No checks to see if min <= max.
     */
    public double getSampleFromUniformDistribution(float min, float max) {
	return getSampleFromUniformDistribution((double) min, (double) max);
    }

    /**
     * No checks to see if min <= max.
     */
    public double getSampleFromUniformDistribution(double min, double max) {
	double u = min + (max - min) * random.nextDouble();
	return u;
    }

    public double getSampleFromLogNormalDistribution(int mean, int stddev) {
	return getSampleFromLogNormalDistribution(mean, stddev);
    }

    public double getSampleFromLogNormalDistribution(float mean, float stddev) {
	return getSampleFromLogNormalDistribution(mean, stddev);
    }

    public double getSampleFromLogNormalDistribution(double mean, double stddev) {
	double varx = Math.pow(stddev, 2);
	double ess = Math.log(1.0 + (varx / Math.pow(mean, 2)));
	double mu = Math.log(mean) - (0.5 * Math.pow(ess, 2));
	return Math.pow(2.71828, (mu + (ess * random.nextGaussian())));
    }

    public double getRandomDouble() {
	return random.nextDouble();
    }

    /**
     * No checks to see if min <= max.
     */
    public double getRandomDouble(double min, double max) {
	double value = 0;
	do {
	    value = random.nextDouble();
	    value += min;
	    value = (double) Math.round(value * 100) / 100;
	} while (value >= max);
	return value;
    }

    public int nextInt(int bound) {
	return random.nextInt(bound);
    }
}
