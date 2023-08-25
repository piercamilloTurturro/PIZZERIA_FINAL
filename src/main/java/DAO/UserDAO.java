package DAO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import model.User;
import util.JPAUtil;

public class UserDAO {

    public User authenticateUser(String username, String password) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        List<User> listaUtenti = new ArrayList();
        try {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username = :username AND u.password = :password", User.class);
            query.setParameter("username", username);
            query.setParameter("password", password);
            listaUtenti = query.getResultList();
            return listaUtenti.isEmpty() ? null : listaUtenti.get(0);
        } catch (NoResultException e) {
            return null; // User not found or authentication failed
        } finally {
            em.close();
        }
    }

    // ... altre operazioni CRUD o metodi specifici

    public void closeEntityManagerFactory() {
        JPAUtil.shutdown();
    }
}
