package edu.umich.eecs.test;

import java.sql.Timestamp;

import edu.umich.eecs.dto.Cell;
import edu.umich.eecs.dto.CellSpan;

public class Util {
	public static CellSpan makeCellSpan(int personCode, double cellCode, int startInMinutes,
			int endInMinutes) {
		Cell cell = new Cell(cellCode);
		Timestamp start = new Timestamp(startInMinutes * 60 * 1000);
		Timestamp end = new Timestamp(endInMinutes * 60 * 1000);
		return new CellSpan(personCode, cell, start, end);
	}
	
}
