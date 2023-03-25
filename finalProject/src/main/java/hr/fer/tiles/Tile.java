package hr.fer.tiles;

import java.awt.*;

/**
 * 2 dimensions: 0 for grass, 1 for roads, 2 for cities
 * 3. dimensions: 0 no rules
 */
public class Tile {
    private final int north;
    private final int south;
    private final int west;
    private final int east;
    private final int top;
    private final int bottom;
    private final Image img;

    public static Tile AcrossCityZero =  new Tile(2, 2, 0, 0, top, bottom, img);
    public static Tile AcrossCityOne =  new Tile(0, 0, 2, 2, top, bottom, img);

    public static Tile AcrossCityTwoPartsZero = new Tile(2, 2, 0, 0, top, bottom, img);
    public static Tile AcrossCityTwoPartsOne = new Tile(0, 0, 2, 2, top, bottom, img);

    public static Tile Cathedral = new Tile(0, 0, 0, 0, top, bottom, img);

    public static Tile CathedralRoadZero = new Tile(1, 0, 0, 0, top, bottom, img);
    public static Tile CathedralRoadOne = new Tile(0, 0, 0, 1, top, bottom, img);
    public static Tile CathedralRoadTwo = new Tile(0, 1, 0, 0, top, bottom, img);
    public static Tile CathedralRoadThree = new Tile(0, 0, 1, 0, top, bottom, img);

    public static Tile CityEntranceZero = new Tile(1, 2, 2, 2, top, bottom, img);
    public static Tile CityEntranceOne = new Tile(2, 2, 2, 1, top, bottom, img);
    public static Tile CityEntranceTwo = new Tile(2, 1, 2, 2, top, bottom, img);
    public static Tile CityEntranceThree = new Tile(2, 2, 1, 2, top, bottom, img);

    public Tile(int north, int south, int west, int east, int top, int bottom, Image img) {
        this.img = img;
        this.top = top;
        this.bottom = bottom;

        this.north = north;
        this.south = south;
        this.west = west;
        this.east = east;
    }

    public int getNorth() {
        return north;
    }
    public int getSouth() {
        return south;
    }
    public int getWest() {
        return west;
    }
    public int getEast() {
        return east;
    }
    public int getTop() {
        return top;
    }
    public int getBottom() {
        return bottom;
    }
    public Image getImg(){
        return img;
    }

}
