package edu.umich.eecs.service;

import java.util.List;

import org.hibernate.Session;

import edu.umich.eecs.dto.Cluster;
import edu.umich.eecs.dto.Location;
import edu.umich.eecs.dto.OscillatingCellTowerPair;

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
	
	
}
