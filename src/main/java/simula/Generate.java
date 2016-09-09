// Arquivo Generate.java
// Implementa��o das Classes do Grupo de Modelagem da Biblioteca de Simula��o JAVA
// 26.Mar.1999	Wladimir

package simula;

/**
 * Classe que implementa um Generate
 */
public class Generate extends ActiveState
{
	/**
	 * refer�ncia da fila conectada
	 */
	protected DeadState to_q;		
	/**
	 * flag "gerando entidade"
	 */
	protected boolean inservice;
	/**
	 * gerador de n�meros aleat�rios
	 * de uma dada distribui��o
	 */
	protected Distribution d;		

	/**
	 * valor inicial dos atributos
	 * das entidades geradas
	 */
	protected float[] attvals;		
	/**
	 * ids dos atributos das entidades
	 */
	protected String[] entitiesAttributesIds;		
	/**
	 * n�mero de entidades geradas
	 */
	public int Generated = 0;	
	/**
	 * n�mero de entidades perdidas
	 */
	public int Wasted = 0;			

	/**
	 * constr�i um estado ativo sem conex�es ou tempo de servi�o definidos.
	 */
	public Generate(Scheduler s){
		super(s);
	}
	
	/**
	 * determina destino das entidades geradas.
	 */
	public void ConnectQueue(DeadState to){
		if(to_q == null)
			to_q = to;
	}
	
	/**
	 * determina o tempo de servi�o de acordo com a distribui��o especificada;
	 * os par�metros da distribui��o s�o passados na cria��o do objeto 
	 * e registra primeiro evento de chegada.
	 */
	public void SetServiceTime(Distribution d){
		this.d = d;
		RegisterEvent((float)d.Draw());
		inservice = true;
	}

	/**
	 * Coloca objeto em seu estado inicial para simula��o
	 */
	public void Clear(){
		super.Clear();
		Generated = 0;
		Wasted = 0;
		RegisterEvent((float)d.Draw());
		inservice = true;
	}
	
	/**
	 * atribui vetores de atributos que cont�m ids e valores
	 * iniciais dos atributos de cada entidade gerada por 
	 * uma inst�ncia deste estado ativo. 
	 */
	public void SetEntitiesAtts(String[] ids, float[] values)
	{
		if(ids.length != values.length)
			throw new IllegalArgumentException
				("Vetores de ids e valores devem ter o mesmo n�mero de elementos");
		entitiesAttributesIds = ids;
		attvals = values;
	}

	/**
	 * implementa protocolo.
	 */
	public boolean BServed(float time){
		
		/*  TODO CT00 - MINIMAL PROCESS WITH ONLY GENERATE ACTIVITY
			FIRST HIT IN THE BREAKPOINT 
				VARIAVEIS: time = 4.999  entity = nao existe  this.inservice = true   
				           name a_0      toq = q_o            this.s.calendar.root = null  this.s.calendar.list = generate a_0
			SECOND HIT IN THE BREAKPOINT 
				VARIAVEIS: time = 9.998  entity = nao existe  this.inservice = true   
				           name = a_0       toq = q_o            this.s.calendar.root = null   this.s.calendar.list =  generate a_0
		*/
 		 
				
		/* TODO CT-01 - PROCESS WITH GENERATE AND PRIORITIZE USER STORIES  
			FIRST HIT IN THE BREAKPOINT 
				VARIAVEIS: time = 9.998  entity =  nao existe inservice = true  
				name a_1 toqueue = q_1  root = null  list = generate a_1
			SECOND HIT IN THE BREAKPOINT 
				VARIAVEIS: time = 9.998  entity =  nao existe inservice = true  
				name a_1 toqueue = q_1  root = null  list = generate a_1
		
			TODO CT-02 - PROCESS WITH GENERATE AND PRIORITIZE USER STORIES  
				FIRST HIT IN THE BREAKPOINT 
					VARIAVEIS: time = 9.998  entity =  nao existe inservice = true  
					name a_1 toqueue = q_1  root = null  list = generate a_1
				SECOND HIT IN THE BREAKPOINT 
					VARIAVEIS: time = 9.998  entity =  nao existe inservice = true  
					name a_1 toqueue = q_1  root = null  list = generate a_1
		*/
		
		/* TODO CT-02 - PROCESS  WITH PRIORITIZE USER STORIES ONLY
		FIRST HIT IN THE BREAKPOINT 
			VARIAVEIS: time = 9.998  entity =  nao existe inservice = true  
			name a_1 toqueue = q_1  root = null  list = generate a_1
		SECOND HIT IN THE BREAKPOINT 
			VARIAVEIS: time = 9.998  entity =  nao existe inservice = true  
			name a_1 toqueue = q_1  root = null  list = generate a_1
	
		TODO CT-02 - PROCESS WITH GENERATE AND PRIORITIZE USER STORIES  
			FIRST HIT IN THE BREAKPOINT 
				VARIAVEIS: time = 9.998  entity =  nao existe inservice = true  
				name a_1 toqueue = q_1  root = null  list = generate a_1
			SECOND HIT IN THE BREAKPOINT 
				VARIAVEIS: time = 9.998  entity =  nao existe inservice = true  
				name a_1 toqueue = q_1  root = null  list = generate a_1
	*/
		
		/* TODO CT-03 - PROCESS WITH GENERATE, PRIORITIZE USER STORIES AND ESTIMATE USER STORIES  
		FIRST HIT IN THE BREAKPOINT 
			VARIAVEIS: time = 9.998  entity =  nao existe inservice = true  
			name a_1 toqueue = q_1  root = null  list = generate a_1
		SECOND HIT IN THE BREAKPOINT 
			VARIAVEIS: time = 9.998  entity =  nao existe inservice = true  
			name a_1 toqueue = q_1  root = null  list = generate a_1
	
		TODO CT-02 - PROCESS WITH GENERATE AND PRIORITIZE USER STORIES  
			FIRST HIT IN THE BREAKPOINT 
				VARIAVEIS: time = 9.998  entity =  nao existe inservice = true  
				name a_1 toqueue = q_1  root = null  list = generate a_1
			SECOND HIT IN THE BREAKPOINT 
				VARIAVEIS: time = 9.998  entity =  nao existe inservice = true  
				name a_1 toqueue = q_1  root = null  list = generate a_1
	*/
		
		Entity entity = new Entity(time);	// cria entidade e atribui-lhe instante de cria��o
		
		/*  TODO CT00 - MINIMAL PROCESS WITH ONLY GENERATE ACTIVITY
			FIRST HIT IN THE BREAKPOINT 
				VARIAVEIS:  entity = 1  entity creation time = 4.999  time = 4.99
			SECOND HIT IN THE BREAKPOINT 
				VARIAVEIS:  entity = 2   entity creation time = 9.998    time =  9.998
		*/
		
		/* TODO CT-01 - PROCESS WITH GENERATE AND PRIORITIZE USER STORIES  
			FIRST HIT IN THE BREAKPOINT  
				VARIAVEIS:  entity = 12  entity creation time = 9.998 time = 9.998
			SECOND HIT IN THE BREAKPOINT 
				VARIAVEIS:  entity = 1  entity creation time = 4.999 
		*/
		
		/* TODO CT-02 - PROCESS WITHOUT GENERATE AND WITH PRIORITIZE USER STORIES  
			FIRST HIT IN THE BREAKPOINT 
				VARIAVEIS:  entity = 12  entity creation time = 9.998 time = 9.998
			SECOND HIT IN THE BREAKPOINT 
				VARIAVEIS:  entity = 1  entity creation time = 4.999 
		*/
		
		/* TODO CT-03 - PROCESS WITH GENERATE, PRIORITIZE USER STORIES AND ESTIMATE USER STORIES  
			FIRST HIT IN THE BREAKPOINT 
				VARIAVEIS:  entity = 12  entity creation time = 9.998 time = 9.998
			SECOND HIT IN THE BREAKPOINT 
				VARIAVEIS:  entity = 1  entity creation time = 4.999 
		*/
		
		// atribui atributos espec�ficos a e

		if(entitiesAttributesIds != null){
			for(int i = 0; i < entitiesAttributesIds.length; i++)
				entity.setAttribute(entitiesAttributesIds[i], attvals[i]);	
		}		
		
		if(to_q.HasSpace())	{			// se tem espa�o para entidade na fila
			to_q.Enqueue(entity);
			Log.LogMessage(name + ":Entity " + entity.getId() + 
				" generated and sent to " + to_q.name);
			if(obs != null)
				obs.Outgoing(entity);
		}
		else {
			Wasted++;					// mais uma entidade desperdi�ada. PAGLIARES: por falta de espaco
			Log.LogMessage(name + ":Entity " + entity.getId() +
				" generated but wasted");
		}
		
		
		Generated++;					// mais uma entidade gerada

		inservice = false;				// libera a gera��o de novas entidades
		if(obs != null)
			obs.StateChange(Observer.BUSY);// marca fim do idle-time => determina inter-arrival


	/*  TODO CT00 - MINIMAL PROCESS WITH ONLY GENERATE ACTIVITY
		FIRST HIT IN THE BREAKPOINT 
			VARIAVEIS:  entity = 1  entity creation time = 4.999  entity qentertime = 4.99
		SECOND HIT IN THE BREAKPOINT 
			VARIAVEIS:  entity = 2   entity creation time = 9.998   entity qentertime = 9.988
	*/
	
	/* TODO CT-01 - PROCESS WITH GENERATE AND PRIORITIZE USER STORIES  
		FIRST HIT IN THE BREAKPOINT  
			VARIAVEIS:  entity = 12  entity creation time = 9.998 time = 9.998
		SECOND HIT IN THE BREAKPOINT 
			VARIAVEIS:  entity = 1  entity creation time = 4.999 
	*/
	
	/* TODO CT-02 - PROCESS WITHOUT GENERATE AND WITH PRIORITIZE USER STORIES  
		FIRST HIT IN THE BREAKPOINT 
			VARIAVEIS:  entity = 12  entity creation time = 9.998 time = 9.998
		SECOND HIT IN THE BREAKPOINT 
			VARIAVEIS:  entity = 1  entity creation time = 4.999 
	*/
	
	/* TODO CT-03 - PROCESS WITH GENERATE, PRIORITIZE USER STORIES AND ESTIMATE USER STORIES  
		FIRST HIT IN THE BREAKPOINT 
			VARIAVEIS:  entity = 12  entity creation time = 9.998 time = 9.998
		SECOND HIT IN THE BREAKPOINT 
			VARIAVEIS:  entity =   entity creation time = 4.999 
	*/
		
		return true;			
	}

	/**
	 * implementa protocolo; agenda evento B se n�o est� "gerando" outra entidade.
	 * sempre retorna false pois n�o tem efeito no instante de simula��o atual.
	 */
	public boolean CServed() {

		/*  TODO CT00 - MINIMAL PROCESS WITH ONLY GENERATE ACTIVITY
		FIRST HIT IN THE BREAKPOINT 
			VARIAVEIS:  inservice = false  name = a_0  this.s.calendar.list = NULL this.s.calendar.root = NULL
			            this.s.clock = 4.99  this.s.running = true this.s.activestates = generate a_o
		SECOND HIT IN THE BREAKPOINT 
			VARIAVEIS:  inservice = false  name = a_0  this.s.calendar.list = NULL this.s.calendar.root = NULL
			            this.s.clock = 9.998  this.s.running = true this.s.activestates = generate a_o
		 */
		
		if(!inservice)					// se n�o est� "gerando" outra entidade...
		{
			float t = (float)d.Draw();		// obt�m instante de cria��o da pr�xima entidade
			RegisterEvent(t);				// agenda evento B
			inservice = true;				// est� "gerando"
			if(obs != null)
				obs.StateChange(Observer.IDLE);// para o Generate, o idle-time � o inter-arrival
			Log.LogMessage(name + ":Scheduled entity generation to " + t);
		}

		/*  TODO CT00 - MINIMAL PROCESS WITH ONLY GENERATE ACTIVITY
		FIRST HIT IN THE BREAKPOINT 
			VARIAVEIS:  inservice = true  name = a_0  this.s.calendar.list = NULL this.s.calendar.root = generate a_o
			            this.s.clock = 4.99  this.running = true this.s.activestates = generate a_o
		SECOND HIT IN THE BREAKPOINT 
			VARIAVEIS:  inservice = true  name = a_1  this.s.calendar.list = NULL this.s.calendar.root = generate a_1
			            this.s.clock = 9.998  this.s.running = true this.s.activestates = generate a_1
		 */
		return false;

	}
}