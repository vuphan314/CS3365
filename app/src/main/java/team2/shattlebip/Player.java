package team2.shattlebip;

/**
 * Created by Vu on 4/2/2016.
 */
public class Player {
    public int num;

    public int shipCount = 1;

    public Ship[] ships = new Ship[shipCount];

    public Player(int num) {
        this.num = num;

        ships[0] = new Ship(num, ShipType.BIG_BOY);
    }
}
