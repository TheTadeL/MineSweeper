package ch.gibb.minesweeper.panels;

import ch.gibb.minesweeper.Main;

import javax.swing.*;
import java.awt.*;

class DefaultPanel extends JPanel {
    DefaultPanel(int height, Color background){
        setBackground(background);
        setPreferredSize(new Dimension(Main.WINDOW_WIDTH, height));
    }
}
