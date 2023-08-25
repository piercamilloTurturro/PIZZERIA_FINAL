package DAO;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.hibernate.Hibernate;

import model.Pizza;
import util.JPAUtil;

import java.util.List;

public class PizzaDAO {

    public List<Pizza> getPizzasByUserId(Long userId) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            String jpql = "SELECT p FROM Pizza p WHERE p.user.id = :userId";
            TypedQuery<Pizza> query = entityManager.createQuery(jpql, Pizza.class);
            query.setParameter("userId", userId);
            List<Pizza> userPizzas = query.getResultList();
            for(Pizza pizza : userPizzas) {
      	      Hibernate.initialize(pizza.getIngredients());
      	    }
            return userPizzas;
        } finally {
            entityManager.close();
        }
    }



	  public void savePizza(Pizza pizza) {
		  EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
		try {
		    em.getTransaction().begin();
		    em.persist(pizza);  
		    em.getTransaction().commit();
        } finally {
		    em.close();
        }
	  }

    
    public void closeEntityManagerFactory() {
        JPAUtil.shutdown();
    }
    // Altri metodi DAO per le operazioni con le pizze
    // ...
}


