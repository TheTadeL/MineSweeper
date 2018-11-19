package ch.gibb.minesweeper.panels;

import ch.gibb.minesweeper.Tile;
import ch.gibb.minesweeper.TileState;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GamePanel extends DefaultPanel implements MouseListener {
    public static List<Tile> tiles = new ArrayList<Tile>();
    private int rowCnt;
    private int colCnt;
    private Random random = new Random();

    public GamePanel(int height, Color background){
        super(height, background);
        setBorder(new EmptyBorder(20,20,20,20));
    }

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
        setBombs(55);
    }

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
        for(int i = 0; i < tiles.size(); i++){
            if(e.getSource() == tiles.get(i)){
                Tile clickedTile = (Tile)e.getSource();
                //
                if (e.getButton() == MouseEvent.BUTTON1 && clickedTile.getState() != TileState.CLICKED) {
                    clickedTile.setState(TileState.CLICKED);
                    //clickedTile.onClick();
                    if(clickedTile.countBombs() == 0){
                        revealOpenTiles(clickedTile);
                    }

                    // TEST
                    System.out.println(clickedTile.isBomb() +
                                "[" + clickedTile.getPositionRow() + "]" +
                                "[" + clickedTile.getPositionCol() + "]");
                        //
                }
                if (e.getButton() == MouseEvent.BUTTON3 && clickedTile.getState() != TileState.CLICKED) {
                    if(clickedTile.getBackground() != Color.PINK){
                        clickedTile.setBackground(Color.PINK);
                    }
                    else {
                        clickedTile.setBackground(new JButton().getBackground());
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

    private int checkBombs(int startRow, int startCol){
        int bombsFound = 0;

        int row = startRow-1;
        int col = startCol-1;

        for (int i = 0; i < 9; i++){
            //StartTile überspringen
            if(!(row == startRow && col == startRow)) {

            }
            col++;
            if (col > startCol + 1) {
                col = startCol - 1;
                row += 1;
            }
        }

        return bombsFound;
    }

    private void revealOpenTiles(Tile startTile){
        List<Tile> tilesToCheck = new ArrayList<>();
        tilesToCheck.add(startTile);

        while (tilesToCheck.size() > 0){
            tilesToCheck.get(0).setState(TileState.CLICKED);
            for(Tile tile : tilesToCheck.get(0).getNeighbours()){
                if(tile.countBombs() == 0 && tile.getState() != TileState.CLICKED){
                    tilesToCheck.add(tile);
                }
                tile.setState(TileState.CLICKED);
            }
            tilesToCheck.remove(0);
        }
    }
}
