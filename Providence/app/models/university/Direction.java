package models.university;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import models.base.UUIDEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="direction")
public class Direction extends UUIDEntity{

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

    @JsonProperty("specialities")
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "direction")
    private List<Speciality> specialities = new ArrayList<>();

    @JsonProperty("groups")
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "direction")
    private List<Group> groups = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public List<Speciality> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(List<Speciality> specialities) {
        this.specialities = specialities;
    }

    public Long getApiId() {
        return apiId;
    }

    public void setApiId(Long apiId) {
        this.apiId = apiId;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}
