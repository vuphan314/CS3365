package team2.shattlebip;

/**
 * Created by Vu on 3/3/2016.
 */
public class Cell {
    public int playerNum;
    public CellStatus cellStatus;

    public Cell(int playerNum, CellStatus cellStatus) {
        this.playerNum = playerNum;
        this.cellStatus = cellStatus;
    }
}
