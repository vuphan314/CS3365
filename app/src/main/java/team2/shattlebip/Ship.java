package team2.shattlebip;

/**
 * Created by Vu on 4/2/2016.
 */
public class Ship {
    public int cellPosition = 0;
    public int upgradeCount = 0;

    public int playerNum;
    public ShipType shipType;
    public int occupancy;
    public int bulletCount;
    public BoardCell[] boardCells;

    public Ship(int playerNum, ShipType shipType) {
        this.playerNum = playerNum;
        this.shipType = shipType;
        setOccupancyAndBulletCount();
        boardCells = new BoardCell[occupancy];
    }

    void setOccupancyAndBulletCount() {
        if (shipType == ShipType.LITTLE_GUY) {
            occupancy = 2;
            bulletCount = 1;
        } else if (shipType == ShipType.MIDDLE_MAN) {
            occupancy = 3;
            bulletCount = 2;
        } else {
            occupancy = 5;
            bulletCount = 3;
        }
    }

    public void upgrade() {
        if (upgradeCount < 3) {
            bulletCount++;
            upgradeCount++;
        }
    }

    public boolean isAlive() {
        for (int i = 0; i < occupancy; i++) {
            BoardCell boardCell = boardCells[i];
            if (boardCell.status == BoardCellStatus.OCCUPIED)
                return true;
        }
        return false;
    }
}
