package team2.shattlebip;

/**
 * Created by Vu on 3/3/2016.
 */
public class BoardCell {
    public int playerNum;
    public BoardCellStatus boardCellStatus;

    public BoardCell(int playerNum, BoardCellStatus boardCellStatus) {
        this.playerNum = playerNum;
        this.boardCellStatus = boardCellStatus;
    }
}
