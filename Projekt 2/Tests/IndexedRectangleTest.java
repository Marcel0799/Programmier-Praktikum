package model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class IndexedRectangleTest {
    @Test
    public void test() {
        IndexedRectangle one = new IndexedRectangle(12,13);
        assertEquals(one.getI(),12);
        assertEquals(one.getJ(),13);
        assertEquals(one.toString(),"(12,13)");
    }
}
