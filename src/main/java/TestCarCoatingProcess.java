

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

public class TestCarCoatingProcess {

	public static void main(String[] args) {
	
		// System.out.println("\n\n---------------------------------   EXECUTING N REPLICATIONS OF THE SIMULATIONS   ---------------------------------\n");
		int quantityOfReplications = 1;
		SimulationManagerFacade simulationManagerFacade = SimulationManagerFacade.getSimulationManagerFacade();
	 
		simulationManagerFacade.execute(144000, quantityOfReplications, true); // PAINTING/COATING  (144000 = 300 days)
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
			printReportForActivitiesPhasesMilestonesIterations(simulationManagerFacade);
		}
		
		System.out.println("\n\n----------------------------------------   GLOBAL RESULTS   -------------------------------------------\n");
		
		double meanNumberOfDays = simulationManagerFacade.calculateMeanNumberOfDays();
		double sdNumberOfDays = simulationManagerFacade.calculateStandardDeviationNumberOfDays();
		System.out.println("Mean (sd) number of days..." + meanNumberOfDays + "(" + sdNumberOfDays + ")");
		
		Map<String, Integer> mapWithAcumulatedNumberOfActivities = simulationManagerFacade.getMapWithAcumulatedActiviitesResults();
		Set<String> keys1 = mapWithAcumulatedNumberOfActivities.keySet();
		
		for (String key: keys1) {
			System.out.println("Mean (sd) number of ACTIVITY named " + "\'"+ key + "\' " + (double)mapWithAcumulatedNumberOfActivities.get(key)/quantityOfReplications);
		}	
	}

	private static double printMeanSDOfEntities(SimulationManagerFacade simulationManagerFacade,
			int[][] matrixWithResults, String deadStateName) {
		double meanQuantityOfEntitiesInQ4 = simulationManagerFacade.calculateMeanNumberOfEntitiesInADeadState(matrixWithResults, deadStateName);
		double sdQuantityOfEntitiesInQ4 = simulationManagerFacade.calculateStandardDeviationNumberOfEntitiesInADeadState(matrixWithResults, deadStateName);
		System.out.println("Mean (sd) number of entities in dead state " + deadStateName + "  " +  
							meanQuantityOfEntitiesInQ4 + "(" + sdQuantityOfEntitiesInQ4 + ")");
		return meanQuantityOfEntitiesInQ4;
	}
	
	private static void printReportForActivitiesPhasesMilestonesIterations(SimulationManagerFacade simulationManagerFacade) {		
		System.out.println("\n\t[Activities] ");
		simulationManagerFacade.printActivityResults();
		
		System.out.println("\n\t[Phases] ");
		simulationManagerFacade.printPhaseResults();
		
		System.out.println("\n\t[Milestones] ");
		simulationManagerFacade.printMilestoneResults();
		
		System.out.println("\n\t[Iterations] ");
		simulationManagerFacade.printIterationAndReleaseResults(); // Releaseses are treated as iterations in SPEM 2.0

		System.out.println();
		
	}

	private static void printMeanAndStandardDeviationNumberOfDays(SimulationManagerFacade simulationManagerFacade) {
		System.out.println("\nMean(sd) number of days..: " + Math.round(simulationManagerFacade.calculateMeanNumberOfDays() * 100.00)/100.0 + 
				          " (" + Math.round(simulationManagerFacade.calculateStandardDeviationNumberOfDays() * 100.00)/100.0 + ")" );
 	}

	private static void outputSimulationResultsConsoleAsImplementedByWladimir(SimulationManagerFacade simulationManagerFacade) {
		//		 Simulation Report
		//		 Simulation ended at time 0.0
		//		 Statistics collected from instant 0.0 during 0.0 time units.
		//		           Observers' report
		//		 ----------------------------------------------------------------------
		//		 Report from observer Task B observer 1
		//		 Statistics summary:
		//		 Active state idle time (or inter-arrival time for Generate states):
		//		 Average: NaN StdDev: 0.0 Variance: 0.0
		//		  Minimum: 3.4028235E38 Maximum: 0.0 Observations: 0
				SimulationManager simulationManager = simulationManagerFacade.getSimulationManager();
		 		simulationManager.outputSimulationResultsConsoleAsImplementedByWladimir();
	}

	private static void printStyle10(SimulationManagerFacade simulationManagerFacade) {
 		System.out.println("mean number of days...: " + simulationManagerFacade.getAverageNumberOfDays());
		System.out.println("mean number of implemented user stories...: " + simulationManagerFacade.getAverageNumberOfImplementedUserStories());
		System.out.println("mean number of iterations...: " + simulationManagerFacade.getAverageNumberOfIterations());
		System.out.println("mean number of releases...: " + simulationManagerFacade.getAverageNumberOfReleases());
	}

	private static void printStyle9(SimulationManagerFacade simulationManagerFacade) {
		// NULL pointer exception in the line below: dynamicExperimentationProgramProxy
		System.out.println(simulationManagerFacade.getSimulationEndedTime());
	}

	private static void printStyle8(SimulationManagerFacade simulationManagerFacade) {
		// Print the number of simulation runs and respectively number of days each - outputing 0 days after first replication
				System.out.println(simulationManagerFacade.getNumberOfSimulationRuns());
				double [] numberOfDaysByReplication = simulationManagerFacade.getNumberOfDaysPerReplication();
				for (Double d: numberOfDaysByReplication) {
					System.out.println(d);
				}
	}

	private static void printStyle7(SimulationManagerFacade simulationManagerFacade) {
		// NUMBER OF SIMULATION RUNS, MEAN(SD) OF PROJECT DURATION, MEAN(SD) OF ITERATION/RELEASE (ONLY FOR ITERATIVE AND RELEASE BASED PROCESSES)   
		//Number of simulation runs......................:  1.0
		//Number of days.....................................:  0.0(0.0)
			System.out.println(simulationManagerFacade.getResultadosCabecalho());
			System.out.println(simulationManagerFacade.getResultadosCabecalhoFromConsole());
	}

	private static void printStyle6(SimulationManagerFacade simulationManagerFacade) {
		// Probably we can eliminate the methods calculateStandardDeviationNumberOfReleases and calculateStandardDeviationUserStoriesProducede
// After finishing extending XACDML. null pointer exception
		System.out.println(simulationManagerFacade.calculateStandardDeviationNumberOfReleases());
//	simulationManagerFacade.calculateStandardDeviationUserStoriesProducede();
	}

	private static void printStyle5(SimulationManagerFacade simulationManagerFacade) {
		// Prints the standard deviation of number of days
		System.out.println(simulationManagerFacade.calculateStandardDeviationNumberOfDays());
	}

	private static void printStyle4(SimulationManagerFacade simulationManagerFacade) {
		//	PRINTING THE MEAN AND STANDARD DEVIATION OF ENTITIES IN EACH QUEUE (See example below)  
		// Queue: q1
		// quantity in replication 1..: 1000
		// mean of entities in queue q1..:1000.0	Standard deviation..: 0.0
			System.out.println(simulationManagerFacade.getResultadosGlobalStringFromConsole());
			System.out.println(simulationManagerFacade.getSimulationResults());
	}

	private static void printStyle3(SimulationManagerFacade simulationManagerFacade) {
		// Print information about all queues (Wladimir style). See below for an example: 
		//		run #1
		//		{q1=<QueueEntry max="1000" policy="FIFO">
		//		<Q_super>
		//		<Entry id="q1" obsid="Work product input queue observer 2" name="q1"/>
		//		</Q_super>
//				Map<String, IDynamicExperimentationProgramProxy> resultadosGlobal =	simulationManagerFacade.getResultadoGlobal();
//				Set<String> keys = resultadosGlobal.keySet();
//				for (String key: keys) {
//					System.out.println(key);
//					System.out.println(resultadosGlobal.get(key));
//				}
	}

	private static void printStyle2(SimulationManagerFacade simulationManagerFacade) {
		// Imprime o endereco de heap de cada DynamicExperimentationProgramProxy
				Map<String, IDynamicExperimentationProgramProxy> resultsSimulationMap = simulationManagerFacade.getResultsSimulationMap();
				Set<String> keys = resultsSimulationMap.keySet();
				for (String key: keys) {
					System.out.println(key);
					System.out.println(resultsSimulationMap.get(key));
				}
	}

	private static void printStyle1(SimulationManagerFacade simulationManagerFacade) {
		// Imprime o prefixo replication + i,
		// NUMBER OF SIMULATION RUNS, MEAN(SD) OF PROJECT DURATION, MEAN(SD) OF ITERATION/RELEASE (ONLY FOR ITERATIVE AND RELEASE BASED PROCESSES)
		//  PRINTING THE MEAN AND STANDARD DEVIATION OF ENTITIES IN EACH QUEUE CONFIGURED AS 'DEPENDENT' FOR EACH REPLICATION
		// The number of entities in each queue is also printed as side effect of call to execute
		//		Replication0
		//		............   NUMBER OF SIMULATION RUNS, MEAN(SD) OF PROJECT DURATION, MEAN(SD) OF ITERATION/RELEASE (ONLY FOR ITERATIVE AND RELEASE BASED PROCESSES)   ............

		//			Number of simulation runs......................:  3.0
		//		Number of days.....................................:  0.0(0.57)
 
		//		............................   PRINTING THE MEAN AND STANDARD DEVIATION OF ENTITIES IN EACH QUEUE CONFIGURED AS 'DEPENDENT' FOR EACH REPLICATION   ............................ 
		//
		//		Queue: q1
		//			quantity in replication 1..: 1000
		//			mean of entities in queue q1..:1000.0	Standard deviation..: 0.0
		
				Map<String, String> resultsSimulationAdaptMe = simulationManagerFacade.getResultsSimulationMapAdaptMe();   
				Set<String> keys = resultsSimulationAdaptMe.keySet();
				for (String key: keys) {
					System.out.println(key);
					System.out.println(resultsSimulationAdaptMe.get(key));
				}
	}

}
