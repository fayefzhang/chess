package org.cis120.chess;

public class Pawn extends ChessPiece {

    public Pawn(int owner, int r, int f) {
        super("Pawn", owner, r, f);
    }

    @Override
    public boolean canMoveTo(int r, int f, ChessPiece[][] board) {
        if (getRank() == r && getFile() == f) {
            return false;
        }

        ChessPiece tgt = board[r][f];

        if (getOwner() == 0) {
            if ((r == getRank() - 1) && (f == getFile())) {
                return (tgt == null);
            } else if ((getRank() == 6) && (r == 4) && (board[5][f] == null) && (f == getFile())) {
                return (tgt == null);
            } else if ((getRank() == 3) && board[3][f] != null && (r == getRank() - 1) &&
                    Math.abs(f - getFile()) == 1 && tgt == null) {
                return (board[3][f].getOwner() == 1); // check movedTwice in Chess logic
            } else if ((r == getRank() - 1) &&
                    ((f == getFile() + 1) || (f == getFile() - 1))) {
                if (tgt != null) {
                    return (tgt.getOwner() == 1);
                }
                return false;
            }
        } else if (getOwner() == 1) {
            if ((r == getRank() + 1) && (f == getFile())) {
                return (tgt == null);
            } else if ((getRank() == 1) && (r == 3) && (board[2][f] == null) && (f == getFile())) {
                return (tgt == null);
            } else if ((getRank() == 4) && board[4][f] != null && (r == getRank() + 1) &&
                    Math.abs(f - getFile()) == 1 && tgt == null) {
                return (board[4][f].getOwner() == 0); // check movedTwice in Chess logic
            } else if ((r == getRank() + 1) &&
                    ((f == getFile() + 1) || (f == getFile() - 1))) {
                if (tgt != null) {
                    return (tgt.getOwner() == 0);
                }
                return false;
            }
        }

        return false;
    }

    /**
     * promote checks if it is valid for the pawn to promote once
     * it reaches the end of the board & promotes to the player's piece
     * of choice.
     *
     * @param target the piece the player wants to promote the pawn to.
     * @return the piece the pawn promotes to, null if promotion is unsuccessful
     */
    public ChessPiece promote(String target) {
        if (getRank() == 0 || getRank() == 8) {
            if (target.equals("Bishop")) {
                return new Bishop(getOwner(), getRank(), getFile());
            } else if (target.equals("Rook")) {
                return new Rook(getOwner(), getRank(), getFile());
            } else if (target.equals("Queen")) {
                return new Queen(getOwner(), getRank(), getFile());
            } else if (target.equals("Knight")) {
                return new Knight(getOwner(), getRank(), getFile());
            }
        }
        return null;
    }

}
