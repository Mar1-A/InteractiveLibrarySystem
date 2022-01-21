package bcu.cmp5332.librarysystem.commands;

import java.time.LocalDate;
import java.util.List;

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;

public class ListPatrons implements Command {

    /**
     * List all the patrons without the ones that been deleted
     * @param library
     * @param currentDate
     * @throws LibraryException
     */

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        List<Patron> patrons = library.getPatrons();
        for (Patron patron : patrons) {
        	if(!patron.isDeleted()) {
        		System.out.println(patron.getDetailsShort());
        	}
        }
        System.out.println(patrons.size() + " Patron(s)");
    }
}
