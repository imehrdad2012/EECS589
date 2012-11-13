package edu.umich.eecs.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import edu.umich.eecs.dto.Cell;
import edu.umich.eecs.dto.CellSpan;
import edu.umich.eecs.dto.OscillationEdge;

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
	
	public void saveOE(OscillationEdge oe){
		Session s=fireTransaction();
		s.saveOrUpdate(oe);
		commitTransaction(s);
	}
	
	/**
	 * This method is used when we want to have speed up
	 * in persisting thousand of OscillationEdge in one session.
	 * @param osEdgeSet
	 */
	
	public void saveSetofOE(Set<OscillationEdge> osEdgeSet){
		Session s=fireTransaction();
		
		for(OscillationEdge oe:osEdgeSet){
			s.saveOrUpdate(oe);
		}
		commitTransaction(s);
	}
	
	/**
	 * This method returns all OscillationEdges extracted.
	 * @return
	 */
	public List<OscillationEdge> getAllOE(){
		  Session s= fireTransaction();
		   Query query=s.createQuery("from OscillationEdge");
		   List <OscillationEdge> allEdges=(List<OscillationEdge>)query.list();
		   commitTransaction(s);
		  return allEdges;
		
		
	}
	

}
