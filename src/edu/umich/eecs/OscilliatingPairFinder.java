package edu.umich.eecs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import edu.umich.eecs.dto.Cell;
import edu.umich.eecs.dto.CellTowerPair;
import edu.umich.eecs.dto.OscillatingCellTowerPair;

public class OscilliatingPairFinder {

	/**
	 * Computes the oscillation support of every pair of cell towers whose
	 * oscillation count is greater than oscillationCountThreshold in at least
	 * one mobility path. The oscillation support for towers (x, y) is defined
	 * as: [# paths in which (x,y) oscillated more than
	 * oscillationCountThreshold times] / [# paths in which (x,y) appear
	 * together]
	 * 
	 * @param mobilityPaths
	 *            the set of mobility paths for which the oscillation support
	 *            will be found
	 * @param oscillationCountThreshold
	 *            the minimum number of switches in one mobility path between
	 *            two towers for those towers to be considered as oscillating.
	 * @return a map of cell tower pairs that oscillated and their support, as
	 *         defined above.
	 */
	public static Collection<OscillatingCellTowerPair> findOscillationSupport(
			Collection<MobilityPath> mobilityPaths, int oscillationCountThreshold) {
		// We use a HashMap for performance -- there are a lot of containsKey() calls.
		HashMap<CellTowerPair, OscillatingCellTowerPair> oscillatingPairs =
				new HashMap<CellTowerPair, OscillatingCellTowerPair>();
		for(MobilityPath mobilityPath : mobilityPaths) {
			Collection<SwitchingCellTowerPair> oscillations = 
					findSwitchesInMobilityPath(mobilityPath);
			for(SwitchingCellTowerPair oscillation : oscillations) {
				if(!oscillatingPairs.containsKey(oscillation.cellTowerPair)) {
					oscillatingPairs.put(oscillation.cellTowerPair,
							new OscillatingCellTowerPair(oscillation.cellTowerPair));
				}
				oscillatingPairs.get(oscillation.cellTowerPair).incrementTotalNumberSwitches();
				if (oscillation.numberOfSwitches >= oscillationCountThreshold) {
					oscillatingPairs.get(oscillation.cellTowerPair).incrementNumberOscillations();
				}
			}
		}

		//
		// Remove cell tower pairs with 0 oscillations.
		//
		Collection<OscillatingCellTowerPair> allOscillations = oscillatingPairs.values();
		Collection<OscillatingCellTowerPair> filteredOscillations = new ArrayList<OscillatingCellTowerPair>(allOscillations.size()/2);
		for(OscillatingCellTowerPair pair : allOscillations) {
			if(pair.getNumberOscillations() > 0 && pair.getCellTowerPair().getCell1().getCellID() > 0 && 
					 pair.getCellTowerPair().getCell2().getCellID() > 0) {
				
				pair.setSupportRate((double)pair.getNumberOscillations()/pair.getTotalNumberSwitches());
				filteredOscillations.add(pair);
			}
			
			
		}
		
		return filteredOscillations;
	}

	/**
	 * Computes every pair of towers that oscillated in a mobility path.
	 * 
	 * @param path
	 *            the mobility path
	 * @return A hash map of cell pairs and the number of oscillations in that
	 *         path.
	 */
	public static Collection<SwitchingCellTowerPair> findSwitchesInMobilityPath(
			MobilityPath path) {
		// We use a HashMap for performance -- there are a lot of containsKey()
		// calls.
		HashMap<CellTowerPair, SwitchingCellTowerPair> oscillatingPairs =
				new HashMap<CellTowerPair, SwitchingCellTowerPair>();
		for (int firstElement = 0; firstElement < path.size() - 1; firstElement++) {
			Cell first = path.getPath().get(firstElement).getCell();
			HashSet<Cell> elementsAlreadySeen = new HashSet<Cell>();
			for (int secondElement = firstElement + 1; secondElement < path
					.size(); secondElement++) {
				Cell second = path.getPath().get(secondElement).getCell();
				if (elementsAlreadySeen.contains(second)) {
					continue;
				} else {
					elementsAlreadySeen.add(second);
				}
				if (first.equals(second)) {
					break;
				}
				CellTowerPair pair = new CellTowerPair(first, second);
				if (oscillatingPairs.containsKey(pair)) {
					oscillatingPairs.get(pair).numberOfSwitches++;
				} else {
					SwitchingCellTowerPair switchingPair = new SwitchingCellTowerPair(
							pair);
					switchingPair.numberOfSwitches = 1;
					oscillatingPairs.put(pair, switchingPair);
				}
			}
		}
		return oscillatingPairs.values();
	}

	
}
