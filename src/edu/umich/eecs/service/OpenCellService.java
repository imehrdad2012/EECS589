package edu.umich.eecs.service;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

import edu.umich.eecs.dto.CellKey;
import edu.umich.eecs.dto.Cluster;
import edu.umich.eecs.dto.OpenCell;
import edu.umich.eecs.dto.OscillatingCellTowerPair;
import edu.umich.eecs.logger.LogClass;

public class OpenCellService extends Service {
	/**
	 * This method save one OscillationEdge in one session.
	 * @param pp
	 */
	
	public void saveLC(OpenCell lc){
		Session s=fireTransaction();
		s.saveOrUpdate(lc);
		commitTransaction(s);
	}
	
	public void saveListToOpenCell(List<OpenCell> locations){
		Session s=fireTransaction();
		for(OpenCell l: locations){
			s.saveOrUpdate(l);
		}
		commitTransaction(s);
	}

	public OpenCell getLocation(CellKey key) {
		return getLocation(key.getCountryID(), key.getNetworkID(), key.getAreaID(), key.getCellID());
	}

	public OpenCell getLocation(int cc, int nc, int aid, int cid){
	
		Session s= fireTransaction();
		Query query = s.createQuery("from OpenCell where cellid=:cid and mnc=:nc and mcc=:cc and lac=:aid ");
		query.setInteger("cid", cid);
		query.setInteger("nc",nc );
		query.setInteger("aid", aid);
		query.setInteger("cc", cc);
		try 
		{
			List<OpenCell> l= (List<OpenCell>)query.list();
			double avgLon=0;
			double avgLat=0;
			for(OpenCell oc: l){
				avgLon+=oc.getLongitude();
				avgLat+=oc.getLatitude();
			}
			avgLon=avgLon/l.size();
			avgLat=avgLat/l.size();
			
			return new OpenCell(avgLat, avgLon, cc, nc, aid, cid);
			
		} catch (Exception e) {

			return null;
		}			
			
		
		
		 
	 }

}
