import UI.GameBar;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author David
 * @author Dan
 *
 */
public class Main {
    public static void main(String[] args) {
        //Maybe we could also change the style of our frame?
        //Create a frame for our game

        JFrame frame = new JFrame();
        frame.setSize(800, 600);
        frame.setResizable(false);
        frame.setTitle("Touch grass");
        frame.setLocationRelativeTo(null);


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GameBar gameBar = new GameBar();
        gameBar.setBackground(Color.WHITE);
        gameBar.setSize(800, 300);
        frame.add(gameBar, BorderLayout.SOUTH);
        //Append the game environment
        GamePanel newGamePanel = new GamePanel();
        frame.add(newGamePanel,BorderLayout.CENTER);
        newGamePanel.startGameThread();



        frame.setVisible(true);

    }


}