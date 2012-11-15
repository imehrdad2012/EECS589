package edu.umich.eecs.service;

import java.util.Collection;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import edu.umich.eecs.dto.OscillatingCellTowerPair;

/**
 * This class extends service class and has some simple queries for manipulating persisted
 * objects of Oscillation class.
 * @author Mehrdad
 *
 */


public class OscillationService extends Service{
	
	public OscillationService() {
		super();
	}
	
	/**
	 * This method save one OscillationEdge in one session.
	 * @param pp
	 */
	
	public void saveOE(OscillatingCellTowerPair oe){
		Session s=fireTransaction();
		s.saveOrUpdate(oe);
		commitTransaction(s);
	}
	
	/**
	 * This method is used when we want to have speed up
	 * in persisting thousand of OscillationEdge in one session.
	 * @param osEdgeSet
	 */
	
	public void saveSetofOE(Collection<OscillatingCellTowerPair> osEdgeSet){
		Session s=fireTransaction();
		
		//
		// Commiting every once in a while makes it go _much_ faster
		// and use _a lot_ less memory.
		//
		int commitEveryX = 1000;
		int i = 0;
		for(OscillatingCellTowerPair oe:osEdgeSet){
			if(++i % commitEveryX == 0) {
				System.out.println("Intermediate commit... (" + i + "/" + osEdgeSet.size() + ")");
				commitTransaction(s);
				s = fireTransaction();
			}
			s.saveOrUpdate(oe);
		}
		commitTransaction(s);
	}
	
	/**
	 * This method returns all OscillationEdges extracted.
	 * @return
	 */
	public List<OscillatingCellTowerPair> getAllOscillatingPairs(){
		  Session s= fireTransaction();
		   Query query=s.createQuery("from OscillationEdge");
		   List <OscillatingCellTowerPair> allEdges=(List<OscillatingCellTowerPair>)query.list();
		   commitTransaction(s);
		  return allEdges;
		
		
	}
	

}
