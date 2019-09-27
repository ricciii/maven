package com.ecc.app;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter; 
import java.io.IOException; 
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.commons.lang3.StringUtils;

public class TextReaderWriter {
	private File file;
	private AsciiTable asciiTable;
	private KeyBank keyBank = new KeyBank();
	
	public TextReaderWriter(AsciiTable asciiTable, File file) {
		this.asciiTable = asciiTable;
		this.file = file;
		read();
	}

	public TextReaderWriter(AsciiTable asciiTable) {
		this.asciiTable = asciiTable;
		this.file = new File("text.txt");
		try {
			this.file.createNewFile();
		} catch(Exception exception) {}
		create();
	}

	public void read() { 
		int i=0;
		int start;
		int end;
		try {
	 		FileReader fileReader = new FileReader(file);
	 		BufferedReader bufferedReader = new BufferedReader(fileReader);
	 		String string = "«[\\x20-\\x7E]*・[\\x20-\\x7E]*»";
	 		Pattern pattern = Pattern.compile(string);
	 		Matcher matcher;
	 		String line;
	 		while((line = bufferedReader.readLine()) != null) {
	 			this.asciiTable.getTable().add(new ArrayList<KeyValue>());
 		        matcher = pattern.matcher(line);
		        while (matcher.find()==true) {
			        start = matcher.start(0);
			        end = matcher.end(0);
			        String[] array = StringUtils.split(line.substring(start+1, end-1),"・"); 
			        if(keyBank.contains(array[0])==false) {
			        	this.asciiTable.getTable().get(i).add(new KeyValue(array[0], array[1]));
			        	this.keyBank.add(array[0]);	
			        }
 		      	}
 		    	i++;
	 		}
		} catch(FileNotFoundException fileNotFoundException) {
		 	System.out.println(fileNotFoundException);
		} catch(IOException iOException) {
		 	System.out.println(iOException);
		} catch(Exception exception) {
			System.out.println("Something went wrong.");
		}
	}

	public AsciiTable getAsciiTable() {
		return this.asciiTable;
	}
	
	public KeyBank getKeyBank() {
		return this.keyBank;
	}

	public void update() {
		try {
			this.file.createNewFile();
			PrintWriter writer = new PrintWriter(this.file, "UTF-8");
			int i;
			int j;
			for(i=0; i<this.asciiTable.getTable().size();i++) {
				for(j=0; j<this.asciiTable.getTable().get(i).size(); j++) {
					writer.write("«"+this.asciiTable.getTable().get(i).get(j).getKey()+"・"
						+this.asciiTable.getTable().get(i).get(j).getValue()+"» ");
				}
				writer.write("\n");
			}
			writer.close();
		} catch(FileNotFoundException fileNotFoundException) {
			System.out.println("Something went wrong. File not Found.");
		}catch(Exception exception) {
			System.out.println("Something went wrong.");
		}
	}

	public void create() {
		int start;
		int end;
		int i=0;
		try {
			PrintWriter writer = new PrintWriter(this.file, "UTF-8");
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("text.txt");
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String string = "«[\\x20-\\x7E]*・[\\x20-\\x7E]*»";
	 		Pattern pattern = Pattern.compile(string);
	 		Matcher matcher;
	 		String line;

	 		while((line = bufferedReader.readLine()) != null) {
	 			this.asciiTable.getTable().add(new ArrayList<KeyValue>());
 		        matcher = pattern.matcher(line);
		        while (matcher.find()==true) {
			        start = matcher.start(0);
			        end = matcher.end(0);
			        String[] array=line.substring(start+1, end-1).split("・"); 
			        if(this.keyBank.contains(array[0])==false) {
			        	this.asciiTable.getTable().get(i).add(new KeyValue(array[0], array[1]));
			        	this.keyBank.add(array[0]);	
			        }
 		      	} 
 		      	i++;
	 		}
			int j;
			for(i=0; i<this.asciiTable.getTable().size();i++) {
				for(j=0; j<this.asciiTable.getTable().get(i).size(); j++) {
					writer.print("«"+this.asciiTable.getTable().get(i).get(j).getKey()+"・"
						+this.asciiTable.getTable().get(i).get(j).getValue()+"» ");
				}
				writer.print("\n");
			}
			writer.close();
		} catch(FileNotFoundException fileNotFoundException) {
			System.out.println("Something went wrong. File not Found.");
		} catch(Exception exception) {
			System.out.println(exception);
		}
	}

	public void setAsciiTable(AsciiTable asciiTable) {
		this.asciiTable = asciiTable;
	}

	public void setKeyBank(KeyBank keyBank) {
		this.keyBank = keyBank;
	}
}