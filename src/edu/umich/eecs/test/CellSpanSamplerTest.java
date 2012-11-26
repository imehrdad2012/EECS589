package edu.umich.eecs.test;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.umich.eecs.data.CellSpanSampler;
import edu.umich.eecs.dto.CellSpan;

public class CellSpanSamplerTest {
	
	@Test
	public void testSample1() {
		List<CellSpan> spans = new ArrayList<CellSpan>();
		spans.add(Util.makeCellSpanSeconds(1, 1, 1.1, 0, 200));
		spans.add(Util.makeCellSpanSeconds(1, 2, 1.2, 200, 220));
		spans.add(Util.makeCellSpanSeconds(1, 3, 1.3, 220, 235));
		spans.add(Util.makeCellSpanSeconds(1, 4, 1.4, 235, 265));
		
		List<CellSpan> sampledSpans = CellSpanSampler.sample(spans, 60);
		assertEquals(5, sampledSpans.size());

		assertEquals(1, sampledSpans.get(0).getCell().getCellID());
		assertEquals(1, sampledSpans.get(1).getCell().getCellID());
		assertEquals(1, sampledSpans.get(2).getCell().getCellID());
		assertEquals(1, sampledSpans.get(3).getCell().getCellID());
		assertEquals(4, sampledSpans.get(4).getCell().getCellID());
	}
	
	@Test
	public void testSample2() {
		List<CellSpan> spans = new ArrayList<CellSpan>();
		spans.add(Util.makeCellSpanSeconds(1, 1, 1.1, 0, 10));
		spans.add(Util.makeCellSpanSeconds(1, 2, 1.2, 10, 15));
		spans.add(Util.makeCellSpanSeconds(1, 3, 1.3, 15, 20));
		spans.add(Util.makeCellSpanSeconds(1, 4, 1.4, 20, 21));
		
		List<CellSpan> sampledSpans = CellSpanSampler.sample(spans, 60);
		assertEquals(1, sampledSpans.size());

		assertEquals(1, sampledSpans.get(0).getCell().getCellID());
	}
	
	@Test
	public void testSample3() {
		List<CellSpan> spans = new ArrayList<CellSpan>();
		spans.add(Util.makeCellSpanSeconds(1, 1, 1.1, 0, 130));
		spans.add(Util.makeCellSpanSeconds(1, 2, 1.2, 50, 105));
		
		List<CellSpan> sampledSpans = CellSpanSampler.sample(spans, 60);
		assertEquals(4, sampledSpans.size());

		assertEquals(1, sampledSpans.get(0).getCell().getCellID());
		assertEquals(1, sampledSpans.get(1).getCell().getCellID());
		assertEquals(1, sampledSpans.get(2).getCell().getCellID());
		assertEquals(2, sampledSpans.get(3).getCell().getCellID());
	}
}
