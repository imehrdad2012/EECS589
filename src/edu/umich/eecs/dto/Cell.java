package edu.umich.eecs.dto;
import java.io.Serializable;
import java.util.StringTokenizer;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;


/**
 * This class is a simple modeling for cell towers that considers 
 * each cell tower has a cell id and area id.
 * @author Mehrdad
 *
 */
@Entity
@Table(name="cell")
public class Cell implements Serializable, Comparable<Cell> {
	/**
	 * Default Cluster Id
	 * 
	 * 
	 */
	
	@Id
	private CellKey cellkey; //Composite Key=5188.40332--> (areaid=5188 cellid=40332)	
	
	
	Cell() {
	}
	
	public CellKey getCellkey() {
		return cellkey;
	}


	public void setCellkey(CellKey cellkey) {
		this.cellkey = cellkey;
	}



	public Cell(String cell_area){
		init(cell_area);
	}
	
	public Cell(double cell_area){
		init(Double.toString(cell_area));
	}
	
	private void init(String cell_area) {
		cellkey= new CellKey();
		if(!cell_area.equals("0")) {
			StringTokenizer st= new StringTokenizer(cell_area, ".");
			cellkey.setAreaID(Integer.valueOf(st.nextToken()).intValue());
			cellkey.setCellID(Integer.valueOf(st.nextToken()).intValue());
		} else {
			cellkey.setAreaID(0);
			cellkey.setCellID(0);
			
		}
	}

	public int getAreaID() {
		return cellkey.getAreaID();
	}
	
	public void setAreaID(int areaID) {
		cellkey.setAreaID(areaID);
	}
	
	public int getCellID() {
		return cellkey.getCellID();
	}
	
	public void setCellID(int cellID) {
		cellkey.setCellID(cellID);
	}

	@Override
	public int compareTo(Cell o) {
		int areaComparison = Integer.compare(this.getAreaID(), o.getAreaID());
		if(areaComparison == 0) {
			return Integer.compare(this.getCellID(), o.getAreaID());
		} else {
			return areaComparison;
		}
	}
	
	@Override
	public int hashCode() {
		return  (getAreaID()+getCellID()) * 10000 + getAreaID();
	}
	
	@Override
	public boolean equals(Object c) {
		if(c == this) {
			return true;
		}
		if(c instanceof Cell && c != null) {
			return ((Cell)c).getAreaID() == this.getAreaID() && ((Cell)c).getCellID() == this.getCellID();
		} else {
			return false;
		}
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
	
}
	

