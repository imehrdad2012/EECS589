package edu.umich.eecs.service;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;

import edu.umich.eecs.dto.Cell;

import edu.umich.eecs.dto.DataSetType;

import edu.umich.eecs.dto.CellKey;
import edu.umich.eecs.dto.GpsPosition;


public class CellService  extends Service {
	public void saveCell(Cell cp) {
		Session s = fireTransaction();
		s.saveOrUpdate(cp);
		commitTransaction(s);
	}
	
	/**
	 * This return all cells in our database.
	 * Cells are limited to any dataset
	 */
	
	@SuppressWarnings("unchecked")
	public List<Cell> getAllCells() {
		Session s = fireTransaction();
		Query query = s.createQuery("from Cell");
		List<Cell> cells = (List<Cell>) query.list();
		return cells;
	}
	


	public Cell getCell(CellKey key) {
		Session s = fireTransaction();
		Query query = s.createQuery(
				"from Cell where cellkey.cellID=:cellid and cellkey.areaID=:areaid and "+
				"cellkey.networkID=:networkid and cellkey.countryID=:countryid");
		
		query.setInteger("cellid", key.getCellID());
		query.setInteger("areaid", key.getAreaID());
		query.setInteger("networkid", key.getNetworkID());
		query.setInteger("countryid", key.getCountryID());
		return (Cell) query.uniqueResult();
		
	}
	
	public boolean exists(CellKey key) {
		Session s = fireTransaction();
		Query query = s.createQuery(
				"from Cell where cellkey.cellID=:cellid and cellkey.areaID=:areaid and "+
				"cellkey.networkID=:networkid and cellkey.countryID=:countryid");
		
		query.setInteger("cellid", key.getCellID());
		query.setInteger("areaid", key.getAreaID());
		query.setInteger("networkid", key.getNetworkID());
		query.setInteger("countryid", key.getCountryID());
		
		return !query.list().isEmpty();
	}

	public void saveCells(Collection<Cell> cells){
		Session s=fireTransaction();
		
		//
		// Commiting every once in a while makes it go _much_ faster
		// and use _a lot_ less memory.
		//
		int commitEveryX = 1000;
		int i = 0;
		for(Cell cell : cells){
			if(++i % commitEveryX == 0) {
				System.out.println("Intermediate commit... (" + i + "/" + cells.size() + ")");
				commitTransaction(s);
				s = fireTransaction();
			}
			s.saveOrUpdate(cell);
		}
		commitTransaction(s);
	}
}
