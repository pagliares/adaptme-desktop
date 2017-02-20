// Arquivo Activity.java
// Implementa��o das Classes do Grupo de Modelagem da Biblioteca de Simula��o JAVA
// 9.Abr.1999	Wladimir

package simula;

import java.sql.Time;
import java.util.*;

import simula.manager.InternalActiveEntry;
import simula.manager.SimulationManager;

 
 
public class Activity extends ActiveState{

	/**
	 * entities_from_v, entities_to_v, conditions_from_v,
	 * resources_from_v, resources_to_v, resources_qt_v:
	 * vetores que mant�m as liga��es e par�metros
	 */
	protected Vector dead_states_from_v, entities_to_v, conditions_from_v,
					 	resources_from_v, resources_to_v, resources_qt_v;
													
	private Distribution d;		// gerador de n�meros aleat�rios de uma dada distribui��o

	/**
	 * fila de entidades/recursos em servi�o
	 */
	protected IntPriorityQ queueOfEntitiesAndResourcesInService;
		
	/**
	 * se est� bloqueado
	 */
	protected boolean blocked;	
	
 	public static boolean isBeginOfSimulation = true;
	
	private String dependencyType;
	private String processingType;
	private String spemType;
	private String processingUnit;
	private double timeBox = 0.0;
	private String father = "";

	private int numberOfEntitiesProduced = 0;
	private double timeWasStarted = 0.0; 
	
	// private Vector entities_qt_v = new Vector(1, 1); // Commented lines to be worked when trying to acquire entities in batch mode
	
	/**
	 * constr�i um estado ativo sem conex�es ou tempo de servi�o definidos.
	 */
	public Activity(Scheduler s){
		super(s);
		// constr�i vetores de liga��es
		dead_states_from_v = new Vector(1, 1);
		entities_to_v = new Vector(1, 1);
		conditions_from_v = new Vector(1, 1);
		resources_from_v = new Vector(1, 1);
		resources_to_v = new Vector(1, 1);
		resources_qt_v = new Vector(1, 1);
		queueOfEntitiesAndResourcesInService = new IntPriorityQ();
		
		// SPEM extension
		 dependencyType = "";
		 processingType = "";
		 spemType = "";
		 processingUnit = "";
		 numberOfEntitiesProduced = 0;
	}
	
	/**
	 * determina o tempo de servico de acordo com a distribuicao especificada;
	 * os parametros da distribuicao sao passados na criacao do objeto.
	 */
	public void SetServiceTime(Distribution d){
		this.d = d;
		 
		double sampling = d.Draw();
		// Pagliares. I am scheduling just the first active state (the first activity added to the simulation manager linked hash map.
		if (this.s.getActiveState(0).name.equals(this.name)) {
				RegisterEvent((float)sampling); // PAGLIARES. 20h de depuracao para fazer com que a simulacao funcionasse sem generate activity
		}		
  	}
	
	public void ConnectQueues(DeadState from, DeadState to){
		ConnectQueues(from, ConstExpression.TRUE, to);
	}
	
	/**
	 * conecta estados mortos � atividade de forma que 
	 * a(s) entidade(s) (recurso(s)) n�o se misturem.
	 */
	public void ConnectResources(ResourceQ from, ResourceQ to, int qty_needed){
		resources_from_v.add(from);
		resources_to_v.add(to);
		resources_qt_v.add(new Integer(qty_needed));
	}
	
	/**
	 * conecta estados mortos � atividade de forma que 
	 * a(s) entidade(s) (recurso(s)) n�o se misturem.
	 * mas a entidade � obtida de from somente se cond � satisfeita
	 */
	public void ConnectQueues(DeadState from, Expression cond, DeadState to){
		conditions_from_v.add(cond);
		dead_states_from_v.add(from);
		entities_to_v.add(to);
	}
	
	/**
	 * Coloca objeto em seu estado inicial para simula��o
	 */
	public void Clear(){
		super.Clear();
		queueOfEntitiesAndResourcesInService = new IntPriorityQ();
	}

	/**
	 * implementa protocolo.
	 */
	public boolean BServed(float time){
		
		if(blocked)									// n�o faz nada enquanto estiver bloqueado
			return false;
			
		InServiceTemporaryEntitiesUntilDueTime inServiceEntities = queueOfEntitiesAndResourcesInService.Dequeue();

		if (isBeginOfSimulation) { // pagliares 
			isBeginOfSimulation = false; // pagliares
			return true; // pagliares
		}
		
		if(inServiceEntities == null)								// n�o h� mais nada a servir
			return false;

		if(time < inServiceEntities.duetime){			// servi�o foi interrompido e scheduler n�o foi notificado									
			queueOfEntitiesAndResourcesInService.PutBack(inServiceEntities);		// devolve � fila para ser servido mais tarde
			return false;
		}

		// fim de servico!
		
		boolean shouldnotblock = true;

		for(int i = 0; i < entities_to_v.size(); i++){		// as entidades...
			DeadState q = (DeadState)entities_to_v.elementAt(i);	// obt�m fila associada
			shouldnotblock &= q.HasSpace();												// condi��o para n�o bloquear
		}
		
		if(!shouldnotblock){
			queueOfEntitiesAndResourcesInService.PutBack(inServiceEntities);
			blocked = true;											// bloqueia
			Log.LogMessage(name + ":Blocked");
			return false;
		}

		for(int i = 0; i < entities_to_v.size(); i++)	{	// as entidades...
			// int qt; // Commented lines to be worked when trying to acquire entities in batch mode
			DeadState q = (DeadState)entities_to_v.elementAt(i);	// obtem fila associada
			if(q.HasSpace())										// se tem espaco
				q.enqueue(inServiceEntities.entities[i]);									// envia ao estado morto
		}
		
		for(int i = 0; i < resources_to_v.size(); i++){		// e os recursos.
			int qt;
			ResourceQ q = (ResourceQ)resources_to_v.elementAt(i);	// obtem fila associada
			q.Release(qt = ((Integer)resources_qt_v.elementAt(i)).intValue());// envia ao estado morto
			Log.LogMessage("\t" + name + ":Released " + qt + " resources to " +
				((ResourceQ)resources_to_v.elementAt(i)).name);
		}

		if(obs != null){
			if(queueOfEntitiesAndResourcesInService.IsEmpty())
				obs.StateChange(Observer.IDLE);
			for(int i = 0; i < inServiceEntities.entities.length; i++)
				obs.Outgoing(inServiceEntities.entities[i]);
		}
		
		for(int i = 0; i < inServiceEntities.entities.length; i++) {
			Log.LogMessage("\t" + name + ":Entity " + inServiceEntities.entities[i].getId() +
				" sent to " + ((DeadState)entities_to_v.elementAt(i)).name);
		}
		numberOfEntitiesProduced++;
  		return true;
	}
	
	public boolean CServed(){
		
		// If the activity has FINISH-TO-START dependency and PROCESS-BY-CLASS, we need to verify the condition 
		// if the class of entities has been produced by the previous task
		if (dependencyType.equalsIgnoreCase("FINISH-TO-START") && processingType.equalsIgnoreCase("PROCESS-BY-CLASS")) {
				boolean isProcessByClassOfEntitiesConditionSatisfied = isProcessByClassOfEntitiesConditionSatisfied();	
				if (isProcessByClassOfEntitiesConditionSatisfied == false) {
					return false;
				}
		}
		
		// primeiro tenta resolve o estado bloqueado, se for o caso
		if(blocked){
			blocked = false;
			while(BServed(s.GetClock()));	// extrai todos os bloqueados
							
			if(blocked)		// se ainda estiver bloqueado, n�o faz nada
				return false;
			Log.LogMessage(name + ":Unblocked");
		}
			
		// primeiro verifica se todos os recursos e entidades est�o dispon�veis
		boolean ok = true;
		int esize = dead_states_from_v.size();
		int i;

		for(i = 0; i < resources_from_v.size() && ok; i++)	{// os recursos...
			ResourceQ resourceQ = (ResourceQ)resources_from_v.elementAt(i);
			Integer quantityOfResourcesUsed = ((Integer)resources_qt_v.elementAt(i)).intValue();
			
			ok &= resourceQ.HasEnough(quantityOfResourcesUsed);
		}
		
		Log.LogMessage("\n\t" + name + " Resources available? " + ok);
		 
		for(i = 0; i < esize && ok; i++)	{				// as entidades...
			DeadState deadState = (DeadState)dead_states_from_v.elementAt(i);
			ok &= deadState.HasEnough();
		}
		
		// Commented lines to be worked when trying to acquire entities in batch mode
		//		for(i = 0; i < esize && ok; i++)					// as entidades...
		//			ok &= ((DeadState)dead_states_from_v.elementAt(i)).HasEnough(((Integer)entities_qt_v.elementAt(i)).intValue());

		Log.LogMessage("\t" + name + " Temporary entities available? " + ok);
		
		float serviceDuration = (float)d.Draw();
		// The TASK can be started only if the current clock + sevice duration < time_father_started + timebox of the father activity
		if (spemType.equalsIgnoreCase("TASK") && (father != "")) {
			boolean mayStart = verifyIfTimeboxIsNotViolated(serviceDuration);
			if (mayStart == false)
					return false;
		}
		
		
		
		//		InServiceTemporaryEntitiesUntilDueTime possible = new InServiceTemporaryEntitiesUntilDueTime(esize, (float)d.Draw()); ANTIGO
		InServiceTemporaryEntitiesUntilDueTime possible = new InServiceTemporaryEntitiesUntilDueTime(esize, serviceDuration);

		
		Entity entity;
		
		// possible.entities[i] insere a entidade que ira trabalhar vindo da fila
		for(i = 0; i < esize && ok; i++) {					// as condicoes.
			
		    entity = ((DeadState)dead_states_from_v.elementAt(i)).dequeue(); // retira entidades...
			possible.entities[i] = entity;
			
			ok &= ((Expression)conditions_from_v.elementAt(i)).Evaluate(entity) != 0;// e testa condicao
		}

		if(!ok){
			Log.LogMessage("\t" + name + " Not all resources or entities are available");
			if(i > 0)		// alguma condicaoo nao foi satisfeita
			{
				for(i--; i >= 0; i--)		// devolve as entidades as respectivas filas
					((DeadState)dead_states_from_v.elementAt(i)).putBack(possible.entities[i]);
			}

			return false;
		}

		// obtem os recursos
		for(i = 0; i < resources_from_v.size(); i++){
			
			int qt = ((Integer)resources_qt_v.elementAt(i)).intValue();
			ResourceQ resourceQ = ((ResourceQ)resources_from_v.elementAt(i));
			String resourceQueueName = ((ResourceQ)resources_from_v.elementAt(i)).name;
			
			resourceQ.Acquire(qt);
				
			Log.LogMessage("\t" + name + ":Acquired " + qt + " resources from " + resourceQueueName);
		}

		Log.LogMessage("\t" + name +  " scheduling itself in the calendar to the due time..: " + (serviceDuration + this.s.GetClock()) + 
				       " by notifiying the scheduler");
		
		possible.duetime = RegisterEvent(possible.duetime);		// notifica scheduler
		queueOfEntitiesAndResourcesInService.Enqueue(possible);							// coloca na fila de servi�o
		 
		if(obs != null){
			obs.StateChange(Observer.BUSY);
			for(i = 0; i < possible.entities.length; i++)
				obs.Incoming(possible.entities[i]);
		}

		for(i = 0; i < possible.entities.length; i++) {
			String deadStateName = ((DeadState)dead_states_from_v.elementAt(i)).name;
			long entityId = possible.entities[i].getId();
			
			Log.LogMessage("\t"+ name + ":Entity " + entityId + " got from " + deadStateName);
		}
		
		// Commented lines to be worked when trying to acquire entities in batch mode
		//		for(i = 0; i < esize && ok; i++)					// as entidades...
		//			ok &= ((DeadState)dead_states_from_v.elementAt(i)).HasEnough(((Integer)entities_qt_v.elementAt(i)).intValue());
		//					int qt;
		//					DeadState deadState = (DeadState)(dead_states_from_v.elementAt(i));
		//					deadState.Acquire(qt = ((Integer)entities_qt_v.elementAt(i)).intValue());
		//					Log.LogMessage("\t" + name + ":Acquired " + qt + " entities from " +
		//							((DeadState)dead_states_from_v.elementAt(i)).name);
		timeWasStarted = s.GetClock();
 		Log.LogMessage("\t"+ name + " started at: " + timeWasStarted);
		return true;
	}

	private boolean verifyIfTimeboxIsNotViolated(float serviceDuration) {
		
			double timebox = 0.0;  
			Activity ac = null;
			Vector v = s.getActivestates();
			for (int j =0; j < v.size(); j++) {
				ActiveState a = (ActiveState)v.get(j);
				if (a.name.equalsIgnoreCase(father)) {
					 ac = (Activity)a;
					 timebox = ac.timeBox;
				}
			}
			
			if (this.s.GetClock() + serviceDuration > ac.timeWasStarted + timebox) {  // cannot start
				Log.LogMessage("\t" + name + " was not possible to start because there are no entities or resources available or since the current clock..: " + this.s.GetClock() + " + service duration..:" +
			                   serviceDuration + " > "  + "Time father started..: " + ac.timeWasStarted  + " + timebox..:" + timebox);
				return false;
			}
		
		return true;
	}
	
	public Vector getEntities_from_v() {
		return dead_states_from_v;
	}

	public static boolean isBeginOfSimulation() {
		return isBeginOfSimulation;
	}

	public static void setBeginOfSimulation(boolean isBeginOfSimulation) {
		Activity.isBeginOfSimulation = isBeginOfSimulation;
	}

	public String getSpemType() {
		return spemType;
	}

	public void setSpemType(String spemType) {
		this.spemType = spemType;
	}

	public String getProcessingUnit() {
		return processingUnit;
	}

	public void setProcessingUnit(String processingUnit) {
		this.processingUnit = processingUnit;
	}

	public String getDependencyType() {
		return dependencyType;
	}

	public void setDependencyType(String dependencyType) {
		this.dependencyType = dependencyType;
	}

	public String getProcessingType() {
		return processingType;
	}

	public void setProcessingType(String processingType) {
		this.processingType = processingType;
	}
	
	private boolean isProcessByClassOfEntitiesConditionSatisfied() {
		Log.LogMessage("\n\t" + name + ": dependency type =\"FINISH-TO-START\" , processing_type = \"PROCESS-BY-CLASS\"");

		int esize = dead_states_from_v.size();
		String previousQueueName = "";

		for (int i = 0; i < esize; i++) {
			DeadState incomingQueue = (DeadState) this.getEntities_from_v().get(i);
			System.out.println("Task: " + name + "   Incoming queue name:  " + incomingQueue.name + "  incoming queue count: "+ incomingQueue.count);
			previousQueueName = incomingQueue.name;
		}

		// In order to start an activity with finish-to-start dependency and processing type by class of entities 
		// we first must verify if the previous activity has already finished (producing the class of entities)
		// this is done by comparing the counter of entities produced by the previous activity with the number of entities describring
		// the class of entities. The previous activity is identified when the output Dead State of the previous activity is the 
		// same the input dead state of the current activity
		Iterator it =  s.getSimulationManager().GetActiveStatesIterator();

		while (it.hasNext()){

			InternalActiveEntry internalActivityEntry = (InternalActiveEntry)it.next();
			String outcomeQueueNamePreviousActivity = (String)internalActivityEntry.getToQueue().get(0);

			if (previousQueueName.equals(outcomeQueueNamePreviousActivity)) { 
				System.out.println("Outcome queue of previous activity " + outcomeQueueNamePreviousActivity);
				Activity ac = (Activity)internalActivityEntry.getActiveState();
				System.out.println("Contador da atividade previa..: " + ac.numberOfEntitiesProduced);
		     
			    if (ac.numberOfEntitiesProduced != SimulationManager.quantityOfEntitiesInClass) {
			    	Log.LogMessage("\t" + name + ": it was not possible to start " + name + " this time, since previous activity did not finish\n");
			    	return false;
			    }
			}
		}
		return true;
	}
	
	public double geTimeBox() {
		return timeBox;
	}
	public void setTimeBox(double timeBox) {
		this.timeBox = timeBox;
	}

	public double getTimeWasStarted() {
		return timeWasStarted;
	}

	public void setTimeWasStarted(double timeWasStarted) {
		this.timeWasStarted = timeWasStarted;
	}

	public String getFather() {
		return father;
	}

	public void setFather(String father) {
		this.father = father;
	}
	
	// Commented lines to be worked when trying to acquire entities in batch mode
	//	public void ConnectQueues(DeadState from, Expression cond, DeadState to, int qty_needed){
	//		dead_states_from_v.add(from);
	//		conditions_from_v.add(cond);
	//		entities_to_v.add(to);
	//		entities_qt_v.add(new Integer(qty_needed));
	//	}
}