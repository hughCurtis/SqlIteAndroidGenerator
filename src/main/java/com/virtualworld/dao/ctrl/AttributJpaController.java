/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.virtualworld.dao.ctrl;

import com.virtualworld.dao.ctrl.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.virtualworld.dao.entities.Relation;
import com.virtualworld.dao.entities.Attribut;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Ulrich TIAYO NGNINGAHE <tiayo.pro@gmail.com>
 */
@SuppressWarnings("UnusedAssignment")
public class AttributJpaController implements Serializable {

    private static final Logger LOG = Logger.getLogger(AttributJpaController.class.getName());

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.virtualworld_SqlIteAndroidGenerator_jar_1.0-SNAPSHOTPU");

    public AttributJpaController() {
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Attribut attribut) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Relation relation = attribut.getRelation();
            if (relation != null) {
                relation = em.getReference(relation.getClass(), relation.getId());
                attribut.setRelation(relation);
            }
            Attribut fkOn = attribut.getFkOn();
            if (fkOn != null) {
                fkOn = em.getReference(fkOn.getClass(), fkOn.getId());
                attribut.setFkOn(fkOn);
            }
            em.persist(attribut);
            if (relation != null) {
                relation.getAttributs().add(attribut);
                relation = em.merge(relation);
            }
            if (fkOn != null) {
                Attribut oldFkOnOfFkOn = fkOn.getFkOn();
                if (oldFkOnOfFkOn != null) {
                    oldFkOnOfFkOn.setFkOn(null);
                    oldFkOnOfFkOn = em.merge(oldFkOnOfFkOn);
                }
                fkOn.setFkOn(attribut);
                fkOn = em.merge(fkOn);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Attribut attribut) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Attribut persistentAttribut = em.find(Attribut.class, attribut.getId());
            Relation relationOld = persistentAttribut.getRelation();
            Relation relationNew = attribut.getRelation();
            Attribut fkOnOld = persistentAttribut.getFkOn();
            Attribut fkOnNew = attribut.getFkOn();
            if (relationNew != null) {
                relationNew = em.getReference(relationNew.getClass(), relationNew.getId());
                attribut.setRelation(relationNew);
            }
            if (fkOnNew != null) {
                fkOnNew = em.getReference(fkOnNew.getClass(), fkOnNew.getId());
                attribut.setFkOn(fkOnNew);
            }
            attribut = em.merge(attribut);
            if (relationOld != null && !relationOld.equals(relationNew)) {
                relationOld.getAttributs().remove(attribut);
                relationOld = em.merge(relationOld);
            }
            if (relationNew != null && !relationNew.equals(relationOld)) {
                relationNew.getAttributs().add(attribut);
                relationNew = em.merge(relationNew);
            }
            if (fkOnOld != null && !fkOnOld.equals(fkOnNew)) {
                fkOnOld.setFkOn(null);
                fkOnOld = em.merge(fkOnOld);
            }
            if (fkOnNew != null && !fkOnNew.equals(fkOnOld)) {
                Attribut oldFkOnOfFkOn = fkOnNew.getFkOn();
                if (oldFkOnOfFkOn != null) {
                    oldFkOnOfFkOn.setFkOn(null);
                    oldFkOnOfFkOn = em.merge(oldFkOnOfFkOn);
                }
                fkOnNew.setFkOn(attribut);
                fkOnNew = em.merge(fkOnNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = attribut.getId();
                if (findAttribut(id) == null) {
                    throw new NonexistentEntityException("The attribut with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Attribut attribut;
            try {
                attribut = em.getReference(Attribut.class, id);
                attribut.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The attribut with id " + id + " no longer exists.", enfe);
            }
            Relation relation = attribut.getRelation();
            if (relation != null) {
                relation.getAttributs().remove(attribut);
                relation = em.merge(relation);
            }
            Attribut fkOn = attribut.getFkOn();
            if (fkOn != null) {
                fkOn.setFkOn(null);
                fkOn = em.merge(fkOn);
            }
            em.remove(attribut);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Attribut> findAttributEntities() {
        return findAttributEntities(true, -1, -1);
    }

    public List<Attribut> findAttributEntities(int maxResults, int firstResult) {
        return findAttributEntities(false, maxResults, firstResult);
    }

    private List<Attribut> findAttributEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Attribut.class));
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

    public Attribut findAttribut(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Attribut.class, id);
        } finally {
            em.close();
        }
    }

    public int getAttributCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Attribut> rt = cq.from(Attribut.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Attribut> getPKAttributes() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("SELECT a FROM Attribut a WHERE a.pk = TRUE");
            return q.getResultList();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, null, e);
            return new ArrayList<>();
        }
    }

    public List<Attribut> findAttributsByRelationId(int relationId) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("SELECT a FROM Attribut a WHERE a.relation.id = ?1");
            q.setParameter(1, relationId);
            return q.getResultList();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, null, e);
            return new ArrayList<>();
        }
    }

}
