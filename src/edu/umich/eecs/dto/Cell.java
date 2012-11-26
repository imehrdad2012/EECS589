package edu.umich.eecs.dto;
import java.io.Serializable;
import java.util.StringTokenizer;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


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
	private static final long serialVersionUID = 1L;
	
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
	
	public Cell(int countryID, int areaID, int cellID) {
		this.cellkey = new CellKey(countryID, areaID, cellID);
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
		return cellkey.compareTo(o.cellkey);
	}
	

	
	@Override
	public int hashCode() {
		return cellkey.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return cellkey.equals(obj);
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
	

