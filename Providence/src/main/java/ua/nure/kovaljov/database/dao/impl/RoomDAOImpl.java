package ua.nure.kovaljov.database.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import ua.nure.kovaljov.database.dao.RoomDAO;
import ua.nure.kovaljov.entity.dbentity.Group;
import ua.nure.kovaljov.entity.dbentity.Room;
import ua.nure.kovaljov.entity.dbentity.Person;
import ua.nure.kovaljov.model.TransactionModel;
import ua.nure.kovaljov.utils.HibernateUtil;

public class RoomDAOImpl extends BaseCRUD implements RoomDAO {

	@Override
	public List<Room> getAllRooms() {
		Session session = null;
		List<Room> objects = new ArrayList<Room>();
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			objects = session.createCriteria(Room.class).list();
		} catch (Exception e) {
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return objects;
	}

	@Override
	public void updateRoom(Room room, int id) {
		Session session = null;
		Room oldValue = getRoomById(id);
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			SQLQuery insertQuery = session.createSQLQuery(
					"UPDATE `room` SET `room_ip`=?, `room_name`=?, "
					+ "`door_state`=? WHERE `room_id`=? and`room_ip`=?;");
			insertQuery.setParameter(0, room.getRoomIp());
			insertQuery.setParameter(1, room.getRoomName());
			insertQuery.setParameter(2, room.getDoorState());
			insertQuery.setParameter(3, id);
			insertQuery.setParameter(4, oldValue.getRoomIp());
			insertQuery.executeUpdate();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	@Override
	public Room getRoomById(int id) {
		Session session = null;
		Room room = new Room();
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			room = (Room) session.createCriteria(Room.class).add(Restrictions.eq("roomId", id)).list().get(0);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return room;
	}

	public void deleteRoom(Room room, int id) {
		super.deleteObject(id, "Room", "room_id");
	}

}
