package edu.umich.eecs.data;

import java.util.List;
import java.util.Set;

import edu.umich.eecs.ClusterFinder;
import edu.umich.eecs.dto.Cell;
import edu.umich.eecs.dto.Cluster;
import edu.umich.eecs.dto.OscillatingCellTowerPair;
import edu.umich.eecs.service.ClusterService;
import edu.umich.eecs.service.OscillationService;
import edu.umich.eecs.util.Tic;


public class ClusterGenerator {
	public static Tic clock = new Tic(true);
	
	public static void computeCluster(){
		OscillationService os= new OscillationService();
		ClusterService  cs= new ClusterService();
		
		clock.tic();
		List<OscillatingCellTowerPair> oEdges=os.getOrderedOscillationPairs();
		clock.toc("Obtained " + oEdges.size() + " Edges ");
		
		clock.tic();
		Set<Cell> oCells=os.getAllOsiCells();
		clock.toc("Obtained " + oEdges.size() + " Vertices ");
		
		clock.tic();
		ClusterFinder cf= new ClusterFinder(oEdges, oCells, new ClusterService());
		clock.toc("Oscillation Graph is Created ");
		
		clock.tic();
		List<Cluster> clusters=cf.makeCluster();
		clock.toc("Cluster Graph is Created ");
		
		clock.tic();
		cs.saveListToCluster(clusters);
		clock.toc("Persisted Remaining Clusters");
	}
	
	
	
	public static void main(String[] args) {
		
		ClusterGenerator.computeCluster();	
		
	}

}
