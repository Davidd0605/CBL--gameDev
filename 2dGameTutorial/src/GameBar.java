import javax.swing.*;
import java.awt.*;
public class GameBar extends JPanel {
    public JLabel label;
    public GamePanel gamePanel;
    public GameBar(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        setDefaultValues();
        label = new JLabel();
        label.setText("*THIS IS UI LOL*" + 0);
        setLayout(new BorderLayout());
        label.setFont(new Font("Arial", Font.PLAIN, 40));
        add(label);
    }
    void setDefaultValues() {
        this.setBackground( new Color(255, 255, 255));
        this.setLayout(null);
        this.setBounds(0, gamePanel.getHeight(), gamePanel.getWidth(), 100);
    }
    void update() {
        label.setText("FPS " + gamePanel.player.FPS);
    }
}
