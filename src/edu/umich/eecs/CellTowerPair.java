package edu.umich.eecs;

import edu.umich.eecs.dto.Cell;

/**
 * This class represents a pair of cell towers.
 * @author Pedro
 *
 */
public class CellTowerPair {
	
	private Cell cell1;
	private Cell cell2;
	
	public CellTowerPair(Cell cell1, Cell cell2) {
		super();
		assert !cell1.equals(cell2);
		//
		// We always order cell1 and cell2 so that CellTowerPair(x,y) and
		// CellTowerPair(y,x) are the same. The comparison is lexicographical.
		//
		if(cell1.compareTo(cell2) < 0) {
			this.cell1 = cell1;
			this.cell2 = cell2;
		} else {
			this.cell1 = cell2;
			this.cell2 = cell1;
		}
	}

	public Cell getCell1() {
		return cell1;
	}

	public Cell getCell2() {
		return cell2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cell1 == null) ? 0 : cell1.hashCode());
		result = prime * result + ((cell2 == null) ? 0 : cell2.hashCode());
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
		CellTowerPair other = (CellTowerPair) obj;
		if (cell1 == null) {
			if (other.cell1 != null)
				return false;
		} else if (!cell1.equals(other.cell1))
			return false;
		if (cell2 == null) {
			if (other.cell2 != null)
				return false;
		} else if (!cell2.equals(other.cell2))
			return false;
		return true;
	}
	
	
}
