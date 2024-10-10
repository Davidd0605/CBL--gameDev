import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    //implement runnable for thread to run

    //Size of a chess tile will be 64x64 pixel
    public final int tileSize = 48;
    final int noColumns = 24;
    final int noRows = 10;
    final int FPS = 40;
    KeyHandler keyHandler = new KeyHandler();
    GameBar gameBar;
    TileManager tileManager = new TileManager(this);


    //temporary

    public double dirX, dirY;
    //Entities
    Player player = new Player(this, keyHandler);
    Enemy enemy = new Enemy(keyHandler, this, player);

    Thread gameThread;
    PlayerThread playerThread = new PlayerThread(player, this);
    //Constructor for the panel
    public GamePanel() {
        this.setSize(tileSize * noColumns, tileSize * noRows);
        this.setBackground(Color.black);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        gameThread = new Thread(this);
    }
    //Start threads
    public void startGameThread() {
        playerThread.startGameThread();
        gameThread.start();
    }
    void update() {
        enemy.update();
        gameBar.update();

    }
    // Redraw the panels graphics
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        tileManager.draw(g2);
        player.draw(g2);
        enemy.draw(g2);
        g2.dispose();

    }

    //Game thread
    @Override
    public void run() {
        double drawInterval = (double) 1000000000 / FPS; //Interval in nanoseconds
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(gameThread.isAlive()) {
            update();
            repaint();

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

}
