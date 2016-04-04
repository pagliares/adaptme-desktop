package adaptme;

import simula.manager.SimulationManager;

public class DynamicExperimentationProgramProxy implements IDynamicExperimentationProgramProxy {
	private SimulationManager simulationManager;
	
	public DynamicExperimentationProgramProxy() {
		this.simulationManager = new SimulationManager();
	}
	
	public void execute() {
		
	}
	
	public void setSimulationManager(SimulationManager simulationManager) {
		this.simulationManager = simulationManager;
	}

	public SimulationManager getSimulationManager() {
		return simulationManager;
	}

	// Skeleton empty template
 
}
