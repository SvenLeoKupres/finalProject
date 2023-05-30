package hr.fer.zemris.wave_function_collapse;

import hr.fer.zemris.Frame;
import hr.fer.zemris.tiles.Tile;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Combination2 {
    private static class Pair{
        private final int rowNo;
        private final int columnNo;

        public Pair(int rowNo, int columnNo){
            this.rowNo=rowNo;
            this.columnNo=columnNo;
        }
    }
    private final Pair pair;
    private BigInteger[][] possibility;
    private final int tileNo;
    private final Frame frame;

    public Combination2(BigInteger[][] possibility, Frame frame, List<Tile> tileList) {
        this.possibility=possibility;
        this.frame=frame;

        Random rand=new Random();

        int lowestBitCount=tileList.size();
        List<Pair> possiblePairs=new ArrayList<>();

        for (int k=possibility.length-1; k>=0; --k){
            for (int i=possibility[k].length-1; i>=0; --i){
                if (possibility[k][i].testBit(tileList.size())) continue;
                if (possibility[k][i].bitCount()==lowestBitCount) possiblePairs.add(new Pair(k, i));
                else if (possibility[k][i].bitCount()<lowestBitCount) {
                    possiblePairs=new ArrayList<>();
                    possiblePairs.add(new Pair(k, i));
                    lowestBitCount=possibility[k][i].bitCount();
                }
            }
        }

        pair=possiblePairs.get(rand.nextInt(possiblePairs.size()));


        List<Integer> possibleTiles=new ArrayList<>();
        for (int k=tileList.size()-1; k>=0; --k){
            if (possibility[pair.rowNo][pair.columnNo].testBit(k)) possibleTiles.add(k);
        }

        tileNo=possibleTiles.get(rand.nextInt(possibleTiles.size()));

    }

    BigInteger[][] getPossibility(){
        return possibility;
    }

    int getRowNo() {
        return pair.rowNo;
    }

    int getColumnNo() {
        return pair.columnNo;
    }

    int getTileNo() {
        return tileNo;
    }

    Frame getFrame() {
        return frame;
    }

    void setPossibility(BigInteger[][] possibility) {
        this.possibility = possibility;
    }
}
