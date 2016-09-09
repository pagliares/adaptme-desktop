// Arquivo FifoQ.java
// Implementa��o das Classes do Grupo de Modelagem da Biblioteca de Simula��o JAVA
// 26.Mar.1999	Wladimir

package simula;

import java.util.*;

/**
 * Implements a First-In First-Out Queue
 */
public class FifoQ extends DeadState{
	private Vector queue;					// implementa fila como vetor

	/**
	 * constr�i uma fila vazia com capacidade para max entidades. 
	 */
	public FifoQ(Scheduler s, short max){
		super(s, max);
		queue = new Vector(max);	
	} 
	
	/**
	 * constr�i uma fila vazia com capacidade ilimitada. 
	 */
	public FifoQ(Scheduler s){
		super(s, 0);
		queue = new Vector(10, 10);	
	}
	
	/**
	 * Coloca objeto em seu estado inicial para simula��o
	 */
	public void clear(){
		super.clear();
		queue.clear();
	}

	/**
	 * implementa a interface segundo a pol�tica FIFO; atualiza atributos de tamanho.
	 */
	public void enqueue(Entity e){
		if(obs != null)
			obs.Incoming(e);
		queue.addElement(e);
		count++;
		e.setQueueEnterTime(s.GetClock());
	}
	/**
	 * implementa a interface segundo a pol�tica FIFO; atualiza atributos de tamanho.
	 */
	public void putBack(Entity e){	
		if(obs != null)
			obs.Incoming(e);
		queue.insertElementAt(e, 0);
		count++;
		e.setQueueEnterTime(s.GetClock());
	}
	/**
	 * implementa a interface segundo a pol�tica FIFO; atualiza atributos de tamanho.
	 */
	public Entity dequeue(){
		try
		{
			Entity e = (Entity)queue.firstElement();
			e.leftQueue(s.GetClock());
			if(obs != null)
				obs.Outgoing(e);
			queue.removeElementAt(0);
			count--;
			return e;
		}
		catch(NoSuchElementException x){return null;}		
	}
}