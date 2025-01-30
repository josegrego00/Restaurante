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
import logica.RecetaDetalle;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import logica.Receta;
import persistencia.exceptions.IllegalOrphanException;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author josepino
 */
public class RecetaJpaController implements Serializable {

    public RecetaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Receta receta) {
        if (receta.getRecetaDetalleList() == null) {
            receta.setRecetaDetalleList(new ArrayList<RecetaDetalle>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<RecetaDetalle> attachedRecetaDetalleList = new ArrayList<RecetaDetalle>();
            for (RecetaDetalle recetaDetalleListRecetaDetalleToAttach : receta.getRecetaDetalleList()) {
                recetaDetalleListRecetaDetalleToAttach = em.getReference(recetaDetalleListRecetaDetalleToAttach.getClass(), recetaDetalleListRecetaDetalleToAttach.getId());
                attachedRecetaDetalleList.add(recetaDetalleListRecetaDetalleToAttach);
            }
            receta.setRecetaDetalleList(attachedRecetaDetalleList);
            em.persist(receta);
            for (RecetaDetalle recetaDetalleListRecetaDetalle : receta.getRecetaDetalleList()) {
                Receta oldIdRecetaOfRecetaDetalleListRecetaDetalle = recetaDetalleListRecetaDetalle.getIdReceta();
                recetaDetalleListRecetaDetalle.setIdReceta(receta);
                recetaDetalleListRecetaDetalle = em.merge(recetaDetalleListRecetaDetalle);
                if (oldIdRecetaOfRecetaDetalleListRecetaDetalle != null) {
                    oldIdRecetaOfRecetaDetalleListRecetaDetalle.getRecetaDetalleList().remove(recetaDetalleListRecetaDetalle);
                    oldIdRecetaOfRecetaDetalleListRecetaDetalle = em.merge(oldIdRecetaOfRecetaDetalleListRecetaDetalle);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Receta receta) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Receta persistentReceta = em.find(Receta.class, receta.getId());
            List<RecetaDetalle> recetaDetalleListOld = persistentReceta.getRecetaDetalleList();
            List<RecetaDetalle> recetaDetalleListNew = receta.getRecetaDetalleList();
            List<String> illegalOrphanMessages = null;
            for (RecetaDetalle recetaDetalleListOldRecetaDetalle : recetaDetalleListOld) {
                if (!recetaDetalleListNew.contains(recetaDetalleListOldRecetaDetalle)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RecetaDetalle " + recetaDetalleListOldRecetaDetalle + " since its idReceta field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<RecetaDetalle> attachedRecetaDetalleListNew = new ArrayList<RecetaDetalle>();
            for (RecetaDetalle recetaDetalleListNewRecetaDetalleToAttach : recetaDetalleListNew) {
                recetaDetalleListNewRecetaDetalleToAttach = em.getReference(recetaDetalleListNewRecetaDetalleToAttach.getClass(), recetaDetalleListNewRecetaDetalleToAttach.getId());
                attachedRecetaDetalleListNew.add(recetaDetalleListNewRecetaDetalleToAttach);
            }
            recetaDetalleListNew = attachedRecetaDetalleListNew;
            receta.setRecetaDetalleList(recetaDetalleListNew);
            receta = em.merge(receta);
            for (RecetaDetalle recetaDetalleListNewRecetaDetalle : recetaDetalleListNew) {
                if (!recetaDetalleListOld.contains(recetaDetalleListNewRecetaDetalle)) {
                    Receta oldIdRecetaOfRecetaDetalleListNewRecetaDetalle = recetaDetalleListNewRecetaDetalle.getIdReceta();
                    recetaDetalleListNewRecetaDetalle.setIdReceta(receta);
                    recetaDetalleListNewRecetaDetalle = em.merge(recetaDetalleListNewRecetaDetalle);
                    if (oldIdRecetaOfRecetaDetalleListNewRecetaDetalle != null && !oldIdRecetaOfRecetaDetalleListNewRecetaDetalle.equals(receta)) {
                        oldIdRecetaOfRecetaDetalleListNewRecetaDetalle.getRecetaDetalleList().remove(recetaDetalleListNewRecetaDetalle);
                        oldIdRecetaOfRecetaDetalleListNewRecetaDetalle = em.merge(oldIdRecetaOfRecetaDetalleListNewRecetaDetalle);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = receta.getId();
                if (findReceta(id) == null) {
                    throw new NonexistentEntityException("The receta with id " + id + " no longer exists.");
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
            Receta receta;
            try {
                receta = em.getReference(Receta.class, id);
                receta.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The receta with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<RecetaDetalle> recetaDetalleListOrphanCheck = receta.getRecetaDetalleList();
            for (RecetaDetalle recetaDetalleListOrphanCheckRecetaDetalle : recetaDetalleListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Receta (" + receta + ") cannot be destroyed since the RecetaDetalle " + recetaDetalleListOrphanCheckRecetaDetalle + " in its recetaDetalleList field has a non-nullable idReceta field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(receta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Receta> findRecetaEntities() {
        return findRecetaEntities(true, -1, -1);
    }

    public List<Receta> findRecetaEntities(int maxResults, int firstResult) {
        return findRecetaEntities(false, maxResults, firstResult);
    }

    private List<Receta> findRecetaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Receta.class));
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

    public Receta findReceta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Receta.class, id);
        } finally {
            em.close();
        }
    }

    public int getRecetaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Receta> rt = cq.from(Receta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
