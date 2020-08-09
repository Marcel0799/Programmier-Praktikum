package model;

import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

public class HighscoreTest {

    Highscore highscore1;
    LocalDateTime jetzt = LocalDateTime.now();
    LocalDateTime einMonat = LocalDateTime.now().minusMonths(1);
    LocalDateTime einTag = LocalDateTime.now().minusDays(1);

    Score score1,score2,score3,score4,score5,score6;
    Duration duration1, duration2;

    @Before
    public void init() {
        highscore1 = new Highscore();
        duration1 = Duration.between(einMonat,jetzt);
        duration2 = Duration.between(einTag,jetzt);
        score1 = new Score("Hans",9,duration1,"Gerd");
        score2 = new Score("Gerd",12,duration2,"Hans");
        score3 = new Score("Gerd",20,duration2,"Hans");
        score4 = new Score("Gerd",100,duration2,"Hans");
        score5 = new Score("Gerd",1,duration2,"Hans");
        score6 = new Score("Gerd",19,duration2,"Hans");
        highscore1.add(score1);
        highscore1.add(score2);
        highscore1.add(score3);
        highscore1.add(null);
        highscore1.add(null);
        highscore1.add(score4);
        highscore1.add(score5);
        highscore1.add(score6);
    }

    @Test
    public void testOrder() {
        //first is correct
        assertEquals(highscore1.getScores()[0],score4);
        //first is correkt
        assertEquals(highscore1.getScores()[highscore1.size()-1],score5);
    }

    @Test
    public void testConvertToList() {
        List<Score> scores = highscore1.convertToList();
        assertTrue(scores.contains(score2));
        assertTrue(scores.contains(score4));
    }

    @Test
    public void testClear() {
        highscore1.clear();
        assertTrue(highscore1.getScoresList().isEmpty());
    }

    @Test
    public void testRemove() {
        highscore1.remove(score3);
        assertFalse(highscore1.convertToList().contains(score3));
    }

    @Test
    public void testToMuch() {
        highscore1.add(score5);
        highscore1.add(score5);
        highscore1.add(score5);
        highscore1.add(score5);
        highscore1.add(score5);
        highscore1.add(score5);
        highscore1.add(score5);
        highscore1.add(score5);
        assertEquals(highscore1.size(),10);
    }

}
