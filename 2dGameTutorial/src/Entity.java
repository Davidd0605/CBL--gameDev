import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The superclass for the player and 
 * enemy class.
 */
public class Entity {
    public double x;
    public double y;
    public double worldX, worldY;
    public double screenX, screenY; 
    //Ignored checkstyle for better readablity
    public double speed;
    public double size;

    int hp;
    int currentHP;
    boolean alive = true;
    String tag;

    public boolean onPath = false;
    public boolean entityCollision = false;

    //animations
    public String direction = "idle";
    public BufferedImage dead,hitLeft, hitRight, up1, up2, down1, down2, left1, left2, right1, right2, idle1, idle2, atkUp1, atkUp2, atkDown1, atkDown2, atkLeft1, atkLeft2, atkRight1, atkRight2;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    //HITBOX
    public Rectangle hitBox;
    public Rectangle hitBoxLocation;

    public void setHitBox(int x, int y, int width, int height) {
        hitBox = new Rectangle(x, y, width, height);
    }
    
    public boolean collisionOn = false;
    //IFRAME
    public boolean hasIframes;
    public int iFrameCounter = 0;


}
