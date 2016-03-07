package team2.shattlebip;

/**
 * Created by Vu on 3/6/2016.
 */
public class GameState {
    public String gameStage;
    public int turnNum;

    public GameState(String gameStage, int turnNum) {
        this.gameStage = gameStage;
        this.turnNum = turnNum;
    }

    public int getWhoseTurn() {
        if (turnNum % 2 == 1)
            return 1;
        else
            return 2;
    }
}
