import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;

public class Enemy extends Entity {
    public Player player;
    public GamePanel gp;
    public KeyHandler keyHandler;
    public Enemy (KeyHandler keyHandler, GamePanel gp, Player player) {
        this.keyHandler = keyHandler;
        this.gp = gp;
        this.player = player;
        setDefaultValues();
    }
    private void setDefaultValues() {
        x = 300;
        y = 300;
        hp = 50;
        speed = 5;
    }
    public void detectCollison() {

        //Debug
        double Distance = Math.sqrt(Math.pow(x - player.x, 2) + Math.pow(x - player.y, 2));
        System.out.println("Distance: " + Distance);
        if(Distance <= 60) {
            System.out.println("Collision Detected");
        }
    }
    public void draw(Graphics2D g2) {
        g2.setColor(Color.RED);
        g2.fillRect(x, y, gp.tileSize, gp.tileSize);
    }
    public void update() {
        if(x < player.x)
            x+= speed;
        else if(x > player.x)
            x-= speed;
        if(y < player.y)
            y+= speed;
        else if(y > player.y)
            y-= speed;
        detectCollison();
    }
}
