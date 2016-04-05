package team2.shattlebip;

/**
 * Updated by Vu on 4/2/2016.
 */
public class GameState {
    public GameStage gameStage;
    private static GameState ourInstance;

    private GameState()
    {

    }

    public GameState(GameStage gameStage) {
        this.gameStage = gameStage;
    }

    public static GameState getInstance() {
        if (ourInstance == null)
            ourInstance = new GameState();
        return ourInstance;
    }
}
