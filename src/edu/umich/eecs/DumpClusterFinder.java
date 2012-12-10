package edu.umich.eecs;

import java.util.List;

import edu.umich.eecs.dto.Cluster;
import edu.umich.eecs.dto.DataSetType;
import edu.umich.eecs.service.ClusterService;

public class DumpClusterFinder {

	
	List< Cluster > clusters;       ///clusterset after running clustering algorithms.
	ClusterService clusterSrv;
	Integer transNumber;// includes number of package of 100 clusters that persisted
	DataSetType dataSet; //dataset type the cluster is creating from.
	
	
	
	
	
}
