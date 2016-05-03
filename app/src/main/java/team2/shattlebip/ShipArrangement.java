package team2.shattlebip;

/**
 * Created by Paul on 4/9/2016.
 */
public class ShipArrangement {


    BoardCell checkL1, checkL2, checkL3, checkL4, checkL5;
    BoardCell checkM1, checkM2, checkM3;

    public boolean checkArrangeLH(AdapterBoard board) {
        int boardSize = (int) Math.sqrt(board.getCount());
        for (int i = 0; i < board.getCount()-4; i++) {
            BoardCell boardCell = board.getItem(i);
            if (boardCell.boardCellStatus == BoardCellStatus.OCCUPIED) {
                if(((i+1)%boardSize==boardSize-1)||((i+2)%boardSize==boardSize-1)||((i+3)%boardSize==boardSize-1)) {
                    continue;
                }
                else{
                    BoardCell boardCell2 = board.getItem(i + 1);
                    if (boardCell2.boardCellStatus == BoardCellStatus.OCCUPIED) {
                        BoardCell boardCell3 = board.getItem(i + 2);
                        if (boardCell3.boardCellStatus == BoardCellStatus.OCCUPIED) {
                            BoardCell boardCell4 = board.getItem(i + 3);
                            if (boardCell4.boardCellStatus == BoardCellStatus.OCCUPIED) {
                                BoardCell boardCell5 = board.getItem(i + 4);
                                if (boardCell5.boardCellStatus == BoardCellStatus.OCCUPIED) {
                                    checkL1 = board.getItem(i);
                                    checkL2 = board.getItem(i + 1);
                                    checkL3 = board.getItem(i + 2);
                                    checkL4 = board.getItem(i + 3);
                                    checkL5 = board.getItem(i + 4);
                                    return true;

                                }
                            }
                        }
                    }
                }
            }
        }


        return false;
    }

    public boolean checkArrangeLV(AdapterBoard board) {
        int boardSize = (int) Math.sqrt(board.getCount());
        for (int i = 0; i < (board.getCount()-(boardSize*4)); i++) {
            BoardCell boardCell = board.getItem(i);
            if (boardCell.boardCellStatus == BoardCellStatus.OCCUPIED) {
                BoardCell boardCell2 = board.getItem(i + boardSize);
                if (boardCell2.boardCellStatus == BoardCellStatus.OCCUPIED) {
                    BoardCell boardCell3 = board.getItem(i + boardSize*2);
                    if (boardCell3.boardCellStatus == BoardCellStatus.OCCUPIED) {
                        BoardCell boardCell4 = board.getItem(i + boardSize*3);
                        if (boardCell4.boardCellStatus == BoardCellStatus.OCCUPIED) {
                            BoardCell boardCell5 = board.getItem(i + boardSize*4);
                            if (boardCell5.boardCellStatus == BoardCellStatus.OCCUPIED) {
                                checkL1 = board.getItem(i);
                                checkL2 = board.getItem(i + boardSize);
                                checkL3 = board.getItem(i + boardSize*2);
                                checkL4 = board.getItem(i + boardSize*3);
                                checkL5 = board.getItem(i + boardSize*4);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean checkArrangeMH(AdapterBoard board) {
        int boardSize = (int) Math.sqrt(board.getCount());
        for (int i = 0; i < board.getCount()-2; i++) {
            BoardCell boardCell = board.getItem(i);
            if (boardCell.boardCellStatus == BoardCellStatus.OCCUPIED) {
                if(((i+1)%boardSize==boardSize-1)||((i+2)%boardSize==boardSize-1)) {
                    continue;
                }
                else{
                    BoardCell boardCell2 = board.getItem(i + 1);
                    if (boardCell2.boardCellStatus == BoardCellStatus.OCCUPIED) {
                        BoardCell boardCell3 = board.getItem(i + 2);
                        if (boardCell3.boardCellStatus == BoardCellStatus.OCCUPIED) {
                            if ((boardCell3 != checkL1) && (boardCell3 != checkL2) && (boardCell3 != checkL3)
                                    && (boardCell3 != checkL4) && (boardCell3 != checkL5)) {
                                checkM1 = board.getItem(i);
                                checkM2 = board.getItem(i + 1);
                                checkM3 = board.getItem(i + 2);
                                return true;

                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean checkArrangeMV(AdapterBoard board) {

        int boardSize = (int) Math.sqrt(board.getCount());
        for (int i = 0; i < (board.getCount()-(boardSize*2)); i++) {
            BoardCell boardCell = board.getItem(i);
            if (boardCell.boardCellStatus == BoardCellStatus.OCCUPIED) {
                BoardCell boardCell2 = board.getItem(i + boardSize);
                if (boardCell2.boardCellStatus == BoardCellStatus.OCCUPIED) {
                    BoardCell boardCell3 = board.getItem(i + boardSize*2);
                    if (boardCell3.boardCellStatus == BoardCellStatus.OCCUPIED) {
                        if ((boardCell3 != checkL1) && (boardCell3 != checkL2) && (boardCell3 != checkL3)
                                && (boardCell3 != checkL4) && (boardCell3 != checkL5)) {
                            checkM1 = board.getItem(i);
                            checkM2 = board.getItem(i + boardSize);
                            checkM3 = board.getItem(i + boardSize*2);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean checkArrangeSH(AdapterBoard board) {
        int boardSize = (int) Math.sqrt(board.getCount());
        for (int i = 0; i < board.getCount(); i++) {
            BoardCell boardCell = board.getItem(i);
            if (boardCell.boardCellStatus == BoardCellStatus.OCCUPIED) {
                if((i+1)%boardSize==boardSize-1) {
                    continue;
                }
                else{
                    BoardCell boardCell2 = board.getItem(i + 1);
                    if (boardCell2.boardCellStatus == BoardCellStatus.OCCUPIED) {
                        if ((boardCell2 != checkM1) && (boardCell2 != checkM2) && (boardCell2 != checkM3)
                                && (boardCell2 != checkL1) && (boardCell2 != checkL2) && (boardCell2 != checkL3)
                                && (boardCell2 != checkL4) && (boardCell2 != checkL5)) {
                            return true;

                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean checkArrangeSV(AdapterBoard board) {

        int boardSize = (int) Math.sqrt(board.getCount());
        for (int i = 0; i < (board.getCount()-boardSize); i++) {
            BoardCell boardCell = board.getItem(i);
            if (boardCell.boardCellStatus == BoardCellStatus.OCCUPIED) {
                BoardCell boardCell2 = board.getItem(i + boardSize);
                if (boardCell2.boardCellStatus == BoardCellStatus.OCCUPIED) {
                    if ((boardCell2 != checkM1) && (boardCell2 != checkM2) && (boardCell2 != checkM3)
                            && (boardCell2 != checkL1) && (boardCell2 != checkL2) && (boardCell2 != checkL3)
                            && (boardCell2 != checkL4) && (boardCell2 != checkL5)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
