// Arquivo DelayObserver.java
// Implementa��o das Classes do Grupo de Resultados da Biblioteca de Simula��o JAVA
// 11.Jun.1999	Wladimir

package simula;

public class DelayObserver extends ActiveObserver
{
	private boolean stamp;
	private boolean entering;

	/**
	 * construtor que determina se esse � um observador que mede o delay 
	 * ou se � um que marca a entidade com o tempo (stamp = true) e se
	 * a a��o deve ser tomada qdo a entidade entra (entering = true) 
	 * ou sai do ActiveState (entering = false).
	 */
	public DelayObserver(Scheduler s, ActiveState a, Statistics st, boolean stamp, boolean entering)
	{ super(s, a, st, null); this.stamp = stamp; this.entering = entering;}
	
	public DelayObserver(Scheduler s, ActiveState a, Histogram h, boolean stamp, boolean entering)
	{ super(s, a, h, null); this.stamp = stamp; this.entering = entering;}

	/**
	 * sem sentido
	 */
	public void StateChange(short to)
	{
		if(next != null)
			next.StateChange(to);	
	}

	/**
	 * atribui timestamp ou mede delay, de acordo com stamp
	 */
	private void Execute(Entity e)
	{ 
		if(stamp)
			e.setTimestamp(s.GetClock());
		else
		{
			if(hist != null)
				hist.Add(s.GetClock() - e.getTimestamp(), 1);
			else
				stat.Add(s.GetClock() - e.getTimestamp());
		}
	}

	/**
	 * processa entidade e manda para pr�ximo observer da lista
	 */
	public void Incoming(Entity e)
	{
		if(entering)	// se deve ser processada na entrada...
			Execute(e);
		if(next != null)
			next.Incoming(e);
	}

	/**
	 * processa entidade e manda para pr�ximo observer da lista
	 */
	public void Outgoing(Entity e)
	{
		if(!entering)	// se deve ser processada na sa�da...
			Execute(e);
		if(next != null)
			next.Outgoing(e);
	}

}