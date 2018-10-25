package LibraryManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Patron {
	//Properties
	private String name;
	private String pId;
	
	//Constructor
	public Patron(String id, String name) {
		this.pId = id;
		this.name = name;
	}
	
	public Patron(int id, String name) {
		this.pId = genId(id);
		this.name = name;
	}
	
	//methods
	public String toString() {
		return "Patron ID: " + this.pId + " Name: " + this.name;
	}
	
	public String genId(int id) {
		return "P" + id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getId() {
		return this.pId;
	}
	
	public void toFile(String filename) {
		
		try {
			PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)));
			output.append("\n" + this.pId + "   " + this.name);
			output.close();
		} catch (IOException e) {

		}
	}
	public static Patron addPatron(int id) {
		Scanner patScanner = new Scanner(System.in);
		System.out.println("Please patron name:");
		String name = patScanner.nextLine();
		Patron newP = new Patron(id, name);
		return newP;
	}
	
	public static ArrayList<Patron> makePatronList(String filename) {
		ArrayList<Patron> mainArrayList = new ArrayList<Patron>();
		try {
		BufferedReader file = new BufferedReader(new FileReader(filename));
		String strLine;
		while ((strLine = file.readLine()) != null) {
			String[] elements = strLine.split("\\s");
			List<String> fixedList = Arrays.asList(elements);
			ArrayList<String> listString = new ArrayList<String>(fixedList);
			String id = listString.get(0).replaceAll("\\s*", "");
			String pName = listString.get(1).replaceAll("\\s*", "");
			Patron newPatron = new Patron(id, pName);
			mainArrayList.add(newPatron);
		}
		}
		catch (IOException e) {
			
		}
		return mainArrayList;

	}
	public static ArrayList<String> showBorrower(String bId, String filename) {
		FileReader fReader;
		BufferedReader bReader;
		String strLine;
		String bookIdTemp;
		String bookId;
		String pId;
		String pIdTemp;
		ArrayList<String> patBor = new ArrayList<String>();
		try {
			fReader = new FileReader(filename);
			bReader = new BufferedReader(fReader);
			while ((strLine = bReader.readLine()) != null) {
				String[] elements = strLine.split("\\s*,\\s*");
				List<String> fixedList = Arrays.asList(elements);
				bookIdTemp = fixedList.get(0);
				bookId = bookIdTemp.replaceAll("\\s","");
				pIdTemp = fixedList.get(1);
				pId = pIdTemp.replaceAll("\\s*","");
				if (Objects.equals(bookId, bId) == true) {
					patBor.add(pId);
				}
		}
		}
		catch (IOException e) {
			
		}
		return patBor;
	}
}
