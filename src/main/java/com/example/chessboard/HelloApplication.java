package com.example.chessboard;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;
import static javafx.scene.paint.Color.GRAY;



public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("menuchess.fxml"));
        Parent menuRoot = menuLoader.load();
        Scene menuScene = new Scene(menuRoot);

        menuScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        primaryStage.setScene(menuScene);
        primaryStage.setTitle("Gra w szachy");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}