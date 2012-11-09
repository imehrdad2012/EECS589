package edu.umich.eecs.dto;

import java.sql.Timestamp;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="cellspan")
public class CellSpan implements Serializable, Comparable<CellSpan> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	@Id
	private CellSpanCompoundKey key; //(person id, starttime) is a irreducible key of our db
	private Cell cell;
	private Timestamp endtime;
	
	public CellSpan(int personid, Cell cell,Timestamp startime, Timestamp endtime) {
		super();
		this.key = new CellSpanCompoundKey(personid, startime);
		this.cell = cell;
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
		return key.getStarttime();
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
	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}
	@Override
	public int compareTo(CellSpan o) {
		if(o.key.personid != key.personid) {
			throw new ClassCastException();
		}
		return key.starttime.compareTo(o.key.starttime);
	}
	


}
