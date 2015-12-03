package ua.nure.kovaljov.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * ����� HibernateUtil, ������� ����� �������� �� ��������� ������� xml ����� �
 * ������������ ���������� � ����� ����� ������
 * 
 * @author �������
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
