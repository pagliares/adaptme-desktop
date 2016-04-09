package adaptme.facade;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import adaptme.DynamicExperimentationProgramProxy;
import adaptme.DynamicExperimentationProgramProxyFactory;
import adaptme.IDynamicExperimentationProgramProxy;
import adaptme.ui.window.perspective.ShowResultsTableModel;
import simula.Scheduler;
import simula.manager.*;
 
public class SimulationManagerFacade {
	
	private static SimulationManagerFacade simulationManagerFacade = new SimulationManagerFacade();
	private Map<String, IDynamicExperimentationProgramProxy> resultsSimulationMap;
	private SimulationManager simulationManager;
	private IDynamicExperimentationProgramProxy epp;
//	private ShowResultsTableModel showResultsTableModel;
	
	private SimulationManagerFacade() {
		resultsSimulationMap = new HashMap<>();
		epp = DynamicExperimentationProgramProxyFactory.newInstance();
	}
	
	public void printSimulationEndedTime() {
		Scheduler scheduler = epp.getSimulationManager().getScheduler();
		System.out.println("Statistics collected from instant " + simulationManager.getResettime());
		System.out.println(" during " + (scheduler.getEndclock() - simulationManager.getResettime()) + " time units.");
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
			printSimulationEndedTime(); // tirar
			simulationManager.OutputSimulationResultsConsole(); // tirar saida histograms report
			resultsSimulationMap.put("process name"+i, epp);  // um por REPLICACAO?
			
			
			
		}
	}
	
	public Map<String, IDynamicExperimentationProgramProxy> getResultsSimulationMap() {
		return resultsSimulationMap;
	}
	
	public static SimulationManagerFacade getSimulationManagerFacade() {
		return simulationManagerFacade;
	}

	public static void main(String [] args) {
//		SimulationManagerFacade hbcFacade = new SimulationManagerFacade();
//		hbcFacade.printSimulationEndedTime();
	}
}
