public class CollisionChecker {
    GamePanel gamePanel;
    //Determine bounds of the hit box
    double entityLeftWorldX ;
    double entityTopWorldY ;
    double entityRightWorldX ;
    double entityBottomWorldY ;
    //Finds coords in n x n tile map matrix
    int entityLeftCol ;
    int entityRightCol ;
    int entityTopRow ;
    int entityBottomRow ;
    CollisionChecker(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    public void checkTile(Entity entity) {
        //Determine bounds of the hit box
        entityLeftWorldX = entity.worldX + entity.hitBox.x;
        entityTopWorldY = entity.worldY + entity.hitBox.y;
        entityRightWorldX = entity.worldX + entity.hitBox.x + entity.hitBox.width;
        entityBottomWorldY = entity.worldY + entity.hitBox.y + entity.hitBox.height;
        //Finds coords in n x n tile map matrix
        entityLeftCol = (int) (entityLeftWorldX/gamePanel.tileSize);
        entityRightCol = (int) (entityRightWorldX/gamePanel.tileSize);
        entityTopRow = (int) (entityTopWorldY/gamePanel.tileSize);
        entityBottomRow = (int) (entityBottomWorldY/gamePanel.tileSize);

        int tileNum1, tileNum2;

        switch(entity.direction) {
            case "up" :
                entityTopRow = (int) ((entityTopWorldY - entity.speed) / gamePanel.tileSize);
                tileNum1 = gamePanel.tileManager.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityTopRow];
                if(gamePanel.tileManager.tile[tileNum1].collision || gamePanel.tileManager.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "down" :
                entityTopRow = (int) ((entityBottomWorldY + entity.speed) / gamePanel.tileSize);
                tileNum1 = gamePanel.tileManager.mapTileNum[entityRightCol][entityBottomRow];
                tileNum2 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
                if(gamePanel.tileManager.tile[tileNum1].collision || gamePanel.tileManager.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "left" :
                entityLeftCol = (int) ((entityLeftWorldX - entity.speed) / gamePanel.tileSize);
                tileNum1 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityTopRow];
                if(gamePanel.tileManager.tile[tileNum1].collision || gamePanel.tileManager.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "right" :
                entityRightCol = (int) ((entityRightWorldX + entity.speed) / gamePanel.tileSize);
                tileNum1 = gamePanel.tileManager.mapTileNum[entityRightCol][entityBottomRow];
                tileNum2 = gamePanel.tileManager.mapTileNum[entityRightCol][entityTopRow];
                if(gamePanel.tileManager.tile[tileNum1].collision || gamePanel.tileManager.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
        }
    }

}
