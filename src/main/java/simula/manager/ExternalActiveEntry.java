// Arquivo  ExternalActiveEntry.java 
// Implementa��o das Classes do Sistema de Gerenciamento da Simula��o
// 21.Mai.1999 Wladimir

package simula.manager;

import simula.*;

/**
 * Entrada para os estados ativos Generate e Destroy.
 */
public class ExternalActiveEntry extends ActiveEntry
{
  private boolean gen;      					// true -> generate / false -> destroy
	/**
	 * id do queue associado
	 */
  private String qid;      
  /**
   * tipo da Entity gerada (se Generate)
   * se null, nenhum tipo (s/ attribs).
   */
  private String enttype;		
    
  public String toString()
  {
	StringBuffer stb = new StringBuffer();
	stb.append("<ExternalActiveEntry gen=\""+gen+"\" qid=\""+qid+"\" enttype=\""+enttype+"\">\r\n");
	stb.append("<EA_super>\r\n");
	stb.append(super.toString());
	stb.append("</EA_super>\r\n");
	stb.append("</ExternalActiveEntry>\r\n");
	return stb.toString();
  }
  /**
   * constr�i um objeto com id gerado internamente;
   * @param	generate	deve especificar se � um estado Generate (true) ou Destroy(false).
   */
  public ExternalActiveEntry(boolean generate)
  {
    super();
    gen = generate;
    isInternal = false;
  }
  
  public void copyAttributes(Entry v_e)
  {
	super.copyAttributes(v_e);
	ExternalActiveEntry extEntry = (ExternalActiveEntry)v_e;
	gen = extEntry.gen;
	qid = extEntry.qid;
	enttype = extEntry.enttype;
  }
  
  /**
   * Seta o ID da Queue associada
   */
  public void setQID(String v_strQID)
  {
	  System.out.println("EAE: id "+id+".setQID "+v_strQID);
	  qid = v_strQID;
  }
  public String getQID(){	return qid;	}
  
  boolean generate(SimulationManager m)
	{
		if(gen)
			activeState = new Generate(m.scheduler);
		else
			activeState = new Destroy(m.scheduler);
			
		return Setup(m);
	}

  /**
   * Ajusta os par�metros comuns aos ActiveState's
   */
  protected boolean Setup(SimulationManager m)
	{
		if(!super.Setup(m))
			return false;
		
		if(gen)
		{
			switch(servicedist)
			{
				case NONE: break;
				case CONST: 	((Generate)activeState).SetServiceTime(new ConstDistribution(m.sample, distp1)); break;
				case UNIFORM: ((Generate)activeState).SetServiceTime(new Uniform(m.sample, distp1, distp2)); break;
				case NORMAL: 	((Generate)activeState).SetServiceTime(new Normal(m.sample, distp1, distp2)); break;
				case NEGEXP: 	((Generate)activeState).SetServiceTime(new NegExp(m.sample, distp1)); break;
				case POISSON: ((Generate)activeState).SetServiceTime(new Poisson(m.sample, distp1)); break;
				default: return false;
			}
			
			((Generate)activeState).ConnectQueue(m.GetQueue(qid).deadState);
		}
		else
		{
			System.out.println("ExternalActiveEntry.Setup id= "+id+" qid"+qid);
			System.out.println("ExternalActiveEntry.Setup "+activeState+" "+m.GetQueue(qid));
			((Destroy)activeState).ConnectQueue(m.GetQueue(qid).deadState);
		}

		if(enttype != null)
		{
			AttributeTable type = m.GetType(enttype);
			if(type == null)
				return false;
			
			((Generate)activeState).SetEntitiesAtts(type.GetIds(), type.GetValues());
		}

		return true;	
	}

  /**
   * Returns true if it is a Generate
   */
	public final boolean IsGenerate(){return gen;}
	public String getEnttype(){	return enttype;	}
	public void setEnttype(String v_strEnttype){	enttype = v_strEnttype;	}
}