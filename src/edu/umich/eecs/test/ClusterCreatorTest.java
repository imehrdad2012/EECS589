package edu.umich.eecs.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.junit.Test;

import edu.umich.eecs.ClusterFinder;
import edu.umich.eecs.dto.Cell;
import edu.umich.eecs.dto.DataSetType;
import edu.umich.eecs.dto.OscillatingCellTowerPair;
import edu.umich.eecs.service.ClusterService;
import edu.umich.eecs.service.OscillationService;

public class ClusterCreatorTest {

	@Test
	public void testDatabaseEdgeOrder() {
		OscillationService os= new OscillationService(DataSetType.RealityMining);
		List<OscillatingCellTowerPair> cellPair =os.getOrderedOscillationPairs().subList(0, 10);
		for(int i=0;i<9;i++){
			assertTrue("ascending", cellPair.get(i+1).getSupportRate()-cellPair.get(i).getSupportRate()>0);
		}
	}

	@Test
	public void testUndirectedGraph(){
		SimpleWeightedGraph<String, DefaultWeightedEdge> sg=
				new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		sg.addVertex("V1");
		sg.addVertex("V2");
		sg.addVertex("V3");
		
        sg.setEdgeWeight(sg.addEdge("V1", "V2"), .2);
        sg.setEdgeWeight(sg.addEdge("V2", "V3"), .4);
        
        assertEquals(sg.edgesOf("V2").size(), 2);
     
	}
	@Test
	public void testConnectedSets(){
		SimpleWeightedGraph<Cell, DefaultWeightedEdge> sg=
				new SimpleWeightedGraph<Cell, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		for(int i=0;i<9;i++){
			sg.addVertex(new Cell("1."+i));
		}
		
        sg.setEdgeWeight(sg.addEdge(new Cell(1.0),new Cell(1.1)), .2);
        sg.setEdgeWeight(sg.addEdge(new Cell(1.1),new Cell(1.2)), .2);
        sg.setEdgeWeight(sg.addEdge(new Cell(1.2),new Cell(1.3)), .2);
        sg.setEdgeWeight(sg.addEdge(new Cell(1.3),new Cell(1.0)), .2);
        
        sg.setEdgeWeight(sg.addEdge(new Cell(1.4),new Cell(1.1)), .2);
        sg.setEdgeWeight(sg.addEdge(new Cell(1.4),new Cell(1.5)), .2);
        
        sg.setEdgeWeight(sg.addEdge(new Cell(1.5),new Cell(1.6)), .2);
        sg.setEdgeWeight(sg.addEdge(new Cell(1.6),new Cell(1.7)), .2);
        sg.setEdgeWeight(sg.addEdge(new Cell(1.7),new Cell(1.8)), .2);
        sg.setEdgeWeight(sg.addEdge(new Cell(1.8),new Cell(1.5)), .2);
        
        

        ClusterFinder cf= new ClusterFinder();
        
        List<Set< Cell>> actuals= new ArrayList<Set<Cell>>();
        
    //  Full Graph
        assertEquals(cf.getDisconnectedParts(sg).size(),1); 
       
        //	Removed Edges(1.4->1.1 && 1.1 ->1.2)");      
        sg.removeEdge(new Cell(1.4),new Cell(1.1));
        sg.removeEdge(new Cell(1.1),new Cell(1.2));
        assertEquals(cf.getDisconnectedParts(sg).size(),2); 

         
     //  Removed Removed Cell(1.3)");
        sg.removeVertex(new Cell(1.3));
        assertEquals(cf.getDisconnectedParts(sg).size(),3);


	}
	
	@Test
	public void testClusterMaker(){
		List<OscillatingCellTowerPair> osEdges= new ArrayList<OscillatingCellTowerPair>();
		
		Set<Cell> cells= new HashSet<Cell>();
		for(int i=1;i<9;i++){
			cells.add(new Cell("1."+i));
			
		}
		osEdges.add(new OscillatingCellTowerPair(new Cell("1.2"), new Cell("1.8"), 1, 10));
		osEdges.add(new OscillatingCellTowerPair(new Cell("1.3"), new Cell("1.8"), 1, 10));
		osEdges.add(new OscillatingCellTowerPair(new Cell("1.2"), new Cell("1.5"), 2, 10));
		osEdges.add(new OscillatingCellTowerPair(new Cell("1.3"), new Cell("1.5"), 2, 10));
		
		osEdges.add(new OscillatingCellTowerPair(new Cell("1.1"), new Cell("1.2"), 75, 100));
		osEdges.add(new OscillatingCellTowerPair(new Cell("1.4"), new Cell("1.1"), 8, 10));
		osEdges.add(new OscillatingCellTowerPair(new Cell("1.3"), new Cell("1.4"), 9, 10));
		osEdges.add(new OscillatingCellTowerPair(new Cell("1.5"), new Cell("1.6"), 9, 10));
		osEdges.add(new OscillatingCellTowerPair(new Cell("1.6"), new Cell("1.7"), 9, 10));
		osEdges.add(new OscillatingCellTowerPair(new Cell("1.7"), new Cell("1.8"), 9, 10));
		osEdges.add(new OscillatingCellTowerPair(new Cell("1.8"), new Cell("1.5"), 9, 10));
		osEdges.add(new OscillatingCellTowerPair(new Cell("1.2"), new Cell("1.3"), 99, 100));
		

		ClusterFinder cf= new ClusterFinder(osEdges,cells, new ClusterService(), DataSetType.RealityMining);
		//cf.makeCluster();
		//assertEquals(new ClusterService().getAllClusters().size(), 2);
		
		// This is test passes, but since, we don't want to persist result in database we comment 
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
