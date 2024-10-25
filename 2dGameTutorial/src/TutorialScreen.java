import javax.swing.*;
import javax.swing.plaf.ProgressBarUI;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class TutorialScreen extends JPanel {
    Font fontPixelated;
    public TutorialScreen() {
        try {
            InputStream is = getClass().getResourceAsStream("/PixelifySans-VariableFont_wght.ttf");
            fontPixelated = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.setFont(fontPixelated);
        g2d.setFont(g.getFont().deriveFont(Font.PLAIN, 20));
        String text = "~CONTROLS~";
        int textSize = (int) g.getFontMetrics(g.getFont()).getStringBounds(text, g).getWidth();
        g2d.drawString(text, getWidth() / 2 - textSize / 2, 20);

        text = "Movement: WASD / Arrow keys";
        textSize = (int) g.getFontMetrics(g.getFont()).getStringBounds(text, g).getWidth();
        g2d.drawString(text, getWidth() / 2 - textSize / 2, 50);
        text = "Attack: SPACE +  WASD / Arrow keys";
        textSize = (int) g.getFontMetrics(g.getFont()).getStringBounds(text, g).getWidth();
        g2d.drawString(text, getWidth() / 2 - textSize / 2, 80);

        text = "Press ESC to pause";
        textSize = (int) g.getFontMetrics(g.getFont()).getStringBounds(text, g).getWidth();
        g2d.drawString(text, getWidth() / 2 - textSize / 2, 110);
    }
}
