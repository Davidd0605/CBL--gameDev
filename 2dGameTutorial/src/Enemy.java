import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Random;

public class Enemy extends Entity {
    public Player player;
    public GamePanel gp;
    public EnemyCollision collisionChecker;
    public int behaviourState;
    public final int wanderingState = 1;
    public final int chasingState = 2;
    public int directionCounter;
    public int detectionDistance;
    public Point initialPosition;
    public boolean playerCollision = false;
    public Enemy(GamePanel gp, Player player) {
        this.gp = gp;
        this.player = player;
        initialPosition = randomPosition();
        collisionChecker = new EnemyCollision(gp, player);
        setDefaultValues();
        assignSprite();

    }

    private void setDefaultValues() {
        behaviourState = wanderingState;
        //Generate worldX worldY randomly
        worldX = initialPosition.x;
        worldY = initialPosition.y;
        hp = 50;
        speed = 2;
        hitBox = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
        detectionDistance = gp.tileSize * 4;
    }
    public Point randomPosition() {
        int worldBoundLeft = gp.tileSize;
        int worldBoundRight = gp.tileSize * gp.maxWorldCol - 3 * gp.tileSize;
        int worldBoundTop = gp.tileSize;
        int worldBoundBottom = gp.tileSize * gp.maxWorldRow - 3 * gp.tileSize;

        double randomXPercent = new Random().nextDouble(100) + 1;
        double randomYPercent = new Random().nextDouble(100) + 1;

        int x = (int)( worldBoundLeft + randomXPercent / 100 * (worldBoundRight - worldBoundLeft));
        int y = (int)( worldBoundTop + randomYPercent / 100 * (worldBoundBottom - worldBoundTop));

        return new Point(x, y);
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
    //REMAINS AS IS
    public void draw(Graphics2D g2) {
        //Complex equation I stole from the tile map draw thing
        int screenX = (int) ((worldX - gp.player.worldX) + gp.player.screenX);  //the tutorial did not need the (int)
        int screenY = (int) ((worldY - gp.player.worldY) + gp.player.screenY);  //it's +player.screen in order to offset the correct coordinate for the tile since the player is in the middle of the screen
        //only render if on screen
        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
                && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
            g2.drawImage(up1, screenX, screenY, gp.tileSize, gp.tileSize, null);

        }
    }

    //WIP//
    public void update() {
        behaviourState = playerProximity() ? chasingState : wanderingState;
        collisionOn = false;
        playerCollision = false;
        collisionChecker.checkPlayer(this);
        collisionChecker.checkTile(this);
        switch (behaviourState) {
            case wanderingState:
                wander();
                break;
            case chasingState:
                chase();
                break;
        }
    }
    private boolean playerProximity() {
        double distance = Math.sqrt(Math.pow(worldX - gp.player.worldX, 2) + Math.pow(worldY - gp.player.worldY, 2));
        if (distance < detectionDistance) {
            return true;
        }
        return false;
    }
    private void wander() {
        //SWITCH DIRECTION EVERY FRAME MAYBE IDK
        directionCounter++;
        if(directionCounter == gp.FPS) {
            int i = new Random().nextInt(5) + 1;
            switch(i) {
                case 1:
                    direction = "up";
                    break;
                case 2:
                    direction = "down";
                    break;
                case 3:
                    direction = "left";
                    break;
                case 4:
                    direction = "right";
                    break;
                default:
                    direction = "idle";
                    break;
            }
        }
        directionCounter %= gp.FPS; //reset every FPS frames so essentially once per second

        if(collisionOn || playerCollision) {
            direction = "idle";
        }
        switch(direction) {
            case "up":
                    worldY -= speed;
                break;
            case "down":
                worldY += speed;
                break;
            case "left":
                worldX -= speed;
                break;
            case "right":
                worldX += speed;
                break;
        }
    }
    public void chase() {

        //TODO PATHFINDING
    }
}