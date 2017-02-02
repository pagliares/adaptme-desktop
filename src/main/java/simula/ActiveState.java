// Arquivo ActiveState.java
// Implementa��o das Classes do Grupo de Modelagem da Biblioteca de Simula��o JAVA
// 26.Mar.1999	Wladimir

package simula;

public abstract class ActiveState
{
	/**
	 * refer�ncia ao Scheduler para registro e chamadas
	 */
	protected Scheduler s;		
	/**
	 * observador (para estat�sticas)
	 */
	protected ActiveObserver obs;
	/**
	 * nome (para identificar estado para o log)
	 */
	public String name = "";		
	
	// pagliares
	// The delimiters are part of regular xacdml activities and routers, that the reason being place here and
	// not in the Activity subclass. the delimiters are not part of special XACDML activities with no simulation time
	// like iteration, phase, activity and milestones in SPEM
//	private String releaseDelimiter =  "";
//	private String iterationDelimiter =  "";
//	private String activityDelimiter =  "";
//	private String phaseDelimiter =  "";
//	private String milestoneDelimiter =  "";
	 

	/**
	 * registra com o Scheduler o estado ativo para ser servido na fase B 
	 * daqui a time instantes; retorna o instante absoluto em que ocorrer� o servi�o.
	 */
	final protected float RegisterEvent(float time)
	{
		return s.ScheduleEvent(this, time);
	}

	/**
	 * referencia Scheduler para registrar-se e registrar eventos da fase B.
	 */
	public ActiveState(Scheduler s)
	{
		this.s = s;
		s.Register(this);
	}

	/**
	 * associa observador
	 */
	public void SetObserver(ActiveObserver o)
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
	 * executa fase B com as entidades que devem ser servidas no tempo se simula��o time;
	 * retorna true se algu�m foi servido.
	 */
	public abstract boolean BServed(float time);
	/**
	 * executa a fase C; retorna true se algum evento condicional ocorreu.
	 */
	public abstract boolean CServed();
	
 
//	public String getActivityDelimiter() {
//		return activityDelimiter;
//	}
//
//	public void setActivityDelimiter(String activityDelimiter) {
//		this.activityDelimiter = activityDelimiter;
//	}
//
//	public String getReleaseDelimiter() {
//		return releaseDelimiter;
//	}
//
//	public void setReleaseDelimiter(String releaseDelimiter) {
//		this.releaseDelimiter = releaseDelimiter;
//	}
//
//	public String getIterationDelimiter() {
//		return iterationDelimiter;
//	}
//
//	public void setIterationDelimiter(String iterationDelimiter) {
//		this.iterationDelimiter = iterationDelimiter;
//	}
//
//	public String getPhaseDelimiter() {
//		return phaseDelimiter;
//	}
//
//	public void setPhaseDelimiter(String phaseDelimiter) {
//		this.phaseDelimiter = phaseDelimiter;
//	}
//
//	public String getMilestoneDelimiter() {
//		return milestoneDelimiter;
//	}
//
//	public void setMilestoneDelimiter(String milestoneDelimiter) {
//		this.milestoneDelimiter = milestoneDelimiter;
//	}
}