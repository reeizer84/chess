package com.example.chessboard;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import java.util.ArrayList;


public class Square {
    protected boolean isOccupied;
    protected Pane pane;
    protected ImageView background;
    protected Piece piece;
    protected int row;
    protected int column;
    protected boolean isBlack;
    public Square (){
        piece = null;
        row = -1;
        column = -1;
        isBlack = false;
        isOccupied = false;
    }
    public Square (Pane pane_in, int row_in, int column_in){
        pane = pane_in;
        row = row_in;
        column = column_in;
        isBlack = false;
        isOccupied = false;
    }

    public void setBackground (){
        String path = null;
        //sprawdza jaka klasa dziedziczaca po Piece to jest, ktora figura
        if (piece instanceof Bishop){
            if (isBlack) path = String.valueOf(getClass().getResource("bishop_b.png"));
            else path = String.valueOf(getClass().getResource("bishop_w.png"));
        }
        else if (piece instanceof King){
            if (isBlack) path = String.valueOf(getClass().getResource("king_b.png"));
            else path = String.valueOf(getClass().getResource("king_w.png"));
        }
        else if (piece instanceof Knight){
            if (isBlack) path = String.valueOf(getClass().getResource("knight_b.png"));
            else path = String.valueOf(getClass().getResource("knight_w.png"));
        }
        else if (piece instanceof Pawn){
            if (isBlack) path = String.valueOf(getClass().getResource("pawn_b.png"));
            else path = String.valueOf(getClass().getResource("pawn_w.png"));
        }
        else if (piece instanceof Queen){
            if (isBlack) path = String.valueOf(getClass().getResource("queen_b.png"));
            else path = String.valueOf(getClass().getResource("queen_w.png"));
        }
        else if (piece instanceof Rook) {
            if (isBlack) path = String.valueOf(getClass().getResource("rook_b.png"));
            else path = String.valueOf(getClass().getResource("rook_w.png"));
        }


        //ustawia zdjecie konkretnego Pane w GridPane
        if (path != null){
            Image image = new Image (path);
            background = new ImageView(image);
            pane.getChildren().add(background);
        }
    }

    public void setSquare (Pane pane_in, Piece piece_in, int row_in, int column_in, boolean isBlack_in){
        pane = pane_in;
        piece = piece_in;
        row = row_in;
        column = column_in;
        isBlack = isBlack_in;
        isOccupied = true;
        this.setBackground();
    }

    public void resetSquare (){
        isOccupied = false;
        pane.getChildren().remove (this.background);
        background = null;
        piece = null;
        isBlack = false;
    }
    //na razie bez uzytku, ale mozna uzyc do ew. zapisywania ruchow w bazie
    public String translateToLetters (){
        String columnToLetters = "";

        if (column == 0) columnToLetters = "A";
        else if (column == 1) columnToLetters = "B";
        else if (column == 2) columnToLetters = "C";
        else if (column == 3) columnToLetters = "D";
        else if (column == 4) columnToLetters = "E";
        else if (column == 5) columnToLetters = "F";
        else if (column == 6) columnToLetters = "G";
        else if (column == 7) columnToLetters =  "H";

        return columnToLetters + row;
    }

    public boolean move(ArrayList<Square> possible_moves, Square move_dest) {
        boolean is_possible = false;
        for (Square square : possible_moves) {
            if (square == move_dest) {
                is_possible = true;
                break;
            }
        }

        if (is_possible) {
            // Roszada - ruch króla o dwie pola w stronę wieży


            // czarna promocja
            if (piece instanceof Pawn && isBlack && move_dest.row == 7) {
                move_dest.resetSquare();
                move_dest.setSquare(move_dest.pane, new Queen(), move_dest.row, move_dest.column, this.isBlack);
            }
            // biala promocja
            else if (piece instanceof Pawn && !isBlack && move_dest.row == 0) {
                move_dest.resetSquare();
                move_dest.setSquare(move_dest.pane, new Queen(), move_dest.row, move_dest.column, this.isBlack);
            }
            else {
                move_dest.resetSquare();
                move_dest.setSquare(move_dest.pane, this.piece, move_dest.row, move_dest.column, this.isBlack);
            }

            this.resetSquare();
            return true;
        } else {
            return false;
        }
    }


}
