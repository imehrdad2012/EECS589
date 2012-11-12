package edu.umich.eecs;

/**
 * Defines a pair of cell towers that had at least one mutual switch in a mobility path.
 * @author Pedro
 *
 */
public class SwitchingCellTowerPair {
	public CellTowerPair cellTowerPair;
	
	public SwitchingCellTowerPair(CellTowerPair cellTowerPair) {
		super();
		this.cellTowerPair = cellTowerPair;
	}

	public int numberOfSwitches;
	
	public boolean pairEquals(CellTowerPair o) {
		return cellTowerPair.equals(o);
	}
}
