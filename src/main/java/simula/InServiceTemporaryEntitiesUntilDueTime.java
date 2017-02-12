// Arquivo IntQEntry.java
// Implementa��o das Classes do Grupo de Modelagem da Biblioteca de Simula��o JAVA
// 9.Abr.1999	Wladimir

package simula;

class InServiceTemporaryEntitiesUntilDueTime{  // Pagliares: named IntQEntry before I refactor it out
	/**
	 * vetor de entidades em servico
	 */
	public Entity entities[];				
	/**
	 * instante de fim de servico
	 */
	public float duetime;		

	public InServiceTemporaryEntitiesUntilDueTime(int nentities, float duetime){
		entities = new Entity[nentities];
		this.duetime = duetime;
	}
}