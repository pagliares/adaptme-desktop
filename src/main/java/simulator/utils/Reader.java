package simulator.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Reader {

    private double elements[][];
    private String titles[];
    private Scanner scanner;
    private String filePath;

    public Reader(String filePath) throws FileNotFoundException {
	this.filePath = filePath;
	init();
    }

    private void init() throws FileNotFoundException {
	String element;
	readSizeOfData();
	scanner = new Scanner(new File(filePath));
	titles = scanner.nextLine().split("\t");
	int lineIndex = 0;
	while (scanner.hasNext()) {
	    element = scanner.nextLine();
	    String[] attributes = element.split("\t");
	    for (int i = 0; i < attributes.length; i++) {
		elements[lineIndex][i] = Double.parseDouble(attributes[i]);
	    }
	    lineIndex++;
	}
    }

    private void readSizeOfData() throws FileNotFoundException {
	scanner = new Scanner(new File(filePath));
	int lines = 0, columns = 0;
	String element = null;
	scanner.nextLine();
	while (scanner.hasNext()) {
	    element = scanner.nextLine();
	    lines++;
	}
	columns = element.split("\t").length;
	elements = new double[lines][columns];
	scanner.close();
    }

    public double[][] getElements() {
	return elements;
    }

    public String[] getTitles() {
	return titles;
    }
}
