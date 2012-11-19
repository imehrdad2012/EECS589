package edu.umich.eecs.test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import edu.umich.eecs.MobilityPath;
import edu.umich.eecs.OscilliatingPairFinder;
import edu.umich.eecs.SwitchingCellTowerPair;
import edu.umich.eecs.dto.CellSpan;
import edu.umich.eecs.dto.CellTowerPair;
import edu.umich.eecs.dto.OscillatingCellTowerPair;

public class OscillatingPairFinderTest {

	@Test
	public void testOneMPPairs() {
		MobilityPath path = new MobilityPath();
		CellSpan x = Util.makeCellSpan(1, 1, 1.1, 0, 0);
		CellSpan y = Util.makeCellSpan(1, 2, 1.2, 0, 0);
		CellSpan v = Util.makeCellSpan(1, 3, 1.3, 0, 0);
		CellSpan w = Util.makeCellSpan(1, 4, 1.4, 0, 0);
		
		path.addToPath(x);
		path.addToPath(y);
		path.addToPath(x);
		path.addToPath(w);
		path.addToPath(v);
		path.addToPath(w);
		path.addToPath(y);
		
		Collection<SwitchingCellTowerPair> pairs = OscilliatingPairFinder.findSwitchesInMobilityPath(path);
		assertTrue(pairs.size() == 6);
		
		CellTowerPair xy = new CellTowerPair(x.getCell(), y.getCell());
		CellTowerPair xw = new CellTowerPair(x.getCell(), w.getCell());
		CellTowerPair xv = new CellTowerPair(x.getCell(), v.getCell());
		CellTowerPair yw = new CellTowerPair(y.getCell(), w.getCell());
		CellTowerPair yv = new CellTowerPair(y.getCell(), v.getCell());
		CellTowerPair wv = new CellTowerPair(w.getCell(), v.getCell());
		
		for(SwitchingCellTowerPair pair : pairs) {
			if(pair.pairEquals(xy)) assertEquals(3, pair.numberOfSwitches);
			else if(pair.pairEquals(xw)) assertEquals(1, pair.numberOfSwitches);
			else if(pair.pairEquals(xv)) assertEquals(1, pair.numberOfSwitches);
			else if(pair.pairEquals(yw)) assertEquals(2, pair.numberOfSwitches);
			else if(pair.pairEquals(yv)) assertEquals(2, pair.numberOfSwitches);
			else if(pair.pairEquals(wv)) assertEquals(2, pair.numberOfSwitches);
			
		}
	}
	
	@Test
	public void testOneMPPairsSupport() {
		MobilityPath path = new MobilityPath();
		CellSpan x = Util.makeCellSpan(1, 1, 1.1, 0, 0);
		CellSpan y = Util.makeCellSpan(1, 2, 1.2, 0, 0);
		CellSpan v = Util.makeCellSpan(1, 3, 1.3, 0, 0);
		CellSpan w = Util.makeCellSpan(1, 4, 1.4, 0, 0);
		
		path.addToPath(x);
		path.addToPath(y);
		path.addToPath(x);
		path.addToPath(w);
		path.addToPath(v);
		path.addToPath(w);
		path.addToPath(y);
		
		Collection<MobilityPath> paths = new ArrayList<MobilityPath>();
		paths.add(path);
		
		Collection<OscillatingCellTowerPair> pairs = OscilliatingPairFinder.findOscillationSupport(paths, 2);
		assertTrue(pairs.size() == 4);
		
		CellTowerPair xy = new CellTowerPair(x.getCell(), y.getCell());
		CellTowerPair xw = new CellTowerPair(x.getCell(), w.getCell());
		CellTowerPair xv = new CellTowerPair(x.getCell(), v.getCell());
		CellTowerPair yw = new CellTowerPair(y.getCell(), w.getCell());
		CellTowerPair yv = new CellTowerPair(y.getCell(), v.getCell());
		CellTowerPair wv = new CellTowerPair(w.getCell(), v.getCell());
		
		for(OscillatingCellTowerPair pair : pairs) {
			if(pair.pairEquals(xy)) {
				assertEquals(1, pair.getNumberOscillations());
			}
			else if(pair.pairEquals(yw)) {
				assertEquals(1, pair.getNumberOscillations());
			}
			else if(pair.pairEquals(yv)) {
				assertEquals(1, pair.getNumberOscillations());
			}
			else if(pair.pairEquals(wv)) {
				assertEquals(1, pair.getNumberOscillations());
			}
			assertEquals(1, pair.getTotalNumberSwitches());
		}
	}
	
	@Test
	public void testTwoMPsPairsSupport() {
		MobilityPath path1 = new MobilityPath();
		MobilityPath path2 = new MobilityPath();
		CellSpan x = Util.makeCellSpan(1, 2, 1.1, 0, 0);
		CellSpan y = Util.makeCellSpan(1, 3, 1.2, 0, 0);
		CellSpan v = Util.makeCellSpan(1, 4, 1.3, 0, 0);
		CellSpan w = Util.makeCellSpan(1, 5, 1.4, 0, 0);
		
		path1.addToPath(x);
		path1.addToPath(y);
		path1.addToPath(x);
		path1.addToPath(w);
		path1.addToPath(v);
		path1.addToPath(w);
		path1.addToPath(y);
		
		path2.addToPath(x);
		path2.addToPath(w);
		path2.addToPath(y);
		path2.addToPath(w);
		path2.addToPath(x);
		path2.addToPath(y);
		
		Collection<MobilityPath> paths = new ArrayList<MobilityPath>();
		paths.add(path1);
		paths.add(path2);
		
		
		Collection<OscillatingCellTowerPair> pairs = OscilliatingPairFinder.findOscillationSupport(paths, 2);
		assertTrue(pairs.size() == 5);
		
		CellTowerPair xy = new CellTowerPair(x.getCell(), y.getCell());
		CellTowerPair xw = new CellTowerPair(x.getCell(), w.getCell());
		CellTowerPair xv = new CellTowerPair(x.getCell(), v.getCell());
		CellTowerPair yw = new CellTowerPair(y.getCell(), w.getCell());
		CellTowerPair yv = new CellTowerPair(y.getCell(), v.getCell());
		CellTowerPair wv = new CellTowerPair(w.getCell(), v.getCell());
		
		for(OscillatingCellTowerPair pair : pairs) {
			if(pair.pairEquals(xy)) {
				assertEquals(2, pair.getNumberOscillations());
				assertEquals(2, pair.getTotalNumberSwitches());
				assertEquals(1.0, pair.support(), 0.0000000001);
			}
			else if(pair.pairEquals(xw)) {
				assertEquals(1, pair.getNumberOscillations());
				assertEquals(2, pair.getTotalNumberSwitches());
				assertEquals(0.5, pair.support(), 0.0000000001);
			}
			else if(pair.pairEquals(xv)) {
				assertEquals(0, pair.getNumberOscillations());
				assertEquals(1, pair.getTotalNumberSwitches());
				assertEquals(0.0, pair.support(), 0.0000000001);
			}
			else if(pair.pairEquals(yw)) {
				assertEquals(2, pair.getNumberOscillations());
				assertEquals(2, pair.getTotalNumberSwitches());
				assertEquals(1.0, pair.support(), 0.0000000001);
			}
			else if(pair.pairEquals(yv)) {
				assertEquals(1, pair.getNumberOscillations());
				assertEquals(1, pair.getTotalNumberSwitches());
				assertEquals(1.0, pair.support(), 0.0000000001);
			}
			else if(pair.pairEquals(wv)) {
				assertEquals(1, pair.getNumberOscillations());
				assertEquals(1, pair.getTotalNumberSwitches());
				assertEquals(1.0, pair.support(), 0.0000000001);
			}
			
		}
	}

}
