package Controller;

import Clas.Room;
import Clas.Server;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Klasa zapewniajaca obsluge lazienik.
 */
public class BathroomScreenController extends Room implements Initializable {

    /**
     * Przelaczniki prysznica.
     */
    @FXML
    private ToggleGroup shower;

    /**
     * Przelaczniki pralki.
     */
    @FXML
    private ToggleGroup washingMachine;

    /**
     * Suwak umozliwiajacy wybor temperatury wody.
     */
    @FXML
    protected Slider showerTemperatureSlider;

    /**
     * Aktualnie wybrana wartosc temperatury wody.
     */
    @FXML
    protected Label valueShowerTemperatureSlider;

    /**
     * Suwak umozliwiajacy wybor temperatury prania.
     */
    @FXML
    protected Slider washingMaschineTemperatureSlider;

    /**
     * Aktualnie wybrana wartosc temperatury prania.
     */
    @FXML
    protected Label valueWashingMaschineTemperatureSlider;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        super.initialize(null,null);

        showerTemperatureSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                valueShowerTemperatureSlider.textProperty().setValue(String.valueOf(t1.intValue()));
            }
        });

        washingMaschineTemperatureSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                valueWashingMaschineTemperatureSlider.textProperty().setValue(String.valueOf(t1.intValue()));
            }
        });
    }

    @Override
    public void saveLight() throws SQLException {
        try {
            PrintWriter output = new PrintWriter(Server.getSocket().getOutputStream(), true);
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(Server.getSocket().getInputStream()));
            String temp = "bathroomLightReq";
            output.println(temp);
            output.println((int)lightSlider.getValue());
            output.println(checkEnableDisable(light));
            temp = input.readLine();
            System.out.println(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveTemperature() throws SQLException {
        try {
            PrintWriter output = new PrintWriter(Server.getSocket().getOutputStream(), true);
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(Server.getSocket().getInputStream()));
            String temp = "bathroomTempReq";
            output.println(temp);
            output.println((int)temperatureSlider.getValue());
            temp = input.readLine();
            System.out.println(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void setValue() throws SQLException {
        List<Double> value = new ArrayList<>();
        try {
            PrintWriter output = new PrintWriter(Server.getSocket().getOutputStream(), true);
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(Server.getSocket().getInputStream()));
            String temp = "setBathroomValueReq";
            Double wartosc = null;
            output.println(temp);
            temp = HomeScreenController.user.getUsername();
            output.println(temp);
            temp = input.readLine();
            temp = input.readLine();
            wartosc = Double.valueOf(Double.parseDouble(temp));
            value.add(wartosc);
            temp = input.readLine();
            wartosc = Double.valueOf(Double.parseDouble(temp));
            value.add(wartosc);
            temp = input.readLine();
            wartosc = Double.valueOf(Double.parseDouble(temp));
            value.add(wartosc);
            temp = input.readLine();
            wartosc = Double.valueOf(Double.parseDouble(temp));
            value.add(wartosc);
            temp = input.readLine();
            wartosc = Double.valueOf(Double.parseDouble(temp));
            value.add(wartosc);
            temp = input.readLine();
            wartosc = Double.valueOf(Double.parseDouble(temp));
            value.add(wartosc);
            temp = input.readLine();
            wartosc = Double.valueOf(Double.parseDouble(temp));
            value.add(wartosc);
            lightSlider.setValue(value.get(0));
            valueLightSlider.setText(Integer.valueOf(value.get(0).intValue()).toString());
            temperatureSlider.setValue(value.get(2));
            valueTemperatureSlider.setText(Integer.valueOf(value.get(2).intValue()).toString());
            showerTemperatureSlider.setValue(value.get(3));
            valueShowerTemperatureSlider.setText(Integer.valueOf(value.get(3).intValue()).toString());
            washingMaschineTemperatureSlider.setValue(value.get(5));
            valueWashingMaschineTemperatureSlider.setText(Integer.valueOf(value.get(5).intValue()).toString());
            setEnableDisable(value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void setEnableDisable(List<Double> value) {

        if (value.get(1) == 1) {
            light.getToggles().get(0).setSelected(true);
        } else {
            light.getToggles().get(1).setSelected(true);
        }

        if(value.get(4)==1)
        {
            shower.getToggles().get(0).setSelected(true);
        }else
        {
            shower.getToggles().get(1).setSelected(true);
        }

        if(value.get(6)==1)
        {
            washingMachine.getToggles().get(0).setSelected(true);
        }else
        {
            washingMachine.getToggles().get(1).setSelected(true);
        }
    }

    /**
     * Metoda zapisujaca dane prysznica.
     */
    public void saveShower() {
        try {
            PrintWriter output = new PrintWriter(Server.getSocket().getOutputStream(), true);
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(Server.getSocket().getInputStream()));
            String temp = "bathroomShowerReq";
            output.println(temp);
            output.println((int)showerTemperatureSlider.getValue());
            output.println(checkEnableDisable(shower));
            System.out.println("test");
            temp = input.readLine();
            System.out.println(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda zapisaujca dane pralki.
     */
    public void saveWashingMachine(ActionEvent event) {
        try {
            PrintWriter output = new PrintWriter(Server.getSocket().getOutputStream(), true);
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(Server.getSocket().getInputStream()));
            String temp = "bathroomWashmachineReq";
            output.println(temp);
            output.println((int)washingMaschineTemperatureSlider.getValue());
            output.println(checkEnableDisable(washingMachine));
            temp = input.readLine();
            System.out.println(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

