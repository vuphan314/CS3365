package team2.shattlebip;

/**
 * Created by Vu on 3/3/2016.
 */
public class BoardCell {
    private int playerNum;
    private BoardCellStatus boardCellStatus;

    public int getPlayerNum() {
        return playerNum;
    }

    public BoardCellStatus getBoardCellStatus() {
        return boardCellStatus;
    }

    public void setBoardCellStatus(BoardCellStatus boardCellStatus) {
        this.boardCellStatus = boardCellStatus;
    }

    public BoardCell(int playerNum, BoardCellStatus boardCellStatus) {
        this.playerNum = playerNum;
        this.boardCellStatus = boardCellStatus;
    }
}
