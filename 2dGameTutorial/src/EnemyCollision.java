public class EnemyCollision extends CollisionChecker{

    public Player player;
    public Enemy enemy;
    EnemyCollision(GamePanel gamePanel, Player player, Enemy enemy) {
        super(gamePanel);
        this.enemy = enemy;
        this.player = player;
    }
    public void determineGridTile() {
        //TEMPORARY DEBUG
        int enemyX = (entityLeftCol+ entityRightCol)/2;
        int enemyY = (entityTopRow + entityBottomRow)/2;
        System.out.println(enemyX + ", " + enemyY);
        int playerX = (gamePanel.collisionChecker.entityLeftCol + gamePanel.collisionChecker.entityRightCol) / 2;
        int playerY =(gamePanel.collisionChecker.entityTopRow + gamePanel.collisionChecker.entityBottomRow)/ 2;
        System.out.println(playerX + "," + playerY);
    }
}
