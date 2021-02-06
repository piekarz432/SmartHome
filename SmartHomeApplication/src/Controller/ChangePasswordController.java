package Controller;

import Clas.Server;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Optional;

/**
 * Klasa umozliwiajaca zmiane uzytkownika
 */
public class ChangePasswordController {

    /**
     * Nazwa uzytkownika
     */
    @FXML
    private TextField username;

    /**
     * Haslo uzytkownika
     */
    @FXML
    private PasswordField password;

    /**
     * Rodzic elementow okna.
     */
    @FXML
    private Pane pane;

    /**
     * Metoda sprawdzjaca dane i zmieniajaca haslo.
     * @throws IOException
     */
    public void onLogin() throws IOException {
        if(checkUsername())
        {
            if(checkPassword()) {
                changePassword();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Informacja");
                alert.setHeaderText(null);
                alert.setContentText("Haslo zostalo zmienione pomyslnie");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {
                    Parent fxml = FXMLLoader.load(getClass().getResource("/Fxml/Login/SignIn.fxml"));
                    pane.getChildren().removeAll();
                    pane.getChildren().setAll(fxml);
                }
            }else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Błąd");
                alert.setHeaderText(null);
                alert.setContentText("Prosze podac nowe hasło");
                alert.showAndWait();
            }
        }else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setHeaderText(null);
            alert.setContentText("Użytkownik o takiej nazwie nie istnieje");
            alert.showAndWait();
        }
    }

    /**
     * Metoda zamykajca okno zmiany hasla.
     * @param mouseEvent klikniecie myszy
     */
    public void OnExit(MouseEvent mouseEvent) {
        Platform.exit();
    }

    /**
     * Metoda sprawdzajaca czy uzytkownik istnieje.
     * @return<pre>
     *     true - jezeli uzytkownik o podanej nazwie istnieje
     *     false - jezeli uzytkownik o podanje nazwie nie istnieje
     * </pre>
     */
    private boolean checkUsername()
    {
        try {
            PrintWriter output = new PrintWriter(Server.getSocket().getOutputStream(), true);
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(Server.getSocket().getInputStream()));
            String temp = "checkUsernameReq";
            output.println(temp);
            output.println(username.getText());
            input.readLine();
            temp = input.readLine();
            if (temp.equals("not")) {
                return false;
            } else {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Metoda sprawdzajaca czy uzytkownik wprowadzil nowe haslo.
     * @return <pre>
     *     true - jezeli nowe haslo zostalo wpisane
     *     false - jezeli uzytkownik nie wprowadzil hasla
     * </pre>
     */
    private boolean checkPassword()
    {
        if(password.getText().equals(""))
        {
            return false;
        }
        return true;
    }

    /**
     * Metoda zmieniajaca haslo.
     */
    private void changePassword()
    {
        try {
            PrintWriter output = new PrintWriter(Server.getSocket().getOutputStream(), true);
            String temp = "changePass";
            output.println(temp);
            output.println(username.getText());
            output.println(password.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
