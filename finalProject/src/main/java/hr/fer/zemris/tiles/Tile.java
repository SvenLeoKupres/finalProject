package hr.fer.zemris.tiles;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
    private final BufferedImage img;

    private static final Tile AcrossCityZero =  new Tile(2, 2, 0, 0, 0, 0, "acrossCityZero");
    private static final Tile AcrossCityOne =  new Tile(0, 0, 2, 2, 0, 0, "acrossCityOne");

    private static final Tile AcrossCityTwoPartsZero = new Tile(2, 2, 0, 0, 0, 0, "acrossCityTwoPartsZero");
    public static Tile AcrossCityTwoPartsOne = new Tile(0, 0, 2, 2, 0, 0, "acrossCityTwoPartsOne");

    public static Tile Cathedral = new Tile(0, 0, 0, 0, 0, 0, "cathedral");

    public static Tile CathedralRoadZero = new Tile(1, 0, 0, 0, 0, 0, "cathedralRoadZero");
    public static Tile CathedralRoadOne = new Tile(0, 0, 0, 1, 0, 0, "cathedralRoadOne");
    public static Tile CathedralRoadTwo = new Tile(0, 1, 0, 0, 0, 0, "cathedralRoadTwo");
    public static Tile CathedralRoadThree = new Tile(0, 0, 1, 0, 0, 0, "cathedralRoadThree");

    public static Tile CityEntranceZero = new Tile(1, 2, 2, 2, 0, 0, "cityEntranceZero");
    public static Tile CityEntranceOne = new Tile(2, 2, 2, 1, 0, 0, "cityEntranceOne");
    public static Tile CityEntranceTwo = new Tile(2, 1, 2, 2, 0, 0, "cityEntranceTwo");
    public static Tile CityEntranceThree = new Tile(2, 2, 1, 2, 0, 0, "cityEntranceThree");

    public static Tile DiagonalCityZero = new Tile(2, 0, 0, 2, 0, 0, "diagonalCityZero");
    public static Tile DiagonalCityOne = new Tile(0, 2, 0, 2, 0, 0, "diagonalCityOne");
    public static Tile DiagonalCityTwo = new Tile(0, 2, 2, 0, 0, 0, "diagonalCityTwo");
    public static Tile DiagonalCityThree = new Tile(2, 0, 2, 0, 0, 0, "diagonalCityThree");

    public static Tile DiagonalCityTurningRoadZero = new Tile(2, 1, 1, 2, 0, 0, "diagonalCityTurningRoadZero");
    public static Tile DiagonalCityTurningRoadOne = new Tile(1, 2, 1, 2, 0, 0, "diagonalCityTurningRoadOne");
    public static Tile DiagonalCityTurningRoadTwo = new Tile(1, 2, 2, 1, 0, 0, "diagonalCityTurningRoadTwo");
    public static Tile DiagonalCityTurningRoadThree = new Tile(2, 1, 2, 1, 0, 0, "diagonalCityTurningRoadThree");

    public static Tile DiagonalCityTwoPartsZero = new Tile(2, 0, 0, 2, 0, 0, "diagonalCityTwoPartsZero");
    public static Tile DiagonalCityTwoPartsOne = new Tile(0, 2, 0, 2, 0, 0, "diagonalCityTwoPartsOne");
    public static Tile DiagonalCityTwoPartsTwo = new Tile(0, 2, 2, 0, 0, 0, "diagonalCityTwoPartsTwo");
    public static Tile DiagonalCityTwoPartsThree = new Tile(2, 0, 2, 0, 0, 0, "diagonalCityTwoPartsThree");

    public static Tile FourWayCrossroads = new Tile(1, 1, 1, 1, 0, 0, "fourWayCrossroads");

    public static Tile Grass = new Tile(0, 0, 0, 0, 0, 0, "grass");

    public static Tile Marketplace = new Tile(2, 2, 2, 2, 0, 0, "marketplace");

    public static Tile OneSideCityZero = new Tile(2, 0, 0, 0, 0, 0, "oneSideCityZero");
    public static Tile OneSideCityOne = new Tile(0, 0, 0, 2, 0, 0, "oneSideCityOne");
    public static Tile OneSideCityTwo = new Tile(0, 2, 0, 0, 0, 0, "oneSideCityTwo");
    public static Tile OneSideCityThree = new Tile(0, 0, 2, 0, 0, 0, "oneSideCityThree");

    public static Tile OneSideCityCrossroadsZero = new Tile(2, 1, 1, 1, 0, 0, "oneSideCityCrossroadsZero");
    public static Tile OneSideCityCrossroadsOne = new Tile(1, 1, 1, 2, 0, 0, "oneSideCityCrossroadsOne");
    public static Tile OneSideCityCrossroadsTwo = new Tile(1, 2, 1, 1, 0, 0, "oneSideCityCrossroadsTwo");
    public static Tile OneSideCityCrossroadsThree = new Tile(1, 1, 2, 1, 0, 0, "oneSideCityCrossroadsThree");

    public static Tile OneSideCityStraightRoadZero = new Tile(2, 0, 1, 1, 0, 0, "oneSideCityStraightRoadZero");
    public static Tile OneSideCityStraightRoadOne = new Tile(1, 1, 0, 2, 0, 0, "oneSideCityStraightRoadOne");
    public static Tile OneSideCityStraightRoadTwo = new Tile(0, 2, 1, 1, 0, 0, "oneSideCityStraightRoadTwo");
    public static Tile OneSideCityStraightRoadThree = new Tile(1, 1, 2, 0, 0, 0, "oneSideCityStraightRoadThree");

    public static Tile OneSideCityTurningLeftRoadZero = new Tile(2, 1, 1, 0, 0, 0, "oneSideCityTurningLeftRoadZero");
    public static Tile OneSideCityTurningLeftRoadOne = new Tile(1, 0, 1, 2, 0, 0, "oneSideCityTurningLeftRoadOne");
    public static Tile OneSideCityTurningLeftRoadTwo = new Tile(1, 2, 0, 1, 0, 0, "oneSideCityTurningLeftRoadTwo");
    public static Tile OneSideCityTurningLeftRoadThree = new Tile(0, 1, 2, 1, 0, 0, "oneSideCityTurningLeftRoadThree");

    public static Tile OneSideCityTurningRightRoadZero = new Tile(2, 1, 0, 1, 0, 0, "oneSideCityTurningRightRoadZero");
    public static Tile OneSideCityTurningRightRoadOne = new Tile(0, 1, 1, 2, 0, 0, "oneSideCityTurningRightRoadOne");
    public static Tile OneSideCityTurningRightRoadTwo = new Tile(1, 2, 1, 0, 0, 0, "oneSideCityTurningRightRoadTwo");
    public static Tile OneSideCityTurningRightRoadThree = new Tile(1, 0, 2, 1, 0, 0, "oneSideCityTurningRightRoadThree");

    public static Tile RoadStraightZero = new Tile(1, 1, 0, 0, 0, 0, "roadStraightZero");
    public static Tile RoadStraightOne = new Tile(0, 0, 1, 1, 0, 0, "roadStraightOne");

    public static Tile RoadTurningZero = new Tile(1, 0, 0, 1, 0, 0, "roadTurningZero");
    public static Tile RoadTurningOne = new Tile(0, 1, 0, 1, 0, 0, "roadTurningOne");
    public static Tile RoadTurningTwo = new Tile(0, 1, 1, 0, 0, 0, "roadTurningTwo");
    public static Tile RoadTurningThree = new Tile(1, 0, 1, 0, 0, 0, "roadTurningThree");

    public static Tile StartTileZero = new Tile(2, 0, 1, 1, 0, 0, "startTileZero");
    public static Tile StartTileOne = new Tile(1, 1, 0, 2, 0, 0, "startTileOne");
    public static Tile StartTileTwo = new Tile(0, 2, 1, 1, 0, 0, "startTileTwo");
    public static Tile StartTileThree = new Tile(1, 1, 2, 0, 0, 0, "startTileThree");

    public static Tile ThreeSidesCityZero = new Tile(2, 0, 2, 2, 0, 0, "threeSidesCityZero");
    public static Tile ThreeSidesCityOne = new Tile(2, 2, 0, 2, 0, 0, "threeSidesCityOne");
    public static Tile ThreeSidesCityTwo = new Tile(0, 2, 2, 2, 0, 0, "threeSidesCityTwo");
    public static Tile ThreeSidesCityThree = new Tile(2, 2, 2, 0, 0, 0, "threeSidesCityThree");

    public static Tile ThreeWayCrossroadsZero = new Tile(0, 1, 1, 1, 0, 0, "threeWayCrossroadsZero");
    public static Tile ThreeWayCrossroadsOne = new Tile(1, 1, 1, 0, 0, 0, "threeWayCrossroadsOne");
    public static Tile ThreeWayCrossroadsTwo = new Tile(1, 0, 1, 1, 0, 0, "threeWayCrossroadsTwo");
    public static Tile ThreeWayCrossroadsThree = new Tile(1, 1, 0, 1, 0, 0, "threeWayCrossroadsThree");

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
        tileList.add(StartTileZero);
        tileList.add(StartTileOne);
        tileList.add(StartTileTwo);
        tileList.add(StartTileThree);
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

    public Tile(int north, int south, int west, int east, int top, int bottom, String img) {
        this.north = north;
        this.south = south;
        this.west = west;
        this.east = east;

        this.top = top;
        this.bottom = bottom;

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

}
