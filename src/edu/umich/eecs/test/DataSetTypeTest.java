package edu.umich.eecs.test;

import edu.umich.eecs.dto.*;
import edu.umich.eecs.service.OscillationService;
import static org.junit.Assert.*;

import org.junit.Test;

public class DataSetTypeTest {

	@Test
	public void test() {
		DataSetType dataset = DataSetType.RealityMining;
		assertEquals(0, dataset.asInt());
		dataset = DataSetType.NokiaChallenge;
		assertEquals(1, dataset.asInt());
	}
	
//	@Test
//	public void test2() {
//		OscillationService nokiaSvc = new OscillationService(DataSetType.NokiaChallenge);
//		OscillationService rmSvc = new OscillationService(DataSetType.RealityMining);
//		assertEquals(12287, nokiaSvc.getAllOscillatingPairs().size());
//		assertEquals(29615, rmSvc.getAllOscillatingPairs().size());
//	}

}
