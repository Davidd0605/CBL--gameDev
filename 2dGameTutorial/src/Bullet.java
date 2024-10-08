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
        this.initialX = initialX;
        this.initialY = initialY;
        currentX = initialX;
        currentY = initialY;
        this.directionX = directionX;
        this.directionY = directionY;
        this.player = player;
        this.speed = 5;
    }
    public void update() {
        currentX += directionX * speed;
        currentY += directionY * speed;
    }
    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.drawOval((int) currentX, (int) currentY, 10, 10);
    }
}
