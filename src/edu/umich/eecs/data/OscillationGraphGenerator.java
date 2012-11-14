package edu.umich.eecs.data;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.umich.eecs.*;
import edu.umich.eecs.dto.CellSpan;
import edu.umich.eecs.dto.OscillatingCellTowerPair;
import edu.umich.eecs.service.CellSpanService;
import edu.umich.eecs.util.Tic;

public class OscillationGraphGenerator {
	public static Tic clock = new Tic(true);
	
	public void computeOscillationEdges(CellSpanService svc) {
		clock.tic();
		List<Integer> personIds = svc.getAllUsers();
		clock.toc("Obtained " + personIds.size() + " person IDs");
		List<MobilityPath> mobilityPaths = new ArrayList<MobilityPath>(personIds.size() * 1000);
		for(Integer personId : personIds) {
			System.out.println(personId + "/" + personIds.size());
			clock.tic();
			List<CellSpan> cellSpans = svc.getCPByUserID(personId.intValue());
			clock.toc("\t" + cellSpans.size() + " cell spans."); 
			
			clock.tic();
			MobilityPathFinder pathFinder = new MobilityPathFinder(cellSpans);
			List<MobilityPath> personsMobiliyPaths = pathFinder.findMobilityPaths(
					Constants.durationThresholdInMs, Constants.durationThresholdInMs);
			clock.toc("\t" + personsMobiliyPaths.size() + " mobility paths.");
			
			clock.tic();
			mobilityPaths.addAll(personsMobiliyPaths);
			clock.toc("\t" + mobilityPaths.size() + " total mobility paths.");
		}

		clock.tic();
		Collection<OscillatingCellTowerPair> oscillations   =
				OscilliatingPairFinder.findOscillationSupport(mobilityPaths, Constants.oscillationThreshold);
		clock.toc(oscillations.size() + " oscillations found.");
		clock.totalTime();
	}
	
	public static void main(String[] args) {
		OscillationGraphGenerator gen = new OscillationGraphGenerator();
		CellSpanService cellSpanService = new CellSpanService();
		gen.computeOscillationEdges(cellSpanService);
		
	}
}
