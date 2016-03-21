package models;


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
public class User  {
     
   
    private Integer id;
     
    private String login;
     
    private String password;
     
   
    private Role role;
    
    private Set<Person> subsribedPersons = new HashSet<Person>();
    private transient Set<Room> subsribedRooms = new HashSet<Room>();
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
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "subscribers")
	public Set<Room> getSubsribedRooms() {
		return subsribedRooms;
	}

	public void setSubsribedRooms(Set<Room> subsribedRooms) {
		this.subsribedRooms = subsribedRooms;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		return true;
	}   
 
}