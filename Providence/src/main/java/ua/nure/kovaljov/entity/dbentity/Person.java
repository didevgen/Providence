package ua.nure.kovaljov.entity.dbentity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "persons")
public class Person {
	private long personId;
	private String personName;
	private long cardNumber;
	private String email;

	private Set<Group> groups = new HashSet<Group>();

	public Person() {

	}

	public void addGroup(Group group) {
		this.groups.add(group);
	}

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	@Column(name = "person_id")
	public long getPersonId() {
		return personId;
	}

	public void setPersonId(long id) {
		this.personId = id;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "users_groups", joinColumns = {
			@JoinColumn(name = "person_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "group_id", nullable = false, updatable = false) })
	public Set<Group> getGroups() {
		return groups;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}

	@Column(name = "personname")
	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String username) {
		this.personName = username;
	}

	@Column(name = "cardNumber")
	public long getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(long cardNumber) {
		this.cardNumber = cardNumber;
	}

	@Column(name = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Person [userName=" + personName + ", cardNumber=" + cardNumber + "]";
	}
}