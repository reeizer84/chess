package com.example.chessboard;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class BazaDanych{
    String selectLogin = "SELECT * FROM gracz";



    private static final String DB_URL = "jdbc:mysql://localhost:3306/szachy";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    private Connection connection;
    private ArrayList<Uzytkownik> uzytkownicy = new ArrayList<>();
    public static Uzytkownik zalogowany1 = new Uzytkownik();
    public static Uzytkownik zalogowany2 = new Uzytkownik();





    public BazaDanych() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            try {
                PreparedStatement statement = connection.prepareStatement(selectLogin);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    String login = resultSet.getString("login");
                    String haslo = resultSet.getString("haslo");
                    String pseudonim = resultSet.getString("nickname");
                    int remisy = resultSet.getInt("remisy");
                    int przegrane = resultSet.getInt("przegrane");
                    int wygrane = resultSet.getInt("wygrane");
                    int ranking = resultSet.getInt("ranking");
                    int id=resultSet.getInt("id");
                    Uzytkownik uzytkownik = new Uzytkownik();
                    uzytkownik.id = id;
                    uzytkownik.przegrane = przegrane;
                    uzytkownik.wygrane = wygrane;
                    uzytkownik.remisy = remisy;
                    uzytkownik.ranking=ranking;
                    uzytkownik.setLogin(login);
                    uzytkownik.setHaslo(haslo);
                    uzytkownik.setPseudonim(pseudonim);
                    uzytkownicy.add(uzytkownik);
                }
                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                openPopup();
            }

        }
        catch (ClassNotFoundException e) {
            openPopup();
        } catch (SQLException e) {
            openPopup();
        }
        closeConnection();
    }




    public void openPopup()  {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("UTRACONO POLACZENIE");

        Label label = new Label("Utracono połączenie z serwerem proszę odświeżyć aplikację!");

        Button closeButton = new Button("Odśwież");
        Button closeButton2 = new Button("Zamknij");
        Platform.setImplicitExit(false);
        closeButton2.setOnAction(e -> {
            popupStage.close();
            Platform.exit();
        });

        popupStage.setOnCloseRequest(event -> {

            event.consume();
        });

        closeButton.setOnAction(e -> {

                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                        if(connection != null && !connection.isClosed()){
                            popupStage.close();
                        }
                    } catch (SQLException ex) {

                    }
                    catch (ClassNotFoundException ex) {

                    }

                }
        );

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton2,closeButton);
        layout.setStyle("-fx-padding: 10px;");
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout, 400, 250);
        popupStage.setScene(scene);
        popupStage.showAndWait();



    }
    public ArrayList<Historia> historia_gry_zalogowany(Uzytkownik logged) {
        ArrayList<Historia> historia = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            try {
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM `historia` WHERE historia.gracz_czarny='"+logged.getPseudonim()+"' or historia.gracz_bialy='"+logged.getPseudonim()+"'");
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    String gracz_bialy = resultSet.getString("gracz_bialy");
                    String gracz_czarny = resultSet.getString("gracz_czarny");
                    String zwyciezca = resultSet.getString("zwyciezca");
                    int id = resultSet.getInt("id");
                    String wynik = resultSet.getString("wynik");
                    Historia hist = new Historia();

                    hist.gracz_bialy=gracz_bialy;
                    hist.gracz_czarny=gracz_czarny;
                    hist.id=id;
                    hist.wynik=wynik;
                    hist.zwyciezca=zwyciezca;
                    historia.add(hist);

                }
                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                openPopup();
            }

        } catch (ClassNotFoundException e) {
            openPopup();
        } catch (SQLException e) {
            openPopup();
        }
        return historia;
    }

    public boolean logowanie1(String login_in, String haslo_in) {
        for(Uzytkownik u : uzytkownicy){
            if(login_in.equals(u.getLogin()) && haslo_in.equals(u.getHaslo())){
                zalogowany1 = u;
                return true;
            }}
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            try {
                PreparedStatement statement = connection.prepareStatement(selectLogin);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    String login = resultSet.getString("login");
                    String haslo = resultSet.getString("haslo");
                    String pseudonim = resultSet.getString("nickname");
                    int ranking = resultSet.getInt("ranking");
                    Uzytkownik uzytkownik = new Uzytkownik();
                    uzytkownik.ranking=ranking;
                    uzytkownik.setLogin(login);
                    uzytkownik.setHaslo(haslo);
                    uzytkownik.setPseudonim(pseudonim);
                    uzytkownicy.add(uzytkownik);
                }
                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                openPopup();
            }
            for(Uzytkownik u : uzytkownicy){
                if(login_in.equals(u.getLogin()) && haslo_in.equals(u.getHaslo())){
                    zalogowany1 = u;
                    return true;
                }
            }
        } catch (ClassNotFoundException e) {
            openPopup();
        } catch (SQLException e) {
            openPopup();
        }
        closeConnection();
        return false;
    }


    public boolean logowanie2(String login_in, String haslo_in) {
        for(Uzytkownik u : uzytkownicy){
            if(login_in.equals(u.getLogin()) && haslo_in.equals(u.getHaslo())){
                zalogowany2 = u;

                return true;
            }}
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            try {
                PreparedStatement statement = connection.prepareStatement(selectLogin);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    String login = resultSet.getString("login");
                    String haslo = resultSet.getString("haslo");
                    String pseudonim = resultSet.getString("nickname");
                    int ranking = resultSet.getInt("ranking");
                    Uzytkownik uzytkownik = new Uzytkownik();
                    uzytkownik.ranking = ranking;
                    uzytkownik.setLogin(login);
                    uzytkownik.setHaslo(haslo);
                    uzytkownik.setPseudonim(pseudonim);
                    uzytkownicy.add(uzytkownik);
                }
                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                openPopup();
            }
            for(Uzytkownik u : uzytkownicy){
                if(login_in.equals(u.getLogin()) && haslo_in.equals(u.getHaslo())){
                    zalogowany2 = u;

                    return true;
                }
            }
        } catch (ClassNotFoundException e) {
            openPopup();
        } catch (SQLException e) {
            openPopup();
        }
        closeConnection();
        return false;
    }
    public void wylogujUzytkownik1(){
        zalogowany1.setLogin("");
        zalogowany1.setPseudonim("");
        zalogowany1.setHaslo("");
        zalogowany1.ranking=0;

    }
    public void wylogujUzytkownik2() {
        zalogowany2.setLogin("");
        zalogowany2.setPseudonim("");
        zalogowany2.setHaslo("");
        zalogowany2.ranking=0;

    }





    public void zapiszWynikGry(Uzytkownik graczBialy, Uzytkownik graczCzarny ,Uzytkownik wygrany,String wynik) {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement statement = connection.prepareStatement("INSERT INTO `historia` (`id`, `gracz_bialy`, `gracz_czarny`, `zwyciezca`, `wynik`) VALUES (NULL, ?, ?, ?, ?)");
            statement.setString(1, graczBialy.getPseudonim());
            statement.setString(2, graczCzarny.getPseudonim());
            statement.setString(3, wygrany.getPseudonim());
            statement.setString(4, wynik);
            statement.executeUpdate();
            statement = connection.prepareStatement("UPDATE `gracz` SET `ranking`='"+(wygrany.ranking+10)+"', `wygrane` = '"+(wygrany.wygrane+1)+"', `przegrane` = '"+(wygrany.przegrane)+"', `remisy` = '"+wygrany.remisy+"' WHERE `gracz`.`id` = "+wygrany.id+";");
            statement.executeUpdate();

            if(wygrany.getPseudonim().equals(graczBialy.getPseudonim())){
                statement = connection.prepareStatement("UPDATE `gracz` SET `ranking`='"+(graczCzarny.ranking-10)+"', `wygrane` = '"+(graczCzarny.wygrane)+"', `przegrane` = '"+(graczCzarny.przegrane+1)+"', `remisy` = '"+graczCzarny.remisy+"' WHERE `gracz`.`id` = "+graczCzarny.id+";");
                statement.executeUpdate();
            }
            else{
                statement = connection.prepareStatement("UPDATE `gracz` SET `ranking`='"+(graczBialy.ranking+10)+"', `wygrane` = '"+(graczBialy.wygrane)+"', `przegrane` = '"+(graczBialy.przegrane+1)+"', `remisy` = '"+graczBialy.remisy+"' WHERE `gracz`.`id` = "+graczBialy.id+";");
                statement.executeUpdate();
            }
            statement.close();
        } catch (SQLException e) {
            openPopup();
        } finally {
            closeConnection();
        }
    }

    public void zapiszRemis(Uzytkownik graczBialy, Uzytkownik graczCzarny) {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement statement = connection.prepareStatement("INSERT INTO `historia` (`id`, `gracz_bialy`, `gracz_czarny`, `zwyciezca`, `wynik`) VALUES (NULL, ?, ?, ?, ?)");
            statement.setString(1, graczBialy.getPseudonim());
            statement.setString(2, graczCzarny.getPseudonim());
            statement.setString(3, "NULL");
            statement.setString(4, "REMIS");
            statement.executeUpdate();
            statement = connection.prepareStatement("UPDATE `gracz` SET `wygrane` = '"+(graczCzarny.wygrane)+"', `przegrane` = '"+(graczCzarny.przegrane)+"', `remisy` = '"+(graczCzarny.remisy+1)+"' WHERE `gracz`.`id` = "+graczCzarny.id+";");
            statement.executeUpdate();
            statement = connection.prepareStatement("UPDATE `gracz` SET `wygrane` = '"+(graczBialy.wygrane)+"', `przegrane` = '"+(graczBialy.przegrane)+"', `remisy` = '"+(graczBialy.remisy+1)+"' WHERE `gracz`.`id` = "+graczBialy.id+";");
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            openPopup();
        } finally {
            closeConnection();
        }
    }

    @FXML
    public boolean rejestracja(String login_in, String haslo_in, String pseudonim_in){
        for(Uzytkownik u : uzytkownicy) {
            if (haslo_in.equals(u.getLogin()) || pseudonim_in.equals(u.getPseudonim())) {

                return false;
            }
        }
        try {

            if(login_in.trim().isEmpty() || haslo_in.trim().isEmpty() || pseudonim_in.trim().isEmpty()) {

            }
            else {
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement statement = connection.prepareStatement("INSERT INTO `gracz` (`id`, `ranking`, `nickname`, `login`, `haslo`) VALUES (NULL, '500', '" + pseudonim_in + "', '" + login_in + "', '" + haslo_in + "')");
                statement.executeUpdate();
                Uzytkownik nowy = new Uzytkownik();
                nowy.ranking = 500;
                nowy.setHaslo(haslo_in);
                nowy.setPseudonim(pseudonim_in);
                nowy.setLogin(login_in);
                uzytkownicy.add(nowy);
                statement.close();
                closeConnection();


                return true;
            }
        } catch (SQLException e) {
            openPopup();
        }

        return false;
    }


    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();

            }
        } catch (SQLException e) {

        }
    }
}