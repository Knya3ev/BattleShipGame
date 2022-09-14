package communication;

import constants.graphics.TableConstants;

import java.util.ArrayList;

public class TranslateForServerOrView {
    private final int startTableX = TableConstants.LEFT_MARGIN_FOR_RENDER_IN_THE_TABLE;
    private final int startTableY = TableConstants.TOP_MARGIN_FOR_RENDER_IN_THE_TABLE;
    private final int stepForTable;

    private ArrayList<Integer> forX = new ArrayList<>();
    private ArrayList<Integer> forY = new ArrayList<>();


    public TranslateForServerOrView(int stepForTable) {
        this.stepForTable = stepForTable;
        init();
    }

    private void fillingTheArray(int start, ArrayList<Integer> array) {
        int x = 0;
        for (int i = start; x < 10; i += stepForTable) {
            array.add(i);
            x++;
        }
    }

    private void init() {
        fillingTheArray(startTableX, forX);
        fillingTheArray(startTableY, forY);
    }

    public int[] getIndexesForServer(int x, int y) {
        return new int[]{forX.indexOf(x),forY.indexOf(y)};
    }

    public int[] getPixelCoordinatesForView(int x, int y) {
        return new int[]{forX.get(x),forY.get(y)};
    }


    public int[][] getShipIndexesForServer(int[] x, int[] y) {
        int[][] array = new int[2][4];
        for (int i = 0; i < x.length; i++) {
            array[0][i] = forX.indexOf(x[i]);
            array[1][i] = forY.indexOf(y[i]);
        }
        return array;
    }

    public int[][] getShipPixelCoordinatesForView(int[] x, int[] y) {
        int[][] array = new int[2][4];
        for (int i = 0; i < x.length; i++) {
            array[0][i] = forX.get(x[i]);
            array[1][i] = forY.get(y[i]);
        }
        return array;
    }
}
