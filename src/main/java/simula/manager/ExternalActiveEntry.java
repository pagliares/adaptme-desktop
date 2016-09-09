// Arquivo  ExternalActiveEntry.java 
// Implementa��o das Classes do Sistema de Gerenciamento da Simula��o
// 21.Mai.1999 Wladimir

package simula.manager;

import simula.*;

/**
 * Entrada para os estados ativos Generate e Destroy.
 */
public class ExternalActiveEntry extends ActiveEntry{
  private boolean isGenerate;      					// true -> generate / false -> destroy
	/**
	 * id do queue associado
	 */
  private String qid;      
  /**
   * tipo da Entity gerada (se Generate)
   * se null, nenhum tipo (s/ attribs).
   */
  private String entityType;		
    
  public String toString() {
	StringBuffer stb = new StringBuffer();
	stb.append("<ExternalActiveEntry gen=\""+isGenerate+"\" qid=\""+qid+"\" enttype=\""+entityType+"\">\r\n");
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
  public ExternalActiveEntry(boolean generate) {
    super();
    isGenerate = generate;
    isInternal = false;
  }
  
  public void copyAttributes(Entry v_e){
	super.copyAttributes(v_e);
	ExternalActiveEntry extEntry = (ExternalActiveEntry)v_e;
	isGenerate = extEntry.isGenerate;
	qid = extEntry.qid;
	entityType = extEntry.entityType;
  }
  
  /**
   * Seta o ID da Queue associada
   */
  public void setQID(String v_strQID){
	  System.out.println("EAE: id "+id+".setQID "+v_strQID);
	  qid = v_strQID;
  }
  
  public String getQID(){	
	  return qid;	
  }
  
  boolean generate(SimulationManager m){
		if(isGenerate)
			activeState = new Generate(m.scheduler);
		else
			activeState = new Destroy(m.scheduler);
			
		return setup(m);
	}

  /**
   * Ajusta os par�metros comuns aos ActiveState's
   */
  protected boolean setup(SimulationManager m){
		if(!super.setup(m))
			return false;
		
		if(isGenerate){
			switch(servicedist){
			    // aqui que dispara as chamadas de metodos de forma a inserir a atividade em calendar.root e acumular
			    // o clock da simulacao
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
		else	{
			System.out.println("ExternalActiveEntry.Setup id= "+id+" qid"+qid);
			System.out.println("ExternalActiveEntry.Setup "+activeState+" "+m.GetQueue(qid));
			((Destroy)activeState).ConnectQueue(m.GetQueue(qid).deadState);
		}

		if(entityType != null){
			AttributeTable type = m.GetType(entityType);
			if(type == null)
				return false;
			
			((Generate)activeState).SetEntitiesAtts(type.GetIds(), type.GetValues());
		}

		return true;	
	}

  /**
   * Returns true if it is a Generate
   */
	public final boolean isGenerate(){
		return isGenerate;
	}
	
	public String getEntityType(){	
		return entityType;
	}
	
	public void setEntityType(String v_strEnttype){	
		entityType = v_strEnttype;	
	}
}