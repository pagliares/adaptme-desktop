

import java.util.ArrayList;
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

public class TestCaseStudyBaselineAndAlternativesProcesses {

	public static void main(String[] args) {
	
		int quantityOfReplications = 500;
		
		SimulationManagerFacade simulationManagerFacade = SimulationManagerFacade.getSimulationManagerFacade();
		System.out.println("It takes about 50 seconds to finish 50 replications of the simulation experiment");

		// Process alternative  A : 30 days with samples of size (50 -> 2 wps, 335 -> x wps, 
 		simulationManagerFacade.execute(30, quantityOfReplications, true);  
		System.out.println();
		
		Map<String, IDynamicExperimentationProgramProxy> results  = simulationManagerFacade.getResultsSimulationMap();
		Set<String> set = results.keySet();
		
		List<Integer> l = new ArrayList<>();
		
		
		for (String replication: set){
			System.out.println(replication);
			System.out.println();
			IDynamicExperimentationProgramProxy dynamicExperimentationProgramProxy = results.get(replication);
			SimulationManager simulationManager = (SimulationManager) dynamicExperimentationProgramProxy.getSimulationManager();
			
			HashMap queues = simulationManager.getQueues();
//			double projectDuration = Math.round(simulationManager.getScheduler().getClockOnEnding()/480) * 10.0/10.0;
			double projectDuration = simulationManager.getScheduler().getClockOnEnding();

 			System.out.println("\t[Project duration]  " + (projectDuration) + " days");
			System.out.println();
			simulationManagerFacade.printNumberOfEntitiesInEachDeadState(queues);
			
			
			l.add(simulationManagerFacade.getQuantityOfEntitiesInFinalDeadStateForPaper(queues));
			
			
 		}
		
		System.out.println("Printing 50 quantities for this replication");
		for (Integer i: l)
			System.out.println(i);
		System.out.println("Finished Printing 50 quantities for this replication");
		
		System.out.println("\n\n----------------------------------------   GLOBAL RESULTS   -------------------------------------------\n");
		
		double meanNumberOfDays = simulationManagerFacade.calculateMeanNumberOfDays();
		double sdNumberOfDays = simulationManagerFacade.calculateStandardDeviationNumberOfDays();
		System.out.println("Mean (sd) number of days..." + meanNumberOfDays * 480 + "(" + sdNumberOfDays * 480 + ")");
			
		// Calculating the mean(sd) number of work products produced. 
		// matrixWithResults (lines) = number of entities in each queue  matrixWithResults (columns) =  experiments
		int [][] matrixWithResults = simulationManagerFacade.createMatrixWithResultsOfAllReplications();
								 			
 		double meanQuantityOfEntitiesInQ5 = printMeanSDOfEntities(simulationManagerFacade, matrixWithResults,"q5");				 		 		   
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
