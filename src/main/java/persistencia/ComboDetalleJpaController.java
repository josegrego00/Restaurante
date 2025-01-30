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
import logica.Combo;
import logica.ComboDetalle;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author josepino
 */
public class ComboDetalleJpaController implements Serializable {

    public ComboDetalleJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ComboDetalle comboDetalle) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Combo idCombo = comboDetalle.getIdCombo();
            if (idCombo != null) {
                idCombo = em.getReference(idCombo.getClass(), idCombo.getId());
                comboDetalle.setIdCombo(idCombo);
            }
            em.persist(comboDetalle);
            if (idCombo != null) {
                idCombo.getComboDetalleList().add(comboDetalle);
                idCombo = em.merge(idCombo);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ComboDetalle comboDetalle) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ComboDetalle persistentComboDetalle = em.find(ComboDetalle.class, comboDetalle.getId());
            Combo idComboOld = persistentComboDetalle.getIdCombo();
            Combo idComboNew = comboDetalle.getIdCombo();
            if (idComboNew != null) {
                idComboNew = em.getReference(idComboNew.getClass(), idComboNew.getId());
                comboDetalle.setIdCombo(idComboNew);
            }
            comboDetalle = em.merge(comboDetalle);
            if (idComboOld != null && !idComboOld.equals(idComboNew)) {
                idComboOld.getComboDetalleList().remove(comboDetalle);
                idComboOld = em.merge(idComboOld);
            }
            if (idComboNew != null && !idComboNew.equals(idComboOld)) {
                idComboNew.getComboDetalleList().add(comboDetalle);
                idComboNew = em.merge(idComboNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = comboDetalle.getId();
                if (findComboDetalle(id) == null) {
                    throw new NonexistentEntityException("The comboDetalle with id " + id + " no longer exists.");
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
            ComboDetalle comboDetalle;
            try {
                comboDetalle = em.getReference(ComboDetalle.class, id);
                comboDetalle.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comboDetalle with id " + id + " no longer exists.", enfe);
            }
            Combo idCombo = comboDetalle.getIdCombo();
            if (idCombo != null) {
                idCombo.getComboDetalleList().remove(comboDetalle);
                idCombo = em.merge(idCombo);
            }
            em.remove(comboDetalle);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ComboDetalle> findComboDetalleEntities() {
        return findComboDetalleEntities(true, -1, -1);
    }

    public List<ComboDetalle> findComboDetalleEntities(int maxResults, int firstResult) {
        return findComboDetalleEntities(false, maxResults, firstResult);
    }

    private List<ComboDetalle> findComboDetalleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ComboDetalle.class));
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

    public ComboDetalle findComboDetalle(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ComboDetalle.class, id);
        } finally {
            em.close();
        }
    }

    public int getComboDetalleCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ComboDetalle> rt = cq.from(ComboDetalle.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
