package ch.gibb.minesweeper.helpers;

public class Position {
    private int row;
    private int column;

    public Position(int row, int column){
        this.row = row;
        this.column = column;
    }

    public boolean equals(Position pos) {
        return this.row == pos.row && this.column == pos.column;
    }

    public int getRow() {
        return row;
    }
    public int getColumn() {
        return column;
    }
}
