import java.awt.*;

public class EnemyCollision extends CollisionChecker{

    public Player player;
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
                        System.out.println("HIT REGISTER " + entity.currentHP);
                    }
                } else {
                    if(!player.hasIframes) {
                        player.hasIframes = true;
                        System.out.println("PLAYER CRITICALLY HIT");
                        player.FPS -= 10;
                    }
                }
            } else {
                if(!player.hasIframes) {
                    player.hasIframes = true;
                    System.out.println("PLAYER HIT");
                    player.FPS -= 5;
                }

            }
        }
    }
}
