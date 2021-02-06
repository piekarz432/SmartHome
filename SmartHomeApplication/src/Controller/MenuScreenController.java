package Controller;

import Clas.Dragable;
import Interface.Exit;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Klasa obslugujaca przelaczania miedzy funkcjonalnosciami w menu glownym.
 */
public class MenuScreenController extends Dragable implements Initializable, Exit {

    /**
     * Kontroler okna.
     */
    @FXML
    private MainScreenMenuController mainScreenMenuController;

    /**
     * Rodzic wszystkich elementow w oknie.
     */
    @FXML
    private VBox vbox;

    /**
     * Metoda otwierajaca okno oblsugi kuchni.
     */
    @FXML
    public void openKitchen(){
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/Fxml/Menu/KitchenScreen.fxml"));
        VBox vBox = null;
        try {
            vBox = loader.load();
        }catch(IOException e){
            e.printStackTrace();
        }
        KitchenScreenController kitchenScreenController = loader.getController();
        kitchenScreenController.setMainController(mainScreenMenuController);
        mainScreenMenuController.setScreen(vBox);
    }

    /**
     * Metoda otwierajaca okno oblsugi pokoju.
     */
    @FXML
    public void openRoom(){
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/Fxml/Menu/RoomScreen.fxml"));
        VBox vBox = null;
        try {
            vBox = loader.load();
        }catch(IOException e){
            e.printStackTrace();
        }
        RoomScreenController roomScreenController = loader.getController();
        roomScreenController.setMainController(mainScreenMenuController);
        mainScreenMenuController.setScreen(vBox);
    }

    /**
     * Metoda otwierajaca okno oblsugi lazienki.
     */
    @FXML
    public void openBathroom(){
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/Fxml/Menu/Bathroom.fxml"));
        VBox vBox = null;
        try {
            vBox = loader.load();
        }catch(IOException e){
            e.printStackTrace();
        }
        BathroomScreenController bathroomScreenController = loader.getController();
        bathroomScreenController.setMainController(mainScreenMenuController);
        mainScreenMenuController.setScreen(vBox);
    }

    /**
     * Metoda otwierajaca okno oblsugi usterek.
     */
    public void openFaults(){
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/Fxml/Menu/Faults.fxml"));
        VBox vBox = null;
        try {
            vBox = loader.load();
        }catch(IOException e){
            e.printStackTrace();
        }
        FaultsController faultsController = loader.getController();
        faultsController.setMainController(mainScreenMenuController);
        mainScreenMenuController.setScreen(vBox);
    }
    /**
     * Metoda otwierajaca okno oblsugi otwierania drzwi i okien.
     */
    public void openWindowsAndDoors(){
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/Fxml/Menu/DorsAndWindows.fxml"));
        VBox vBox = null;
        try {
            vBox = loader.load();
        }catch(IOException e){
            e.printStackTrace();
        }
        DoorControlController windowsAndBlindsController = loader.getController();
        windowsAndBlindsController.setMainController(mainScreenMenuController);
        mainScreenMenuController.setScreen(vBox);
    }

    /**
     * Metoda otwierajaca oko uzytkownika.
     * @throws IOException
     */
    public void openScreenHome() throws IOException {
        Parent fxml= FXMLLoader.load(getClass().getResource("/Fxml/Menu/HomeScreen.fxml"));
        vbox.getChildren().removeAll();
        vbox.getChildren().setAll(fxml);
        vbox.setVisible(true);
        HomeScreenController.vbox=vbox;
    }

    /**
     * Metoda ustawiajaca odpowiedni kontroler.
     * @param mainScreenMenuController kontroler okna
     */
    public void setMainController(MainScreenMenuController mainScreenMenuController) {
        this.mainScreenMenuController = mainScreenMenuController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        makeDragable();
    }

    @Override
    public void OnExit() {
        Platform.exit();
    }




}
