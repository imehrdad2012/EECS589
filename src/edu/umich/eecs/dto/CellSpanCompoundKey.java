package edu.umich.eecs.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Embeddable;

@Embeddable
public class CellSpanCompoundKey implements Serializable {
	/**
	 * 
	 */
	public int personid;
	public Timestamp starttime;
	
	public CellSpanCompoundKey(int personid, Timestamp starttime) {
		super();
		this.personid = personid;
		this.starttime = starttime;
	}
	CellSpanCompoundKey() {
		super();
	}
	public int getPersonid() {
		return personid;
	}
	public void setPersonid(int personid) {
		this.personid = personid;
	}
	public Timestamp getStarttime() {
		return starttime;
	}
	public void setStarttime(Timestamp starttime) {
		this.starttime = starttime;
	}

}
