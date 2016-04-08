// Arquivo ObserverEntry.java 
// Implementa��o das Classes do Sistema de Gerenciamento da Simula��o
// 21.Mai.1999 Wladimir

package simula.manager;

import simula.*;
import java.io.*;

/**
 * Entrada para os diveros observadores do modelo.
 * Entry.obsid � usado como link para a lista de Observadores
 */
public class ObserverEntry extends Entry
{
	private static int lastid;	// identificador �NICO para os observadores
	static boolean hasSerialized = true; // "lastid j� foi serializado"
		
	/**
	 * QUEUE, RESOURCE, ACTIVE, PROCESSOR, DELAY:
	 * constantes que identificam o tipo do observador
	 */
	public final static short QUEUE 		= 0; 
	public final static short RESOURCE 	= 1;
	public final static short ACTIVE 		= 2;
	public final static short PROCESSOR	= 3;
	public final static short DELAY			= 4;
		
	/**
	 * tipo do observador
	 */
	private short type;
	/**
	 * id de quem � observado
	 */
	private String observed;

	/**
	 * atributo observado<p>
	 * para QUEUE, se "time" -> queue time, se null -> length
	 * para RESOURCE, sempre null
	 * para ACTIVE, se null -> idle time, sen�o o pr�prio atributo
	 * para PROCESSOR, o atributo a que ser� atribu�do exp
	 * para DELAY, se null -> stamp, se "obs" -> mede delay
	 */
	private String att;							
		
	/**
	 * para PROCESSOR, express�o; 
	 * para DELAY, se null -> na entrada, se "" -> na sa�da
	 */
	private String exp;		

	/**
	 * id do histograma associado
	 * se null -> Statistics
	 * para PROCESSOR: null -> entering
	 */
	private String histid;					
															
																
																		
	transient Observer SimObj;			// objeto de simula��o
                              			// n�o � serializado
  			
	public String toString()
	{
		StringBuffer stb = new StringBuffer();
		stb.append("<ObserverEntry type=\""+typeString()+"\" observed=\""+observed+"\" att=\""+att+"\" exp=\""+exp+"\" histid=\""+histid+"\">\r\n");
		stb.append("<O_super>\r\n");
		stb.append(super.toString());
		stb.append("</O_super>\r\n");
		stb.append("</ObserverEntry>\r\n");
		return stb.toString();
	}
	String typeString()
	{
		if(type == QUEUE)
		{
			return "QUEUE";
		}
		else if(type == RESOURCE)
		{
			return "RESOURCE";
		}
		else if(type == ACTIVE)
		{
			return "ACTIVE";
		}
		else if(type == PROCESSOR)
		{
			return "PROCESSOR";
		}
		else if(type == DELAY)
		{
			return "DELAY";
		}
		return "TYPE???";
	}
	/**
	 * constr�i um objeto com id gerado internamente;
	 * determina o tipo do observador e quem � observado.
	 */
	public ObserverEntry(short obsType, String who)
	{
		super("o_" + String.valueOf(lastid));
		lastid++;
		type = obsType;
		observed = who;
	}
	
	public void copyAttributes(Entry v_e)
	{
		super.copyAttributes(v_e);
		ObserverEntry obsEntry = (ObserverEntry)v_e;
		type = obsEntry.type;
		observed = obsEntry.observed;
		att = obsEntry.att;
		exp = obsEntry.exp;
		histid = obsEntry.histid;
		SimObj = obsEntry.SimObj;
	}
	
	/**
	 * Retorna tipo do observador.
	 */
	public final short getType(){return type;}
	public final String getAttribute(){	return att;	}
	public final String getExp(){	return exp;	}
	public final String getHistid(){	return histid;	}
	public final String getObserved(){	return observed;	}
	public final void setAttribute(String v_strAttribute){	att = v_strAttribute;	}
	public final void setExp(String v_strExp){	exp = v_strExp;	}
	public final void setHistid(String v_strHistid){	histid = v_strHistid;	}
	
	boolean Generate(SimulationManager m)
	{
		boolean ok = true;
		
		switch(type)
		{
			case RESOURCE: SimObj = new ResourceObserver(m.s, m.GetResource(observed).SimObj, new Statistics(m.s)); break;
	
			case QUEUE: 
				if(histid == null)
					SimObj = new QueueObserver(m.s, m.GetQueue(observed).SimObj, new Statistics(m.s), att != null);
				else
				{
					HistogramEntry he = m.GetHistogram(histid);
					ok = he.Generate(m);
					SimObj = new QueueObserver(m.s, m.GetQueue(observed).SimObj, he.SimObj, att != null);
				}
				break;
	
			case ACTIVE:
				if(histid == null)
					SimObj = new ActiveObserver(m.s, m.GetActiveState(observed).SimObj, new Statistics(m.s), att);
				else
				{
					HistogramEntry he = m.GetHistogram(histid);
					ok = he.Generate(m);
					SimObj = new ActiveObserver(m.s, m.GetActiveState(observed).SimObj, he.SimObj, att);
				}
				break;
		
			case PROCESSOR:
				Expression expobj;
			
				try
				{
					float val = Float.parseFloat(exp);
					expobj = new ConstExpression(val);
				}
				catch(NumberFormatException x)
				{
					if(exp.equalsIgnoreCase("true"))
						expobj = ConstExpression.TRUE;
					else if(exp.equalsIgnoreCase("false"))
						expobj = ConstExpression.FALSE;
					else if(exp.charAt(0) == '#')
						expobj = new RandomExpression(exp);
					else
						expobj = new Expression(exp);
				}

				SimObj = new ProcessorObserver(m.s, m.GetActiveState(observed).SimObj, att, expobj, histid == null);
				break;
				
			case DELAY: 
				if(histid == null)
					SimObj = new DelayObserver(m.s, m.GetActiveState(observed).SimObj, new Statistics(m.s), att == null, exp == null);
				else
				{
					HistogramEntry he = m.GetHistogram(histid);
					ok = he.Generate(m);
					SimObj = new DelayObserver(m.s, m.GetActiveState(observed).SimObj, he.SimObj, att == null, exp == null);
				}
				break;

			default: return false;
		}
		
		if(!ok)
			return false;
		
		if(obsid == null)			// fim da lista de observadores
			return true;
			
		return m.GetObserver(obsid).Generate(m); // cria pr�ximo
	}
	
	/**
	 * Realiza o relat�rio dos dados observados em forma textual;
	 * Deve ser chamado com a simula��o parada;
	 * obstime � o intervalo de tempo a que as estat�sticas se referem.
	 */
	void DoReport(PrintStream os, float obstime)
	{
		if(SimObj == null)	// erro
			return;
	
		os.println("----------------------------------------------------------------------");
		os.println("\r\nReport from observer " + name);
		os.println("Statistics summary:");
		
		boolean weighted = false;
		
		switch(type)
		{
			case RESOURCE:
				os.println("Number of resources permanencing in queue:");
				weighted = true;
				break;
			case QUEUE:
				if(att == null)
				{
					os.println("Queue length:");
					weighted = true;
				}
				else
					os.println("Entity queue time:");
				break;
			case ACTIVE:
				if(att == null)
					os.println("Active state idle time (or inter-arrival time for Generate states):");
				else
					os.println("Observed entity attribute " + att + ":");
				break;
			case DELAY:
				if(att == null)
				{
					os.println("Timestamper observer; no statistics available.");
					return;
				}
				else
				{
					os.print("Delay observed from previous stamp when ");
					if(exp == null)
						os.println("arriving:");
					else
						os.println("leaving:");
				}
				break;
			case PROCESSOR:
				os.println("Processor observer; no statistics available.");
				os.println("Assigned " + exp + " to " + att + ".");
				return;
			default:
				os.println("Error: unknown observer type.");
		}
		
		if(weighted)
		{
			float div = obstime / SimObj.NumObs();
			
			os.print("Average: " + SimObj.Mean() / div);
			os.print(" StdDev: " + SimObj.StdDev() / div);
			os.println(" Variance: " + SimObj.Variance() / (div * div));
			os.print(" Minimum: " + SimObj.Min());
			os.print(" Maximum: " + SimObj.Max());
			os.println(" Observations: " + SimObj.NumObs());
		}
		else
		{
			os.print("Average: " + SimObj.Mean());
			os.print(" StdDev: " + SimObj.StdDev());
			os.println(" Variance: " + SimObj.Variance());
			os.print(" Minimum: " + SimObj.Min());
			os.print(" Maximum: " + SimObj.Max());
			os.println(" Observations: " + SimObj.NumObs());
		}

		if(histid != null)			
			os.println("\r\nAditional information available for histogram with id = " + histid + ".");
			
		os.println("----------------------------------------------------------------------");
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
 	
 	void DoReportConsole(float obstime)
	{
		if(SimObj == null)	// erro
			return;
	
		System.out.println("----------------------------------------------------------------------");
		System.out.println("\r\nReport from observer " + name);
		System.out.println("Statistics summary:");
		
		boolean weighted = false;
		
		switch(type)
		{
			case RESOURCE:
				System.out.println("Number of resources permanencing in queue:");
				weighted = true;
				break;
			case QUEUE:
				if(att == null)
				{
					System.out.println("Queue length:");
					weighted = true;
				}
				else
					System.out.println("Entity queue time:");
				break;
			case ACTIVE:
				if(att == null)
					System.out.println("Active state idle time (or inter-arrival time for Generate states):");
				else
					System.out.println("Observed entity attribute " + att + ":");
				break;
			case DELAY:
				if(att == null)
				{
					System.out.println("Timestamper observer; no statistics available.");
					return;
				}
				else
				{
					System.out.print("Delay observed from previous stamp when ");
					if(exp == null)
						System.out.println("arriving:");
					else
						System.out.println("leaving:");
				}
				break;
			case PROCESSOR:
				System.out.println("Processor observer; no statistics available.");
				System.out.println("Assigned " + exp + " to " + att + ".");
				return;
			default:
				System.out.println("Error: unknown observer type.");
		}
		
		if(weighted)
		{
			float div = obstime / SimObj.NumObs();
			
			System.out.print("Average: " + SimObj.Mean() / div);
			System.out.print(" StdDev: " + SimObj.StdDev() / div);
			System.out.println(" Variance: " + SimObj.Variance() / (div * div));
			System.out.print(" Minimum: " + SimObj.Min());
			System.out.print(" Maximum: " + SimObj.Max());
			System.out.println(" Observations: " + SimObj.NumObs());
		}
		else
		{
			System.out.print("Average: " + SimObj.Mean());
			System.out.print(" StdDev: " + SimObj.StdDev());
			System.out.println(" Variance: " + SimObj.Variance());
			System.out.print(" Minimum: " + SimObj.Min());
			System.out.print(" Maximum: " + SimObj.Max());
			System.out.println(" Observations: " + SimObj.NumObs());
		}

		if(histid != null)			
			System.out.println("\r\nAditional information available for histogram with id = " + histid + ".");
			
		System.out.println("----------------------------------------------------------------------");
	}
 	
 	StringBuffer getReportConsole(float obstime)
	{
		if(SimObj == null)	// erro
			return null;
	
		StringBuffer result = new StringBuffer();
		result.append("----------------------------------------------------------------------\n");
		result.append("\r\nReport from observer " + name + "\n");
		result.append("Statistics summary:\n");
		
		boolean weighted = false;
		
		switch(type)
		{
			case RESOURCE:
				result.append("Number of resources permanencing in queue:\n");
				weighted = true;
				break;
			case QUEUE:
				if(att == null)
				{
					result.append("Queue length:\n");
					weighted = true;
				}
				else
					result.append("Entity queue time:\n");
				break;
			case ACTIVE:
				if(att == null)
					result.append("Active state idle time (or inter-arrival time for Generate states):\n");
				else
					result.append("Observed entity attribute " + att + ":\n");
				break;
			case DELAY:
				if(att == null)
				{
					result.append("Timestamper observer; no statistics available.\n");
					return result;
				}
				else
				{
					result.append("Delay observed from previous stamp when ");
					if(exp == null)
						result.append("arriving:\n");
					else
						result.append("leaving:\n");
				}
				break;
			case PROCESSOR:
				result.append("Processor observer; no statistics available.\n");
                result.append("Assigned " + exp + " to " + att + ".\n");
				return result;
			default:
				result.append("Error: unknown observer type.\n");
		}
		
		if(weighted)
		{
			float div = obstime / SimObj.NumObs();
			result.append("Average: " + SimObj.Mean() / div);
			result.append(" StdDev: " + SimObj.StdDev() / div);
			result.append(" Variance: " + SimObj.Variance() / (div * div) + "\n"); 
			result.append(" Minimum: " + SimObj.Min());
			result.append(" Maximum: " + SimObj.Max()); 
			result.append(" Observations: " + SimObj.NumObs() + "\n");
		}
		else
		{
			result.append("Average: " + SimObj.Mean());
			result.append(" StdDev: " + SimObj.StdDev());
			result.append(" Variance: " + SimObj.Variance() + "\n");
			result.append(" Minimum: " + SimObj.Min());
			result.append(" Maximum: " + SimObj.Max());
			result.append(" Observations: " + SimObj.NumObs() + "\n"); 
			 
		}

		if(histid != null)			
			result.append("\r\nAditional information available for histogram with id = " + histid + ".\n");
		    
		result.append("----------------------------------------------------------------------\n");	
		return result; 
	}
 	
 	public double getAvearageWeighted(float obstime) {
 		float div = obstime / SimObj.NumObs();
 		return SimObj.Mean() / div;
 	}
 	
 	public double getStandardDeviationWeighted(float obstime) {
  		float div = obstime / SimObj.NumObs();
 		return SimObj.StdDev() / div;
 	}
 	
 	public double getVarianceWeighted(float obstime) {
  		float div = obstime / SimObj.NumObs();
 		return SimObj.Variance() / (div * div);
 	}
 	
 	public double getMin(float obstime) {
 		return SimObj.Min();
 	}
 	
 	public double getMax(float obstime) {
 		return SimObj.Max();
 	}
 	
 	public double getNumberOfObservations(float obstime) {
 		return SimObj.NumObs();
 	}
}