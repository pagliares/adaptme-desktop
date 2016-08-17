// Arquivo Calendar.java
// Implementa��o das Classes do Grupo Executivo da Biblioteca de Simula��o JAVA
// 19.Mar.1999	Wladimir

package simula;

/**
 * Calendario de Atividades
 */
class Calendar
{
	//Pagliares BINGO: root e a atividade que esta sendo executada no exato momento. List, as proximas. Preciso
	// executar o b de root e agendar um novo root (novo ttentry com prioritize task and another time sampled
	private TTreeEntry root = null;	// raiz da �rvore 
	private TTreeEntry list = null;	// lista dos estados ativos correntes (pagliares: atividades em execucao)

	public Calendar(){}
	/**
	 * Adiciona uma atividade ao calendario
	 */
	public void Add(ActiveState activeState, double duetime)
	{
		if(root == null)	// se � a primeira inser��o
			root = new TTreeEntry(activeState, duetime);
		else{
			TTreeEntry child, parent, e;
			e = new TTreeEntry(activeState, duetime);  // Pagliares: e eh == root inicialmente? nao pois so existe e chamado apos primeira insercao

			child  = root; // por que?
			parent = null;

			// find insertion point.
			while (child != null) 
			{
				parent = child;

				// Descend left or right sub-trees
				if ((float)duetime < parent.time)
					child = parent.left;
				else if ((float)duetime > parent.time)
					child = parent.right;
				else 
				{
					// Add to list for this node 
					
					// Flag that child is now in middle list 
					e.left = e;
					TTreeEntry y;	// auxiliar

					if (child.middle != null) 
					{
						y = child.middle;
						e.parent = y.parent;
						(y.parent).middle = e;
						y.parent = e;
						e.middle = y;
					}
					else 
					{
						child.middle = e;
						e.right  = child;
						e.parent = e;
						e.middle = e;
					}
					return;
				}
			}

			e.parent = parent;
			// Update parent 
			if ((float)duetime < parent.time)
				parent.left = e;
			else if ((float)duetime > parent.time)
				parent.right = e;
		}
	}
	/**
	 * Remove uma Atividade do calendario
	 */
	public boolean Remove(ActiveState activeState, double duetime)
	{
		if(root == null)	// se �rvore vazia...
			return false;

		TTreeEntry node = Find(activeState, duetime);  // PAGLIARES: renomeei e para node

		if(node == null)		// se n�o encontrou
			return false;

		// remove da lista ligada ao n�

		TTreeEntry x, y;

		x = node.middle;

		if (node.left == node)
		{
			// e is in a list => remove 
			if (node == x) 
			{
				// nothing else in the list 
				(node.right).middle = null;
				node.right = node.middle = node.left = node.parent = null;	// zera refer�ncias
				return true;
			}
			else if (node.right != null)
			{
				// e is at the head of the list 
				(node.right).middle = x;
				x.right = node.right;
			}
		
			(node.parent).middle = x;
			x.parent = node.parent;
			node.right = node.middle = node.left = node.parent = null;	// zera refer�ncias

			return true;
		}
		else if (x != null) 
		{
			// e has a non-null child list 
			if (x.middle != x) 
			{
				// there is more than one child 
				y = x.middle;
			    (x.parent).middle = y;
				y.parent = x.parent;
				y.right  = x;
			}
			else
				// there is one child 
				x.middle = null;

			x.parent = node.parent;
			x.left   = node.left;
			if (x.left != null)
				(x.left).parent = x;
			x.right  = node.right;
			if (x.right != null)
				(x.right).parent = x;

			// Check if we need to update root 
			if (root == node)
				root = x;
			else if ((node.parent).left == node)
				(node.parent).left = x;
			else
				(node.parent).right = x;
		}
		else 
		{
			// e is in the tree and there is no list 
			// Work out which entity to reposition in the tree. 
			if (node.left != null && node.right != null) 
			{
				for (y = node.right; y.left != null; y = y.left);
			}
			else
				y = node;

			// Set x to a non-null child of y, or null if  
			// there are no non-null children.             
			x = null;
			if (y.left != null)
				x = y.left;
			else
				x = y.right;

			 // Set the pointers for x.
			if (x != null)
				x.parent = y.parent;

			// Set the pointers for y. 
			if (root == y)
				root = x;
			else if (y == (y.parent).left)
				(y.parent).left = x;
			else
				(y.parent).right = x;

			// Insert y in place of entity e. 
			if (y != node) 
			{
				// Update the pointers from e 
				y.parent = node.parent;
				y.left   = node.left;
				y.right  = node.right;

				// Update the pointers to e 
				if (node.left != null)
					(node.left).parent = y;
				if (node.right != null)
					(node.right).parent = y;

				// Update root or parent 
				if (root == node)
					root = y;
				else if ((node.parent).left == node)
					(node.parent).left = y;
				else
					(node.parent).right = y;
			}

			// Invalidate e's old pointers. 
			node.right = null;
			node.left  = null;
			node.parent= null;
			node.middle= null;
		}

		return true;
	}

	/**
	 * encontra posi��o na �rvore
	 * n�o precisa se preocupar em procurar na lista porque ela s� cont�m 
	 * os eventos que ser�o executados neste instante
	 */
	private TTreeEntry Find(ActiveState a, double duetime){

		TTreeEntry node;
		node = root;
		while(true)
		{
			if((float)duetime < node.time && node.left != null)
				node = node.left;
			else if((float)duetime > node.time && node.right != null)
				node = node.right;
			else if((float)duetime == node.time)
				break;			
			else
				return null;	// n�o est� na �rvore
		}
		if(a == node.activeState)			// achou!
			return node;
		if(a != node.activeState && node.middle == null)
			return null;		// tempo certo mas estado ativo n�o est� presente
		
		// procura posi��o na lista do n�
		TTreeEntry endflag = node.middle.parent;
		while(a != node.activeState)
		{
			if(node == endflag)	// se sou o �ltimo e ainda n�o encontrei
				return null;	// n�o existe na �rvore
			node = node.middle;	
		}

		return node;
	}

	public ActiveState getNextActiveState()
	{
		if(list == null)		// se o rel�gio ainda n�o avan�ou
			return null;		// n�o retorna nenhum estado ativo
		
		return list.activeState; // PAGLIARES: CORROBORA O BINGO. LIST CONTEM CURRENT TASK EXECUTED
  
	}
	public float GetNextClock()  // PAGLIARES: next clock indica o time associado com o no list
	{
		// se este � o primeiro evento ap�s o avan�o do rel�gio
		if(list == null)  // VERIFICAR VARIAVEIS LIST E ROOT PARA EXEMPLO COM GENERATE ACTIVITY
		{
			if(root == null)		// se j� removeu todas as entidades...
				                    // PAGLIARES: in other words, se nao ha mais nenhuma atividade agendada
			{
				list = null;
				return (float)0.0;  // AQUI QUE ESTA VOLTANDO O CLOCK PARA ZERO (POIS LIST E ROOT SE TORNARAM NULL
			}
			
			TTreeEntry node, child, parent;
	
			child = root;
			parent = null;

			// encontra n� com menor duetime
			// PAGLIARES: eh este while entao que pega a proxima atividade agendada e prepara para leva-la para root?
			while (child.left != null) 
			{
				parent = child;
				child  = child.left;
			}
	
			// remodela �rvore
			if (child == root)  // PAGLIARES: child agora seria o de menor duetime (proxima a ser executada)
				root = child.right;  // SEM GENERATE, CAI AQUI FAZENDO ROOT = NULL CHILD CONTINUA COM ATIVIDADE
			                         // NA VERDADE, JA CHEGA COM ROOT = NULL
									// CASO NAO TENHA ACHADO COM MENOR DUE TIME, PESQUISA A DIREITA DA RAIZ
			else 
			{
				parent.left = child.right;  // PAGLIAERS: NAO ENTENDI ESTE ELSE
				if (child.right != null)
					(child.right).parent = parent;
			}

			// transforma numa lista duplamente ligada com parent e middle somente
			// e acaba com a lista circular

			if(child.middle != null)
			{
				child.middle.parent.middle = null;	// indica fim da lista
				child.middle.parent = child;		// uniformiza
				child.middle.right = null;			// idem
			}
			child.right = null;					// idem
			child.parent = null;				// desconecta da �rvore

			// obt�m a lista com os estados ativos a serem servidos neste tempo
			list = child;
		}

		return list.time;
	}
	
	public boolean RemoveNext(){
		if(list == null) return false;	// n�o cria lista, apenas remove dela
	
		if(list.middle != null)		{	// se n�o � �ltimo...
			list = list.middle;
			
			// limpa refer�ncias 
			list.parent.middle = null;
			list.parent.left = null;
			list.parent = null;			// desconecta da lista
			
			return true;
		}
		else{
			list.left = null;
			list = null;		// lista vazia  TESTAR SEM FAZER NULL
			// sem null, nao avanca o clock, e a simulacao fica sem terminar em loop.
			// verificar como com generate, agenda novamente para mudar o clock e nao ficar sempre no mesmo
			
			return false; 
		}
	}
}