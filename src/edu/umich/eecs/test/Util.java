package edu.umich.eecs.test;

import java.sql.Timestamp;

import edu.umich.eecs.dto.Cell;
import edu.umich.eecs.dto.CellSpan;

public class Util {
	public static CellSpan makeCellSpan(int personCode, int transitionId, double cellCode, int startInMinutes,
			int endInMinutes) {
		return makeCellSpanSeconds(personCode, transitionId, cellCode, startInMinutes * 60, endInMinutes * 60);
	}
	
	public static CellSpan makeCellSpanSeconds(int personCode, int transitionId, double cellCode, int startInSeconds,
			int endInSeconds) {
		Cell cell = new Cell(cellCode);
		Timestamp start = new Timestamp(startInSeconds  * 1000);
		Timestamp end = new Timestamp(endInSeconds * 1000);
		return new CellSpan(personCode, cell, transitionId, start, end);
	}
	
}
