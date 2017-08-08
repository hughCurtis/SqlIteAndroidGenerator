/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.virtualworld.dao.ctrl;

import com.virtualworld.dao.ctrl.exceptions.NonexistentEntityException;
import com.virtualworld.dao.entities.DataBase;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Ulrich TIAYO NGNINGAHE <tiayo.pro@gmail.com>
 */
public class DataBaseJpaController implements Serializable {

    private static final Logger LOG = Logger.getLogger(DataBaseJpaController.class.getName());

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.virtualworld_SqlIteAndroidGenerator_jar_1.0-SNAPSHOTPU");

    public DataBaseJpaController() {
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DataBase dataBase) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(dataBase);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DataBase dataBase) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            dataBase = em.merge(dataBase);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = dataBase.getId();
                if (findDataBase(id) == null) {
                    throw new NonexistentEntityException("The dataBase with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Short id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DataBase dataBase;
            try {
                dataBase = em.getReference(DataBase.class, id);
                dataBase.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The dataBase with id " + id + " no longer exists.", enfe);
            }
            em.remove(dataBase);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DataBase> findDataBaseEntities() {
        return findDataBaseEntities(true, -1, -1);
    }

    public List<DataBase> findDataBaseEntities(int maxResults, int firstResult) {
        return findDataBaseEntities(false, maxResults, firstResult);
    }

    private List<DataBase> findDataBaseEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DataBase.class));
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

    public DataBase findDataBase(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DataBase.class, id);
        } finally {
            em.close();
        }
    }

    public int getDataBaseCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DataBase> rt = cq.from(DataBase.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    /**
     * La casse n'est pas prise en compte
     *
     * @param dbName
     * @return
     */
    public DataBase findByName(String dbName) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("SELECT d FROM DataBase d WHERE UPPER(d.databaseName) = ?1");
            q.setParameter(1, dbName.toUpperCase());
            return (DataBase) q.getSingleResult();
        } catch (Exception e) {
            LOG.log(Level.INFO, "Recherche de BD par le nom{0}", e.getMessage());
            return null;
        }
    }

}
