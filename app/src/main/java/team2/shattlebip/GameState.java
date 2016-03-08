package team2.shattlebip;

//  VU RESOLVED MERGE CONFLICT

//  commented out Zach's code
//public class GameState {
//    private Player player;
//    private Player player2;
//
//    public GameState(boolean isComputer) {
//        player = new Player();
//        if (isComputer)
//            player2 = new Computer();
//        else
//            player2 = new Player2();
//    }

//  kept Vu's code
public class GameState {
    public String gameStage;

    public GameState(String gameStage) {
        this.gameStage = gameStage;
    }
}
