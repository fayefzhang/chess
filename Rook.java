package org.cis120.chess;

public class Rook extends ChessPiece {

    private boolean hasMoved;

    public Rook(int owner, int r, int f) {
        super("Rook", owner, r, f);
    }

    @Override
    public boolean canMoveTo(int r, int f, ChessPiece[][] board) {
        if (getRank() == r && getFile() == f) {
            return false;
        }

        if (getFile() == f) {
            int tempR = getRank();
            while (tempR != r) {
                if (board[tempR][getFile()] != null && !(tempR == getRank())) {
                    return false;
                }

                if (getRank() - r > 0) {
                    tempR -= 1;
                } else if (getRank() - r < 0) {
                    tempR += 1;
                }
            }

            return (board[r][f] == null || board[r][f].getOwner() != getOwner());
        } else if (getRank() == r) {
            int tempF = getFile();
            while (tempF != f) {
                if (board[getRank()][tempF] != null && !(tempF == getFile())) {
                    return false;
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
