package hr.fer;

import hr.fer.tiles.Tile;
import hr.fer.wave_function_collapse.Algorithm;

import java.util.Arrays;

public class Frame {
    private final int width;
    private final int height;
    private final Tile[][] frame;
    private final Frame prevFrame;
    private boolean done = false;
    private Algorithm algorithm = new Algorithm(this);

    public Frame(int width, int height, Frame prevFrame){
        this.width=width;
        this.height=height;
        frame = new Tile[height][width];
        this.prevFrame=prevFrame;

        while (!done){
            createFrame();
        }
    }

    private void createFrame(){

    }

    public boolean done(){
        return done;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public Tile[][] getTiles(){
        Tile[][] copy=new Tile[height][width];
        for (int k=0; k<height; ++k){
            System.arraycopy(frame[k], 0, copy[k], 0, width);
        }
        return copy;
    }
}
