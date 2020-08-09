package controller;

import application.Tuple;
import model.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import view.aui.GameAUI;
import view.aui.PlayerAUI;
import controller.factory.DominoFactory;
import static org.junit.Assert.*;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

public class GameControllerTest {

    private MainController mainController;
    private GameController toTest;
    private DominoFactory dominoFactory;

    @Before
    public void setUp() throws Exception {
        mainController = new MainController();
        mainController.setKingDomino(new KingDomino());

        toTest = mainController.getGameController();
        toTest.setGameAUI(new SimulateAUI());
        toTest.setPlayerAUI(new SimulatePlayerAUI());
        dominoFactory = new DominoFactory();
    }

    @Test
    public void testCreateGame() {
        String csvPath = "";

        Player player1 = new Player("PLAYER1", PlayerType.AI_NOOB, ColourType.BLUE);
        Player player2 = new Player("PLAYER2", PlayerType.HUMAN, ColourType.RED);
        player1.setGrid(new Grid(false));
        player2.setGrid(new Grid(false));
        LinkedList<Player> players = new LinkedList<>();
        players.add(player1);
        players.add(player2);

        boolean[] boolSettings = new boolean[]{false, false, false, false, false, false};
        Domino firstdomino = dominoFactory.getDomino(1);

        assert (toTest.createGame(players, false, csvPath, boolSettings));

        assert (mainController.getKingDomino().getCurrentGame().getFixedPlayersList().size() == 2);

        assert (mainController.getKingDomino().getCurrentGame().getChoice().get(0).equals(firstdomino));

        assert (!mainController.getKingDomino().getCurrentGame().isDynasty());
        assert (!mainController.getKingDomino().getCurrentGame().isHarmony());
        assert (!mainController.getKingDomino().getCurrentGame().isKingdomOfTheMiddle());
        assert (!mainController.getKingDomino().getCurrentGame().isTheGreatDuel());

        //new Test




    }

    @Test
    public void testCreateGameWithDifferentSettings() {
        String csvPath = "";
        LinkedList<Player> players = new LinkedList<>();
        Player player1 = new Player("PLAYER1", PlayerType.AI_NOOB, ColourType.BLUE);
        Player player2 = new Player("PLAYER2", PlayerType.HUMAN, ColourType.RED);
        Player player3 = new Player("Player3", PlayerType.HUMAN, ColourType.GREEN);
        player1.setGrid(new Grid(true));
        player2.setGrid(new Grid(true));
        player3.setGrid(new Grid(true));
        players.add(player3);
        players.add(player2);
        boolean[] boolSettings2 = new boolean[]{true,true,true,true,true,true};

        Domino firstdomino = dominoFactory.getDomino(1);
        assert(toTest.createGame(players,true,csvPath,boolSettings2));
        assertEquals(mainController.getKingDomino().getCurrentGame().getFixedPlayersList().size(),2);
        assertFalse(mainController.getKingDomino().getCurrentGame().getChoice().get(0).equals(firstdomino));

        assert (mainController.getKingDomino().getCurrentGame().isDynasty());
        assert (mainController.getKingDomino().getCurrentGame().isHarmony());
        assert (mainController.getKingDomino().getCurrentGame().isKingdomOfTheMiddle());
        assert (mainController.getKingDomino().getCurrentGame().isTheGreatDuel());
    }

    @Test
    public void testSetChoice() {
        LinkedList<Domino> bag = new LinkedList<>();
        LinkedList<Player> allPlayer = new LinkedList<>();
        Player player1 = new Player("Hannes", PlayerType.AI_CHALLENGER, ColourType.GREEN);
        Player player2 = new Player("Hannes", PlayerType.HUMAN, ColourType.RED);
        Player player3 = new Player("Hannes", PlayerType.AI_NOOB, ColourType.BLUE);
        Player player4 = new Player("Hannes", PlayerType.AI_NOOB, ColourType.GREEN);
        Tile tile1 = new Tile(1, LandscapeType.FOREST);
        Tile tile2 = new Tile(2, LandscapeType.GRASS);
        Tile tile3 = new Tile(3, LandscapeType.DESERT);
        Tile tile4 = new Tile(4, LandscapeType.ROCKS);
        Tile tile5 = new Tile(5, LandscapeType.FOREST);
        Tile tile6 = new Tile(6, LandscapeType.WASTELAND);
        Tile tile7 = new Tile(7, LandscapeType.FOREST);
        Tile tile8 = new Tile(8, LandscapeType.WASTELAND);
        Domino domino1 = new Domino(1, tile1, tile2);
        Domino domino2 = new Domino(1, tile2, tile3);
        Domino domino3 = new Domino(1, tile4, tile5);
        Domino domino4 = new Domino(1, tile6, tile7);
        bag.add(domino1);
        bag.add(domino2);
        bag.add(domino3);
        bag.add(domino4);
        allPlayer.add(player1);
        allPlayer.add(player2);
        allPlayer.add(player3);
        allPlayer.add(player4);
        int nrOfDominos = bag.size();
        List<Domino> choiceList = mainController.getGameController().setChoice(allPlayer, bag);
        Assert.assertTrue(bag.isEmpty());
        Assert.assertEquals(choiceList.size(), nrOfDominos);
    }

    @Test
    public void testThrowDominosAway() {
        int playerCount = 4;
        LinkedList<Domino> bag = new LinkedList<>();
        Tile tile1 = new Tile(1, LandscapeType.FOREST);
        Tile tile2 = new Tile(2, LandscapeType.GRASS);
        Tile tile3 = new Tile(3, LandscapeType.DESERT);
        Tile tile4 = new Tile(4, LandscapeType.ROCKS);
        Tile tile5 = new Tile(5, LandscapeType.FOREST);
        Tile tile6 = new Tile(6, LandscapeType.WASTELAND);
        Tile tile7 = new Tile(7, LandscapeType.FOREST);
        Domino domino1 = new Domino(1, tile1, tile2);
        Domino domino2 = new Domino(2, tile2, tile3);
        Domino domino3 = new Domino(3, tile4, tile5);
        Domino domino4 = new Domino(4, tile6, tile7);
        Domino domino5 = new Domino(5, tile1, tile2);
        Domino domino6 = new Domino(6, tile2, tile3);
        Domino domino7 = new Domino(7, tile4, tile5);
        Domino domino8 = new Domino(8, tile6, tile7);
        Domino domino9 = new Domino(9, tile1, tile2);
        Domino domino10 = new Domino(10, tile2, tile3);
        Domino domino11 = new Domino(11, tile4, tile5);
        Domino domino12 = new Domino(12, tile6, tile7);
        Domino domino13 = new Domino(13, tile1, tile2);
        Domino domino14 = new Domino(14, tile2, tile3);
        Domino domino15 = new Domino(15, tile4, tile5);
        Domino domino16 = new Domino(16, tile6, tile7);
        Domino domino17 = new Domino(17, tile1, tile2);
        Domino domino18 = new Domino(18, tile2, tile3);
        Domino domino19 = new Domino(19, tile4, tile5);
        Domino domino20 = new Domino(20, tile6, tile7);

        bag.add(domino1);
        bag.add(domino2);
        bag.add(domino3);
        bag.add(domino4);
        bag.add(domino5);
        bag.add(domino6);
        bag.add(domino7);
        bag.add(domino8);
        bag.add(domino9);
        bag.add(domino10);
        bag.add(domino11);
        bag.add(domino12);
        bag.add(domino13);
        bag.add(domino14);
        bag.add(domino15);
        bag.add(domino16);
        bag.add(domino17);
        bag.add(domino18);
        bag.add(domino19);
        bag.add(domino20);
        bag.add(domino1);
        bag.add(domino2);
        bag.add(domino3);
        bag.add(domino4);
        bag.add(domino5);
        bag.add(domino6);
        bag.add(domino7);
        bag.add(domino8);
        bag.add(domino9);
        bag.add(domino10);
        bag.add(domino11);
        bag.add(domino12);
        bag.add(domino13);
        bag.add(domino14);
        bag.add(domino15);
        bag.add(domino16);
        bag.add(domino17);
        bag.add(domino18);
        bag.add(domino19);
        bag.add(domino20);
        bag.add(domino13);
        bag.add(domino14);
        bag.add(domino15);
        bag.add(domino16);
        bag.add(domino17);
        bag.add(domino18);
        bag.add(domino19);
        bag.add(domino20);
        LinkedList<Domino> tmpBag = new LinkedList<>();
        tmpBag.addAll(bag);
        LinkedList<Domino> bagTmp = mainController.getGameController().throwDominosAway(bag, true, 4);
        Assert.assertEquals(bagTmp.size(), bag.size());
        bag.clear();
        bag.addAll(tmpBag);
        bagTmp = mainController.getGameController().throwDominosAway(bag, false, 3);
        Assert.assertEquals(36, bagTmp.size());
        bag.clear();
        bag.addAll(tmpBag);
        bagTmp = mainController.getGameController().throwDominosAway(bag, false, 2);
        Assert.assertTrue(bagTmp.size() == 24);
    }

    @Test
    public void testAddPointsToDynasty(){
        Player player1 = new Player("Hannes", PlayerType.AI_CHALLENGER, ColourType.GREEN);
        Player player2 = new Player("Gerd", PlayerType.HUMAN, ColourType.RED);
        LinkedList<Tuple<Player,Integer>> oldList = new LinkedList<>();
        LinkedList<Tuple<Player,Integer>> newList = new LinkedList<>();
        oldList.add(new Tuple<>(player1,10));
        newList.add(new Tuple<>(player1,20));
        LinkedList<Tuple<Player,Integer>> result = toTest.addPointsToDynasty(oldList,newList);
        assertEquals(result.getFirst().getSecond(),(Integer)30);
    }
    @Test(expected = IllegalStateException.class)
    public void testAddPointsToDynastyNull(){

        Player player1 = new Player("Hannes", PlayerType.AI_CHALLENGER, ColourType.GREEN);
        Player player2 = new Player("Hannes", PlayerType.HUMAN, ColourType.RED);
        LinkedList<Tuple<Player,Integer>> oldList = new LinkedList<>();
        LinkedList<Tuple<Player,Integer>> newList = new LinkedList<>();
        oldList.add(new Tuple<>(player1,12));
        toTest.addPointsToDynasty(oldList,newList);
    }

    @Test
    public void  testEndGame(){
        String csvPath = "";
        LinkedList<Domino> bag = new LinkedList<>();
        LinkedList<Player> allPlayer = new LinkedList<>();
        Player player1 = new Player("Hannes",PlayerType.AI_CHALLENGER,ColourType.GREEN);
        Player player2 = new Player("Hannes",PlayerType.HUMAN,ColourType.RED);
        Player player3 = new Player("Hannes",PlayerType.AI_NOOB,ColourType.BLUE);
        Player player4 = new Player("Hannes",PlayerType.AI_NOOB,ColourType.GREEN);
        Tile tile1 = new Tile(1, LandscapeType.FOREST);
        Tile tile2 = new Tile(2, LandscapeType.GRASS);
        Tile tile3 = new Tile(3, LandscapeType.DESERT);
        Tile tile4 = new Tile(4, LandscapeType.ROCKS);
        Tile tile5 = new Tile(5, LandscapeType.FOREST);
        Tile tile6 = new Tile(6, LandscapeType.WASTELAND);
        Tile tile7 = new Tile(7, LandscapeType.FOREST);
        Tile tile8 = new Tile(8, LandscapeType.WASTELAND);
        Domino domino1 = new Domino(1,tile1,tile2);
        Domino domino2 = new Domino(1,tile8,tile3);
        Domino domino3 = new Domino(1,tile4,tile5);
        Domino domino4 = new Domino(1,tile6,tile7);
        bag.add(domino1);
        bag.add(domino2);
        bag.add(domino3);
        bag.add(domino4);
        allPlayer.add(player1);
        player1.setHighscoreable(false);
        allPlayer.add(player2);
        boolean[] boolSettings1 = new boolean[]{false,true,false,false,false,false};
        toTest.createGame(allPlayer,true,csvPath,boolSettings1);
        toTest.endGame();
        mainController.getKingDomino().getHighscore().getScores();
    }

    @Test
    public void testEndGameDynastie() {
        //create a game
        String csvPath = "";
        LinkedList<Player> players = new LinkedList<>();
        Player player1 = new Player("Hannes", PlayerType.AI_CHALLENGER, ColourType.GREEN);
        Player player2 = new Player("Gerd", PlayerType.HUMAN, ColourType.RED);
        Player player3 = new Player("Lukas",PlayerType.AI_NOOB,ColourType.BLUE);
        players.add(player3);
        players.add(player2);
        boolean[] boolSettings2 = new boolean[]{true,true,true,true,true,true};
        assertTrue(toTest.createGame(players,true,csvPath,boolSettings2));

        // the case where their are no more games to play
        mainController.getKingDomino().getCurrentGame().setGamesToPlay(0);
        LinkedList<Tuple<Player,Integer>> pointsFromBevore = new LinkedList<>();
        pointsFromBevore.add(new Tuple<>(player1,20));
        pointsFromBevore.add(new Tuple<>(player2,15));
        pointsFromBevore.add(new Tuple<>(player3,100));

        toTest.endGameDynastie(pointsFromBevore);
        assertTrue(mainController.getGameController().getDynastyPoints().isEmpty());
    }

    @Test(expected = NullPointerException.class)
    public void testEndGameDynastieWithNull() {
        //create a game
        String csvPath = "";
        LinkedList<Player> players = new LinkedList<>();
        Player player1 = new Player("Hannes", PlayerType.AI_CHALLENGER, ColourType.GREEN);
        Player player2 = new Player("Gerd", PlayerType.HUMAN, ColourType.RED);
        Player player3 = new Player("Lukas",PlayerType.AI_NOOB,ColourType.BLUE);
        players.add(player3);
        players.add(player2);
        boolean[] boolSettings2 = new boolean[]{true,true,true,true,true,true};
        assertTrue(toTest.createGame(players,true,csvPath,boolSettings2));
        //initialize
        mainController.getKingDomino().getCurrentGame().setGamesToPlay(0);
        LinkedList<Tuple<Player,Integer>> pointsFromBevore = new LinkedList<>();
        pointsFromBevore.add(new Tuple<>(player1,20));
        pointsFromBevore.add(new Tuple<>(player2,15));
        pointsFromBevore.add(new Tuple<>(player3,100));
        //second case
        mainController.getKingDomino().getCurrentGame().setGamesToPlay(2);
        toTest.endGameDynastie(pointsFromBevore);
        mainController.getKingDomino().getCurrentGame().getGamesToPlay();
    }
    @Test
    public void testUndo() {
        String csvPath = "";

        Player player1 = new Player("PLAYER1", PlayerType.AI_NOOB, ColourType.BLUE);
        Player player2 = new Player("PLAYER2", PlayerType.HUMAN, ColourType.RED);
        player1.setGrid(new Grid(false));
        player2.setGrid(new Grid(false));
        LinkedList<Player> players = new LinkedList<>();
        players.add(player1);
        players.add(player2);

        boolean[] boolSettings = new boolean[]{false, false, false, false, false, false};
        Domino firstdomino = dominoFactory.getDomino(1);

        assert (toTest.createGame(players, false, csvPath, boolSettings));

        GameState current = mainController.getKingDomino().getCurrentGame();
        boolean exceptioned = false;
        try {
            toTest.undo();
        }
        catch (IllegalStateException i ){
            exceptioned = true;
        }
        assert(exceptioned);

        toTest.turnEnd();

        GameState successor = mainController.getKingDomino().getCurrentGame();
        assert(!current.equals(successor));

        toTest.turnEnd();
        successor = mainController.getKingDomino().getCurrentGame();
        assert(!current.equals(successor));

        toTest.turnEnd();
        successor = mainController.getKingDomino().getCurrentGame();
        assert(!current.equals(successor));

        toTest.undo();
        assert(current.equals(mainController.getKingDomino().getCurrentGame()));
    }

    @Test
    public void testRedo() {
        String csvPath = "";

        Player player1 = new Player("PLAYER1", PlayerType.AI_NOOB, ColourType.BLUE);
        Player player2 = new Player("PLAYER2", PlayerType.HUMAN, ColourType.RED);
        player1.setGrid(new Grid(false));
        player2.setGrid(new Grid(false));
        LinkedList<Player> players = new LinkedList<>();
        players.add(player1);
        players.add(player2);

        boolean[] boolSettings = new boolean[]{false, false, false, false, false, false};
        Domino firstdomino = dominoFactory.getDomino(1);

        assert (toTest.createGame(players, false, csvPath, boolSettings));

        GameState current = mainController.getKingDomino().getCurrentGame();

        toTest.turnEnd();

        toTest.turnEnd();

        toTest.turnEnd();

        GameState succ = mainController.getKingDomino().getCurrentGame();

        toTest.undo();
        assert(current.equals(mainController.getKingDomino().getCurrentGame()));

        toTest.redo();
        assert(succ.equals(mainController.getKingDomino().getCurrentGame()));
    }

    @Test
    public void testSetHighscorableForFixedPlayers() {
        boolean[] boolSettings2 = new boolean[]{true,true,true,true,true,true};
        LinkedList<Player> players = new LinkedList<>();
        LinkedList<Domino> bag = new LinkedList<>();
        LinkedList<Domino> choice = new LinkedList<>();
        Player player1 = new Player("Hannes", PlayerType.AI_CHALLENGER, ColourType.GREEN);
        Player player2 = new Player("Gerd", PlayerType.HUMAN, ColourType.RED);
        Player player3 = new Player("Lukas",PlayerType.AI_NOOB,ColourType.BLUE);
        player1.setHighscoreable(true);
        player2.setHighscoreable(true);
        player3.setHighscoreable(true);
        players.add(player1);
        players.add(player3);
        players.add(player2);
        LinkedList<Player> fixedPlayersList = new LinkedList<>();
        fixedPlayersList.add(player1);
        fixedPlayersList.add(player2);
        fixedPlayersList.add(player3);
        GameState gameState = new GameState(players,boolSettings2,bag,choice,fixedPlayersList);
        mainController.getKingDomino().setCurrentGame(gameState);
        toTest.setHighscoreableForFixedPlayer(gameState);
        Assert.assertFalse(player1.isHighscoreable());

    }

    @Test
    public void testTurnEnd() {
        LinkedList<Domino> choice = new LinkedList<>();
        LinkedList<Domino> bag = new LinkedList<>();
        LinkedList<Player> allPlayer = new LinkedList<>();
        Player player1 = new Player("Hannes",PlayerType.HUMAN,ColourType.GREEN);
        Player player2 = new Player("Hannes",PlayerType.HUMAN,ColourType.RED);
        Player player3 = new Player("Hannes",PlayerType.HUMAN,ColourType.BLUE);
        Player player4 = new Player("Hannes",PlayerType.HUMAN,ColourType.GREEN);
        Tile tile1 = new Tile(1, LandscapeType.FOREST);
        Tile tile2 = new Tile(2, LandscapeType.GRASS);
        Tile tile3 = new Tile(3, LandscapeType.DESERT);
        Tile tile4 = new Tile(4, LandscapeType.ROCKS);
        Tile tile5 = new Tile(5, LandscapeType.FOREST);
        Tile tile6 = new Tile(6, LandscapeType.WASTELAND);
        Tile tile7 = new Tile(7, LandscapeType.FOREST);
        Tile tile8 = new Tile(8, LandscapeType.WASTELAND);
        Domino domino1 = new Domino(1,tile1,tile2);
        Domino domino2 = new Domino(2,tile8,tile3);
        Domino domino3 = new Domino(3,tile4,tile5);
        Domino domino4 = new Domino(4,tile6,tile7);
        Domino domino5 = new Domino(5,tile1,tile2);
        Domino domino6 = new Domino(6,tile8,tile3);
        Domino domino7 = new Domino(7,tile4,tile5);
        Domino domino8 = new Domino(8,tile6,tile7);
        domino1.setPlayer(player1);
        domino2.setPlayer(player2);
        domino3.setPlayer(player3);
        domino4.setPlayer(player4);
        domino5.setPlayer(player4);
        domino6.setPlayer(player3);
        domino7.setPlayer(player2);
        domino8.setPlayer(player1);

        choice.add(domino5);
        choice.add(domino6);
        choice.add(domino7);
        choice.add(domino8);
        bag.add(domino1);
        bag.add(domino2);
        bag.add(domino3);
        bag.add(domino4);
        allPlayer.add(player1);
        boolean[] boolSettings2 = new boolean[]{true,true,true,true,true,true};
        player1.setHighscoreable(true);
        player2.setHighscoreable(true);
        player3.setHighscoreable(true);
        allPlayer.add(player1);
        allPlayer.add(player3);
        allPlayer.add(player2);
        LinkedList<Player> fixedPlayersList = new LinkedList<>();
        fixedPlayersList.add(player1);
        fixedPlayersList.add(player2);
        fixedPlayersList.add(player3);
        GameState gameState = new GameState(allPlayer,boolSettings2,bag,choice,fixedPlayersList);
        mainController.getKingDomino().setCurrentGame(gameState);
        assertEquals(player3, allPlayer.getFirst());
        allPlayer.clear();
        toTest.turnEnd();
        assertEquals(mainController.getKingDomino().getCurrentGame().getPlayers().size(),4);

    }
    @Test
    public void testTurnEndTwo() {
        LinkedList<Domino> choice = new LinkedList<>();
        LinkedList<Domino> current = new LinkedList<>();
        LinkedList<Domino> bag = new LinkedList<>();
        LinkedList<Player> allPlayer = new LinkedList<>();
        Player player1 = new Player("Hannes",PlayerType.HUMAN,ColourType.GREEN);
        Player player2 = new Player("Hannes",PlayerType.HUMAN,ColourType.RED);
        Player player3 = new Player("Hannes",PlayerType.HUMAN,ColourType.BLUE);
        Player player4 = new Player("Hannes",PlayerType.HUMAN,ColourType.GREEN);
        Tile tile1 = new Tile(1, LandscapeType.FOREST);
        Tile tile2 = new Tile(2, LandscapeType.GRASS);
        Tile tile3 = new Tile(3, LandscapeType.DESERT);
        Tile tile4 = new Tile(4, LandscapeType.ROCKS);
        Tile tile5 = new Tile(5, LandscapeType.FOREST);
        Tile tile6 = new Tile(6, LandscapeType.WASTELAND);
        Tile tile7 = new Tile(7, LandscapeType.FOREST);
        Tile tile8 = new Tile(8, LandscapeType.WASTELAND);
        Domino domino1 = new Domino(1,tile1,tile2);
        Domino domino2 = new Domino(2,tile8,tile3);
        Domino domino3 = new Domino(3,tile4,tile5);
        Domino domino4 = new Domino(4,tile6,tile7);
        Domino domino5 = new Domino(5,tile1,tile2);
        Domino domino6 = new Domino(6,tile8,tile3);
        Domino domino7 = new Domino(7,tile4,tile5);
        Domino domino8 = new Domino(8,tile6,tile7);
        domino1.setPlayer(player1);
        domino2.setPlayer(player2);
        domino3.setPlayer(player3);
        domino4.setPlayer(player4);
        domino5.setPlayer(player4);
        domino6.setPlayer(player3);
        domino7.setPlayer(player2);
        domino8.setPlayer(player1);
        current.add(domino5);
        current.add(domino4);
        current.add(domino3);
        bag.add(domino1);
        bag.add(domino2);
        bag.add(domino3);
        bag.add(domino4);
        allPlayer.add(player1);
        boolean[] boolSettings2 = new boolean[]{true,true,true,true,true,true};
        player1.setHighscoreable(true);
        player2.setHighscoreable(true);
        player3.setHighscoreable(true);
        allPlayer.add(player1);
        allPlayer.add(player3);
        allPlayer.add(player2);
        LinkedList<Player> fixedPlayersList = new LinkedList<>();
        fixedPlayersList.add(player1);
        fixedPlayersList.add(player2);
        fixedPlayersList.add(player3);
        GameState gameState = new GameState(allPlayer,boolSettings2,bag,choice,fixedPlayersList);
        mainController.getKingDomino().setCurrentGame(gameState);
        gameState.setCurrent(current);
        assertEquals(player3, allPlayer.getFirst());
        allPlayer.clear();
        toTest.turnEnd();
        assertEquals(mainController.getKingDomino().getCurrentGame().getPlayers().size(),3);

    }

    @Test
    public void testAiGame() {
        boolean[] boolSettings = new boolean[]{false, false, false, false, false, false};
        String csvDominos = "";


        Player player1 = new Player("PLAYER1",PlayerType.AI_NOOB,ColourType.BLUE);
        Player player2 = new Player("PLAYER2",PlayerType.HUMAN,ColourType.RED);
        player1.setGrid(new Grid(false));
        player2.setGrid(new Grid(false));
        LinkedList<Player> players = new LinkedList<>();
        players.add(player1);
        players.add(player2);

        mainController.getGameController().createGame(players, false, csvDominos, boolSettings);
        mainController.getPlayerController().setPlayers(players);
        Player pOne =  mainController.getPlayerController().getPlayers().get(0); //1. Spieler KI
        toTest.aiGame();
        Player pTwo = mainController.getPlayerController().getPlayers().get(0); //1. Spieler nun Human
        assertEquals(pOne, pTwo);


        pOne =  mainController.getPlayerController().getPlayers().get(0); //1. Spieler KI
        toTest.aiGame();
        pTwo = mainController.getPlayerController().getPlayers().get(0); //1. Spieler nun Human

        assertEquals(pOne, pTwo);
    }

    @Test
    public void testShiftChoiceToCurrent() {
        String csvPath = "";

        Player player1 = new Player("PLAYER1", PlayerType.AI_NOOB, ColourType.BLUE);
        Player player2 = new Player("PLAYER2", PlayerType.HUMAN, ColourType.RED);
        player1.setGrid(new Grid(false));
        player2.setGrid(new Grid(false));
        LinkedList<Player> players = new LinkedList<>();
        players.add(player1);
        players.add(player2);

        boolean[] boolSettings = new boolean[]{false, false, false, false, false, false};

        assert (toTest.createGame(players, false, csvPath, boolSettings));

        List<Domino> oldChoice = new LinkedList<>();
        List<Domino> list = new LinkedList<>();
        mainController.getKingDomino().getCurrentGame().setCurrent(list);

        for(Domino domino : mainController.getKingDomino().getCurrentGame().getChoice()){
            oldChoice.add(domino);
            domino.setPlayer(player1);
        }

        toTest.shiftChoiceToCurrent();
        assert(!mainController.getKingDomino().getCurrentGame().getCurrent().isEmpty());

        for( Domino dominoFromCurrent : mainController.getKingDomino().getCurrentGame().getCurrent()){
            boolean isInOldCoice = false;
            for(Domino fromOldChoice : oldChoice){
                if(fromOldChoice.equals(dominoFromCurrent)){
                    isInOldCoice = true;
                }
            }
            assert(isInOldCoice);
        }

        oldChoice = mainController.getKingDomino().getCurrentGame().getChoice();
        boolean notFirstDom = false;

        for(Domino domino : oldChoice ){
            if(notFirstDom){
                domino.setPlayer(player1);
            }
            else{
                notFirstDom = true;
            }
        }
        toTest.shiftChoiceToCurrent();
        assert(oldChoice.equals(mainController.getKingDomino().getCurrentGame().getChoice()));
    }

    @Test
    public void testAllPicked() {
    }

    @Test
    public void testEnterWhenChange() {
    }

    private static class SimulatePlayerAUI implements PlayerAUI {

        @Override
        public void showPlayerControllerError(String msg) {

        }

        @Override
        public void refreshPlayerList() {

        }
    }

    private static class SimulateAUI implements GameAUI {

        static boolean choiceRefreshed;
        static boolean gridRefreshed;
        static boolean playerRefreshed;
        static boolean thrownAway;

        SimulateAUI() {
            choiceRefreshed = false;
            gridRefreshed = false;
            playerRefreshed = false;
            thrownAway = false;
        }

        static boolean getGridRefreshed() {
            return gridRefreshed;
        }

        static boolean getChoiceRefreshed() {
            return choiceRefreshed;

        }

        static boolean getPlayerRefreshed() {
            return playerRefreshed;
        }

        static boolean getThrownAway() {
            return thrownAway;
        }

        @Override
        public void refreshChoice() {
            choiceRefreshed = true;
        }

        @Override
        public void refreshGrid() {
            gridRefreshed = true;
        }

        @Override
        public void refreshCurrentPlayer() {
            playerRefreshed = true;
        }

        @Override
        public void showErrorMessage(String msg) {
            thrownAway = true;
        }

        /**
         * shows logs of the game (all moves of the players)
         *
         * @param msg - log
         */
        @Override
        public void addLogs(String msg) {

        }

        @Override
        public void showWinningMessage(LinkedList<Tuple<Player, Integer>> winners) {

        }

        @Override
        public void showHighscoreMessage(String msg) {

        }

        @Override
        public void refreshDominoColour() {

        }

        @Override
        public void showReserveTip(int positionInChoice) {

        }

        @Override
        public void showPlacementTip(Tuple<Tuple<Integer, Integer>, Tuple<Integer, Integer>> placement) {

        }


    }
}
