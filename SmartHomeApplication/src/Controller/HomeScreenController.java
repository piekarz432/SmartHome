package Controller;

import Clas.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Klasa obslugujaca okno uzytkownika.
 */
public class HomeScreenController implements Initializable {

    /**
     * Imie uzytkownika.
     */
    @FXML
    private Label name;

    /**
     * Nazwa uzytkownika.
     */
    @FXML
    private Label username;

    /**
     * Rodzic wszystkich elementow okna.
     */
    @FXML
    public static VBox vbox;

    /**
     * Obiekt uzytkownika.
     */
    public static User user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name.setText(user.getName() + " " + user.getSurname());
        username.setText(user.getUsername());
    }

    /**
     * Metoda zamykajaca okn uzytkownika.
     * @throws SQLException
     */
    public void exitHomeScreen() throws SQLException {
        vbox.setVisible(false);
    }

    /**
     * Metoda pozwlajaca zglosic wlamanie.
     */
    public void reportBurglary() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Zgłoszenie włamania");
        alert.setHeaderText(null);
        alert.setContentText("Włamanie zostało zgłoszone");
        alert.showAndWait();
    }

    /**
     * Metoda pokazujaca informacji o tworcach aplikacji.
     */
    public void info() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informacja");
        alert.setHeaderText(null);
        alert.setContentText("Aplikacja została stworzona przez: Karol Piekarski, Krzysztof Skorłutowski, Rafał Sochacki");
        alert.showAndWait();
    }
}