package hr.fer.zemris.wave_function_collapse;

import hr.fer.zemris.tiles.Tile;
import hr.fer.zemris.util.Longlong;

import java.util.Random;
import java.util.Stack;

public class Combination {
    private static class Pair{
        public int rowNo;
        public int columnNo;

        public Pair(int rowNo, int columnNo){
            this.rowNo=rowNo;
            this.columnNo=columnNo;
        }
    }
    private final Stack<Longlong[][]> possibilitiesStack;

    /**
     * Remembers which position is being tested
     */
    private final Stack<Pair> pair=new Stack<>();

    /**
     * Remembers which tile is being tested
     */
    private final Stack<Tile> tile=new Stack<>();
    private final Random rand = new Random();

    public Combination(Stack<Longlong[][]> possibilitiesStack){
        this.possibilitiesStack=possibilitiesStack;
    }

    public void collapseSuperposition(){}

    public void revertSuperposition(){}

}
