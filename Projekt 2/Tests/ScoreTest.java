package model;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class ScoreTest {
    LocalDateTime jetzt = LocalDateTime.now();
    LocalDateTime einMonat = LocalDateTime.now().minusMonths(1);
    LocalDateTime einTag = LocalDateTime.now().minusDays(1);

    Score score1,score2,score3;
    Duration duration1, duration2,duration3;
    @Before
    public void init() {
        duration1 = Duration.between(einMonat,jetzt);
        duration2 = Duration.between(einTag,jetzt);
        duration3 = Duration.between(jetzt,einTag); //negativ duration
        score1 = new Score("Hans",12,duration1,"Gerd");
        score2 = new Score("Gerd",12,duration2,"Hans");
        score3 = new Score("falschHerum",24,duration3,"Hand und Gerd");
    }

    @Test
    public void testGetter() {
        assertEquals(score1.getName(),"Hans");
        assertEquals(score1.getEnemies(),"Gerd");
        assertEquals(score1.getPoints(),12);
        assertEquals(score1.getTime(),duration1.toMinutes());
    }

    @Test
    public void testToString() {
        assertEquals(score1.toString(), "Hans, 12 Punkte, Spielzeit: " + score1.getTime() + ", Gegner: " + score1.getEnemies());
    }
}
