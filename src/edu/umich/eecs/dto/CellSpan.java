package edu.umich.eecs.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

/**
 * This class is a modeling of different users transition
 * from various celltowers
 * @author Mehrdad
 *
 */
@Entity
@Table(name="cell_span")
public class CellSpan implements Serializable, Comparable<CellSpan> {
	
	
	@Id
	private CellSpanCompoundKey key; //(person id, transitionId) is a irreducible key of our db
	
	@ManyToOne
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private Cell cell;
	private Timestamp startTime;
	private Timestamp endtime;
	
	public CellSpan(int personid, Cell cell, int transitionId, Timestamp startTime, Timestamp endtime) {
		super();
		this.key = new CellSpanCompoundKey(personid, transitionId);
		this.cell = cell;
		this.startTime = startTime;
		this.endtime = endtime;
	}
	CellSpan() {
		super();
	}
	public CellSpanCompoundKey getKey() {
		return key;
	}
	public void setKey(CellSpanCompoundKey key) {
		this.key = key;
	}
	public Cell getCell() {
		return cell;
	}
	public void setCell(Cell cell) {
		this.cell = cell;
	}
	public Timestamp getStartTime() {
		return startTime;
	}
	
	/**
	 * The duration in milliseconds.
	 * @return
	 */
	public long getDuration() {
		return getEndtime().getTime() - getStartTime().getTime();
	}
	
	public Timestamp getEndtime() {
		return endtime;
	}

	@Override
	public int compareTo(CellSpan o) {
		if(o.key.personid != key.personid) {
			throw new ClassCastException();
		}
		return Integer.compare(key.transitionId, o.key.transitionId);
	}
	


}
