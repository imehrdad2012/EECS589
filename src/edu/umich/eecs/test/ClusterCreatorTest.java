package edu.umich.eecs.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import edu.umich.eecs.dto.OscillatingCellTowerPair;
import edu.umich.eecs.service.OscillationService;

public class ClusterCreatorTest {

	@Test
	public void testDatabaseEdgeOrder() {
		OscillationService os= new OscillationService();
		List<OscillatingCellTowerPair>list =os.getOrderedOscillationPairs();
	
		
	}

}
