package edu.umich.eecs.service;

import java.util.Collection;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import edu.umich.eecs.dto.Cluster;
import edu.umich.eecs.dto.DataSetType;
import edu.umich.eecs.dto.OscillatingCellTowerPair;

public class ClusterService extends Service {

	public List<Cluster> getAllClusters(DataSetType dataset) {
		Session s= fireTransaction();
		Query query= s.createQuery("from Cluster where ckey.dataset=:dset" );
		query.setInteger("dset", dataset.asInt());
		List<Cluster> cells=(List<Cluster>)query.list();
		return cells;
	}
	public void saveListToCluster(List<Cluster> clusters){
		Session s=fireTransaction();
		int row=0;
		Query query= s.createQuery("select max(ckey.clusterID) from Cluster" );
		if(query.list().get(0)!=null){
			row=((Integer)query.list().get(0))+1;
		}
		for(Cluster c: clusters){
			c.getCkey().setClusterID(++row);
			s.saveOrUpdate(c);
		}
		commitTransaction(s);
	}
	
}
