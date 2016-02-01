package simulator.utils;

/***********************************************************
 * Introduction to Computers and Programming (Fall 2009) * Professor Evan Korth
 * * * File Name: Deviation.java * PIN: K0002F09084 * Description: Calculating
 * Standard Deviation * * Focus: * a. Calculating Standard Deviation *
 ***********************************************************/

// Beginning of class Deviation
public class Summary {

    public static double[][] findSummary(double[][] values) {
	double[][] summary = new double[values.length][4];
	for (int i = 0; i < summary.length; i++) {
	    summary[i][0] = findMean(values[i]);
	    summary[i][1] = findDeviation(values[i]);
	    summary[i][2] = findMin(values[i]);
	    summary[i][3] = fintMax(values[i]);
	}
	return summary;
    }

    public static double findDeviation(double[] nums) {
	double mean = findMean(nums);
	double squareSum = 0;

	for (int i = 0; i < nums.length; i++) {
	    squareSum += Math.pow(nums[i] - mean, 2);
	}

	return Math.sqrt((squareSum) / (nums.length - 1));
    }

    public static double findDeviation(int[] nums) {
	double mean = findMean(nums);
	double squareSum = 0;

	for (int i = 0; i < nums.length; i++) {
	    squareSum += Math.pow(nums[i] - mean, 2);
	}

	return Math.sqrt((squareSum) / (nums.length - 1));
    }

    public static double findMean(double[] nums) {
	double sum = 0;

	for (int i = 0; i < nums.length; i++) {
	    sum += nums[i];
	}

	return sum / nums.length;
    }

    public static double findMean(int[] nums) {
	double sum = 0;

	for (int i = 0; i < nums.length; i++) {
	    sum += nums[i];
	}

	return sum / nums.length;
    }

    public static double findMin(double[] nums) {
	double min = Double.MAX_VALUE;
	for (double num : nums) {
	    if (num < min) {
		min = num;
	    }
	}
	return min;
    }

    public static double fintMax(double[] nums) {
	double max = Double.MIN_VALUE;
	for (double num : nums) {
	    if (num > max) {
		max = num;
	    }
	}
	return max;
    }

    public static void printArray(double[] nums) {
	for (int i = 0; i < nums.length; i++) {
	    System.out.print(nums[i] + " ");
	}

	System.out.println();
    }

    public static double[][] transposeMatrix(double[][] m) {
	double[][] temp = new double[m[0].length][m.length];
	for (int i = 0; i < m.length; i++)
	    for (int j = 0; j < m[0].length; j++)
		temp[j][i] = m[i][j];
	return temp;
    }

}
