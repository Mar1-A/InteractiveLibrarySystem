package bcu.cmp5332.librarysystem.model;

import bcu.cmp5332.librarysystem.main.LibraryException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Patron {

    private int id;
    private String name;
    private String phone;
    private String email;
    private final List<Book> books = new ArrayList<>();

    private boolean deleted;

    public Patron(int id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
    	this.name = name;
    }
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
    	this.phone = phone;
    }
    
    /**
     * gets patron email
     * @return email
     */
    public String getEmail() {
    	return email;
    }
    
    /**
     * sets patron email
     * @param email
     */
    public void setEmail(String email) {
    	this.email = email;
    }
    
    /**
     * gets patron info
     * @return patron info
     */
    
    public String getDetailsShort() {
    	String details = "- Patron #" + id + "\n"
    	    	+ "Name: "+ name+" \n"	
    	    	+ "Phone: "+ phone + "\n"
    	    	+ "Email: " + email;
    	return details;
    }
    
    /**
     * gets patron info along with  books borrowed info and books due date
     * @param library
     */
    
    public String getDetailsLong(Library library) {
    	String loan_string = "";
    	//gets borrowed books 
    	for (Book book : books){
    		//checks if book is on loan
    		if(book.isOnLoan()) {
    			loan_string = loan_string + book.getDetailsShort() + "\nDue date: "+ book.getDueDate() + "\n";		
    		} else {
    			loan_string = loan_string + "* The loan for Book "+ book.getTitle()+ "has finished. \n";
    		}
    	}
    	String patron_string ="Patron detailes:\n" + "Patron #" + id + "\nName:" + name + "\nPhone: " + phone + "\nEmail: "+ email + "\n---------------------------\n Books on loan:\n";
    	String isDeletedCheck = "";
    	 //checks if patron has been removed from the system 
        if (isDeleted()== true) {
        	isDeletedCheck = "\n\n[Alert]This patron has been removed from the system";
        }
        return  patron_string + loan_string
        +"\n"+books.size()+" book(s)" + isDeletedCheck;
    }
    
    /**
     *function to help borrow book command.
     * @param book
     * @param currentDate
     * @param dueDate
     * @throws LibraryException
     */

    public void borrowBook(Book book, LocalDate currentDate, LocalDate dueDate) throws LibraryException {
        books.add(book); // add book to the list of borrowed
        book.setLoan(new Loan(this, book, currentDate, dueDate)); // set loan in the for the book
    }

    /**
     * function to help renew book command.
     * @param book
     * @param currentDate
     * @param dueDate
     * @throws LibraryException
     */

    public void renewBook(Book book, LocalDate currentDate, LocalDate dueDate) throws LibraryException {
        book.setLoan(new Loan(this, book, currentDate, dueDate));// sets new loan for the book
    }

    /**
     * function to help return book command.
     * @param book
     * @param currentDate
     * @throws LibraryException
     */

    public void returnBook(Book book,LocalDate currentDate) throws LibraryException {
        books.remove(book);// remove book from borrowed book list
        book.returnToLibrary(currentDate);// date the book was returned in
    }

    /**
     * adds book to the list of borrowed books by patron
     * @param book
     */
    public void addBook(Book book) {
        books.add(book);
    }

    /**
     * get the patron statues if deleted of not
     * @return deleted
     */
    public boolean isDeleted() {
        return deleted;
    }

    /**
     * Delete patron.
     * @param deleted
     */
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * list all borrowed books 
     * @return books
     */
    public List<Book> getBooks() {
        return books;
    }
}
