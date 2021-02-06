package Clas;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Klasa odpowiedzialna za zmiane polozenia aplikacji.
 */

public abstract class Dragable {

    /**
     * Zmienna przechowujaca okno.
     */
    @FXML
    protected Pane parent;

    /**
     * Wspolrzedna x okna.
     */
    protected double x=0;

    /**
     * Wspolrzedna y okna.
     */
    protected double y=0;

    /**
     * Zmienna przechowujaca okno po zmianie pozycji.
     */
    protected Stage stage;

    /**
     * Metoda umozliwiajca zmiane polozenia okna aplikacji.
     */
    protected void makeDragable()
    {
        parent.setOnMousePressed((mouseEvent -> {
            x=mouseEvent.getSceneX();
            y=mouseEvent.getSceneY();
        }));

        parent.setOnMouseDragged(mouseEvent -> {
            stage=(Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            stage.setX(mouseEvent.getScreenX() - x);
            stage.setY(mouseEvent.getScreenY() - y);
        });
    }
}
