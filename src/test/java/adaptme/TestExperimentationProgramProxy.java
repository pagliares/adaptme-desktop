package adaptme;

import simula.manager.SimulationManager;

public class TestExperimentationProgramProxy {

	public static void main(String[] args) {
		IDynamicExperimentationProgramProxy experimentationProgramProxy = new DynamicExperimentationProgramProxy();
		experimentationProgramProxy.execute(500);

	}

}
