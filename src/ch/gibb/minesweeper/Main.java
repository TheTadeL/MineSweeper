package ch.gibb.minesweeper;

import ch.gibb.minesweeper.panels.GamePanel;
import ch.gibb.minesweeper.panels.InfoPanel;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    public static int WINDOW_WIDTH = 900;
    public static int WINDOW_HEIGHT = 600;
    public static int BOMB_COUNT = 55;
    public static int hor = 20;
    public static int ver = 15;
    private JPanel mainPanel = new JPanel(new BorderLayout());

    public static void main(String[] args){
        new Main();
    }

    private Main(){
        mainPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        mainPanel.setVisible(true);

        InfoPanel infoPanel = new InfoPanel(50, Color.BLACK);
        mainPanel.add(infoPanel, BorderLayout.NORTH);

        GamePanel gamePanel = new GamePanel(550, Color.LIGHT_GRAY);
        gamePanel.setupGame(hor, ver);
        mainPanel.add(gamePanel, BorderLayout.SOUTH);

        this.add(mainPanel);
        this.setVisible(true);
        this.pack();
    }
}
