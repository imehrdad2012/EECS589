package edu.umich.eecs.dto;
import java.io.Serializable;
import java.util.StringTokenizer;
import javax.persistence.Embeddable;


/**
 * This class is a simple modeling for cell towers that considers 
 * each cell tower has a cell id and area id.
 * @author Mehrdad
 *
 */

@Embeddable
public class Cell implements Serializable, Comparable<Cell> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int countryID = 1;
	private int areaID;
	private int cellID;
	// e.g: 5188.40332--> areaid=5188 cellid=40332	
	
	Cell() {
	}
	
	public Cell(String cell_area){
		init(cell_area);
	}
	
	public Cell(double cell_area){
		init(Double.toString(cell_area));
	}
	
	public Cell(int countryID, int areaID, int cellID) {
		this.countryID = countryID;
		this.areaID = areaID;
		this.cellID = cellID;
	}

	private void init(String cell_area) {
		if(!cell_area.equals("0")) {
			StringTokenizer st= new StringTokenizer(cell_area, ".");
			areaID=Integer.valueOf(st.nextToken()).intValue();
			cellID=Integer.valueOf(st.nextToken()).intValue();
		} else {
			areaID = cellID = 0;
		}
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

	@Override
	public int compareTo(Cell o) {
		int countryComparison = Integer.compare(this.countryID, o.countryID);
		if(countryComparison != 0) return countryComparison;
		
		int areaComparison = Integer.compare(this.areaID, o.areaID);
		if(areaComparison != 0) return areaComparison;
		
		return Integer.compare(this.cellID, o.cellID);
	}
	

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + areaID;
		result = prime * result + cellID;
		result = prime * result + countryID;
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
		Cell other = (Cell) obj;
		if (areaID != other.areaID)
			return false;
		if (cellID != other.cellID)
			return false;
		if (countryID != other.countryID)
			return false;
		return true;
	}

	/**
	 * We have overrided toString method of Object class for having
	 *  string representation of each cell in the future
	 */
	
	@Override
	public String toString() {
		
		return new StringBuilder().append(getAreaID()).
				append(".").append(getCellID()).toString();
	}

	public int getCountryID() {
		return countryID;
	}

	public void setCountryID(int countryID) {
		this.countryID = countryID;
	}
	
}
	

