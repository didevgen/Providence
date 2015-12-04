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
import javax.persistence.Table;

@Entity
@Table(name = "GROUPS")
public class Group {
	private long id;
	private String name;

	private Set<User> users = new HashSet<User>();

	public Group() {

	}

	public Group(String name) {
		this.setName(name);
	}

	@Id
	@GeneratedValue
	@Column(name = "GROUP_ID")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void addUser(User user) {
		this.users.add(user);
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "groups")
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// other getters and setters...
}