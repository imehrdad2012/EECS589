package edu.umich.eecs.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Embeddable;

@Embeddable
public class CellSpanCompoundKey implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * TransitionId is an identifier that represents the order in which
	 * the cell span was in the original file. We need this hack because
	 * there are some weird inconsistencies in the dataset, e.g.
	 * 
	 * 04-Sep-2004 18:23:39,7803.00361,
	 * 04-Sep-2004 18:25:43,7803.00362,
	 * 04-Sep-2004 18:30:03,7803.00361,
	 * 04-Sep-2004 18:31:36,7803.00362,
	 * (couple of lines later)
	 * 04-Sep-2004 19:05:01,0,
	 * 04-Sep-2004 18:23:47,54024.21803,
	 * 04-Sep-2004 18:23:48,54024.21803,
	 * 04-Sep-2004 18:24:34,54024.21962,
	 * 04-Sep-2004 18:24:54,54024.21963,
	 * 04-Sep-2004 18:25:17,54024.20291,
	 * 04-Sep-2004 18:27:29,54024.20292,
	 */
	public int personid;
	public int transitionId;
	
	public CellSpanCompoundKey(int personid, int transitionId) {
		super();
		this.personid = personid;
		this.transitionId = transitionId;
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
	public int getTransitionId() {
		return transitionId;
	}

}
