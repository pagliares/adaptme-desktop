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
	protected Vector entities_from_v, entities_to_v, conditions_from_v,
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
	
	public static int acounter = 0; // Pagliares
	public static boolean isBeginOfSimulation = true;
  
	/**
	 * constr�i um estado ativo sem conex�es ou tempo de servi�o definidos.
	 */
	public Activity(Scheduler s){
		super(s);
		// constr�i vetores de liga��es
		entities_from_v = new Vector(1, 1);
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
		 
		// ACHO QUE TENHO QUE COLOCAR A GALERA PARA TRABALHAR AQUI
//			solucao();
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
		entities_from_v.add(from);
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
			
		InServiceEntitiesUntilDueTime inServiceEntities = queueOfEntitiesAndResourcesInService.Dequeue();

		if (isBeginOfSimulation) { // pagliares 
			isBeginOfSimulation = false; // pagliares
			return true; // pagliares
		}
		
//		if (counter ==0) { // pagliares 
//			counter = counter +1; // pagliares
//			return true; // pagliares
//		}
		
		if(inServiceEntities == null)								// n�o h� mais nada a servir
			return false;

		
		
		if(time < inServiceEntities.duetime){			// servi�o foi interrompido e scheduler n�o foi notificado									
			queueOfEntitiesAndResourcesInService.PutBack(inServiceEntities);		// devolve � fila para ser servido mais tarde
			return false;
		}

		// fim de servi�o!
		
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
		
			// pagliares
//		   Log.LogMessage("Entity END time...: " + (int)inServiceEntities.entities[i].getActivityEndTime());
//			changeDelimitersState();
//
//			Log.LogMessage("Delimiter...: " + this.getActivityDelimiter());

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
		int esize = entities_from_v.size();
		int i;

		for(i = 0; i < resources_from_v.size() && ok; i++)	// os recursos...
			ok &= ((ResourceQ)resources_from_v.elementAt(i)).
				HasEnough(((Integer)resources_qt_v.elementAt(i)).intValue());

		for(i = 0; i < esize && ok; i++)					// as entidades...
			ok &= ((DeadState)entities_from_v.elementAt(i)).HasEnough();

		InServiceEntitiesUntilDueTime possible = new InServiceEntitiesUntilDueTime(esize, (float)d.Draw());
		Entity entity;
		// possible.entities[i] insere a entidade que ira trabalhar vindo da fila
		for(i = 0; i < esize && ok; i++) {					// as condi��es.
		
			possible.entities[i] = entity = ((DeadState)entities_from_v.elementAt(i)).dequeue();
																// retira entidades...
			
//			possible.entities[i].setActivityBeginTime(s.GetClock()); // Pagliares' code to calculate
			// time an entity spends on a process
			
			ok &= ((Expression)conditions_from_v.elementAt(i)).Evaluate(entity) != 0;
																// e testa condi��o
		}

		if(!ok){
			if(i > 0)		// alguma condi��o n�o foi satisfeita
			{
				for(i--; i >= 0; i--)		// devolve as entidades �s respectivas filas
					((DeadState)entities_from_v.elementAt(i)).putBack(possible.entities[i]);
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
				" got from " + ((DeadState)entities_from_v.elementAt(i)).name);
			
			// pagliares
//			Log.LogMessage("Entity BEGIN time...: " + possible.entities[i].getActivityBeginTime());
//			changeDelimitersState();
//			Log.LogMessage("Delimiter...: " + this.getActivityDelimiter());

 			
		}
		return true;
	}
	
	private boolean solucao() {
		
				return true;
	}
	
	/**
	 * implementa protocolo.
	 */
	public boolean BServedBackup(float time){
		// Pagliares
		// TODO CT-00 -  SO GENERATE (First pass). VARIAVEIS: inServiceEntities =  
		// TODO CT-00 PAGLIARES: (Second pass) ANTES DA INSTRUCAO SO GENERATE: list =  null   , root = generate,  child = nao existe 
				
				 
		// @TODO PAGLIARES: (First pass) ANTES DA INSTRUCAO SO ACTIVITY: list =   null  , root =  activity   ,  child =  nao existe
		// @TODO PAGLIARES: (Second pass) ANTES DA INSTRUCAO SO ACTIVITY: list =  null    , root =  null (PROBLEMA)   ,  child =  nao existe
				
		// @TODO PAGLIARES (CT_03): (First pass) ANTES DA INSTRUCAO GENERATE AND TWO ACTIVITIES: list = null     , root = activity a_1      ,  child = nao existe 
		// @TODO PAGLIARES(CT_03) (Second pass) ANTES DA INSTRUCAO GENERATE AND TWO ACTIVITIES: list =  null     , root =  activity a_1      ,  child =   nao existe
 
		
		/* TODO CT-02 - PROCESS  WITH PRIORITIZE USER STORIES ONLY
		FIRST HIT IN THE BREAKPOINT 
			VARIAVEIS: time = 3.52   entity = nao existe   this.inservice = false  
			name: a_o     toq = nao existe             this.s.calendar.root =null    this.s.calendar.list = activity a_0
			inServiceEntities =  nao existe
		SECOND HIT IN THE BREAKPOINT 
			VARIAVEIS: time =    entity =    inservice =    
			name  =  toqueue =   root =    list =  
		*/
//		 COLOQUEI A LINHA ABAIXO = PARECE QUE TEM UM CONFLITO ENTRE VARIAVEL LOCAL E NAO LOCAL
		InServiceEntitiesUntilDueTime inServiceEntities = queueOfEntitiesAndResourcesInService.Dequeue(); // RETORNA NULL na primeira passagem, processo com generate e duas atividades
 //		InServiceEntities inServiceEntities = queueOfEntitiesAndResourcesInService.Dequeue(); // RETORNA NULL na primeira passagem, processo com generate e duas atividades

		/* TODO CT-02 - PROCESS  WITH PRIORITIZE USER STORIES ONLY
		FIRST HIT IN THE BREAKPOINT 
			VARIAVEIS: time = 3.52   entity = nao existe   this.inservice = false  
			name: a_o     toq = nao existe             this.s.calendar.root =null    this.s.calendar.list = activity a_0
			inServiceEntities =  NULL
		SECOND HIT IN THE BREAKPOINT 
			VARIAVEIS: time =    entity =    inservice =    
			name  =  toqueue =   root =    list =  
		*/
		
		
		if(inServiceEntities == null)								// n�o h� mais nada a servir
			return false; // PAGLIARES. AQUI FAZ O FLAG EXECUTED RETORNAR FALSE

		for(int i = 0; i < entities_to_v.size(); i++)	{	// as entidades...
			DeadState deadState = (DeadState)entities_to_v.elementAt(i);	// obt�m fila associada
			if(deadState.HasSpace())										// se tem espa�o
				deadState.enqueue(inServiceEntities.entities[i]);									// envia ao estado morto
		}
		
		for(int i = 0; i < resources_to_v.size(); i++){		// e os recursos.
			int qt;
			ResourceQ resourceQueue = (ResourceQ)resources_to_v.elementAt(i);	// obt�m fila associada
			resourceQueue.Release(qt = ((Integer)resources_qt_v.elementAt(i)).intValue());// envia ao estado morto
			Log.LogMessage(name + ":Released " + qt + " resources to " +
				((ResourceQ)resources_to_v.elementAt(i)).name);
		}

		if(obs != null){
			if(queueOfEntitiesAndResourcesInService.IsEmpty())
				obs.StateChange(Observer.IDLE);
			for(int i = 0; i < inServiceEntities.entities.length; i++)
				obs.Outgoing(inServiceEntities.entities[i]);
		}
		
		for(int i = 0; i < inServiceEntities.entities.length; i++)
			Log.LogMessage(name + ":Entity " + inServiceEntities.entities[i].getId() +
				" sent to " + ((DeadState)entities_to_v.elementAt(i)).name);
			
		// NAO CHEGA NESTE PONTO inServiceEntities FICA NULL, FAZENDO COM QUE O METODO RETORNE COM FALSE
		/* TODO CT-02 - PROCESS  WITH PRIORITIZE USER STORIES ONLY
		FIRST HIT IN THE BREAKPOINT 
			VARIAVEIS: time =    entity =    inservice =    
			name:      toqueue =              root =    list =  
		SECOND HIT IN THE BREAKPOINT 
			VARIAVEIS: time =    entity =    inservice =    
			name  =  toqueue =   root =    list =  
		*/
		
 		return true;
	}
	
	/**
	 * implementa protocolo.
	 */
	public boolean CServedBackup(){
         // CONTINUAR DAQUI. PARECE QUE O PROBLE E QUE SEM GENERATE, INSERVICE ENTITIES NAO EG CRIADO
		// primeiro verifica se todos os recursos e entidades est�o dispon�veis
		
		
		/* TODO CT-02 - PROCESS  WITH PRIORITIZE USER STORIES ONLY (NAO CAI AQUI NESTE CASO TESTE)
		FIRST HIT IN THE BREAKPOINT 
			VARIAVEIS: time = 3.52   entity = nao existe   this.inservice = false  
			name: a_o     toq = nao existe             this.s.calendar.root =null    this.s.calendar.list = activity a_0  entity
			inServiceEntities =  nao existe
		SECOND HIT IN THE BREAKPOINT 
			VARIAVEIS: time =    entity =    inservice =    
			name  =  toqueue =   root =    list =  
		*/
		
		
		boolean ok = true;
		int sizeEntitiesFrom = entities_from_v.size();
		int i;

		for(i = 0; i < resources_from_v.size() && ok; i++)	// os recursos...
			ok &= ((ResourceQ)resources_from_v.elementAt(i)).
				HasEnough(((Integer)resources_qt_v.elementAt(i)).intValue());

		for(i = 0; i < sizeEntitiesFrom && ok; i++)					// as entidades...
			ok &= ((DeadState)entities_from_v.elementAt(i)).HasEnough();

		// A linha abaixo e minha - 6 sept
//		inServiceEntities = new InServiceEntities(sizeEntitiesFrom, (float)d.Draw());

		InServiceEntitiesUntilDueTime inServiceEntities = new InServiceEntitiesUntilDueTime(sizeEntitiesFrom, (float)d.Draw());
		
		/* TODO CT-02 - PROCESS  WITH PRIORITIZE USER STORIES ONLY (NAO CAI AQUI NESTE CASO TESTE)
		FIRST HIT IN THE BREAKPOINT 
			VARIAVEIS: time = 3.52   entity = nao existe   this.inservice = false  
			name: a_o     toq = nao existe             this.s.calendar.root =null    this.s.calendar.list = activity a_0  entity
			inServiceEntities =  nao existe
		SECOND HIT IN THE BREAKPOINT 
			VARIAVEIS: time =    entity =    inservice =    
			name  =  toqueue =   root =    list =  
		*/
		
		Entity entity;
		for(i = 0; i < sizeEntitiesFrom && ok; i++) {					// as condi��es.
		
			inServiceEntities.entities[i] = entity = ((DeadState)entities_from_v.elementAt(i)).dequeue();
																// retira entidades...
			ok &= ((Expression)conditions_from_v.elementAt(i)).Evaluate(entity) != 0;
																// e testa condi��o
		}

		if(!ok){
			if(i > 0){	// alguma condi��o n�o foi satisfeita	
				for(i--; i >= 0; i--)		// devolve as entidades �s respectivas filas
					((DeadState)entities_from_v.elementAt(i)).putBack(inServiceEntities.entities[i]);
			}
			return false;
		}

		// obt�m os recursos

		for(i = 0; i < resources_from_v.size(); i++){
			int quantity;	
			((ResourceQ)resources_from_v.elementAt(i)).
				Acquire(quantity = ((Integer)resources_qt_v.elementAt(i)).intValue());
				
			Log.LogMessage(name + ":Acquired " + quantity + " resources from " +
				((ResourceQ)resources_from_v.elementAt(i)).name);
		}

		/* TODO CT-02 - PROCESS  WITH PRIORITIZE USER STORIES ONLY (NAO CAI AQUI NESTE CASO TESTE)
		FIRST HIT IN THE BREAKPOINT 
			VARIAVEIS: time = 3.52   entity = nao existe   this.inservice = false  
			name: a_o     toq = nao existe             this.s.calendar.root =null    this.s.calendar.list = activity a_0
			inServiceEntities =  nao existe
		SECOND HIT IN THE BREAKPOINT 
			VARIAVEIS: time =    entity =    inservice =    
			name  =  toqueue =   root =    list =  
		*/
		
		inServiceEntities.duetime = RegisterEvent(inServiceEntities.duetime);		// notifica scheduler
		queueOfEntitiesAndResourcesInService.Enqueue(inServiceEntities);							// coloca na fila de servi�o
		
		 
		
		if(obs != null){
			obs.StateChange(Observer.BUSY);
			for(i = 0; i < inServiceEntities.entities.length; i++)
				obs.Incoming(inServiceEntities.entities[i]);
		}

		for(i = 0; i < inServiceEntities.entities.length; i++)
			Log.LogMessage(name + ":Entity " + inServiceEntities.entities[i].getId() +
				" got from " + ((DeadState)entities_from_v.elementAt(i)).name);

		
		/* TODO CT-02 - PROCESS  WITH PRIORITIZE USER STORIES ONLY (NAO CAI AQUI NESTE CASO TESTE)
		FIRST HIT IN THE BREAKPOINT 
			VARIAVEIS: time = 3.52   entity = nao existe   this.inservice = false  
			name: a_o     toq = nao existe             this.s.calendar.root =null    this.s.calendar.list = activity a_0
			inServiceEntities =  nao existe
		SECOND HIT IN THE BREAKPOINT 
			VARIAVEIS: time =    entity =    inservice =    
			name  =  toqueue =   root =    list =  
		*/
		return true;
	}

	public Vector getEntities_from_v() {
		return entities_from_v;
	}
	
//	private void changeDelimitersState() {
//		switch (name){ // ISSO E TASK, NAO E LEVADO EM CONSIDERACAO. JUSTIFICA A NECESSIDADE DO ATRIBUTO SPEM_TYPE PARA ALL BUT TASK
//		case "Iteration":
//			if (getIterationDelimiter().equals("BEGIN"))
//					setIterationDelimiter("END");
//				else
//					setIterationDelimiter("BEGIN");	
//			break;
//		case "Release":
//			if (getReleaseDelimiter().equals("BEGIN"))
//				setReleaseDelimiter("END");
//			else
//				setReleaseDelimiter("BEGIN");	
//		break;
//		case "Activity":
//			if (getActivityDelimiter().equals("BEGIN"))
//				setActivityDelimiter("END");
//			else
//				setActivityDelimiter("BEGIN");	
//		break;
//		case "Phase":
//			if (getPhaseDelimiter().equals("BEGIN"))
//				setPhaseDelimiter("END");
//			else
//				setPhaseDelimiter("BEGIN");	
//		break;
//		}
//	}
	
}