package edu.umich.eecs.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import edu.umich.eecs.dto.OpenCell;
import edu.umich.eecs.service.OpenCellService;

public class ImportFile {
	
	private FileReader fileReader;
	private BufferedReader bufferReader;
	public FileWriter fileWriter; 
	public BufferedWriter bufferWriter;
	
	public ImportFile(FileReader fileReader, FileWriter fileWriter) {
		super();
		this.fileReader = fileReader;
		this.fileWriter=fileWriter;
		bufferReader = new BufferedReader(fileReader);
		bufferWriter=new BufferedWriter(fileWriter);
	}
	
	public void convert() throws IOException  {
		String sCurrentLine;
		while ((sCurrentLine = bufferReader.readLine()) != null) {
		
			if(sCurrentLine.contains("INSERT INTO \"cells\" VALUES(")){
				
				String result=sCurrentLine.replace("INSERT INTO \"cells\" VALUES(", "");
				String result2=result.replace(");", "");
				bufferWriter.write(result2+"\n");
			}
	

		}
		bufferReader.close();
		fileReader.close();
		bufferWriter.close();
		fileWriter.close();
		
		
	}
	public static void main(String[] args) {
		try {
			for(int i=2;i<18;i++){
				ImportFile ip= new ImportFile(new FileReader("/Users/Mehrdad/Documents/workspace/csv/"+i+".sql"),
						new FileWriter("/Users/Mehrdad/Documents/workspace/csv/out.txt",true));
				ip.convert();
				System.out.println("File "+i+"is Converted!");
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
