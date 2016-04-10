package team2.shattlebip;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

/**
 * Updated by Vu
 */
public class Game {
    Context context;
    GameStage gameStage;
    int numCells1side;
    TextView textViewGameStage;
    Button buttonArrange, buttonBattle, buttonRestart;
    GridView gridViewBoard1, gridViewBoard2;
    AdapterBoard adapterBoard1, adapterBoard2;
    Fleet fleet1, fleet2;

    public Game(Context context, int numCells1side,
                TextView textViewGameStage,
                Button buttonArrange, Button buttonBattle, Button buttonRestart,
                GridView gridViewBoard1, GridView gridViewBoard2,
                AdapterBoard adapterBoard1, AdapterBoard adapterBoard2,
                Fleet fleet1, Fleet fleet2) {
        this.context = context;
        this.numCells1side = numCells1side;
        this.textViewGameStage = textViewGameStage;
        this.buttonArrange = buttonArrange;
        this.buttonBattle = buttonBattle;
        this.buttonRestart = buttonRestart;
        this.gridViewBoard1 = gridViewBoard1;
        this.gridViewBoard2 = gridViewBoard2;
        this.adapterBoard1 = adapterBoard1;
        this.adapterBoard2 = adapterBoard2;
        this.fleet1 = fleet1;
        this.fleet2 = fleet2;
    }

    public void start() {
        putGameStage(GameStage.INITIALIZED);

        instantiateFleets();
        createBoard(1);
        createBoard(2);

        letP2arrange();
        enableGameStageArranging();
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
                putGameStage(GameStage.ARRANGING);

                letP1arrange();

                //TODO enable randomizing fleet2 after tmpzach passes tests
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
                putGameStage(GameStage.BATTLING);

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
                cell = adapterBoard1.getItem(random.nextInt(getNumCells1board()));
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
                putGameStage(GameStage.INITIALIZED);

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

    public void createBoard(int playerNum) {
        getGridViewBoard(playerNum).setAdapter(getAdapterBoard(playerNum));
        CellStatus cellsStatus;
        for (int i = 0; i < getNumCells1board(); i++) {
            cellsStatus = CellStatus.VACANT;
            Cell cell = new Cell(playerNum, cellsStatus);
            getAdapterBoard(playerNum).add(cell);
        }
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

    public void putGameStage(GameStage gameStage) {
        this.gameStage = gameStage;
        String msg = "Game stage: " + gameStage;
        textViewGameStage.setText(msg);
        describeGameStage();
    }

    public void describeGameStage() {
        String msg;
        if (gameStage == GameStage.INITIALIZED)
            msg = "click ARRANGE";
        else if (gameStage == GameStage.ARRANGING)
            msg = "tap cell on your board to arrange your " + fleet1.numShips + " ships";
        else
            msg = "tap cell on bot's board to attack its " + fleet2.numShips + " ships";
        makeToast(msg);
    }

    public void makeToast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public int getNumCells1board() {
        return (int) Math.pow(numCells1side, 2);
    }
}
