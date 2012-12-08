package edu.umich.eecs.dto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AreaDensity implements Serializable {
	
	@Id
	int areaCode;
	int numberOfCluster;
	DataSetType dataSet;
	public DataSetType getDataSet() {
		return dataSet;
	}

	public void setDataSet(DataSetType dataSet) {
		this.dataSet = dataSet;
	}


	int numberOfCells;
	
	/*
	 * density=(number of cells in a specific area/ number of cluster in an area)
	 */
	double density;
	
	
	public int getNumberOfCluster() {
		return numberOfCluster;
	}

	public void setNumberOfCluster(int numberOfCluster) {
		this.numberOfCluster = numberOfCluster;
	}

	public int getNumberOfCells() {
		return numberOfCells;
	}

	public void setNumberOfCells(int numberOfCells) {
		this.numberOfCells = numberOfCells;
	}

	public void setDensity(double density) {
		this.density = density;
	}



	public int getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(int areaCode) {
		this.areaCode = areaCode;
	}

	public double getDensity() {
		return density;
	}

	public void setDensity(int density) {
		this.density = density;
	}
	
	


@Override
public boolean equals(Object c) {
	if(c == this) {
		return true;
	}
	if(c instanceof OpenCell && c != null) {
		return ((AreaDensity)c).getDensity() == this.getDensity()
				&& ((AreaDensity)c).getAreaCode() == this.getAreaCode()
				&&((AreaDensity)c).getNumberOfCluster() == this.getNumberOfCluster()
				&&((AreaDensity)c).getNumberOfCells() == this.getNumberOfCells()
				&&((AreaDensity)c).getDataSet().asInt()==this.getDataSet().asInt();
				

	} else {
		return false;
	}
}
	
	
public AreaDensity(int areaCode, int numberOfCluster, DataSetType dataSet,
		int numberOfCells, double density) {
	super();
	this.areaCode = areaCode;
	this.numberOfCluster = numberOfCluster;
	this.dataSet = dataSet;
	this.numberOfCells = numberOfCells;
	this.density = density;
}

AreaDensity() {
	super();
}

@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + getAreaCode();
	result = prime * result + (int)getDensity();
	result = prime * result + getNumberOfCells();
	result = prime * result + getNumberOfCluster();
	result= prime*result+getDataSet().asInt();
	return result;
}
	
	

}
