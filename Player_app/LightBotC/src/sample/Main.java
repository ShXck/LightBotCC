package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("LightBotCC");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
