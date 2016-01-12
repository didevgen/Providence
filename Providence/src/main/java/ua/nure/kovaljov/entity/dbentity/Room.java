package ua.nure.kovaljov.entity.dbentity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "room")
public class Room {
	private int roomId;
	
	private String roomIp;
	
	private String roomName;
	
	private String doorState;
	private boolean isSubscribed;
	
	private transient Set<History> history = new HashSet<>();
	private transient Set<User> subscribers = new HashSet<>();
	private List<Person> whoIsOnline = new ArrayList<>();
	
	public Room() {
		
	}
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	@Column(name = "room_id")
	public int getRoomId() {
		return roomId;
	}
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	
	@Column(name="room_ip")
	public String getRoomIp() {
		return roomIp;
	}

	public void setRoomIp(String roomIp) {
		this.roomIp = roomIp;
	}
	@Column(name="room_name")
	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	@Column(name="door_state")
	public String getDoorState() {
		return doorState;
	}

	public void setDoorState(String doorState) {
		this.doorState = doorState;
	}
	@JsonIgnore
	@OneToMany(mappedBy="room")
	public Set<History> getHistory() {
		return history;
	}
	public void setHistory(Set<History> history) {
		this.history = history;
	}
	@Transient
	public boolean isSubscribed() {
		return isSubscribed;
	}
	
	public void setSubscribed(boolean isSubscribed) {
		this.isSubscribed = isSubscribed;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((roomName == null) ? 0 : roomName.hashCode());
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
		Room other = (Room) obj;
		if (roomName == null) {
			if (other.roomName != null)
				return false;
		} else if (!roomName.equals(other.roomName))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Room [roomName=" + roomName + ", doorState=" + doorState + "]";
	}
	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "s_user_to_room", joinColumns = {
			@JoinColumn(name = "room_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "id", nullable = false, updatable = false) })
	public Set<User> getSubscribers() {
		return subscribers;
	}

	public void setSubscribers(Set<User> subscribers) {
		this.subscribers = subscribers;
	}
	@Transient
	public List<Person> getWhoIsOnline() {
		return whoIsOnline;
	}
	public void setWhoIsOnline(List<Person> whoIsOnline) {
		this.whoIsOnline = whoIsOnline;
	}
	
}
