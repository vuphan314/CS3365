package team2.shattlebip;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vu on 4/2/2016.
 */
public class Ship {
    public int numCellsAdded = 0;
    public int numAttacksMade = 0;
    public int numUpgradesMade = 0;
    public int numUpgradesAllowed = 3;

    public int playerNum;
    public ShipType shipType;

    public int numCells;
    public int numAttacksAllowed;

    public List<BoardCell> boardCells;

    public Ship(int playerNum, ShipType shipType) {
        this.playerNum = playerNum;
        this.shipType = shipType;
        setShipTypeDependentFields();
        boardCells = new ArrayList<>(numCells);
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

    public void addCell(BoardCell boardCell) {
        boardCell.boardCellStatus = BoardCellStatus.OCCUPIED;
        boardCells.add(boardCell);
        numCellsAdded++;
    }

    public int getNumAttacksLeft() {
        return numAttacksAllowed - numAttacksMade;
    }

    public boolean canAttack() {
        return isAlive() && getNumAttacksLeft() > 0;
    }

    public void attackCell(BoardCell boardCell) {
        if (boardCell.boardCellStatus == BoardCellStatus.VACANT)
            boardCell.boardCellStatus = BoardCellStatus.MISSED;
        if (boardCell.boardCellStatus == BoardCellStatus.OCCUPIED)
            boardCell.boardCellStatus = BoardCellStatus.HIT;
        numAttacksMade++;
    }

    public int getNumUpgradesLeft() {
        return numUpgradesAllowed - numUpgradesMade;
    }

    boolean canUpgrade() {
        return isAlive() && getNumUpgradesLeft() > 0;
    }

    public void upgrade() {
        numAttacksAllowed++;
        numUpgradesMade++;
    }

    public boolean isAlive() {
        for (BoardCell boardCell : boardCells)
            if (boardCell.boardCellStatus != BoardCellStatus.HIT)
                return true;
        return false;
    }
}
