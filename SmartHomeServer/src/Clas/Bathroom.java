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
 * Klasa umozliwiajaca operacje na bazie danych w tabeli lazienki.
 */
public class Bathroom {

    /**
     * Obiekt uzywany do logow.
     */
    private static final Logger logger = Logger.getLogger(Datasource.class.getName());

    /**
     * Obsluga pliku.
     */
    private FileHandler fh;

    /**
     * Konstruktor Lazienka
     */
    public Bathroom() {
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
     * Metoda dodajaca domyslne wartosci do tabeli lazienka.
     * @param id id uzytkownika
     * @param connection polaczenie z baza danych
     */
    public static void insertValueBathroom(int id, Connection connection) {
        try {
            PreparedStatement prepStmt = connection.prepareStatement("insert into Lazienka values (NULL, ?, ?, ?, ?, ?, ?, ?, ?);");
            prepStmt.setInt(1, 0);
            prepStmt.setInt(2, 0);
            prepStmt.setInt(3, 0);
            prepStmt.setInt(4, 0);
            prepStmt.setInt(5, 0);
            prepStmt.setInt(6, 0);
            prepStmt.setInt(7, 0);
            prepStmt.setInt(8, id);
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy dodaniu domyslnych wartosci do tabeli Lazienka");
            logger.warning("Blad przy aktualizacji temperatury w tabeli kuchnia");
            e.printStackTrace();
        }
    }

    /**
     * Metoda pobierajaca wartosci dla oblsugi lazienki.
     * @param id id uzytkownika
     * @param connection polaczenie z baza danych
     * @return lista wartosci
     */
    public static List<Double> getValueBathoom(int id, Connection connection) {
        ResultSet result;
        double light=0, switchLight=0, temperature = 0, shower=0, showerSwitch=0, washmachine=0, washmachineSwitch=0;
        try {
            PreparedStatement prepStmt = connection.prepareStatement("select Intensywnosc_Swiatla, Wlacz_Wylacz_Swiatlo," +
                    " Temperatura, Temperatura_Wody, Wlacz_Wylacz_Prysznic," +
                    "Temperatura_Prania, Wlacz_Wylacz_Pralke from Lazienka where Id_Uzytkownika=?;");
            prepStmt.setInt(1,id);
            result=prepStmt.executeQuery();
            light=result.getInt("Intensywnosc_Swiatla");
            switchLight=result.getInt("Wlacz_Wylacz_Swiatlo");
            temperature=result.getInt("Temperatura");
            shower=result.getInt("Temperatura_Wody");
            showerSwitch=result.getInt("Wlacz_Wylacz_Prysznic");
            washmachine=result.getInt("Temperatura_Prania");
            washmachineSwitch=result.getInt("Wlacz_Wylacz_Pralke");
        } catch (SQLException e) {
            System.err.println("Blad przy wyswietlaniu wartosci z tabeli lazienka");
            logger.warning("Blad przy wyswietlaniu wartosci z tabeli lazienka");
            e.printStackTrace();
        }
        return Arrays.asList(light, switchLight, temperature, shower, showerSwitch, washmachine, washmachineSwitch);
    }

    /**
     * Metoda umozliwiajaca odczyt wartosci dla danych w lazience.
     * @param id id uzytkownika
     * @param socket port i host polaczenia
     * @param connection polaczenie z baza danych
     */
    public static void setValueBathoom(int id, Socket socket, Connection connection) {
        try {
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            List<Double> value = getValueBathoom(id, connection);
            output.println("test");
            output.println(value.get(0));
            output.println(value.get(1));
            output.println(value.get(2));
            output.println(value.get(3));
            output.println(value.get(4));
            output.println(value.get(5));
            output.println(value.get(6));
        } catch (IOException e) {
            logger.warning("Blad przy wysylaniu wartosci z tabeli lazienka");
            e.printStackTrace();
        }
    }

    /**
     * Metoda umozliwiajaca zmiane wartosci oswietlenia i wlacznika switla dla lazienki.
     * @param id id uzytkownika
     * @param light intensywnosc swiatla
     * @param switchLight 1-wlaczony lub 0-wylaczony
     * @param connection polaczenie z baza danych
     */
    public static void updateValueLightBathroom(int id, int light, int switchLight, Connection connection) {
        try {
            PreparedStatement prepStmt = connection.prepareStatement("update Lazienka SET Intensywnosc_Swiatla=?,Wlacz_Wylacz_Swiatlo=? where Id_Uzytkownika=?;");
            prepStmt.setInt(1, light);
            prepStmt.setInt(2, switchLight);
            prepStmt.setInt(3, id);
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy aktualizacji danych w tabeli lazienka metoda (Light)");
            logger.warning("Blad przy aktualizacji danych w tabeli lazienka metoda (Light)");
            e.printStackTrace();
        }
    }

    /**
     * Metoda umozliwiajaca zmiane wartosci temperatury w lazience
     * @param id id uzytkownika
     * @param temperature temperatura
     * @param connection polaczenie z baza danych
     */
    public static void updateValueTemperatureBathroom(int id, int temperature, Connection connection) {
        try {
            PreparedStatement prepStmt = connection.prepareStatement("update Lazienka SET Temperatura=? where Id_Uzytkownika=?;");
            prepStmt.setInt(1, temperature);
            prepStmt.setInt(2, id);
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy aktualizacji danych w tabeli lazienka metoda (Temperature)");
            logger.warning("Blad przy aktualizacji danych w tabeli lazienka metoda (Temperature)");
            e.printStackTrace();
        }
    }

    /**
     * Metoda umozliwiajaca zmiane wartosci temperatury wody i wlacznika prysznica dla lazienki.
     * @param id id uzytkownika
     * @param temperature temperatura
     * @param showerSwitch 1-wlaczony lub 0-wylaczony
     * @param connection polaczenie z baza danych
     */
    public static void updateValueShowerBathroom(int id, int temperature, int showerSwitch, Connection connection) {
        try {
            PreparedStatement prepStmt = connection.prepareStatement("update Lazienka SET Temperatura_Wody=?, Wlacz_Wylacz_Prysznic=? where Id_Uzytkownika=?;");
            prepStmt.setInt(1, temperature);
            prepStmt.setInt(2, showerSwitch);
            prepStmt.setInt(3, id);
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy aktualizacji danych w tabeli lazienka metoda (Shower)");
            logger.warning("Blad przy aktualizacji danych w tabeli lazienka metoda (Shower)");
            e.printStackTrace();
        }
    }

    /**
     * Metoda umozliwiajaca zmiane wartosci temperatury prania i wlacznika pralki dla lazienki.
     * @param id id uzytkownika
     * @param temperature temperautra
     * @param washmachineSwitch 1-wlaczony lub 0-wylaczony
     * @param connection polaczenie z baza danych
     */
    public static void updateValueWashmachineBathroom(int id, int temperature, int washmachineSwitch, Connection connection) {
        try {
            PreparedStatement prepStmt = connection.prepareStatement("update Lazienka SET Temperatura_Prania=?, Wlacz_Wylacz_Pralke=? where Id_Uzytkownika=?;");
            prepStmt.setInt(1, temperature);
            prepStmt.setInt(2, washmachineSwitch);
            prepStmt.setInt(3, id);
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy aktualizacji danych w tabeli lazienka metoda (Washmachine)");
            logger.warning("Blad przy aktualizacji danych w tabeli lazienka metoda (Washmachine)");
            e.printStackTrace();
        }
    }
}
