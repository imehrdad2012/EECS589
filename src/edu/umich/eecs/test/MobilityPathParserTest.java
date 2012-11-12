package edu.umich.eecs.test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.umich.eecs.MobilityPath;
import edu.umich.eecs.MobilityPathParser;
import edu.umich.eecs.dto.CellSpan;

public class MobilityPathParserTest {

	
	private void assertMobilityPath(MobilityPath path, int[] cellIds) {
		assertTrue(path.size() == cellIds.length);
		for(int i = 0; i < cellIds.length; i++) {
			assertTrue(path.getPath().get(i).getCell().getCellID() == cellIds[i]);
		}
	}
	
	@Test
	public void testPaper() {
		List<CellSpan> spans = new ArrayList<CellSpan>();
		spans.add(Util.makeCellSpan(1, 1.1, 0, 4));
		spans.add(Util.makeCellSpan(1, 1.2, 6, 9));
		spans.add(Util.makeCellSpan(1, 1.3, 9, 13));
		spans.add(Util.makeCellSpan(1, 1.5, 15, 23));
		spans.add(Util.makeCellSpan(1, 1.3, 23, 27));
		spans.add(Util.makeCellSpan(1, 1.1, 27, 30));
		spans.add(Util.makeCellSpan(1, 1.2, 41, 45));
		spans.add(Util.makeCellSpan(1, 1.3, 49, 50));
		spans.add(Util.makeCellSpan(1, 1.1, 56, 58));
		spans.add(Util.makeCellSpan(1, 1.3, 58, 61));
		spans.add(Util.makeCellSpan(1, 1.4, 63, 66));
		
		assertTrue(spans.size() == 11);
		
		MobilityPathParser parser = new MobilityPathParser(spans);
		List<MobilityPath> paths = parser.parsePaths(7*60*1000, 5*60*1000);
		
		assertTrue(paths.size() == 4);
		assertMobilityPath(paths.get(0), new int[] { 1, 2, 3, 5 });
		assertMobilityPath(paths.get(1), new int[] { 5, 3, 1 });
		assertMobilityPath(paths.get(2), new int[] { 2, 3 });
		assertMobilityPath(paths.get(3), new int[] { 1, 3, 4 });
	}
	
	@Test
	public void testUnsorted() {
		List<CellSpan> spans = new ArrayList<CellSpan>();
		spans.add(Util.makeCellSpan(1, 1.2, 41, 45));
		spans.add(Util.makeCellSpan(1, 1.2, 6, 9));
		spans.add(Util.makeCellSpan(1, 1.1, 56, 58));
		spans.add(Util.makeCellSpan(1, 1.3, 23, 27));
		spans.add(Util.makeCellSpan(1, 1.3, 9, 13));
		spans.add(Util.makeCellSpan(1, 1.1, 0, 4));
		spans.add(Util.makeCellSpan(1, 1.3, 58, 61));
		spans.add(Util.makeCellSpan(1, 1.5, 15, 23));
		spans.add(Util.makeCellSpan(1, 1.3, 49, 50));
		spans.add(Util.makeCellSpan(1, 1.4, 63, 66));
		spans.add(Util.makeCellSpan(1, 1.1, 27, 30));

		assertTrue(spans.size() == 11);
		
		MobilityPathParser parser = new MobilityPathParser(spans);
		List<MobilityPath> paths = parser.parsePaths(7*60*1000, 5*60*1000);
		
		assertTrue(paths.size() == 4);
		assertMobilityPath(paths.get(0), new int[] { 1, 2, 3, 5 });
		assertMobilityPath(paths.get(1), new int[] { 5, 3, 1 });
		assertMobilityPath(paths.get(2), new int[] { 2, 3 });
		assertMobilityPath(paths.get(3), new int[] { 1, 3, 4 });
	}

}
