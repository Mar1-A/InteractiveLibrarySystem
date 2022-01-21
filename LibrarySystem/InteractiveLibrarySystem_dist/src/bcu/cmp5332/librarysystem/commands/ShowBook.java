package bcu.cmp5332.librarysystem.commands;

import java.time.LocalDate;

import javax.swing.JOptionPane;

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;

public class ShowBook implements Command {
    private int bookID;

    public ShowBook(int bookID) {
        this.bookID = bookID;
    }

    /**
     * Show one book info.
     * @param library
     * @param currentDate
     * @throws LibraryException
     */

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        Book book = library.getBookByID(bookID);
        if (!book.isDeleted()) { //checks if book is not deleted.
        	System.out.println(book.getDetailsLong(library));
        } else {
        	JOptionPane.showMessageDialog(null,"Book has been deleted");
        }
    }
}
