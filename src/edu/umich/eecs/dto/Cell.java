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



public class Cell implements Serializable  {

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
	
	/**
	 * We have overrided toString method of Object class for having
	 *  string representation of each cell in the future
	 */
	
	@Override
	public String toString() {
		
		return new StringBuilder().append(getAreaID()).
				append(".").append(getCellID()).toString();
	}

	@Override
	public boolean equals(Object obj) {
	    	if(obj==null) return false;
	    	if(obj==this)return true;
	    	if(!(obj instanceof Cell))return false;
	    	Cell otherCell=(Cell)obj;
	    	if(otherCell.cellID==this.cellID&& otherCell.areaID==this.areaID) return true;
	    	return false;
	    }
	
	
	}
	

