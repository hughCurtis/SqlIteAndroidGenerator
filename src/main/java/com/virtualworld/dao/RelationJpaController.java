/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.virtualworld.dao;

import com.virtualworld.dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.virtualworld.entities.Attribut;
import com.virtualworld.entities.Relation;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Ulrich TIAYO NGNINGAHE <tiayo.pro@gmail.com>
 */
@SuppressWarnings("UnusedAssignment")
public class RelationJpaController implements Serializable {
    
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.virtualworld_SqlIteAndroidGenerator_jar_1.0-SNAPSHOTPU");

    public RelationJpaController() {
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Relation relation) {
        if (relation.getAttributs() == null) {
            relation.setAttributs(new ArrayList<>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Attribut> attachedAttributs = new ArrayList<>();
            for (Attribut attributsAttributToAttach : relation.getAttributs()) {
                attributsAttributToAttach = em.getReference(attributsAttributToAttach.getClass(), attributsAttributToAttach.getId());
                attachedAttributs.add(attributsAttributToAttach);
            }
            relation.setAttributs(attachedAttributs);
            em.persist(relation);
            for (Attribut attributsAttribut : relation.getAttributs()) {
                Relation oldRelationOfAttributsAttribut = attributsAttribut.getRelation();
                attributsAttribut.setRelation(relation);
                attributsAttribut = em.merge(attributsAttribut);
                if (oldRelationOfAttributsAttribut != null) {
                    oldRelationOfAttributsAttribut.getAttributs().remove(attributsAttribut);
                    oldRelationOfAttributsAttribut = em.merge(oldRelationOfAttributsAttribut);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Relation relation) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Relation persistentRelation = em.find(Relation.class, relation.getId());
            List<Attribut> attributsOld = persistentRelation.getAttributs();
            List<Attribut> attributsNew = relation.getAttributs();
            List<Attribut> attachedAttributsNew = new ArrayList<>();
            for (Attribut attributsNewAttributToAttach : attributsNew) {
                attributsNewAttributToAttach = em.getReference(attributsNewAttributToAttach.getClass(), attributsNewAttributToAttach.getId());
                attachedAttributsNew.add(attributsNewAttributToAttach);
            }
            attributsNew = attachedAttributsNew;
            relation.setAttributs(attributsNew);
            relation = em.merge(relation);
            for (Attribut attributsOldAttribut : attributsOld) {
                if (!attributsNew.contains(attributsOldAttribut)) {
                    attributsOldAttribut.setRelation(null);
                    attributsOldAttribut = em.merge(attributsOldAttribut);
                }
            }
            for (Attribut attributsNewAttribut : attributsNew) {
                if (!attributsOld.contains(attributsNewAttribut)) {
                    Relation oldRelationOfAttributsNewAttribut = attributsNewAttribut.getRelation();
                    attributsNewAttribut.setRelation(relation);
                    attributsNewAttribut = em.merge(attributsNewAttribut);
                    if (oldRelationOfAttributsNewAttribut != null && !oldRelationOfAttributsNewAttribut.equals(relation)) {
                        oldRelationOfAttributsNewAttribut.getAttributs().remove(attributsNewAttribut);
                        oldRelationOfAttributsNewAttribut = em.merge(oldRelationOfAttributsNewAttribut);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = relation.getId();
                if (findRelation(id) == null) {
                    throw new NonexistentEntityException("The relation with id " + id + " no longer exists.");
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
            Relation relation;
            try {
                relation = em.getReference(Relation.class, id);
                relation.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The relation with id " + id + " no longer exists.", enfe);
            }
            List<Attribut> attributs = relation.getAttributs();
            for (Attribut attributsAttribut : attributs) {
                attributsAttribut.setRelation(null);
                attributsAttribut = em.merge(attributsAttribut);
            }
            em.remove(relation);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Relation> findRelationEntities() {
        return findRelationEntities(true, -1, -1);
    }

    public List<Relation> findRelationEntities(int maxResults, int firstResult) {
        return findRelationEntities(false, maxResults, firstResult);
    }

    private List<Relation> findRelationEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Relation.class));
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

    public Relation findRelation(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Relation.class, id);
        } finally {
            em.close();
        }
    }

    public int getRelationCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Relation> rt = cq.from(Relation.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
