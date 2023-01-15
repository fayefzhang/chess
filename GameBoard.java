package org.cis120.chess;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("serial")
public class GameBoard extends JPanel {

    private Chess c;
    private JLabel status;
    private JFrame promotion;

    // Game constants
    public static final int BOARD_WIDTH = 800;
    public static final int BOARD_HEIGHT = 800;

    private boolean pickingPiece = true;
    private int r;
    private int f;

    // game assets
    private static BufferedImage whitePawn;
    private static BufferedImage whiteRook;
    private static BufferedImage whiteKnight;
    private static BufferedImage whiteBishop;
    private static BufferedImage whiteQueen;
    private static BufferedImage whiteKing;

    private static BufferedImage blackPawn;
    private static BufferedImage blackRook;
    private static BufferedImage blackKnight;
    private static BufferedImage blackBishop;
    private static BufferedImage blackQueen;
    private static BufferedImage blackKing;

    /**
     * Initializes the game board.
     */
    public GameBoard(JLabel statusInit, JFrame promotion) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        c = new Chess(); // initializes model for the game
        status = statusInit; // initializes the status JLabel
        this.promotion = promotion; // init promotion JFrame

        /*
         * Listens for mouseclicks. Updates the model, then updates the game
         * board based off of the updated model.
         */
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Point p = e.getPoint();

                // updates the model given the coordinates of the mouseclick
                if (pickingPiece) {
                    r = p.y / 100;
                    f = p.x / 100;
                    if (c.getPiece(r, f) != null &&
                            (c.getPiece(r, f).getOwner() == c.getPlayer())) {
                        pickingPiece = false;
                    }
                } else {
                    System.out.println("\nYou tried to move: (" + r + ", " + f + ") to " +
                            "(" + (p.y / 100) + ", " + (p.x / 100) + ").");
                    int feedback = c.move(r, f, p.y / 100, p.x / 100);
                    if (feedback == 1) {
                        JOptionPane.showMessageDialog(null,
                                String.format("<html><h1>Illegal Move!</h1>" +
                                                "<p>Please make a valid move. <br>Check" +
                                                " if your king is in check, etc.",
                                        200, 300));
                    } else if (feedback == 2) {
                        promotion.setVisible(true);
                    }
                    pickingPiece = true;

                    updateStatus(); // updates the status JLabel
                    repaint(); // repaints the game board
                }
            }
        });
    }

    /**
     * (Re-)sets the game to its initial state and loads in the image files for the game.
     */
    public void reset() {
        c.reset();
        status.setText("White's Turn");

        // load in all the piece images
        try {
            if (whitePawn == null) {
                whitePawn = ImageIO.read(new File("files/wpawn.png"));
            }
            if (whiteRook == null) {
                whiteRook = ImageIO.read(new File("files/wrook.png"));
            }
            if (whiteKnight == null) {
                whiteKnight = ImageIO.read(new File("files/wknight.png"));
            }
            if (whiteBishop == null) {
                whiteBishop = ImageIO.read(new File("files/wbishop.png"));
            }
            if (whiteQueen == null) {
                whiteQueen = ImageIO.read(new File("files/wqueen.png"));
            }
            if (whiteKing == null) {
                whiteKing = ImageIO.read(new File("files/wking.png"));
            }

            if (blackPawn == null) {
                blackPawn = ImageIO.read(new File("files/bpawn.png"));
            }
            if (blackRook == null) {
                blackRook = ImageIO.read(new File("files/brook.png"));
            }
            if (blackKnight == null) {
                blackKnight = ImageIO.read(new File("files/bknight.png"));
            }
            if (blackBishop == null) {
                blackBishop = ImageIO.read(new File("files/bbishop.png"));
            }
            if (blackQueen == null) {
                blackQueen = ImageIO.read(new File("files/bqueen.png"));
            }
            if (blackKing == null) {
                blackKing = ImageIO.read(new File("files/bking.png"));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    /**
     * Responds to pawn promotion panel in RunChess.
     */
    public void promote(String target) {
        c.promote(target);
    }

    /**
     * Updates the JLabel to reflect the current state of the game.
     */
    private void updateStatus() {
        if (c.getPlayer() == 0) {
            status.setText("White's Turn");
        } else {
            status.setText("Black's Turn");
        }

        if (c.isCheck()) {
            if (c.isCheckmate()) {
                if (c.getPlayer() == 0) {
                    status.setText("Checkmate! Black wins!");
                } else {
                    status.setText("Checkmate! White wins!");
                }
            }
        } else if (c.isStaleMate()) {
            status.setText("A stalemate! A tie!");
        }
    }

    /**
     * repaints the game board.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // paint the squares
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 9; j++) {
                if ((i + j) % 2 == 1) {
                    g.setColor(new Color(140, 140, 150));
                } else {
                    g.setColor(new Color(100, 100, 120));
                }
                g.fillRect(i * 100, (j - 1) * 100, 100, 100);

            }
        }

        // paint the pieces
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (c.getPiece(j, i) != null) {
                    String state = c.getPiece(j, i).getType();

                    int x = i * 100 + 10;
                    int y = j * 100 + 10;
                    int size = 80;
                    BufferedImage target;

                    if (c.getPiece(j, i).getOwner() == 0) {
                        if (state.equals("Pawn")) {
                            target = whitePawn;
                        } else if (state.equals("Rook")) {
                            target = whiteRook;
                        } else if (state.equals("Knight")) {
                            target = whiteKnight;
                        } else if (state.equals("Bishop")) {
                            target = whiteBishop;
                        } else if (state.equals("Queen")) {
                            target = whiteQueen;
                        } else {
                            target = whiteKing;
                        }
                    } else {
                        if (state.equals("Pawn")) {
                            target = blackPawn;
                        } else if (state.equals("Rook")) {
                            target = blackRook;
                        } else if (state.equals("Knight")) {
                            target = blackKnight;
                        } else if (state.equals("Bishop")) {
                            target = blackBishop;
                        } else if (state.equals("Queen")) {
                            target = blackQueen;
                        } else {
                            target = blackKing;
                        }
                    }

                    g.drawImage(target, x, y, size, size, null);
                }
            }
        }
    }

    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }

    /**
     * Undoes the last move (if there was one).
     */
    public void undo() {
        c.undo();

        updateStatus();
        repaint();
    }
}
