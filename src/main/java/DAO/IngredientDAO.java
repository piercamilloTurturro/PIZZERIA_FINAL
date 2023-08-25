package DAO;

import util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import model.Ingredient;

import java.util.List;

public class IngredientDAO {

    public Ingredient getIngredientById(Long id) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return entityManager.find(Ingredient.class, id);
        } finally {
            entityManager.close();
        }
    }

    public static List<Ingredient> getAllIngredients() {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<Ingredient> query = entityManager.createQuery("SELECT i FROM Ingredient i", Ingredient.class);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }
    
    public int getNumTot() {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(i) FROM Ingredient i", Long.class);
            Long count = query.getSingleResult();
            return count.intValue();
        } finally {
            entityManager.close();
        }
    	
    }

    public void closeEntityManagerFactory() {
        JPAUtil.shutdown();
    }
    // Altri metodi del DAO per inserire, aggiornare o eliminare ingredienti
}
