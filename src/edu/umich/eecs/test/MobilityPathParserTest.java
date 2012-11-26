package edu.umich.eecs.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import edu.umich.eecs.MobilityPath;
import edu.umich.eecs.MobilityPathFinder;
import edu.umich.eecs.data.Constants;
import edu.umich.eecs.dto.CellSpan;
import edu.umich.eecs.service.CellSpanService;

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
		spans.add(Util.makeCellSpan(1, 1, 1.1, 0, 4));
		spans.add(Util.makeCellSpan(1, 2, 1.2, 6, 9));
		spans.add(Util.makeCellSpan(1, 3, 1.3, 9, 13));
		spans.add(Util.makeCellSpan(1, 4, 1.5, 15, 23));
		spans.add(Util.makeCellSpan(1, 5, 1.3, 23, 27));
		spans.add(Util.makeCellSpan(1, 6, 1.1, 27, 30));
		spans.add(Util.makeCellSpan(1, 7, 1.2, 41, 45));
		spans.add(Util.makeCellSpan(1, 8, 1.3, 49, 50));
		spans.add(Util.makeCellSpan(1, 9, 1.1, 56, 58));
		spans.add(Util.makeCellSpan(1, 10, 1.3, 58, 61));
		spans.add(Util.makeCellSpan(1, 11, 1.4, 63, 66));
		
		assertTrue(spans.size() == 11);
		
		MobilityPathFinder parser = new MobilityPathFinder(spans);
		List<MobilityPath> paths = parser.findMobilityPaths(7*60*1000, 5*60*1000);
		
		assertTrue(paths.size() == 4);
		assertMobilityPath(paths.get(0), new int[] { 1, 2, 3, 5 });
		assertMobilityPath(paths.get(1), new int[] { 5, 3, 1 });
		assertMobilityPath(paths.get(2), new int[] { 2, 3 });
		assertMobilityPath(paths.get(3), new int[] { 1, 3, 4 });
	}

	@Test
	public void testSort() {
		List<CellSpan> spans = new ArrayList<CellSpan>();
		spans.add(Util.makeCellSpanSeconds(1, 2, 1.2, 1094336743, 1094336745));
		spans.add(Util.makeCellSpanSeconds(1, 1, 1.3, 1094329543, 1094329549));
		Collections.sort(spans);
		assertEquals(3, spans.get(0).getCell().getCellID());
		assertEquals(2, spans.get(1).getCell().getCellID());
	}
	
	@Test
	public void testDataBase() {
		//
		// This test can take a long time to run.
		//
		CellSpanService svc = new CellSpanService();
		List<Integer> personIds = svc.getAllUsers();
		for(Integer personId : personIds) {
			System.out.println(personId + "/" + personIds.size());
			List<CellSpan> cellSpans = svc.getCPByUserID(personId.intValue());
			long maxTime = 0, minTime = 1 << 30;
			for(CellSpan span : cellSpans) {
				if(span.getStartTime().getTime() > maxTime) {
					maxTime = span.getStartTime().getTime();
				} if(span.getStartTime().getTime() < minTime) {
					minTime = span.getStartTime().getTime();
				}
			}
			System.out.println("MinTime: " + minTime + "/ MaxTime: " + maxTime);
			MobilityPathFinder pathFinder = new MobilityPathFinder(cellSpans);
			List<MobilityPath> personsMobiliyPaths = pathFinder.findMobilityPaths(
					Constants.durationThresholdInMs, Constants.durationThresholdInMs);
			for(MobilityPath path : personsMobiliyPaths) {
				assertTrue(path.isConsistent(Constants.durationThresholdInMs, Constants.transitionThresholdInMs));
			}
		}
	}

}
