package com.example.chessboard;

import java.util.ArrayList;
import java.util.stream.Stream;

public class Queen extends Piece{
    public ArrayList<Square> getMoves(Board board, com.example.chessboard.Square square){
        ArrayList<Square> list = new ArrayList<>();

        ArrayList<Square> one_d_list1 = getDirection(board, square,1, 1);
        Stream.of (list, one_d_list1).forEach (list::addAll);

        ArrayList<Square> one_d_list2 = getDirection(board, square,-1, -1);
        Stream.of (list, one_d_list2).forEach (list::addAll);

        ArrayList<Square> one_d_list3 = getDirection(board, square,-1, 1);
        Stream.of (list, one_d_list3).forEach (list::addAll);

        ArrayList<Square> one_d_list4 = getDirection(board, square,1, -1);
        Stream.of (list, one_d_list4).forEach (list::addAll);

        ArrayList<Square> one_d_list5 = getDirection(board, square,0, 1);
        Stream.of (list, one_d_list5).forEach (list::addAll);

        ArrayList<Square> one_d_list6 = getDirection(board, square,0, -1);
        Stream.of (list, one_d_list6).forEach (list::addAll);

        ArrayList<Square> one_d_list7 = getDirection(board, square,1, 0);
        Stream.of (list, one_d_list7).forEach (list::addAll);

        ArrayList<Square> one_d_list8 = getDirection(board, square,-1, 0);
        Stream.of (list, one_d_list8).forEach (list::addAll);

        return list;
    }

    public ArrayList<Square> getDirection (Board board, Square square, int dx, int dy){
        ArrayList<Square> list = new ArrayList<>();

        //ruchy w pionowej linii, zmieniaja poczatkowa wartosc i pozniejsza inkrementacje w petli
        //dlatego sprawdzamy to teraz
        boolean horizontal_move = false;
        boolean vertical_move = false;
        int row = square.row;
        int column = square.column;

        if (dx != 0 && dy != 0){
            row = square.row + dy;
            column = square.column + dx;
        }
        else if (dx == 0){
            row = square.row + dy;
            vertical_move = true;
        }
        //(dy == 0)
        else{
            column = square.column + dx;
            horizontal_move = true;
        }

        Square square2;
        while ((row < 8 && row >= 0) && (column < 8 && column >=0)){
            square2 = board.squares[row][column];
            if (!square2.isOccupied) list.add (square2);
            else{
                if ((square.isBlack && !square2.isBlack) || (!square.isBlack && square2.isBlack)){
                    list.add (square2);
                }
                break;
            }

            if (!horizontal_move && !vertical_move){
                row += dy;
                column += dx;
            }
            else if (horizontal_move) column += dx;
            //(vertical_move)
            else row += dy;

        }

        return list;
    }
}
