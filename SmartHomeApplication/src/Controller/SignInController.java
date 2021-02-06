package Controller;

import Clas.Dragable;
import Clas.Server;
import Clas.User;
import Interface.Exit;
import Main.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import java.io.*;
import java.util.Optional;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Klasa umozliwiaca zalogowanie uzytkownikow.
 */
public class SignInController extends Dragable implements Exit{

    /**
     * Aktualnie przesylany obiekt.
     */
    static ObjectInputStream is;

    /**
     * Inicjalizacja statyczna
     */
    static {
        try {
            is = new ObjectInputStream(Server.getSocket().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
     * Rodzic elementow okna.
     */
    @FXML
    public Pane pane;

    /**
     * Obiekt uzywany do logow.
     */
    private static final Logger logger = Logger.getLogger(SignUpController.class.getName());

    /**
     * Metoda logujaca uzytkownika.
     * @throws IOException
     */
    public void onLogin() throws IOException {
        try {
            PrintWriter output = new PrintWriter(Server.getSocket().getOutputStream(), true);
            String test = "loginRequest";
            output.println(test);
            test = username.getText();
            output.println(test);
            test = password.getText();
            output.println(test);

        } catch (IOException e) {
            logger.warning("Logowanie nie powiodło się");
            e.printStackTrace();
        }
        checkData(recaiveUser());
        logger.info("Logowanie powiodło sie");
    }

    /**
     * Metoda odbierajca obiekt uzytkownika od serwera.
     * @return obiekt uzytkownika
     */
    private User recaiveUser() {
        User u = new User("error", "", "", "", "");

        try {
            u = (User) is.readObject();
            System.out.println(u.toString());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return u;
    }

    /**
     * Metoda sprawdzajaca poprawnosc wprowadzonych danych
     * @param check obiekt uzytkownika
     * @throws IOException
     */
    private void checkData(User check) throws IOException {
        if(check.getName().equals("error")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Błąd");
            alert.setHeaderText(null);
            alert.setContentText("Wprowadzone dane są nieprawidłowe");
            alert.showAndWait();
            logger.warning("Błędne dane w logowaniu");
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informacja");
            alert.setHeaderText(null);
            alert.setContentText("Zostałeś zalogowany pomyślnie");
            Optional<ButtonType> result = alert.showAndWait();
            logger.info("Logowanie powiodło sie");

            if (result.get() == ButtonType.OK)
            {
                MainScreenMenuController.loadMenu();
                HomeScreenController.user=check;
                Main.primaryStage.close();
            }
        }
    }

    /**
     * Metoda ladujaca okno zmiany hasla.
     * @throws IOException
     */
    public void changePassword() throws IOException {
        Parent fxml= FXMLLoader.load(getClass().getResource("/Fxml/Login/ChangePassword.fxml"));
        pane.getChildren().removeAll();
        pane.getChildren().setAll(fxml);
    }

    @Override
    public void OnExit() {
        Platform.exit();
    }
}
