package com.ecc.app;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File; 
import org.apache.commons.lang3.ArrayUtils;

public class App {
	
	private TextReaderWriter textReaderWriter;
	private AsciiTable asciiTable;
	private AsciiTableActionsImpl asciiTableActions;

	public static void main(String[] args) {
		App app = new App(); 
		try {
			if(ArrayUtils.isNotEmpty(args)==true) {
				File file = new File(args[0]);
				if(file.exists()==true) {
		        	System.out.println("File found. Using given file...");
		        	app.useGivenFile(file);
		        } else {
		        	System.out.println("File not found. Using default file...");
		        	app.useDefaultFile();
		        }
			} else {
				System.out.println("No file given. Using default file...");
				app.useDefaultFile();
			} 
			app.start();
		} catch(Exception exception) {
			System.out.println(exception);
		}
	}

	public void start() {
		Scanner scanner = new Scanner(System.in);
		boolean cont = true;
		do {
			try {
				System.out.println("What do you want to do?\n1=Print 2=Search 3=Edit 4=Sort\n5=Add Row 6=Reset 7=Exit");
				System.out.print("Input:");
				int choice = scanner.nextInt();

				switch(choice) {
					case 1:
							asciiTableActions.print();
							break;				
					case 2:
							asciiTableActions.search();
							break;
					case 3:
							asciiTableActions.edit();
							break;
					case 4:
							asciiTableActions.sort();
							break;
					case 5:
							asciiTableActions.addRow();
							break;
					case 6:
							asciiTableActions.reset();
							break;
					case 7:
							asciiTableActions.exit();
							cont = false;
							break;
					default:
							System.out.println("Not in the choices. Please try again.");
				} 
			} catch(IndexOutOfBoundsException indexOutOfBoundsException) {
				System.out.println(indexOutOfBoundsException);
			} catch(Exception exception) {
				System.out.println("Something went wrong.");
			}
		} while(cont==true);
	}
		

	private void useDefaultFile() {
		this.asciiTable = new AsciiTable();
		this.asciiTableActions = new AsciiTableActionsImpl(asciiTable);
	}

	private void useGivenFile(File file) {
		this.asciiTable = new AsciiTable();
		this.asciiTableActions = new AsciiTableActionsImpl(asciiTable, file);
	}
}