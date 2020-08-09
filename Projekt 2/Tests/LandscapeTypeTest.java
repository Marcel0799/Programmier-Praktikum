package model;

import org.junit.Test;
import static org.junit.Assert.*;

public class LandscapeTypeTest {

    @Test
    public void testToString() {
        LandscapeType water = LandscapeType.WATER;
        LandscapeType grass = LandscapeType.GRASS;
        LandscapeType forest = LandscapeType.FOREST;
        LandscapeType desert = LandscapeType.DESERT;
        LandscapeType startingField = LandscapeType.STARTING_FIELD;
        LandscapeType rocks = LandscapeType.ROCKS;
        LandscapeType wasteland = LandscapeType.WASTELAND;

        assertEquals(water.toString(),"Water");
        assertEquals(grass.toString(),"Grass");
        assertEquals(forest.toString(),"Forest");
        assertEquals(desert.toString(),"Desert");
        assertEquals(startingField.toString(), "Castle");
        assertEquals(rocks.toString(),"Rocks");
        assertEquals(wasteland.toString(),"Wasteland");
    }
}
