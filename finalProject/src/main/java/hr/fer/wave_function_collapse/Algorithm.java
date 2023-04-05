package hr.fer.wave_function_collapse;

import hr.fer.Frame;

public class Algorithm {
    private final Frame frame;
    private final int[][] possibilities;
    public Algorithm(Frame frame){
        this.frame=frame;
        possibilities=new int[frame.getHeight()][frame.getWidth()];
    }
}
