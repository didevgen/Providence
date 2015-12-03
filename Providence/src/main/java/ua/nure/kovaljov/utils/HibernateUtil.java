package ua.nure.kovaljov.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * класс HibernateUtil, который будет отвечать за обработку данного xml файла и
 * установление соединения с нашей базой данных
 * 
 * @author Евгений
 *
 */
@SuppressWarnings("deprecation")
public class HibernateUtil {
	private static SessionFactory sessionFactory = null;

	static {
		try {
			sessionFactory = new Configuration().configure().buildSessionFactory();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
