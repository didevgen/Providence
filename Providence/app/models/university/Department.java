package models.university;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import models.base.IndexEntity;
import models.base.UUIDEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eugne on 26.05.2016.
 */
@Entity
@Table(name="department")
public class Department extends UUIDEntity{

    @Id
    @JsonIgnore
    @Basic(optional = false)
    @Column(name = "id", nullable = false, columnDefinition = "INT(11)")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonProperty(value = "id")
    @Column(name="api_id")
    private Long apiId;

    @JsonProperty("short_name")
    @Column(name = "name")
    private String name;

    @JsonProperty("full_name")
    @Column(name = "full_name")
    private String fullName;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Faculty faculty;

    @JsonProperty("teachers")
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "department")
    private List<Teacher> teachers = new ArrayList<>();

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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public Long getApiId() {
        return apiId;
    }

    public void setApiId(Long apiId) {
        this.apiId = apiId;
    }


}
