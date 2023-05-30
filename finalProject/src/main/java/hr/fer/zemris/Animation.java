package hr.fer.zemris;

import hr.fer.zemris.tiles.Tile;

import java.util.List;

/**
 * Defines an animation
 */
public class Animation {
    private final Frame[] frames;
    private int lastFrameIndex = 0;
    private final int width;
    private final int height;
    private final List<Tile> tileList = Tile.initializeList();

    /**
     * @param noOfFrames maximum number of frames allowed to be created by this animation
     * @param width column count of the created frames
     * @param height row count of the created frames
     */
    public Animation(int noOfFrames, int width, int height){
        if (noOfFrames<=0) throw new IllegalArgumentException();

        frames=new Frame[noOfFrames];
        this.width = width;
        this.height = height;
    }

    /**
     * creates, stores and returns a new animation frame
     * @return the newly created frame, <code>null</code> if this object already created the maximum number of frames allowed
     */
    public Frame newFrame(){
        if (done()) return null;
        frames[lastFrameIndex] = new Frame(width, height, lastFrameIndex==0 ? null : frames[lastFrameIndex-1], tileList);
        return frames[lastFrameIndex++];
    }

    /**
     *
     * @return <code>true</code> if this animation has already created the maximum number of frames allowed, <code>false</code> otherwise
     */
    public boolean done(){
        return lastFrameIndex==frames.length;
    }

    /**
     *
     * @return an array containing all the frames created so far, in the order they were created in
     */
    public Frame[] getFrames(){
        Frame[] framesCopy=new Frame[lastFrameIndex];

        System.arraycopy(frames, 0, framesCopy, 0, lastFrameIndex);

        return framesCopy;
    }

    /**
     *
     * @return the last Frame created
     */
    public Frame getLastCreated(){
        return frames[lastFrameIndex-1];
    }
}
