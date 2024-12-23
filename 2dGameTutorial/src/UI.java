import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

/**Main class for UI. It handles everything on the user interface
 * from the menu to wave counter. It also works closely with sounds in
 * the options menu.
 */
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
    public int musicVolumeInd = 25;
    public int SFXVolumeInd = 50;
    private final DecimalFormat df = new DecimalFormat("0.00");
    public int mapSize = 2;
    private String size = "Large";
    public boolean showSize = false;
    PerlinGenerator peg = new PerlinGenerator();

    /**Main constructor of the UI linking it
     * to the game panel and getting the font.
     */

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

    /**Method used to draw a window like the one
     * for options.
     */
    public void drawWindow(int x, int y, int height, int width, Graphics2D g) {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));
        g.setColor(Color.black);
        g.fillRoundRect(x, y, width, height, 35, 35);
        g.setColor(Color.white);
        g.drawRoundRect(x, y, width, height, 35, 35);
    }

    /**Method used to draw the options based on the previous method. */
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

    /**Temporary method used to test with bars that fill
     * in the UI. */
    public void barFiller(int i, int x, int y, int width, int height, Graphics2D g) {
        int currentX = x;
        for (int j = 0; j < i; j++) {
            g.fillRect(currentX, y, width, height);
            currentX += width;
        }
    }

    /**Method creating options for when the player wants to leave
     * from the pause menu.
     */
    public void confirmExit(int x, int y, int width, int height, Graphics2D g) {
        String text = "ARE YOU SURE YOU WANT TO QUIT?";
        int textSize = (int) g.getFontMetrics(g.getFont()).getStringBounds(text, g).getWidth();
        int textX = x + width / 2 - textSize / 2;
        int textY = y + gamePanel.tileSize;
        g.drawString(text, textX, textY);
        maxOptionScroll = 2;
        minOptionScroll = 0;
        text = "YES";
        textSize = (int) g.getFontMetrics(g.getFont()).getStringBounds(text, g).getWidth();
        textX = x + width / 2 - textSize / 2;
        textY += 2 * gamePanel.tileSize;
        text = optionScroll == 1 ? " > " + text : text;
        g.drawString(text, textX, textY);

        text = "NO";
        textSize = (int) g.getFontMetrics(g.getFont()).getStringBounds(text, g).getWidth();
        textX = x + width / 2 - textSize / 2;
        textY += gamePanel.tileSize;
        text = optionScroll == 2 ? " > " + text : text;
        g.drawString(text, textX, textY);
    }

    /**Method handling the sound settings in the options menu. */
    public void soundSettings(int x, int y, int width, int height, Graphics2D g) {
        String text = "Sound Settings";
        int textSize = (int) g.getFontMetrics(g.getFont()).getStringBounds(text, g).getWidth();
        int textX = x + width / 2 - textSize / 2;
        int textY = y + gamePanel.tileSize;
        g.drawString(text, textX, textY);

        maxOptionScroll = 3;
        minOptionScroll = 0;
        textX = x + gamePanel.tileSize / 2;
        //MUSIC VOLUME
        textY += gamePanel.tileSize;
        text = "ADJUST MUSIC";
        g.drawRect(x + width / 2, textY - 15, 100, 15);
        barFiller(musicVolumeInd, x + width / 2, textY - 15, 1, 15, g);
        text = optionScroll == 1 ? " > "  + text : text;
        g.drawString(text, textX, textY);
        //MUTE/UNMUTE SFX
        textY += gamePanel.tileSize;
        text = "ADJUST SFX";
        g.drawRect(x + width / 2, textY - 15, 100, 15);
        barFiller(SFXVolumeInd, x + width / 2, textY - 15, 1, 15, g);
        text = optionScroll == 2 ? " > "  + text : text;
        g.drawString(text, textX, textY);
        textY += gamePanel.tileSize;
        text = "BACK";
        text = optionScroll == 3 ? " > " + text : text;
        g.drawString(text, textX, textY);
    }

    /**Method for main options menu. */
    public void optionList(int x, int y, int width, int height, Graphics2D g) {
        String text = "Options";
        int textSize = (int) g.getFontMetrics(g.getFont()).getStringBounds(text, g).getWidth();
        int textX = x + width / 2 - textSize / 2;
        int textY = y + gamePanel.tileSize;
        g.drawString(text, textX, textY);
        maxOptionScroll = 4;
        minOptionScroll = 0;

        //MAIN MENU
        textX = x + gamePanel.tileSize / 2;
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

    /**Method used to handle notifications on the screen. */
    public void setNotification(String notification) {
        this.notification = notification;
        notificationOn = true;
    }

    /**Main draw method. Checks for notifications, state changes and so on. */

    public void draw(Graphics2D g) {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        g.setFont(fontPixelated);
        g.setFont(g.getFont().deriveFont(Font.PLAIN, 20));
        g.setColor(Color.WHITE);
        if (notificationOn) {
            g.drawString(notification, 50, 200);
            notificationCounter++;
            if (notificationCounter == gamePanel.FPS * 4) {
                notificationCounter = 0;
                notificationOn = false;
            }
        }
        if (gamePanel.gameState == gamePanel.titleState){
            notificationOn = false;
            g.setFont(g.getFont().deriveFont(Font.BOLD, 80F));
            String titleText = "Fails Per Second"; //Originally "Stupid idiot with a knife"
            int textSize = (int) g.getFontMetrics(g.getFont()).getStringBounds(titleText, g).getWidth();
            int x = gamePanel.getWidth() / 2 - textSize / 2;
            int y = 2 * gamePanel.tileSize;

            //Shadow text
            g.setColor(Color.YELLOW);
            g.drawString(titleText, x + 3, y + 3);
            //Main text
            g.setColor(Color.BLUE);
            g.drawString(titleText, x, y);

            //MENU
            g.setFont(g.getFont().deriveFont(Font.BOLD, 40F));
            int offset = gamePanel.tileSize / 2;
            titleText = "Start game";
            x = gamePanel.getWidth() / 2 - 2 * gamePanel.tileSize - 37;
            y = gamePanel.getHeight() / 2 - 2 * gamePanel.tileSize + 2 * offset;
            g.setColor(Color.WHITE);
            g.drawString(titleText, x, y);
            if (optionScroll == 0) {
                showSize = false;
                g.drawString(">", x - gamePanel.tileSize / 2 - 10, y);
            }


            titleText = "Map Size";
            x = gamePanel.getWidth() / 2 - 2 * gamePanel.tileSize;
            y = gamePanel.getHeight() / 2 - 3 * gamePanel.tileSize / 2 +  3 * offset;
            g.setColor(Color.WHITE);
            g.drawString(titleText, x, y);
            if (optionScroll == 1) {
                g.drawString(">", x - gamePanel.tileSize  / 2 - 8, y);
                if (showSize) {

                    if (mapSize == 0) {
                        size = "Small";
                        g.setColor(Color.BLUE);
                        g.setFont(g.getFont().deriveFont(Font.BOLD, 30F));
                        PerlinGenerator.mapSize = 16;
                        peg.generatePerlin();

                    } else if (mapSize == 1) {
                        size = "Medium";
                        g.setColor(Color.YELLOW);
                        g.setFont(g.getFont().deriveFont(Font.BOLD, 38F));
                        PerlinGenerator.mapSize = 24;
                        peg.generatePerlin();

                    } else if (mapSize == 2) {
                        size = "Large";
                        g.setColor(Color.RED);
                        g.setFont(g.getFont().deriveFont(Font.BOLD, 48F));
                        PerlinGenerator.mapSize = 32;
                        peg.generatePerlin();
                    } else if (mapSize > 2) {
                        mapSize = 0;
                    } else {
                        mapSize = 2;
                    }

                    g.drawString(size, x + 5 * gamePanel.tileSize, y);
                    g.setColor(Color.WHITE);
                    g.setFont(g.getFont().deriveFont(Font.BOLD, 40F));
                }

            }


            titleText = "Exit game";
            x = gamePanel.getWidth() / 2 - 2 * gamePanel.tileSize  - 15;
            y = gamePanel.getHeight() / 2 - gamePanel.tileSize + 4 * offset;
            g.setColor(Color.WHITE);
            g.drawString(titleText, x, y);
            if (optionScroll == 2) {
                showSize = false;
                g.drawString(">", x - gamePanel.tileSize / 2 - 7, y);
            }
        }
        if (gamePanel.gameState == gamePanel.pauseState) {
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
            waveX = gamePanel.getWidth() / 2 - textSize / 2;
            waveY = 50;
            g.drawString(waveText, waveX, waveY);

            String enemiesText = "Enemies left: " + gamePanel.numberOfEnemies;
            int enemiesX;
            int enemiesY;
            textSize = (int) g.getFontMetrics(g.getFont()).getStringBounds(enemiesText, g).getWidth();
            enemiesY = waveY + gamePanel.tileSize;
            enemiesX = gamePanel.getWidth() / 2 -  textSize / 2;
            g.drawString(enemiesText, enemiesX, enemiesY);

            //DRAW TIMER
            int counterX = 50;
            int counterY = 70;
            String counterText = df.format(timeCounter) + " seconds";
            if (timeCounter > 60) {
                fontPixelated = fontPixelated.deriveFont(Font.PLAIN, 40);
                counterX = waveX - (int) g.getFontMetrics(g.getFont()).getStringBounds(counterText, g).getWidth() / 2;
                counterY = waveY + 2 * gamePanel.tileSize;

                g.setFont(fontPixelated);
                g.setColor(Color.RED);
            }
            g.drawString(counterText, counterX, counterY);
        }
        if (gamePanel.gameState == gamePanel.overState) {
            maxOptionScroll = 2;
            minOptionScroll = 0;
            notificationOn = false;
            int frameX = gamePanel.tileSize + gamePanel.tileSize * 2;
            int frameY = gamePanel.tileSize;
            int frameWidth = gamePanel.tileSize * 10;
            int frameHeigth = gamePanel.tileSize * 8;
            drawWindow(frameX, frameY, frameHeigth, frameWidth, g);

            g.setFont(g.getFont().deriveFont(Font.BOLD, 40));
            String text = "Game Over";
            int textX;
            int textY;
            int textSize = (int) g.getFontMetrics(g.getFont()).getStringBounds(text, g).getWidth();
            textX = frameX + frameWidth / 2 - textSize / 2;
            textY = frameY + 2 * gamePanel.tileSize;
            g.drawString(text, textX, textY);

            g.setFont(g.getFont().deriveFont(Font.PLAIN, 20));
            text = "You have survived " + gamePanel.waveNumber + " waves";
            textY += 3 * gamePanel.tileSize / 2;
            textSize = (int) g.getFontMetrics(g.getFont()).getStringBounds(text, g).getWidth();
            textX = frameX + frameWidth / 2 - textSize / 2;
            g.drawString(text, textX, textY);

            g.setFont(g.getFont().deriveFont(Font.BOLD, 25));
            text = "TRY AGAIN?";
            textY += 3 * gamePanel.tileSize / 2;
            textSize = (int) g.getFontMetrics(g.getFont()).getStringBounds(text, g).getWidth();
            textX = frameX + frameWidth / 2 -  textSize / 2;
            g.drawString(text, textX, textY);

            textY += gamePanel.tileSize;
            g.setFont(g.getFont().deriveFont(Font.PLAIN, 20));
            textX = frameX + 4 * gamePanel.tileSize;
            text = "YES";
            text = optionScroll == 1 ? " > " + text : text;
            g.drawString(text, textX, textY);

            text = "NO";
            text = optionScroll == 2 ? " > " + text : text;
            textSize = (int) g.getFontMetrics(g.getFont()).getStringBounds(text, g).getWidth();
            textX = frameX + frameWidth - 4 * gamePanel.tileSize - textSize;
            g.drawString(text, textX, textY);
        }
        if (gamePanel.gameState == gamePanel.winState) {
            maxOptionScroll = 2;
            minOptionScroll = 0;
            notificationOn = false;
            int frameX = gamePanel.tileSize + gamePanel.tileSize * 2;
            int frameY = gamePanel.tileSize;
            int frameWidth = gamePanel.tileSize * 10;
            int frameHeigth = gamePanel.tileSize * 8;
            drawWindow(frameX, frameY, frameHeigth, frameWidth, g);


            g.setFont(g.getFont().deriveFont(Font.BOLD, 40));
            String text = "YOU WON";
            int textX;
            int textY;
            int textSize = (int) g.getFontMetrics(g.getFont()).getStringBounds(text, g).getWidth();
            textX = frameX + frameWidth / 2 - textSize / 2;
            textY = frameY + 2 * gamePanel.tileSize;
            g.drawString(text, textX, textY);

            g.setFont(g.getFont().deriveFont(Font.PLAIN, 20));
            text = "You have defeated " + (mapSize + 1) * 15 + " enemies";
            textY += 3 * gamePanel.tileSize / 2;
            textSize = (int) g.getFontMetrics(g.getFont()).getStringBounds(text, g).getWidth();
            textX = frameX + frameWidth / 2 -  textSize / 2;
            g.drawString(text, textX, textY);

            g.setFont(g.getFont().deriveFont(Font.BOLD, 25));
            text = "CONTINUE?";
            textY += 3 * gamePanel.tileSize / 2;
            textSize = (int) g.getFontMetrics(g.getFont()).getStringBounds(text, g).getWidth();
            textX = frameX + frameWidth / 2 - textSize / 2;
            g.drawString(text, textX, textY);

            textY += gamePanel.tileSize;
            g.setFont(g.getFont().deriveFont(Font.PLAIN, 20));
            textX = frameX + 4 * gamePanel.tileSize;
            text = "YES";
            text = optionScroll == 1 ? " > " + text : text;
            g.drawString(text, textX, textY);

            text = "NO";
            text = optionScroll == 2 ? " > " + text : text;
            textSize = (int) g.getFontMetrics(g.getFont()).getStringBounds(text, g).getWidth();
            textX = frameX + frameWidth - 4 * gamePanel.tileSize - textSize;
            g.drawString(text, textX, textY);
        }
    }


}
