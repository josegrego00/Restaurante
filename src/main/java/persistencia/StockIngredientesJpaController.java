/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import logica.Ingredientes;
import logica.StockIngredientes;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author josepino
 */
public class StockIngredientesJpaController implements Serializable {

    public StockIngredientesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public StockIngredientesJpaController() {
    this.emf=Persistence.createEntityManagerFactory("persistenciaPU");
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(StockIngredientes stockIngredientes) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ingredientes idIngrediente = stockIngredientes.getIdIngrediente();
            if (idIngrediente != null) {
                idIngrediente = em.getReference(idIngrediente.getClass(), idIngrediente.getId());
                stockIngredientes.setIdIngrediente(idIngrediente);
            }
            em.persist(stockIngredientes);
            if (idIngrediente != null) {
                idIngrediente.getStockIngredientesList().add(stockIngredientes);
                idIngrediente = em.merge(idIngrediente);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(StockIngredientes stockIngredientes) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            StockIngredientes persistentStockIngredientes = em.find(StockIngredientes.class, stockIngredientes.getId());
            Ingredientes idIngredienteOld = persistentStockIngredientes.getIdIngrediente();
            Ingredientes idIngredienteNew = stockIngredientes.getIdIngrediente();
            if (idIngredienteNew != null) {
                idIngredienteNew = em.getReference(idIngredienteNew.getClass(), idIngredienteNew.getId());
                stockIngredientes.setIdIngrediente(idIngredienteNew);
            }
            stockIngredientes = em.merge(stockIngredientes);
            if (idIngredienteOld != null && !idIngredienteOld.equals(idIngredienteNew)) {
                idIngredienteOld.getStockIngredientesList().remove(stockIngredientes);
                idIngredienteOld = em.merge(idIngredienteOld);
            }
            if (idIngredienteNew != null && !idIngredienteNew.equals(idIngredienteOld)) {
                idIngredienteNew.getStockIngredientesList().add(stockIngredientes);
                idIngredienteNew = em.merge(idIngredienteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = stockIngredientes.getId();
                if (findStockIngredientes(id) == null) {
                    throw new NonexistentEntityException("The stockIngredientes with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            StockIngredientes stockIngredientes;
            try {
                stockIngredientes = em.getReference(StockIngredientes.class, id);
                stockIngredientes.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The stockIngredientes with id " + id + " no longer exists.", enfe);
            }
            Ingredientes idIngrediente = stockIngredientes.getIdIngrediente();
            if (idIngrediente != null) {
                idIngrediente.getStockIngredientesList().remove(stockIngredientes);
                idIngrediente = em.merge(idIngrediente);
            }
            em.remove(stockIngredientes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<StockIngredientes> findStockIngredientesEntities() {
        return findStockIngredientesEntities(true, -1, -1);
    }

    public List<StockIngredientes> findStockIngredientesEntities(int maxResults, int firstResult) {
        return findStockIngredientesEntities(false, maxResults, firstResult);
    }

    private List<StockIngredientes> findStockIngredientesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(StockIngredientes.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public StockIngredientes findStockIngredientes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(StockIngredientes.class, id);
        } finally {
            em.close();
        }
    }

    public int getStockIngredientesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<StockIngredientes> rt = cq.from(StockIngredientes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
