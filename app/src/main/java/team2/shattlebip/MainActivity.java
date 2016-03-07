package team2.shattlebip;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    GameState gameState;
    AdapterBoard adapterBoard1, adapterBoard2;

    GridView gridViewBoard1, gridViewBoard2;
    Button buttonArrange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameState = new GameState(getString(R.string.game_stage_initialized), -1);

        adapterBoard1 = new AdapterBoard(this, new ArrayList<BoardCell>());
        adapterBoard2 = new AdapterBoard(this, new ArrayList<BoardCell>());

        gridViewBoard1 = (GridView) findViewById(R.id.gridViewBoard1);
        gridViewBoard2 = (GridView) findViewById(R.id.gridViewBoard2);

        buttonArrange = (Button) findViewById(R.id.button_arrange);

        initializeGame();
        toastStage();

        buttonArrange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameState.gameStage = getString(R.string.game_stage_arranging);
                toastStage();

                gridViewBoard1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        BoardCell boardCell = (BoardCell) parent.getAdapter().getItem(position);
                        if (boardCell.boardCellStatus.equals(getString(R.string.board_cell_status_vacant)))
                            boardCell.boardCellStatus = getString(R.string.board_cell_status_occupied);
                        else
                            boardCell.boardCellStatus = getString(R.string.board_cell_status_vacant);
                        adapterBoard1.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    public void initializeGame() {
        inflateBoard(1);
        inflateBoard(2);
    }

    public void inflateBoard(int playerNum) {
        getGridViewBoard(playerNum).setAdapter(getAdapterBoard(playerNum));
        initializeBoard(playerNum);
    }

    public void initializeBoard(int playerNum) {
        for (int i = 0; i < Math.pow(R.integer.board_side_cells_count, 2); i++) {
            BoardCell boardCell = new BoardCell(playerNum, getString(R.string.board_cell_status_vacant));
            getAdapterBoard(playerNum).add(boardCell);
        }
    }

    public GridView getGridViewBoard(int playerNum) {
        if (playerNum == 1)
            return gridViewBoard1;
        else
            return gridViewBoard2;
    }

    public AdapterBoard getAdapterBoard(int playerNum) {
        if (playerNum == 1)
            return adapterBoard1;
        else
            return adapterBoard2;
    }

    public void toastStage() {
        Toast.makeText(this, gameState.gameStage, Toast.LENGTH_SHORT).show();
    }
}
