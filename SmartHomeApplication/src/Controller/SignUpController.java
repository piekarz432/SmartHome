package Controller;

import Clas.Dragable;
import Clas.Server;
import Interface.Exit;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Klasa umozliwiaca tworzenie uzytkownikow.
 */
public class SignUpController extends Dragable implements Exit {

    /**
     * Imie uzytkownika.
     */
    @FXML
    private TextField name;

    /**
     * Nazwisko uzytkownika.
     */
    @FXML
    private TextField surname;

    /**
     * Email uzytkownika.
     */
    @FXML
    private TextField email;

    /**
     * Nazwa uzytkownika.
     */
    @FXML
    private TextField username;

    /**
     * Haslo uzytkownika.
     */
    @FXML
    private PasswordField password;

    /**
     * Obiekt uzywany do logow.
     */
    private static final Logger logger = Logger.getLogger(SignUpController.class.getName());


    /**
     * Metoda tworzaca uzytkownika.
     */
    public void onRegister() {
        if (checkField()) {
            try {
                PrintWriter output = new PrintWriter(Server.getSocket().getOutputStream(), true);
                BufferedReader input = new BufferedReader(
                        new InputStreamReader(Server.getSocket().getInputStream()));
                String temp = "registerReq";
                output.println(temp);
                temp = name.getText();
                output.println(temp);
                temp = surname.getText();
                output.println(temp);
                temp = email.getText();
                output.println(temp);
                temp = username.getText();
                output.println(temp);
                temp = password.getText();
                output.println(temp);
                successfullRegister();
            } catch (IOException e) {
                logger.warning("Błąd podczas rejestracji");
                e.printStackTrace();
            }
        }
        logger.info("Rejestracja udana ");
    }

    /**
     * Metoda sprawdzajaca czy wszystkie pola zostaly uzupelnione.
     *
     * @return<pre> true - jezeli wszystkie pola zostaly uzupelnion
     *     false - jezeli nie wszystkie pola zostaly uzupelnione
     * </pre>
     */
    private boolean checkField() {
        if (username.getText().equals("") || name.getText().equals("") || surname.getText().equals("") || email.getText().equals("") || password.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ostrzeżenie");
            alert.setHeaderText(null);
            alert.setContentText("Proszę uzupełnic wszystkie pola");
            alert.showAndWait();
            logger.warning("Błędne wypełnienie pól");
            return false;
        }
        logger.info("Pola w rejestracji wypełnione poprawnie");
        return true;
    }

    /**
     * Metoda wyswietlajca komunikat o pomyslnej rejestracji.
     */
    private void successfullRegister() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informacja");
        alert.setHeaderText(null);
        alert.setContentText("Zostałeś zarejestrowany pomyślnie");
        alert.showAndWait();
        logger.info("Rejestracja przebiegła pomyślnie");
    }


    @Override
    public void OnExit() {
        Platform.exit();
    }
}
