package database;

import com.mysema.query.jpa.impl.JPAQuery;
import play.db.jpa.JPA;

import javax.persistence.EntityManager;

public class DB {

    public static JPAQuery query() {
        return new JPAQuery(JPA.em());
    }
    public static <T> T save(T item) {
        JPA.em().persist(item);
        return item;
    }


}
