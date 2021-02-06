package Clas;

import java.io.Serializable;

/**
 * Klasa przechowujaca informacje o uzytkowniku.
 */

public class User implements Serializable {

    /**
     * Imie uzytkownika.
     */
    private String name;

    /**
     * Nazwisko uzytkownika.
     */
    private String surname;

    /**
     * Email uzytkownika.
     */
    private String email;

    /**
     * Nazwa uzytkownika.
     */
    private String username;

    /**
     * Haslo uzytkownika.
     */
    private String password;

    private static final long serialVersionUID = 2L;

    /**
     *
     * @return Zwraca nazwe uzytkownika.
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @return Zwraca imie uzytkownika.
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return Zwraca nazwisko uzytkownika
     */
    public String getSurname() {
        return surname;
    }

    /**
     *
     * @return Zwraca email uzytkownika.
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @return Zwraca haslo uzytkownika
     */
    public String getPassword() {
        return password;
    }

    /**
     * Konstruktor tworzacy obiekt Uzytkownik.
     * @param name imie uzytkownika
     * @param surname nazwisko uzytkownika
     * @param email email uzytkownika
     * @param username nazwa uzytkownika
     * @param password haslo uzytkownika
     */
    public User(String name, String surname, String email, String username, String password)
    {
        this.name=name;
        this.surname=surname;
        this.email=email;
        this.username=username;
        this.password=password;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }


}
