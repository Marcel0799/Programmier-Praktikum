package model;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class KingdominoTest {
    KingDomino kingDomino1, kingDomino2;
    GameState gameState;
    Highscore highscore;

    @Before
    public void init () {
        // intit Gamestate
        LinkedList<Player> players = new LinkedList<>();
        List<Domino> bag = new LinkedList<>();
        List<Domino> choice = new LinkedList<>();
        LinkedList<Player> FixedPlayers = new LinkedList<>();
        boolean[] settings = new boolean[9];
        gameState = new GameState(players,settings,bag,choice,FixedPlayers);
        // init HIghscore
        highscore = new Highscore();
        // init kingDomino
        kingDomino1 = new KingDomino();
        kingDomino2 = new KingDomino(gameState,highscore);
    }

    @Test
    public void testGetSetCurrentGame() {
        kingDomino1.setCurrentGame(gameState);
        assertEquals(kingDomino1.getCurrentGame(),gameState);
    }

    @Test
    public void testGetSetHighScore() {
        kingDomino1.setHighscore(highscore);
        assertEquals(kingDomino1.getHighscore(),highscore);
    }
}
