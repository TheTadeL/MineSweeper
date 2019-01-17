package ch.gibb.minesweeper;

import ch.gibb.minesweeper.panels.GamePanel;
import ch.gibb.minesweeper.panels.InfoPanel;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    private final static int BOMB_COUNT = 60;
    private final static int WINDOW_WIDTH = 900;
    private final static int WINDOW_HEIGHT = 600;
    private final static int HOR = 20;
    private final static int VER = 15;

    public static void main(String[] args){
        new Main();
    }

    private Main(){
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        mainPanel.setVisible(true);

        InfoPanel infoPanel = new InfoPanel(WINDOW_WIDTH,50, Color.BLACK);
        mainPanel.add(infoPanel, BorderLayout.NORTH);

        GamePanel gamePanel = new GamePanel(WINDOW_WIDTH, 550, Color.LIGHT_GRAY);
        gamePanel.setupGame(HOR, VER, BOMB_COUNT);
        mainPanel.add(gamePanel, BorderLayout.SOUTH);

        this.add(mainPanel);
        this.setVisible(true);
        this.pack();
    }
}
