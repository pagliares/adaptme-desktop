// Arquivo TTreeEntry.java
// Implementa��o das Classes do Grupo Executivo da Biblioteca de Simula��o JAVA
// 19.Mar.1999	Wladimir

package simula;

/**
 * Classe que implementa um n� de uma �rvore
 */
class TTreeEntry {
	/** 
	 * PAGLIARES: Each node in a ternary tree is like a hashMap or associative array
	 * In this case, each node contains an activeState (GenerateActivity, Router, Activity, Destroy) and its
	 * Corresponding duration time (sample duration? cumulativeClock?)
	 */
	public ActiveState activeState; 
	public float time;      
	  
	public TTreeEntry left, right, middle, parent;
	
	public TTreeEntry(){ 
		left = right = middle = parent = null;
		time = (float)0.0; 
		activeState = null;
	}
	
	public TTreeEntry(ActiveState activeState, double duetime){ 
		this.activeState = activeState;
		time = (float)duetime; 
		left = right = middle = parent = null;
		
		
	}
}
