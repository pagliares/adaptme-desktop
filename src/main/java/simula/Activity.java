// Arquivo Activity.java
// Implementa��o das Classes do Grupo de Modelagem da Biblioteca de Simula��o JAVA
// 9.Abr.1999	Wladimir

package simula;

import java.sql.Time;
import java.util.*;

 
 
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
	
	private String dependencyType = "";
	private String acd_processing_type = "";
	private String spemType = "";
	private String processingUnit = "";
	
	 
  
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
	}
	
	/**
	 * determina o tempo de servi�o de acordo com a distribui��o especificada;
	 * os par�metros da distribui��o s�o passados na cria��o do objeto.
	 */
	public void SetServiceTime(Distribution d){
		this.d = d;
		RegisterEvent((float)d.Draw()); // PAGLIARES. 20h de depuracao para fazer com que a simulacao funcionasse sem generate activity
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
			DeadState q = (DeadState)entities_to_v.elementAt(i);	// obt�m fila associada
			if(q.HasSpace())										// se tem espa�o
				q.enqueue(inServiceEntities.entities[i]);									// envia ao estado morto
		}
		
		for(int i = 0; i < resources_to_v.size(); i++){		// e os recursos.
			int qt;
			ResourceQ q = (ResourceQ)resources_to_v.elementAt(i);	// obt�m fila associada
			q.Release(qt = ((Integer)resources_qt_v.elementAt(i)).intValue());// envia ao estado morto
			Log.LogMessage(name + ":Released " + qt + " resources to " +
				((ResourceQ)resources_to_v.elementAt(i)).name);
		}

		if(obs != null){
			if(queueOfEntitiesAndResourcesInService.IsEmpty())
				obs.StateChange(Observer.IDLE);
			for(int i = 0; i < inServiceEntities.entities.length; i++)
				obs.Outgoing(inServiceEntities.entities[i]);
		}
		
		for(int i = 0; i < inServiceEntities.entities.length; i++) {
			Log.LogMessage(name + ":Entity " + inServiceEntities.entities[i].getId() +
				" sent to " + ((DeadState)entities_to_v.elementAt(i)).name);
		}
  		return true;
	}
	
	public boolean CServed(){
		
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

		for(i = 0; i < resources_from_v.size() && ok; i++)	// os recursos...
			ok &= ((ResourceQ)resources_from_v.elementAt(i)).
				HasEnough(((Integer)resources_qt_v.elementAt(i)).intValue());

		for(i = 0; i < esize && ok; i++)					// as entidades...
			ok &= ((DeadState)dead_states_from_v.elementAt(i)).HasEnough();

		InServiceTemporaryEntitiesUntilDueTime possible = new InServiceTemporaryEntitiesUntilDueTime(esize, (float)d.Draw());
		Entity entity;
		// possible.entities[i] insere a entidade que ira trabalhar vindo da fila
		for(i = 0; i < esize && ok; i++) {					// as condi��es.
		
			possible.entities[i] = entity = ((DeadState)dead_states_from_v.elementAt(i)).dequeue();
																// retira entidades...
			
			ok &= ((Expression)conditions_from_v.elementAt(i)).Evaluate(entity) != 0;
																// e testa condi��o
		}

		if(!ok){
			if(i > 0)		// alguma condi��o n�o foi satisfeita
			{
				for(i--; i >= 0; i--)		// devolve as entidades �s respectivas filas
					((DeadState)dead_states_from_v.elementAt(i)).putBack(possible.entities[i]);
			}

			return false;
		}

		// obt�m os recursos

		for(i = 0; i < resources_from_v.size(); i++){
			int qt;	
			((ResourceQ)resources_from_v.elementAt(i)).
				Acquire(qt = ((Integer)resources_qt_v.elementAt(i)).intValue());
				
			Log.LogMessage("\n" + name + ":Acquired " + qt + " resources from " +
				((ResourceQ)resources_from_v.elementAt(i)).name);
		}

		possible.duetime = RegisterEvent(possible.duetime);		// notifica scheduler
		queueOfEntitiesAndResourcesInService.Enqueue(possible);							// coloca na fila de servi�o
		 
		if(obs != null){
			obs.StateChange(Observer.BUSY);
			for(i = 0; i < possible.entities.length; i++)
				obs.Incoming(possible.entities[i]);
		}

		for(i = 0; i < possible.entities.length; i++) {
			Log.LogMessage(name + ":Entity " + possible.entities[i].getId() +
				" got from " + ((DeadState)dead_states_from_v.elementAt(i)).name);
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

	public String getAcd_processing_type() {
		return acd_processing_type;
	}

	public void setAcd_processing_type(String acd_processing_type) {
		this.acd_processing_type = acd_processing_type;
	}
}