// Arquivo ResourceQ.java
// Implementa��o das Classes do Grupo de Modelagem da Biblioteca de Simula��o JAVA
// 26.Mar.1999	Wladimir

package simula;

public class ResourceQ extends DeadState
{
	private short init_qty;

	/**
	 * constr�i uma fila de recursos com init_qty recursos iniciais.
	 */
	public ResourceQ(Scheduler s, int init_qty)
	{
		super(s, 0);	// sinaliza que este estado tem capacidade ilimitada
		this.init_qty = count = (short)init_qty;
	}
	
	/**
	 * constr�i uma fila de recursos com zero recursos iniciais.
	 */
	public ResourceQ(Scheduler s){super(s, 0);}
	
	/**
	 * Coloca objeto em seu estado inicial para simula��o
	 */
	public void clear()
	{
		super.clear();
		count = init_qty;;
	}
	
	/**
	 * n�o utilizada; produz erro em tempo de execu��o.
	 */
	public void enqueue(Entity e)
	{
		System.err.println("\nChamou Enqueue() de um objeto ResourceQ!\nEncerrando simula��o!");
		Scheduler.Get().Stop();
	}
	
	/**
	 * n�o utilizada; produz erro em tempo de execu��o.
	 */
	public Entity dequeue()
	{
		System.err.println("\nChamou Dequeue() de um objeto ResourceQ!\nEncerrando simula��o!");
		Scheduler.Get().Stop();
		return null;
	}
	/**
	 * n�o utilizada; produz erro em tempo de execu��o.
	 */
	public void putBack(Entity e) 
	{
		System.err.println("\nChamou PutBack() de um objeto ResourceQ!\nEncerrando simula��o!");
		Scheduler.Get().Stop();
	}
	
	/**
	 * retira n recursos do armaz�m; se n�o tem suficientes o resultado � imprevisto.
	 */
	public void Acquire(int n)
	{
		if(obs != null)
			obs.StateChange(Observer.IDLE);		// o argumento n�o tem sentido nesse caso
		count -= (short)n;
	}

	/**
	 * libera n recursos ao armaz�m; a integridade da opera��o � responsabilidade do usu�rio.
	 */
	public void Release(int n)
	{
		if(obs != null)
			obs.StateChange(Observer.IDLE);		// o argumento n�o tem sentido nesse caso
		count += (short)n;
	}

}