import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class GamePanel extends JPanel implements MouseListener {
    //implement runnable for thread to run

    //Size of a chess tile will be 64x64 pixel
    public final int tileSize = 48;
    final int noColumns = 24;
    final int noRows = 14;
    KeyHandler keyHandler = new KeyHandler();


    //temporary

    public double dirX, dirY;
    //Entities
    Player player = new Player(this, keyHandler);
    Enemy enemy = new Enemy(keyHandler, this, player);
    EnemyThread enemyThread = new EnemyThread(enemy, this);
    PlayerThread playerThread = new PlayerThread(player, this);
    //Constructor for the panel
    public GamePanel() {
        this.setSize(tileSize * noColumns, tileSize * noRows);
        this.setBackground(Color.black);
        this.addMouseListener(this);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }
    //Start threads
    public void startGameThread() {
        playerThread.startGameThread();
        enemyThread.startGameThread();
    }

    // Redraw the panels graphics
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        player.draw(g2);
        enemy.draw(g2);
        g2.dispose();
    }

    @Override
    public void mouseClicked(MouseEvent e) {


    }

    @Override
    public void mousePressed(MouseEvent e) {
        //System.out.println("Mouse Clicked at position" + e.getX() + "," + e.getY());
        double norm = Math.sqrt(Math.pow((e.getX() - player.x), 2) + Math.pow((e.getY() - player.y), 2));
        dirX = (e.getX() - player.x)/norm;
        dirY = (e.getY() - player.y)/norm;
        //System.out.println("Direction vector of projectile:" + (e.getX() - player.x)/norm + "," + (e.getY() - player.y) /norm);
        player.shootButtonClicked = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //System.out.println("Mouse Released at position" + e.getX() + "," + e.getY());
        player.shootButtonClicked = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
