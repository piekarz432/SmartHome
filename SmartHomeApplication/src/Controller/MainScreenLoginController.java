package Controller;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Klasa odpowiedzialna za ekran logowania i rejestracji.
 */
public class MainScreenLoginController implements Initializable {

    /**
     * Rodzic wszystkich elementow w oknie.
     */
    @FXML
    private VBox vbox;

    /**
     * Pole przechwujace plik fxml.
     */
    @FXML
    private Parent fxml;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), vbox);
        translateTransition.setToX(vbox.getLayoutX()*33.5);
        translateTransition.play();
        translateTransition.setOnFinished((e) -> {
            try{
                fxml= FXMLLoader.load(getClass().getResource("/Fxml/Login/SignIn.fxml"));
                vbox.getChildren().removeAll();
                vbox.getChildren().setAll(fxml);
            }catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    /**
     * Metoda uruchamiajaca animacje i ladujaca ekran logowania.
     */
    @FXML
    public void onSignIn() {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), vbox);
        translateTransition.setToX(vbox.getLayoutX()*33.5);
        translateTransition.play();
        translateTransition.setOnFinished((e) -> {
            try{
                fxml= FXMLLoader.load(getClass().getResource("/Fxml/Login/SignIn.fxml"));
                vbox.getChildren().removeAll();
                vbox.getChildren().setAll(fxml);
            }catch (IOException ex) {
                ex.printStackTrace();
            }
        });

    }

    /**
     * Metoda uruchamiajaca animacje i ladujaca ekran rejestracji.
     */
    @FXML
    public void onSignUp() {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), vbox);
        translateTransition.setToX(-7);
        translateTransition.play();
        translateTransition.setOnFinished((e) -> {
            try{
                fxml= FXMLLoader.load(getClass().getResource("/Fxml/Login/SignUp.fxml"));
                vbox.getChildren().removeAll();
                vbox.getChildren().setAll(fxml);
            }catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

}
