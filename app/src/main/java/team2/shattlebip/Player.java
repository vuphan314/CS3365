package team2.shattlebip;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vu on 4/3/2016.
 */
public class Player {
    private int numShipsArranged = 0;

    public int getNumShipsArranged() {
        return numShipsArranged;
    }

    private int numShips = 3;

    public int getNumShips() {
        return numShips;
    }

    private int playerNum;
    private List<Ship> ships = new ArrayList<>(numShips);

    public List<Ship> getShips() {
        return ships;
    }

    public Player(int playerNum) {
        this.playerNum = playerNum;

        Ship ship = new Ship(playerNum, ShipType.LITTLE_GUY);
        ships.add(ship);
        ship = new Ship(playerNum, ShipType.MIDDLE_MAN);
        ships.add(ship);
        ship = new Ship(playerNum, ShipType.BIG_BOY);
        ships.add(ship);
    }

    private int getNumShipsToArrange() {
        return numShips - numShipsArranged;
    }

    public boolean canAddCell() {
        return getNumShipsToArrange() > 0;
    }

    public void addCell(BoardCell boardCell) {
        Ship ship = ships.get(numShipsArranged);

        ship.addCell(boardCell);
        if (!ship.canAddCells())
            numShipsArranged++;
    }

    public boolean canAttack() {
        for (Ship ship : ships)
            if (ship.canAttack())
                return true;
        return false;
    }

    public void attackCell(BoardCell boardCell) {
        Ship ship = getNextShipCanAttack();
        ship.attackCell(boardCell);
    }

    public Ship getNextShipCanAttack() {
        int index = 0;
        Ship ship;
        do {
            ship = ships.get(index);
            index++;
        } while (!ship.canAttack());
        return ship;
    }

    public void resetNumsAttacksMade() {
        for (Ship ship : ships)
            ship.setNumAttacksMade(0);
    }

    private int getNumShipsAlive() {
        int numShipsAlive = 0;
        for (Ship ship : ships)
            if (ship.isAlive())
                numShipsAlive++;
        return numShipsAlive;
    }

    public boolean isAlive() {
        return getNumShipsAlive() > 0;
    }
}
