package org.cis120.chess;

public class Queen extends ChessPiece {

    public Queen(int owner, int r, int f) {
        super("Queen", owner, r, f);
    }

    @Override
    public boolean canMoveTo(int r, int f, ChessPiece[][] board) {
        if (getRank() == r && getFile() == f) {
            return false;
        }

        // copied from bishop method
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

        // copied from the rook logic
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

}
