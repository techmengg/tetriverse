/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package csculminatingprojecttetris;

/**
 *
 * @author techmengg
 */

import javax.swing.JFrame;

public class WindowGame {
    public static final int WIDTH = 445, HEIGHT = 629;

    private Board board;
    private Title title;
    private JFrame window;


/*
    Set up the main menu for the game.
    create jfram and titles for program.
    initialize window for users to see.
 */
    
    public WindowGame() {

        window = new JFrame("Culminating Project: Tetris");
        window.setSize(WIDTH, HEIGHT);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setResizable(false);

        board = new Board();
        title = new Title(this);

        window.addKeyListener(board);
        window.addKeyListener(title);

        window.add(title);

        window.setVisible(true);
    }

    // startTetris method.
    
    public void startTetris() {
        window.remove(title);
        window.addMouseMotionListener(board);
        window.addMouseListener(board);
        window.add(board);
        board.startGame();
        window.revalidate();
    }

    public static void main(String[] args) {
        new WindowGame();
    }

}
