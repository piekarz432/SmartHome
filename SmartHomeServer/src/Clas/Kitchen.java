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
 * Klasa umozliwiajaca operacje na bazie danych w tabeli kuchni.
 */
public class Kitchen {

    /**
     * Obiekt uzywany do logow.
     */
    private static final Logger logger = Logger.getLogger(Datasource.class.getName());

    /**
     * Obsluga pliku.
     */
    private FileHandler fh;

    /**
     * Konstruktor kuchnia
     */
    public Kitchen() {
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
     * Metoda dodajaca domyslne wartosci do tabeli kuchnia.
     * @param id - id uzytkownika
     * @param connection polaczenie z baza danych
     */
    public static void insertValueKitchen(int id, Connection connection) {
        try {
            PreparedStatement prepStmt = connection.prepareStatement("insert into Kuchnia values (NULL, ?, ?, ?, ?, ?);");
            prepStmt.setInt(1, 0);
            prepStmt.setInt(2, 0);
            prepStmt.setInt(3, 0);
            prepStmt.setInt(4, 0);
            prepStmt.setInt(5, id);
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy dodaniu domyslnych wartosci do tabeli kuchnia");
            logger.warning("Błąd podczas dodawania kuchni");
            e.printStackTrace();
        }
    }

    /**
     * Metoda umozliwiajaca zmiane wartosci temperatury dla kuchni.
     * @param id id uzytkownika
     * @param temperature temperatur
     * @param connection polaczenie z baza danych
     */
    public static void updateValueTemperatureKitchen(int id, int temperature, Connection connection) {
        try {
            PreparedStatement prepStmt = connection.prepareStatement("update Kuchnia SET Temperatura=? where Id_Uzytkownika=?;");
            prepStmt.setInt(1, temperature);
            prepStmt.setInt(2, id);
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy aktualizacji danych w tabeli kuchnia metoda (Temperature)");
            logger.warning("Blad przy aktualizacji temperatury w tabeli kuchnia");
            e.printStackTrace();
        }
    }

    /**
     * Metoda umozliwiajaca zmiane wartosci oswietlenia i wlacznika switla dla kuchni
     * @param id id uzytkownika
     * @param light intensywnosc swiatla
     * @param switchLight 1-wlaczony lub 0-wylaczony
     * @param connection polaczenie z baza danych
     */
    public static void updateValueLightKitchen(int id, int light, int switchLight, Connection connection) {
        try {
            PreparedStatement prepStmt = connection.prepareStatement("update Kuchnia SET Intensywnosc_Swiatla=?,Wlacz_Wylacz_Swiatlo=? where Id_Uzytkownika=?;");
            prepStmt.setInt(1, light);
            prepStmt.setInt(2, switchLight);
            prepStmt.setInt(3, id);
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy aktualizacji danych w tabeli kuchnia metoda (Light)");
            logger.warning("Blad aktualizacji swaitla w tabeli kuchnia");
            e.printStackTrace();
        }
    }

    /**
     * Metoda pobierajaca wartosci dla oblsugi kuchni.
     * @param id id uzytkownika
     * @param connection polaczenie z baza danych
     * @return lista wartosci
     */
    public static List<Double> getValueKitchen(int id, Connection connection) {
        ResultSet result;
        double light=0, switchLight=0, temperature = 0, switchFridge=0;
        try {
            PreparedStatement prepStmt = connection.prepareStatement("select Intensywnosc_Swiatla, Wlacz_Wylacz_Swiatlo, Temperatura, Wlacz_Wylacz_Lodowka from Kuchnia where Id_Uzytkownika=?;");
            prepStmt.setInt(1,id);
            result=prepStmt.executeQuery();
            light=result.getInt("Intensywnosc_Swiatla");
            switchLight=result.getInt("Wlacz_Wylacz_Swiatlo");
            temperature=result.getInt("Temperatura");
            switchFridge=result.getInt("Wlacz_Wylacz_Lodowka");
        } catch (SQLException e) {
            System.err.println("Blad przy wyswietlaniu wartosci z tabeli kuchnia");
            logger.warning("Blad przy pobieraniu wartosci z tabeli kuchnia");
            e.printStackTrace();
        }
        return Arrays.asList(light, switchLight, temperature, switchFridge);
    }

    /**
     * Metoda umozliwiajaca zmiane wartosci wlacznika lodowki dla kuchni
     * @param id id uzytkownika
     * @param switchFridge 1-wlaczony lub 0-wylaczony
     * @param connection polaczenie z baza danych
     */
    public static void updateValueFridgeKitchen(int id, int switchFridge, Connection connection) {
        try {
            PreparedStatement prepStmt = connection.prepareStatement("update Kuchnia SET Wlacz_Wylacz_Lodowka=? where Id_Uzytkownika=?;");
            prepStmt.setInt(1, switchFridge);
            prepStmt.setInt(2, id);
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy aktualizacji danych w tabeli kuchnia metoda (Fridge)");
            logger.warning("Blad przy pobieraniu wartosci lodowki z tabeli kuchnia");
            e.printStackTrace();
        }
    }

    /**
     * Metoda umozliwiajaca odczyt wartosci dla danych w kuchni.
     * @param id id uzytkownika
     * @param socket port i host polaczenia
     * @param connection polaczenie z baza danych
     */
    public static void setValueKitchen(int id, Socket socket, Connection connection) {
        try {
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            List<Double> value = getValueKitchen(id, connection);
            output.println("test");
            output.println(value.get(0));
            output.println(value.get(1));
            output.println(value.get(2));
            output.println(value.get(3));
        } catch (IOException e) {
            e.printStackTrace();
            logger.warning("Blad przy wysylaniu wartosci z tabeli kuchnia");
        }
    }
}
