package edu.umich.eecs.data;

import java.util.List;

import edu.umich.eecs.dto.CellSpan;
import edu.umich.eecs.service.CellSpanService;
import edu.umich.eecs.service.SampledCellSpanService;
import edu.umich.eecs.util.Tic;

public class RMSpanSampler {
	private CellSpanService cellSpanSvc = new CellSpanService();
	private static SampledCellSpanService sampledCellSpanSvc = new SampledCellSpanService();
	
	public void sampleAndSave(int samplingRateInSeconds) {
		List<Integer> personIds = cellSpanSvc.getAllUsers();
		for(Integer id : personIds) {
			List<CellSpan> cellSpans = cellSpanSvc.getCPByUserID(id.intValue());
			List<CellSpan> sampledSpans = CellSpanSampler.sample(cellSpans, samplingRateInSeconds);
			for(CellSpan sampledSpan : sampledSpans) {
				sampledCellSpanSvc.saveCP(sampledSpan);
			}
			System.out.println(id.intValue() + " / " + personIds.size());
		}
	}
	
	public static void main(String[] args) {
//		List<CellSpan> spans = new ArrayList<CellSpan>();
//		spans.add(Util.makeCellSpanSeconds(1, 1, 1.1, 0, 200));
//		spans.add(Util.makeCellSpanSeconds(1, 2, 1.2, 200, 220));
//		spans.add(Util.makeCellSpanSeconds(1, 3, 1.3, 220, 235));
//		spans.add(Util.makeCellSpanSeconds(1, 4, 1.4, 235, 265));
//		
//		List<CellSpan> sampledSpans = CellSpanSampler.sample(spans, 60);
//		for(CellSpan sampledSpan : sampledSpans) {
//			sampledCellSpanSvc.saveCP(sampledSpan);
//		}
		Tic clock = new Tic();
		RMSpanSampler sampler = new RMSpanSampler();
		sampler.sampleAndSave(60);
		clock.toc();
	}
}
