package model;

import junit.framework.TestCase;

/**
 * 
 * @author Marcel Bienia
 *
 */
public class TestFood extends TestCase {

    public void testEquals() {
        Food f1 = new Food("Coca-Cola", 200);
        Food f2 = new Food("Coca-Cola", 200);
        boolean isEqual1 = f1.equals(f2);
        assertTrue(isEqual1);

        Food f3 = new Food("Coca-Cola", 250, 20,
                "", 0, false);
        Food f4 = new Food("Coca-Cola", 260, 20,
                "", 0, false);
        boolean isEqual2 = f4.equals(f3);
        assertFalse(isEqual2);
    }

    public void testHashCode() {
        Food f1 = new Food("Muffin", 420);
        Food f2 = new Food("Muffin", 420);
        Food f3 = new Food("Muffin", 419);

        assertEquals(f1.hashCode(), f2.hashCode());
        assertNotSame(f3.hashCode(), f2.hashCode());
    }

    public void testGetSetName() {
        Food f1 = new Food("Snickers", 200);
        String name1 = f1.getName();
        assertEquals("Snickers", name1);
        f1.setName("Snickers (White Choco)");
        String name2 = f1.getName();
        assertEquals("Snickers (White Choco)", name2);
    }

    public void testGetSetPrice() {
        Food f1 = new Food("MATE", 350, 0,
                "", 100, false);
        int price1 = f1.getPrice();
        int price2 = f1.getPriceWithoutDiscount();
        assertEquals(350, price2 - price1);

        f1.setPrice(100);
        int price3 = f1.getPriceWithoutDiscount();
        assertEquals(100, price3);
    }

    public void testGetSetCashReturn() {
        Food f1 = new Food("NonReturnable-Water", 150, 0,
                "", 0, false);
        int cashReturn1 = f1.getCashReturn();
        assertEquals(0, cashReturn1);

        f1.setCashReturn(25);
        int cashReturn2 = f1.getCashReturn();
        assertEquals(25, cashReturn2);
    }

    public void testGetSetRetailer() {
        Food f1 = new Food("Popeye Spinach", 1);
        String retailer1 = f1.getRetailer();
        assertEquals("", retailer1);

        f1.setRetailer("Sailors Inc.");
        String retailer2 = f1.getRetailer();
        assertEquals("Sailors Inc.",retailer2);
    }

    public void testGetSetDiscount() {
        Food f1 = new Food("Expensive Ice Cream", 99999);
        int discount1 = f1.getDiscount();
        assertEquals(0, discount1);

        f1.setDiscount(99);
        int discount2 = f1.getDiscount();
        assertEquals(99, discount2);
    }

    public void testGetSetSubscription() {
        Food f1 = new Food("Vegan Chicken", 900);
        boolean isSubscription1 = f1.getIsSubscription();
        assertFalse(isSubscription1);

        f1.setIsSubscription(true);
        boolean isSubscription2 = f1.getIsSubscription();
        assertTrue(isSubscription2);
    }
}
