package ua.nure.kovaljov.database.dao;

public interface CRUD {
	Object getObject(long objectId,Class<?> clazzName);

	Object insertObject(Object obj);

	void deleteObject(long objectId, String objectName);

	Object updateObject(Object anotherObject);
}
