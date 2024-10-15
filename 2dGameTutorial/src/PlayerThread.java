import java.awt.*;
import java.util.ArrayList;

public class PlayerThread implements Runnable {
    Thread thread;
    Player player;
    GamePanel gamePanel;

    PlayerThread(Player player, GamePanel gamePanel) {
        this.player = player;
        this.gamePanel = gamePanel;
    }

    public void startGameThread() {
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        double drawInterval = (double) 1000000000 / player.FPS; //Interval in nanoseconds
        double nextDrawTime = System.nanoTime() + drawInterval;
        while(thread.isAlive()) {
            update();
            gamePanel.repaint();
            drawInterval = (double) 1000000000 / player.FPS;
            // try and catch works as follows: if the program runs intro any errors in the try flag, then it will cll t
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if(remainingTime < 0) {
                    remainingTime = 0;
                }
                Thread.sleep((long)remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
    //Update game data function
    public void update() {
        if(gamePanel.gameState == gamePanel.pauseState) {

        } else {
            player.update();
        }

    }
    //Redraw the panels components


}
