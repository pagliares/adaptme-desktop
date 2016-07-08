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

import adaptme.DynamicExperimentationProgramProxy;
import adaptme.DynamicExperimentationProgramProxyFactory;
import adaptme.IDynamicExperimentationProgramProxy;
import adaptme.ui.window.perspective.ExperimentationPanel;
import adaptme.ui.window.perspective.ShowResultsPanel;
import adaptme.ui.window.perspective.ShowResultsTableModel;
import executive.queue.Queue;
import model.spem.SimulationFacade;
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
	
	
	public void execute(int numberReplications) {
		
		for (int i =0; i < numberReplications; i++) {
			 
			epp = DynamicExperimentationProgramProxyFactory.newInstance();
			System.out.println("Execution #" + (i+1));
			epp.execute();
			this.simulationManager = (SimulationManager)epp.getSimulationManager(); // nao funciona no construtor
			simulationManager.OutputSimulationResultsConsole(); // tirar saida histograms report
			HashMap queues = simulationManager.getQueues();
			resultadoGlobal.put("run #" + (i+1), queues);
			Set keys = queues.keySet();
			for (Object o: keys) {
				//QueueEntry qe = simulationManager.GetQueue("User story input queue");
				System.out.println("\nQueue name : " + o);
				QueueEntry qe = (QueueEntry)queues.get(o);
				// ambas saidas abaixo retornam a variavel count
				System.out.println("Nunber of entities in queue via getCount: " + qe.SimObj.getCount());
//				System.out.println("numero de entidadas na fila: via ObsLength" + qe.SimObj.getCount());
				
				//System.out.println(qe.intialQuantity);
				//System.out.println("numero final entidades : " + qe.SimObj.getCount());
			}
			
			 
			System.out.println("number of iterations ..: " + simulationManager.getScheduler().getNumberOfIterations());
			System.out.println("number of releases ..: " + simulationManager.getScheduler().getNumberOfReleases());
			
			System.out.println("Displaying results by iteration");
			printObserversReportByIteration(simulationManager.getSimulationResultsByIteration());
			
			System.out.println("\nDisplaying global results");
			
			
			
			
//		    String selectedProcessAlternativeName = showResultsPanel.getSelectedProcessAlternativeName();
		    int currentProessAlternativeIndex = simulationFacade.getProcessAlternatives().size()-1;
            String selectedProcessAlternativeName = simulationFacade.getProcessAlternatives().get(currentProessAlternativeIndex).getName();

			resultsSimulationMap.put(selectedProcessAlternativeName+i, epp);  // armazena replicacoes
			epp = null;
		}
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
					 System.out.println("QueuName..:" + chave1  + "  "  + "inner queue name..:  "  + qe1.getName() + "  number of entities..:" + qe1.SimObj.getCount());
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
					 quantity = qe1.SimObj.getCount();
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
		System.out.println("Imprimindo a matriz");
		Set<String>  keys = resultadoGlobal.keySet();  // contem o nome de todos experimentos como chave
		
		HashMap secondHash = (HashMap)resultadoGlobal.get(keys.iterator().next());
		Collection<String> chavesNomeFilasSegundoHash = secondHash.keySet();
		
		double soma = 0.0;
		Iterator iterator = chavesNomeFilasSegundoHash.iterator();
		
		for (int j=0; j < matrizResultados[0].length; j++) { // para uma determinada colunas (representa quantidade de entities)
			System.out.println("Fila " + iterator.next());
			
			for (int i=0; i < matrizResultados.length; i++) { // somo todas as linhas para a coluna acima (representa experimentos)
				System.out.println("quantity: " + matrizResultados[i][j]);
				soma = soma + matrizResultados[i][j];	
			}	
			System.out.println("means of entities in queue  X " + soma/matrizResultados.length);
			soma = 0.0;
		}
	}
}
