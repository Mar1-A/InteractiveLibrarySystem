package bcu.cmp5332.librarysystem.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import bcu.cmp5332.librarysystem.main.LibraryException;

public class Library {

    private final int loanPeriod = 7;
    private final int maximumBooksForPatron = 5;
    private final Map<Integer, Patron> patrons = new TreeMap<>();
    private final Map<Integer, Book> books = new TreeMap<>();

    public int getLoanPeriod() {
        return loanPeriod;
    }

    /**
     * Maximum books for a patron at once.
     * @return
     */

    public int getMaximumBooksForPatron() {
        return maximumBooksForPatron;
    }

    public List<Book> getBooks() {
        List<Book> out = new ArrayList<>();

        for (Book book : books.values()) {
            if (!book.isDeleted()) {
                out.add(book);
            }
        }
        return Collections.unmodifiableList(out);
    }

    public Book getBookByID(int id) throws LibraryException {
        if (!books.containsKey(id)) {
            throw new LibraryException("There is no such book with that ID.");
        }
        return books.get(id);
    }

    /**
     * Get patrons by id.
     * @param id
     * @return the patron id
     * @throws LibraryException
     */

    public Patron getPatronByID(int id) throws LibraryException {
    	 if (!patrons.containsKey(id)) {
             throw new LibraryException("There is no such book with that ID.");
         }
         return patrons.get(id);
    }

    public void addBook(Book book) {
        if (books.containsKey(book.getId())) {
            throw new IllegalArgumentException("Duplicate book ID.");
        }
        books.put(book.getId(), book);
    }

    /**
     * Add patron to the library
     * @param patron
     */

    public void addPatron(Patron patron) {
        if (patrons.containsKey(patron.getId())) {
            throw new IllegalArgumentException("Duplicate patron ID.");
        }
        patrons.put(patron.getId(), patron);
    }

    /**
     * get the list of patrons.
     * @return
     */

    public List<Patron> getPatrons() {
        List<Patron> out = new ArrayList<>();

        for (Patron patron : patrons.values()) {
            if (!patron.isDeleted()) {
                out.add(patron);
            }
        }

        return Collections.unmodifiableList(out);
    }
}
