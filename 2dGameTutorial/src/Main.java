import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

/**
 *  The main class for the CBL --gameDev assignment. A top down wave survival game(optional 
 * endless) incorporating algorithms such A* path finding for enemies and improved Perlin Noise  
 * generation for random map generations with a freestyled random structure generation. Also,
 * the main feature of the game is the replacing of the player health with FPS, incorporating
 * multithreading for it to work.
 * @author David
 * @author Dan
 *
 */
public class Main {
    public static void main(String[] args) {

        JFrame frame = new JFrame();
        BufferedImage icon;
        frame.setIconImage(new ImageIcon("Player/boy_idle_1").getImage());
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if (info.getName().equals("CDE/Motif")) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        GamePanel newGamePanel = new GamePanel(16, 12);;
        frame.setSize(780, 520);
        frame.setResizable(false);
        frame.setTitle("FPS survivor");
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(newGamePanel, BorderLayout.CENTER);
        newGamePanel.startGameThread();
        frame.setVisible(true);

        JFrame tutorial = new JFrame();
        tutorial.setTitle("Tutorial");
        tutorial.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        tutorial.setResizable(false);
        tutorial.setSize(400, 200);
        tutorial.setLocationRelativeTo(null);

        TutorialScreen screen = new TutorialScreen();
        screen.setVisible(true);
        tutorial.add(screen);
        tutorial.setVisible(true);

    }

}