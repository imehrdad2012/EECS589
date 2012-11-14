package edu.umich.eecs.data;
import java.util.Collection;
import java.util.List;

import edu.umich.eecs.*;
import edu.umich.eecs.dto.CellSpan;
import edu.umich.eecs.dto.OscillatingCellTowerPair;
import edu.umich.eecs.service.CellSpanService;
import edu.umich.eecs.util.Tic;

public class OscillationGraphGenerator {
	
	public void computeOscillationEdges(List<CellSpan> spans) {
		Tic clock = new Tic(true);
		clock.tic();
		MobilityPathFinder pathFinder = new MobilityPathFinder(spans);
		List<MobilityPath> mobilityPaths = pathFinder.findMobilityPaths(
				Constants.durationThresholdInMs, Constants.durationThresholdInMs);
		System.out.println(mobilityPaths.size() + " mobility paths found in " + spans.size() + " spans.");
		clock.toctic();
		Collection<OscillatingCellTowerPair> oscillations   =
				OscilliatingPairFinder.findOscillationSupport(mobilityPaths, Constants.oscillationThreshold);
		System.out.println(oscillations.size() + " oscillations found.");
		clock.toc();
		
	}
	
	public static void main(String[] args) {
		OscillationGraphGenerator gen = new OscillationGraphGenerator();
		gen.computeOscillationEdges(new CellSpanService().getCPByUserID(4));
	}
}
