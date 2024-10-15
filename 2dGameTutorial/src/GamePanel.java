import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    //implement runnable for thread to run

    public final int tileSize = 48;
    final int noColumns = 24;
    final int noRows = 10;


    //WORLD SETTINGS
    public double screenHeight;
    public double screenWidth;
    public final int maxWorldCol = 18;  //values of the miniMap number of columns and rows
    public final int maxWorldRow = 24;

    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;
    final int FPS = 60;
    //UI
    public UI ui = new UI(this);
    public int waveNumber = 1;

    //Object initialization
    CollisionChecker collisionChecker = new CollisionChecker(this);
    KeyHandler keyHandler = new KeyHandler(this);
    GameBar gameBar;
    TileManager tileManager = new TileManager(this);


    //Game State
    public int gameState;
    public int playState = 0;
    public int pauseState = 1;
    public int overState = 2;

    public double dirX, dirY;
    //Entities
    Player player = new Player(this, keyHandler);

    Thread gameThread;
    PlayerThread playerThread = new PlayerThread(player, this);

    //test enemy
    Enemy enemy = new Enemy(keyHandler, this, player);
    //Constructor for the panel

    public GamePanel(int x, int y) {
        this.screenHeight = tileSize * y;
        this.screenWidth = tileSize * x;
        this.setSize((int) screenWidth, (int) screenHeight);
        this.setBackground(Color.black);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        this.gameState = this.playState;
        gameThread = new Thread(this);
    }
    //Start threads
    public void startGameThread() {
        playerThread.startGameThread();
        gameThread.start();
    }
    void update(){
        if(gameState == playState){
            enemy.update();
        }
        if(gameState == pauseState){
            //PAUSE
        }
    }
    // Redraw the panels graphics
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        tileManager.draw(g2);
        enemy.draw(g2);
        player.draw(g2);
        ui.draw(g2);
        g2.dispose();

    }

    //Game thread
    @Override
    public void run() {
        //Game runs at constant 60 FPS
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
