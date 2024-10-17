import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

public class UI {
    GamePanel gamePanel;
    public double timeCounter = 0;
    private Font fontPixelated;
    private final DecimalFormat df = new DecimalFormat("0.00");

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

    public void draw(Graphics2D g) {
        g.setFont(fontPixelated);
        g.setFont(g.getFont().deriveFont(Font.PLAIN, 20));
        g.setColor(Color.WHITE);
        if(gamePanel.gameState == gamePanel.pauseState) {
            int x;
            int y;
            String pauseText = "Game Paused";
            g.setFont(g.getFont().deriveFont(Font.PLAIN, 50));
            int textSize = (int) g.getFontMetrics(g.getFont()).getStringBounds(pauseText, g).getWidth();
            x = gamePanel.getWidth()/2 - textSize / 2;
            y = gamePanel.getHeight()/2 - 2 * gamePanel.tileSize;
            g.setColor(Color.BLACK);
            Rectangle rect = new Rectangle(x, y, gamePanel.tileSize, gamePanel.tileSize);
            g.drawRect(x, y - gamePanel.tileSize * 3, textSize, textSize);
            g.fillRect(x, y - gamePanel.tileSize * 3, textSize, textSize);
            g.setColor(Color.WHITE);
            g.drawString(pauseText, x, y);
        }
        if (gamePanel.gameState == gamePanel.playState) {
            timeCounter += (double) 1 / gamePanel.FPS;
            g.drawString(gamePanel.player.FPS + " FPS", 50, 50);
            g.drawString(df.format(timeCounter) + " seconds", 50, 70);
            String waveText = "wave: " + gamePanel.waveNumber;
            int textSize = (int) g.getFontMetrics(g.getFont()).getStringBounds(waveText, g).getWidth();
            int x;
            int y;
            x = gamePanel.getWidth()/2 - textSize / 2;
            y = 50;
            g.drawString(waveText, x, y);
        }

    }
    // DISPLAY WAVE


}
