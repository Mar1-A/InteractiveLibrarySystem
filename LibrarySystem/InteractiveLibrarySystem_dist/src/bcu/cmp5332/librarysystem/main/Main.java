package bcu.cmp5332.librarysystem.main;

import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.commands.Command;
import bcu.cmp5332.librarysystem.model.Library;

import javax.swing.*;
import java.io.*;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) throws LibraryException {

        Library library = null;
        try {
            library = LibraryData.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Library system");
        System.out.println("Enter 'help' to see a list of available commands.");
        while (true) {
            try {
                System.out.print("> ");
                String line = br.readLine();
                if (line.equals("exit")) {
                    break;
                }
                Command command = CommandParser.parse(line);
                command.execute(library, LocalDate.now());
            } catch (LibraryException ex) {
                System.out.println(ex.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            LibraryData.store(library);// saving the library data if not will display an error,
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Could not save the information to the system, rolling back to the last stage.");
        }
        System.exit(0);
    }
}
