package bcu.cmp5332.librarysystem.commands;

import java.io.IOException;
import java.time.LocalDate;

import javax.swing.JOptionPane;

import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;

public class DeletePatron implements Command {
    private int patronID;

    public DeletePatron(int patronID) {
        this.patronID = patronID;
    }

    /**
     * Delete the patron if the patron does not have books on loan.
     * @param library
     * @param currentDate
     * @throws LibraryException
     * @throws IOException 
     */

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException, IOException{
        Patron patron = library.getPatronByID(patronID);
        if (patron.getBooks().size() > 0) { //check if patron borrowed any book
            JOptionPane.showMessageDialog(null, "Could not delete the patron because the patron currently has books on loan.");
        } else {
            patron.setDeleted(true);
            LibraryData.store(library);//store data after command execution 
        }
    }
}
