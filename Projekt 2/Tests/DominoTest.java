package model;
import application.Tuple;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class DominoTest {

    Tile tile1,tile2;
    Domino domino1;
    @Before
    public void init(){
        tile1 = new Tile(1,LandscapeType.ROCKS);
        tile2 = new Tile(2,LandscapeType.WATER);
        domino1 = new Domino(40,tile1,tile2);
    }

    @Test
    public void testGetNumber() {
        assertEquals(domino1.getNumber(),40);
    }

    @Test
    public void testGetTiles() {
        Tuple<Tile,Tile> tiles = domino1.getTiles();
        assertEquals(tile1,tiles.getFirst());
        assertEquals(tile2,tiles.getSecond());
    }

    @Test
    public void testGetSetPlayerAndIsTaken() {
        assertFalse(domino1.isTaken());
        Player player = new Player("Hans",PlayerType.HUMAN,ColourType.BLUE);
        domino1.setPlayer(player);
        assertTrue(domino1.isTaken());
        assertEquals(player,domino1.getPlayer());
    }

    @Test
    public void testGetImage() {
        File file = domino1.getIMAGE();
        String[] split = file.toURI().toString().split("/");
        String end = split[split.length-1];
        assertEquals(end,"tile40.png");
    }

    @Test
    public void testClone() {
        Domino domino1Clone = domino1.clone();
        assertEquals(domino1Clone,domino1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEqualWrong() {
        domino1.equals(tile1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCompareWrong() {
        domino1.compareTo(tile1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCompareNull() {
        domino1.compareTo(null);
    }

    @Test
    public void testCompareTo() {
        assertEquals(domino1.compareTo(domino1.clone()),0);
    }
}
