// Arquivo Destroy.java
// Implementa��o das Classes do Grupo de Modelagem da Biblioteca de Simula��o JAVA
// 26.Mar.1999	Wladimir

package simula;

/**
 * Implementa um Destroy no Systema
 * @author Wladimir
 */
public class Destroy extends ActiveState
{
	/**
	 * refer�ncia da fila conectada
	 */
	protected DeadState from_q;		
	
	/**
	 * n�mero de entidades destru�das
	 */
	public int Destroyed = 0;		

	/**
	 * constr�i um estado ativo sem conex�es ou tempo de servi�o definidos.
	 */
	public Destroy(Scheduler s){super(s);}

	/**
	 * determina origem das entidades destru�das.
	 * @param	from	the queue to connect from
	 */
	public void ConnectQueue(DeadState from)
	{
		if(from_q == null)
			from_q = from;
	}

	/**
	 * Coloca objeto em seu estado inicial para simula��o
	 */
	public void Clear()
	{
		super.Clear();
		Destroyed = 0;
	}

	/**
	 * retorna false (nunca executa evento agendado (B)).
	 */
	public boolean BServed(float time){return false;}

	/**
	 * consome entidades dispon�veis na fila e realiza estat�sticas (quando aplic�vel).
	 */
	public boolean CServed()
	{
		boolean got = false;

		while(from_q.HasEnough())			// enquanto tiver entidades a serem destru�das
		{
			Entity e = from_q.dequeue();	// retira entidade
			// faz estat�sticas
			if(obs != null)
				obs.Incoming(e);
			got = true;
			Destroyed++;
			Log.LogMessage(name + ":Entity " + e.getId() + 
				", from " + from_q.name + ", destroyed.");
		}
		
		return got;
	}
}