package ua.nure.kovaljov.entity.dbentity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "verifymode")
public class VerifyMode {

	private long modeId;
	
	private String modeDescription;
	
	private Set<History> history;

	@Id
	@Column(name = "verify_id")
	public long getModeId() {
		return modeId;
	}

	public void setModeId(long modeId) {
		this.modeId = modeId;
	}
	
	@Column(name = "mode_description")
	public String getModeDescription() {
		return modeDescription;
	}

	public void setModeDescription(String modeDescription) {
		this.modeDescription = modeDescription;
	}

	@Override
	public String toString() {
		return "VerifyMode [modeDescription=" + modeDescription + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (modeId ^ (modeId >>> 32));
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
		VerifyMode other = (VerifyMode) obj;
		if (modeId != other.modeId)
			return false;
		return true;
	}
	@JsonIgnore
	@OneToMany(mappedBy="verifyMode")
	public Set<History> getHistory() {
		return history;
	}

	public void setHistory(Set<History> history) {
		this.history = history;
	}
	
	
}
