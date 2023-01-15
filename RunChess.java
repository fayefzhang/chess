package org.cis120.chess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RunChess implements Runnable {
    public void run() {

        // instructions panel
        JOptionPane.showMessageDialog(null,
                String.format("<html><h1>Welcome to Chess!</h1>" +
                        "<p>All rules of standard chess are followed. <br>Consult google for " +
                        "a more detailed explanation on how chess is played ;)<br><br>" +
                        "<b>Some specific functionality I implemented to look out for:</b> " +
                        "<ol><li>Pawn Promotion</li><li>En Passant, Castling, etc</li>" +
                        "<li>Checkmate & Stalemate</li><li>...and more for you to find!</li>" +
                        "<h2>HOW TO PLAY:</h2> " +
                        "Use the mouse and click on which piece you want to move." +
                        "<br>Then click the square you want to move said piece to.<br><br>",
                        200, 300));

        // pop up for pawn promotion
        final JFrame promotion = new JFrame("Pawn Promotion");
        promotion.setLocation(1000, 100);
        promotion.setSize(500, 100);

        // label for promotion frame
        final JPanel promotion_label = new JPanel();
        promotion.add(promotion_label, BorderLayout.CENTER);
        final JLabel promotion_prompt = new JLabel(
                "What piece do you want to promote your pawn to?");
        promotion_label.add(promotion_prompt);

        // pop up for instructions
        final JFrame instructions = new JFrame("Instructions");
        instructions.setLocation(1000, 100);
        instructions.setSize(500, 100);

        // Top-level frame in which game components live
        final JFrame frame = new JFrame("Chess");
        frame.setLocation(300, 50);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Setting up...");
        status_panel.add(status);

        // Game board
        final GameBoard board = new GameBoard(status, promotion);
        frame.add(board, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.reset();
            }
        });
        control_panel.add(reset);

        final JButton undo = new JButton("Undo");
        undo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.undo();
            }
        });
        control_panel.add(undo);

        // pack onto frame
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // promotion buttons
        final JPanel promotion_panel = new JPanel(new FlowLayout());
        promotion.add(promotion_panel);

        final JButton queen = new JButton("Queen");
        queen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                promotion.setVisible(false);
                board.promote("Queen");
                board.repaint();
            }
        });
        promotion_panel.add(queen);

        final JButton rook = new JButton("Rook");
        rook.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                promotion.setVisible(false);
                board.promote("Rook");
                board.repaint();
            }
        });
        promotion_panel.add(rook);

        final JButton bishop = new JButton("Bishop");
        bishop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                promotion.setVisible(false);
                board.promote("Bishop");
                board.repaint();
            }
        });
        promotion_panel.add(bishop);

        final JButton knight = new JButton("Knight");
        knight.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                promotion.setVisible(false);
                board.promote("Knight");
                board.repaint();
            }
        });
        promotion_panel.add(knight);

        // pack onto promotion frame
        promotion.pack();
        promotion.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        promotion.setVisible(false);

        // reset and start over
        board.reset();
    }
}
