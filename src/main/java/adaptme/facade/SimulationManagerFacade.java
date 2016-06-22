package adaptme.facade;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import adaptme.DynamicExperimentationProgramProxy;
import adaptme.DynamicExperimentationProgramProxyFactory;
import adaptme.IDynamicExperimentationProgramProxy;
import adaptme.ui.window.perspective.ExperimentationPanel;
import adaptme.ui.window.perspective.ShowResultsPanel;
import adaptme.ui.window.perspective.ShowResultsTableModel;
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
			
			
//		    String selectedProcessAlternativeName = showResultsPanel.getSelectedProcessAlternativeName();
		    int currentProessAlternativeIndex = simulationFacade.getProcessAlternatives().size()-1;
            String selectedProcessAlternativeName = simulationFacade.getProcessAlternatives().get(currentProessAlternativeIndex).getName();

			resultsSimulationMap.put(selectedProcessAlternativeName+i, epp);  // um por REPLICACAO?
		}
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
}
