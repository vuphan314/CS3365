package team2.shattlebip;

/**
 * Created by Vu on 3/3/2016.
 */
public class BoardCell {
    public int playerNum;
    public BoardCellStatus status;

    public BoardCell(int playerNum, BoardCellStatus status) {
        this.playerNum = playerNum;
        this.status = status;
    }
}
