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
        System.out.println("Determine Grid Tile" + entityLeftCol + "," + entityRightCol + " " + entityTopRow + "," + entityBottomRow);
        System.out.println(gamePanel.collisionChecker.entityLeftCol + "," + gamePanel.collisionChecker.entityRightCol + "," + gamePanel.collisionChecker.entityTopRow + "," + gamePanel.collisionChecker.entityBottomRow);
    }
}
