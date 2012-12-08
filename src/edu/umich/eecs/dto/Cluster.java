package edu.umich.eecs.dto;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name="Cluster")
public class Cluster implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	
	
	@Id
	ClusterKey ckey;
	
	@ManyToMany
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	Set<Cell> cells;
	
	@Column(name="Quality_Ratio")
	double quality;
	

	Cluster() {
		super();
	}
	
	
	public ClusterKey getCkey() {
		return ckey;
	}


	public void setCkey(ClusterKey ckey) {
		this.ckey = ckey;
	}

	public Cluster(Set<Cell> cells, double quality, DataSetType dataset) {
		super();
		this.cells = cells;
		this.quality = quality;
		this.ckey=new ClusterKey(dataset);
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
	
	

	@Override
	public boolean equals(Object c) {
		if(c == this) {
			return true;
		}
		if(c instanceof Cluster && c != null) {
			return ((Cluster)c).getCkey()
					.getClusterID()== this.getCkey().getClusterID()
					&& ((Cluster)c).getCkey().getDataset() == this.getCkey().getDataset();
					
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + getCkey().getDataset().asInt();
		result = prime * result + getCkey().getClusterID();
		return result;
	}
		
	

}
