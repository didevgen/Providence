package ua.nure.kovaljov.entity.dbentity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User {
     
   
    private Integer id;
     
    private String login;
     
    private String password;
     
   
    private Role role;
    
    private Set<Person> subsribedPersons = new HashSet<Person>();
    @Id
    @GeneratedValue
    public Integer getId() {
        return id;
    }
 
    public void setId(Integer id) {
        this.id = id;
    }
    @Column(name ="login")
    public String getLogin() {
        return login;
    }
 
    public void setLogin(String login) {
        this.login = login;
    }
    @Column(name ="password")
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
    @OneToOne(cascade=CascadeType.ALL)
    @JoinTable(name="user_roles",
        joinColumns = {@JoinColumn(name="user_id", referencedColumnName="id")},
        inverseJoinColumns = {@JoinColumn(name="role_id", referencedColumnName="id")}
    )
    public Role getRole() {
        return role;
    }
 
    public void setRole(Role role) {
        this.role = role;
    }
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "subscribers")
	public Set<Person> getSubsribedPersons() {
		return subsribedPersons;
	}

	public void setSubsribedPersons(Set<Person> subsribedPersons) {
		this.subsribedPersons = subsribedPersons;
	}   
 
}