import java.awt.*;

public class Enemy extends Entity {
    public Player player;
    public GamePanel gp;
    public KeyHandler keyHandler;
    public boolean isCollided = false;
    public double hitBoxRadius;

    public Enemy (KeyHandler keyHandler, GamePanel gp, Player player) {
        this.keyHandler = keyHandler;
        this.gp = gp;
        this.player = player;
        setDefaultValues();
        assignSprite();

    }
    private void setDefaultValues() {
        x = 300;
        y = 300;
        hp = 50;
        speed = 5;
        hitBoxRadius = Math.sqrt(2)/2 * gp.tileSize;
    }
    public void assignSprite() {
//        up1 = ImageIO.read(getClass().getResourceAsStream());
//        up2 = ImageIO.read(getClass().getResourceAsStream());
//        down1 = ImageIO.read(getClass().getResourceAsStream());
//        down2 = ImageIO.read(getClass().getResourceAsStream());
//        left1 = ImageIO.read(getClass().getResourceAsStream());
//        left2 = ImageIO.read(getClass().getResourceAsStream());
//        right1 = ImageIO.read(getClass().getResourceAsStream());
//        right2 = ImageIO.read(getClass().getResourceAsStream());
    }
    public void detectCollision() {
        double Distance = Math.sqrt(Math.pow(x - player.x, 2) + Math.pow(y - player.y, 2));
        if(Distance <= hitBoxRadius) {
            // could make like an imaginary circle around the player and the enemy characters and just detect
            // the collision when the circles intersect? maybe
            isCollided = true;
            if(player.FPS > 8 && !player.hasIFrames) {
                player.FPS -= 5;
                player.hasIFrames = true;
            }

            //System.out.println("Collision Detected");
        }
        else
            isCollided = false;
    }
    public void draw(Graphics2D g2) {
        g2.setColor(Color.ORANGE);
        g2.fillRect((int) x, (int) y, gp.tileSize, gp.tileSize);
    }
    public void update() {
        if(!isCollided) {
            if(x < player.x)
                x+= speed;
            else if(x > player.x)
                x-= speed;
            if(y < player.y)
                y+= speed;
            else if(y > player.y)
                y-= speed;
        } /*else {
        *
        * PAUSE FOR X SECONDS, ATK, PLAY SOME ATK ANIMATION
        *
        *}
        */
        detectCollision();
    }
}
