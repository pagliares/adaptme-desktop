// Arquivo IntQEntry.java
// Implementa��o das Classes do Grupo de Modelagem da Biblioteca de Simula��o JAVA
// 9.Abr.1999	Wladimir

package simula;

class InServiceEntitiesUntilDueTime{  // Pagliares: named IntQEntry before I refactor it out
	/**
	 * vetor de entidades em servi�o
	 */
	public Entity entities[];				
	/**
	 * instante de fim de servi�o
	 */
	public float duetime;		

	public InServiceEntitiesUntilDueTime(int nentities, float duetime){
		entities = new Entity[nentities];
		this.duetime = duetime;
	}
}