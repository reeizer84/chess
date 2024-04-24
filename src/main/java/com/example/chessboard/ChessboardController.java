package com.example.chessboard;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ChessboardController {

    @FXML
    private HBox hbox;
    @FXML
    private VBox vbox;
    @FXML
    private GridPane chessboard = new GridPane();
    @FXML
    public Pane[][] gridPaneArray = new Pane[8][8];
    @FXML
    private Label uzytkownik1 = new Label();
    @FXML
    private Label uzytkownik2 = new Label();
    @FXML
    private Label ranking1 = new Label();
    @FXML
    private Label ranking2 = new Label();
    //@FXML
    private MenuController menuController;
    BazaDanych baza;

    @FXML
    private Label timer1;
    @FXML
    private Label timer2;


    private Timeline timer;
    @FXML
    public ChoiceBox<String> timechoice;
    @FXML
    public Button wroc;
    private MenuController menu = new MenuController();
    private int timeRemaining = 60 * menu.czas;

    private boolean whitesTurn = true;

    private int timeRemaining1;
    private int timeRemaining2;

    public Timeline timer11;
    public Timeline timer22;

    private Board board;



    public void setMenuController(MenuController menuController) {
        this.menuController = menuController;
    }

    @FXML
    private Button przyciskGracz1;

    @FXML
    private Button przyciskGracz2;
    @FXML
    private void zaproponujRemis(ActionEvent event) {
        Button sourceButton = (Button) event.getSource();

        String gracz;
        Uzytkownik zalogowany;

        if (sourceButton == przyciskGracz1) {
            gracz = baza.zalogowany1.getPseudonim();
            zalogowany = baza.zalogowany1;
        } else if (sourceButton == przyciskGracz2) {
            gracz =  baza.zalogowany2.getPseudonim();
            zalogowany = baza.zalogowany2;
        } else {
            return;
        }

        // Tworzenie i wyświetlanie okna dialogowego (PopUp)
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Propozycja remisu");
        alert.setHeaderText(gracz + " proponuje remis!");
        alert.setContentText("Czy chcesz zaakceptować propozycję remisu od " + zalogowany.getPseudonim() + "?");

        // Ustawienie przycisków w oknie dialogowym
        ButtonType buttonTypeAccept = new ButtonType("Zaakceptuj", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonTypeReject = new ButtonType("Odrzuć", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeAccept, buttonTypeReject);

        baza.zapiszRemis(baza.zalogowany1, baza.zalogowany2);

        // Obsługa kliknięcia przycisku Zaakceptuj
        alert.setOnCloseRequest(dialogEvent -> {
            if (alert.getResult() == buttonTypeAccept) {
                try {
                    FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("menuchess.fxml"));
                    Parent menuRoot = menuLoader.load();
                    Scene menuScene = new Scene(menuRoot);

                    menuScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

                    stopTimer1();
                    stopTimer2();

                    Stage stage = (Stage) sourceButton.getScene().getWindow();
                    stage.setScene(menuScene);
                    stage.show();
                } catch (IOException e) {

                }
            }
        });

        // Wyświetlanie okna dialogowego
        alert.show();
    }

    @FXML
    private void poddajsieGracz1(ActionEvent event) {
        if (baza.zalogowany1 != null) {
            baza.zalogowany1.ranking = baza.zalogowany1.ranking - 10;
            baza.zalogowany2.ranking += 10;









            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Poddanie się gracza");
            alert.setHeaderText("Gracz 1 poddał się!");
            alert.setContentText("Użytkownik: " + baza.zalogowany1.getPseudonim() + " poddał się!");


            baza.zapiszWynikGry(baza.zalogowany1, baza.zalogowany2, baza.zalogowany2,"Poddanie");
            // Wyświetlanie Alertu
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("menuchess.fxml"));
                        Parent menuRoot = menuLoader.load();
                        Scene menuScene = new Scene(menuRoot);

                        menuScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

                        stopTimer1();
                        stopTimer2();

                        Stage stage = (Stage) wroc.getScene().getWindow();
                        stage.setScene(menuScene);
                        stage.show();
                    } catch (IOException e) {

                    }
                }
            });
        }
    }

    private void showTimeUpDialog(Uzytkownik winningPlayer, Uzytkownik losingPlayer) {
        String losingPlayerLabel = (losingPlayer == BazaDanych.zalogowany1) ? "Gracz 1" : "Gracz 2";
        String winningPlayerLabel = (winningPlayer == BazaDanych.zalogowany1) ? "Gracz 1" : "Gracz 2";

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Koniec gry");
        alert.setHeaderText("Czas " + losingPlayerLabel + " minął!");
        alert.setContentText(winningPlayerLabel + " wygrywa!");

        losingPlayer.ranking -= 10;
        winningPlayer.ranking += 10;

        baza.zapiszWynikGry(winningPlayer, losingPlayer,winningPlayer,"KONIEC CZASU");

        // Obsługa zamknięcia okna dialogowego
        alert.setOnCloseRequest(dialogEvent -> {
            try {
                FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("menuchess.fxml"));
                Parent menuRoot = menuLoader.load();
                Scene menuScene = new Scene(menuRoot);

                menuScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

                stopTimer1();
                stopTimer2();

                Stage stage = (Stage) wroc.getScene().getWindow();
                stage.setScene(menuScene);
                stage.show();
            } catch (IOException e) {

            }
        });

        // Wyświetlanie okna dialogowego
        alert.show();
    }

    @FXML
    private void poddajsieGracz2(ActionEvent event) {
        if (baza.zalogowany2 != null) {
            baza.zalogowany2.ranking = baza.zalogowany2.ranking - 10;
            baza.zalogowany1.ranking += 10;


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Poddanie się gracza");
            alert.setHeaderText("Gracz 1 poddał się!");
            alert.setContentText("Użytkownik: " + baza.zalogowany2.getPseudonim() + " poddał się!");
            String nul="NULL";

            baza.zapiszWynikGry(BazaDanych.zalogowany1, BazaDanych.zalogowany2,baza.zalogowany1,"Poddanie");
            // Wyświetlanie Alertu
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("menuchess.fxml"));
                        Parent menuRoot = menuLoader.load();
                        Scene menuScene = new Scene(menuRoot);

                        menuScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

                        stopTimer1();
                        stopTimer2();

                        Stage stage = (Stage) wroc.getScene().getWindow();
                        stage.setScene(menuScene);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @FXML
    private void handleWrocButtonClick(ActionEvent event) throws IOException {
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("menuchess.fxml"));
        Parent menuRoot = menuLoader.load();
        Scene menuScene = new Scene(menuRoot);

        menuScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        stopTimer1();
        stopTimer2();

        Stage stage = (Stage) wroc.getScene().getWindow();
        stage.setScene(menuScene);
        stage.show();
    }

    public void setMenuController(BazaDanych menuController) {
        baza = menuController;
    }

    public void initialize() {

        //board = new Board(this);

        initializeTimer();
        startTimers();
        uzytkownik1.setText(baza.zalogowany1.getPseudonim());
        uzytkownik2.setText(baza.zalogowany2.getPseudonim());
        ranking1.setText(String.valueOf(baza.zalogowany1.ranking));
        ranking2.setText(String.valueOf(baza.zalogowany2.ranking));
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Pane square = new Pane();
                square.setStyle("-fx-background-color: " + ((row + col) % 2 == 0 ? "#FFFFFF" : "purple") + ";");
                chessboard.add(square, col, row);
                gridPaneArray [row][col] = square;
            }

        }
    }
    private void initializeTimer() {


        //whitesTurn = board.whitesTurn;

        timeRemaining1 = 60 * menu.czas;
        timeRemaining2 = 60 * menu.czas;

        timer11 = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (timeRemaining1 > 0) {
                timeRemaining1--;
                int minutes = timeRemaining1 / 60;
                int seconds = timeRemaining1 % 60;
                String time = String.format("%02d:%02d", minutes, seconds);
                timer1.setText(time);
            } else {
                stopTimer1();
                showTimeUpDialog(BazaDanych.zalogowany1, BazaDanych.zalogowany2);
            }
        }));
        timer11.setCycleCount(Animation.INDEFINITE);

        timer22 = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (timeRemaining2 > 0) {
                timeRemaining2--;
                int minutes = timeRemaining2 / 60;
                int seconds = timeRemaining2 % 60;
                String time = String.format("%02d:%02d", minutes, seconds);
                timer2.setText(time);
            } else {
                stopTimer2();
                showTimeUpDialog(BazaDanych.zalogowany2, BazaDanych.zalogowany1);
            }
        }));
        timer22.setCycleCount(Animation.INDEFINITE);
    }

    public void stopTimer1() {
        timer11.stop();
    }

    public void stopTimer2() {
        timer22.stop();
    }

    public void startTimer1() {
        timer11.play();
    }

    public void startTimer2() {
        timer22.play();
    }

    private void startTimer() {
        timer.play();
    }

    private void startTimers() {
        if (whitesTurn) {
            timer11.play();
            timer22.stop();
        } else {
            timer11.stop();
            timer22.play();
        }
    }
    private void stopTimer() {
        timer.stop();
    }

}







