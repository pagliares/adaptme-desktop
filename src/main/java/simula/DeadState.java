// Arquivo DeadState.java
// Implementa��o das Classes do Grupo de Modelagem da Biblioteca de Simula��o JAVA
// 26.Mar.1999	Wladimir

package simula;

public abstract class DeadState
{
	private short capacity;		// quantidade m�xima de entidades neste estado

	/**
	 * n�mero de entidades presentes
	 */
	protected short count = 0;
	/**
	 * refer�ncia ao scheduler para obter rel�gio
	 * da simula��o (para as estat�sticas)
	 */
	protected Scheduler s;
	/**
	 * observador (para estat�sticas)
	 */
	protected Observer obs;	
	/**
	 * nome (para identificar estado para o log)
	 */
	public String name = "";	
	/**
	 * construtor: se max for nulo, tem capacidade ilimitada.
	 */
	public DeadState(Scheduler s, int max){capacity = (short)max; this.s = s;}	
	
	/**
	 * retorna true se h� espa�o para inserir nentities entidades. 
	 */
	public boolean HasSpace(int nentities)
	{return (capacity != 0)? ((short)nentities <= capacity - count) : true;}
	
	/**
	 * retorna true se h� espa�o para inserir uma entidade. 
	 */
	public boolean HasSpace()
	{return (capacity != 0)? (1 <= capacity - count) : true;}
	
	/**
	 * retorna true se h� nentities entidades (ou recursos) para serem retiradas.
	 */
	public boolean HasEnough(int nentities)	
	{return (short)nentities <= count;}
	
	/**
	 * retorna true se h� uma entidade (ou recurso) para ser retirada.
	 */
	public boolean HasEnough()	
	{return 1 <= count;}
	
	/**
	 * retorna o tamanho da fila (para ser usado por Observer).
	 */
	public short ObsLength(){return count;}		

	/**
	 * associa observador
	 */
	public void SetObserver(Observer o)
	{
		if(obs == null)
			obs = o;
		else
			obs.Link(o);
	}
	
	/**
	 * Coloca objeto em seu estado inicial para simula��o
	 */
	public void Clear()
	{
		if(obs == null)
			return;
		obs.Clear();
	}

	/**
	 * adiciona entidade e no "final" da fila; 
	 * resultado imprevis�vel se ocorrer estouro da capacidade.
	 */
	public abstract void Enqueue(Entity e);					
	/**
	 * remove entidade da "frente" da fila; 
	 * resultado imprevis�vel se n�o houver entidade a ser retirada.
	 */
	public abstract Entity Dequeue();						
	/**
	 * devolve entidade e � "frente" da fila;
	 * resultado imprevis�vel se ocorrer estouro da capacidade.
	 */
	public abstract void PutBack(Entity e);

	public short getCount() {
		return count;
	}

	public void setCount(short count) {
		this.count = count;
	}
}