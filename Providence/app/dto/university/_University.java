package dto.university;

import models.university.University;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eugne on 28.05.2016.
 */
public class _University extends _NamedDto{

    private List<_Faculty> faculties = new ArrayList<>();

    public _University from(University university) {
        setUuid(university.getUuid());
        setName(university.getName());
        setFullName(university.getFullName());
        university.getFaculties().forEach(item->{
            _Faculty faculty = new _Faculty();
            faculty.setUuid(item.getUuid());
            faculty.setFullName(item.getFullName());
            faculty.setName(item.getName());
            faculties.add(faculty);
        });
        return this;
    }

    public List<_Faculty> getFaculties() {
        return faculties;
    }

    public void setFaculties(List<_Faculty> faculties) {
        this.faculties = faculties;
    }
}
