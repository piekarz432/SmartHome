package Controller;

import Clas.Room;
import Clas.Server;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa zapewniajaca obsluge kuchni.
 */
public class KitchenScreenController extends Room {

    /**
     * Przelaczniki lodowki.
     */
    @FXML
    private ToggleGroup fridge;

    @Override
    public void saveLight() {
        try {
            PrintWriter output = new PrintWriter(Server.getSocket().getOutputStream(), true);
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(Server.getSocket().getInputStream()));
            String temp = "kitchenLightReq";
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
    public void saveTemperature() {
        try {
            PrintWriter output = new PrintWriter(Server.getSocket().getOutputStream(), true);
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(Server.getSocket().getInputStream()));
            String temp = "kitchenTempReq";
            output.println(temp);
            output.println((int)temperatureSlider.getValue());
            temp = input.readLine();
            System.out.println(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void setValue() {
        List<Double> value = new ArrayList<>();
        try {
            PrintWriter output = new PrintWriter(Server.getSocket().getOutputStream(), true);
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(Server.getSocket().getInputStream()));
            String temp = "setKitchenValueReq";
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
            lightSlider.setValue(value.get(0));
            valueLightSlider.setText(Integer.valueOf(value.get(0).intValue()).toString());
            setEnableDisable(value);
            temperatureSlider.setValue(value.get(2));
            valueTemperatureSlider.setText(Integer.valueOf(value.get(2).intValue()).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void setEnableDisable(List<Double> value)
    {
        if (value.get(1) == 1) {
            light.getToggles().get(0).setSelected(true);
        } else {
            light.getToggles().get(1).setSelected(true);
        }

        if(value.get(3)==1)
        {
            fridge.getToggles().get(0).setSelected(true);
        }else
        {
            fridge.getToggles().get(1).setSelected(true);
        }
    }

    /**
     * Metoda zapisujaca dane lodowki.
     */
    public void saveFridge() throws SQLException {
        try {
            PrintWriter output = new PrintWriter(Server.getSocket().getOutputStream(), true);
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(Server.getSocket().getInputStream()));
            String temp = "kitchenFridgeReq";
            output.println(temp);
            output.println(checkEnableDisable(fridge));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}