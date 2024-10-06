import java.awt.*;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyHandler;
    public int FPS;

    public Player(GamePanel gp , KeyHandler keyHandler) {
        this.gp = gp;
        this.keyHandler = keyHandler;
        setDefaultValues();
        FPS = 24;
        hp = FPS;
    }
    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 10;

    }
    public void update() {
        if(keyHandler.downPressed) {
            y += speed;
        }
        if(keyHandler.upPressed) {
            y-= speed;
        }
        if(keyHandler.leftPressed) {
            x -= speed;
        }
        if(keyHandler.rightPressed) {
            x += speed;
        }
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
        g2.setColor(Color.WHITE);
        g2.fillRect(x, y, gp.tileSize, gp.tileSize);
    }
}
