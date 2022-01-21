package bcu.cmp5332.librarysystem.model;

import java.time.LocalDate;

public class Loan {

    private Patron patron;
    private Book book;
    private LocalDate startDate;
    private LocalDate dueDate;

    private boolean terminated;
    private LocalDate returnDate;

    public Loan(Patron patron, Book book, LocalDate startDate, LocalDate dueDate) {
        this.patron = patron;
        this.book = book;
        this.startDate = startDate;
        this.dueDate = dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public Patron getPatron() {
        return patron;
    }

    public void setPatron(Patron patron) {
        this.patron = patron;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    
    
    public boolean isTerminated() {
        return terminated;
    }

    /**
     * Set terminate date and boolean variable when terminated.
     * @param terminated
     * @param returnDate
     */

    public void setTerminated(boolean terminated, LocalDate returnDate) {
        this.terminated = terminated;
        this.returnDate = returnDate;
    }
    /**
     * it will terminate the loan when the book is returned or the 
     * @param terminated
     */
    public void setTerminated(boolean terminated) {
        this.terminated = terminated;
    }
}
