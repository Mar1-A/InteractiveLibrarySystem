package bcu.cmp5332.librarysystem.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import bcu.cmp5332.librarysystem.data.PatronDataManager;
import bcu.cmp5332.librarysystem.data.DataManager;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Patron;
import bcu.cmp5332.librarysystem.model.Library;

/*
 * Note:
 * Performing the following test will result in deleting all patrons
 * data from the system. 
 */

class PatronTest {
	Patron patron = new Patron(9, "marwan", "20116865", "example@mail.com");

	@Test
	void getEmailTest() {
		assertEquals("example@mail.com", patron.getEmail());
	}
	
	@Test
	void setEmailTest() {
		patron.setEmail("example2@mail.com");
		assertEquals("example2@mail.com", patron.getEmail());
	}
	
	@Test
	void storeEmailTest() throws LibraryException, IOException{
		String RESOURCE = "./resources/data/patrons.txt";
		
		Library library = new Library();
		library.addPatron(patron);
		PatronDataManager manager = new PatronDataManager();
		manager.storeData(library);
		Scanner sc = new Scanner(new File(RESOURCE));
		while (sc.hasNextLine()) {
			final String lineFromFile = sc.nextLine();
			String[] properties = lineFromFile.split(DataManager.SEPARATOR, -1);
			String email = properties[3];
			assertEquals("example@mail.com", email);
		}
	}
	
	@Test
	public void loadEmailTest() throws LibraryException, IOException{
		String RESOURCE = "./resources/data/patrons.txt";
		Library library = new Library();
		PatronDataManager manager = new PatronDataManager();
		PrintWriter out = new PrintWriter(new FileWriter(RESOURCE));
        out.print(patron.getId() + DataManager.SEPARATOR);
        out.print(patron.getName() + DataManager.SEPARATOR);
        out.print(patron.getPhone() + DataManager.SEPARATOR);
        out.print(patron.getEmail() + DataManager.SEPARATOR);
        if(patron.isDeleted()) {
        	out.print(1 + DataManager.SEPARATOR);
        }else {
        	out.print(0 + DataManager.SEPARATOR);
        }
        out.println();
        out.close();
		manager.loadData(library);
		Patron p = library.getPatronByID(9);
		assertEquals("example@mail.com", p.getEmail());
	            
	    
	}

}
