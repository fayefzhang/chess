package org.cis120.chess;

public class Bishop extends ChessPiece {

    public Bishop(int owner, int r, int f) {
        super("Bishop", owner, r, f);
    }

    @Override
    public boolean canMoveTo(int r, int f, ChessPiece[][] board) {

        if (Math.abs(getFile() - f) == Math.abs(getRank() - r)) {
            int tempR = getRank();
            int tempF = getFile();

            while (tempR != r && tempF != f) {
                if (board[tempR][tempF] != null &&
                        !(tempF == getFile() && tempR == getRank())) {
                    return false;
                }

                if (getRank() - r > 0) {
                    tempR -= 1;
                } else if (getRank() - r < 0) {
                    tempR += 1;
                }

                if (getFile() - f > 0) {
                    tempF -= 1;
                } else if (getFile() - f < 0) {
                    tempF += 1;
                }
            }

            return (board[r][f] == null || board[r][f].getOwner() != getOwner());
        }

        return false;
    }

}
