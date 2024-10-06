import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;

public class Enemy extends Entity {
    public Player player;
    public GamePanel gp;
    public KeyHandler keyHandler;
    public boolean isCollided = false;

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
        double Distance = Math.sqrt(Math.pow(x - player.x, 2) + Math.pow(y - player.y, 2));
        if(Distance <= 60) {
            // could make like an imaginary circle around the player and the enemy characters and just detect
            // the collision when the circles intersect? maybe
            isCollided = true;
            System.out.println("Collision Detected");
        }
        else
            isCollided = false;
    }
    public void draw(Graphics2D g2) {
        g2.setColor(Color.ORANGE);
        g2.fillRect(x, y, gp.tileSize, gp.tileSize);
    }
    public void update() {
        if(!isCollided) {
            if(x < player.x)
                x+= speed;
            else if(x > player.x)
                x-= speed;
            if(y < player.y)
                y+= speed;
            else if(y > player.y)
                y-= speed;
        } /*else {
        *
        * PAUSE FOR X SECONDS, ATK, PLAY SOME ATK ANIMATION
        *
        *}
        */
        detectCollison();
    }
}
