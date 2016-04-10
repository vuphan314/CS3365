package team2.shattlebip;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    int numCells1side;
    TextView textViewGameStage;
    Button buttonArrange, buttonBattle, buttonRestart;
    GridView gridViewBoard1, gridViewBoard2;
    AdapterBoard adapterBoard1, adapterBoard2;
    Fleet fleet1, fleet2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeVariables();
        enableGame();
    }

    public void enableGame() {
        Game game = new Game(this, numCells1side,
                textViewGameStage, buttonArrange, buttonBattle, buttonRestart,
                gridViewBoard1, gridViewBoard2, adapterBoard1, adapterBoard2, fleet1, fleet2
        );

        game.start();
        game.enableGameRestart();
    }

    public void initializeVariables() {
        numCells1side = getResources().getInteger(R.integer.board_side_cells_count);
        textViewGameStage = (TextView) findViewById(R.id.text_view_game_stage);
        buttonArrange = (Button) findViewById(R.id.button_arrange);
        buttonBattle = (Button) findViewById(R.id.button_battle);
        buttonRestart = (Button) findViewById(R.id.button_restart);
//        buttonUpgrade = (Button) findViewById(R.id.button_upgrade);
        gridViewBoard1 = (GridView) findViewById(R.id.gridViewBoard1);
        gridViewBoard2 = (GridView) findViewById(R.id.gridViewBoard2);
        adapterBoard1 = new AdapterBoard(this, new ArrayList<Cell>());
        adapterBoard2 = new AdapterBoard(this, new ArrayList<Cell>());
    }
}
