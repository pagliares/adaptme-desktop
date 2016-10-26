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

import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

import adaptme.DynamicExperimentationProgramProxy;
import adaptme.DynamicExperimentationProgramProxyFactory;
import adaptme.IDynamicExperimentationProgramProxy;
import adaptme.ui.window.perspective.ExperimentationPanel;
import adaptme.ui.window.perspective.ShowResultsPanel;
import adaptme.ui.window.perspective.ShowResultsTableModel;
import executive.queue.Queue;
import model.spem.SimulationFacade;
import simula.Activity;
import simula.Scheduler;
import simula.manager.*;
 
public class SimulationManagerFacade {
	
	private static SimulationManagerFacade simulationManagerFacade = new SimulationManagerFacade();
	private Map<String, IDynamicExperimentationProgramProxy> resultsSimulationMap;
	private SimulationManager simulationManager;
	private IDynamicExperimentationProgramProxy epp;
 	private ShowResultsPanel showResultsPanel;
 	private SimulationFacade simulationFacade;
 	
 	private SortedMap resultadoGlobal = new TreeMap();
 	private double averageNumberOfDays;
 	private double averageNumberOfImplementedUserStories;
 	private double averageNumberOfReleases;
 	private double averageNumberOfIterations;
 	private double numberOfSimulationRuns;
 	private double [] numberOfDaysPerReplication; // TODO generalizar depois 
 	private double [] numberOfproducedUserStoriesPerReplication; // TODO generalizar depois
 	private double [] numberOfReleasesPerReplication; // TODO generalizar depois
 	private double [] numberOfIterationsPerReplication; // TODO generalizar depois para release e nao replication - Aparentemetente refatorar para criar classes Release e iteration para manter
 	  												// quantidade de iteracoes por release
 	
	private SimulationManagerFacade() {
		resultsSimulationMap = new HashMap<>();
		epp = DynamicExperimentationProgramProxyFactory.newInstance();
	}
	
	public String getSimulationEndedTime() {
		Scheduler scheduler = epp.getSimulationManager().getScheduler();
		return (Float.toString(scheduler.getEndclock()));
	}
	
	public String getSimulationResults() {
		return simulationManager.getSimulationResults();
	}
	
	public void printOneObserver() {
		Iterator it;
		  HashMap observers = simulationManager.getObservers();
		  it = observers.values().iterator();
		   ObserverEntry observerEntry = (ObserverEntry)it.next();
		   simulationManager.printObserversReport(observerEntry);
	}
	
	
	public void execute(float simulationDuration, int numberReplications) {
		double acumulatedNumberOfDays = 0.0;
		double acumulatedNumberOfUserStories = 0.0;
		double acumulateNumberOfIterations = 0.0;
		double acumulateNumberOfReleases = 0.0;
		this.numberOfSimulationRuns = numberReplications;
		numberOfDaysPerReplication = new double[numberReplications];
		numberOfReleasesPerReplication = new double[numberReplications];
		numberOfproducedUserStoriesPerReplication = new double[numberReplications];
		numberOfIterationsPerReplication = new double[numberReplications];
		 
		
		for (int i =0; i < numberReplications; i++) {
			epp = DynamicExperimentationProgramProxyFactory.newInstance();
			
			epp.setSimulationDuration(simulationDuration);
			epp.execute(simulationDuration);
			this.simulationManager = (SimulationManager)epp.getSimulationManager(); // nao funciona no construtor
			
			
			// output to console implemented by Pagliares
			// Imprime numero de dias, armazena a replicacao corrente em um mapa para posterior calculo agregado
			
			simulationManager.OutputSimulationResultsConsole(); // tirar saida histograms report
			System.out.println("Execution #" + (i+1));
			double numberOfDays = simulationManager.getScheduler().GetClock() / 480;
			numberOfDaysPerReplication[i] = numberOfDays;
			System.out.println("Project duration: " + numberOfDays + " days");  // 480 minutes = 1 day
			acumulatedNumberOfDays+= numberOfDays;
			
			HashMap queues = simulationManager.getQueues();
			Set keys = queues.keySet();
			
			for (Object o: keys) {
				//	QueueEntry qe1 = simulationManager.GetQueue("User story input queue");
				System.out.println("\nQueue name : " + o);
				QueueEntry qe = (QueueEntry)queues.get(o);
				
				// TODO so para o o artigo. Precisa ser generalizado
				if (qe.GetId().equalsIgnoreCase("Implemented User stories")) {
					acumulatedNumberOfUserStories+= qe.deadState.getCount();
					numberOfproducedUserStoriesPerReplication[i] = qe.deadState.getCount();
				}
				
				// ambas saidas abaixo retornam a variavel count
				System.out.println("Nunber of entities in queue via getCount: " + qe.deadState.getCount());
				// System.out.println("numero de entidadas na fila: via ObsLength, basta mudar simobj para o novo nome" + qe.SimObj.getCount()); 
			}
			
			resultadoGlobal.put("run #" + (i+1), queues);
			
			
			System.out.println("number of iterations ..: " + simulationManager.getScheduler().getNumberOfIterations());
			numberOfIterationsPerReplication[i] = simulationManager.getScheduler().getNumberOfIterations();
			
			acumulateNumberOfIterations+= simulationManager.getScheduler().getNumberOfIterations();
			
			System.out.println("number of releases ..: " + simulationManager.getScheduler().getNumberOfReleases());
			acumulateNumberOfReleases+= simulationManager.getScheduler().getNumberOfReleases();
			numberOfReleasesPerReplication[i] = simulationManager.getScheduler().getNumberOfReleases();
			
			System.out.println("Displaying results by iteration");
			printObserversReportByIteration(simulationManager.getSimulationResultsByIteration());
			
			System.out.println("\nDisplaying global results");
			
//		    String selectedProcessAlternativeName = showResultsPanel.getSelectedProcessAlternativeName();
		    int currentProessAlternativeIndex = simulationFacade.getProcessAlternatives().size()-1;
            String selectedProcessAlternativeName = simulationFacade.getProcessAlternatives().get(currentProessAlternativeIndex).getName();

			resultsSimulationMap.put(selectedProcessAlternativeName+i, epp);  // armazena replicacoes
			epp.getSimulationManager().getScheduler().Stop();
			epp.getSimulationManager().getScheduler().Clear();
			
			epp = null;
			Activity.counter = 0;
		}
		
		this.averageNumberOfDays = acumulatedNumberOfDays/numberReplications * 10/10;
		this.averageNumberOfIterations = acumulateNumberOfIterations/numberReplications;
		this.averageNumberOfImplementedUserStories = acumulatedNumberOfUserStories/numberReplications;
		this.averageNumberOfReleases = acumulateNumberOfReleases/numberReplications;
 		
		System.out.println("Average number of days ..: " + averageNumberOfDays);
		printResultadosGlobal();
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
		 Collection<String>  chaves = firstHash.keySet();
		 
		 for (String chave: chaves) {
			 System.out.println(chave);
			 HashMap secondHash = (HashMap)firstHash.get(chave);
			 Collection<String> chavesSegundoHash = secondHash.keySet();
			 for (String chave1: chavesSegundoHash) {
				 if (secondHash.get(chave1) instanceof QueueEntry) {
					 QueueEntry qe1 = (QueueEntry)secondHash.get(chave1);
					 System.out.println("QueuName..:" + chave1  + "  "  + "inner queue name..:  "  + qe1.getName() + "  number of entities..:" + qe1.deadState.getCount());
				 } else if (secondHash.get(chave1) instanceof ObserverEntry) {
					 ObserverEntry oe1 = (ObserverEntry)secondHash.get(chave1);
					 System.out.println("Chave second hash..:" + chave1  + "  "  + "value (queue name) ..:  "  + oe1);
				 }
			 }
			 
		 }
	}
		
	/** 
	 * Algorithm
	 * 		1 - Determines the dimension of the bidimensional array of results
	 * 		2-  Creates the two-dimension array
	 * 		3 - For each experiment in resultadoGlobalSimulacao
	 * 			3.1 - extract the quantity of entities in each queue and stores it in the array
	 * 		4 - Print array contents
	 */
	public void printResultadosGlobal() {  
		// quatro linhas abaixo apenas para descobrir o numero de linhas e  colunas da matriz
		Set<String>  keys = resultadoGlobal.keySet();  // contem o nome de todos experimentos como chave
		int numeroExperimentos = keys.size();
		HashMap hm = (HashMap)resultadoGlobal.get(keys.iterator().next());
		Set<String> chaves = hm.keySet();
		int numeroFilas = chaves.size();
		int [][] matrizResultados = new int[numeroExperimentos][numeroFilas];
		int soma = 0;
		int quantity = 0;
		int contadorLinhas = 0;   // number of entities in each queue
		int contadorColunas = 0; // experimentos
		
		 for (String experimento: keys) {   
			 System.out.println("nome do experimento..: " + experimento);
			 HashMap secondHash = (HashMap)resultadoGlobal.get(experimento);
			 Collection<String> chavesNomeFilasSegundoHash = secondHash.keySet();
			 
			 for (String queueName: chavesNomeFilasSegundoHash) {
				  if (secondHash.get(queueName) instanceof QueueEntry) {
					 QueueEntry qe1 = (QueueEntry)secondHash.get(queueName);
					 quantity = qe1.deadState.getCount();
					 matrizResultados[contadorLinhas][contadorColunas] = quantity; // nao armazeno o nome do experimento, apenas inteiros com #entities
				 }  
				  contadorColunas++;
			 }		  
			 contadorLinhas++;
			 contadorColunas = 0;
		 }
		 printResultadosGlobal(matrizResultados);	 
	}
	
	private void printResultadosGlobal(int [][] matrizResultados) {
		System.out.println("Printing the mean and sd of entities in each queue and for each replication");
		Set<String>  keys = resultadoGlobal.keySet();  // contem o nome de todos experimentos como chave
		
		HashMap secondHash = (HashMap)resultadoGlobal.get(keys.iterator().next());
		Collection<String> chavesNomeFilasSegundoHash = secondHash.keySet();
		
		double soma = 0.0;
		StandardDeviation sd = new StandardDeviation();
	
		Iterator iterator = chavesNomeFilasSegundoHash.iterator();
		double [] numeroEntidadesPorFila = new double[matrizResultados.length];
		
		for (int j=0; j < matrizResultados[0].length; j++) { // para uma determinada colunas (celula representa quantidade de entities)
			String fila = (String)iterator.next();
			System.out.println("Fila " + fila);
			for (int i=0; i < matrizResultados.length; i++) { // somo todas as linhas para a coluna acima (representa experimentos)
				System.out.println("quantity in replication " + (i+1) + "..: " + matrizResultados[i][j]);
				numeroEntidadesPorFila[i] = matrizResultados[i][j];
				soma = soma + matrizResultados[i][j];	
			}	
			System.out.println("means of entities in queue " + fila + "..:" + soma/matrizResultados.length);
			System.out.println("Standard deviation..: " + sd.evaluate(numeroEntidadesPorFila));
			soma = 0.0;
			numeroEntidadesPorFila = new double[matrizResultados.length];
			
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
	
	public double calculateStandardDeviationNumberOfDays() {
		StandardDeviation sd = new StandardDeviation();
		return sd.evaluate(numberOfDaysPerReplication) * 100/100;
	}
	
	public double calculateStandardDeviationUserStoriesProducede() {
		StandardDeviation sd = new StandardDeviation();
		return sd.evaluate(numberOfproducedUserStoriesPerReplication) * 100/100;
	}
	
	public double calculateStandardDeviationNumberOfReleases() {
		StandardDeviation sd = new StandardDeviation();
		return sd.evaluate(numberOfReleasesPerReplication) * 100/100;
	}
}
