package edu.umich.eecs.dto;

import java.sql.Timestamp;
import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This class is a modeling of different users transition
 * from various celltowers
 * @author Mehrdad
 *
 */
@Entity
@Table(name="cellspan")
public class CellSpan implements Serializable{
	
	
	@Id
	private CellSpanCompoundKey key; //(person id, starttime) is a irreducible key of our db
	@Embedded
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
	public Timestamp getEndtime() {
		return endtime;
	}
	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}
	


}
