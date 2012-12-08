package edu.umich.eecs.data;

import java.util.ArrayList;
import java.util.List;

import javassist.expr.NewArray;

import edu.umich.eecs.dto.AreaDensity;
import edu.umich.eecs.dto.Cell;
import edu.umich.eecs.dto.CellSpan;
import edu.umich.eecs.dto.Cluster;
import edu.umich.eecs.dto.DataSetType;
import edu.umich.eecs.service.AreaDensityService;
import edu.umich.eecs.service.CellService;
import edu.umich.eecs.service.CellSpanService;
import edu.umich.eecs.service.CellSpanServiceInterface;
import edu.umich.eecs.service.ClusterService;

public class DensityGenerator {
	
	ArrayList<AreaDensity> listAreaDensity;
	AreaDensityService aSrv;
	int numberOfPersistence;
	

	public void computeDensity(CellSpanServiceInterface cSpaninf, DataSetType dataset){
		
		listAreaDensity= new ArrayList<>();
		aSrv= new AreaDensityService();
		numberOfPersistence=1;
		List<Integer> areas= cSpaninf.getAllAreaID();
		System.out.println("DataSet is: "+dataset.toString()+" There is #"+areas.size()+" areas");
		for(Integer i: areas){
			List<Integer> cl=new ClusterService().getAllClustersByAreaID(dataset, i);
			List<Integer> uc= new ClusterService().getUnclusteredCellbyAreaID(dataset, i);
			double numberOfCluster= cl.size()+uc.size();
			double numberOfCells=cSpaninf.getAllCellsByAreaID(i).size();
			double density= numberOfCells/numberOfCluster;
			AreaDensity ad= new AreaDensity(i, (int)numberOfCluster, dataset, (int)numberOfCells,density );
			addDensity(ad);
		}	
		System.out.println("#"+(50*numberOfPersistence+listAreaDensity.size())+" Area Densities is Persited");
		listAreaDensity.clear();
	}
	public void addDensity(AreaDensity ad){
		
		listAreaDensity.add(ad);
		if(listAreaDensity.size()==50){
			aSrv.saveListToAD(listAreaDensity);
			listAreaDensity.clear();
			System.out.println("#"+50*(numberOfPersistence++)+" Area Densities is Persisted");
		
		}
	}
	
	
	
	public static void main(String[] args) {
		
		Integer cl=new ClusterService().getCount(DataSetType.RealityMining, 1);
		//new DensityGenerator().computeDensity(new CellSpanService(), DataSetType.RealityMining);
	}

}
