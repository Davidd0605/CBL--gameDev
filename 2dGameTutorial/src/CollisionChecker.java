/**
 * A collision checker class used to detect if a tile is occupied by a "wall"
 * tile or if two entities are colliding.
 */

public class CollisionChecker {
    GamePanel gamePanel;
    //Determine bounds of the hit box
    double entityLeftWorldX;
    double entityTopWorldY;
    double entityRightWorldX;
    double entityBottomWorldY;
    //Finds coords in n x n tile map matrix
    int entityLeftCol;
    int entityRightCol;
    int entityTopRow;
    int entityBottomRow;

    CollisionChecker(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public boolean doOverLap(double PLX, double PRX, double PTY, double PBY, double ELX, double ERX, double ETY, double EBY) {
        //if one rectangle is to the left of the other
        if (PTY > EBY || ETY > PBY) {
            return false;
        }
        if (PLX > ERX || ELX > PRX) {
            return false;
        }
        return true;
    }
    /**
     * Method used to check if the next move of an entity
     * will collide with a "wall" tile.
     * 
     */

    public void checkTile(Entity entity) {
        //Determine bounds of the hit box
        entityLeftWorldX = entity.worldX + entity.hitBox.x;
        entityTopWorldY = entity.worldY + entity.hitBox.y;
        entityRightWorldX = entity.worldX + entity.hitBox.x + entity.hitBox.width;
        entityBottomWorldY = entity.worldY + entity.hitBox.y + entity.hitBox.height;
        //Finds coords in n x n tile map matrix
        entityLeftCol = (int) (entityLeftWorldX / gamePanel.tileSize);
        entityRightCol = (int) (entityRightWorldX / gamePanel.tileSize);
        entityTopRow = (int) (entityTopWorldY / gamePanel.tileSize);
        entityBottomRow = (int) (entityBottomWorldY / gamePanel.tileSize);

        int tileNum1;
        int tileNum2;

        switch (entity.direction) {
            case "up":
                entityTopRow = (int) ((entityTopWorldY - entity.speed) / gamePanel.tileSize);
                tileNum1 = gamePanel.tileManager.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityTopRow];
                if (gamePanel.tileManager.tile[tileNum1].collision 
                    || gamePanel.tileManager.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (int) ((entityBottomWorldY + entity.speed) / gamePanel.tileSize);
                tileNum1 = gamePanel.tileManager.mapTileNum[entityRightCol][entityBottomRow];
                tileNum2 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
                if (gamePanel.tileManager.tile[tileNum1].collision 
                    || gamePanel.tileManager.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (int) ((entityLeftWorldX - entity.speed) / gamePanel.tileSize);
                tileNum1 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityTopRow];
                if (gamePanel.tileManager.tile[tileNum1].collision 
                    || gamePanel.tileManager.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (int) ((entityRightWorldX + entity.speed) / gamePanel.tileSize);
                tileNum1 = gamePanel.tileManager.mapTileNum[entityRightCol][entityBottomRow];
                tileNum2 = gamePanel.tileManager.mapTileNum[entityRightCol][entityTopRow];
                if (gamePanel.tileManager.tile[tileNum1].collision 
                    || gamePanel.tileManager.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
        }
    }

    /**
     * Method mostly used to check collision between enemy entities.
     * 
     */

    public void checkEntity(Entity entity) {
        
        for (Enemy e: gamePanel.enemy) {
            if (e != null && e.alive) {
                if (e != entity) {
                    int detectionDistance = gamePanel.tileSize;
                    if (e.tag.equals("enemy") && entity.tag.equals("enemy")) {
                        detectionDistance += gamePanel.tileSize / 2;
                    }
                    double wx = entity.worldX;
                    double wy = entity.worldY;
                    double distance;
                    switch (entity.direction) {
                        case "up":
                            wy -= entity.speed;
                            break;
                        case "down":
                            wy += entity.speed;
                            break;
                        case "left":
                            wx -= entity.speed;
                            break;
                        case "right":
                            wx += entity.speed;
                            break;
                    }
                    distance = Math.sqrt(Math.pow(wx - e.worldX, 2) + Math.pow(wy - e.worldY, 2));
                    if (distance < detectionDistance) {
                        entity.collisionOn = true;
                    }
                }
            }

        }
    }

}
