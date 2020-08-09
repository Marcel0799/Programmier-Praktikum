package model;

import org.junit.Test;
import static org.junit.Assert.*;

public class PlayerTypeTest {

    @Test
    public void unnoetigerTest() {
        PlayerType human = PlayerType.HUMAN;
        PlayerType ai1 = PlayerType.AI_NOOB;
        PlayerType ai2 = PlayerType.AI_MEDIUM;
        PlayerType ai3 = PlayerType.AI_CHALLENGER;
        assertEquals(human.toString(),PlayerType.HUMAN.toString());
        assertEquals(ai1.toString(),PlayerType.AI_NOOB.toString());
        assertEquals(ai2.toString(),PlayerType.AI_MEDIUM.toString());
        assertEquals(ai3.toString(),PlayerType.AI_CHALLENGER.toString());
    }
}
