package ch.gibb.minesweeper;

import ch.gibb.minesweeper.panels.GamePanel;
import com.sun.istack.internal.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class Tile extends JButton {
    public static Color COLOR_EMPTY_CLICKED = Color.LIGHT_GRAY;
    public static Color COLOR_NUMBER_CLICKED = Color.WHITE;
    public static Color COLOR_FLAGGED = Color.PINK;
    public static Color COLOR_BOMB = Color.RED;

    private boolean isBomb;
    private int positionRow;
    private int positionCol;

    public Tile(int positionRow, int positionCol){
        this.positionRow = positionRow;
        this.positionCol = positionCol;
    }

    /**
     * Listet alle benachbarten Felder auf, welche NICHT bereits aufgedeckt sind.
     * @return - Liste der Nachbarsfelder
     */
    public List<Tile> getNeighbours(){
        List<Tile> neighbours = new ArrayList<>();

        for(Tile tile : GamePanel.tiles){
            if(
                    tile.getPositionRow() == this.getPositionRow()-1 && tile.getPositionCol() == this.getPositionCol()-1 ||
                    tile.getPositionRow() == this.getPositionRow()-1 && tile.getPositionCol() == this.getPositionCol() ||
                    tile.getPositionRow() == this.getPositionRow()-1 && tile.getPositionCol() == this.getPositionCol()+1 ||
                    tile.getPositionRow() == this.getPositionRow() && tile.getPositionCol() == this.getPositionCol()-1 ||
                    tile.getPositionRow() == this.getPositionRow() && tile.getPositionCol() == this.getPositionCol()+1 ||
                    tile.getPositionRow() == this.getPositionRow()+1 && tile.getPositionCol() == this.getPositionCol()-1 ||
                    tile.getPositionRow() == this.getPositionRow()+1 && tile.getPositionCol() == this.getPositionCol() ||
                    tile.getPositionRow() == this.getPositionRow()+1 && tile.getPositionCol() == this.getPositionCol()+1
            ){
                if(tile.getState() == TileState.DEFAULT || tile.getState() == TileState.FLAGGED)
                    neighbours.add(tile);
            }
        }
        return neighbours;
    }

    /**
     * Gibt die Anzahl der benachbarten Bomben zurück.
     * @return - Anzahl Bomben
     */
     public int countBombs(){
        List<Tile> tiles = this.getNeighbours();
        int bombCount = 0;

        for (Tile tile: tiles) {
            if(tile.isBomb()){
                bombCount++;
            }
        }

        if(bombCount != 0) {
            this.setText(String.valueOf(bombCount));
            this.setState(TileState.CLICKED);
        }
        else {
            this.setState(TileState.EMPTY);
        }
         return bombCount;
    }

    private TileState state = TileState.DEFAULT;

    public void setState(TileState state) {
        switch(state){
            case CLICKED:
                this.setBackground(COLOR_NUMBER_CLICKED);
                break;
            case EMPTY:
                this.setBackground(COLOR_EMPTY_CLICKED);
                this.setEnabled(false);
                break;
            case FLAGGED:
                this.setBackground(COLOR_FLAGGED);
                break;
            case BLOWN:
                this.setText("X");
                this.setBackground(COLOR_BOMB);
                break;
            case DEFAULT:
                this.setBackground(new JButton().getBackground());
                break;
                default:
                    throw new IllegalArgumentException("Kein gültiger TileState mitgegeben!");
        }
        this.state = state;
    }
    public boolean isBomb() {
        return isBomb;
    }
    public void setBomb(boolean bomb) {
        isBomb = bomb;
    }
    public TileState getState() {
        return state;
    }
    public int getPositionRow() {
        return positionRow;
    }
    public void setPositionRow(int positionRow) {
        this.positionRow = positionRow;
    }
    public int getPositionCol() {
        return positionCol;
    }
    public void setPositionCol(int positionCol) {
        this.positionCol = positionCol;
    }
}
