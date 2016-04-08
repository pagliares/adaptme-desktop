package adaptme.facade;

import java.util.HashMap;
import java.util.Iterator;

import adaptme.DynamicExperimentationProgramProxy;
import adaptme.DynamicExperimentationProgramProxyFactory;
import adaptme.IDynamicExperimentationProgramProxy;
import simula.Scheduler;
import simula.manager.*;
 
public class SimulationManagerFacade {
	private SimulationManager simulationManager;
	private IDynamicExperimentationProgramProxy epp;
	
	public SimulationManagerFacade() {
		
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
			printSimulationEndedTime();
			simulationManager.OutputSimulationResultsConsole();
			
		}
	}
	
	public static void main(String [] args) {
//		SimulationManagerFacade hbcFacade = new SimulationManagerFacade();
//		hbcFacade.printSimulationEndedTime();
	}
}
