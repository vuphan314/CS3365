package team2.shattlebip;

/**
 * Created by Paul on 4/9/2016.
 */
public class ShipArrangement {


    Cell checkL1, checkL2, checkL3, checkL4, checkL5;
    Cell checkM1, checkM2, checkM3;

    public boolean checkArrangeLH(AdapterBoard board) {
        for (int i = 0; i < board.getCount(); i++) {
            Cell cell = board.getItem(i);
            if (cell.cellStatus == CellStatus.OCCUPIED) {
                Cell cell2 = board.getItem(i + 1);
                if (cell2.cellStatus == CellStatus.OCCUPIED) {
                    Cell cell3 = board.getItem(i + 2);
                    if (cell3.cellStatus == CellStatus.OCCUPIED) {
                        Cell cell4 = board.getItem(i + 3);
                        if (cell4.cellStatus == CellStatus.OCCUPIED) {
                            Cell cell5 = board.getItem(i + 4);
                            if (cell5.cellStatus == CellStatus.OCCUPIED) {
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
        return false;
    }

    public boolean checkArrangeLV(AdapterBoard board) {
        int boardSize = (int) Math.sqrt(board.getCount());
        for (int i = 0; i < board.getCount(); i++) {
            Cell cell = board.getItem(i);
            if (cell.cellStatus == CellStatus.OCCUPIED) {
                Cell cell2 = board.getItem(i + boardSize);
                if (cell2.cellStatus == CellStatus.OCCUPIED) {
                    Cell cell3 = board.getItem(i + boardSize * 2);
                    if (cell3.cellStatus == CellStatus.OCCUPIED) {
                        Cell cell4 = board.getItem(i + boardSize * 3);
                        if (cell4.cellStatus == CellStatus.OCCUPIED) {
                            Cell cell5 = board.getItem(i + boardSize * 4);
                            if (cell5.cellStatus == CellStatus.OCCUPIED) {
                                checkL1 = board.getItem(i);
                                checkL2 = board.getItem(i + boardSize);
                                checkL3 = board.getItem(i + boardSize * 2);
                                checkL4 = board.getItem(i + boardSize * 3);
                                checkL5 = board.getItem(i + boardSize * 4);
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

        for (int i = 0; i < board.getCount(); i++) {
            Cell cell = board.getItem(i);
            if (cell.cellStatus == CellStatus.OCCUPIED) {
                Cell cell2 = board.getItem(i + 1);
                if (cell2.cellStatus == CellStatus.OCCUPIED) {
                    Cell cell3 = board.getItem(i + 2);
                    if (cell3.cellStatus == CellStatus.OCCUPIED) {
                        if ((cell3 != checkL1) && (cell3 != checkL2) && (cell3 != checkL3)
                                && (cell3 != checkL4) && (cell3 != checkL5)) {
                            checkM1 = board.getItem(i);
                            checkM2 = board.getItem(i + 1);
                            checkM3 = board.getItem(i + 2);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean checkArrangeMV(AdapterBoard board) {

        int boardSize = (int) Math.sqrt(board.getCount());
        for (int i = 0; i < board.getCount(); i++) {
            Cell cell = board.getItem(i);
            if (cell.cellStatus == CellStatus.OCCUPIED) {
                Cell cell2 = board.getItem(i + boardSize);
                if (cell2.cellStatus == CellStatus.OCCUPIED) {
                    Cell cell3 = board.getItem(i + boardSize * 2);
                    if (cell3.cellStatus == CellStatus.OCCUPIED) {
                        if ((cell3 != checkL1) && (cell3 != checkL2) && (cell3 != checkL3)
                                && (cell3 != checkL4) && (cell3 != checkL5)) {
                            checkM1 = board.getItem(i);
                            checkM2 = board.getItem(i + boardSize);
                            checkM3 = board.getItem(i + boardSize * 2);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean checkArrangeSH(AdapterBoard board) {

        for (int i = 0; i < board.getCount(); i++) {
            Cell cell = board.getItem(i);
            if (cell.cellStatus == CellStatus.OCCUPIED) {
                Cell cell2 = board.getItem(i + 1);
                if (cell2.cellStatus == CellStatus.OCCUPIED) {
                    if ((cell2 != checkM1) && (cell2 != checkM2) && (cell2 != checkM3)
                            && (cell2 != checkL1) && (cell2 != checkL2) && (cell2 != checkL3)
                            && (cell2 != checkL4) && (cell2 != checkL5)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean checkArrangeSV(AdapterBoard board) {

        int boardSize = (int) Math.sqrt(board.getCount());
        for (int i = 0; i < board.getCount(); i++) {
            Cell cell = board.getItem(i);
            if (cell.cellStatus == CellStatus.OCCUPIED) {
                Cell cell2 = board.getItem(i + boardSize);
                if (cell2.cellStatus == CellStatus.OCCUPIED) {
                    if ((cell2 != checkM1) && (cell2 != checkM2) && (cell2 != checkM3)
                            && (cell2 != checkL1) && (cell2 != checkL2) && (cell2 != checkL3)
                            && (cell2 != checkL4) && (cell2 != checkL5)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
