package bcu.cmp5332.librarysystem.data;

import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.main.LibraryException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LibraryData {
    
    private static final List<DataManager> dataManagers = new ArrayList<>();
  //storing the files in an attribute.
    private final static String booksFile = "./resources/data/books.txt"; 
    private final static String patronsFile = "./resources/data/patrons.txt";
    private final static String loansFile = "./resources/data/loans.txt";
    private final static File bFile = new File(booksFile);
    private final static File pFile = new File(patronsFile);
    private final static File lFile = new File(loansFile);
    
    // runs only once when the object gets loaded to memory
    static {
        dataManagers.add(new BookDataManager());
        
        /* Uncomment the two lines below when the implementation of their 
        loadData() and storeData() methods is complete */
         dataManagers.add(new PatronDataManager());
         dataManagers.add(new LoanDataManager());
    }
    
    public static Library load() throws LibraryException, IOException {

        Library library = new Library();
        for (DataManager dm : dataManagers) {
            dm.loadData(library);
        }
        return library;
    }

    public static void store(Library library) throws IOException {
        for (DataManager dm : dataManagers) {
            dm.storeData(library);
        }
    }
    /*
     * checks if the books file is Accessible
     */
    public static boolean isBookAccessible() { 
    	if(bFile.canWrite()) { 
    		return true;
    	}return false;
    }
    
    /*
     * checks if the loans file is Accessible 
     */
    public static boolean isLoanAccessible() { 
    	if(lFile.canWrite()) { 
    		return true;
    	}return false;
    }
    
    /*
     * checks if the books file is Accessible 
     */
    public static boolean isPatronAccessible() {
    	if(pFile.canWrite()) {
    		return true;
    	}return false;
    }
}
