package simulator.utils;

import executive.sample.Sample;
import simulator.utils.enums.UserStoryPriority;

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

    public static void setSeed(long seed) {
	System.out.println("seed: " + seed);
	sample.setSeed(seed);
    }

    public static int getDevelopmentSessionSize(double[] probabilities) {
	double[] values = { 0.1, 0.2, 0.3, 0.4, 0.5 };

	double randomDouble = sample.getRandomDouble();
	double accumulatedProbabilite = 0;
	int index = 0;
	for (double probabilite : probabilities) {
	    accumulatedProbabilite += probabilite;
	    if (randomDouble > accumulatedProbabilite) {
		index++;
	    } else {
		break;
	    }
	}
	return (int) (values[index] * Constants.WORK_DAY_DURATION_MINUTES);
    }

    public static double getUserStoryPointsDone(double pointsDonePerMinute, long timeOfDevelopment) {
	return pointsDonePerMinute * timeOfDevelopment;
    }

    public static double getUserStoryEstimatedPoints(double mean, double stddev) {
	return sample.getSampleFromLogNormalDistribution(mean, stddev);
    }

    public static double getUserStoryEstimtedError(double mean, double stddev) {
	return sample.getSampleFromLogNormalDistribution(mean, stddev);
    }

    public static int getRandomSkill() {
	return (int) sample.getSampleFromUniformDistribution(1, 4);
    }

    public static double getDeveloperInitialVelocity(double mean, double stddev) {
	return sample.getSampleFromLogNormalDistribution(mean, stddev);
    }

    public static int getMaximumRandomSkill() {
	return (int) sample.getSampleFromUniformDistribution(7, 11);
    }

    public static double getClassesDeveloped(int timeOfDevelopment, double mean, double stddev) {
	return sample.getSampleFromLogNormalDistribution(mean, stddev)
		* ((double) timeOfDevelopment / Constants.WORK_DAY_DURATION_MINUTES);
    }

    public static double getMethodsDeveloped(int timeOfDevelopment, double mean, double stddev) {
	return sample.getSampleFromLogNormalDistribution(mean, stddev)
		* ((double) timeOfDevelopment / Constants.WORK_DAY_DURATION_MINUTES);
    }

    public static double getLinesOfCodeDeveloped(int timeOfDevelopment, double mean, double stddev) {
	return sample.getSampleFromLogNormalDistribution(mean, stddev)
		* ((double) timeOfDevelopment / Constants.WORK_DAY_DURATION_MINUTES);
    }

    public static UserStoryPriority getUserStoryPriority(double[] prioritiesProbability) {
	double priority = sample.getRandomDouble();
	if (priority < prioritiesProbability[0]) {
	    return UserStoryPriority.MUST;
	} else if (priority < prioritiesProbability[0] + prioritiesProbability[1]) {
	    return UserStoryPriority.SHOULD;
	} else {
	    return UserStoryPriority.COULD;
	}
    }

    private static boolean useSoloProgramming = true;

    public static boolean usePairProgramming(int pairProgrammingAdoption) {
	int value = (int) sample.getSampleFromUniformDistribution(0, 100);
	if (value < pairProgrammingAdoption) {
	    useSoloProgramming = false;
	    return true;
	} else {
	    useSoloProgramming = true;
	    return false;
	}
    }

    public static boolean useSoloProgramming() {
	return useSoloProgramming;
    }

    private static boolean useTestAfter = true;

    public static boolean useTestFirst(int tddAdoption) {
	int value = (int) sample.getSampleFromUniformDistribution(0, 100);
	if (value < tddAdoption) {
	    useTestAfter = false;
	    return true;
	} else {
	    useTestAfter = true;
	    return false;
	}
    }

    public static boolean useTestAfter() {
	return useTestAfter;
    }

    public static int nextInt(int numberOfFinishedIterations) {
	return sample.nextInt(numberOfFinishedIterations);
    }

    public static boolean useSoloTestFirst(int soloTestFirstAdoption) {
	int value = (int) sample.getSampleFromUniformDistribution(0, 100);
	if (value < soloTestFirstAdoption) {
	    return true;
	} else {
	    return false;
	}
    }

    public static boolean useSoloTestAfter(int soloTestAfterAdoption) {
	int value = (int) sample.getSampleFromUniformDistribution(0, 100);
	if (value < soloTestAfterAdoption) {
	    return true;
	} else {
	    return false;
	}
    }

    public static boolean usePairTestFirst(int pairTestFirstAdoption) {
	int value = (int) sample.getSampleFromUniformDistribution(0, 100);
	if (value < pairTestFirstAdoption) {
	    return true;
	} else {
	    return false;
	}
    }

    public static boolean usePairTestAfter(int pairTestAfterAdoption) {
	int value = (int) sample.getSampleFromUniformDistribution(0, 100);
	if (value < pairTestAfterAdoption) {
	    return true;
	} else {
	    return false;
	}
    }

    public static boolean useRefectoring(int refactiongAdoption) {
	int value = (int) sample.getSampleFromUniformDistribution(0, 100);
	if (value < refactiongAdoption) {
	    return true;
	} else {
	    return false;
	}
    }
}
