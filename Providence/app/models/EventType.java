package models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "event_type")
public class EventType {

	private long eventTypeId;
	
	private String eventDescription;
	
	
	private transient Set<History> history;

	@Id
	@Column(name = "event_id")
	public long getEventTypeId() {
		return eventTypeId;
	}

	public void setEventTypeId(long eventTypeId) {
		this.eventTypeId = eventTypeId;
	}
	@Column(name = "type_description")
	public String getEventDesription() {
		return eventDescription;
	}

	public void setEventDesription(String eventDesription) {
		this.eventDescription = eventDesription;
	}
	@JsonIgnore
	@OneToMany(mappedBy="eventType")
	public Set<History> getHistory() {
		return history;
	}

	public void setHistory(Set<History> history) {
		this.history = history;
	}

	
	
}
