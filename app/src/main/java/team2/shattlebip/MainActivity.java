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
    Button buttonArrange, buttonBattle, buttonRestart, buttonUpgrade;
    GridView gridViewBoard1, gridViewBoard2;
    AdapterBoard adapterBoard1, adapterBoard2;

    Fleet fleet1, fleet2;
    Ship ship1tmp, ship2tmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numCells1side = getResources().getInteger(R.integer.board_side_cells_count);
        numCells1board = (int) Math.pow(numCells1side, 2);
        gameState = new GameState(GameStage.INITIALIZED);
        textViewGameStage = (TextView) findViewById(R.id.text_view_game_stage);
        buttonArrange = (Button) findViewById(R.id.button_arrange);
        buttonBattle = (Button) findViewById(R.id.button_battle);
        buttonRestart = (Button) findViewById(R.id.button_restart);
        buttonUpgrade = (Button) findViewById(R.id.button_upgrade);
        gridViewBoard1 = (GridView) findViewById(R.id.gridViewBoard1);
        gridViewBoard2 = (GridView) findViewById(R.id.gridViewBoard2);
        adapterBoard1 = new AdapterBoard(this, new ArrayList<Cell>());
        adapterBoard2 = new AdapterBoard(this, new ArrayList<Cell>());

        fleet1 = new Fleet(1);
        fleet2 = new Fleet(2);
        ship1tmp = fleet1.ships[0];
        ship2tmp = fleet2.ships[0];

        startGame();
        enableGameRestart();
    }

    public void startGame() {
        gameState.gameStage = GameStage.INITIALIZED;
        notifyGameStage();

        createBoard(1);
        createBoard(2);
        letP2arrange();

        enableGameStageArranging();
    }

    public void createBoard(int playerNum) {
        getGridViewBoard(playerNum).setAdapter(getAdapterBoard(playerNum));
        CellStatus boardCellsStatus;
        for (int i = 0; i < numCells1board; i++) {
            boardCellsStatus = CellStatus.VACANT;
            Cell cell = new Cell(playerNum, boardCellsStatus);
            getAdapterBoard(playerNum).add(cell);
        }
    }

    public void letP2arrange() {
        Random random = new Random();
        int cellPos = random.nextInt(1); //todo randomize
        for (int i = 0; i < ship2tmp.occupancy; i++) {
            Cell cell = adapterBoard2.getItem(cellPos + i);
            cell.cellStatus = CellStatus.OCCUPIED;
            ship2tmp.cells[i] = cell;
        }
        adapterBoard1.notifyDataSetChanged();
    }

    public void enableGameStageArranging() {
        buttonArrange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameState.gameStage = GameStage.ARRANGING;
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
                Cell cell = (Cell) parent.getAdapter().getItem(position);
                cell.cellStatus = CellStatus.OCCUPIED; //todo allow reset ship arrangement
                adapterBoard1.notifyDataSetChanged();

                ship1tmp.cells[ship1tmp.vacantCellPosition++] = cell;
            }
        });
    }

    public void enableGameStageBattling() {
        buttonBattle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameState.gameStage = GameStage.BATTLING;
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
                Cell cell = (Cell) parent.getAdapter().getItem(position);
                attackCell(cell);
                adapterBoard2.notifyDataSetChanged();

                ship1tmp.attack();
                toast(Integer.toString(ship1tmp.getNumAttacksLeft()));

                if (!ship1tmp.canAttack()) {
                    letP2attack();
                    ship1tmp.resetNumAttacksMade();
                }

            }
        });
    }

    public void letP2attack() {
        Random random = new Random();
        while (ship2tmp.canAttack()) {
            ship2tmp.attack();
            Cell cell = adapterBoard1.getItem(random.nextInt(numCells1board));
            attackCell(cell);
        }
        ship2tmp.resetNumAttacksMade();
        adapterBoard1.notifyDataSetChanged();
    }

    public void attackCell(Cell cell) {
        if (cell.cellStatus == CellStatus.OCCUPIED)
            cell.cellStatus = CellStatus.HIT;
        if (cell.cellStatus == CellStatus.VACANT)
            cell.cellStatus = CellStatus.MISSED;
    }

    public void enableGameRestart() {
        buttonRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameState.gameStage = GameStage.INITIALIZED;
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
            adapterBoard.getItem(i).cellStatus = CellStatus.VACANT;
        adapterBoard.notifyDataSetChanged();
    }

    public void notifyGameStage() {
        String message = "Game stage: " + gameState.gameStage;
        toast(message);
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

    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
