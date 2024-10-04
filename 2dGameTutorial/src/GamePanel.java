import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;

public class GamePanel extends JPanel implements Runnable {
    //implement runnable for thread to run

    //Size of a chess tile will be 64x64 pixel
    final int tileSize = 64;

    final int noColumns = 8;
    final int noRows = 8;
    final int panelWidth = tileSize * noColumns;
    final int panelHeight = tileSize * noRows;
    int FPS = 16;
    private int playerX = 100, playerY = 100;

    Thread gameThread;
    KeyHandler keyHandler = new KeyHandler();
    //Constructor for the panel
    public GamePanel() {
        setPreferredSize(new Dimension(panelWidth, panelHeight));
        this.setBackground(Color.black);

        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    //Run is the game loop
    @Override
    public void run() {
        //System.out.println("Game thread started");
        double drawInterval = (double) 1000000000 / FPS; //Interval in nanoseconds
        double nextDrawTime = System.nanoTime() + drawInterval; //Calculate the next sys time we are drawing at
        while(gameThread.isAlive()) {
            update();
            repaint();
            //I don't fucking know what this is like... jesus
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
        if(keyHandler.downPressed) {
            playerY += tileSize;
        }
        if(keyHandler.upPressed) {
            playerY -= tileSize;
        }
        if(keyHandler.leftPressed) {
            playerX -= tileSize;
        }
        if(keyHandler.rightPressed) {
            playerX += tileSize;
        }

    }
    //Redraw the panels components
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        //Convert graphics to 2D
        Graphics2D g2 = (Graphics2D) g;
        //Debug
        g2.setColor(Color.WHITE);
        g2.fillRect(playerX, playerY, tileSize, tileSize);
        g2.dispose();
    }
}
