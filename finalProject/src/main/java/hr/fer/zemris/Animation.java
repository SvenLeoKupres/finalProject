package hr.fer.zemris;

public class Animation {
    private final Frame[] frames;
    private int lastFrameIndex = 0;
    private final int width;
    private final int height;

    public Animation(int noOfFrames, int width, int height){
        if (noOfFrames<=0) throw new IllegalArgumentException();

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

    public Frame[] getFrames(){
        Frame[] framesCopy=new Frame[lastFrameIndex];

        System.arraycopy(frames, 0, framesCopy, 0, lastFrameIndex);

        return framesCopy;
    }
}
