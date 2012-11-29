package edu.umich.eecs.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import edu.umich.eecs.ClusterFinder;
import edu.umich.eecs.dto.Cell;
import edu.umich.eecs.dto.CellTowerPair;
import edu.umich.eecs.dto.OscillatingCellTowerPair;
import edu.umich.eecs.service.OscillationService;

public class main {
	public static void main(String[] args) {
		//OscillationService os= new OscillationService();
		//List<OscillatingCellTowerPair>list =os.getOrderedOscillationPairs();
		//ClusterFinder cf= new ClusterFinder(os.getOrderedOscillationPairs(), os.getAllOsiCells());

/*	
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
        System.out.println("Full Connected Set");
        for(Set<Cell> cellSet: cf.getDisconnectedParts(sg)){
        	System.out.println(cellSet.toString());
        }
        
        sg.removeEdge(new Cell(1.4),new Cell(1.1));
        sg.removeEdge(new Cell(1.1),new Cell(1.2));

        System.out.println("Removed Edges(1.4->1.1 && 1.1 ->1.2)");
        for(Set<Cell> cellSet: cf.getDisconnectedParts(sg)){
        	System.out.println(cellSet.toString());
        }
        
        sg.removeVertex(new Cell(1.3));
        System.out.println("Removed Cell(1.3)");

        for(Set<Cell> cellSet: cf.getDisconnectedParts(sg)){
        	System.out.println(cellSet.toString());
        }
	**/
		

		
		
		
	}

}
