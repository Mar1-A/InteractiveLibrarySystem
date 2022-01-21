package bcu.cmp5332.librarysystem.model;

import java.time.LocalDate;

import bcu.cmp5332.librarysystem.main.LibraryException;

public class Book {

    private int id;
    private String title;
    private String author;
    private String publicationYear;
    private String publisher;
    private boolean isDeleted;
    private Loan loan;

    public Book(int id, String title, String author, String publicationYear, String publisher) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.publisher = publisher;
    }

  //constructor if isDeleted is specified. Used in data manager
  	/**
  	 * Second Constructor, identical to the first, but has the extra boolean property isDeleted - having 2 constructors so when loading the data from the file, 
  	 * it saves/keeps track of its state regarding if it has been deleted or not and to do this, the second constructor will be used to initialise the already existing object instead of the first. 
  	 * As the first constructor will be used to create a new flight.
  	 * @param isDeleted   boolean representing if the flight has been deleted/removed from the FBS.
  	 * @param id  integer representing the book unique ID.
  	 * @param title   String representing the title of the book.
  	 * @param author   String representing the author of the book.
  	 * @param publicationYear   String representing when the book was published.
  	 * @param publisher   String representing the publisher name.
  	 */
    public Book(int id, String title, String author, String publicationYear, String publisher, boolean isDeleted) {
	      this.id = id;
	      this.title = title;
	      this.author = author;
	      this.publicationYear = publicationYear;
	      this.publisher = publisher;
	      this.isDeleted = false;
      
    }
      
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(String publicationYear) {
        this.publicationYear = publicationYear;
    }
    
    /**
     * gets the publisher of the book
     * @return publisher
     */
    public String getPublisher() {
        return publisher;
    }
    
    /**
     * sets the publisher of the book
     * @param publisher
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }


    /**
     * returns book details
     * @return id, title, publication year, publisher
     */
    public String getDetailsShort() {
        return "Book #" + id + "\n"
    	    	+ "Title: "+ title+"\n"	
    	    	+ "Author: "+ author + "\n"
    	    	+ "PublicationYear: " + publicationYear + "\n"
    	    	+ "Publisher: " + publisher;
    }
    
    /**
     * gets book info along with patron that borrowed the 
     * book info and the date which the loan finishes at.
     * @param library
     * @return book details and patron details 
     */
    
    public String getDetailsLong(Library library) {
    	String book_string = "";
    	/* Checks if the book is on loan, if so it will return book details
    	 *  along with the patron who borrowed it and the date that the book will be returned on.
    	 */
    	if (isOnLoan()){
    		book_string = loan.getBook().getDetailsShort() + "\n" +"Is on loan For:\n "
    	+ loan.getPatron().getDetailsShort() + "\n---------------------------\n"
    				+ "Book will be avalible to borrow on: "+ loan.getDueDate();
    	} else {
    		book_string = getDetailsShort() + "\n---------------------------\n"+ 
    	"book is avalible to borrow";
    	}
    	
        return book_string;
    }

    /**
     * checks if the book is on loan or not
     * @return true, false
     */
    public boolean isOnLoan() {
        return (loan != null) && (!loan.isTerminated());
    }

    /**
     * gets the due date for the book
     * @return due date
     */
    public LocalDate getDueDate() {
        return loan.getDueDate();
    }

    /**
     * sets the due date of the book after bing issued to patron
     * @param dueDate
     * @throws LibraryException
     */
    public void setDueDate(LocalDate dueDate) throws LibraryException {
        loan.setDueDate(dueDate);
    }
    
    /**
     * return if the book is deleted or not
     * @return book deleted statues 
     */
    public boolean isDeleted() {
        return isDeleted;
    }

    /**
     * sets the book to be deleted or not
     * @param deleted
     */
    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * Status changed according to the availability of the books.
     * @return
     */

    public String getStatus() {
        if (isOnLoan()) {
            return "On Loan.";
        } else {
            return "Available.";
        }
    }
    
    /**
     * gets the loan property of the book
     * @return loan
     */
    public Loan getLoan() {
        return loan;
    }
    
    /**
     * sets the book loan status.
     * @param loan
     */
    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    /**
     * Terminate the loan when returning to the library.
     * @param currentDate
     */

    public void returnToLibrary(LocalDate currentDate) {
        loan.setTerminated(true, currentDate);
    }
}
