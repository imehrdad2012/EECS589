package edu.umich.eecs.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import edu.umich.eecs.dto.Cell;
import edu.umich.eecs.dto.CellSpan;

/**
 * This class contains queries to the cellspan table.
 * 
 * @author Mehrdad
 * 
 */

public class CellSpanService extends Service implements CellSpanServiceInterface {

	public CellSpanService() {
		super();
	}

	public void saveCP(CellSpan cp) {

		Session s = fireTransaction();
		s.saveOrUpdate(cp);
		commitTransaction(s);
	}
	
	@SuppressWarnings("unchecked")
	public List<CellSpan> getCPByUserID(int pid) {

		Session s = fireTransaction();
		Query query = s
				.createQuery("from CellSpan where key.personid=:pid order by"
						+ " key.transitionId ");
		query.setInteger("pid", pid);
		List<CellSpan> lcp = (List<CellSpan>) query.list();
		return lcp;
	}

	@SuppressWarnings("unchecked")
	public List<CellSpan> getAllCellSpans() {
		Session s = fireTransaction();
		Query query = s.createQuery("from CellSpan");
		List<CellSpan> cells = (List<CellSpan>) query.list();
		return cells;
	}


	@SuppressWarnings("unchecked")
	public List<Cell> getAllCells() {
		Session s = fireTransaction();
		Query query = s.createQuery("select distinct(cellspan.cell) from CellSpan cellspan");
		List<Cell> cells = (List<Cell>) query.list();
		return cells;

	}
	

	@SuppressWarnings("unchecked")
	public Map<Integer, Long> getCountAllCellsByAreaID(List<Integer> areas) {
		Map<Integer, Long> result= new HashMap<Integer, Long>();
		Session s = fireTransaction();
		
		for(int i:areas){
			Query query = s
					.createQuery("select count(distinct cellspan.cell.cellkey.cellID) from CellSpan cellspan where " +
							"cellspan.cell.cellkey.areaID=:id ");
			query.setInteger("id", i);
			List<Long> cells = (List<Long>) query.list();	
			result.put(i, cells.get(0));
		}
		
	
		return result;

	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> getAllAreaID() {
		Session s = fireTransaction();
		Query query = s.createQuery("select distinct(cellspan.cell.cellkey.areaID) from CellSpan " +
				"cellspan");
		List<Integer> areas = (List<Integer>) query.list();
		areas.remove(new Integer(0));
		return areas;

	}

	@SuppressWarnings("unchecked")
	public List<Integer> getAllUsers() {
		Session s = fireTransaction();
		Query query = s
				.createQuery("select key.personid from CellSpan group by key.personid");
		List<Integer> users = (List<Integer>) query.list();
		return users;

	}

}
