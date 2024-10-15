public class CollisionChecker {
    GamePanel gamePanel;

    CollisionChecker(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    public void checkTile(Entity entity) {

        double entityLeftWorldX = entity.worldX + entity.hitBox.x;
        double entityTopWorldY = entity.worldY + entity.hitBox.y;
        double entityRightWorldX = entity.worldX + entity.hitBox.x + entity.hitBox.width;
        double entityBottomWorldY = entity.worldY + entity.hitBox.y + entity.hitBox.height;

        int entityLeftCol = (int) (entityLeftWorldX/gamePanel.tileSize);
        int entityRightCol = (int) (entityRightWorldX/gamePanel.tileSize);
        int entityTopRow = (int) (entityTopWorldY/gamePanel.tileSize);
        int entityBottomRow = (int) (entityBottomWorldY/gamePanel.tileSize);

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
