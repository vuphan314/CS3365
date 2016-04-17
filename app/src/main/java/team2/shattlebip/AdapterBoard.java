package team2.shattlebip;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;

import java.util.List;

/**
 * Created by Vu on 3/3/2016.
 */
public class AdapterBoard extends ArrayAdapter<BoardCell> {
    LayoutInflater inflater;

    public AdapterBoard(Context context, List<BoardCell> objects) {
        super(context, -1, objects);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.layout_board_cell, parent, false);
        BoardCell boardCell = getItem(position);

        Button button = (Button) view.findViewById(R.id.button_board_cell);

        if (boardCell.getBoardCellStatus() == BoardCellStatus.HIT)
            button.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorHit));
        else if (boardCell.getBoardCellStatus() == BoardCellStatus.MISSED)
            button.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorMissed));

        else if (boardCell.getPlayerNum() == 2)
            button.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorUnknown));

        else if (boardCell.getBoardCellStatus() == BoardCellStatus.VACANT)
            button.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorVacant));
        else
            button.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorOccupied));

        return view;
    }

    public void createBoard(int playerNum, GridView grid, int cells) {
        grid.setAdapter(this);
        for (int i = 0; i < cells; i++)
            this.add(new BoardCell(playerNum, BoardCellStatus.VACANT));
    }
}
