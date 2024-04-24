package com.example.chessboard;

import java.util.ArrayList;

public class CheckmateDetector {

    public Square matting_square;
    public static boolean isCheckmate(Board board, boolean isBlackTurn, Square kingSquare) {
        if (!isKingInCheck(board, kingSquare, isBlackTurn)) {
            return false;
        }
        Arduino arduino = new Arduino();
        arduino.sendSignalToArduino(1);

        // Sprawdza czy krol moze uciec
        ArrayList<Square> safeSquares = getSafeSquares(board, kingSquare, isBlackTurn);
        if (!safeSquares.isEmpty()) {

            return false;
        }
        else{

        }

        Square square = KingAttackers(board,kingSquare,isBlackTurn);
        ArrayList<Square> lista = new ArrayList<>();
        lista = getIntermediateSquares(board, kingSquare, square);

        //sprawdzanie czy sojusznicza figura moze zbic
        if (square != null){
            if (isKingInCheck (board, square, !isBlackTurn)){

                return false;
            }
            else{

            }
        }

        //zaslanianie bicia przez inna figure
        for (Square square5: lista){
            if (KingAttackers (board, square5, !isBlackTurn) != null){

                return false;
            }
        }



        return true;
    }

    public static boolean isKingInCheck(Board board, Square kingSquare, boolean isBlackTurn) {
        ArrayList<Square> opponentMoves = new ArrayList<>();
        // zbierz wszystkie ruchy przeciwnika
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Square square = board.squares[row][col];
                if (square.isOccupied && square.isBlack != isBlackTurn) {
                    ArrayList<Square> moves = square.piece.getMoves(board, square);
                    opponentMoves.addAll(moves);
                }
            }
        }

        // sprawdz czy ktoras figura moze zbic krola
        for (Square move : opponentMoves) {
            if (move == kingSquare) {
                return true;
            }
        }

        return false;
    }

    public static ArrayList<Square> getSafeSquares(Board board, Square kingSquare, boolean isBlackTurn) {
        ArrayList<Square> safeSquares = new ArrayList<>();
        ArrayList<Square> kingMoves = kingSquare.piece.getMoves(board, kingSquare);

        for (Square move : kingMoves) {
            // zasymyluj ruch krola i sprawdz czy bedzie w checku
            Piece movedPiece = move.piece;
            move.piece = kingSquare.piece;

            if (!isKingInCheck(board, move, isBlackTurn)) {
                // krol moze poruszyl sie na bezpieczne pole bez bycia w checku
                safeSquares.add(move);
            }

            // cofnij ruch
            move.piece = movedPiece;
        }

        return safeSquares;
    }

    private static Square KingAttackers(Board board, Square kingSquare, boolean isBlackTurn) {
        ArrayList<CheckingPieces> opponentMoves = new ArrayList<>();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Square square = board.squares[row][col];
                if (square.isOccupied && square.isBlack != isBlackTurn) {
                    ArrayList<CheckingPieces> moves = new ArrayList<>();

                    CheckingPieces pomoc = new CheckingPieces();
                    pomoc.checking = square;
                    pomoc.lista = square.piece.getMoves(board,square);
                    moves.add(pomoc);
                    opponentMoves.addAll(moves);
                }
            }
        }

        for (CheckingPieces move : opponentMoves) {
            for(Square move2 : move.lista) {
                if (move2 == kingSquare) {
                    if (!(move.checking.piece instanceof King) ) return move.checking;
                }
            }
        }

        return null;
    }

    private static ArrayList<Square> getIntermediateSquares(Board board, Square kingSquare, Square attackingSquare) {
        ArrayList<Square> intermediateSquares = new ArrayList<>();

        int kingRow = kingSquare.row;
        int kingCol = kingSquare.column;
        int attackingRow = attackingSquare.row;
        int attackingCol = attackingSquare.column;

        // oblicz roznice kolumn i wierszy miedzy pozycja krola, a figura przeciwnika
        int rowDiff = attackingRow - kingRow;
        int colDiff = attackingCol - kingCol;

        // ustal kierunek ruchu dla kazdej kolumny i wiersza
        int rowDirection = Integer.signum(rowDiff);
        int colDirection = Integer.signum(colDiff);

        // oblicz liczbe ruchow potrzebna aby dosiegnac figure
        int steps = Math.max(Math.abs(rowDiff), Math.abs(colDiff)) - 1;

        // przejdz polami miedzy krolem, a figura atakujaca
        for (int i = 1; i <= steps; i++) {
            int row = kingRow + i * rowDirection;
            int col = kingCol + i * colDirection;
            Square square = board.squares[row][col];
            intermediateSquares.add(square);
        }

        return intermediateSquares;
    }

}