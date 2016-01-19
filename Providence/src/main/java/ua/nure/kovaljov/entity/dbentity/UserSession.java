package ua.nure.kovaljov.entity.dbentity;

import java.util.Date;

public class UserSession {
	private Person person;
	
	private Date startDate;
	
	private Date finishDate;

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
	
	
}
