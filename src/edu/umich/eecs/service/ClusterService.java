package edu.umich.eecs.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	public Map<Integer, Long> getCountOfClustersByAreaID(DataSetType dataset, List<Integer> areas) {
		Map<Integer,Long> result= new HashMap<>();
		Session s= fireTransaction();
		
		for(Integer i: areas){
			Query query= s.createQuery("select count(distinct cl.ckey.clusterID) from Cluster cl join cl.cells ce where" +
					" cl.ckey.dataset=:dset and ce.cellkey.areaID=:id" );
			query.setInteger("id", i);
			query.setInteger("dset", dataset.asInt());
			List<Long> clusterID=(List<Long>)query.list();
			result.put(i, clusterID.get(0));
			
		}
		
		
		return result;
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
	
	
	public Map<Integer, Long> getCountUnclusteredCellbyAreaID(DataSetType dataset, List<Integer> areas){
		
		Map<Integer, Long> oCellCount=new OscillationService(dataset).getCountOfOsiCellsByAreaId(areas);
		Map<Integer, Long> cMap= null;
		
		if(dataset==DataSetType.RealityMining){
			cMap= new CellSpanService().getCountAllCellsByAreaID(areas);
		}
		else if(dataset==DataSetType.NokiaChallenge){
			cMap=new MDCCellSpanService().getCountAllCellsByAreaID(areas);
		}
		else if(dataset==DataSetType.SampledRealityMining){
			cMap= new SampledCellSpanService().getCountAllCellsByAreaID(areas);
		}
		for(int i: cMap.keySet()){
			cMap.put(i, cMap.get(i)-oCellCount.get(i));
		}
		
		return cMap;
		
		
	}
	
	
}
