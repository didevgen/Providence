package ua.nure.kovaljov.entity;

import java.util.ArrayList;
import java.util.List;

public class User {
	private long userId;
	private String name;
	private String email;
	private long cardNumber;
	private List<Group> groups = new ArrayList<Group>();
	
	
	public User() {
		super();
	}
	
	public User(String name, String email, long cardNumber) {
		super();
		this.name = name;
		this.email = email;
		this.cardNumber = cardNumber;
	}

	public long getUserId() {
		return userId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (cardNumber ^ (cardNumber >>> 32));
		result = prime * result + ((email == null) ? 0 : email.hashCode());
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
		if (cardNumber != other.cardNumber)
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public long getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(long cardNumber) {
		this.cardNumber = cardNumber;
	}
	public List<Group> getGroups() {
		return groups;
	}
	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
	
}
