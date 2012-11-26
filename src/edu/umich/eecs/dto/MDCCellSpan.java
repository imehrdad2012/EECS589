package edu.umich.eecs.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "mdc_cellspan")
public class MDCCellSpan extends AbstractCellSpan {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MDCCellSpan(CellSpan span) {
		super(span.getKey().getPersonid(), span.getCell(), span.getKey()
				.getTransitionId(), span.getStartTime(), span.getEndtime());
	}

	public MDCCellSpan() {
		super();
	}

	public static List<CellSpan> listAsCellSpan(List<MDCCellSpan> sampledSpans) {
		List<CellSpan> spans = new ArrayList<CellSpan>(sampledSpans.size());
		for (MDCCellSpan sampledSpan : sampledSpans) {
			spans.add(new CellSpan(sampledSpan.key.personid, sampledSpan.getCell(),
					sampledSpan.key.transitionId, sampledSpan.getStartTime(),
					sampledSpan.getEndtime()));
		}
		return spans;
	}
}
