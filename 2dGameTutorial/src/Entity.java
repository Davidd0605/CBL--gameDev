import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    public double x;
    public double y;
    public int speed;
    public double size;
    int hp;


    //animations
    public String direction = "idle";
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2, idle1, idle2;
    public int spriteCounter = 0;
    public int spriteNum = 1;
}
