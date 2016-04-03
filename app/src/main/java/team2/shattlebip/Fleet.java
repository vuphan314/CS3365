package team2.shattlebip;

/**
 * Created by Vu on 4/3/2016.
 */
public class Fleet {
    public int numShips = 1;
    public Ship[] ships = new Ship[numShips];

    public int playerNum;

    public Fleet(int playerNum) {
        ships[0] = new Ship(playerNum, ShipType.BIG_BOY);

        this.playerNum = playerNum;
    }
}
