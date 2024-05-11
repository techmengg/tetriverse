/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package csculminatingprojecttetris;

/**
 *
 * @author techmengg
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Title extends JPanel implements KeyListener {

	private static final long serialVersionUID = 1L;
	private BufferedImage instructions;
	private WindowGame window;
	private Timer timer;
	
	//The Title class has a constructor that takes a WindowGame object as a parameter.
	public Title(WindowGame window){
                instructions = ImageLoader.loadImage("/Users/techmengg/VSCode Projects/tmscs-tetris-project/images/arrow.png");
		timer = new Timer(1000/60, new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
			}
			
		});
		timer.start();
		this.window = window;
		
		
		
	}
	
        // This method is responsible for painting the components of the panel.
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
                // Set the background color to black.
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WindowGame.WIDTH, WindowGame.HEIGHT);
		
		// Draw the instructions image at the center of the panel.
		g.drawImage(instructions, WindowGame.WIDTH/2 - instructions.getWidth()/2,
				30 - instructions.getHeight()/2 + 150, null);
		
                // Set the color and font for the text.
                g.setColor(Color.WHITE);
                g.setFont(new Font("Times New Roman", Font.BOLD, 20));
                
                // Draw the "PRESS SPACE TO PLAY!" text.
		g.drawString("PRESS SPACE TO PLAY!", 115, WindowGame.HEIGHT / 2 + 100);
                
                // Set the font for the additional instructions.
                g.setFont(new Font("Times New Roman", Font.BOLD, 14));
                
                // Draw the instructions for arrow keys.
                g.drawString("Up Arrow = Rotate / Down Arrow = Speed Down", 72, WindowGame.HEIGHT / 2 + 160);
                g.drawString("Left Arrow = Move Left / Right Arrow = Move Right", 61, WindowGame.HEIGHT / 2 + 180);
                
                // Set the font for the final message.
                g.setFont(new Font("Times New Roman", Font.BOLD, 15));
                
                // Draw the final message.
                g.drawString("Good Luck. Have Fun. Stay Alive.", 115, WindowGame.HEIGHT / 2 + 260);

		
		
	}	

    @Override
    public void keyTyped(KeyEvent e) {
        // Check if the space key is pressed.
        if(e.getKeyChar() == KeyEvent.VK_SPACE) {
            // Call the startTetris method of the window to start the game.
            window.startTetris();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    // No action required for keyPressed event.
    }
    

    @Override
    public void keyReleased(KeyEvent e) {
    // No action required for keyReleased event.
    }
}
