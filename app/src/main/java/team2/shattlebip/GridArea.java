package team2.shattlebip;

import android.content.Context;
import android.widget.GridView;

/**
 * Created by Vu on 3/2/2016.
 */
public class GridArea extends GridView {
    public GridArea(Context context, int playerNum) {
        super(context);

        addCells();
        this.playerNum = playerNum;
    }

    public void addCells() {
        String cellStatus;
        if (playerNum == 1)
            cellStatus = getResources().getString(R.string.status_vacant);
        else
            cellStatus = getResources().getString(R.string.status_unknown);

        for (int c = 0; c < Math.pow(sideCellsCount, 2); c++) {
            GridCell cell = new GridCell(getContext(), cellStatus);
            addView(cell);
        }
    }

    public int playerNum;
    public int sideCellsCount = 5;
}
