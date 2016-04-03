package team2.shattlebip;

/**
 * Created by Vu on 4/2/2016.
 */
public class Player {
    public int playerNum;

    public int shipCount = 1;

    public Ship[] ships = new Ship[shipCount];

    public Player(int playerNum) {
        this.playerNum = playerNum;

        ships[0] = new Ship(playerNum, ShipType.LITTLE_GUY);
    }
}
