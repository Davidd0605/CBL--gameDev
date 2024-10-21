import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {
    //implement runnable for thread to run

    public final int tileSize = 48;
    public final int noColumns = 24;
    public final int noRows = 10;


    //WORLD SETTINGS
    public double screenHeight;
    public double screenWidth;
    public final int maxWorldCol = 24;  //values of the miniMap number of columns and rows
    public final int maxWorldRow = 24;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;
    final int FPS = 60;
    //UI
    public UI ui = new UI(this);
    public int waveNumber = 0;

    //Object initialization
    CollisionChecker collisionChecker = new CollisionChecker(this);
    KeyHandler keyHandler = new KeyHandler(this);
    TileManager tileManager = new TileManager(this);


    //Game State
    public int gameState;
    public int titleState = -1;
    public int playState = 0;
    public int pauseState = 1;
    public int overState = 2;

    //Entities
    Player player = new Player(this, keyHandler);
    Thread gameThread;
    PlayerThread playerThread = new PlayerThread(player, this);

    //test enemy
    Enemy[] enemy = new Enemy[5];
    ArrayList<Entity> entityList = new ArrayList<>();
    //Constructor for the panel
    void checkNumberOfEnemies() {
        int no = 0;
        for(int i = 0; i < enemy.length; i ++) {
            if(enemy[i] != null && enemy[i].alive) {
                no++;
            }
        }
        if(no == 0) {
            System.out.println("No more enemies");
            waveNumber++;
            ui.timeCounter = 0;
            setEnemy();
        }
    }
    void setEnemy() {
        for(int i = 0 ; i < waveNumber; i ++) {
            enemy[i] = new Enemy(this, player);
        }
    }
    public GamePanel(int x, int y) {

        this.screenHeight = tileSize * y;
        this.screenWidth = tileSize * x;
        this.setSize((int) screenWidth, (int) screenHeight);
        this.setBackground(Color.green);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        //this.gameState = this.playState;
        this.gameState = this.titleState;

        gameThread = new Thread(this);

        setEnemy();
    }
    //Start threads
    public void startGameThread() {
        playerThread.startGameThread();
        gameThread.start();
    }
    void update(){
        if(gameState == titleState){

        }
        if(gameState == playState){
            checkNumberOfEnemies();
            for(int i = 0 ; i < waveNumber ; i ++) {
                if(enemy[i] != null) {
                    enemy[i].update();
                }
            }
        }
        if(gameState == pauseState){

        }
    }
    // Redraw the panels graphics
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if(keyHandler.OPressed){
            keyHandler.OPressed = false;
            tileManager.generatePerlin();
            for(int i =0; i< 24; i++) {
                tileManager.mapTileNum = tileManager.perlinMap;
            }
        }

        //FOR TITLE
        if(gameState == titleState) {
            ui.draw(g2);
            g2.dispose();
        }
        else {
            tileManager.draw(g2);
            for(int i = 0 ; i < waveNumber ; i ++) {
                if(enemy[i] != null) {
                    enemy[i].draw(g2);
                }
            }
            player.draw(g2);
            ui.draw(g2);
            g2.dispose();
        }

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
