package org.cis120.chess;

public class Knight extends ChessPiece {

    public Knight(int owner, int r, int f) {
        super("Knight", owner, r, f);
    }

    @Override
    public boolean canMoveTo(int r, int f, ChessPiece[][] board) {
        if (getRank() == r && getFile() == f) {
            return false;
        }

        if ((board[r][f] == null || board[r][f].getOwner() != getOwner()) &&
                Math.abs(getRank() - r) == 2 && Math.abs(getFile() - f) == 1) {
            return true;
        } else if ((board[r][f] == null || board[r][f].getOwner() != getOwner()) &&
                Math.abs(getRank() - r) == 1 && Math.abs(getFile() - f) == 2) {
            return true;
        }
        return false;
    }

}
