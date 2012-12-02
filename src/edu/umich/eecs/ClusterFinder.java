package edu.umich.eecs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import junit.framework.Assert;
import org.h2.schema.Constant;
import org.jgraph.JGraph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import edu.umich.eecs.data.Constants;
import edu.umich.eecs.dto.Cell;
import edu.umich.eecs.dto.Cluster;
import edu.umich.eecs.dto.DataSetType;
import edu.umich.eecs.dto.OscillatingCellTowerPair;
import edu.umich.eecs.logger.LogClass;
import edu.umich.eecs.service.ClusterService;
import edu.umich.eecs.service.OscillationService;
import edu.umich.eecs.util.Tic;

/**
 * This class do a weight base hierarchical 
 * graph clustering from oscillation graph G=(V, E)
 * that contains set of weighted edges between
 * oscillated cell towers with support as weights.
 * The algorithm is came in Page 441, Pervasive and
 * Mobile Computing Paper( Mobility Profiler: A framework 
 * for discovering mobility profiles of cell phone users).
 * @author Mehrdad
 *
 */

public class ClusterFinder {
	
	public static Tic clock = new Tic(true);

	SimpleWeightedGraph<Cell, DefaultWeightedEdge> oscGraph;     //oscillation graph.
	SimpleWeightedGraph<Cell, DefaultWeightedEdge> backupGraph; //keeping the original graph.
	List< Cluster > clusters;       ///clusterset after running clustering algorithms.
	ClusterService clusterSrv;
	Integer transNumber;// includes number of package of 100 clusters that persisted
	DataSetType dataSet; //dataset type the cluster is creating from.
	
	
	
	List<OscillatingCellTowerPair> orderedEdges;   //list oredred edges based on ascending weights.

	
	/**
	 * Constructor that handles setting up oscillation graph using oscillation edges and cells
	 * @param osCellPair: edges(ordered by weight)
	 * @param distinctCells: vertices
	 */
	
	public ClusterFinder(List<OscillatingCellTowerPair> osCellPair, Set<Cell> distinctCells, ClusterService cs, DataSetType dataset) {
		
		oscGraph= new SimpleWeightedGraph<Cell, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		backupGraph= new SimpleWeightedGraph<Cell, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		clusters= new ArrayList<Cluster>();
		clusterSrv=cs;
		this.dataSet=dataset;
		transNumber=0;
		if(osCellPair != null && osCellPair.size() > 0){
			orderedEdges=osCellPair;
			setUpGraph(osCellPair, distinctCells);
			
		
		}
	}
	
	
	public ClusterFinder() {
		super();
	}
	/**
	 * This method creates a semi-deep copy of oscillation graph for computing quality metric
	 * @param source: oscillation graph
	 * @param dest: backup graph
	 */
	
	
	public void simpleGraphDeepCopy(SimpleWeightedGraph<Cell, DefaultWeightedEdge> source,
		SimpleWeightedGraph<Cell, DefaultWeightedEdge> dest){
		
		for(Cell cell:source.vertexSet()){
			dest.addVertex(cell);
		}
		for(DefaultWeightedEdge e: source.edgeSet()){
			DefaultWeightedEdge edge=dest.addEdge(source.getEdgeSource(e), source.getEdgeTarget(e));
			dest.setEdgeWeight(edge, source.getEdgeWeight(e));
	
		}
		
	}

	/**
	 * Inserts all oscillation edges into our graph
	 *
	 * @param osEdges
	 */

	public void setUpGraph(List<OscillatingCellTowerPair> osCellPair, Set<Cell> distinctCells){
		
		addVertices(distinctCells);
		clock.tic();
		int numberOfInsertedEdges=0;
		for(Iterator<OscillatingCellTowerPair> i= osCellPair.iterator(); i.hasNext();){
			
			OscillatingCellTowerPair op=i.next();
			DefaultWeightedEdge e=oscGraph.addEdge(op.getCellTowerPair().getCell1(), op.getCellTowerPair().getCell2());
			//LogClass.log(numberOfInsertedEdges+": "+op.getCellTowerPair().getCell1()+ "<-->"+op.getCellTowerPair().getCell2()+
				//	" Weight:"+ op.getSupportRate());
			if(e==null){
				e=oscGraph.getEdge(op.getCellTowerPair().getCell1(), op.getCellTowerPair().getCell2());
				//LogClass.log(numberOfInsertedEdges+": "+op.getCellTowerPair().getCell1()+ "<-->"+op.getCellTowerPair().getCell2()+
						//" Weight:"+ op.getSupportRate());
			}
			oscGraph.setEdgeWeight(e, op.getSupportRate());
			numberOfInsertedEdges++;
		}
     	
     	clock.toc("\t Insertions of "+ numberOfInsertedEdges+ "is done..");
     	clock.tic();
     	
     	simpleGraphDeepCopy(oscGraph, backupGraph);
		clock.toc("\t Copying Graph is done...");
	}
	/**
	 * Inserts all oscillation vertices into oscillation graph	
	 * @param cells
	 */
	
	public void addVertices(Set<Cell> cells){
		clock.tic();
		for(Cell cell: cells){
			if(!oscGraph.addVertex(cell)){
				System.err.println("cell "+cell.toString()+" is inserted into graph multiple times..");
				return;
			}
			
		}
		clock.toc("\tInsertions of "+ cells.size()+ "distinct cells is done..");
	
	}
	
	/**
	 * Computes a greedy algorithm for finding cell towers that oscillates frequently to eachother.
	 */
	
	public void makeCluster(){

		int clusterCount=0;
		while(oscGraph.vertexSet().size() > 0 && oscGraph.edgeSet().size() > 0){
			
			removeLowestWeightEdge();
			for(Set< Cell> connectedSet: getDisconnectedParts(oscGraph)){
				
				if(connectedSet.size() <= Constants.maximumClusterSize && 
					qualityMetric(connectedSet) >= Constants.minimumQualityValue ){
					double qualityMetric=qualityMetric(connectedSet);
					//LogClass.log("Cluster:"+ (++clusterCount)+ " #Cell:"+ connectedSet.size()+" Quality"+qualityMetric);
					//LogClass.log("\t"+connectedSet);
					addCluster(new Cluster(connectedSet, qualityMetric,dataSet ));
					oscGraph.removeAllVertices(connectedSet);
				}
			}

		}
		
		System.out.println("Persisted #... " + (Constants.maximumClusterBufferSize*transNumber+clusters.size())+ "/All" );
		clusterSrv.saveListToCluster(clusters);
		clusters.clear();
		
		
		
	}
	
  
	/**
	 * Creates a connectivity inspector for the specified undirected 
	 * graph and returns connected sets of it.
     * After defining ConnectivityInspector the graph cannot be changed, so
     * Every time we need to find connectedSets we must invoke this method again.
     * @param g the graph for which a connectivity inspector to be created.
	 * @return
	 */
	
	
	public List<Set<Cell>> getDisconnectedParts(SimpleWeightedGraph<Cell, DefaultWeightedEdge> sg){
		ConnectivityInspector<Cell, DefaultWeightedEdge> insp= new ConnectivityInspector<Cell, DefaultWeightedEdge>(sg);
		return insp.connectedSets();
		
		
	}
	
	/**
	 * Finds the lowest weight edge in the oscillation graph and removes that edge.
	 * @return true: finds that edge and removes it.
	 * @return false: there is no remaining edge  or removing becomes unsuccessful
	 */
	
	public boolean removeLowestWeightEdge(){
		
		if(orderedEdges.size() > 0){
			
			OscillatingCellTowerPair minWeightEdge=orderedEdges.get(0);
			//LogClass.log("Selected Edge Weight For Removal"+ minWeightEdge);
			orderedEdges.remove(0);
			if(oscGraph.removeEdge(minWeightEdge.getCellTowerPair().getCell1(),
					minWeightEdge.getCellTowerPair().getCell2()) != null){
				return true;
			}
		}
		return false;
		
	}
	/**
	 * Computes the quality metric for a connected set or cluster
	 * @param cells
	 *                       (sum of weights of edges inside the cluster in original graph)
	 *  @return qualityMetric=--------------------------------------------------------------
	 *                       (sum of weights of edges outside the cluster in original graph) 
	 */
	
	
	public double qualityMetric(Set<Cell> cells){
		
		double edgeWeightOutside=0;
		double edgeWeightInside=0;
		
		for(Cell c: cells){
			Set<DefaultWeightedEdge> allEdges=backupGraph.edgesOf(c);
			Set<DefaultWeightedEdge> insideEdges=oscGraph.edgesOf(c);
			double totalWeight=0, insideWeight=0;
			
			for(DefaultWeightedEdge e:allEdges){
				totalWeight+=backupGraph.getEdgeWeight(e);
			}
			for(DefaultWeightedEdge e: insideEdges){
				insideWeight+=oscGraph.getEdgeWeight(e);
			}
			edgeWeightOutside+=totalWeight-insideWeight;
			edgeWeightInside+=insideWeight/2;
		}
		return edgeWeightInside/edgeWeightOutside;
		
		
	}
	/**
	 * This method persist each 100 clusters into database.
	 * @param c
	 */
	
	
	public void addCluster(Cluster c){
		
		clusters.add(c);
		if(clusters.size()==Constants.maximumClusterBufferSize){
			transNumber++;
			System.out.println("Persisted #... " + Constants.maximumClusterBufferSize*transNumber+ "/All" );
			clusterSrv.saveListToCluster(clusters);
			clusters.clear();
			
		}
		
		
		
	}
	
	
	
	
	
	

}
