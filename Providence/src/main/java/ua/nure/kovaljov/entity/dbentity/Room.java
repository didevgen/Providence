package ua.nure.kovaljov.entity.dbentity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "room")
public class Room {
	private int roomId;
	
	private String roomIp;
	
	private String roomName;
	
	private String doorState;
	
	private transient Set<History> history;
	
	public Room() {
		
	}
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	@Column(name = "room_id")
	public int getRoomId() {
		return roomId;
	}
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	@Id
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((roomIp == null) ? 0 : roomIp.hashCode());
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
		if (roomIp == null) {
			if (other.roomIp != null)
				return false;
		} else if (!roomIp.equals(other.roomIp))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Room [roomName=" + roomName + ", doorState=" + doorState + "]";
	}
	
	
}
