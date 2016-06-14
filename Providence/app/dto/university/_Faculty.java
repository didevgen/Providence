package dto.university;

import models.university.Faculty;

/**
 * Created by Eugne on 28.05.2016.
 */
public class _Faculty extends _NamedDto{
    public _Faculty from(Faculty faculty) {
        setUuid(faculty.getUuid());
        setName(faculty.getName());
        setFullName(faculty.getFullName());
        return this;
    }

}
