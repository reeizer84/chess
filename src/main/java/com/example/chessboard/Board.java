package com.example.chessboard;

import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.synedra.validatorfx.Check;

import java.io.IOException;
import java.util.ArrayList;

public class Board {

    protected Square[][] squares; // odpowiada tez za pojedyncze pole, ale przeznaczonej do tego klasy
    EventHandler<MouseEvent> click;
    boolean during_click = false; // do kontroli wylapywania w momencie, ktorego klikania jest uzytkownik
    //za pierwszym razem wyswietla ruchy, za drugim pole na ktore chce sie ruszyc
    ArrayList <Square> last_possible = new ArrayList<>(); // pomocnicze do przechowywania ostatnich mozliwych ruchow
    //(przy ostatnim podswietleniu)
    Square last_clicked;
    MenuController menu = new MenuController();
    boolean whitesTurn = false;
    boolean during_check = false;
    boolean white_check = false;
    CheckmateDetector detector = new CheckmateDetector();

    public Board (ChessboardController controller){
        CheckmateDetector detector = new CheckmateDetector();
        //bieremy z controllera gridPane
        Pane[][] gridPaneArray = controller.gridPaneArray;

        Timeline Timer1 = controller.timer11;
        Timeline Timer2 = controller.timer22;

        //ustawianie czarnych i bialych figur
        squares = new Square[8][8];
        //pole czarne
        for (int r = 0; r < 2; r++){
            for (int c = 0; c < squares.length; c++){
                squares [r][c] = new Square();
            }
        }
        //pola biale
        for (int r = 6; r < 8; r++){
            for (int c = 0; c < squares.length; c++){
                squares [r][c] = new Square();
            }
        }

        //pola puste
        for (int r = 2; r < 6; r++){
            for (int c = 0; c < squares.length; c++){
                squares [r][c] = new Square (gridPaneArray [r][c], r, c);
            }
        }

        squares [0][0].setSquare (gridPaneArray [0][0], new Rook(),0,0,true);
        squares [0][1].setSquare (gridPaneArray [0][1], new Knight(),0,1,true);
        squares [0][2].setSquare (gridPaneArray [0][2], new Bishop(),0,2,true);
        squares [0][3].setSquare (gridPaneArray [0][3], new Queen(),0,3,true);
        squares [0][4].setSquare (gridPaneArray [0][4], new King(),0,4,true);
        squares [0][5].setSquare (gridPaneArray [0][5], new Bishop(),0,5,true);
        squares [0][6].setSquare (gridPaneArray [0][6], new Knight(),0,6,true);
        squares [0][7].setSquare (gridPaneArray [0][7], new Rook(),0,7,true);

        for (int c = 0; c < 8; c++){
            squares [1][c].setSquare (gridPaneArray [1][c], new Pawn(),1, c, true);
            squares [6][c].setSquare (gridPaneArray [6][c], new Pawn(),6, c, false);
        }

        squares [7][0].setSquare (gridPaneArray [7][0], new Rook(),7,0,false);
        squares [7][1].setSquare (gridPaneArray [7][1], new Knight(),7,1,false);
        squares [7][2].setSquare (gridPaneArray [7][2], new Bishop(),7,2,false);
        squares [7][3].setSquare (gridPaneArray [7][3], new Queen(),7,3,false);
        squares [7][4].setSquare (gridPaneArray [7][4], new King(),7,4,false);
        squares [7][5].setSquare (gridPaneArray [7][5], new Bishop(),7,5,false);
        squares [7][6].setSquare (gridPaneArray [7][6], new Knight(),7,6,false);
        squares [7][7].setSquare (gridPaneArray [7][7], new Rook(),7,7,false);

        //do podswietlania:)
        Light.Distant light = new Light.Distant();
        light.setAzimuth(-45.0);

        Lighting lighting = new Lighting();
        lighting.setLight(light);
        lighting.setSurfaceScale(3.0);

        //tutaj najwazniejszy kod
        click = mouseEvent -> {
            //usuwa podswietlenie jak ktos kliknal, a nie moze tam albo np. jest jego figura
            removeEffects();

            //po kliknieciu na kafelek, sprawdza jakie id ma Pane (kafelek)\


            String id = ((Pane)mouseEvent.getSource()).getId();
            //przerabia id
            String[] parts = id.split (" ");
            //id = "x y"
            //row = "x",  column = "y"
            String row = parts[0];
            String column = parts[1];

            //upewnia sie, ze int
            int int_row = Integer.parseInt(row);
            int int_column = Integer.parseInt(column);

            boolean moved = false;
            if (during_click && !during_check){
                //jak ktos juz podswietlil, to teraz lecimy ruch
                moved = last_clicked.move (last_possible, squares [int_row][int_column]);
            }
            else if (during_check && during_click){
                ArrayList<Square> squares1 = getCurrentKingSquare(this);
                ArrayList<Square> safeSquares;
                if (white_check) safeSquares = detector.getSafeSquares(this, squares1.get(1), whitesTurn);
                else safeSquares = detector.getSafeSquares(this, squares1.get(0), whitesTurn);


                moved = last_clicked.move (last_possible, squares [int_row][int_column]);
            }

            if (squares[int_row][int_column].isOccupied && squares[int_row][int_column].isBlack != whitesTurn) {
                return; // Zwraca bez dodawania podświetlenia
            }


            if (moved){
                //reset parametrow, zeby po nastepnym kliknieciu dzialy sie inne rzeczy w tym mouseEvencie
                //check dla czarnego
                ArrayList<Square> squares = getCurrentKingSquare(this);
                if (squares.size() < 2){
                    if (squares.get(0).isBlack) zwyciezca(!squares.get(0).isBlack); // bialy
                    else zwyciezca(!squares.get(0).isBlack); // czarny
                }

                if (!whitesTurn){
                    boolean isCheckmate1 = detector.isCheckmate(this, !whitesTurn,squares.get(0));
                    during_check = detector.isKingInCheck(this,squares.get(0),!whitesTurn);
                    if (during_check) white_check = false;
                }
                else{
                    boolean isCheckmate2 = detector.isCheckmate(this, !whitesTurn,squares.get(1));
                    during_check = detector.isKingInCheck(this,squares.get(1),!whitesTurn);
                    if (during_check) white_check = true;
                }
                whitesTurn = !whitesTurn;
                last_possible = null;
                last_clicked = null;
                during_click = false;
                if(whitesTurn)
                {
                    Timer1.stop();
                    Timer2.play();
                }
                else
                {
                    Timer2.stop();
                    Timer1.play();
                }
            }



            if (squares [int_row][int_column].isOccupied && !moved ) {
                //podswietlenie:)
                if (during_check){
                    ArrayList<Square> squares1 = getCurrentKingSquare(this);
                    if (white_check) last_possible = detector.getSafeSquares(this, squares1.get(1), whitesTurn);
                    else last_possible = detector.getSafeSquares(this, squares1.get(0), whitesTurn);
                }
                else{
                    last_possible = squares[int_row][int_column].piece.getMoves(this, squares[int_row][int_column]);
                }
                for (Square square : last_possible) square.pane.setEffect(lighting);
//zmmianaaaa
                //mozna nakurwiac ruch po during_click
                if (whitesTurn == squares[int_row][int_column].isBlack) {
                    during_click = true;
                    last_clicked = squares[int_row][int_column];
                }
            }
        };

        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                //za pierwszym uruchomieniem dodaje id dla kazdego kafla i daje mu agenta;) (setOnMouseClicked)
                //agent nasluchuje
                String s = i + " " + j;
                gridPaneArray [i][j].setId (s);
                gridPaneArray [i][j].setOnMouseClicked(click);
            }
        }
    }

    public void removeEffects(){
        for (int i = 0 ; i < 8; i++){
            for (int j = 0; j < 8; j++) this.squares[i][j].pane.setEffect(null);
        }
    }

    public ArrayList<Square> getCurrentKingSquare(Board board) {
        //index 0 to czarny krol, index 1 to bialy krol;
        ArrayList<Square> Kings_Square = new ArrayList<>();

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Square square = board.squares[row][col];
                if(square.isBlack) {
                    if (square.isOccupied && square.piece instanceof King) {
                        Kings_Square.add(square);
                    }
                }else if(square.isOccupied && square.piece instanceof King) {
                    Kings_Square.add(square);
                }
            }
        }
        return Kings_Square;
    }
    public void zwyciezca(boolean stan){
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Warning");
        Label label;

        if(stan) {
            label = new Label(menu.baza.zalogowany1.getPseudonim()+" Wygrał!");
            menu.baza.zapiszWynikGry(menu.baza.zalogowany1,menu.baza.zalogowany2,menu.baza.zalogowany1,"Szachmat");
        }
        else {
            label = new Label(menu.baza.zalogowany2.getPseudonim()+" Wygrał!");
            menu.baza.zapiszWynikGry(menu.baza.zalogowany2,menu.baza.zalogowany1,menu.baza.zalogowany2,"Szachmat");
        }
        Button closeButton2 = new Button("Zamknij");
        Platform.setImplicitExit(false);



        closeButton2.setOnAction(e -> {
            popupStage.close();
            Platform.exit();


        });


        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton2);
        layout.setStyle("-fx-padding: 10px;");
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout, 400, 250);
        popupStage.setScene(scene);
        popupStage.showAndWait();
    }
}
