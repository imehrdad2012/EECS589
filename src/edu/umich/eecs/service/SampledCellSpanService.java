package edu.umich.eecs.service;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import edu.umich.eecs.dto.Cell;
import edu.umich.eecs.dto.CellSpan;
import edu.umich.eecs.dto.SampledCellSpan;

public class SampledCellSpanService extends Service implements CellSpanServiceInterface {

	public SampledCellSpanService() {
		super();
	}

	public void saveCP(CellSpan cp) {
		SampledCellSpan sampledCp = new SampledCellSpan(cp);
		Session s = fireTransaction();
		s.saveOrUpdate(sampledCp);
		commitTransaction(s);
	}

	@SuppressWarnings("unchecked")
	public List<CellSpan> getCPByUserID(int pid) {

		Session s = fireTransaction();
		Query query = s
				.createQuery("from SampledCellSpan where key.personid=:pid order by"
						+ " key.transitionId ");
		query.setInteger("pid", pid);
		List<SampledCellSpan> sampledSpans = (List<SampledCellSpan>) query
				.list();
		List<CellSpan> cellSpans = SampledCellSpan.listAsCellSpan(sampledSpans);
		closeSession(s);
		return cellSpans;
	}
	
	@SuppressWarnings("unchecked")
	public List<CellSpan> getAllCellSpans() {
		Session s = fireTransaction();
		Query query = s.createQuery("from SampledCellSpan");
		List<SampledCellSpan> sampledSpans = (List<SampledCellSpan>) query.list();
		List<CellSpan> cellSpans = SampledCellSpan.listAsCellSpan(sampledSpans);
		closeSession(s);
		return cellSpans;
	}
	
	@SuppressWarnings("unchecked")
	public List<Cell> getAllCells() {

		Session s = fireTransaction();
		Query query = s.createQuery("select cell from SampledCellSpan group by cell");
		List<Cell> cells = (List<Cell>) query.list();
		closeSession(s);
		return cells;

	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> getAllUsers() {

		Session s = fireTransaction();
		Query query = s
				.createQuery("select key.personid from SampledCellSpan group by key.personid");
		List<Integer> users = (List<Integer>) query.list();
		closeSession(s);
		return users;

	}

}
