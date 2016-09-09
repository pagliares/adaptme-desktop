// Arquivo ProcessorObserver.java
// Implementa��o das Classes do Grupo de Resultados da Biblioteca de Simula��o JAVA
// 16.Abr.1999	Wladimir

package simula;

public class ProcessorObserver extends ActiveObserver
{
	protected Expression exp;
	protected boolean entering;

	/**
	 * construtor que impede realiza��o de estat�sticas
	 */
	public ProcessorObserver(Scheduler s, ActiveState a, String attribute, Expression e, boolean entering)
	{ super(s, a, (Statistics) null, attribute); exp = e; this.entering = entering;}

	/**
	 * sem sentido
	 */
	public void StateChange(short to)
	{
		if(next != null)
			next.StateChange(to);	
	}

	/**
	 * atribui resultado de exp � atribute att de e
	 */
	private void Execute(Entity e)
	{ e.setAttribute(att, exp.Evaluate(e));}
	
	/**
	 * processa entidade e manda para pr�ximo observer da lista
	 */
	public void Incoming(Entity e)
	{
		if(entering)
			Execute(e);
		if(next != null)
			next.Incoming(e);
	}
	
	/**
	 * processa entidade e manda para pr�ximo observer da lista
	 */
	public void Outgoing(Entity e)
	{
		if(!entering)
			Execute(e);
		if(next != null)
			next.Outgoing(e);
	}

}