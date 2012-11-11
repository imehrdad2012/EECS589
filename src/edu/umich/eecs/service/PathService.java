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
import edu.umich.eecs.dto.PathPiece;
/**
 * This class extends service class and has some simple queries for manipulating persisted
 * objects of PathPiece class.
 * @author Mehrdad
 *
 */


public class PathService extends Service{
	
	public PathService() {
		super();
	}
	
	/**
	 * We save an pathpeice object in one session
	 * @param pp
	 */
	
	public void savePP(PathPiece pp){
		
		Session s=fireTransaction();
		s.saveOrUpdate(pp);
		commitTransaction(s);
	}
	
	/**
	 * This method is used when we want to have speed up
	 * in persisting thousand of pathpiece in one session
	 * @param pp
	 */
	
	public void saveSetofPP(Set<PathPiece> pathPieceSet){
		Session s=fireTransaction();
		
		for(PathPiece pp: pathPieceSet){
			s.saveOrUpdate(pp);
		}
		commitTransaction(s);
	}
	
	/**
	 * 
	 * @return
	 */
	public List<PathPiece> getAllPathPiece(){
		  Session s= fireTransaction();
		   Query query=s.createQuery("from PathPiece");
		   List <PathPiece> allPiece=(List<PathPiece>)query.list();
		   commitTransaction(s);
		  return allPiece;
		
		
	}
	

}
