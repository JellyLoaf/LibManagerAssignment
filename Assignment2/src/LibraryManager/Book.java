package LibraryManager;

/**
 * @author Erik Stroud
 * 
 * @version 1.0.0
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Book {
	private String id;
	private String title;
	private String author;
	private int pubYear;
	private Boolean checkOut;
	private String patron;
	// Constructor
	
	
	/**
	 * Default constructor for book. Used when ID is already known. 
	 * 
	 * Patron is set to null until loans are read from file.
	 * 
	 * @param id Unique ID of book
	 * @param title The title of the book
	 * @param author The author of the book
	 * @param year The year the book was published
	 * @param checkOut Whether the book is currently loaned or not
	 * 
	 */
	public Book(String id, String title, String author, int year, boolean checkOut) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.pubYear = year;
		this.checkOut = false;
		this.patron = null;
	}
	/**
	 * Overload constructor for use when ID has to be generated.
	 * 
	 * Patron is set to null until loans are read from file.
	 * 
	 * @param id Unique ID of book
	 * @param title The title of the book
	 * @param author The author of the book
	 * @param year The year the book was published
	 * @param checkOut Whether the book is currently loaned or not
	 */
	public Book(int id, String title, String author, int year, boolean checkOut) {
		this.title = title;
		this.author = author;
		this.pubYear = year;
		this.id = genId(id);
		this.checkOut = false;
		this.patron = null;
	}

	// methods
	
	/**
	 * Converts book into String format for displaying to user.
	 * 
	 * @return String formatted version of book properties
	 */
	public String toString() {
		return "Book ID: " + id + " Title: " + title + " Author: " + author + " Year: " + pubYear + " Checked out: "
				+ this.checkOut;
	}
	
	/**
	 * Writes book data to file in proper format for future use.
	 * 
	 * @param filename The path to the file that will have the date written to it
	 */
	public void toFile(String filename) {

		try {
			PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)));
			output.append("\n" + this.id + " ; " + this.title + " ; " + this.author + " ; " + this.pubYear);
			output.close();
		} catch (IOException e) {

		}
	}
	/**
	 * 
	 * Checks loans file to determine what books are loaned out.
	 * 
	 */
	public void setLoaned() {
		this.checkOut = true;
	}

	public static void setLoans(String filename, ArrayList<Book> bookList) {
		FileReader fReader;
		BufferedReader bReader;
		String strLine;
		String bId;
		String bIdTemp;
		String pId;
		String pIdTemp;
		int j;
		System.out.println(bookList.size());
		try {
			fReader = new FileReader(filename);
			bReader = new BufferedReader(fReader);
			while ((strLine = bReader.readLine()) != null) {
				String[] elements = strLine.split("\\s*,\\s*");
				List<String> fixedList = Arrays.asList(elements);
				bIdTemp = fixedList.get(0);
				pIdTemp = fixedList.get(1);
				bId = bIdTemp.replaceAll("\\s*", "");
				pId = pIdTemp.replaceAll("\\s*", "");
				j = 0;
				while (j != bookList.size()) {
					if (Objects.equals(bId, bookList.get(j).id) == true) {
						bookList.get(j).setLoaned();
						bookList.get(j).patron = pIdTemp;
					}
					j++;
				}
			}
		} catch (IOException e) {
		}
	}
	/**
	 * Generate new unique ID for a newly added Book
	 * @param id The integer that will be combined with B to create proper ID string
	 * 
	 * @return String of the new unique ID
	 */
	public String genId(int id) {
		int newId = id + 1;
		return "B" + newId;
	}
	
	public String getId() {
		return this.id;
	}

	public int getYear() {
		return this.pubYear;
	}

	public String getAuthor() {
		return this.author;
	}

	public void getBook(String bookId) {
	}

	public static Book newBook(int id) {
		Scanner bookScanner = new Scanner(System.in);
		System.out.println("Please enter the title of the book:");
		String title = bookScanner.nextLine();
		System.out.println("please enter the author of the book:");
		String author = bookScanner.nextLine();
		System.out.println("Please enter the publication year:");
		String pubYear = bookScanner.nextLine();
		int year = Integer.parseInt(pubYear);
		Book newBook = new Book(id, title, author, year, false);
		return newBook;
	}

	public void checkOut(String pId) {
		try {
			FileWriter fWriter = new FileWriter("Resources/loans.txt");
			fWriter.write(this.id + " , " + pId);
		} catch (IOException e) {

		}
	}

	public static void bookReturn(String id, ArrayList<Book> bookList) {
		int i;
		i = 0;
		while (i != bookList.size()) {
			if (Objects.equals(bookList.get(i).id, id) == true) {
				bookList.get(i).checkOut = false;
			}
			i++;
		}

	}

	public static ArrayList<Book> makeBookList(String filename) {
		BufferedReader bReader;
		FileReader fReader;
		String strLine;
		ArrayList<Book> mainArrayList = new ArrayList<Book>();
		try {
			fReader = new FileReader(filename);
			bReader = new BufferedReader(fReader);
			while ((strLine = bReader.readLine()) != null) {
				String[] elements = strLine.split("\\s*;\\s*");
				List<String> fixedList = Arrays.asList(elements);
				ArrayList<String> listString = new ArrayList<String>(fixedList);
				String id = listString.get(0).replaceAll("\\s*", "");
				String title = listString.get(1);
				String author = listString.get(2);
				int iYear = Integer.parseInt(listString.get(3));
				Book newBook = new Book(id, title, author, iYear, false);
				mainArrayList.add(newBook);
			}
		} catch (IOException e) {

		}
		return mainArrayList;
	}

	public static ArrayList<String> showBorrowed(String pId, String filename) {
		FileReader fReader;
		BufferedReader bReader;
		String strLine;
		String bId;
		String bIdTemp;
		String patId;
		String patIdTemp;
		ArrayList<String> borBooks = new ArrayList<String>();
		try {
			fReader = new FileReader(filename);
			bReader = new BufferedReader(fReader);
			while ((strLine = bReader.readLine()) != null) {
				String[] elements = strLine.split("\\s*,\\s*");
				List<String> fixedList = Arrays.asList(elements);
				bIdTemp = fixedList.get(0);
				bId = bIdTemp.replaceAll("\\s*", "");
				patIdTemp = fixedList.get(1);
				patId = patIdTemp.replaceAll("\\s*", "");
				// System.out.println(patId);
				// System.out.println(bId);
				if (Objects.equals(patId, pId) == true) {
					borBooks.add(bId);
				} else {

				}
			}

		} catch (IOException e) {

		}
		return borBooks;
	}

	public static ArrayList<ArrayList> makeLoanList(String filename) {
		FileReader fReader;
		BufferedReader bReader;
		String strLine;
		String pId;
		String pIdTemp;
		String bId;
		String bIdTemp;
		ArrayList<ArrayList> loans = new ArrayList<ArrayList>();
		ArrayList<String> loan = new ArrayList<String>();
		try {
			fReader = new FileReader(filename);
			bReader = new BufferedReader(fReader);
			while ((strLine = bReader.readLine()) != null) {
				String[] elements = strLine.split("\\s*,\\s*");
				List<String> fixedList = Arrays.asList(elements);
				bIdTemp = fixedList.get(0);
				bId = bIdTemp.replaceAll("\\s*", "");
				pIdTemp = fixedList.get(1);
				pId = pIdTemp.replaceAll("\\s*", "");
				loan.add(bId);
				loan.add(pId);
				loans.add(loan);
				loan.clear();
			}
		} catch (IOException e) {
		}
		return loans;
	}

	public static void loansToFile(String filename, ArrayList<Book> bookList) {

		int i;
		PrintWriter pw;
		try {
			pw = new PrintWriter(filename);
			pw.close();
			try {
				PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)));
				i = 0;
				while (i != bookList.size()) {
					if (bookList.get(i).checkOut == true) {
						output.append(bookList.get(i).getId() + " ," + bookList.get(i).patron + "\n");
					}
					i++;
				}
				output.close();
			} catch (IOException e) {

			}
		} catch (FileNotFoundException e) {

		}

	}
}
