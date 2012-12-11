package edu.umich.eecs.statistics;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import java.util.List;

import edu.umich.eecs.LatitudeLongitude;
import edu.umich.eecs.dto.Cell;
import edu.umich.eecs.dto.CellKey;
import edu.umich.eecs.dto.Cluster;
import edu.umich.eecs.dto.DataSetType;
import edu.umich.eecs.dto.GpsPosition;
import edu.umich.eecs.service.CellService;
import edu.umich.eecs.service.ClusterService;
import edu.umich.eecs.util.Tic;

/**
 * Measures how many cells should have been clustered together but weren't.
 * @author Pedro
 *
 */
public class ClusterIncompletenessStatistics {
	
	private CellService cellSvc;
	private ClusterService clusterSvc;
	private Tic clock = new Tic(true);
	private DataSetType dataset;
	private Map<Integer, Integer> clusterToCountUnclutered= new HashMap<>();
	private final double radius = 500;  // cells within radius meters of each other should be clustered.
	
	
	
	public void calculateIncompleteness(Collection<CellKey> cells) {
		//
		// Store the cells with GPS data in a hash map for performance.
		//
		Map<CellKey, GpsPosition>  cellGpsMap = getGpsData(cells, cellSvc);
		//
		// Preprocess cluster list for fast access.
		//
		Map<CellKey, Cluster> cellClusterMap = preprocessClusters(cells);
		// cells that should have been clustered together but weren't
		int countTotalUnclusteredCells = 0;
		int countValidCells = 0;
		System.out.println("CountryID\tNetworkID\tAreaID\tCellID\tClusterID\tCountUnclustered");
		//System.out.println("CellID\t\tClusterID\t\tCountUnclustered");

		for(CellKey cell1 : cells) {
			int countCellUnclusteredCells = 0;
			if(!isValidCell(cell1, cellGpsMap)) {
				continue;
			}
			countValidCells++;
			//
			// Iterate through every other valid cell and see if it should have been clustered with cell1.
			//
			for(CellKey cell2 : cells) {
				if(!isValidCell(cell2, cellGpsMap)) {
					continue;
				}
				// Is cell2 close to cell1?
				double distance = LatitudeLongitude.distanceInMeters(
						new LatitudeLongitude(cellGpsMap.get(cell1)),
						new LatitudeLongitude(cellGpsMap.get(cell2)));
				if(distance <= radius) {
					// are cell1 and cell2 clustered?
					if(cellClusterMap.containsKey(cell1)) {
						// cell1 is in some cluster. is cell2 in it too?
						Cluster cell1Cluster = cellClusterMap.get(cell1);
						if(!cellClusterMap.containsKey(cell2) || !cellClusterMap.get(cell2).equals(cell1Cluster)) {
							// they are not clustered together
							countCellUnclusteredCells++;
						}
					} else {
						// cell1 isn't in any cluster, therefore cell2 can't be clusterd with cell1
						countCellUnclusteredCells++;
					}
				}
			}
			countTotalUnclusteredCells += countCellUnclusteredCells;
			
			System.out.println(cell1.getCountryID() + "\t" + cell1.getNetworkID() + "\t" + cell1.getAreaID() + "\t"
			+ cell1.getCellID() + "\t"
			+ (cellClusterMap.containsKey(cell1) ? cellClusterMap.get(cell1).getCkey().getClusterID() : "-1")
			+ "\t" + countCellUnclusteredCells);
			
			//System.out.println(countCellUnclusteredCells);
			
			
			
			
			
		}
		double countUnclusteredCells = countTotalUnclusteredCells / 2; // our method double counts (c1, c2) and (c2, c1)
		double averageCountUnclusteredCells = countUnclusteredCells / countValidCells;
		System.out.println("Average number of unclustered cells: " + averageCountUnclusteredCells);
	}
	
	public ClusterIncompletenessStatistics(CellService cellSvc,
			ClusterService clusterSvc, DataSetType dataset) {
		super();
		this.cellSvc = cellSvc;
		this.clusterSvc = clusterSvc;
		this.dataset = dataset;
	}

	/**
	 * Associates every cell with the cluster it's in. If a cell is not in any
	 * cluster it won't be in the map.
	 * @param cells
	 * @return
	 */
	private Map<CellKey, Cluster> preprocessClusters(Collection<CellKey> cells) {
		clock.tic();
		Map<CellKey, Cluster> map = new HashMap<>(cells.size());
		List<Cluster> allClusters = clusterSvc.getAllClusters(dataset);
		for(Cluster cluster : allClusters) {
			Collection<Cell> cellsInCluster = cluster.getCells();
			for(Cell cellInCluster : cellsInCluster) {
				if(cells.contains(cellInCluster.getCellkey())) {
					map.put(cellInCluster.getCellkey(), cluster);
				}
			}
		}
		clock.toc("Preprocessed the cell/cluster association.");
		return map;
	}

	private boolean isValidCell(CellKey cell, Map<CellKey, GpsPosition> cellGpsMap) {
		GpsPosition gps = cellGpsMap.get(cell);
		return gps.getCountSightings() > 1 && gps.getStdDev() <= 500;
	}
	
	private Map<CellKey, GpsPosition> getGpsData(Collection<CellKey> cellKeys, CellService svc) {
		clock.tic();
		Map<CellKey, GpsPosition> gpsMap = new HashMap<>();
		for(CellKey key : cellKeys) {
			Cell cell = svc.getCell(key);
			gpsMap.put(cell.getCellkey(), cell.getGpsPosition());
		}
		clock.toc("Got all the GPS data");
		return gpsMap;
	}
	
	
	public static void main(String[] args) throws FileNotFoundException {
		DataSetType dataset = DataSetType.NokiaChallenge;
		
		ClusterIncompletenessStatistics incompletenessStats = new ClusterIncompletenessStatistics(
				new CellService(),
				new ClusterService(), dataset);
		
		Scanner scanner = new Scanner(new File("/Users/Mehrdad/Documents/workspace/589-Project/files/clustered_cells_with_gpslog.txt"));
		
		scanner.nextLine(); // get rid of the header
		List<CellKey> cellsWithGpsData = CellListParser.fromFile(scanner);
		
		incompletenessStats.calculateIncompleteness(cellsWithGpsData);
		
	}
}

