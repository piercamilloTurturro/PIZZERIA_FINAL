package DAO;

import util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import model.Dough;

import java.util.List;

public class DoughDAO {

    public Dough getDoughById(Long id) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return entityManager.find(Dough.class, id);
        } finally {
            entityManager.close();
        }
    }

    public static List<Dough> getAllDoughs() {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<Dough> query = entityManager.createQuery("SELECT d FROM Dough d", Dough.class);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    public void closeEntityManagerFactory() {
        JPAUtil.shutdown();
    }
    // Altri metodi del DAO per inserire, aggiornare o eliminare impasti
}
