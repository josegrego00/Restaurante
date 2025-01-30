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
import logica.ComboDetalle;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import logica.Combo;
import persistencia.exceptions.IllegalOrphanException;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author josepino
 */
public class ComboJpaController implements Serializable {

    public ComboJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Combo combo) {
        if (combo.getComboDetalleList() == null) {
            combo.setComboDetalleList(new ArrayList<ComboDetalle>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ComboDetalle> attachedComboDetalleList = new ArrayList<ComboDetalle>();
            for (ComboDetalle comboDetalleListComboDetalleToAttach : combo.getComboDetalleList()) {
                comboDetalleListComboDetalleToAttach = em.getReference(comboDetalleListComboDetalleToAttach.getClass(), comboDetalleListComboDetalleToAttach.getId());
                attachedComboDetalleList.add(comboDetalleListComboDetalleToAttach);
            }
            combo.setComboDetalleList(attachedComboDetalleList);
            em.persist(combo);
            for (ComboDetalle comboDetalleListComboDetalle : combo.getComboDetalleList()) {
                Combo oldIdComboOfComboDetalleListComboDetalle = comboDetalleListComboDetalle.getIdCombo();
                comboDetalleListComboDetalle.setIdCombo(combo);
                comboDetalleListComboDetalle = em.merge(comboDetalleListComboDetalle);
                if (oldIdComboOfComboDetalleListComboDetalle != null) {
                    oldIdComboOfComboDetalleListComboDetalle.getComboDetalleList().remove(comboDetalleListComboDetalle);
                    oldIdComboOfComboDetalleListComboDetalle = em.merge(oldIdComboOfComboDetalleListComboDetalle);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Combo combo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Combo persistentCombo = em.find(Combo.class, combo.getId());
            List<ComboDetalle> comboDetalleListOld = persistentCombo.getComboDetalleList();
            List<ComboDetalle> comboDetalleListNew = combo.getComboDetalleList();
            List<String> illegalOrphanMessages = null;
            for (ComboDetalle comboDetalleListOldComboDetalle : comboDetalleListOld) {
                if (!comboDetalleListNew.contains(comboDetalleListOldComboDetalle)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ComboDetalle " + comboDetalleListOldComboDetalle + " since its idCombo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<ComboDetalle> attachedComboDetalleListNew = new ArrayList<ComboDetalle>();
            for (ComboDetalle comboDetalleListNewComboDetalleToAttach : comboDetalleListNew) {
                comboDetalleListNewComboDetalleToAttach = em.getReference(comboDetalleListNewComboDetalleToAttach.getClass(), comboDetalleListNewComboDetalleToAttach.getId());
                attachedComboDetalleListNew.add(comboDetalleListNewComboDetalleToAttach);
            }
            comboDetalleListNew = attachedComboDetalleListNew;
            combo.setComboDetalleList(comboDetalleListNew);
            combo = em.merge(combo);
            for (ComboDetalle comboDetalleListNewComboDetalle : comboDetalleListNew) {
                if (!comboDetalleListOld.contains(comboDetalleListNewComboDetalle)) {
                    Combo oldIdComboOfComboDetalleListNewComboDetalle = comboDetalleListNewComboDetalle.getIdCombo();
                    comboDetalleListNewComboDetalle.setIdCombo(combo);
                    comboDetalleListNewComboDetalle = em.merge(comboDetalleListNewComboDetalle);
                    if (oldIdComboOfComboDetalleListNewComboDetalle != null && !oldIdComboOfComboDetalleListNewComboDetalle.equals(combo)) {
                        oldIdComboOfComboDetalleListNewComboDetalle.getComboDetalleList().remove(comboDetalleListNewComboDetalle);
                        oldIdComboOfComboDetalleListNewComboDetalle = em.merge(oldIdComboOfComboDetalleListNewComboDetalle);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = combo.getId();
                if (findCombo(id) == null) {
                    throw new NonexistentEntityException("The combo with id " + id + " no longer exists.");
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
            Combo combo;
            try {
                combo = em.getReference(Combo.class, id);
                combo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The combo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ComboDetalle> comboDetalleListOrphanCheck = combo.getComboDetalleList();
            for (ComboDetalle comboDetalleListOrphanCheckComboDetalle : comboDetalleListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Combo (" + combo + ") cannot be destroyed since the ComboDetalle " + comboDetalleListOrphanCheckComboDetalle + " in its comboDetalleList field has a non-nullable idCombo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(combo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Combo> findComboEntities() {
        return findComboEntities(true, -1, -1);
    }

    public List<Combo> findComboEntities(int maxResults, int firstResult) {
        return findComboEntities(false, maxResults, firstResult);
    }

    private List<Combo> findComboEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Combo.class));
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

    public Combo findCombo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Combo.class, id);
        } finally {
            em.close();
        }
    }

    public int getComboCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Combo> rt = cq.from(Combo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
