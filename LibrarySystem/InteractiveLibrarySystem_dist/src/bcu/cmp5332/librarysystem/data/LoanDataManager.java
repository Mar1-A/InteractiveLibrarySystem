package bcu.cmp5332.librarysystem.data;

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Loan;
import bcu.cmp5332.librarysystem.model.Patron;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Scanner;

public class LoanDataManager implements DataManager {

    public final String RESOURCE = "./resources/data/loans.txt";

    @Override
    public void loadData(Library library) throws IOException, LibraryException {
        try (Scanner sc = new Scanner(new File(RESOURCE))) {
            int line_idx = 1;
            if(sc.hasNext()) { //checks if the file is empty.
	            while (sc.hasNextLine()) {
	                String line = sc.nextLine();
	                String[] properties = line.split(SEPARATOR, -1);
	                try {
	                    int patronID = Integer.parseInt(properties[0]);
	                    int bookID = Integer.parseInt(properties[1]);
	                    // load the start date and finish date of the loan.
	                    LocalDate startDate = LocalDate.parse(properties[2]);
	                    LocalDate dueDate = LocalDate.parse(properties[3]);
	                    // load the status of the loan if it is terminated or not.
	                    boolean isTerminated = Boolean.parseBoolean(properties[4]);
	                    LocalDate returnDate = null;
	                    if (isTerminated) {
	                        returnDate = LocalDate.parse(properties[5]);
	                    }
	                    
	                    Patron patron = library.getPatronByID(patronID);
	                    Book book = library.getBookByID(bookID);
	
	                    Loan loan = new Loan(patron, book, startDate, dueDate);
	                    // terminate a loan 
	                    loan.setTerminated(isTerminated, returnDate);
	                 // if the loan is not terminated the book will be added to the patron list of books when loading the loan data.
	                    if (!loan.isTerminated()) {  
	                        patron.addBook(book);
	                    }
	                    book.setLoan(loan);// set loan for a book
	
	                } catch (NumberFormatException ex) {
	                    throw new LibraryException("Unable to parse patron/book id " + properties[0] + properties[1]
	                    		+ " on line " + line_idx
	                            + "\nError: " + ex);
	                }
	                line_idx++;
	            }
	        }else {
	            System.out.println("[ERROR]No loans could be retrieved.");
	         }
        }
    }

    @Override
    public void storeData(Library library) throws IOException {
    	if (LibraryData.isLoanAccessible()) {// Checks if loans file accessible
	        try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {
	            for (Book book : library.getBooks()) {
	                if (book.getLoan() == null) {// if the book is not on loan it will continue to the next book
	                    continue;
	                }
	                // if it is it will store the loan info 
	                Loan loan = book.getLoan();
	                out.print(loan.getPatron().getId() + SEPARATOR);
	                out.print(loan.getBook().getId() + SEPARATOR);
	                out.print(loan.getStartDate() + SEPARATOR);
	                out.print(loan.getDueDate() + SEPARATOR);
	                out.print(loan.isTerminated() + SEPARATOR);
	                out.print(loan.getReturnDate() + SEPARATOR);
	                out.println();
	            }
	        }
    	}else {
    		System.out.println("Can not accesse loans file");
    	}
    }

}
