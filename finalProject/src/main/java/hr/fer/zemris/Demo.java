package hr.fer.zemris;

import hr.fer.zemris.tiles.Tile;

public class Demo {
    public static void main(String[] args){
        Animation animation = new Animation(1, 10, 10);
        int frameNo=1;
        while (!animation.done()){
            Tile[][] frame=animation.newFrame().getFrame();
            System.out.println("Done with frame"+frameNo++);
            for (var k:frame){
                for (var i:k){
                    System.out.print(i+" ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }
}
