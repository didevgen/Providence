package models.university;

import models.base.IndexEntity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Eugne on 26.05.2016.
 */
@Entity
@Table(name="faculty")
public class Faculty extends IndexEntity {

    @Column(name="name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private University university;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,mappedBy = "faculty")
    private List<Department> departments;

    public Faculty() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }
}
