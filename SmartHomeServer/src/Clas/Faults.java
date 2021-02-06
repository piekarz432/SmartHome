package Clas;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Klasa umozliwiajaca operacje na bazie danych w tabeli usterek.
 */
public class Faults {

    /**
     * Obiekt uzywany do logow.
     */
    private static final Logger logger = Logger.getLogger(Datasource.class.getName());

    /**
     * Obsluga pliku.
     */
    private FileHandler fh;

    /**
     * Konstruktor Bledy
     */
    public Faults() {
        try{
            fh = new FileHandler("LogFile.log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

        }catch(SecurityException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Metoda dodajaca usterke do bazy.
     * @param id id uzytkownika
     * @param text opis usterki
     * @param connection polaczenie z baza danych
     */
    public static void addFault(int id, String text, Connection connection) {
        PreparedStatement prepStmt = null;
        try {
            prepStmt = connection.prepareStatement("INSERT INTO Usterki (Opis, Id_Uzytkownika) VALUES (?, ?);");
            prepStmt.setString(1, text);
            prepStmt.setInt(2, id);
            prepStmt.execute();
        } catch (SQLException e) {
            logger.warning("Blad przy dodaniu usterki");
            e.printStackTrace();
        }
    }

    /**
     * Metoda pobiera usterki z bazy i dodaje je do tablicy
     * @param id id uzytkownika
     * @param socket port i host polaczenia
     * @param connection polaczenie z baza danych
     * @return Zwraca liste usterek
     */
    public static ArrayList<String> getFaults(int id, Socket socket, Connection connection) {
        ResultSet usterka;
        ArrayList<String> faultyList = new ArrayList<>();
        try {
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            PreparedStatement prepStmt = connection.prepareStatement("SELECT Opis FROM Usterki WHERE Id_Uzytkownika=?");
            prepStmt.setInt(1, id);
            usterka = prepStmt.executeQuery();
            while (usterka.next()) {
                String us = usterka.getString(1);
                faultyList.add(us);
            }
        } catch (SQLException | IOException e) {
            logger.warning("Blad przy pobieraniu usterek");
            e.printStackTrace();
        }
        return faultyList;
    }
}
