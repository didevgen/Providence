package models.university;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import models.base.IndexEntity;
import models.base.UUIDEntity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Eugne on 26.05.2016.
 */
@Entity
@Table(name="faculty")
public class Faculty extends UUIDEntity {

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
    private University university;

    @JsonProperty("departments")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,mappedBy = "faculty")
    private List<Department> departments;

    @JsonProperty("directions")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,mappedBy = "faculty")
    private List<Direction> directions;

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

    public List<Direction> getDirections() {
        return directions;
    }

    public void setDirections(List<Direction> directions) {
        this.directions = directions;
    }

    public Long getApiId() {
        return apiId;
    }

    public void setApiId(Long apiId) {
        this.apiId = apiId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Faculty faculty = (Faculty) o;

        return !(apiId != null ? !apiId.equals(faculty.apiId) : faculty.apiId != null);

    }

    @Override
    public int hashCode() {
        return apiId != null ? apiId.hashCode() : 0;
    }
}
