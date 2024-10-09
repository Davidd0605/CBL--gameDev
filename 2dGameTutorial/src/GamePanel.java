import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {
    //implement runnable for thread to run
    public final int originalTileSize = 16;
    final int scale =3;


    //Size of a chess tile will be 64x64 pixel
    public final int tileSize = originalTileSize * scale;

    final int noColumns = 8;
    final int noRows = 8;
    final int panelWidth = tileSize * noColumns;
    final int panelHeight = tileSize * noRows;
    public int frameClock = 0;

    TileManager tileM = new TileManager(this);
    Thread gameThread;
    Thread enemyThread;
    KeyHandler keyHandler = new KeyHandler();

    //Entities
    Player player = new Player(this, keyHandler);
    Enemy enemy = new Enemy(keyHandler, this, player);

    ArrayList<Enemy> enemies = new ArrayList<>();
    //Constructor for the panel
    public GamePanel() {
        this.setBounds(0, 0, 800, 450);
        this.setBackground(Color.black);

        //For key input
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }
    public void startEnemyThread() {
        enemyThread = new Thread(this);

    }
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    // Create another thread for the mobs
    //Run is the game loop
    @Override
    public void run() {

        //System.out.println("Game thread started");
        double drawInterval = (double) 1000000000 / player.FPS; //Interval in nanoseconds
        double nextDrawTime = System.nanoTime() + drawInterval; //Calculate the next sys time we are drawing at
        while(gameThread.isAlive()) {
            //the part where we actually do stuff
            //----------------------
            frameClock++;
            frameClock %= 3;
            update();
            repaint();
            //----------------------

            //THIS U IGNORE
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
        player.update();
        enemy.update();
    }
    //Redraw the panels components
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2);

        player.draw(g2);

        enemy.draw(g2);


        g2.dispose();
    }
}
