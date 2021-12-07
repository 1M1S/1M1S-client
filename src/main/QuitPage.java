package main;

import utils.Images;
import javax.swing.*;
import java.awt.*;

public class QuitPage extends JPanel {
    public static MainFrame mainFrame;
    public QuitPage(MainFrame mainFrame){
        this.mainFrame = mainFrame;
        setVisible(true);
        setLayout(null);
        setSize(1115, 824);
    }

    @Override
        public void paintComponent(Graphics g) {
            Dimension d = getSize();
            ImageIcon image = Images.QuitBackGround1.getImageIcon();
            g.drawImage(image.getImage(), 0, 0, d.width, d.height, null);
        }
}
