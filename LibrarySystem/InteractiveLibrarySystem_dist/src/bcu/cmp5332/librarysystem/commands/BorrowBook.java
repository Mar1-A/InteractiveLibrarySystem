package bcu.cmp5332.librarysystem.commands;

import java.io.IOException;
import java.time.LocalDate;

import javax.swing.JOptionPane;

import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;

public class BorrowBook implements Command {
    private int bookID;
    private int patronID;

    public BorrowBook(int bookID, int patronID) {
        this.bookID = bookID;
        this.patronID = patronID;
    }
    /**
     * 
	 * checks if the patron has been deleted or has reached the maximum number of books allowed, 
     * as well as if the book is on loan or if it has been deleted, before a patron can borrow the book
     * @param library
     * @param currentDate
     * @throws LibraryException
     * @throws IOException 
     */

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException, IOException {
        Patron patron = library.getPatronByID(patronID);
        if (patron.getBooks().size() >= library.getMaximumBooksForPatron()) {
            JOptionPane.showMessageDialog(null, "Maximum limit of book for the patron has reached.");
        } else {
            Book book = library.getBookByID(bookID);
            if (book.isDeleted()) {
                JOptionPane.showMessageDialog(null, "Book is deleted from the library.");
            }
            if (patron.isDeleted()) {
                JOptionPane.showMessageDialog(null, "Patron is deleted from the library.");
            } else {
                if (book.isOnLoan()) {
                    JOptionPane.showMessageDialog(null, "Book is already on loan.");
                } else
                    patron.borrowBook(book, currentDate, currentDate.plusDays(library.getLoanPeriod()));
                	LibraryData.store(library);
            }
        }

    }
}
