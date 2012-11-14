package edu.umich.eecs.dto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Defines a pair of cell towers that oscillated in at least one mobility path.
 * Oscillating cell tower pairs have at least k switches in a single mobility path.
 * @author Pedro
 *
 */
@Entity
@Table(name = "osc_edges")
public class OscillatingCellTowerPair  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private CellTowerPair cellTowerPair;
	
	/**
	 * The number of paths in which cellTowerPair appear.
	 */
	private int totalNumberSwitches;

	/**
	 * The number of paths in which cellTowerPair oscillates (oscillation
	 * being defined as a path in which the number of switches between
	 * the towers is greater than a threshold).
	 */
	private int numberOscillations;
	
	public OscillatingCellTowerPair(CellTowerPair cellTowerPair) {
		super();
		this.cellTowerPair = cellTowerPair;
	}
	/**
	 * support = numberOscillations / totalNumberPaths
	 */
	public double support() {
		return (double)numberOscillations / totalNumberSwitches;
	}

	
	public boolean pairEquals(CellTowerPair o) {
		return cellTowerPair.equals(o);
	}

	public int getTotalNumberSwitches() {
		return totalNumberSwitches;
	}
	public void setTotalNumberSwitches(int totalNumberSwitches) {
		this.totalNumberSwitches = totalNumberSwitches;
	}
	
	public void incrementTotalNumberSwitches() {
		this.totalNumberSwitches++;
	}
	
	public int getNumberOscillations() {
		return numberOscillations;
	}
	public void setNumberOscillations(int numberOscillations) {
		this.numberOscillations = numberOscillations;
	}
	
	public void incrementNumberOscillations() {
		this.numberOscillations++;
	}
	
	@Override
	public String toString() {
		return new StringBuilder().append(
				"First Cell: " + this.cellTowerPair.getCell1().toString()
						+ "  TotalNumberofOscillation: " + getNumberOscillations()
						+ "   TotalNumberofSwitches:" + getTotalNumberSwitches()
						+ "  Second Cell: " + this.cellTowerPair.getCell2().toString())
				.toString();

	}
}
