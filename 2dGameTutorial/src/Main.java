
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
        JFrame frame = new JFrame();
        BufferedImage icon;
        frame.setIconImage(new ImageIcon("Player/boy_idle_1").getImage());
        try {
            for(UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if(info.getName().equals("CDE/Motif")) {
                        break;
                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        GamePanel newGamePanel = new GamePanel(16, 12);
        frame.setSize(780, 520);
        frame.setResizable(false);
        frame.setTitle("FPS survivor");
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(newGamePanel, BorderLayout.CENTER);
        newGamePanel.startGameThread();
        frame.setVisible(true);

    }


}