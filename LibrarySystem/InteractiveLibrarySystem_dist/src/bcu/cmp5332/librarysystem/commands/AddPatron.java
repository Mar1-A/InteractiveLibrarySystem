package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;

import java.io.IOException;
import java.time.LocalDate;

import javax.swing.JOptionPane;

public class AddPatron implements Command {

    private final String name;
    private final String phone;
	private final String email;

    public AddPatron(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }
    /**
     * Add the patron to the library by given name, phone and email.
     * @param library
     * @param currentDate
     * @throws LibraryException
     * @throws IOException 
     */
    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException, IOException {
		int maxId = 0;
		if(LibraryData.isPatronAccessible()) {
		//will try to find the last patron id 
			if (library.getPatrons().size() > 0) {  
				int lastIndex = library.getPatrons().size() - 1;
				maxId = library.getPatrons().get(lastIndex).getId();
			}
			Patron patron = new Patron(++maxId, name, phone,email);
			library.addPatron(patron); //adds patron to the system.
			LibraryData.store(library);//stroe data after command excuted
			JOptionPane.showMessageDialog(null,"Patron #" + patron.getId() + " added.");
		}else {
    		JOptionPane.showMessageDialog(null,"[ERROR]Command cannot be executed. File is not accessible.");
		}
    }
}
