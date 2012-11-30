package edu.umich.eecs.dto;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="Cluster")
public class Cluster implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private int clusterID;
	
	@OneToMany
	@JoinTable(name="Cluster_Cell")
	Set<Cell> cells;
	
	@Column(name="Quality_Ratio")
	double quality;
	
	private DataSetType dataset;
	
	public DataSetType getDataset() {
		return dataset;
	}


	public void setDataset(DataSetType dataset) {
		this.dataset = dataset;
	}


	Cluster() {
		super();
	}
	
	
	public Cluster(Set<Cell> cells, double quality) {
		super();
		this.cells = cells;
		this.quality = quality;
	}


	public Set<Cell> getCells() {
		return cells;
	}
	public void setCells(Set<Cell> cells) {
		this.cells = cells;
	}
	public double getQuality() {
		return quality;
	}
	public void setQuality(double quality) {
		this.quality = quality;
	}
	
	

}
