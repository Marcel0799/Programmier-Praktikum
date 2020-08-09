package model;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class TileTest {

    @Before
    public void init () {
        Tile tile1 = new Tile(1,LandscapeType.WATER);
        Tile tile2 = new Tile(2,LandscapeType.GRASS);
        Tile tile3_40_1 = new Tile(3,LandscapeType.WASTELAND,40,true);
    }

    @Test
    public void testConstructorOne() {
        Tile tile2 = new Tile(2,LandscapeType.GRASS);
        assertEquals(tile2.getCrowns(),2);
        assertEquals(tile2.getLandscapeType(),LandscapeType.GRASS);
    }


    @Test
    public void testConstructorTwo() {
        Tile tile4_40_2 = new Tile(3,LandscapeType.ROCKS,40,false);
        assertEquals(tile4_40_2.getCrowns(),3);
        Tile tile3_40_1 = new Tile(3,LandscapeType.WASTELAND,40,true);
        assertEquals(tile3_40_1.getCrowns(),3);
        assertEquals(tile3_40_1.getLandscapeType(),LandscapeType.WASTELAND);
        File file = tile3_40_1.getImage();
        String[] split = file.toURI().toString().split("/");
        String end = split[split.length-1];
        assertEquals(end, "tile40_1.png");
    }

    @Test
    public void testToString() {
        Tile start = new Tile(0,LandscapeType.STARTING_FIELD);
        assertEquals(start.toString(),"Castle");
        Tile water = new Tile(3,LandscapeType.WATER);
        assertEquals(water.toString(),"Water3");
    }
}
