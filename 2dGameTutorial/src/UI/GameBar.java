package UI;

import javax.swing.*;
import java.awt.*;

public class GameBar extends JPanel {


    public GameBar() {
        setDefaultValues();
        JLabel label = new JLabel();
        label.setText("*THIS IS UI LOL*");
        setLayout(new BorderLayout());
        label.setFont(new Font("Arial", Font.PLAIN, 40));
        add(label);
    }
    void setDefaultValues() {
        this.setBackground( new Color(243, 191, 161));
        this.setLayout(null);
        this.setBounds(0, 450, 800, 150);
    }
}
