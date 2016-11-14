// Arquivo Scheduler.java
// Implementa��o das Classes do Grupo Executivo da Biblioteca de Simula��o JAVA
// 19.Mar.1999	Wladimir

package simula;

import java.util.*;

import simula.manager.QueueEntry;
import simula.manager.SimulationManager;

public class Scheduler implements Runnable{
	private Calendar calendar;		// estrutura de armazenamento dos estados ativos a servir
	private float clock = 0;		// rel�gio da simula��o
	private float endclock;			// fim da simula��o
	private float timeprecision;	// diferen�a m�nima que deve haver entre dois instantes
									// para que sejam considerados diferentes 
	private Vector activestates;	// Vetor dos estados ativos da simula��o
	private boolean crescan = true;	// flag de habilita��o de re-varrudura dos eventos C
	private volatile boolean running = false;
									// controla se a simula��o deve continuar rodando
	private boolean stopped = false;// indica se a simula��o parou conforme ordenado
	private Thread simulation;		// thread em que a simula��o ir� executar
	private byte termreason;		// porque encerrou a simula��o
	
	private static Scheduler s;		// uma refer�ncia est�tica ao Scheduler
									// para permitir a��es de emerg�ncia (parada)

	private SimulationManager simulationManager;
	
	private float iterationDuration;  // pagliares
	private float releaseDuration;  // pagliares
	
	private int numberOfIterations = 1; // Pagliares. Precisa ser um. vide metodo run sobreescrito
	private int numberOfReleases = 0; // Pagliares. diferentemente de iteration, so contamos release quando entrega
	private int multiploIterations = 2; // Pagliares: usado para se contar apenas uma vez uma iteracao quando o clock for maior que a duracao da iteracao
	private int multiploRelease = 1; // Pagliares: usado para se contar apenas uma vez uma release quando o clock for maior que a duracao da iteracao
	
	private HashMap<String, HashMap>simulationResultsByIteration = new HashMap<>();
	
    private boolean hasRelease;
    private boolean hasIteration;
	
	
	
	/**
	 * retorna refer�ncia ao objeto ativo
	 */
	public static Scheduler Get(){return s;}

	public Scheduler(SimulationManager simulationManager)	{
		activestates = new Vector(20, 10);
		calendar = new Calendar();
		timeprecision = (float)0.001;
		s = this;
		this.simulationManager = simulationManager;
	}
	
	/**
	 * Coloca objeto em seu estado inicial para simula��o
	 * Apaga todos os eventos agendados. Deve ser chamado ANTES
	 * de todos os Clear() dos Active/DeadState
	 */
	public void Clear(){
		if(running)
			return;
		simulation = null;	// impede continua��o
		clock = 0;					// reinicia rel�gio
		stopped = false;
		termreason = 0;
		calendar = new Calendar();
	}

	
	float ScheduleEvent(ActiveState activeState, double duetime){
		double time = clock + duetime; // @TODO PAGLIARES: ACUMULADOR DO CLOCK
		time = Math.floor(time / timeprecision);
		time *= timeprecision;					// trunca parte menor que timeprecision
		calendar.Add(activeState, time); // atribui calendar.root   
		return (float)time;							// retorna instante realmente utilizado
	}
	
	void Register(ActiveState a){
		if(!activestates.contains(a))
			activestates.addElement(a);
	}
	
	/**
	 * >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	 */
	public void CRescan(boolean on){	
		if(!running)
			crescan = on;
	}

	/**
	 * retorna true se a simula��o terminou
	 */
	public boolean Finished(){
		return stopped;
	}
	
	/**
	 * Inicia execu�ao da simulacao numa thread separada
	 */
	public synchronized boolean Run(double endtime){
		hasIteration = false;
		hasRelease = false;
		if(endtime < 0.0)				// rel�gio n�o pode ser negativo
			return false;				// se for 0.0 executa at� acabarem as entidades

		if(!running) { // @TODO INICIA A SIMULACAO PELA PRIMEIRA VEZ
		
			if(activestates.isEmpty())	// se n�o h� nenhum estado ativo registrado, 
				return false;			// como executar?
			activestates.trimToSize();
			running = true;
			stopped = false;
			endclock = (float)endtime;
			clock = 0;
			termreason = 0;
			simulation = new Thread(this);
			simulation.setPriority(Thread.MAX_PRIORITY);
			simulation.start();			// inicia execu��o
			Log.LogMessage("Scheduler: simulation started");

			return true;
		}

		return false;
	}
	
	/**
	 * P�ra a simulacao
	 */
	public synchronized void Stop(){
		if(stopped)						// se j� parou
			return;
		
		stopped = false;
		running = false;				// encerramento suave
		try{
			simulation.join(5000);		// espera at� 5 segundos
		}
		catch(InterruptedException e){
			stopped = true;
			simulation = null;	// n�o pode continuar
			termreason = 4;			// parada dr�stica
			Log.LogMessage("Scheduler: simulation stopped drastically");
			Log.Close();
			return;							// j� parou mesmo...
		}
		
		termreason = 3;			// parada suave bem sucedida
			
		if(!stopped){					// se ainda n�o parou...
			try{
				simulation.interrupt();		// p�ra de forma dr�stica
			}
			catch(SecurityException e){}
			
			simulation = null;	// n�o pode continuar
			termreason = 4;
			Log.LogMessage("Scheduler: simulation stopped drastically");
			Log.Close();
		}
		
		Log.LogMessage("Scheduler: simulation paused");	
	}
	
	/**
	 * Continua a execu��o de uma simula��o parada por Stop()
	 */
	public synchronized boolean Resume(){
		if(running || simulation == null || termreason != 3)
			return false;
		
		running = true;
		stopped = false;	
		simulation.start();
		termreason = 0;
		Log.LogMessage("Scheduler: simulation resumed");
		
		return true;
	}
	
	/**
	 * Seta o m�nimo intervalo entre dois instantes para que sejam considerados o mesmo.
	 */
	public void SetPrecision(double timeprec){ 
		if(!running)
			timeprecision = (float)timeprec;
	}

	/**
	 * retorna rel�gio da simula��o.
	 */
	public float GetClock(){
		return clock;
	}

	/**
	 * Codigo que roda a simulacao. (rodado numa Thread separada)
	 */
	public void run(){
		
		while(running){
			
			// TODO PAGLIARES: AVANCA O CLOCK DA SIMULACAO - FASE A
			clock = calendar.getNextClock();
			
			Log.LogMessage("Scheduler: clock advanced to " + clock);
			
			// Pagliares
			// Se clock corrente for multiplo do tempo de iteracao definido como parametro, indica o fim de  nova iteracao
			// precisa de um tick a mais de clock, pelo menos para iniciar uma nova
			
			if ((int)clock/iterationDuration > multiploIterations) {
				    multiploIterations++;
					numberOfIterations++;
					// insere os resultados da iteracao. Fazer o mesmo para atividades
					getSimulationResultsByIteration().put("Iteration" + (numberOfIterations - 1), simulationManager.getQueues());
					// para observers
					// getSimulationResultsByIteration().put("Iteration" + (numberOfIterations - 1), simulationManager.getObservers());
			}
			
			// Pagliares
			// Se clock corrente for multiplo do tempo de release definido como parametro, indica o fim de release
			// precisa de um tick a mais de clock, pelo menos para iniciar uma nova 
			if ((int)clock/releaseDuration > multiploRelease) {
					multiploRelease++;
					numberOfReleases++;
			}
			
			// verifica se simula��o chegou ao fim

			if(clock == 0.0){			// fim das entidades
				running = false;
				termreason = 1;			
				Log.LogMessage("Scheduler: simulation finished due to end of entities");
				Log.Close();
				break;
			}
			
			if(clock >= endclock && endclock != 0.0){	// fim do intervalo
				running = false;
				termreason = 2;
				Log.LogMessage("Scheduler: simulation finished due to end of simulation time");
				Log.Close();
				break;
			}
			
			// pagliares - Gera excecao para outros processos 
//			QueueEntry firstQueue = simulationManager.GetQueue("User story input queue");
//			QueueEntry lastQueue = simulationManager.GetQueue("Implemented User stories");
//			if (lastQueue.deadState.count == firstQueue.intialQuantity) {
//				running = false;
//				termreason = 1;			
//				Log.LogMessage("Scheduler: simulation finished due to end of entities");
//				Log.Close();
//				break;
//			}

			boolean executed;			// se algum evento B ou C foi executado
			
			// Fase B
			
			executed = false;
			ActiveState activeState;

			do{
				activeState = calendar.getNextActiveState();
				executed |= activeState.BServed(clock);	// se ao menos um executou, fica registrado
			}while(calendar.RemoveNext()); // ESTA LIMPANDO A LISTA SEM GENERATE ACTIVITY

			// PAGLIARES : COMENTEI AS DUAS ABAIXO SO PARA TESTE - da null pointer exception
			if(!executed)				// se n�o havia nada a ser executado nesse instante
				continue;				// pula para o pr�ximo sem executar a fase C.
										// (as atividades podem ter alterado o tempo localmente) AQUI ESTA O ERRO PRECISO CONTROLAR ESTE FLAG EXECUTED
			// PAGLIARES: COM GENERATE, PASSA DESTE IF
 			// Fase C
 
			do{
				executed = false;

				for(short i = 0; i < activestates.size(); i++)
					executed |= ((ActiveState)activestates.elementAt(i)).CServed();  // NAO ENTRA AQUI SO COM PRIORITIZE
				
			}while(crescan && executed);	
		}

		stopped = true;			// sinaliza o encerramento
		running = false;
	}

	public float getEndclock() {
		return endclock;
	}
	
	// Pagliares TODO - VERIFICAR SE PRECISA SER ATUALIZADO COM O CONTEUDO DO METODO ORIGINAL QUE FOI SOBRECARREGADO
	public synchronized boolean Run(double endtime, float iterationTime, float releaseTime){
		hasIteration = true;
		hasRelease = true;
		this.iterationDuration = iterationTime;
		this.releaseDuration = releaseTime;
		
		if(endtime < 0.0)				// relogio nao pode ser negativo
			return false;				// se for 0.0 executa ate acabarem as entidades

		if(!running){
			if(activestates.isEmpty())	// se nao ha nenhum estado ativo registrado, 
				return false;			// como executar?
			activestates.trimToSize();
			running = true;
			stopped = false;
			endclock = (float)endtime;
			clock = 0;
			termreason = 0;
			simulation = new Thread(this);
			simulation.setPriority(Thread.MAX_PRIORITY);
			simulation.start();			// inicia execu��o
			Log.LogMessage("Scheduler: simulation started");

			return true;
		}

		return false;
	}

	public int getNumberOfIterations() {
		return numberOfIterations;
	}

	public void setNumberOfIterations(int numberOfIterations) {
		this.numberOfIterations = numberOfIterations;
	}

	public int getNumberOfReleases() {
		return numberOfReleases;
	}

	public HashMap<String, HashMap> getSimulationResultsByIteration() {
		return simulationResultsByIteration;
	}

	public void setSimulationResultsByIteration(HashMap<String, HashMap> simulationResultsByIteration) {
		this.simulationResultsByIteration = simulationResultsByIteration;
	}
	
	public boolean hasRelease() {
		return hasRelease;
	}
	
	public boolean hasIteration() {
		return hasIteration;
	}
	
}