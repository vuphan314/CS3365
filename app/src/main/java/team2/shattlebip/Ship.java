package team2.shattlebip;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vu
 */
public class Ship {
    private int numCellsAdded = 0;
    private int numAttacksMade = 0;
    private int numUpgradesMade = 0;
    private int numUpgradesAllowed = 3;
    private int playerNum;
    private ShipType shipType;
    private int numCells;
    private int numAttacksAllowed;
    private List<Cell> cells;

    public Ship(int playerNum, ShipType shipType) {
        this.playerNum = playerNum;
        this.shipType = shipType;
        setShipTypeDependentFields();
        cells = new ArrayList<>(numCells);
    }

    public void setNumAttacksMade(int numAttacksMade) {
        this.numAttacksMade = numAttacksMade;
    }

    public int getNumCells() {
        return numCells;
    }

    void setShipTypeDependentFields() {
        if (shipType == ShipType.LITTLE_GUY) {
            numCells = 2;
            numAttacksAllowed = 1;
        } else if (shipType == ShipType.MIDDLE_MAN) {
            numCells = 3;
            numAttacksAllowed = 2;
        } else {
            numCells = 5;
            numAttacksAllowed = 3;
        }
    }

    public int getNumCellsToAdd() {
        return numCells - numCellsAdded;
    }

    public boolean canAddCells() {
        return getNumCellsToAdd() > 0;
    }

    public void addCell(Cell cell) {
        cell.setStatus(Cell.Status.OCCUPIED);
        cells.add(cell);
        numCellsAdded++;
    }

    public int getNumAttacksLeft() {
        return numAttacksAllowed - numAttacksMade;
    }

    public boolean canAttack() {
        return isAlive() && getNumAttacksLeft() > 0;
    }

    public void attackCell(Cell cell) {
        if (cell.getStatus() == Cell.Status.VACANT)
            cell.setStatus(Cell.Status.MISSED);
        if (cell.getStatus() == Cell.Status.OCCUPIED)
            cell.setStatus(Cell.Status.HIT);
        numAttacksMade++;
    }

    private int getNumUpgradesLeft() {
        return numUpgradesAllowed - numUpgradesMade;
    }

    private boolean canUpgrade() {
        return isAlive() && getNumUpgradesLeft() > 0;
    }

    private void upgrade() {
        numAttacksAllowed++;
        numUpgradesMade++;
    }

    public boolean isAlive() {
        for (Cell cell : cells)
            if (cell.getStatus() != Cell.Status.HIT)
                return true;
        return false;
    }

    public enum ShipType {
        LITTLE_GUY, MIDDLE_MAN, BIG_BOY
    }
}
