/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import logica.StockProducto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import logica.Producto;
import persistencia.exceptions.IllegalOrphanException;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author josepino
 */
public class ProductoJpaController implements Serializable {

    public ProductoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Producto producto) {
        if (producto.getStockProductoList() == null) {
            producto.setStockProductoList(new ArrayList<StockProducto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<StockProducto> attachedStockProductoList = new ArrayList<StockProducto>();
            for (StockProducto stockProductoListStockProductoToAttach : producto.getStockProductoList()) {
                stockProductoListStockProductoToAttach = em.getReference(stockProductoListStockProductoToAttach.getClass(), stockProductoListStockProductoToAttach.getId());
                attachedStockProductoList.add(stockProductoListStockProductoToAttach);
            }
            producto.setStockProductoList(attachedStockProductoList);
            em.persist(producto);
            for (StockProducto stockProductoListStockProducto : producto.getStockProductoList()) {
                Producto oldIdProductoOfStockProductoListStockProducto = stockProductoListStockProducto.getIdProducto();
                stockProductoListStockProducto.setIdProducto(producto);
                stockProductoListStockProducto = em.merge(stockProductoListStockProducto);
                if (oldIdProductoOfStockProductoListStockProducto != null) {
                    oldIdProductoOfStockProductoListStockProducto.getStockProductoList().remove(stockProductoListStockProducto);
                    oldIdProductoOfStockProductoListStockProducto = em.merge(oldIdProductoOfStockProductoListStockProducto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Producto producto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto persistentProducto = em.find(Producto.class, producto.getId());
            List<StockProducto> stockProductoListOld = persistentProducto.getStockProductoList();
            List<StockProducto> stockProductoListNew = producto.getStockProductoList();
            List<String> illegalOrphanMessages = null;
            for (StockProducto stockProductoListOldStockProducto : stockProductoListOld) {
                if (!stockProductoListNew.contains(stockProductoListOldStockProducto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain StockProducto " + stockProductoListOldStockProducto + " since its idProducto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<StockProducto> attachedStockProductoListNew = new ArrayList<StockProducto>();
            for (StockProducto stockProductoListNewStockProductoToAttach : stockProductoListNew) {
                stockProductoListNewStockProductoToAttach = em.getReference(stockProductoListNewStockProductoToAttach.getClass(), stockProductoListNewStockProductoToAttach.getId());
                attachedStockProductoListNew.add(stockProductoListNewStockProductoToAttach);
            }
            stockProductoListNew = attachedStockProductoListNew;
            producto.setStockProductoList(stockProductoListNew);
            producto = em.merge(producto);
            for (StockProducto stockProductoListNewStockProducto : stockProductoListNew) {
                if (!stockProductoListOld.contains(stockProductoListNewStockProducto)) {
                    Producto oldIdProductoOfStockProductoListNewStockProducto = stockProductoListNewStockProducto.getIdProducto();
                    stockProductoListNewStockProducto.setIdProducto(producto);
                    stockProductoListNewStockProducto = em.merge(stockProductoListNewStockProducto);
                    if (oldIdProductoOfStockProductoListNewStockProducto != null && !oldIdProductoOfStockProductoListNewStockProducto.equals(producto)) {
                        oldIdProductoOfStockProductoListNewStockProducto.getStockProductoList().remove(stockProductoListNewStockProducto);
                        oldIdProductoOfStockProductoListNewStockProducto = em.merge(oldIdProductoOfStockProductoListNewStockProducto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = producto.getId();
                if (findProducto(id) == null) {
                    throw new NonexistentEntityException("The producto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto producto;
            try {
                producto = em.getReference(Producto.class, id);
                producto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The producto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<StockProducto> stockProductoListOrphanCheck = producto.getStockProductoList();
            for (StockProducto stockProductoListOrphanCheckStockProducto : stockProductoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the StockProducto " + stockProductoListOrphanCheckStockProducto + " in its stockProductoList field has a non-nullable idProducto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(producto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Producto> findProductoEntities() {
        return findProductoEntities(true, -1, -1);
    }

    public List<Producto> findProductoEntities(int maxResults, int firstResult) {
        return findProductoEntities(false, maxResults, firstResult);
    }

    private List<Producto> findProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Producto.class));
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

    public Producto findProducto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Producto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Producto> rt = cq.from(Producto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
