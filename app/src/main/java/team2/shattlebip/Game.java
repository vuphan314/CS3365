package team2.shattlebip;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.Random;

/**
 * @author Vu
 */
public class Game {
    private static Game instance;
    Stage stage;
    Context context;
    int numCells1side;
    TextView textViewGameStage, textViewMessage;
    Button buttonAttack, buttonUpgrade, buttonRestart;
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
                          TextView textViewGameStage, TextView textViewMessage,
                          Button buttonAttack, Button buttonUpgrade,
                          Button buttonRestart,
                          GridView gridViewBoard1, GridView gridViewBoard2,
                          AdapterBoard adapterBoard1, AdapterBoard adapterBoard2,
                          Player player1, Player player2) {
        this.context = context;
        this.numCells1side = numCells1side;
        this.textViewGameStage = textViewGameStage;
        this.textViewMessage = textViewMessage;
        this.buttonAttack = buttonAttack;
        this.buttonUpgrade = buttonUpgrade;
        this.buttonRestart = buttonRestart;
        this.gridViewBoard1 = gridViewBoard1;
        this.gridViewBoard2 = gridViewBoard2;
        this.adapterBoard1 = adapterBoard1;
        this.adapterBoard2 = adapterBoard2;
        this.player1 = player1;
        this.player2 = player2;
    }

    public void initialize() {
        buttonRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialize();
            }
        });

        buttonAttack.setOnClickListener(null);
        buttonUpgrade.setOnClickListener(null);
        gridViewBoard1.setOnItemClickListener(null);
        gridViewBoard2.setOnItemClickListener(null);

        adapterBoard1.clear();
        adapterBoard2.clear();
        adapterBoard1.addCells(gridViewBoard1, 1, getNumCellsBoardArea());
        adapterBoard2.addCells(gridViewBoard2, 2, getNumCellsBoardArea());

        player1 = new Player(1);
        player2 = new Player(2);

        letP2arrange();
    }

    private void letP2arrange() {
//        generateShipPlacementDeprecated();

        //TODO uncomment after Zach fixes errors
        MathModel.generateShipPlacement(player2, adapterBoard2, numCells1side);

        enableGameStageArranging();
    }

    private void generateShipPlacementDeprecated() {
        Random random = new Random();
        for (int i = 0; i < player2.getNumShips(); i++) {
            Ship ship = player2.getShips().get(i);
            int randomRow = i * 2 + random.nextInt(2),
                    randomColumn = random.nextInt(2),
                    randomPosition = randomRow * numCells1side + randomColumn;
            for (int j = 0; j < ship.getNumCells(); j++) {
                Cell cell = adapterBoard2.getItem(randomPosition + j);
                cell.setStatus(Cell.Status.OCCUPIED);
                ship.addCell(cell);
            }
        }
    }

    private void enableGameStageArranging() {
        putGameStage(Stage.ARRANGING);

        letP1arrange();
    }

    private void letP1arrange() {
        gridViewBoard1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cell cell = (Cell) parent.getAdapter().getItem(position);
                player1.addCell(cell);
                adapterBoard1.notifyDataSetChanged();

                if (player1.canAddCell())
                    setMessage(player1.getShips().get(player1.getNumShipsArranged()).getNumCellsToAdd() +
                            " cell(s) left to add to your ship " +
                            (player1.getNumShipsArranged() + 1));
                else {
                    gridViewBoard1.setOnItemClickListener(null);
                    enableGameStageBattling();

                    //TODO uncomment after Paul fixes errors
//                    checkArrange();
                }
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
        putGameStage(Stage.BATTLING);

        enableGameStageAttacking();

        buttonUpgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player1.upgrade();

                buttonUpgrade.setOnClickListener(null);

                letP2attack();
            }
        });
    }

    private void enableGameStageAttacking() {
        buttonAttack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putGameStage(Stage.ATTACKING);

                buttonAttack.setOnClickListener(null);

                letP1attack();
            }
        });
    }

    private void letP1attack() {
        gridViewBoard2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cell cell = (Cell) parent.getAdapter().getItem(position);
                player1.attackCell(cell);
                adapterBoard2.notifyDataSetChanged();

                if (!player2.isAlive())
                    setMessage("you won; click RESTART");
                else if (player1.canAttack()) {
                    Ship ship = player1.getNextShipCanAttack();
                    setMessage(ship.getNumAttacksLeft() + " attack(s) left for your ship " +
                            (player1.getShips().indexOf(ship) + 1));
                } else {
                    gridViewBoard2.setOnItemClickListener(null);
                    player1.resetNumsAttacksMade();
                    letP2attack();
                }
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
        adapterBoard1.notifyDataSetChanged();
        player2.resetNumsAttacksMade();

        if (!player1.isAlive())
            setMessage("you lost; click RESTART");
        else
            enableGameStageBattling();
    }

    private void putGameStage(Stage stage) {
        this.stage = stage;
        String msg = "Game stage: " + stage;
        textViewGameStage.setText(msg);
        describeGameStage();
    }

    private void describeGameStage() {
        String msg;
        if (stage == Stage.ARRANGING)
            msg = "tap cell on your board to arrange your " +
                    player1.getNumShips() + " ships";
        else if (stage == Stage.BATTLING)
            msg = "click ATTACK or UPGRADE";
        else
            msg = "tap cell on bot's board to attack its " +
                    player2.getNumShipsAlive() + " alive ship(s)";
        setMessage(msg);
    }

    private void setMessage(String msg) {
        textViewMessage.setText("Message: " + msg);
    }

    private int getNumCellsBoardArea() {
        return (int) Math.pow(numCells1side, 2);
    }

    public enum Stage {
        ARRANGING, BATTLING, ATTACKING
    }
}
