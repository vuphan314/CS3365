package team2.shattlebip;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.List;

/**
 * Created by Vu on 3/3/2016.
 */
public class AdapterBoard extends ArrayAdapter<Cell> {
    LayoutInflater inflater;

    public AdapterBoard(Context context, List<Cell> objects) {
        super(context, -1, objects);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.layout_board_cell, parent, false);
        Cell cell = getItem(position);

        Button button = (Button) view.findViewById(R.id.button_board_cell);

        if (cell.cellStatus == CellStatus.HIT)
            button.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorHit));
        else if (cell.cellStatus == CellStatus.MISSED)
            button.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorMissed));

        else if (cell.playerNum == 2)
            button.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorUnknown));

        else if (cell.cellStatus == CellStatus.VACANT)
            button.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorVacant));
        else
            button.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorOccupied));

        return view;
    }
}
