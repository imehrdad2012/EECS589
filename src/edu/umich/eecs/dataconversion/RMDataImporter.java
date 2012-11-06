package edu.umich.eecs.dataconversion;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.StringTokenizer;

import edu.umich.eecs.dto.*;
import edu.umich.eecs.service.CellSpanService;

/**
 * This class imports data from the CSV files generated from the
 * Reality Mining dataset.
 * @author Pedro
 *
 */
public class RMDataImporter {
	
	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
	private CellSpanService cellSpanSvc;
	private Scanner scanner;
	private int personID;
	private int countCorruptedEntries = 0; // the number of 01-Jan-2004 entries skipped
	
	public int getCountCorruptedEntries() {
		return countCorruptedEntries;
	}

	/**
	 * Creates a data importer for a specific CSV file.
	 * @param path The path to the file. The format of the filename should be x_yyyy.csv, where
	 * x must be the person ID and yyyy can be anything.
	 * @param cellSpanSvc The database connection service.
	 * @throws FileNotFoundException
	 */
	public RMDataImporter(String path, CellSpanService cellSpanSvc) throws FileNotFoundException {
		File file = new File(path);
		this.scanner = new Scanner(file);
		this.cellSpanSvc = cellSpanSvc;
		personID = Integer.parseInt(new StringTokenizer(file.getName(), "_").nextToken());
	}
	
	/**
	 * Goes through the whole CSV file provided in the constructor and inserts each
	 * cellspan in the database. 
	 * @throws ParseException 
	 */
	public void insertIntoDatabase() throws ParseException {

		//
		// The CSV file has one cell tower transition per line, in this format:
		//		19-Jul-2004 16:53:10,24127.02421,
		//		19-Jul-2004 16:53:26,24127.00111,
		// This means that at 0:19AM of 12/19, that person connected to tower 24127.02421,
		// and 16 seconds later switched to tower 24127.00111.
		//
		// In the database, we store the span of time during which the user was connected to
		// a tower. In the previous example, we would store a 16-long span for tower 24127.02421.
		//
		// A couple of things to keep in mind:
		//  - One of the implications is that we ignore the last connection of the CSV file; we only
		//	  care about the time at which the previous connection ended.
		//  - According to the Reality Mining dataset readme, some dates are wrongly labeled as
		//	  1/1/2004. This happens when the phone runs out of battery. Our workaround is to simply
		//	  ignore those dates (this imply ignoring the connection that ended at that date,
		//	  plus the one that just started.
		//
		
		scanner.useDelimiter(",");
		String lastEndDate = null;
		while(scanner.hasNext()) {
			String startDate = lastEndDate == null ? scanner.next().trim() : lastEndDate;
			String cellAreaCode = scanner.next().trim();
			
			// we skip the last line
			if(!scanner.hasNext()) {
				continue;
			}
			
			String endDate = scanner.next().trim();
			
			if(endDate.equals("")) {
				continue;
			}
			
			if(!startDate.startsWith("01-Jan-2004") && !endDate.startsWith("01-Jan-2004")) {
					CellSpan span = createCellSpan(personID, startDate, endDate, cellAreaCode);
					cellSpanSvc.saveCP(span);
			} else {
				//
				// We don't have to do anything. If endDate is not broken, than the next iteration
				// will work correctly. If it is broken, the next iteration will detect it and skip
				// the insertion.
				//
				countCorruptedEntries++;
			}
			
			lastEndDate = endDate;
		}
	}
	
	private Timestamp parseTimestamp(String timestamp) throws ParseException {
		//
		// Pretty much all timestamps follow the dd-MMM-yyyy HH:mm:ss format configured in
		// dateFormatter, but if an event happens _exactly_ at midnight there won't be the HH:mm:ss
		// part.
		//
		try {
			return new Timestamp(dateFormatter.parse(timestamp).getTime());
		} catch(ParseException ex) {
			return new Timestamp(new SimpleDateFormat("dd-MMM-yyyy").parse(timestamp).getTime());
		}
	}
	
	private CellSpan createCellSpan(int personId, String startTime, String endTime, String cell)
			throws ParseException {
		return new CellSpan(
				personId,
				new Cell(cell),
				parseTimestamp(startTime),
				parseTimestamp(endTime));
	}
	
	public static void main(String[] args) throws ParseException, FileNotFoundException {
		
		//
		// Replace this with the path to where the X_locs.csv files are. Note that these must be the only
		// files in the directory, i.e. the other csv that the matlab scripts generate must be put somewhere
		// else. The path must end with a trailing slash.
		//
		
		String pathToCsvFiles = "C:\\Users\\Pedro\\Desktop\\589 project data\\realitydata_csv\\locs\\";
		CellSpanService cellSpanService = new CellSpanService();

		try {
			File dir = new File(pathToCsvFiles);
			 int count = 0;
			String[] contents = dir.list();
			for (String file : contents) {
				RMDataImporter dataImporter = new RMDataImporter(pathToCsvFiles + file, cellSpanService);
				dataImporter.insertIntoDatabase();
				System.out.println(++count + "/" + contents.length + " CSV files inserted in the DB");
			}
		} finally {
			cellSpanService.tearDown();
		}
	}
}
