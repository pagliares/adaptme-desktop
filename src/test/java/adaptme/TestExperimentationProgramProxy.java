package adaptme;

import adaptme.facade.SimulationManagerFacade;
import simula.manager.SimulationManager;

public class TestExperimentationProgramProxy {

	public static void main(String[] args) {
		SimulationManagerFacade simulationManagerFacade = SimulationManagerFacade.getSimulationManagerFacade();
		simulationManagerFacade.executeFromConsole(20000, 1);
		 

	}

}
