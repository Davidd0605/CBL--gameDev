import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
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
    public String lastDirection = "right";
    public boolean playerCollision = false;


    public Enemy(GamePanel gp, Player player) {
        this.gp = gp;
        this.player = player;
        initialPosition = randomPosition();
        collisionChecker = new EnemyCollision(gp, player);
        setDefaultValues();
        assignSprite();
        this.tag = "enemy";

    }
    public void checkLife() {
        if(currentHP == 0) {
            alive = false;
        }
    }
    private void setDefaultValues() {
        hp = currentHP = 5;
        behaviourState = wanderingState;
        worldX = initialPosition.x;
        worldY = initialPosition.y;
        hp = 50;
        speed = 2;
        hitBox = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
        detectionDistance = gp.tileSize * 4;
    }
    public Point randomPosition() {
        int x = new Random().nextInt(gp.tileManager.perlinMap.length);
        int y = new Random().nextInt(gp.tileManager.perlinMap.length) ;

        if(gp.tileManager.tile[gp.tileManager.mapTileNum[x][y]].collision) {
            return randomPosition();
        }
        x *=gp.tileSize;
        y *=gp.tileSize;

        //CHECK DISTANCE TO PLAYER
        double distance = Math.sqrt(Math.pow(x - gp.player.worldX, 2) + Math.pow(y - gp.player.worldY, 2));
        if(distance < (double) (3 * gp.tileSize) / 2) {
            return randomPosition();
        }
        //CHECK ENEMIES COLLIDING ON SPAWN
        for(int i = 0 ; i < 15; i ++) {
            if(gp.enemy[i] != null && gp.enemy[i] != this) {
                Enemy otherEnemy = gp.enemy[i];
                distance = Math.sqrt(Math.pow(x - otherEnemy.worldX, 2) + Math.pow(y - otherEnemy.worldY, 2));
                if(distance < 2 * gp.tileSize) {
                    return randomPosition();
                }
            }
        }
        return new Point(x, y);

    }
    public void assignSprite() {
        try {
            left1 = ImageIO.read(getClass().getResourceAsStream("Enemy/enemy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("Enemy/enemy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("Enemy/enemy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("Enemy/enemy_right_2.png"));
            dead = ImageIO.read(getClass().getResourceAsStream("Enemy/enemy_dead.png"));
            hitLeft = ImageIO.read(getClass().getResourceAsStream("Enemy/enemy_hit_left.png"));
            hitRight = ImageIO.read(getClass().getResourceAsStream("Enemy/enemy_hit_right.png"));
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
            BufferedImage image = right1;
            if(alive) {
                switch(direction) {
                    case "up","down","idle":
                        switch (lastDirection) {
                            case "right":
                                if(spriteNum == 1) {
                                    image = right1;
                                } else {
                                    image = right2;
                                }
                                break;
                            case "left":
                                if(spriteNum == 1) {
                                    image = left1;
                                } else {
                                    image = left2;
                                }
                                break;
                        }
                        break;
                    case "left":
                        if(spriteNum == 1) {
                            image = left1;
                        } else {
                            image = left2;
                        }
                        break;
                    case "right":
                        if(spriteNum == 1) {
                            image = right1;
                        } else {
                            image = right2;
                        }
                        break;
                }
                if(hasIframes)
                    if(player.worldX < worldX)
                        image = hitLeft;
                    else
                        image = hitRight;
            }
            if(!alive)
                image = dead;
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

        }
    }

    //WIP//
    public void update() {
        if(alive) {
            checkLife();
            if(hasIframes) {
                iFrameCounter ++;
                if(iFrameCounter == gp.FPS) {
                    hasIframes = false;
                    iFrameCounter = 0;
                }
            }
            behaviourState = playerProximity() ? chasingState : wanderingState;
            collisionOn = false;
            playerCollision = false;
            collisionChecker.checkPlayer(this);
            collisionChecker.checkTile(this);
            if(!collisionOn) {
                collisionChecker.checkEntity(this);
            }
            switch (behaviourState) {
                case wanderingState:
                    wander();
                    break;
                case chasingState:
                    chase();
                    break;
            }
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

        if(collisionOn) {
            switch (direction) {
                case "left":
                    direction = "right";
                    break;
                case "right":
                    direction = "left";
                    break;
                case "up" :
                    direction = "down";
                    break;
                case "down":
                    direction = "up";
                    break;
            }
        }
        spriteCounter++;
        if(spriteCounter < gp.FPS/4 || (spriteCounter >= gp.FPS / 4 * 2 && spriteCounter <= gp.FPS / 4 * 3)) {
            spriteNum = 1;
        } else {
            spriteNum = 2;
        }
        if(spriteCounter == gp.FPS) {
            spriteCounter = 0;
        }
        switch(direction) {
            case "up":
                    worldY -= speed;
                break;
            case "down":
                worldY += speed;
                break;
            case "left":
                lastDirection = direction;
                worldX -= speed;
                break;
            case "right":
                lastDirection = direction;
                worldX += speed;
                break;
        }
    }
    public void chase() {
        //TODO PATHFINDING
    }
}