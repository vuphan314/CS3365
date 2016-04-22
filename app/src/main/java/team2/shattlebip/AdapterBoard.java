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
 * @author Vu
 */
public class AdapterBoard extends ArrayAdapter<Cell> {
    LayoutInflater inflater;

    public AdapterBoard(Context context, List<Cell> objects) {
        super(context, -1, objects);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.layout_cell, parent, false);
        Cell cell = getItem(position);

        Button button = (Button) view.findViewById(R.id.button_board_cell);

        if (cell.getStatus() == Cell.Status.HIT)
            button.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorHit));
        else if (cell.getStatus() == Cell.Status.MISSED)
            button.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorMissed));

        else if (cell.getPlayerNum() == 2)
            button.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorUnknown));

        else if (cell.getStatus() == Cell.Status.VACANT)
            button.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorVacant));
        else
            button.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorOccupied));

        return view;
    }

    public void createBoard(int playerNum, GridView grid, int cells) {
        grid.setAdapter(this);
        for (int i = 0; i < cells; i++)
            this.add(new Cell(playerNum, Cell.Status.VACANT));
    }
}
