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
public class BoardAdapter extends ArrayAdapter<BoardCell> {
    LayoutInflater inflater;

    public BoardAdapter(Context context, List<BoardCell> objects) {
        super(context, -1, objects);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.board_cell_layout, parent, false);
        BoardCell boardCell = (BoardCell) getItem(position);
        Button button = (Button) view.findViewById(R.id.board_cell_button);

        if (boardCell.status.equals(getContext().getResources().getString(R.string.status_vacant)))
            button.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorVacant));
        else if (boardCell.status.equals(getContext().getResources().getString(R.string.status_occupied)))
            button.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorOccupied));

        return view;
    }
}
