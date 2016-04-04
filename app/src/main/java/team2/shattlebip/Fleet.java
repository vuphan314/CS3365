package team2.shattlebip;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vu on 4/3/2016.
 */
public class Fleet {
    public int numShipsArranged = 0;
    public int numShips = 3;

    public int playerNum;
    public List<Ship> ships = new ArrayList<>(numShips);

    public Fleet(int playerNum) {
        this.playerNum = playerNum;

        Ship ship = new Ship(playerNum, ShipType.BIG_BOY);
        ships.add(ship);
        ship = new Ship(playerNum, ShipType.LITTLE_GUY);
        ships.add(ship);
        ship = new Ship(playerNum, ShipType.MIDDLE_MAN);
        ships.add(ship);
    }

    public int getNumShipsToArrange() {
        return numShips - numShipsArranged;
    }

    public boolean canAddCell() {
        return getNumShipsToArrange() > 0;
    }

    public void addCell(Cell cell) {
        Ship ship = ships.get(numShipsArranged);

        ship.addCell(cell);
        if (!ship.canAddCells())
            numShipsArranged++;
    }

    public boolean canAttack() {
        for (Ship ship : ships)
            if (ship.canAttack())
                return true;
        return false;
    }

    public void attackCell(Cell cell) {
        Ship ship = getNextShipCanAttack();
        ship.attackCell(cell);
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
            ship.numAttacksMade = 0;
    }

    public int getNumShipsAlive() {
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
