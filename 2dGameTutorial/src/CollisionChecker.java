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
                entityBottomRow = (int) ((entityBottomWorldY + entity.speed) / gamePanel.tileSize);
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
    public void checkEntity(Entity entity) {
        //TODO CHECK COLLISIONS WITH OTHER ENTITIES
        //Determine bounds of the hit box
        entityLeftWorldX = entity.worldX;
        entityTopWorldY = entity.worldY;
        entityRightWorldX = entity.worldX + gamePanel.tileSize;
        entityBottomWorldY = entity.worldY + gamePanel.tileSize;
//        //Finds coords in n x n tile map matrix
//        entityLeftCol = (int) (entityLeftWorldX/gamePanel.tileSize);
//        entityRightCol = (int) (entityRightWorldX/gamePanel.tileSize);
//        entityTopRow = (int) (entityTopWorldY/gamePanel.tileSize);
//        entityBottomRow = (int) (entityBottomWorldY/gamePanel.tileSize);
        for(Enemy e: gamePanel.enemy) {
            if(e != null) {
                if(e != entity) {
                    //GET BOUNDS OF COLLIDING ENTITY
                    double collisionLeftWorldX = e.worldX;
                    double collisionTopWorldY = e.worldY;
                    double collisionRightWorldX = e.worldX + gamePanel.tileSize;
                    double collisionBottomWorldY = e.worldY + gamePanel.tileSize;
                    //GET COORDS IN MATRIX
//                int collisionLeftCol = (int) (collisionLeftWorldX/gamePanel.tileSize);
//                int collisionBottomCol = (int) (collisionBottomWorldY/gamePanel.tileSize);
//                int collisionRightCol = (int) (collisionRightWorldX/gamePanel.tileSize);
//                int collisionTopRow = (int) (collisionTopWorldY/gamePanel.tileSize);
//
                switch(entity.direction) {
                    case "up" :
                        entityTopWorldY = entityTopWorldY - entity.speed;
                        entityBottomWorldY = entityBottomWorldY - entity.speed;
                        break;
                    case "down" :
                        entityBottomWorldY = entityBottomWorldY + entity.speed;
                        entityTopWorldY = entityTopWorldY + entity.speed;
                        break;
                    case "left" :
                        entityLeftWorldX = (entityLeftWorldX - entity.speed);
                        entityRightWorldX = entityRightWorldX - entity.speed;

                        break;
                    case "right" :
                        entityRightWorldX= (entityRightWorldX + entity.speed);
                        entityLeftWorldX = entityLeftWorldX + entity.speed;
                        break;
                }
                    if( doOverLap(collisionLeftWorldX, collisionRightWorldX, collisionTopWorldY, collisionBottomWorldY,
                            entityLeftWorldX, entityRightWorldX, entityTopWorldY, entityBottomWorldY) ) {
                        entity.collisionOn = true;
                    }
                }
            }

        }
    }

}
