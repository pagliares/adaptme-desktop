// Arquivo Scheduler.java
// Implementa��o das Classes do Grupo Executivo da Biblioteca de Simula��o JAVA
// 19.Mar.1999	Wladimir

package simula;

import java.util.*;

import simula.manager.QueueEntry;
import simula.manager.SimulationManager;
import simulator.spem.xacdml.results.ActivityResults;
import simulator.spem.xacdml.results.IterationResults;
import simulator.spem.xacdml.results.MilestoneResults;
import simulator.spem.xacdml.results.PhaseResults;
 
public class Scheduler implements Runnable{
	private Calendar calendar;		// estrutura de armazenamento dos estados ativos a servir
	private float clock = 0;		// rel�gio da simula��o
	private float endclock;			// fim da simula��o
	private float timeprecision;	// diferen�a m�nima que deve haver entre dois instantes
									// para que sejam considerados diferentes 
	private Vector activestates;	// Vetor dos estados ativos da simula��o
	private boolean crescan = true;	// flag de habilita��o de re-varrudura dos eventos C
	private volatile boolean running = false;
									// controla se a simula��o deve continuar rodando
	private boolean stopped = false;// indica se a simula��o parou conforme ordenado
	private Thread simulation;		// thread em que a simula��o ir� executar
	private byte termreason;		// porque encerrou a simula��o
	
	private static Scheduler s;		// uma refer�ncia est�tica ao Scheduler
									// para permitir a��es de emerg�ncia (parada)

	private SimulationManager simulationManager;
		
    public static boolean hasFinishedByLackOfEntities = false;
    private float clockOnEnding;
    
    private boolean hasIteration = false;
    private boolean hasRelease = false;
    private double iterationTimeBox = 0.0;
    private double releaseTimeBox = 0.0;
    private double activityTimeBox = 0.0;
	
    // TODO Tradeoff analysis if it is worthwhile to create only one map with SPEM results as object associated to one key 
    // and benefit from the polymorphism. Maybe tranforming SPEMResults in abstract with the abstract methods to determine the begin/finish of the activity
    private Map<String, List<ActivityResults>> mapWithActivityResults = new LinkedHashMap<>();
    private Map<String, List<PhaseResults>> mapWithPhaseResults = new LinkedHashMap<>();
    private Map<String, List<MilestoneResults>> mapWithMilestoneResults = new LinkedHashMap<>();
    private Map<String, List<IterationResults>> mapWithIterationResults = new LinkedHashMap<>();
    
 
	/**
	 * retorna refer�ncia ao objeto ativo
	 */
	public static Scheduler Get(){return s;}

	public Scheduler(SimulationManager simulationManager)	{
		activestates = new Vector(20, 10);
		calendar = new Calendar();
		timeprecision = (float)0.001;
		s = this;
		this.simulationManager = simulationManager;
	}
	
	/**
	 * Coloca objeto em seu estado inicial para simula��o
	 * Apaga todos os eventos agendados. Deve ser chamado ANTES
	 * de todos os Clear() dos Active/DeadState
	 */
	public void Clear(){
		if(running)
			return;
		simulation = null;	// impede continua��o
		clock = 0;					// reinicia rel�gio
		stopped = false;
		termreason = 0;
		calendar = new Calendar();
	}

	
	float ScheduleEvent(ActiveState activeState, double duetime){
		double time = clock + duetime; // @TODO PAGLIARES: ACUMULADOR DO CLOCK
		time = Math.floor(time / timeprecision);
		time *= timeprecision;					// trunca parte menor que timeprecision
		calendar.Add(activeState, time); // atribui calendar.root   
		return (float)time;							// retorna instante realmente utilizado
	}
	
	void Register(ActiveState a){
		if(!activestates.contains(a))
			activestates.addElement(a);
	}
	
	/**
	 * >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	 */
	public void CRescan(boolean on){	
		if(!running)
			crescan = on;
	}

	/**
	 * retorna true se a simula��o terminou
	 */
	public boolean Finished(){
		return stopped;
	}
	
	/**
	 * Inicia execucao da simulacao numa thread separada
	 */
	public synchronized boolean Run(double endtime){
		hasIteration = false;
		hasRelease = false;
		if(endtime < 0.0)				// relogio nao pode ser negativo
			return false;				// se for 0.0 executa ate acabarem as entidades ( @TODO DUMMY ACTIVITY TIP)

		if(!running) { // @TODO INICIA A SIMULACAO PELA PRIMEIRA VEZ
		
			if(activestates.isEmpty())	// se nao ha nenhum estado ativo registrado, 
				return false;			// como executar?
			activestates.trimToSize();
			running = true;
			stopped = false;
			endclock = (float)endtime;
			clock = 0;
			termreason = 0;
			simulation = new Thread(this);
			simulation.setPriority(Thread.MAX_PRIORITY);
			simulation.start();			// inicia execu��o
			Log.LogMessage("Scheduler: simulation started\n");

			return true;
		}

		return false;
	}
	
	/**
	 * P�ra a simulacao
	 */
	public synchronized void Stop(){
		if(stopped)						// se j� parou
			return;
		
		stopped = false;
		running = false;				// encerramento suave
		try{
			simulation.join(5000);		// espera at� 5 segundos
		}
		catch(InterruptedException e){
			stopped = true;
			simulation = null;	// n�o pode continuar
			termreason = 4;			// parada dr�stica
			Log.LogMessage("Scheduler: simulation stopped drastically");
			Log.Close();
			return;							// j� parou mesmo...
		}
		
		termreason = 3;			// parada suave bem sucedida
			
		if(!stopped){					// se ainda n�o parou...
			try{
				simulation.interrupt();		// p�ra de forma dr�stica
			}
			catch(SecurityException e){}
			
			simulation = null;	// n�o pode continuar
			termreason = 4;
			Log.LogMessage("Scheduler: simulation stopped drastically");
			Log.Close();
		}
		
		Log.LogMessage("Scheduler: simulation paused");	
	}
	
	/**
	 * Continua a execu��o de uma simula��o parada por Stop()
	 */
	public synchronized boolean Resume(){
		if(running || simulation == null || termreason != 3)
			return false;
		
		running = true;
		stopped = false;	
		simulation.start();
		termreason = 0;
		Log.LogMessage("Scheduler: simulation resumed");
		
		return true;
	}
	
	/**
	 * Seta o m�nimo intervalo entre dois instantes para que sejam considerados o mesmo.
	 */
	public void SetPrecision(double timeprec){ 
		if(!running)
			timeprecision = (float)timeprec;
	}

	/**
	 * retorna rel�gio da simula��o.
	 */
	public float GetClock(){
		return clock;
	}

	/**
	 * Codigo que roda a simulacao. (rodado numa Thread separada)
	 */
	public void run(){
		while(running){
			
			// PAGLIARES: AVANCA O CLOCK DA SIMULACAO - FASE A 
			// When hits this point the first time, it is already scheduled the activities in the calendar (first activity)
			// I changed the signature of the getNextClock to include the parameter clock. This is done to not return the clock back to zero
			// after invoking the method
			clock = calendar.getNextClock(clock); // @TODO colocar breakpoint aqui para verificar o porque de estar agendando inicialmente mais tarefas para alguns testes de caso
			
			Log.LogMessage("\nBegin of phase A - 3 Phase approach");
			Log.LogMessage("\tScheduler: clock advanced to " + clock);
			
			// verifica se simulacao chegou ao fim

			ActiveState activeState;
							
			// Pagliares. The commented lines below is from Wladimir that finishes the simulation by lack of entities when clock is zero.
			//	if(clock == 0.0){			 
			//		running = false;
			//		termreason = 1;			
			//		Log.LogMessage("\nScheduler: simulation finished due to end of entities");
			//		Log.Close();
			//		firstTime = false;
			//		break;
			//	}

			// Pagliares. If below programmed by me to replace the commented if above
			if(hasFinishedByLackOfEntities == true){			// fim das entidades
				clockOnEnding = s.GetClock();
				running = false;
				termreason = 1;			
				Log.LogMessage("\nScheduler: simulation finished due to end of entities");
				Log.Close();
				break;
			}
			
			
			if(clock >= endclock && endclock != 0.0){	// fim do intervalo
				clockOnEnding = s.GetClock();
				running = false;
				termreason = 2;
				Log.LogMessage("\nScheduler: simulation finished due to end of simulation time");
				Log.Close();
				break;
			}
			
			Log.LogMessage("End of phase A");

			boolean executed;			// se algum evento B ou C foi executado
			
			// Fase B
			Log.LogMessage("\nBegin of phase B - 3 Phase approach");
			//	Log.LogMessage("\tSimulation clock at begin of phase B : " + s.GetClock() +   "(ticks)," + s.GetClock()/60 + "(hours), " + s.GetClock()/480 + "(days)");

			executed = false;

			do{
				activeState = calendar.getNextActiveState();
				Log.LogMessage("\n\tNext activity in the Calendar : " + activeState.name);
				executed |= activeState.BServed(clock);	// se ao menos um executou, fica registrado
				
				if (!(activeState instanceof Generate)) {
					// Store the number of activities in a map with the results
					Activity act = (Activity) activeState;
					if ((executed) && act.getSpemType().equalsIgnoreCase("ACTIVITY") && act.name.startsWith("END_")) {
						double timeActivityStarted = 0.0;
						double timeActivityFinished = 0.0;
						if (!mapWithActivityResults.containsKey(act.name)) {  // Create a new ActivityResults and insert it into the map
							timeActivityStarted = getBEGINActivityStarted(act.name);
							timeActivityFinished = s.GetClock();
							ActivityResults activityResults = new ActivityResults(act.name, timeActivityStarted, timeActivityFinished);  
							List<ActivityResults> activityResultsList = new ArrayList<>();
							activityResultsList.add(activityResults);
							mapWithActivityResults.put(act.name, activityResultsList); // talvez esta linha nao seja necessaria, por nao ser imutavel
						} else {
							List<ActivityResults> listActivityResults = (mapWithActivityResults.get(act.name));
							timeActivityStarted = getBEGINActivityStarted(act.name);
							timeActivityFinished = s.GetClock();
							ActivityResults activityResults = new ActivityResults(act.name, timeActivityStarted, timeActivityFinished); // verificar se no mapa usa-se ou no o prefixo do nome (END_)        	
							listActivityResults.add(activityResults);	
						}
					} 
				
					else if ((executed) && act.getSpemType().equalsIgnoreCase("PHASE") && act.name.startsWith("END_")) {  // Store the phase results in a map
						if (!(mapWithPhaseResults.containsKey(act.name))) {
	                    	double timePhaseStarted = getBEGINPhaseStarted(act.name);
							double TimePhaseFinished = s.GetClock();
						    PhaseResults phaseResults = new PhaseResults(act.name, timePhaseStarted,TimePhaseFinished);
						    List<PhaseResults> list = new ArrayList<>();
						    list.add(phaseResults);
						    mapWithPhaseResults.put(act.name, list);
	                    } else {
	                    	List<PhaseResults> listPhaseResults = (mapWithPhaseResults.get(act.name));
	                    	
	                    	double timeActivityStarted = getBEGINActivityStarted(act.name);
							double timeActivityFinished = s.GetClock();
							
							PhaseResults phaseResults = new PhaseResults(act.name, timeActivityStarted, timeActivityFinished); // verificar se no mapa usa-se ou no o prefixo do nome (END_)        	
							listPhaseResults.add(phaseResults);	
	                     }
					} 
			
					else if ((executed) && act.getSpemType().equalsIgnoreCase("MILESTONE")) {  // Store the phase results in a map
						if (!(mapWithMilestoneResults.containsKey(act.name))) {
	                    	double timeMilestoneWasReached = s.GetClock();
 						    MilestoneResults milestoneResults = new MilestoneResults(act.name, timeMilestoneWasReached);
 						    List<MilestoneResults> list = new ArrayList<>();
						    list.add(milestoneResults);
						    mapWithMilestoneResults.put(act.name, list);
	                    } else {
	                    	List<MilestoneResults> listMilestoneResults = (mapWithMilestoneResults.get(act.name));
	                    	
	                    	double timeMilestoneWasReached = s.GetClock();
							
							MilestoneResults milestoneResults = new MilestoneResults(act.name, timeMilestoneWasReached);
							listMilestoneResults.add(milestoneResults);	
	                     }
					} 
				
					else if ((executed) && act.getSpemType().equalsIgnoreCase("ITERATION") && act.name.startsWith("END_")) {  // Store the iteration/release results in a map

						if (!(mapWithIterationResults.containsKey(act.name))) {
							double timeIterationStarted = getBEGINIterationOrReleaseStarted(act.name);
							double TimeIterationFinished = s.GetClock(); 
							IterationResults iterationResults = new IterationResults(act.name, timeIterationStarted, TimeIterationFinished);
							List<IterationResults> list = new ArrayList<>();
							list.add(iterationResults);
							mapWithIterationResults.put(act.name, list);
						} else {
							List<IterationResults> listIterationResults = (mapWithIterationResults.get(act.name));
							double timeIterationStarted = getBEGINActivityStarted(act.name);
							double timeIterationFinished = s.GetClock();
							IterationResults iterationResults = new IterationResults(act.name, timeIterationStarted, timeIterationFinished);
							listIterationResults.add(iterationResults);	
						}
					
						// System.out.println("Snapshot at the end of the iteration. Printing the number of entities in each dead state");
						// printNumberOfEntitiesInEachDeadState();
					
						// System.out.println("\nsize of entity class before moving..: " + SimulationManager.quantityOfEntitiesInClass);
						Log.LogMessage("\n\tsize of entity class before moving..: " + SimulationManager.quantityOfEntitiesInClass);
					
						// TODO Need to grab from XSLT, the attributes for iteration_behaviour. The below method must be called for 
						// the SPEM-TO-XACDML process, but not called for the approach paper example (product development)
//						moveEntitiesBackToInitialState(act);
						
						// System.out.println("\nSnapshot after moving the entities");
						//	printNumberOfEntitiesInEachDeadState();

						SimulationManager.quantityOfEntitiesInClass = getIncomingDeadStateOfBEGINIterationCounterpart(act).count;
						//	System.out.println("\nsize of entity class after moving " + SimulationManager.quantityOfEntitiesInClass);
						Log.LogMessage("\n\tsize of entity class after moving " + SimulationManager.quantityOfEntitiesInClass + "\n");
						    
						for (Object o: this.activestates) {
							Activity activity = (Activity)o;
							activity.setNumberOfEntitiesProduced(0);
							activity.setStartedWithExceededTimeBox(false);
						}
						    
						Activity activity = act.getBEGINIterationActiveState(act.name); 
						// pegando a anterior ao Begin_Iteration
						Activity previousBeginIteration = activity.getPreviousActivity(activity.name);  
						if (previousBeginIteration != null) {
							previousBeginIteration.setNumberOfEntitiesProduced(SimulationManager.quantityOfEntitiesInClass);	  
						}
					}
				} 
//				else if (activeState instanceof Generate) {
//					SimulationManager.quantityOfEntitiesInClass++;
//				}
				
			}while(calendar.RemoveNext());  
 
			if(!executed)	{	
				Log.LogMessage("\nNothing to be executed at this instant. Jumps to the next, without executing the C-Phase");
				continue;				// se n�o havia nada a ser executado nesse instante pula para o pr�ximo sem executar a fase C.
				                  // (as atividades podem ter alterado o tempo localmente)  
			}
			Log.LogMessage("End of phase B");
			
 			// Fase C
			Log.LogMessage("\nBegin of phase C - 3 Phase approach");
			
			do{
				executed = false;

				for(short i = 0; i < activestates.size(); i++) {	
					ActiveState a = (ActiveState)activestates.elementAt(i);
					
						 executed |= ((ActiveState)activestates.elementAt(i)).CServed();  
 
 				}
				
			}while(crescan && executed);	
			
		    Log.LogMessage("End of phase C");
		}

		stopped = true;			// sinaliza o encerramento
		running = false;
	}

	public float getEndclock() {
		return endclock;
	}
	
	// Pagliares TODO - VERIFICAR SE PRECISA SER ATUALIZADO COM O CONTEUDO DO METODO ORIGINAL QUE FOI SOBRECARREGADO
	public synchronized boolean Run(double endtime, float iterationTimeBox, float releaseTimeBox){
		hasIteration = true;
		hasRelease = true;
		this.iterationTimeBox = iterationTimeBox;
		this.releaseTimeBox = releaseTimeBox;
		
		if(endtime < 0.0)				// relogio nao pode ser negativo
			return false;				// se for 0.0 executa ate acabarem as entidades

		if(!running){
			if(activestates.isEmpty())	// se nao ha nenhum estado ativo registrado, 
				return false;			// como executar?
			activestates.trimToSize();
			running = true;
			stopped = false;
			endclock = (float)endtime;
			clock = 0;
			termreason = 0;
			simulation = new Thread(this);
			simulation.setPriority(Thread.MAX_PRIORITY);
			simulation.start();			// inicia execu��o
			Log.LogMessage("Scheduler: simulation started\n");

			return true;
		}

		return false;
	}

	
	
	private double getBEGINPhaseStarted(String endAPhaseName) {
		String endPhaseSufix = endAPhaseName.substring(4);
		String beginPhaseSufix = "";
		Vector activities  = s.getActivestates();
		Activity activeState;
		for (int i =0; i < activities.size(); i++) {
			activeState = (Activity)activities.get(i);
			beginPhaseSufix = activeState.name.substring(6);
			if ((activeState.name.startsWith("BEGIN_")) && (endPhaseSufix.equalsIgnoreCase(beginPhaseSufix))){
				return activeState.getTimeWasStarted();
			}
		}
		return 0;
	}
	
	private double getBEGINActivityStarted(String endActivityName) {
		String endActivitySufix = endActivityName.substring(4);
		String beginActivitySufix = "";
		Vector activities  = s.getActivestates();
		Activity activeState;
		for (int i =0; i < activities.size(); i++) {
			activeState = (Activity)activities.get(i);
			beginActivitySufix = activeState.name.substring(6);
			if ((activeState.name.startsWith("BEGIN_")) && (endActivitySufix.equalsIgnoreCase(beginActivitySufix))){
				return activeState.getTimeWasStarted();
			}
		}
		return 0;
	}
	
	private double getBEGINIterationOrReleaseStarted(String endAIterationName) {
		String endIterationSufix = endAIterationName.substring(4);
		String beginIterationSufix = "";
		Vector activities  = s.getActivestates();
		Activity activeState;
		for (int i =0; i < activities.size(); i++) {
			activeState = (Activity)activities.get(i);
			beginIterationSufix = activeState.name.substring(6);
			if ((activeState.name.startsWith("BEGIN_")) && (endIterationSufix.equalsIgnoreCase(beginIterationSufix))){
				return activeState.getTimeWasStarted();
			}
		}
		return 0;
	}
	
	// tem uma versao deste metodo em SimulationManagerFacade. Reconciliar
	private void printNumberOfEntitiesInEachDeadState() { 
		// Show the number of entities in all dead states
		System.out.println("\tPrinting the number of entities in each dead state");
		HashMap queues = simulationManager.getQueues();
		Set keys = queues.keySet();

		for (Object queueName : keys) {
			System.out.println("\n\t\tQueue name : " + queueName);
			QueueEntry qe = (QueueEntry) queues.get(queueName);

			// Both below outputs return the count variable
			System.out.println("\t\tNunber of entities in queue via getCount: " + qe.deadState.getCount());
			// System.out.println("numero de entidadas na fila: via ObsLength, basta mudar simobj para o novo nome" + qe.SimObj.getCount());
		}
	}
	
    private void moveEntitiesBackToInitialState(Activity activity) {
    	if (activity.getSpemType().equalsIgnoreCase("ITERATION") && activity.name.startsWith("END_")) {
//    		System.out.println("\nMoving entities in intermediary deadstates to the initial dead state");
    		Log.LogMessage("\n\tMoving entities in intermediary deadstates to the initial dead state");
    	    // need to find the incoming dead state of the BEGIN iterationCounterpart
    	     DeadState incomingDeadStateBeginCounterpart = getIncomingDeadStateOfBEGINIterationCounterpart(activity);
//    	     DeadState q0 = (DeadState)activity.getEntities_from_v().get(0);
    	     String incomingDeadStateBeginCounterpartName = incomingDeadStateBeginCounterpart.name;
//    	     String incomingDeadStateBeginCounterpartName = q0.name;
    	     
    	     DeadState outcomingDeadStateEndIteration = (DeadState)activity.dead_states_to_v.get(0);
    	     String outcomingDeadStateEndIterationName = outcomingDeadStateEndIteration.name;
    	     
    	    HashMap queues = simulationManager.getQueues();
    		Set keys = queues.keySet();
            int quantityOfEntities = 0;
    		for (Object queueName : keys) {
    			QueueEntry qe = (QueueEntry) queues.get(queueName);
    			quantityOfEntities = qe.deadState.count;
    			if ((quantityOfEntities != 0) && (queueName != outcomingDeadStateEndIterationName)) {
    				for (int i= 0; i < quantityOfEntities; i++) {
    					Entity entity = qe.deadState.dequeue();
    						 if (entity != null)
    							 incomingDeadStateBeginCounterpart.enqueue(entity);
//    							 q0.enqueue(entity);
    					}
    				}
    			}	
    	}
    }
    
    private DeadState getIncomingDeadStateOfBEGINIterationCounterpart(Activity activity) {
		String endIterationSufix = activity.name.substring(4);
		String beginIterationSufix = "";
		Vector activities  = s.getActivestates();
		Activity activeState;
		for (int i =0; i < activities.size(); i++) {
			activeState = (Activity)activities.get(i);
			beginIterationSufix = activeState.name.substring(6);
			if ((activeState.name.startsWith("BEGIN_")) && (endIterationSufix.equalsIgnoreCase(beginIterationSufix))){
				DeadState incomingDeadState = (DeadState)activeState.getEntities_from_v().get(0);
				return (incomingDeadState);
			}
		}
		return null;
	}
    
	public SimulationManager getSimulationManager() {
		return simulationManager;
	}

	public void setSimulationManager(SimulationManager simulationManager) {
		this.simulationManager = simulationManager;
	}
	
	public float getClockOnEnding() {
		return clockOnEnding;
	}
	
	public ActiveState getActiveState(int index) {
		return (ActiveState)activestates.get(index);
	}

	public Vector getActivestates() {
		return activestates;
	}

	public void setActivestates(Vector activestates) {
		this.activestates = activestates;
	}

	public Map<String, List<ActivityResults>> getMapWithActivityResults() {
		return mapWithActivityResults;
	}

	public Map<String, List<PhaseResults>> getMapWithPhaseResults() {
		return mapWithPhaseResults;
	}
	
	public Map<String, List<MilestoneResults>> getMapWithMilestoneResults() {
		return mapWithMilestoneResults;
	}
	
	public Map<String, List<IterationResults>> getMapWithIterationResults() {
		return mapWithIterationResults;
	}
}