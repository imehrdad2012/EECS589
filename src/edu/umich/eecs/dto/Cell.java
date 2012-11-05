package edu.umich.eecs.dto;
import java.io.Serializable;
import java.util.StringTokenizer;
import javax.persistence.Embeddable;

@Embeddable
public class Cell implements Serializable {

	/**
	 * 
	 */

	private int areaID;
	private int cellID;
	// e.g: 5188.40332--> areaid=5188 cellid=40332	
	
	Cell() {
	}
	
	public Cell(double cell_area){
		StringTokenizer st= new StringTokenizer(Double.toString(cell_area), ".");
		areaID=Integer.valueOf(st.nextToken()).intValue();
		cellID=Integer.valueOf(st.nextToken()).intValue();
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
