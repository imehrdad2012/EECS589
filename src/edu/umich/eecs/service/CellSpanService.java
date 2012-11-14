package edu.umich.eecs.service;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import edu.umich.eecs.dto.Cell;
import edu.umich.eecs.dto.CellSpan;


/**
 * This class  contains queries to the cellspan table.
 * @author Mehrdad
 *
 */


public class CellSpanService extends Service {


	public CellSpanService() {
		super();
	}

	public void saveCP(CellSpan cp){
		
		Session s=fireTransaction();
		s.saveOrUpdate(cp);
		commitTransaction(s);
	}
	
	public  List< CellSpan> getCPByUserID(int pid){
		
		  Session s= fireTransaction();
		   Query query=s.createQuery("from CellSpan where key.personid=:pid order by" +
		   		" key.starttime ");
		   query.setInteger("pid", pid);
		   List <CellSpan> lcp=(List<CellSpan>)query.list();
		   commitTransaction(s);
	  return lcp; 
	}
	
	public List<CellSpan> getAllCellSpans() {
		Session s= fireTransaction();
		Query query= s.createQuery("from CellSpan" );
		List<CellSpan> cells=(List<CellSpan>)query.list();
		return cells;
	}

	public List<Cell> getAllCells(){
		
		Session s= fireTransaction();
		Query query= s.createQuery("select cell from CellSpan group by cell" );
		List<Cell> cells=(List<Cell>)query.list();
		return cells;
		
	}
	
public List<Integer> getAllUsers(){
		
		Session s= fireTransaction();
		Query query= s.createQuery("select key.personid from CellSpan group by key.personid" );
		List<Integer> users=(List<Integer>)query.list();
		return users;
		
	}
	
	
	
	
	
}

