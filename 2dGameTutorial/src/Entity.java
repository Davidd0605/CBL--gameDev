import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    public double worldX;   //worldX and worldY was previously x and y. the enemy is acting weird
    public double worldY;
    public int speed;
    public double size;
    int hp;


    //animations
    public String direction = "idle";
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2, idle1, idle2;
    public int spriteCounter = 0;
    public int spriteNum = 1;

    //HITBOX
    public Rectangle hitBox;
    public boolean collisionOn = false;

}
