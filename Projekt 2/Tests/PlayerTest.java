package model;

import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PlayerTest {

    Player player1,player2;
    Grid grid;
    @Before
    public void init() {
        grid = new Grid(false);
        player1 = new Player("Hans",PlayerType.HUMAN,ColourType.BLUE);
        player2 = new Player("Gerd",PlayerType.AI_NOOB,ColourType.RED);
    }

    @Test
    public void testGetter() {
        assertEquals(player1.getName(),"Hans");
        assertEquals(player1.getColourType().getColour(),ColourType.BLUE.getColour());
        assertEquals(player1.getPlayerType().toString(),PlayerType.HUMAN.toString());
    }

    @Test
    public void testHiscorable() {
        assertTrue(player1.isHighscoreable());
        player1.setHighscoreable(false);
        assertFalse(player1.isHighscoreable());
    }

    @Test
    public void testSetGetGrid() {
        player1.setGrid(grid);
        assertEquals(player1.getGrid(),grid);
    }

    @Test
    public void setGetTypes() {
        ColourType colour = ColourType.BLUE;
        PlayerType player = PlayerType.HUMAN;
        player1.setColourType(colour);
        player1.setPlayerType(player);
        assertEquals(player1.getPlayerType().toString(),player.toString());
        assertEquals(player1.getColourType().getColour(),colour.getColour());
    }

    @Test
    public void testClone() {
        player1.setGrid(grid);
        Player player1Clone = player1.clone();
        assertEquals(player1Clone,player1);
    }

    @Test
    public void testGetColourAsString() {
        Color red = Color.RED;
        Color blue = Color.BLUE;
        Color green = Color.GREEN;
        Color yellow = Color.YELLOW;
        Color pink = Color.PINK;
        player2.setColourType(ColourType.RED);
        assertEquals(player2.getColourAsString(),red);
        player2.setColourType(ColourType.BLUE);
        assertEquals(player2.getColourAsString(),blue);
        player2.setColourType(ColourType.GREEN);
        assertEquals(player2.getColourAsString(),green);
        player2.setColourType(ColourType.YELLOW);
        assertEquals(player2.getColourAsString(),yellow);
        player2.setColourType(null);
        assertEquals(player2.getColourAsString(),pink);
        player1.hashCode();
    }

    @Test
    public void testToString() {
        String name = player1.getName() + " " + player1.getPlayerType().toString() + " " + player1.getColourType().toString();
        assertEquals(player1.toString(),name);
    }
}
