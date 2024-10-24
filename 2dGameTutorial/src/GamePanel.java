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
//    public final int worldWidth = tileSize * maxWorldCol;
//    public final int worldHeight = tileSize * maxWorldRow;
    public static int generatedSize = 0;
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
    public int winState = 3;

    //Entities
    Player player = new Player(this, keyHandler);
    Thread gameThread;
    PlayerThread playerThread = new PlayerThread(player, this);

    //test enemy
    Enemy[] enemy = new Enemy[15];
    public int numberOfEnemies = 0;
    //Sound
    SoundManager SFX = new SoundManager();
    SoundManager music = new SoundManager();

    void checkNumberOfEnemies() {
        int no = 0;
        for(int i = 0; i < enemy.length; i ++) {
            if(enemy[i] != null && enemy[i].alive) {
                no++;
            }
        }
        numberOfEnemies = no;
        if(no == 0) {
                if(waveNumber == 5) {
                    gameState = winState;
                }
                waveNumber++;
                ui.timeCounter = 0;
                setEnemy();
        }
    }
    void setEnemy() {
        int numberOfEnemies = Math.min(waveNumber + waveNumber * ui.mapSize, (ui.mapSize + 1) * 5);
        for(int i = 0 ; i < numberOfEnemies; i ++) {
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
    //Restart game
    public void restartGame(){

        PerlinGenerator.mapSize = 32;
        gameState = this.titleState;
        ui.mapSize = 2;
        player.FPS = 50;

        waveNumber = 0;
        for(Enemy e : enemy) {
            if(e != null)
                e.alive = false;
        }
        ui.timeCounter = 0;
        player.setDefaultValues();
        tileManager.generatePerlin();
        tileManager.mapTileNum = PerlinGenerator.perlinMap;
        setEnemy();
        //Alternatively you can just make the waveNumber = 1. Leaving it like this to avoid fewer possible problems
        //didn't really touch threads. A superficial restart

    }
    //Start threads
    public void startGameThread() {
        //START LOOP OF BACKGROUND GAME MUSIC
        playMusic(3);
        playerThread.startGameThread();
        gameThread.start();
    }
    void update(){
        music.volumeInd = ui.musicVolumeInd;
        music.checkVolume();
        if(gameState == titleState){

        }
        if(gameState == playState){
            if(player.FPS == 8 || ui.timeCounter >= 90) {
                gameState = overState;
            }
            if(!player.canAttack) {
                player.attackCooldown++;
                if(player.attackCooldown == FPS) {
                    player.canAttack = true;
                    player.attackCooldown = 0;
                }
            }
            checkNumberOfEnemies();
            int numberOfEnemies = Math.min(waveNumber + waveNumber * ui.mapSize, (ui.mapSize + 1) * 5);
            for(int i = 0 ; i < numberOfEnemies ; i ++) {
                if(enemy[i] != null) {
                    enemy[i].update();
                }
            }
        }
        if(gameState == pauseState){

        }
        if(gameState == overState){

        }
    }
    // Redraw the panels graphics
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if(keyHandler.OPressed){
            keyHandler.OPressed = false;
            tileManager.generatePerlin();
            for(int i = 0; i< PerlinGenerator.mapSize; i++) {
                tileManager.mapTileNum = PerlinGenerator.perlinMap;
            }
        }

        //FOR TITLE
        if(gameState == titleState) {
            this.setBackground(Color.BLACK);
            ui.draw(g2);
            g2.dispose();
        }
        else {
            this.setBackground(Color.GREEN);

            tileManager.draw(g2);
            int numberOfEnemies = Math.min(waveNumber + waveNumber * ui.mapSize, (ui.mapSize + 1) * 5);
            for(int i = 0 ; i < numberOfEnemies ; i ++) {
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
    void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }
    void stopMusic() {
        music.stop();
    }
    void playSFX(int i) {
        SFX.setFile(i);
        SFX.play();
    }

}
