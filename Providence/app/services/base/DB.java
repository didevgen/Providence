package services.base;

import com.mysema.query.jpa.impl.JPAQuery;
import play.db.jpa.JPA;

import javax.persistence.EntityManager;

public class DB {
    private static EntityManager em = JPA.em();

    public static JPAQuery query() {
        return new JPAQuery(em);
    }
    public static EntityManager getEM() {
        return em;
    }
    public static<T> T save(T item) {
        em.getTransaction().begin();
        em.persist(item);
        em.getTransaction().commit();
        return item;
    }


}
