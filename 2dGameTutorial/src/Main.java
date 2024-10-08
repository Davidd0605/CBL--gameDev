import UI.GameBar;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

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
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GamePanel newGamePanel = new GamePanel();
        frame.add(newGamePanel);
        newGamePanel.startGameThread();

        GameBar newGameBar = new GameBar();
        frame.add(newGameBar);
        newGameBar.addMouseListener(new myListener());

        frame.setVisible(true);

    }


}