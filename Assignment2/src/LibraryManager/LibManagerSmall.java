package LibraryManager;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class LibManagerSmall {
	private ArrayList<Book> bookList;
	private ArrayList<Patron> patronList;

	private String[] menuOptions;

	public static void main(String[] args) {
		LibManagerSmall lm = new LibManagerSmall();
		lm.execute();
	}

	public LibManagerSmall() {
		bookList = readBooks("Resources/books.txt");
		patronList = readPatrons("Resources/patrons.txt");
		recordLoans("Resources/loans.txt");

		menuOptions = new String[] { "Add Book", "Add Patron", "List By Author", "List By Year", "Show Borrower",
				"Show Borrowed Books", "Return Book", "Exit" };
	}

	private void execute() {

		while (true) {
			int opt = getMenuOption();
			switch (opt) {
			case 1:
				addBook();
				break;
			case 2:
				addPatron();
				break;
			case 3:
				listByAuthor();
				break;
			case 4:
				listByYear();
				break;
			case 5:
				showBorrowers();
				break;
			case 6:
				showBorrowedBooks();
				break;
			case 7:
				returnBook();
				break;
			case 8:
				exitProgram();
				break;
			default:
				System.out.println("No such option");
			}
		}

	}

	private int getMenuOption() {

		System.out.println("Select one of the following options");
		for (int i = 0; i < menuOptions.length; i++) {
			System.out.println("\t" + (i + 1) + ". " + menuOptions[i]);
		}

		Scanner s = new Scanner(System.in);
		int choice = s.nextInt();

		return choice;
	}

	/* MAKE NO CHANGES ABOVE THIS LINE */
	/* COMPLETE ALL THE CODE STUBS BELOW */

	private void exitProgram() {
		System.out.println("Exiting..");
		ArrayList<Book> bookListOld = readBooks("Resources/books.txt");
		ArrayList<Patron> patronListOld = readPatrons("Resources/patrons.txt");
		int i;
		i = 0;
		int differenceBooks = bookList.size() - bookListOld.size();
		while (i != differenceBooks) {
			bookList.get(bookListOld.size()+i).toFile("Resources/books.txt");
			i++;
		}
		int differencePatrons = patronList.size() - patronListOld.size();
		i = 0;
		while (i != differencePatrons) {
			patronList.get(patronListOld.size()+i).toFile("Resources/patrons.txt");
			i++;
		}
		Book.loansToFile("Resources/loans.txt", bookList);
		System.exit(0);
		
	}

	private ArrayList<Book> readBooks(String filename) {
		// TODO Auto-generated method stub
		System.out.println("Reading file " + filename);
		ArrayList<Book> bookList;
		bookList = Book.makeBookList(filename);
		return bookList;
	}

	private ArrayList<Patron> readPatrons(String filename) {
		// TODO Auto-generated method stub
		System.out.println("Reading file " + filename);
		return Patron.makePatronList(filename);
	}

	private void recordLoans(String filename) {
		// TODO Auto-generated method stub
		System.out.println("Reading file " + filename);
		Book.setLoans(filename, bookList);
	}

	private Book locateBook(String bookId) {
		// TODO Auto-generated method stub
		System.out.println("Locating book with id = " + bookId);
		int i = 0;
		Book tempBook = null;
		while (i != bookList.size()) {
			if (Objects.equals(bookList.get(i).getId(), bookId) == true) {
				tempBook = bookList.get(i);
			}
		}
		return tempBook;
	}

	private Patron locatePatron(String patronId) {
		// TODO Auto-generated method stub
		System.out.println("Locating patron with id =" + patronId);
		int i = 0;
		Patron tempPat = null;
		while (i != patronList.size()) {
			if (Objects.equals(patronList.get(i).getId(), patronId) == true) {
				tempPat = patronList.get(i);
			}
		}
		return tempPat;
	}

	private void showBorrowedBooks() {
		// TODO Auto-generated method stub
		System.out.println("Executing showBorrowedBooks");
		Scanner pScan = new Scanner(System.in);
		String pId;
		System.out.print("Please ID of Patron: ");
		pId = pScan.nextLine();
		ArrayList<String> borBooks; 
		borBooks = Book.showBorrowed(pId, "Resources/loans.txt");
		int i = 0;
		int j = 0;
		while (i != borBooks.size()) {
			j = 0;
			while (j != bookList.size()) {
				if ((bookList.get(j).getId().equals(borBooks.get(i)) == true)) {
					System.out.println(bookList.get(j));
				}
				j++;
			}
			i++;
		}
	}

	private void showBorrowers() {
		// TODO Auto-generated method stub
		System.out.println("Executing showBorrowers");
		Scanner bScan = new Scanner(System.in);
		String bId;
		System.out.print("Please enter the ID of Book: ");
		bId = bScan.nextLine();
		ArrayList<String> patBors; 
		patBors = Patron.showBorrower(bId, "Resources/loans.txt");
		int i = 0;
		int j = 0;
		while (i != patBors.size()) {
			j = 0;
			while (j != patronList.size()) {
				if ((patronList.get(j).getId().equals(patBors.get(i)) == true)) {
					System.out.println(patronList.get(j));
				}
				j++;
			}
			i++;
		}
	}
	

	private void listByYear() {
		// TODO Auto-generated method stub
		System.out.println("Executing listByYear");
		int i = 0;
		Scanner yearScanner = new Scanner(System.in);
		String sYear1 = yearScanner.nextLine();
		int year1 = Integer.parseInt(sYear1);
		String sYear2 = yearScanner.nextLine();
		int year2 = Integer.parseInt(sYear2);
		while (i != bookList.size()) {
			if (bookList.get(i).getYear() > year1 && bookList.get(i).getYear() < year2) {
				System.out.println(bookList.get(i));
			}
			i++;
		}
	}

	private void listByAuthor() {
		// TODO Auto-generated method stub
		System.out.println("Executing listByAuthor");
		int i = 0;
		System.out.println("Please enter the name of the author: ");
		Scanner authorScan = new Scanner(System.in);
		String author = authorScan.nextLine();
		while (i != bookList.size()) {
			if (Objects.equals(bookList.get(i).getAuthor(), author)) {
				System.out.println(bookList.get(i));
			}
			i++;
		}
	}

	private void addPatron() {
		// TODO Auto-generated method stub
		System.out.println("Executing addPatron");
		int newId = patronList.size();
		Patron newPatron = Patron.addPatron(newId);
		patronList.add(newPatron);
	}

	private void addBook() {
		// TODO Auto-generated method stub
		System.out.println("Executing addBook");
		int newId = bookList.size();
		Book newBook = Book.newBook(newId);
		bookList.add(newBook);
	}

	private void returnBook() {
		// TODO Auto-generated method stub
		System.out.println("Executing returnBook");
		Scanner bookScan = new Scanner(System.in);
		System.out.println("Please enter the ID of returned book: ");
		String bId = bookScan.nextLine();
		Book.bookReturn(bId, bookList);
		
		
	}

}