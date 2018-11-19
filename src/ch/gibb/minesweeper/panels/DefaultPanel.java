package ch.gibb.minesweeper.panels;

import ch.gibb.minesweeper.Main;

import javax.swing.*;
import java.awt.*;

class DefaultPanel extends JPanel {
    protected int height;
    protected int width;

    public DefaultPanel(int height, Color background){
        this.width = Main.WINDOW_WIDTH;
        this.height = height;

        setBackground(background);
        setPreferredSize(new Dimension(width, height));
    }
}
