package edu.umich.eecs.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;

import edu.umich.eecs.dto.Cell;
import edu.umich.eecs.dto.Cluster;
import edu.umich.eecs.dto.DataSetType;
import edu.umich.eecs.dto.OscillatingCellTowerPair;

public class ClusterService extends Service {

	@SuppressWarnings("unchecked")
	public List<Cluster> getAllClusters(DataSetType dataset) {
		Session s= fireTransaction();
		Query query= s.createQuery("from Cluster where ckey.dataset=:dset" );
		query.setInteger("dset", dataset.asInt());
		List<Cluster> cells=(List<Cluster>)query.list();
		return cells;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Integer> getAllClustersByAreaID(DataSetType dataset, int areaID) {
		Session s= fireTransaction();
		Query query= s.createQuery("select distinct(cl.ckey.clusterID) from Cluster cl join cl.cells ce where" +
				" cl.ckey.dataset=:dset and ce.cellkey.areaID=:id " );
		query.setInteger("id", areaID);
		query.setInteger("dset", dataset.asInt());
		List<Integer> clusterID=(List<Integer>)query.list();
		return clusterID;
	}
	
	public int getCount(DataSetType dataset, int areaID) {
		Session s= fireTransaction();
		Query query= s.createQuery("select count(*) from Cluster cl join cl.cells ce where" +
				" cl.ckey.dataset=:dset and ce.cellkey.areaID=:id group by cl.ckey.clusterID " );
		query.setInteger("id", areaID);
		query.setInteger("dset", dataset.asInt());
		List<Integer> clusterID=(List<Integer>)query.list();
		return (int)clusterID.get(0);
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
	
	
	public List<Integer> getUnclusteredCellbyAreaID(DataSetType dataset, int areaID){
		
		OscillationService os= new OscillationService(dataset);
		Set<Integer>oSet=os.getAllOsiCellsByAreaId(areaID);
		List<Integer>cList= null;
		
		if(dataset==DataSetType.RealityMining){
			cList= new CellSpanService().getAllCellsByAreaID(areaID);
		}
		else if(dataset==DataSetType.NokiaChallenge){
			cList=new MDCCellSpanService().getAllCellsByAreaID(areaID);
		}
		else if(dataset==DataSetType.SampledRealityMining){
			cList= new SampledCellSpanService().getAllCellsByAreaID(areaID);
		}
		cList.removeAll(oSet);
		return cList ;
		
		
	}
	
	
}
