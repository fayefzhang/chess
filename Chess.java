package org.cis120.chess;

import java.util.LinkedList;

public class Chess {

    private ChessPiece[][] board;
    private ChessPiece[][] tgt; // temp board to test isCheck w/
    private LinkedList<ChessPiece[][]> history = new LinkedList<>();

    private int currentPlayer;
    private ChessPiece lastMoved;

    public Chess() {
        reset();
    }

    /**
     * move allows players to move a piece. Returns true if the move is valid & successful,
     * false otherwise.
     *
     * @param f target file
     * @param r target rank
     * @param newFile target file to move to
     * @param newRank target rank to move to
     * @return 0 if the move was successful, 1 if the move wasn't successful,
     *         2 if it requires player input for pawn promotion
     */
    public int move(int r, int f, int newRank, int newFile) {
        ChessPiece target = board[r][f];
        int result = 0;

        if (target == null) {
            return 1;
        }

        if ((target.getOwner() == currentPlayer) && target.canMoveTo(newRank, newFile, board)) {
            tgt = copyBoard();
            tgt[r][f] = null;
            tgt[newRank][newFile] = target;

            // handle the history section of en passant
            if (target.getType().equals("Pawn") && currentPlayer == 0 && target.getRank() == 3 &&
                    history.getLast()[newRank - 1][newFile] instanceof Pawn &&
                    tgt[newRank + 1][newFile] instanceof Pawn &&
                    tgt[newRank - 1][newFile] == null) {
                tgt[newRank + 1][newFile] = null;
            } else if (target.getType().equals("Pawn") &&
                    currentPlayer == 1 &&  target.getRank() == 4 &&
                    history.getLast()[newRank + 1][newFile] instanceof Pawn &&
                    tgt[newRank - 1][newFile] instanceof Pawn &&
                    tgt[newRank + 1][newFile] == null) {
                tgt[newRank - 1][newFile] = null;
            }

            // check if valid piece move puts current player into check
            if (isCheck()) {
                return 1;
            }

            // handle pawn promotion
            if (target.getType().equals("Pawn") && currentPlayer == 0 && newRank == 0) {
                result = 2;
            } else if (target.getType().equals("Pawn") && currentPlayer == 1 && newRank == 7) {
                result = 2;
            }

            // toggle move if Rook / King
            if (target instanceof Rook) {
                ((Rook) target).setHasMoved(true);
            } else if (target instanceof King) {
                //handle castling
                if (f - newFile == 2) {
                    if (!((King) target).hasMoved() && tgt[newRank][0] instanceof Rook) {
                        if (!((Rook) tgt[newRank][0]).hasMoved()) {
                            tgt[newRank][3] = tgt[newRank][0];
                            tgt[newRank][0].updatePos(newRank, 0);
                            tgt[newRank][0] = null;
                        }
                    } else {
                        return 1;
                    }
                } else if (f - newFile == -2) {
                    if (!((King) target).hasMoved() && tgt[newRank][7] instanceof Rook) {
                        if (!((Rook) tgt[newRank][7]).hasMoved()) {
                            tgt[newRank][5] = tgt[newRank][7];
                            tgt[newRank][7].updatePos(newRank, 7);
                            tgt[newRank][7] = null;
                        }
                    } else {
                        return 1;
                    }
                }
                ((King) target).setHasMoved(true);
            }

            history.add(board);
            board = tgt;
            target.updatePos(newRank, newFile);

            currentPlayer = Math.abs(1 - currentPlayer);
            lastMoved = target;

            return result;
        }

        return 1;
    }

    /**
     * isCheck checks whether the target board puts the current player's king in check
     *
     * @return true if the current player is put in check, false otherwise.
     */
    public boolean isCheck() {
        King k = null;
        int targetRank = 0;
        int targetFile = 0;

        if (tgt != null) {
            for (int r = 0; r < 8; r++) {
                for (int f = 0; f < 8; f++) {
                    if (tgt[r][f] != null && tgt[r][f].getOwner() == currentPlayer) {
                        if (tgt[r][f] instanceof King) {
                            k = (King) tgt[r][f];
                            targetRank = r;
                            targetFile = f;
                        }
                    }
                }
            }

            if (k != null) {
                for (ChessPiece[] pieces : tgt) {
                    for (ChessPiece p : pieces) {
                        if (p != null && p.getOwner() != currentPlayer) {
                            if (p.canMoveTo(targetRank, targetFile, tgt)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * isStaleMate checks whether the target board ties the game.
     *
     * @return true if current player is in a stalemate, false otherwise.
     */
    public boolean isStaleMate() {
        tgt = copyBoard();
        for (int r = 0; r < 8; r++) {
            for (int f = 0; f < 8; f++) {
                if (tgt[r][f] != null && tgt[r][f].getOwner() == currentPlayer) {
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            if (tgt[r][f].canMoveTo(i, j, tgt)) {
                                ChessPiece temp = tgt[r][f];
                                tgt[r][f] = null;
                                tgt[i][j] = temp;

                                if (!isCheck()) {
                                    return false;
                                }
                                tgt = copyBoard();
                            }
                        }
                    }
                }


            }
        }

        return true;
    }

    /**
     * isCheckmate checks whether the opposing player is in Checkmate is has
     * lost.
     *
     * @return true if the opposing player is put in checkmate, false otherwise.
     */
    public boolean isCheckmate() {
        if (isStaleMate()) {
            tgt = copyBoard();
            return isCheck();
        }
        return false;
    }

    /**
     * copyBoard makes a copy of the current board for easy modification
     *
     * @return the copied 2d array of the board
     */
    private ChessPiece[][] copyBoard() {
        ChessPiece[][] target = new ChessPiece[8][8];
        for (int r = 0; r < 8; r++) {
            for (int f = 0; f < 8; f++) {
                target[r][f] = board[r][f];
            }
        }
        return target;
    }


    /**
     * printBoard is a helpful function for debugging... can now print any board you pass in!
     *
     * @param b the board you want to print
     */
    private void printBoard(ChessPiece[][] b) {
        System.out.println("\n\nTurn " + history.size() + ":\n");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (b[i][j] instanceof Pawn) {
                    System.out.print("P");
                } else if (b[i][j] instanceof Rook) {
                    System.out.print("R");
                } else if (b[i][j] instanceof Knight) {
                    System.out.print("N");
                } else if (b[i][j] instanceof Bishop) {
                    System.out.print("B");
                } else if (b[i][j] instanceof Queen) {
                    System.out.print("Q");
                } else if (b[i][j] instanceof King) {
                    System.out.print("K");
                } else {
                    System.out.print(" ");
                }
                if (j < 7) {
                    System.out.print(" | ");
                }
            }
            if (i < 7) {
                System.out.println("\n-----------------------------");
            }
        }
        System.out.println("\n\n");
    }

    /**
     * printBoard is a helpful function for debugging
     */
    private void printBoard() {
        printBoard(board);
    }

    /**
     * undo removes the latest history.
     */
    public void undo() {
        System.out.println(history.size());
        if (history.size() > 2) {
            ChessPiece[][] target = history.removeLast();
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (target[i][j] != null && ((board[i][j] == null) ||
                            (target[i][j].getOwner() != board[i][j].getOwner()) ||
                            !(target[i][j].getType().equals(board[i][j].getType())))) {
                        target[i][j].updatePos(i, j);

                        if (target[i][j] instanceof King) {
                            ((King) target[i][j]).setHasMoved(false);
                        } else if (target[i][j] instanceof Rook) {
                            ((Rook) target[i][j]).setHasMoved(false);
                        }
                    }
                }
            }

            board = target;
            currentPlayer = Math.abs(getPlayer() - 1);
        }
    }

    /**
     * reset(s) game state or starts a new game.
     */
    public void reset() {
        board = new ChessPiece[8][8];

        // pawns
        for (int i = 0; i < 8; i++) {
            board[1][i] = new Pawn(1, 1, i);
            board[6][i] = new Pawn(0, 6, i);
        }

        // rooks
        board[0][0] = new Rook(1, 0, 0);
        board[0][7] = new Rook(1, 0, 7);
        board[7][0] = new Rook(0, 7, 0);
        board[7][7] = new Rook(0, 7, 7);

        // knights
        board[0][1] = new Knight(1, 0, 1);
        board[0][6] = new Knight(1, 0, 6);
        board[7][1] = new Knight(0, 7, 1);
        board[7][6] = new Knight(0, 7, 6);

        // bishops
        board[0][2] = new Bishop(1, 0, 2);
        board[0][5] = new Bishop(1, 0, 5);
        board[7][2] = new Bishop(0, 7, 2);
        board[7][5] = new Bishop(0, 7, 5);

        // queens
        board[0][3] = new Queen(1, 0, 3);
        board[7][3] = new Queen(0, 7, 3);

        // kings
        board[0][4] = new King(1, 0, 4);
        board[7][4] = new King(0, 7, 4);

        currentPlayer = 0;
        history.add(board);
    }

    public ChessPiece getPiece(int r, int f) {
        return board[r][f];
    }

    /**
     * getPlayer returns which player is currently playing
     *
     * @return 0 if the current player is white, 1 otherwise
     */
    public int getPlayer() {
        return currentPlayer;
    }

    /**
     * promote calls the promote function on the target pawn and
     * replaces the target pawn with the new replacement Chess Piece.
     */
    public void promote(String target) {
        if (lastMoved != null && lastMoved instanceof Pawn) {
            ChessPiece replacement = ((Pawn) lastMoved).promote(target);
            board[lastMoved.getRank()][lastMoved.getFile()] = replacement;
        }
    }

    /**
     * This main method illustrates how the model is completely independent of
     * the view and controller. We can play the game from start to finish
     * without ever creating a Java Swing object.
     *
     * This is modularity in action, and modularity is the bedrock of the
     * Model-View-Controller design framework.
     *
     * Run this file to see the output of this method in your console.
     */
    public static void main(String[] args) {
        Chess c = new Chess();
        c.printBoard();

        c.move(6, 3, 4, 3);
        c.printBoard();

        c.move(0, 6, 2, 5);
        c.printBoard();

        c.move(7, 2, 3, 6);
        c.printBoard();

        c.move(1, 3, 3, 3);
        c.printBoard();

        c.move(7, 1, 5, 2);
        c.printBoard();

        c.move(1, 4, 2, 4);
        c.printBoard();

        c.move(7, 3, 6, 3);
        c.printBoard();

        c.move(0, 1, 2, 2);
        c.printBoard();

        c.move(7, 4, 7, 2);
        c.printBoard();
    }

}
