package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.main.LibraryException;

import java.io.IOException;
import java.time.LocalDate;

import javax.swing.JOptionPane;

public class AddBook implements Command {

    private final String title;
    private final String author;
    private final String publicationYear;
    private final String publisher;

    public AddBook(String title, String author, String publicationYear, String publisher) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.publisher = publisher;
    }
    /**
     * Add the book to the library.
     * @param library
     * @param currentDate
     * @throws LibraryException
     * @throws IOException 
     */

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException, IOException {
    	int maxId = 0;
    	//Checks if the book File is Accessible
    	if(LibraryData.isBookAccessible()) { 
    		//will try to find the last book id
    		if (library.getBooks().size() > 0) {   
	              int lastIndex = library.getBooks().size() - 1;
	              maxId = library.getBooks().get(lastIndex).getId();
	          }
	        Book book = new Book(++maxId, title, author, publicationYear, publisher);
	        library.addBook(book); // add the book to the library
	        LibraryData.store(library);//stroe data after command excuted
	        JOptionPane.showMessageDialog(null,"Book #" + book.getId() + " added.");
    	}else {
    		JOptionPane.showMessageDialog(null,"[ERROR]Command cannot be executed. File is not accessible.");
		}
    }
}
