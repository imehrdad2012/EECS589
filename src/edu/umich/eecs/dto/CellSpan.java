package edu.umich.eecs.dto;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * This class is a modeling of different users transition
 * from various celltowers
 * @author Mehrdad
 *
 */
@Entity
@Table(name="cellspan")
public class CellSpan extends AbstractCellSpan {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CellSpan(int personid, Cell cell, int transitionId, Timestamp startTime, Timestamp endtime) {
		super(personid, cell, transitionId, startTime, endtime);
	}
	
	public CellSpan() {
		super();
	}
}
