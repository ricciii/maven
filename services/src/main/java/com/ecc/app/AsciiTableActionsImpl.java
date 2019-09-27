package com.ecc.app;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
import java.io.File;

public class AsciiTableActionsImpl implements AsciiTableActions {
	
	private AsciiTable asciiTable;
	private KeyBank keyBank;
	private TextReaderWriter textReaderWriter;
	private Scanner scanner = new Scanner(System.in);
	private RandomAsciiGenerator generator = new RandomAsciiGenerator();

	public AsciiTableActionsImpl(AsciiTable asciiTable) {
		this.asciiTable = asciiTable;
		this.textReaderWriter = new TextReaderWriter(asciiTable);
		this.keyBank = this.textReaderWriter.getKeyBank();
	}

	public AsciiTableActionsImpl(AsciiTable asciiTable, File file) {
		this.asciiTable = asciiTable;
		this.textReaderWriter = new TextReaderWriter(asciiTable, file);
		this.keyBank = this.textReaderWriter.getKeyBank();
	}

	public void print() {
		int i;
		int j;
		for(i=0; i<this.asciiTable.getTable().size();i++) {
			for(j=0; j<this.asciiTable.getTable().get(i).size(); j++) {
				System.out.print("«"+this.asciiTable.getTable().get(i).get(j).getKey()+"・"
					+this.asciiTable.getTable().get(i).get(j).getValue()+"» ");
			}
			System.out.println();
		}
	}

	public void search() {
		System.out.print("What do you want to search: ");
		String pattern = scanner.nextLine();
		int patternLength = pattern.length();
		int i;
		int j;
		int counterKey=0;
		int counterValue=0;
		int total=0;
		String string = new String();
		for(i=0; i<this.asciiTable.getTable().size();i++){
			for(j=0; j<this.asciiTable.getTable().get(i).size(); j++){
				string = this.asciiTable.getTable().get(i).get(j).getKey();
				int length = string.length();
				int a;
				int b;
				for(a=0; a<=length-patternLength;a++) {
					 for(b=0; b<patternLength; b++) {
					 	if(string.charAt(a+b)!=pattern.charAt(b)) {
					 		break;
					 	}
					 } 
					 if(b==patternLength) {
					 	counterKey++;
					 	b=0;
					 }
				}
				string=this.asciiTable.getTable().get(i).get(j).getValue();
				length=string.length();
				for(a=0; a<=length-patternLength;a++) {
					 for(b=0; b<patternLength; b++) {
					 	if(string.charAt(a+b)!=pattern.charAt(b)) {
					 		break;
					 	}
					 } 
					 if(b==patternLength) {
					 	counterValue++;
					 	b=0;
					 }
				}

				if(counterKey>0 || counterValue>0) {
					System.out.format("Found at: Index[%d][%d]\n", i,j);
					if(counterKey>0) {
						System.out.format("Key occurences in this index: %d\n", counterKey);
						total+=counterKey;
						counterKey=0;
					} 
					if(counterValue>0) {
						System.out.format("Value occurences in this index: %d\n", counterValue);
						total+=counterValue;
						counterValue=0;
					}
				}
			}
		}
		System.out.format("Total # of \"%s\" Occurences: %d \n", pattern, total);
	}

	public void edit() {
		scanner = new Scanner(System.in);
		try {
			int choice;
			System.out.println("Which one do you want to edit?\n1=Key 2=Value 3=Both");
			System.out.print("Input: ");
			choice=scanner.nextInt();
			
			switch(choice) {
				case 1:
						editKey();
						break;
				case 2: 
						editValue();
						break;
				case 3: 
						editBoth();
						break;
				default: 
						System.out.println("Not in the choices. Please try again.");
						edit();
			}
			
		} catch(Exception exception) {
			System.out.println("Not in the choices. Please try again.");
			edit();
		}
		textReaderWriter.update();
		System.out.println("Edited Successfully!");
	}

	private void editKey() {
		int row;
		int column;
		String newKey;
		Scanner scanner = new Scanner(System.in);
		try {
			System.out.print("Enter the row index: ");
			row = scanner.nextInt();
			scanner = new Scanner(System.in);
			System.out.print("Enter the column index: ");
			column = scanner.nextInt();
			scanner = new Scanner(System.in);
			System.out.print("Enter the new key: ");
			newKey = scanner.nextLine();
			if(keyBank.contains(newKey)) {
				throw new KeyValueException();
			}
			this.asciiTable.getTable().get(row).get(column).setKey(newKey);
			this.keyBank.add(newKey);
			this.keyBank.remove(this.asciiTable.getTable().get(row).get(column).getKey());

		} catch(IndexOutOfBoundsException indexOutOfBoundsException) {
			System.out.println("No such index.");
		} catch(KeyValueException pairValueKeyException) {
			System.out.println("Key already exists.");
		} catch(Exception exception) {
			System.out.println("Something went wrong. Going back to menu.");
		}
	}

	private void editValue() {
		int row;
		int column;
		String newValue;
		scanner = new Scanner(System.in);
		try {
			System.out.print("Enter the row index: ");
			row = scanner.nextInt();
			scanner = new Scanner(System.in);
			System.out.print("Enter the column index: ");
			column = scanner.nextInt();
			scanner = new Scanner(System.in);
			System.out.print("Enter the new value: ");
			newValue = scanner.nextLine();
			this.asciiTable.getTable().get(row).get(column).setValue(newValue);

		} catch(IndexOutOfBoundsException indexOutOfBoundsException) {
			System.out.println("No such index.");
		} catch(Exception exception) {
			System.out.println("Something went wrong. Going back to menu.");
		}
	}

	private void editBoth() {
		int row;
		int column;
		String newKey;
		String newValue;
		scanner = new Scanner(System.in);
		try {
			System.out.print("Enter the row index: ");
			row = scanner.nextInt();
			scanner = new Scanner(System.in);
			System.out.print("Enter the column index: ");
			column = scanner.nextInt();
			scanner = new Scanner(System.in);
			System.out.print("Enter the new key: ");
			newKey = scanner.nextLine();
			if(keyBank.contains(newKey)) {
				throw new KeyValueException();
			}
			this.asciiTable.getTable().get(row).get(column).setKey(newKey);
			this.keyBank.add(newKey);
			this.keyBank.remove(this.asciiTable.getTable().get(row).get(column).getKey());
			scanner = new Scanner(System.in);
			System.out.println("Enter the new value: ");
			newValue = scanner.nextLine();
			this.asciiTable.getTable().get(row).get(column).setValue(newValue);

		} catch(IndexOutOfBoundsException indexOutOfBoundsException) {
			System.out.println("No such index. Please try again.");
			editBoth();
		} catch(KeyValueException pairValueKeyException) {
			System.out.println("Key already exists. Please try again.");
			editBoth();
		} catch(Exception exception) {
			System.out.println("Something went wrong. Please try again.");
			editBoth();
		}
	}

	public void sort() {
		try {
			scanner = new Scanner(System.in);
			System.out.println("What order do you want to sort?\n1=Ascending 2=Descending");
			System.out.print("Input: ");
			int choice = scanner.nextInt();

			switch(choice) {
				case 1:
						sortAscending();
				break;
				
				case 2:
						sortDescending();
				break;
				
				default:
						System.out.println("Not in the choices. Please try again.");
						sort();
			} 
		} catch(Exception exception) {
			System.out.println(exception);
			sort();
		}
		textReaderWriter.update();
		System.out.println("Sorted Successfully!");
	}

	private void sortAscending() {
		ArrayList<KeyValue> tempList = new ArrayList<KeyValue>();
		int i;
		int j;
		for(i=0; i<this.asciiTable.getTable().size();i++) {
			for(j=0;j<this.asciiTable.getTable().get(i).size();j++) {
				tempList.add(this.asciiTable.getTable().get(i).get(j));
			}
		}

		int size = tempList.size();
      	int a;
      	int b;
     	for(a = 0; a<size-1; a++) {
     		for (b = a+1; b<size; b++) {
         		String string1 = new String();
         		string1=tempList.get(a).getCode();
         		String string2 = new String();
         		string2=tempList.get(b).getCode();

            	if(string1.compareTo(string2)>0) {
                	KeyValue tempKeyValue = new KeyValue(tempList.get(a).getKey(),tempList.get(a).getValue());
               		tempList.get(a).setKey(tempList.get(b).getKey());
               		tempList.get(a).setValue(tempList.get(b).getValue());
               		tempList.get(b).setKey(tempKeyValue.getKey());
               		tempList.get(b).setValue(tempKeyValue.getValue());
            }
         }
      }
      int x=0;
      for(i=0; i<this.asciiTable.getTable().size();i++) {
      			if(x>size) {
      				break;
      			}
      			for(j=0; j<this.asciiTable.getTable().get(i).size(); j++) {
      				this.asciiTable.getTable().get(i).get(j).setKey(tempList.get(x).getKey());
      				this.asciiTable.getTable().get(i).get(j).setValue(tempList.get(x).getValue());
      				x++;
      			}
      		} 
	}

	private void sortDescending() {
		ArrayList<KeyValue> tempList = new ArrayList<KeyValue>();
		int i;
		int j;
		for(i=0; i<this.asciiTable.getTable().size();i++) {
			for(j=0;j<this.asciiTable.getTable().get(i).size();j++) {
				tempList.add(this.asciiTable.getTable().get(i).get(j));
			}
		}

		int size = tempList.size();
      	int a;
      	int b;
     	for(a = 0; a<size-1; a++) {
     		for (b = a+1; b<size; b++) {
         		String string1 = new String();
         		string1=tempList.get(a).getCode();
         		String string2 = new String();
         		string2=tempList.get(b).getCode();

            	if(string1.compareTo(string2)<0) {
                	KeyValue tempKeyValue = new KeyValue(tempList.get(a).getKey(),tempList.get(a).getValue());
               		tempList.get(a).setKey(tempList.get(b).getKey());
               		tempList.get(a).setValue(tempList.get(b).getValue());
               		tempList.get(b).setKey(tempKeyValue.getKey());
               		tempList.get(b).setValue(tempKeyValue.getValue());
            }
         }
      }
      int x=0;
      for(i=0; i<this.asciiTable.getTable().size();i++) {
      			if(x>size) {
      				break;
      			}
      			for(j=0; j<this.asciiTable.getTable().get(i).size(); j++) {
      				this.asciiTable.getTable().get(i).get(j).setKey(tempList.get(x).getKey());
      				this.asciiTable.getTable().get(i).get(j).setValue(tempList.get(x).getValue());
      				x++;
      			}
      		} 
	}

	public void addRow() throws IndexOutOfBoundsException {
		int i=0;
		int size=this.asciiTable.getTable().size();
		String stringKey;
		String stringValue;
		if(size<=0) {
			System.out.println("Table is empty. No max column found.");
			try {
				Scanner scanner = new Scanner(System.in);
				System.out.print("Enter the number of columns: ");
				int column = scanner.nextInt();
				if(column<0) {
					throw new NegativeIndexException();
				}
				this.asciiTable.getTable().add(new ArrayList<KeyValue>());
				for(i=0; i<column;i++) {
					do {
						stringKey = this.generator.generate();
					} while(this.keyBank.contains(stringKey)==true);
					this.keyBank.add(stringKey);
					stringValue = this.generator.generate();
					this.asciiTable.getTable().get(this.asciiTable.getTable().size()-1).add(new KeyValue(stringKey, stringValue));
				}

			} catch(NegativeIndexException negativeIndexException) {
				
				System.out.println("Please input a positive integer. Try again.");
				addRow();

			} catch(Exception exception) {
				
				System.out.println("Please input an integer. Try again.");
				addRow();

			}
		} else {
			i=0;
			size=this.asciiTable.getTable().get(i).size();
			for(i=0;i<this.asciiTable.getTable().size();i++) {
				if(this.asciiTable.getTable().get(i).size()>size) {
					size=this.asciiTable.getTable().get(i).size();
				}
			}
			this.asciiTable.getTable().add(new ArrayList<KeyValue>());

			for(i=0; i<size;i++) {
				do {
					stringKey = this.generator.generate();
				} while(keyBank.contains(stringKey)==true);
				stringValue = this.generator.generate();
				this.asciiTable.getTable().get(this.asciiTable.getTable().size()-1).add(new KeyValue(stringKey, stringValue));
			}
		}
		this.textReaderWriter.update();
		System.out.println("Row Successfully Added!");
	}

	public void reset() {
		try {
			Scanner scanner = new Scanner(System.in);
			System.out.print("Enter the number of rows: ");
			int rows = scanner.nextInt();
			if(rows<=0) {
				throw new NegativeIndexException();
			}
			scanner = new Scanner(System.in);
			System.out.print("Enter the number of columns: ");
			int columns = scanner.nextInt();
			if(columns<=0) {
				throw new NegativeIndexException();
			}
			this.textReaderWriter.setAsciiTable(new AsciiTable());
			this.textReaderWriter.setKeyBank(new KeyBank());
			this.asciiTable = this.textReaderWriter.getAsciiTable();
			this.keyBank = this.textReaderWriter.getKeyBank();
			int i;
			int j;
			String stringKey;
			String stringValue;
			for(i=0;i<rows;i++) {
				this.asciiTable.getTable().add(new ArrayList<KeyValue>());
				for(j=0;j<columns;j++) {
					do {
						stringKey =this.generator.generate();
					} while(keyBank.contains(stringKey)==true);
					this.keyBank.add(stringKey);
					stringValue =this.generator.generate();
					this.asciiTable.getTable().get(i).add(new KeyValue(stringKey, stringValue));
				}
			}
		} catch(NegativeIndexException negativeIndexException) {
			System.out.println("Please input a positive integer which is more than 0. Try again.");
			reset();
		} catch(Exception exception) {
			System.out.println("Please input an integer. Try again.");
			reset();
		}
		textReaderWriter.update();
		System.out.println("Reset Successfully!");
	}

	public void exit() {
		System.out.println("Closing program...");
	}
}