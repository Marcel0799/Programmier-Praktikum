package model;
import application.Tuple;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

public class GridTest {
    Grid grid1, grid2, grid3;
    Tile tile1,tile2,tile3,tile4;

    @Before
    public void init() {
        grid1 = new Grid(false);
        grid2 = new Grid(false);
        grid3 = new Grid(true);
        tile1 = new Tile(1,LandscapeType.WATER);
        tile2 = new Tile(2,LandscapeType.WATER);
        tile3 = new Tile(3,LandscapeType.GRASS);
        tile4 = new Tile(4,LandscapeType.GRASS);
        //init grid 1
        grid1.place(tile1,1,1);
        grid1.place(tile2,1,2);
        grid1.place(tile3,2,1);
        grid1.place(tile4,2,2);
        // init grid 2
        grid2.place(tile1,1,1);
        grid2.place(tile2,8,8);
        // inti grid 3
        grid3.place(tile1,1,1);
        grid3.place(tile2,1,2);
    }

    @Test
    public void testGetter() {
        assertEquals(grid1.getTileFromPosition(1,1),tile1);
        assertEquals(grid1.getStartingOffset(),6);
        assertEquals(grid1.getGridLength(),13);
    }

    @Test
    public void testAusgabe() {
        grid1.showGrid();
    }

    @Test
    public void testClone() {
        Grid grid1Clone = grid1.clone();
        assertNotEquals(grid1Clone, grid1);
        assertEquals(grid1Clone.getTileFromPosition(1,1),grid1.getTileFromPosition(1,1));
    }

    @Test
    public void testBorders() {
        grid1.refreshBorders();
        grid2.refreshBorders();
        grid3.refreshBorders();
        assertEquals(grid1.getLeftBorder(),1);
        assertEquals(grid1.getRightBorder(),6);
        assertEquals(grid1.getUpperBorder(),6);
        assertEquals(grid1.getLowestBorder(),1);
    }

    @Test
    public void testOccupied() {
        assertTrue(grid1.isOccupied(1,1));
        assertFalse(grid1.isOccupied(4,4));
    }

    @Test
    public void testScore() {
        assertEquals(grid1.getScore(),20);
        assertEquals(grid2.getScore(),3);
        assertEquals(grid3.getScore(),6);

        assertEquals(grid2.endScore(true,true),8);
        assertEquals(grid3.endScore(true,true),11);
    }
    @Test
    public void testGetLandscapeType() {
        LinkedList<LandscapeType> area = grid1.allLandscapeTypesPlaceable();
        assertTrue(area.contains(LandscapeType.WATER));
        assertTrue(area.contains(LandscapeType.GRASS));
    }
    @Test
    public void testGetNeibhourPosition() {
        LinkedList<Tuple<Integer,Integer>> area = grid3.neighbourPositions(1,1);
        assertEquals(area.getFirst().getFirst(),(Integer)2);
        assertEquals(area.getFirst().getSecond(),(Integer)1);
    }

}
