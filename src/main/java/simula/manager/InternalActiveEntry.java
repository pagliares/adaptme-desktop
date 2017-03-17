// Arquivo  InternalActiveEntry.java 
// Implementa��o das Classes do Sistema de Gerenciamento da Simula��o
// 21.Mai.1999 Wladimir

package simula.manager;

import java.util.Vector;
import com.tony.util.*;
import simula.*;

/**
 * Entrada para os estados ativos Activity e Router.
 */
public class InternalActiveEntry extends ActiveEntry{
  
	boolean isRouter;				// especifica se eh um Router ou Activity

  /**
   * fq, toq, fr, tor:
   * ids dos estados mortos associados
   */
  private Vector fq;
  /**
   * fq, toq, fr, tor:
   * ids dos estados mortos associados
   */
  private Vector toq, fr, tor;
  /**
   * qtde de cada resource utilizado
   */
  private Vector quantityUsedResource;	
  /**
   * condi��es (strings) associadas;
   * se router de sa�da, sen�o de entrada
   */
  private Vector conditions;	
  
  private String dependencyType = "";
  private String conditionToProcess = "";
  private String spemType = "";
  private String processingQuantity = "";
  private double timeBox = 0.0;
  private String parent = "";
  
  
  
  //private Vector quantityUsedTemporaryEntities;	// Commented lines to be worked when trying to acquire entities in batch mode

  public String toString(){
	StringBuffer stb = new StringBuffer();
	stb.append("<InternalActiveEntry router=\""+isRouter+"\">\r\n");
	stb.append("<IA_super>\r\n");
	stb.append(super.toString());
	stb.append("</IA_super>\r\n");
	stb.append("<fq>\r\n");
	SimulationManager.appendVector(fq, stb);
	stb.append("</fq>\r\n");
	stb.append("<toq>\r\n");
	SimulationManager.appendVector(toq, stb);
	stb.append("</toq>\r\n");
	stb.append("<fr>\r\n");
	SimulationManager.appendVector(fr, stb);
	stb.append("</fr>\r\n");
	stb.append("<tor>\r\n");
	SimulationManager.appendVector(tor, stb);
	stb.append("</tor>\r\n");
	stb.append("<rqty>\r\n");
	SimulationManager.appendVector(quantityUsedResource, stb);
	stb.append("</rqty>\r\n");
	stb.append("<conds>\r\n");
	int iNConds = conditions.size();
	for(int i=0; i<iNConds; i++){
		String strCond = (String)conditions.elementAt(i);
		strCond = TString.replace(strCond, "<=", ".LE.");
		strCond = TString.replace(strCond, ">=", ".GE.");
		strCond = TString.replace(strCond, "<", ".LT.");
		strCond = TString.replace(strCond, ">", ".GT.");
		strCond = TString.replace(strCond, "=", ".EQ.");
		stb.append(strCond+"\r\n");
	}
	stb.append("</conds>\r\n");
	stb.append("</InternalActiveEntry>\r\n");
	return stb.toString();
  }
  /**
   * constr�i um objeto com id gerado internamente;
   * @param	isRouter se for do tipo Router deve passar true, sen�o false.
   */
  public InternalActiveEntry(boolean isRouter) {
    super();
    this.isRouter = isRouter;
    fq = new Vector(2, 2); //from queue?
    toq = new Vector(2, 2);//to queue?
    fr = new Vector(2, 2);
    tor = new Vector(2, 2);
    conditions = new Vector(2, 2); //conditions
    quantityUsedResource = new Vector(2, 2);
    //quantityUsedTemporaryEntities = new Vector(2,2); // Commented lines to be worked when trying to acquire entities in batch mode
    isInternal = true;
   
  }
  
  public void copyAttributes(Entry v_e) {
	super.copyAttributes(v_e);
	InternalActiveEntry intEntry = (InternalActiveEntry)v_e;
	isRouter = intEntry.isRouter;
	fq = intEntry.fq;
	toq = intEntry.toq;
	fr = intEntry.fr;
	tor = intEntry.tor;
	quantityUsedResource = intEntry.quantityUsedResource;
	// quantityUsedTemporaryEntities = intEntry.quantityUsedTemporaryEntities; // Commented lines to be worked when trying to acquire entities in batch mode
	conditions = intEntry.conditions;
  }
  
  
  boolean generate(SimulationManager m){
		if(isRouter)
			activeState = new Router(m.scheduler);
		else
			activeState = new Activity(m.scheduler);
			
		return setup(m);
	}
  	
  /**
   * Ajusta os parametros referentes aos Router's e Activity's
   * PAGLIARES: This method is the bridge between InternalActiveEntry and ActiveState. It schedule the activities in the calendar for the very first time
   */
  protected boolean setup(SimulationManager m){ 
		if(!super.setup(m))
			return false;
		
		TrimVectors();
		
		if(isRouter){
			switch(servicedist) { 
			
				case NONE: break;
				case CONST: 	((Router)activeState).setServiceTime(new ConstDistribution(m.sample, distp1)); break;
				case UNIFORM: ((Router)activeState).setServiceTime(new Uniform(m.sample, distp1, distp2)); break;
				case NORMAL: 	((Router)activeState).setServiceTime(new Normal(m.sample, distp1, distp2)); break;
				case NEGEXP: 	((Router)activeState).setServiceTime(new NegExp(m.sample, distp1)); break;
				case POISSON: ((Router)activeState).setServiceTime(new Poisson(m.sample, distp1)); break;
				case LOGNORMAL: ((Router)activeState).setServiceTime(new LogNormal(m.sample, distp1, distp2)); break;
				default: return false;
			}
			
			for(int i = 0; i < fq.size(); i++){
				((Router)activeState).ConnectQueues(m.GetQueue((String)fq.get(i)).deadState);	
			}
			
			String sexp;
			Expression exp;
			
			for(int i = 0; i < toq.size(); i++){
				sexp = (String)conditions.get(i);
				if(sexp.equalsIgnoreCase("true"))
					exp = ConstExpression.TRUE;
				else if(sexp.equalsIgnoreCase("false"))
					exp = ConstExpression.FALSE;
				else
					exp = new Expression(sexp);
					
				((Router)activeState).ConnectQueues(m.GetQueue((String)toq.get(i)).deadState, exp);	
			}
			
			for(int i = 0; i < fr.size(); i++){
				((Router)activeState).ConnectResources(m.GetResource((String)fr.get(i)).SimObj,
					 m.GetResource((String)tor.get(i)).SimObj, ((Integer)quantityUsedResource.get(i)).intValue());	
			}

		}
		else{
			
			// SPEM attributes
			Activity a = (Activity)activeState;
			a.setDependencyType(dependencyType);
			a.setParent(parent);
			a.setConditionToProcess(conditionToProcess);
			a.setProcessingQuantity(processingQuantity);
 			a.setTimeBox(timeBox);
 			a.setSpemType(spemType);
						
 			switch(servicedist){
				case NONE: break;
				case CONST: 	((Activity)activeState).SetServiceTime(new ConstDistribution(m.sample, distp1)); break;
				case UNIFORM: ((Activity)activeState).SetServiceTime(new Uniform(m.sample, distp1, distp2)); break;
				case NORMAL: 	((Activity)activeState).SetServiceTime(new Normal(m.sample, distp1, distp2)); break;
				case NEGEXP: 	((Activity)activeState).SetServiceTime(new NegExp(m.sample, distp1)); break;
				case POISSON: ((Activity)activeState).SetServiceTime(new Poisson(m.sample, distp1)); break;
				case LOGNORMAL: ((Activity)activeState).SetServiceTime(new LogNormal(m.sample, distp1, distp2)); break;
				default: return false;
			}
			 
			String sexp;
			Expression exp;
			
			for(int i = 0; i < toq.size(); i++) {
				
				sexp = (String)conditions.get(i);
				if(sexp.equalsIgnoreCase("true"))
					exp = ConstExpression.TRUE;
				else if(sexp.equalsIgnoreCase("false"))
					exp = ConstExpression.FALSE;
				else
					exp = new Expression(sexp);
				
				// Pagliares: refactored Wladimir code to avoid method chaining
				DeadState fromDeadState = m.GetQueue((String)fq.get(i)).deadState;
				DeadState toDeadState = m.GetQueue((String)toq.get(i)).deadState;
				Activity activity = (Activity) activeState;
				
				activity.ConnectQueues(fromDeadState, exp, toDeadState);	// antigo
				
				// Commented lines to be worked when trying to acquire entities in batch mode
				//	Integer quantityEntities = ((Integer)(quantityUsedTemporaryEntities.get(i))).intValue();
				// Attempt to get more than one entity
				//	activity.ConnectQueues(fromDeadState,exp, toDeadState,quantityEntities);		 
			}
			
			for(int i = 0; i < fr.size(); i++){
				ResourceQ fromResourceDeadState = m.GetResource((String)fr.get(i)).SimObj;
				ResourceQ toResourceDeadState = m.GetResource((String)tor.get(i)).SimObj;
				Integer quantityOfResourcesUsed = ((Integer)quantityUsedResource.get(i)).intValue();
				Activity activity = (Activity) activeState;
				
				activity.ConnectResources(fromResourceDeadState,toResourceDeadState, quantityOfResourcesUsed);	
			}
		}
		
		return true;	
	}
  	
  public final boolean isRouter(){	
	  return isRouter;	
  }
  
  public final void addToQueue(Object v_o){	
	  toq.add(v_o);	
  }
  
  public final void addFromQueue(Object v_o){	
	  fq.add(v_o);	
  }
  
  public final void addToResource(Object v_o){	
	  tor.add(v_o);	
  }
  
  public final void addFromResource(Object v_o){	
	  fr.add(v_o);	
  }
  
  public final void addCond(Object v_o){	
	  conditions.add(v_o);	
  }
 
  public final void addResourceQty(Object v_o){	
	  quantityUsedResource.add(v_o);	
  }
 
  public final Vector getToQueue(){	
	  return toq;	
  }
 
  public final Vector getFromQueue(){	
	  return fq;	
  }
 
  public final Vector getToResource(){	
	  return tor;	
  }
 
  public final Vector getFromResource(){	
	  return fr;	
  }
 
  public final Vector getConds(){	
	  return conditions;	
  }
 
  public final int toQueueIndexOf(Object v_o){	
	  return toq.indexOf(v_o);	
  }
 
  public final int fromQueueIndexOf(Object v_o){	return fq.indexOf(v_o);	}

  public final int toResourceIndexOf(Object v_o){	return tor.indexOf(v_o);	}
 
  public final int fromResourceIndexOf(Object v_o){	return fr.indexOf(v_o);	}
 
  public final boolean fromQueueContains(Object v_o){	return fq.contains(v_o);	}
 
  public final boolean toQueueContains(Object v_o){	return toq.contains(v_o);	}
 
  public final void removeFromQueue(int v_i){	fq.remove(v_i);	}
 
  public final void removeFromQueue(Object v_o){	fq.remove(v_o);	}
 
  public final void removeToQueue(int v_i){	toq.remove(v_i);	}
 
  public final void removeFromResource(int v_i){	fr.remove(v_i);	}
 
  public final void removeToResource(int v_i){	tor.remove(v_i);	}
 
  public final void removeCond(int v_i){	conditions.remove(v_i);	}
 
  public final void removeResourceQty(int v_i){	quantityUsedResource.remove(v_i);	}
 
  public final void setCondAt(Object v_o, int v_i){	conditions.setElementAt(v_o, v_i);	}
  
  /**
   * chama trimToSize() para cada Vector interno
   * para economizar mem�ria alocada
   */
  public void TrimVectors(){
		fq.trimToSize();
		toq.trimToSize();
		fr.trimToSize();
		tor.trimToSize();
		conditions.trimToSize();
		quantityUsedResource.trimToSize();
	}
 
 
  public String getDependencyType() {
	return dependencyType;
  }

  public void setDependencyType(String dependencyType) {
	this.dependencyType = dependencyType;
  }

  public String getConditionToProcess() {
	return conditionToProcess;
  }

  public void setConditionToProcess(String conditionToProcess) {
	this.conditionToProcess = conditionToProcess;
  }

  public void setSpemType(String spemType) {
	this.spemType = spemType;	
  }

  public String getProcessingQuantity() {
	return processingQuantity;
  }

  public void setProcessingQuantity(String processingQuantity) {
	this.processingQuantity = processingQuantity;
  }

  public double getTimeBox() {
	return timeBox;
  }

  public void setTimeBox(String timeBox) {
	if (timeBox.equals("")) 
		this.timeBox = 0;
    else 
		this.timeBox = Double.parseDouble(timeBox);
  }

  public String getParent() {
	return parent;
  }

  public void setParent(String parent) {
	this.parent = parent;
  }
public String getSpemType() {
	return spemType;
} 
}