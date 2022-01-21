package bcu.cmp5332.librarysystem.data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;

public class BookDataManager implements DataManager {

    private final String RESOURCE = "./resources/data/books.txt";

    @Override
    public void loadData(Library library) throws IOException, LibraryException {
    	if(LibraryData.isBookAccessible()) {
	    	try (Scanner sc = new Scanner(new File(RESOURCE))) {
	             int line_idx = 1;
	                 while (sc.hasNextLine()) { //checks if the next line is not empty.
	                     String line = sc.nextLine();
	                     if(!line.isEmpty()) { //if the line is not empty it will start collecting the information from the line.
	                         String[] properties = line.split(SEPARATOR, -1);
	                         try {
	                             int id = Integer.parseInt(properties[0]); // records book info from file.
	                             String title = properties[1];
	                             String author = properties[2];
	                             String publicationYear = properties[3];
	                             String publisher = properties[4];
	                             @SuppressWarnings("unused")
								 boolean isDeleted = Boolean.parseBoolean(properties[5]);
	                             Book book = new Book(id,title,author,publicationYear, publisher);
	                             book.setDeleted(isDeleted);
	                             library.addBook(book); //adds the book to the system
	                         } catch (NumberFormatException ex) {
	                             throw new LibraryException("Unable to parse book id " + properties[0] + " on line " + line_idx
	                                 + "\nError: " + ex);
	                         }
	                         line_idx++;
	                     }
	                 }
	        }
    	}
    }

    @Override
    public void storeData(Library library) throws IOException {
    	if(LibraryData.isBookAccessible()) {//checks if file is accessible 
	        try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {
	            for (Book book : library.getBooks()) {
	                out.print(book.getId() + SEPARATOR);
	                out.print(book.getTitle() + SEPARATOR);
	                out.print(book.getAuthor() + SEPARATOR);
	                out.print(book.getPublicationYear() + SEPARATOR);
	                out.print(book.getPublisher() + SEPARATOR);
	                out.print(book.isDeleted() + SEPARATOR);
	                out.println();
	            }
	        }
    	}
    }
}
