package hr.fer.zemris;

import hr.fer.zemris.tiles.Tile;
import hr.fer.zemris.wave_function_collapse.Algorithm2;

import java.util.List;

/**
 * Defines a single frame of the animation<br>
 * Uses a two-dimensional array, the size of which is n x m, where n is the number of rows
 * and m is the number of columns
 *
 */
public class Frame {
    private final int width;
    private final int height;
    private Tile[][] frame;
    private final Frame prevFrame;
    private final Algorithm2 algorithm;
    private final List<Tile> tileList;

    /**
     * @param width number of columns in the frame
     * @param height number of rows in the frame
     * @param prevFrame previous frame in the animation, <code>null</code> if this is the first frame
     */
    public Frame(int width, int height, Frame prevFrame, List<Tile> tileList){
        this(width, height, prevFrame, null, null, tileList);
    }

    private Frame(int width, int height, Frame prevFrame, Algorithm2 algorithm, Tile[][] frame, List<Tile> tileList){
        this.width=width;
        this.height=height;
        this.prevFrame=prevFrame;
        this.tileList=tileList;

        this.frame=new Tile[height][width];

        if (frame!=null) {
            for (int k=0; k<frame.length; ++k){
                System.arraycopy(frame[k], 0, this.frame[k], 0, frame[k].length);
            }
        }

        this.algorithm = (algorithm==null) ? new Algorithm2(this, tileList) : algorithm;

        if (algorithm==null) createFrame();
    }

    /**
     * constructs the frame, one tile at a time
     */
    private void createFrame(){
        Tile[][] tmp=null;
        while (!algorithm.done()){
            tmp=algorithm.step();
        }
        this.frame=tmp;
    }

    /**
     * Inserts the given tile into the desired place in the frame
     * @param tile the tile to be inserted
     * @param row row index where the tile should be inserted
     * @param column column index where the tile should be inserted
     * @param ignoreAnimation if <code>true</code>, while checking if inserting the tile into the given positions follows the rules,
     *                        ignores the <code>top</code> and <code>bottom</code> values for tiles
     * @throws IllegalArgumentException if the insertion of the given tile breaks any rules
     */
    public void addTile(Tile tile, int row, int column, boolean ignoreAnimation){
        if (!respectsRules(tile, row, column, ignoreAnimation)) throw new IllegalArgumentException();

        frame[row][column]=tile;
    }

    /**
     * checks if inserting the given tile into the desired place in the frame would break any rules
     * @param tile the tile to be inserted
     * @param row row index where the tile should be inserted
     * @param column column index where the tile should be inserted
     * @param ignoreAnimation if <code>true</code>, while checking if inserting the tile into the given positions follows the rules,
     *                        ignores the <code>top</code> and <code>bottom</code> values for tiles
     * @return <code>true</code> if the tile breaks no rules, <code>false</code> otherwise
     */
    public boolean respectsRules(Tile tile, int row, int column, boolean ignoreAnimation){
        if (row<0 || row>=height || column<0 || column>=width) return false;

        Tile tileLeft=null, tileRight=null, tileUp=null, tileDown=null, tilePrev=null;
        if (prevFrame!=null && !ignoreAnimation) tilePrev=prevFrame.getFrame()[row][column];
        if (row!=0) tileUp=frame[row-1][column];
        if (row!=height-1) tileDown=frame[row+1][column];
        if (column!=0) tileLeft=frame[row][column-1];
        if (column!=width-1) tileRight=frame[row][column+1];

        if (tilePrev!=null){
            if (tilePrev.getTop()!=tile.getBottom()) return false;
        }
        if (tileUp!=null){
            if (tileUp.getSouth()!=tile.getNorth() || tileUp.equals(tile)) return false;
        }
        if (tileDown!=null){
            if (tileDown.getNorth()!=tile.getSouth() || tileDown.equals(tile)) return false;
        }
        if (tileLeft!=null){
            if (tileLeft.getEast()!=tile.getWest() || tileLeft.equals(tile)) return false;
        }
        if (tileRight!=null){
            return tileRight.getWest() == tile.getEast() && !tileRight.equals(tile);
            //return tileRight.getWest() == tile.getEast() || !tileRight.equals(tile);
        }

        return true;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    /**
     * Returns the Tile at given position within the frame
     * @param row index of desired Tile's row
     * @param column index of desired Tile's column
     * @return Tile located at given row and column
     */
    public Tile getTile(int row, int column){
        if (row<0 || row>=height || column<0 || column>=width) throw new IllegalArgumentException();

        return frame[row][column];
    }

    /**
     *
     * @return a copy of the two-dimensional array which stores the tiles
     */
    public Tile[][] getFrame(){
        Tile[][] copy=new Tile[height][width];
        for (int k=0; k<height; ++k){
            System.arraycopy(frame[k], 0, copy[k], 0, width);
        }
        return copy;
    }

    /**
     *
     * @return an exact copy of this frame
     */
    public Frame copy(){
        return new Frame(this.width, this.height, this.prevFrame, algorithm, frame, this.tileList);
    }

}
