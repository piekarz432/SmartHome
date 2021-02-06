package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Klasa bedaca podstawa do oblsugi przelaczania miedzy funkcjonalnosciami w menu glownym.
 */
public class MainScreenMenuController implements Initializable {
    /**
     * Rodzic wszyskich elementow okna.
     */
    @FXML
    private StackPane mainStackPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showMenu();
    }

    /**
     * Metoda pokazujaca menu glowne aplikacji.
     */
    public void showMenu(){
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/Fxml/Menu/MenuScreen.fxml"));
        Pane pane = null;
        try{
            pane = loader.load();
        }catch(IOException e){
            e.printStackTrace();
        }

        MenuScreenController menuScreenController  = loader.getController();
        menuScreenController.setMainController(this);
        setScreen(pane);

    }

    /**
     * Metoda ustawiajaca okno.
     * @param pane rodzic wszysktich elemntow okna
     */
    public void setScreen(Pane pane) {
        mainStackPane.getChildren().clear();
        mainStackPane.getChildren().add(pane);
    }

    /**
     * Metoda ladujaca okno glownej aplikacji.
     * @throws IOException
     */
    public static void loadMenu() throws IOException {
        Stage stage=new Stage();
        Parent root = FXMLLoader.load(MainScreenMenuController.class.getResource("/Fxml/Menu/MainScreenMenu.fxml"));
        Scene scene= new Scene(root);
        scene.setFill(new Color(1f,0f,0f,0f));
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }


}
