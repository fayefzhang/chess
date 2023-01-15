package org.cis120.chess;

public class King extends ChessPiece {

    private boolean hasMoved;

    public King(int owner, int r, int f) {
        super("King", owner, r, f);
    }

    @Override
    public boolean canMoveTo(int r, int f, ChessPiece[][] board) {
        if (board[r][f] == null || board[r][f].getOwner() != getOwner()) {
            if (getRank() == r && getFile() == f) {
                return false;
            } else if (Math.abs(getRank() - r) <= 1 && Math.abs(getFile() - f) <= 1) {
                return true;
            }

            // queen-side & king-side castling
            if (getFile() - f == 2 && getRank() == r) {
                ChessPiece tgt = board[r][7];
                if (tgt instanceof Rook && !((Rook) tgt).hasMoved() && !hasMoved()) {
                    return true;
                }
            } else if (getFile() - f == -2 && getRank() == r) {
                ChessPiece tgt = board[r][0];
                if (tgt instanceof Rook && !((Rook) tgt).hasMoved() && !hasMoved()) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * setHasMoved sets the hasMoved private variable to the argued variable.
     *
     * @param b the argued boolean variable to set hasMoved to.
     */
    public void setHasMoved(boolean b) {
        hasMoved = b;
    }

    /**
     * hasMoved returns whether the piece has already moved.
     *
     * @return true if the piece has moved, false otherwise.
     */
    public boolean hasMoved() {
        return hasMoved;
    }

}
