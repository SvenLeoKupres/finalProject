package hr.fer.zemris.tiles;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Defines the appearance and behaviour of a tile. For each side of the tile, defines a number -
 * only tiles with a corresponding number on their opposite side can be placed next to it.<br>
 * For instance, if a tile has the number 0 for their north side, only tiles with the number 0 on their south side
 * can be placed above it, and <i>vice versa</i>
 *
 */
public class Tile {
    private final int north;
    private final int south;
    private final int west;
    private final int east;
    private final int top;
    private final int bottom;
    private final String name;
    private final BufferedImage img;

    private static final Tile AcrossCityZero =  new Tile(2, 2, 0, 0, 0, 0, "acrossCityZero");
    private static final Tile AcrossCityOne =  new Tile(0, 0, 2, 2, 1, 1, "acrossCityOne");

    private static final Tile AcrossCityTwoPartsZero = new Tile(2, 2, 0, 0, 0, 2, "acrossCityTwoPartsZero");
    private static final Tile AcrossCityTwoPartsOne = new Tile(0, 0, 2, 2, 1, 2, "acrossCityTwoPartsOne");

    private static final Tile Cathedral = new Tile(0, 0, 0, 0, 3, 3, "cathedral");

    private static final Tile CathedralRoadZero = new Tile(1, 0, 0, 0, 3, 3, "cathedralRoadZero");
    private static final Tile CathedralRoadOne = new Tile(0, 0, 0, 1, 3, 3, "cathedralRoadOne");
    private static final Tile CathedralRoadTwo = new Tile(0, 1, 0, 0, 3, 3, "cathedralRoadTwo");
    private static final Tile CathedralRoadThree = new Tile(0, 0, 1, 0, 3, 3, "cathedralRoadThree");

    private static final Tile CityEntranceZero = new Tile(1, 2, 2, 2, 6, 6, "cityEntranceZero");
    private static final Tile CityEntranceOne = new Tile(2, 2, 2, 1, 7, 7, "cityEntranceOne");
    private static final Tile CityEntranceTwo = new Tile(2, 1, 2, 2, 8, 8, "cityEntranceTwo");
    private static final Tile CityEntranceThree = new Tile(2, 2, 1, 2, 9, 9, "cityEntranceThree");

    private static final Tile DiagonalCityZero = new Tile(2, 0, 0, 2, 10, 14, "diagonalCityZero");
    private static final Tile DiagonalCityOne = new Tile(0, 2, 0, 2, 11, 15, "diagonalCityOne");
    private static final Tile DiagonalCityTwo = new Tile(0, 2, 2, 0, 12, 16, "diagonalCityTwo");
    private static final Tile DiagonalCityThree = new Tile(2, 0, 2, 0, 13, 17, "diagonalCityThree");

    private static final Tile DiagonalCityTurningRoadZero = new Tile(2, 1, 1, 2, 10, 10, "diagonalCityTurningRoadZero");
    private static final Tile DiagonalCityTurningRoadOne = new Tile(1, 2, 1, 2, 11, 11, "diagonalCityTurningRoadOne");
    private static final Tile DiagonalCityTurningRoadTwo = new Tile(1, 2, 2, 1, 12, 12, "diagonalCityTurningRoadTwo");
    private static final Tile DiagonalCityTurningRoadThree = new Tile(2, 1, 2, 1, 13, 13, "diagonalCityTurningRoadThree");

    private static final Tile DiagonalCityTwoPartsZero = new Tile(2, 0, 0, 2, 14, 18, "diagonalCityTwoPartsZero");
    private static final Tile DiagonalCityTwoPartsOne = new Tile(0, 2, 0, 2, 15, 18, "diagonalCityTwoPartsOne");
    private static final Tile DiagonalCityTwoPartsTwo = new Tile(0, 2, 2, 0, 16, 18, "diagonalCityTwoPartsTwo");
    private static final Tile DiagonalCityTwoPartsThree = new Tile(2, 0, 2, 0, 17, 18, "diagonalCityTwoPartsThree");

    private static final Tile FourWayCrossroads = new Tile(1, 1, 1, 1, 19, 19, "fourWayCrossroads");

    private static final Tile Grass = new Tile(0, 0, 0, 0, 4, 2, "grass");

    private static final Tile Marketplace = new Tile(2, 2, 2, 2, 5, 5, "marketplace");

    private static final Tile OneSideCityZero = new Tile(2, 0, 0, 0, 20, 2, "oneSideCityZero");
    private static final Tile OneSideCityOne = new Tile(0, 0, 0, 2, 21, 2, "oneSideCityOne");
    private static final Tile OneSideCityTwo = new Tile(0, 2, 0, 0, 22, 2, "oneSideCityTwo");
    private static final Tile OneSideCityThree = new Tile(0, 0, 2, 0, 23, 2, "oneSideCityThree");

    private static final Tile OneSideCityCrossroadsZero = new Tile(2, 1, 1, 1, 24, 24, "oneSideCityCrossroadsZero");
    private static final Tile OneSideCityCrossroadsOne = new Tile(1, 1, 1, 2, 25, 25, "oneSideCityCrossroadsOne");
    private static final Tile OneSideCityCrossroadsTwo = new Tile(1, 2, 1, 1, 26, 26, "oneSideCityCrossroadsTwo");
    private static final Tile OneSideCityCrossroadsThree = new Tile(1, 1, 2, 1,27 , 27, "oneSideCityCrossroadsThree");

    private static final Tile OneSideCityStraightRoadZero = new Tile(2, 0, 1, 1, 24, 20, "oneSideCityStraightRoadZero");
    private static final Tile OneSideCityStraightRoadOne = new Tile(1, 1, 0, 2, 25, 21, "oneSideCityStraightRoadOne");
    private static final Tile OneSideCityStraightRoadTwo = new Tile(0, 2, 1, 1, 26, 22, "oneSideCityStraightRoadTwo");
    private static final Tile OneSideCityStraightRoadThree = new Tile(1, 1, 2, 0, 27, 23, "oneSideCityStraightRoadThree");

    private static final Tile OneSideCityTurningLeftRoadZero = new Tile(2, 1, 1, 0, 24, 20, "oneSideCityTurningLeftRoadZero");
    private static final Tile OneSideCityTurningLeftRoadOne = new Tile(1, 0, 1, 2, 25, 21, "oneSideCityTurningLeftRoadOne");
    private static final Tile OneSideCityTurningLeftRoadTwo = new Tile(1, 2, 0, 1, 26, 22, "oneSideCityTurningLeftRoadTwo");
    private static final Tile OneSideCityTurningLeftRoadThree = new Tile(0, 1, 2, 1, 27, 23, "oneSideCityTurningLeftRoadThree");

    private static final Tile OneSideCityTurningRightRoadZero = new Tile(2, 1, 0, 1, 24, 20, "oneSideCityTurningRightRoadZero");
    private static final Tile OneSideCityTurningRightRoadOne = new Tile(0, 1, 1, 2, 25, 21, "oneSideCityTurningRightRoadOne");
    private static final Tile OneSideCityTurningRightRoadTwo = new Tile(1, 2, 1, 0, 26, 22, "oneSideCityTurningRightRoadTwo");
    private static final Tile OneSideCityTurningRightRoadThree = new Tile(1, 0, 2, 1, 27, 23, "oneSideCityTurningRightRoadThree");

    private static final Tile RoadStraightZero = new Tile(1, 1, 0, 0, 28, 28, "roadStraightZero");
    private static final Tile RoadStraightOne = new Tile(0, 0, 1, 1, 28, 28, "roadStraightOne");

    private static final Tile RoadTurningZero = new Tile(1, 0, 0, 1, 28, 28, "roadTurningZero");
    private static final Tile RoadTurningOne = new Tile(0, 1, 0, 1, 28, 28, "roadTurningOne");
    private static final Tile RoadTurningTwo = new Tile(0, 1, 1, 0, 28, 28, "roadTurningTwo");
    private static final Tile RoadTurningThree = new Tile(1, 0, 1, 0, 28, 28, "roadTurningThree");

    private static final Tile ThreeSidesCityZero = new Tile(2, 0, 2, 2, 34, 34, "threeSidesCityZero");
    private static final Tile ThreeSidesCityOne = new Tile(2, 2, 0, 2, 35, 35, "threeSidesCityOne");
    private static final Tile ThreeSidesCityTwo = new Tile(0, 2, 2, 2, 36, 26, "threeSidesCityTwo");
    private static final Tile ThreeSidesCityThree = new Tile(2, 2, 2, 0, 37, 37, "threeSidesCityThree");

    private static final Tile ThreeWayCrossroadsZero = new Tile(0, 1, 1, 1, 19, 28, "threeWayCrossroadsZero");
    private static final Tile ThreeWayCrossroadsOne = new Tile(1, 1, 1, 0, 19, 28, "threeWayCrossroadsOne");
    private static final Tile ThreeWayCrossroadsTwo = new Tile(1, 0, 1, 1, 19, 28, "threeWayCrossroadsTwo");
    private static final Tile ThreeWayCrossroadsThree = new Tile(1, 1, 0, 1, 19, 28, "threeWayCrossroadsThree");

    /**
     *
     * @return a list of all the internally defined tiles
     */
    public static java.util.List<Tile> initializeList() {
        java.util.List<Tile> tileList = new ArrayList<>();
        tileList.add(AcrossCityZero);
        tileList.add(AcrossCityOne);
        tileList.add(AcrossCityTwoPartsZero);
        tileList.add(AcrossCityTwoPartsOne);
        tileList.add(Cathedral);
        tileList.add(CathedralRoadZero);
        tileList.add(CathedralRoadOne);
        tileList.add(CathedralRoadTwo);
        tileList.add(CathedralRoadThree);
        tileList.add(CityEntranceZero);
        tileList.add(CityEntranceOne);
        tileList.add(CityEntranceTwo);
        tileList.add(CityEntranceThree);
        tileList.add(DiagonalCityZero);
        tileList.add(DiagonalCityOne);
        tileList.add(DiagonalCityTwo);
        tileList.add(DiagonalCityThree);
        tileList.add(DiagonalCityTurningRoadZero);
        tileList.add(DiagonalCityTurningRoadOne);
        tileList.add(DiagonalCityTurningRoadTwo);
        tileList.add(DiagonalCityTurningRoadThree);
        tileList.add(DiagonalCityTwoPartsZero);
        tileList.add(DiagonalCityTwoPartsOne);
        tileList.add(DiagonalCityTwoPartsTwo);
        tileList.add(DiagonalCityTwoPartsThree);
        tileList.add(FourWayCrossroads);
        tileList.add(Grass);
        tileList.add(Marketplace);
        tileList.add(OneSideCityZero);
        tileList.add(OneSideCityOne);
        tileList.add(OneSideCityTwo);
        tileList.add(OneSideCityThree);
        tileList.add(OneSideCityCrossroadsZero);
        tileList.add(OneSideCityCrossroadsOne);
        tileList.add(OneSideCityCrossroadsTwo);
        tileList.add(OneSideCityCrossroadsThree);
        tileList.add(OneSideCityStraightRoadZero);
        tileList.add(OneSideCityStraightRoadOne);
        tileList.add(OneSideCityStraightRoadTwo);
        tileList.add(OneSideCityStraightRoadThree);
        tileList.add(OneSideCityTurningLeftRoadZero);
        tileList.add(OneSideCityTurningLeftRoadOne);
        tileList.add(OneSideCityTurningLeftRoadTwo);
        tileList.add(OneSideCityTurningLeftRoadThree);
        tileList.add(OneSideCityTurningRightRoadZero);
        tileList.add(OneSideCityTurningRightRoadOne);
        tileList.add(OneSideCityTurningRightRoadTwo);
        tileList.add(OneSideCityTurningRightRoadThree);
        tileList.add(RoadStraightZero);
        tileList.add(RoadStraightOne);
        tileList.add(RoadTurningZero);
        tileList.add(RoadTurningOne);
        tileList.add(RoadTurningTwo);
        tileList.add(RoadTurningThree);
        tileList.add(ThreeSidesCityZero);
        tileList.add(ThreeSidesCityOne);
        tileList.add(ThreeSidesCityTwo);
        tileList.add(ThreeSidesCityThree);
        tileList.add(ThreeWayCrossroadsZero);
        tileList.add(ThreeWayCrossroadsOne);
        tileList.add(ThreeWayCrossroadsTwo);
        tileList.add(ThreeWayCrossroadsThree);

        return tileList;
    }

    /**
     * Creates a new instance of Tile
     * @param north number which determines which tiles can go above this tile
     * @param south number which determines which tiles can go below this tile
     * @param west number which determines which tiles can go right of this tile
     * @param east number which determines which tiles can go left this tile
     * @param top number which determines which tiles can go after this tile in the animation
     * @param bottom number which determines which tiles can go before this tile in the animation
     * @param img name of the corresponding .png image. The constructor attempts to load the file as a BufferedImage,
     *            but throws <code>RuntimeException</code> if no image with the given name exists
     */
    public Tile(int north, int south, int west, int east, int top, int bottom, String img) {
        this.north = north;
        this.south = south;
        this.west = west;
        this.east = east;

        this.top = top;
        this.bottom = bottom;

        this.name=img;

        try {
            String path="./src/main/resources/"+img + ".png";
            this.img = ImageIO.read(new File(path));
        } catch(IOException ex) {
            throw new RuntimeException("Greška pri čitanju datoteke.", ex);
        }
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

    @Override
    public String toString(){
        return this.name;
    }

}
