package team2.shattlebip;

/**
 * Created by Vu on 4/2/2016.
 */
public class Ship {
    public int playerNum;
    public ShipType shipType;
    public int occupancy;
    public int bulletCount;

    public int upgradeCount = 0;
    public BoardCell[] boardCells = new BoardCell[occupancy];

    public Ship(int playerNum, ShipType shipType) {
        this.playerNum = playerNum;
        this.shipType = shipType;
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
}
