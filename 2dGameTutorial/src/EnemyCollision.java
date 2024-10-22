import java.awt.*;

public class EnemyCollision extends CollisionChecker{

    public Player player;
    public int comboCounter = 0;
    EnemyCollision(GamePanel gamePanel, Player player) {
        super(gamePanel);
        this.player = player;
    }
    public void checkPlayer(Enemy entity) {
        //Determine bounds of the hit box
        entityLeftWorldX = entity.worldX + entity.hitBox.x;
        entityTopWorldY = entity.worldY + entity.hitBox.y;
        entityRightWorldX = entity.worldX + gamePanel.tileSize;
        entityBottomWorldY = entity.worldY + gamePanel.tileSize;

        //Determine the bounds of the player
        double playerLeftWorldX = player.worldX;
        double playerTopWorldY = player.worldY;
        double playerRightWorldX = player.worldX + gamePanel.tileSize;
        double playerBottomWorldY = player.worldY + gamePanel.tileSize;
        if(player.attacking) {
            switch(player.direction) {
                case "up":
                    playerTopWorldY = playerTopWorldY - gamePanel.tileSize / 2;
                    break;
                case "down":
                    playerBottomWorldY = playerBottomWorldY + gamePanel.tileSize / 2;
                    break;
                case "left":
                    playerLeftWorldX = playerLeftWorldX - gamePanel.tileSize / 2;
                    break;
                case "right":
                    playerRightWorldX = playerRightWorldX + gamePanel.tileSize / 2;
                    break;
            }
        }
        entity.playerCollision = doOverLap(playerLeftWorldX, playerRightWorldX, playerTopWorldY, playerBottomWorldY,
                entityLeftWorldX, entityRightWorldX, entityTopWorldY, entityBottomWorldY);

        if(entity.playerCollision) {
            if(player.attacking) {
                boolean hit = false;
                switch(player.direction) {
                    case "up":
                        if(entityTopWorldY <= playerTopWorldY)
                            hit = true;
                        break;
                    case "down":
                        if(entityBottomWorldY >= playerBottomWorldY)
                            hit = true;
                        break;
                    case "left":
                        if(entityLeftWorldX <= playerLeftWorldX)
                            hit = true;
                        break;
                    case "right":
                        if(entityRightWorldX >= playerRightWorldX)
                            hit = true;
                        break;
                }
                if(hit) {
                    if(!entity.hasIframes) {
                        entity.hasIframes = true;
                        entity.currentHP--;
                        player.hitConnected = true;
                        if(gamePanel.ui.notificationOn) {
                            gamePanel.ui.notification = "ENEMY HAS BEEN HIT! x" + comboCounter;
                            gamePanel.ui.notificationCounter = 0;
                            comboCounter++;
                        } else {
                            gamePanel.ui.notificationOn = true;
                            gamePanel.ui.notification = "ENEMY HAS BEEN HIT!";
                            comboCounter = 2;
                        }

                    }
                } else {
                    if(!player.hasIframes) {
                        gamePanel.playSFX(4);
                        player.hasIframes = true;
                        gamePanel.ui.notificationOn = true;
                        gamePanel.ui.notification = "PLAYER HAS BEEN CRITICALLY INJURED!";
                        player.FPS -= 10;
                    }
                }
            } else {
                if(!player.hasIframes) {
                    gamePanel.playSFX(4);
                    player.hasIframes = true;
                    gamePanel.ui.notificationOn = true;
                    gamePanel.ui.notification = "PLAYER HAS BEEN HIT!";
                    player.FPS -= 5;
                }

            }
        }
    }
}
