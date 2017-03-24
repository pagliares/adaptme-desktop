

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import adaptme.DynamicExperimentationProgramProxy;
import adaptme.IDynamicExperimentationProgramProxy;
import adaptme.facade.SimulationManagerFacade;
import adaptme.ui.window.perspective.VariableType;
import simula.manager.QueueEntry;
import simula.manager.SimulationManager;

public class TestSimulationFromConsole {

	public static void main(String[] args) {
		
		SimulationManagerFacade simulationManagerFacade = SimulationManagerFacade.getSimulationManagerFacade();
		simulationManagerFacade.execute(130000, 1, true);
		
		simulationManagerFacade.printQuantityOfSPEMActivitiesCompleted();
		
		simulationManagerFacade.printPhaseResults();

		System.out.println();
		
		simulationManagerFacade.printQuantityOfSPEMIterationsAndReleasesCompleted();
		
//		simulationManagerFacade.printReportWitSPEMMilestonesReached();
		
		printMeanAndStandardDeviationNumberOfDays(simulationManagerFacade);
		
		
		
		
		
		
		
		
		
		
		
		
		//		printStyle1(simulationManagerFacade);
		
		//		printStyle2(simulationManagerFacade);
		
		//		printStyle3(simulationManagerFacade);
		
		//		printStyle4(simulationManagerFacade);

		//		printStyle5(simulationManagerFacade);
		
		//		printStyle6(simulationManagerFacade); // NULL pointer exception
		
		//		printStyle7(simulationManagerFacade);
		
		//		printStyle8(simulationManagerFacade);
		
		//		printStyle9(simulationManagerFacade); // NULL pointer exception
		
		//		printStyle10(simulationManagerFacade);
  		
		//		printStyle11(simulationManagerFacade); 
		
	}
	
	private static void printMeanAndStandardDeviationNumberOfDays(SimulationManagerFacade simulationManagerFacade) {
		System.out.println("\nMean(sd) number of days..: " + Math.round(simulationManagerFacade.calculateMeanNumberOfDays() * 100.00)/100.0 + 
				          " (" + Math.round(simulationManagerFacade.calculateStandardDeviationNumberOfDays() * 100.00)/100.0 + ")" );
 	}

	private static void printStyle11(SimulationManagerFacade simulationManagerFacade) {
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
				Map<String, IDynamicExperimentationProgramProxy> resultadosGlobal =	simulationManagerFacade.getResultadoGlobal();
				Set<String> keys = resultadosGlobal.keySet();
				for (String key: keys) {
					System.out.println(key);
					System.out.println(resultadosGlobal.get(key));
				}
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
