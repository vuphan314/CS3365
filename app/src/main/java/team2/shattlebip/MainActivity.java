package team2.shattlebip;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    GridView gridViewBoard;
    AdapterBoard adapterBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        gridViewBoard = (GridView) findViewById(R.id.gridView_board1);
//        adapterBoard = new AdapterBoard(this, new ArrayList<BoardCell>());
//        gridViewBoard.setAdapter(adapterBoard);
//        initializeBoard(1);
        inflateBoard(1);
        inflateBoard(2);
    }

    public void inflateBoard(int playerNum) {
        if (playerNum == 1)
            gridViewBoard = (GridView) findViewById(R.id.gridView_board1);
        else
            gridViewBoard = (GridView) findViewById(R.id.gridView_board2);
        adapterBoard = new AdapterBoard(this, new ArrayList<BoardCell>());
        gridViewBoard.setAdapter(adapterBoard);

        initializeBoard(playerNum);
    }

    public void initializeBoard(int playerNum) {
        String boardCellsStatus;
        for (int i = 0; i < Math.pow(getResources().getInteger(R.integer.board_side_cells_count), 2); i++) {
            if (playerNum == 1)
                boardCellsStatus = getResources().getString(R.string.status_vacant);
            else
                boardCellsStatus = getResources().getString(R.string.status_unknown);
            BoardCell boardCell = new BoardCell(boardCellsStatus);
            adapterBoard.add(boardCell);
        }
    }
}
