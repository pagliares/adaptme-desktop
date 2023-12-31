// Arquivo  ActiveEntry.java 
// Implementa��o das Classes do Sistema de Gerenciamento da Simula��o
// 21.Mai.1999 Wladimir

package simula.manager;

import java.io.*;

/**
 * Classe base para todos os estados ativos do modelo. 
 */
public abstract class ActiveEntry extends Entry{
  
	public static int lastid;  // identificador �NICO para os estados ativos 
	static boolean hasSerialized = true; // "lastid j� foi serializado"
		
	// PAGLIARES: antes chamado SimObj
  transient simula.ActiveState activeState;	// objeto de simula��o
                              					// n�o � serializado
  boolean isInternal;						// se � um estado ativo interno ou externo
                              					
	/**
	 * FIFO, STACK, PRIORITY:
	 * constantes que identificam
	 * as distribui��es de servi�o
	 */
  public static final short NONE    = 0;
  public static final short CONST   = 1;
  public static final short UNIFORM = 2;
  public static final short NORMAL  = 3;
  public static final short NEGEXP  = 4; 
  public static final short POISSON = 5;
  public static final short LOGNORMAL = 6;  // Criado por Pagliares
  public static final short WEIBULL = 7;  // Criado por Pagliares
  public static final short GAMMA = 8;  // Criado por Pagliares
  public static final short EXPONENTIAL = 9;  // Criado por Pagliares
  
  /**
   * tipo de distribui��o de servi�o
   */
  protected short servicedist;
  /**
   * distp1, distp2:
   * par�metros da distribui��o;
   * t�m significados diferentes 
   * de acordo com a distribui��o
   */
  protected double distp1, distp2;   // Pagliares - I refactored from float to double in order to generate DynamicExperimentationProgramProxy without casting to int

  public String toString(){
	StringBuffer stb = new StringBuffer();
	stb.append("<ActiveEntry internal=\""+isInternal+"\" servicedist=\""+serviceDistString()+"\" distp1=\""+distp1+"\" distp2=\""+distp2+"\">\r\n");
	stb.append("<A_super>\r\n");
	stb.append(super.toString());
	stb.append("</A_super>\r\n");
	stb.append("</ActiveEntry>\r\n");
	return stb.toString();
  }
  String serviceDistString()
  {
	if(servicedist == NONE)
	{
		return "NONE";
	}
	else if(servicedist == CONST)
	{
		return "CONST";
	}
	else if(servicedist == UNIFORM)
	{
		return "UNIFORM";
	}
	else if(servicedist == NORMAL)
	{
		return "NORMAL";
	}
	else if(servicedist == NEGEXP)
	{
		return "NEGEXP";
	}
	else if(servicedist == POISSON)
	{
		return "POISSON";
	}
	else if(servicedist == LOGNORMAL)
	{
		return "LOGNORMAL";
	}
	else if(servicedist == WEIBULL)
	{
		return "WEIBULL";
	}
	else if(servicedist == GAMMA)
	{
		return "GAMMA";
	}
	else if(servicedist == EXPONENTIAL)
	{
		return "EXPONENTIAL";
	}
	return "SERVICEDIST???";
  }
  /**
   * constr�i um objeto com id gerado internamente;
   * preenche com argumentos padr�o os demais campos.
   */
  public ActiveEntry()
  {
    super("a_" + String.valueOf(lastid));
    lastid++;
    servicedist = UNIFORM;
	distp1 = 0;
	distp2 = 10;
  }
  
  public void copyAttributes(Entry v_e)
  {
	super.copyAttributes(v_e);
	ActiveEntry actEntry = (ActiveEntry)v_e;
	activeState = actEntry.activeState;
	isInternal = actEntry.isInternal;
	servicedist = actEntry.servicedist;
	distp1 = actEntry.distp1;
	distp2 = actEntry.distp2;
  }
  
  /**
   * Cria os observadores e histogramas associados
   */
  protected boolean setup(SimulationManager m)
  {
//System.out.println("\tActiveEntry.Setup. name = "+name);	  
  	activeState.name = name;
		if(obsid == null)			// nada para criar
			return true;
			
		return m.GetObserver(obsid).generate(m);
	}
  	
  private void writeObject(ObjectOutputStream stream)
     throws IOException
	{
		stream.defaultWriteObject();
		
		if(hasSerialized)
			return;
			
		stream.writeInt(lastid);
		hasSerialized = true;
	}
 	private void readObject(ObjectInputStream stream)
     throws IOException, ClassNotFoundException
	{
		stream.defaultReadObject();
		
		if(hasSerialized)
			return;
			
		lastid = stream.readInt();
		hasSerialized = true;
	}
	
	public short getServiceDist(){	
		return servicedist;	
	}
	
	public void setServiceDist(short v_sServiceDist){	
		servicedist = v_sServiceDist;	
	}
	
	public double getDistP1(){	
		return distp1;	
	}
	
	public double getDistP2(){	
		return distp2;	
	}
	
	public void setDistP1(double v_fDistP1){	
		distp1 = v_fDistP1;	
	}
	
	public void setDistP2(double v_fDistP2){	
		distp2 = v_fDistP2;	
	}
	
	public simula.ActiveState getActiveState() {
		return activeState;
	}
	
	public void setActiveState(simula.ActiveState activeState) {
		this.activeState = activeState;
	}
}