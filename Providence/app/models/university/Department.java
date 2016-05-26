package models.university;

import models.base.IndexEntity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Eugne on 26.05.2016.
 */
@Entity
@Table(name="department")
public class Department extends IndexEntity{

    @Column(name="name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Faculty faculty;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "department")
    private List<Group> groups;

    public Department() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}
