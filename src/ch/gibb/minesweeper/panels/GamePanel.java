package ch.gibb.minesweeper.panels;

import ch.gibb.minesweeper.Main;
import ch.gibb.minesweeper.Tile;
import ch.gibb.minesweeper.TileState;

import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GamePanel extends DefaultPanel implements MouseListener {
    public static List<Tile> tiles = new ArrayList<>();
    private int rowCnt;
    private int colCnt;
    private Random random = new Random();

    public GamePanel(int height, Color background){
        super(height, background);
        setBorder(new EmptyBorder(20,20,20,20));
    }

    /**
     * Das Spielfeld aufbauen. Die Felder werden erstellt und auf das Spielfeld ausgelegt.
     * @param colCnt - Anzahl Spalten
     * @param rowCnt - Anzahl Zeilen
     */
    public void setupGame(int colCnt, int rowCnt) {
        this.colCnt = colCnt;
        this.rowCnt = rowCnt;

        setLayout(new GridLayout(rowCnt, colCnt));
        for(int i = 0; i < rowCnt; i++){
            for(int j = 0; j < colCnt; j++){
                Tile tile = new Tile(i, j);
                tile.setPreferredSize(new Dimension(2,2));
                tile.addMouseListener(this);
                add(tile);
                tiles.add(tile);
            }
        }
        setBombs(Main.BOMB_COUNT);
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
                if(tile.getPositionRow() == randomNumVer && tile.getPositionCol() == randomNumHor){
                    tile.setBomb(true);
                    bombsInGame++;
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        for(Tile tile : tiles){
            if(e.getSource() == tile){
                //Das geklickte Feld in einer Variable abspeichern.
                Tile clickedTile = (Tile)e.getSource();

                //Wenn Linksklick:
                if (e.getButton() == MouseEvent.BUTTON1 && clickedTile.getState() != TileState.CLICKED && clickedTile.getState() != TileState.EMPTY) {
                    //Bomben um das Feld werden gezählt. Wenn es 0 Bomben sind werden die benachbarten Felder überprüft.
                    if(clickedTile.countBombs() == 0){
                        revealOpenTiles(clickedTile);
                    }
                }
                //Wenn Rechtsklick:
                else if (e.getButton() == MouseEvent.BUTTON3 && clickedTile.getState() != TileState.CLICKED && clickedTile.getState() != TileState.EMPTY) {
                    if(clickedTile.getState() != TileState.FLAGGED){
                        clickedTile.setState(TileState.FLAGGED);
                    }
                    else {
                        clickedTile.setState(TileState.DEFAULT);
                    }
                }
            }
        }
    }
    @Override
    public void mousePressed(MouseEvent e) {

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
            for(Tile tile : tilesToCheck.get(0).getNeighbours()){
                if(tile.countBombs() == 0 ){
                    tilesToCheck.add(tile);
                }
            }
            tilesToCheck.remove(0);
        }
    }
}
