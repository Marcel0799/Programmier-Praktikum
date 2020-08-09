package model;

import static org.junit.Assert.*;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Marcel Bienia
 *
 */
public class TestRegister {
	
	File f;
	Register reg;
	Food cola;
	Food fanta;
	Food erdnuesse;
	Food kaffee;
	
	Employee steffen;
	Employee hans;

	@Before
	public void setUp() throws Exception {
		reg = new Register("Register",0,100);
		
		cola = new Food("Cola", 120 , 15, "GetraenkeMarkt", 10, false);
		fanta = new Food("Fanta", 150 , 15, "GetraenkeMarkt", 10, false);
		erdnuesse = new Food("Erdnuesse", 210, 0, "" , 15, false);
		kaffee = new Food("Kaffee", 500, 0, "Aldi", 0, true);
		reg.addFood(cola);
		reg.addFood(fanta);
		reg.addFood(erdnuesse);
		reg.addFood(kaffee);
		reg.addFoodToStock(cola);
		reg.addFoodToStock(fanta);
		reg.addFoodToStock(erdnuesse);
		reg.addFoodToStock(kaffee);
		
		steffen = new Employee("steffen", "123456", 7813, false);
		hans = new Employee("Hans", "asdf" , 7814, true);
		reg.addEmployee(steffen);
		reg.addEmployee(hans);

		reg.setCurrentEmployee(steffen);
	}
	
	@Test
	public void testAddFood() {
		Collection<Food> foods = reg.getFoods();
		assertTrue(foods.contains(cola));
		assertTrue(foods.contains(fanta));
		assertTrue(foods.contains(erdnuesse));
		assertTrue(foods.contains(kaffee));
	}
	
	@Test
	public void testDeleteFood() {	
		reg.deleteFood(cola);
		Collection<Food> foods = reg.getFoods();
		assertFalse(foods.contains(cola));
		reg.addFood(cola);
	}
	
	@Test
	public void testAddFoodToStock() {
		Collection<Food> stock = reg.getStock();
		assertTrue(stock.contains(cola));
		assertTrue(stock.contains(fanta));
		assertTrue(stock.contains(erdnuesse));
		assertTrue(stock.contains(kaffee));
	}
	
	@Test
	public void testAddEmployee() {
		Collection<Employee> employees= reg.getEmployees();
		assertTrue(employees.contains(steffen));
		assertTrue(employees.contains(hans));
	}
	
	@Test
	public void testDeleteEmployee() {
		reg.deleteEmployee(steffen);
		reg.deleteEmployee(hans);
		Collection<Employee> employees= reg.getEmployees();
		assertFalse(employees.contains(steffen));
		assertFalse(employees.contains(hans));	
		reg.addEmployee(steffen);
		reg.addEmployee(hans);
	}
	
	@Test
	public void testCurrentEmployee() {
		reg.setCurrentEmployee(steffen);
		assertTrue(reg.getCurrentEmployee().equals(steffen));
	}
	

	@Test
	public void testBuyFood() {
		assertTrue(reg.getStock().contains(cola));		
		reg.buyFood(cola);
		assertFalse(reg.getStock().contains(cola));
		
		Collection<Transaction> transactions = reg.getCurrentEmployee().getTransactions();
	
		boolean contains = false;
		for (Transaction t : transactions) {
			if (t.getIsPurchase()) {
				Purchase p = (Purchase) t;
				if(p.getFood().equals(cola)) {
					contains = true;
				}
			}
		}
		assertTrue(contains);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testRefundFoodExceptionOne() {
		Purchase pFanta = new Purchase(fanta,true, LocalDateTime.now());
		reg.refundEmpty(pFanta);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testRefundFoodExceptionTwo() {
		Purchase pFanta = new Purchase(fanta,false, LocalDateTime.now());
		reg.refundEmpty(pFanta);
	}
	
	@Test
	public void testRefundFood() {
		reg.buyFood(cola);
		
		Collection<Transaction> transactions = reg.getCurrentEmployee().getTransactions();
		Purchase pCola = new Purchase(fanta,true, LocalDateTime.now());
		
		for (Transaction t : transactions) {
			if (t.getIsPurchase()) {
				Purchase p = (Purchase) t;
				if(p.getFood().equals(cola)) {
					pCola = p;
				}
			}
		}
			
		assertFalse(pCola.getReturned());
		assertTrue(pCola.getFood().equals(cola));

		reg.refundEmpty(pCola);
		assertTrue(reg.getEmpties().contains(cola));
		assertTrue(pCola.getReturned());
	}
	
	@Test
	public void testClearEmpties() {
		reg.buyFood(erdnuesse);
		Purchase pErdnuesse = new Purchase(cola,true,LocalDateTime.now().plusYears(12));
		Collection<Transaction> kaeufe = reg.getCurrentEmployee().getTransactions();
		for(Transaction tFood : kaeufe) {
			if(tFood.getIsPurchase()) {
				Purchase pFood = (Purchase) tFood;
				if(pFood.getFood()==erdnuesse) {
					pErdnuesse = pFood;
				}
			}
		}
		reg.refundEmpty(pErdnuesse);
		assertTrue(reg.getEmpties().contains(erdnuesse));
		reg.clearEmpties();
		assertFalse(reg.getEmpties().contains(erdnuesse));
	}

	@Test
	public void testDebtLimit() {
		assertTrue(reg.getDebtLimit() == 0);
	}

	@Test
	public void testBalanceLimit() {
		assertTrue(reg.getBalanceLimit() == 100);
	}

}
