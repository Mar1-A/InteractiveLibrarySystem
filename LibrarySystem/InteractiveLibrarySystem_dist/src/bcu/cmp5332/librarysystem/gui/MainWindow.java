package bcu.cmp5332.librarysystem.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;

public class MainWindow extends JFrame implements ActionListener {

    private JMenuBar menuBar;
    private JMenu adminMenu;
    private JMenu booksMenu;
    private JMenu membersMenu;

    private JMenuItem adminExit;

    private JMenuItem booksView;
    private JMenuItem booksAdd;
    private JMenuItem booksDel;
    private JMenuItem booksBorrow;
    private JMenuItem booksRenew;
    private JMenuItem booksReturn;

    private JMenuItem memView;
    private JMenuItem memAdd;
    private JMenuItem memDel;

    private Library library;

    public MainWindow(Library library) {

        initialize();
        this.library = library;
    }

    public Library getLibrary() {
        return library;
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {

        }

        setTitle("Library Management System");

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        //adding adminMenu menu and menu items
        adminMenu = new JMenu("Admin");
        menuBar.add(adminMenu);

        adminExit = new JMenuItem("Exit");
        adminMenu.add(adminExit);
        adminExit.addActionListener(this);

        // adding booksMenu menu and menu items
        booksMenu = new JMenu("Books");
        menuBar.add(booksMenu);

        booksView = new JMenuItem("View");
        booksAdd = new JMenuItem("Add");
        booksDel = new JMenuItem("Delete");
        booksBorrow = new JMenuItem("Borrow");
        booksRenew = new JMenuItem("Renew");
        booksReturn = new JMenuItem("Return");
        booksMenu.add(booksView);
        booksMenu.add(booksAdd);
        booksMenu.add(booksDel);
        booksMenu.add(booksBorrow);
        booksMenu.add(booksRenew);
        booksMenu.add(booksReturn);
        for (int i = 0; i < booksMenu.getItemCount(); i++) {
            booksMenu.getItem(i).addActionListener(this);
        }

        // adding membersMenu menu and menu items
        membersMenu = new JMenu("Members");
        menuBar.add(membersMenu);

        memView = new JMenuItem("View");
        memAdd = new JMenuItem("Add");
        memDel = new JMenuItem("Delete");

        membersMenu.add(memView);
        membersMenu.add(memAdd);
        membersMenu.add(memDel);

        memView.addActionListener(this);
        memAdd.addActionListener(this);
        memDel.addActionListener(this);

        setSize(800, 500);

        setVisible(true);
        setAutoRequestFocus(true);
        toFront();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        /* Uncomment the following line to not terminate the console app when the window is closed */
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    }

    /* Uncomment the following code to run the GUI version directly from the IDE */
    public static void main(String[] args) throws IOException, LibraryException {
        Library library = LibraryData.load();
        new MainWindow(library);			
    }


    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == adminExit) {
            try {
                LibraryData.store(library);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Could not save the information to the system, rolling back to the last stage.");
            }
            System.exit(0);
        } else if (ae.getSource() == booksView) {
            displayBooks();
        } else if (ae.getSource() == booksAdd) {
            new AddBookWindow(this);
        } else if (ae.getSource() == booksDel) {
            new BookDeleteWindow(this);
        } else if (ae.getSource() == booksBorrow) {
            new BookBorrowWindow(this);
        } else if (ae.getSource() == booksRenew) {
            new BookRenewWindow(this);
        } else if (ae.getSource() == booksReturn) {
            new BookReturnWindow(this);
        } else if (ae.getSource() == memView) {
            viewMembers();
        } else if (ae.getSource() == memAdd) {
            new AddPatronWindow(this);
        } else if (ae.getSource() == memDel) {
            new PatronDeleteWindow(this);
        }
    }

    public void displayBooks() {
        List<Book> booksList = library.getBooks();
        // headers for the table
        String[] columns = new String[]{"Id", "Title", "Author", "Pub Date", "Status"};

        Object[][] data = new Object[booksList.size()][6];
        for (int i = 0; i < booksList.size(); i++) {
            Book book = booksList.get(i);
            if (!book.isDeleted()) {
                data[i][0] = book.getId();
                data[i][1] = book.getTitle();
                data[i][2] = book.getAuthor();
                data[i][3] = book.getPublicationYear();
                data[i][4] = book.getStatus();
            }
        }

        JTable table = new JTable(data, columns);
        this.getContentPane().removeAll();
        this.getContentPane().add(new JScrollPane(table));
        this.revalidate();

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
        	public void valueChanged(ListSelectionEvent event) {
                try {
                    Book book = library.getBookByID((Integer) table.getValueAt(table.getSelectedRow(), 0));

                    if (book.isOnLoan()) {
                        JOptionPane.showMessageDialog(null, book.getDetailsLong(library));
                    }
                } catch (LibraryException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void viewMembers() {
        List<Patron> patrons = library.getPatrons();
        // headers for the table
        String[] columns = new String[]{"ID", "Name", "Phone", "email", "Number of Books on Loan"};

        Object[][] data = new Object[patrons.size()][5];
        for (int i = 0; i < patrons.size(); i++) {
            Patron patron = patrons.get(i);
            if (!patron.isDeleted()) {
                data[i][0] = patron.getId();
                data[i][1] = patron.getName();
                data[i][2] = patron.getPhone();
                data[i][3] = patron.getEmail();
                data[i][4] = patron.getBooks().size();
            }
        }

        JTable table = new JTable(data, columns);
        this.getContentPane().removeAll();
        this.getContentPane().add(new JScrollPane(table));
        this.revalidate();

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                try {
                    Patron patron = library.getPatronByID((Integer) table.getValueAt(table.getSelectedRow(), 0));

                    if (patron.getBooks().size() > 0) {
                        JOptionPane.showMessageDialog(null, patron.getDetailsLong(library));
                    }
                } catch (LibraryException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
