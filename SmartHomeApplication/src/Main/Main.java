package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    public static Stage primaryStage = new Stage();
    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/Fxml/Login/MainScreenLogin.fxml"));
        Scene scene= new Scene(root);
        scene.setFill(new Color(1f,0f,0f,0f));
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
