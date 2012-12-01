package edu.umich.eecs.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.junit.Test;

import edu.umich.eecs.data.CellGpsDataImporter;
import edu.umich.eecs.data.CellGpsDataImporter.GpsTimestampedData;
import edu.umich.eecs.dto.Cell;
import edu.umich.eecs.dto.GpsPosition;

public class CellGpsDataImporterTest {

	@Test
	public void testReadGpsGsmFilePair() throws FileNotFoundException {
		CellGpsDataImporter importer = new CellGpsDataImporter(20);
		HashMap<Cell, List<GpsTimestampedData>> cellGpsData = importer.readGpsGsmFilePair(
				new File("src/edu/umich/eecs/test/files/gsm_test_file.txt"),
				new File("src/edu/umich/eecs/test/files/gps_test_file.txt"),
				new HashMap<Cell, List<GpsTimestampedData>>());
		
		Cell cell1 = new Cell(228, 3, 6503, 1146748);
		assertTrue(cellGpsData.containsKey(cell1));
		assertEquals(1255109236, cellGpsData.get(cell1).get(0).time);
		
		cell1 = new Cell(228, 3, 6504, 1146748);
		assertTrue(cellGpsData.containsKey(cell1));
		assertEquals(1255109236, cellGpsData.get(cell1).get(0).time);
		
		cell1 = new Cell(228, 3, 6506, 1146748);
		assertTrue(cellGpsData.containsKey(cell1));
		assertEquals(1255276872, cellGpsData.get(cell1).get(0).time);
		
		cell1 = new Cell(228, 3, 6503, 1146748);
		assertTrue(cellGpsData.containsKey(cell1));
		assertEquals(2, cellGpsData.get(cell1).size());
	}
	
	@Test
	public void testReadGpsGsmFilePair2() throws FileNotFoundException {
		CellGpsDataImporter importer = new CellGpsDataImporter(20);
		HashMap<Cell, List<GpsTimestampedData>> cellGpsData = importer.readGpsGsmFilePair(
				new File("src/edu/umich/eecs/test/files/gsm_test_file2.txt"),
				new File("src/edu/umich/eecs/test/files/gps_test_file.txt"),
				new HashMap<Cell, List<GpsTimestampedData>>());
		
		HashMap<Cell, GpsPosition> cellPosition = importer.computeCellGpsPosition(cellGpsData);
		Cell cell1 = new Cell(228, 3, 6505, 1146748);
		assertTrue(cellPosition.containsKey(cell1));
		assertEquals(6.80545, cellPosition.get(cell1).getLongitude(),0.0001);
		assertEquals(46.80735, cellPosition.get(cell1).getLatitude(),0.0001);
		assertEquals(0.0, cellPosition.get(cell1).getStdDev(), 0.00001);	
	}
	
	@Test
	public void testReadGpsGsmFilePair3() throws FileNotFoundException {
		CellGpsDataImporter importer = new CellGpsDataImporter(20);
		HashMap<Cell, List<GpsTimestampedData>> cellGpsData = importer.readGpsGsmFilePair(
				new File("src/edu/umich/eecs/test/files/gsm_test_file3.txt"),
				new File("src/edu/umich/eecs/test/files/gps_test_file.txt"),
				new HashMap<Cell, List<GpsTimestampedData>>());
		
		HashMap<Cell, GpsPosition> cellPosition = importer.computeCellGpsPosition(cellGpsData);
		Cell cell1 = new Cell(228, 3, 6505, 1146748);
		assertTrue(cellPosition.containsKey(cell1));
		assertEquals(6.789769, cellPosition.get(cell1).getLongitude(),0.0001);
		assertEquals(46.60695, cellPosition.get(cell1).getLatitude(),0.0001);
		assertEquals(0.209876, cellPosition.get(cell1).getStdDev(), 0.00001);	
	}

	@Test
	public void testFindMatchingGpsDataNoTolerance() throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("src/edu/umich/eecs/test/files/gps_test_file.txt"));
		CellGpsDataImporter importer = new CellGpsDataImporter(20);
		List<GpsTimestampedData> data = importer.readGpsFile(scanner);
		Collections.sort(data);
		assertEquals(10454, data.size());
		
		GpsTimestampedData needle = importer.findMatchingGpsData(1265269501, data, 0);
		assertEquals(1265269501, needle.time);
		assertEquals(6.9445123, needle.longitude, 0.0001);
		assertEquals(46.3224622, needle.latitude, 0.0001);
		
		needle = importer.findMatchingGpsData(1270228938, data, 0);
		assertEquals(1270228938, needle.time);
		assertEquals(6.5878938, needle.longitude, 0.0001);
		assertEquals(46.518234, needle.latitude, 0.0001);
		
		needle = importer.findMatchingGpsData(1270228939, data, 0);
		assertEquals(null, needle);
	}
	
	@Test
	public void testFindMatchingGpsDataWithTolerance() throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("src/edu/umich/eecs/test/files/gps_test_file.txt"));
		CellGpsDataImporter importer = new CellGpsDataImporter(20);
		List<GpsTimestampedData> data = importer.readGpsFile(scanner);
		Collections.sort(data);
		assertEquals(10454, data.size());
		
		// there is no such timestamp in the file
		GpsTimestampedData needle = importer.findMatchingGpsData(1270228942, data, 10);
		assertEquals(1270228938, needle.time);
		assertEquals(6.58789, needle.longitude, 0.0001);
		assertEquals(46.5182, needle.latitude, 0.0001);
		
		// there is no such timestamp in the file
		needle = importer.findMatchingGpsData(1270228942, data, 4);
		assertEquals(1270228938, needle.time);
		assertEquals(6.58789, needle.longitude, 0.0001);
		assertEquals(46.5182, needle.latitude, 0.0001);
		
		// there is no such timestamp in the file
		needle = importer.findMatchingGpsData(1270228943, data, 4);
		assertEquals(null, needle);
	}
	
	@Test
	public void testReadGpsFile() {
		String s =
	"userid	time	tz	gps_time	altitude	longitude	latitude	speed	heading	horizontal_accuracy	horizontal_dop	vertical_accuracy	vertical_dop	speed_accuracy	heading_accuracy	time_since_gps_boot\n"
	+ "017	1255109147	-3600	1255109147	464.0	6.56	46.51	6.44399986267	6.40000009537	102.707122803	5.03999996185	74.0	3.57999992371	21.636000824	163.050003052	31.0\n"
	+ "017	1255109157	-3600	1255109157	457.0	6.56	46.51	3.96000008583	358.519989014	29.5993347168	1.37999999523	52.5	1.78999996185	7.34399986267	72.9800033569	41.0\n"
	+ "017	1255109167	-3600	1255109167	463.5	6.56	46.51	3.0960000515	18.4200000763	43.9310874939	1.49000000954	109.5	3.21000003815	3.85200018883	70.4499969482	51.0\n"
	+ "017	1255109176	-3600	1255109176	452.5	6.56	46.51	6.5880001545	316.640014648	50.9120635986	2.70000004768	63.5	3.19000005722	23.5439998627	32.2700004578	60.0\n"
	+ "017	1255109186	-3600	1255109186	450.0	6.56	46.51	2.3039999485	247.059997559	45.9310417175	2.27999997139	95.0	3.29999995232	8.42399969101	207.559997559	70.0\n"
	+ "017	1255109196	-3600	1255109196	451.5	6.56	46.51	2.3039999485	247.059997559	114.669120789	2.59999990463	250.5	5.48000001907	8.42399969101	207.559997559	80.0\n"
	+ "017	1255109206	-3600	1255109206	452.0	6.56	46.51	2.3039999485	247.059997559	188.931594849	2.42000007629	14.0	0.97000002861	8.42399969101	207.559997559	90.0\n"
	+ "017	1255109216	-3600	1255109216	453.0	6.56	46.51	2.3039999485	247.059997559	56.8741912842	3.28999996185	115.0	6.23999977112	8.42399969101	207.559997559	100.0\n"
	+ "017	1255109226	-3600	1255109226	454.0	6.52	47.51	2.3039999485	247.059997559";
		
		Scanner scanner = new Scanner(s);
		CellGpsDataImporter importer = new CellGpsDataImporter(20);
		List<GpsTimestampedData> data = importer.readGpsFile(scanner);
		assertEquals(9, data.size());
		
		assertEquals(1255109147, data.get(0).time);
		assertEquals(6.56, data.get(0).longitude, 0.0001);
		assertEquals(46.51, data.get(0).latitude, 0.0001);
		
		assertEquals(1255109226, data.get(8).time);
		assertEquals(6.52, data.get(8).longitude, 0.0001);
		assertEquals(47.51, data.get(8).latitude, 0.0001);
		
	}

}
