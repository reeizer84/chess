package com.example.chessboard;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MenuController implements Initializable {



    @FXML
    private Button zaloguj1 = new Button();
    @FXML
    private Button zaloguj2 = new Button();
    @FXML
    private Button zarejestruj = new Button();
    @FXML
    private TextField login1 = new TextField();
    @FXML
    private TextField login2 = new TextField();
    @FXML
    private TextField login3 = new TextField();
    @FXML
    private TextField haslo1 = new TextField();
    @FXML
    private TextField haslo2 = new TextField();
    @FXML
    private TextField haslo3 = new TextField();
    @FXML
    private TextField pseudonim = new TextField();
    @FXML
    private Label zalogowany1_ = new Label();
    @FXML
    private Label zalogowany2_ = new Label();
    @FXML
    private Button startButton;
    @FXML
    private ChoiceBox<String> timechoice = new ChoiceBox<>();
    @FXML
    private ChoiceBox<String> colorChoiceBox = new ChoiceBox<>();
public static int  colorValue=0; // bialy = 0, czarny = 1
    public static int czas = 10;


      BazaDanych baza = new BazaDanych();
    @FXML
    private void zalogujUzytkownik1(){

            if (baza.zalogowany1.getPseudonim().equals("") && !baza.zalogowany2.getHaslo().equals(haslo1.getText()) ) {

                    loginPopup(baza.logowanie1(login1.getText(), haslo1.getText()));

            }
            if (!baza.zalogowany1.getPseudonim().equals("")) {
                zalogowany1_.setText(baza.zalogowany1.getPseudonim());
            }



    }
    @FXML
    private void wyloguj1(){
        if(baza.zalogowany1.getPseudonim().equals("")){

        }
        else {
            baza.wylogujUzytkownik1();
            zalogowany1_.setText("");
            wylogowaniePopup();
        }

    }
    @FXML
    private void wyloguj2(){
        if(baza.zalogowany2.getPseudonim().equals("")){

        }
        else {
            baza.wylogujUzytkownik2();
            zalogowany2_.setText("");
            wylogowaniePopup();
        }

    }
    @FXML
    private void zalogujUzytkownik2(){
        if (baza.zalogowany2.getPseudonim().equals("") && !baza.zalogowany1.getHaslo().equals(haslo2.getText())) {

                loginPopup(baza.logowanie2(login2.getText(), haslo2.getText()));
        }
        if(!baza.zalogowany2.getPseudonim().equals("")){
            zalogowany2_.setText(baza.zalogowany2.getPseudonim());
        }
    }
    @FXML
    private void rejestracja(){

        rejestracjaPopup(baza.rejestracja(login3.getText(),haslo3.getText(),pseudonim.getText()));
    }

    public void wylogowaniePopup(){
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Warning");
        Label label = new Label("Wylogowano pomyślnie");

        Button closeButton2 = new Button("Zamknij");
        Platform.setImplicitExit(false);
        closeButton2.setOnAction(e -> {
            popupStage.close();

        });


        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton2);
        layout.setStyle("-fx-padding: 10px;");
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout, 400, 250);
        popupStage.setScene(scene);
        popupStage.showAndWait();

    }
    public void rejestracjaPopup(boolean stan){
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Warning");
        Label label;
        if(stan) {
            label = new Label("Zarejestrowano pomyślnie!");
        }
        else {
            label = new Label("Wystąpił błąd, proszę sprawdzić z innymi danymi!");
        }
        Button closeButton2 = new Button("Zamknij");
        Platform.setImplicitExit(false);
        closeButton2.setOnAction(e -> {
            popupStage.close();

        });


        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton2);
        layout.setStyle("-fx-padding: 10px;");
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout, 400, 250);
        popupStage.setScene(scene);
        popupStage.showAndWait();

    }
    @FXML
    public void historia1(){
        if(baza.zalogowany1.getPseudonim().equals(""))
        {

        }
        else {
            historiaPopup(baza.historia_gry_zalogowany(baza.zalogowany1));
        }
    }
    @FXML
    public void historia2(){
        if(baza.zalogowany2.getPseudonim().equals(""))
        {

        }
        else {
            historiaPopup(baza.historia_gry_zalogowany(baza.zalogowany2));
        }
    }

    public void historiaPopup(ArrayList<Historia> historia){
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Warning");
        VBox layout = new VBox(10);

        for (Historia h : historia){
            String gra = "Id gry: "+ h.id+"   Gracz Biały: "+h.gracz_bialy+"   Gracz Czarny: "+ h.gracz_czarny+ "   Zwycięzca: "+h.zwyciezca+"   Wynik: "+ h.wynik;

            Label label = new Label(gra);

            layout.getChildren().addAll(label);
        }
        Button closeButton2 = new Button("Zamknij");
        Platform.setImplicitExit(false);
        closeButton2.setOnAction(e -> {
            popupStage.close();

        });



        layout.getChildren().addAll( closeButton2);
        layout.setStyle("-fx-padding: 10px;");
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout, 600, 250);
        popupStage.setScene(scene);
        popupStage.showAndWait();
    }

    public void loginPopup(boolean stan){
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Warning");
        Label label;

        if(stan) {
            label = new Label("Zalogowano pomyślnie!");
        }
        else {
            label = new Label("Proszę sprawdzić swoje dane logowania");
        }
        Button closeButton2 = new Button("Zamknij");
        Platform.setImplicitExit(false);
        closeButton2.setOnAction(e -> {
            popupStage.close();

        });


        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton2);
        layout.setStyle("-fx-padding: 10px;");
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout, 400, 250);
        popupStage.setScene(scene);
        popupStage.showAndWait();
    }
    @FXML
    public void ustawCzas(ActionEvent event) {
        String selectedValue = timechoice.getValue();
        if (selectedValue != null) {
            if (selectedValue.equals("1 minuta")) {
                czas = 1;
            } else if (selectedValue.equals("3 minuty")) {
                czas = 3;
            } else if (selectedValue.equals("5 minut")) {
                czas = 5;
            } else if (selectedValue.equals("10 minut")) {
                czas = 10;
            }
        }
    }
    @FXML
    public void ustawKolor(ActionEvent event) {
        String selectedColor = colorChoiceBox.getValue();
        Uzytkownik pomoc;

        if (selectedColor.equals("BIAŁE")) {
            colorValue = 0;


        } else if (selectedColor.equals("CZARNE")) {
            colorValue = 1;
             pomoc = baza.zalogowany1;
            baza.zalogowany1=baza.zalogowany2;
            baza.zalogowany2=pomoc;
        } else {
            // Wybór losowy
            colorValue = (int) (Math.random() * 2);
            if (colorValue==0) {

            } else if (colorValue==1) {

                pomoc = baza.zalogowany2;
                baza.zalogowany2=baza.zalogowany1;
                baza.zalogowany1=pomoc;
            }

        }

    }

    @FXML
    private void handleStartButtonClick() throws IOException {

        if (!baza.zalogowany1.getPseudonim().equals("") && !baza.zalogowany2.getPseudonim().equals("")) {

            FXMLLoader chessLoader = new FXMLLoader(getClass().getResource("chessgame.fxml"));
            Parent chessRoot = chessLoader.load();
            Scene chessScene = new Scene(chessRoot);


            ChessboardController controller = chessLoader.getController();
            new Board(controller);


            controller.setMenuController(baza);


            Stage stage = (Stage) startButton.getScene().getWindow();
            stage.setScene(chessScene);
            stage.show();
        } else {

        }

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(!baza.zalogowany1.getPseudonim().equals("")){
            zalogowany1_.setText(baza.zalogowany1.getPseudonim());
        }
        if(!baza.zalogowany2.getPseudonim().equals("")){
            zalogowany2_.setText(baza.zalogowany2.getPseudonim());
        }
    }
}