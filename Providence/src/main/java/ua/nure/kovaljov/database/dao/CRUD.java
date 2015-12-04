package ua.nure.kovaljov.database.dao;

import java.util.List;

public interface CRUD {
	Object getObject(long objectId,Class<?> clazzName);

	Object insertObject(Object obj);

	void deleteObject(long objectId, String objectName,String whereClause);

	Object updateObject(Object anotherObject);
	
	List<Object> getAllObjects(Class<?> classCriteria);
}
