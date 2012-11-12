package edu.umich.eecs;

/**
 * Defines a pair of cell towers that oscillated in at least one mobility path.
 * Oscillating cell tower pairs have at least k switches in a single mobility path.
 * @author Pedro
 *
 */
public class OscillatingCellTowerPair {
	public CellTowerPair cellTowerPair;
	
	public OscillatingCellTowerPair(CellTowerPair cellTowerPair) {
		super();
		this.cellTowerPair = cellTowerPair;
	}
	/**
	 * support = numberOscillations / totalNumberPaths
	 */
	public double support() {
		return (double)numberOscillations / totalNumberPaths;
	}
	/**
	 * The number of paths in which cellTowerPair appear.
	 */
	public int totalNumberPaths;
	/**
	 * The number of paths in which cellTowerPair oscillates (oscillation
	 * being defined as a path in which the number of switches between
	 * the towers is greater than a threshold).
	 */
	public int numberOscillations;
	
	public boolean pairEquals(CellTowerPair o) {
		return cellTowerPair.equals(o);
	}
}
