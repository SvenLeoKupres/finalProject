package hr.fer.zemris.wave_function_collapse;

import hr.fer.zemris.Frame;
import hr.fer.zemris.tiles.Tile;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

@Deprecated
public class Combination {
    private static class Pair{
        public int rowNo;
        public int columnNo;

        public Pair(int rowNo, int columnNo){
            this.rowNo=rowNo;
            this.columnNo=columnNo;
        }
    }
    private final Algorithm algorithm;

    /**
     * Remembers which position is being tested
     */
    private final Stack<Pair> pairs=new Stack<>();

    /**
     * Remembers which tile is being tested
     */
    private final Stack<Integer> tiles=new Stack<>();
    private final Random rand = new Random();

    public Combination(Algorithm algorithm){
        this.algorithm=algorithm;
    }

    public void collapseSuperposition(){
        Stack<Frame> frames=algorithm.getFrames();
        frames.push(frames.peek().copy());

        Stack<BigInteger[][]> possibilitiesStack=algorithm.getPossibilitiesStack();
        List<Tile> tileList=algorithm.getTileList();

        //pairs.push(new Pair(rand.nextInt(possibilitiesStack.peek().length), rand.nextInt(possibilitiesStack.peek()[0].length)));
        tiles.push(rand.nextInt(tileList.size()));

        BigInteger[][] possibility=possibilitiesStack.peek();
        int lowest=tileList.size();

        List<Pair> possiblePairs=new ArrayList<>();
        for (int k=possibility.length-1; k>=0; --k){
            for (int i=possibility[k].length-1; i>=0; --i){
                if (possibility[k][i].testBit(tileList.size())) continue;
                if (possibility[k][i].bitCount()==lowest) possiblePairs.add(new Pair(k, i));
                else if (possibility[k][i].bitCount()<lowest) {
                    possiblePairs=new ArrayList<>();
                    possiblePairs.add(new Pair(k, i));
                    lowest=possibility[k][i].bitCount();
                }
            }
        }

        Pair selectedPair=possiblePairs.get(rand.nextInt(possiblePairs.size()));
        pairs.push(selectedPair);

        List<Integer> possibleTiles=new ArrayList<>();
        for (int k=tileList.size()-1; k>=0; --k){
            if (possibility[selectedPair.rowNo][selectedPair.columnNo].testBit(k)) possibleTiles.add(k);
        }

        int selectedTile=possibleTiles.get(rand.nextInt(possibleTiles.size()));
        tiles.push(selectedTile);

        algorithm.createNewPossibility();

        algorithm.addTileToFrame(selectedTile, selectedPair.rowNo, selectedPair.columnNo);
    }

    public void revertSuperposition(){
        Stack<BigInteger[][]> possibilitiesStack=algorithm.getPossibilitiesStack();

        possibilitiesStack.pop();
        Pair failedPair=pairs.pop();
        int failedTile=tiles.pop();

        BigInteger[][] currentPossibility=possibilitiesStack.peek();

        BigInteger mask = BigInteger.ONE.shiftLeft(failedTile).not();
        currentPossibility[failedPair.rowNo][failedPair.columnNo]=currentPossibility[failedPair.rowNo][failedPair.columnNo].and(mask);
    }
}
