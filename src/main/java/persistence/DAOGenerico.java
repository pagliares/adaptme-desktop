/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import javax.persistence.EntityManager;

/**
 *
 * @author pagliares
 */
// @Stateless
public class DAOGenerico {

    // @PersistenceContext
    private EntityManager gerenciador;

    public DAOGenerico() {
	// TODO Auto-generated constructor stub
    }

    // public DAOGenerico(EntityManager em) {
    // gerenciador = em;
    // }

    public void cadastrar(Object o) {
	// gerenciador.getTransaction().begin();
	gerenciador.persist(o);
	// gerenciador.getTransaction().commit();
    }

    public Object pesquisarPorChave(Class c, Integer chave) {
	return gerenciador.find(c, chave);
    }

    public Object pesquisarPorChave(Class c, Long chave) {
	return gerenciador.find(c, chave);
    }

    public void atualizar(Object o) {
	// gerenciador.getTransaction().begin();
	gerenciador.merge(o);
	// gerenciador.getTransaction().commit();
    }

    public void remover(Object o) {
	// gerenciador.getTransaction().begin();
	gerenciador.remove(o);
	// gerenciador.getTransaction().commit();
    }

}
