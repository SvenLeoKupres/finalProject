package hr.fer.zemris.wave_function_collapse;

import hr.fer.zemris.Frame;
import hr.fer.zemris.tiles.Tile;
import hr.fer.zemris.util.Longlong;

import java.util.List;
import java.util.Stack;

public class Algorithm {
    private final Stack<Frame> frames = new Stack<>();
    private final Stack<Longlong[][]> possibilitiesStack=new Stack<>();
    private boolean done=false;
    private final List<Tile> tileList = Tile.initializeList();
    private Frame bestFrame;
    private boolean ignoreAnimation=false;
    public Algorithm(Frame frame){
        this.frames.push(frame);

        Longlong[][] arr = new Longlong[frame.getHeight()][frame.getWidth()];

        Longlong num = new Longlong(0, 1);
        for (int k=tileList.size()-2; k>=0; --k){
            num.lshift(1);
            num.add(1L);
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
        try {

        }
        catch (IllegalArgumentException e){

        }
    }

    private void updatePossibilities(){
        Longlong[][] possibility = possibilitiesStack.peek();
        Frame frame = frames.peek();
        for (int k=possibility.length-1; k>=0; --k){
            for (int i=possibility[k].length-1; i>=0; --i){
                for (var tileIndex=tileList.size()-1; tileIndex>=0; --tileIndex){
                    if (!frame.respectsRules(tileList.get(tileIndex), k, i, ignoreAnimation)) {
                        Longlong mask = new Longlong().lshift(tileIndex).not();
                        possibility[k][i].and(mask);
                    }
                }
            }
        }
    }
}
