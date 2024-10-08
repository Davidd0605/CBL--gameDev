import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyHandler;
    public int FPS;

    public Player(GamePanel gp , KeyHandler keyHandler) {

        this.gp = gp;
        this.keyHandler = keyHandler;
        setDefaultValues();
        FPS = 50;
        hp = FPS;
    }
    public void setDefaultValues() {
        size = gp.tileSize;
        x = 100;
        y = 100;
        speed = 10;
    }

    public void assignSprite() {
//        up1 = ImageIO.read(getClass().getResourceAsStream());
//        up2 = ImageIO.read(getClass().getResourceAsStream());
//        down1 = ImageIO.read(getClass().getResourceAsStream());
//        down2 = ImageIO.read(getClass().getResourceAsStream());
//        left1 = ImageIO.read(getClass().getResourceAsStream());
//       left2 = ImageIO.read(getClass().getResourceAsStream());
//       right1 = ImageIO.read(getClass().getResourceAsStream());
//       right2 = ImageIO.read(getClass().getResourceAsStream());
    }
    public void update() {

        speed = 50 * 10 / FPS; // speed is i.p. to FPS
        double xDirection = 0;
        double yDirection = 0;

        if(keyHandler.downPressed) {
            direction = "down";
            yDirection = 1;
        }
        if(keyHandler.upPressed) {
            direction = "up";
            yDirection = -1;
        }
        if(keyHandler.rightPressed) {
            direction = "right";
            xDirection = 1;
        }
        if(keyHandler.leftPressed) {
            direction = "left";
            xDirection = -1;
        }

        //Normalise the (xDirection, yDirection) vector, then multiply its length to be equal to speed
        double length = Math.sqrt(Math.pow(xDirection,2) + Math.pow(yDirection,2));
        xDirection = length == 0 ? 0 : xDirection / length;
        yDirection = length == 0 ? 0 : yDirection / length;

        x += xDirection * speed;
        y += yDirection * speed;

        if(keyHandler.turnFPSDown) {
            this.FPS--;
            System.out.println("FPS: " + this.FPS);
        }
        if(keyHandler.turnFPSUp) {
            this.FPS++;
            System.out.println("FPS: " + this.FPS);
        }
    }
    public void draw(Graphics2D g2) {
//        g2.setColor(Color.WHITE);
//        g2.fillRect(x, y, gp.tileSize, gp.tileSize);
//        g2.drawRect(x,y,gp.tileSize,gp.tileSize);
        BufferedImage img = null;
//
//        switch (direction) {
//            case "left":
//                img = left1;
//                break;
//            case "up":
//                img = up1;
//                break;
//            case "right":
//                img = right1;
//                break;
//            case "down":
//                img = down1;
//                break;
//        }

        //PLACEHOLDER
        try
        {
            img = ImageIO.read(getClass().getResourceAsStream("Monkey-Selfie.jpeg"));
        }
        catch ( IOException exc )
        {
            //TODO: Handle exception.
        }
        //----------------------

        g2.drawImage((Image) img, (int)x, (int)y, (int) size, (int) size, null);

    }
}
