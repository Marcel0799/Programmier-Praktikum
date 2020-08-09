package model;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class testCharge {

    Charge charge1;
    Charge charge2;
    Charge charge3;

    @Before
    public void setUp() {
        charge1 = new Charge(10, "First charge");
        charge2 = new Charge(20, "Second charge");
        charge3 = new Charge(20, "Second charge");
    }

    @Test
    public void testGetter() {
        //Value
        assertEquals(10, charge1.getValue());

        //Description
        assertEquals("First charge", charge1.getDescription());

        //Purchase
        assertFalse(charge1.getIsPurchase());

        //TransactionDate
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime time = charge1.getTransactionDate();
        assertTrue(localDateTime.isAfter(time) || localDateTime.isEqual(time));
    }


    @Test
    public void testSetter() {
        charge1.setDescription("Changed Discription");

        //Value
        charge1.setValue(2500);
        assertEquals(2500, charge1.getValue());

        //Description
        assertEquals("Changed Discription", charge1.getDescription());

        //TransactionDate
        LocalDateTime date = LocalDateTime.of(2020, 1, 1, 0, 0);
        charge1.setTransactionDate(date);
        assertEquals(date, charge1.getTransactionDate());
    }

    @Test
    public void testEquals() {
        assertNotEquals(charge1,null);
        assertNotEquals(charge1,String.class);
        assertNotEquals(charge1,charge2);
        assertEquals(charge1, charge1);
    }

    @Test
    public void testHashCode() {
        assertEquals(charge2.hashCode(), charge3.hashCode());
        assertNotSame(charge1.hashCode(), charge2.hashCode());
    }
}
