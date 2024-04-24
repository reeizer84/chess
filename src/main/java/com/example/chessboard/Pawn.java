package com.example.chessboard;

import java.util.ArrayList;

public class Pawn extends Piece{

    public ArrayList<Square> getMoves(Board board, Square square){
        ArrayList<Square> list = new ArrayList<>();

        int add;
        if (square.isBlack) add = 2;
        else add = -2;
        Square square2;
        Square square3;
        Square squarePrzelot;
        //ruchy o 2 pionie gdy zaczynamy
        if ((square.row == 1 && square.isBlack)|| (square.row == 6 && !square.isBlack)){
            square2 = board.squares[square.row + add][square.column];
            //ruch w obie strony
            int add2;
            if (square.isBlack) add2 = 1;
            else add2 = -1;

            //square3 to pole o 1 do przodu, kiedy ruszamy sie o 2 sprawdzamy czy nic nie stoi na drodze
            //square2 to pole o 2 do przodu
            square3 = board.squares [square.row + add2][square.column];
            if (!square2.isOccupied && !square3.isOccupied) list.add (square2);

        }

        if (square.isBlack) add = 1;
        else add = -1;

        //ruchy o 1
        if (square.row + add < 8 && square.row + add >= 0) {
            square2 = board.squares[square.row + add][square.column];
            if (!square2.isOccupied) list.add(square2);

        }

        //ruchy gdy pojawia sie bicie
        if ((square.row + add >= 0 && square.row + add < 8) && (square.column - add >=0 && square.column - add < 8)) {
            square2 = board.squares[square.row + add][square.column - add];
            if (square2.isOccupied) {
                if (square.isBlack && !square2.isBlack) list.add(square2);
                else if (!square.isBlack && square2.isBlack) list.add(square2);
            }
        }

        if ((square.row + add >= 0 && square.row + add < 8) && (square.column + add >=0 && square.column + add < 8)) {
            square2 = board.squares[square.row + add][square.column + add];
            if (square2.isOccupied) {
                if (square.isBlack && !square2.isBlack) list.add(square2);
                else if (!square.isBlack && square2.isBlack) list.add(square2);
            }
        }
        //warunek definiujacy bicie w przelocie tylko po wykonaniu ruchu o 2
        if((square.row == 3) || (square.row == 4 )){
            //bicie w przelocie lewo
            if ((square.row + add >= 0 && square.row + add < 8) && (square.column - add >=0 && square.column - add < 8)) {
                square2 = board.squares[square.row + add][square.column - add];
                squarePrzelot = board.squares[square.row ][square.column - add];
                if (!square2.isOccupied && squarePrzelot.isOccupied ) {
                    if (square.isBlack && !squarePrzelot.isBlack) list.add(square2);
                    else if (!square.isBlack && squarePrzelot.isBlack) list.add(square2);
                }
            }
            //bicie w przelocie prawo
            if ((square.row + add >= 0 && square.row + add < 8) && (square.column + add >=0 && square.column + add < 8)) {
                square2 = board.squares[square.row + add][square.column + add];
                squarePrzelot = board.squares[square.row ][square.column + add];
                if (!square2.isOccupied && squarePrzelot.isOccupied) {
                    if (square.isBlack && !squarePrzelot.isBlack) list.add(square2);
                    else if (!square.isBlack && squarePrzelot.isBlack) list.add(square2);
                }
            }
        }



        return list;
    }
}