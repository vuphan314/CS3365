package team2.shattlebip;

/**
 * Created by Zach on 3/6/2016.
 */
public class GameState
{
    private Player player;
    private Player player2;


    public GameState(boolean isComputer)
    {
        player = new Player();
        if (isComputer)
            player2 = new Computer();
        else
            player2 = new Player2();
    }

}
