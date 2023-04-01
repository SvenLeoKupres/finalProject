package hr.fer.tiles;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

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

    public static Tile DiagonalCityZero = new Tile(2, 0, 0, 2, top, bottom, "diagonalCityZero");
    public static Tile DiagonalCityOne = new Tile(0, 2, 0, 2, top, bottom, "diagonalCityOne");
    public static Tile DiagonalCityTwo = new Tile(0, 2, 2, 0, top, bottom, "diagonalCityTwo");
    public static Tile DiagonalCityThree = new Tile(2, 0, 2, 0, top, bottom, "diagonalCityThree");

    public static Tile DiagonalCityTurningRoadZero = new Tile(2, 1, 1, 2, top, bottom, img);
    public static Tile DiagonalCityTurningRoadOne = new Tile(1, 2, 1, 2, top, bottom, img);
    public static Tile DiagonalCityTurningRoadTwo = new Tile(1, 2, 2, 1, top, bottom, img);
    public static Tile DiagonalCityTurningRoadThree = new Tile(2, 1, 2, 1, top, bottom, img);

    public static Tile DiagonalCityTwoPartsZero = new Tile(2, 0, 0, 2, top, bottom, img);
    public static Tile DiagonalCityTwoPartsOne = new Tile(0, 2, 0, 2, top, bottom, img);
    public static Tile DiagonalCityTwoPartsTwo = new Tile(0, 2, 2, 0, top, bottom, img);
    public static Tile DiagonalCityTwoPartsThree = new Tile(2, 0, 2, 0, top, bottom, img);

    public static Tile FourWayCrossroads = new Tile(1, 1, 1, 1, top, bottom, "fourWayCrossroads");

    public static Tile Grass = new Tile(0, 0, 0, 0, top, bottom, "grass");

    public static Tile Marketplace = new Tile(2, 2, 2, 2, top, bottom, img);

    public static Tile OneSideCityZero = new Tile(2, 0, 0, 0, top, bottom, "oneSideCityZero");
    public static Tile OneSideCityOne = new Tile(0, 0, 0, 2, top, bottom, "oneSideCityOne");
    public static Tile OneSideCityTwo = new Tile(0, 2, 0, 0, top, bottom, "oneSideCityTwo");
    public static Tile OneSideCityThree = new Tile(0, 0, 2, 0, top, bottom, "oneSideCityThree");

    public static Tile OneSideCityCrossroadsZero = new Tile(2, 1, 1, 1, top, bottom, img);
    public static Tile OneSideCityCrossroadsOne = new Tile(1, 1, 1, 2, top, bottom, img);
    public static Tile OneSideCityCrossroadsTwo = new Tile(1, 2, 1, 1, top, bottom, img);
    public static Tile OneSideCityCrossroadsThree = new Tile(1, 1, 2, 1, top, bottom, img);

    public static Tile OneSideCityStraightRoadZero = new Tile(2, 0, 1, 1, top, bottom, img);
    public static Tile OneSideCityStraightRoadOne = new Tile(1, 1, 0, 2, top, bottom, img);
    public static Tile OneSideCityStraightRoadTwo = new Tile(0, 2, 1, 1, top, bottom, img);
    public static Tile OneSideCityStraightRoadThree = new Tile(1, 1, 2, 0, top, bottom, img);

    public static Tile OneSideCityTurningRightRoadZero = new Tile(2, 1, 1, 0, top, bottom, img);
    public static Tile OneSideCityTurningRightRoadOne = new Tile(1, 0, 1, 2, top, bottom, img);
    public static Tile OneSideCityTurningRightRoadTwo = new Tile(1, 2, 0, 1, top, bottom, img);
    public static Tile OneSideCityTurningRightRoadThree = new Tile(0, 1, 2, 1, top, bottom, img);

    public static Tile OneSideCityTurningLeftRoadZero = new Tile(2, 1, 0, 1, top, bottom, img);
    public static Tile OneSideCityTurningLeftRoadOne = new Tile(0, 1, 1, 2, top, bottom, img);
    public static Tile OneSideCityTurningLeftRoadTwo = new Tile(1, 2, 1, 0, top, bottom, img);
    public static Tile OneSideCityTurningLeftRoadThree = new Tile(1, 0, 2, 1, top, bottom, img);

    public static Tile RoadStraightZero = new Tile(1, 1, 0, 0, top, bottom, "roadStraightZero");
    public static Tile RoadStraightOne = new Tile(0, 0, 1, 1, top, bottom, "roadStraightOne");

    public static Tile RoadTurningZero = new Tile(1, 0, 0, 1, top, bottom, "roadTurningZero");
    public static Tile RoadTurningOne = new Tile(0, 1, 0, 1, top, bottom, "roadTurningOne");
    public static Tile RoadTurningTwo = new Tile(0, 1, 1, 0, top, bottom, "roadTurningTwo");
    public static Tile RoadTurningThree = new Tile(1, 0, 1, 0, top, bottom, "roadTurningThree");

    public static Tile StartTileZero = new Tile(2, 0, 1, 1, top, bottom, img);
    public static Tile StartTileOne = new Tile(1, 1, 0, 2, top, bottom, img);
    public static Tile StartTileTwo = new Tile(0, 2, 1, 1, top, bottom, img);
    public static Tile StartTileThree = new Tile(1, 1, 2, 0, top, bottom, img);

    public static Tile ThreeSidesCityZero = new Tile(2, 0, 2, 2, top, bottom, "threeSidesCityZero");
    public static Tile ThreeSidesCityOne = new Tile(2, 2, 0, 2, top, bottom, "threeSidesCityOne");
    public static Tile ThreeSidesCityTwo = new Tile(0, 2, 2, 2, top, bottom, "threeSidesCityTwo");
    public static Tile ThreeSidesCityThree = new Tile(2, 2, 2, 0, top, bottom, "threeSidesCityThree");

    public static Tile ThreeWayCrossroadsZero = new Tile(0, 1, 1, 1, top, bottom, "threeWayCrossroadsZero");
    public static Tile ThreeWayCrossroadsOne = new Tile(1, 1, 1, 0, top, bottom, "threeWayCrossroadsOne");
    public static Tile ThreeWayCrossroadsTwo = new Tile(1, 0, 1, 1, top, bottom, "threeWayCrossroadsTwo");
    public static Tile ThreeWayCrossroadsThree = new Tile(1, 1, 0, 1, top, bottom, "threeWayCrossroadsThree");

    public Tile(int north, int south, int west, int east, int top, int bottom, String img) {
        this.north = north;
        this.south = south;
        this.west = west;
        this.east = east;

        this.top = top;
        this.bottom = bottom;

        try {
            this.img = ImageIO.read(new File(img + ".png"));
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
