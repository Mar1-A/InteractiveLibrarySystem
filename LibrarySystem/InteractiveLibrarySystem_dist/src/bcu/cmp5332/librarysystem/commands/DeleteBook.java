package bcu.cmp5332.librarysystem.commands;

import java.io.IOException;
import java.time.LocalDate;

import javax.swing.JOptionPane;

import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;

public class DeleteBook implements Command {
    private int bookID;

    public DeleteBook(int bookID) {
        this.bookID = bookID;
    }

    /**
     * Delete the book from the library. Book will not be deleted if it is already on a loan.
     * @param library
     * @param currentDate
     * @throws LibraryException
     * @throws IOException 
     */

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException, IOException{
        Book book = library.getBookByID(bookID);
        if (book.isOnLoan()) {//checks if the book is on loan
            JOptionPane.showMessageDialog(null, "Could not delete the book because the book is currently on a loan");
        } else {
        	boolean deleted = true;
            book.setDeleted(deleted);//if not the book will be deleted
            LibraryData.store(library);//store data after command execution 
           
        }
    }
}
