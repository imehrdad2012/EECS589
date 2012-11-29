package edu.umich.eecs.dto;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Defines a pair of cell towers that oscillated in at least one mobility path.
 * Oscillating cell tower pairs have at least k switches in a single mobility path.
 * @author Pedro
 *
 */
@Entity
@Table(name = "OSC_EDGE")
public class OscillatingCellTowerPair  implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Embedded
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
	
	/**
	 * support = numberOscillations / totalNumberPaths
	 */
	@Basic(fetch = FetchType.LAZY)
	@Column(updatable = false, name = "Support_Ratio", nullable = false)
	public double supportRate;
	
	private DataSetType dataset;
	
	OscillatingCellTowerPair() {
		super();
	}
	
	public OscillatingCellTowerPair(Cell cell1, Cell cell2, int numberOscillations, int totalNumberSwitches){
		this.setTotalNumberSwitches(totalNumberSwitches);
		this.setNumberOscillations(numberOscillations) ;
		this.setCellTowerPair(new CellTowerPair(cell1, cell2));
		this.setSupportRate((double)getNumberOscillations()/getTotalNumberSwitches());
	}
	
	public DataSetType getDataSetType() {
		return dataset;
	}
	
	public void setDataSetType(DataSetType dataset) {
		this.dataset = dataset;
	}
	
	public OscillatingCellTowerPair(CellTowerPair cellTowerPair) {
		super();
		this.cellTowerPair = cellTowerPair;
	}
	
	public double getSupportRate() {
		return supportRate;
	}
	public void setSupportRate(double r){
		this.supportRate=r;
		
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
						+ "   TotalNumberofOscillation: " + getNumberOscillations()
						+ "    TotalNumberofSwitches:" + getTotalNumberSwitches()
						+ "   Second Cell: " + this.cellTowerPair.getCell2().toString()+"  Weight:"+getSupportRate())
				.toString();

	}


	public CellTowerPair getCellTowerPair() {
		return cellTowerPair;
	}


	public void setCellTowerPair(CellTowerPair cellTowerPair) {
		this.cellTowerPair = cellTowerPair;
	}
}
