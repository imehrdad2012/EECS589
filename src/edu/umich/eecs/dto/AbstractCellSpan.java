package edu.umich.eecs.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Embedded;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class AbstractCellSpan  implements Serializable, Comparable<CellSpan> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	protected CellSpanCompoundKey key;
	@Embedded
	protected Cell cell;
	protected Timestamp startTime;
	protected Timestamp endtime;

	public AbstractCellSpan(int personid, Cell cell, int transitionId, Timestamp startTime, Timestamp endtime) {
		this.key = new CellSpanCompoundKey(personid, transitionId);
		this.cell = cell;
		this.startTime = startTime;
		this.endtime = endtime;
	}
	
	public AbstractCellSpan() {

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