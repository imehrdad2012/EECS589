package edu.umich.eecs.data;

import java.util.List;

import edu.umich.eecs.dto.Cell;
import edu.umich.eecs.dto.CellSpan;
import edu.umich.eecs.dto.MDCCellSpan;
import edu.umich.eecs.service.CellSpanService;
import edu.umich.eecs.service.LocationService;
import edu.umich.eecs.service.MDCCellSpanService;

public class CellGPSUpdator {
	
	public void addGPSLocationToNokia(MDCCellSpanService mSrv){
		List<Cell> cells=new MDCCellSpanService().getAllCells();
		LocationService ls= new LocationService();
		for(Cell cell:cells){
			
			ls.getLocation(cell.getCellkey().getCountryID(), cell.getCellkey().getNetworkID(), cell.getAreaID(), cell.getCellID());
		}	
	}	
	
	public static void main(String[] args) {
		new CellGPSUpdator().addGPSLocationToNokia(new MDCCellSpanService());
		
	}

}
