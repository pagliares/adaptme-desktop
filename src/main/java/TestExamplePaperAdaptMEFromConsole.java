

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

import adaptme.DynamicExperimentationProgramProxy;
import adaptme.IDynamicExperimentationProgramProxy;
import adaptme.facade.SimulationManagerFacade;
import adaptme.ui.window.perspective.VariableType;
import simula.manager.QueueEntry;
import simula.manager.SimulationManager;

public class TestExamplePaperAdaptMEFromConsole {

	public static void main(String[] args) {
	
		// System.out.println("\n\n---------------------------------   EXECUTING N REPLICATIONS OF THE SIMULATIONS   ---------------------------------\n");
		int quantityOfReplications = 10;
		SimulationManagerFacade simulationManagerFacade = SimulationManagerFacade.getSimulationManagerFacade();
 		simulationManagerFacade.execute(86400, quantityOfReplications, true); //  (86400 = 180 business days)
		System.out.println();
		
		//System.out.println("\n\n----------------------------------------   RESULTS BY REPLICATION   -------------------------------------------\n");

		Map<String, IDynamicExperimentationProgramProxy> results  = simulationManagerFacade.getResultsSimulationMap();
		
		Set<String> set = results.keySet();
		
		for (String replication: set){
			System.out.println(replication);
			System.out.println();
			IDynamicExperimentationProgramProxy dynamicExperimentationProgramProxy = results.get(replication);
			SimulationManager simulationManager = (SimulationManager) dynamicExperimentationProgramProxy.getSimulationManager();
			
			HashMap queues = simulationManager.getQueues();
			double projectDuration = Math.round(simulationManager.getScheduler().getClockOnEnding()/480) * 10.0/10.0;
 			System.out.println("\t[Project duration]  " + (projectDuration) + " days");
			System.out.println();
			simulationManagerFacade.printNumberOfEntitiesInEachDeadState(queues);
 		}
		
		System.out.println("\n\n----------------------------------------   GLOBAL RESULTS   -------------------------------------------\n");
		
		double meanNumberOfDays = simulationManagerFacade.calculateMeanNumberOfDays();
		double sdNumberOfDays = simulationManagerFacade.calculateStandardDeviationNumberOfDays();
		System.out.println("Mean (sd) number of days..." + meanNumberOfDays + "(" + sdNumberOfDays + ")");
			
		// Calculating the mean(sd) number of user stories produced. Notice that a produced user story may be in more than one dead state
					// (i.e. it can be on q4 - input dead state of activity END_iteration, q5 - input dead state of activity END_Release
					// q6 - output dead state of activity END_Release)
					// matrixWithResults (lines) = number of entities in each queue  matrixWithResults (columns) =  experiments
					int [][] matrixWithResults = simulationManagerFacade.createMatrixWithResultsOfAllReplications();
					
		 			
					 
					double meanQuantityOfEntitiesInQ4 = printMeanSDOfEntities(simulationManagerFacade, matrixWithResults,"q4");
					double meanQuantityOfEntitiesInQ5 = printMeanSDOfEntities(simulationManagerFacade, matrixWithResults,"q5");
					double meanQuantityOfEntitiesInQ6 = printMeanSDOfEntities(simulationManagerFacade, matrixWithResults,"q6");
					System.out.println("ljfdlskfjlkf   "  + (meanQuantityOfEntitiesInQ4 + meanQuantityOfEntitiesInQ5 + meanQuantityOfEntitiesInQ6));
					
					 
		 		   
 	}
	
	private static double printMeanSDOfEntities(SimulationManagerFacade simulationManagerFacade,
			int[][] matrixWithResults, String deadStateName) {
		double meanQuantityOfEntitiesInQ4 = simulationManagerFacade.calculateMeanNumberOfEntitiesInADeadState(matrixWithResults, deadStateName);
		double sdQuantityOfEntitiesInQ4 = simulationManagerFacade.calculateStandardDeviationNumberOfEntitiesInADeadState(matrixWithResults, deadStateName);
		System.out.println("Mean (sd) number of entities in dead state " + deadStateName + "  " +  
							meanQuantityOfEntitiesInQ4 + "(" + sdQuantityOfEntitiesInQ4 + ")");
		return meanQuantityOfEntitiesInQ4;
	}
}
