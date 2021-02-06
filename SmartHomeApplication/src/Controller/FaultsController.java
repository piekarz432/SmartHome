package Controller;

import Clas.Dragable;
import Clas.Server;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Klasa zapewniajaca obsluge zglaszania usterek.
 */
public class FaultsController extends Dragable implements Initializable {

    /**
     * Kontroler okna
     */
    private MainScreenMenuController mainScreenMenuController;

    /**
     * Pole umozliwiajace wpisanie usterek.
     */
    @FXML
    private TextArea otherFaults;

    /**
     * Pole do obslugi pokazywania usterek.
     */
    @FXML
    private Label showFaultsText;

    /**
     * Przycisk do obslugi pokazwyania usterek.
     */
    @FXML
    private Button showFaultsButton;

    /**
     * Przycisk do obslugi zapisu usterek.
     */
    @FXML
    private Button sendFaults;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        makeDragable();
    }

    /**
     * Metoda ustawiajaca kontroler okna.
     * @param mainScreenMenuController kontroler okna
     */
    public void setMainController(MainScreenMenuController mainScreenMenuController) {
        this.mainScreenMenuController = mainScreenMenuController;
    }

    /**
     * Metoda umozliwiajaca zapis usterek.
     */
    public void sendFaultsToDatabase(){
        try {
            PrintWriter output = new PrintWriter(Server.getSocket().getOutputStream(), true);
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(Server.getSocket().getInputStream()));
            String temp = "addFaultReq";
            output.println(temp);
            temp = otherFaults.getText();
            output.println(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(otherFaults.getText());
        otherFaults.clear();
    }

    /**
     * Metoda umozliwiajaca powrot do menu glownego.
     */
    public void backToMenu() {mainScreenMenuController.showMenu(); }

    /**
     * Metoda pokazujaca usterki.
     * @throws SQLException
     */
    public void showFaults() throws SQLException{
        try {
            PrintWriter output = new PrintWriter(Server.getSocket().getOutputStream(), true);
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(Server.getSocket().getInputStream()));
            String temp = "getFaults";
            String faultyList = "";
            output.println(temp);
            input.readLine();
            int faultsNumber = Integer.parseInt(input.readLine());
            for (int i=0; i<faultsNumber; i++) {
                faultyList = faultyList + input.readLine() + "\n";
            }
        showFaultsText.setText("Lista usterek:");
        showFaultsButton.setText("Wstecz");
        otherFaults.setEditable(false);
        sendFaults.setDisable(true);
        otherFaults.setText(faultyList);
        showFaultsButton.setOnAction(event -> back());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda zmieniajaca dzialanie przyciskow.
     */
    private void back()
    {
        showFaultsText.setText("Podaj opis usterki:");
        showFaultsButton.setText("Wyswietl usterki");
        otherFaults.setText("");
        otherFaults.setEditable(true);
        sendFaults.setDisable(false);
        showFaultsButton.setOnAction(event -> {
            try {
                showFaults();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }

}
