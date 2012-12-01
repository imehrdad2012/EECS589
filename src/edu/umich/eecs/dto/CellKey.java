package edu.umich.eecs.dto;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class CellKey implements Serializable, Comparable<CellKey>{

		
	private int countryID;
	private int cellID;
	private int areaID;
	private int networkID;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CellKey(int countryID, int cellID, int areaID, int networkID) {
		super();
		this.countryID = countryID;
		this.cellID = cellID;
		this.areaID = areaID;
		this.networkID = networkID;
	}

	
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

	public int getCountryID() {
		return countryID;
	}

	public void setCountryID(int countryID) {
		this.countryID = countryID;
	}

	public int getNetworkID() {
		return networkID;
	}


	public void setNetworkID(int networkID) {
		this.networkID = networkID;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + areaID;
		result = prime * result + cellID;
		result = prime * result + countryID;
		result = prime * result + networkID;
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
		CellKey other = (CellKey) obj;
		if (areaID != other.areaID)
			return false;
		if (cellID != other.cellID)
			return false;
		if (countryID != other.countryID)
			return false;
		if(networkID != other.networkID)
			return false;
		return true;
	}

	@Override
	public int compareTo(CellKey o) {
		int countryComparison = Integer.compare(this.countryID, o.countryID);
		if(countryComparison != 0) return countryComparison;
		
		int areaComparison = Integer.compare(this.areaID, o.areaID);
		if(areaComparison != 0) return areaComparison;
		
		return Integer.compare(this.cellID, o.cellID);
	}
	
	
	
}
