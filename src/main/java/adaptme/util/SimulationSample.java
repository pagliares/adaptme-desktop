package adaptme.util;

import executive.sample.Sample;

public class SimulationSample {

    private static Sample sample;

    private SimulationSample() {
    }

    static {
	long nanoTime = System.nanoTime();
	// System.out.println(nanoTime);
	// long nanoTime = 2035076452152L;
	sample = new Sample(nanoTime);
    }

    public static double nextDouble() {
	return sample.getRandomDouble();
    }

    public static int nextInt(int numberOfFinishedIterations) {
	return sample.nextInt(numberOfFinishedIterations);
    }

    public static void setSeed(long seed) {
	System.out.println("seed: " + seed);
	sample.setSeed(seed);
    }

    public static double getSampleFromLogNormalDistribution(double mean, double stddev) {
	return sample.getSampleFromLogNormalDistribution(mean, stddev);
    }
}
