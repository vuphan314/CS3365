package team2.shattlebip;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    GameState gameState;
    Button buttonArrange;
    GridView gridViewBoard1, gridViewBoard2;
    AdapterBoard adapterBoard1, adapterBoard2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameState = new GameState(getString(R.string.game_stage_initialized), -1);
        buttonArrange = (Button) findViewById(R.id.button_arrange);
        gridViewBoard1 = (GridView) findViewById(R.id.gridViewBoard1);
        gridViewBoard2 = (GridView) findViewById(R.id.gridViewBoard2);
        adapterBoard1 = new AdapterBoard(this, new ArrayList<BoardCell>());
        adapterBoard2 = new AdapterBoard(this, new ArrayList<BoardCell>());

        initializeGame();

        arrange();
    }

    public void initializeGame() {
        inflateBoard(1);
        inflateBoard(2);
        arrange2();
        toastStage();
    }

    public void inflateBoard(int playerNum) {
        getGridViewBoard(playerNum).setAdapter(getAdapterBoard(playerNum));
        initializeBoard(playerNum);
    }

    public void initializeBoard(int playerNum) {
        String boardCellsStatus;
        for (int i = 0; i < Math.pow(getResources().getInteger(R.integer.board_side_cells_count), 2); i++) {
            boardCellsStatus = getResources().getString(R.string.board_cell_status_vacant);
            BoardCell boardCell = new BoardCell(playerNum, boardCellsStatus);
            getAdapterBoard(playerNum).add(boardCell);
        }
    }

    public void arrange2() {
        Random random = new Random();
        int cell1 = random.nextInt(4);
        for (int i = 0; i < 2; i++) {
            BoardCell boardCell = adapterBoard2.getItem(cell1 + i);
            boardCell.boardCellStatus = getString(R.string.board_cell_status_occupied);
        }
        adapterBoard1.notifyDataSetChanged();
    }

    public void arrange() {
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

    public void toastStage() {
        Toast.makeText(this, gameState.gameStage, Toast.LENGTH_SHORT).show();
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
}
