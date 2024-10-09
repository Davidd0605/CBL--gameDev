import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class GamePanel extends JPanel implements MouseListener, Runnable {
    //implement runnable for thread to run

    //Size of a chess tile will be 64x64 pixel
    public final int tileSize = 48;
    final int noColumns = 24;
    final int noRows = 10;
    final int FPS = 40;
    KeyHandler keyHandler = new KeyHandler();
    ArrayList<Bullet> bullets = new ArrayList<>();
    GameBar gameBar;


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
        this.addMouseListener(this);
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
        for(Bullet bullet : bullets) {
            bullet.update();
        }
        gameBar.update();

    }
    // Redraw the panels graphics
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        player.draw(g2);
        enemy.draw(g2);
        for (Bullet bullet : bullets) {
            bullet.draw(g2);
        }
        g2.dispose();

    }

    @Override
    public void mouseClicked(MouseEvent e) {


    }

    @Override
    public void mousePressed(MouseEvent e) {
        //System.out.println("Mouse Clicked at position " + e.getX() + "," + e.getY());
        double norm = Math.sqrt(Math.pow((e.getX() - player.x), 2) + Math.pow((e.getY() - player.y), 2));
        dirX = (e.getX() - player.x)/norm;
        dirY = (e.getY() - player.y)/norm;
        //System.out.println("Direction vector of projectile: " + (e.getX() - player.x)/norm + "," + (e.getY() - player.y) /norm);
        if(player.canShoot) {
            //Instantiate new bullet
            Bullet tempBullet = new Bullet(player.x, player.y, dirX, dirY, player);
            bullets.add(tempBullet);
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }
    @Override
    public void mouseEntered(MouseEvent e) {

    }
    @Override
    public void mouseExited(MouseEvent e) {

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
