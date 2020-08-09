package model;
import org.junit.Test;

import static org.junit.Assert.*;

public class ColourTypeTest {

    @Test
    public void NurFuerDieHundertProzent() {
        ColourType yellow = ColourType.YELLOW;
        ColourType blue = ColourType.BLUE;
        ColourType red = ColourType.RED;
        ColourType green = ColourType.GREEN;
        assertEquals(ColourType.YELLOW.getColour(),yellow.getColour());
        assertEquals(ColourType.BLUE.getColour(),blue.getColour());
        assertEquals(ColourType.RED.getColour(),red.getColour());
        assertEquals(ColourType.GREEN.getColour(),green.getColour());
    }
}
