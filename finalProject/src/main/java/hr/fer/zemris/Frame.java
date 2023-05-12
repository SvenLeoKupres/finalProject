package hr.fer.zemris;

import hr.fer.zemris.tiles.Tile;
import hr.fer.zemris.wave_function_collapse.Algorithm2;

public class Frame {
    private final int width;
    private final int height;
    private Tile[][] frame;
    private final Frame prevFrame;
    private final Algorithm2 algorithm;

    public Frame(int width, int height, Frame prevFrame){
        this(width, height, prevFrame, null, null);
    }

    private Frame(int width, int height, Frame prevFrame, Algorithm2 algorithm, Tile[][] frame){
        this.width=width;
        this.height=height;
        this.prevFrame=prevFrame;

        this.frame=new Tile[height][width];

        if (frame!=null) {
            for (int k=0; k<frame.length; ++k){
                System.arraycopy(frame[k], 0, this.frame[k], 0, frame[k].length);
            }
        }

        this.algorithm = (algorithm==null) ? new Algorithm2(this) : algorithm;

        if (algorithm==null) createFrame();
    }

    private void createFrame(){
        Tile[][] tmp=null;
        while (!algorithm.done()){
            tmp=algorithm.step();
        }
        this.frame=tmp;
    }

    public void addTile(Tile tile, int row, int column, boolean ignoreAnimation){
        if (!respectsRules(tile, row, column, ignoreAnimation)) throw new IllegalArgumentException();

        frame[row][column]=tile;
    }

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

    public Tile getTile(int row, int column){
        if (row<0 || row>=height || column<0 || column>=width) throw new IllegalArgumentException();

        return frame[row][column];
    }

    public Tile[][] getFrame(){
        Tile[][] copy=new Tile[height][width];
        for (int k=0; k<height; ++k){
            System.arraycopy(frame[k], 0, copy[k], 0, width);
        }
        return copy;
    }

    public Frame copy(){
        return new Frame(this.width, this.height, this.prevFrame, algorithm, frame);
    }

}
