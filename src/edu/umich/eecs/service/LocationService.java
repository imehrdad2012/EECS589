package edu.umich.eecs.service;

import java.util.List;

import org.hibernate.Query;

import org.hibernate.Session;

import edu.umich.eecs.dto.Cluster;
import edu.umich.eecs.dto.Location;
import edu.umich.eecs.dto.OscillatingCellTowerPair;
import edu.umich.eecs.logger.LogClass;

public class LocationService extends Service {

	
	
	/**
	 * This method save one OscillationEdge in one session.
	 * @param pp
	 */
	
	public void saveLC(Location lc){
		Session s=fireTransaction();
		s.saveOrUpdate(lc);
		commitTransaction(s);
	}
	
	public void saveListToLocation(List<Location> locations){
		Session s=fireTransaction();
		for(Location l: locations){
			s.saveOrUpdate(l);
		}
		commitTransaction(s);
	}
	

	public Location getLocation(int cc, int nc, int aid, int cid){
	
		Session s= fireTransaction();
		Query query = s.createQuery("from Location where cellid=:cid and mnc=:nc and mcc=:cc and lac=:aid ");
		query.setInteger("cid", cid);
		query.setInteger("nc",nc );
		query.setInteger("aid", aid);
		query.setInteger("cc", cc);
		if(query.list()==null || query.list().size()==0){
			LogClass.log("There is no result for mcc="+cc+" mnc="+nc+" aid="+aid+" cid="+cid);
		}
		else {
		
			List<Location> l= (List<Location>)query.list();
			LogClass.log(l.get(0).getCellid()+ "exists");
			
		}
		return null;
		 
	 }
	
	
	
}
