package bcu.cmp5332.librarysystem.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import bcu.cmp5332.librarysystem.data.BookDataManager;
import bcu.cmp5332.librarysystem.data.DataManager;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;

/*
 * Note:
 * Performing the following test will result in deleting all Books
 * data from the system. 
 */
public class BookTest {
	
	Book book = new Book(100,"Effective Java", "Joshua Bloch", "2017", "Joshua Bloch");
	
	@Test
	void getPublisherTest() {
		assertEquals("Joshua Bloch", book.getPublisher());
	}
	
	@Test
	void setPublisherTest() {
		book.setPublisher("Bloch Joshua");
		assertEquals("Bloch Joshua", book.getPublisher());
	}
	
	@Test
	void storePublisherTest() throws LibraryException, IOException{
		String RESOURCE = "./resources/data/books.txt";
		
		Library library = new Library();
		library.addBook(book);
		BookDataManager manager = new BookDataManager();
		manager.storeData(library);
		Scanner sc = new Scanner(new File(RESOURCE));
		while (sc.hasNextLine()) {
			final String lineFromFile = sc.nextLine();
			String[] properties = lineFromFile.split(DataManager.SEPARATOR, -1);
			String publisher = properties[4];
			assertEquals("Joshua Bloch", publisher);
		}
	}
	
	@Test
	public void loadPublisherTest() throws LibraryException, IOException{
		String RESOURCE = "./resources/data/books.txt";
		Library library = new Library();
		BookDataManager manager = new BookDataManager();
		PrintWriter out = new PrintWriter(new FileWriter(RESOURCE)); //writing the book info in the text file
        out.print(book.getId() + DataManager.SEPARATOR);
        out.print(book.getTitle() + DataManager.SEPARATOR);
        out.print(book.getAuthor() + DataManager.SEPARATOR);
        out.print(book.getPublicationYear() + DataManager.SEPARATOR);
        out.print(book.getPublisher() + DataManager.SEPARATOR);
        if(book.isDeleted()) {
        	out.print(1 + DataManager.SEPARATOR);
        }else {
        	out.print(0 + DataManager.SEPARATOR);
        }
        out.println();
         out.close();
		manager.loadData(library);
		 Book b = library.getBookByID(100);
		 assertEquals("Joshua Bloch", b.getPublisher());
	            
	    
	}

}
