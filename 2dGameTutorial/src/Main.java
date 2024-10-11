
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

        //GamePanel newGamePanel = new GamePanel();   //added in an attempt to make the frame size dependent on game panel size

        //change look and feel of main JFrame
        frame.setSize(780, 520);    //originally 24 * 32 and 600
        //frame.setSize(newGamePanel.noColumns * newGamePanel.tileSize, (newGamePanel.noRows+3) * newGamePanel.tileSize);
        frame.setResizable(false);
        frame.setTitle("FPS survivor");
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        GamePanel newGamePanel = new GamePanel(frame.getWidth(), frame.getHeight());
        frame.add(newGamePanel, BorderLayout.CENTER);

        //GameBar newGameBar = new GameBar(newGamePanel);   //Game bar code
        //frame.add(newGameBar, BorderLayout.SOUTH);
        //newGamePanel.gameBar = newGameBar;
        newGamePanel.startGameThread();
        frame.setVisible(true);

    }


}