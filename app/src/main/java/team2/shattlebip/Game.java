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
 * @author Vu
 */
public class Game {
    private static Game instance;
    Context context;
    Stage stage;
    int numCells1side;
    TextView textViewGameStage;
    Button buttonArrange, buttonBattle, buttonRestart;
    GridView gridViewBoard1, gridViewBoard2;
    AdapterBoard adapterBoard1, adapterBoard2;
    Player player1, player2;

    private Game() {
    }

    public static Game getInstance() {
        if (instance == null)
            instance = new Game();
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
        putGameStage(Stage.INITIALIZED);

        instantiateFleets();
        adapterBoard1.createBoard(1, gridViewBoard1, getNumCellsBoardArea());
        adapterBoard2.createBoard(2, gridViewBoard2, getNumCellsBoardArea());

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
                Cell cell = adapterBoard2.getItem(randomPosition + j);
                cell.setStatus(Cell.Status.OCCUPIED);
                ship.addCell(cell);
            }
        }
    }

    private void enableGameStageArranging() {
        buttonArrange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putGameStage(Stage.ARRANGING);

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
                Cell cell = (Cell) parent.getAdapter().getItem(position);
                if (player1.canAddCell()) {
                    player1.addCell(cell);
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
            Cell cell = adapterBoard1.getItem(i);
            if (cell.getStatus() == Cell.Status.OCCUPIED) {
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
                putGameStage(Stage.BATTLING);

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
                Cell cell = (Cell) parent.getAdapter().getItem(position);
                if (player1.canAttack()) {
                    player1.attackCell(cell);
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
            Cell cell;
            do {
                cell = adapterBoard1.getItem(random.nextInt(getNumCellsBoardArea()));
            }
            while (cell.getStatus() == Cell.Status.HIT ||
                    cell.getStatus() == Cell.Status.MISSED);
            player2.attackCell(cell);
        }
        player2.resetNumsAttacksMade();
        adapterBoard1.notifyDataSetChanged();
    }

    public void enableGameRestart() {
        buttonRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putGameStage(Stage.INITIALIZED);

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
            adapterBoard.getItem(i).setStatus(Cell.Status.VACANT);
        adapterBoard.notifyDataSetChanged();
    }

    private AdapterBoard getAdapterBoard(int playerNum) {
        if (playerNum == 1)
            return adapterBoard1;
        else
            return adapterBoard2;
    }

    private void putGameStage(Stage stage) {
        this.stage = stage;
        String msg = "Game stage: " + stage;
        textViewGameStage.setText(msg);
        describeGameStage();
    }

    private void describeGameStage() {
        String msg;
        if (stage == Stage.INITIALIZED)
            msg = "click ARRANGE";
        else if (stage == Stage.ARRANGING)
            msg = "tap cell on your board to arrange your " + player1.getNumShips() + " ships";
        else
            msg = "tap cell on bot's board to attack its " + player2.getNumShips() + " ships";
        makeToast(msg);
    }

    private void makeToast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    private int getNumCellsBoardArea() {
        return (int) Math.pow(numCells1side, 2);
    }

    public enum Stage {
        INITIALIZED, ARRANGING, BATTLING
    }
}
