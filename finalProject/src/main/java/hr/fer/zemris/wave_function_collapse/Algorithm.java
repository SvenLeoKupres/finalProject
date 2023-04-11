package hr.fer.zemris.wave_function_collapse;

import hr.fer.zemris.Frame;

public class Algorithm {
    private final Frame frame;
    private final long[][] possibilities;
    private boolean done=false;
    public Algorithm(Frame frame){
        this.frame=frame;
        possibilities=new long[frame.getHeight()][frame.getWidth()];
    }

    public boolean done(){
        return done;
    }

    public void step(){

    }
}
