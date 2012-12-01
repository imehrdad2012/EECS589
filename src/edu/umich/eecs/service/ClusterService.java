package edu.umich.eecs.service;

import java.util.Collection;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import edu.umich.eecs.dto.Cluster;
import edu.umich.eecs.dto.OscillatingCellTowerPair;

public class ClusterService extends Service {

	public List<Cluster> getAllClusters() {
		Session s= fireTransaction();
		Query query= s.createQuery("from Cluster" );
		List<Cluster> cells=(List<Cluster>)query.list();
		closeSession(s);
		return cells;
	}
	public void saveListToCluster(List<Cluster> clusters){
		Session s=fireTransaction();
		for(Cluster c: clusters){
		
			s.saveOrUpdate(c);
		}
		commitTransaction(s);
	}
}
