package Controller;

import Clas.Dragable;
import Clas.Server;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Klasa umozlwiajaca obsluge sterowania drzwiami i oknami.
 */
public class DoorControlController extends Dragable implements Initializable {
    /**
     * Pole wyboru drzwi pokoju.
     */
    @FXML
    private CheckBox d_pokoj;

    /**
     * Pole wyboru drzwi kuchni.
     */
    @FXML
    private CheckBox d_kuchnia;

    /**
     * Pole wyboru drzwi lazienki.
     */
    @FXML
    private CheckBox d_lazienka;

    /**
     * Pole wyboru drzwi frontowych.
     */
    @FXML
    private CheckBox d_frontowe;

    /**
     * Pole wyboru okna pokoju.
     */
    @FXML
    private CheckBox o_pokoj;

    /**
     * Pole wyboru okna lazienki.
     */
    @FXML
    private CheckBox o_lazienka;

    /**
     * Pole wyboru okna kuchni.
     */
    @FXML
    private CheckBox o_kuchnia;

    /**
     * Pole wyboru okna garazu.
     */
    @FXML
    private CheckBox o_garaz;

    /**
     * Kontroler okna.
     */
    private MainScreenMenuController mainScreenMenuController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            PrintWriter output = new PrintWriter(Server.getSocket().getOutputStream(), true);
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(Server.getSocket().getInputStream()));
            String temp = "setDoorWindowReq";
            output.println(temp);
            temp = HomeScreenController.user.getUsername();
            output.println(temp);
            temp = input.readLine();
            temp = input.readLine();
            if (temp.equals("1")) {
                d_pokoj.setSelected(true);
            } else {
                d_pokoj.setSelected(false);
            }

            temp = input.readLine();
            if (temp.equals("1")) {
                d_kuchnia.setSelected(true);
            } else {
                d_kuchnia.setSelected(false);
            }

            temp = input.readLine();
            if (temp.equals("1")) {
                d_lazienka.setSelected(true);
            } else {
                d_lazienka.setSelected(false);
            }

            temp = input.readLine();
            if (temp.equals("1")) {
                d_frontowe.setSelected(true);
            } else {
                d_frontowe.setSelected(false);
            }

            temp = input.readLine();
            if (temp.equals("1")) {
                o_pokoj.setSelected(true);
            } else {
                o_pokoj.setSelected(false);
            }

            temp = input.readLine();
            if (temp.equals("1")) {
                o_lazienka.setSelected(true);
            } else {
                o_lazienka.setSelected(false);
            }

            temp = input.readLine();
            if (temp.equals("1")) {
                o_kuchnia.setSelected(true);
            } else {
                o_kuchnia.setSelected(false);
            }

            temp = input.readLine();
            if (temp.equals("1")) {
                o_garaz.setSelected(true);
            } else {
                o_garaz.setSelected(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda odpowiadajaca za zmiane checkboxow
     */
    public void change() {

    }

    /**
     * Metoda zapisujaca dane do bazy.
     */
    public void save() {
        try {
            PrintWriter output = new PrintWriter(Server.getSocket().getOutputStream(), true);
            String temp = "doorWindowReq";
            output.println(temp);
            temp = HomeScreenController.user.getUsername();
            output.println(temp);
            if (d_pokoj.isSelected()) {
                output.println(1);
            } else {
                output.println(0);
            }

            if (d_kuchnia.isSelected()) {
                output.println(1);
            } else {
                output.println(0);
            }

            if (d_lazienka.isSelected()) {
                output.println(1);
            } else {
                output.println(0);
            }

            if (d_frontowe.isSelected()) {
                output.println(1);
            } else {
                output.println(0);
            }

            if (o_pokoj.isSelected()) {
                output.println(1);
            } else {
                output.println(0);
            }

            if (o_lazienka.isSelected()) {
                output.println(1);
            } else {
                output.println(0);
            }

            if (o_kuchnia.isSelected()) {
                output.println(1);
            } else {
                output.println(0);
            }

            if (o_garaz.isSelected()) {
                output.println(1);
            } else {
                output.println(0);
            }

            System.out.println("test");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda ustawiajaca kontroler okna.
     * @param mainScreenMenuController kontroler okna
     */
    public void setMainController(MainScreenMenuController mainScreenMenuController) {
        this.mainScreenMenuController = mainScreenMenuController;
    }

    /**
     * Metoda umozliwiajaca powrot z wybranej opcji.
     */
    @FXML
    public void backToMenu(){
        mainScreenMenuController.showMenu();
    }


}
