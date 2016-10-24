// Arquivo  SimulationManager.java 
// Implementa��o das Classes do Sistema de Gerenciamento da Simula��o
// 11.Jun.1999 Wladimir

package simula.manager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import java.io.*;
import simula.*;

/**
 * Classe principal do sistema de gerenciamento. Concentra todos os
 * pedidos de cria��o e remo��o de Entry's no modelo. 
 * Verifica consist�ncia e prov� controle de concorr�ncia.
 * Gerencia diferentes reposit�rios de entry's de diversos tipos
 * e gera programa de simula��o. 
 * @author	Wladimir
 */
public class SimulationManager implements Serializable{
	
	private HashMap queues, resources, activestates,observers, histograms;

	private Vector attributeTables;
	private AttributeTable attributeTable; // PAGLIARES: antes do refactoring, chamave globals
	
	private transient boolean isRunning = false;
	transient Scheduler scheduler;
	transient Sample sample;
	private transient float endtime = 0;	// instante programado 
											// de t�rmino da simula��o
	private transient float resettime = 0;  // instante em que as 
	
	 

	/**
	 * Creates a new, ready to use SimulationManager
	 */
	public SimulationManager() {
		queues = new HashMap();
		resources  = new HashMap();
		activestates = new HashMap();
		observers = new HashMap();
		histograms = new HashMap();
		attributeTables = new Vector(5, 2);
		attributeTable = new AttributeTable();
		attributeTable.name = "globals";	// nome padr�o 
	}
	
	public String toString(){
		StringBuffer stb = new StringBuffer();
		stb.append("<SimulationManager>\r\n");
		stb.append("<SM_queues>\r\n");
		appendIterator(queues.values().iterator(), stb);
		stb.append("</SM_queues>\r\n");
		stb.append("<SM_resources>\r\n");
		appendIterator(resources.values().iterator(), stb);
		stb.append("</SM_resources>\r\n");
		stb.append("<SM_activestates>\r\n");
		appendIterator(activestates.values().iterator(), stb);
		stb.append("</SM_activestates>\r\n");
		stb.append("<SM_observers>\r\n");
		appendIterator(observers.values().iterator(), stb);
		stb.append("</SM_observers>\r\n");
		stb.append("<SM_histograms>\r\n");
		appendIterator(histograms.values().iterator(), stb);
		stb.append("</SM_histograms>\r\n");
		stb.append("<SM_attributeTables>\r\n");  // attributeTables era chamada de types antes do refactoring
		appendVector(attributeTables, stb);
		stb.append("</SM_attributeTables>\r\n");
		stb.append("<SM_attributeTable>\r\n");
		stb.append(attributeTable);
		stb.append("</SM_attributeTable>\r\n");
		stb.append("</SimulationManager>\r\n");
		return stb.toString();
	}
	
	public static void appendIterator(Iterator v_it, StringBuffer v_stb){
		while(v_it.hasNext())
		{
			v_stb.append(v_it.next()+"\r\n");
		}
	}
	
	public static void appendVector(Vector v_vec, StringBuffer v_stb){
		int iN = v_vec.size();
		for(int i=0; i<iN; i++)
		{
			v_stb.append(v_vec.elementAt(i)+"\r\n");
		}
	}
	
	/**
	 * Adds a Queue to the system
	 */
	public boolean AddQueue(QueueEntry queueEntry)	{
		synchronized(queues)
		{
			if(queues.containsKey(queueEntry.id))
				return false;
			
			queues.put(queueEntry.id, queueEntry);
		}
		
		return true;
	}
	
	/**
	 * Adds a Resource to the system
	 */
	public boolean AddResource(ResourceEntry resourceEntry){
		synchronized(resources)
		{
			if(resources.containsKey(resourceEntry.id))
				return false;
			
			resources.put(resourceEntry.id, resourceEntry);
		}	
		return true;
	}
	
	/**
	 * Adds an ActiveState to the system
	 */
	public boolean AddActiveState(ActiveEntry activeEntry)	{
		synchronized(activestates)
		{
			if(activestates.containsKey(activeEntry.id))
				return false;
			
			activestates.put(activeEntry.id, activeEntry);
		}
		return true;
	}
	
	/**
	 * Adds an Observer to the System
	 */
	public boolean AddObserver(ObserverEntry observerEntry)	{
		synchronized(observers)
		{
			if(observers.containsKey(observerEntry.id))
				return false;
			
			observers.put(observerEntry.id, observerEntry);
		}
		return true;
	}

	
	/**
	 * Adds a Histogram to the system
	 */
	public boolean AddHistogram(HistogramEntry histrogramEntry){
		synchronized(histograms)
		{
			if(histograms.containsKey(histrogramEntry.id))
				return false;
			
			histograms.put(histrogramEntry.id, histrogramEntry);
		}	
		return true;
	}
	
	/**
	 * Adds an AttributeTable to the system
	 */
	public boolean addAttributeTable(AttributeTable attributeTable){  // PAGLIARES: metodo chamado AddType antes do refactoring
		synchronized(attributeTables)
		{
			if(attributeTables.contains(attributeTable))
				return false;
			
			attributeTables.add(attributeTable);
		}	
		return true;
	}
		
	/**
	 * Atualiza vari�veis globais.
	 */
	public boolean updateAttributeTable(AttributeTable globalVars){  // PAGLIARES: metodo chamado UpdateGlobals antes do refactoring
		if(globalVars == null)
			return false;
			
		if(!attributeTable.id.equals(globalVars.id))
			return false;
			
		attributeTable = globalVars;
		
		return true;
	}
	
	/**
	 * Removes a Queue from the system,
	 * updating the related entities properly
	 */
	public void RemoveQueue(String id){	
		RemoveQueue(id, true, true);	
	}
	
	public void RemoveQueue(String id, boolean v_bRemoveObservers, boolean v_bRemovePointingActiveStates){
		QueueEntry e;
		
		synchronized(queues){
			e = (QueueEntry)queues.remove(id);
		}
		
		if(e != null){
			// remove todos os observadores
			if(e.obsid != null && v_bRemoveObservers){
				synchronized(observers){
					String oid = e.obsid; //existe uma lista encadeada de observadores dentro da tabela de observadores
					ObserverEntry oe = (ObserverEntry)observers.remove(oid);

					while(oe != null)	{
						oid = oe.obsid;
						oe = (ObserverEntry)observers.remove(oid);
					}
				}
			}
			if(v_bRemovePointingActiveStates){
				synchronized(activestates){
					// remove todas as refer�ncias a esse queue
					Iterator it; // para percorrer todos os active states
						
					it = activestates.values().iterator();
					ActiveEntry ae;
						
					while(it.hasNext())
					{
						ae = (ActiveEntry)it.next();
						if(ae.isInternal)
						{
							InternalActiveEntry ia = (InternalActiveEntry)ae;
							if(ia.isRouter)
							{
								// remove se for fonte ou destino
								ia.removeFromQueue(e.id);
								int i = ia.toQueueIndexOf(e.id);
								if(i != -1) // se � destino, remove tb a condi��o
								{
									ia.removeToQueue(i);
									ia.removeCond(i);
								}
							}
							else
							{
							int i;
								if(ia.fromQueueContains(e.id))
								{
									i = ia.fromQueueIndexOf(e.id);
									ia.removeFromQueue(i);
									ia.removeToQueue(i);
									ia.removeCond(i);
								}
							if(ia.toQueueContains(e.id))
								{
									i = ia.toQueueIndexOf(e.id);
									ia.removeFromQueue(i);
									ia.removeToQueue(i);
									ia.removeCond(i);
								}
							}
						}
						else
						{
	//		System.out.println("removequeue8.1 "+((ExternalActiveEntry)ae).qid+" "+e.id);
							//boolean bTest = ((ExternalActiveEntry)ae).qid.equals(e.id);
							if(e.id.equals(((ExternalActiveEntry)ae).getQID()))
							{
								((ExternalActiveEntry)ae).setQID(null); //GOTCHA! aqui que tu tah anulando essa porra!!!!
							}
						}
					}
				}
			}
		}
	}
		
	/**
	 * Removes a Resource from the system
	 * updating the related entities properly
	 */
	public void RemoveResource(String id){
		ResourceEntry e;
		
		synchronized(resources)
		{
			e = (ResourceEntry)resources.remove(id);
		}
		if(e != null)
		{
			// remove o observador
			if(e.obsid != null)
			{
				synchronized(observers)
				{
					observers.remove(e.obsid);
				}
			}
			
			synchronized(activestates)
			{
				// remove todas as refer�ncias a esse queue
				Iterator it; // para percorrer todos os active states
				
				it = activestates.values().iterator();
				ActiveEntry ae;
				
				while(it.hasNext())
				{
					ae = (ActiveEntry)it.next();
					if(ae.isInternal)
					{
						InternalActiveEntry ia = (InternalActiveEntry)ae;
						int i;
						
						i = ia.fromResourceIndexOf(e.id);
						if(i != -1)
						{
							ia.removeFromResource(i);
							ia.removeToResource(i);
							ia.removeResourceQty(i);
						}

						i = ia.toResourceIndexOf(e.id);
						if(i != -1)
						{
							ia.removeFromResource(i);
							ia.removeToResource(i);
							ia.removeResourceQty(i);
						}
					}
				}
			}	
		}
	}
		
	/**
	 * Removes an ActiveState from the system
	 * updating the related entities properly
	 */
	public void RemoveActiveState(String id){
		ActiveEntry ae;
		
		synchronized(activestates)
		{
			ae = (ActiveEntry)activestates.remove(id);
		}
		
		if(ae != null)
		{
			// remove todos os observadores
			if(ae.obsid != null)
			{
				synchronized(observers)
				{
					String oid = ae.obsid;				
					ObserverEntry oe = (ObserverEntry)observers.remove(oid);

					while(oe != null)
					{
						oid = oe.obsid;
						oe = (ObserverEntry)observers.remove(oid);
					}
				}
			}
			if(ae instanceof InterruptActiveEntry)
			{
				synchronized(activestates)
				{
					Iterator it = activestates.values().iterator();
					while(it.hasNext())
					{
						ae = (ActiveEntry)it.next();
						if(ae instanceof InterruptActiveEntry)
							((InterruptActiveEntry)ae).removeInterrupt(id);
					}
				}
			}
		}
	}
		
	/**
	 * Removes an Observer from the system
	 * updating the related entities properly
	 */
	public void RemoveObserver(String id){
		ObserverEntry oe;
		
		synchronized(observers)
		{
			oe = (ObserverEntry)observers.remove(id);
		}
		
		if(oe != null)
		{
			Entry e;
			switch(oe.getType())
			{
				case ObserverEntry.RESOURCE: 
					e = (Entry)resources.get(oe.getObserved());
					break;
				case ObserverEntry.QUEUE: 
					e = (Entry)queues.get(oe.getObserved());
					break;
				case ObserverEntry.ACTIVE: 
				case ObserverEntry.PROCESSOR:
				case ObserverEntry.DELAY:
					e = (Entry)activestates.get(oe.getObserved());
					break;
				default: return;
			}
			while(e.obsid != null && !e.obsid.equals(id))	// encontra o anterior na lista 
			{
				e = (Entry)observers.get(e.obsid);
			}
			e.obsid = oe.obsid;		// liga com o pr�ximo
			if(oe.getHistid() != null)
				RemoveHistogram(oe.getHistid());
		}
	}
			
	/**
	 * Removes a Histogram from the system
	 * updating the related entities properly
	 */
	public void RemoveHistogram(String id){
		HistogramEntry he;
		
		synchronized(histograms)
		{
			he = (HistogramEntry)histograms.remove(id);
		}
		
		if(he == null)
			return;
			
		if(he.obsid != null)
		{
			ObserverEntry oe = GetObserver(he.obsid);
			if(oe != null)
				oe.setHistid(null);
		}
	}
	
	/**
	 * Removes an AttributeTable from the system
	 */
	public void removeAttributeTable(String id){ // PAGLIARES: RemoveType antes de refatorar
		synchronized(attributeTables)
		{
			Iterator it = attributeTables.iterator();
			while(it.hasNext())
			{
				if(((AttributeTable)it.next()).id == id)
				{
					it.remove();
					break;
				}	
			}
		}	
	}
	// Remove entrada do reposit�rio respectivo e suas associadas
	// de forma a manter a consist�ncia
	
	/**
	 * Returns a Queue given its ID
	 */
	public QueueEntry GetQueue(String id)
	{return (QueueEntry)queues.get(id);}
		
	/**
	 * Returns a Resource given its ID
	 */
	public ResourceEntry GetResource(String id)
	{return (ResourceEntry)resources.get(id);}
		
	/**
	 * Returns an ActiveState given its ID
	 */
	public ActiveEntry GetActiveState(String id)
	{return (ActiveEntry)activestates.get(id);}
	
	/**
	 * Returns an iterator to the ActiveStates HashMap
	 */
	public Iterator GetActiveStatesIterator(){	
		return activestates.values().iterator();	
	}
		
	/**
	 * Returns an Observer given its ID
	 */
	public ObserverEntry GetObserver(String id){
		return (ObserverEntry)observers.get(id);
	}
		
	/**
	 * Returns a Histogram given its ID
	 */
	public HistogramEntry GetHistogram(String id){
		return (HistogramEntry)histograms.get(id);
	}
	
	/**
	 * Returns an AttributeTable given its TypeID
	 * TypeID is the user-visible, and editable field.
	 */
	public AttributeTable GetType(String id){ // Pagliares: chamado GetType antes do refactoring
	// Este � o �nico caso em que o Id �nico n�o � usado para indexa��o,
	// e sim o name, que � o campo visto e alterado pelo usu�rio.
	
		AttributeTable e = null;
		synchronized(attributeTables)
		{
			Iterator it = attributeTables.iterator();
			while(it.hasNext())
			{
				if((e = (AttributeTable)it.next()).name.equals(id))
					return e;
			}
		}	
		
		return null;
	}
	
	/**
	 * Obt�m entrada do reposit�rio respectivo atrav�s de seu ID �nico
	 */
	public AttributeTable getAttributeTable(){ // Pagliares: GetGlobals antes do refactoring
		return attributeTable;
	}
		
	/**
	 * Gera modelo de simula��o e prepara para execu��o
	 */
	public synchronized boolean GenerateModel(){  
		
		//System.out.println("simulationmanager.GenerateModel");
		Iterator iterator;
		
		// 1.o cria o Scheduler
		
		scheduler = new Scheduler(this); // Pagliares: estou passando simulation manager para permitir 
								         // o calculo de estatisticas por iteracao/release
		
		// logo depois a stream de n�meros aleat�rios
		
		sample = new Sample();
		
		synchronized(queues){
			synchronized(activestates){
				synchronized(resources){
					synchronized(observers){
						synchronized(histograms)
		// ningu�m pode estar sendo alterado
		{
			QueueEntry queueEntry;
			ActiveEntry activeEntry;
			ResourceEntry resourceEntry;
			ObserverEntry observerEntry;
			HistogramEntry histogramEntry;
			
			// agora os estados mortos

			iterator = queues.values().iterator();
			while(iterator.hasNext()){
				queueEntry = (QueueEntry)iterator.next();
				if(!queueEntry.generate(this)){
					System.err.println("Imposs�vel criar fila " + queueEntry.id);
					return false;
				}
			}
			
			iterator = resources.values().iterator();
			while(iterator.hasNext()){
				resourceEntry = (ResourceEntry)iterator.next();
				if(!resourceEntry.generate(this))
				{
					System.err.println("Imposs�vel criar recurso " + resourceEntry.id);
					return false;
				}
			}

			// da� os ativos
			
			// 1.o limpa os objs de simula��o (devido �s InterruptActivity's)
			iterator = activestates.values().iterator();
			while(iterator.hasNext()){
				activeEntry = (ActiveEntry)iterator.next();
				activeEntry.activeState = null;
			}

			iterator = activestates.values().iterator();
			while(iterator.hasNext()){
				activeEntry = (ActiveEntry)iterator.next();
				if(!activeEntry.generate(this)){
					System.err.println("Imposs�vel criar estado ativo " + activeEntry.id);
					return false;
				}
			}
		}
					}
				}
			}
		}
		
		// cria, por �ltimo, as vari�veis globais
		
		Expression.globals = new Variables();
		
		QueueEntry queueEntry = null;
		ResourceEntry resourceEntry = null;
		HashMap deadsHashMap = new HashMap(queues.size() + resources.size());
		
		iterator = queues.values().iterator();
		while(iterator.hasNext()){
			queueEntry = (QueueEntry)iterator.next();
			deadsHashMap.put(queueEntry.name, queueEntry.deadState);		
		}
		
		iterator = resources.values().iterator();
		while(iterator.hasNext()){
			resourceEntry = (ResourceEntry)iterator.next();
			deadsHashMap.put(resourceEntry.name, resourceEntry.SimObj);		
		}
		
		Expression.globals.AssignQueuesTable(deadsHashMap);
		
		Var var = null;
		iterator = attributeTable.getVarsIterator();
		while(iterator.hasNext()){
			var = (Var)iterator.next();
			Expression.globals.CreateVar(var.id, var.value);
		}

		return true;
	}		
	
	/**
	 * Executa simula��o at� instante endTime
	 */
	public synchronized boolean ExecuteSimulation(float endTime)
	{
		boolean ok = false; // TODO melhor nome: isRunning, isSimulationStarted
		
		if(endTime >= 0 && scheduler != null)	// o modelo j� deve ter sido gerado
		{
			Log.Close();
			Log.OpenFile();
			ok = scheduler.Run(endTime);
			if(ok)
			{
				endtime = endTime;
				isRunning = true;
			}
		}
		
		return ok;
	}
		
	/**
	 * Coloca todos os objetos da simula��o em seus estados iniciais
	 */
	public synchronized boolean ResetSimulation()
	{
		if(scheduler == null || isRunning)
			return false;	// modelo precisa ser gerado antes
		
		scheduler.Clear();
		resettime = 0;
		
		Iterator it;
		
		it = queues.values().iterator();
		while(it.hasNext())
		{
			((QueueEntry)it.next()).deadState.clear();
		}
		it = activestates.values().iterator();
		while(it.hasNext())
		{
			((ActiveEntry)it.next()).activeState.Clear();
		}
		it = resources.values().iterator();
		while(it.hasNext())
		{
			((ResourceEntry)it.next()).SimObj.clear();
		}
		
		return true;
	}
		
	/**
	 * Limpa todos os objetos estat�sticos, mesmo durante a simula��o
	 * a simula��o deve estar pausada (Stop() suave do scheduler)
	 */
	public synchronized boolean ResetStatistics()
	{
		if(scheduler == null || isRunning)
			return false;	// modelo precisa ser gerado antes e  
										// n�o pode estar executando
		Iterator it;
		resettime = scheduler.GetClock();
		
		it = observers.values().iterator();
		while(it.hasNext())
		{
			((ObserverEntry)it.next()).SimObj.Clear();
		}
		
		return true;
	}
		
	/**
	 * Interrompe simula��o
	 */
	public synchronized void StopSimulation()
	{
		if(isRunning)
			scheduler.Stop();		// p�ra
				
		isRunning = false;
	}
	
	/**
	 * Continua a simula��o, se poss�vel
	 */
	public synchronized boolean ResumeSimulation()
	{
		if(scheduler == null)
			return false;
			
		return scheduler.Resume();
	}
	
	/**
	 * Verifica se a simula��o ainda est� executando
	 */
	public boolean Finished()
	{
		if(scheduler == null) 
			return true;
			
		return scheduler.Finished();
	}
	
	
		
	private void writeObject(ObjectOutputStream stream)
     throws IOException
	{
		ActiveEntry.hasSerialized = false;
		ResourceEntry.hasSerialized = false;
		QueueEntry.hasSerialized = false;
		ObserverEntry.hasSerialized = false;
		HistogramEntry.hasSerialized = false;
		AttributeTable.hasSerialized = false;
		
		stream.defaultWriteObject();
		
		ActiveEntry.hasSerialized = true;
		ResourceEntry.hasSerialized = true;
		QueueEntry.hasSerialized = true;
		ObserverEntry.hasSerialized = true;
		HistogramEntry.hasSerialized = true;
		AttributeTable.hasSerialized = true;
	}
 	private void readObject(ObjectInputStream stream)
     throws IOException, ClassNotFoundException
	{
		ActiveEntry.hasSerialized = false;
		ResourceEntry.hasSerialized = false;
		QueueEntry.hasSerialized = false;
		ObserverEntry.hasSerialized = false;
		HistogramEntry.hasSerialized = false;
		AttributeTable.hasSerialized = false;
		
		stream.defaultReadObject();
		
		ActiveEntry.hasSerialized = true;
		ResourceEntry.hasSerialized = true;
		QueueEntry.hasSerialized = true;
		ObserverEntry.hasSerialized = true;
		HistogramEntry.hasSerialized = true;
		AttributeTable.hasSerialized = true;
	}
 	
 	public Scheduler getScheduler() {
 		return scheduler;
 	}

	public float getResettime() {
		return resettime;
	}
	
	/**
	 * Depois da simula��o terminada, escreve os resultados
	 * das estat�sticas nos arquivos de sa�da
	 */
	public synchronized boolean OutputSimulationResults(String filename)
	{
		System.out.println("OutputSimulationResults "+filename);
		PrintStream os;
		FileOutputStream ofile;
		
		try
		{
			ofile = new FileOutputStream("output/"+filename); // recording to output instead of root folder. Pagliares
			os = new PrintStream(ofile);
		}
		catch(FileNotFoundException x)
		{
			return false;
		}
		
		os.println("\r\n                    Simulation Report");
		os.print("\r\nSimulation ended at time ");
		os.println(scheduler.GetClock());
		os.print("Statistics collected from instant " + resettime);
		os.println(" during " + (scheduler.GetClock() - resettime) + " time units.");
		
		os.println("\r\n          Observers' report");
		
		Iterator it;
		
		it = observers.values().iterator();
		while(it.hasNext())
		{
			((ObserverEntry)it.next()).DoReport(os, scheduler.GetClock() - resettime);
		}
		
		os.println("\r\n          Histograms' report");

		it = histograms.values().iterator();
		while(it.hasNext())
		{
			((HistogramEntry)it.next()).DoReport(os);
		}
		
		os.println("\r\nSimulation Report End");
		
		os.close();
		
		return true;
	}
	
	public synchronized boolean OutputSimulationResultsConsole(){
		System.out.println("OutputSimulationResults ");
		PrintStream os;
		FileOutputStream ofile;
	
		System.out.println("\r\n                    Simulation Report");
		System.out.print("\r\nSimulation ended at time ");
		System.out.println(scheduler.GetClock());
		System.out.print("Statistics collected from instant " + resettime);
		System.out.println(" during " + (scheduler.GetClock() - resettime) + " time units.");
		
		System.out.println("\r\n          Observers' report");
		
		Iterator it;
		
		it = observers.values().iterator();
		while(it.hasNext()){
			((ObserverEntry)it.next()).DoReportConsole(scheduler.GetClock() - resettime);
		}
		
//		System.out.println("\r\n          Histograms' report");

		it = histograms.values().iterator();
		while(it.hasNext()){
//			((HistogramEntry)it.next()).DoReportConsole();
		}
		
		System.out.println("\r\nSimulation Report End");
		return true;
	}
	
	
	public synchronized String getSimulationResults()
	{
		String result = null;
		result = "OutputSimulationResults \n";
		result+= "\r\n                    Simulation Report";
		result+= "\r\nSimulation ended at time \n";
		result+= scheduler.GetClock();
		result+= "Statistics collected from instant " + resettime;
		result+= " during " + (scheduler.GetClock() - resettime) + " time units.";
		result+= "\r\n\n          Observers' report\n";
		
		Iterator it;
		
		it = observers.values().iterator();
		while(it.hasNext())
		{
//			((ObserverEntry)it.next()).DoReportConsole(s.GetClock() - resettime);
			result += ((ObserverEntry)it.next()).getReportConsole(scheduler.GetClock() - resettime).toString();
		}
		
	
		result+= "\r\nSimulation Report End";
		 
		return result;
	}
	
	
	public void printObserversReport() { 
		System.out.println("\r\n          Observers' report");
		
		Iterator it;
		
		it = observers.values().iterator();
		while(it.hasNext())
		{
			((ObserverEntry)it.next()).DoReportConsole(scheduler.GetClock() - resettime);
		}
		
	}
	
	public HashMap getObservers() {
		return observers;
	}
	
	public void printObserversReport(ObserverEntry observerEntry) { 
		 
		observerEntry.DoReportConsole(scheduler.GetClock() - resettime);
	 
	
}
	
	
	
	public void printWeightedAverage(ObserverEntry observerEntry) { 
		 
		System.out.println(observerEntry.getAvearageWeighted(scheduler.GetClock() - resettime));
	 
	 
	
}
	
	public void printStandardDeviationWeighted(ObserverEntry observerEntry) { 
		 
		System.out.println(observerEntry.getStandardDeviationWeighted(scheduler.GetClock() - resettime));
	 
	 
	
}
	
	public void printVarianceWeighted(ObserverEntry observerEntry) { 
		 
		System.out.println(observerEntry.getVarianceWeighted(scheduler.GetClock() - resettime));
	}
	
	public void printMin(ObserverEntry observerEntry) { 
		 
		System.out.println(observerEntry.getMin(scheduler.GetClock() - resettime));
	}
	
	public void printMax(ObserverEntry observerEntry) { 
		 
		System.out.println(observerEntry.getMax(scheduler.GetClock() - resettime));
	}
	
	public void printNumberOfObservations(ObserverEntry observerEntry) { 
		 
		System.out.println(observerEntry.getNumberOfObservations(scheduler.GetClock() - resettime));
	}

	public HashMap getQueues() {
		return queues;
	}
	
	// pagliares
	
	/**
	 * Executa simula��o at� instante endTime
	 */
	public synchronized boolean ExecuteSimulation(float endTime, float iterationTime, float releaseTime)
	{
		boolean ok = false;
		
		if(endTime >= 0 && scheduler != null)	// o modelo j� deve ter sido gerado
		{
			Log.Close();
			Log.OpenFile();
			ok = scheduler.Run(endTime, iterationTime, releaseTime);
			if(ok)
			{
				endtime = endTime;
				isRunning = true;
			}
		}
		
		return ok;
	}
	
	// pagliares
	public HashMap getSimulationResultsByIteration() {
		return scheduler.getSimulationResultsByIteration();
		
	}
	
	
}	