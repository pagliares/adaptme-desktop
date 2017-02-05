package adaptme.facade;

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
import simula.Activity;
import simula.Scheduler;
import simula.manager.*;

public class SimulationManagerFacade {

	private static SimulationManagerFacade simulationManagerFacade = new SimulationManagerFacade();
	private Map<String, IDynamicExperimentationProgramProxy> resultsSimulationMap;
	
	private Map<String, String> resultsSimulationMapAdaptMe;
	private SimulationManager simulationManager;
	private IDynamicExperimentationProgramProxy dynamicExperimentationProgramProxy;
	private ShowResultsPanel showResultsPanel;
	private SimulationFacade simulationFacade;

	private SortedMap resultadoGlobal = new TreeMap();
	private double averageNumberOfDays;
	private double averageNumberOfImplementedUserStories;
	private double averageNumberOfReleases;
	private double averageNumberOfIterations;
	private double numberOfSimulationRuns;
	private double[] numberOfDaysPerReplication; // TODO generalizar depois
	private double[] numberOfproducedUserStoriesPerReplication; // TODO
																// generalizar
																// depois
	private double[] numberOfReleasesPerReplication; // TODO generalizar depois
	private double[] numberOfIterationsPerReplication; // TODO generalizar
														// depois para release e
														// nao replication -
														// Aparentemetente
														// refatorar para criar
														// classes Release e
														// iteration para manter
	// quantidade de iteracoes por release
	
	private ExperimentationPanel experimentationPanel;
	
	private HashMap queues;

	private SimulationManagerFacade() {
		resultsSimulationMap = new HashMap<>();
		resultsSimulationMapAdaptMe = new TreeMap<>();
		dynamicExperimentationProgramProxy = DynamicExperimentationProgramProxyFactory.newInstance();
	}

	public String getSimulationEndedTime() {
		Scheduler scheduler = dynamicExperimentationProgramProxy.getSimulationManager().getScheduler();
		return (Float.toString(scheduler.getEndclock()));
	}

	public String getSimulationResults() {
		return simulationManager.getSimulationResults();
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
	 * queue and stores it in the array 4 - Print array contents
	 */
	public void printResultadosGlobal() {
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
			System.out.println("nome do experimento..: " + experimento);
			HashMap secondHash = (HashMap) resultadoGlobal.get(experimento);
			Collection<String> chavesNomeFilasSegundoHash = secondHash.keySet();

			for (String queueName : chavesNomeFilasSegundoHash) {
				if (secondHash.get(queueName) instanceof QueueEntry) {
					QueueEntry qe1 = (QueueEntry) secondHash.get(queueName);
					quantity = qe1.deadState.getCount();
					matrizResultados[contadorLinhas][contadorColunas] = quantity; // nao
																					// armazeno
																					// o
																					// nome
																					// do
																					// experimento,
																					// apenas
																					// inteiros
																					// com
																					// #entities
				}
				contadorColunas++;
			}
			contadorLinhas++;
			contadorColunas = 0;
		}
		printResultadosGlobal(matrizResultados);
	}

	private void printResultadosGlobal(int[][] matrizResultados) {
		System.out.println("Printing the mean and sd of entities in each queue and for each replication");
		Set<String> keys = resultadoGlobal.keySet(); // contem o nome de todos
														// experimentos como
														// chave

		HashMap secondHash = (HashMap) resultadoGlobal.get(keys.iterator().next());
		Collection<String> chavesNomeFilasSegundoHash = secondHash.keySet();

		double soma = 0.0;
		StandardDeviation sd = new StandardDeviation();

		Iterator iterator = chavesNomeFilasSegundoHash.iterator();
		double[] numeroEntidadesPorFila = new double[matrizResultados.length];

		for (int j = 0; j < matrizResultados[0].length; j++) { // para uma
																// determinada
																// colunas
																// (celula
																// representa
																// quantidade de
																// entities)
			String fila = (String) iterator.next();
			System.out.println("Fila " + fila);
			for (int i = 0; i < matrizResultados.length; i++) { // somo todas as
																// linhas para a
																// coluna acima
																// (representa
																// experimentos)
				System.out.println("quantity in replication " + (i + 1) + "..: " + matrizResultados[i][j]);
				numeroEntidadesPorFila[i] = matrizResultados[i][j];
				soma = soma + matrizResultados[i][j];
			}
			System.out.println("means of entities in queue " + fila + "..:" + soma / matrizResultados.length);
			System.out.println("Standard deviation..: " + sd.evaluate(numeroEntidadesPorFila));
			soma = 0.0;
			numeroEntidadesPorFila = new double[matrizResultados.length];

		}
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
	
	public String getResultadosCabecalho() {
		
		String resultadoGlobalString = "\n............   NUMBER OF SIMULATION RUNS, MEAN(SD) OF PROJECT DURATION, MEAN(SD) OF ITERATION/RELEASE (ONLY FOR ITERATIVE AND RELEASE BASED PROCESSES)   ............\n";
 		
		resultadoGlobalString+= "\nNumber of simulation runs......................:  "  + getNumberOfSimulationRuns() + "\n";
		
		resultadoGlobalString+= "Number of days.....................................:  " + Math.round(simulationManagerFacade.getAverageNumberOfDays()*100.0)/100.0 +
                "(" + Math.round(simulationManagerFacade.calculateStandardDeviationNumberOfDays()*100.0)/100.0 + ")" + "\n";
 
		if (getSimulationManager().getScheduler().hasRelease()) {
			resultadoGlobalString+= "Number of releases................................:  " + Math.round(simulationManagerFacade.getAverageNumberOfReleases()*100.0)/100.0 +
					"(" + Math.round(simulationManagerFacade.calculateStandardDeviationNumberOfReleases()*100.0)/100.0 + ")" + "\n";
		}
		
		if (getSimulationManager().getScheduler().hasIteration()) {
			resultadoGlobalString+= "Number of iterations per release.............:  " + Math.round(simulationManagerFacade.getAverageNumberOfIterations()/simulationManagerFacade.getAverageNumberOfReleases()*100.0)/100.0 + 
					"(" + Math.round(simulationManagerFacade.calculateStandardDeviationNumberOfReleases()*100.0)/100.0 + ")" + "\n";; // TODO implementar para release  
		}
		
		return resultadoGlobalString;
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

	public double calculateStandardDeviationNumberOfDays() {
		StandardDeviation sd = new StandardDeviation();
		return sd.evaluate(numberOfDaysPerReplication) * 100 / 100;
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

	public SortedMap getResultadoGlobal() {
		return resultadoGlobal;
	}

	public Map<String, String> getResultsSimulationMapAdaptMe() {
		return resultsSimulationMapAdaptMe;
	}

	public void setExperimentationPanel(ExperimentationPanel experimentationPanel) {
		this.experimentationPanel = experimentationPanel;
	}
	
	public void execute(float simulationDuration, int numberReplications, boolean isFromConsole) {
		
		double numberOfDays = 0.0;
		double acumulatedNumberOfDays = 0.0;
		double acumulatedNumberOfUserStories = 0.0;
		double acumulateNumberOfIterations = 0.0;
		double acumulateNumberOfReleases = 0.0;
		
		this.numberOfSimulationRuns = numberReplications;
		this.numberOfDaysPerReplication = new double[numberReplications];
		this.numberOfReleasesPerReplication = new double[numberReplications];
		this.numberOfproducedUserStoriesPerReplication = new double[numberReplications];
		this.numberOfIterationsPerReplication = new double[numberReplications];

		System.out.println("\n\t\t\tDETAILED RESULTS BY REPLICATION - IMPLEMENTED BY PAGLIARES\n");

		for (int i = 0; i < numberReplications; i++) {
			
			dynamicExperimentationProgramProxy = DynamicExperimentationProgramProxyFactory.newInstance();
			dynamicExperimentationProgramProxy.setSimulationDuration(simulationDuration);
			dynamicExperimentationProgramProxy.execute(simulationDuration);
			
			this.simulationManager = (SimulationManager) dynamicExperimentationProgramProxy.getSimulationManager(); // nao funciona no construtor
																						
			// simulationManager.outputSimulationResultsConsoleAsImplementedByWladimir();  
			
			// output to console implemented by Pagliares
			// Imprime numero de dias, armazena a replicacao corrente em um mapa para posterior calculo agregado
				
			
			System.out.println("\nReplication (execution) #" + (i + 1) + "\n");
			
			// Computo do numero de dias da simulacao desta replicacao e armazenamento para posterior calculo da media
			numberOfDays = simulationManager.getScheduler().GetClock() / 480;
			numberOfDaysPerReplication[i] = numberOfDays;											
			acumulatedNumberOfDays += numberOfDays;
			System.out.println("\tProject duration: " + numberOfDays + " days\n"); // 480 minutes = 1 day 
			
			// saida de todos dead states
            printNumberOfEntitiesInEachDeadState();

			// armazena o estado de todos os dead states desta replicacao para o computo da media por dead state no final de todas as replicacoes
			resultadoGlobal.put("run #" + (i + 1), queues);

//			acumulateNumberOfIterations = reportByIteration(acumulateNumberOfIterations, i);
//
//			acumulateNumberOfReleases = reportByRelease(acumulateNumberOfReleases, i);

//			printGlobalResults(numberReplications, acumulatedNumberOfDays, acumulatedNumberOfUserStories,acumulateNumberOfIterations, acumulateNumberOfReleases);

			

			
			if ( !isFromConsole) {
				
				int currentProessAlternativeIndex = simulationFacade.getProcessAlternatives().size() - 1;
				String selectedProcessAlternativeName = simulationFacade.getProcessAlternatives().get(currentProessAlternativeIndex).getName();
				resultsSimulationMap.put(selectedProcessAlternativeName + i, dynamicExperimentationProgramProxy); // armazena replicacoes
				
                 //	String selectedProcessAlternativeName = showResultsPanel.getSelectedProcessAlternativeName();
				resultsSimulationMapAdaptMe.put(selectedProcessAlternativeName + i, getResultadosCabecalho()+ "\n" + 
                        getResultadosGlobalString(experimentationPanel.getMapQueueVariableType()));
			}	 

			houseCleaning();
		}

		
//		System.out.println("GLOBAL RESULTS - STATISTICS OF ALL REPLICATIONS TAKEN TOGETHER");
//		 printResultadosGlobal();
	}

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

	private double reportByRelease(double acumulateNumberOfReleases, int i) {
		if (simulationManager.getScheduler().hasRelease()) {
			System.out.println("number of releases ..: " + simulationManager.getScheduler().getNumberOfReleases());
			acumulateNumberOfReleases += simulationManager.getScheduler().getNumberOfReleases();
			numberOfReleasesPerReplication[i] = simulationManager.getScheduler().getNumberOfReleases();
		} else {
			System.out.println("number of releases ..: " + 0);
			acumulateNumberOfReleases += 0;
			numberOfReleasesPerReplication[i] = 0;
		}
		return acumulateNumberOfReleases;
	}

	private double reportByIteration(double acumulateNumberOfIterations, int i) {
		// Iteration and Release seen as ACD activities that does not advance the simulation clock
		if (simulationManager.getScheduler().hasIteration()) {
			System.out.println("number of iterations ..: " + simulationManager.getScheduler().getNumberOfIterations());
			numberOfIterationsPerReplication[i] = simulationManager.getScheduler().getNumberOfIterations();
			acumulateNumberOfIterations += simulationManager.getScheduler().getNumberOfIterations();
			 System.out.println("Displaying results by iteration");
			 printObserversReportByIteration(simulationManager.getSimulationResultsByIteration());
		} else {
			System.out.println("number of iterations ..: " + 0);
			numberOfIterationsPerReplication[i] = 0;
			acumulateNumberOfIterations += 0;
		}
		return acumulateNumberOfIterations;
	}

	private void printNumberOfEntitiesInEachDeadState() {
		// Show the number of entities in all dead states
		System.out.println("\tPrinting the number of entities in each dead state");
		queues = simulationManager.getQueues();
		Set keys = queues.keySet();

		for (Object queueName : keys) {
			System.out.println("\n\t\tQueue name : " + queueName);
			QueueEntry qe = (QueueEntry) queues.get(queueName);

			// ambas saidas abaixo retornam a variavel count
			System.out.println("\t\tNunber of entities in queue via getCount: " + qe.deadState.getCount());
			// System.out.println("numero de entidadas na fila: via ObsLength, basta mudar simobj para o novo nome" + qe.SimObj.getCount());
		}
	}

	private void houseCleaning() {
		dynamicExperimentationProgramProxy.getSimulationManager().getScheduler().Stop();
		dynamicExperimentationProgramProxy.getSimulationManager().getScheduler().Clear();

		dynamicExperimentationProgramProxy = null;
//		Activity.counter = 0;
		Activity.isBeginOfSimulation = true;
		ActiveEntry.lastid = 0; // se nao fizer isso, a cada replicacao os numeros de atividades vao so aumentando. Tem impacto na classe Scheduler
								// metodo run(), onde tento controlar o inicio de uma atividade via Math.random quando mais de uma atividade
		                        // tem a mesma atividade com predecessora em SPEM ou em outras palavras, quando mais de uma atividade tem como 
		                        // a mesma fila como previous state.
	}

}
