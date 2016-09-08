// Arquivo  QueueEntry.java 
// Implementa��o das Classes do Sistema de Gerenciamento da Simula��o
// 21.Mai.1999 Wladimir

package simula.manager;

import java.io.*;
import simula.Entity; // pagliares

 
 
/**
 * Entrada para as filas de entidades do modelo.
 */
public class QueueEntry extends Entry
{
	private static int lastid;	// identificador �NICO para as filas
	static boolean hasSerialized = true; // "lastid j� foi serializado"
	public int intialQuantity;
	
	/**
	 * FIFO, STACK, PRIORITY:
	 * constantes que identificam
	 * a pol�tica da fila
	 */
	public final static short FIFO = 0;			// constantes que identificam
	public final static short STACK = 1;		// a pol�tica da fila
	public final static short PRIORITY = 2;
		
	/**
	 * qtde m�x de entidades na fila
	 */
	private short max;
	/**
	 * pol�tica da fila
	 */ 
	private short policy;							
		
  public transient simula.DeadState deadState;	// objeto de simula��o
                              			// n�o � serializado
  										// PAGLIARES: SimObj antes de refatorar
	
	public String toString()	{
		StringBuffer stb = new StringBuffer();
		stb.append("<QueueEntry max=\""+max+"\" policy=\""+policyString()+"\">\r\n");
		stb.append("<Q_super>\r\n");
		stb.append(super.toString());
		stb.append("</Q_super>\r\n");
		stb.append("</QueueEntry>\r\n");
		return stb.toString();
	}
	
	String policyString(){
		if(policy == FIFO){
			return "FIFO";
		}
		else if(policy == STACK){
			return "STACK";
		}
		else if(policy == PRIORITY){
			return "PRIORITY";
		}
		return "POLICY??";
	}
	
  /**
   * constr�i um objeto com id gerado internamente;
   * preenche com argumentos padr�o os demais campos.
   */
	public QueueEntry(){
		super("q_" + String.valueOf(lastid));
		lastid++;
		max = (short)1000;
		policy = FIFO;
	}
	
	public void copyAttributes(Entry v_e){
		super.copyAttributes(v_e);
		QueueEntry qEntry = (QueueEntry)v_e;
		max = qEntry.max;
		policy = qEntry.policy;
		deadState = qEntry.deadState;
	}
	
	public final short getMax(){	return max;	}
	public final short getPolicy(){	return policy;	}
	public final void setMax(short v_sMax){	max = v_sMax;	}
	public final void setPolicy(short v_sPolicy){	policy = v_sPolicy;	}
	
	
	boolean generate(SimulationManager m)	{   
		switch(policy)	{
			case FIFO: 
				deadState = new simula.FifoQ(m.scheduler, max); 
				createAndEnqueueEntities(); // Pagliares
				break;
			case STACK:
				deadState = new simula.StackQ(m.scheduler, max); 
				createAndEnqueueEntities(); // Pagliares
				break;
			case PRIORITY: 
				deadState = new simula.PriorityQ(m.scheduler, max); 
				createAndEnqueueEntities(); // Pagliares
				break;
			default: 
				return false;
		}

		deadState.name = name;
				
		if(obsid != null)
			return m.GetObserver(obsid).generate(m);
			
		return true;
	}
	
	private void writeObject(ObjectOutputStream stream) throws IOException{
		stream.defaultWriteObject();
		
		if(hasSerialized)
			return;
			
		stream.writeInt(lastid);
		hasSerialized = true;
	}
	
 	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException{
		stream.defaultReadObject();
		
		if(hasSerialized)
			return;
			
		lastid = stream.readInt();
		hasSerialized = true;
	}
 	
 	// The method below, before the statement break as developed by Rodrigo Pagliares to cope with the problem of
 	// starting a simulation without using generate activities, placing some 
 	// initial entities on the first queue of the simulation. Make the same to the other options of the 
 	// switch, refactoring 
 	private boolean createAndEnqueueEntities() {
 		for (int i=0; i < intialQuantity; i++) {
 			Entity e = new Entity(0); // 0 is the time of creation. I am using zero to indicate before simulation start
 			deadState.Enqueue(e);  
 		}
 		return true;
 	}
}