package edu.umich.eecs.statistics;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import edu.umich.eecs.LatitudeLongitude;
import edu.umich.eecs.dto.Cell;
import edu.umich.eecs.dto.CellKey;
import edu.umich.eecs.dto.GpsPosition;
import edu.umich.eecs.dto.OpenCell;
import edu.umich.eecs.service.CellService;
import edu.umich.eecs.service.OpenCellService;

public class CellLocationStatistics {
	
	private CellService cellSvc;
	private OpenCellService openCellSvc;
	
	public CellLocationStatistics(CellService cellSvc,
			OpenCellService openCellSvc) {
		super();
		this.cellSvc = cellSvc;
		this.openCellSvc = openCellSvc;
	}

	/**
	 * Goes through every cell in the database and checks the difference between
	 * the OpenCell and the GPS log position. It prints out cell and the disparity
	 * in the location estimates.
	 * @param cells
	 * @return
	 */
	public MeanAndStdDev computeLocationDisparity(Set<CellKey> cells) {
		Set<Double> disparities = new HashSet<>();
		int i = 1;
		for(CellKey key : cells) {
			
			Cell cellWithGpsLog = cellSvc.getCell(key);
			GpsPosition gpsPos = cellWithGpsLog.getGpsPosition();
			
			OpenCell openCell = openCellSvc.getLocation(key);
			
			double disparity = LatitudeLongitude.distanceInMeters(
					new LatitudeLongitude(gpsPos),
					new LatitudeLongitude(openCell.getLatitude(), openCell.getLongitude()));
			disparities.add(disparity);
			
		 //System.out.println(/*i++ + "/" + cells.size() + " (" + */disparity); //+ ")");
			System.out.println(key.getCountryID() + "\t" + key.getNetworkID() + "\t" + key.getAreaID() + "\t" + key.getCellID() + "\t" + disparity);
		}
		
		return MeanAndStdDev.fromSample(disparities);
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		//
		// This file contains all cells that have location data from OpenCell and GPS logs. I use an
		// input file instead of getting the data from the database because it is a somewhat convoluted
		// query and I didn't want to do it in Hibernate.
		//
		Scanner scanner = new Scanner(new File(
				"/Users/Mehrdad/Documents/workspace/589-Project/files/cells_opencell_and_gpslog_all.txt"));
		try {

			// CELLID LAC MNC MCC
			scanner.nextLine();
			Set<CellKey> cells = new HashSet<CellKey>(CellListParser.fromFile(scanner));
			CellService cellSvc = new CellService();
			OpenCellService openCellSvc = new OpenCellService();

			CellLocationStatistics stats = new CellLocationStatistics(cellSvc,
					openCellSvc);

			MeanAndStdDev meanStdDev = stats.computeLocationDisparity(cells);

			System.out.println("Mean: " + meanStdDev.getMean() + "m");
			System.out.println("Std dev: " + meanStdDev.getStdDev() + "m");
		} finally {
			scanner.close();
		}
	}
	
}
