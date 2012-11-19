package edu.umich.eecs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.umich.eecs.dto.CellSpan;

/**
 * This class creates mobility paths given a users list of cell tower connections.
 * @author Pedro
 *
 */
public class MobilityPathFinder {
	private List<CellSpan> cellspans;

	public MobilityPathFinder(List<CellSpan> cellspans) {
		//
		// We need to (shallow) copy the array because we sort it. Sorting
		// is done by start time.
		//
		this.cellspans = new ArrayList<CellSpan>(cellspans);
		//Collections.sort(this.cellspans);
	}
	
	/**
	 * Returns the list of mobility paths given the data provided in the constructor.
	 * @return
	 */
	public List<MobilityPath> findMobilityPaths(double durationThresholdInMs, double transitionThresholdInMs) {
		//
		// This is pretty much verbatim Algorithm 1 in Bayir et al.
		//
		
		List<MobilityPath> paths = new ArrayList<MobilityPath>();
		MobilityPath currentPath = new MobilityPath();
		int i = -1;
		for(CellSpan span : cellspans) {
			boolean spanIsObservedEndLocation = false;
			boolean spanFollowsHiddenEndLocation = false;
			boolean spanIsNormalElement = false;
			i++;
			if(span.getDuration() <= durationThresholdInMs) {
				if(currentPath.size() > 0) {
					assert span.getStartTime().getTime() >= currentPath.getLastEndTime();
					if(span.getStartTime().getTime() - currentPath.getLastEndTime() <= transitionThresholdInMs) {
						spanIsNormalElement = true;
					} else {
						spanFollowsHiddenEndLocation = true;
					}
				} else {
					spanIsNormalElement = true;
				}
			} else {
				//
				// Cell span duration is greater than the duration threshold. Either
				// this is an observed end-location or a hidden end-location (if the
				// previous span's endtime is too long ago).
				//
				if(currentPath.size() > 0) {
					assert span.getStartTime().getTime() >= currentPath.getLastEndTime();
					if(span.getStartTime().getTime() - currentPath.getLastEndTime() <= transitionThresholdInMs) {
						spanIsObservedEndLocation = true;
					} else {
						spanFollowsHiddenEndLocation = true;
					}
				} else {
					spanIsNormalElement = true;
				}
			}
			
			if(spanIsNormalElement) {
				currentPath.addToPath(span);
			}
			if(spanIsObservedEndLocation) {
				assert !spanIsNormalElement;
				
				currentPath.addToPath(span);
				paths.add(currentPath);
				currentPath = new MobilityPath();
				currentPath.addToPath(span);
			}
			if(spanFollowsHiddenEndLocation) {
				assert !spanIsObservedEndLocation && !spanIsNormalElement;
				
				paths.add(currentPath);
				currentPath = new MobilityPath();
				currentPath.addToPath(span);
			}
		}
		assert currentPath.size() > 0;
		paths.add(currentPath);
		return paths;
	}
}
