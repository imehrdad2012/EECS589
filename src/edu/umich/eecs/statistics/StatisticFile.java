package edu.umich.eecs.statistics;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class StatisticFile {
	
	FileWriter fstream; 
	BufferedWriter out;
	String fileName;
	
	public StatisticFile(String fileName) {
		super();
		this.fileName = fileName;

		try {
			fstream= new FileWriter(fileName);
			out= new BufferedWriter(fstream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	public void appendAndClose(String logdata){
		 try{
			fstream= new FileWriter(fileName,true);
			out= new BufferedWriter(fstream);
			out.write(logdata+"\n");
			out.close();
			  
		 }
		 catch (Exception e){
	
			  System.err.println("Error: " + e.getMessage());
			  
		 }
	}
		
	
	public void writeString(String myData){
		try {
			out.write(myData);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void writeDouble(Double myDouble){
		try {
			out.write(String.valueOf(myDouble));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void writeInt(int myInt){
		try {
			out.write(String.valueOf(myInt));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void tearDown(){
		
		 try{
				out.close();
				  
			 }
			 catch (Exception e){
		
				  System.err.println("Error: " + e.getMessage());	  
			 }
		}
	
	

	


}
