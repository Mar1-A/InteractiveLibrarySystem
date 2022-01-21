package bcu.cmp5332.librarysystem.commands;

import java.time.LocalDate;

import javax.swing.JOptionPane;

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;

public class ShowPatron implements Command {
    private int patronID;

    public ShowPatron(int patronID) {
        this.patronID = patronID;
    }

    /**
     * Show one patron info.
     * @param library
     * @param currentDate
     * @throws LibraryException
     */

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        Patron patron = library.getPatronByID(patronID);
        if (!patron.isDeleted()) { //checks if patron is not deleted.
        	System.out.println(patron.getDetailsLong(library));
        } else {
        	JOptionPane.showMessageDialog(null,"Patron has been deleted");
        }
    }
}
