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
        frame.setSize(24 * 32, 600);
        frame.setResizable(false);
        frame.setTitle("Touch grass");
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        GamePanel newGamePanel = new GamePanel();
        frame.add(newGamePanel, BorderLayout.CENTER);

        GameBar newGameBar = new GameBar(newGamePanel);
        frame.add(newGameBar, BorderLayout.SOUTH);
        newGamePanel.gameBar = newGameBar;
        newGamePanel.startGameThread();
        frame.setVisible(true);

    }


}