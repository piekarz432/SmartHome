package Clas;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Klasa umozliwiajaca operacje na bazie danych w tabeli pokoju.
 */
public class Room {

    /**
     * Obiekt uzywany do logow.
     */
    private static final Logger logger = Logger.getLogger(Datasource.class.getName());

    /**
     * Obsluga pliku.
     */
    private FileHandler fh;

    /**
     * Konstruktor Pokoj
     */
    public Room() {
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
     * Metoda dodajaca domyslne wartosci do tabeli pokoj.
     * @param id id uzytkownika
     * @param connection polaczenie z baza danych
     */
    public static void insertValueRoom(int id, Connection connection) {
        try {
            PreparedStatement prepStmt = connection.prepareStatement("insert into Pokoj values (NULL, ?, ?, ?, ?, ?);");
            prepStmt.setInt(1, 0);
            prepStmt.setInt(2, 0);
            prepStmt.setInt(3, 0);
            prepStmt.setInt(4, 0);
            prepStmt.setInt(5, id);
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy dodaniu domyslnych wartosci do tabeli pokoj");
            logger.warning("Blad przy dodaniu domyslnych wartosci do tabeli pokoj");
            e.printStackTrace();
        }
    }

    /**
     * Metoda umozliwiajaca zmiane wartosci temperatury w pokoju
     * @param id id uzytkownika
     * @param temperature temperatura
     * @param connection polaczenie z baza danych
     */
    public static void updateValueTemperatureRoom(int id, int temperature, Connection connection) {
        try {
            PreparedStatement prepStmt = connection.prepareStatement("update Pokoj SET Temperatura=? where Id_Uzytkownika=?;");
            prepStmt.setInt(1, temperature);
            prepStmt.setInt(2, id);
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy aktualizacji danych w tabeli kuchnia metoda (Temperature)");
            logger.warning("Blad przy aktualizacji danych w tabeli kuchnia metoda (Temperature)");
            e.printStackTrace();
        }
    }

    /**
     * Metoda umozliwiajaca zmiane wartosci oswietlenia i wlacznika switla dla pokoju
     * @param id id uzytkownika
     * @param light intensywnosc swiatla
     * @param switchLight 1-wlaczony lub 0-wylaczony
     * @param connection polaczenie z baza danych
     */
    public static void updateValueLightRoom(int id, int light, int switchLight, Connection connection) {
        try {
            PreparedStatement prepStmt = connection.prepareStatement("update Pokoj SET Intensywnosc_Swiatla=?,Wlacz_Wylacz_Swiatlo=? where Id_Uzytkownika=?;");
            prepStmt.setInt(1, light);
            prepStmt.setInt(2, switchLight);
            prepStmt.setInt(3, id);
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy aktualizacji danych w tabeli kuchnia metoda (Light)");
            logger.warning("Blad przy aktualizacji danych w tabeli kuchnia metoda (Light)");
            e.printStackTrace();
        }
    }

    /**
     * Metoda umozliwiajaca zmiane wartosci wlacznika telewizora dla pokoju
     * @param id id uzytkownika
     * @param switchTv 1-wlaczony lub 0-wylaczony
     * @param connection polaczenie z baza danych
     */
    public static void updateValueTvRoom(int id, int switchTv, Connection connection) {
        try {
            PreparedStatement prepStmt = connection.prepareStatement("update Pokoj SET Wlacz_Wylacz_Telewizor=? where Id_Uzytkownika=?;");
            prepStmt.setInt(1, switchTv);
            prepStmt.setInt(2, id);
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy aktualizacji danych w tabeli pokoj metoda (Tv)");
            logger.warning("Blad przy aktualizacji danych w tabeli pokoj metoda (Tv)");
            e.printStackTrace();
        }
    }

    /**
     * Metoda pobierajaca wartosci dla oblsugi pokoju.
     * @param id id uzytkownika
     * @param connection polaczenie z baza danych
     * @return lista wartosci
     */
    public static List<Double> getValueRoom(int id, Connection connection) {
        ResultSet result;
        double light=0, switchLight=0, temperature = 0, switchTv=0;
        try {
            PreparedStatement prepStmt = connection.prepareStatement("select Intensywnosc_Swiatla, Wlacz_Wylacz_Swiatlo, Temperatura, Wlacz_Wylacz_Telewizor from Pokoj where Id_Uzytkownika=?;");
            prepStmt.setInt(1,id);
            result=prepStmt.executeQuery();
            light=result.getInt("Intensywnosc_Swiatla");
            switchLight=result.getInt("Wlacz_Wylacz_Swiatlo");
            temperature=result.getInt("Temperatura");
            switchTv=result.getInt("Wlacz_Wylacz_Telewizor");
        } catch (SQLException e) {
            System.err.println("Blad przy wyswietlaniu wartosci z tabeli pokoj");
            logger.warning("Blad przy wyswietlaniu wartosci z tabeli pokoj");
            e.printStackTrace();
        }
        return Arrays.asList(light, switchLight, temperature, switchTv);
    }

    /**
     * Metoda umozliwiajaca odczyt wartosci dla danych w pokoju.
     * @param id id uzytkownika
     * @param socket port i host polaczenia
     * @param connection polaczenie z baza danych
     */
    public static void setValueRoom(int id, Socket socket, Connection connection) {
        try {
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            List<Double> value = getValueRoom(id, connection);
            output.println("test");
            output.println(value.get(0));
            output.println(value.get(1));
            output.println(value.get(2));
            output.println(value.get(3));
        } catch (IOException e) {
            logger.warning("Blad przy wysylaniu wartosci z tabeli pokoj");
            e.printStackTrace();
        }
    }

}
