package adaptme.facade;

import java.util.HashMap;
import java.util.Iterator;

import adaptme.ExperimentationProgramProxy;
import simula.Scheduler;
import simula.manager.*;
 
public class SimulationManagerFacade {
	private SimulationManager man;
	
	public void printSimulationEndedTime() {
		Scheduler scheduler = man.getScheduler();
		System.out.println("Statistics collected from instant " + man.getResettime());
		System.out.println(" during " + (scheduler.GetClock() - man.getResettime()) + " time units.");
	}
	
	public SimulationManagerFacade() {
		 
		// primeiro teste. Tentando gerar os resultados da simulacao mais de
					// uma vez - gera, mas todos com o mesmo resultado
					// Parece que vou precisar das classes Statistics e Histogram
		for (int i = 0; i < 3; i++) {
			System.out.println("\nGeracao de HBC atraves de Simulation Manager");
			man = new SimulationManager();

			QueueEntry qe;// queues
			ResourceEntry re;//resource queues
			ExternalActiveEntry eae;// generator and detroy
			InternalActiveEntry iae;//activivity and router
			ObserverEntry oe;
			HistogramEntry he;

			// queues and observer queues

			qe = new QueueEntry();
			qe.SetId("WAIT0");// mapped by dead id
			qe.setObsid("CALLER_OBS");// observer
			man.AddQueue(qe);

			 qe = new QueueEntry();
			 qe.SetId("WAIT1");//mapped by dead id
			 qe.setObsid("INQUIRER_OBS");//observer
			 man.AddQueue(qe);

			 re = new ResourceEntry();
			 re.SetId("IDLE");//mapped by dead id
			 re.setInit((short)1);
			 re.setObsid("CLERK_OBS");//observer
			 man.AddResource(re);

			 qe = new QueueEntry();
			 qe.SetId("B0");//mapped by dead id
			 man.AddQueue(qe);
			
			 qe = new QueueEntry();
			 qe.SetId("B1");//mapped by dead id
			 man.AddQueue(qe);

			// active states

			// externals (generates and destroys)

			 eae = new ExternalActiveEntry(true); //generate
			 eae.SetId("ARRIVE"); //mapped by generate id
			 eae.setQID("WAIT1"); // mapped by next dead
			 eae.setServiceDist( ActiveEntry.UNIFORM);// mapped by stat type
			 eae.setDistP1(1);// mapped by parm1
			 eae.setDistP2(4); //mapped by parm2
			 man.AddActiveState(eae);

			eae = new ExternalActiveEntry(true); // generate
			eae.SetId("CALLING"); // mapped by generate id
			eae.setQID("WAIT0"); // mapped by next dead
			eae.setServiceDist(ActiveEntry.UNIFORM);// mapped by stat type
			eae.setDistP1(1);// mapped by parm1
			eae.setDistP2(9); // mapped by parm2
			man.AddActiveState(eae);

			eae = new ExternalActiveEntry(false); //destroy
			 eae.SetId("OUT0"); //mapped by destroy id
			 eae.setQID("B0"); // mapped by prev dead
			 man.AddActiveState(eae);

			 eae = new ExternalActiveEntry(false); //destroy
			 eae.SetId("OUT1"); //mapped by destroy id
			 eae.setQID("B1"); // mapped by prev dead
			 man.AddActiveState(eae);

			// internals (activities and routers)

			 iae = new InternalActiveEntry(true); //isn't router
			 iae.SetId("TALK"); //mapped by act id
			 iae.setServiceDist(ActiveEntry.NORMAL);// mapped by stat type
			 eae.setDistP1(2);// mapped by parm1
			 eae.setDistP2(9); //mapped by parm2

			 iae.addToQueue("B0");// mapped by next dead
			 iae.addFromQueue("WAIT0");// mapped by prev dead
			 iae.addToResource("IDLE");// mapped by next resource dead
			 iae.addResourceQty(new Integer(1));// mapped by init on resource
			// dead
			 iae.addFromResource("IDLE");// mapped by prev resource dead

			 iae.addCond("true"); // TODO verificar exatamente o que faz
			 man.AddActiveState(iae);

			 iae = new InternalActiveEntry(true); //isn't router
			 iae.SetId("SERVICE"); //mapped by act id
			 iae.setServiceDist(ActiveEntry.NORMAL);// mapped by stat type
			 eae.setDistP1(3);// mapped by parm1
			 eae.setDistP2(9); //mapped by parm2

			 iae.addToQueue("B1");// mapped by next dead
			 iae.addFromQueue("WAIT1");// mapped by prev dead
			 iae.addToResource("IDLE");// mapped by next resource dead
			 iae.addResourceQty(new Integer(1));// mapped by init on resource
			// dead
			 iae.addFromResource("IDLE");// mapped by prev resource dead

			 iae.addCond("true"); // TODO verificar exatamente o que faz
			 man.AddActiveState(iae);

			// observers

			oe = new ObserverEntry(ObserverEntry.QUEUE, "WAIT0");// observer
																	// queue
			oe.SetId("CALLER_OBS");
			man.AddObserver(oe);

			 oe= new ObserverEntry(ObserverEntry.QUEUE,"WAIT1");//observer
//			 queue
			 oe.SetId("INQUIRER_OBS");
			 man.AddObserver(oe);

			 oe= new ObserverEntry(ObserverEntry.RESOURCE,"IDLE");//observer
			// resource
			 oe.SetId("CLERK_OBS");
			 man.AddObserver(oe);

			 oe= new ObserverEntry(ObserverEntry.ACTIVE,"ARRIVE");
			 oe.SetId("INQUIRING_OBS");
			 man.AddObserver(oe);

			 eae = (ExternalActiveEntry)man.GetActiveState("ARRIVE");
			 eae.setObsid( "INQUIRING_OBS");

			 oe= new ObserverEntry(ObserverEntry.ACTIVE,"CALLING");
			 oe.SetId("CALLING_OBS");
			 man.AddObserver(oe);

			 eae = (ExternalActiveEntry)man.GetActiveState("CALLING");
			 eae.setObsid( "CALLING_OBS");

			// generate the model

			if (man.GenerateModel())
				System.out.println("\nModel successfuly generated!");
			else {
				System.out.println("\nThere was an errors during the model generation!");
				System.out.println("Exiting...");
				return;
			}

			// start the simulation

			System.out.println("Starting the simulation. Simulation wil run unitl time=500");

			

			man.ExecuteSimulation(20);

			while (!man.Finished()) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					break;
				}
			}

			System.out.println("Simulation Stopped!");

			// finally, output the results

			man.OutputSimulationResults("HBC" + i + ".out");
			man.OutputSimulationResultsConsole();
			
			System.out.println("AQUI");
			man.printObserversReport();
			System.out.println("\r\n          Observers' report");
			
			Iterator it;
			HashMap observers = man.getObservers();
			it = observers.values().iterator();
//			while(it.hasNext())
//			{
				System.out.println("IMPRIMINDO APENAS UM OBSERVADOR");
				ObserverEntry observerEntry = (ObserverEntry)it.next();
				man.printObserversReport(observerEntry);
				
//			}
			
				
			System.out.println("IMPRIMINDO APENAS a media do observador acima");
			man.printWeightedAverage(observerEntry);
			
			System.out.println("IMPRIMINDO APENAS o  desvio padrao do observador acima");
			man.printWeightedAverage(observerEntry);
			
			System.out.println("IMPRIMINDO APENAS A  variancia do observador acima");
			man.printVarianceWeighted(observerEntry);
			
			System.out.println("IMPRIMINDO MINIMO do observador acima");
			man.printMin(observerEntry);
			
			System.out.println("IMPRIMINDO MAXIMO do observador acima");
			man.printMax(observerEntry);
			
			System.out.println("IMPRIMINDO numero de observacoes do observador acima");
			man.printNumberOfObservations(observerEntry);
//			printSimulationEndedTime();
			

		}

		System.out.println("\nThat's all folks!");
	}
	
	public void execute(int numberReplications) {
		for (int i =0; i < numberReplications; i++) {
			ExperimentationProgramProxy epp = new ExperimentationProgramProxy();
			System.out.println("Execution #" + (i+1));
			
			epp.execute();
		}
	}
	
	public static void main(String [] args) {
		SimulationManagerFacade hbcFacade = new SimulationManagerFacade();
//		hbcFacade.printSimulationEndedTime();
	}
}
