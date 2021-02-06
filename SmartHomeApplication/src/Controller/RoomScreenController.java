package Controller;

import Clas.Room;
import Clas.Server;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa zapewniajaca obsluge pokoju.
 */
public class RoomScreenController extends Room {

    /**
     * Przelaczniki telewizora.
     */
    @FXML
    private ToggleGroup tv;

    @Override
    public void saveLight() {
        try {
            PrintWriter output = new PrintWriter(Server.getSocket().getOutputStream(), true);
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(Server.getSocket().getInputStream()));
            String temp = "roomLightReq";
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
            String temp = "roomTempReq";
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
            String temp = "setRoomValueReq";
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
    protected void setEnableDisable(List<Double> value) {
        if (value.get(1) == 1) {
            light.getToggles().get(0).setSelected(true);
        } else {
            light.getToggles().get(1).setSelected(true);
        }

        if(value.get(3)==1) {
            tv.getToggles().get(0).setSelected(true);
        }else {
            tv.getToggles().get(1).setSelected(true);
        }
    }

    /**
     * Metoda zapisujaca dane telewizora.
     */
    public void saveTv() {
        try {
            PrintWriter output = new PrintWriter(Server.getSocket().getOutputStream(), true);
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(Server.getSocket().getInputStream()));
            String temp = "roomTvReq";
            output.println(temp);
            output.println(checkEnableDisable(tv));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
