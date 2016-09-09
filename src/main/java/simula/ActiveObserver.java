// Arquivo ActiveObserver.java
// Implementa��o das Classes do Grupo de Resultados da Biblioteca de Simula��o JAVA
// 16.Abr.1999	Wladimir

package simula;

public class ActiveObserver extends Observer
{
	private ActiveState a;					// estado ativo associado
	/**
	 * atributo da entidade a observar
	 */
	protected String att;				
	/**
	 * instante em que ficou ocioso
	 */
	protected float beginidletime = 0;	

	public ActiveObserver(Scheduler s, ActiveState a, Histogram h, String attribute)
	{ 
		super(s, h); 
		this.a = a; 
		att = attribute;
		a.SetObserver(this);
	}
	/**
	 * construtores; se attribute == null, observa idletime.
	 */
	public ActiveObserver(Scheduler s, ActiveState a, Statistics st, String attribute)
	{ 
		super(s, st); 
		this.a = a; 
		att = attribute;
		a.SetObserver(this);
	}

	/**
	 * observa tempo ocioso do estado ativo, mas somente se n�o observar nenhum atributo
	 */
	public void StateChange(short to)
	{
		if(att != null)
		{
			if(next != null)						// procura o 1.o obs de idletime
				next.StateChange(to);
			return;
		}
		
		float clock = s.GetClock();
		if(to == IDLE) 
			beginidletime = clock;
		else if(beginidletime < clock && beginidletime >= 0)
		{
			if(hist != null)
				hist.Add(clock - beginidletime, 1);
			else 
				stat.Add(clock - beginidletime);
			
			beginidletime = -1;		// sinaliza que est� BUSY
		}
	}
	
	/**
	 * realiza estat�sticas do atributo att.
	 */
	private void Sample(Entity e)
	{
		if(att != null)
		{
			if(hist != null)
				hist.Add(e.getAttribute(att), 1);
			else
				stat.Add(e.getAttribute(att));
		}
	}
			
	/**
	 * processa entidade e manda para pr�ximo observer da lista
	 */
	public void Incoming(Entity e)
	{
		Sample(e);
		if(next != null)
			next.Incoming(e);
	}
	
	/**
	 * processa entidade e manda para pr�ximo observer da lista
	 */
	public void Outgoing(Entity e)
	{
		Sample(e);
		if(next != null)
			next.Outgoing(e);
	}

}