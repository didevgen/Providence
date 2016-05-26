package models.university;

import models.base.IndexEntity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Eugne on 26.05.2016.
 */

@Entity
@Table(name ="university")
public class University extends IndexEntity{

    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "university",fetch = FetchType.LAZY)
    private List<Faculty> faculties;

    public University() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Faculty> getFaculties() {
        return faculties;
    }

    public void setFaculties(List<Faculty> faculties) {
        this.faculties = faculties;
    }
}
