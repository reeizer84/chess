package com.example.chessboard;

import java.util.ArrayList;
import java.util.stream.Stream;

public class Bishop extends Piece{
    public ArrayList<Square> getMoves(Board board, Square square){
        ArrayList<Square> list = new ArrayList<>();

        //laczymy liste dla kazdego kierunku po kolei (4 linie po ktorych moze sie ruszac)
        ArrayList<Square> one_d_list1 = getDirection(board, square,1, 1);
        Stream.of (list, one_d_list1).forEach (list::addAll);

        ArrayList<Square> one_d_list2 = getDirection(board, square,-1, -1);
        Stream.of (list, one_d_list2).forEach (list::addAll);

        ArrayList<Square> one_d_list3 = getDirection(board, square,-1, 1);
        Stream.of (list, one_d_list3).forEach (list::addAll);

        ArrayList<Square> one_d_list4 = getDirection(board, square,1, -1);
        Stream.of (list, one_d_list4).forEach (list::addAll);

        return list;
    }

    public ArrayList<Square> getDirection (Board board, Square square, int dx, int dy){
        ArrayList<Square> list = new ArrayList<>();
        //dzieki temu w kazdym kierunku mozna sprawdzac pola
        int row = square.row + dy;
        int column = square.column + dx;

        Square square2;
        //zeby nie wyjebalo
        while ((row < 8 && row >= 0) && (column < 8 && column >= 0)){
            //w kazdej iteracji wybieramy nastepny kwadrat po przekatnej
            square2 = board.squares[row][column];
                //jezeli pole jest wolne
                if (!square2.isOccupied) list.add (square2);
                else {
                    //jezeli pojawia sie bicie
                    if ((square.isBlack && !square2.isBlack) || (!square.isBlack && square2.isBlack)) {
                        list.add(square2);
                    }
                    //jezeli na drodze pojawia sie figura tego samego koloru
                    //(tu nie trzeba ifa, bo roznego koloru wyzej to reszta mozliwosci)
                    break;
                }

                row += dy;
                column += dx;
        }


        return list;
    }
}
