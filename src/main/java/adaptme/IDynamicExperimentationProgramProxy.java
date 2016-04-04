package adaptme;

import simula.manager.SimulationManager;

public interface IDynamicExperimentationProgramProxy {

	void execute();
	
	void setSimulationManager(SimulationManager simulationManager);
		 
	SimulationManager getSimulationManager();
		 
	

}