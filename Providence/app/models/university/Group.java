package models.university;

import models.base.IndexEntity;

import javax.persistence.*;

/**
 * Created by Eugne on 26.05.2016.
 */
@Entity
@Table(name = "groups")
public class Group extends IndexEntity{

    @Column(name ="name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Department department;

    public Group() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
