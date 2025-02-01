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
import logica.Ingredientes;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import logica.UnidadMedida;
import persistencia.exceptions.IllegalOrphanException;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author josepino
 */
public class UnidadMedidaJpaController implements Serializable {

    public UnidadMedidaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public UnidadMedidaJpaController() {
    this.emf=Persistence.createEntityManagerFactory("persistenciaPU");
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UnidadMedida unidadMedida) {
        if (unidadMedida.getIngredientesList() == null) {
            unidadMedida.setIngredientesList(new ArrayList<Ingredientes>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Ingredientes> attachedIngredientesList = new ArrayList<Ingredientes>();
            for (Ingredientes ingredientesListIngredientesToAttach : unidadMedida.getIngredientesList()) {
                ingredientesListIngredientesToAttach = em.getReference(ingredientesListIngredientesToAttach.getClass(), ingredientesListIngredientesToAttach.getId());
                attachedIngredientesList.add(ingredientesListIngredientesToAttach);
            }
            unidadMedida.setIngredientesList(attachedIngredientesList);
            em.persist(unidadMedida);
            for (Ingredientes ingredientesListIngredientes : unidadMedida.getIngredientesList()) {
                UnidadMedida oldIdUnidadMedidaOfIngredientesListIngredientes = ingredientesListIngredientes.getIdUnidadMedida();
                ingredientesListIngredientes.setIdUnidadMedida(unidadMedida);
                ingredientesListIngredientes = em.merge(ingredientesListIngredientes);
                if (oldIdUnidadMedidaOfIngredientesListIngredientes != null) {
                    oldIdUnidadMedidaOfIngredientesListIngredientes.getIngredientesList().remove(ingredientesListIngredientes);
                    oldIdUnidadMedidaOfIngredientesListIngredientes = em.merge(oldIdUnidadMedidaOfIngredientesListIngredientes);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UnidadMedida unidadMedida) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UnidadMedida persistentUnidadMedida = em.find(UnidadMedida.class, unidadMedida.getId());
            List<Ingredientes> ingredientesListOld = persistentUnidadMedida.getIngredientesList();
            List<Ingredientes> ingredientesListNew = unidadMedida.getIngredientesList();
            List<String> illegalOrphanMessages = null;
            for (Ingredientes ingredientesListOldIngredientes : ingredientesListOld) {
                if (!ingredientesListNew.contains(ingredientesListOldIngredientes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ingredientes " + ingredientesListOldIngredientes + " since its idUnidadMedida field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Ingredientes> attachedIngredientesListNew = new ArrayList<Ingredientes>();
            for (Ingredientes ingredientesListNewIngredientesToAttach : ingredientesListNew) {
                ingredientesListNewIngredientesToAttach = em.getReference(ingredientesListNewIngredientesToAttach.getClass(), ingredientesListNewIngredientesToAttach.getId());
                attachedIngredientesListNew.add(ingredientesListNewIngredientesToAttach);
            }
            ingredientesListNew = attachedIngredientesListNew;
            unidadMedida.setIngredientesList(ingredientesListNew);
            unidadMedida = em.merge(unidadMedida);
            for (Ingredientes ingredientesListNewIngredientes : ingredientesListNew) {
                if (!ingredientesListOld.contains(ingredientesListNewIngredientes)) {
                    UnidadMedida oldIdUnidadMedidaOfIngredientesListNewIngredientes = ingredientesListNewIngredientes.getIdUnidadMedida();
                    ingredientesListNewIngredientes.setIdUnidadMedida(unidadMedida);
                    ingredientesListNewIngredientes = em.merge(ingredientesListNewIngredientes);
                    if (oldIdUnidadMedidaOfIngredientesListNewIngredientes != null && !oldIdUnidadMedidaOfIngredientesListNewIngredientes.equals(unidadMedida)) {
                        oldIdUnidadMedidaOfIngredientesListNewIngredientes.getIngredientesList().remove(ingredientesListNewIngredientes);
                        oldIdUnidadMedidaOfIngredientesListNewIngredientes = em.merge(oldIdUnidadMedidaOfIngredientesListNewIngredientes);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = unidadMedida.getId();
                if (findUnidadMedida(id) == null) {
                    throw new NonexistentEntityException("The unidadMedida with id " + id + " no longer exists.");
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
            UnidadMedida unidadMedida;
            try {
                unidadMedida = em.getReference(UnidadMedida.class, id);
                unidadMedida.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The unidadMedida with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Ingredientes> ingredientesListOrphanCheck = unidadMedida.getIngredientesList();
            for (Ingredientes ingredientesListOrphanCheckIngredientes : ingredientesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This UnidadMedida (" + unidadMedida + ") cannot be destroyed since the Ingredientes " + ingredientesListOrphanCheckIngredientes + " in its ingredientesList field has a non-nullable idUnidadMedida field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(unidadMedida);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UnidadMedida> findUnidadMedidaEntities() {
        return findUnidadMedidaEntities(true, -1, -1);
    }

    public List<UnidadMedida> findUnidadMedidaEntities(int maxResults, int firstResult) {
        return findUnidadMedidaEntities(false, maxResults, firstResult);
    }

    private List<UnidadMedida> findUnidadMedidaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UnidadMedida.class));
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

    public UnidadMedida findUnidadMedida(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UnidadMedida.class, id);
        } finally {
            em.close();
        }
    }

    public int getUnidadMedidaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UnidadMedida> rt = cq.from(UnidadMedida.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
