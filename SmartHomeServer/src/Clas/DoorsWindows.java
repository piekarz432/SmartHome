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
 * Klasa umozliwiajaca operacje na bazie danych w tabeli drzwi i okien.
 */
public class DoorsWindows {

    /**
     * Obiekt uzywany do logow.
     */
    private static final Logger logger = Logger.getLogger(Datasource.class.getName());

    /**
     * Obsluga pliku.
     */
    private FileHandler fh;

    /**
     * Konstruktor drzwi i okna
     */
    public DoorsWindows() {
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
     * Metoda dodajaca domyslne wartosci do tabeli okna drzwi.
     * @param id id uzytkownika
     * @param connection polaczenie z baza danych
     */
    public static void insertValueDoorWindow(int id, Connection connection) {
        try {
            PreparedStatement prepStmt = connection.prepareStatement("insert into DrzwiOkna values (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
            prepStmt.setInt(1, 0);
            prepStmt.setInt(2, 0);
            prepStmt.setInt(3, 0);
            prepStmt.setInt(4, 0);
            prepStmt.setInt(5, 0);
            prepStmt.setInt(6, 0);
            prepStmt.setInt(7, 0);
            prepStmt.setInt(8, 0);
            prepStmt.setInt(9, id);
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy dodaniu domyslnych wartosci do tabeli Lazienka");
            logger.warning("Blad podczas dodawani kuchni");
            e.printStackTrace();
        }
    }

    /**
     * Metoda umozliwiajaca odczyt wartosci dla danych z tabeli drzwi okna.
     * @param id id uzytkownika
     * @param socket port i host polaczenia
     * @param connection polaczenie z baza danych
     */
    public static void setValueDoorsWindows(int id, Socket socket, Connection connection) {
        ResultSet result;
        int d_pokoj, d_kuchnia, d_lazienka , d_front, o_pokoj, o_lazienka, o_kuchnia, o_garaz;
        try {
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            PreparedStatement prepStmt = connection.prepareStatement("select drzwiPokoj, drzwiKuchnia, drzwiLazienka," +
                    "drzwiFront, oknoPokoj, oknoLazienka, oknoKuchnia, oknoGaraz from DrzwiOkna where Id_Uzytkownika=?;");
            prepStmt.setInt(1,id);
            result=prepStmt.executeQuery();
            d_pokoj=result.getInt("drzwiPokoj");
            d_kuchnia=result.getInt("drzwiKuchnia");
            d_lazienka=result.getInt("drzwiLazienka");
            d_front=result.getInt("drzwiFront");
            o_pokoj=result.getInt("oknoPokoj");
            o_lazienka=result.getInt("oknoLazienka");
            o_kuchnia=result.getInt("oknoKuchnia");
            o_garaz=result.getInt("oknoGaraz");
            output.println("skip");
            output.println(d_pokoj);
            output.println(d_kuchnia);
            output.println(d_lazienka);
            output.println(d_front);
            output.println(o_pokoj);
            output.println(o_lazienka);
            output.println(o_kuchnia);
            output.println(o_garaz);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            logger.warning("Blad przy wysylaniu wartosci z tabeli DrzwiOkna");
        }
    }

    /**
     * Metoda umozliwiajaca dodanie wartosci do tabeli okna drzwi.
     * @param id id uzytkownika
     * @param list lista wartosci
     * @param connection polaczenie z baza danych
     */
    public static void updateValueDoorsWindows(int id, ArrayList<Integer> list, Connection connection) {
        try {
            PreparedStatement prepStmt = connection.prepareStatement("update DrzwiOkna SET drzwiPokoj=?, drzwiKuchnia=?, drzwiLazienka=?," +
                    "drzwiFront=?, oknoPokoj=?, oknoLazienka=?, oknoKuchnia=?, oknoGaraz=? where Id_Uzytkownika=?;");
            int temp = 0;
            for (int i = 1; i < 9; i++) {
                temp = i-1;
                prepStmt.setInt(i, list.get(temp));
            }
            prepStmt.setInt(9, id);
            prepStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            logger.warning("Blad przy ustawianiu wartosci w tabeli DrzwiOkna");
        }
    }
}
