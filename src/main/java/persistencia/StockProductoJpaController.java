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
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import logica.Producto;
import logica.StockProducto;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author josepino
 */
public class StockProductoJpaController implements Serializable {

    public StockProductoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(StockProducto stockProducto) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto idProducto = stockProducto.getIdProducto();
            if (idProducto != null) {
                idProducto = em.getReference(idProducto.getClass(), idProducto.getId());
                stockProducto.setIdProducto(idProducto);
            }
            em.persist(stockProducto);
            if (idProducto != null) {
                idProducto.getStockProductoList().add(stockProducto);
                idProducto = em.merge(idProducto);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(StockProducto stockProducto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            StockProducto persistentStockProducto = em.find(StockProducto.class, stockProducto.getId());
            Producto idProductoOld = persistentStockProducto.getIdProducto();
            Producto idProductoNew = stockProducto.getIdProducto();
            if (idProductoNew != null) {
                idProductoNew = em.getReference(idProductoNew.getClass(), idProductoNew.getId());
                stockProducto.setIdProducto(idProductoNew);
            }
            stockProducto = em.merge(stockProducto);
            if (idProductoOld != null && !idProductoOld.equals(idProductoNew)) {
                idProductoOld.getStockProductoList().remove(stockProducto);
                idProductoOld = em.merge(idProductoOld);
            }
            if (idProductoNew != null && !idProductoNew.equals(idProductoOld)) {
                idProductoNew.getStockProductoList().add(stockProducto);
                idProductoNew = em.merge(idProductoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = stockProducto.getId();
                if (findStockProducto(id) == null) {
                    throw new NonexistentEntityException("The stockProducto with id " + id + " no longer exists.");
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
            StockProducto stockProducto;
            try {
                stockProducto = em.getReference(StockProducto.class, id);
                stockProducto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The stockProducto with id " + id + " no longer exists.", enfe);
            }
            Producto idProducto = stockProducto.getIdProducto();
            if (idProducto != null) {
                idProducto.getStockProductoList().remove(stockProducto);
                idProducto = em.merge(idProducto);
            }
            em.remove(stockProducto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<StockProducto> findStockProductoEntities() {
        return findStockProductoEntities(true, -1, -1);
    }

    public List<StockProducto> findStockProductoEntities(int maxResults, int firstResult) {
        return findStockProductoEntities(false, maxResults, firstResult);
    }

    private List<StockProducto> findStockProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(StockProducto.class));
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

    public StockProducto findStockProducto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(StockProducto.class, id);
        } finally {
            em.close();
        }
    }

    public int getStockProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<StockProducto> rt = cq.from(StockProducto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
