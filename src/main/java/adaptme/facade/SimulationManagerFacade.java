package adaptme.facade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.swing.JTable;

import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

import adaptme.DynamicExperimentationProgramProxy;
import adaptme.DynamicExperimentationProgramProxyFactory;
import adaptme.IDynamicExperimentationProgramProxy;
import adaptme.ui.window.perspective.ExperimentationPanel;
import adaptme.ui.window.perspective.ShowResultsPanel;
import adaptme.ui.window.perspective.ShowResultsTableModel;
import adaptme.ui.window.perspective.VariableType;
import executive.queue.Queue;
import model.spem.SimulationFacade;
import simula.ActiveState;
import simula.Activity;
import simula.Scheduler;
import simula.manager.*;
import simulator.spem.xacdml.results.ActivityResults;
import simulator.spem.xacdml.results.IterationResults;
import simulator.spem.xacdml.results.MilestoneResults;
import simulator.spem.xacdml.results.PhaseResults;

public class SimulationManagerFacade {

	private static SimulationManagerFacade simulationManagerFacade = new SimulationManagerFacade();
	private Map<String, IDynamicExperimentationProgramProxy> resultsSimulationMap;
	
	private Map<String, String> resultsSimulationMapAdaptMe;
	private SimulationManager simulationManager;
	private IDynamicExperimentationProgramProxy dynamicExperimentationProgramProxy;
	private ShowResultsPanel showResultsPanel;
	private SimulationFacade simulationFacade;

	private Map<String, HashMap<String, QueueEntry>> resultadoGlobal = new TreeMap();
	
	private double averageNumberOfDays;
	private double averageNumberOfImplementedUserStories;
	private double averageNumberOfReleases;
	private double averageNumberOfIterations;
	private double numberOfSimulationRuns;
	
	private double[] numberOfDaysPerReplication; // TODO generalizar depois
	private double[] numberOfproducedUserStoriesPerReplication; // TODO// generalizar depois
	private double[] numberOfReleasesPerReplication; // TODO generalizar depois
	private List<Integer> numberOfIterationsPerReplication; // TODO generalizar depois para release e nao replication. Aparentemetente refatorar para criar
														// classes Release e iteration para manter
	private double acumulatedNumberOfDays = 0.0;
	private double acumulatedNumberOfIterations = 0.0;
	private double acumulatedNumberOfReleases = 0.0;
	
	private ExperimentationPanel experimentationPanel;
	
	private HashMap queues;
	
	private Map<String, Integer> mapWithAcumulatedIterationResults;
	private Map<String, Integer> mapWithAcumulatedActivityResults;
	
	private Map<String, List<Integer>> mapWithNumberOfIterationsPerReplication;

	private SimulationManagerFacade() {
		resultsSimulationMap = new HashMap<>();
		resultsSimulationMapAdaptMe = new TreeMap<>();
		mapWithAcumulatedIterationResults = new HashMap<>();
		mapWithAcumulatedActivityResults = new HashMap<>();
		mapWithNumberOfIterationsPerReplication = new HashMap<>();
		numberOfIterationsPerReplication = new ArrayList<>();
		dynamicExperimentationProgramProxy = DynamicExperimentationProgramProxyFactory.newInstance();
	}
	
	public void execute(float simulationDuration, int numberReplications, boolean isFromConsole) {
		
		double numberOfDays = 0.0;
		double numberOfIterations = 0.0;
		
		double acumulatedNumberOfUserStories = 0.0;
	 
		this.numberOfSimulationRuns = numberReplications;
		this.numberOfDaysPerReplication = new double[numberReplications];
		
		this.numberOfReleasesPerReplication = new double[numberReplications];
		this.numberOfproducedUserStoriesPerReplication = new double[numberReplications];
 
		System.out.println("\n------------------------- DETAILED RESULTS BY REPLICATION -------------------------------------- \n");

		for (int i = 1; i <= numberReplications; i++) {
			
			dynamicExperimentationProgramProxy = DynamicExperimentationProgramProxyFactory.newInstance();
			dynamicExperimentationProgramProxy.setSimulationDuration(simulationDuration);	
			dynamicExperimentationProgramProxy.execute(simulationDuration); // Execute the replication
			
			// output to console implemented by Pagliares
			// Print the number of days, stores the current replication in a map for further aggregate computation
			this.simulationManager = (SimulationManager) dynamicExperimentationProgramProxy.getSimulationManager(); // nao funciona no construtor
//			System.out.println("\nReplication (execution) #" + (i + 1) + "\n");
			
			// Calculates the the number of days of the simulation in this replication and stores for further average computation
			numberOfDays = simulationManager.getScheduler().GetClock() / 480; 
			numberOfDaysPerReplication[i-1] = numberOfDays;											
			acumulatedNumberOfDays += numberOfDays;
			
			// Calculates the the number of iterations of the simulation in this replication and stores for further average computation
			Map<String, List<IterationResults>> mapWithIterationResults= simulationManager.getScheduler().getMapWithIterationResults(); 
			Set<String> keys = mapWithIterationResults.keySet();
			
			List<IterationResults> listWithIterationResults = new ArrayList<>();
			for (String key : keys) {
				listWithIterationResults = mapWithIterationResults.get(key);
				
				
				if (mapWithNumberOfIterationsPerReplication.containsKey(key)) {
					List<Integer> temp = mapWithNumberOfIterationsPerReplication.get(key);
					temp.add(listWithIterationResults.size());
			//		mapWithNumberOfIterationsPerReplication.put(key, numberOfIterationsPerReplication);

 				} else {
 					List<Integer> numberOfIterationsPerReplication = new ArrayList<>();
 					numberOfIterationsPerReplication.add(listWithIterationResults.size());
					mapWithNumberOfIterationsPerReplication.put(key, numberOfIterationsPerReplication);
				}
				
				if (mapWithAcumulatedIterationResults.containsKey(key)) {
					Integer a = mapWithAcumulatedIterationResults.get(key);
					a += listWithIterationResults.size();
					mapWithAcumulatedIterationResults.put(key, a);
				} else {
					mapWithAcumulatedIterationResults.put(key, listWithIterationResults.size());	
				}
			}
			
			//	System.out.println("\tProject duration: " + numberOfDays + " days\n"); // 480 minutes = 1 day 
			
			queues = simulationManager.getQueues();
//            printNumberOfEntitiesInEachDeadState(queues);

			// Stores the state of all dead states in this replication in order to compute the mean for dead states at the end of all replications
			resultadoGlobal.put("run #" + i, queues);

//			acumulateNumberOfIterations = reportByIteration(acumulateNumberOfIterations, i);
//			acumulateNumberOfReleases = reportByRelease(acumulateNumberOfReleases, i);
//			printGlobalResults(numberReplications, acumulatedNumberOfDays, acumulatedNumberOfUserStories,acumulateNumberOfIterations, acumulateNumberOfReleases);

			if ( !isFromConsole) {
				
				int currentProessAlternativeIndex = simulationFacade.getProcessAlternatives().size() - 1;
				String selectedProcessAlternativeName = simulationFacade.getProcessAlternatives().get(currentProessAlternativeIndex).getName();
				resultsSimulationMap.put(selectedProcessAlternativeName + i, dynamicExperimentationProgramProxy); // armazena replicacoes
				
                 //	String selectedProcessAlternativeName = showResultsPanel.getSelectedProcessAlternativeName();
				resultsSimulationMapAdaptMe.put(selectedProcessAlternativeName + i, getResultadosCabecalho()+ "\n" + 
                        getResultadosGlobalString(experimentationPanel.getMapQueueVariableType()));
			} else {
 				resultsSimulationMap.put("Replication " + i, dynamicExperimentationProgramProxy); // armazena replicacoes
				resultsSimulationMapAdaptMe.put("Replication " + i, getResultadosCabecalhoFromConsole()+ "\n" + 
						getResultadosGlobalStringFromConsole());  
			}

			houseCleaning();
		}
	}
	
	
	
	// TODO remove this method after finishing the code to extend XACDML in order to support iteration and releases
	private void printGlobalResults(int numberReplications, double acumulatedNumberOfDays,
			double acumulatedNumberOfUserStories, double acumulateNumberOfIterations,
			double acumulateNumberOfReleases) {
		System.out.println("\nDisplaying global results");

		this.averageNumberOfDays = acumulatedNumberOfDays / numberReplications * 10 / 10;
		this.averageNumberOfIterations = acumulateNumberOfIterations / numberReplications;
		this.averageNumberOfImplementedUserStories = acumulatedNumberOfUserStories / numberReplications;
		this.averageNumberOfReleases = acumulateNumberOfReleases / numberReplications;

		System.out.println("\tAverage number of days ..: " + averageNumberOfDays);
	}

	public void printNumberOfEntitiesInEachDeadState(HashMap queues) {
		// Show the number of entities in all dead states
		System.out.println("\t[Dead states snapshot] - Printing the number of entities in each dead state at the end of the replication");
		
		Set keys = queues.keySet();

		for (Object queueName : keys) {
			System.out.println("\n\t\tQueue name : " + queueName);
			QueueEntry qe = (QueueEntry) queues.get(queueName);

			// Both below outputs return the count variable
			System.out.println("\t\tNunber of entities in queue via getCount: " + qe.deadState.getCount());
			// System.out.println("numero de entidadas na fila: via ObsLength, basta mudar simobj para o novo nome" + qe.SimObj.getCount());
		}
	}
	
	public int getQuantityOfEntitiesInFinalDeadStateForPaper(HashMap queues) {
  		int quantity = 0;
		Set keys = queues.keySet();

		for (Object queueName : keys) {
			if (queueName.equals("q5")) {
				QueueEntry qe = (QueueEntry) queues.get(queueName);
				// Both below outputs return the count variable
				return qe.deadState.getCount();
			}
		}
		return quantity;
	}
	
	
	public void printOneObserver() {
		Iterator it;
		HashMap observers = simulationManager.getObservers();
		it = observers.values().iterator();
		ObserverEntry observerEntry = (ObserverEntry) it.next();
		simulationManager.printObserversReport(observerEntry);
	}

	public Map<String, IDynamicExperimentationProgramProxy> getResultsSimulationMap() {
		return resultsSimulationMap;
	}

	public static SimulationManagerFacade getSimulationManagerFacade() {
		return simulationManagerFacade;
	}

	public void setShowResultsPanel(ShowResultsPanel showResultsPanel) {
		this.showResultsPanel = showResultsPanel;
	}

	public void setSimulationFacade(SimulationFacade simulationFacade) {
		this.simulationFacade = simulationFacade;

	}

	// TODO remove this method after finishing the code to extend XACDML in order to support iteration and releases
	public void printObserversReportByIteration(HashMap firstHash) {
		Collection<String> chaves = firstHash.keySet();

		for (String chave : chaves) {
			System.out.println(chave);
			HashMap secondHash = (HashMap) firstHash.get(chave);
			Collection<String> chavesSegundoHash = secondHash.keySet();
			for (String chave1 : chavesSegundoHash) {
				if (secondHash.get(chave1) instanceof QueueEntry) {
					QueueEntry qe1 = (QueueEntry) secondHash.get(chave1);
					System.out.println("QueuName..:" + chave1 + "  " + "inner queue name..:  " + qe1.getName()
							+ "  number of entities..:" + qe1.deadState.getCount());
				} else if (secondHash.get(chave1) instanceof ObserverEntry) {
					ObserverEntry oe1 = (ObserverEntry) secondHash.get(chave1);
					System.out.println("Chave second hash..:" + chave1 + "  " + "value (queue name) ..:  " + oe1);
				}
			}

		}
	}

	 
	
	/**
	 * Algorithm 1 - Determines the dimension of the bidimensional array of
	 * results 2- Creates the two-dimension array 3 - For each experiment in
	 * resultadoGlobalSimulacao 3.1 - extract the quantity of entities in each
	 * queue and stores it in the array  
	 */
	public int[][] createMatrixWithResultsOfAllReplications() {
		// quatro linhas abaixo apenas para descobrir o numero de linhas e colunas da matriz
		Set<String> keysWithReplicationNames = resultadoGlobal.keySet(); // contem o nome de todos experimentos como chave
		int quantityOfExperiments = keysWithReplicationNames.size();
		
		Map<String, QueueEntry> hm = resultadoGlobal.get(keysWithReplicationNames.iterator().next());
		Set<String> chaves = hm.keySet();
		int quantityOfDeadStates = chaves.size();
		
		int[][] matrixWithResults = new int[quantityOfExperiments][quantityOfDeadStates];
 		int quantity = 0;
		int linesCounter = 0; // number of entities in each queue
		int columnsCounter = 0; // experiments

		for (String replication : keysWithReplicationNames) {
			Map<String, QueueEntry> secondHash = resultadoGlobal.get(replication);
			Collection<String> keysWithDeadStatesNames = secondHash.keySet();

			for (String queueName : keysWithDeadStatesNames) {
				 
					QueueEntry qe1 =  secondHash.get(queueName);
					quantity = qe1.deadState.getCount();
					matrixWithResults[linesCounter][columnsCounter] = quantity; // nao armazeno o nome do experimento, apenas inteiros com #enitites
					columnsCounter++;
				
			}
			linesCounter++;
			columnsCounter = 0;
		}
		return matrixWithResults;
	}

	public void printResultadosGlobal(int[][] matrizResultados) {
		System.out.println("\tPrinting the mean and sd of entities in each queue and for each replication");
		Set<String> keys = resultadoGlobal.keySet(); // contem o nome de todos
														// experimentos como
														// chave

		HashMap<String, QueueEntry> secondHash = resultadoGlobal.get(keys.iterator().next());
		Collection<String> chavesNomeFilasSegundoHash = secondHash.keySet();

		double soma = 0.0;
		StandardDeviation sd = new StandardDeviation();

		Iterator<String> iterator = chavesNomeFilasSegundoHash.iterator();
		double[] numeroEntidadesPorFila = new double[matrizResultados.length];

		for (int j = 0; j < matrizResultados[0].length; j++) { // para uma determinada coluna (celula representa a quantidade de entities)
			String fila = (String) iterator.next();
 			System.out.println("Fila " + fila);
			for (int i = 0; i < matrizResultados.length; i++) { // somo todas as linhas para a coluna acima (representa experimentos)				
				System.out.println("quantity in replication " + (i + 1) + "..: " + matrizResultados[i][j]);
				numeroEntidadesPorFila[i] = matrizResultados[i][j];
				soma = soma + matrizResultados[i][j];
			}
			System.out.println("means of entities in queue " + fila + "..:" + soma / matrizResultados.length);
			System.out.println("Standard deviation..: " + sd.evaluate(numeroEntidadesPorFila));
			soma = 0.0;
			numeroEntidadesPorFila = new double[matrizResultados.length];
			System.out.println("");
			
		}
	}
	
	public double calculateMeanNumberOfEntitiesInADeadState(int[][] matrixWithResults, String deadStateName) {
		double mean = 0.0;
 		Set<String> keys = resultadoGlobal.keySet(); // contem o nome de todos experimentos como chave

		HashMap<String, QueueEntry> mapWithDeadStatesAtTheEndOfSimulation = resultadoGlobal.get(keys.iterator().next());
		Collection<String> keyNamesMapWithDeadStatesAtTheEndOfSimulation = mapWithDeadStatesAtTheEndOfSimulation.keySet();

		double sum = 0.0;
 
		Iterator<String> iterator = keyNamesMapWithDeadStatesAtTheEndOfSimulation.iterator();
		
		double[] quantyOfEntitiesPerDeadState = new double[matrixWithResults.length];

		for (int j = 0; j < matrixWithResults[0].length; j++) { // For a specific column (cell represents the quantity of entities)
			String queueName = (String) iterator.next();
 			if (queueName.equalsIgnoreCase(deadStateName)) {
  				for (int i = 0; i < matrixWithResults.length; i++) { // Sum all rows for the above column (represents experiments)				
  					quantyOfEntitiesPerDeadState[i] = matrixWithResults[i][j];
 					sum = sum + matrixWithResults[i][j];
 				}
// 				mean = Math.round(sum / matrixWithResults.length) *100.0/100.0;
 				mean = sum / matrixWithResults.length;

  				sum = 0.0;
 				quantyOfEntitiesPerDeadState = new double[matrixWithResults.length];
 			}}
		return mean;
	}
	
	public double calculateMeanNumberOfEntitiesForMoreThanOneDeadState(int[][] matrixWithResults, String deadStateName, String deadStateName2, String deadStateName3) {
		double mean = 0.0;
 		Set<String> keys = resultadoGlobal.keySet(); // contem o nome de todos experimentos como chave

		HashMap<String, QueueEntry> mapWithDeadStatesAtTheEndOfSimulation = resultadoGlobal.get(keys.iterator().next());
		Collection<String> keyNamesMapWithDeadStatesAtTheEndOfSimulation = mapWithDeadStatesAtTheEndOfSimulation.keySet();

		double sum = 0.0;
 
		Iterator<String> iterator = keyNamesMapWithDeadStatesAtTheEndOfSimulation.iterator();
		
		double[] quantyOfEntitiesPerDeadState = new double[matrixWithResults.length];

		for (int j = 0; j < matrixWithResults[0].length; j++) { // For a specific column (cell represents the quantity of entities)
			String queueName = (String) iterator.next();
 			if ((queueName.equalsIgnoreCase(deadStateName)) || (queueName.equalsIgnoreCase(deadStateName2)) || (queueName.equalsIgnoreCase(deadStateName3))) {
  				for (int i = 0; i < matrixWithResults.length; i++) { // Sum all rows for the above column (represents experiments)				
  					quantyOfEntitiesPerDeadState[i] = matrixWithResults[i][j];
 					sum = sum + matrixWithResults[i][j];
 				}
 				mean =  sum / matrixWithResults.length;
  				sum = 0.0;
 				quantyOfEntitiesPerDeadState = new double[matrixWithResults.length];
 			}}
		return mean;
	}
	
	public double calculateStandardDeviationNumberOfEntitiesInADeadState(int[][] matrixWithResults, String deadStateName) {
		double standardDeviation = 0.0;
 		Set<String> keys = resultadoGlobal.keySet(); // contem o nome de todos experimentos como chave

		HashMap<String, QueueEntry> mapWithDeadStatesAtTheEndOfSimulation = resultadoGlobal.get(keys.iterator().next());
		Collection<String> keyNamesMapWithDeadStatesAtTheEndOfSimulation = mapWithDeadStatesAtTheEndOfSimulation.keySet();

		double sum = 0.0;
		StandardDeviation sd = new StandardDeviation();

		Iterator<String> iterator = keyNamesMapWithDeadStatesAtTheEndOfSimulation.iterator();
		
		double[] quantyOfEntitiesPerDeadState = new double[matrixWithResults.length];

		for (int j = 0; j < matrixWithResults[0].length; j++) { // For a specific column (cell represents the quantity of entities)
			String queueName = (String) iterator.next();
 			if (queueName.equalsIgnoreCase(deadStateName)) {
  				for (int i = 0; i < matrixWithResults.length; i++) { // Sum all rows for the above column (represents experiments)				
  					quantyOfEntitiesPerDeadState[i] = matrixWithResults[i][j];
 					sum = sum + matrixWithResults[i][j];
 				}
//  				 standardDeviation =  Math.round(sd.evaluate(quantyOfEntitiesPerDeadState)) * 100.00/100.00;
  				 standardDeviation =  sd.evaluate(quantyOfEntitiesPerDeadState);
 				sum = 0.0;
 				quantyOfEntitiesPerDeadState = new double[matrixWithResults.length];
 			}}
		return standardDeviation;
	}
	
	public double calculateStandardDeviationNumberOfEntitiesForMoreThanOneDeadState(int[][] matrixWithResults, String deadStateName, String deadStateName2, String deadStateName3) {
		double standardDeviation = 0.0;
 		Set<String> keys = resultadoGlobal.keySet(); // contem o nome de todos experimentos como chave

		HashMap<String, QueueEntry> mapWithDeadStatesAtTheEndOfSimulation = resultadoGlobal.get(keys.iterator().next());
		Collection<String> keyNamesMapWithDeadStatesAtTheEndOfSimulation = mapWithDeadStatesAtTheEndOfSimulation.keySet();

		double sum = 0.0;
		StandardDeviation sd = new StandardDeviation();

		Iterator<String> iterator = keyNamesMapWithDeadStatesAtTheEndOfSimulation.iterator();
		
		double[] quantyOfEntitiesPerDeadState = new double[matrixWithResults.length*3];

		for (int j = 0; j < matrixWithResults[0].length; j++) { // For a specific column (cell represents the quantity of entities)
			String queueName = (String) iterator.next();
 			if ((queueName.equalsIgnoreCase(deadStateName)) || (queueName.equalsIgnoreCase(deadStateName2)) || (queueName.equalsIgnoreCase(deadStateName3))) {
  				for (int i = 0; i < matrixWithResults.length; i++) { // Sum all rows for the above column (represents experiments)				
  					quantyOfEntitiesPerDeadState[i] = matrixWithResults[i][j];
 					sum = sum + matrixWithResults[i][j];
 				}
  				 standardDeviation =  Math.round(sd.evaluate(quantyOfEntitiesPerDeadState)) * 100.00/100.00;
 				sum = 0.0;
 				quantyOfEntitiesPerDeadState = new double[matrixWithResults.length];
 			}}
		return standardDeviation;
	}
	
	public void printResultadosGlobal(int[][] matrixWithResults, String deadStateName) {
		System.out.println("\t\nPrinting the mean and sd of entities in the " + deadStateName + " dead state");
		Set<String> keys = resultadoGlobal.keySet(); // contem o nome de todos experimentos como chave

		HashMap<String, QueueEntry> mapWithDeadStatesAtTheEndOfSimulation = resultadoGlobal.get(keys.iterator().next());
		Collection<String> keyNamesMapWithDeadStatesAtTheEndOfSimulation = mapWithDeadStatesAtTheEndOfSimulation.keySet();

		double sum = 0.0;
		StandardDeviation sd = new StandardDeviation();

		Iterator<String> iterator = keyNamesMapWithDeadStatesAtTheEndOfSimulation.iterator();
		
		double[] quantyOfEntitiesPerDeadState = new double[matrixWithResults.length];

		for (int j = 0; j < matrixWithResults[0].length; j++) { // For a specific column (cell represents the quantity of entities)
			String queueName = (String) iterator.next();
 			if (queueName.equalsIgnoreCase(deadStateName)) {
 				System.out.println("Queue " + queueName);
 				for (int i = 0; i < matrixWithResults.length; i++) { // Sum all rows for the above column (represents experiments)				
 					System.out.println("quantity in replication " + (i + 1) + "..: " + matrixWithResults[i][j]);
 					quantyOfEntitiesPerDeadState[i] = matrixWithResults[i][j];
 					sum = sum + matrixWithResults[i][j];
 				}
 				double mean = Math.round(sum / matrixWithResults.length) *100.0/100.0;
 				double standardDeviation =  Math.round(sd.evaluate(quantyOfEntitiesPerDeadState)) * 100.00/100.00;
 				System.out.println("mean (sd) of entities in the dead state " + queueName + "..:" + mean);
 				System.out.println("Standard deviation..: " + standardDeviation);
 				sum = 0.0;
 				quantyOfEntitiesPerDeadState = new double[matrixWithResults.length];
 				System.out.println("");
 			}}
//		}
	}

	/**
	 * Algorithm 1 - Determines the dimension of the bidimensional array of
	 * results 
	 * 2- Creates the two-dimension array 
	 * 3 - For each experiment in
	 * resultadoGlobalSimulacao 
	 * 		3.1 - extract the quantity of entities in each queue and stores it in the array 
	 * 4 - Print array contents
	 */
	public String getResultadosGlobalString(Map<String, VariableType> mapQueueVariableType ) {
		
		String resultadoGlobalString = "";

		// quatro linhas abaixo apenas para descobrir o numero de linhas e
		// colunas da matriz
		Set<String> keys = resultadoGlobal.keySet(); // contem o nome de todos
														// experimentos como
														// chave
		int numeroExperimentos = keys.size();
		HashMap hm = (HashMap) resultadoGlobal.get(keys.iterator().next());
		Set<String> chaves = hm.keySet();
		int numeroFilas = chaves.size();
		int[][] matrizResultados = new int[numeroExperimentos][numeroFilas];
		int soma = 0;
		int quantity = 0;
		int contadorLinhas = 0; // number of entities in each queue
		int contadorColunas = 0; // experimentos

		for (String experimento : keys) {
 
			HashMap secondHash = (HashMap) resultadoGlobal.get(experimento);
			Collection<String> chavesNomeFilasSegundoHash = secondHash.keySet();

			for (String queueName : chavesNomeFilasSegundoHash) {
				if (secondHash.get(queueName) instanceof QueueEntry) {
					QueueEntry qe1 = (QueueEntry) secondHash.get(queueName);
					quantity = qe1.deadState.getCount();
					matrizResultados[contadorLinhas][contadorColunas] = quantity; // nao armazeno o nome do experimento. Apenas inteiros com #entities																 
				}
				contadorColunas++;
			}
			contadorLinhas++;
			contadorColunas = 0;
		}
		String temp = printResultadosGlobalTextArea(matrizResultados, resultadoGlobalString, mapQueueVariableType );
		return temp;
	}
	
	/**
	 * Algorithm 1 - Determines the dimension of the bidimensional array of
	 * results 
	 * 2- Creates the two-dimension array 
	 * 3 - For each experiment in
	 * resultadoGlobalSimulacao 
	 * 		3.1 - extract the quantity of entities in each queue and stores it in the array 
	 * 4 - Print array contents
	 */
	public String getResultadosGlobalStringFromConsole() {
		
		String resultadoGlobalString = "";

		// quatro linhas abaixo apenas para descobrir o numero de linhas e
		// colunas da matriz
		Set<String> keys = resultadoGlobal.keySet(); // contem o nome de todos
														// experimentos como
														// chave
		int numeroExperimentos = keys.size();
		Object o = keys.iterator().next();
		HashMap hm = (HashMap) resultadoGlobal.get(o);
		Set<String> chaves = hm.keySet();
		int numeroFilas = chaves.size();
		int[][] matrizResultados = new int[numeroExperimentos][numeroFilas];
		int soma = 0;
		int quantity = 0;
		int contadorLinhas = 0; // number of entities in each queue
		int contadorColunas = 0; // experimentos

		for (String experimento : keys) {
 
			HashMap secondHash = (HashMap) resultadoGlobal.get(experimento);
			Collection<String> chavesNomeFilasSegundoHash = secondHash.keySet();

			for (String queueName : chavesNomeFilasSegundoHash) {
				if (secondHash.get(queueName) instanceof QueueEntry) {
					QueueEntry qe1 = (QueueEntry) secondHash.get(queueName);
					quantity = qe1.deadState.getCount();
					matrizResultados[contadorLinhas][contadorColunas] = quantity; // nao armazeno o nome do experimento. Apenas inteiros com #entities																 
				}
				contadorColunas++;
			}
			contadorLinhas++;
			contadorColunas = 0;
		}
		String temp = printResultadosGlobalTextAreaFromConsole(matrizResultados, resultadoGlobalString);
		return temp;
	}

	private String printResultadosGlobalTextArea(int[][] matrizResultados, String resultadoGlobalString, Map<String, VariableType> mapQueueVariableType) {
//		String cabecalho = "";
//		if (mapQueueVariableType.size() !=0) {
		    String cabecalho = "\n............................   PRINTING THE MEAN AND STANDARD DEVIATION OF ENTITIES IN EACH QUEUE CONFIGURED AS 'DEPENDENT' FOR EACH REPLICATION   ............................ \n";
//		} else {
//			cabecalho = "";
//		}
			
		Set<String> keys = resultadoGlobal.keySet(); // contem o nome de todos experimentos como chave

		HashMap secondHash = (HashMap) resultadoGlobal.get(keys.iterator().next());
		Collection<String> chavesNomeFilasSegundoHash = secondHash.keySet();

		double soma = 0.0;
		StandardDeviation sd = new StandardDeviation();

		Iterator iterator = chavesNomeFilasSegundoHash.iterator();
		double[] numeroEntidadesPorFila = new double[matrizResultados.length];

		for (int j = 0; j < matrizResultados[0].length; j++) { // para uma determinada coluna (celula representa quantidade de entities)
																
			String fila = (String) iterator.next();
			System.out.println(mapQueueVariableType.toString());
			if (mapQueueVariableType.containsKey(fila)) {
				resultadoGlobalString += "\nQueue: " + fila;
			 
				for (int i = 0; i < matrizResultados.length; i++) { // somo todas as linhas para a coluna acima (representa experimentos)				 
					resultadoGlobalString += "\n\tquantity in replication " + (i + 1) + "..: " + matrizResultados[i][j];
					numeroEntidadesPorFila[i] = matrizResultados[i][j];
					soma = soma + matrizResultados[i][j];
				}
				
				double mean = Math.round(((soma / matrizResultados.length)*100.0)/100.0);
				double standardDeviation = Math.round(((sd.evaluate(numeroEntidadesPorFila))*100.0)/100.0);
				
				resultadoGlobalString += "\n\tmean of entities in queue " + fila + "..:" + mean;
				resultadoGlobalString += "\tStandard deviation..: " + standardDeviation + "\n";
				soma = 0.0;
				numeroEntidadesPorFila = new double[matrizResultados.length];
			}
		}
		return cabecalho + resultadoGlobalString;
	}
	
	private String printResultadosGlobalTextAreaFromConsole(int[][] matrizResultados, String resultadoGlobalString) {
//		String cabecalho = "";
//		if (mapQueueVariableType.size() !=0) {
		    String cabecalho = "\n............................   PRINTING THE MEAN AND STANDARD DEVIATION OF ENTITIES IN EACH QUEUE CONFIGURED AS 'DEPENDENT' FOR EACH REPLICATION   ............................ \n";
//		} else {
//			cabecalho = "";
//		}
			
		Set<String> keys = resultadoGlobal.keySet(); // contem o nome de todos experimentos como chave

		HashMap secondHash = (HashMap) resultadoGlobal.get(keys.iterator().next());
		Collection<String> chavesNomeFilasSegundoHash = secondHash.keySet();

		double soma = 0.0;
		StandardDeviation sd = new StandardDeviation();

		Iterator iterator = chavesNomeFilasSegundoHash.iterator();
		double[] numeroEntidadesPorFila = new double[matrizResultados.length];

		for (int j = 0; j < matrizResultados[0].length; j++) { // para uma determinada coluna (celula representa quantidade de entities)
																
			String fila = (String) iterator.next();
//			System.out.println(mapQueueVariableType.toString());
//			if (mapQueueVariableType.containsKey(fila)) {
				resultadoGlobalString += "\nQueue: " + fila;
			 
				for (int i = 0; i < matrizResultados.length; i++) { // somo todas as linhas para a coluna acima (representa experimentos)				 
					resultadoGlobalString += "\n\tquantity in replication " + (i + 1) + "..: " + matrizResultados[i][j];
					numeroEntidadesPorFila[i] = matrizResultados[i][j];
					soma = soma + matrizResultados[i][j];
				}
				
				double mean = Math.round(((soma / matrizResultados.length)*100.0)/100.0);
				double standardDeviation = Math.round(((sd.evaluate(numeroEntidadesPorFila))*100.0)/100.0);
				
				resultadoGlobalString += "\n\tmean of entities in queue " + fila + "..:" + mean;
				resultadoGlobalString += "\tStandard deviation..: " + standardDeviation + "\n";
				soma = 0.0;
				numeroEntidadesPorFila = new double[matrizResultados.length];
			}
//		}
		return cabecalho + resultadoGlobalString;
	}
	
	public String getResultadosCabecalho() {
		
		String resultadoGlobalString = "\n............   NUMBER OF SIMULATION RUNS, MEAN(SD) OF PROJECT DURATION, MEAN(SD) OF ITERATION/RELEASE (ONLY FOR ITERATIVE AND RELEASE BASED PROCESSES)   ............\n";
 		
		resultadoGlobalString+= "\nNumber of simulation runs......................:  "  + getNumberOfSimulationRuns() + "\n";
		
		resultadoGlobalString+= "Number of days.....................................:  " + Math.round(simulationManagerFacade.getAverageNumberOfDays()*100.0)/100.0 +
                "(" + Math.round(simulationManagerFacade.calculateStandardDeviationNumberOfDays()*100.0)/100.0 + ")" + "\n";
 
//		if (getSimulationManager().getScheduler().hasRelease()) {
//			resultadoGlobalString+= "Number of releases................................:  " + Math.round(simulationManagerFacade.getAverageNumberOfReleases()*100.0)/100.0 +
//					"(" + Math.round(simulationManagerFacade.calculateStandardDeviationNumberOfReleases()*100.0)/100.0 + ")" + "\n";
//		}
//		
//		if (getSimulationManager().getScheduler().hasIteration()) {
//			resultadoGlobalString+= "Number of iterations per release.............:  " + Math.round(simulationManagerFacade.getAverageNumberOfIterations()/simulationManagerFacade.getAverageNumberOfReleases()*100.0)/100.0 + 
//					"(" + Math.round(simulationManagerFacade.calculateStandardDeviationNumberOfReleases()*100.0)/100.0 + ")" + "\n";; // TODO implementar para release  
//		}
		
		return resultadoGlobalString;
	}
	
	
	public String getResultadosCabecalhoFromConsole() {
		
		String resultadoGlobalString = "\n............   NUMBER OF SIMULATION RUNS, MEAN(SD) OF PROJECT DURATION, MEAN(SD) OF ITERATION/RELEASE (ONLY FOR ITERATIVE AND RELEASE BASED PROCESSES)   ............\n";
 		
		resultadoGlobalString+= "\nNumber of simulation runs......................:  "  + getNumberOfSimulationRuns() + "\n";
		
		resultadoGlobalString+= "Number of days.....................................:  " + Math.round(simulationManagerFacade.getAverageNumberOfDays()*100.0)/100.0 +
                "(" + Math.round(simulationManagerFacade.calculateStandardDeviationNumberOfDays()*100.0)/100.0 + ")" + "\n";
 
//		if (getSimulationManager().getScheduler().hasRelease()) {
//			resultadoGlobalString+= "Number of releases................................:  " + Math.round(simulationManagerFacade.getAverageNumberOfReleases()*100.0)/100.0 +
//					"(" + Math.round(simulationManagerFacade.calculateStandardDeviationNumberOfReleases()*100.0)/100.0 + ")" + "\n";
//		}
//		
//		if (getSimulationManager().getScheduler().hasIteration()) {
//			resultadoGlobalString+= "Number of iterations per release.............:  " + Math.round(simulationManagerFacade.getAverageNumberOfIterations()/simulationManagerFacade.getAverageNumberOfReleases()*100.0)/100.0 + 
//					"(" + Math.round(simulationManagerFacade.calculateStandardDeviationNumberOfReleases()*100.0)/100.0 + ")" + "\n";; // TODO implementar para release  
//		}
		
		return resultadoGlobalString;
	}
	
	
	private void houseCleaning() {
		dynamicExperimentationProgramProxy.getSimulationManager().getScheduler().Stop();
		dynamicExperimentationProgramProxy.getSimulationManager().getScheduler().Clear();

		dynamicExperimentationProgramProxy = null;
		Activity.isBeginOfSimulation = true;
		Scheduler.hasFinishedByLackOfEntities = false;
		ActiveEntry.lastid = 0; // se nao fizer isso, a cada replicacao os numeros de atividades vao so aumentando. Tem impacto na classe Scheduler
								// metodo run(), onde tento controlar o inicio de uma atividade via Math.random quando mais de uma atividade
		                        // tem a mesma atividade com predecessora em SPEM ou em outras palavras, quando mais de uma atividade tem como 
		                        // a mesma fila como previous state.
	}
	
		public void printActivityResults() {
			Map<String, List<ActivityResults>> mapWithActivityResults = simulationManager.getScheduler().getMapWithActivityResults();
			Set<String> keys = mapWithActivityResults.keySet();
			List<ActivityResults> activityResultsList = null;
 
			if (keys.size() == 0){
				System.out.println("\n\t\tThere are no SPEM activities in the simulated process or the simulation finished before at least one activity finished");
			} else {
			    for (String key: keys) {
			       activityResultsList = mapWithActivityResults.get(key);
				   System.out.println("\n\t\tThe activity named \'" +  key.split("_")[1] + " was executed " + activityResultsList.size()  +  " times");

				   for (int i = 1; i <=  activityResultsList.size(); i++) {
				    	ActivityResults ac = activityResultsList.get(i-1);
					     System.out.println("\n\t\t\t - In the execution # " + i + ", it started at (day) : " + ((int)ac.getTimeWorkBreakdownElementStarted()/480 + 1) +  
							" and finished at (day) : " + ((int)ac.getTimeWorkBreakdownElementFinished()/480 + 1 ));
				   }
				}
			}	
		}
		
		public void printPhaseResults() {
			Map<String, List<PhaseResults>> mapWithPhaseResults = simulationManager.getScheduler().getMapWithPhaseResults();
			Set<String> keys = mapWithPhaseResults.keySet();
			List<PhaseResults> phaseResultsList = null;
			if (keys.size() == 0){
				System.out.println("\n\t\tThere are no SPEM phases in the simulated process or the simulation finished before at least one phase finished");
			} else  {
				 for (String key: keys) {
					 phaseResultsList = mapWithPhaseResults.get(key);
					 System.out.println("\n\t\tThe phase named \'" +  key.split("_")[1] + " was executed " + phaseResultsList.size()  +  " times");
				 
			          for (int i = 1; i <=  phaseResultsList.size(); i++) {
			            	PhaseResults fr = phaseResultsList.get(i-1);
			            	System.out.println("\n\t\t\t - In the execution # " + i + ", it started at (day) : " + ((int)fr.getTimeWorkBreakdownElementStarted()/480 + 1) +  
						" and finished at (day) : " + ((int)fr.getTimeWorkBreakdownElementFinished()/480 + 1 ));
			          }
				 }	 
			  }	
		}
		
		public void printMilestoneResults() {
			Map<String, List<MilestoneResults>> mapWithMilestoneResults = simulationManager.getScheduler().getMapWithMilestoneResults();
			Set<String> keys = mapWithMilestoneResults.keySet();
			List<MilestoneResults> milestoneResultsList = null;
			
			if (keys.size() == 0){
				System.out.println("\n\t\tThere are no SPEM milestones in the simulated process or the simulation finished before at least one milestone reached");
			} else  {
			  for (String key: keys) {
				milestoneResultsList = mapWithMilestoneResults.get(key);
				System.out.println("\n\tMilestone " +  key + " reached !" );
				

		          for (int i = 1; i <=  milestoneResultsList.size(); i++) {
		            	MilestoneResults mr = milestoneResultsList.get(i-1);
		            	System.out.println("\n\t it was reached at (day) : " + ((int)mr.getTimeReached()/480 + 1));
		          }
			  }
			}
			
		}
		
		public void printIterationAndReleaseResults() {
			Map<String, List<IterationResults>> mapWithIterationResults = simulationManager.getScheduler().getMapWithIterationResults();
			Set<String> keys = mapWithIterationResults.keySet();
			List<IterationResults> iterationResultsList = null;
			if (keys.size() == 0){
				System.out.println("\n\tThere are no SPEM iteration in the simulated process or the simulation finished before at least one iteration finished");
			} else  {
				 for (String key: keys) {
					 iterationResultsList = mapWithIterationResults.get(key);
					 System.out.println("\n\t\tThe iteration named \'" +  key.split("_")[1] + "\'"+ " was executed " + iterationResultsList.size()  +  " times");
				 
			          for (int i = 1; i <=  iterationResultsList.size(); i++) {
			            	IterationResults ir = iterationResultsList.get(i-1);
			            	System.out.println("\n\t\t\t - In the execution # " + i + ", it started at (day) : " + ((int)ir.getTimeWorkBreakdownElementStarted()/480 + 1) +  
						" and finished at (day) : " + ((int)ir.getTimeWorkBreakdownElementFinished()/480 + 1 ));
			          }
				 }	 
			  }	
		}
		
		public double getAverageNumberOfDays() {
			return averageNumberOfDays;
		}

		public void setAverageNumberOfDays(double averageNumberOfDays) {
			this.averageNumberOfDays = averageNumberOfDays;
		}

		public double getAverageNumberOfImplementedUserStories() {
			return averageNumberOfImplementedUserStories;
		}

		public void setAverageNumberOfImplementedUserStories(double averageNumberOfImplementedUserStories) {
			this.averageNumberOfImplementedUserStories = averageNumberOfImplementedUserStories;
		}

		public double getAverageNumberOfReleases() {
			return averageNumberOfReleases;
		}

		public void setAverageNumberOfReleases(double averageNumberOfReleases) {
			this.averageNumberOfReleases = averageNumberOfReleases;
		}

		public double getAverageNumberOfIterations() {
			return averageNumberOfIterations;
		}

		public void setAverageNumberOfIteration(double averageNumberOfIterations) {
			this.averageNumberOfIterations = averageNumberOfIterations;
		}

		public double getNumberOfSimulationRuns() {
			return numberOfSimulationRuns;
		}

		public void setNumberOfSimulationRuns(double numberOfSimulationRuns) {
			this.numberOfSimulationRuns = numberOfSimulationRuns;
		}

		public double[] getNumberOfDaysPerReplication() {
			return numberOfDaysPerReplication;
		}
		
		public String getSimulationEndedTime() {
			Scheduler scheduler = dynamicExperimentationProgramProxy.getSimulationManager().getScheduler();
			return (Float.toString(scheduler.getEndclock()));
		}

		public String getSimulationResults() {
			return simulationManager.getSimulationResults();
		}

		public double calculateMeanNumberOfDays() {
			double result = (double) acumulatedNumberOfDays/numberOfSimulationRuns;
			return result;
		}
		
		public double calculateStandardDeviationNumberOfDays() {
			StandardDeviation sd = new StandardDeviation();
			return sd.evaluate(numberOfDaysPerReplication);
		}
		
		 
		
		 
		
		public double calculateMeanNumberOfReleases() {
			return acumulatedNumberOfReleases/numberOfSimulationRuns;
		}
		
		public double calculateMeanNumberOfActivities() {
 			return acumulatedNumberOfReleases/numberOfSimulationRuns;
		}

		public double calculateStandardDeviationUserStoriesProducede() {
			StandardDeviation sd = new StandardDeviation();
			return sd.evaluate(numberOfproducedUserStoriesPerReplication) * 100 / 100;
		}

		public double calculateStandardDeviationNumberOfReleases() {
			StandardDeviation sd = new StandardDeviation();
			return sd.evaluate(numberOfReleasesPerReplication) * 100 / 100;
		}

		public SimulationManager getSimulationManager() {
			return simulationManager;
		}

		public Map<String, HashMap<String, QueueEntry>> getResultadoGlobal() {
			return resultadoGlobal;
		}

		public Map<String, String> getResultsSimulationMapAdaptMe() {
			return resultsSimulationMapAdaptMe;
		}

		public void setExperimentationPanel(ExperimentationPanel experimentationPanel) {
			this.experimentationPanel = experimentationPanel;
		}

		public Map<String, Integer> getMapWithAcumulatedIterationResults() {
			return mapWithAcumulatedIterationResults;
		}
		
		public Map<String, Integer> getMapWithAcumulatedActiviitesResults() {
			return mapWithAcumulatedActivityResults;
		}
		
		public double getAcumulatedNumberOfIterations() {
			return acumulatedNumberOfIterations;
		}

		public Map<String, List<Integer>> getMapWithNumberOfIterationsPerReplication() {
			return mapWithNumberOfIterationsPerReplication;
		}
}
