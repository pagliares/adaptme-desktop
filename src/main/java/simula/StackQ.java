// Arquivo StackQ.java
// Implementa��o das Classes do Grupo de Modelagem da Biblioteca de Simula��o JAVA
// 26.Mar.1999	Wladimir

package simula;

import java.util.*;

public class StackQ extends DeadState
{
	private Stack q;					// implementa fila como vetor

	/**
	 * constr�i uma fila vazia com capacidade para max entidades.
	 */
	public StackQ(Scheduler s, short max)
	{
		super(s, max);
		q = new Stack();
		q.ensureCapacity(max);	
	} 
	
	/**
	 * constr�i uma fila vazia com capacidade ilimitada. 
	 */
	public StackQ(Scheduler s)
	{
		super(s, 0);
		q = new Stack();	
		q.ensureCapacity(10);
	}
	
	/**
	 * Coloca objeto em seu estado inicial para simula��o
	 */
	public void clear()
	{
		super.clear();
		q.clear();
	}

	/**
	 * implementa a interface segundo a pol�tica FIFO; atualiza atributos de tamanho.
	 */
	public void enqueue(Entity e)
	{
		if(obs != null)
			obs.Incoming(e);
		q.push(e);
		count++;
		e.setQueueEnterTime(s.GetClock());
	}
	
	/**
	 * implementa a interface segundo a pol�tica FIFO; atualiza atributos de tamanho.
	 */
	public void putBack(Entity e)
	{	
		if(obs != null)
			obs.Incoming(e);
		q.push(e);
		count++;
		e.setQueueEnterTime(s.GetClock());
	}
	/**
	 * implementa a interface segundo a pol�tica FIFO; atualiza atributos de tamanho.
	 */
	public Entity dequeue()
	{
		try
		{
			Entity e = (Entity)q.peek();
			e.leftQueue(s.GetClock());
			if(obs != null)
				obs.Outgoing(e);
			q.pop();
			count--;
			return e;
		}
		catch(EmptyStackException x){return null;}	
	}
}