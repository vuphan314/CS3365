package team2.shattlebip;

/**
 * Created by Vu on 3/3/2016.
 */
public class BoardCell {
    public int playerNum;
    public String boardCellStatus;

    public BoardCell(int playerNum, String boardCellStatus) {
        this.playerNum = playerNum;
        this.boardCellStatus = boardCellStatus;
    }
}
