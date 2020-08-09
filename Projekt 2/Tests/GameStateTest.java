package model;

import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class GameStateTest {
    Player playerOne;
    GameState gameState;
    LinkedList<Player> players,fixedPlayers;
    List<Domino> bag,choice;
    boolean[] settings;

    LocalDateTime jetzt = LocalDateTime.now();
    LocalDateTime einMonat = LocalDateTime.now().minusMonths(1);
    Duration duration1;

    @Before
    public void init () {
        Tile tile1 = new Tile(3,LandscapeType.GRASS);
        Tile tile2 = new Tile(3,LandscapeType.GRASS);
        Domino domino = new Domino(40,tile1,tile2);
        playerOne = new Player("Hans",PlayerType.HUMAN,ColourType.GREEN);
        players = new LinkedList<>();
        players.add(playerOne);
        bag = new LinkedList<>();
        bag.add(domino);
        choice = new LinkedList<>();
        choice.add(domino);
        fixedPlayers = new LinkedList<>();
        settings = new boolean[9];
        gameState = new GameState(players,settings,bag,choice,fixedPlayers);

        duration1 = Duration.between(einMonat,jetzt);
    }

    @Test
    public void testGetterAndSetter() {
        assertEquals(gameState.getChoice(),choice);
        assertEquals(gameState.getDominoBag(),bag);
        gameState.setPlayers(players);
        assertEquals(gameState.getPlayers(),players);
        gameState.setCurrent(choice);
        assertEquals(gameState.getCurrent(),choice);
        gameState.setGamesToPlay(3);
        assertEquals(gameState.getGamesToPlay(),3);
        assertEquals(gameState.getCurrentPlayer(),playerOne);
        assertEquals(gameState.getFixedPlayersList(),fixedPlayers);
        assertFalse(gameState.hasPredecessor());
        assertFalse(gameState.hasSuccessor());
        assertFalse(gameState.isDynasty());
        assertFalse(gameState.isHarmony());
        assertFalse((gameState.isKingdomOfTheMiddle()));
        assertFalse((gameState.isTheGreatDuel()));
        assertFalse((gameState.isGameOver()));
        gameState.setShowHints(true);
        gameState.setShowGameLog(true);
        assertTrue(gameState.isShowGameLogActive());
        assertTrue(gameState.isShowHintsActive());
        //getter
        gameState.isShowHintsActive();
        gameState.isShowGameLogActive();
    }

    @Test
    public void testPredAndSucc() {
        gameState.setCurrent(choice);
        GameState succ = gameState.clone();
        assertEquals(gameState.getSuccessor(),succ);
        GameState pred = gameState.clone();
        gameState.setPredecessor(pred);
        gameState.setSuccessor(succ);
        assertEquals(gameState.getSuccessor(),succ);
        assertEquals(gameState.getPredecessor(),pred);
    }

    @Test
    public void testTime() {
        gameState.setGameTime(duration1);
        assertEquals(gameState.getGameTime(),duration1);
        gameState.getStartTime();
    }
}
