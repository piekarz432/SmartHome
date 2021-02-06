package Clas;

import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Klasa umozliwiajaca operacje na bazie danych.
 */
public class Datasource extends Thread {

    /**
     * Polaczenie.
     */
    private static Connection connection;

    /**
     * Komunikat.
     */
    private static Statement statement;

    /**
     * Gniazdo(socket).
     */
    private Socket socket;

    /**
     * Obiekt uzytkownika.
     */
    private User user;

    /**
     * Obiekt do odczytu.
     */
    private ObjectOutputStream os;

    /**
     * Obiekt uzywany do logow.
     */
    private static final Logger logger = Logger.getLogger(Datasource.class.getName());

    /**
     * Obsluga pliku.
     */
    private FileHandler fh;

    /**
     * Kontruktor tworzacy polaczenie
     * @param socket gniazdo(socket)
     */
    public Datasource(Socket socket) {
        this.socket = socket;
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
     * Metoda obslugujaca tworzenie watkow.
     */
    @Override
    public void run() {
        try {
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            user = null;
            os = new ObjectOutputStream(socket.getOutputStream());
            ArrayList<Integer> drzwiOkna = new ArrayList<>();

            System.out.println("Client connected");

            while (true) {
                String requestString = input.readLine();

                if (requestString.equals("loginRequest")) {
                    requestString = input.readLine();
                    String login = requestString;
                    requestString = input.readLine();
                    String password = requestString;
                    user = checkData(login, password);
                    os.writeObject(user);
                } else if(requestString.equals("kitchenTempReq")) {
                    String tempValue = input.readLine();
                    Kitchen.updateValueTemperatureKitchen(getId(user.getUsername()), Integer.parseInt(tempValue), connection);
                    output.println("Temperature ustawiono na: " + tempValue);
                    logger.info("Temperature w kuchni ustawiono na: " + tempValue);
                } else if(requestString.equals("kitchenLightReq")) {
                    String tempValue = input.readLine();
                    String onOff = input.readLine();
                    Kitchen.updateValueLightKitchen(getId(user.getUsername()), Integer.parseInt(tempValue), Integer.parseInt(onOff), connection);
                    output.println("Swiatlo ustawiono na: " + tempValue);
                    logger.info("Swiatlo w kuchni ustawiono na: " + tempValue);
                } else if(requestString.equals("kitchenFridgeReq")) {
                    String onOff = input.readLine();
                    Kitchen.updateValueFridgeKitchen(getId(user.getUsername()), Integer.parseInt(onOff), connection);
                    logger.info("Zmieniono ustawienie lodówki");
                }else if(requestString.equals("roomTempReq")) {
                    String tempValue = input.readLine();
                    Room.updateValueTemperatureRoom(getId(user.getUsername()), Integer.parseInt(tempValue), connection);
                    output.println("Temperature ustawiono na: " + tempValue);
                    logger.info("Temperature w pokoju ustawiono na: " + tempValue);
                } else if(requestString.equals("roomLightReq")) {
                    String tempValue = input.readLine();
                    String onOff = input.readLine();
                    Room.updateValueLightRoom(getId(user.getUsername()), Integer.parseInt(tempValue), Integer.parseInt(onOff), connection);
                    output.println("Swiatlo ustawiono na: " + tempValue);
                    logger.info("Swiatlo w pokoju ustawiono na: " + tempValue);
                } else if(requestString.equals("roomTvReq")) {
                    String onOff = input.readLine();
                    Room.updateValueTvRoom(getId(user.getUsername()), Integer.parseInt(onOff), connection);
                    logger.info("Zmieniono ustawienie telewizora");
                }else if (requestString.equals("bathroomTempReq")) {
                    String tempValue = input.readLine();
                    Bathroom.updateValueTemperatureBathroom(getId(user.getUsername()), Integer.parseInt(tempValue), connection);
                    output.println("Temperature ustawiono na: " + tempValue);
                    logger.info("Temperature w łazience ustawiono na: " + tempValue);
                } else if (requestString.equals("bathroomLightReq")) {
                    String tempValue = input.readLine();
                    String onOff = input.readLine();
                    Bathroom.updateValueLightBathroom(getId(user.getUsername()), Integer.parseInt(tempValue), Integer.parseInt(onOff), connection);
                    output.println("Temperature ustawiono na: " + tempValue);
                    logger.info("światło w łazience ustawiono na: " + tempValue);
                } else if(requestString.equals("bathroomShowerReq")) {
                    String tempValue = input.readLine();
                    String onOff = input.readLine();
                    Bathroom.updateValueShowerBathroom(getId(user.getUsername()), Integer.parseInt(tempValue), Integer.parseInt(onOff), connection);
                    output.println("Temperature ustawiono na: " + tempValue);
                    logger.info("Zmieniono ustawienie prysznica");
                } else if(requestString.equals("bathroomWashmachineReq")) {
                    String tempValue = input.readLine();
                    String onOff = input.readLine();
                    Bathroom.updateValueWashmachineBathroom(getId(user.getUsername()), Integer.parseInt(tempValue), Integer.parseInt(onOff), connection);
                    output.println("Temperature ustawiono na: " + tempValue);
                    logger.info("Zmieniono ustawienie prysznica");
                } else if(requestString.equals("setKitchenValueReq")) {
                    String tempValue = input.readLine();
                    Kitchen.setValueKitchen(getId(tempValue), socket, connection);
                } else if(requestString.equals("setRoomValueReq")) {
                    String tempValue = input.readLine();
                    Room.setValueRoom(getId(tempValue), socket, connection);
                } else if(requestString.equals("setBathroomValueReq")) {
                    String tempValue = input.readLine();
                    Bathroom.setValueBathoom(getId(tempValue), socket, connection);
                } else if(requestString.equals("addFaultReq")) {
                    String tempValue = input.readLine();
                    Faults.addFault(getId(user.getUsername()), tempValue, connection);
                    logger.info("Dodano usterke");
                } else if(requestString.equals("doorWindowReq")) {
                    String tempValue = input.readLine();
                    String d_pokoj = input.readLine();
                    drzwiOkna.add(Integer.parseInt(d_pokoj));
                    String d_kuchnia = input.readLine();
                    drzwiOkna.add(Integer.parseInt(d_kuchnia));
                    String d_lazienka = input.readLine();
                    drzwiOkna.add(Integer.parseInt(d_lazienka));
                    String d_frontowe = input.readLine();
                    drzwiOkna.add(Integer.parseInt(d_frontowe));
                    String o_pokoj = input.readLine();
                    drzwiOkna.add(Integer.parseInt(o_pokoj));
                    String o_lazienka = input.readLine();
                    drzwiOkna.add(Integer.parseInt(o_lazienka));
                    String o_kuchnia = input.readLine();
                    drzwiOkna.add(Integer.parseInt(o_kuchnia));
                    String o_garaz = input.readLine();
                    drzwiOkna.add(Integer.parseInt(o_garaz));
                    DoorsWindows.updateValueDoorsWindows(getId(tempValue), drzwiOkna, connection);
                    drzwiOkna.clear();
                    logger.info("Zmieniono ustawienia okien i drzwi");
                } else if(requestString.equals("setDoorWindowReq")) {
                    String tempValue = input.readLine();
                    DoorsWindows.setValueDoorsWindows(getId(tempValue), socket, connection);
                } else if(requestString.equals("getFaults")) {
                    ArrayList<String> faultyList = Faults.getFaults(getId(user.getUsername()), socket, connection);
                    int faultsNumber = faultyList.size();
                    output.println("skip");
                    output.println(faultsNumber);
                    for (int i=0; i<faultsNumber; i++) {
                        output.println(faultyList.get(i));
                    }
                } else if(requestString.equals("registerReq")) {
                    String name = input.readLine();
                    String surname = input.readLine();
                    String email = input.readLine();
                    String username = input.readLine();
                    String password = input.readLine();
                    if (checkUsername(username)) {
                        User user = new User(name, surname, email, username, password);
                        addToUser(user);
                        Kitchen.insertValueKitchen(getId(user.getUsername()), connection);
                        Room.insertValueRoom(getId(user.getUsername()), connection);
                        Bathroom.insertValueBathroom(getId(user.getUsername()), connection);
                        DoorsWindows.insertValueDoorWindow(getId(user.getUsername()), connection);
                        System.out.println("Utworzono uzytkownika");
                        logger.info("Utworzono nowego uzytkownika");
                    }
                } else if(requestString.equals("checkUsernameReq")) {
                    String tempValue = input.readLine();
                    output.println("skip");
                    if (checkUsername(tempValue)) {
                        output.println("not");
                    } else {
                        output.println("exists");
                    }
                } else if(requestString.equals("changePass")) {
                    String login = input.readLine();
                    String pass = input.readLine();
                    updatePassword(login, pass);
                    logger.info("Zmieniono haslo uzytkownika o loginie " + login);
                }
            }

        } catch (IOException | SQLException e) {
            System.out.println(e.getMessage());
            logger.warning("Metoda wyrzuciła wyjątek");
        } finally {
            try {
                socket.close();
            } catch (IOException e) {

            }
        }
    }

    /**
     * Metoda otwierajaca polaczenie z baza.
     */
    static void openConnection() {
        connection = null;
        try {
            String url = "jdbc:sqlite:resources\\Baza_Danych.db";
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Metoda sprawdzajaca nazwe uzytkownika
     * @param username - nazwa uzytkownika
     * @return <pre>
     *     true - jezeli uzytkownik istnieje
     *     false - jezeli uzytkownik nie istnieje
     * </pre>
     */
    private boolean checkUsername(String username) {
        for(String s: getUsername()) {
            if (username.equals(s)) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @return Zwraca liste uzytkownikow.
     */
    private static List<String> getUsername() {
        List<String> usernames = new LinkedList<String>();
        String username = null;
        try {
            ResultSet result = statement.executeQuery("SELECT Nazwa_Uzytkownika FROM Uzytkownicy");
            while (result.next()) {
                username = result.getString("Nazwa_Uzytkownika");
                usernames.add(username);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.warning("Niepowodzenie w pobraniu nazwy urzytkownika");
            return null;
        }
        return usernames;
    }

    /**
     * Metoda umozliwiajaca dodanie uzytkownika do bazy.
     * @param user obiekt Uzyttkownika
     */
    private static void addToUser(User user) {
        try {
            PreparedStatement prepStmt = connection.prepareStatement("insert into Uzytkownicy values (NULL, ?, ?, ?, ?, ?);");
            prepStmt.setString(1, user.getName());
            prepStmt.setString(2, user.getSurname());
            prepStmt.setString(3, user.getEmail());
            prepStmt.setString(4, user.getUsername());
            prepStmt.setString(5, user.getPassword());
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy wstawianiu uzytkownika");
            logger.warning("Blad przy wstawianiu uzytkownika");
            e.printStackTrace();
        }
    }

    /**
     * Metoda sprawdzajaca dane uzytkownika
     * @param login nazwa uzytkownika
     * @param password haslo
     * @return obiekt uzytkownika
     */
    private User checkData(String login, String password) {
        for(User u: getUser())
        {
            if(login.equals(u.getUsername()) && password.equals((u.getPassword())))
            {
                return u;
            }
        }
        return new User("error", "", "", "", "");
    }

    /**
     *
     * @return Zwraca liste uzytkownikow.
     */
    private static List<User> getUser() {
        List<User> users = new LinkedList<User>();
        try {
            ResultSet result = statement.executeQuery("SELECT Imie, Nazwisko, Email, Nazwa_Uzytkownika, Haslo FROM Uzytkownicy");
            String name, surname,email,username, password;
            while(result.next()) {
                name=result.getString("Imie");
                surname=result.getString("Nazwisko");
                email=result.getString("Email");
                username = result.getString("Nazwa_Uzytkownika");
                password = result.getString("Haslo");
                users.add(new User(name,surname,email,username,password));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.warning("Błąd podczas pobiewrania urzytkownika");
            return null;
        }
        return users;
    }

    /**
     *
     * @param username nazwa uzytkownika.
     * @return Zwraca id uzytkownika.
     * @throws SQLException nie udalo sie odczytac wartosci id uzytkownika
     */
    private static int getId(String username) throws SQLException {
        ResultSet id = null;
        try {
            PreparedStatement prepStmt = connection.prepareStatement("SELECT Id_Uzytkownika FROM Uzytkownicy WHERE Nazwa_Uzytkownika = ?");
            prepStmt.setString(1, username);
            id = prepStmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        return id.getInt(1);
    }

    /**
     * Metoda pozwalajaca zmiane hasla uzytkownika.
     * @param username nazwa uzytkownika
     * @param password haslo uzytkownika
     */
    public static void updatePassword(String username, String password) {
        try {
            PreparedStatement prepStmt = connection.prepareStatement("update Uzytkownicy set Haslo=? where Nazwa_Uzytkownika=? ;");
            prepStmt.setString(1, password);
            prepStmt.setString(2, username);
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy zmianie hasła uzytkownika");
            logger.warning("Blad przy zmianie hasła uzytkownika");
            e.printStackTrace();
        }
    }
}