package hr.fer;

public class Animation {
    private Frame[] frames;
    private int lastFrameIndex = 0;
    private int width;
    private int height;

    public Animation(int noOfFrames, int width, int height){
        frames=new Frame[noOfFrames];
        this.width = width;
        this.height = height;
    }

    public Frame newFrame(){
        if (done()) return null;
        frames[lastFrameIndex++] = new Frame(width, height, null);
        return frames[lastFrameIndex-1];
    }

    public boolean done(){
        return lastFrameIndex==frames.length;
    }
}
