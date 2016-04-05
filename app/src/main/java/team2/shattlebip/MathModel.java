package team2.shattlebip;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by twi on 4/3/2016.
 */
public class MathModel {
    private static MathModel ourInstance = new MathModel();
    private static AdapterBoard board;
    private static int x, y, cols;
    private static Random random = new Random();

    public static MathModel getInstance() {
        if (ourInstance == null)
            ourInstance = new MathModel();
        return ourInstance;
    }

    private MathModel()
    {
        x = 0;
        y = 0;
        cols = 1;
    }

    private static void setXYFromPos(int cols, int pos)
    {
        x = pos / cols;
        y = pos % cols;
    }

    private static int getPosFromXY()
    {
        return x * cols + y;
    }

    public static int getPosFromXY(int x, int y)
    {
        return x * cols + y;
    }

    private static void getEmptyCell()
    {
        do
        {
            x = random.nextInt(cols);
            y = random.nextInt(cols);
        }
        while (board.getItem(getPosFromXY()).status == BoardCellStatus.OCCUPIED);
    }

    private static boolean isNorthValid(int size)
    {
        for (int i = 0; i < size; i++)
        {
            if (board.getItem(getPosFromXY(x, y + i)).status == BoardCellStatus.OCCUPIED);
                return false;
        }

        return true;
    }

    private static void setNorthPlacement(int size)
    {
        for (int i = 0; i < size; i++)
            board.getItem(getPosFromXY(x, y + i)).status = BoardCellStatus.OCCUPIED;
    }

    private static boolean isEastValid(int size)
    {
        for (int i = 0; i < size; i++)
        {
            if (board.getItem(getPosFromXY(x + i, y)).status == BoardCellStatus.OCCUPIED);
                return false;
        }

        return true;
    }

    private static void setEastPlacement(int size)
    {
        for (int i = 0; i < size; i++)
            board.getItem(getPosFromXY(x + i, y)).status = BoardCellStatus.OCCUPIED;
    }

    private static boolean isSouthValid(int size)
    {
        for (int i = 0; i < size; i++)
        {
            if (board.getItem(getPosFromXY(x, y - i)).status == BoardCellStatus.OCCUPIED);
                return false;
        }

        return true;
    }

    private static void setSouthPlacement(int size)
    {
        for (int i = 0; i < size; i++)
            board.getItem(getPosFromXY(x, y - i)).status = BoardCellStatus.OCCUPIED;
    }

    private static boolean isWestValid(int size)
    {
        for (int i = 0; i < size; i++)
        {
            if (board.getItem(getPosFromXY(x - i, y)).status == BoardCellStatus.OCCUPIED);
                return false;
        }

        return true;
    }

    private static void setWestPlacement(int size)
    {
        for (int i = 0; i < size; i++)
            board.getItem(getPosFromXY(x - i, y)).status = BoardCellStatus.OCCUPIED;
    }

    private static void setPlacement(int size)
    {
        List<Integer> sample = new ArrayList<>(4);
        sample.add(0);
        sample.add(1);
        sample.add(2);
        sample.add(3);

        while (!sample.isEmpty())
        {
            int i = random.nextInt(sample.size());
            switch (sample.get(i))
            {
                case 0:
                    if (isNorthValid(size))
                        setNorthPlacement(size);
                    else
                        sample.remove(i);
                    break;
                case 1:
                    if (isEastValid(size))
                        setEastPlacement(size);
                    else
                        sample.remove(i);
                    break;
                case 2:
                    if (isSouthValid(size))
                        setSouthPlacement(size);
                    else
                        sample.remove(i);
                    break;
                case 3:
                    if (isWestValid(size))
                        setWestPlacement(size);
                    else
                        sample.remove(i);
                    break;
                default:
                    break;
            }
        }
    }

    public static void generateShipPlacement(AdapterBoard adapterBoard, int dim)
    {
        board = adapterBoard;
        cols = dim;

        setPlacement(2);
        setPlacement(4);
        setPlacement(4);
        setPlacement(5);

        board.notifyDataSetChanged();
    }

}
