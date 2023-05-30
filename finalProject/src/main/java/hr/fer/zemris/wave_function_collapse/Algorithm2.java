package hr.fer.zemris.wave_function_collapse;

import hr.fer.zemris.Frame;
import hr.fer.zemris.tiles.Tile;

import javax.management.MBeanServerInvocationHandler;
import java.math.BigInteger;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

public class Algorithm2 {
    private final Stack<Combination2> stack=new Stack<>();
    private boolean done = false;
    private final List<Tile> tileList;
    private Combination2 bestCombination;
    private int bestFrameCompletion = -1;
    private boolean ignoreAnimation = false;
    private int nextRow = -1, nextColumn = -1;

    public Algorithm2(Frame frame, List<Tile> tileList) {
        this.tileList = tileList;
        createNewPossibility(frame);
    }

    /**
     *
     * @return <code>true</code> if algorithm is finished with frame generation, otherwise <code>false</code>
     */
    public boolean done() {
        return done;
    }

    /**
     * calls method <code>updatePossibilities</code> and decides upon a method of action based on the returned code
     * @return <code>null</code> if not yet finished, the frame's completed Tile array otherwise
     */
    public Tile[][] step() {
        if (done) return stack.peek().getFrame().getFrame();

        Combination2 combination = stack.peek();
        BigInteger[][] possibility = combination.getPossibility();
        Frame frame = combination.getFrame();

        int nextOption = updatePossibilities(possibility, frame);


        if (nextOption == -1) {
            int frameCompletion = 0;
            Tile[][] tiles = frame.getFrame();
            for (Tile[] k : tiles) {
                for (Tile i : k) {
                    if (i != null) frameCompletion++;
                }
            }

            if (frameCompletion > bestFrameCompletion) {
                bestCombination=combination;
                bestFrameCompletion = frameCompletion;
            }

            try {
                revertSuperposition();
            }
            catch (EmptyStackException ignored){}
        } else if (nextOption == 0) collapseSuperposition();
        else if (nextOption == 1) {
            Tile tile = tileList.get(combination.getPossibility()[nextRow][nextColumn].getLowestSetBit());
            frame.addTile(tile, nextRow, nextColumn, ignoreAnimation);
            nextRow = -1;
            nextColumn = -1;
        } else {
            done = true;
            return frame.getFrame();
        }

        if (!stack.isEmpty()) return null;

        if (!ignoreAnimation) {
            ignoreAnimation = true;
            stack.push(bestCombination);
            possibility = createNewPossibilityArray(stack.peek().getFrame());
            stack.peek().setPossibility(possibility);
            return null;
        }
        throw new RuntimeException("Created an untileable space");
    }

    /**
     * Updates the given array based on the defined Tiles in the given Frame
     * @param possibility array being updated
     * @param frame object whose internal tile array is used to update the given BigInteger array
     * @return -1 if the one of the fields in the given array equals 0 (doesn't finish the procedure of updating)<br>
     * 1 if it found a position within array with exaclty one possibility (doesn't finish the procedure of updating)
     * 2 if the frame is already filled out
     * 0 in any other scenario
     */
    private int updatePossibilities(BigInteger[][] possibility, Frame frame) {

        int doneCount = possibility.length * possibility[0].length;
        int doneFlag = tileList.size();

        for (int k = possibility.length - 1; k >= 0; --k) {
            for (int i = possibility[k].length - 1; i >= 0; --i) {
                if (frame.getFrame()[k][i] == null) {
                    BigInteger mask = BigInteger.ZERO;
                    for (var tileIndex = tileList.size() - 1; tileIndex >= 0; --tileIndex) {
                        if (!frame.respectsRules(tileList.get(tileIndex), k, i, ignoreAnimation)) {
                            mask = mask.add(BigInteger.ONE.shiftLeft(tileIndex));
                        }
                    }
                    mask = mask.not();
                    possibility[k][i] = possibility[k][i].and(mask);
                } else possibility[k][i] = BigInteger.ONE.shiftLeft(doneFlag);

                if (possibility[k][i].bitCount() == 0) return -1;
                else if (possibility[k][i].equals(BigInteger.ONE.shiftLeft(doneFlag))) doneCount--;
                else if (possibility[k][i].bitCount() == 1) {
                    nextRow = k;
                    nextColumn = i;
                    return 1;
                }
            }
        }

        if (doneCount == 0) return 2;
        return 0;
    }

    private void createNewPossibility(Frame frame) {
        BigInteger[][] arr = createNewPossibilityArray(frame);

        updatePossibilities(arr, frame);

        stack.push(new Combination2(arr, frame, tileList));
    }

    private void addTileToFrame(int tileNo, int rowNo, int columnNo) {
        Tile tile = tileList.get(tileNo);
        stack.peek().getFrame().addTile(tile, rowNo, columnNo, ignoreAnimation);
    }

    private void collapseSuperposition(){
        createNewPossibility(stack.peek().getFrame().copy());

        Combination2 combination = stack.peek();

        int tileNo = combination.getTileNo();
        int rowNo = combination.getRowNo();
        int colNo = combination.getColumnNo();

        combination.getFrame().addTile(tileList.get(tileNo), rowNo, colNo, ignoreAnimation);
    }

    private void revertSuperposition(){
        Combination2 combinationToRevert = stack.pop();
        Combination2 combination;
        try {
            combination = stack.peek();
        }
        catch (EmptyStackException e){
            throw new EmptyStackException();
        }


        int failedTile = combinationToRevert.getTileNo();
        int failedRow = combinationToRevert.getRowNo();
        int failedCol = combinationToRevert.getColumnNo();

        if (failedCol==-1 || failedRow==-1) return;

        BigInteger[][] possibility = combination.getPossibility();
        BigInteger mask = BigInteger.ONE.shiftLeft(failedTile).not();
        possibility[failedRow][failedCol]=possibility[failedRow][failedCol].and(mask);

    }

    private BigInteger[][] createNewPossibilityArray(Frame frame){
        BigInteger[][] arr = new BigInteger[frame.getHeight()][frame.getWidth()];

        BigInteger num = BigInteger.ZERO;
        for (int k = tileList.size() - 2; k >= 0; --k) {
            num = num.shiftLeft(1);
            num = num.add(BigInteger.ONE);
        }

        for (int k = frame.getHeight() - 1; k >= 0; --k) {
            for (int i = frame.getWidth() - 1; i >= 0; --i) {
                arr[k][i] = num;
            }
        }

        return arr;
    }
}