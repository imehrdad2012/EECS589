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
	
	public Cell(String cell_area){
		init(cell_area);
	}
	
	public Cell(double cell_area){
		init(Double.toString(cell_area));
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
	
}
