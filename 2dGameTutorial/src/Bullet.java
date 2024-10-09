import java.awt.*;

public class Bullet {
    public double initialX;
    public double initialY;
    public double directionX;
    public double directionY;
    public double currentX;
    public double currentY;
    public Player player;
    public int speed;

    public Bullet(double initialX, double initialY, double directionX, double directionY, Player player) {
        this.initialX = initialX - player.size/2;
        this.initialY = initialY - player.size/2;
        currentX = initialX;
        currentY = initialY;
        this.directionX = directionX;
        this.directionY = directionY;
        this.player = player;
        this.speed = 35;
    }

    public void update() {
        currentX += directionX * speed;
        currentY += directionY * speed;
    }
    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillOval((int) currentX, (int) currentY, 5,5);
        g.drawOval((int) currentX, (int) currentY, 5, 5);
    }
    void checkDistance() {
        double distance = Math.sqrt(Math.pow(currentX - initialX, 2) + Math.pow(currentY - initialY, 2));
    }
}
