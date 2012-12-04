package edu.umich.eecs.service;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import edu.umich.eecs.dto.Cell;
import edu.umich.eecs.dto.CellSpan;
import edu.umich.eecs.dto.MDCCellSpan;

public class MDCCellSpanService extends Service implements CellSpanServiceInterface {

	public MDCCellSpanService() {
		super();
	}

	public void saveCP(CellSpan cp) {
		MDCCellSpan sampledCp = new MDCCellSpan(cp);
		Session s = fireTransaction();
		s.saveOrUpdate(sampledCp);
		commitTransaction(s);
	}

	@SuppressWarnings("unchecked")
	public List<CellSpan> getCPByUserID(int pid) {

		Session s = fireTransaction();
		Query query = s
				.createQuery("from MDCCellSpan where key.personid=:pid order by"
						+ " key.transitionId ");
		query.setInteger("pid", pid);
		List<MDCCellSpan> mdcSpans = (List<MDCCellSpan>) query.list();
		List<CellSpan> cellSpans = MDCCellSpan.listAsCellSpan(mdcSpans);
		commitTransaction(s);
		return cellSpans;
	}
	
	@SuppressWarnings("unchecked")
	public List<CellSpan> getAllCellSpans() {
		Session s = fireTransaction();
		Query query = s.createQuery("from MDCCellSpan");
		List<MDCCellSpan> mdcSpans = (List<MDCCellSpan>) query.list();
		List<CellSpan> cellSpans = MDCCellSpan.listAsCellSpan(mdcSpans);
		return cellSpans;
	}

	@SuppressWarnings("unchecked")
	public List<Cell> getAllCells() {
		Session s = fireTransaction();
		Query query = s
				.createQuery("select mdc.cell from MDCCellSpan mdc group by mdc.cell");
		List<Cell> cells = (List<Cell>) query.list();
	
		return cells;

	}

	@SuppressWarnings("unchecked")
	public List<Integer> getAllUsers() {

		Session s = fireTransaction();
		Query query = s
				.createQuery("select key.personid from MDCCellSpan group by key.personid");
		List<Integer> users = (List<Integer>) query.list();
		return users;

	}

}
