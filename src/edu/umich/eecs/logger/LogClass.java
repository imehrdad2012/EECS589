package edu.umich.eecs.logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LogClass {
	
	static FileWriter fstream; 
	static BufferedWriter out;
	
	static{
		

		try {
			fstream= new FileWriter("log.txt");
			out= new BufferedWriter(fstream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void log(String logdata){
		 try{
			fstream= new FileWriter("log.txt",true);
			out= new BufferedWriter(fstream);
			out.write(logdata+"\n");
			out.close();
			  
		 }
		 catch (Exception e){
	
			  System.err.println("Error: " + e.getMessage());
			  
		 }
	}
		
}

