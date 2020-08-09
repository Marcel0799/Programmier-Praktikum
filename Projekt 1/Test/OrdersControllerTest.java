package controller;

import model.Food;
import model.Purchase;
import model.Register;
import model.Transaction;
import view.OrdersManageViewController;
import view.aui.OrdersAUI;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Collection;

import static org.junit.Assert.*;


/**
 * this class tests the OrderController
 * it tests creating orders, increasing amounts in stock and clearing the empties
 *
 * @author Marcel Bienia
 * Test for {@link controller.OrdersController}
 */
public class OrdersControllerTest {

    class TestOrdersManageViewControler implements OrdersAUI {

        boolean refreshOrdersCalled = false;
        boolean refreshEmptiesCalled = false;

        @Override
        public void refreshOrders() {
            refreshOrdersCalled = true;
        }

        @Override
        public void refreshEmpties() {
            refreshEmptiesCalled = true;
        }

        @Override
        public void showOrderError(int errorCode) {
        }

    }

    TestOrdersManageViewControler omvc = new TestOrdersManageViewControler();
    MainController mainController = new MainController();
    Register register = new Register("Register", 0, 0);
    OrdersController ordersController = mainController.getOrdersController();
    Food cola = new Food("Cola", 250);
    Food fanta = new Food("Fanta", 300);
    Food sprite = new Food("Sprite", 350);
    Food lift = new Food("Lift", 400);
    LocalDate time = LocalDate.now().plusMonths(2);

    @Before
    public void setUp() throws Exception {
        ordersController.setOrdersAUI(omvc);
        mainController.setRegister(register);
        register.addFood(cola);
        register.addFood(fanta);
    }


    @Test
    public void testNewOrder() {
        ordersController.newOrder(cola, time);
        ordersController.newOrder(fanta, time);

        Collection<Purchase> orders = mainController.getRegister().getOrders();
        boolean contains = false;
        for (Purchase current : orders) {
            if (current.getFood().equals(cola)) {
                contains = true;
            }
        }
        assertTrue(contains);

        contains = false;
        for (Purchase current : orders) {
            if (current.getFood().equals(fanta)) {
                contains = true;
            }
        }
        assertTrue(contains);
    }

    @Test
    public void testNewOrderYesterday() {
        Food fantaTwo = new Food("fanta2", 120);
        ordersController.newOrder(fantaTwo, time);
        assertFalse(mainController.getRegister().getOrders().contains(fantaTwo));
    }

    @Test
    public void testNewOrderNull() {
        ordersController.newOrder(null, time);
        assertTrue(mainController.getRegister().getOrders().isEmpty());
    }

    @Test
    public void testSetter() {
        OrdersAUI ordersAUI = new OrdersManageViewController();
        ordersController.setOrdersAUI(ordersAUI);
        assertEquals(ordersController.getOrderAUI(), ordersAUI);

    }

    @Test
    public void testNewOrderRefresh() {
        Food fantaThree = new Food("fanta3", 130);
        ordersController.newOrder(fantaThree, time);
        Collection<Purchase> orders = mainController.getRegister().getOrders();
        boolean contains = false;
        for (Purchase current : orders) {
            if (current.getFood().equals(fantaThree)) {
                contains = true;
            }
        }
        assertTrue(contains);
        assertTrue(omvc.refreshOrdersCalled);
    }

    @Test
    public void testIncreaseAmount() {
        assertTrue(mainController.getRegister().getStock().isEmpty());
        ordersController.increaseItemAmount(cola, "3");
        ordersController.increaseItemAmount(fanta, "100");
        assertTrue(mainController.getRegister().getStock().size() == 103);
        Collection<Food> stock = mainController.getRegister().getStock();
        assertTrue(stock.contains(cola));
        assertTrue(stock.contains(fanta));
    }

    @Test
    public void testIncreaseAmountNull() {
        ordersController.increaseItemAmount(null, "10");
        assertTrue(mainController.getRegister().getStock().isEmpty());
    }

    @Test
    public void testIncreaseAmountNegativ() {
        ordersController.increaseItemAmount(cola, "-10");
        assertTrue(mainController.getRegister().getStock().isEmpty());
    }

    @Test
    public void testClearEmpties() {
        assertTrue(mainController.getRegister().getEmpties().isEmpty());
        mainController.getRegister().getEmpties().add(cola);
        mainController.getRegister().getEmpties().add(fanta);
        mainController.getRegister().getEmpties().add(cola);
        assertTrue(mainController.getRegister().getEmpties().size() == 3);
        ordersController.clearEmpties();
        assertTrue(mainController.getRegister().getEmpties().size() == 0);
    }

    @Test
    public void testClearEmptiesRefresh() {
        mainController.getRegister().getEmpties().add(cola);
        mainController.getRegister().getEmpties().add(fanta);
        mainController.getRegister().getEmpties().add(cola);
        assertTrue(mainController.getRegister().getEmpties().size() == 3);
        ordersController.clearEmpties();
        assertTrue(mainController.getRegister().getEmpties().size() == 0);
        assertTrue(omvc.refreshEmptiesCalled);
    }
}
