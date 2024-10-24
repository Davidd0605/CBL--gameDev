import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public GamePanel gamePanel;
    public KeyHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    boolean upPressed, downPressed, leftPressed, rightPressed, turnFPSUp, turnFPSDown, atkPressed, escPressed = false, OPressed = false,JPressed = false;
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        //Title State
        if(gamePanel.gameState == gamePanel.titleState){
            if(key == KeyEvent.VK_W){
                gamePanel.ui.commandNum--;
                if(gamePanel.ui.commandNum < 0){
                    gamePanel.ui.commandNum = 2;
                }
            }
            if(key == KeyEvent.VK_S){
                gamePanel.ui.commandNum++;
                if(gamePanel.ui.commandNum > 2){
                    gamePanel.ui.commandNum = 0;
                }
            }
            if(key == KeyEvent.VK_A && gamePanel.ui.commandNum == 1 && gamePanel.ui.showSize){
                gamePanel.ui.mapSize--;
            }
            if(key == KeyEvent.VK_D && gamePanel.ui.commandNum == 1 && gamePanel.ui.showSize){
                gamePanel.ui.mapSize++;
            }
            if(key == KeyEvent.VK_ENTER){
                if(gamePanel.ui.commandNum == 0){
                    gamePanel.gameState = gamePanel.playState;
                }
                if(gamePanel.ui.commandNum == 1){
                    gamePanel.ui.showSize = !gamePanel.ui.showSize;
                }
                if(gamePanel.ui.commandNum == 2){
                    System.exit(0);
                }

            }
        }


        //Play State
        if(key == KeyEvent.VK_G){
            Main.frame.dispose();
            Main.frame = new JFrame();

            Main.frame.setIconImage(new ImageIcon("Player/boy_idle_1").getImage());
            try {
                for(UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if(info.getName().equals("CDE/Motif")) {
                        break;
                    }
                }
            } catch (Exception t) {
                t.printStackTrace();
            }
            PerlinGenerator.mapSize = 32;
            GamePanel newGamePanel = new GamePanel(16, 12);
            Main.frame.setSize(780, 520);
            Main.frame.setResizable(false);
            Main.frame.setTitle("FPS survivor");
            Main.frame.setLocationRelativeTo(null);
            Main.frame.setLayout(null);
            Main.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Main.frame.add(newGamePanel, BorderLayout.CENTER);
            newGamePanel.startGameThread();
            Main.frame.setVisible(true);


        }

        if(key == KeyEvent.VK_J){

            //Main.main(new String[0]);
            gamePanel.gameState = gamePanel.titleState;
            gamePanel.restartGame();
        }
        if(key == KeyEvent.VK_SPACE) {
            atkPressed = true;
        }
        if(key == KeyEvent.VK_R) {
            turnFPSUp = true;
        }
        if(key == KeyEvent.VK_T) {
            turnFPSDown = true;
        }
        if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
            upPressed = true;
        }
        if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
            downPressed = true;
        }
        if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
            leftPressed = true;
        }
        if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }
        if(key == KeyEvent.VK_ESCAPE) {
            escPressed = !escPressed;
            gamePanel.gameState = gamePanel.gameState == gamePanel.playState ? gamePanel.pauseState : gamePanel.playState;
        }
        if(key == KeyEvent.VK_O){
            OPressed = !OPressed;
            System.out.println("Map toggled");
            System.out.println(OPressed);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_SPACE) {
            atkPressed = false;
        }
        if(key == KeyEvent.VK_R) {
            turnFPSUp = false;
        }
        if(key == KeyEvent.VK_T) {
            turnFPSDown = false;
        }
        if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
            upPressed = false;
        }
        if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
            downPressed = false;
        }
        if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }
        if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
    }
}
