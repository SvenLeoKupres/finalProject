package hr.fer.zemris.wave_function_collapse;

import hr.fer.zemris.Frame;
import hr.fer.zemris.tiles.Tile;

import java.math.BigInteger;
import java.util.List;
import java.util.Stack;

public class Algorithm {
    private final Stack<Frame> frames = new Stack<>();
    private final Stack<long[][]> possibilitiesStack=new Stack<>();
    private boolean done=false;
    private final List<Tile> tileList = Tile.initializeList();
    public Algorithm(Frame frame){
        this.frames.push(frame);
        long[][] arr = new long[frame.getHeight()][frame.getWidth()];

        long num = 0;
        for (int k=tileList.size()-1; k>=0; --k){
            num+= (1L << k);
        }

        for (int k=frame.getHeight()-1; k>=0; --k){
            for (int i=frame.getWidth()-1; i>=0; --i){
                arr[k][i]=num;
            }
        }

        possibilitiesStack.push(arr);
    }

    public boolean done(){
        return done;
    }

    public void step(){

    }
}
