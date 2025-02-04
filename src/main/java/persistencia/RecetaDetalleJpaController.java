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
import logica.Receta;
import logica.RecetaDetalle;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author josepino
 */
public class RecetaDetalleJpaController implements Serializable {

    public RecetaDetalleJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public RecetaDetalleJpaController() {
        this.emf = Persistence.createEntityManagerFactory("persistenciaPU");
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RecetaDetalle recetaDetalle) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ingredientes idIngrediente = recetaDetalle.getIdIngrediente();
            if (idIngrediente != null) {
                idIngrediente = em.getReference(idIngrediente.getClass(), idIngrediente.getId());
                recetaDetalle.setIdIngrediente(idIngrediente);
            }
            Receta idReceta = recetaDetalle.getIdReceta();
            if (idReceta != null) {
                idReceta = em.getReference(idReceta.getClass(), idReceta.getId());
                recetaDetalle.setIdReceta(idReceta);
            }
            em.persist(recetaDetalle);
            if (idIngrediente != null) {
                idIngrediente.getRecetaDetalleList().add(recetaDetalle);
                idIngrediente = em.merge(idIngrediente);
            }
            if (idReceta != null) {
                idReceta.getRecetaDetalleList().add(recetaDetalle);
                idReceta = em.merge(idReceta);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RecetaDetalle recetaDetalle) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RecetaDetalle persistentRecetaDetalle = em.find(RecetaDetalle.class, recetaDetalle.getId());
            Ingredientes idIngredienteOld = persistentRecetaDetalle.getIdIngrediente();
            Ingredientes idIngredienteNew = recetaDetalle.getIdIngrediente();
            Receta idRecetaOld = persistentRecetaDetalle.getIdReceta();
            Receta idRecetaNew = recetaDetalle.getIdReceta();
            if (idIngredienteNew != null) {
                idIngredienteNew = em.getReference(idIngredienteNew.getClass(), idIngredienteNew.getId());
                recetaDetalle.setIdIngrediente(idIngredienteNew);
            }
            if (idRecetaNew != null) {
                idRecetaNew = em.getReference(idRecetaNew.getClass(), idRecetaNew.getId());
                recetaDetalle.setIdReceta(idRecetaNew);
            }
            recetaDetalle = em.merge(recetaDetalle);
            if (idIngredienteOld != null && !idIngredienteOld.equals(idIngredienteNew)) {
                idIngredienteOld.getRecetaDetalleList().remove(recetaDetalle);
                idIngredienteOld = em.merge(idIngredienteOld);
            }
            if (idIngredienteNew != null && !idIngredienteNew.equals(idIngredienteOld)) {
                idIngredienteNew.getRecetaDetalleList().add(recetaDetalle);
                idIngredienteNew = em.merge(idIngredienteNew);
            }
            if (idRecetaOld != null && !idRecetaOld.equals(idRecetaNew)) {
                idRecetaOld.getRecetaDetalleList().remove(recetaDetalle);
                idRecetaOld = em.merge(idRecetaOld);
            }
            if (idRecetaNew != null && !idRecetaNew.equals(idRecetaOld)) {
                idRecetaNew.getRecetaDetalleList().add(recetaDetalle);
                idRecetaNew = em.merge(idRecetaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = recetaDetalle.getId();
                if (findRecetaDetalle(id) == null) {
                    throw new NonexistentEntityException("The recetaDetalle with id " + id + " no longer exists.");
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
            RecetaDetalle recetaDetalle;
            try {
                recetaDetalle = em.getReference(RecetaDetalle.class, id);
                recetaDetalle.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The recetaDetalle with id " + id + " no longer exists.", enfe);
            }
            Ingredientes idIngrediente = recetaDetalle.getIdIngrediente();
            if (idIngrediente != null) {
                idIngrediente.getRecetaDetalleList().remove(recetaDetalle);
                idIngrediente = em.merge(idIngrediente);
            }
            Receta idReceta = recetaDetalle.getIdReceta();
            if (idReceta != null) {
                idReceta.getRecetaDetalleList().remove(recetaDetalle);
                idReceta = em.merge(idReceta);
            }
            em.remove(recetaDetalle);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RecetaDetalle> findRecetaDetalleEntities() {
        return findRecetaDetalleEntities(true, -1, -1);
    }

    public List<RecetaDetalle> findRecetaDetalleEntities(int maxResults, int firstResult) {
        return findRecetaDetalleEntities(false, maxResults, firstResult);
    }

    private List<RecetaDetalle> findRecetaDetalleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RecetaDetalle.class));
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

    public RecetaDetalle findRecetaDetalle(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RecetaDetalle.class, id);
        } finally {
            em.close();
        }
    }

    public int getRecetaDetalleCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RecetaDetalle> rt = cq.from(RecetaDetalle.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
