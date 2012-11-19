package edu.umich.eecs;

import java.util.ArrayList;
import java.util.List;

import edu.umich.eecs.dto.*;

public class MobilityPath {
	private List<CellSpan> path = new ArrayList<CellSpan>();
	public void addToPath(CellSpan cellSpan) {
		getPath().add(cellSpan);
	}
	
	public boolean isConsistent(double durationThresholdInMs, double transitionThresholdInMs) {
		//
		// Being consistent means:
		//	- no cellspan duration is longer than durationThresholdInMs
		//	  (except possibly the first or last in the path)
		//  - the difference between consecutive cellspans start and end time is less than
		//	  transitionThresholdInMs
		// 
		CellSpan previous = null;
		for(CellSpan cellspan : getPath()) {
			if(cellspan.getDuration() > durationThresholdInMs &&
					!(getPath().get(0) == cellspan || getPath().get(getPath().size() - 1) == cellspan)) {
				return false;
			}
			if(previous != null && (previous.getEndtime().getTime() - cellspan.getStartTime().getTime() >
					transitionThresholdInMs)) {
				return false;
			}
			previous = cellspan;
		}
		
		return true;
	}

	public int size() {
		return path.size();
	}
	
	public long getLastEndTime() {
		return path.get(size() - 1).getEndtime().getTime();
	}
	
	public List<CellSpan> getPath() {
		return path;
	}	
	
}
