import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

public class UI {
    GamePanel gamePanel;
    public double timeCounter = 0;
    private Font fontPixelated;

    public boolean notificationOn = false;
    public String notification = "";
    public int notificationCounter = 0;
    private final DecimalFormat df = new DecimalFormat("0.00");
    public void drawOptions(Graphics g) {
        String optionText = "OPTIONS";
        g.drawString(optionText, 10, 20);
        g.setColor(Color.black);
        g.drawRect(10, 20, 100,100);

    }
    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        try {
            InputStream is = getClass().getResourceAsStream("/PixelifySans-VariableFont_wght.ttf");
            fontPixelated = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setNotification(String notification) {
        this.notification = notification;
        notificationOn = true;
    }
    public void draw(Graphics2D g) {
        g.setFont(fontPixelated);
        g.setFont(g.getFont().deriveFont(Font.PLAIN, 20));
        g.setColor(Color.WHITE);
        if(notificationOn) {
            g.drawString(notification, 50, 200);
            notificationCounter++;
            if(notificationCounter == gamePanel.FPS * 4) {
                notificationCounter = 0;
                notificationOn = false;
            }
        }
        if(gamePanel.gameState == gamePanel.pauseState) {
            notificationOn = false;
//            int x;
//            int y;
//            String pauseText = "Game Paused";
//            g.setFont(g.getFont().deriveFont(Font.PLAIN, 50));
//            int textSize = (int) g.getFontMetrics(g.getFont()).getStringBounds(pauseText, g).getWidth();
//            x = gamePanel.getWidth()/2 - textSize / 2;
//            y = gamePanel.getHeight()/2 - 2 * gamePanel.tileSize;
//            g.setColor(Color.WHITE);
//            g.drawString(pauseText, x, y);
            drawOptions(g);



        }
        if (gamePanel.gameState == gamePanel.playState) {
            timeCounter += (double) 1 / gamePanel.FPS;
            g.drawString(gamePanel.player.FPS + " FPS", 50, 50);
            String waveText = "wave: " + gamePanel.waveNumber;
            int textSize = (int) g.getFontMetrics(g.getFont()).getStringBounds(waveText, g).getWidth();
            int waveX;
            int waveY;
            waveX = gamePanel.getWidth()/2 - textSize / 2;
            waveY = 50;
            g.drawString(waveText, waveX, waveY);

            //DRAW TIMER
            int counterX = 50;
            int counterY = 70;
            String counterText = df.format(timeCounter) + " seconds";
            if(timeCounter > 30) {
                fontPixelated = fontPixelated.deriveFont(Font.PLAIN, 40);
                counterX = waveX - (int) g.getFontMetrics(g.getFont()).getStringBounds(counterText, g).getWidth()/2;
                counterY = waveY + gamePanel.tileSize;

                g.setFont(fontPixelated);
                g.setColor(Color.RED);
            }
            g.drawString(counterText, counterX, counterY);
        }
    }


}
