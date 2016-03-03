package team2.shattlebip;

import android.content.Context;
import android.widget.Button;

/**
 * Created by Vu on 3/2/2016.
 */
public class GridCell extends Button {
    public GridCell(Context context, String status) {
        super(context);

        this.status = status;
        setAppearance();
    }

    public String status;

    public void setAppearance() {
        if (status == getResources().getString(R.string.status_vacant)) {
            setBackgroundColor(getResources().getColor(R.color.colorVacant));
        } else if (status == getResources().getString(R.string.status_occupied)) {
            setBackgroundColor(getResources().getColor(R.color.colorOccupied));
        }
    }
}
