package edu.umich.eecs.service;

import java.util.List;
import java.util.Map;

import edu.umich.eecs.dto.Cell;
import edu.umich.eecs.dto.CellSpan;

public interface CellSpanServiceInterface {
	public void saveCP(CellSpan cp) ;
	public List<CellSpan> getCPByUserID(int pid);
	public List<CellSpan> getAllCellSpans();
	public List<Cell> getAllCells();
	public List<Integer> getAllUsers();
	public List<Integer> getAllAreaID();
	public Map<Integer, Long> getCountAllCellsByAreaID(List<Integer> areas);
}