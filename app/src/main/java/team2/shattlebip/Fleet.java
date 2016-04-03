package team2.shattlebip;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vu on 4/3/2016.
 */
public class Fleet {
    public int shipIndex = 0;
    public int numShips = 1;

    public int playerNum;
    public List<Ship> ships = new ArrayList<>(numShips);

    public Fleet(int playerNum) {
        this.playerNum = playerNum;

        Ship ship = new Ship(playerNum, ShipType.BIG_BOY);
        ships.add(ship);
    }
}
