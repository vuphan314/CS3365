package team2.shattlebip;

/**
 * Created by Vu on 3/3/2016.
 */
public class BoardCell {
//    MERGE CONFLICT RESOLVED BY VU

//    commented out Zach's code
//    public BoardCellStatus status;
//
//    public BoardCell(BoardCellStatus status) {
//        this.status = status;
//    }

    //  kept Vu's code
    public int playerNum;
    public String boardCellStatus;

    public BoardCell(int playerNum, String boardCellStatus) {
        this.playerNum = playerNum;
        this.boardCellStatus = boardCellStatus;
    }
}
