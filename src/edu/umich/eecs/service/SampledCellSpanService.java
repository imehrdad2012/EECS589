package edu.umich.eecs.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import edu.umich.eecs.dto.AbstractCellSpan;
import edu.umich.eecs.dto.Cell;
import edu.umich.eecs.dto.CellSpan;
import edu.umich.eecs.dto.SampledCellSpan;

public class SampledCellSpanService extends Service {

	public SampledCellSpanService() {
		super();
	}

	public void saveCP(CellSpan cp) {
		SampledCellSpan sampledCp = new SampledCellSpan(cp);
		Session s = fireTransaction();
		s.saveOrUpdate(sampledCp);
		commitTransaction(s);
	}

	public List<CellSpan> getCPByUserID(int pid) {

		Session s = fireTransaction();
		Query query = s
				.createQuery("from SampledCellSpan where key.personid=:pid order by"
						+ " key.transitionId ");
		query.setInteger("pid", pid);
		List<SampledCellSpan> sampledSpans = (List<SampledCellSpan>) query
				.list();
		List<CellSpan> cellSpans = SampledCellSpan.listAsCellSpan(sampledSpans);
		commitTransaction(s);
		return cellSpans;
	}

	public List<CellSpan> getAllCellSpans() {
		Session s = fireTransaction();
		Query query = s.createQuery("from SampledCellSpan");
		List<SampledCellSpan> sampledSpans = (List<SampledCellSpan>) query.list();
		List<CellSpan> cellSpans = SampledCellSpan.listAsCellSpan(sampledSpans);
		return cellSpans;
	}

	public List<Cell> getAllCells() {

		Session s = fireTransaction();
		Query query = s.createQuery("select cell from SampledCellSpan group by cell");
		List<Cell> cells = (List<Cell>) query.list();
		return cells;

	}

	public List<Integer> getAllUsers() {

		Session s = fireTransaction();
		Query query = s
				.createQuery("select key.personid from SampledCellSpan group by key.personid");
		List<Integer> users = (List<Integer>) query.list();
		return users;

	}

}
