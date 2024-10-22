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
    public int optionScroll = 0;
    public int minOptionScroll = 0;
    public int maxOptionScroll = 3;
    public int optionScreen = 0;
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
    public void drawWindow(int x, int y, int height, int width, Graphics2D g) {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));
        g.setColor(Color.black);
        g.fillRoundRect(x, y, width, height, 35, 35);
        g.setColor(Color.white);
        g.drawRoundRect(x, y, width, height, 35,35);
    }
    public void drawOptions(Graphics2D g) {

        int frameX = gamePanel.tileSize + gamePanel.tileSize * 2;
        int frameY = gamePanel.tileSize;
        int frameWidth = gamePanel.tileSize * 10;
        int frameHeigth = gamePanel.tileSize * 8;
        drawWindow(frameX, frameY, frameHeigth, frameWidth, (Graphics2D)g);

        switch (optionScreen) {
            case 0:
                optionList(frameX, frameY, frameWidth, frameHeigth, g);
                break;
            case 1:
                break;
            case 2:
                break;
        }

    }
    public void optionList(int x, int y, int width, int height, Graphics2D g) {
        String text = "Options";
        int textSize = (int) g.getFontMetrics(g.getFont()).getStringBounds(text, g).getWidth();
        int textX = x + width / 2 - textSize/2;
        int textY = y + gamePanel.tileSize;
        g.drawString(text, textX, textY);


        textX = x + gamePanel.tileSize/2;
        //MUSIC VOLUME
        textY += gamePanel.tileSize;
        text = "ADJUST VOLUME";
        g.drawRect(x + width / 2, textY - 15, 100, 15);
        text = optionScroll == 0 ? " > "  + text : text;
        g.drawString(text, textX, textY);
        //MUTE/UNMUTE SFX
        textY += gamePanel.tileSize;
        text = "MUTE SFX";
        text = optionScroll == 1 ? " > "  + text : text;
        g.drawString(text, textX, textY);
        //MAIN MENU
        textY += gamePanel.tileSize;
        text = "MAIN MENU";
        text = optionScroll == 2 ? " > " + text : text;
        g.drawString(text, textX, textY);
        
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
            if(timeCounter > 60) {
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
