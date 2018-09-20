package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.io.IOException;

public class Interface extends Application {

    @FXML
    Button showButton = new Button("show");

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("Interface.fxml"));
        primaryStage.setTitle("LightBotCC");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();

       }

    public void passWindow() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 689);
        Stage stage = new Stage();
        stage.setTitle("LightBotC");
        stage.setScene(scene);
        stage.show();
    }

    public void passInstructions() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("Instructions.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = new Stage();
        stage.setTitle("Instructions");
        stage.setScene(scene);
        stage.show();
    }
}
