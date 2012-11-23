package edu.umich.eecs.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.umich.eecs.dto.CellSpan;

public class CellSpanSampler {
	/**
	 * Samples a list of cell tower spans at a given rate. 
	 * @param spans
	 * @param samplingRateInSeconds
	 * @return
	 */
	public static List<CellSpan> sample(Collection<CellSpan> spans, int samplingRateInSeconds) {
		List<CellSpan> sampledSpans = new ArrayList<CellSpan>();
		//
		// Keeps track of how many seconds are left until we need to sample again.
		// We always sample the first one so we start it at 0.
		//
		int secondsRemaining = 0;
		for(CellSpan span : spans) {
			long spanDurationLeft = span.getDuration() / 1000;
			do {
				if(spanDurationLeft > secondsRemaining) {
					//
					// Sample.
					//
					sampledSpans.add(span);
					spanDurationLeft -= secondsRemaining;
					secondsRemaining = samplingRateInSeconds;
				} else {
					//
					// Keep track of how many seconds until the next sampling.
					// We are done with this span.
					//
					secondsRemaining -= spanDurationLeft;
					spanDurationLeft = 0;
				}
			} while( spanDurationLeft > 0);
		}
		return sampledSpans;
	}
}
