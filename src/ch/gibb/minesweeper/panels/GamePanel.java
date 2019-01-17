package ch.gibb.minesweeper.panels;

import ch.gibb.minesweeper.Tile;
import ch.gibb.minesweeper.TileState;
import ch.gibb.minesweeper.helpers.Position;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Random;

/**
 * Die Spiellogik findet in dieser Klasse statt.
 */
public class GamePanel extends DefaultPanel implements MouseListener {
    private List<Tile> tiles = new ArrayList<>();
    private int rowCnt;
    private int colCnt;
    private Random random = new Random();
    private boolean gameOver = false;
    private int bombCount;

    public GamePanel(int width, int height, Color background){
        super(width, height, background);
        setBorder(new EmptyBorder(20,20,20,20));
    }

    /**
     * Das Spielfeld aufbauen. Die Felder werden erstellt und auf das Spielfeld ausgelegt.
     * @param colCnt - Anzahl Spalten
     * @param rowCnt - Anzahl Zeilen
     */
    public void setupGame(int colCnt, int rowCnt, int bombCount) {
        this.colCnt = colCnt;
        this.rowCnt = rowCnt;
        this.bombCount = bombCount;
        removeAll();
        tiles.clear();

        setLayout(new GridLayout(rowCnt, colCnt));
        for(int i = 0; i < rowCnt; i++){
            for(int j = 0; j < colCnt; j++){
                Tile tile = new Tile(new Position(i, j));
                tile.setPreferredSize(new Dimension(2,2));
                tile.addMouseListener(this);
                add(tile);
                tiles.add(tile);
            }
        }
        setBombs(this.bombCount);
        this.gameOver = false;
    }


    /**
     * Bomben werden zufällig auf das Spielfeld aufgeteilt.
     * @param bombCnt - Anzahl zu setzende Bomben.
     */
    private void setBombs(int bombCnt){
        int bombsInGame = 0;

        while(bombsInGame < bombCnt){
            int randomNumHor = random.nextInt(colCnt);
            int randomNumVer = random.nextInt(rowCnt);
            for (Tile tile : tiles) {
                if(tile.getPosition().equals(new Position(randomNumVer, randomNumHor))){
                    tile.setBomb(true);
                    tile.setText(" ");
                    bombsInGame++;
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }
    @Override
    public void mousePressed(MouseEvent e) {
        try {//Klicks auf dem Spielfeld nur überprüfen, wenn das Spiel noch nicht zu ende ist.
            if (!gameOver) {
                for (Tile tile : tiles) {
                    if (e.getSource() == tile) {
                        //Das geklickte Feld in einer Variable abspeichern.
                        Tile clickedTile = (Tile) e.getSource();

                        //Wenn Linksklick:
                        if (e.getButton() == MouseEvent.BUTTON1 && clickedTile.getState() != TileState.CLICKED && clickedTile.getState() != TileState.EMPTY) {
                            if (clickedTile.isBomb()) {
                                gameOver = true;
                                blowBombs();
                                showGameOverWindow();
                            } else {
                                //Bomben um das Feld werden gezählt. Wenn es 0 Bomben sind werden die benachbarten Felder überprüft.
                                if (clickedTile.countAdjacentBombs(this) == 0) {
                                    revealOpenTiles(clickedTile);
                                }
                            }
                        }
                        //Wenn Rechtsklick:
                        else if (e.getButton() == MouseEvent.BUTTON3 && clickedTile.getState() != TileState.CLICKED && clickedTile.getState() != TileState.EMPTY) {
                            if (clickedTile.getState() != TileState.FLAGGED) {
                                clickedTile.setState(TileState.FLAGGED);
                            } else {
                                clickedTile.setState(TileState.DEFAULT);
                            }
                        }
                    }
                }
                checkGame();
            }

        }
        catch (ConcurrentModificationException exeption) {
            //Try / Catch um den ersten Klick zu umgehen welcher sich nur auf das JOptionPane bezieht.
            System.out.println("Game restarted");
        }
    }
    @Override
    public void mouseReleased(MouseEvent e) {

    }
    @Override
    public void mouseEntered(MouseEvent e) {

    }
    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * Felder werden nacheinander überprüft und es wird geschaut ob benachbarte Bomben existieren. Wenn ein Feld
     * keine benachbarte Bomben hat, wird dieses auch aufgedeckt und es wird nochmal nach benachbarten Bomben gesucht,
     * bis keine benachbarten Felder 0 benachbarte Bomben mehr haben.
     * @param startTile - bei diesem Feld startet die Überprüfung.
     */
    private void revealOpenTiles(Tile startTile){
        //Liste mit Feldern die überprüft werden müssen.
        List<Tile> tilesToCheck = new ArrayList<>();
        tilesToCheck.add(startTile);


        //Alle Felder in der Liste überprüfen.
        while (tilesToCheck.size() > 0){
            for(Tile tile : tilesToCheck.get(0).getNeighbours(this)){
                if(tile.countAdjacentBombs(this) == 0 ){
                    tilesToCheck.add(tile);
                }
            }
            tilesToCheck.remove(0);
        }
    }

    /**
     * Zeigt alle Bomben auf dem Spielfeld an.
     */
    private void blowBombs(){
        for (Tile tile : tiles) {
            if(tile.isBomb()){
                tile.setState(TileState.BLOWN);
            }
        }
    }

    /**
     * Prüft wieviele Felder bereits aufgedekt sind.
     * Wenn alle Felder aufgedeckt sind, ist das Spiel zu ende
     */
    private void checkGame(){
        int unopenedTiles = 0;
        for(Tile tile : tiles){
            if(!tile.isBomb() && tile.getState() != TileState.EMPTY && tile.getState() != TileState.CLICKED){
                unopenedTiles++;
            }
        }
        // Wenn alle Tiles, welche keine Bomben sind, geöffnet wurden, ohne ein GameOver auszulösen,
        // werden alle Bomben aufgedekt und das Spiel ist gewonnen
        if(unopenedTiles == 0){
            for(Tile tile : tiles){
                if(tile.isBomb()) {
                    tile.setBackground(Color.GREEN);
                    tile.setText("O");
                }
            }
            gameOver = true;
            showGameWonWindow();
        }
    }

    private void showGameOverWindow(){
        //Custom button text
        String[] options = {"Yes", "No"};
        int answer = JOptionPane.showOptionDialog(this,
                "You lost! Try again?",
                "GAME OVER",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,     //do not use a custom Icon
                options,  //the titles of buttons
                options[0]); //default button title

        if (answer == JOptionPane.YES_OPTION) {
            setupGame(colCnt, rowCnt, bombCount);
        } else if (answer == JOptionPane.NO_OPTION) {
            System.exit(1);
        }
    }

    private void showGameWonWindow(){
        //Custom button text
        String[] options = {"Yes", "No"};
        int answer = JOptionPane.showOptionDialog(this,
                "You Won!! Want to play again?",
                "WINNER, WINNER, CHICKEN DINNER",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,     //do not use a custom Icon
                options,  //the titles of buttons
                options[0]); //default button title

        if (answer == JOptionPane.YES_OPTION) {
            setupGame(colCnt, rowCnt, bombCount);
        } else if (answer == JOptionPane.NO_OPTION) {
            System.exit(1);
        }
    }

    public List<Tile> getTiles(){
        return tiles;
    }
}
