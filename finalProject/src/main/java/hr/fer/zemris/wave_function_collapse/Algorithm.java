package hr.fer.zemris.wave_function_collapse;

import hr.fer.zemris.Frame;
import hr.fer.zemris.tiles.Tile;

import java.math.BigInteger;
import java.util.List;
import java.util.Stack;

public class Algorithm {
    private final Stack<Frame> frames = new Stack<>();
    private final Stack<BigInteger[][]> possibilitiesStack=new Stack<>();
    private boolean done=false;
    private final List<Tile> tileList = Tile.initializeList();
    private Frame bestFrame;
    private int bestFrameCompletion=-1;
    private boolean ignoreAnimation=false;
    private final Combination combination=new Combination(this);
    private int nextRow=-1, nextColumn=-1;
    public Algorithm(Frame frame){
        this.frames.push(frame);

        createNewPossibility();
    }

    public boolean done(){
        return done;
    }

    public Tile[][] step(){

        int nextOption=updatePossibilities();

        if (nextOption==-1) {
            int frameCompletion=0;
            Frame frame=frames.peek();
            Tile[][] tiles=frame.getFrame();
            for (Tile[] k:tiles){
                for (Tile i:k){
                    if (i!=null) frameCompletion++;
                }
            }

            if (frameCompletion>bestFrameCompletion){
                bestFrame=frame;
                bestFrameCompletion=frameCompletion;
            }

            combination.revertSuperposition();
        }
        else if (nextOption==0) combination.collapseSuperposition();
        else if (nextOption==1) {
            Frame frame=frames.peek();
            frame.getFrame()[nextRow][nextColumn]=tileList.get(possibilitiesStack.peek()[nextRow][nextColumn].getLowestSetBit());
            nextRow=-1;
            nextColumn=-1;
        }
        else {
            done=true;
            Frame finalFrame=frames.pop();
            return finalFrame.getFrame();
        }

        if (possibilitiesStack.isEmpty()) {
            ignoreAnimation=true;
            frames.push(bestFrame);

            createNewPossibility();
        }

        return null;
    }

    private int updatePossibilities(){

        int doneCount=possibilitiesStack.peek().length*possibilitiesStack.peek()[0].length;
        int doneFlag=tileList.size();

        BigInteger[][] possibility = possibilitiesStack.peek();
        Frame frame = frames.peek();
        for (int k=possibility.length-1; k>=0; --k){
            for (int i=possibility[k].length-1; i>=0; --i){
                if (frames.peek().getFrame()[k][i]==null) {
                    BigInteger mask = BigInteger.ZERO;
                    for (var tileIndex = tileList.size() - 1; tileIndex >= 0; --tileIndex) {
                        if (!frame.respectsRules(tileList.get(tileIndex), k, i, ignoreAnimation)) {
                            mask = mask.add(BigInteger.ONE.shiftLeft(tileIndex));
                        }
                    }
                    mask=mask.not();
                    possibility[k][i] = possibility[k][i].and(mask);
                }
                else possibility[k][i]=BigInteger.ONE.shiftLeft(doneFlag);

                if (possibility[k][i].bitCount()==0) return -1;
                else if (possibility[k][i].equals(BigInteger.ONE.shiftLeft(doneFlag))) doneCount--;
                else if (possibility[k][i].bitCount()==1) {
                    nextRow=k;
                    nextColumn=i;
                    return 1;
                }
            }
        }

        if (doneCount==0) return 2;
        return 0;
    }

    void createNewPossibility(){
        Frame frame=frames.peek();

        BigInteger[][] arr = new BigInteger[frame.getHeight()][frame.getWidth()];

        BigInteger num = BigInteger.ZERO;
        for (int k=tileList.size()-2; k>=0; --k){
            num=num.shiftLeft(1);
            num=num.add(BigInteger.ONE);
        }

        for (int k=frame.getHeight()-1; k>=0; --k){
            for (int i=frame.getWidth()-1; i>=0; --i){
                arr[k][i]=num;
            }
        }

        possibilitiesStack.push(arr);
    }

    void addTileToFrame(int tileNo, int rowNo, int columnNo){
        Tile tile=tileList.get(tileNo);
        Frame frame=frames.peek();
        frame.addTile(tile, rowNo, columnNo, ignoreAnimation);

        /*
        BigInteger[][] possibility = possibilitiesStack.peek();
        possibility[rowNo][columnNo]=BigInteger.ONE.shiftLeft(tileList.size());

        for (int k=0; k<4; ++k){
            int tmpRow = rowNo + ((k==0) ? -1 : (k==3) ? 1 : 0);
            int tmpCol = columnNo + ((k==1) ? -1 : (k==2) ? 1 : 0);
            if ( !(tmpCol<0 || tmpCol>possibility[0].length || tmpRow<0 || tmpRow>possibility.length) ){
                BigInteger mask = BigInteger.ZERO;
                for (int i=tileList.size()-1; i>=0; --i){
                    if (!frame.respectsRules(tileList.get(i), tmpRow, tmpCol, ignoreAnimation)) {
                        mask = mask.add(BigInteger.ONE.shiftLeft(i));
                    }
                }
                mask=mask.not();
                possibility[rowNo][columnNo] = possibility[rowNo][columnNo].and(mask);
            }
        }
        */
    }

    List<Tile> getTileList(){
        return tileList;
    }

    Stack<BigInteger[][]> getPossibilitiesStack(){
        return possibilitiesStack;
    }

    Stack<Frame> getFrames(){
        return frames;
    }
}
