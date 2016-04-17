package team2.shattlebip;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vu on 4/2/2016.
 */
public class Ship {
    private int numCellsAdded = 0;
    private int numAttacksMade = 0;

    public void setNumAttacksMade(int numAttacksMade) {
        this.numAttacksMade = numAttacksMade;
    }

    private int numUpgradesMade = 0;
    private int numUpgradesAllowed = 3;

    private int playerNum;
    private ShipType shipType;

    private int numCells;

    public int getNumCells() {
        return numCells;
    }

    private int numAttacksAllowed;

    private List<BoardCell> boardCells;

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
        boardCell.setBoardCellStatus(BoardCellStatus.OCCUPIED);
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
        if (boardCell.getBoardCellStatus() == BoardCellStatus.VACANT)
            boardCell.setBoardCellStatus(BoardCellStatus.MISSED);
        if (boardCell.getBoardCellStatus() == BoardCellStatus.OCCUPIED)
            boardCell.setBoardCellStatus(BoardCellStatus.HIT);
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
        for (BoardCell boardCell : boardCells)
            if (boardCell.getBoardCellStatus() != BoardCellStatus.HIT)
                return true;
        return false;
    }
}
