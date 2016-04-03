package team2.shattlebip;

/**
 * Created by Vu on 4/2/2016.
 */
public class Ship {
    public int vacantCellPosition = 0;
    public int numAttacksMade = 0;
    public int numUpgradesMade = 0;

    public int playerNum;
    public ShipType shipType;

    public int occupancy;
    public int numAttacksAllowed;

    public Cell[] cells;
    public int numUpgradesAllowed = 3;

    public Ship(int playerNum, ShipType shipType) {
        this.playerNum = playerNum;
        this.shipType = shipType;
        setShipTypeDependentFields();
        cells = new Cell[occupancy];
    }

    void setShipTypeDependentFields() {
        if (shipType == ShipType.LITTLE_GUY) {
            occupancy = 2;
            numAttacksAllowed = 1;
        } else if (shipType == ShipType.MIDDLE_MAN) {
            occupancy = 3;
            numAttacksAllowed = 2;
        } else {
            occupancy = 5;
            numAttacksAllowed = 3;
        }
    }

    public void resetNumAttacksMade() {
        numAttacksMade = 0;
    }

    public int getNumAttacksLeft() {
        return numAttacksAllowed - numAttacksMade;
    }

    public boolean canAttack() {
        return getNumAttacksLeft() > 0;
    }

    public void attack() {
        numAttacksMade++;
    }

    public int getNumUpgradesLeft() {
        return numUpgradesAllowed - numUpgradesMade;
    }

    boolean canUpgrade() {
        return getNumUpgradesLeft() > 0;
    }

    public void upgrade() {
        numAttacksAllowed++;
        numUpgradesMade++;
    }

    public boolean isAlive() {
        for (int i = 0; i < occupancy; i++) {
            Cell cell = cells[i];
            if (cell.cellStatus == CellStatus.OCCUPIED)
                return true;
        }
        return false;
    }
}
