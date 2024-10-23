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
    public int musicVolumeInd = 0;
    public int SFXVolumeInd = 50;
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
        drawWindow(frameX, frameY, frameHeigth, frameWidth, g);

        switch (optionScreen) {
            case 0:
                optionList(frameX, frameY, frameWidth, frameHeigth, g);
                break;
            case 1:
                soundSettings(frameX, frameY, frameWidth, frameHeigth, g);
                break;
            case 2:
                confirmExit(frameX, frameY, frameWidth, frameHeigth, g);
                break;
        }

    }
    public void barFiller(int i, int x, int y, int width, int height, Graphics2D g) {
        int currentX = x;
        for(int j = 0; j < i; j++) {
            g.fillRect(currentX, y, width, height);
            currentX += width;
        }
    }
    public void confirmExit(int x, int y, int width, int height, Graphics2D g) {
        String text = "ARE YOU SURE YOU WANT TO QUIT?";
        int textSize = (int) g.getFontMetrics(g.getFont()).getStringBounds(text, g).getWidth();
        int textX = x + width / 2 - textSize/2;
        int textY = y + gamePanel.tileSize;
        g.drawString(text, textX, textY);
        maxOptionScroll = 2;
        minOptionScroll = 0;
        text = "YES";
        textSize = (int) g.getFontMetrics(g.getFont()).getStringBounds(text, g).getWidth();
        textX = x + width / 2 - textSize/2;
        textY += 2*gamePanel.tileSize;
        text = optionScroll == 1 ? " > " + text : text;
        g.drawString(text, textX, textY);

        text = "NO";
        textSize = (int) g.getFontMetrics(g.getFont()).getStringBounds(text, g).getWidth();
        textX = x + width / 2 - textSize/2;
        textY += gamePanel.tileSize;
        text = optionScroll == 2 ? " > " + text : text;
        g.drawString(text, textX, textY);
    }
    public void soundSettings(int x, int y, int width, int height, Graphics2D g) {
        String text = "Sound Settings";
        int textSize = (int) g.getFontMetrics(g.getFont()).getStringBounds(text, g).getWidth();
        int textX = x + width / 2 - textSize/2;
        int textY = y + gamePanel.tileSize;
        g.drawString(text, textX, textY);

        maxOptionScroll = 3;
        minOptionScroll = 0;
        textX = x + gamePanel.tileSize/2;
        //MUSIC VOLUME
        textY += gamePanel.tileSize;
        text = "ADJUST MUSIC";
        g.drawRect(x + width / 2, textY - 15, 100, 15);
        barFiller(musicVolumeInd, x + width / 2, textY - 15, 1 , 15, g);
        text = optionScroll == 1 ? " > "  + text : text;
        g.drawString(text, textX, textY);
        //MUTE/UNMUTE SFX
        textY += gamePanel.tileSize;
        text = "ADJUST SFX";
        g.drawRect(x + width / 2, textY - 15, 100, 15);
        barFiller(SFXVolumeInd, x + width / 2, textY - 15, 1 , 15, g);
        text = optionScroll == 2 ? " > "  + text : text;
        g.drawString(text, textX, textY);
        textY += gamePanel.tileSize;
        text = "BACK";
        text = optionScroll == 3 ? " > " + text : text;
        g.drawString(text, textX, textY);
    }
    public void optionList(int x, int y, int width, int height, Graphics2D g) {
        String text = "Options";
        int textSize = (int) g.getFontMetrics(g.getFont()).getStringBounds(text, g).getWidth();
        int textX = x + width / 2 - textSize/2;
        int textY = y + gamePanel.tileSize;
        g.drawString(text, textX, textY);
        maxOptionScroll = 4;
        minOptionScroll = 0;

        //MAIN MENU
        textX = x + gamePanel.tileSize/2;
        textY += gamePanel.tileSize;
        text = "MAIN MENU";
        text = optionScroll == 1 ? " > " + text : text;
        g.drawString(text, textX, textY);
        //SOUND MENU
        textY += gamePanel.tileSize;
        text = "SOUND SETTINGS";
        text = optionScroll == 2 ? " > "  + text : text;
        g.drawString(text, textX, textY);
        //QUIT GAME
        textY += gamePanel.tileSize;
        text = "QUIT GAME";
        text = optionScroll == 3 ? " > " + text : text;
        g.drawString(text, textX, textY);
        //BACK BUTTON
        textY += gamePanel.tileSize;
        text = "BACK";
        text = optionScroll == 4 ? " > " + text : text;
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
