import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    //implement runnable for thread to run

    //Size of a chess tile will be 64x64 pixel
    public final int tileSize = 64;

    final int noColumns = 8;
    final int noRows = 8;
    final int panelWidth = tileSize * noColumns;
    final int panelHeight = tileSize * noRows;

    public int FPS = 8;
    Thread gameThread;
    KeyHandler keyHandler = new KeyHandler();
    Player player = new Player(this, keyHandler);
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
            //I don't fucking know what this is like... Jesus
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
        player.update();
    }
    //Redraw the panels components
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        player.draw(g2);

        g2.dispose();
    }
}
