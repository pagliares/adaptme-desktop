package adaptme;

import simula.manager.SimulationManager;

public class TestExperimentationProgramProxy {

	public static void main(String[] args) {
		SimulationManager manager = new SimulationManager();
		IDynamicExperimentationProgramProxy experimentationProgramProxy = new DynamicExperimentationProgramProxy(manager);
		experimentationProgramProxy.execute();

	}

}
