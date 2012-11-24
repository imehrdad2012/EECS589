package edu.umich.eecs.dto;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class CellKey implements Serializable{

	
	public CellKey(int cellID, int areaID) {
		super();
		this.cellID = cellID;
		this.areaID = areaID;
	}
	
	private int cellID;
	private int areaID;
	
	 CellKey() {
		super();
	}
	
	public int getAreaID() {
		return areaID;
	}
	public void setAreaID(int areaID) {
		this.areaID = areaID;
	}
	public int getCellID() {
		return cellID;
	}
	public void setCellID(int cellID) {
		this.cellID = cellID;
	}
	
}
