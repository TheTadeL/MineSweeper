package ch.gibb.minesweeper;

import ch.gibb.minesweeper.helpers.Position;
import ch.gibb.minesweeper.panels.GamePanel;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Tiles sind die einzelnen Felder auf einem GamePanel.
 */
public class Tile extends JButton {
    private static Color COLOR_EMPTY_CLICKED = Color.LIGHT_GRAY;
    private static Color COLOR_NUMBER_CLICKED = Color.WHITE;
    private static Color COLOR_FLAGGED = Color.PINK;
    private static Color COLOR_BOMB = Color.RED;

    private TileState state = TileState.DEFAULT;
    private boolean isBomb;
    private Position position;

    public Tile(Position position){
        this.position = position;
    }

    /**
     * Gibt alle benachbarten Tiles zurück
     * @param gamePanel => GamePanel, in welchem sich das Tile befindet
     * @return => benachbarte Tiles : List<Tile>
     */
    public List<Tile> getNeighbours(GamePanel gamePanel){
        List<Tile> neighbours = new ArrayList<>();

        for(Tile tile : gamePanel.getTiles()){
            if(
                    tile.getPosition().getRow() == this.getPosition().getRow()-1 && tile.getPosition().getColumn() == this.getPosition().getColumn()-1 ||
                    tile.getPosition().getRow() == this.getPosition().getRow()-1 && tile.getPosition().getColumn() == this.getPosition().getColumn() ||
                    tile.getPosition().getRow() == this.getPosition().getRow()-1 && tile.getPosition().getColumn() == this.getPosition().getColumn()+1 ||
                    tile.getPosition().getRow() == this.getPosition().getRow() && tile.getPosition().getColumn() == this.getPosition().getColumn()-1 ||
                    tile.getPosition().getRow() == this.getPosition().getRow() && tile.getPosition().getColumn() == this.getPosition().getColumn()+1 ||
                    tile.getPosition().getRow() == this.getPosition().getRow()+1 && tile.getPosition().getColumn() == this.getPosition().getColumn()-1 ||
                    tile.getPosition().getRow() == this.getPosition().getRow()+1 && tile.getPosition().getColumn() == this.getPosition().getColumn() ||
                    tile.getPosition().getRow() == this.getPosition().getRow()+1 && tile.getPosition().getColumn() == this.getPosition().getColumn()+1
            ){
                //Nur Felder zu den Nachbaren hinzufügen, welche noch nicht aufgedekt wurden.
                if(tile.getState() == TileState.DEFAULT || tile.getState() == TileState.FLAGGED)
                    neighbours.add(tile);
            }
        }
        return neighbours;
    }

    /**
     * Gibt die Anzahl der benachbarten Bomben zurück.
     * @param gamePanel => GamePanel, in welchem sich das Tile befindet
     * @return anzahl Bomben : integer
     */
     public int countAdjacentBombs(GamePanel gamePanel){
        List<Tile> neighbours = this.getNeighbours(gamePanel);
        int bombCount = 0;

        for (Tile tile: neighbours) {
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


    /**
     * Wechselt die State des Tiles und passt das Tile automatisch an.
     * @param state => TileState, welche von dem Tile angenommen werden soll
     */
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
    public Position getPosition() {
        return position;
    }
}
