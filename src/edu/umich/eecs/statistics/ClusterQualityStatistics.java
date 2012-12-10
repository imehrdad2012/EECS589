package edu.umich.eecs.statistics;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import edu.umich.eecs.LatitudeLongitude;
import edu.umich.eecs.dto.Cell;
import edu.umich.eecs.dto.CellKey;
import edu.umich.eecs.dto.Cluster;
import edu.umich.eecs.dto.DataSetType;
import edu.umich.eecs.dto.GpsPosition;
import edu.umich.eecs.service.CellService;
import edu.umich.eecs.service.ClusterService;
import edu.umich.eecs.util.Tic;

public class ClusterQualityStatistics {
	
	private ClusterService clusterSvc;
	private Tic clock = new Tic(true);
	
	public ClusterQualityStatistics(ClusterService clusterSvc) {
		super();
		this.clusterSvc = clusterSvc;
	}

	public void printClusterLocationInfo(List<CellKey> cellsWithGpsList, CellService cellSvc, DataSetType dataset) {
		//
		// Store the cells with GPS data in a hash map for performance.
		//
		Map<CellKey, GpsPosition>  cellGpsMap = getGpsData(cellsWithGpsList, cellSvc);
		
		List<Cluster> allClusters = clusterSvc.getAllClusters(dataset);
		System.out.println("ClusterID\tTotalCellCount\tCellWithGpsCount\tMeanDistance\tStdDevDistance");
		for(Cluster cluster : allClusters) {
			Set<Cell> clusterCells = cluster.getCells();
			Collection<LatitudeLongitude> gpsPositionsInCluster = new ArrayList<>();
			
			for(Cell clusterCell : clusterCells) {
				if(cellGpsMap.containsKey(clusterCell.getCellkey())) {
					GpsPosition gps = cellGpsMap.get(clusterCell.getCellkey());
					if(gps.getCountSightings() > 1 && gps.getStdDev() <= 150) {
						gpsPositionsInCluster.add(new LatitudeLongitude(gps));
					}
				}
			}
			if(!gpsPositionsInCluster.isEmpty()) {
				LatitudeLongitude clusterMean = LatitudeLongitude
						.average(gpsPositionsInCluster);
				Collection<Double> distances = new ArrayList<>();
				for (LatitudeLongitude point : gpsPositionsInCluster) {
					distances.add(LatitudeLongitude.distanceInMeters(
							clusterMean, point));
				}
				MeanAndStdDev clusterDistances = MeanAndStdDev
						.fromSample(distances);
				System.out.println(cluster.getCkey().getClusterID() + "\t"
						+ clusterCells.size() + "\t"
						+ gpsPositionsInCluster.size() + "\t"
						+ clusterDistances.getMean() + "\t"
						+ clusterDistances.getStdDev());
			}
		}
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
		
		ClusterQualityStatistics qualityStats = new ClusterQualityStatistics(new ClusterService());
		Scanner scanner = new Scanner(new File("/Users/Mehrdad/Documents/workspace/589-Project/files/clustered_cells_with_gpslog.txt"));
		scanner.nextLine(); // get rid of the header
		List<CellKey> cellsWithGpsData = CellListParser.fromFile(scanner);
		qualityStats.printClusterLocationInfo(cellsWithGpsData,  new CellService(), dataset);
	}
}

