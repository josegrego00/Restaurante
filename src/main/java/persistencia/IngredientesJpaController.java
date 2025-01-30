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
import logica.UnidadMedida;
import logica.RecetaDetalle;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import logica.Ingredientes;
import logica.StockIngredientes;
import persistencia.exceptions.IllegalOrphanException;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author josepino
 */
public class IngredientesJpaController implements Serializable {

    public IngredientesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public IngredientesJpaController() {
    this.emf=Persistence.createEntityManagerFactory("persistenciaPU");
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ingredientes ingredientes) {
        if (ingredientes.getRecetaDetalleList() == null) {
            ingredientes.setRecetaDetalleList(new ArrayList<RecetaDetalle>());
        }
        if (ingredientes.getStockIngredientesList() == null) {
            ingredientes.setStockIngredientesList(new ArrayList<StockIngredientes>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UnidadMedida idUnidadMedida = ingredientes.getIdUnidadMedida();
            if (idUnidadMedida != null) {
                idUnidadMedida = em.getReference(idUnidadMedida.getClass(), idUnidadMedida.getId());
                ingredientes.setIdUnidadMedida(idUnidadMedida);
            }
            List<RecetaDetalle> attachedRecetaDetalleList = new ArrayList<RecetaDetalle>();
            for (RecetaDetalle recetaDetalleListRecetaDetalleToAttach : ingredientes.getRecetaDetalleList()) {
                recetaDetalleListRecetaDetalleToAttach = em.getReference(recetaDetalleListRecetaDetalleToAttach.getClass(), recetaDetalleListRecetaDetalleToAttach.getId());
                attachedRecetaDetalleList.add(recetaDetalleListRecetaDetalleToAttach);
            }
            ingredientes.setRecetaDetalleList(attachedRecetaDetalleList);
            List<StockIngredientes> attachedStockIngredientesList = new ArrayList<StockIngredientes>();
            for (StockIngredientes stockIngredientesListStockIngredientesToAttach : ingredientes.getStockIngredientesList()) {
                stockIngredientesListStockIngredientesToAttach = em.getReference(stockIngredientesListStockIngredientesToAttach.getClass(), stockIngredientesListStockIngredientesToAttach.getId());
                attachedStockIngredientesList.add(stockIngredientesListStockIngredientesToAttach);
            }
            ingredientes.setStockIngredientesList(attachedStockIngredientesList);
            em.persist(ingredientes);
            if (idUnidadMedida != null) {
                idUnidadMedida.getIngredientesList().add(ingredientes);
                idUnidadMedida = em.merge(idUnidadMedida);
            }
            for (RecetaDetalle recetaDetalleListRecetaDetalle : ingredientes.getRecetaDetalleList()) {
                Ingredientes oldIdIngredienteOfRecetaDetalleListRecetaDetalle = recetaDetalleListRecetaDetalle.getIdIngrediente();
                recetaDetalleListRecetaDetalle.setIdIngrediente(ingredientes);
                recetaDetalleListRecetaDetalle = em.merge(recetaDetalleListRecetaDetalle);
                if (oldIdIngredienteOfRecetaDetalleListRecetaDetalle != null) {
                    oldIdIngredienteOfRecetaDetalleListRecetaDetalle.getRecetaDetalleList().remove(recetaDetalleListRecetaDetalle);
                    oldIdIngredienteOfRecetaDetalleListRecetaDetalle = em.merge(oldIdIngredienteOfRecetaDetalleListRecetaDetalle);
                }
            }
            for (StockIngredientes stockIngredientesListStockIngredientes : ingredientes.getStockIngredientesList()) {
                Ingredientes oldIdIngredienteOfStockIngredientesListStockIngredientes = stockIngredientesListStockIngredientes.getIdIngrediente();
                stockIngredientesListStockIngredientes.setIdIngrediente(ingredientes);
                stockIngredientesListStockIngredientes = em.merge(stockIngredientesListStockIngredientes);
                if (oldIdIngredienteOfStockIngredientesListStockIngredientes != null) {
                    oldIdIngredienteOfStockIngredientesListStockIngredientes.getStockIngredientesList().remove(stockIngredientesListStockIngredientes);
                    oldIdIngredienteOfStockIngredientesListStockIngredientes = em.merge(oldIdIngredienteOfStockIngredientesListStockIngredientes);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ingredientes ingredientes) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ingredientes persistentIngredientes = em.find(Ingredientes.class, ingredientes.getId());
            UnidadMedida idUnidadMedidaOld = persistentIngredientes.getIdUnidadMedida();
            UnidadMedida idUnidadMedidaNew = ingredientes.getIdUnidadMedida();
            List<RecetaDetalle> recetaDetalleListOld = persistentIngredientes.getRecetaDetalleList();
            List<RecetaDetalle> recetaDetalleListNew = ingredientes.getRecetaDetalleList();
            List<StockIngredientes> stockIngredientesListOld = persistentIngredientes.getStockIngredientesList();
            List<StockIngredientes> stockIngredientesListNew = ingredientes.getStockIngredientesList();
            List<String> illegalOrphanMessages = null;
            for (RecetaDetalle recetaDetalleListOldRecetaDetalle : recetaDetalleListOld) {
                if (!recetaDetalleListNew.contains(recetaDetalleListOldRecetaDetalle)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RecetaDetalle " + recetaDetalleListOldRecetaDetalle + " since its idIngrediente field is not nullable.");
                }
            }
            for (StockIngredientes stockIngredientesListOldStockIngredientes : stockIngredientesListOld) {
                if (!stockIngredientesListNew.contains(stockIngredientesListOldStockIngredientes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain StockIngredientes " + stockIngredientesListOldStockIngredientes + " since its idIngrediente field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idUnidadMedidaNew != null) {
                idUnidadMedidaNew = em.getReference(idUnidadMedidaNew.getClass(), idUnidadMedidaNew.getId());
                ingredientes.setIdUnidadMedida(idUnidadMedidaNew);
            }
            List<RecetaDetalle> attachedRecetaDetalleListNew = new ArrayList<RecetaDetalle>();
            for (RecetaDetalle recetaDetalleListNewRecetaDetalleToAttach : recetaDetalleListNew) {
                recetaDetalleListNewRecetaDetalleToAttach = em.getReference(recetaDetalleListNewRecetaDetalleToAttach.getClass(), recetaDetalleListNewRecetaDetalleToAttach.getId());
                attachedRecetaDetalleListNew.add(recetaDetalleListNewRecetaDetalleToAttach);
            }
            recetaDetalleListNew = attachedRecetaDetalleListNew;
            ingredientes.setRecetaDetalleList(recetaDetalleListNew);
            List<StockIngredientes> attachedStockIngredientesListNew = new ArrayList<StockIngredientes>();
            for (StockIngredientes stockIngredientesListNewStockIngredientesToAttach : stockIngredientesListNew) {
                stockIngredientesListNewStockIngredientesToAttach = em.getReference(stockIngredientesListNewStockIngredientesToAttach.getClass(), stockIngredientesListNewStockIngredientesToAttach.getId());
                attachedStockIngredientesListNew.add(stockIngredientesListNewStockIngredientesToAttach);
            }
            stockIngredientesListNew = attachedStockIngredientesListNew;
            ingredientes.setStockIngredientesList(stockIngredientesListNew);
            ingredientes = em.merge(ingredientes);
            if (idUnidadMedidaOld != null && !idUnidadMedidaOld.equals(idUnidadMedidaNew)) {
                idUnidadMedidaOld.getIngredientesList().remove(ingredientes);
                idUnidadMedidaOld = em.merge(idUnidadMedidaOld);
            }
            if (idUnidadMedidaNew != null && !idUnidadMedidaNew.equals(idUnidadMedidaOld)) {
                idUnidadMedidaNew.getIngredientesList().add(ingredientes);
                idUnidadMedidaNew = em.merge(idUnidadMedidaNew);
            }
            for (RecetaDetalle recetaDetalleListNewRecetaDetalle : recetaDetalleListNew) {
                if (!recetaDetalleListOld.contains(recetaDetalleListNewRecetaDetalle)) {
                    Ingredientes oldIdIngredienteOfRecetaDetalleListNewRecetaDetalle = recetaDetalleListNewRecetaDetalle.getIdIngrediente();
                    recetaDetalleListNewRecetaDetalle.setIdIngrediente(ingredientes);
                    recetaDetalleListNewRecetaDetalle = em.merge(recetaDetalleListNewRecetaDetalle);
                    if (oldIdIngredienteOfRecetaDetalleListNewRecetaDetalle != null && !oldIdIngredienteOfRecetaDetalleListNewRecetaDetalle.equals(ingredientes)) {
                        oldIdIngredienteOfRecetaDetalleListNewRecetaDetalle.getRecetaDetalleList().remove(recetaDetalleListNewRecetaDetalle);
                        oldIdIngredienteOfRecetaDetalleListNewRecetaDetalle = em.merge(oldIdIngredienteOfRecetaDetalleListNewRecetaDetalle);
                    }
                }
            }
            for (StockIngredientes stockIngredientesListNewStockIngredientes : stockIngredientesListNew) {
                if (!stockIngredientesListOld.contains(stockIngredientesListNewStockIngredientes)) {
                    Ingredientes oldIdIngredienteOfStockIngredientesListNewStockIngredientes = stockIngredientesListNewStockIngredientes.getIdIngrediente();
                    stockIngredientesListNewStockIngredientes.setIdIngrediente(ingredientes);
                    stockIngredientesListNewStockIngredientes = em.merge(stockIngredientesListNewStockIngredientes);
                    if (oldIdIngredienteOfStockIngredientesListNewStockIngredientes != null && !oldIdIngredienteOfStockIngredientesListNewStockIngredientes.equals(ingredientes)) {
                        oldIdIngredienteOfStockIngredientesListNewStockIngredientes.getStockIngredientesList().remove(stockIngredientesListNewStockIngredientes);
                        oldIdIngredienteOfStockIngredientesListNewStockIngredientes = em.merge(oldIdIngredienteOfStockIngredientesListNewStockIngredientes);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ingredientes.getId();
                if (findIngredientes(id) == null) {
                    throw new NonexistentEntityException("The ingredientes with id " + id + " no longer exists.");
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
            Ingredientes ingredientes;
            try {
                ingredientes = em.getReference(Ingredientes.class, id);
                ingredientes.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ingredientes with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<RecetaDetalle> recetaDetalleListOrphanCheck = ingredientes.getRecetaDetalleList();
            for (RecetaDetalle recetaDetalleListOrphanCheckRecetaDetalle : recetaDetalleListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ingredientes (" + ingredientes + ") cannot be destroyed since the RecetaDetalle " + recetaDetalleListOrphanCheckRecetaDetalle + " in its recetaDetalleList field has a non-nullable idIngrediente field.");
            }
            List<StockIngredientes> stockIngredientesListOrphanCheck = ingredientes.getStockIngredientesList();
            for (StockIngredientes stockIngredientesListOrphanCheckStockIngredientes : stockIngredientesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ingredientes (" + ingredientes + ") cannot be destroyed since the StockIngredientes " + stockIngredientesListOrphanCheckStockIngredientes + " in its stockIngredientesList field has a non-nullable idIngrediente field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            UnidadMedida idUnidadMedida = ingredientes.getIdUnidadMedida();
            if (idUnidadMedida != null) {
                idUnidadMedida.getIngredientesList().remove(ingredientes);
                idUnidadMedida = em.merge(idUnidadMedida);
            }
            em.remove(ingredientes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ingredientes> findIngredientesEntities() {
        return findIngredientesEntities(true, -1, -1);
    }

    public List<Ingredientes> findIngredientesEntities(int maxResults, int firstResult) {
        return findIngredientesEntities(false, maxResults, firstResult);
    }

    private List<Ingredientes> findIngredientesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ingredientes.class));
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

    public Ingredientes findIngredientes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ingredientes.class, id);
        } finally {
            em.close();
        }
    }

    public int getIngredientesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ingredientes> rt = cq.from(Ingredientes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
