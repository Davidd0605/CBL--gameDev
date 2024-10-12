
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


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
        BufferedImage icon;
        frame.setIconImage(new ImageIcon("Player/boy_idle_1").getImage());
        try {
            for(UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if(info.getName().equals("CDE/Motif")) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //change look and feel of main Jframe
        frame.setSize(16 * 48 + 48, 48 * 12 + 48);
        frame.setResizable(false);
        frame.setTitle("FPS survivor");
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        GamePanel newGamePanel = new GamePanel();
        frame.add(newGamePanel, BorderLayout.CENTER);

        GameBar newGameBar = new GameBar(newGamePanel);
//        frame.add(newGameBar, BorderLayout.SOUTH);
//        newGamePanel.gameBar = newGameBar;
        newGamePanel.startGameThread();
        frame.setVisible(true);

    }


}