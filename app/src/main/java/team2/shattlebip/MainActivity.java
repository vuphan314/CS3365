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
    Game game;
    TextView textViewGameStage;
    Button buttonArrange, buttonBattle, buttonRestart, buttonUpgrade;
    GridView gridViewBoard1, gridViewBoard2;
    AdapterBoard adapterBoard1, adapterBoard2;
    Fleet fleet1, fleet2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numCells1side = getResources().getInteger(R.integer.board_side_cells_count);
        numCells1board = (int) Math.pow(numCells1side, 2);
        game = new Game(GameStage.INITIALIZED);
        textViewGameStage = (TextView) findViewById(R.id.text_view_game_stage);
        buttonArrange = (Button) findViewById(R.id.button_arrange);
        buttonBattle = (Button) findViewById(R.id.button_battle);
        buttonRestart = (Button) findViewById(R.id.button_restart);
//        buttonUpgrade = (Button) findViewById(R.id.button_upgrade);
        gridViewBoard1 = (GridView) findViewById(R.id.gridViewBoard1);
        gridViewBoard2 = (GridView) findViewById(R.id.gridViewBoard2);
        adapterBoard1 = new AdapterBoard(this, new ArrayList<Cell>());
        adapterBoard2 = new AdapterBoard(this, new ArrayList<Cell>());

        startGame();
        enableGameRestart();
    }

    public void startGame() {
        setGameStage(GameStage.INITIALIZED);

        instantiateFleets();
        createBoard(1);
        createBoard(2);

        letP2arrange();
        enableGameStageArranging();
    }

    public void createBoard(int playerNum) {
        getGridViewBoard(playerNum).setAdapter(getAdapterBoard(playerNum));
        CellStatus cellsStatus;
        for (int i = 0; i < numCells1board; i++) {
            cellsStatus = CellStatus.VACANT;
            Cell cell = new Cell(playerNum, cellsStatus);
            getAdapterBoard(playerNum).add(cell);
        }
    }

    public void letP2arrange() {
        Random random = new Random();
        for (int i = 0; i < fleet2.numShips; i++) {
            Ship ship = fleet2.ships.get(i);
            int randomRow = i * 2 + random.nextInt(2), randomColumn = random.nextInt(2),
                    randomPosition = randomRow * numCells1side + randomColumn;
            for (int j = 0; j < ship.numCells; j++) {
                Cell cell = adapterBoard2.getItem(randomPosition + j);
                cell.cellStatus = CellStatus.OCCUPIED;
                ship.addCell(cell);
            }
        }
    }

    public void enableGameStageArranging() {
        buttonArrange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGameStage(GameStage.ARRANGING);

                letP1arrange();

//                todo enable randomizing fleet2
//                MathModel.generateShipPlacement(adapterBoard2, gridViewBoard2.getNumColumns());
            }
        });
    }

    public void letP1arrange() {
        gridViewBoard1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cell cell = (Cell) parent.getAdapter().getItem(position);
                if (fleet1.canAddCell()) {
                    fleet1.addCell(cell);
                    String msg;
                    if (fleet1.canAddCell())
                        msg = fleet1.ships.get(fleet1.numShipsArranged).getNumCellsToAdd() +
                                " cell(s) left to add to your ship " +
                                (fleet1.numShipsArranged + 1);
                    else
                        msg = "now click BATTLE";
                    makeToast(msg);
                }
                adapterBoard1.notifyDataSetChanged();

                checkArrange();
            }
        });
    }

    public void checkArrange() {
        ShipArrangement shipArr = new ShipArrangement();
        AdapterBoard adapterBoard = adapterBoard1;
        int c = 0;
        for (int i = 0; i < adapterBoard.getCount(); i++) {
            Cell cell = adapterBoard1.getItem(i);
            if (cell.cellStatus == CellStatus.OCCUPIED) {
                c = c + 1;
            }
        }
        if (c == 10) {
            if (((shipArr.checkArrangeLH(adapterBoard)) || (shipArr.checkArrangeLV(adapterBoard)))) {
                if (((shipArr.checkArrangeMH(adapterBoard)) || (shipArr.checkArrangeMV(adapterBoard)))) {
                    if (((shipArr.checkArrangeSH(adapterBoard)) || (shipArr.checkArrangeSV(adapterBoard)))) {
                        enableGameStageBattling();
                    }
                }
            }
        }
    }


    public void enableGameStageBattling() {
        buttonBattle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGameStage(GameStage.BATTLING);

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
                if (fleet1.canAttack()) {
                    fleet1.attackCell(cell);
                    String msg;
                    if (!fleet2.isAlive())
                        msg = "you won; click RESTART";
                    else if (fleet1.canAttack()) {
                        Ship ship = fleet1.getNextShipCanAttack();
                        msg = ship.getNumAttacksLeft() + " attack(s) left for your ship " +
                                (fleet1.ships.indexOf(ship) + 1);
                    } else {
                        fleet1.resetNumsAttacksMade();
                        letP2attack();
                        if (!fleet1.isAlive())
                            msg = "you lost; click RESTART";
                        else
                            msg = "bot done; your turn again";
                    }
                    makeToast(msg);
                }
                adapterBoard2.notifyDataSetChanged();
            }
        });
    }

    public void letP2attack() {
        Random random = new Random();
        while (fleet2.canAttack()) {
            Cell cell;
            do {
                cell = adapterBoard1.getItem(random.nextInt(numCells1board));
            }
            while (cell.cellStatus == CellStatus.HIT || cell.cellStatus == CellStatus.MISSED);
            fleet2.attackCell(cell);
        }
        fleet2.resetNumsAttacksMade();
        adapterBoard1.notifyDataSetChanged();
    }

    public void enableGameRestart() {
        buttonRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGameStage(GameStage.INITIALIZED);

                buttonArrange.setOnClickListener(null);
                gridViewBoard1.setOnItemClickListener(null);
                buttonBattle.setOnClickListener(null);
                gridViewBoard2.setOnItemClickListener(null);

                instantiateFleets();
                clearBoard(1);
                clearBoard(2);

                letP2arrange();
                enableGameStageArranging();
            }
        });
    }

    public void instantiateFleets() {
        fleet1 = new Fleet(1);
        fleet2 = new Fleet(2);
    }

    public void clearBoard(int playerNum) {
        AdapterBoard adapterBoard = getAdapterBoard(playerNum);
        for (int i = 0; i < adapterBoard.getCount(); i++)
            adapterBoard.getItem(i).cellStatus = CellStatus.VACANT;
        adapterBoard.notifyDataSetChanged();
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

    public void setGameStage(GameStage gameStage) {
        game.gameStage = gameStage;
        String msg = "Game stage: " + game.gameStage;
        textViewGameStage.setText(msg);
        describeGameStage();
    }

    public void describeGameStage() {
        String msg;
        if (game.gameStage == GameStage.INITIALIZED)
            msg = "click ARRANGE";
        else if (game.gameStage == GameStage.ARRANGING)
            msg = "tap cell on your board to arrange your " + fleet1.numShips + " ships";
        else
            msg = "tap cell on bot's board to attack its " + fleet2.numShips + " ships";
        makeToast(msg);
    }

    public void makeToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
