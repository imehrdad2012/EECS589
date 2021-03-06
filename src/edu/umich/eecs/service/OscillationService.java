package edu.umich.eecs.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import edu.umich.eecs.dto.Cell;
import edu.umich.eecs.dto.DataSetType;
import edu.umich.eecs.dto.OscillatingCellTowerPair;

/**
 * This class extends service class and has some simple queries for manipulating persisted
 * objects of Oscillation class.
 * @author Mehrdad
 *
 */


public class OscillationService extends Service{
	
	private DataSetType dataset;
	
	public OscillationService(DataSetType dataset) {
		super();
		this.dataset = dataset;
	}
	
	/**
	 * This method save one OscillationEdge in one session.
	 * @param 
	 */
	
	public void saveOE(OscillatingCellTowerPair oe){
		oe.setDataSetType(dataset);
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
			oe.setDataSetType(dataset);
			s.saveOrUpdate(oe);
		}
		commitTransaction(s);
	}
	
	/**
	 * This method returns all OscillationEdges extracted.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<OscillatingCellTowerPair> getAllOscillatingPairs(){
		  Session s= fireTransaction();
		  Query query=s.createQuery("from OscillatingCellTowerPair where dataset =:dataset");
		  query.setInteger("dataset", dataset.asInt());
		   List <OscillatingCellTowerPair> allEdges=(List<OscillatingCellTowerPair>)query.list();
		  return allEdges;
		
		
	}
	
	/**
	 * This method is used for retrieving an ascending ordered lists of oscillation edges by their weights.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<OscillatingCellTowerPair> getOrderedOscillationPairs(){
		  Session s= fireTransaction();
		Criteria crit = s.createCriteria(OscillatingCellTowerPair.class);
		crit.add(Restrictions.eq("dataset", dataset));
		crit.addOrder(Order.asc("supportRate"));
		List<OscillatingCellTowerPair> allEdges = (List<OscillatingCellTowerPair>) crit
				.list();
		return allEdges;
		
	}
	
	/**
	 * computes all cells that are in oscillation tables.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Set<Cell> getAllOsiCells(){
		Session s= fireTransaction();
		Query query1= s.createQuery("select distinct(cellTowerPair.cell1)" +
				" from OscillatingCellTowerPair where dataset =:dataset");
		query1.setInteger("dataset", dataset.asInt());
		Query query2= s.createQuery("select distinct(cellTowerPair.cell2)" +
				" from OscillatingCellTowerPair where dataset =:dataset");
		query2.setInteger("dataset", dataset.asInt());
		Set<Cell> cell = new HashSet<Cell>();
		cell.addAll(query1.list());
		cell.addAll(query2.list());
		return cell;
		
		
	}
	
	@SuppressWarnings("unchecked")
	public Map<Integer, Long> getCountOfOsiCellsByAreaId(List<Integer> areas){
		Session s= fireTransaction();
		Map<Integer, Long> result= new HashMap<>();
		
		for(Integer i: areas){
			Query query1= s.createQuery("select distinct(pair.cellTowerPair.cell1.cellkey.cellID) " +
					"from OscillatingCellTowerPair pair where pair.dataset =:dataset" +
					" and pair.cellTowerPair.cell1.cellkey.areaID=:id ");
			query1.setInteger("dataset", dataset.asInt());
			query1.setInteger("id", i);
			
			Query query2= s.createQuery("select distinct(pair.cellTowerPair.cell2.cellkey.cellID) " +
					"from OscillatingCellTowerPair pair where pair.dataset =:dataset " +
					"and pair.cellTowerPair.cell2.cellkey.areaID=:id");
			query2.setInteger("dataset", dataset.asInt());
			query2.setInteger("id", i);

			Set<Integer> cell = new HashSet<Integer>();
			cell.addAll(query1.list());
			cell.addAll(query2.list());
			
			result.put(i, (long)cell.size());
			
		}
	
		return result;
		
		
	}
	
	
	
	
	

}
