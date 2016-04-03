package adaptme.facade;

import java.util.HashMap;
import java.util.Iterator;

import adaptme.ExperimentationProgramProxy;
import simula.Scheduler;
import simula.manager.*;
 
public class SimulationManagerFacade {
	private SimulationManager man;
	
	public void printSimulationEndedTime() {
		Scheduler scheduler = man.getScheduler();
		System.out.println("Statistics collected from instant " + man.getResettime());
		System.out.println(" during " + (scheduler.GetClock() - man.getResettime()) + " time units.");
	}
	
	public void printOneObserver() {
		Iterator it;
		  HashMap observers = man.getObservers();
		  it = observers.values().iterator();
		   ObserverEntry observerEntry = (ObserverEntry)it.next();
		   man.printObserversReport(observerEntry);
	}
	
	
	public void execute(int numberReplications) {
		
		for (int i =0; i < numberReplications; i++) {
			ExperimentationProgramProxy epp = new ExperimentationProgramProxy();
			System.out.println("Execution #" + (i+1));
			epp.execute();
			epp = null;
		}
	}
	
	public static void main(String [] args) {
		SimulationManagerFacade hbcFacade = new SimulationManagerFacade();
//		hbcFacade.printSimulationEndedTime();
	}
}
