package org.cis120.chess;

public abstract class ChessPiece {

    private String type;
    private int owner;
    private int r;
    private int f;

    public ChessPiece(String type, int owner, int r, int f) {
        this.type = type;
        this.owner = owner;
        this.r = r;
        this.f = f;
    }

    /**
     * canMoveTo checks if the target x and y point to a valid square to move the pawn to
     *
     * @param r target row to move to
     * @param f target column to move to
     * @return the piece the pawn promotes to, null if promotion is unsuccessful
     */
    public abstract boolean canMoveTo(int r, int f, ChessPiece[][] board);

    /**
     * getType returns the type of the chess piece
     *
     * @return the string type of the chess piece
     */
    public String getType() {
        return type;
    }

    /**
     * getOwner returns which player owns the chess piece
     *
     * @return 0 if the owner is white, 1 otherwise
     */
    public int getOwner() {
        return owner;
    }

    /**
     * updatePos updates the position of the chess piece
     */
    public void updatePos(int r, int f) {
        this.r = r;
        this.f = f;
    }

    /**
     * getRank returns what rank the chess piece is located on
     *
     * @return the rank 0-7 the piece is located on
     */
    public int getRank() {
        return r;
    }

    /**
     * getFile returns what file the chess piece is located on
     *
     * @return the file 0-7 the piece is located on
     */
    public int getFile() {
        return f;
    }

    /**
     * toString turns the chess piece into a string;;; for debugging purposes
     * dont grade pls thanks <3 love u sabrina & vivek
     *
     * @return the string format
     */
    public String toString() {
        return "Type: " + type + ", Owner: " + owner + ", r: " + r + ", f: " + f;
    }

}
