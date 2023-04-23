package hr.fer.zemris.wave_function_collapse;

import hr.fer.zemris.Frame;
import hr.fer.zemris.tiles.Tile;

import java.math.BigInteger;
import java.util.List;
import java.util.Stack;

public class Algorithm2 {
    private final Stack<Combination2> stack=new Stack<>();
    private boolean done = false;
    private final List<Tile> tileList = Tile.initializeList();
    private Combination2 bestCombination;
    private int bestFrameCompletion = -1;
    private boolean ignoreAnimation = false;
    private int nextRow = -1, nextColumn = -1;

    public Algorithm2(Frame frame) {
        createNewPossibility(frame);
    }

    public boolean done() {
        return done;
    }

    public Tile[][] step() {

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

            revertSuperposition();
        } else if (nextOption == 0) collapseSuperposition();
        else if (nextOption == 1) {
            //frame.getFrame()[nextRow][nextColumn] = tileList.get(combination.getPossibility()[nextRow][nextColumn].getLowestSetBit());
            Tile tile = tileList.get(combination.getPossibility()[nextRow][nextColumn].getLowestSetBit());
            frame.addTile(tile, nextRow, nextColumn, ignoreAnimation);
            nextRow = -1;
            nextColumn = -1;
        } else {
            done = true;
            return frame.getFrame();
        }

        if (stack.isEmpty() && !ignoreAnimation) {
            ignoreAnimation = true;
            stack.push(bestCombination);
        }
        else if (ignoreAnimation) throw new RuntimeException("Created an untileable space");

        return null;
    }

    private int updatePossibilities(BigInteger[][] possibility, Frame frame) {

        //Combination2 combination=stack.peek();
        //BigInteger[][] possibility = combination.getPossibility();
        //Frame frame = combination.getFrame();

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

        updatePossibilities(arr, frame);

        stack.push(new Combination2(arr, frame, tileList));
    }

    private void addTileToFrame(int tileNo, int rowNo, int columnNo) {
        Tile tile = tileList.get(tileNo);
        stack.peek().getFrame().addTile(tile, rowNo, columnNo, ignoreAnimation);

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
        Combination2 combination = stack.peek();

        int failedTile = combinationToRevert.getTileNo();
        int failedRow = combinationToRevert.getRowNo();
        int failedCol = combinationToRevert.getColumnNo();

        if (failedCol==-1 || failedRow==-1) return;

        BigInteger[][] possibility = combination.getPossibility();
        BigInteger mask = BigInteger.ONE.shiftLeft(failedTile).not();
        possibility[failedRow][failedCol]=possibility[failedRow][failedCol].and(mask);

        //possibility[combinationToRevert.getRowNo()][combinationToRevert.getColumnNo()] = possibility[combinationToRevert.getRowNo()][combinationToRevert.getColumnNo()].set(combinationToRevert.getTileNo());
    }
}