package edu.umich.eecs.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Embeddable
public class ClusterKey implements Serializable {

	
	@Column(name="DataSet_Type")
	private DataSetType dataset;
	
	
	@Column(name = "Cluster_ID", unique = true, nullable = false)
	private int clusterID;
	
	
	public int getClusterID() {
		return clusterID;
	}

	public void setClusterID(int clusterID) {
		this.clusterID = clusterID;
	}

	public ClusterKey(DataSetType dataset) {
		super();
		this.dataset = dataset;
	}

	
	public DataSetType getDataset() {
		return dataset;
	}

	public void setDataset(DataSetType dataset) {
		this.dataset = dataset;
	}
}
