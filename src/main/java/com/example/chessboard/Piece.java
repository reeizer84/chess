package com.example.chessboard;

import java.util.ArrayList;

public abstract class Piece{


    public Piece (){}

    public abstract ArrayList<Square> getMoves(Board board, Square square);
}
