package team2.shattlebip;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int numCells1side, numCells1board;
    GameState gameState;
    TextView textViewGameStage;
    Button buttonArrange, buttonBattle, buttonRestart;
    GridView gridViewBoard1, gridViewBoard2;
    AdapterBoard adapterBoard1, adapterBoard2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numCells1side = getResources().getInteger(R.integer.board_side_cells_count);
        numCells1board = (int) Math.pow(numCells1side, 2);
        gameState = new GameState(getString(R.string.game_stage_initialized));
        textViewGameStage = (TextView) findViewById(R.id.text_view_game_stage);
        buttonArrange = (Button) findViewById(R.id.button_arrange);
        buttonBattle = (Button) findViewById(R.id.button_battle);
        buttonRestart = (Button) findViewById(R.id.button_restart);
        gridViewBoard1 = (GridView) findViewById(R.id.gridViewBoard1);
        gridViewBoard2 = (GridView) findViewById(R.id.gridViewBoard2);
        adapterBoard1 = new AdapterBoard(this, new ArrayList<BoardCell>());
        adapterBoard2 = new AdapterBoard(this, new ArrayList<BoardCell>());

        startGame();
        enableGameRestart();
    }

    public void startGame() {
        gameState.gameStage = getString(R.string.game_stage_initialized);
        notifyGameStage();

        createBoard(1);
        createBoard(2);
        letP2arrange();

        enableGameStageArranging();
    }

    public void createBoard(int playerNum) {
        getGridViewBoard(playerNum).setAdapter(getAdapterBoard(playerNum));
        String boardCellsStatus;
        for (int i = 0; i < numCells1board; i++) {
            boardCellsStatus = getString(R.string.board_cell_status_vacant);
            BoardCell boardCell = new BoardCell(playerNum, boardCellsStatus);
            getAdapterBoard(playerNum).add(boardCell);
        }
    }

    public void letP2arrange() {
        Random random = new Random();
        int cell1 = random.nextInt(4);
        for (int i = 0; i < 2; i++) {
            BoardCell boardCell = adapterBoard2.getItem(cell1 + i);
            boardCell.boardCellStatus = getString(R.string.board_cell_status_occupied);
        }
        adapterBoard1.notifyDataSetChanged();
    }

    public void enableGameStageArranging() {
        buttonArrange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameState.gameStage = getString(R.string.game_stage_arranging);
                notifyGameStage();

                letP1arrange();

                enableGameStageBattling();
            }
        });
    }

    public void letP1arrange() {
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

    public void enableGameStageBattling() {
        buttonBattle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameState.gameStage = getString(R.string.game_stage_battling);
                notifyGameStage();

                buttonArrange.setOnClickListener(null);
                gridViewBoard1.setOnItemClickListener(null);

                letP1attack();
            }
        });
    }

    public void letP1attack() {
        gridViewBoard2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BoardCell boardCell = (BoardCell) parent.getAdapter().getItem(position);
                attackCell(boardCell);
                adapterBoard2.notifyDataSetChanged();
                letP2attack();
            }
        });
    }

    public void letP2attack() {
        Random random = new Random();
        BoardCell boardCell = adapterBoard1.getItem(random.nextInt(numCells1board));
        attackCell(boardCell);
        adapterBoard1.notifyDataSetChanged();
    }

    public void attackCell(BoardCell boardCell) {
        if (boardCell.boardCellStatus.equals(getString(R.string.board_cell_status_occupied)))
            boardCell.boardCellStatus = getString(R.string.board_cell_status_hit);
        if (boardCell.boardCellStatus.equals(getString(R.string.board_cell_status_vacant)))
            boardCell.boardCellStatus = getString(R.string.board_cell_status_missed);
    }

    public void enableGameRestart() {
        buttonRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameState.gameStage = getString(R.string.game_stage_initialized);
                notifyGameStage();

                buttonArrange.setOnClickListener(null);
                gridViewBoard1.setOnItemClickListener(null);
                buttonBattle.setOnClickListener(null);
                gridViewBoard2.setOnItemClickListener(null);

                clearBoard(1);
                clearBoard(2);
                letP2arrange();

                enableGameStageArranging();
            }
        });
    }

    public void clearBoard(int playerNum) {
        AdapterBoard adapterBoard = getAdapterBoard(playerNum);
        for (int i = 0; i < adapterBoard.getCount(); i++)
            adapterBoard.getItem(i).boardCellStatus = getString(R.string.board_cell_status_vacant);
        adapterBoard.notifyDataSetChanged();
    }

    public void notifyGameStage() {
        String message = "Game stage: " + gameState.gameStage;
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        textViewGameStage.setText(message);
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
