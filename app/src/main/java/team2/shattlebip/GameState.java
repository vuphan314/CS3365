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
public class GameState {
    private static GameState instance;
    Context context;
    GameStage gameStage;
    int numCells1side;
    TextView textViewGameStage;
    Button buttonArrange, buttonBattle, buttonRestart;
    GridView gridViewBoard1, gridViewBoard2;
    AdapterBoard adapterBoard1, adapterBoard2;
    Player player1, player2;

    private GameState() {
    }

    public static GameState getInstance() {
        if (instance == null)
            instance = new GameState();
        return instance;
    }

    public void setFields(Context context, int numCells1side,
                          TextView textViewGameStage,
                          Button buttonArrange, Button buttonBattle, Button buttonRestart,
                          GridView gridViewBoard1, GridView gridViewBoard2,
                          AdapterBoard adapterBoard1, AdapterBoard adapterBoard2,
                          Player player1, Player player2) {
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
        this.player1 = player1;
        this.player2 = player2;
    }

    public void start() {
        putGameStage(GameStage.INITIALIZED);

        instantiateFleets();
        adapterBoard1.createBoard(1, gridViewBoard1, getNumCells1board());
        adapterBoard2.createBoard(2, gridViewBoard2, getNumCells1board());

        letP2arrange();
        enableGameStageArranging();
    }

    private void letP2arrange() {
        Random random = new Random();
        for (int i = 0; i < player2.getNumShips(); i++) {
            Ship ship = player2.getShips().get(i);
            int randomRow = i * 2 + random.nextInt(2), randomColumn = random.nextInt(2),
                    randomPosition = randomRow * numCells1side + randomColumn;
            for (int j = 0; j < ship.getNumCells(); j++) {
                BoardCell boardCell = adapterBoard2.getItem(randomPosition + j);
                boardCell.setBoardCellStatus(BoardCellStatus.OCCUPIED);
                ship.addCell(boardCell);
            }
        }
    }

    private void enableGameStageArranging() {
        buttonArrange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putGameStage(GameStage.ARRANGING);

                letP1arrange();

                //TODO uncomment after Zach fixes errors
//                MathModel.generateShipPlacement(adapterBoard2, gridViewBoard2.getNumColumns());
            }
        });
    }

    private void letP1arrange() {
        gridViewBoard1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BoardCell boardCell = (BoardCell) parent.getAdapter().getItem(position);
                if (player1.canAddCell()) {
                    player1.addCell(boardCell);
                    String msg;
                    if (player1.canAddCell())
                        msg = player1.getShips().get(player1.getNumShipsArranged()).getNumCellsToAdd() +
                                " cell(s) left to add to your ship " +
                                (player1.getNumShipsArranged() + 1);
                    else
                        msg = "now click BATTLE";
                    makeToast(msg);
                }
                adapterBoard1.notifyDataSetChanged();

                enableGameStageBattling();
                //TODO uncomment after Paul fixes errors
//                checkArrange();
            }
        });
    }

    private void checkArrange() {
        ShipArrangement shipArr = new ShipArrangement();
        AdapterBoard adapterBoard = adapterBoard1;
        int c = 0;
        for (int i = 0; i < adapterBoard.getCount(); i++) {
            BoardCell boardCell = adapterBoard1.getItem(i);
            if (boardCell.getBoardCellStatus() == BoardCellStatus.OCCUPIED) {
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

    private void enableGameStageBattling() {
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

    private void letP1attack() {
        gridViewBoard2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BoardCell boardCell = (BoardCell) parent.getAdapter().getItem(position);
                if (player1.canAttack()) {
                    player1.attackCell(boardCell);
                    String msg;
                    if (!player2.isAlive())
                        msg = "you won; click RESTART";
                    else if (player1.canAttack()) {
                        Ship ship = player1.getNextShipCanAttack();
                        msg = ship.getNumAttacksLeft() + " attack(s) left for your ship " +
                                (player1.getShips().indexOf(ship) + 1);
                    } else {
                        player1.resetNumsAttacksMade();
                        letP2attack();
                        if (!player1.isAlive())
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

    private void letP2attack() {
        Random random = new Random();
        while (player2.canAttack()) {
            BoardCell boardCell;
            do {
                boardCell = adapterBoard1.getItem(random.nextInt(getNumCells1board()));
            }
            while (boardCell.getBoardCellStatus() == BoardCellStatus.HIT ||
                    boardCell.getBoardCellStatus() == BoardCellStatus.MISSED);
            player2.attackCell(boardCell);
        }
        player2.resetNumsAttacksMade();
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

    private void instantiateFleets() {
        player1 = new Player(1);
        player2 = new Player(2);
    }

    private void clearBoard(int playerNum) {
        AdapterBoard adapterBoard = getAdapterBoard(playerNum);
        for (int i = 0; i < adapterBoard.getCount(); i++)
            adapterBoard.getItem(i).setBoardCellStatus(BoardCellStatus.VACANT);
        adapterBoard.notifyDataSetChanged();
    }

    private AdapterBoard getAdapterBoard(int playerNum) {
        if (playerNum == 1)
            return adapterBoard1;
        else
            return adapterBoard2;
    }

    private void putGameStage(GameStage gameStage) {
        this.gameStage = gameStage;
        String msg = "GameState stage: " + gameStage;
        textViewGameStage.setText(msg);
        describeGameStage();
    }

    private void describeGameStage() {
        String msg;
        if (gameStage == GameStage.INITIALIZED)
            msg = "click ARRANGE";
        else if (gameStage == GameStage.ARRANGING)
            msg = "tap cell on your board to arrange your " + player1.getNumShips() + " ships";
        else
            msg = "tap cell on bot's board to attack its " + player2.getNumShips() + " ships";
        makeToast(msg);
    }

    private void makeToast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    private int getNumCells1board() {
        return (int) Math.pow(numCells1side, 2);
    }
}
