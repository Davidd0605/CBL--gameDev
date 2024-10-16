import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Enemy extends Entity {
    public Player player;
    public GamePanel gp;
    public KeyHandler keyHandler;
    public boolean isCollided = false;
    public double hitBoxRadius;
    public boolean dir = true;
    public EnemyCollision collisionChecker;

    public Enemy (KeyHandler keyHandler, GamePanel gp, Player player) {
        this.keyHandler = keyHandler;
        this.gp = gp;
        this.player = player;
        collisionChecker = new EnemyCollision(gp, player, this);
        setDefaultValues();
        assignSprite();

    }
    private void setDefaultValues() {
        worldX = 300;
        worldY   = 300;
        hp = 50;
        speed = 4;
        hitBox = new Rectangle(0,0,gp.tileSize,gp.tileSize );
    }
    public void assignSprite() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("image-119.jpg"));
            up2 = ImageIO.read(getClass().getResourceAsStream("image-119.jpg"));
            down1 = ImageIO.read(getClass().getResourceAsStream("image-119.jpg"));
            down2 = ImageIO.read(getClass().getResourceAsStream("image-119.jpg"));
            left1 = ImageIO.read(getClass().getResourceAsStream("image-119.jpg"));
            left2 = ImageIO.read(getClass().getResourceAsStream("image-119.jpg"));
            right1 = ImageIO.read(getClass().getResourceAsStream("image-119.jpg"));
            right2 = ImageIO.read(getClass().getResourceAsStream("image-119.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void detectCollision() {
        double Distance = Math.sqrt(Math.pow(worldX - player.worldX, 2) + Math.pow(worldY - player.worldY, 2));
        System.out.println(Distance);
        if(Distance <= gp.tileSize) {
            isCollided = true;
            if(player.FPS > 8 && !player.hasIFrames) {
                player.FPS -= 5;
                player.hasIFrames = true;
            }
        }
        else
            isCollided = false;
    }
    public void draw(Graphics2D g2) {
        //Complex equation I stole from the tile map draw thing
        int screenX = (int) ((worldX - gp.player.worldX) + gp.player.screenX);  //the tutorial did not need the (int)
        int screenY = (int) ((worldY - gp.player.worldY) + gp.player.screenY);  //it's +player.screen in order to offset the correct coordinate for the tile since the player is in the middle of the screen
        //only render if on screen
        if(worldX +gp.tileSize > gp.player.worldX - gp.player.screenX && worldX - gp.tileSize< gp.player.worldX + gp.player.screenX
                && worldY + gp.tileSize> gp.player.worldY - gp.player.screenY && worldY -gp.tileSize< gp.player.worldY + gp.player.screenY){
            g2.drawImage(up1, screenX, screenY, gp.tileSize, gp.tileSize, null);

        }
    }
    public void update() {
        collisionOn = false;
        collisionChecker.checkTile(this);
        collisionChecker.determineGridTile();
        if(!collisionOn) {
            if(dir) {
                direction = "down";
                worldY += speed;
            }
            else {
                direction = "up";
                worldY -= speed;
            }
        }
        if(collisionOn) {
            dir = !dir;
        }
            if(dir) {
                direction = "down";
                worldY += speed;
            }
            else {
                direction = "up";
                worldY -= speed;
            }
            //detectCollision();
        }

    }
