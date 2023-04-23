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
    private final String name;
    private final BufferedImage img;

    private static final Tile AcrossCityZero =  new Tile(2, 2, 0, 0, 0, 0, "acrossCityZero");
    private static final Tile AcrossCityOne =  new Tile(0, 0, 2, 2, 0, 0, "acrossCityOne");

    private static final Tile AcrossCityTwoPartsZero = new Tile(2, 2, 0, 0, 0, 0, "acrossCityTwoPartsZero");
    private static final Tile AcrossCityTwoPartsOne = new Tile(0, 0, 2, 2, 0, 0, "acrossCityTwoPartsOne");

    private static final Tile Cathedral = new Tile(0, 0, 0, 0, 0, 0, "cathedral");

    private static final Tile CathedralRoadZero = new Tile(1, 0, 0, 0, 0, 0, "cathedralRoadZero");
    private static final Tile CathedralRoadOne = new Tile(0, 0, 0, 1, 0, 0, "cathedralRoadOne");
    private static final Tile CathedralRoadTwo = new Tile(0, 1, 0, 0, 0, 0, "cathedralRoadTwo");
    private static final Tile CathedralRoadThree = new Tile(0, 0, 1, 0, 0, 0, "cathedralRoadThree");

    private static final Tile CityEntranceZero = new Tile(1, 2, 2, 2, 0, 0, "cityEntranceZero");
    private static final Tile CityEntranceOne = new Tile(2, 2, 2, 1, 0, 0, "cityEntranceOne");
    private static final Tile CityEntranceTwo = new Tile(2, 1, 2, 2, 0, 0, "cityEntranceTwo");
    private static final Tile CityEntranceThree = new Tile(2, 2, 1, 2, 0, 0, "cityEntranceThree");

    private static final Tile DiagonalCityZero = new Tile(2, 0, 0, 2, 0, 0, "diagonalCityZero");
    private static final Tile DiagonalCityOne = new Tile(0, 2, 0, 2, 0, 0, "diagonalCityOne");
    private static final Tile DiagonalCityTwo = new Tile(0, 2, 2, 0, 0, 0, "diagonalCityTwo");
    private static final Tile DiagonalCityThree = new Tile(2, 0, 2, 0, 0, 0, "diagonalCityThree");

    private static final Tile DiagonalCityTurningRoadZero = new Tile(2, 1, 1, 2, 0, 0, "diagonalCityTurningRoadZero");
    private static final Tile DiagonalCityTurningRoadOne = new Tile(1, 2, 1, 2, 0, 0, "diagonalCityTurningRoadOne");
    private static final Tile DiagonalCityTurningRoadTwo = new Tile(1, 2, 2, 1, 0, 0, "diagonalCityTurningRoadTwo");
    private static final Tile DiagonalCityTurningRoadThree = new Tile(2, 1, 2, 1, 0, 0, "diagonalCityTurningRoadThree");

    private static final Tile DiagonalCityTwoPartsZero = new Tile(2, 0, 0, 2, 0, 0, "diagonalCityTwoPartsZero");
    private static final Tile DiagonalCityTwoPartsOne = new Tile(0, 2, 0, 2, 0, 0, "diagonalCityTwoPartsOne");
    private static final Tile DiagonalCityTwoPartsTwo = new Tile(0, 2, 2, 0, 0, 0, "diagonalCityTwoPartsTwo");
    private static final Tile DiagonalCityTwoPartsThree = new Tile(2, 0, 2, 0, 0, 0, "diagonalCityTwoPartsThree");

    private static final Tile FourWayCrossroads = new Tile(1, 1, 1, 1, 0, 0, "fourWayCrossroads");

    private static final Tile Grass = new Tile(0, 0, 0, 0, 0, 0, "grass");

    private static final Tile Marketplace = new Tile(2, 2, 2, 2, 0, 0, "marketplace");

    private static final Tile OneSideCityZero = new Tile(2, 0, 0, 0, 0, 0, "oneSideCityZero");
    private static final Tile OneSideCityOne = new Tile(0, 0, 0, 2, 0, 0, "oneSideCityOne");
    private static final Tile OneSideCityTwo = new Tile(0, 2, 0, 0, 0, 0, "oneSideCityTwo");
    private static final Tile OneSideCityThree = new Tile(0, 0, 2, 0, 0, 0, "oneSideCityThree");

    private static final Tile OneSideCityCrossroadsZero = new Tile(2, 1, 1, 1, 0, 0, "oneSideCityCrossroadsZero");
    private static final Tile OneSideCityCrossroadsOne = new Tile(1, 1, 1, 2, 0, 0, "oneSideCityCrossroadsOne");
    private static final Tile OneSideCityCrossroadsTwo = new Tile(1, 2, 1, 1, 0, 0, "oneSideCityCrossroadsTwo");
    private static final Tile OneSideCityCrossroadsThree = new Tile(1, 1, 2, 1, 0, 0, "oneSideCityCrossroadsThree");

    private static final Tile OneSideCityStraightRoadZero = new Tile(2, 0, 1, 1, 0, 0, "oneSideCityStraightRoadZero");
    private static final Tile OneSideCityStraightRoadOne = new Tile(1, 1, 0, 2, 0, 0, "oneSideCityStraightRoadOne");
    private static final Tile OneSideCityStraightRoadTwo = new Tile(0, 2, 1, 1, 0, 0, "oneSideCityStraightRoadTwo");
    private static final Tile OneSideCityStraightRoadThree = new Tile(1, 1, 2, 0, 0, 0, "oneSideCityStraightRoadThree");

    private static final Tile OneSideCityTurningLeftRoadZero = new Tile(2, 1, 1, 0, 0, 0, "oneSideCityTurningLeftRoadZero");
    private static final Tile OneSideCityTurningLeftRoadOne = new Tile(1, 0, 1, 2, 0, 0, "oneSideCityTurningLeftRoadOne");
    private static final Tile OneSideCityTurningLeftRoadTwo = new Tile(1, 2, 0, 1, 0, 0, "oneSideCityTurningLeftRoadTwo");
    private static final Tile OneSideCityTurningLeftRoadThree = new Tile(0, 1, 2, 1, 0, 0, "oneSideCityTurningLeftRoadThree");

    private static final Tile OneSideCityTurningRightRoadZero = new Tile(2, 1, 0, 1, 0, 0, "oneSideCityTurningRightRoadZero");
    private static final Tile OneSideCityTurningRightRoadOne = new Tile(0, 1, 1, 2, 0, 0, "oneSideCityTurningRightRoadOne");
    private static final Tile OneSideCityTurningRightRoadTwo = new Tile(1, 2, 1, 0, 0, 0, "oneSideCityTurningRightRoadTwo");
    private static final Tile OneSideCityTurningRightRoadThree = new Tile(1, 0, 2, 1, 0, 0, "oneSideCityTurningRightRoadThree");

    private static final Tile RoadStraightZero = new Tile(1, 1, 0, 0, 0, 0, "roadStraightZero");
    private static final Tile RoadStraightOne = new Tile(0, 0, 1, 1, 0, 0, "roadStraightOne");

    private static final Tile RoadTurningZero = new Tile(1, 0, 0, 1, 0, 0, "roadTurningZero");
    private static final Tile RoadTurningOne = new Tile(0, 1, 0, 1, 0, 0, "roadTurningOne");
    private static final Tile RoadTurningTwo = new Tile(0, 1, 1, 0, 0, 0, "roadTurningTwo");
    private static final Tile RoadTurningThree = new Tile(1, 0, 1, 0, 0, 0, "roadTurningThree");

    private static final Tile StartTileZero = new Tile(2, 0, 1, 1, 0, 0, "startTileZero");
    private static final Tile StartTileOne = new Tile(1, 1, 0, 2, 0, 0, "startTileOne");
    private static final Tile StartTileTwo = new Tile(0, 2, 1, 1, 0, 0, "startTileTwo");
    private static final Tile StartTileThree = new Tile(1, 1, 2, 0, 0, 0, "startTileThree");

    private static final Tile ThreeSidesCityZero = new Tile(2, 0, 2, 2, 0, 0, "threeSidesCityZero");
    private static final Tile ThreeSidesCityOne = new Tile(2, 2, 0, 2, 0, 0, "threeSidesCityOne");
    private static final Tile ThreeSidesCityTwo = new Tile(0, 2, 2, 2, 0, 0, "threeSidesCityTwo");
    private static final Tile ThreeSidesCityThree = new Tile(2, 2, 2, 0, 0, 0, "threeSidesCityThree");

    private static final Tile ThreeWayCrossroadsZero = new Tile(0, 1, 1, 1, 0, 0, "threeWayCrossroadsZero");
    private static final Tile ThreeWayCrossroadsOne = new Tile(1, 1, 1, 0, 0, 0, "threeWayCrossroadsOne");
    private static final Tile ThreeWayCrossroadsTwo = new Tile(1, 0, 1, 1, 0, 0, "threeWayCrossroadsTwo");
    private static final Tile ThreeWayCrossroadsThree = new Tile(1, 1, 0, 1, 0, 0, "threeWayCrossroadsThree");

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
