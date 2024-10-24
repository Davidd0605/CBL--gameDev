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
    public int commandNum = 0;
    public int mapSize = 0;
    private String size = "Small";
    public boolean showSize = false;
    PerlinGenerator peg = new PerlinGenerator();

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
        System.out.println("Reached here");
        if(gamePanel.gameState == gamePanel.titleState){

            g.setFont(g.getFont().deriveFont(Font.BOLD, 48F));
            //System.out.println("Title Screen Rendered");
            String titleText = "Stupid idiot with a knife";
            int textSize = (int) g.getFontMetrics(g.getFont()).getStringBounds(titleText, g).getWidth();
            int x = gamePanel.getWidth()/2 - textSize / 2;;
            int y = 2 * gamePanel.tileSize;

            //Shadow text
            g.setColor(Color.YELLOW);
            g.drawString(titleText, x+3, y+3);

            //Main text
            g.setColor(Color.BLUE);
            g.drawString(titleText, x, y);

            //MENU
            g.setFont(g.getFont().deriveFont(Font.BOLD, 24F));

            int offset = gamePanel.tileSize / 2;
            titleText="Start game";
            x=gamePanel.getWidth()/2-2*gamePanel.tileSize;
            y=gamePanel.getHeight()/2-2*gamePanel.tileSize + 2*offset;
            g.setColor(Color.WHITE);
            g.drawString(titleText, x, y);
            if(commandNum == 0) {
                showSize = false;
                g.drawString(">", x- gamePanel.tileSize/2, y);
            }


            titleText="Map Size";
            x=gamePanel.getWidth()/2-2*gamePanel.tileSize+25;
            y=gamePanel.getHeight()/2-3*gamePanel.tileSize/2 + 2*offset;
            g.setColor(Color.WHITE);
            g.drawString(titleText, x, y);
            if(commandNum == 1) {
                g.drawString(">", x- gamePanel.tileSize/2, y);
                if(showSize) {

                    if(mapSize == 0){
                        size = "Small";
                        PerlinGenerator.mapSize = 16;
                        peg.generatePerlin();

                    }
                    else if(mapSize == 1){
                        size = "Medium";
                        PerlinGenerator.mapSize = 24;
                        peg.generatePerlin();

                    }
                    else if(mapSize == 2){
                        size = "Large";
                        PerlinGenerator.mapSize = 32;
                        peg.generatePerlin();
                    }
                    else if(mapSize > 2){
                        mapSize = 0;
                    }
                    else {
                        mapSize = 2;
                    }

                    g.drawString(size, x + 3* gamePanel.tileSize, y );
                }

            }


            titleText="Exit game";
            x=gamePanel.getWidth()/2-2*gamePanel.tileSize+15;
            y=gamePanel.getHeight()/2-gamePanel.tileSize + 2*offset;
            g.setColor(Color.WHITE);
            g.drawString(titleText, x, y);
            if(commandNum == 2) {
                showSize = false;
                g.drawString(">", x- gamePanel.tileSize/2, y);

            }
        }
        if(gamePanel.gameState == gamePanel.pauseState) {
            notificationOn = false;
            int x;
            int y;
            String pauseText = "Game Paused";
            g.setFont(g.getFont().deriveFont(Font.PLAIN, 50));
            int textSize = (int) g.getFontMetrics(g.getFont()).getStringBounds(pauseText, g).getWidth();
            x = gamePanel.getWidth()/2 - textSize / 2;
            y = gamePanel.getHeight()/2 - 2 * gamePanel.tileSize;
            g.setColor(Color.WHITE);
            g.drawString(pauseText, x, y);

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
            //System.out.println("fssfsfsf");
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
