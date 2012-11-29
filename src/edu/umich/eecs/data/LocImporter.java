package edu.umich.eecs.data;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import edu.umich.eecs.dto.Location;
import edu.umich.eecs.logger.LogClass;
import edu.umich.eecs.service.LocationService;

public class LocImporter {
	
	private FileReader fileReader;
	private BufferedReader bufferReader;
	private ArrayList<Location> listLocs;
	int  persistCounter;

	
	LocImporter( FileReader fileReader) {
		super();
		this.fileReader = fileReader;
		bufferReader = new BufferedReader(fileReader);
		listLocs=new ArrayList<Location>();
		persistCounter=0;
	}
	
	public void insertIntoDatabase(LocationService l) throws IOException  {
		String sCurrentLine;
		while ((sCurrentLine = bufferReader.readLine()) != null) {
		
			String[] splits = sCurrentLine.toString().split(",");
			Location myloc= 
				new Location(getInteger(splits[0]),getDouble(splits[1]),getDouble(splits[2]),
					getInteger(splits[3]),getInteger(splits[4]), getInteger(splits[5]), getInteger(splits[6])) ;
			persistIntoDatabase(myloc, l);
		}
	}
	
	public void persistIntoDatabase(Location loc, LocationService l){
		
		listLocs.add(loc);
		if(listLocs.size()==1000){
			System.out.println("Persisting"+ (persistCounter++)*1000);
			l.saveListToLocation(listLocs);
			listLocs.clear();
			
		}
		
	}
	public double getDouble(String number){
		if(number.contains("N")) return -10;
		return Double.parseDouble(new String(number).replace("\n", ""));
	}
	
	public int getInteger(String number){
		if(number.contains("N")) return -10;
		return Integer.parseInt(new String(number).replace("\n", ""));
	}
	
	public static void main(String[] args)  {
		
		LocImporter li;
		try {
			
			li = new LocImporter( new FileReader("/Users/Mehrdad/Documents/workspace/589-Project/cells"));
			li.insertIntoDatabase(new LocationService());
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	

}
