package edu.umich.eecs.statistics;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import edu.umich.eecs.dto.Cell;
import edu.umich.eecs.dto.CellKey;

public class CellListParser {
	
	/**
	 * Reads a list of cells that are specified like this:
	 * CELLID   LAC   MNC   MCC
	 * The method assumes there is no header.
	 * @param scanner
	 * @return
	 */
	public static	List<CellKey> fromFile(Scanner scanner) {
		List<CellKey> cells = new ArrayList<CellKey>();
		while (scanner.hasNext()) {
			int cellId = scanner.nextInt();
			int areaId = scanner.nextInt();
			int networkId = scanner.nextInt();
			int countryId = scanner.nextInt();
			CellKey cellKey = new CellKey(countryId, cellId, areaId,
					networkId);
			cells.add(cellKey);
		}
		return cells;
	}
}
