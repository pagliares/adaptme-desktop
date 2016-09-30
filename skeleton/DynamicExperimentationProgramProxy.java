package adaptme;

import simula.manager.SimulationManager;

public class DynamicExperimentationProgramProxy implements IDynamicExperimentationProgramProxy {
	private SimulationManager simulationManager;
	private float simulationDuration;

	public DynamicExperimentationProgramProxy() {
		this.simulationManager = new SimulationManager();
	}

	public void execute(float simulationDuration) {

	}

	public void setSimulationManager(SimulationManager simulationManager) {
		this.simulationManager = simulationManager;
	}

	public SimulationManager getSimulationManager() {
		return simulationManager;
	}

	public void setSimulationDuration(float simulationDuration) {
		this.simulationDuration = simulationDuration;
	}

	public float getSimulationDuration() {
		return simulationDuration;
	}

	// Skeleton empty template

}
