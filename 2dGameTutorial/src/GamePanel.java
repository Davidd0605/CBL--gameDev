import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel{
    //implement runnable for thread to run

    //Size of a chess tile will be 64x64 pixel
    public final int tileSize = 64;
    final int noColumns = 8;
    final int noRows = 8;
    KeyHandler keyHandler = new KeyHandler();

    //Entities
    Player player = new Player(this, keyHandler);
    Enemy enemy = new Enemy(keyHandler, this, player);
    //Constructor for the panel
    public GamePanel() {
        this.setBounds(0, 0, 800, 450);
        this.setBackground(Color.black);

        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }
    //Start threads
    public void startGameThread() {
        PlayerThread playerThread = new PlayerThread(player, this);
        playerThread.startGameThread();

        EnemyThread enemyThread = new EnemyThread(enemy, this);
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
}
