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
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "persons")
public class Person {
	private long personId;
	private String personName;
	private long cardNumber;
	private String email;
	private boolean isSubscribed;
	

	private Set<Group> groups = new HashSet<Group>();
	private Set<User> subscribers = new HashSet<>();
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
	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "s_user_to_person", joinColumns = {
			@JoinColumn(name = "person_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "id", nullable = false, updatable = false) })
	public Set<User> getSubscribers() {
		return subscribers;
	}

	public void setSubscribers(Set<User> subscribers) {
		this.subscribers = subscribers;
	}
	@Transient
	public boolean isSubscribed() {
		return isSubscribed;
	}

	public void setSubscribed(boolean isSubscribed) {
		this.isSubscribed = isSubscribed;
	}

	@Override
	public String toString() {
		return "Person [userName=" + personName + ", cardNumber=" + cardNumber + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (personId ^ (personId >>> 32));
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
		Person other = (Person) obj;
		if (personId != other.personId)
			return false;
		return true;
	}
	
	
}