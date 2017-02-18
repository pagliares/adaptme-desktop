

import adaptme.DynamicExperimentationProgramProxy;
import adaptme.facade.SimulationManagerFacade;
import simula.manager.SimulationManager;

public class TestSimulationFromConsole {

	public static void main(String[] args) {
		
		SimulationManagerFacade simulationManagerFacade = SimulationManagerFacade.getSimulationManagerFacade();
		simulationManagerFacade.execute(10000, 1, true);
		
//		DynamicExperimentationProgramProxy dynamicExperimentationProgramProxy = new DynamicExperimentationProgramProxy();
//		SimulationManager simulationManager = (SimulationManager) dynamicExperimentationProgramProxy.getSimulationManager(); // nao funciona no construtor
		
		// output to console implemented by Pagliares
		// Imprime numero de dias, armazena a replicacao corrente em um mapa para posterior calculo agregado

		
//		dynamicExperimentationProgramProxy.executeFromConsole(20000, 1);
//		simulationManager.OutputSimulationResultsConsole(); 
		
//		dynamicExperimentationProgramProxy.getSimulationManager().getScheduler().Stop();
//		dynamicExperimentationProgramProxy.getSimulationManager().getScheduler().Clear();

	}

}
