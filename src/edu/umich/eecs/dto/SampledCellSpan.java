package edu.umich.eecs.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * We need this class so that we can use a different table.
 * @author Pedro
 *
 */
@Entity
@Table(name="sampled_cellspan")
public class SampledCellSpan extends AbstractCellSpan {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SampledCellSpan(CellSpan span) {
		super(span.getKey().getPersonid(), span.getCell(), span.getKey().getTransitionId(),
				span.getStartTime(), span.getEndtime());
	}
	
	public SampledCellSpan() {
		super();
	}
	
	public static List<CellSpan> listAsCellSpan(List<SampledCellSpan> sampledSpans) {
		List<CellSpan> spans = new ArrayList<CellSpan>(sampledSpans.size());
		for(SampledCellSpan sampledSpan : sampledSpans) {
			spans.add(new CellSpan(
					sampledSpan.key.personid,
					sampledSpan.cell,
					sampledSpan.key.transitionId,
					sampledSpan.startTime,
					sampledSpan.endtime));
		}
		return spans;
	}
}
