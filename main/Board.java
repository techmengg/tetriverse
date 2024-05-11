package main;
/**
 *
 * @author techmengg
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements KeyListener, MouseListener, MouseMotionListener {

	//Assets
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private BufferedImage pause, refresh;

	//board dimensions (the playing area)
    private final int boardHeight = 20, boardWidth = 10;

	// block size
    public static final int blockSize = 30;

	// field
    private Color[][] board = new Color[boardHeight][boardWidth];

	// array with all the possible shapes
    private Shape[] shapes = new Shape[7];

	// currentShape
    private static Shape currentShape, nextShape;

	// game loop
    private Timer looper;

    private int FPS = 144;

    private int delay = 1000 / FPS;

	// mouse events variables
    private int mouseX, mouseY;

    private boolean leftClick = false;

    private Rectangle stopBounds, refreshBounds;

    private boolean gamePaused = false;

    private boolean gameOver = false;
    
    private Color[] colors = {Color.decode("#ed1c24"), Color.decode("#ff7f27"), Color.decode("#fff200"), 
        Color.decode("#22b14c"), Color.decode("#00a2e8"), Color.decode("#a349a4"), Color.decode("#3f48cc")};
    private Random random = new Random();
	// buttons press lapse
    private Timer buttonLapse = new Timer(300, new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            buttonLapse.stop();
        }
    });

	// score
    private int score = 0;

    /* 
    sets up the initial configuration of the Tetris game board, including loading images, 
    defining shapes, and initializing necessary variables and objects.
    */
    
    public Board() {

        pause = ImageLoader.loadImage("images/Pause.png");
        refresh = ImageLoader.loadImage("images/refresh.png");

        mouseX = 0;
        mouseY = 0;

        stopBounds = new Rectangle(350, 500, pause.getWidth(), pause.getHeight() + pause.getHeight() / 2);
        refreshBounds = new Rectangle(335, 500 - refresh.getHeight() - 20, refresh.getWidth(),
                refresh.getHeight() + refresh.getHeight() / 2);

		// create game looper
        looper = new Timer(delay, new GameLooper());

		// create shapes of the tetris game
        shapes[0] = new Shape(new int[][]{
            {1, 1, 1, 1} // I shape;
        }, this, colors[0]);

        shapes[1] = new Shape(new int[][]{
            {1, 1, 1},
            {0, 1, 0}, // T shape;
        }, this, colors[1]);

        shapes[2] = new Shape(new int[][]{
            {1, 1, 1},
            {1, 0, 0}, // L shape;
        }, this, colors[2]);

        shapes[3] = new Shape(new int[][]{
            {1, 1, 1},
            {0, 0, 1}, // J shape;
        }, this, colors[3]);

        shapes[4] = new Shape(new int[][]{
            {0, 1, 1},
            {1, 1, 0}, // S shape;
        }, this, colors[4]);

        shapes[5] = new Shape(new int[][]{
            {1, 1, 0},
            {0, 1, 1}, // Z shape;
        }, this, colors[5]);

        shapes[6] = new Shape(new int[][]{
            {1, 1},
            {1, 1}, // O shape;
        }, this, colors[6]);

    }

    /* 
    Updates the game state based on mouse input and game conditions, 
    including pausing or resuming the game when the "stop" button is clicked,
    starting a new game when the "refresh" button is clicked, and updating the 
    position of the current shape unless the game is paused or over.
    */
    
    private void update() {
        if (stopBounds.contains(mouseX, mouseY) && leftClick && !buttonLapse.isRunning() && !gameOver) {
            buttonLapse.start();
            gamePaused = !gamePaused;
        }

        if (refreshBounds.contains(mouseX, mouseY) && leftClick) {
            startGame();
        }

        if (gamePaused || gameOver) {
            return;
        }
        currentShape.update();
    }

    // Overrides the paintComponent() method to visually render the game board, 
    // including filled blocks, next shape, buttons, game state text, score, and grid lines.
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {

                if (board[row][col] != null) {
                    g.setColor(board[row][col]);
                    g.fillRect(col * blockSize, row * blockSize, blockSize, blockSize);
                    
                    g.setColor(Color.BLACK);
                    g.drawRect(col * blockSize, row * blockSize, blockSize, blockSize);
                    
                }

            }
        }
        g.setColor(nextShape.getColor());
        for (int row = 0; row < nextShape.getCoords().length; row++) {
            for (int col = 0; col < nextShape.getCoords()[0].length; col++) {
                if (nextShape.getCoords()[row][col] != 0) {
                    g.fillRect(col * 30 + 320, row * 30 + 50, Board.blockSize, Board.blockSize);
                }
            }
        }
        currentShape.render(g);

        if (stopBounds.contains(mouseX, mouseY)) {
            g.setColor(Color.RED);
            g.drawImage(pause.getScaledInstance(pause.getWidth() + 3, pause.getHeight() + 3, BufferedImage.SCALE_DEFAULT), stopBounds.x + 3, stopBounds.y + 3, null);
        } else {
            g.drawImage(pause, stopBounds.x, stopBounds.y, null);
        }

        if (refreshBounds.contains(mouseX, mouseY)) {
            g.drawImage(refresh.getScaledInstance(refresh.getWidth() + 3, refresh.getHeight() + 3,
                    BufferedImage.SCALE_DEFAULT), refreshBounds.x + 3, refreshBounds.y + 3, null);
        } else {
            g.drawImage(refresh, refreshBounds.x, refreshBounds.y, null);
        }
        
        // Color, Font, and Placement of Text after being Paused, etc.

        if (gamePaused) {
            String gamePausedString = "GAME PAUSED!?";
            g.setColor(Color.WHITE);
            g.setFont(new Font("Time New Roman", Font.BOLD, 30));
            g.drawString(gamePausedString, 35, WindowGame.HEIGHT / 2);
        }
        if (gameOver) {
            String gameOverString = "GAME OVER";
            g.setColor(Color.RED);
            g.setFont(new Font("Time New Roman", Font.BOLD, 40));
            g.drawString(gameOverString, 40, WindowGame.HEIGHT / 2);
        }
        g.setColor(Color.WHITE);

        g.setFont(new Font("Time New Roman", Font.BOLD, 20));

        g.drawString("SCORE(S)", WindowGame.WIDTH - 125, WindowGame.HEIGHT / 2);
        g.drawString(score + "", WindowGame.WIDTH - 125, WindowGame.HEIGHT / 2 + 30);

        // Color of the Grid Line on Board
        
        g.setColor(Color.RED);

        for (int i = 0; i <= boardHeight; i++) {
            g.drawLine(0, i * blockSize, boardWidth * blockSize, i * blockSize);
        }
        for (int j = 0; j <= boardWidth; j++) {
            g.drawLine(j * blockSize, 0, j * blockSize, boardHeight * 30);
        }
    }

    // Sets the next shape to be played by generating a random index and color,
    // and creating a new Shape object with the corresponding coordinates and color.
    
    public void setNextShape() {
        int index = random.nextInt(shapes.length);
        int colorIndex = random.nextInt(colors.length);
        nextShape = new Shape(shapes[index].getCoords(), this, colors[colorIndex]);
    }

    // Sets the current shape to the previously set next shape, then generates a new next
    // shape. It also checks if the current shape's coordinates overlap with any filled blocks
    // on the board, indicating a game over condition.
    
    public void setCurrentShape() {
        currentShape = nextShape;
        setNextShape();

        for (int row = 0; row < currentShape.getCoords().length; row++) {
            for (int col = 0; col < currentShape.getCoords()[0].length; col++) {
                if (currentShape.getCoords()[row][col] != 0) {
                    if (board[currentShape.getY() + row][currentShape.getX() + col] != null) {
                        gameOver = true;
                    }
                }
            }
        }

    }

    public Color[][] getBoard() {
        return board;
    }

    // Overrides the keyPressed() method to handle key events.
    
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            currentShape.rotateShape();
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            currentShape.setDeltaX(1);
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            currentShape.setDeltaX(-1);
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            currentShape.speedUp();
        }
    }

    // Overrides the keyReleased() method to handle key events. 
    // If the down arrow key is released, the current shape's speed is decreased.
    
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            currentShape.speedDown();
        }
    }

    // Overrides the keyTyped() method to handle key events (no action is performed).  
    @Override
    public void keyTyped(KeyEvent e) {

    }

    // Starts the game by initializing the necessary components, setting the next shape,
    // setting the current shape, resetting the game over status, and starting the game looper.
    public void startGame() {
        stopGame();
        setNextShape();
        setCurrentShape();
        gameOver = false;
        looper.start();

    }

    // Stops the game by resetting the score, clearing the board, and stopping the game looper.
    public void stopGame() {
        score = 0;

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                board[row][col] = null;
            }
        }
        looper.stop();
    }

    // Implements the ActionListener interface to handle game loop events.
    class GameLooper implements ActionListener {

        // Overrides the actionPerformed() method to update the game state and repaint the game screen.
        @Override
        public void actionPerformed(ActionEvent e) {
            update();
            repaint();
        }

    }

    // Overides mouse__() methods
    
    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftClick = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftClick = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void addScore() {
        score++;
    }

}
