// Arquivo Entity.java
// Implementa��o das Classes do Grupo de Modelagem da Biblioteca de Simula��o JAVA
// 26.Mar.1999	Wladimir

package simula;

import java.util.*;

public class Entity { // pagliares, public para ser acessado via QueueEntry

	/**
	 * A m�nima prioridade (255)
	 */
	public static final short MinPriority = 255;	
	/**
	 * A m�xima prioridade (ZERO)
	 */
	public static final short MaxPriority = 0;
	private static long counter = 1;

	private long id;							// id da entidade
	private float creationtime;
	private float timestamp;
	private short priority = 128;	// valor padr�o
	private float queueEnterTime;			// instante em que entrou na fila atual
	private float totalTimeInQueues = 0;	// tempo total que passou em filas
	private float timeInLastQueue = 0;			// tempo que passou na �ltima fila

	private HashMap attributes;
	
//	public double activityBeginTime = 0.0;
//	public double activityEndTime = 0.0;

	/**
	 * constr�i uma entidade e atribui o instante da sua cria��o.
	 */
	public Entity(float creationtime){
		timestamp = this.creationtime = creationtime;
		id = counter++;	
	}
	
	/**
	 * obt�m instante de cria��o.
	 */
	public float getCreationTime(){
		return creationtime;
	}
	
	/**
	 * obt�m valor de um atributo personalisado.
	 */
	public float getAttribute(String name){
		if(attributes == null)
			return Float.NaN;
		Float data = (Float)attributes.get(name);
		if(data == null)							// atributo n�o existe
			return Float.NaN;
		
		return data.floatValue();
	}

	/**
	 * atribui valor a um atributo personalisado; cria, se necess�rio. 
	 */
	public void setAttribute(String name, float value){
		if(attributes == null)
			attributes = new HashMap(5);			// cria a tabela na primeira atribui��o
		attributes.put(name, new Float(value));		// armazena
	}

	/**
	 * determina prioridade de uma entidade em rela��o �s outras.
	 */
	public void setPriority(short p){
		if(p > MinPriority)
			priority = MinPriority;
		else if(p < MaxPriority)
			priority = MaxPriority;
		else
			priority = p;
	}

	/**
	 * obt�m prioridade da entidade.
	 */
	public short getPriority(){
		return priority;
	} 

	/**
	 * faz uma marca��o de tempo na entidade.
	 */
	public void setTimestamp(float time){
		timestamp = time;
	}

	/**
	 * obt�m valor da �ltima marca��o.
	 */
	public float getTimestamp(){
		return timestamp;
	}

	/**
	 * obt�m o tempo em que a entidade ficou em filas
	 */
	public float getTotalQueueTime(){
		return totalTimeInQueues;
	}

	/**
	 * obt�m o tempo em que a entidade ficou em filas
	 */
	public long getId(){
		return id;
	}

	/**
	 * notifica o instante em que entrou em uma fila
	 */
	public void setQueueEnterTime(float time){
		queueEnterTime = time;
	}

	/**
	 * notifica o instante em que saiu de uma fila; 
	 */
	public void leftQueue(float time){
		timeInLastQueue = time - queueEnterTime;
		totalTimeInQueues += timeInLastQueue;
		queueEnterTime = 0;
	}

	/**
	 * retorna o tempo em que passou na fila
	 */
	public float getTimeInLastQueue(){
		return timeInLastQueue;
	}

//	public double getActivityBeginTime() {
//		return activityBeginTime;
//	}
//
//	public void setActivityBeginTime(double activityBeginTime) {
//		this.activityBeginTime = activityBeginTime;
//	}
//
//	public double getActivityEndTime() {
//		return activityEndTime;
//	}
//
//	public void setActivityEndTime(double activityEndTime) {
//		this.activityEndTime = activityEndTime;
//	}
		
}