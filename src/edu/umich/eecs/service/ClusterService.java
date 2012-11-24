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
		return cells;
	}
	public void saveListToCluster(List<Cluster> clusters){
		Session s=fireTransaction();
		int commitEveryX = 1000;
		int i = 0;
		for(Cluster c: clusters){
			if(++i % commitEveryX == 0) {
				System.out.println("Persisting #...(" + i + "/" + clusters.size() + ") Cluster" );
				commitTransaction(s);
				s = fireTransaction();
			}
			s.saveOrUpdate(c);
		}
		commitTransaction(s);
	}
}
