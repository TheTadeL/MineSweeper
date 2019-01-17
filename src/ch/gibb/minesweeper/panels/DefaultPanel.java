package ch.gibb.minesweeper.panels;

import javax.swing.*;
import java.awt.*;

class DefaultPanel extends JPanel {
    DefaultPanel(int width, int height, Color background){
        setBackground(background);
        setPreferredSize(new Dimension(width, height));
    }
}
