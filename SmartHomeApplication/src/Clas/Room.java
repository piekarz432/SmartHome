package Clas;

import Controller.MainScreenMenuController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Klasa bedaca wzorcem do towrzenia pokoi.
 */
public abstract class Room extends Dragable implements Initializable {
    /**
     * Suwak umozliwiajacy wybor intensywnosci swiatla.
     */
    @FXML
    protected Slider lightSlider;

    /**
     * Aktualnie wybrana wartosc intensywnosci swiatla.
     */
    @FXML
    protected Label valueLightSlider;

    /**
     * Suwak umozliwiajacy wybor temperatury.
     */
    @FXML
    protected Slider temperatureSlider;

    /**
     * Aktualnie wybrana temperatura.
     */
    @FXML
    protected Label valueTemperatureSlider;

    /**
     * Przelaczniki swiatla.
     */
    @FXML
    protected ToggleGroup light;

    /**
     * Kontroler okna.
     */
    private MainScreenMenuController mainScreenMenuController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setValue();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        lightSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                valueLightSlider.textProperty().setValue(String.valueOf(t1.intValue()));
            }
        });

        temperatureSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                valueTemperatureSlider.textProperty().setValue(String.valueOf(t1.intValue()));
            }
        });

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
     * Metoda sprawdzajaca ktory przycisk z grupy przelacznikow zostal wybrany.
     * @param group grupa przelacznikow
     * @return
     * <pre>
     * wartosc 1 jezeli zostal wybrany przycisk Wlacz
     * wartosc 0 jezeli zostal wybrany przycisk Wylacz
     * </pre>
     */
    protected int checkEnableDisable(ToggleGroup group)
    {
        RadioButton selectRadioButton= (RadioButton) group.getSelectedToggle();
        if(selectRadioButton.getText().equals("Włącz")) {
            return 1;
        }
        return 0;
    }

    /**
     * Metoda umozliwiajaca powrot z wybranej opcji.
     */
    @FXML
    public void backToMenu() {mainScreenMenuController.showMenu(); }

    /**
     * Metoda zapisujaca dane temperatury.
     * @throws SQLException
     */
    public abstract void saveLight() throws SQLException;

    /**
     * Metoda zapisujaca dane temperatury.
     * @throws SQLException
     */
    public abstract void saveTemperature() throws SQLException;

    /**
     * Metoda pobierajca i ustawiajaca dane.
     * @throws SQLException
     */
    protected abstract void setValue() throws SQLException;

    /**
     * Metoda ustawiajca wszystkie przelaczniki.
     * @param value lista wartosci
     */
    protected abstract void setEnableDisable(List<Double> value);

}
