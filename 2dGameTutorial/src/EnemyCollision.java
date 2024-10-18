import java.awt.*;

public class EnemyCollision extends CollisionChecker{

    public Player player;
    EnemyCollision(GamePanel gamePanel, Player player) {
        super(gamePanel);
        this.player = player;
    }
    public boolean doOverLap(double PLX, double PRX, double PTY, double PBY, double ELX, double ERX, double ETY, double EBY) {
        //if one rectangle is to the left of the other
        if(PTY > EBY || ETY > PBY) {
            return false;
        }
        if (PLX > ERX || ELX > PRX) {
            return false;
        }
        return true;
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
            //CHECK WHICH DIRECTION THE ATTACK IS COMING FROM AND SEE IF IT LANDS
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

        if(player.attacking && entity.playerCollision) {
            //CHECK WHICH DIRECTION THE ATTACK IS COMING FROM AND SEE IF IT LANDS
            switch(player.direction) {
                case "up":
                    if(entityTopWorldY <= playerTopWorldY) {
                        System.out.println("HIT");
                    } else
                        System.out.println("MISSED");
                    break;
                case "down":
                    if(entityBottomWorldY >= playerBottomWorldY) {
                        System.out.println("HIT");
                    }
                    else
                        System.out.println("MISSED");
                    break;
                case "left":
                    if(entityLeftWorldX <= playerLeftWorldX) {
                        System.out.println("HIT");
                    }
                    else
                        System.out.println("MISSED");
                    break;
                case "right":
                    if(entityRightWorldX >= playerRightWorldX) {
                        System.out.println("HIT");
                    }else
                        System.out.println("MISSED");
                    break;
            }
        }
    }
    public void checkEntity() {

    }
}
