package com.example.chessboard;

import java.util.ArrayList;

public class Knight extends Piece {
    public ArrayList<Square> getMoves(Board board, com.example.chessboard.Square square) {
        ArrayList<Square> list = new ArrayList<>();
        int[][] offsets = {
                {1, -2}, {-1, -2}, {1, 2}, {-1, 2},
                {2, -1}, {2, 1}, {-2, -1}, {-2, 1}
        };

        for (int[] offset : offsets) {
            Square square2;
            int row_add = 0;
            int col_add = 0;
            int i = 0;
            for (int cord : offset) {
                if (i == 0) row_add = cord;
                else col_add = cord;
                i++;
                i %= 2;
            }

            if ((square.row + row_add >= 0 && square.row + row_add < 8)
                    && (square.column + col_add >= 0 && square.column + col_add < 8)) {
                square2 = board.squares[square.row + row_add][square.column + col_add];

                if (!square2.isOccupied) list.add(square2);
                else {
                    if ((square.isBlack && !square2.isBlack) || (!square.isBlack && square2.isBlack)) {
                        list.add(square2);
                    }
                }
            }
        }

        return list;
    }

}