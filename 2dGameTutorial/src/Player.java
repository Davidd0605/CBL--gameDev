import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyHandler;

    public final int screenX;
    public final int screenY;

    public int FPS;
    public boolean shootButtonClicked = false;
    public PlayerThread thread;
    public boolean canShoot = true;
    public boolean hasIFrames = false;
    public int iFrames = 0;
    public Player(GamePanel gp , KeyHandler keyHandler) {

        this.gp = gp;
        this.keyHandler = keyHandler;
        setDefaultValues();
        thread = gp.playerThread;
        FPS = 50;
        hp = FPS;
        hitBox = new Rectangle();
        hitBox.x = 8 ;
        hitBox.y = 16 ;
        hitBox.width = 32;
        hitBox.height = 32;

    //        screenX=(gp.tileSize* gp.noColumns/2)-(gp.tileSize/2);    //temporary comment till I find a fix
            screenX=(gp.tileSize * 8) - (gp.tileSize / 2);
            screenY=(gp.tileSize* gp.noRows/2)-(gp.tileSize/2);



    }
    public void setDefaultValues() {
        size = gp.tileSize;
        worldX = 12* gp.tileSize;
        worldY = 9* gp.tileSize;
        speed = 10;
        assignSprite();
    }

    public void assignSprite(){
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/Player/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/Player/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/Player/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/Player/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/Player/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/Player/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/Player/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/Player/boy_right_2.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void update() {

        speed = 10 * 10 / (FPS/2); // speed is i.p. to FPS
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
        if(xDirection == 0 && yDirection == 0) {
            direction = "down";
        }

        if(hasIFrames) {
            iFrames++;
            if(iFrames == 5 * FPS / 2) {
                hasIFrames = false;
                iFrames = 0;
            }
        }
        //Normalise the (xDirection, yDirection) vector, then multiply its length to be equal to speed
        double length = Math.sqrt(Math.pow(xDirection,2) + Math.pow(yDirection,2));
        xDirection = length == 0 ? 0 : xDirection / length;
        yDirection = length == 0 ? 0 : yDirection / length;

        worldX += xDirection * speed;
        worldY += yDirection * speed;

        if(keyHandler.turnFPSDown && FPS > 8) {
            this.FPS--;
            System.out.println("FPS: " + this.FPS);
        }
        if(keyHandler.turnFPSUp && FPS < 60) {
            this.FPS++;
            System.out.println("FPS: " + this.FPS);
        }
        spriteCounter++;
        if(spriteCounter > 10 * FPS / 50) {
            spriteNum = spriteNum == 2 ? 1 : 2;
            spriteCounter = 0;
        }
    }
    public void draw(Graphics2D g2) {
        BufferedImage img = up1;

        switch (direction) {
            case "left":
                if(spriteNum == 1)
                    img = left1;
                else
                    img = left2;
                break;
            case "up":
                if(spriteNum == 1)
                    img = up1;
                else
                    img = up2;
                break;
            case "right":
                if(spriteNum == 1)
                    img = right1;
                else
                    img = right2;
                break;
            case "down":
                if(spriteNum == 1)
                    img = down1;
                else
                    img = down2;
                break;
        }
        g2.drawImage(img, (int)screenX, (int)screenY, (int) size, (int) size, null);


    }
}
