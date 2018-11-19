package ch.gibb.minesweeper;

import ch.gibb.minesweeper.panels.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class Tile extends JButton {
    private boolean isBomb;
    private int positionRow;
    private int positionCol;

    public Tile(int positionRow, int positionCol){
        this.positionRow = positionRow;
        this.positionCol = positionCol;
    }

    public void onClick(){
//        int bombCount = 0;
//        this.setState(TileState.CLICKED);
//
//        if(this.isBomb()){
//            //GAME OVER
//        }
//        else {
//            this.setBackground(Color.LIGHT_GRAY);
//            bombCount = countBombs(this.getNeighbours());
//            if(bombCount != 0) {
//                this.setText(String.valueOf(bombCount));
//            }
//            else {
//                for(Tile tile : this.getNeighbours()){
//                    tile.onReveal();
//                }
//            }
//        }
    }

    public void onReveal(){
        int bombCount = 0;
        if(this.isBomb()){

        }
        else {
            bombCount = this.countBombs();
            this.setBackground(Color.LIGHT_GRAY);
            if(bombCount != 0) {
                this.setText(String.valueOf(bombCount));
            }
            else {
                for(Tile tile : this.getNeighbours()){

                }
            }
        }
    }

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
                if(tile.getState() == TileState.DEFAULT)
                    neighbours.add(tile);
            }
        }

        return neighbours;
    }

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
            this.setBackground(Color.white);
        } else {
            this.setBackground(Color.lightGray);
            this.setEnabled(false);
        }

         return bombCount;
    }

    private TileState state = TileState.DEFAULT;

    public boolean isBomb() {
        return isBomb;
    }
    public void setBomb(boolean bomb) {
        setText("B");
        isBomb = bomb;
    }
    public TileState getState() {
        return state;
    }
    public void setState(TileState state) {
        this.state = state;
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
