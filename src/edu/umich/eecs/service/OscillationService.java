package edu.umich.eecs.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import edu.umich.eecs.dto.Cell;
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
		   Query query=s.createQuery("from OscillatingCellTowerPair  ");
		   List <OscillatingCellTowerPair> allEdges=(List<OscillatingCellTowerPair>)query.list();
		   commitTransaction(s);
		  return allEdges;
		
		
	}
	
	/**
	 * This method is used for retrieving an ascending ordered lists of oscillation edges by their weights.
	 * @return
	 */
	
	public List<OscillatingCellTowerPair> getOrderedOscillationPairs(){
		  Session s= fireTransaction();
		  Criteria crit = s.createCriteria(OscillatingCellTowerPair.class);
		  crit.addOrder(Order.asc("supportRate"));  
		   List <OscillatingCellTowerPair> allEdges=(List<OscillatingCellTowerPair>) crit.list();
		   commitTransaction(s);
		  return allEdges;
		
		
	}
	
	/**
	 * computes all cells that are in oscillation tables.
	 * @return
	 */
	public Set<Cell> getAllOsiCells(){
		Session s= fireTransaction();
		Query query1= s.createQuery("select distinct(cellTowerPair.cell1) from OscillatingCellTowerPair");
		Query query2= s.createQuery("select distinct(cellTowerPair.cell2) from OscillatingCellTowerPair");
		Set<Cell> cell = new HashSet<Cell>();
		cell.addAll(query1.list());
		cell.addAll(query2.list());
		return cell;
		
		
	}
	
	
	
	
	

}
